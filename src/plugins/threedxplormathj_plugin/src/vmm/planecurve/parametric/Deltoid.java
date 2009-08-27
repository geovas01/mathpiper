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

import vmm.core.Transform;
import vmm.core.View;


/**
 * Defines  parametric Deltoid.
 */
public class Deltoid  extends DecoratedCurve{
	
	
	public Deltoid() {
		tResolution.setValueAndDefault(250);
		tmin.setValueAndDefaultFromString("0");                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("2*pi");
		setDefaultWindow(-4.5,4.5,-4.5,4.5);  // Adjust requested range of restricted x- and y-values
		//MMOView.simplifyActionMenu = true;
	}
	
	private static double sin(double t) {return Math.sin(t);}
	private static double cos(double t) {return Math.cos(t);}
	
	/**
	 * Define the x-coordinate function x(t) = 2*cos(t)+cos(2*t) for the curve.
	 */
	public double xValue(double t) {
		return 2*cos(t)+cos(2*t);
		}

	/**
	 * Define the y-coordinate function y(t) = (2*sin(t)-sin(2*t)) for the curve.
	 */
	public double yValue(double t) {
		return  2*sin(t)-sin(2*t);
       }

	/**
	 * Override the numerical differentiation xValue(t). Returns -2*sin(t)-2*sin(2*t).
	 */
	public double xDerivativeValue(double t){
	return -2*sin(t)-2*sin(2*t);
	}
	
	/**
	 * Override the numerical differentiation yValue(t). Returns 2*cos(t)-2*cos(2*t).
	 */
	public double yDerivativeValue(double t){
	return 2*cos(t)-2*cos(2*t);
	}
	
	
	protected void drawNeededStuff (Graphics2D g, View view, Transform limits, double t){
		MMOView myView = (MMOView)view;
		Color saveColor = g.getColor();
		setStrokeSize(3);
		//myView.setStrokeSizeMultiplier(3); // draws the green Astroid thin while the previous draws thick
		double xa = xValue(t);
		double ya = yValue(t);
		double tx = xDerivativeValue(t);
		double ty = yDerivativeValue(t);
		double etx=tx/Math.sqrt(tx*tx+ty*ty);
		double ety=ty/Math.sqrt(tx*tx+ty*ty);
		g.setColor(Color.blue);
		myView.setStrokeSizeMultiplier(1);
		g.draw(new Ellipse2D.Double(-3,-3,6,6));//circle, on which the small circle runs
		
		g.setColor(Color.red); // for the running circle
		myView.setStrokeSizeMultiplier(3);		
		g.draw(new Ellipse2D.Double(2*cos(t)-1,2*sin(t)-1,2,2));
		myView.setStrokeSizeMultiplier(1);
		g.setColor(Color.lightGray);//circle whos diameter is the Tangent of constant length
		g.draw(new Ellipse2D.Double(cos(t)-2,sin(t)-2,4,4));
		g.draw(new Ellipse2D.Double(cos(t)-0.07, sin(t)-0.07,0.14,0.14)); // and its midpoint
		myView.setStrokeSizeMultiplier(3);
		g.setColor(Color.green); //for stick and earlier part of curve
		g.draw(new Line2D.Double(2*cos(t), 2*sin(t), xa, ya));
		g.setColor(new Color(0,0,0)); // stick point
		g.draw(new Line2D.Double(2*cos(t)+0.9*cos(2*t), 2*sin(t)-0.9*sin(2*t), xa, ya));
		myView.setStrokeSizeMultiplier(1);
		g.setColor(new Color(0,128,0)); // tangent and normal
		g.draw(new Line2D.Double(cos(t)-2*etx, sin(t)-2*ety, cos(t)+2*etx, sin(t)+2*ety));
		g.draw(new Line2D.Double(3*cos(t), 3*sin(t), xa, ya));
		myView.setStrokeSizeMultiplier(3);
		g.setColor(saveColor);
	}
}