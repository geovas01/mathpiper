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

public class Pilz extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.Pilz.a", 0.9, 0.9, 0.9);
	private RealParamAnimateable b = new RealParamAnimateable("vmm.surface.implicit.Pilz.b", 1, 1, 1);
	private RealParamAnimateable c = new RealParamAnimateable("vmm.surface.implicit.Pilz.c", 0.03, 0.03, 0.83);
	private RealParamAnimateable d = new RealParamAnimateable("vmm.surface.implicit.Pilz.d", 0.28, 0.28, 0.28);



	public double heightFunction(double x, double y, double z) {
		double height;
		double dd = d.getValue();
		double cc = c.getValue();
		double bb = b.getValue();
		double aa = a.getValue();
		double xsqr=sqr(x); double ysqr = sqr(y); double zsqr = sqr(z);
		height = ( sqr(xsqr + ysqr - 1) + zsqr ) *
        (  sqr( ysqr/sqr(aa) + sqr(z + cc) -1) +xsqr ) - sqr(dd)*(1 + bb*zsqr ); 
		return height;
	}
	
	public Pilz(){
		addParameter(d);
		addParameter(c);
		addParameter(b);
		addParameter(a);
		setDefaultWindow(-1.7, 1.7, -1.7, 1.7);
		setDefaultViewpoint(new Vector3D(20,-12,22));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(0.0,0.0,0);
		setFramesForMorphing(11);
	}

}