/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core3D.Vector3D;


/**
 * Defines a parametric Kuen Surface.
 */
public class KuenSurface extends SurfaceParametric {
	
	
	public KuenSurface() {
		uPatchCount.setValueAndDefault(21);
		vPatchCount.setValueAndDefault(21);
		umin.reset(-4);
		umax.reset(4);
		vmin.reset(-3.75);
		vmax.reset(3.75);
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-2,2,-2,2);
	}
	
	static private double sinh(double t){
		return 0.5*(Math.exp(t)-Math.exp(-t));
	}
	
	static private double cosh(double t){
		return 0.5*(Math.exp(t)+Math.exp(-t));
	}
	
	public Vector3D surfacePoint(double u, double v) {
		double x = 2 * cosh(v) * (Math.cos(u) + u * Math.sin(u)) / (cosh(v) * cosh(v) + u * u);
		double y = 2 * cosh(v) * (-u * Math.cos(u) + Math.sin(u)) / (cosh(v) * cosh(v) + u * u);
		double z = v - (2 * sinh(v) * cosh(v)) / (cosh(v) * cosh(v) + u * u);
		return new Vector3D(x,y,z);
	}

}
