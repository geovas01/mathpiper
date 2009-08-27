/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder2D;

abstract public class CentralForce extends ODE2ndOrder2D {
	
	public CentralForce() {
		dtDefault = 0.05;
		timeSpanDefault = 999;
		addOrbitTypesToControlPanel = false;
		addAnimateCheckBoxToControlPanel = false;
	}

	protected double xdotdot(double x, double y, double xdot, double ydot) {
		double r = Math.sqrt(x*x+y*y);
		double F = force(r);
		return x/r * F;
	}

	protected double ydotdot(double x, double y, double xdot, double ydot) {
		double r = Math.sqrt(x*x+y*y);
		double F = force(r);
		return y/r * F;
	}
	
	abstract protected double force(double r);

}
