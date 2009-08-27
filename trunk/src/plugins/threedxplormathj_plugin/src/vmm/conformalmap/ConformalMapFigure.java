/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.conformalmap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import vmm.core.Complex;
import vmm.core.Decoration;
import vmm.core.Transform;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;

/**
 * Represents a figure in the complex plane, and the image of that figure under a
 * {@link ConformalMap}.  The figure in the domain can be either a line segment, a line, or a
 * circle.  In any case, the figure is determined by two points; for a circle,
 * the two points are the center of the circle and a point on the circumference.
 */
@VMMSave
public class ConformalMapFigure extends Decoration {
	
	
	public final static int LINE_SEGMENT = 0;
	public final static int LINE = 1;
	public final static int CIRCLE = 2;
	
	
	public final static int DEFAULT_POINTS_ON_CIRCLE = 256;
	public final static int DEFAULT_POINTS_ON_SEGMENT = 250;
	public final static int DEFAULT_POINTS_ON_LINE = 500;
	

	@VMMSave
	private int shape = LINE_SEGMENT;
	
	@VMMSave
	private Color color;  // Default value null uses the view's foreground color.
	
	private Complex[] arguments;  // For drawing the input figure.
	private Complex[] values;     // For drawing the image of the input figure.
	
	@VMMSave
	private Point2D p1;
	
	@VMMSave
	private Point2D p2;
	
	@VMMSave
	private int pointCount;  // number of points used to draw the figure
	
	
	/**
	 * Creates a ConformalMapFigure which represents a line segment in the domain, but for which both the 
	 * points that determine the figure are null.
	 */
	public ConformalMapFigure() {
	}
	
	/**
	 * Create a figure defined by two given points in the domain, using
	 * the constructor {@link #ConformalMapFigure(Point2D, Point2D, int, int)}
	 * with the fourth parameter set equal to zero (giving the default number
	 * of points on the figure).
	 */
	public ConformalMapFigure(Point2D p1, Point2D p2,int shape) {
		this(p1,p2,shape,0);
	}
	
	/**
	 * Create a figure defined by two given points in the domain.  
	 * @param shape the code number for the type of figure to draw.  This should be one
	 * of the constants ConformalMapFigure.LINE_SEGMENT, ConformalMapFigure.LINE, or 
	 * ConformalMapFigure.CIRCLE.  Any other value is treated as equivalent to line segment.
	 * @param p1 one of the two points that define the shape.  For a circle, p1 is the center
	 * of the circle.  For a line segement, it is one of the endpoints.  For a line, it is
	 * a point on the line.
	 * @param p2 one of the two points that define the shape.  For a circle, p2 is a point
	 * on the circle.  For a line segement, it is one of the endpoints.  For a line, it is
	 * a point on the line.
	 * @param pointCount the number of points to use when drawing the figure.  For lines
	 * and line segments, this refers only to the image of the figure.  If the specified
	 * value is less than 2, then the default number of points is used.  The default
	 * value depends on the type of figure.
	 */
	public ConformalMapFigure(Point2D p1, Point2D p2, int shape, int pointCount) {
		setShape(shape);  // Call the setShape(0 method to ignore illegal values.
		setPointCount(pointCount);
		this.p1 = p1;
		this.p2 = p2;
	}

	/**
	 * Gets the color of the figure.
	 * @see #setColor(Color)
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color to be used for drawing the figure. The default value is null,
	 * unless set by a constructor.  (However note that in anaglyph stereo view, the color
	 * is ignored and the figure is drawn in as a maximally bright color.)
	 * @param color the color to be used; if null, then the foreground color of the View
	 * is used.
	 */
	public void setColor(Color color) {
		this.color = color;
		fireDecorationChangeEvent();
	}
	
	/**
	 * Gets the first of the two points that determine the figure.
	 */
	public Point2D getP1() {
		return p1;
	}

	/**
	 * Sets the first of the two points that determine the figure.  For a circle, this is the
	 * center point of the circle.  The default value is null, unless set by a constructor.
	 * @param p1 the point; if null, no figure is drawn.
	 */
	public void setP1(Point2D p1) {
		this.p1 = p1;
		fireDecorationChangeEvent();
	}

	/**
	 * Gets the second of the two points that determine the figure.
	 */
	public Point2D getP2() {
		return p2;
	}

	/**
	 * Sets the second of the two points that detrmine the figure.  For a circle, this is a
	 * point on the circumference of the circle. The default value is null, unless set by a constructor.
	 * @param p2 the point; if null, no figure is drawn.
	 */
	public void setP2(Point2D p2) {
		this.p2 = p2;
		fireDecorationChangeEvent();
	}

	/**
	 * Gets the code number for the shape that is drawn.
	 * @see #getShape()
	 */
	public int getShape() {
		return shape;
	}

	/**
	 * Sets the figure that is to be drawn.  The default is to draw a line segment.
	 * @param shape the code number for the figure.  This should be one of the constants
	 * ConformalMapFigure.LINE_SEGMENT, ConformalMapFigure.LINE, or ConformalMapFigure.CIRCLE.
	 * Any other value is ignored.
	 */
	public void setShape(int shape) {
		if (shape != LINE_SEGMENT && shape != LINE && shape != CIRCLE)
			return;
		this.shape = shape;
		fireDecorationChangeEvent();
	}
	
	/**
	 * Returns the number of points that are used for drawing this figure.
	 * @see #setPointCount(int)
	 */
	public int getPointCount() {
		return pointCount;
	}

	/**
	 * Sets the number of points that are used for drawing the figure.  More points will
	 * give a smoother curve.  (For lines and line segments, the specified number of
	 * points is used only for drawing the image of the figure; in the domain, where the
	 * figure is just a straight line, the line is drawn using just two points.)
	 * @param pointCount the number of points to show on the line.  If the specified 
	 * value is less than two, then the default number of points will be used, as
	 * given by the constants DEFAULT_POINTS_ON_CIRCLE, DEFAULT_POINTS_ON_SEGMENT,
	 * and DEFAULT_POINTS_ON_LINE.
	 */
	public void setPointCount(int pointCount) {
		if (pointCount < 2) {
			switch (this.shape) {
			case CIRCLE:
				pointCount = DEFAULT_POINTS_ON_CIRCLE;
				break;
			case LINE:
				pointCount = DEFAULT_POINTS_ON_LINE;
				break;
			default:
				pointCount = DEFAULT_POINTS_ON_SEGMENT;
			}
		}
		this.pointCount = pointCount;
	}

	public void computeDrawData(View view, boolean exhibitNeedsRedraw, Transform previousTransform, Transform newTransform) {
		if (p1 == null || p2 == null)
			return;
		ConformalMap cm = (ConformalMap)view.getExhibit();
		if (values  == null || exhibitNeedsRedraw) {
			switch (shape){
			case LINE_SEGMENT:
				arguments = new Complex[2];
				arguments[0] = new Complex(p1.getX(), p1.getY());
				arguments[1] = new Complex(p2.getX(), p2.getY());
				if (values == null)
					values = new Complex[pointCount];
				Complex dz = (arguments[1].minus(arguments[0])).dividedBy(pointCount-1);
				for (int i = 0; i < pointCount; i++) {
					Complex arg = arguments[0].plus(dz.times(i));
					values[i] = cm.composedFunction(arg);
				}
				// System.out.println("pointCount: "+pointCount);
				break;
			case LINE:
				double xmn = newTransform.getXmin();
				double xmx = newTransform.getXmax();
				double ymn = newTransform.getYmin();
				double ymx = newTransform.getYmax();
				double bigDim = Math.abs(xmx-xmn)+Math.abs(ymx-ymn);
				double dx=p2.getX() - p1.getX();
				double dy=p2.getY() - p1.getY();
				double lngth=Math.sqrt(dx*dx + dy*dy);
				dx = dx/lngth;
				dy = dy/lngth;
				arguments = new Complex[2];
				arguments[0] = new Complex(p1.getX()-2*bigDim*dx, p1.getY()-2*bigDim*dy);
				arguments[1] = new Complex(p2.getX()+2*bigDim*dx, p2.getY()+2*bigDim*dy);
				if (values == null)
					values = new Complex[pointCount];
				Complex dw = (arguments[1].minus(arguments[0])).dividedBy(pointCount-1);
				for (int i = 0; i < values.length; i++) {
					Complex arg = arguments[0].plus(dw.times(i));
					values[i] = cm.composedFunction(arg);
				}
				break;
			case CIRCLE:
				double radius = Math.sqrt((p1.getX() - p2.getX())*(p1.getX() - p2.getX()) +
						(p1.getY() - p2.getY())*(p1.getY() - p2.getY()));
				arguments = new Complex[pointCount];
				double dtheta= 2* Math.PI/pointCount;
				for (int i = 0;i <pointCount;i++) {
					arguments[i] = new Complex(p1.getX() + radius * Math.cos(i * dtheta), p1.getY() + radius * Math.sin(i * dtheta));
				}
				if (values == null)
					values = new Complex[pointCount];
				for (int i = 0; i <= pointCount-1; i++) 
					values[i] = cm.composedFunction(arguments[i]);
				break;
			}
		}
	}
	
	
	/**
	 * If 3D is enabled, draws a 3D line segment from the stereographic projection of p1 to the
	 * sterographic projection of p2; if not just draws a 2D line segment from p1 to p2.
	 */
	private void whereToDraw(ConformalMap.ConformalMapView cmView, Complex p1,Complex p2) {
		if (cmView.getUse3D()) {
			double [] v1,v2;
			Vector3D V1,V2;
			if (p1.abs2() > 100000) 
				V1 = new Vector3D(0,0,1);
			else {
				v1 = p1.stereographicProjection();
				V1 = new Vector3D(v1[0],v1[1],v1[2]);
			}
			if (p2.abs2() > 100000) 
				V2 = new Vector3D(0,0,1);
			else {
				v2 = p2.stereographicProjection();
				V2 = new Vector3D(v2[0],v2[1],v2[2]); 
			}
			cmView.drawLine(V1,V2);
		}
		else {
			if ((p1.abs2() + p2.abs2()) < 1000000) 
				cmView.drawLine(p1.re, p1.im, p2.re, p2.im);
		}
	}


	public void doDraw(Graphics2D g, View view, Transform transform) {
		if (p1 == null || p2 == null)
			return;
		ConformalMap.ConformalMapView cmView = (ConformalMap.ConformalMapView)view;
		Color saveColor = g.getColor();
		Color colorToUse = color;
		if (colorToUse == null)
			colorToUse = view.getForeground();
		g.setColor(((View3D)view).getViewStyle() == View3D.RED_GREEN_STEREO_VIEW ? Color.WHITE : color);
		switch (shape){
		case LINE_SEGMENT:
			if (cmView.getDrawValueGrid()) {
				for (int i = 0; i < pointCount-1; i++) {
					whereToDraw(cmView, values[i], values[i+1]);
				}
			}
			else {
				view.drawLine(arguments[0].re, arguments[0].im, arguments[1].re, arguments[1].im);
			}
			break;
		case LINE:
			if (cmView.getDrawValueGrid()) {
				for (int i = 0; i < values.length-1; i++) {
					whereToDraw(cmView, values[i], values[i+1]);
				}
			}
			else {
				view.drawLine(arguments[0].re, arguments[0].im, arguments[1].re, arguments[1].im);
			}
			break;
		case CIRCLE:
			if (cmView.getDrawValueGrid()) {
				for (int i = 0; i < pointCount-1; i++) {
					 whereToDraw(cmView, values[i], values[i+1]);
				}
				whereToDraw(cmView, values[pointCount-1], values[0]);				
			}
			else {
				for (int i = 0; i < pointCount-1; i++) {
					view.drawLine(arguments[i].re, arguments[i].im, arguments[i+1].re, arguments[i+1].im);
				}
				view.drawLine(arguments[pointCount-1].re, arguments[pointCount-1].im, arguments[0].re, arguments[0].im);
			}
			break;
		}
		g.setColor(saveColor);
	}

}
