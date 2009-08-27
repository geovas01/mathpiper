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

/**
 * Defines the Loxodrome curve parametrically.
 */
public class Loxodrome extends SphericalCurve {
	
	private RealParamAnimateable Slope;
		
	public Loxodrome() {
		tResolution.setValueAndDefault(300);
		tmin.setValueAndDefaultFromString("-8 * pi");
		tmax.setValueAndDefaultFromString("8 * pi");
		Slope = new RealParamAnimateable("vmm.spacecurve.parametric.Loxodrome.Slope",0.2,0.1,0.3);
		addParameter(Slope);
		tubeSize.setValueAndDefault(0.1);
	}

	protected Vector3D value(double t) {
		double r = Math.exp(Slope.getValue()*t);
		double x = 2*r*Math.cos(t)/(1 + r*r);
		double y = 2*r*Math.sin(t)/(1 + r*r);
		double z = (1 - r*r)/(1 + r*r);
		return new Vector3D(x,y,z);
	}

}