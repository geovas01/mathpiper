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

public class ImplicitHyperboloid2Sheet extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.Hyperboloid2.a", 0.6, 0.6, 0.8);
	private RealParamAnimateable b = new RealParamAnimateable("vmm.surface.implicit.Hyperboloid2.b", 10, 10.0, 8.0);

	public double heightFunction(double x, double y, double z) {
		double height;
		double aa = a.getValue();
		double bb = b.getValue();
		
		height = bb*(sqr(x) - sqr(y) - sqr(z/aa)) ;  
		return height;
	}
	
	public ImplicitHyperboloid2Sheet(){
		addParameter(b);
		addParameter(a);
		setDefaultWindow(-1.5, 1.5, -1.5, 1.5);
		setDefaultViewpoint(new Vector3D(9,500,-1.5));
		searchRadius.reset(3);
		pointCloudCount.reset(12000);
		level.reset(1.0,1.0,1.0);
		heightFunctionType = equationType.QUADRATIC;
	}

}
