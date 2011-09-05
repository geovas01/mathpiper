/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import vmm.core3D.Vector3D;

public class GrannyKnot extends SpaceCurveParametric {
	
	public GrannyKnot() {
		tmin.setValueAndDefault(0);
		tmax.setValueAndDefaultFromString("2 * pi");
		tResolution.setValueAndDefault(300);
		setDefaultViewpoint(new Vector3D(28,-150,0));
		setDefaultWindow(-2,2,-1.5,1.5);
		tubeSize.setValueAndDefault(0.15);
	}

	protected Vector3D value(double t) {
		return new Vector3D( 
				(-22*Math.cos(t) - 128*Math.sin(t) - 44*Math.cos(3*t) - 78*Math.sin(3*t))/100, 
				(-10*Math.cos(2*t) - 27*Math.sin(2*t) +38*Math.cos(4*t) + 46*Math.sin(4*t))/100, 
				( 70*Math.cos(3*t) - 40*Math.sin(3*t))/100 
			);
	}

	protected Vector3D deriv1(double t) {
		return new Vector3D( 
				(22*Math.sin(t) - 128*Math.cos(t) + 44*Math.sin(3*t)*3 - 78*Math.cos(3*t)*3)/100, 
				(10*Math.sin(2*t)*2 - 27*Math.cos(2*t)*2 - 38*Math.sin(4*t)*4 + 46*Math.cos(4*t)*4)/100, 
				(-70*Math.sin(3*t)*3 - 40*Math.cos(3*t)*3)/100 
			);
	}

	protected Vector3D deriv2(double t) {
		return new Vector3D( 
				(22*Math.cos(t) + 128*Math.sin(t) + 44*Math.cos(3*t)*3*3 + 78*Math.sin(3*t)*3*3)/100, 
				(10*Math.cos(2*t)*2*2 + 27*Math.sin(2*t)*2*2 - 38*Math.cos(4*t)*4*4 - 46*Math.sin(4*t)*4*4)/100, 
				(-70*Math.cos(3*t)*3*3 + 40*Math.sin(3*t)*3*3)/100 
			);
	}

}
