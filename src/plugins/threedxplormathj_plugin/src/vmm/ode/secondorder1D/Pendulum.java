/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder1D;

import vmm.core.RealParamAnimateable;

public class Pendulum extends ODE2ndOrder1D {
	
	private RealParamAnimateable a;
	private RealParamAnimateable b;
	
	protected double f(double x, double y){
		double aa = a.getValue();
		double bb = b.getValue();
		return -aa*Math.sin(x) - bb*y;
	}

	public Pendulum() {
		a = new RealParamAnimateable("vmm.ode.secondorder1D.Pendulum.a", 1, 1.0, 1.0);
		b = new RealParamAnimateable("vmm.ode.secondorder1D.Pendulum.b", 0, 0, 0.5);
		addParameter(b);
		addParameter(a);
		setDefaultWindow(-4, 4, -4, 4);
		initialDataDefault = new double[] { 0.7071, 0.7071, 0.05, 10}; 
	}
	
}

