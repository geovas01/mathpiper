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
 * Defines a Steiner Surface
 * @author palais
 *
 */
public class SteinerSurface extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",1.732,1.6,1.8);
	
	public SteinerSurface() {
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-2,2,-2,2);
		umin.reset("0");
		umax.reset("pi");
		vmin.reset("-pi/2");
		vmax.reset("pi/2");
		uPatchCount.setValueAndDefault(20);
		vPatchCount.setValueAndDefault(20);
		setDefaultOrientation(View3DLit.NO_ORIENTATION);
		addParameter(aa);
	}
	
	static private double sin(double t){return Math.sin(t);}
	static private double cos(double t){return Math.cos(t);}

	public Vector3D surfacePoint(double u, double v) {
		double AA = aa.getValue();
		double HalfAASquare = 0.5*AA*AA;
		return new Vector3D(HalfAASquare * (sin(2 * u) * cos(v) * cos(v)),  
				           HalfAASquare * (sin(u) * sin(2 * v)),  
				           HalfAASquare * (cos(u) * sin(2 * v)));
	}

}