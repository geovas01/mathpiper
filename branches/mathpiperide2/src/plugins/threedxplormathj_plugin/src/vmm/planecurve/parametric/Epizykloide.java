/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 /*package vmm.planecurve.parametric;

public class Epizykloide {

}
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
 * Defines  parametric Epi- and Hypo- Cycloids. Sign of frequ determins Epi:+ and Hypo:-
 * Shows rolling wheel and tangent construction.
 */
public class Epizykloide extends DecoratedCurve {
	
	private RealParamAnimateable radius; 
	private RealParamAnimateable frequ; 
	private RealParamAnimateable stick; 
	private int pointCount;
	
	public Epizykloide() {
		tResolution.setValueAndDefault(300);
	    radius = new RealParamAnimateable("vmm.planecurve.parametric.Epizykloide.radius", 3.0, 1.5, 4);
	    frequ = new RealParamAnimateable("vmm.planecurve.parametric.Epizykloide.frequency", 4, 1, 8);
	    stick = new RealParamAnimateable("vmm.planecurve.parametric.Epizykloide.stick", 2, 0.3, 2);
		addParameter(radius);
		addParameter(frequ);
		addParameter(stick);
		tmin.setValueAndDefaultFromString("0");                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("2*pi");
		tResolution.setValueAndDefaultFromString("500");
		setDefaultWindow(-5,5,-5,5);  // Adjust requested range of restricted x- and y-values
		pointCount = 6600;
		randomSquare = fillRandomSquare(pointCount); // initializes the fixed set of random points
		movingSquare = initializeMovingSquare(pointCount); // Completely initialize the array movingSquare
	}

	private double r, fr, l, rr, rrabs, dt;
	
	private void setConstants(){
		r = radius.getValue(); 
		fr = frequ.getValue(); 
		l = stick.getValue();
		rr = r/fr;	
		rrabs=Math.abs(rr);
		dt = (tmax.getValue()-tmin.getValue())/tResolution.getValue();
	}


	private static double sin(double t) {return Math.sin(t);}
	private static double cos(double t) {return Math.cos(t);}
	
	/**
	 * Define the x-coordinate function x(t) = l*rr*cos(fr*t) + r*cos(t) for the curve.
	 */
	public double xValue(double t) {
		setConstants();
		return l*rr*cos(fr*t) + r*cos(t);
		}

	/**
	 * Define the y-coordinate function y(t) = l*rr*sin(fr*t) + r*sin(t) for the curve.
	 */
	public double yValue(double t) {
		//setConstants();
		return l*rr*sin(fr*t) + r*sin(t);
	}
	
	public View getDefaultView() {
		MMOView view = (MMOView)super.getDefaultView();
		view.simplifyActionMenu = false;  // "true" removes ToggleAction from Menu
		view.setUseCloud(false);   // sets ToggleAction initially to the simpler version
		return view;
	}
	
	/**
	 * t-dependent abbreviations for drawNeededStuff
	 */
	double xa, ya, ftx, fty, fox, foy, tx, ty, nn, ex, ey, aux;
	private void abbreviations(double t){
		xa = xValue(t); // r*cos(t) +l*rr*cos(fr*t);
		ya = yValue(t); // r*sin(t)+l*rr*sin(fr*t);
		ftx= (r-rr)*cos(t);
		fty= (r-rr)*sin(t);
		tx =  2*( ya - fty);
		ty =  2*(-xa + ftx);
		ex = cos(fr*t);
		ey = sin(fr*t);
		//nn = Math.sqrt(ex*ex+ey*ey);
		aux= Math.abs(r-rr);}
	
	/**
	 * This drawNeededStuff contains two different startup animations.
	 */
	protected void drawNeededStuff (Graphics2D g, View view, Transform limits, double t){
		MMOView myView = (MMOView)view;
		Color saveColor = g.getColor();
		abbreviations(t);
		fox = ftx; foy = fty;
		g.draw(new Ellipse2D.Double(-aux,-aux,2*aux,2*aux));//circle, on which the small circle runs
		g.setColor(Color.red); // for the running circle
		myView.setStrokeSizeMultiplier(3);		
		g.draw(new Ellipse2D.Double(r*cos(t)-rrabs,r*sin(t)-rrabs,2*rrabs,2*rrabs));
		g.setColor(Color.green); //for stick
		g.draw(new Line2D.Double(r*cos(t), r*sin(t), xa, ya));
		g.setColor(new Color(0,0,0)); // stick point
		g.draw(new Line2D.Double(r*cos(t)+0.9*l*rr*cos(fr*t), r*sin(t)+0.9*l*rr*sin(fr*t), xa, ya));
		myView.setStrokeSizeMultiplier(1);
		g.setColor(new Color(0,150,0)); // tangent
		g.draw(new Line2D.Double(ftx, fty, xa, ya));
		g.draw(new Line2D.Double(xa-tx, ya-ty, xa+tx, ya+ty));
		if (myView.getUseCloud()){          // boolean useCloud is changed in the ToggleAction
			g.setColor(Color.blue);
			abbreviations(t-dt);
			movingSquare = moveSquare(pointCount, xa, ya, ex, ey, 2*r);
			myView.drawPixels(movingSquare, 0, pointCount);
			abbreviations(t+dt);
			movingSquare = moveSquare(pointCount, xa, ya, ex, ey, 2*r);
			myView.drawPixels(movingSquare, 0, pointCount);
		}
		g.setColor(saveColor);
	}
	

}

