/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder2D;

import vmm.core.RealParamAnimateable;

public class PowerLaw extends CentralForce {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",2.5);
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",-2.01,-1.95,-2.05);
	
	public PowerLaw() {
		addParameter(bb);
		addParameter(aa);
		initialDataDefault = new double[] { .5, .5, -1.25, 2 };
		setDefaultWindow(-3,3,-3,3);
	}

	protected double force(double r) {
		if (Math.abs(r) < 0.001)
			return 0;
		else return -aa.getValue() * Math.pow(r, bb.getValue());
	}

}
