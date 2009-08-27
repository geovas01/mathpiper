/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import java.awt.Color;
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

public class Riemann extends WeierstrassMinimalSurface {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.Riemann.aa",1,-2,2);
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public Riemann() {
		super();
		addParameter(aa);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		setDefaultViewpoint(new Vector3D(50,-20, 1));
		setDefaultViewUp(new Vector3D(0.3,0.85,0.5));
		setDefaultWindow(-7,7.7,-5.8,6.3);
	/*	setDefaultViewUp(new Vector3D(-0.155, 0.9, 0.4));
		setDefaultViewpoint(new Vector3D(-35.25, -19.5, -30.5));
		setDefaultWindow(-9.75, 10,-8.5, 8.5);*/
			uPatchCount.setValueAndDefault(12);
			vPatchCount.setValueAndDefault(24);
			umin.reset(0);
			umax.reset(Math.PI);
			vmax.reset(3.0);
			vmin.reset(-vmax.getValue()); 
		removeParameter(vmin);
		removeParameter(umax);
		removeParameter(umin);
		multipleCopyOptions = new int[]{2,3};
		canShowConjugateSurface = true;
		wantsToSeeDomain = false;
		wantsToSeeGaussImage = false;//true;//
		if (wantsToSeeGaussImage)  wantsToSeeDomain = true;
		if (wantsToSeeDomain)		 {setDefaultViewUp(new Vector3D(0,0,1));
	  	                           setDefaultViewpoint(new Vector3D(0,0,40));}
	}
	public View getDefaultView() {
		//SurfaceView view = (SurfaceView)super.getDefaultView();  // its actually a WMSView
		WMSView view = new WMSView();
		view.setGridSpacing(12);
		float c0 = (float)0.34;
		view.getLightSettings().setLight0(new Color(c0,c0,c0));
		view.getLightSettings().getDirectionalLight2().setItsColor(new Color((float)0.5,(float)1.0,(float)0.0));
		view.getLightSettings().getDirectionalLight2().setItsDirection (new Vector3D(0.66,-0.34,-0.66));
		view.getLightSettings().getDirectionalLight3().setItsDirection (new Vector3D(0.57,0.2,-0.8));
		view.getLightSettings ().setSpecularExponent(55);
		view.getLightSettings().setSpecularRatio( (float)0.5 );
		return view;
	}

	private int jP,um,vm;
	private double P,PL,r1;
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		     super.parameterChanged(param, oldValue, newValue);
		     if (param == vmax)  vmin.reset(-vmax.getValue());	
	}
	
	protected void createData() {
        super.createData();     // helperArray created
        data.discardGridTransforms();
        GridTransformMatrix[] trList = new GridTransformMatrix[12];
				trList[0] = new GridTransformMatrix();
		if ((!inAssociateMorph)&&(!wantsToSeeDomain)	) {
        	trList[1] = new GridTransformMatrix().scale(-1,1,-1).setReverseSurfaceOrientation(true);
        data.addGridTransform(trList[1]);
        trList[2] = new GridTransformMatrix().scale(1,-1,1);
        data.addGridTransform(trList[2]);
        trList[3] = new GridTransformMatrix().scale(-1,-1,-1).setReverseSurfaceOrientation(true);
        data.addGridTransform(trList[3]);

		if ((getNumberOfPieces()==2)||(getNumberOfPieces()==3)) {
			for (int e=0; e < 4; e++){
				if (flag0)
				trList[e+4] = new GridTransformMatrix(trList[e]).translate(2*halfPeriod.x.re,0,2*halfPeriod.z.re);
				else if (flag05)
				trList[e+4] = new GridTransformMatrix(trList[e]).translate(2*halfPeriod.x.im,0,2*halfPeriod.z.im);
				data.addGridTransform(trList[e+4]);
			}
		}
		if (getNumberOfPieces()==3) {
			for (int e=0; e < 4; e++){
				if (flag0)
				trList[e+8] = new GridTransformMatrix(trList[e]).translate(-2*halfPeriod.x.re,0,-2*halfPeriod.z.re);
				else if (flag05)
				trList[e+8] = new GridTransformMatrix(trList[e]).translate(-2*halfPeriod.x.im,0,-2*halfPeriod.z.im);				
				data.addGridTransform(trList[e+8]);
			}
		}
		}
	}
	
	// override the default Cartesian Grid
	protected Complex domainGrid(double u, double v){
		// return z = exp(v+i*u)
		double deps = 1.0/32.0;  
		// This detour parameter should not be too small since the inegration path uses the detour
		Complex z;
			double r = Math.exp(v);
			double ph = u;
			if ((u < umin.getValue()+deps)&&(Math.abs(v-PL) < deps))
				ph = u + (deps-Math.abs(v-PL))*((-u+deps + umin.getValue())/deps );
			else if ((u > umax.getValue()-deps)&&(Math.abs(v+PL) < deps))
				ph = u - (deps-Math.abs(v+PL))*((u+deps - umax.getValue())/deps );
			// Detours around branch points
			z = new Complex(r*Math.cos(ph), r*Math.sin(ph));
			if (wantsToSeeGaussImage) z = hPrime(z);
		    return z;
	}
//	Index of grid point closest to branch value P from below
	protected void p_Index(){
		jP = (int) Math.floor((PL-vmin.getValue())/(vmax.getValue()-vmin.getValue())*(vcount-1));
	}
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		// return z = Weierstrass-p
		return new Complex(z);		
	    }
	
	protected Complex hPrime(Complex z) {
		// return 1/weierstrassPrime=1/(z*sqrt(1/z-z+P-1/P))
		double denom= z.re*z.re+z.im*z.im;
		Complex w1 = new Complex(z.re/denom - z.re + P - 1/P, -z.im*(1/denom + 1));
		Complex w = w1.squareRootNearer(I2_C);
		        w.assignTimes(z);
		        w.assignInvert();
		        w.assignTimes(r1);
		return w;
		}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		PL  = aa.getValue();
		P  = Math.exp(PL);
		r1 = 0.5;
		p_Index(); // needs dv
		// System.out.println(jP);
		um = (int) Math.floor((ucount-1)/2.0);
		vm = (int) Math.floor((vcount-1)/2.0);
	}
	
	/**
	 * We want to center the surface already at the helper Level. We cannot use the symmetry
	 * lines of the surface and therefore need to integrate towards the image of P.
	 */
	protected ComplexVector3D getCenter(){
        if (inAssociateMorph)	 {  // Keeps the saddle fixed
        		return new ComplexVector3D(helperArray[um][vm]);
        }
        else if (wantsToSeeDomain)  
        	        return new ComplexVector3D(helperArray[0][0].plus(helperArray[ucount-1][0]).times(0.5));  
            //  return new ComplexVector3D(ZERO_C,ZERO_C,ZERO_C);
		else {
			ComplexVector3D gC;
			ComplexVector3D Mearly = new ComplexVector3D(helperToMinimal(helperArray[0][jP+19]));
			ComplexVector3D Mlate = new ComplexVector3D(helperToMinimal(helperArray[0][jP-19]));
			Vector3D MinEarlyRE = new Vector3D(Mearly.re());
			Vector3D MinEarlyIM = new Vector3D(Mearly.im());
			Vector3D MinLateRE = new Vector3D(Mlate.re());
			Vector3D MinLateIM = new Vector3D(Mlate.im());
			Vector3D MinBranchRE = new Vector3D(MinEarlyRE.x, MinLateRE.y, MinEarlyRE.z);
			Vector3D MinBranchIM = new Vector3D(MinLateIM.x, MinEarlyIM.y, MinLateIM.z);
			gC = new ComplexVector3D(MinBranchRE, MinBranchIM);
			gC = minimalToHelper(gC);
			return gC;
		}
	}
	
	public void computeHalfPeriod(){
		halfPeriod = new ComplexVector3D(helperToMinimal(helperArray[um][vm].times(2.0)));
		// This is the point with the symmetry normal, no numerical problem here.
        halfPeriod.y = ZERO_C;
	}
	
	/**
	 * Override to close the hole around the images of P, -1/P.
	 */
	public Vector3D surfacePoint(double u, double v) {
		ComplexVector3D eW,auxW;
		int i = (int) Math.floor(0.25 + (u-umin.getValue())/du );
		int j = (int) Math.floor(0.25 + (v-vmin.getValue())/dv );
			//Complex zInitial = domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv);
			//Complex z = domainGrid(u,v);		
		if ((!inAssociateMorph)&&(!wantsToSeeDomain)&&(i==0)&&(j==jP))
			eW = new ComplexVector3D(ComplexVector3D.ORIGIN);
		else if ((!inAssociateMorph)&&(!wantsToSeeDomain)&&(i==ucount-1)&&(j==vcount-1-jP)) {
			eW = new ComplexVector3D(halfPeriod);
		}
		else 
		{
			auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));	
			eW = helperToMinimal(auxW);
		  if((!inAssociateMorph)&&(!wantsToSeeDomain)) {  // Correct the detour-hole
			if (flag0){
				if ((((i==0)&&(j <= jP))||((i==ucount-1)&&(j >= vcount-1-jP)))) {eW.y.assign(ZERO_C);}
				if ((i==0)&&(j > jP))                              {eW.assign(ZERO_C, eW.y, ZERO_C); }
				if ((i==ucount-1)&&(j < vcount-1-jP))  {eW.assign(halfPeriod.x, eW.y, halfPeriod.z); } 
			           } //if (flag0)
			if (flag05){
				if ((((i==0)&&(j > jP))||((i==ucount-1)&&(j < vcount-1-jP))))  {eW.y.assign(ZERO_C);}	
				if ((i==0)&&(j < jP))                             {eW.assign(ZERO_C, eW.y, ZERO_C); }
			           } // if (flag05)
		  } // !wantsToSeeDomain
		}// else
		if (AFP==0)
			{if (wantsToSeeDomain)     return new Vector3D(eW.z.re, eW.z.im, 0);
			 else                      return eW.re(); 
			} else
				return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));     
	}  
	   
}


