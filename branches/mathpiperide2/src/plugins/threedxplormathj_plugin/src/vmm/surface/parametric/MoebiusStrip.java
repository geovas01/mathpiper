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
 * Defines a Moebius Strip surface
 * @author palais
 *
 */
public class MoebiusStrip extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",1.5,1.5,1.5);
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",1,1,1);

	
	public MoebiusStrip() {
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-2,2,-2,2);
		umin.reset("-0.3");
		umax.reset("0.3");
		vmin.reset("0.0");
		vmax.reset("2*pi","0.3","2*pi");
		uPatchCount.setValueAndDefault(3);
		vPatchCount.setValueAndDefault(21);
		setDefaultOrientation(View3DLit.NO_ORIENTATION);
		addParameter(aa);
		addParameter(bb);
	}
	
	static private double sin(double t){return Math.sin(t);}
	static private double cos(double t){return Math.cos(t);}

	public Vector3D surfacePoint(double u, double v) {
		double AA = aa.getValue();
		double halftwists = Math.round(bb.getValue());
		return new Vector3D(AA * (cos(v) + u * cos(halftwists * v / 2) * cos(v)),  
				           AA * (sin(v) + u * cos(halftwists * v / 2) * sin(v)),  
				           AA * u * sin(halftwists * v / 2));
	}

}
