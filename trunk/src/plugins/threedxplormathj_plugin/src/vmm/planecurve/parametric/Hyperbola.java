/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import vmm.core.RealParamAnimateable;
import vmm.core.Transform;
import vmm.core.View;

/**
 * A hyperbola given by parametric equations (a*cosh(t),b*sinh(t)).  This gives only
 * one branch of the hyperbola.  The second, symmetrical branch is also drawn, but only
 * the first branch is used for animations and decorations.
 */
public class Hyperbola extends ConicSection {
	
	private RealParamAnimateable aa;
	private RealParamAnimateable bb;

	/**
	 * Create a hyperboal given by parametric equations (a*cosh(t),b*singh(t)), for 
	 * t between -2.3 and 2.3.  The initial values of a and b are 1.  a has animation
	 * start and end values both set to 1.  b has animation start and end values of 1 and 1.5.
	 *
	 */
	public Hyperbola() {
		aa = new RealParamAnimateable("vmm.planecurve.parametric.Hyperbola.a",1,1,1);
		bb = new RealParamAnimateable("vmm.planecurve.parametric.Hyperbola.b",1,1,1.5);
		setDefaultWindow(-4,4,-4,4);
		tmin.setValueAndDefault(-2.3);
		tmax.setValueAndDefault(2.3);
		addParameter(bb);
		addParameter(aa);
	}

	/**
	 * Draw the hyperbola.  This overrides the inherited version of this method to draw
	 * the second branch of the hyperbola as well as the first.
	 */
	 public void doDraw(Graphics2D g, View view, Transform limits) {
		if (points.length == 0)
			return;
		super.doDraw(g,view,limits);
		if (view instanceof PlaneCurveParametricView && ((PlaneCurveParametricView)view).fractionToDraw < 1)
			return;
		GeneralPath curve = new GeneralPath();
		curve.moveTo(-(float)points[0].getX(), -(float)points[0].getY());
		for (int i = 1; i < points.length; i++)
			curve.lineTo(-(float)points[i].getX(), -(float)points[i].getY());
		g.draw(curve);
	}

	public double xValue(double t) {
		double exp = Math.exp(t);
		double cosh = (exp+1/exp)/2;
		return bb.getValue()*cosh;
	}

	public double yValue(double t) {
		double exp = Math.exp(t);
		double sinh = (exp-1/exp)/2;
		return aa.getValue()*sinh;
	}

	public double xDerivativeValue(double t) {
		double exp = Math.exp(t);
		double sinh = (exp-1/exp)/2;
		return bb.getValue()*sinh;
	}

	public double yDerivativeValue(double t) {
		double exp = Math.exp(t);
		double cosh = (exp+1/exp)/2;
		return aa.getValue()*cosh;
	}

	public double x2ndDerivativeValue(double t) {
		return xValue(t);
	}

	public double y2ndDerivativeValue(double t) {
		return yValue(t);
	}

	protected void drawFociAndDirectrix(Graphics2D g, View view, Transform limits, double t) {
		double f1_x, f1_y, f2_x, f2_y;  // coordinates of the two foci
		double a = bb.getValue();
		double b = aa.getValue();
		double c = Math.sqrt(Math.abs(a*a + b*b));
		double radius;
		f1_x = c;
		f2_x = -c;
		f1_y = f2_y = 0;
		radius = 2*Math.abs(a);
		Color saveColor = g.getColor();
		double width = Math.abs(limits.getXmax() - limits.getXmin());
		double crossSize = 4*limits.getPixelWidth();
		g.setColor(Color.red);  // for the two foci
		g.draw(new Line2D.Double(f1_x-crossSize, f1_y-crossSize, f1_x+crossSize, f1_y+crossSize));
		g.draw(new Line2D.Double(f1_x-crossSize, f1_y+crossSize, f1_x+crossSize, f1_y-crossSize));
		g.draw(new Line2D.Double(f2_x-crossSize, f2_y-crossSize, f2_x+crossSize, f2_y+crossSize));
		g.draw(new Line2D.Double(f2_x-crossSize, f2_y+crossSize, f2_x+crossSize, f2_y-crossSize));
		g.setColor(Color.blue);
		g.draw(new Ellipse2D.Double(f2_x-radius,f2_y-radius,2*radius,2*radius));
		if (Double.isNaN(t)) {
			g.setColor(saveColor);
			return;
		}
		double x = xValue(t);
		double y = yValue(t);
		g.setColor(new Color(0,150,0)); // For the tangent line.
		double dx = xDerivativeValue(t);
		double dy = yDerivativeValue(t);
		g.draw(new Line2D.Double(x - width*dx, y - width*dy, x + width*dx, y+width*dy));
		g.setColor(Color.red); // for the line from 1st focus to curve
		g.draw(new Line2D.Double(x,y,f2_x,f2_y));
		g.setColor(Color.magenta); // for the triangle
		g.draw(new Line2D.Double(x,y,f1_x,f1_y));
		dx = x - f2_x;
		dy = y - f2_y;
		double length = Math.sqrt(dx*dx+dy*dy);
		double s_x = f2_x + dx/length*radius;
		double s_y = f2_y + dy/length*radius;
		g.draw(new Line2D.Double(x,y,s_x,s_y));
		g.draw(new Line2D.Double(f1_x,f1_y,s_x,s_y));
		view.drawString("F",f1_x+4*limits.getPixelWidth(),f1_y+4*limits.getPixelHeight());
		view.drawString("S",s_x - 10*limits.getPixelWidth(), s_y + 3*limits.getPixelHeight());
		g.setColor(saveColor);
	}
}
