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
 * Defines  parametric Limacon.
 */
public class Limacon extends PlaneCurveParametric {
	
	private RealParamAnimateable aa; 
	private RealParamAnimateable bb; 
 
	
	public Limacon() {
		tResolution.setValueAndDefault(200);
	    aa = new RealParamAnimateable("aa", 0.5, 1, 1);
	    bb = new RealParamAnimateable("bb", 0.5, 2, 0.5);
		addParameter(aa);
		addParameter(bb);
		tmin.setValueAndDefaultFromString("0");                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("2*pi");
		setDefaultWindow(-1.25,1.25,-1.25,1.25);  // Adjust requested range of restricted x- and y-values
	}
	
	private static double sin(double t) {return Math.sin(t);}
	private static double cos(double t) {return Math.cos(t);}
	
	/**
	 * Define the x-coordinate function x(t) = 2 aa t^3/(1 + t^2) for the curve.
	 */
	public double xValue(double t) {
		double AA = aa.getValue(); 
		double BB = bb.getValue(); 
		return (AA - (2* AA * cos(t-Math.PI) + BB) * cos(t- Math.PI));
		}

	/**
	 * Define the y-coordinate function y(t) = 2 aa t^2/(1 + t^2) for the curve.
	 */
	public double yValue(double t) {
		double AA = aa.getValue(); 
		double BB = bb.getValue(); 
		return ((2 * AA * cos(t-Math.PI) + BB) * sin(t-Math.PI));
	}

}
