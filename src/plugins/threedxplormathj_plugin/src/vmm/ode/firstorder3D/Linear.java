/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder3D;

import vmm.core.RealParamAnimateable;

public class Linear extends ODE1stOrder3D {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",-0.05);
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",-1);
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc",0);
	private RealParamAnimateable dd = new RealParamAnimateable("genericParam.dd",1);
	private RealParamAnimateable ee = new RealParamAnimateable("genericParam.ee",-0.05);
	private RealParamAnimateable ff = new RealParamAnimateable("genericParam.ff",0);
	private RealParamAnimateable gg = new RealParamAnimateable("genericParam.gg",0);
	private RealParamAnimateable hh = new RealParamAnimateable("genericParam.hh",-0.02,0,-0.04);
	private RealParamAnimateable ii = new RealParamAnimateable("genericParam.ii",-0.05,0,-0.1);

	public Linear() {
		addParameter(ii);
		addParameter(hh);
		addParameter(gg);
		addParameter(ff);
		addParameter(ee);
		addParameter(dd);
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		initialDataDefault = new double[] { 2, 2, 2.55, 0.05, 50 };
		setDefaultWindow(-4,4,-4,4);
		showAxes = true;
	}
	
	protected double xPrime(double x, double y, double z) {
		return aa.getValue()*x + bb.getValue()*y + cc.getValue()*z;
	}

	protected double yPrime(double x, double y, double z) {
		return dd.getValue()*x + ee.getValue()*y + ff.getValue()*z;
	}

	protected double zPrime(double x, double y, double z) {
		return gg.getValue()*x + hh.getValue()*y + ii.getValue()*z;
	}
	

}
