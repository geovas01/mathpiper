/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.implicit;

import vmm.core3D.Vector3D;

public class BarthSextic extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	

	public double heightFunction(double x, double y, double z) {
		double height;
		double xsqr=sqr(x); double ysqr = sqr(y); double zsqr = sqr(z);
		height = -( 4*(2.618*xsqr-ysqr)*(2.618*ysqr-zsqr)*(2.618*zsqr-xsqr)-4.236*sqr(xsqr+ysqr+zsqr-1) ); 
		return height;
	}
	
	public BarthSextic(){
		setDefaultWindow(-3.0, 3.0, -3.0, 3.0);
		setDefaultViewpoint(new Vector3D(3.4,-13,9.8));
		searchRadius.reset(3);
		randomLineCount.reset(80000);
		rayTraceResolution.reset(0.005);
	    resolution = rayTraceResolution.getValue();
		pointCloudCount.reset(18000);
		level.reset(0.0,0.05,0.1);
		setFramesForMorphing(12);
	}

}