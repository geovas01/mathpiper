/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder2D;

import vmm.core.UserExhibit;
import vmm.core.VariableParamAnimateable;

public class UserODEF2ndOrder2DNonAutonomous extends ODE2ndOrder2DNonAutonomous implements UserExhibit {

	private UserExhibit.Support userExhibitSupport;
	private UserExhibit.FunctionInfo f1, f2;
	
	public UserODEF2ndOrder2DNonAutonomous() {
		userExhibitSupport = new UserExhibit.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",1));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",0.05,0,0.15));
		f1 = userExhibitSupport.addRealFunction("x''", "-a*x/(x^2+y^2) + b*x*(x^2+y^2)*t", "x", "y", "x'", "y'", "t");
		f2 = userExhibitSupport.addRealFunction("y''", "-a*y/(x^2+y^2) + b*y*(x^2+y^2)*t", "x", "y", "x'", "y'", "t");
		initialDataDefault = new double[] { 0, 1, 0, 0, 0.4, -1, 15 };
		setDefaultWindow(-3,3,-3,3);
	}
	
	public Support getUserExhibitSupport() {
		return userExhibitSupport;
	}

	protected double xdotdot(double x, double y, double xdot, double ydot, double t) {
		return f1.realFunctionValue(x,y,xdot,ydot,t);
	}

	protected double ydotdot(double x, double y, double xdot, double ydot, double t) {
		return f2.realFunctionValue(x,y,xdot,ydot,t);
	}

}
