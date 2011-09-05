/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder2D;

import vmm.core.*;

public class Volterra_Lotka extends ODE1stOrder2D {
	
	private RealParamAnimateable a;
	private RealParamAnimateable b;
	private RealParamAnimateable c;
	private RealParamAnimateable d;
	
	public Volterra_Lotka() { 
		setDefaultWindow(-0.1,2.5,-0.1,4.5);
		a = new RealParamAnimateable("vmm.ode.firstorder2D.Volterra_Lotka.a", 3, 2, 4);
		b = new RealParamAnimateable("vmm.ode.firstorder2D.Volterra_Lotka.b", 5, 5, 5);
		c = new RealParamAnimateable("vmm.ode.firstorder2D.Volterra_Lotka.c", 3, 2, 4);
		d = new RealParamAnimateable("vmm.ode.firstorder2D.Volterra_Lotka.d", 4, 4, 4);
		addParameter(d);
		addParameter(c);
		addParameter(b);
		addParameter(a);
	}

	protected double x1Prime(double x1, double x2) {
		return  (a.getValue() - b.getValue()*x2)*x1;
	}

	protected double x2Prime(double x1, double x2) {
		return (c.getValue()* x1 -d.getValue())*x2;
	}
	
}

