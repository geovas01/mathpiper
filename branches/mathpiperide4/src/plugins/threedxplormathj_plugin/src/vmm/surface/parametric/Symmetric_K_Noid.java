/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.core.BasicAnimator;
import vmm.core.Complex;
import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core.View;
import vmm.core3D.ComplexVector3D;
import vmm.core3D.GridTransformMatrix;
import vmm.core3D.Vector3D;
import vmm.core3D.View3DLit;

public class Symmetric_K_Noid extends WeierstrassMinimalSurface {
	
	private IntegerParam exponent = new IntegerParam("vmm.surface.parametric.Symmetric_K_Noid.MainEx",6);
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.Symmetric_K_Noid.aa",0,0,1);
	
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public Symmetric_K_Noid() {
		super();
		addParameter(aa);
		aa.setMinimumValueForInput(0);
		addParameter(exponent);
		exponent.setMinimumValueForInput(3);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		setDefaultViewpoint(new Vector3D(-26,10,-43));
		setDefaultWindow(-7,7,-6,6);
			uPatchCount.setValueAndDefault(24);
			vPatchCount.setValueAndDefault(8);
			umin.reset(-2.5); 
			umax.reset(2.5);
			vmin.reset(-Math.PI/2);
			vmax.reset(0);	
		removeParameter(vmax);
		removeParameter(vmin);
		canShowConjugateSurface = true;
	}

	private int ex,ex_,e_odd;
	private double AA,r1,rex,re_e;
	private Complex q1;
	
	
	protected void createData() {
		super.createData();
        data.discardGridTransforms();
        if (!inAssociateMorph) {
            GridTransformMatrix[] trList = new GridTransformMatrix[ex*2];
        	trList[0] = new GridTransformMatrix();
        	if (flag0){
        	trList[1] = new GridTransformMatrix().scale(1,-1,1); // reflect in x-z-plane
        data.addGridTransform(trList[1]);
        	}  else if (flag05)  {
        		trList[1] = new GridTransformMatrix().scale(-1,1,-1).reverse(); // rotate around y-axis
                data.addGridTransform(trList[1]);
        	}
        if ((flag0)||(flag05))
        for (int e = 2; e < ex; e++){
        		trList[e] = new GridTransformMatrix(trList[e-2]).rotateZ(-720.0/ex);
        		data.addGridTransform(trList[e]);
        }
        if (flag0)
        for (int e = 0; e < ex; e++){
        			trList[ex +e] = new GridTransformMatrix(trList[e]).scale(1,1,-1);
        			data.addGridTransform(trList[ex+e]);
        		}
        }
	}
	
	// override the default Cartesian Grid
	protected Complex domainGrid(double u, double v){
		// return ( (-z+1)/(z+1) )^(1/ex), z = exp(u+i*v)
		Complex z,aux,w;
			double r = Math.exp(u);
			z = new Complex(r*Math.cos(v), r*Math.sin(v));
			w = new Complex(-z.re + 1, -z.im);
			aux = new Complex(z.re +1, z.im);
		    w = w.dividedBy(aux);
		    z=w.logNearer(I_C);
		    		z.assignTimes(2.0/ex);
		    return z.exponential();
	}
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		// return z^(ex-1)*(z*ex - AA^ex)/(1-AA^ex*z^ex)
		Complex w;
		if (e_odd > 0){
			return z.integerPower(e_odd);
			//return ONE_C;  // to see the domain
		}
		else{
		Complex wk = z.integerPower(ex_-1);
		         w = z.times(wk);
		Complex aux = w.times(-rex);
                aux.re = aux.re +1;
		        w.re = w.re - rex;
		        w = wk.times(w).dividedBy(aux);
		        return w;
		}		
	}
	
	protected Complex hPrime(Complex z) {
		// return z^(ex-1)*(z^ex - re_e*(z^(2ex) +1)/(z^(2ex) + 1 - 4re_e*z^ex)^2
		Complex w,w2;
		if (e_odd > 0){    // z^(ex-1)/(z^ex-1)^2
			w = z.integerPower(ex-1);
			w2= z.times(w);
			w2.re = w2.re -1;
			w2.assignTimes(w2);
			return w.dividedBy(w2.times(2.0/exponent.getValue()));
			//return ONE_C;    // to see the domain
		}
		else{
		Complex wk = z.integerPower(ex_-1);
		        w = z.times(wk);
		        w2= w.times(w);
		        w2.re = w2.re + 1;
		Complex aux = w2.times(-re_e);
		        aux.assignPlus(w);
		        aux.assignTimes(wk); // numerator
		        w2.re = w2.re - 2;
		        w2.assignTimes(w2);
		        return aux.dividedBy(w2.times(2.0/exponent.getValue()));
		}
	}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		AA  = Math.sqrt(aa.getValue());
		ex = exponent.getValue();
		e_odd = 0;
		if ((ex/2.0 - Math.floor(ex/2.0))==0.5){
			e_odd = ex-1;
			rex = 0;
		}
		else {
			ex_ = (int) Math.floor(ex/2.0);
			rex = Math.pow(AA,ex_);
			re_e = rex/(rex*rex + 1);	
		}
	}
	
	protected ComplexVector3D getCenter(){
		ComplexVector3D gC = new ComplexVector3D(helperArray[(int)Math.floor(ucount/2)][vcount-1]);
		gC.z = new Complex((helperArray[0][0].z.plus(helperArray[ucount-1][0].z)).times(0.5)); 
		// the third component of helper is also the surface coordinate
		return gC;
	}
	   
}


