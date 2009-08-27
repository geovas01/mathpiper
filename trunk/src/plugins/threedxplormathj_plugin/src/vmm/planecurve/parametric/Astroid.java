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
import java.awt.geom.Line2D;

import vmm.core.Transform;
import vmm.core.View;

public class Astroid extends DecoratedCurve {
	public Astroid() {
		tResolution.setValueAndDefault(300);
		tmin.setValueAndDefaultFromString("0");                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("2*pi");
		setDefaultWindow(-1.25,1.25,-1.25,1.25);  // Adjust requested range of restricted x- and y-values
	}
	
	private static double Cube(double t) {return t*t*t;}
	private static double sin(double t) {return Math.sin(t);}
	private static double cos(double t) {return Math.cos(t);}
	
	/**
	 * Define the x-coordinate function x(t) = cos(t)^3 for the curve.
	 */
	public double xValue(double t) {
		return Cube(cos(t));
		}

	/**
	 * Define the y-coordinate function y(t) = sin(t)^3 for the curve.
	 */
	public double yValue(double t) {
		return  Cube(sin(t));
       }
	protected void drawNeededStuff (Graphics2D g, View view, Transform limits, double t){
		Color saveColor = g.getColor();
		setWantedColor(Color.RED);
		setStrokeSize(3);
		double xa = Cube(cos(t));
		double ya = Cube(sin(t));
		double tx = -3*cos(t)*cos(t)*sin(t);
		double ty = 3*sin(t)*sin(t)*cos(t);
		double m = tx/ty;
	
		//view.setStrokeSizeMultiplier(4); 
		g.setColor(Color.red); //for stick
		g.draw(new Line2D.Double(0, yValue(t)-xValue(t)*ty/tx, xValue(t)-yValue(t)*tx/ty, 0));	
		g.draw(new Line2D.Double(xa+0.02, ya+0.02, xa-0.02, ya-0.02));
		g.draw(new Line2D.Double(xa-0.02, ya+0.02, xa+0.02, ya-0.02));
		g.setColor(Color.green);
		view.setStrokeSizeMultiplier(1);
		g.draw(new Line2D.Double(-10.0,-10.0, 10.0, 10.0));
		g.draw(new Line2D.Double(-10.0,+10.0, 10.0, -10.0));
		g.draw(new Line2D.Double(1/(m-1)*(xa*m+ya),-1/(m-1)*(xa*m+ya), 1/(m+1)*(ya+m*xa), 1/(m+1)*(ya+m*xa)));
    	view.setStrokeSizeMultiplier(3); 
	    g.setColor(saveColor);
	    //view.setStrokeSizeMultiplier(2);
	}
}

