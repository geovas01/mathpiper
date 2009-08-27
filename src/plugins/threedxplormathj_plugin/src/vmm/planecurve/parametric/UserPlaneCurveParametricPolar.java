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
 * A plane curve defined by polar parametric functions (rho(t),theta(t)), where the functions and other data
 * for the exhibit are entered by the user. 
 */
public class UserPlaneCurveParametricPolar extends PlaneCurveParametric implements UserExhibit {
	
	private UserExhibit.Support userExhibitSupport;
	private UserExhibit.FunctionInfo rho,theta;
	
	public UserPlaneCurveParametricPolar() {
		tmin.reset(0);
		tmax.reset("2 * pi");
		tResolution.reset(250);
		userExhibitSupport = new UserExhibit.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",1.5,1,2));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",3,1,2));
		rho = userExhibitSupport.addRealFunction("rho", "a * cos(t)", "t");
		theta = userExhibitSupport.addRealFunction("theta", "b * t", "t");
		setDefaultWindow(-2,2,-2,2);
	}
	
	public double xValue(double t) {
		return rho.realFunctionValue(t)*Math.cos(theta.realFunctionValue(t));
	}
	
	public double yValue(double t) {
		return rho.realFunctionValue(t)*Math.sin(theta.realFunctionValue(t));
	}

	public Support getUserExhibitSupport() {
		return userExhibitSupport;
	}
	
}
