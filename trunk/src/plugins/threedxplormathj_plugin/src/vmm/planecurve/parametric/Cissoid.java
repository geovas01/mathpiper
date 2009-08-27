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
 * Defines a parametric Cissoid .
 */
public class Cissoid extends DecoratedCurve {
	
	private RealParamAnimateable aa;
	private int pointCount; 
	double a;
	
	public Cissoid() {
		tResolution.setValueAndDefault(395);
	    aa = new RealParamAnimateable("aa", 1, 2, 0.5);
		addParameter(aa);
		tmin.setValueAndDefault(-4.5);                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("4.5");
		setDefaultWindow(-4,4,-4,4);  // Adjust requested range of restricted x- and y-values
		pointCount = 12000;
		randomSquare = fillRandomSquare(pointCount); // initializes the fixed set of random points
		movingSquare = initializeMovingSquare(pointCount); // Completely initialize the array movingSquare
	}
	
	/**
	 * Define the x-coordinate function x(t) = 2 aa t^3/(1 + t^2) for the curve.
	 */
	public double xValue(double t) {
		return t * yValue(t);
		}

	/**
	 * Define the y-coordinate function y(t) = 2 aa t^2/(1 + t^2) for the curve.
	 */
	public double yValue(double t) {
		a = aa.getValue(); 
		return 2* a * t * t/(1 + t * t ) ;
	}
	
	public View getDefaultView() {
		MMOView view = (MMOView)super.getDefaultView();
		view.simplifyActionMenu = false;  // "true" removes ToggleAction from Menu
		view.setUseCloud(false);   // sets ToggleAction initially to the simpler version
		return view;
	}
	
	double xa, ya, xco, yco, aux, rotx, roty, tx, ty, nn, ex, ey, dt;
	/**
	 * t-dependent abbreviations for drawNeededStuff
	 */
	private void abbreviations(double t){
		a = aa.getValue();
		xa = xValue(t);
		ya = yValue(t);
		aux = Math.sqrt(ya*(2*a-ya));
		xco= xa - Math.signum(xa)*aux;
		yco = 2*ya -a;
		rotx = xa + Math.signum(xa)*aux;
		roty = -a -rotx*xco/(yco+a);
		ex = xco - rotx;
		ey = yco - a;
		//nn = Math.sqrt(ex*ex+ey*ey);
		tx = -roty + ya;
		ty = +rotx - xa;
		dt = (tmax.getValue()-tmin.getValue())/tResolution.getValue();
	}

	@Override
	protected void drawNeededStuff(Graphics2D g, View view, Transform limits, double t) {
		MMOView myView = (MMOView)view;
		setStrokeSize(2);
		Color saveColor = g.getColor();
		abbreviations(t);
		
		g.setColor(Color.red);
		myView.setStrokeSizeMultiplier(1);
		g.draw(new Line2D.Double(-10,a, 10,a));
		g.draw(new Line2D.Double(0,-a, -10*xco/(yco+a),-10));
		myView.setStrokeSizeMultiplier(3);
		g.draw(new Line2D.Double(0,-a, xco,yco)); // from rotation point of hinge to corner
		g.draw(new Line2D.Double(xco,yco, rotx,a));
		myView.setStrokeSizeMultiplier(2);
		g.draw(new Ellipse2D.Double(rotx-.04,a-0.04,0.08,0.08));
		g.draw(new Ellipse2D.Double(-0.06,-a-0.06,0.12,0.12));
		g.setColor(Color.green);
		g.draw(new Ellipse2D.Double(xa-.06,ya-0.06,0.12,0.12));
		if (myView.getUseCloud()){          // boolean useCloud is changed in the ToggleAction
			myView.setStrokeSizeMultiplier(1);
			g.draw(new Line2D.Double(rotx,roty, rotx,a));
			g.draw(new Line2D.Double(rotx,roty, 0,-a));
			g.setColor(Color.blue);
			g.draw(new Line2D.Double(rotx,roty, xa, ya));
			g.draw(new Line2D.Double(xa+tx,ya+ty, xa-tx, ya-ty));
			g.draw(new Ellipse2D.Double(rotx-.06, roty-0.06, 0.12,0.12));
			abbreviations(t-dt);
			movingSquare = moveSquare(pointCount, xa, ya, ex, ey, 3*a);
			myView.drawPixels(movingSquare, 0, pointCount);
			abbreviations(t+dt);
			movingSquare = moveSquare(pointCount, xa, ya, ex, ey, 3*a);
			myView.drawPixels(movingSquare, 0, pointCount);
		}
		g.setColor(saveColor);
	}
	
}
