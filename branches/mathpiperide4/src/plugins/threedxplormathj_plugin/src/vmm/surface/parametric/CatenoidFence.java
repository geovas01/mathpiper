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

public class CatenoidFence extends WeierstrassMinimalSurface {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.CatenoidFence.aa",0,-2,2);
	private IntegerParam pieces = new IntegerParam("vmm.surface.parametric.CatenoidFence.pieces",1);
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public CatenoidFence() {
		super();
		addParameter(pieces);
		addParameter(aa);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		setDefaultViewpoint(new Vector3D(25, 43, -10));
		setDefaultViewUp(new Vector3D(0.1,-0.3,-1));
		setDefaultWindow(-7.5,7.5,-6,6);
			uPatchCount.setValueAndDefault(18);
			vPatchCount.setValueAndDefault(18);
			vmax.reset(3.0);
			vmin.reset(-vmax.getValue()); 
			umin.reset(-Math.PI+0.0001);
			umax.reset(+Math.PI-0.0001);
		removeParameter(vmin);
		removeParameter(umax);
		removeParameter(umin);
		wantsToSeeDomain = false;
		if (wantsToSeeDomain)		 {setDefaultViewUp(new Vector3D(0,0,1));
        						  	  setDefaultViewpoint(new Vector3D(0,0,40));}
		multipleCopyOptions = new int[] {2,3};  // indicates that we can show twice the usual number of copies of fundamntal piece
		canShowConjugateSurface = true;
	}
	public View getDefaultView() {
		WMSView view = new WMSView();
		view.setGridSpacing(6);
	/*	float c0 = (float)0.34;
		view.getLightSettings().setLight0(new Color(c0,c0,c0));
		view.getLightSettings().getDirectionalLight2().setItsColor(new Color((float)0.5,(float)1.0,(float)0.0));
		view.getLightSettings().getDirectionalLight2().setItsDirection (new Vector3D(0.66,-0.34,-0.66));
		view.getLightSettings().getDirectionalLight3().setItsDirection (new Vector3D(0.57,0.2,-0.8));
		view.getLightSettings ().setSpecularExponent(55);
		view.getLightSettings().setSpecularRatio( (float)0.5 ); */
		return view;
	}

	private int jP,um,vm;
	double amp = 0.04; // for domainGrid
	private double P,PL,r1,R,R_,PR;
	private Complex qP;
	private ComplexVector3D halfPeriodII;
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		     super.parameterChanged(param, oldValue, newValue);
		     AFP = afp.getValue();
		     if (param != afp)   needsValueArray = true;
		     if (param == vmax)  vmin.reset(-vmax.getValue());	
	}
	
	protected void createData() {
        super.createData();     // helperArray created
        data.discardGridTransforms();
        GridTransformMatrix[] trList = new GridTransformMatrix[12];
				trList[0] = new GridTransformMatrix();
		if ((!inAssociateMorph)&&(!wantsToSeeDomain) ) {
		if (flag0){
        	trList[1] = new GridTransformMatrix().scale(1,-1,1).translate(0,2*halfPeriod.y.re,0);
        data.addGridTransform(trList[1]);  }
		if (flag05){
        	trList[1] = new GridTransformMatrix().scale(-1,1,-1).translate(0,0,2*halfPeriod.z.im);
        data.addGridTransform(trList[1]);  }
		if ((getNumberOfPieces()==2)||(getNumberOfPieces()==3)) {
			for (int e=0; e < 2; e++){
				if (flag0){
				trList[e+2] = new GridTransformMatrix(trList[e]).translate(2*halfPeriodII.x.re,0,0);
				data.addGridTransform(trList[e+2]);
				}
				if (flag05){
					trList[e+2] = new GridTransformMatrix(trList[e]).translate(0,0,-4*halfPeriod.z.im);
					data.addGridTransform(trList[e+2]);
				}
			}}
		if ((getNumberOfPieces()==3)&&(flag0)) {
			for (int e=0; e < 2; e++){
					trList[e+4] = new GridTransformMatrix(trList[e]).translate(-2*halfPeriodII.x.re,0,0);
					data.addGridTransform(trList[e+4]);
				}
			}
		}
	}
	
	// override the default Cartesian Grid
	protected Complex domainGrid(double u, double v){
		// return z = exp(v+i*u)
		Complex z;	
		double r = R*Math.exp(v);  // Not the unit circle but the circle of radius R is the horizontal symmetry line.
		z = new Complex(r*Math.cos(u)+P, r*Math.sin(u));
		// Detours around branch points
		if (u<umin.getValue()+0.0001)   { z.im = -Math.max(Math.max(-z.im, amp - Math.abs(z.re-P)), amp - Math.abs(z.re-P-1/P));}
		if (u>umax.getValue()-0.0001)   { z.im = Math.max(Math.max(z.im, amp - Math.abs(z.re-P)), amp - Math.abs(z.re-P-1/P));}
		return z;
	}
//	Index of grid point closest to branch value P
	protected void p_Index(){
		jP = (int) Math.floor((PL-vmin.getValue())/(vmax.getValue()-vmin.getValue())*(vcount-1));
	}
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		// return JD = sqrt( P/R*(1/(Weierstrass-p) - (Weierstrass-p)) +(P*P-1)/R)
		double denom= z.re*z.re+z.im*z.im;
		Complex w = new Complex( PR*(z.re/denom - z.re) + R_, -PR*z.im*(1/denom+1));
        if (z.im > 0)   w = w.squareRootNearer(I2_C);
        else            w = w.squareRootNearer(I1_C);
        return w;		
	    }
	
	protected Complex hPrime(Complex z) {
		// return r1/(weierstrass-P) = 1/(z-P)
		double denom= ((z.re-P)*(z.re-P)+z.im*z.im)/r1;
		return new Complex((z.re-P)/denom, -z.im/denom);
			//return ONE_C.times(0.25);    // to see the domain
		}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		P  = Math.exp(aa.getValue());
		R = (P*P+1);
		R_= (P*P-1)/R;
		PR= P/R;
		R = Math.sqrt(R);
		PL = Math.log(P/R);// jP
		qP = new Complex(P,0.0000125);
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
	   	return new ComplexVector3D(helperArray[um][vm]); // default
	/*	if (inAssociateMorph)	 {  // Keeps the saddle fixed
        		return new ComplexVector3D(helperArray[um][vm]);
        }
		else {
			ComplexVector3D gC = new ComplexVector3D(helperArray[0][jP]);
			gC = gC.plus(ComplexVectorIntegrator(domainGrid(umin.getValue(),vmin.getValue()+jP*dv ), qP,16));
			return gC;
		}*/
	}
	
	public void computeHalfPeriod(){
		halfPeriod = new ComplexVector3D(helperArray[0][0]);
		halfPeriod.assign(halfPeriod.y.minus(halfPeriod.x), halfPeriod.y.plus(halfPeriod.x).times(I_C), halfPeriod.z);
		halfPeriodII = new ComplexVector3D(helperArray[0][vm]);
		halfPeriodII.assign(halfPeriodII.y.minus(halfPeriodII.x), halfPeriodII.y.plus(halfPeriodII.x).times(I_C), halfPeriodII.z);
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
	/*	if ((!inAssociateMorph)&&(i==0)&&(j==jP))
			eW = new ComplexVector3D(ComplexVector3D.ORIGIN);
		else if ((!inAssociateMorph)&&(i==ucount-1)&&(j==vcount-1-jP)) {
			eW = new ComplexVector3D(halfPeriod);
		}
		else */
		{
			auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));	
			eW = new ComplexVector3D(auxW.y.minus(auxW.x), (auxW.y.plus(auxW.x)).times(I_C), auxW.z );
	/*		if ((!inAssociateMorph)&&(((i==0)&&(j <= jP))||((i==ucount-1)&&(j >= vcount-1-jP)))) eW.y.assign(ZERO_C);
			if ((!inAssociateMorph)&&(i==0)&&(j > jP))    {eW.assign(ZERO_C, eW.y, ZERO_C); }
			if ((!inAssociateMorph)&&(i==ucount-1)&&(j < vcount-1-jP))  
			     { eW.assign(halfPeriod.x, eW.y, halfPeriod.z); }   */
		}
		if (AFP==0)
			return eW.re();
		else
			return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));    
	}
	   
}


