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
 * Defines a surface with parametric equations (v*cos(u), v*sin(u), 2*sin(u))
 * @author eck
 *
 */
public class RightConoid extends SurfaceParametric {

	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",1.0,1.0,0.0);
	
	public RightConoid() {
		addParameter(aa);
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-3.5,3.5,-3.5,3.5);
		umin.reset("-pi");
		umax.reset("pi");
		vmin.reset(-2);
		vmax.reset(2);
		uPatchCount.setValueAndDefault(27);
		vPatchCount.setValueAndDefault(12);
	}

	public Vector3D surfacePoint(double u, double v) {
		double aux = aa.getValue();
		return new Vector3D(v * Math.cos(u),  v * Math.sin(u),  2*aux * Math.sin(u)+(1-aux)*u);
	}

}
