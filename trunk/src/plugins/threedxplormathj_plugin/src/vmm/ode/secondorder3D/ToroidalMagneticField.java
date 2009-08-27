/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder3D;

import vmm.core3D.Vector3D;

public class ToroidalMagneticField extends ChargedParticles {
	
	public ToroidalMagneticField() {
		initialDataDefault = new double[] { .5, .5, -.5, 0, -.15, 0, .02, 150 };
		setDefaultWindow(-1,1,-1,1);
		setDefaultViewpoint(new Vector3D(11.6, -8.1, 10));
	}

	protected void magneticField(double x, double y, double z, Vector3D answer) {
//		rsquare := x * x + y * y ;
//	    MagneticField.x := 2*y/ rsquare;
//	    MagneticField.y := -2 * x / rsquare;
//	    MagneticField.z := 0;
		double rsquare = x*x + y*y;
		answer.x = 2*y / rsquare;
		answer.y = -2*x / rsquare;
		answer.z = 0;
	}

}
