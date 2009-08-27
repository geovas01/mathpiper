/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve;

import java.awt.Graphics2D;

import vmm.core.View;
import vmm.core3D.Exhibit3D;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;


/**
 * Represents a curve, or set of curve segments, in three-space.  The curve is defined by an array of 3D points.
 */
abstract public class SpaceCurve extends Exhibit3D {
	
	/**
	 * The array of 3D points that define the curve.  This array must be computed in the {@link #makePoints()}.
	 * method.  The curve is formed by connecting the points in the array with line segments.  Null values
	 * are allowed in the array.  A null value effectively breaks the curve into two segments.
	 */
	protected Vector3D[] points;
	
	/**
	 * Subclasses must override this method to compute the array of points that define the curve.
	 * The array must be assigned to the protected variable {@link #points}. 
	 */
	abstract protected void makePoints();
	
	/**
	 * Returns a {@link SpaceCurveView} with antialiasing turned on.
	 */
	public View getDefaultView() {
		View v = new SpaceCurveView();
		v.setAntialiased(true);
		return v;
	}
	
	/**
	 * Get the size of the array of points that define this curve.  If the array has not yet been
	 * computed, because the curve has not yet been drawn, then the return value will be 0.
	 */
	public int getPointCount() {
		return (points == null? 0 : points.length);
	}
	
	/**
	 * Get one of the points from the array that defines the curve.
	 * @param i The index of the point in the array.  This must be in the range 0 to <code>getPointCount()-1</code>,
	 * or an error will occur.
	 * @return The i-th point from the array.  This can be null, indicating a break in the curve.
	 */
	public Vector3D getPoint(int i) {
		return points[i];
	}
	
	/**
	 * Computes the array of points.  This is not meant to be called directlly.
	 */
	protected void computeDrawData3D(View3D view, boolean exhibitNeedsRedraw, Transform3D previousTransform, Transform3D newTransform) {
		if (exhibitNeedsRedraw)
			makePoints(); 
	}

	/**
	 * Draws the curve.  This is not  meant to be called directly.
	 */
	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
		boolean useReverseCollar = false;
		if (view instanceof SpaceCurveView)
			useReverseCollar = ((SpaceCurveView)view).getUseReverseCollar();
		view.drawCollaredCurve(points,useReverseCollar);
	}


}
