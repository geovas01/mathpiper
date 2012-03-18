/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import vmm.core.Decoration;
import vmm.core.Exhibit;
import vmm.core.Transform;
import vmm.core.VMMSave;
import vmm.core.View;

/**
 * A decoration that appears as a red tangent line and a blue normal line
 * on a parametric plane curve at a specified value of t.  The length of
 * the lines is 40 pixels.  If the functions that define the curve or
 * their derivatives are undefined at t, or if the length of the tangent
 * vector is zero, then nothing is drawn.
 */
public class TangentAndNormalDecoration extends Decoration {

	private PlaneCurveParametric curve; //private PlaneCurveParametricInterface curve;
	@VMMSave private double t = Double.NaN;
	
	/**
	 * Creates a TangentAndNormalDecoration with no associated curve.  A curve can be
	 * set later with {@link #setCurve(PlaneCurveParametric)}, or it will be set
	 * implicitly when the decoration is drawn. 
	 */
	public TangentAndNormalDecoration() {
	}
	
	
	/**
	 * Returns the curve to which this decoration applies.
	 */
	public PlaneCurveParametric getCurve() {
		return curve;
	}
	
	/**
	 * Sets the curve to which this decoration applies.
	 */
	public void setCurve(PlaneCurveParametric c) {
		curve = c;
	}

	/**
	 * Change the value of t where the decoration appears.  This should
	 * be withing the range of t-values shown on the curve, although
	 * this is not checked.
	 */
	public void setT(double t) {
		this.t = t;
		fireDecorationChangeEvent();
	}
	
	/**
	 * Returns the t-value of the point (x(t),y(t)) where the decoration
	 * is drawn on the curve.  A value of Double.NaN means that the
	 * decoration is not drawn; this is the value when the decoration is
	 * first constructed.
	 */
	public double getT() {
		return t;
	}
	
	/**
	 * Set the t-value to be at one of the points that are used for
	 * drawing the curve. If the curve is null when this is called, it
	 * has no effect.
	 * @param tIndex An index into the array of t-values that the curve
	 * uses to generate the curve.  The index should be in the legal
	 * range; if not, nothing is drawn.  The range is from 0 to
	 * getCurve().getPointCount().
	 */
	public void setIndex(int tIndex) {
		if (curve != null)
			setT(curve.getT(tIndex));
	}

	/**
	 * Draw the decoration.  If the curve is null when this is called, then
	 * curve will be set to the exhibit displayed in the View, which should
	 * be of type PlaneCurveParameteric, if this decoration is being used correctly.
	 */
	public void doDraw(Graphics2D g, View view, Transform limits) {
		if (curve == null && view != null) {
			Exhibit c = view.getExhibit();
			if (c instanceof PlaneCurveParametric)  // It better be!
				curve = (PlaneCurveParametric)c;
			else
				return;
		}
		if (Double.isNaN(t))
			return;
		double x = curve.xValue(t);
		if (Double.isNaN(x) || Double.isInfinite(x))
			return;
		double y = curve.yValue(t);
		if (Double.isNaN(y) || Double.isInfinite(y))
			return;
		double dx = curve.xDerivativeValue(t);
		if (Double.isNaN(dx) || Double.isInfinite(dx))
			return;
		double dy = curve.yDerivativeValue(t);
		if (Double.isNaN(dy) || Double.isInfinite(dy))
			return;
		double length = Math.sqrt(dx*dx + dy*dy);
		if (length == 0)
			return;
		dx = dx/length * 40*limits.getPixelWidth();
		dy = dy/length * 40*limits.getPixelHeight();
		Color saveColor = g.getColor();
		Stroke saveStroke = g.getStroke();
		g.setStroke(new BasicStroke(2*limits.getDefaultStrokeSize()));
		g.setColor(Color.red);
		g.draw(new Line2D.Double(x,y,x+dx,y+dy));
		g.setColor(Color.blue);
		g.draw(new Line2D.Double(x,y,x-dy,y+dx));
		g.setColor(saveColor);
		g.setStroke(saveStroke);
	}
		
}
