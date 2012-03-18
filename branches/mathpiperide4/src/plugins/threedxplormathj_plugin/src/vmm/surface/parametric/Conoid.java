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

public class Conoid  extends SurfaceParametric {



	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.Conoid.aa","1","1","1");
     private RealParamAnimateable bb = new RealParamAnimateable("vmm.surface.parametric.Conoid.bb",1,1,12);
	
	
	public Conoid() {
		uPatchCount.setValueAndDefault(16);
		vPatchCount.setValueAndDefault(16);
		umin.reset("-1");
		umax.reset("1");
		vmin.reset("-pi");
		vmax.reset("pi");
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-2.5,2.5,-2.5,2.5);
		addParameter(aa);
		addParameter(bb);
		setFramesForMorphing(11);
	}
	
	static private double cos(double t){
		return Math.cos(t);
	}
	
	static private double sin(double t){
		return Math.sin(t);
	}
	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = aa.getValue();
	   double BB = bb.getValue();
		double x = u * cos(v);
		double y = u * sin(v);
		double z = AA * sin(BB*v);
		return new Vector3D(x,y,z);
	}

}
