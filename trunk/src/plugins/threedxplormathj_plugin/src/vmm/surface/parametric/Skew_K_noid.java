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
import vmm.surface.SurfaceView;
import vmm.surface.parametric.WeierstrassMinimalSurface.WMSView;

public class Skew_K_noid extends WeierstrassMinimalSurface {
	
	private IntegerParam exponent = new IntegerParam("vmm.surface.parametric.Skew_K_noid.MainEx",2);
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.Skew_K_noid.aa",0.4,0,0.93);
	
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public Skew_K_noid() {
		super();
		afp.reset(0,0,0);
		addParameter(aa);
		aa.setMaximumValueForInput(0.95);
		aa.setMinimumValueForInput(0.0);
		addParameter(exponent);
		exponent.setMinimumValueForInput(2); 
		setDefaultViewpoint(new Vector3D(-2,14,-50));
		setDefaultOrientation(View3DLit.REVERSE_ORIENTATION);
		setDefaultWindow(-4,4,-3,3);
			uPatchCount.setValueAndDefault(12);
			vPatchCount.setValueAndDefault(20);
			umin.reset(-2.5); 
			umax.reset(0.0);
			vmin.reset(-Math.PI/2);
			vmax.reset(Math.PI/2);	
		umin.setMaximumValueForInput(-0.05);
		removeParameter(umax);
		removeParameter(vmax);
		removeParameter(vmin);
		canShowConjugateSurface = true;
	}
	public View getDefaultView() {
		//SurfaceView view = (SurfaceView)super.getDefaultView();  // its actually a WMSView
		WMSView view = new WMSView();
		view.setGridSpacing(12);
		return view;
	}

	private int ex,e_odd,j0;
	private double AA,r1,rex,re_e,mob;
	private Complex q1;

	protected void createData() {
		super.createData();
        data.discardGridTransforms();
        if (!inAssociateMorph) {
            GridTransformMatrix[] trList = new GridTransformMatrix[ex*4];
        	trList[0] = new GridTransformMatrix();
        	if (flag0){
        	trList[1] = new GridTransformMatrix().scale(1,-1,1); // reflect in x-z-plane
        data.addGridTransform(trList[1]);
        	}  else if (flag05)  {
        		trList[1] = new GridTransformMatrix().scale(-1,1,-1).reverse(); // rotate around y-axis
                data.addGridTransform(trList[1]);
        	}
        if ((flag0)||(flag05))
        for (int e = 1; e < ex; e++){
        		trList[2*e] = new GridTransformMatrix(trList[2*e-2]).rotateZ(360.0/ex);
        		data.addGridTransform(trList[2*e]);
        		trList[2*e+1] = new GridTransformMatrix(trList[2*e-1]).rotateZ(360.0/ex);
        		data.addGridTransform(trList[2*e+1]);
        }
        if (flag0)
        for (int e = 0; e < 2*ex; e++){
        			trList[2*ex +e] = new GridTransformMatrix(trList[e]).scale(1,1,-1);
        			data.addGridTransform(trList[2*ex+e]);
        		} 
        }
	}	
 	
	/**
	 * This grid is precisely adapted to the symmetry lines and ends of the surface
	 */
	protected Complex domainGrid(double u, double v){
		// return ( (i*(-z+1)/(z+1)).mobius1_1(mob) )^(1/ex), z = exp(u+i*v)
		Complex z,aux,w;
			double r = Math.exp(u);
			z = new Complex(r*Math.cos(v), r*Math.sin(v));
			w = new Complex(-z.re + 1, -z.im);
			aux = new Complex(z.im, -z.re -1);
		    w.assignDivide(aux);  // Polar centers at +i,-i
		    w = w.mobius1_1(mob); // Polar centers at ck+i*sk, ck-i*sk
		    z=w.logNearer(I_C);
		    z.assignTimes(1.0/ex);
		    return z.exponential();// Polar centers at the punctures of the Weierstrass data
	}
	// Index of grid point closest to zero
	protected void zeroIndex(){
		double aux;
		double test = 10;
		for (int j = 0; j < vcount ; j++){
			aux = domainGrid(umax.getValue(),vmin.getValue() +j*dv).r();
			if (aux <= test) {
				test = aux; j0=j;
			}
		}
	}
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		// return z^(ex-1)*(z*ex - AA^ex)/(1-AA^ex*z^ex)
		Complex w;
		Complex wk = z.integerPower(ex-1);
		         w = z.times(wk);
		Complex aux = w.times(-rex);
                aux.re = aux.re +1;
		        w.re = +w.re - rex;
		        w = wk.times(w).dividedBy(aux);
		        return w;
	}
	
	protected Complex hPrime(Complex z) {
		// return z^(ex-1)*(z^ex - re_e*(z^(2ex) +1)/(z^(2ex) + 1 - 4re_e*z^ex)^2
		Complex wk = z.integerPower(ex-1);
		Complex w = z.times(wk);
		Complex w2= w.times(w);
		        w2.re = w2.re + 1;
		Complex aux = w2.times(-re_e);
		        aux.assignPlus(w);
		        aux.assignTimes(wk); // numerator
		        w2.re = w2.re - 4*re_e*w.re;
		        w2.im = w2.im - 4*re_e*w.im;
		        w2.assignTimes(w2);
		        w2.assignTimes(r1);  // denominator
		        return aux.dividedBy(w2);
	}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		double ck,sk;
		AA  = Math.sqrt(aa.getValue());
		ex = exponent.getValue();
			rex = Math.pow(AA,ex);
			re_e = rex/(rex*rex + 1);
			r1 = 1/(1- AA*AA*AA*AA)*Math.sqrt(2.0/ex);
			ck = 2*re_e;
			sk = Math.sqrt(1-ck*ck);
			mob= Math.sqrt((1.0-sk)/(1.0-ck)/2.0); 
			// mob is the Mobius-parameter so that the polar center at I_C is mapped
			// to ck+i*sk, and then with the ex-root to the poles of hPrime.
		zeroIndex(); // needs dv
		//System.out.println(j0);
	}
	
	/**
	 * We want to center the surface already at the helper Level. We cannot use the symmetry
	 * lines of the surface and therefore need to integrate towards the image of ZERO_C.
	 */
	protected ComplexVector3D getCenter(){
		ComplexVector3D gC = new ComplexVector3D(helperArray[ucount-1][j0]);
		gC = gC.plus(ComplexVectorOneStepIntegrator(domainGrid(umax.getValue(),vmin.getValue()+j0*dv ), del));
        if (inAssociateMorph)	 {  // Keeps the saddle fixed
		}
		else
			gC.z = new Complex(helperArray[ucount-1][0].z); // the third component of helper is also the surface coordinate	
        return gC;
	}
	
	/**
	 * Override to close the hole around the image of ZERO_C.
	 */
	public Vector3D surfacePoint(double u, double v) {
		int i = (int) Math.floor(0.25 + (u-umin.getValue())/du );
		int j = (int) Math.floor(0.25 + (v-vmin.getValue())/dv );
			//Complex zInitial = domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv);
			//Complex z = domainGrid(u,v);
		ComplexVector3D auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));
		ComplexVector3D eW = new ComplexVector3D(auxW.y.minus(auxW.x), (auxW.y.plus(auxW.x)).times(I_C), auxW.z ) ;
		if ((i==ucount-1)&&(j==j0))
			eW = new ComplexVector3D(ZERO_C,ZERO_C,eW.z);
		if (AFP==0)
			return eW.re();
		else
			return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));    
	}
	
	
	public void createIntegrationGrid(){
		double u = umin.getValue();
		double v = vmin.getValue();
	}
	   
}

