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
 * Defines the Catenoid-Helicoid surface family.
 */
public class Catenoid_Helicoid extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","0","0","pi/2");
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",0.5,0.5,0.5);
	
	
	public Catenoid_Helicoid() {
		uPatchCount.setValueAndDefault(16);
		vPatchCount.setValueAndDefault(10);
		umin.reset("-pi");
		umax.reset("pi");
		vmin.reset(-1.6);
		vmax.reset(1.6);
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-2.5,2.5,-2.5,2.5);
		addParameter(aa);
		addParameter(bb);
	}
	
	static private double sinh(double t){
		return 0.5*(Math.exp(t)-Math.exp(-t));
	}
	
	static private double cosh(double t){
		return 0.5*(Math.exp(t)+Math.exp(-t));
	}
	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = aa.getValue();
	    double BB = bb.getValue();
		double x = BB * (Math.cos(AA) * sinh(v) * Math.sin(u) + Math.sin(AA) * cosh(v) * Math.cos(u));
		double y = BB * (-Math.cos(AA) * sinh(v) * Math.cos(u) + Math.sin(AA) * cosh(v) * Math.sin(u));
		double z = BB * (Math.cos(AA) * u + Math.sin(AA) * v);
		return new Vector3D(x,y,z);
	}

}

