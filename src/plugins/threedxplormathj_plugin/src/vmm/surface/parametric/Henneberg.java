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
 * Defines a Henneberg surface.
 */
public class Henneberg extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",0.0,0.0,Math.PI/2);
	
	
	public Henneberg() {
		uPatchCount.setValueAndDefault(8);
		vPatchCount.setValueAndDefault(24);
		umin.reset(0.35);
		umax.reset(0.85);
		vmin.reset("-pi");
		vmax.reset("pi");
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-8,8,-8,8);
		addParameter(aa);
	}
	
	static private double sinh(double t){
		return 0.5*(Math.exp(t)-Math.exp(-t));
	}
	
	static private double cosh(double t){
		return 0.5*(Math.exp(t)+Math.exp(-t));
	}
	
	public Vector3D surfacePoint(double u, double v) {
		double PI = Math.PI;
	    double AA = aa.getValue();
		double x = - 2 * Math.cos(AA * PI) * (-sinh(u) * Math.cos(v) + sinh(3 * u) * Math.cos(3 * v) / 3)
		       - 2 * Math.sin(AA * PI) * (-cosh(u) * Math.sin(v) + cosh(3 * u) * Math.sin(3 * v) / 3);
		       
		double y = - 2 * Math.cos(AA * PI) * (-sinh(u) * Math.sin(v) - sinh(3 * u) * Math.sin(3 * v) / 3)
		       - 2 * Math.sin(AA * PI) * ( cosh(u) * Math.cos(v) + cosh(3 * u) * Math.cos(3 * v) / 3);
		       
		double z = 2 * Math.cos(AA * PI) * (-1 + cosh(2 * u) * Math.cos(2 * v))
		       + 2 * Math.sin(AA * PI) * (sinh(2 * u) * Math.sin(2 * v));
		       
		return new Vector3D(x,y,z);
	}

}