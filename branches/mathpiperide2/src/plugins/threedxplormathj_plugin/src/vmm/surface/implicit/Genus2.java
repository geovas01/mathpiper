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

public class Genus2 extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.Genus2.a", 0.005, 0.005, 0.005);


	public double heightFunction(double x, double y, double z) {
		double height;
		double aa = a.getValue();
		double xsqr =sqr(x); double ysqr = sqr(y); double zsqr = sqr(z); 
		double aux = (1 + aa*(1 + xsqr + ysqr + zsqr));
		height = (sqr(xsqr*(1-xsqr)-ysqr) + 0.5*zsqr)/aux;
		return height;
	}
	
	public Genus2(){
		addParameter(a);
		setDefaultWindow(-1.0, 1.0, -1.0, 1.0);
		setDefaultViewpoint(new Vector3D(1.6,-7,15.75));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(0.025,0.025,0.025);
		setFramesForMorphing(12);
	}

}