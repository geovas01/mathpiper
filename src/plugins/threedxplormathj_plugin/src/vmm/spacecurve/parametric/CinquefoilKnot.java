/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

public class CinquefoilKnot extends SpaceCurveParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",2,2,6);
	
	public CinquefoilKnot() {
		addParameter(aa);
		tmin.setValueAndDefault(0);
		tmax.setValueAndDefaultFromString("10 * pi");
		tResolution.setValueAndDefault(300);
		setDefaultViewpoint(new Vector3D(100,-100,52.5));
		setDefaultWindow(-3,3,-3,3);
		tubeSize.setValueAndDefault(0.3);
	}

	protected Vector3D value(double t) {
		double a = aa.getValue();
		double m = 2 / (2*a + 1);
		return new Vector3D( 
				Math.cos(t)*(2-Math.cos(m*t)), 
				Math.sin(t)*(2-Math.cos(m*t)),
				-Math.sin(m*t) 
			);
	}

}
