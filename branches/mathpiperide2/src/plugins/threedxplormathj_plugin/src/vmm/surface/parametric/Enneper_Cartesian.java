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
 * Defines an Enneper surface with a cartesian grid.
 * Gauss map is aa*z + bb*zz^2 + cc*z^3
 */
public class Enneper_Cartesian extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",0.6,0.6,0);
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",0,0,0);
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc",0,0,0.25);
	
	public Enneper_Cartesian() {
		uPatchCount.setValueAndDefault(24);
		vPatchCount.setValueAndDefault(24);
		umin.reset(-2);
		umax.reset(2);
		vmin.reset(-2);
		vmax.reset(2);
		setDefaultViewpoint(new Vector3D(-0.635,0.635,30));
		setDefaultWindow(-8,8,-8,8);
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
	}
	
	static private Complex AplusZW(double a, Complex z, Complex w) {
		     Complex A = new Complex(a,0);
        return A.plus(z.times(w));
	}   	
	
	public Vector3D surfacePoint(double u, double v) {
		
		double AA = aa.getValue();
		double BB = bb.getValue();
		double CC = cc.getValue();
		
		Complex z = new Complex(u,v);
		Complex z2 = new Complex(z.re*z.re - z.im*z.im,2*z.re*z.im);
		Complex zCube = new Complex(z.times(z2));
		
		Complex CChalf = new Complex(0.5*CC,0);
		Complex CCSquareOver7 = new Complex(CC*CC/7.0,0);
		
		Complex wh = new Complex();
		wh = z2.times(AplusZW(AA,z, AplusZW(BB/1.5,z, CChalf )));
		
		Complex w_g = new Complex();
		Complex wg = new Complex();

		w_g = z;
		wg = AplusZW((BB*BB+2*AA*CC)/5.0,z, AplusZW(BB*CC/3.0,z, CCSquareOver7) );
		wg = zCube.times( AplusZW(AA*AA/3.0,z, AplusZW(AA*BB/2.0,z, wg))) ;
		
		double x =  wg.re - w_g.re;
		double y =  -wg.im - w_g.im;
		double zz =  wh.re;          
		return new Vector3D(x,y,zz);
	}

}
