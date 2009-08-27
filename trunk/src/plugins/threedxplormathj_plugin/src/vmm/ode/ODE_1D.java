/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode;


/**
 * Represents a first order ODE exhibit y' = f(y,t).
 * This is subsumed under the ODE_2D class by the standard trick;
 * namely setting x'(t) = 1 and x(0) = t_0 (so that x(t) = t_0 + t) and 
 * putting y' = f(y,x).
 */

abstract public class ODE_1D extends ODE_2D{
	
	protected ODE_1D(boolean canShowVectorField, boolean isAutonomous, String... inputLabelName) {
		super(canShowVectorField,isAutonomous, inputLabelName);
	}
	
	/**
	 * Defines the x1-component of the vector field.
	 */
	abstract protected double x1Prime(double x, double y);
	
	/**
	 * Defines the x2-component of the vector field.
	 */
	abstract protected double x2Prime(double x, double y);

	
	/**
	 * Computes the next point on an integral curve, using Euler's method.  
	 * The pointData parameter array contains the data for the current point.
	 * This data should be replaced with the data for the next point.  The
	 * size of the array and the meaning of the data that it contains will
	 * vary from one subclass to another.  However, for non-autonomous ODEs,
	 * the time should always be the first parameter in the array.
	 * The second parameter, dt, gives  the time step from the current 
	 * point to the next point.
	 */
	abstract protected void nextEulerPoint(double[] pointData, double dt);
	
	/**
	 * Computes the next point on an integral curve, using the Runge-Kutta method.  
	 * The pointData parameter array contains the data for the current point.
	 * This data should be replaced with the data for the next point.  The
	 * size of the array and the meaning of the data that it contains will
	 * vary from one subclass to another.  But for the non-autonmous case,
	 * the time should always be the first value in the array.
	 * The second parameter, dt, gives the time step from the current point to the next point.
	 */
	abstract protected void nextRungeKuttaPoint(double[] pointData, double dt);

}
