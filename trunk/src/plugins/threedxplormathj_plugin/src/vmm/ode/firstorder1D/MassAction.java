/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder1D;


import vmm.core.RealParamAnimateable;


public class MassAction extends ODE1stOrder1D{
	
	private RealParamAnimateable a;
	private RealParamAnimateable b;
	
	public MassAction() { 
		dtDefault = 0.1;
		timeSpanDefault = 25;
		initialDataDefault = new double[] { -0.4545, .05};
		setDefaultWindow(-8,8,-8,8);
		a = new RealParamAnimateable("vmm.ode.firstorder2D.Logistic.a", 1.5, 1, 2);
		b = new RealParamAnimateable("vmm.ode.firstorder2D.Logistic.b", 0.55, 0.1, 0.3);
		addParameter(a);
		addParameter(b);
	}

	protected double x1Prime(double x1, double x2) {
		return  1;
	}

	protected double x2Prime(double x1, double x2) {
		return a.getValue()*x2 - b.getValue()*x2*x2;
	}
	
}

