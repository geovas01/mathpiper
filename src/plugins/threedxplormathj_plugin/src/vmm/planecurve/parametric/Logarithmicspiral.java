/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;

import vmm.core.RealParamAnimateable;

/**
 * Defines a parametric Logarithmic Spiral.
 */
public class Logarithmicspiral extends PlaneCurveParametric {
	
	private RealParamAnimateable aa; 
	private RealParamAnimateable bb; 
	
	public Logarithmicspiral() {
		tResolution.setValueAndDefault(500);
	    aa = new RealParamAnimateable("aa", 1, 1, 1);
	    bb = new RealParamAnimateable("bb", 0.08, 0.08, 0.04);
		addParameter(aa);
		addParameter(bb);
		tmin.setValueAndDefaultFromString("0");                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("12*pi");
		setDefaultWindow(-10,10,-10,10);  // Adjust requested range of restricted x- and y-values
	}
	
	/**
	 * Define the x-coordinate function x(t) =  aa exp(bb t) cos(t) for the curve.
	 */
	public double xValue(double t) {
		double AA = aa.getValue(); 
		double BB = bb.getValue(); 
		return AA * Math.exp(BB*t) *  Math.cos(t);
		}

	/**
	 * Define the y-coordinate function y(t) = aa exp(bb t) sin(t) for the curve.
	 */
	public double yValue(double t) {
		double AA = aa.getValue(); 
		double BB = bb.getValue(); 
		return AA * Math.exp(BB*t) * Math.sin(t);
	}

}
