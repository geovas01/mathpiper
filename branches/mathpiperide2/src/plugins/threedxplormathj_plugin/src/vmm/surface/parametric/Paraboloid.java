/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

/**
 * Defines a paraboloid with parametric equations
 * (a*v*cos(u), b*v*sin(u), c*(v^2 - 0.5)) 
 */
public class Paraboloid extends SurfaceParametric {
	
	private RealParamAnimateable xRadius = new RealParamAnimateable("vmm.surface.parametric.Paraboloid.xRadius", 1, 1, 1.5);
	private RealParamAnimateable yRadius = new RealParamAnimateable("vmm.surface.parametric.Paraboloid.yRadius", 1, 1.5, 0.5);
	private RealParamAnimateable height = new RealParamAnimateable("vmm.surface.parametric.Paraboloid.height", 2.5, 2.5, 2.5);
	
	public Paraboloid() {
		umin.reset(0);
		umax.reset("2 * pi");
		vmin.reset(0);
		vmax.reset(1);
		addParameter(height);
		addParameter(yRadius);
		addParameter(xRadius);
		setDefaultWindow(-2, 2, -2, 2);
		setDefaultViewpoint(new Vector3D(20,3,7));
	}
	
	public Vector3D surfacePoint(double u, double v) {
		double x = xRadius.getValue() * v * Math.cos(u);
		double y = yRadius.getValue() * v * Math.sin(u);
		double z = height.getValue() * v * v - height.getValue()/2;
		return new Vector3D(x,y,z);
	}

}
