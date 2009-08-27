/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder3D;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

public class MagneticDipole extends ChargedParticles {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",0);
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",0,0,1.4142);
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc",1.4142,1.4142,0);
	
	public MagneticDipole() {
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		initialDataDefault = new double[] { -.7, -.7, 0, 0, 0, -.2, .02, 100 };
		setDefaultWindow(-1, 1, -1, 1);
		setDefaultViewpoint(new Vector3D(11.6, -8.1, 10));
	}

	protected void magneticField(double x, double y, double z, Vector3D answer) {
		double a = aa.getValue();
		double b = bb.getValue();
		double c = cc.getValue();
		double rsquare = x*x + y*y + z*z;
		double rcube = rsquare * Math.sqrt(rsquare);
		double eDOTp = a * x + b * y + c * z;
		answer.x = ((3 * eDOTp * x)/rsquare - a) / rcube;
		answer.y = ((3 * eDOTp * y)/rsquare - b) / rcube;
		answer.z = ((3 * eDOTp * z)/rsquare - c) / rcube;
	}
	
//	rsquare := x * x + y * y + z * z;
//	rcube := rsquare * sqrt(rsquare);
//	eDOTp := aa * x + bb * y + cc * z;
//	MagneticField.x := ((3 * eDOTp * x)/rsquare - aa) / rcube;
//	MagneticField.y := ((3 * eDOTp * y)/rsquare - bb) / rcube;
//	MagneticField.z := ((3 * eDOTp * z)/rsquare - cc) / rcube;


}
