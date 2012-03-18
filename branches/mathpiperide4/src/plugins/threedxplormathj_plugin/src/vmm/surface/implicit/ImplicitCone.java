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

public class ImplicitCone  extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.Cone.a", 0.6, 0.4, 0.8);


	public double heightFunction(double x, double y, double z) {
		double height;
		double aa = a.getValue();
		
		height = sqr(aa*x) - sqr(y) - sqr(z) ;  
		return height;
	}
	
	public ImplicitCone(){
		addParameter(a);
		setDefaultWindow(-1.25, 1.25, -1.25, 1.25);
		setDefaultViewpoint(new Vector3D(0,17.32,0));
		searchRadius.reset(2,2,2);
		pointCloudCount.reset(12000);
		level.reset(0.0,0.0,0.0);
		heightFunctionType = equationType.QUADRATIC;
	}

}
