/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder3D;

import vmm.core.RealParamAnimateable;
import vmm.core.View;
import vmm.core3D.Vector3D;

public class Rossler extends ODE1stOrder3D {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",0.2,0.1,0.2);
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",0.2,0.1,0.2);
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc",5.7,5,6);

	public Rossler() {
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		initialDataDefault = new double[] { -1, 0, 0, 0.05, 100 };
		setDefaultWindow(-18, 18, -18, 18);
		setDefaultViewpoint(new Vector3D( 22, -22, 28 ));
	}
	
	public View getDefaultView() {
		View v = super.getDefaultView();
		v.setShowAxes(false);
		return v;
	}
	
	protected double xPrime(double x, double y, double z) {
		return -y-z;
	}

	protected double yPrime(double x, double y, double z) {
		return x + aa.getValue()*y;
	}

	protected double zPrime(double x, double y, double z) {
		return bb.getValue() + x*z -cc.getValue()*z;
	}
	

}
