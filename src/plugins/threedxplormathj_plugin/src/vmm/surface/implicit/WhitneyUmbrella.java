/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.implicit;

import vmm.core3D.Vector3D;

public class WhitneyUmbrella extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	

	public double heightFunction(double x, double y, double z) {
		double height;
		double xsqr =sqr(x); double zsqr = sqr(z); 
		height = -(xsqr * y -zsqr); 
		return height;
	}
	
	public WhitneyUmbrella(){
		setDefaultWindow(-3.2, 3.2, -3.2, 3.2);
		setDefaultViewpoint(new Vector3D(0.05,-17.33,0.83));
		setDefaultViewUp(new Vector3D(1,0,0.0));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(0.0,-0.5,0.5);
		setFramesForMorphing(14);
		 heightFunctionType = equationType.CUBIC;
		// heightFunctionType = equationType.QUADRATIC;
	}

}