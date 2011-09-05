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

public class ImplicitParaboloid extends SurfaceImplicit{
	
	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.Paraboloid.a", 1.5, 1.5, 1.0);

	public double heightFunction(double x, double y, double z) {
		double height;
		double aa = a.getValue();

		height =   z - aa*(sqr(x) + sqr(y));
		return height;
	}
	
	public ImplicitParaboloid(){
		addParameter(a);
		setDefaultWindow(0.5, 1.75, -2.2, 2.4);
		setDefaultViewpoint(new Vector3D(-21,-44,-55));
		setDefaultViewUp(new Vector3D(-0.866,0.5,0.0));
		searchRadius.reset(3);
		pointCloudCount.reset(12000);
		level.reset(-1);
		heightFunctionType = equationType.QUADRATIC;
	}

}
