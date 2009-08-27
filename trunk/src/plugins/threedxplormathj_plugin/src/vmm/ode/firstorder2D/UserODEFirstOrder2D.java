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

public class UserODEFirstOrder2D extends ODE1stOrder2D implements UserExhibit {

	private UserExhibit.Support userExhibitSupport;
	private UserExhibit.FunctionInfo f1, f2;
	
	public UserODEFirstOrder2D() {
		userExhibitSupport = new UserExhibit.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",0.1,0,0.2));
		f1 = userExhibitSupport.addRealFunction("x'", "-y + c*x*(1 - x^2 - y^2)", "x", "y");
		f2 = userExhibitSupport.addRealFunction("y'", "x + c*y*(1 - x^2 - y^2)", "x", "y");
		setDefaultWindow(-2,2,-2,2);
		initialDataDefault = new double[] { -2, 2, 0.05, 30 };
	}
	
	protected double x1Prime(double x, double y) {
		return f1.realFunctionValue(x,y);
	}

	protected double x2Prime(double x, double y) {
		return f2.realFunctionValue(x,y);
	}

	public Support getUserExhibitSupport() {
		return userExhibitSupport;
	}

}
