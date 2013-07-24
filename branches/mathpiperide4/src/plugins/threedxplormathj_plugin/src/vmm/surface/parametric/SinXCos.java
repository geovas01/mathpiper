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
 * Defines a Sin * Cos surface with parametric equations
 * (u, v, (Math.sin(u),Math.cos(v))) 
 */
public class SinXCos extends SurfaceParametric {
	
	
	public SinXCos() {
		umin.reset(-5.0);
		umax.reset(5.0);
		vmin.reset(-5.0);
		vmax.reset(5.0);
		setDefaultWindow(-2, 2, -2, 2);
		setDefaultViewpoint(new Vector3D(20,3,7));
	}
	
	public Vector3D surfacePoint(double u, double v) {
		
		double z = Math.sin(u)*Math.cos(v);
		return new Vector3D(u,v,z);
	}

}
