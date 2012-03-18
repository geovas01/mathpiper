/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core.Complex;
import vmm.core.IntegerParam;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core3D.ComplexVector3D;
import vmm.core3D.Vector3D;

public class CatenoidEnneper extends WeierstrassMinimalSurface {
	
	private IntegerParam exponent = new IntegerParam("vmm.surface.parametric.CatenoidEnneper.MainEx",2);
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.CatenoidEnneper.CoeffA",0.5,0,1.0);
	
	//ComplexVector3D[][] helperArray;
	//boolean needsValueArray = true;
	
	public CatenoidEnneper() {
		super();
		afp.reset(0,0,0);
		addParameter(aa);
		addParameter(exponent);
		setDefaultViewpoint(new Vector3D(-11,-17,12));
		setDefaultWindow(-4,4,-4.5,3.5);
			uPatchCount.setValueAndDefault(8);
			vPatchCount.setValueAndDefault(18);
			umin.reset(-2); 
			umax.reset(2);
			vmin.reset(0.0);
			vmax.reset(2*Math.PI);	
		canShowConjugateSurface = true;
	}

	private int ex;
	private double a,r1,r2;
	private Complex q1;
	
	// override the default Cartesian Grid
	protected Complex domainGrid(double u, double v){
		Complex z;
			double r = Math.exp(u);
			z = new Complex(r*Math.cos(v), r*Math.sin(v));
		return z;
	}
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		// return ((az)^ex + q1)*r1/z
		Complex w = new Complex(a*z.re, a*z.im);
		w.assignPow(w,ex);
		w.assignPlus(q1);
		w.assignTimes(r1);
		w.assignDivide(z);
		return w;
	}
	
	protected Complex hPrime(Complex z) {
		return gauss(z).times(r2);
	}
	
	protected Complex gaussTimesHPrime(Complex z){
		Complex w = gauss(z);
		w.assignTimes(w);
		return w.times(r2);
	}
	protected Complex gaussInverseTimesHPrime(Complex z) {
		return new Complex(r2,0);
	}

	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		ex = exponent.getValue();
		a  = aa.getValue();
		r1 = 1.0/(3+a*a);
		r2 = 0.4/(1+a*a*ex/2);     // controls the size
		q1 = new Complex(-3+a,0);  
	}
	
	protected ComplexVector3D getCenter(){
		return new ComplexVector3D(helperArray[(int)Math.floor(ucount/2)][(int)Math.floor(vcount/2)]);
	}
	   
}

