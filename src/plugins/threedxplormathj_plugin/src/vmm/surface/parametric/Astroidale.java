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

public class Astroidale extends SurfaceParametric {



	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.Astroidale.aa","2.0","2.0","2.0");
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.surface.parametric.Astroidale.bb","3.0","0.4","3.0");
	
	
	public Astroidale() {
		uPatchCount.setValueAndDefault(16);
		vPatchCount.setValueAndDefault(10);
		umin.reset("-pi");
		umax.reset("pi");
		vmin.reset("-pi/2");
		vmax.reset("pi/2");
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-2.5,2.5,-2.5,2.5);
		addParameter(aa);		
		addParameter(bb);
		
	}
	//returns cos^r(t)
	static private double cospower(double t,double r){
		return Math.signum(Math.cos(t))*Math.exp(r*Math.log(Math.abs(Math.cos(t))));
	}
	//returns sin^r(t)
	static private double sinpower(double t,double r){
		return Math.signum(Math.sin(t))*Math.exp(r*Math.log(Math.abs(Math.sin(t))));
	}
	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = aa.getValue();
	    double BB = bb.getValue();
		double x = AA * cospower(u,BB) * cospower(v,BB);
		double y = AA * sinpower(u,BB)*cospower(v,BB);
		double z = AA * sinpower(v,BB);
		return new Vector3D(x,y,z);
	}

}

