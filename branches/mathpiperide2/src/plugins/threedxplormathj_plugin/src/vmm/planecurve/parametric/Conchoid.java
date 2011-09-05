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
 * Defines a parametric Conchoid of Nicomedes .
 */
public class Conchoid extends DecoratedCurve{
	
	private RealParamAnimateable aa; 
	private RealParamAnimateable bb;
	double AA, BB;
	private int pointCount;
	
	public Conchoid() {
		tResolution.setValueAndDefault(400);
	    aa = new RealParamAnimateable("vmm.planecurve.parametric.Conchoid.aa", 2, 0, 4);
	    bb = new RealParamAnimateable("vmm.planecurve.parametric.Conchoid.bb", 1, 1, 1);
		addParameter(aa);
		addParameter(bb);
		tmin.setValueAndDefaultFromString("-pi/2 + 0.000001");                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("3*pi/2 - 0.000001");
		setDefaultWindow(-5,5,-4,5);  // Adjust requested range of restricted x- and y-values
		pointCount = 12000;
		randomSquare = fillRandomSquare(pointCount); // initializes the fixed set of random points
		movingSquare = initializeMovingSquare(pointCount); // Completely initialize the array movingSquare
	}
	
	/**
	 * Define the x-coordinate function x(t) = (aa +bb/cos(t))*cos(t) 
	 */
	public double xValue(double t) {
		AA = aa.getValue(); 
		BB = bb.getValue(); 
		double r = AA + (BB/Math.cos(t));
		return r * Math.sin(t);
		}

	/**
	 * Define the y-coordinate function y(t) = (aa +bb/cos(t))*sin(t) 
	 */
	public double yValue(double t) {
		//AA = aa.getValue(); 
		//BB = bb.getValue(); 
		double r = AA + (BB/Math.cos(t));
		return r * Math.cos(t);
	}
	
	public View getDefaultView() {
		MMOView view = (MMOView)super.getDefaultView();
		view.simplifyActionMenu = false;  // "true" removes ToggleAction from Menu
		view.setUseCloud(false);   // sets ToggleAction initially to the simpler version
		return view;
	}
	
	double xa, ya, xb, yb, tx, ty, m, rotx, roty, nn, dt, ex, ey;
	/**
	 * t-dependent abbreviations for drawNeededStuff
	 */
	private void abbreviations(double t){
		xa = xValue(t);
		ya = yValue(t);
		m = xa/ya;
		rotx = BB*m;
		roty = -rotx*m;
		tx = -(roty-ya);
		ty = +(rotx-xa);
		ex = xa - rotx;
		ey = ya - BB;
		//nn = Math.sqrt(ex*ex+ey*ey);
		dt = (tmax.getValue()-tmin.getValue())/tResolution.getValue();
	}
	@Override
	protected void drawNeededStuff(Graphics2D g, View view, Transform limits, double t) {
		MMOView myView = (MMOView)view;
		setWantedColor(Color.green);
		setStrokeSize(3); 
		abbreviations(t);
		
		myView.setStrokeSizeMultiplier(4);
		g.setColor(Color.green);
		g.draw(new Line2D.Double(xa, ya, BB*m, BB));
		myView.setStrokeSizeMultiplier(1); 
		g.setColor(Color.red);
		g.draw(new Line2D.Double(-20, BB, 20,BB));
		g.setColor(Color.blue);
		if (ya > BB)
			g.draw(new Line2D.Double(0, 0, BB*m, BB));
		else
			if (ya > 0) g.draw(new Line2D.Double(0, 0, xa, ya));
		if (myView.getUseCloud()){          // boolean useCloud is changed in the ToggleAction
			g.draw(new Line2D.Double(rotx, roty, xa, ya));
			g.draw(new Line2D.Double(xa+tx, ya+ty, xa-tx, ya-ty ));
			g.draw(new Ellipse2D.Double(rotx-0.03, roty-0.03,0.06,0.06));
			g.setColor(Color.green);
			g.draw(new Line2D.Double(0, 0, rotx,roty));
			g.draw(new Line2D.Double(rotx,roty, rotx,BB));
			g.setColor(Color.blue);
			abbreviations(t-dt);
			movingSquare = moveSquare(pointCount, xa, ya, ex, ey, 3*BB);
			myView.drawPixels(movingSquare, 0, pointCount);
			abbreviations(t+dt);
			movingSquare = moveSquare(pointCount, xa, ya, ex, ey, 3*BB);
			myView.drawPixels(movingSquare, 0, pointCount);
		}
	}
}
