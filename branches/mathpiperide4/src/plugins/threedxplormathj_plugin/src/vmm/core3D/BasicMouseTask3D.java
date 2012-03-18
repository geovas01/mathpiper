/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import vmm.core.Display;
import vmm.core.MouseTask;
import vmm.core.TimerAnimation;
import vmm.core.View;

/**
 * A MouseTask that is designed to handle the most common cases of interaction with a three-dimensional
 * Exhibit.  Clicking and dragging with the left mouse button on a 3D Exhibit will do trackball-style rotation
 * of the Exhibit.  Using the middle mouse button, or the left mouse button with the Shift or ALT/Option key, will
 * zoom the Exhibit as the mouse is dragged up and down.  Using the right mouse button, or the left mouse button
 * with the Control or Meta/Command key, will drag the Exhibit.  
 * <p>"Dragging" the exhibit has a special meaning when
 * the exhibit is displayed in dual, sterographic mode. In that case, dragging the mouse horizontally will slide
 * the two views of the exhibit closer together and farther apart.  This is to allow the user to adjust the
 * distance to make it easier to fuse the two views into one 3D view.
 */
public class BasicMouseTask3D extends MouseTask {
	
	private int operation;
	private static final int NONE = 0, DRAG = 1, ZOOM = 2, ROTATE = 3, MOVE_NORMAL_TO_SCREEN = 4;
	
	private int startx, starty;
	private int prevx, prevy;
	private double[] startWindow;
	private double requestedAspectRatio;
	
	private Transform3D transform;
	private double virtualSphereRadius;
	
	private Vector3D previousRay, currentRay;
	private long lastDragTime;
	private long mouseDownTime;
	private boolean startedDragging;
	private boolean saveFastDrawing;
	
	private boolean isStereographView;  // set to true if the View is a View3D that is set up for Stereograph or Cross-Eyed Stereograph viewing.
	                                    // In this case, mouse input has to be interpreted differently.
	private Rectangle stereographLeftEyeRect, stereographRightEyeRect;
	private boolean inLeftEyeRect;
	private Transform3D stereographTransform;
	
	private double zoom_startx_real, zoom_starty_real;  // window coords for mouse start postion for zoom operation.
	private double zoom_startx_pixel, zoom_starty_pixel;  // pixel coords for mouse start position for zoom, adjusted to fill window size for sterographic view

	private double originalObjectDisplacementNormalToScreen;  // For the MOVE_NORMAL_TO_SCREEN operation
	
	/**
	 * Decides which operation to perform, if any.  For stereographic views, a ZOOM or ROTATE operation is begun
	 * only if the mouse click is actually inside one of the two view rectangles.
	 */
	public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
		operation = NONE;
		if (view.getExhibit() == null)
			return false;
		try {
			startWindow = view.getWindow();
			transform = (Transform3D)view.getTransform();
			requestedAspectRatio = Math.abs((transform.getYmaxRequested() - transform.getYminRequested()) 
					/ (transform.getXmaxRequested() - transform.getXminRequested()));
		}
		catch (Exception e) {
			return false;
		}
		if ( (view instanceof View3D) && ((View3D)view).getViewStyle() == View3D.RED_GREEN_STEREO_VIEW
				&& evt.isAltDown() && evt.isShiftDown() )
			operation = MOVE_NORMAL_TO_SCREEN; 
		else if (evt.isAltDown() || evt.isShiftDown())
			operation = ZOOM;
		else if (evt.isControlDown() || evt.isMetaDown())
			operation = DRAG;
		else
			operation = ROTATE;
		startx = prevx = evt.getX();
		starty = prevy = evt.getY();
		isStereographView = (view instanceof View3D) &&
							( ((View3D)view).getViewStyle() == View3D.STEREOGRAPH_VIEW || 
									((View3D)view).getViewStyle() == View3D.CROSS_EYE_STEREO_VIEW );
		if (isStereographView && ! (view.getTransform() instanceof Transform3D) )
			return false;  // shouldn't happen, if things are done in the ordinary way.
		if (isStereographView && operation != DRAG) {
			stereographLeftEyeRect = ((View3D)view).stereographLeftEyeRect();
			stereographRightEyeRect = ((View3D)view).stereographRightEyeRect();
			if (stereographLeftEyeRect.contains(startx, starty)) {
				startx = prevx = startx - stereographLeftEyeRect.x;
				starty = prevy = starty - stereographLeftEyeRect.y;
				inLeftEyeRect = true;
			}
			else if (stereographRightEyeRect.contains(startx, starty)) {
				startx = prevx = startx - stereographRightEyeRect.x;
				starty = prevy = starty - stereographRightEyeRect.y;
				inLeftEyeRect = false;
			}
			else
				return false;  // for sterographic views, ignore clicks outside the viewing rectangles.
			stereographTransform = (Transform3D)view.getTransform().clone();
			stereographTransform.setUpDrawInfo(null,0,0,stereographLeftEyeRect.width,stereographLeftEyeRect.height,
					view.getPreserveAspect(),view.getApplyGraphics2DTransform());
		}
		if (operation == ZOOM) {
			operation = ZOOM;
			if (isStereographView) {
				zoom_startx_real = stereographTransform.getXmin() + startx * ((stereographTransform.getXmax() - stereographTransform.getXmin()) / stereographTransform.getWidth());
				zoom_starty_real = stereographTransform.getYmin() + (stereographTransform.getHeight() - starty) * ((stereographTransform.getYmax() - stereographTransform.getYmin()) / stereographTransform.getHeight());
				Point2D pt = new Point2D.Double(startx,starty);
				stereographTransform.viewportToWindow(pt);
				transform.windowToViewport(pt);
				zoom_startx_pixel = pt.getX();
				zoom_starty_pixel = pt.getY();
			}
			else {
				zoom_startx_real = startWindow[0] + startx * ((startWindow[1] - startWindow[0]) / width);
				zoom_starty_real = startWindow[2] + (height-starty) * ((startWindow[3] - startWindow[2]) / height);
				zoom_startx_pixel = startx;
				zoom_starty_pixel = starty;
			}
		}
		else if (operation == ROTATE) {
			if (isStereographView) {
				virtualSphereRadius = 0.48 * Math.min( Math.abs(stereographTransform.getXmax() - stereographTransform.getXmin()),
						Math.abs(stereographTransform.getYmax() - stereographTransform.getYmin()) );
			}
			else {
				virtualSphereRadius = 0.48 * Math.min( Math.abs(transform.getXmax() - transform.getXmin()),
						Math.abs(transform.getYmax() - transform.getYmin()) );
			}
			operation = ROTATE;
		}
		else if (operation == MOVE_NORMAL_TO_SCREEN)
			originalObjectDisplacementNormalToScreen = ((Transform3D)view.getTransform()).getObjectDisplacementNormalToScreen();
		saveFastDrawing = view.getFastDrawing();
		mouseDownTime = evt.getWhen();
		startedDragging = false;  // won't actualy start until 1/3 second has passed or user moves mouse
		return true;
	}

	/**
	 * Continue an operation that was begun in the <code>doMouseDown</code> method.  The user can change the 
	 * operation in the middle of a mouse drag by pressing and releasing modifier keys.
	 */
	public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
		if (operation == NONE)
			return;
		if (!startedDragging && evt.getWhen() - mouseDownTime < 300 
				&& Math.abs(evt.getX() - startx) < 3 && Math.abs(evt.getY() - starty) < 3)
			return;
		if (!startedDragging) {
			startedDragging = true;
			view.setFastDrawing(true);
		}
		if (operation == MOVE_NORMAL_TO_SCREEN) {
			double extent = Math.max(transform.getXmax() - transform.getXmin(),
					transform.getYmax() - transform.getYmin());
			double change = (double)(evt.getY() - starty) / (height/2) * extent;
			transform.setObjectDisplacementNormalToScreen(originalObjectDisplacementNormalToScreen + change);
			return;
		}
		int newoperation;
		if (evt.isAltDown() || evt.isShiftDown())
			newoperation = ZOOM;
		else if (evt.isControlDown() || evt.isMetaDown())
			newoperation = DRAG;
		else 
			newoperation = ROTATE;
		if (newoperation != operation) {
			operation = newoperation;
			startx = prevx = evt.getX();
			starty = prevy = evt.getY();
			if (isStereographView && operation != DRAG) {
				if (inLeftEyeRect) {
					startx = prevx = startx - stereographLeftEyeRect.x;
					starty = prevy = starty - stereographLeftEyeRect.y;
				}
				else  {
					startx = prevx = startx - stereographRightEyeRect.x;
					starty = prevy = starty - stereographRightEyeRect.y;
				}
				stereographTransform = (Transform3D)view.getTransform().clone();
				stereographTransform.setUpDrawInfo(null,0,0,stereographLeftEyeRect.width,stereographLeftEyeRect.height,
						view.getPreserveAspect(),view.getApplyGraphics2DTransform());
			}
			startWindow = view.getWindow();
			if (newoperation == ZOOM) {
				if (isStereographView) {
					zoom_startx_real = stereographTransform.getXmin() + startx * ((stereographTransform.getXmax() - stereographTransform.getXmin()) / stereographTransform.getWidth());
					zoom_starty_real = stereographTransform.getYmin() + (stereographTransform.getHeight() - starty) * ((stereographTransform.getYmax() - stereographTransform.getYmin()) / stereographTransform.getHeight());
					Point2D pt = new Point2D.Double(startx,starty);
					stereographTransform.viewportToWindow(pt);
					transform.windowToViewport(pt);
					zoom_startx_pixel = pt.getX();
					zoom_starty_pixel = pt.getY();
				}
				else {
					zoom_startx_real = startWindow[0] + startx * ((startWindow[1] - startWindow[0]) / width);
					zoom_starty_real = startWindow[2] + (height-starty) * ((startWindow[3] - startWindow[2]) / height);
					zoom_startx_pixel = startx;
					zoom_starty_pixel = starty;
				}
			}
			else if (newoperation == ROTATE) {
				if (isStereographView) {
					virtualSphereRadius = 0.48 * Math.min( Math.abs(stereographTransform.getXmax() - stereographTransform.getXmin()),
							Math.abs(stereographTransform.getYmax() - stereographTransform.getYmin()) );
				}
				else {
					virtualSphereRadius = 0.48 * Math.min( Math.abs(transform.getXmax() - transform.getXmin()),
							Math.abs(transform.getYmax() - transform.getYmin()) );
				}
			}
			return;
		}
		int currentX = evt.getX();
		int currentY = evt.getY();
		if (isStereographView && operation != DRAG) {
			if (inLeftEyeRect) {
				currentX -= stereographLeftEyeRect.x;
				currentY -= stereographLeftEyeRect.y;
			}
			else {
				currentX -= stereographRightEyeRect.x;
				currentY -= stereographRightEyeRect.y;
			}
		}
		switch (operation) {
		case DRAG:
			if (isStereographView) {
				int offset = evt.getX() - prevx;
				if (offset != 0)
					((View3D)view).moveStereographImages(offset);
				prevx = evt.getX();
			}
			else {
				double offsetX, offsetY;
				double pixelWidth = Math.abs(startWindow[1] - startWindow[0])/width;
				double pixelHeight = Math.abs(startWindow[3] - startWindow[2])/height;
				if ( isStereographView ) {
					double pixelWidthS = Math.abs(stereographTransform.getXmax() - stereographTransform.getXmin()) / stereographLeftEyeRect.width;
					double pixelHeightS = Math.abs(stereographTransform.getYmax() - stereographTransform.getYmin()) / stereographLeftEyeRect.height;
					offsetX =  (startx - currentX) * pixelWidthS;
					offsetY = -(starty - currentY) * pixelHeightS;
				}
				else {
					offsetX =  (startx - currentX) * pixelWidth;
					offsetY = -(starty - currentY) * pixelHeight;
				}
				newWindow(view,startWindow[0]+offsetX,startWindow[1]+offsetX,
						startWindow[2]+offsetY,startWindow[3]+offsetY);
				view.forceRedraw();
			}
			break;
		case ZOOM:
			if (isStereographView) {
				width = stereographTransform.getWidth();
				height = stereographTransform.getHeight();
			}
			double diff = -(starty - currentY) / 200.0;  
			double mag = Math.exp(diff);  // factor of e every 200 pixels
			double newwidth = (startWindow[1] - startWindow[0]) * mag;
			double newheight = (startWindow[3] - startWindow[2]) * mag;
			double newpixelwidth = newwidth / width;
			double newpixelheight = newheight / height;
			double newxmin = zoom_startx_real - newpixelwidth*zoom_startx_pixel;
			double newxmax = newxmin + newwidth;
			double newymin = zoom_starty_real - newpixelheight*(height-zoom_starty_pixel);
			double newymax = newymin + newheight;
			newWindow(view,newxmin,newxmax,newymin,newymax);
			view.forceRedraw();
			break;
		case ROTATE:
			if (prevx == currentX && prevy == currentY)
				break;
			previousRay = mousePointToRay(prevx,prevy);
			currentRay = mousePointToRay(currentX,currentY);
			transform.applyTransvection(previousRay,currentRay);
			prevx = currentX;
			prevy = currentY;
			view.forceRedraw();
			lastDragTime = evt.getWhen();
			break;
		}
	}
	
	/**
	 * Finish an operation that was begun in the <code>doMouseDown</code> method.  If the operation
	 * is ROTATE and the user was still moving the mouse when the mouse button was released, then
	 * an animation is installed in the Display that will continue the rotation indefinitely.
	 */
	public void doMouseUp(MouseEvent evt, final Display display, final View view, int width, int height) {
		if (!startedDragging || operation == NONE)
			return;
		boolean keepGoing = false;
		if (operation == ROTATE) {
			display.repaint();
			final boolean fast = saveFastDrawing;
			final Transform3D tr = transform;
			final Vector3D ray1 = previousRay;
			final Vector3D ray2 = currentRay;
			if (evt.getWhen() - lastDragTime < 200 && currentRay != null
					&& currentRay.minus(previousRay).norm() >= 0.002) {
				keepGoing = true;
				display.installAnimation( new TimerAnimation(0,50) {
					protected void drawFrame() {
						tr.applyTransvection(ray1,ray2);
						view.forceRedraw();
					}
					protected void animationEnding() {
						view.setFastDrawing(fast);
					}
				});
			}
		}
		if (!keepGoing)
			view.setFastDrawing(saveFastDrawing);
		transform = null;
		previousRay = currentRay = null;
		stereographLeftEyeRect = null;
		stereographRightEyeRect = null;
		stereographTransform = null;
		operation = NONE;
	}
	
	/**
	 * Returns the cursor that the Display should use during a drag operation.
	 * The cursor that is returned depends on which operation is being performed
	 * with the mouse.
	 * TODO: Use a better cursor for ZOOMing.
	 */
	public Cursor getCursorForDragging(MouseEvent mouseDownEvent, Display display, View view) {
		if (operation == DRAG)
			return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
		else if (operation == ZOOM)
			return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
		else
			return null;
	}
	
	
	/**
	 * Converts the mouse point (x,y) to a point on the unit sphere.
	 */
	private Vector3D mousePointToRay(int x, int y) {
		
		Transform3D theTransform = isStereographView? stereographTransform : transform;
		
		Point2D pt = new Point2D.Double(x,y);
		theTransform.viewportToWindow(pt);

		Vector3D xdir = theTransform.getImagePlaneXDirection();
		Vector3D ydir = theTransform.getImagePlaneYDirection();
		
		double vx = pt.getX() * xdir.x + pt.getY() * ydir.x;  // The mouse point as a vector in the image plane.
		double vy = pt.getX() * xdir.y + pt.getY() * ydir.y;
		double vz = pt.getX() * xdir.z + pt.getY() * ydir.z;
		
		double normSquared = vx*vx + vy*vy + vz*vz; 
		
		Vector3D answer;
		
		if (normSquared > virtualSphereRadius*virtualSphereRadius) {  // A point lying in the image plane, on the "equator" of the virtual sphere
			double len = Math.sqrt(normSquared);
			answer = new Vector3D(vx*virtualSphereRadius/len, vy*virtualSphereRadius/len, vz*virtualSphereRadius/len);
		}
		else {  // Add a z-coordinate to (vx,vy,vz) bring the length up to the virtualSphereRadius
			 double z = Math.sqrt(virtualSphereRadius*virtualSphereRadius - normSquared);  
			                        // in view coordinates; vector we want is -z*ViewDirection
			 Vector3D zdir = theTransform.getViewDirection(); 
			 answer = new Vector3D(vx - z*zdir.x, vy - z*zdir.y, vz -z*zdir.z);
		}
		
		answer.normalize();
		return answer;
		
	}
	
	/**
	 * Used to set a new window for the View that has the same requested aspect ratio
	 * as the current one, and has the specified x and y ranges as the actual ranges
	 * on the window.
	 */
	private void newWindow(View view,double xmin, double xmax, double ymin, double ymax) {
		double aspect = Math.abs( (ymax - ymin) / (xmax - xmin) );
		if (aspect > requestedAspectRatio) {  // shrink y range
			double shrinkFactor = requestedAspectRatio / aspect;
			double newHeight = (ymax - ymin) * shrinkFactor;
			double middle = (ymax + ymin) / 2;
			ymin = middle - newHeight / 2;
			ymax = middle + newHeight / 2;
		}
		else { // shrink x range
			double shrinkFactor = aspect / requestedAspectRatio;
			double newWidth = (xmax - xmin) * shrinkFactor;
			double middle = (xmax + xmin) / 2;
			xmin = middle - newWidth / 2;
			xmax = middle + newWidth / 2;
		}
		view.setWindow(xmin,xmax,ymin,ymax);
	}



}

