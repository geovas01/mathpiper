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

public class Rikitake extends ODE1stOrder3D {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",3.75);
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",1);

	public Rikitake() {
		addParameter(bb);
		addParameter(aa);
		initialDataDefault = new double[] { 0.1, 0.5, 4, 0.03, 100 };
		setDefaultViewpoint(new Vector3D( 7, -7, 28 ));
		showAxes = true;
	}
	
	protected double xPrime(double x, double y, double z) {
		return z*y - bb.getValue()*x;
	}

	protected double yPrime(double x, double y, double z) {
		return z*x - aa.getValue()*x - bb.getValue()*y;
	}

	protected double zPrime(double x, double y, double z) {
		return 1 - x*y;
	}
	

}
