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
 * Defines a parametric Tractrix .
 */
public class Tractrix extends DecoratedCurve{
	
	private RealParamAnimateable aa;
	private int pointCount;
	private double AA;
	
	public Tractrix() {
		tResolution.setValueAndDefault(150);
	    aa = new RealParamAnimateable("aa", 1, 0.75, 2);
		addParameter(aa);
		tmin.setValueAndDefault(0.01);                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("0.99*pi");
		setDefaultWindow(-1.75,1.75,-1.75,1.75);  // Adjust requested range of restricted x- and y-values
		pointCount = 18000;
		randomSquare = fillRandomSquare(pointCount); // initializes the fixed set of random points
		movingSquare = initializeMovingSquare(pointCount); // Completely initialize the array movingSquare
	}
	

	/**
	 * Define the x-coordinate function x(t) = aa * sin(t) for the curve.
	 */
	public double xValue(double t) {
		AA = aa.getValue(); 
		return AA * Math.sin(t);
	}

	/**
	 * Define the y-coordinate function y(t) = aa  (cos(t) + log(tan(t/2))) for the curve.
	 */
	public double yValue(double t) {
		//AA = aa.getValue(); 
		return AA *( Math.cos( t ) + Math.log(Math.tan(t/2)));
	}
	
	
	public View getDefaultView() {
		MMOView view = (MMOView)super.getDefaultView();
		view.simplifyActionMenu = false;  // "true" removes ToggleAction from Menu
		view.setUseCloud(false);   // sets ToggleAction initially to the simpler version
		return view;
	}
	
	double xa, ya, xb, yb, rotx, roty, tx, ty, dt, nn, ex, ey;
	private void abbreviations(double t){
		xa = xValue(t);
		ya = yValue(t);
		tx=Math.cos(t);
		ty=-Math.sin(t)+1/Math.sin(t);
		nn = Math.sqrt(tx*tx+ty*ty);
		if (nn == 0) {
			ex = 0; ey = 1;}
		else {
			ex = -ty/nn;
			ey = tx/nn;  }
		roty = -xa*ty/tx + ya;
		rotx = xa - (roty-ya)*ty/tx;
		dt = (tmax.getValue()-tmin.getValue())/tResolution.getValue();
	}
	
	protected void drawNeededStuff(Graphics2D g, View view, Transform limits, double t){
		MMOView myView = (MMOView)view;
		Color saveColor = g.getColor();
		abbreviations(t);
		
		myView.setStrokeSizeMultiplier(3);
		g.setColor(Color.red);
		g.draw(new Line2D.Double(xa,ya, 0, roty));
		g.setColor(saveColor);
		setStrokeSize(3);
		setWantedColor(Color.red);
		g.setColor(saveColor);
		if (myView.getUseCloud()){          // boolean useCloud is changed in the ToggleAction
			myView.setStrokeSizeMultiplier(1);
			g.setColor(Color.green);
			g.draw(new Line2D.Double(0, roty,  rotx, roty));
			g.draw(new Line2D.Double(xa, ya,  rotx, roty));
			g.draw(new Ellipse2D.Double(rotx-0.03, roty-0.03,0.06,0.06));
			g.setColor(Color.blue);
			abbreviations(t-dt);
			movingSquare = moveSquare(pointCount, xa, ya, ex, ey, 2*AA);
			myView.drawPixels(movingSquare, 0, pointCount);
			abbreviations(t+dt);
			movingSquare = moveSquare(pointCount, xa, ya, ex, ey, 2*AA);
			myView.drawPixels(movingSquare, 0, pointCount);
			myView.setStrokeSizeMultiplier(3);
		}
		g.setColor(saveColor);
	}
}
