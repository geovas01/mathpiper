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
 * Defines the Lissajous surface family.
 */
public class LissajousSurface extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","1","1","1");
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb","1","1","1");
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc","1","1","1");
	private RealParamAnimateable dd = new RealParamAnimateable("genericParam.dd","0.0","0.0","pi/2");
	
	
	public LissajousSurface() {
		uPatchCount.setValueAndDefault(18);
		vPatchCount.setValueAndDefault(18);
		umin.reset("-pi");
		umax.reset("pi");
		vmin.reset("-pi");
		vmax.reset("pi");
		setDefaultViewpoint(new Vector3D(-4.25,-0.67,8.7)); 
		setDefaultWindow(-2,2,-2,2);
		addParameter(aa);
		addParameter(bb);
		addParameter(cc);
		addParameter(dd);
	}
	
	static private double sin(double t){
		return Math.sin(t);
	}

	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = Math.round(aa.getValue());
	    double BB = Math.round(bb.getValue());
	    double CC = Math.round(cc.getValue());
	    double DD = dd.getValue();
		double x = AA * sin(u);
		double y = BB * sin(v);
		double z = CC * sin(DD - AA * u - BB * v);
		return new Vector3D(x,y,z);
	}

}
