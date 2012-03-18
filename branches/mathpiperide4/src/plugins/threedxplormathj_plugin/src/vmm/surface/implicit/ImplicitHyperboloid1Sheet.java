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

public class ImplicitHyperboloid1Sheet extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.Hyperboloid1.a", 0.75, 0.75, 1.5);
	

	public double heightFunction(double x, double y, double z) {
		double height;
		double aa = a.getValue();
		
		height = -(sqr(x/aa) + sqr(y) - sqr(z)) ;  
		return height;
	}
	
	public ImplicitHyperboloid1Sheet(){
		addParameter(a);
		setDefaultWindow(-2.0, 2.0, -2.0, 2.0);
		setDefaultViewpoint(new Vector3D(9,50,1.5));
		searchRadius.reset(3);
		pointCloudCount.reset(16000);
		randomLineCount.reset(80000);
		level.reset(-1.0,-1.0,-1.0);
		heightFunctionType = equationType.QUADRATIC;
	}

}
