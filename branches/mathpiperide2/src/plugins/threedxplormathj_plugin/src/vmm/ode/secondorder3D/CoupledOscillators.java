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

public class CoupledOscillators extends ODE2ndOrder3D {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.ode.secondOrder3D.CoupledOscillators.ForceX",1);
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.ode.secondOrder3D.CoupledOscillators.ForceY",1);
	private RealParamAnimateable cc = new RealParamAnimateable("vmm.ode.secondOrder3D.CoupledOscillators.ForceZ",1);
	private RealParamAnimateable dd = new RealParamAnimateable("vmm.ode.secondOrder3D.CoupledOscillators.CouplingXY",0.2,0,0.1);
	private RealParamAnimateable ee = new RealParamAnimateable("vmm.ode.secondOrder3D.CoupledOscillators.CouplingZX",0,0,0.15);
	private RealParamAnimateable ff = new RealParamAnimateable("vmm.ode.secondOrder3D.CoupledOscillators.CouplingYZ",0.2,0,0.2);
	private RealParamAnimateable gg = new RealParamAnimateable("vmm.ode.secondOrder3D.CoupledOscillators.FrictionX",0);
	private RealParamAnimateable hh = new RealParamAnimateable("vmm.ode.secondOrder3D.CoupledOscillators.FrictionY",0);
	private RealParamAnimateable ii = new RealParamAnimateable("vmm.ode.secondOrder3D.CoupledOscillators.FrictionZ",0);

	public CoupledOscillators() {
		addParameter(ii);
		addParameter(hh);
		addParameter(gg);
		addParameter(ff);
		addParameter(ee);
		addParameter(dd);
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		initialDataDefault = new double[] { 0, 0, 0, 1.5, 0, 0, 0.05, 80 };
		setDefaultWindow(-2,2,-2,2);
		setDefaultViewpoint(new Vector3D(10,-10,10));
		showAxes = true;
	}	
	
	protected double xdotdot(double x, double y, double z, double xdot, double ydot, double zdot) {
		double a = aa.getValue();
		return -a*a*x + dd.getValue()*(y-x) + ee.getValue()*(z-x) - gg.getValue()*xdot;
	}

	protected double ydotdot(double x, double y, double z, double xdot, double ydot, double zdot) {
		double b = bb.getValue();
		return -b*b*y + dd.getValue()*(x-y) + ff.getValue()*(z-y) - hh.getValue()*ydot;
	}

	protected double zdotdot(double x, double y, double z, double xdot, double ydot, double zdot) {
		double c = cc.getValue();
		return -c*c*z + ee.getValue()*(x-z) + ff.getValue()*(y-z) - ii.getValue()*zdot;
	}

}
