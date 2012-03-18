/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 * A BasicMouseTask2D is a MouseTask that allows the user to drag and zoom
 * the 2D window that is used to draw the Exhibit in a display.  Dragging
 * is done with the left mouse button.  Zooming is done by dragging up/down
 * with the middle mouse button (or holding down a shift or alt or option key).
 * When draggin with the left mouse button, the user can switch between
 * dragging the exhibit and zooming it by pressing and releasing the shift key.
 * 
 * <p>The user can also use the right mouse key (or hold down the control or command key) to draw
 * a rectangle; the rectangle can be moved while dragging by holding down the shift
 * key.  The View is then zoomed into that rectangle.   The shape of the rectangle
 * is contrained to have the same aspect ration as the display.
 * 
 * <p>All the methods in this class are called by a Display in response to user actions.
 * None of them are meant to be called directly.
 */
public class BasicMouseTask2D extends MouseTask {
	
	private int operation;
	private static final int NONE = 0, DRAG = 1, ZOOM = 2, ZOOM_RECT = 3;
	
	private int startx, starty;
	private int prevx, prevy;
	private double[] startWindow;
	private double requestedAspectRatio;  // aspect ratio of requested x- and y-limits on starting window
	private Rectangle scaleRect; // for ZOOM_RECT operation
	
	private long mouseDownTime;
	private boolean startedDragging;
	private boolean saveFastDrawing;  

	/**
	 * Decides what type of operation to perform in response to a user's mouse click, and
	 * initiates that operation.  This method is called by a Display when the user clicks on
	 * the Display; it is not meant to be called directly.
	 * @return returns true to tell the Display that the drag operation should continue, 
	 * unless there is no Exhibit currently installed in the display.
	 */
	public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
		operation = NONE;
		if (view.getExhibit() == null)
			return false;
		startx = prevx = evt.getX();
		starty = prevy = evt.getY();
		startWindow = view.getWindow();
		Transform tr = view.getTransform();
		requestedAspectRatio = Math.abs((tr.getYmaxRequested() - tr.getYminRequested()) / (tr.getXmaxRequested() - tr.getXminRequested()));
		if (evt.isAltDown() || evt.isShiftDown())
			operation = ZOOM;
		else if (evt.isControlDown() || evt.isMetaDown()) {
			operation = ZOOM_RECT;
			scaleRect = null;
		}
		else
			operation = DRAG;
		saveFastDrawing = view.getFastDrawing();
		mouseDownTime = evt.getWhen();
		startedDragging = false;  // won't actualy start until 1/3 second has passed or user moves mouse
		return true;
	}
	
	/**
	 * Continue processing a drag operation.  
	 */
	public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
		if (!startedDragging && evt.getWhen() - mouseDownTime < 300 
				&& Math.abs(evt.getX() - startx) < 3 && Math.abs(evt.getY() - starty) < 3)
			return;
		if (!startedDragging) {
			startedDragging = true;
			if (operation != ZOOM_RECT)
				view.setFastDrawing(true);
		}
		if (operation == DRAG && !(evt.isControlDown() || evt.isMetaDown())) {
			if (evt.isAltDown() || evt.isShiftDown()) {
				operation = ZOOM;
				startx = evt.getX();
				starty = evt.getY();
				startWindow = view.getWindow();
				return;
			}
		}
		else if (operation == ZOOM && !(evt.isAltDown() || evt.isShiftDown())) {
			operation = DRAG;
			startx = evt.getX();
			starty = evt.getY();
			startWindow = view.getWindow();
			return;
		}
		switch (operation) {
		case DRAG:
			double pixelWidth = Math.abs(startWindow[1] - startWindow[0])/width;
			double pixelHeight = Math.abs(startWindow[3] - startWindow[2])/height;
			double offsetX =  (startx - evt.getX()) * pixelWidth;
			double offsetY = -(starty - evt.getY()) * pixelHeight;
			newWindow(view,startWindow[0]+offsetX,startWindow[1]+offsetX,
					startWindow[2]+offsetY,startWindow[3]+offsetY);
			display.repaint();
			break;
		case ZOOM:
			double diff = -(starty - evt.getY()) / 200.0;  
			double mag = Math.exp(diff);  // factor of e every 200 pixels
			double startx_real = startWindow[0] + startx * ((startWindow[1] - startWindow[0]) / width);
			double starty_real = startWindow[2] + (height-starty) * ((startWindow[3] - startWindow[2]) / height);
			double newwidth = (startWindow[1] - startWindow[0]) * mag;
			double newheight = (startWindow[3] - startWindow[2]) * mag;
			double newpixelwidth = newwidth / width;
			double newpixelheight = newheight / height;
			double newxmin = startx_real - newpixelwidth*startx;
			double newxmax = newxmin + newwidth;
			double newymin = starty_real - newpixelheight*(height-starty);
			double newymax = newymin + newheight;
			newWindow(view,newxmin,newxmax,newymin,newymax);
			display.repaint();
			break;
		case ZOOM_RECT:
			if (evt.isShiftDown()) { // move the rectangle, if any
				if (scaleRect != null) {
					int movex = evt.getX() - prevx;
					int movey = evt.getY() - prevy;
					startx += movex;
					starty += movey;
					prevx = evt.getX();
					prevy = evt.getY();
					scaleRect.translate(movex,movey);
				}
			}
			else { // change the rectangle's shape
				prevx = evt.getX();
				prevy = evt.getY();
				if (prevx < 0)
					prevx = 0;
				else if (prevx >= width)
					prevx = width-1;
				if (prevy < 0)
					prevy = 0;
				else if (prevy >= height)
					prevy = height-1;
				if (Math.abs(prevx - startx) < 3 || Math.abs(prevy - starty) < 3)
					scaleRect = null;
				else {
					if (scaleRect == null)
						scaleRect = new Rectangle();
					scaleRect.setBounds(startx,starty,0,0);
					if (view.getPreserveAspect()) {
						int w = Math.abs(prevx - startx);
						int h = Math.abs(prevy - starty);
						double aspect = (double)width / height;
						double rectAspect = (double)w / h;
						if (aspect > rectAspect)
							w = (int)(w*aspect/rectAspect+0.499);
						else if (aspect < rectAspect)
							h = (int)(h*rectAspect/aspect+0.499);
						scaleRect.add(prevx>startx? startx+w : startx-w, prevy>starty? starty+h : starty-h);
					}
					else
						scaleRect.add(prevx,prevy);
				}
			}
			display.repaint();
			break;
		}
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
	
	/**
	 *  Finish processing a drag operation.
	 */
	public void doMouseUp(MouseEvent evt, Display display, View view, int width, int height) {
		if (!startedDragging)
			return;
		if (operation == ZOOM_RECT && scaleRect != null) {
			double pixelWidth = (startWindow[1] - startWindow[0])/width;
			double pixelHeight = (startWindow[3] - startWindow[2])/height;
			double newxmin = startWindow[0] + scaleRect.x*pixelWidth;
			double newxmax = newxmin + scaleRect.width*pixelWidth;
			double newymax = startWindow[3] - (scaleRect.y)*pixelHeight;
			double newymin = newymax - scaleRect.height*pixelHeight;
			newWindow(view,newxmin,newxmax,newymin,newymax);
			display.repaint();
		}
		view.setFastDrawing(saveFastDrawing);
	}
	
		
	/**
	 * Returns the cursor that the Display should use during a drag operation.
	 * The cursor that is returned depends on which operation is being performed
	 * with the mouse.
	 */
	public Cursor getCursorForDragging(MouseEvent mouseDownEvent, Display display, View view) {
		if (operation == ZOOM_RECT)
			return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		else if (operation == DRAG)
			return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
		else if (operation == ZOOM)
			return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
		else
			return null;
	}
	
	/**
	 * When the Display is repainted during a drag operation, this method will be
	 * called after the Exhibit is drawn, to give the MouseTask a chance to draw
	 * some extra stuff on top of the Exhibit.  In this class, it draws nothing
	 * unless the operation that is being performed is to draw a rectangle for 
	 * zooming; if that is the operation, then the rectangle is drawn in red.
	 */
	public void drawWhileDragging(Graphics2D g, Display display, View view, int width, int height) {
		if (operation != ZOOM_RECT || scaleRect == null)
			return;
		if (Math.abs(startx - prevx) < 3 || Math.abs(starty - prevy) < 3)
			return;
		g.setColor(Color.WHITE);
		g.drawRect(scaleRect.x-1,scaleRect.y-1,scaleRect.width+2,scaleRect.height+2);
		g.drawRect(scaleRect.x+1,scaleRect.y+1,scaleRect.width-2,scaleRect.height-2);
		g.setColor(Color.BLACK);
		g.drawRect(scaleRect.x,scaleRect.y,scaleRect.width,scaleRect.height);
	}
}


