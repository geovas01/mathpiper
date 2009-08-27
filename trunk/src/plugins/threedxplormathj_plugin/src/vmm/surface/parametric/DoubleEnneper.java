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

public class DoubleEnneper extends WeierstrassMinimalSurface {
	
	private IntegerParam exponent = new IntegerParam("vmm.surface.parametric.DoubleEnneper.MainEx",8);
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.DoubleEnneper.aa",4.0,4.0,4.0);
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.surface.parametric.DoubleEnneper.bb",0.1,0,1.0);
	
	//ComplexVector3D[][] helperArray;
	//boolean needsValueArray = true;
	double uminmax = 1.5;
	
	public DoubleEnneper() {
		super();
		afp.reset(0,0,0);
		addParameter(bb);
		addParameter(aa);
		//addParameter(only3);
		addParameter(exponent);
		setDefaultViewpoint(new Vector3D(40,-50,40));
		setDefaultWindow(-9,9,-7,7);
			uPatchCount.setValueAndDefault(8);
			vPatchCount.setValueAndDefault(18);
			umin.reset(-uminmax); 
			umax.reset(uminmax);
			vmin.reset(0.0);
			vmax.reset(2*Math.PI);	
		canShowConjugateSurface = true;
	}

	private int ee;
	private double AA,BE;
	private Complex q1,q2,q3,q1q2;
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		     super.parameterChanged(param, oldValue, newValue);
		     AFP = afp.getValue();
		     if (param != afp){
		    	 needsValueArray = true;
		     if (param == exponent){
		     uminmax=2.0-exponent.getValue()/16.0;
		     umin.reset(-uminmax); 
			 umax.reset(uminmax);
		     }}
	}
	
	protected Complex domainGrid(double u, double v){
		Complex z;
			double r = Math.exp(u);
			z = new Complex(r*Math.cos(v), r*Math.sin(v));
		return z;
	}
	
	protected Complex gauss(Complex z)  {
		Complex w = z.integerPower(ee);
		Complex aux = q2.times(w);
		        aux.assignMinus(q1);
		        aux.assignTimes(w); // P1*z^ee
		Complex auy = q1.times(w);
		        auy.assignMinus(q2); 
		        auy.assignTimes(z); // P2*z
		return aux.dividedBy(auy);
	}
	
	protected Complex hPrime(Complex z) {
		Complex w = z.integerPower(ee);
		Complex aux = w.minus(q1q2);
		        aux.assignTimes(w);
		        aux.re = aux.re +1;
		Complex auy = q3.times(w);
		        auy.assignTimes(z);
		return aux.dividedBy(auy);
	}

	/* No useful cancellation in g*dh or 1/g*dh */
	
	protected void redoConstants(){
		double r;
		// The following constants are reevaluated for each surface once; thread safe
		super.redoConstants();
		ee = exponent.getValue(); 
		BE = 2*Math.PI*bb.getValue()/ee;
		AA = Math.max(1.1, aa.getValue());
		q1 = new Complex(Math.sqrt(AA)*Math.cos(BE/2), Math.sqrt(AA)*Math.sin(BE/2));
		q1 = q1.integerPower(ee);
		q2 = q1.inverse();
		q1q2 = q1.integerPower(2).plus(q2.integerPower(2));
		r = Math.exp(2*ee*Math.log(AA));
		r = (r-1)/(r+1);
		q3 = new Complex(Math.cos(ee*BE), r*Math.sin(ee*BE)); // no residue
		// The following controls the surface size for ee between 2 and 9
		q3 = q3.times(Math.sqrt(AA*AA + 1/AA/AA)*Math.exp(ee)*(ee+1)/(5.5+1.0/ee)*Math.sqrt(ee));
	}
	
	protected ComplexVector3D getCenter(){
		int um = (int) Math.floor((ucount-1)/2.0);
		int vm = (int) Math.floor((vcount-1)/2.0);
		ComplexVector3D hA00 = new ComplexVector3D(helperArray[um][0]);
		return hA00.plus(helperArray[um][vm]).times(0.5);
	}
	   
}

