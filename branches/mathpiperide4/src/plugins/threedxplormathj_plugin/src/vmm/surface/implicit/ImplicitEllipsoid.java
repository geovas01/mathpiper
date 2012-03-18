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

public class ImplicitEllipsoid extends SurfaceImplicit{
	
	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable xSemiAxis = new RealParamAnimateable("vmm.surface.implicit.Ellipsoid.xSemiaxis", 1, 1, 1.5);
	private RealParamAnimateable ySemiAxis = new RealParamAnimateable("vmm.surface.implicit.Ellipsoid.ySemiaxis", 1.75, 1.75, 1.0);
	private RealParamAnimateable zSemiAxis = new RealParamAnimateable("vmm.surface.implicit.Ellipsoid.zSemiaxis", 1.25, 1.25, 1.75);

	public double heightFunction(double x, double y, double z) {
		double height;
		double a = xSemiAxis.getValue();
		double b = ySemiAxis.getValue();
		double c = zSemiAxis.getValue();
	        height =  sqr(x/a) + sqr(y/b) + sqr(z/c);
		return height;
	}
	
	public ImplicitEllipsoid(){
		addParameter(zSemiAxis);
		addParameter(ySemiAxis);
		addParameter(xSemiAxis);
		setDefaultWindow(-2, 2, -2, 2);
		setDefaultViewpoint(new Vector3D(10,-10,10));
		searchRadius.reset(3);
		level.reset(1.0);
		pointCloudCount.reset(12000);
		heightFunctionType = equationType.QUADRATIC;
	}

}
