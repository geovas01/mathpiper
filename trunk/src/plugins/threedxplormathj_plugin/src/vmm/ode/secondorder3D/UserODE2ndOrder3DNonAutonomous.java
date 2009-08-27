/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder3D;

import vmm.core.VariableParamAnimateable;
import vmm.core3D.UserExhibit3D;
import vmm.core3D.Vector3D;

public class UserODE2ndOrder3DNonAutonomous extends ODE2ndOrder3DNonAutonomous implements UserExhibit3D {

	private UserExhibit3D.Support userExhibitSupport;
	private UserExhibit3D.FunctionInfo f1, f2, f3;
	
	public UserODE2ndOrder3DNonAutonomous() {
		userExhibitSupport = new UserExhibit3D.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",1));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",0.5,0,1));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",1));
		f1 = userExhibitSupport.addRealFunction("x''", "-(a^2 + b*sin(t)) * x", "x", "y", "z", "x'", "y'", "z'", "t");
		f2 = userExhibitSupport.addRealFunction("y''", "-(a^2 + b*sin(t + c)) * y", "x", "y", "z", "x'", "y'", "z'", "t");
		f3 = userExhibitSupport.addRealFunction("z''", "-(a^2 + b*sin(t + c*2)) * z", "x", "y", "z", "x'", "y'", "z'", "t");
		initialDataDefault = new double[] { 0, 1, 1, 1, 0, 0, 0 };
		setDefaultWindow(-5,5,-5,5);
		setDefaultViewpoint(new Vector3D(10,-10,10));
	}
	
	public UserExhibit3D.Support getUserExhibitSupport() {
		return userExhibitSupport;
	}

	protected double xdotdot(double x, double y, double z, double xdot, double ydot, double zdot, double t) {
		return f1.realFunctionValue(x,y,z,xdot,ydot,zdot,t);
	}

	protected double ydotdot(double x, double y, double z, double xdot, double ydot, double zdot, double t) {
		return f2.realFunctionValue(x,y,z,xdot,ydot,zdot,t);
	}

	protected double zdotdot(double x, double y, double z, double xdot, double ydot, double zdot, double t) {
		return f3.realFunctionValue(x,y,z,xdot,ydot,zdot,t);
	}


}
