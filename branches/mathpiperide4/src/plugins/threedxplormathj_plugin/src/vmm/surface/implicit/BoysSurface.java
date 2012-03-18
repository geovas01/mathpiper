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

public class BoysSurface extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.BoysSurface.a", 0.3, 0.3, 0.3);


	public double heightFunction(double x, double y, double z) {
		double height;
		double aa = a.getValue();
		
		double xsqr=sqr(x); double ysqr = sqr(y); double zsqr = sqr(z);
		height = 64 * sqr(1-z) *(1 - z) * z * zsqr - 48 * sqr(1-z) * zsqr * (3 * xsqr + 3 * ysqr + 2 * zsqr) +
                12 * (1-z) * z * (27 * sqr(xsqr + ysqr) - 24 * zsqr * (xsqr + ysqr) + 
                36 * Math.sqrt(2) * y * z * (ysqr - 3 * xsqr) + 4 * sqr(zsqr)) + 
                (9 * xsqr + 9 * ysqr - 2 * zsqr) * (-81 * sqr(xsqr + ysqr) - 72 * zsqr * (xsqr + ysqr) +
                108 * Math.sqrt(2) * x * z * (xsqr - 3 * ysqr) + 4 * zsqr * zsqr); 
		return height;
	}
	
	public BoysSurface(){
		addParameter(a);
		setDefaultWindow(-1.85, 1.85, -1.85, 1.85);
		setDefaultViewpoint(new Vector3D(0.25,0.25,14));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(0,0,0);
		setFramesForMorphing(12);
	}

}