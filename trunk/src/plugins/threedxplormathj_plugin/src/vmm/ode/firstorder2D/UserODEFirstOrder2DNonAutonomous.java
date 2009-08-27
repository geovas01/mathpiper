/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder2D;

import vmm.core.UserExhibit;
import vmm.core.VariableParamAnimateable;

public class UserODEFirstOrder2DNonAutonomous extends ODE1stOrder2DNonAutonomous implements UserExhibit {

	private UserExhibit.Support userExhibitSupport;
	private UserExhibit.FunctionInfo f1, f2;
	
	public UserODEFirstOrder2DNonAutonomous() {
		userExhibitSupport = new UserExhibit.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",-1,-1,-1));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",1,1,1));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",0.25,0.5,0));
		f1 = userExhibitSupport.addRealFunction("x'", "a * sin(t) * y - c*x", "x", "y", "t");
		f2 = userExhibitSupport.addRealFunction("y'", "b * cos(t) * x - c*y", "x", "y", "t");
		setDefaultWindow(-2,2,-2,2);
		initialDataDefault = new double[] { 0, -0.6, 0.25, -1, 30 };
	}
	
	protected double x1Prime(double x, double y, double t) {
		return f1.realFunctionValue(x,y,t);
	}

	protected double x2Prime(double x, double y, double t) {
		return f2.realFunctionValue(x,y,t);
	}

	public Support getUserExhibitSupport() {
		return userExhibitSupport;
	}

}
