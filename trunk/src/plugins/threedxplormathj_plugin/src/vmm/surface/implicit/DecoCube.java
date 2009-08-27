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

public class DecoCube extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.DecoCube.a", 0.8, 0.35, 1.3);



	public double heightFunction(double x, double y, double z) {
		double height;
		double aa = a.getValue();
		double xsqr=sqr(x); double ysqr = sqr(y); double zsqr = sqr(z);

	
		height =  ( sqr(xsqr + ysqr - sqr(aa)) + sqr((z-1) * (z + 1 )) ) *
	              ( sqr(ysqr + zsqr - sqr(aa)) + sqr((x-1) * (x + 1)) ) *
	              ( sqr(zsqr + xsqr - sqr(aa)) + sqr((y-1) * (y + 1 )) );
		return height;
	}
	
	public DecoCube(){
		addParameter(a);
		setDefaultWindow(-2.0, 2.0, -2.0, 2.0);
		setDefaultViewpoint(new Vector3D(-10,-10,10));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(18000);
		level.reset(0.04,0.0625,0.04);
		setFramesForMorphing(12);
	}

}