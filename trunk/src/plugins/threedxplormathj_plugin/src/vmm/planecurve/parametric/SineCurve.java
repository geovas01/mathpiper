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
import java.awt.geom.Point2D;

import vmm.core.RealParamAnimateable;
import vmm.core.Transform;
import vmm.core.View;


/**
 * Defines a sine curve as a parametric plane curve. The amplitude, frequency,
 * and phase of the curve are parameters that can be set by the user.  By default,
 * the curve has amplitude 1, frequency 1, and phase 0 and is defined on the
 * intervale [0,2pi].
 */
public class SineCurve extends DecoratedCurve {
	
	private RealParamAnimateable amplitude;   // Three parameters that affect the appearance of the exhibit
	private RealParamAnimateable frequency;
	private RealParamAnimateable phase;
	 
	public SineCurve() {

		amplitude = new RealParamAnimateable("vmm.planecurve.parametric.SineCurve.amplitude", 1, 0.5, 2);
		frequency = new RealParamAnimateable("vmm.planecurve.parametric.SineCurve.frequency", 2, 1, 1);
		phase = new RealParamAnimateable("vmm.planecurve.parametric.SineCurve.phase", 0, 0, 0);
		
		phase.setMinimumValueForInput(0);  // Restrict user input phase to the range 0 to 2*pi
		phase.setMaximumValueForInput(2*Math.PI);
		
		frequency.setMinimumValueForInput(Double.MIN_VALUE); // Restrict user input for frequency to positive values.
		
		addParameter(phase);      // Add parameters in the REVERSE of the order that they will appear in dialog boxes
		addParameter(frequency);
		addParameter(amplitude);

		tmin.setValueAndDefault(0);                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("3*pi");
		tResolution.setValueAndDefaultFromString("300");

		setDefaultWindow(-2.5,10,-2,2);  // Adjust requested range of restricted x- and y-values	
		//MMOView.simplifyActionMenu = true; // "true" removes ToggleAction from Menu
	}


	/**
	 * Define the x-coordinate function x(t) = t for the parametric curve.
	 */
	public double xValue(double t) {
		return t;
	}

	/**
	 * Define the y-coordinate function y(t) = a*sin(f*(t-p)), where a is the amplitude, f is the
	 * frequency, and p is the phase.
	 */
	public double yValue(double t) {
		double a = amplitude.getValue();  // Get the current values of the three parameters.
		double f = frequency.getValue();
		double p = phase.getValue();
		return a * Math.sin( f * (t - p) );
	}

	protected void drawNeededStuff(Graphics2D g, View view, Transform limits, double t){
		double a = amplitude.getValue();
		double f = frequency.getValue();
		double p = phase.getValue();
		Color saveColor = g.getColor();
		
		g.setColor(Color.blue);
		double angle = f*t - 2*Math.PI*Math.floor(t*f/2/Math.PI);
		Point2D [] circPts = myCircle(-a, 0, a, 120);
		view.drawCurve(circPts, 0, 120);
		//g.draw(new Ellipse2D.Double(-2*a,-a,2*a,2*a));
		double x = xValue(t);
		double y = yValue(t);
		view.setStrokeSizeMultiplier(2);
		g.setColor(Color.red);
		g.draw(new Line2D.Double(-a,0.0,(a*Math.cos(f*(t-p))-a), a*Math.sin(f*(t-p) ) ));// radius
		if (a==1){
			view.drawCurve(circPts, 0, (int)Math.floor(120*angle/2/Math.PI));
			g.draw(new Line2D.Double(2*Math.PI*Math.floor(t*f/2/Math.PI)/f, 0.0, t,0.0));
		}
		g.setColor(Color.gray);
		g.draw(new Line2D.Double((a*Math.cos(f*(t-p))-a),(a*Math.sin(f*(t-p) )),x,y));
		g.draw(new Line2D.Double(t, 0.0, t, a*Math.sin(f*(t-p))));
		view.setStrokeSizeMultiplier(1);
		g.setColor(saveColor);
		
		//DecoratedCurve.strokeSize = 3;
		//DecoratedCurve.wantedColor = Color.red;
	}

	
}
