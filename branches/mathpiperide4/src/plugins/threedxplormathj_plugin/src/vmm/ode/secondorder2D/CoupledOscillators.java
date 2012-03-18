/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder2D;

import vmm.core.RealParamAnimateable;

public class CoupledOscillators extends ODE2ndOrder2D {
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.ode.secondorder2D.CoupledOscillators.ForceConstant",1);
	private RealParamAnimateable c = new RealParamAnimateable("vmm.ode.secondorder2D.CoupledOscillators.CouplingConstant",0.1,0,0.2);
	private RealParamAnimateable f = new RealParamAnimateable("vmm.ode.secondorder2D.CoupledOscillators.Friction",0);
	
	public CoupledOscillators() {
		addParameter(f);
		addParameter(c);
		addParameter(a);
		setDefaultWindow(-2, 2, -2, 2);
		initialDataDefault = new double[] { 0, 0, 1, 0 };
	}

	protected double xdotdot(double x, double y, double xdot, double ydot) {
		double aa = a.getValue();
		return -aa*aa*x + c.getValue()*(y-x) - f.getValue()*xdot;
	}

	protected double ydotdot(double x, double y, double xdot, double ydot) {
		double aa = a.getValue();
		return -aa*aa*y + c.getValue()*(x-y) - f.getValue()*ydot;
	}

}
