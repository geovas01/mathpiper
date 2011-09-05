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

public class CrossCap extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.CrossCap.a", 0.17,0.17 , 0.17);


	public double heightFunction(double x, double y, double z) {
		double height;
		double aa = a.getValue();
		double xsqr=sqr(x); double ysqr = sqr(y); double zsqr = sqr(z);
		double aasqr = sqr(aa);
		height = 4 * aasqr * xsqr * (aasqr*(xsqr + ysqr + zsqr) + aa *z) + 
                 aasqr* ysqr*(aasqr * (ysqr + zsqr) -1); 
		return height;
	}
	
	public CrossCap(){

		addParameter(a);
		setDefaultWindow(-7, 7, -7, 7);
		setDefaultViewpoint(new Vector3D(-16.93,-0.137,3.64));
		setDefaultViewUp(new Vector3D(0.21,0.007,0.98));
		searchRadius.reset(6);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(0.0,0.0,0.0);
		setFramesForMorphing(12);
		heightFunctionType = equationType.QUARTIC;
	}

}