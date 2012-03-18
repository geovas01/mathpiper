/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder3D;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

public class ConstantMagneticField extends ChargedParticles {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",1,1,0);
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",1,1,1);
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc",0,0,1);
	
	public ConstantMagneticField() {
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		initialDataDefault = new double[] { -2, -2, 0, .2, .2, -.5, .05, 25 };
		setDefaultWindow(-2, 2, -2, 2);
		setDefaultViewpoint(new Vector3D(14.5, -3.9, 14.5));
	}

	protected void magneticField(double x, double y, double z, Vector3D answer) {
		answer.x = aa.getValue();
		answer.y = bb.getValue();
		answer.z = cc.getValue();
	}

}
