/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.implicit;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

public class SteinerRoman extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.SteinerRoman.a", 1.6, 1.6, 1.6);
	

	public double heightFunction(double x, double y, double z) {
		double height;
		double aa = a.getValue();
		double xsqr = sqr(x);   double ysqr = sqr(y);    double zsqr = sqr(z); double aasqr = sqr(aa);
		height = (aasqr * aasqr) * (xsqr * ysqr + ysqr * zsqr + zsqr * xsqr) - 2* aa * aasqr * x * y * z;
		return height;
	}
	
	public SteinerRoman(){
		addParameter(a);
		setDefaultWindow(-0.75, 0.75, -0.75, 0.75);
		setDefaultViewpoint(new Vector3D(10,-10,10));
		searchRadius.reset(1);
		randomLineCount.reset(80000);
		pointCloudCount.reset(18000);
		level.reset(0.0,0.0,0.0);
		setFramesForMorphing(12);
		resolution = 0.01;
		rayTraceResolution.setValue(resolution);
		heightFunctionType = equationType.QUARTIC;
	}

}