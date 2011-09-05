/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder3D;

import vmm.core.VariableParamAnimateable;
import vmm.core3D.UserExhibit3D;
import vmm.core3D.Vector3D;

public class UserODE1stOrder3D extends ODE1stOrder3D implements UserExhibit3D {

	private UserExhibit3D.Support userExhibitSupport;
	private UserExhibit3D.FunctionInfo f1, f2, f3;
	
	public UserODE1stOrder3D() {
		userExhibitSupport = new UserExhibit3D.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",-1));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",1,0.2,1));
		f1 = userExhibitSupport.addRealFunction("x'", "a * y", "x", "y", "z");
		f2 = userExhibitSupport.addRealFunction("y'", "b * x", "x", "y", "z");
		f3 = userExhibitSupport.addRealFunction("z'", "-z", "x", "y", "z");
		setDefaultWindow(-2,2,-2,2);
		showAxes = true;
		setDefaultViewpoint(new Vector3D(11,11,5));
		initialDataDefault = new double[] { 1, -1, 2, -1, 20 };
	}
	
	protected double xPrime(double x, double y, double z) {
		return f1.realFunctionValue(x,y,z);
	}

	protected double yPrime(double x, double y, double z) {
		return f2.realFunctionValue(x,y,z);
	}

	protected double zPrime(double x, double y, double z) {
		return f3.realFunctionValue(x,y,z);
	}

	public UserExhibit3D.Support getUserExhibitSupport() {
		return userExhibitSupport;
	}
	
	

}
