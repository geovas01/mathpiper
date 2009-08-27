/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;

import vmm.core.UserExhibit;
import vmm.core.VariableParamAnimateable;

/**
 * A plane curve defined by parametric functions (x(t),y(t)), where the functions and other data
 * for the exhibit are entered by the user.
 */
public class UserPlaneCurveParametric extends PlaneCurveParametric implements UserExhibit {
		
	private UserExhibit.Support userExhibitSupport;
	private UserExhibit.FunctionInfo f1, f2;
	
	public UserPlaneCurveParametric() {
		userExhibitSupport = new UserExhibit.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",3,5,1));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",1.5,0.5,3));
		f1 = userExhibitSupport.addRealFunction("x", "a * cos(t)", "t");
		f2 = userExhibitSupport.addRealFunction("y", "b * sin(2*t)", "t");
	}
	
	
	public double xValue(double t) {
		return f1.realFunctionValue(t);
	}
	
	public double yValue(double t) {
		return f2.realFunctionValue(t);
	}

	public Support getUserExhibitSupport() {
		return userExhibitSupport;
	}
		
	
}
