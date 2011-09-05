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
 * Defines the Cyclide surface family.
 */
public class Cyclide extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","1.75","1.75","1.2");
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb","0.4","0.4","1.2");
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc","0.4","0.4","1.2");
	private RealParamAnimateable dd = new RealParamAnimateable("genericParam.dd","1","1","1");
	private RealParamAnimateable ee = new RealParamAnimateable("genericParam.ee","1","1","1");
	private RealParamAnimateable ff = new RealParamAnimateable("genericParam.ff","1","1","1");
	private RealParamAnimateable gg = new RealParamAnimateable("genericParam.gg","3.25","3.55","3.55");
	private RealParamAnimateable hh = new RealParamAnimateable("genericParam.hh","2.25","2.25","2.25");
	
	
	public Cyclide() {
		uPatchCount.setValueAndDefault(12);
		vPatchCount.setValueAndDefault(32);
		umin.reset("0");
		umax.reset("2 pi");
		vmin.reset("0");
		vmax.reset("2 pi");
		setDefaultViewpoint(new Vector3D(-20,-6,-6)); 
		setDefaultWindow(-3.0,3.0,-3.0,3.0);
		setDefaultOrientation(View3DLit.NO_ORIENTATION);
		addParameter(aa);
		addParameter(bb);
		addParameter(cc);
		addParameter(dd);
		addParameter(ee);
		addParameter(ff);
		addParameter(gg);
		addParameter(hh);
	}
	
	static private Vector3D Inversion(double radius,Vector3D center, Vector3D source ){
		Vector3D u = new Vector3D();
		Vector3D P = new Vector3D();
		double nrmsqr,mult;
		u.x = source.x - center.x;
		u.y = source.y - center.y;
		u.z = source.z - center.z;
		nrmsqr = u.x * u.x + u.y * u.y + u.z * u.z;
		mult = radius / nrmsqr;
		P.x = mult * u.x + center.x;
		P.y = mult * u.y + center.y;
		P.z = mult * u.z + center.z;
		return P;
	}
	
	public Vector3D surfacePoint(double u, double v) {
	    double AA = aa.getValue();
	    double BB = bb.getValue();
	    double CC = cc.getValue();
	    double DD = dd.getValue();
	    double EE = ee.getValue();
	    double FF = ff.getValue();
	    double GG = gg.getValue();
	    double HH = hh.getValue();
	    double x = (AA + BB * Math.cos(u))* Math.cos(v);
		double y = (AA + BB * Math.cos(u))* Math.sin(v);
		double z = CC * Math.sin(u);
		Vector3D center = new Vector3D(DD,EE,FF);
		Vector3D toruspoint = new Vector3D(x,y,z);
		Vector3D P = new Vector3D();
		P = Inversion(GG,center,toruspoint);
		P.z = P.z + HH;
		return  P;
	}

}
