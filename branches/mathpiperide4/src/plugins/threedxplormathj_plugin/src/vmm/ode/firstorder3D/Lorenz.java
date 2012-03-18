/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder3D;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

public class Lorenz extends ODE1stOrder3D {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",28,12,32);

	public Lorenz() {
		addParameter(aa);
		initialDataDefault = new double[] { 0.1, 0.2, 0, 0.014, 30 };
		setDefaultWindow(-70,70,-70,70);
		setDefaultViewpoint(new Vector3D( 4.5, 9, 90 ));
	}
	
	protected double xPrime(double x, double y, double z) {
		return -10*x + 10*y;
	}

	protected double yPrime(double x, double y, double z) {
		return aa.getValue()*x - y - x*z;
	}

	protected double zPrime(double x, double y, double z) {
		return -(8.0/3.0)*z + x*y;
	}
	

}
