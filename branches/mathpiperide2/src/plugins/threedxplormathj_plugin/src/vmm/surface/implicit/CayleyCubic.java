/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.implicit;

import vmm.core3D.Vector3D;

public class CayleyCubic extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}


	public double heightFunction(double x, double y, double z) {
		double height;
		double xsqr =sqr(x); double ysqr = sqr(y); double zsqr = sqr(z); 
		height = 4*(xsqr + ysqr + zsqr) + 16 * x * y * z; 
		return height;
	}
	
	public CayleyCubic(){
		setDefaultWindow(-3.25, 3.25, -3.25, 3.25);
		setDefaultViewpoint(new Vector3D(-7,-10,10.0));
		setDefaultViewUp(new Vector3D(0.3634,0.5192,0.774));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(1.0,0.0,1.0);
		setFramesForMorphing(12);
    	heightFunctionType = equationType.CUBIC;
	}

}