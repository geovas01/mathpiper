/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 /*package vmm.planecurve.parametric;

public class Convex {

}
*/

package vmm.planecurve.parametric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import vmm.core.RealParamAnimateable;
import vmm.core.Transform;
import vmm.core.View;

/**
 * Defines  parametric Epi- and Hypo- Cycloids. Sign of frequ determins Epi:+ and Hypo:-
 * Shows rolling wheel and tangent construction.
 */
public class Convex extends DecoratedCurve { //PlaneCurveParametric{ //DecoratedCurve { //
	
	private RealParamAnimateable aa; 
	private RealParamAnimateable bb; 
	private RealParamAnimateable cc;
	private RealParamAnimateable dd; 
	private RealParamAnimateable ee; 
	private RealParamAnimateable ff;
	private RealParamAnimateable phase;
	private double a,  b,  c,  d,  e,  f, ph;	

	public Convex() {
		tResolution.setValueAndDefault(300);
	    aa = new RealParamAnimateable("vmm.planecurve.parametric.Convex.aa", 15.0, 15.0, 15.0);
	    bb = new RealParamAnimateable("vmm.planecurve.parametric.Convex.bb", 0.0, 0.0, 0.0);
	    cc = new RealParamAnimateable("vmm.planecurve.parametric.Convex.cc", 0.0, 0.0, 0.0);
	    dd = new RealParamAnimateable("vmm.planecurve.parametric.Convex.dd", 1.5, 1.5, 0.0);
	    ee = new RealParamAnimateable("vmm.planecurve.parametric.Convex.ee", 0.0, 0.0, 0.0);
	    ff = new RealParamAnimateable("vmm.planecurve.parametric.Convex.ff", 0.0, 0.0, 0.6);
	    phase = new RealParamAnimateable("vmm.planecurve.parametric.Convex.phase", 0.0, 0.0, 0.0);
	    addParameter(phase);
	    addParameter(ff);
		addParameter(ee);
		addParameter(dd);
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		tmin.setValueAndDefaultFromString("0");                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("2*pi");
		setDefaultWindow(-18,18,-18, 18);  // Adjust requested range of restricted x- and y-values
	}

	private void setConstants(){
		a = aa.getValue();
		b = bb.getValue();
		c = cc.getValue();
		d = dd.getValue();
		e = ee.getValue();
		f = ff.getValue();
		ph = phase.getValue();
	}
	
	private static double sin(double t) {return Math.sin(t);}
	private static double cos(double t) {return Math.cos(t);}
	
	/**
	 * Minkowski support function for the curve:
	 * supportFunction(t) = a+ b*cos(t)+ c*cos(2*t)+ d*cos(3*t)+ e*cos(4*t)+ f*cos(5*t)
	 */
	private double supportFunction(double t){
		setConstants();
		return a+ b*cos(t)+ c*cos(2*t)+ d*cos(3*t)+ e*cos(4*t)+ f*cos(5*t);
	}
	
	/**
	 * Derivative of Minkowski support function =
	 *   -b*sin(t)- 2*c*sin(2*t)- 3*d*sin(3*t)- 4*e*sin(4*t)- 5*f*sin(5*t)
	 */
	private double supportDerivative(double t){
		//setConstants();
		return -b*sin(t)- 2*c*sin(2*t)- 3*d*sin(3*t)- 4*e*sin(4*t)- 5*f*sin(5*t);
	}
	
//	/**
//	 * Second derivative of Minkowski support function =
//	 *  -b*cos(t)- 4*c*cos(2*t)- 9*d*cos(3*t)- 16*e*cos(4*t)- 25*f*cos(5*t);
//	 */
//	private double support2ndDerivative(double t){
//		return -b*cos(t)- 4*c*cos(2*t)- 9*d*cos(3*t)- 16*e*cos(4*t)- 25*f*cos(5*t);
//	}
	
	/**
	 * Define the x-coordinate function for the curve:
	 * x(t) = supportFunction(t)*cos(t) - supportDerivative(t)*sin(t).
	 */
	public double xValue(double t) {
		return supportFunction(t)*cos(t-ph) - supportDerivative(t)*sin(t-ph);
		}

	/**
	 * Define the y-coordinate function for the curve:
	 * y(t) =  supportFunction(t)*sin(t) + supportDerivative(t)*cos(t).
	 */
	public double yValue(double t) {
		return supportFunction(t)*sin(t-ph) + supportDerivative(t)*cos(t-ph) + supportFunction(ph-Math.PI/2) - a;
	}
	
	protected void drawNeededStuff (Graphics2D g, View view, Transform limits, double t){
		Color saveColor = g.getColor();
		g.setColor(Color.lightGray);
		view.setStrokeSizeMultiplier(6);
		double h = a*1.02;
		g.draw(new Line2D.Double(-40, -h, +40, -h));
		g.draw(new Line2D.Double(-40, +h, +40, +h));
		g.setColor(saveColor);
		view.setStrokeSizeMultiplier(4);
		setStrokeSize(4);
		setWantedColor(saveColor);
		phase.setValue(t);
         //int divisionPoint = (int)Math.floor(tResolution.getValue()*(t-tmin.getValue())/(tmax.getValue()-tmin.getValue()));
		//view.drawCurve(points, 0, divisionPoint);
	}

}
	

