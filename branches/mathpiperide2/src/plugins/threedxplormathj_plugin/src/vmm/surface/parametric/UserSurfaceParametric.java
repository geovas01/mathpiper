/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core.VariableParamAnimateable;
import vmm.core3D.UserExhibit3D;
import vmm.core3D.Vector3D;


public class UserSurfaceParametric extends SurfaceParametric implements UserExhibit3D {
	
	private UserExhibit3D.Support userExhibitSupport;
	private UserExhibit3D.FunctionInfo xFunc, yFunc, zFunc;
	
	public UserSurfaceParametric() {
		umin.reset(-2);
		umax.reset(2);
		vmin.reset(-2);
		vmax.reset(2);
		setDefaultWindow(-3,3,-2.6,2.6);
		setDefaultViewpoint(new Vector3D(5,-5.5,3));
		userExhibitSupport = new UserExhibit3D.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",14,0,14));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",0.5,0.25,0.5));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",2,1,2));
		xFunc = userExhibitSupport.addRealFunction("x", "u", "u", "v");
		yFunc = userExhibitSupport.addRealFunction("y", "v", "u", "v");
		zFunc = userExhibitSupport.addRealFunction("z", "c * sin(a/sqrt(1+u^2+v^2)) * exp(-b*(u^2+v^2))", "u", "v");
	}
	
	
	public Vector3D surfacePoint(double u, double v) {
		double[] args = new double[] { u,v };
		double x = xFunc.realFunctionValue(args);
		double y = yFunc.realFunctionValue(args);
		double z = zFunc.realFunctionValue(args);
		if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
			return null;
		else
			return new Vector3D(x,y,z);
	}


	public vmm.core.UserExhibit.Support getUserExhibitSupport() {
		return userExhibitSupport;
	}

}
