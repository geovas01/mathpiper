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
import java.awt.geom.Line2D;

import vmm.core.RealParamAnimateable;
import vmm.core.Transform;
import vmm.core.View;

/**
 * An ellipse, represented as a parametric plane curve.  The ellipse is given by the parametric
 * equations (a*cos(t),b*sin(t)), where a and b are the semi-axes of the ellipse, for t between
 * 0 and 2*pi.
 */
public class Ellipse extends ConicSection {
	
	private RealParamAnimateable verticalRadius;
	private RealParamAnimateable horizontalRadius;

	/**
	 * Construct an Ellipse that is centered at the origin and initially has vertical "radius" 2 and
	 * horizontal "radius" 3.  The radii are animateable parameters of the exhibit.  The default
	 * animation start and end values are 1 and 3 for the vertical radius and 5 and 1 for the horizontal radius.
	 */
	public Ellipse() {
		verticalRadius = new RealParamAnimateable("vmm.planecurve.parametric.Ellipse.VerticalRadius",2,1,3);
		horizontalRadius = new RealParamAnimateable("vmm.planecurve.parametric.Ellipse.HorizontalRadius",3,5,1);
		setDefaultWindow(-6.5,6.5,-6.5,6.5);
		tmin.setValueAndDefault(0);
		tmax.setValueAndDefaultFromString("2 * pi");
		addParameter(verticalRadius);
		addParameter(horizontalRadius);
	}
	

	public double xValue(double t) {
		return horizontalRadius.getValue()*Math.cos(t);
	}

	public double yValue(double t) {
		return verticalRadius.getValue()*Math.sin(t);
	}

	public double xDerivativeValue(double t) {
		return -horizontalRadius.getValue()*Math.sin(t);
	}

	public double yDerivativeValue(double t) {
		return verticalRadius.getValue()*Math.cos(t);
	}

	public double x2ndDerivativeValue(double t) {
		return -horizontalRadius.getValue()*Math.cos(t);
	}

	public double y2ndDerivativeValue(double t) {
		return -verticalRadius.getValue()*Math.sin(t);
	}

	protected void drawFociAndDirectrix(Graphics2D g, View view, Transform limits, double t) {
		double f1_x, f1_y, f2_x, f2_y;  // coordinates of the two foci
		double a = horizontalRadius.getValue();
		double b = verticalRadius.getValue();
		double c = Math.sqrt(Math.abs(a*a - b*b));
		double radius;
		if (a*a > b*b) {
			f1_x = c;
			f2_x = -c;
			f1_y = f2_y = 0;
			radius = 2*Math.abs(a);
		}
		else {
			f1_y = c;
			f2_y = -c;
			f1_x = f2_x = 0;
			radius = 2*Math.abs(b);
		}
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
		view.drawString("S",s_x + 3*limits.getPixelWidth(), s_y + 2*limits.getPixelHeight());
		g.setColor(saveColor);
	}
}
