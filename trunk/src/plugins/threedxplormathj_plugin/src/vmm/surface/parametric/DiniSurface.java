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
 * Defines a parametric Dini Surface surface.
 */
public class DiniSurface extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",0.35,0.20,0.5);
	
	
	public DiniSurface() {
		uPatchCount.setValueAndDefault(20);
		vPatchCount.setValueAndDefault(12);
		umin.reset(-4);
		umax.reset(4);
		vmin.reset("-pi");
		vmax.reset("pi");
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-1.75,1.75,-1.75,1.75);
		addParameter(aa);
	}

	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = aa.getValue();
	    double psi = Math.PI * Math.min(0.999,Math.max(AA,0.001));
	    
	    double sinpsi = Math.sin(psi);
	    double cospsi = Math.cos(psi);
	    
	    double g = (u - cospsi * v) / sinpsi;
	    double s = Math.exp(g);
	    double r = (2 * sinpsi) / (s + 1 / s);
	    double t = r * (s - 1 / s) * 0.5;
	    
		double x = (u-t);
		double y = (r * Math.cos(v));
		double z = (r * Math.sin(v));
		return new Vector3D(x,y,z);
	}

}
