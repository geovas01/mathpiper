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
 * Defines a parametric Nephroid Of Freeth .
 */
public class NephroidOfFreeth extends PlaneCurveParametric {
	
	private RealParamAnimateable aa;   
	
	public NephroidOfFreeth() {
		tResolution.setValueAndDefault(300);
	    aa = new RealParamAnimateable("aa", 2, 0, 3);
		addParameter(aa);
		tmin.setValueAndDefault(0.0);                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("4*pi");
		setDefaultWindow(-4,4,-4,4);  // Adjust requested range of restricted x- and y-values
	}
	
	private double Multiplier(double t){
		double AA = aa.getValue(); 
		return (1-AA*Math.sin(t/2));
	}

	/**
	 * Define the x-coordinate function x(t) = (1-aa sin(t/2)) * cos(t) for the curve.
	 */
	public double xValue(double t) {
		return Multiplier(t) * Math.cos( t );
	}

	/**
	 * Define the y-coordinate function y(t) = (1-aa sin(t/2)) * sin(t) for the curve.
	 */
	public double yValue(double t) {
		return Multiplier(t) * Math.sin( t );
	}

}
