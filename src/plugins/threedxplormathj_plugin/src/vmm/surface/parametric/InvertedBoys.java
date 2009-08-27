/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core.Complex;
import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;


/**
 * Defines an Inverted Boy's surface.
 */
public class InvertedBoys extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","0","0","pi/2");  //The Associate Family parameter.
	
	public InvertedBoys() {
		uPatchCount.setValueAndDefault(8);
		vPatchCount.setValueAndDefault(34);
		umin.reset(Math.log(0.1));
		umax.reset(Math.log(0.57));
		vmin.reset(0.0);
		vmax.reset("2*pi");
		setDefaultViewpoint(new Vector3D(0,0,17.3));
		setDefaultWindow(-1.25,1.25,-1.25,1.25);
		addParameter(aa);
	}
	
	public Vector3D surfacePoint(double u, double v) {
		double afp = aa.getValue();
		double COSafp = Math.cos(afp);
		double SINafp = Math.sin(afp);
		Complex One_C = new Complex(1.0,0.0);
		Complex I_C = new Complex(0.0,1.0);
		Complex a = new Complex(Math.sqrt(5),0);
		Complex b = new Complex(0.0,0.66666667);
		Complex z = new Complex(Math.exp(u)*Math.cos(v),Math.exp(u)*Math.sin(v));
		Complex zSquare = new Complex();
		zSquare = z.times(z);
		Complex zCube = new Complex();
		zCube = z.times(zSquare);
		Complex OneOverZsquare = new Complex();
		OneOverZsquare = One_C.dividedBy(zSquare);
		Complex OneOverZCube = new Complex();
		OneOverZCube = One_C.dividedBy(zCube);
		Complex zSquarePlusOneOverZsquare = new Complex();
		zSquarePlusOneOverZsquare = zSquare.plus(OneOverZsquare);
		Complex zSquareMinusOneOverZsquare= new Complex();
		zSquareMinusOneOverZsquare = zSquare.minus(OneOverZsquare);
		Complex zCubePlusOneOverZCube = new Complex();
		zCubePlusOneOverZCube = zCube.plus(OneOverZCube);
		Complex zCubeMinusOneOverZCube = new Complex();
		zCubeMinusOneOverZCube = zCube.minus(OneOverZCube);
		Complex multiplier = new Complex();
		multiplier = One_C.dividedBy(zCubeMinusOneOverZCube.plus(a));
		Complex Wx = new Complex(); 
		Wx = I_C.times(zSquareMinusOneOverZsquare);
		Wx = multiplier.times(Wx);
		Complex Wy = new Complex(); 
		Wy = multiplier.times(zSquarePlusOneOverZsquare);
		Complex Wz = new Complex();
		Wz = b.times(zCubePlusOneOverZCube);
		Wz = multiplier.times(Wz);
		double x  = COSafp * Wx.re + SINafp * Wx.im;
		double y  = COSafp * Wy.re + SINafp * Wy.im;
		double zz = COSafp * Wz.re + SINafp * Wz.im;
		return new Vector3D(x,y,zz);
	}

}
