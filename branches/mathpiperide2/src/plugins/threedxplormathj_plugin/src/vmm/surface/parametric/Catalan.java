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
 * Defines an Catalan surface.
 */
public class Catalan extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","0","0","pi / 2");
	
	
	public Catalan() {
		uPatchCount.setValueAndDefault(20);
		vPatchCount.setValueAndDefault(20);
		umin.reset(0.0);
		umax.reset("4 * pi");
		vmin.reset(-2.0);
		vmax.reset(2.0);
		setDefaultViewpoint(new Vector3D(3,-12,12));
		setDefaultWindow(-10,10,-10,10);
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
	    double PI = Math.PI;
		double x = Math.cos(AA * PI) * (u - Math.sin(u) * cosh(v)) + Math.sin(AA * PI) * (v - Math.cos(u) * sinh(v)) - 4;
		double y = Math.cos(AA * PI) * (1 - Math.cos(u) * cosh(v)) + Math.sin(AA * PI) * Math.sin(u) * sinh(v);
		double z = Math.cos(AA * PI) * 4 * Math.sin(u / 2) * sinh(v / 2) + Math.sin(AA * PI) * 4 * (Math.cos(u / 2) * cosh(v / 2) - 1);
		return new Vector3D(x,y,z);
	}

}
