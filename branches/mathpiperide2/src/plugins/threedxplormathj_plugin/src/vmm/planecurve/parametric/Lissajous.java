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
 * Defines a Lissajous curve as a parametric plane curve. The two amplitudes, frequencies,
 * and a phase of the curve are parameters that can be set by the user.  By default,
 * the curve has amplitudes 2 and 2, frequencies 3 and 5, and phase 0 and is defined on the
 * intervale [0,2pi].
 */
public class Lissajous extends PlaneCurveParametric {
	
	private RealParamAnimateable amplitude1;   // Three parameters that affect the appearance of the exhibit
	private RealParamAnimateable amplitude2; 
	private RealParamAnimateable frequency1;
	private RealParamAnimateable frequency2;
	private RealParamAnimateable phase;
	
	public Lissajous() {
		tResolution.setValueAndDefault(500);
		amplitude1 = new RealParamAnimateable("vmm.planecurve.parametric.SineCurve.amplitude", 2, 2, 2);
		amplitude2 = new RealParamAnimateable("vmm.planecurve.parametric.SineCurve.amplitude", 2, 2, 2);
		frequency1 = new RealParamAnimateable("vmm.planecurve.parametric.SineCurve.frequency", 3, 1, 5);
		frequency2 = new RealParamAnimateable("vmm.planecurve.parametric.SineCurve.frequency", 5, 1, 7);
		phase = new RealParamAnimateable("vmm.planecurve.parametric.SineCurve.phase", 0, 0, 0);
		phase.setMinimumValueForInput(0);  // Restrict user input phase to the range 0 to 2*pi
		phase.setMaximumValueForInput(2*Math.PI);
		frequency1.setMinimumValueForInput(Double.MIN_VALUE); // Restrict user input for frequency to positive values.
		frequency2.setMinimumValueForInput(Double.MIN_VALUE); // Restrict user input for frequency to positive values.
		addParameter(phase);      // Add parameters in the REVERSE of the order that they will appear in dialog boxes
		addParameter(frequency1);
		addParameter(frequency2);
		addParameter(amplitude1);
		addParameter(amplitude2);
		tmin.setValueAndDefault(0);                 // Adjust inherited parameters
		tmax.setValueAndDefaultFromString("2*pi");
		setDefaultWindow(-4,4,-4,4);  // Adjust requested range of restricted x- and y-values
	}

	/**
	 * Define the x-coordinate function for the curve.
	 */
	public double xValue(double t) {
		double aa = amplitude1.getValue();  // Get the current values of the three parameters.
		long n = Math.round(frequency2.getValue());
		double p = phase.getValue();
		return aa * Math.sin( n * t + p );
	}

	/**
	 * Define the y-coordinate function for the curve.
	 */
	public double yValue(double t) {
		double bb = amplitude2.getValue();
		long m = Math.round(frequency1.getValue());
		return bb * Math.cos( m * t );
	}

}
