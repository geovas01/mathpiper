/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;

/**
 * Defines a parametric Folium curve. This curve has no parameters.
 */
public class Folium extends PlaneCurveParametric {
	
	public Folium() {
        tResolution.setValueAndDefault(500);
		tmin.setValueAndDefault(-2.5);             // Adjust inherited parameters
		tmax.setValueAndDefault(4.25);
		setDefaultWindow(-1,1,-1,1);               // Adjust requested range of restricted x- and y-values
	}

	/**
	 * Define the x-coordinate function for the curve.
	 */
	public double xValue(double t) {
		return (t*t*(1-t))/(1-3*t + 3*t*t);
	}

	/**
	 * Define the y-coordinate function for the curve.
	 */
	public double yValue(double t) {
		return (t*(1 - 2*t + t*t))/(1 - 3*t + 3*t*t);
	}

}
