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

public class UserODEF2ndOrder3D extends ODE2ndOrder3D implements UserExhibit3D {

	private UserExhibit3D.Support userExhibitSupport;
	private UserExhibit3D.FunctionInfo f1, f2, f3;
	
	public UserODEF2ndOrder3D() {
		userExhibitSupport = new UserExhibit3D.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",-4));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",0,0,-0.05));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",-9));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("d",0,0,-0.02));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("e",-16));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("f",0,0,-0.05));
		f1 = userExhibitSupport.addRealFunction("x''", "a*x + b*x'", "x", "y", "z", "x'", "y'", "z'");
		f2 = userExhibitSupport.addRealFunction("y''", "c*y + d*y'", "x", "y", "z", "x'", "y'", "z'");
		f3 = userExhibitSupport.addRealFunction("z''", "e*z + f*z'", "x", "y", "z", "x'", "y'", "z'");
		initialDataDefault = new double[] { 1, 1, 1, -1, -1.5, 2, 0.01, 10 };
		setDefaultWindow(-2,2,-2,2);
		setDefaultViewpoint(new Vector3D(10,-10,10));
	}
	
	public UserExhibit3D.Support getUserExhibitSupport() {
		return userExhibitSupport;
	}

	protected double xdotdot(double x, double y, double z, double xdot, double ydot, double zdot) {
		return f1.realFunctionValue(x,y,z,xdot,ydot,zdot);
	}

	protected double ydotdot(double x, double y, double z, double xdot, double ydot, double zdot) {
		return f2.realFunctionValue(x,y,z,xdot,ydot,zdot);
	}

	protected double zdotdot(double x, double y, double z, double xdot, double ydot, double zdot) {
		return f3.realFunctionValue(x,y,z,xdot,ydot,zdot);
	}

}
