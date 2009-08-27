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

public class ForcedOscillators extends ODE2ndOrder3DNonAutonomous {

	private RealParamAnimateable aa = new RealParamAnimateable("vmm.ode.secondOrder3D.ForcedOscillators.ForceX",1);
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.ode.secondOrder3D.ForcedOscillators.ForcingX",0.1,0,0.5);
	private RealParamAnimateable cc = new RealParamAnimateable("vmm.ode.secondOrder3D.ForcedOscillators.FrequencyX",1);
	private RealParamAnimateable dd = new RealParamAnimateable("vmm.ode.secondOrder3D.ForcedOscillators.ForceY",1);
	private RealParamAnimateable ee = new RealParamAnimateable("vmm.ode.secondOrder3D.ForcedOscillators.ForcingY",0.1,0,0.5);
	private RealParamAnimateable ff = new RealParamAnimateable("vmm.ode.secondOrder3D.ForcedOscillators.FrequencyY",1);
	private RealParamAnimateable gg = new RealParamAnimateable("vmm.ode.secondOrder3D.ForcedOscillators.ForceZ",1);
	private RealParamAnimateable hh = new RealParamAnimateable("vmm.ode.secondOrder3D.ForcedOscillators.ForcingZ",0.1,0,0.5);
	private RealParamAnimateable ii = new RealParamAnimateable("vmm.ode.secondOrder3D.ForcedOscillators.FrequencyZ",1);

	public ForcedOscillators() {
		addParameter(ii);
		addParameter(hh);
		addParameter(gg);
		addParameter(ff);
		addParameter(ee);
		addParameter(dd);
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		initialDataDefault = new double[] { 0, 0, 0, 0, 1.5, 0, 0, 0.05, 80 };
		setDefaultWindow(-2,2,-2,2);
		setDefaultViewpoint(new Vector3D(10,-10,10));
		showAxes = true;
	}	
	
	protected double xdotdot(double x, double y, double z, double xdot, double ydot, double zdot, double t) {
		double a = aa.getValue();
		return -a*a*x + bb.getValue()*Math.cos(cc.getValue()*t);
	}

	protected double ydotdot(double x, double y, double z, double xdot, double ydot, double zdot, double t) {
		double d = dd.getValue();
		return -d*d*y + ee.getValue()*Math.cos(ff.getValue()*t);
	}

	protected double zdotdot(double x, double y, double z, double xdot, double ydot, double zdot, double t) {
		double g = gg.getValue();
		return -g*g*z + hh.getValue()*Math.cos(ii.getValue()*t);
	}

}
