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

public class CostaHoffmanMeeks extends WeierstrassMinimalSurface {
	
	private IntegerParam exponent = new IntegerParam("vmm.surface.parametric.CostaHoffmanMeeks.exponent",2);
	private RealParamAnimateable lrp = new RealParamAnimateable("vmm.surface.parametric.CostaHoffmanMeeks.lrp",1,1.2,0.8);
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public CostaHoffmanMeeks() {
		super();
		addParameter(lrp);
		lrp.setMinimumValueForInput(0.1);
		addParameter(exponent);
		exponent.setMinimumValueForInput(2);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		setDefaultViewpoint(new Vector3D(25, 43, -10));
		setDefaultViewUp(new Vector3D(0.1,-0.3,-1));
		setDefaultWindow(-9,9,-8,8);
		uPatchCount.setValueAndDefault(24);
		vPatchCount.setValueAndDefault(12);
		umin.reset(-1.9); 
		umax.reset(2.4);
		vmin.reset(-0.99995);
		vmax.reset(0.99995);	
		removeParameter(vmin);
		removeParameter(vmax);
		iFirstInHelper = false;
		needsPeriodClosed = true;
		wantsToSeeDomain = false;
		if (wantsToSeeDomain)		 {setDefaultViewUp(new Vector3D(0,0,1));
        						  	  setDefaultViewpoint(new Vector3D(0,0,40));}
		//multipleCopyOptions = new int[] {2,3};  // indicates that we can show twice the usual number of copies of fundamntal piece
		canShowConjugateSurface = false;
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

	private int Ex,iP,um,vm;
	double amp; // for domainGrid
	private double r1,LRP;
	private Complex qP;
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		     super.parameterChanged(param, oldValue, newValue);
		     AFP = afp.getValue();
		     if (param != afp)   needsValueArray = true;
		     if (param == exponent) { needsPeriodClosed = true;  LRP = 1.0;}
	}
	
	protected void createData() {
        super.createData();     // helperArray created
        data.discardGridTransforms();
        GridTransformMatrix[] trList = new GridTransformMatrix[4*Ex];
				trList[0] = new GridTransformMatrix();
		
		if ((!inAssociateMorph)&&(!wantsToSeeDomain)&&flag0 ) {
			trList[1] = new GridTransformMatrix().scale(1,-1,1);
			data.addGridTransform(trList[1]);  
        for (int e = 2; e < 2*Ex; e++){
    			trList[e] = new GridTransformMatrix(trList[e-2]).rotateZ(360.0/(double)Ex);
    			data.addGridTransform(trList[e]);
        		}
        for (int e = 0; e < 2*Ex; e++){
			trList[e+2*Ex] = new GridTransformMatrix(trList[e]).scale(1,-1,-1).rotateZ(180.0/(double)Ex).reverse();
			data.addGridTransform(trList[e+2*Ex]);
    		}
		}
	}
	
	/** 
	 * Override the default Cartesian Grid. It is critical that the domain grid is adapted to
	 * the minimal surface.
	 */
	protected Complex domainGrid(double u, double v){
		// return pos*sqrt(1+exp(u+iv)), with concentration of lines
		Complex z,w;
		    //if ((Math.abs(u-umin.getValue())<amp)&&(Math.abs(v-vmin.getValue())<amp))
			//{u=u+64*amp;  v=v+512*amp;} // was used to identify initial grid point in image
			double r = Math.exp(monotonPow(u,2));//  (u,Ex)*2.0/((double)Ex));
			double a = Math.PI*Math.sin(v*Math.PI/2);
			z = new Complex(r*Math.cos(a) + 1, r*Math.sin(a));
			w = z.squareRootNearer(ONE_C);
			if (w.r() < amp) {
				w.re = Math.max(w.re, amp - Ex*Math.abs(z.im));
			}
		    return w;
	}
	/**
     * Last index of grid point before symmetry line switches to straight line.
     */
	protected void p_Index(){
		Complex z;
		for (int i=0; i<ucount; i++){
			z = domainGrid(umin.getValue()+i*du, vmin.getValue());
			if (Math.abs(z.im) < 0.0001) iP=i;
		}
	}
	
//	 ************************** Weierstrass Data *********************************** //	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		// return LRP*z/u,  u^(-Ex) = (1/z - z)
		Complex aux;
		double denom= z.re*z.re+z.im*z.im;
		Complex w = new Complex(z.re/denom -z.re, -z.im*(1.0/denom +1));// w = 1/z - z
		if (z.im > 0 )
			{aux = (w.logNearer(ZERO_C)).times(1.0/(double)Ex);}
		else
			aux = (w.logNearer(IP__C)).times(1.0/(double)Ex);
		w = aux.exponential();  // w = 1/u
		w.assignTimes(z);
		w.assignTimes(LRP);
        return w;		
	    }
	
	protected Complex hPrime(Complex z) {
		// return r1/(z^2-1)
		Complex w = z.times(z);
		double denom= ((w.re-1)*(w.re-1)+w.im*w.im)/r1;
		return new Complex((w.re-1)/denom, -w.im/denom);
			//return ONE_C.times(0.25);    // to see the domain
		}
	
	/**
	 * This surface needs that the Lopez-Ros parameter is adjusted to close the period.
	 * The default morph illustrates non-closed periods.
	 */
	protected double closingLopezRos(){
		double ro,constLeft,factRight;
		// This needs only three points from the helperArray
		// Use the last point on the straight line and the middle point on the unbroken symmetry line
		// Method: zero on straight line  equals  zero on intersection of symmetry planes
		//         zero = x - y*ctg(); The points are over the negative x-axis
		//- (cs + ro^2*ds)*ts - (as + ro^2*bs) = - (cl + ro^2*dl)*tl - (al + ro^2*bl)
		double as = +helperArray[0][vm].y.re;
		double bs = -helperArray[0][vm].x.re;
		double cs = -helperArray[0][vm].y.im;
		double ds = -helperArray[0][vm].x.im;
		double ts = Math.cos(Math.PI/(1.0*Ex))/Math.sin(Math.PI/(1.0*Ex));
		if (Ex!=3) {
			double al = +helperArray[iP+3][vcount-1].y.re;
			double bl = -helperArray[iP+3][vcount-1].x.re;
			double cl = -helperArray[iP+3][vcount-1].y.im;
			double dl = -helperArray[iP+3][vcount-1].x.im;
			double tl = Math.cos(1.0*Math.PI/(2.0*Ex))/Math.sin(1.0*Math.PI/(2.0*Ex)); 
			constLeft = -cs*ts - as + cl*tl + al;
			factRight = -dl*tl - bl + ds*ts + bs; 
		} else {
			double al = +helperArray[iP+3][0].y.re;
			double bl = -helperArray[iP+3][0].x.re;
			double cl = -helperArray[iP+3][0].y.im;
			double dl = -helperArray[iP+3][0].x.im;
			double tl = Math.cos(3.0*Math.PI/(2.0*Ex))/Math.sin(3.0*Math.PI/(2.0*Ex)); 
			constLeft = -cs*ts - as + cl*tl - al;
			factRight = -dl*tl + bl + ds*ts + bs; 	
		}
		ro = Math.sqrt(constLeft/factRight);
		LRP = ro*lrp.getValue();
		return ro;
	}
	
	protected void doClosingJob(){
		LRPclosed = closingLopezRos(); // Sets LRP = LRPclosed*lrp.getValue();
		createHelperArray();
		// System.out.println(LRPclosed); //"adjusted Lopez-Ros"
	}
	
	/**
	 * redoConstants is called in super.createData before any other computations start.
	 */
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		if (needsPeriodClosed) 
			LRP = 1.0;
		else 
			LRP  = LRPclosed*lrp.getValue(); // Lopez-Ros Parameter
		Ex = exponent.getValue();
		r1 = 1.0;
		amp = 1.0/2048.0/((double)Ex)/((double)Ex)/((double)Ex);
		p_Index(); // needs dv
		// System.out.println(iP);
		um = (int) Math.floor((ucount-1)/2.0);  // ucount, vcount are odd
		vm = (int) Math.floor((vcount-1)/2.0);
	}
	
	/**
	 * We want to center the surface already at the helper Level. We cannot use the symmetry
	 * lines of the surface and therefore need to integrate towards the image of P.
	 */
	protected ComplexVector3D getCenter(){
	   	// return new ComplexVector3D(helperArray[um][vm]); // default
		if ((inAssociateMorph)||(wantsToSeeDomain))	 {  // Keeps the midle fixed
        		return new ComplexVector3D(helperArray[um][vm]);
        }
		else {
			ComplexVector3D minMaxMin2 = helperToMinimal(helperArray[0][0]);
			ComplexVector3D minMaxMin1 = helperToMinimal(helperArray[8][0]);
			ComplexVector3D minMaxMid1 = helperToMinimal(helperArray[um][vm]);
			ComplexVector3D minMaxMid2 = helperToMinimal(helperArray[ucount-1][vm]);
			ComplexVector3D zeroLevel  = helperToMinimal(helperArray[ucount-1][0]);
			Complex z1 = new Complex(minMaxMid1.x.re, minMaxMid1.y.re);
			Complex z2 = new Complex(minMaxMid2.x.re, minMaxMid2.y.re);
			Complex w1 = new Complex(minMaxMin1.x.re, minMaxMin1.y.re);
			Complex w2 = new Complex(minMaxMin2.x.re, minMaxMin2.y.re);
			Complex cs = intersectLines(z1,z2,w1,w2);
			return minimalToHelper(new ComplexVector3D(cs,cs.times(I__C), zeroLevel.z));
		}
	}
	
	/**
	 * Override  surfacePoint  to close the hole around the center saddle.
	 */
	public Vector3D surfacePoint(double u, double v) {
		ComplexVector3D eW,auxW;
		int i = (int) Math.floor(0.25 + (u-umin.getValue())/du );
		int j = (int) Math.floor(0.25 + (v-vmin.getValue())/dv );
			//Complex zInitial = domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv);
			//Complex z = domainGrid(u,v);		
		if ((lrp.getValue()==1)&&(!inAssociateMorph)&&(!wantsToSeeDomain)&&(i==iP+1)&&((j==vcount-1)||(j==0)))
			eW = new ComplexVector3D(ComplexVector3D.ORIGIN);
		else
		{
			auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));	
			eW = helperToMinimal(auxW);
	/*		if ((!inAssociateMorph)&&(((i==0)&&(j <= jP))||((i==ucount-1)&&(j >= vcount-1-jP)))) eW.y.assign(ZERO_C);
			if ((!inAssociateMorph)&&(i==0)&&(j > jP))    {eW.assign(ZERO_C, eW.y, ZERO_C); }
			if ((!inAssociateMorph)&&(i==ucount-1)&&(j < vcount-1-jP))  
			     { eW.assign(halfPeriod.x, eW.y, halfPeriod.z); }   */
		}
		
		if (wantsToSeeDomain)     return new Vector3D(eW.z.re, eW.z.im, 0);
		else if (AFP==0)  return eW.re(); 
		else      return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));    
	}
	   
}


