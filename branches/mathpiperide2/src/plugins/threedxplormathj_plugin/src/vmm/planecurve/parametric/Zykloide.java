/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 /**
 * 
 */
package vmm.planecurve.parametric;

/**
 * @author Traudel
 *
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import vmm.core.RealParamAnimateable;
import vmm.core.Transform;
import vmm.core.View;

/**
 * Defines a parametric Cycloid.
 */
public class Zykloide extends DecoratedCurve {
	
	private RealParamAnimateable radius; 

	private RealParamAnimateable stick;
	public Zykloide() {
		tResolution.setValueAndDefault(300);
		radius = new RealParamAnimateable("vmm.planecurve.parametric.Zykloide.radius", 3.0, 3, 3);
	    //velo = new RealParamAnimateable("vmm.planecurve.parametric.Zykloide.velo", 2, 2, 2);
	    stick = new RealParamAnimateable("vmm.planecurve.parametric.Zykloide.stick", 1, 0.2, 4);
		addParameter(radius);		
		//addParameter(velo);
		addParameter(stick);
		tmin.setValueAndDefaultFromString("-8*pi");                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("8*pi");
		setDefaultWindow(-20,20,-6,6);  // Adjust requested range of restricted x- and y-values
	}

	/**
	 * Define the x-coordinate function x(t) = t + r*cos(t) for the curve.
	 */
	public double xValue(double t) {
		double r = radius.getValue(); 
		//double v = velo.getValue(); 
		//double fr = v/r;
		double l = stick.getValue();
		return t + l*r*  Math.cos(t/r);
		}

	/**
	 * Define the y-coordinate function y(t) = -r*sin(t) for the curve.
	 */
	public double yValue(double t) {
		double r = radius.getValue(); 
		double l = stick.getValue();
		return -l*r * Math.sin(t/r)+r;
	}
	
	protected void drawNeededStuff(Graphics2D g, View view, Transform limits, double t){
		Color saveColor = g.getColor();
		double r = radius.getValue(); 
		double l = stick.getValue();
		double tx=1-l*Math.sin(t/r);
		double ty= -l*Math.cos(t/r);
		g.setColor(Color.red);
		if (r > 0){
		       g.draw(new Ellipse2D.Double((t-r),0,2*r,2*r ));}
		else   g.draw(new Ellipse2D.Double((t+r),2*r,-2*r,-
				2*r ));
		g.setColor(Color.blue);
		g.draw(new Line2D.Double(xValue(t),yValue(t),t,0));
		g.draw(new Line2D.Double(xValue(t)-2*tx,yValue(t)-2*ty,xValue(t)+2*tx,yValue(t)+2*ty));
		g.setColor(Color.green);
		g.draw(new Line2D.Double(t,r,xValue(t),yValue(t)));
		g.setColor(saveColor);
	}
}
