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
 * Defines a Scherk surface with parametric equations (u, v, (log(cos(aa * v) / cos(aa * u))) / aa).
 */
public class Scherk extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",1,1,1);
	
	
	public Scherk() {
		uPatchCount.setValueAndDefault(10);
		vPatchCount.setValueAndDefault(10);
		umin.reset("-pi/2 + 0.01");
		umax.reset("pi/2 - 0.01");
		vmin.reset("-pi/2 + 0.01");
		vmax.reset("pi/2 - 0.01");
		setDefaultViewpoint(new Vector3D(10,10,10));
		setDefaultWindow(-5,5,-5,5);
		addParameter(aa);
	}
	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = aa.getValue();
		double x = u;
		double y = v;
		double z = (Math.log(Math.cos(AA * v) / Math.cos(AA * u))) / AA;
		return new Vector3D(x,y,z);
	}

}
