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

public class CubeOktaeder extends SurfaceImplicit {

	public static double sqr(double x){return x*x;}
	
	private RealParamAnimateable a = new RealParamAnimateable("vmm.surface.implicit.CubeOktaeder.a", 1, 1, 1);
	private RealParamAnimateable b = new RealParamAnimateable("vmm.surface.implicit.CubeOktaeder.b", 2, 1, 3);


	public double heightFunction(double x, double y, double z) {
		double height;
		double bb = b.getValue();
		double aa = a.getValue();
		double partA = aa*(Math.abs(x)+Math.abs(y)+Math.abs(z)); //octahedron
		double partB = bb*(Math.max(Math.max(Math.abs(x),Math.abs(y)),Math.abs(z))); //cube
		height = Math.max(partA,partB) ;  
		return height;
	}
	
	public CubeOktaeder(){
		addParameter(b);
		addParameter(a);
		setDefaultWindow(-1.0, 1.0, -1.0, 1.0);
		setDefaultViewpoint(new Vector3D(-10,-10,10));
		searchRadius.reset(3);
		randomLineCount.reset(60000);
		pointCloudCount.reset(12000);
		level.reset(1.0,1.0,1.5);
		setFramesForMorphing(11);
	}

}

