/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder2D;

import vmm.core.RealParamAnimateable;

public class ForcedOscillators extends ODE2ndOrder2DNonAutonomous {
	
	private RealParamAnimateable a = new RealParamAnimateable("genericParam.aa",1,1,1);
	private RealParamAnimateable b = new RealParamAnimateable("genericParam.bb",0.1,0,0.5);
	private RealParamAnimateable c = new RealParamAnimateable("genericParam.cc",1,0,0.1);
	private RealParamAnimateable d = new RealParamAnimateable("genericParam.dd",1,0,0.1);
	private RealParamAnimateable e = new RealParamAnimateable("genericParam.ee",0.1,0,0.5);
	private RealParamAnimateable f = new RealParamAnimateable("genericParam.ff",1,0.5,1.5);
	
	public ForcedOscillators() {
		addParameter(f);
		addParameter(e);
		addParameter(d);
		addParameter(c);
		addParameter(b);
		addParameter(a);
		setDefaultWindow(-2, 2, -2, 2);
		initialDataDefault = new double[] {0, 0, 0, 1, 0, 0.05, 60 };
	}

	protected double xdotdot(double x, double y, double xdot, double ydot, double t) {
		double aa = a.getValue();
		return -aa*aa*x + b.getValue()*Math.cos(c.getValue()*t);
	}

	protected double ydotdot(double x, double y, double xdot, double ydot,double t) {
		double dd = d.getValue();
		return -dd*dd*y + e.getValue()*Math.cos(f.getValue()*t);
	}

}
