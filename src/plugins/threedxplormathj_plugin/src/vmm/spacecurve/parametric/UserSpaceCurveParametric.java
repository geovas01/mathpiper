/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import vmm.core.VariableParamAnimateable;
import vmm.core3D.UserExhibit3D;
import vmm.core3D.Vector3D;

/**
 * Defines a space curve from three functions that give the coordinates of points on the curve as
 * functions of t, where the data is input by the user.
 */
public class UserSpaceCurveParametric extends SpaceCurveParametric implements UserExhibit3D {
		
	private UserExhibit3D.Support userExhibitSupport;
	private UserExhibit3D.FunctionInfo xOfT, yOfT, zOfT;
	
	public UserSpaceCurveParametric() {
		tmin.reset(-1);
		tmax.reset(1);
		tResolution.reset(300);
		tubeSize.reset(0.2);
		userExhibitSupport = new UserExhibit3D.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",2,1.3,2.2));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",3,2.3,3.2));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",0.1,0,0.3));
		xOfT = userExhibitSupport.addRealFunction("x", "(a + c*cos(7*t)) * sin(5*pi*t)", "t");
		yOfT = userExhibitSupport.addRealFunction("y", "(a + c*cos(7*t)) * cos(5*pi*t)", "t");
		zOfT = userExhibitSupport.addRealFunction("z", "b*sin(7*pi*t)", "t");
	}
	
	protected Vector3D value(double t) {
		double[] arg = new double[] { t };
		double x = xOfT.realFunctionValue(arg);
		double y = yOfT.realFunctionValue(arg);
		double z = zOfT.realFunctionValue(arg);
		if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z))
			return null;
		else
			return new Vector3D(x,y,z);
	}

	public vmm.core.UserExhibit.Support getUserExhibitSupport() {
		return userExhibitSupport;
	}
	
	

}
