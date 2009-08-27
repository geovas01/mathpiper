/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import vmm.core3D.Vector3D;

public class Figure8Knot extends SpaceCurveParametric {
	
	public Figure8Knot() {
		tmin.setValueAndDefault(0);
		tmax.setValueAndDefaultFromString("2 * pi");
		tResolution.setValueAndDefault(300);
		setDefaultViewpoint(new Vector3D(36,-138,13));
		setDefaultWindow(-4,4,-3,3);
		tubeSize.setValueAndDefault(0.15);
	}

	protected Vector3D value(double t) {
		return new Vector3D( 
				(32*Math.cos(t) - 51*Math.sin(t) - 104*Math.cos(2*t) - 34*Math.sin(2*t) + 104*Math.cos(3*t) - 91*Math.sin(3*t))/100, 
				(94*Math.cos(t) + 41*Math.sin(t) + 113*Math.cos(2*t) - 68*Math.cos(3*t) - 124*Math.sin(3*t))/100, 
				(16*Math.sin(t) + 73*Math.cos(t) - 211*Math.cos(2*t) - 39*Math.sin(2*t) - 99*Math.cos(3*t) - 21*Math.sin(3*t))/100 
			);
	}

	protected Vector3D deriv1(double t) {
		return new Vector3D( 
				(-32*Math.sin(t) - 51*Math.cos(t) + 104*Math.sin(2*t)*2 - 34*Math.cos(2*t)*2 - 104*Math.sin(3*t)*3 - 91*Math.cos(3*t)*3)/100, 
				(-94*Math.sin(t) + 41*Math.cos(t) - 113*Math.sin(2*t)*2 + 68*Math.sin(3*t)*3 - 124*Math.cos(3*t)*3)/100, 
				(16*Math.cos(t) - 73*Math.sin(t) + 211*Math.sin(2*t)*2 - 39*Math.cos(2*t)*2 + 99*Math.sin(3*t)*3 - 21*Math.cos(3*t)*3)/100 
			);
	}

	protected Vector3D deriv2(double t) {
		return new Vector3D( 
				(-32*Math.cos(t) +51*Math.sin(t) +104*Math.cos(2*t)*4 +34*Math.sin(2*t)*4 -104*Math.cos(3*t)*9 +91*Math.sin(3*t)*9)/100, 
				(-94*Math.cos(t) -41*Math.sin(t) -113*Math.cos(2*t)*4 +68*Math.cos(3*t)*9 +124*Math.sin(3*t)*9)/100, 
				(-16*Math.sin(t) -73*Math.cos(t) +211*Math.cos(2*t)*4 +39*Math.sin(2*t)*4 +99*Math.cos(3*t)*9 +21*Math.sin(3*t)*9)/100 
			);
	}

}
