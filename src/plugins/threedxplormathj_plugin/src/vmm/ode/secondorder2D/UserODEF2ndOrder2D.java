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

public class UserODEF2ndOrder2D extends ODE2ndOrder2D implements UserExhibit {

	private UserExhibit.Support userExhibitSupport;
	private UserExhibit.FunctionInfo f1, f2;
	
	public UserODEF2ndOrder2D() {
		userExhibitSupport = new UserExhibit.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",-1,-1,-1));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",0,0,-0.05));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",-2,-1,-1));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("d",0,0,-0.05));
		f1 = userExhibitSupport.addRealFunction("x''", "a*x + b*x'", "x", "y", "x'", "y'");
		f2 = userExhibitSupport.addRealFunction("y''", "c*y + d*y'", "x", "y", "x'", "y'");
		initialDataDefault = new double[] { 1, 1, -1, 1 };
		setDefaultWindow(-2,2,-2,2);
	}
	
	public Support getUserExhibitSupport() {
		return userExhibitSupport;
	}

	protected double xdotdot(double x, double y, double xdot, double ydot) {
		return f1.realFunctionValue(x,y,xdot,ydot);
	}

	protected double ydotdot(double x, double y, double xdot, double ydot) {
		return f2.realFunctionValue(x,y,xdot,ydot);
	}

}
