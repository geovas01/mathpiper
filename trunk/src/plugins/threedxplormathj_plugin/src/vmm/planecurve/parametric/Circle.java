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

import javax.swing.Action;

import vmm.core.I18n;
import vmm.core.RealParamAnimateable;
import vmm.core.Transform;
import vmm.core.View;

/**
 * A circle, given as a parametric plane curve.
 */
public class Circle extends DecoratedCurve  {   // or ConicSection
	
	private RealParamAnimateable radius;
	private int pointCount;

	/**
	 * Construct a circle of radius 3, centered at the origin.  The radius is an animateable
	 * parameter with default animation start and end values 1 and 5.  The circle is given
	 * by the parametric equations (radius*cos(t),radius*sin(t)), for t between 0 and 2*pi.
	 */
	public Circle() {
		radius = new RealParamAnimateable("vmm.planecurve.parametric.Circle.Radius",3,1,5);
		radius.setMinimumValueForInput(Double.MIN_VALUE);
		tmin.setValueAndDefault(0);
		tmax.setValueAndDefaultFromString("2 * pi");
		tResolution.setValueAndDefaultFromString("400");
		addParameter(radius);
		pointCount = 6600;
		randomSquare = fillRandomSquare(pointCount); // initializes the fixed set of random points
		movingSquare = initializeMovingSquare(pointCount); // Completely initialize the array movingSquare
	}
	

	public double xValue(double t) {
		return radius.getValue()*Math.cos(t);
	}

	public double yValue(double t) {
		return radius.getValue()*Math.sin(t);
	}

	public double xDerivativeValue(double t) {
		return -radius.getValue()*Math.sin(t);
	}

	public double yDerivativeValue(double t) {
		return radius.getValue()*Math.cos(t);
	}

	public double x2ndDerivativeValue(double t) {
		return -radius.getValue()*Math.cos(t);
	}

	public double y2ndDerivativeValue(double t) {
		return -radius.getValue()*Math.sin(t);
	}
	
	public View getDefaultView() {
		class CircleView extends MMOView {
			CircleView() {
				setShowAxes(true);
				simplifyActionMenu = false;
				setUseCloud(false);
				useCloudToggle.putValue(Action.NAME, I18n.tr("vmm.planecurve.parametric.PlaneCurveParameteric.ToggleUseCloudForCircle"));
			}
		}
		return new CircleView();
	}


	@Override
	protected void drawNeededStuff(Graphics2D g, View view, Transform limits, double t) { // or drawFociAndDirectrix
		MMOView myView = (MMOView)view;
		Color saveColor = g.getColor();
		setWantedColor(saveColor);
		double xa = xValue(t);
		double ya = yValue(t);
		double r =radius.getValue();
		myView.setStrokeSizeMultiplier(2);
		g.setColor(Color.blue);
		g.draw(new Line2D.Double(-r,0,r,0));
		g.draw(new Line2D.Double( 0, 0,0,-r*Math.signum(ya) ));
		g.setColor(Color.red);
		g.draw(new Line2D.Double(-r,0,xa,ya));
		g.draw(new Line2D.Double(xa,ya,r,0));
		myView.setStrokeSizeMultiplier(1);
		g.draw(new Line2D.Double(xa,ya,0,-r*Math.signum(ya) ));
		if (myView.getUseCloud()){          // boolean useCloud is changed in the ToggleAction
			g.setColor(Color.blue);
			//g.setColor(Color.lightGray);
			g.draw(new Line2D.Double(xa,ya,-xa/5,-ya/5 ));
		}
		g.setColor(saveColor);
	}
	
}
