/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder2D;

import vmm.core.RealParamAnimateable;

public class FoucaultPendulum extends ODE2ndOrder2D {
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.ode.secondorder2D.FoucaultPendulum.ForceConstant",1);
	private RealParamAnimateable b = new RealParamAnimateable("vmm.ode.secondorder2D.FoucaultPendulum.Rotation",0.1);
	private RealParamAnimateable c = new RealParamAnimateable("vmm.ode.secondorder2D.FoucaultPendulum.Latitude",45,0,45);
	
	public FoucaultPendulum() {
		addParameter(c);
		addParameter(b);
		addParameter(a);
		setDefaultWindow(-1.5, 1.5, -1.5, 1.5);
		initialDataDefault = new double[] { 0.7071, 0.7071, 0, 0, 0.05, 60 };
	}

	protected double xdotdot(double x, double y, double xdot, double ydot) {
		double aa = a.getValue();
		return -aa*aa*x + 2*b.getValue()*Math.sin(Math.PI*c.getValue()/180)*ydot;
	}

	protected double ydotdot(double x, double y, double xdot, double ydot) {
		double aa = a.getValue();
		return -aa*aa*y - 2*b.getValue()*Math.sin(Math.PI*c.getValue()/180)*xdot;
	}

}
