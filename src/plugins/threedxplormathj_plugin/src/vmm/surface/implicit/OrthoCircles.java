/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.implicit;

import vmm.core3D.Vector3D;

public class OrthoCircles extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	


	public double heightFunction(double x, double y, double z) {
		double height;
		height = (sqr(sqr(x) + sqr(y) -1) + sqr(z))*
        (sqr(sqr(y) + sqr(z) -1) + sqr(x))*
        (sqr(sqr(z) + sqr(x) -1) + sqr(y));
		return height;
	}
	
	public OrthoCircles(){
		setDefaultWindow(-1.3, 1.3, -1.3, 1.3);
		setDefaultViewpoint(new Vector3D(-6,-10,13));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(0.01,0.0016,0.1225);
		setFramesForMorphing(14);
	}

}