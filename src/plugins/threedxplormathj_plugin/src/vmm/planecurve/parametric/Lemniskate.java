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
import java.awt.geom.Point2D;

import vmm.core.LinearAlgebra;
import vmm.core.RealParamAnimateable;
import vmm.core.Transform;
import vmm.core.View;

public class Lemniskate extends DecoratedCurve{
	private RealParamAnimateable bb;
	private int pointCount;
	
	public Lemniskate() {
		bb= new RealParamAnimateable("vmm.planecurve.parametric.Lemniskate.bb","pi/4", "0.0", "1.5707");
		addParameter(bb);
		tmin.setValueAndDefault(0.0);                 // Adjust inherited parameters
		tmax.setValueAndDefault(2*Math.PI);
		setDefaultWindow(-3,3,-3,3);  // Adjust requested range of restricted x- and y-values
		tResolution.setValueAndDefault(600);
		pointCount = 12000;
		randomSquare = fillRandomSquare(pointCount); // initializes the fixed set of random points
		movingSquare = initializeMovingSquare(pointCount); // Completely initialize the array movingSquare
	}
	
	
	/**
	 * Define the x-coordinate function x(t) = (aa + (1-abs(aa))*cos(t))/(1+sin^2(t)) for the curve.
	 */
	double a,b;
	private static double sinsq (double t) {return (1+Math.sin(t)*Math.sin(t));}
	
	public double xValue(double t) {	
		if (t < 0.1){
			b = bb.getValue();
			if ((b < 0.0)||(b > Math.PI/2)){
				bb.setValue(Math.max(0,Math.min(b,Math.PI/2) ));  // This does not belong here
				b = bb.getValue();
			}
			a=3.0*Math.cos((b-Math.PI/8)*4/3);
			b=Math.tan(b);
		}
		return a*(Math.cos(t)/sinsq(t)+(1-b)*(2/sinsq(t)-1)/Math.sqrt(2.0));
	}
	/**
	 * Define the y-coordinate function y(t) = sin(t)*(aa + (1-abs(aa))*cos(t))/(1+sin^2(t)) for the curve.
	 */
	public double yValue(double t) {
		return a*(Math.sin(t)*Math.cos(t)/sinsq(t)+(1-b)*Math.sqrt(2)*Math.sin(t)/sinsq(t));
	}
	
	public View getDefaultView() {
		MMOView view = (MMOView)super.getDefaultView();
		view.simplifyActionMenu = false;  // "true" removes ToggleAction from Menu
		view.setUseCloud(false);   // sets ToggleAction initially to the simpler version		
		return view;
	}
	
	double xa, ya, xb, yb, corn1x,corn1y, foc1x, foc2x, corn2x, corn2y, rotx, roty, tx, ty, nn, ex, ey, dt;
	Point2D Rot = new Point2D.Double();
	
	private void abbreviations(double t){
		foc1x = a/Math.sqrt(2);
		foc2x = -foc1x;
		xa = xValue(t); 
		ya = yValue(t);
		corn1x =a*(Math.cos(t)/sinsq(t)-Math.sqrt(2)*Math.sin(t)*Math.sin(t)/sinsq(t)+Math.sqrt(0.5));
		corn2x =a*(Math.cos(t)/sinsq(t)+Math.sqrt(2)*Math.sin(t)*Math.sin(t)/sinsq(t)-Math.sqrt(0.5));
		corn1y = a*(Math.cos(t)+ Math.sqrt(2))*Math.sin(t)/sinsq(t);
		corn2y = a*(Math.cos(t)- Math.sqrt(2))*Math.sin(t)/sinsq(t);
		ex = corn2x-corn1x;
		ey = corn2y-corn1y;
		nn = Math.sqrt(ex*ex+ey*ey);
		ex = ex/nn; ey = ey/nn;
		Rot = LinearAlgebra.intersectTwoLines(foc1x,0.0, corn1x,corn1y,  foc2x,0.0, corn2x,corn2y);
		rotx = Rot.getX();
		roty = Rot.getY();
		tx = -roty + ya;
		ty = +rotx - xa;
		dt = (tmax.getValue()-tmin.getValue())/tResolution.getValue();
	}
	
	@Override
	protected void drawNeededStuff(Graphics2D g, View view, Transform limits, double t) {
		double rc = 0.03;
		MMOView myView = (MMOView)view;
		Color saveColor = g.getColor();
		abbreviations(t);
		g.setColor(Color. red);
		myView.setStrokeSizeMultiplier(2);
		g.draw(new Ellipse2D.Double(foc1x-rc,-rc,2*rc,2*rc));
		g.draw(new Ellipse2D.Double(foc2x-rc,-rc,2*rc,2*rc));
		g.draw(new Ellipse2D.Double(corn1x-rc,corn1y-rc,2*rc,2*rc));
		g.draw(new Ellipse2D.Double(corn2x-rc,corn2y-rc,2*rc,2*rc));
		g.draw(new Line2D.Double(corn1x,corn1y,foc1x,0.0));
		g.draw(new Line2D.Double(corn2x,corn2y,foc2x,0.0));
		g.draw(new Line2D.Double(corn1x,corn1y,corn2x,corn2y));
		g.setColor(Color.green);
		g.draw(new Line2D.Double(corn2x,corn2y,xa,ya));
		g.draw(new Ellipse2D.Double(xa-rc,ya-rc,2*rc,2*rc));
		if (myView.getUseCloud()){          // boolean useCloud is changed in the ToggleAction
			myView.setStrokeSizeMultiplier(1);
			g.draw(new Line2D.Double(corn1x,corn1y,rotx,roty));
			g.draw(new Line2D.Double(rotx,roty,corn2x,corn2y));
			g.setColor(Color. red);
			myView.setStrokeSizeMultiplier(2); // overwrite green lines partially
			g.draw(new Line2D.Double(corn1x,corn1y,foc1x,0.0));
			g.draw(new Line2D.Double(corn2x,corn2y,foc2x,0.0));
			g.setColor(Color.blue);
			myView.setStrokeSizeMultiplier(1);
			g.draw(new Line2D.Double(rotx,roty, xa, ya));
			g.draw(new Line2D.Double(xa+tx,ya+ty, xa-tx, ya-ty));
			g.draw(new Ellipse2D.Double(rotx-rc, roty-rc, 2*rc,2*rc));
			abbreviations(t-dt);
			movingSquare = moveSquare(pointCount, (corn1x+corn2x)/2, (corn1y+corn2y)/2, ex, ey, 3);
			myView.drawPixels(movingSquare, 0, pointCount);
			abbreviations(t+dt);
			movingSquare = moveSquare(pointCount, (corn1x+corn2x)/2, (corn1y+corn2y)/2, ex, ey, 3);
			myView.drawPixels(movingSquare, 0, pointCount);
		}
		g.setColor(saveColor);
	}
	
}


