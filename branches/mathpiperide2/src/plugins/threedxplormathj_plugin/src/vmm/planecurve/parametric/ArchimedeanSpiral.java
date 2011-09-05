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
 * Defines a parametric Archimedean Spiral curve.
 */
public class ArchimedeanSpiral extends PlaneCurveParametric {
	
	private RealParamAnimateable aa;  
	
	public ArchimedeanSpiral() {

		tResolution.setValueAndDefault(500);
	    aa = new RealParamAnimateable("aa", 1, -1, 1.25);
		addParameter(aa);
		tmin.setValueAndDefault(0.2);     
		tmax.setValueAndDefaultFromString("7*pi");
		setDefaultWindow(-25,25,-25,25);  // Adjust requested range of restricted x- and y-values
	}
	
	private double ExpAAlog(double t){    // For convenience
		double AA = aa.getValue(); 
		return Math.exp(AA*Math.log(t));
	}

	/**
	 * Define the x-coordinate function x(t) = exp(aa*log(t)) * cos(t)t for the parametric curve.
	 */
	public double xValue(double t) {
		return ExpAAlog(t) * Math.cos( t );
	}

	/**
	 * Define the y-coordinate function y(t) = exp(aa*log(t)) * sin(t)t for the parametric curve.
	 */
	public double yValue(double t) {
		return ExpAAlog(t) * Math.sin( t );
	}

}
