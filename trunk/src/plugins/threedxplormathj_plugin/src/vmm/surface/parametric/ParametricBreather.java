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
import vmm.core3D.View3DLit;


/**
 * Defines a parametric Breather Surface.
 */
public class ParametricBreather extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",0.4,0.8,0.4);
	
	
	public ParametricBreather() {
		uPatchCount.setValueAndDefault(24);
		vPatchCount.setValueAndDefault(24);
		
		umin.reset(-13.4, -10, -13.4);
		umax.reset(13.4, 10, 13.4);
		vmin.reset("-37.2", "-3*pi", "-37.2");
		vmax.reset("37.2", "3*pi", "37.2");
            
		setDefaultViewpoint(new Vector3D(10,-10,10.5));
		setDefaultWindow(-5.5,5.5,-5.5,5.5);
		setDefaultOrientation(View3DLit.NO_ORIENTATION);
		addParameter(aa);
	}
	
	static private double sinh(double t){
		return 0.5*(Math.exp(t)-Math.exp(-t));
	}
	
	static private double cosh(double t){
		return 0.5*(Math.exp(t)+Math.exp(-t));
	}
	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = aa.getValue();
	    double wsqr = 1-AA*AA;
	    double w = Math.sqrt(wsqr);
	    double q = w *w*cosh(AA * u)*cosh(AA * u);
	    double r = AA*AA*Math.sin(w * v)*Math.sin(w * v);
	    double denom = AA * (q + r);
		double x = -u + (2 * wsqr * cosh(AA * u) * sinh(AA * u) / denom);
		double y = 2 * w * cosh(AA * u) * (-(w * Math.cos(v) * Math.cos(w * v)) - (Math.sin(v) * Math.sin(w * v))) / denom;
		double z = 2 * w * cosh(AA * u) * (-(w * Math.sin(v) * Math.cos(w * v)) + (Math.cos(v) * Math.sin(w * v))) / denom;
		return new Vector3D(x,y,z);
	}

}
