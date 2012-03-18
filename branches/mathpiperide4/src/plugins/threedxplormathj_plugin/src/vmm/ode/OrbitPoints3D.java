/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;
import vmm.core.*;

/**
 * This class really just represents a list of 3D points that can be drawn as dots
 * or a connected sequence of line segments.  It is possible to add points and
 * have those points show up in a View3D without redrawing the entire contents 
 * of the View.  (The new points are just added to the existing bitmap in the
 * View.)  This class is meant for storing the points on the soltion curve of
 * an ODE in three dimensions. 
 */
public class OrbitPoints3D  {
	
	/**
	 * A constant fof selecting whether the points should be drawn as individual dots or
	 * should be connectined by lines.
	 */
	public static final int LINES = 0,  // Lines should be drawn from each point to the next.
							DOTS = 1;   // Only a one-pixel dot at each point should be drawn.
	
	private ArrayList<Vector3D> points = new ArrayList<Vector3D>();  // This is the list of points.
	private Color color = null;   // If this is null, the points are drawn in the current drawing color; otherwise, this color is used.
	private int style = LINES;  // Drawing style of points, one of the above constants.
	
	private double dotDiameter = 2.0;  // diameter of ovals used to draw dots when style == DOTS
	
	
	/**
	 * Returns the number of points currently in the list.
	 */
	public int getPointCount() {
		return points.size();
	}
	
	
	/**
	 * Gets the i-th point from the list.  Points are numbered 0, 1, 2, ..., getPointCount() - 1.
	 * An error will occur if i is outside this range.
	 */
	public Vector3D getPoint(int i) {
		return points.get(i);
	}
	
	/**
	 * Get the color that will be used to draw the points.  A null value
	 * indicates that the current drawing color will be used.
	 */ 
	public Color getColor() {
		return color;
	}

	
	/**
	 * Set the color that will be used to draw the points.  A null value
	 * indicates that the current drawing color will be used.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	
	/**
	 * Get the style  used to draw the points, either OrbitPoint3D.LINES or OrbitPoint3D.DOTS.
	 */
	public int getStyle() {
		return style;
	}
	

	/**
	 * Set the style  used to draw the points, either OrbitPoint3D.LINES or OrbitPoint3D.DOTS.
	 * Other values are <b>ignored</b>.
	 */
	public void setStyle(int style) {
		if ( this.style != style && (style == DOTS || style == LINES) ) {
			this.style = style;
		}
	}

	
	/**
	 * Returns the diameter of the dots that are used to draw points, when the draw
	 * style is {@link #DOTS}.
	 * @see #setDotDiameter(double)
	 */
	public double getDotDiameter() {
		return dotDiameter;
	}


	/**
	 * Sets diameter of the dots that are used to draw points, when the draw
	 * style is {@link #DOTS}.  The diameter is specified in pixels.  Values
	 * less than 1 are quitely changed to 1.
	 */
	public void setDotDiameter(double dotDiameter) {
		this.dotDiameter = dotDiameter < 1 ? 1 : dotDiameter;
	}


	/**
	 * Removes all points from the list.  This method does NOT cause a redraw.  
	 */
	public void clear() {
		points.clear();
	}
	
	
	/**
	 * Adds a specified point to the end of the list.  This method does NOT cause a redraw.  A class that
	 * uses this method should make sure that any View that contains the orbit is redrawn.  
	 * NOTE:  A null value will not cause an error.  It will simply produce a break in  the curve.
	 * @see #addNow(View3D, Vector3D)
	 */
	public void addPoint(Vector3D pt) {
		points.add(pt);
	}
	
	
	/**
	 * Adds all the points from an array to the curve by calling {@link #addPoint(Vector3D)} for
	 * each element in the array.  The value of pts must be non-null. A class that
	 * uses this method should make sure that any View that contains the orbit is redrawn.  
	 * NOTE:  Null values IN THE ARRAY will not cause an error.  They will simply produce breaks in  the curve.
	 * @see #addNow(View3D, Vector3D[])
	 */
	public void addPoints(Vector3D[] pts) {
		if (pts != null & pts.length > 0) {
			for (int i = 0; i < pts.length; i++)
				points.add(pts[i]);
		}
	}
	
	
	/**
	 * Adds a point to the list, and show the new point in the specified view.
	 * It does NOT immediately appear in other views of the orbit; it will
	 * only appear when those views are redrawn.
	 * This method tries to add the point to the bitmap of the specified view,
	 * without redrawing the View; if this succeeds the return value of the
	 * method is true.  However, if it fails, nothing is done, and the
	 * return value of the method is false -- uses of this class can use the
	 * return value to test whether the View needs to be redrawn.
	 * @param view The view where the point is to immediately appear.  If this is null, the
	 * point is still added to the list, but no immediately visible changes are made.
	 * @param pt The point to be added.  A null value is not an error; it will just cause a
	 * gap in the curve.
	 */
	public boolean addNow(View3D view, Vector3D pt) {
		points.add(pt);
		if (view != null && pt != null && ( style == DOTS  ||  points.size() > 1 && points.get(points.size()-2) != null)) {
			if (view.beginDrawToOffscreenImage()) {  // Try to draw to the offscreen bitmap
				if (view.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW)
					view.setColor(Color.WHITE);
				else if (color != null)
					view.setColor(color);
				drawPoint(view,points.size()-1);
				view.endDrawToOffscreenImage();
				Display disp = view.getDisplay();
				if (disp != null)
					disp.repaint();  // Causes the modified bitmap to be added to the screen.
				return true;
			}
			else { // couldn't draw to bitmap
				return false;
			}
		}
		else
			return true;  // No changes need to be made, since adding the specified point does not change the picture.
	}

	
	/**
	 * Adds an array full of specified points to the list, and shows them in the
	 * specfied view.  If possible, the points will be added to the View's bitmap
	 * without redrawing the bitmap.  ; if this succeeds the return value of the
	 * method is true.  However, if it fails, nothing is done, and the
	 * return value of the method is false -- uses of this class can use the
	 * return value to test whether the View needs to be redrawn.
	 * @param view The view where the point is to immediately appear.  If this is null, the
	 * point is still added to the list, but no immediately visible changes are made.
	 * @param pts The points to be added.  The value must be non-null.  A null value in this array
	 * is not an error; it will just cause a gap in the curve.
	 */
	public boolean addNow(View3D view, Vector3D[] pts) {
		if (view != null && pts != null && pts.length > 0) {
			addPoints(pts);
			if (view.beginDrawToOffscreenImage()) {  // try to draw to bitmap
				if (color != null)
					view.getTransform().getGraphics().setColor(color);  // NEW CODE TO SUPPORT COLOR;  Soon, this
				                                                        // will be done with simply "view.setColor(color".
				for (int i = points.size() - pts.length; i < points.size(); i++)
					drawPoint(view,i);
				view.endDrawToOffscreenImage();
				Display disp = view.getDisplay();
				if (disp != null)
					disp.repaint();
				return true;
			}
			else {  // can't draw to bitmap
				return false;
			}
		}
		else
			return true;  // no changes need to be made
	}
	

	/**
	 * Draws the point at the specified index, with a connecting line to the
	 * preceding point if appropriate.  An error occurs if the index is
	 * not in the range 0, 1, ..., getPointCount() - 1.  
	 */
	public void drawPoint(View3D view, int index) {
		Vector3D p1, p0;
		p1 = points.get(index);
		if (p1 == null)
			return;
		switch (style) {
		case LINES:
			p0 = index == 0 ? p1 : (Vector3D)points.get(index-1);
			if (p0 == null)
				return;
			view.drawLine(p0,p1);
			break;
		case DOTS:
			view.drawDot(p1, dotDiameter);
			break;
		}
	}
	
	
	/**
	 * Draws the entire list of points.
	 */
	public void draw(Graphics2D g, View3D view, Transform transform) {
		Color saveColor = g.getColor();
		if (view.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW)
			g.setColor(Color.WHITE);
		else if (color != null)
			g.setColor(color);
		if (style == LINES) {
			Vector3D[] pts = new Vector3D[points.size()];
			points.toArray(pts);
			view.drawCurve(pts);
		}
		else {
			for (Vector3D p : points)
				view.drawDot(p, dotDiameter);
		}
		if (color != null)
			g.setColor(saveColor);
	}


}
