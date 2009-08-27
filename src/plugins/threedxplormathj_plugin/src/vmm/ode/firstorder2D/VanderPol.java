/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder2D;

import vmm.core.*;

public class VanderPol extends ODE1stOrder2D {
	
	private RealParamAnimateable a;
	
	public VanderPol() { 
		setDefaultWindow(-5,5,-3,3);
		a = new RealParamAnimateable("vmm.ode.firstorder2D.VanderPol.resistance", 1, 0, 2);
		addParameter(a);
	}

	protected double x1Prime(double x, double y) {
		return y;
	}

	protected double x2Prime(double x, double y) {
		return y*(1-x*x)*a.getValue() - x;
	}
	
}
