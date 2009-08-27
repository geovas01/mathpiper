/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder1D;

import vmm.core.UserExhibit;
import vmm.core.VariableParamAnimateable;
import vmm.ode.firstorder2D.ODE1stOrder2DNonAutonomous;

public class UserODESecondOrder1D extends ODE1stOrder2DNonAutonomous implements UserExhibit {

	private UserExhibit.Support userExhibitSupport;
	private UserExhibit.FunctionInfo f2; 
	
	public UserODESecondOrder1D() {
		userExhibitSupport = new UserExhibit.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",-1,-1,-1));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",-0.1,1,1));
		f2 = userExhibitSupport.addRealFunction("x''", "a * sin(t) * x + b * x'", "x", "x'", "t");
		setDefaultWindow(-6,6,-6,6);
		initialDataDefault = new double[] { 0, -0.6, 0.25, -1, 15 };
	}
	
	protected double x1Prime(double x, double y, double t) {
		//return f1.realFunctionValue(x,y,t);
		return y;
	}

	protected double x2Prime(double x, double y, double t) {
		return f2.realFunctionValue(x,y,t);
	}

	public Support getUserExhibitSupport() {
		return userExhibitSupport;
	}

}