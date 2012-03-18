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
 * Defines an Kusner Dihedral Symmetric surface.
 */
public class Kusner_Dihedral_Symmetric extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","0","0","pi / 2");
    private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb",4,4,4);
	
	public Kusner_Dihedral_Symmetric() {
		uPatchCount.setValueAndDefault(8);
		vPatchCount.setValueAndDefault(34);
		setDefaultViewpoint(new Vector3D(0,0,17.3));
		setDefaultWindow(-1.25,1.25,-1.25,1.25);
		addParameter(aa);
		addParameter(bb);
		//double BB = bb.getValue();
		umin.reset(Math.log(0.002));
		umax.reset(-0.45);
		vmin.reset(0.0);
		vmax.reset("2*pi");
		addParameter(aa);
		addParameter(bb);
	}
	
	static private Complex Pow(Complex z,int k) {
		 Complex w = new Complex(z);
		   for (int i=2;i<=k;i++)
			   w = w.times(z);
		  return w;
	}
	
	public Vector3D surfacePoint(double u, double v) {
		double afp = aa.getValue();
		double BB = bb.getValue();
		int Pp = Math.max(Math.round((int)BB),2);
		int Qq = Pp - 1;
		Complex a = new Complex(2*Math.sqrt(2*Pp - 1)/Qq,0);
		Complex b = new Complex(0.0,(double)Qq/Pp);
		double COSafp = Math.cos(afp);
		double SINafp = Math.sin(afp);
		Complex One_C = new Complex(1.0,0.0);
		Complex I_C = new Complex(0.0,1.0);
		Complex z = new Complex(Math.exp(u)*Math.cos(v),Math.exp(u)*Math.sin(v));
		
		Complex temp1 = new Complex();
		Complex temp2 = new Complex();
		temp1 = Pow(z,Qq);                      //  z^(Pp - 1) 
		temp2 = One_C.dividedBy(temp1);         //  1/z^(Pp-1) 
		
		Complex zToQqPlusOneOverZToQq = new Complex();   
		Complex zToQqMinusOneOverZToQq = new Complex();
		
		zToQqPlusOneOverZToQq = temp1.plus(temp2);    //  z^(Pp-1) + 1/ z^(Pp-1)  
		zToQqMinusOneOverZToQq = temp1.minus(temp2);  //  z^(Pp-1) - 1/ z^(Pp-1) 
		
		temp1 = temp1.times(z);                     //  z^Pp 
		temp2 = temp2.dividedBy(z);                 //  1/z^Pp 
		
		
		Complex zToPpPlusOneOverZToPp = new Complex();
		Complex zToPpMinusOneOverZToPp = new Complex();
		
		zToPpPlusOneOverZToPp = temp1.plus(temp2);     //  z^Pp + 1/z^Pp 
		zToPpMinusOneOverZToPp = temp1.minus(temp2);   //  z^Pp - 1/z^Pp 
		
		Complex multiplier = new Complex();
		multiplier = One_C.dividedBy(zToPpMinusOneOverZToPp.plus(a));  // 1/(z^Pp - 1/z^Pp + a) 
		
		
		Complex Wx = new Complex(); 
		Wx = I_C.times(zToQqMinusOneOverZToQq);
		Wx = multiplier.times(Wx);
		
		Complex Wy = new Complex(); 
		Wy = multiplier.times(zToQqPlusOneOverZToQq);
		
		Complex Wz = new Complex();
		Wz = b.times(zToPpPlusOneOverZToPp);
		Wz = multiplier.times(Wz);
		
		double x  = COSafp * Wx.re + SINafp * Wx.im;
		double y  = COSafp * Wy.re + SINafp * Wy.im;
		double zz = COSafp * Wz.re + SINafp * Wz.im;
		return new Vector3D(x,y,zz);
	}

}
