/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder1D;

import vmm.core.UserExhibit;
import vmm.core.VariableParamAnimateable;

public class UserODEFirstOrder1D extends ODE1stOrder1D implements UserExhibit {

	private UserExhibit.Support userExhibitSupport;
	private UserExhibit.FunctionInfo f2;
	
	public UserODEFirstOrder1D() {
		userExhibitSupport = new UserExhibit.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",1,1,2));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",1,1,2));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",1,1,0));
		f2 = userExhibitSupport.addRealFunction("y'", "a*cos(b*t)*sin(c*y)", "t", "y");
		setDefaultWindow(-4,4,-7,7);
		initialDataDefault = new double[] { -2, 2, 0.1, 30 };
	}
	
	protected double x2Prime(double t, double y) {
		return f2.realFunctionValue(t,y);
	}

	protected double x1Prime(double t, double y) {
		return 1;
	}

	public Support getUserExhibitSupport() {
		return userExhibitSupport;
	}

}