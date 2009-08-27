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

public class UserMagneticField extends ChargedParticles implements UserExhibit3D {

	private UserExhibit3D.Support userExhibitSupport;
	private UserExhibit3D.FunctionInfo f1, f2, f3;
	
	public UserMagneticField() {
		userExhibitSupport = new UserExhibit3D.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",1,1,0));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",1,1,0));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",0,0,1));
		f1 = userExhibitSupport.addRealFunction("Field_x", "a", "x", "y", "z");
		f2 = userExhibitSupport.addRealFunction("Field_y", "b", "x", "y", "z");
		f3 = userExhibitSupport.addRealFunction("Field_z", "c", "x", "y", "z");
		initialDataDefault = new double[] { -2, -2, 0, .2, .2, -.5, 0.05, 25 };
		setDefaultWindow(-2,2,-2,2);
		setDefaultViewpoint(new Vector3D(12,-12,12));
	}
	
	public UserExhibit3D.Support getUserExhibitSupport() {
		return userExhibitSupport;
	}
	

	protected void magneticField(double x, double y, double z, Vector3D answer) {
		answer.x = f1.realFunctionValue(x,y,z);
		answer.y = f2.realFunctionValue(x,y,z);
		answer.z = f3.realFunctionValue(x,y,z);
	}



}
