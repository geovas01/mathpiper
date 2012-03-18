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
 * Defines the Torus surface family.
 */
public class Torus extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","1.75","1.2","1.2");
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb","0.4","0.3","1.2");
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc","0.4","0.3","1.2");
	
	
	public Torus() {
		uPatchCount.setValueAndDefault(12);
		vPatchCount.setValueAndDefault(32);
		umin.reset("0");
		umax.reset("2 pi");
		vmin.reset("0");
		vmax.reset("2 pi");
		setDefaultViewpoint(new Vector3D(10,-10,10)); 
		setDefaultWindow(-2.5,2.5,-2.5,2.5);
		setDefaultOrientation(View3DLit.NO_ORIENTATION);
		addParameter(aa);
		addParameter(bb);
		addParameter(cc);
	}
	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = aa.getValue();
	    double BB = bb.getValue();
	    double CC = cc.getValue();
	    double x = (AA + BB * Math.cos(u))* Math.cos(v);
		double y = (AA + BB * Math.cos(u))* Math.sin(v);
		double z = CC * Math.sin(u);
		return new Vector3D(x,y,z);
	}

}
