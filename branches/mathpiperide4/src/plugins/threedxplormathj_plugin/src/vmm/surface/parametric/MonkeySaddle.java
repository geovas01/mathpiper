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
 * Defines a Monkey Saddle surface with parametric equations (a*u, b*v, c*(u^3 - 3*v^3)).
 */
public class MonkeySaddle extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",1,1,1);
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",1,1,1);
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc",0.35,0,0.35);
	
	public MonkeySaddle() {
		uPatchCount.setValueAndDefault(12);
		vPatchCount.setValueAndDefault(10);
		umin.reset(-1.5);
		umax.reset(1.5);
		vmin.reset(-1.5);
		vmax.reset(1.5);
		setDefaultViewpoint(new Vector3D(6.64,-13,9.3));
		setDefaultWindow(-2.5,2.5,-2.5,2.5);
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
	}
	
	public Vector3D surfacePoint(double u, double v) {
		double x = aa.getValue() * v;
		double y = bb.getValue() * u;
		double z = cc.getValue() * (u * u * u - 3 * u * v * v);
		return new Vector3D(x,y,z);
	}

}
