/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;

/**
 * Defines a parametric Nephroid .
 */
public class Nephroid extends PlaneCurveParametric {
	
	
	public Nephroid() {
		tResolution.setValueAndDefault(200);
		tmin.setValueAndDefault(0.0);                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("2*pi");
		setDefaultWindow(-5,5,-5,5);  // Adjust requested range of restricted x- and y-values
	}
	

	/**
	 * Define the x-coordinate function x(t) = 3 cos(t) - cos(3 t) for the curve.
	 */
	public double xValue(double t) {
		return 3 * Math.cos( t ) - Math.cos(3*t);
	}

	/**
	 * Define the y-coordinate function y(t) = -3  sin(t) + sin(3 t) for the curve.
	 */
	public double yValue(double t) {
		return -3 * Math.sin( t ) + Math.sin(3*t);
	}

}
