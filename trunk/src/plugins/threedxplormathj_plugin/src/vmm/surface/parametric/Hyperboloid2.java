/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core.RealParamAnimateable;
import vmm.core3D.GridTransformMatrix;
import vmm.core3D.Vector3D;


/**
 * Defines the Two Sheeted Hyperboloid surface family.
 */
public class Hyperboloid2 extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","1.0","1.0","0.5");
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb","1.0","1.0","1.8");
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc","1.0","1.0","1.0");
	
	
	
	public Hyperboloid2() {
		uPatchCount.setValueAndDefault(18);
		vPatchCount.setValueAndDefault(18);
		umin.reset("0");
		umax.reset("2");
		vmin.reset("0");
		vmax.reset("2 pi");
		uPatchCount.setValueAndDefault(12);
		vPatchCount.setValueAndDefault(12);
		setDefaultViewpoint(new Vector3D(25,25,15)); 
		setDefaultWindow(-5.5,5.5,-5.5,5.5);
		addParameter(aa);
		addParameter(bb);
		addParameter(cc);
	}
	
	/**
	 * Overridden to add a "grid transform" to the data; this adds the second sheet.
	 */
	protected void createData() {
		super.createData();
		data.discardGridTransforms();  // We might have already added the transform to the data, and don't want to add it twice
		data.addGridTransform(new GridTransformMatrix().scale(1,1,-1));
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
	    double CC = cc.getValue();
		double x = AA * sinh(u) * Math.cos(v);
		double y = BB * sinh(u) * Math.sin(v);
		double z = CC * cosh(u);
		return new Vector3D(x,y,z);
	}

}
