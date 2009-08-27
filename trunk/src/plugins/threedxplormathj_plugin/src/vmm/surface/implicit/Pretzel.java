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

public class Pretzel extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.Pretzel.a", 0.475, 0.475, 0.475);
	private RealParamAnimateable b = new RealParamAnimateable("vmm.surface.implicit.Pretzel.b", 3, 3, 3);
	private RealParamAnimateable c = new RealParamAnimateable("vmm.surface.implicit.Pretzel.c", 1.5, 1.5, 1.5);
	private RealParamAnimateable d = new RealParamAnimateable("vmm.surface.implicit.Pretzel.d", 0.2, 0.1, 0.5);


	public double heightFunction(double x, double y, double z) {
		double height;
		double dd = d.getValue();
		double cc = c.getValue();
		double bb = b.getValue();
		double aa = a.getValue();
		double xsqr = sqr(x);   double ysqr = sqr(y);    double zsqr = sqr(z);
		height = sqr((sqr(x-aa) + ysqr - 1)*(sqr(x+aa) + ysqr - 1) ) 
          + (cc*zsqr - sqr(dd)) * (1 + bb*sqr(xsqr + ysqr));
		return height;
	}
	
	public Pretzel(){
	    addParameter(d);
		addParameter(c);
		addParameter(b);
		addParameter(a);
		setDefaultWindow(-1.25, 1.25, -1.25, 1.25);
		setDefaultViewpoint(new Vector3D(2,18,10));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(0,0.0,0.0);
		setFramesForMorphing(11);
	}

}
