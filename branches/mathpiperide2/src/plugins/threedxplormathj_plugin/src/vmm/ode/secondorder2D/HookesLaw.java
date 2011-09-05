/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder2D;

import vmm.core.RealParamAnimateable;

public class HookesLaw extends CentralForce {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",2.5,2,3);
	
	public HookesLaw() {
		addParameter(aa);
		initialDataDefault = new double[] { .5, .5, -1.25, 2 };
		setDefaultWindow(-3,3,-3,3);
	}

	protected double force(double r) {
		return -aa.getValue() * r;
	}

}
