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
//import vmm.core.RealParamAnimateable;
import vmm.core3D.ComplexVector3D;
import vmm.core3D.Vector3D;

public class PlanarEnneper extends WeierstrassMinimalSurface {
	
	private IntegerParam exponent = new IntegerParam("vmm.surface.parametric.PlanarEnneper.MainEx",1);
	private IntegerParam only3 = new IntegerParam("vmm.surface.parametric.PlanarEnneper.Only3",2);
	//private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.PlanarEnneper.CoeffA",0.5,0,1.0);
	
	//ComplexVector3D[][] helperArray;
	//boolean needsValueArray = true;
	
	public PlanarEnneper() {
		super();
		afp.reset(0,0,0);
		//addParameter(aa);
		addParameter(only3);
		addParameter(exponent);
		setDefaultViewpoint(new Vector3D(9,-13,18));
		setDefaultWindow(-4,4,-4.5,3.5);
			uPatchCount.setValueAndDefault(8);
			vPatchCount.setValueAndDefault(18);
			umin.reset(-1.3); 
			umax.reset(0.25,0.15,0.45);
			vmin.reset(0.0);
			vmax.reset(2*Math.PI);	
		canShowConjugateSurface = true;
	}

	private int kW,d3;
	private double r1;

	protected Complex domainGrid(double u, double v){
		Complex z;
			double r = Math.exp(u);
			z = new Complex(r*Math.cos(v), r*Math.sin(v));
		return z;
	}
	
	protected Complex gauss(Complex z)  {
		Complex w = z.integerPower(kW);
		return w;
	}
	
	protected Complex hPrime(Complex z) {
		Complex w = z.integerPower(kW-d3);
		return w;
	}
	
	protected Complex gaussTimesHPrime(Complex z){
		Complex w = z.integerPower(2*kW-d3);
		return w;
	}
	protected Complex gaussInverseTimesHPrime(Complex z) {
		Complex w = z.integerPower(d3);
		return w.inverse();
	}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		kW = 1+exponent.getValue(); 
		d3 = only3.getValue();
		r1 = 1.0;    // controls the size
		ucount = 1+uPatchCount.getValue()*6;
		vcount = 1+vPatchCount.getValue()*6;
		helperArray = new ComplexVector3D [ucount][vcount];
		du = (umax.getValue() - umin.getValue())/(ucount-1);
		dv = (vmax.getValue() - vmin.getValue())/(vcount-1);
	}
	
	protected ComplexVector3D getCenter(){
		ComplexVector3D hA00 = new ComplexVector3D(helperArray[0][0]);
		hA00 = hA00.plus(helperArray[0][(int)Math.floor(vcount-1)]).times(0.5);
		hA00 = hA00.plus(helperArray[0][(int)Math.floor(vcount/2)]).times(0.5);
		return hA00;
		}
	   
}

