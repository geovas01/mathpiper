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
 * Defines the Hyperbolic Paraboloid surface family.
 */
public class HyperbolicParaboloid extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","1.0","1.0","0.5");
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb","2.0","1.0","1.8");
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc","1.0","1.0","1.0");
	
	
	public HyperbolicParaboloid() {
		uPatchCount.setValueAndDefault(18);
		vPatchCount.setValueAndDefault(18);
		umin.reset("-2.5");
		umax.reset("2.5");
		vmin.reset("-2.5");
		vmax.reset("2.5");
		setDefaultViewpoint(new Vector3D(29,16,20)); 
		setDefaultWindow(-8,8,-8,8);
		addParameter(aa);
		addParameter(bb);
		addParameter(cc);
	}
	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = aa.getValue();
	    double BB = bb.getValue();
	    double CC = cc.getValue();
		double x = AA * u;
		double y = BB * v;
		double z = CC * u * v;
		return new Vector3D(x,y,z);
	}

}
