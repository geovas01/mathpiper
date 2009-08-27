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
 * Defines a parametric Catenary .
 */
public class Catenary extends PlaneCurveParametric {
	
	private RealParamAnimateable aa;  
	
	public Catenary() {
		tResolution.setValueAndDefault(395);
	    aa = new RealParamAnimateable("aa", 1, 0.75, 1.5);
		addParameter(aa);
		tmin.setValueAndDefault(-4);                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("4");
		setDefaultWindow(-5,5,-5,5);  // Adjust requested range of restricted x- and y-values
	}
	
	private static double cosh(double t) {return 0.5 *(Math.exp(t) + Math.exp(-t));}
	

	/**
	 * Define the x-coordinate function x(t) = t for the curve.
	 */
	public double xValue(double t) {return t;}

	/**
	 * Define the y-coordinate function y(t) = aa*cosh(t) for the curve.
	 */
	public double yValue(double t) {
		double AA = aa.getValue(); 
		return AA * cosh( t/AA ) ;
	}

}
