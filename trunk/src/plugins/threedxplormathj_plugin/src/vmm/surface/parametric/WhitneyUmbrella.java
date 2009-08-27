/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

/**
 * Defines a surface with parametric equations (u*v, u, v^2 - a).
 */
public class WhitneyUmbrella extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",1.0,1.0,0.0);
	
	public WhitneyUmbrella() {
		addParameter(aa);
		umin.reset(-1);
		umax.reset(1);
		vmin.reset(-1);
		vmax.reset(1);
		uPatchCount.setValueAndDefault(18);
		vPatchCount.setValueAndDefault(18);
		setDefaultViewpoint(new Vector3D(2.4,-13,5.5));
		setDefaultWindow(-1.4,1.4,-1.4,1.4);
	}
	
	public Vector3D surfacePoint(double u, double v) {
		double aux = aa.getValue();
		double x = u*(aux*v +(1-aux)*Math.sin(Math.PI*v));
		double z = aux*v*v -(1-aux)*Math.cos(Math.PI*v);
		return new Vector3D(x,  u,  z);
	}

}
