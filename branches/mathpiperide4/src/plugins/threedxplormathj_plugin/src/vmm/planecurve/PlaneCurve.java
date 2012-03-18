/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Random;

import vmm.core.Exhibit;
import vmm.core.Transform;
import vmm.core.View;

/**
 * A PlaneCurve represents a curve in the xy-plane that is defined by an array of points.
 * Since a null value in that array is interpreted as a break in the curve, the curve
 * can in fact consist of multiple segments.
 */
abstract public class PlaneCurve extends Exhibit {

	/**
	 * The array of xy-points that define the curve.  A null value in this array is
	 * interpreted as a break in the curve.  This array is initially null; if it is
	 * null at the time the curve is drawn, nothing is done.
	 */
	protected Point2D[] points;
	
	/**
	 * This method should compute the array of points that define the curve.  The
	 * array must be assigned to the protected variable {@link #points}.  This method
	 * is called by the {@link #computeDrawData(View, boolean, Transform, Transform)}
	 * method.
	 */
	abstract protected void makePoints();
	
	/**
	 * Returns the number of points in the Point array for this curve (including any null values).
	 * If the point array has not yet been created, because the curve has not yet been drawn, then
	 * the return value is zero.
	 */
	public int getPointCount() {
		if (points == null)
			return 0;
		else
			return points.length;
	}
	
	/**
	 * Get one of the points in the point array that defines this curve.
	 * @param index the index of the point in the point array.  The value must be in the range from 0 to one less than the
	 * value returned by {@link #getPointCount()}, or an error of type NullPointerException or ArrayIndexOutOfBoundsError
	 * will occur.  
	 * @return the point at the specified index in the point array.  This can be null if the specified position
	 * in the array holds a null value.
	 */
	public Point2D getPoint(int index) {
		return points[index];
	}

	protected void computeDrawData(View view, boolean exhibitNeedsRedraw, Transform previousTransform, Transform newTransform) {
		if (exhibitNeedsRedraw || points == null)
			makePoints();
	}

	protected void doDraw(Graphics2D g, View view, Transform transform) {
		view.drawCurve(points);  // does not draw anything if points is null.
	}
	
	protected Point2D[] randomSquare;
	/**
	 * public Point2D[] fillRandomSquare() puts numPoints random points into a square of edge length 2.
	 */
	public Point2D[] fillRandomSquare(int numPoints){
		Random random = new Random(42);
		Point2D[] randSquare = new Point2D[numPoints];
		for (int i = 0; i < numPoints; i++) {
			randSquare[i] = new Point2D.Double(2*random.nextDouble()-1, 2*random.nextDouble()-1);
		}
		return randSquare;
	}

	protected Point2D[] movingSquare;
	/**
	 * initializeMovingSquare(numPoints) Initializes the array MovingSquare[] and its 
	 * elements only once for each new call of a curve exhibit.
	 * Avoids garbage collection while the dotted square moves along a curve.
	 */
	public Point2D[] initializeMovingSquare(int numPoints){
		movingSquare = new Point2D[numPoints];      // redimensions the member array
		for (int i = 0; i < numPoints; i++) {             
			movingSquare[i] = new Point2D.Double(); // initialize all array elements
		}
		return movingSquare;
	}
	
	/**
	 * Point2D[] moveSquare(...) moves the scaled standard random square along the curve.
	 * The random points are put with .setLocation into movingSquare[] without creating new points.
	 */
	protected Point2D[] moveSquare(int numPoints, double xm, double ym, double ex, double ey, double ll){
		double x, y;
		for (int i = 0; i < numPoints; i++) {
			x = ll*randomSquare[i].getX();
			y = ll*randomSquare[i].getY();
			movingSquare[i].setLocation( xm + x*ex - y*ey,  ym + x*ey + y*ex );
		}
		return movingSquare;
	}
	
}
