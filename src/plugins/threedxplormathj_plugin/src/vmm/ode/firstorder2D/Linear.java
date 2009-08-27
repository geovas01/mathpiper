/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder2D;

import vmm.core.RealParamAnimateable;


public class Linear extends ODE1stOrder2D {
	
	private RealParamAnimateable a;
	private RealParamAnimateable b;
	private RealParamAnimateable c;
	private RealParamAnimateable d;
	
	public Linear() { 
		setDefaultWindow(-5,5,-3,3);
		a = new RealParamAnimateable("vmm.ode.firstorder2D.HarmonicOscillator.a", -0.5, -0.001, -005);
		b = new RealParamAnimateable("vmm.ode.firstorder2D.HarmonicOscillator.b", -1, -1, -1);
		c = new RealParamAnimateable("vmm.ode.firstorder2D.HarmonicOscillator.c", 1, 1, 1);
		d = new RealParamAnimateable("vmm.ode.firstorder2D.HarmonicOscillator.d", -0.05, 0, 0);
		addParameter(d);
		addParameter(c);
		addParameter(b);
		addParameter(a);
	}

	protected double x1Prime(double x1, double x2) {
		return  a.getValue()*x1 + b.getValue()*x2;
	}

	protected double x2Prime(double x1, double x2) {
		return c.getValue()*x1 + d.getValue()*x2;
	}
	
}