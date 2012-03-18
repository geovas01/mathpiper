/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.implicit;

import vmm.core3D.UserExhibit3D;
import vmm.core3D.Vector3D;

	
	public class UserSurfaceImplicit extends SurfaceImplicit implements UserExhibit3D {
		
		
		
		private UserExhibit3D.Support userExhibitSupport;
		private UserExhibit3D.FunctionInfo UserHeightFcn;
		
		public UserSurfaceImplicit() {
			setDefaultWindow(-2.25, 2.25, -2.25, 2.25);
			setDefaultViewpoint(new Vector3D(0.25,2,-18));
			searchRadius.reset(2);
			randomLineCount.reset(20000);
			pointCloudCount.reset(8000);
			level.reset(2,0.5,2.5);
			setFramesForMorphing(11);
			userExhibitSupport = new UserExhibit3D.Support(this);
			//userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",1,1,1));
			//userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",1,1,1));
			//userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",2,1,1));
			//userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("r",2,2,8));
			UserHeightFcn = userExhibitSupport.addRealFunction("Height", "4 * x^2 + 4 * y^2 + 4 * z^2 + 16 * x*y*z", "x", "y", "z");
		}
		
		
		public double heightFunction(double x, double y, double z) {
			return UserHeightFcn.realFunctionValue(x,y,z); 
		}


		public vmm.core.UserExhibit.Support getUserExhibitSupport() {
			return userExhibitSupport;
		}

	}

