/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import java.awt.Color;

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

public class DoublyPeriodicJD extends WeierstrassMinimalSurface {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.DoublyPeriodicJD.aa",0,0.6,-0.6);
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public DoublyPeriodicJD() {
		super();
		addParameter(aa);
		aa.setMaximumValueForInput(0.9); // if one wants to morph that far, increase resolution
		aa.setMinimumValueForInput(-0.84);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		setDefaultViewUp(new Vector3D(0.35,  0.05,  0.93));
		setDefaultViewpoint(new Vector3D(-46, -8, 18));
		setDefaultWindow(-4.5,2.7,-3.9,2.2);
			uPatchCount.setValueAndDefault(12);
			vPatchCount.setValueAndDefault(12);
			umin.reset(-4.5);
			umax.reset(0);
			vmin.reset(-Math.PI/2); 
			vmax.reset(Math.PI/2);
		removeParameter(vmin);
		removeParameter(vmax);
		removeParameter(umax);
		wantsToSeeDomain = false;
		wantsToSeeGaussImage = false;//true;//
		if (wantsToSeeGaussImage)  wantsToSeeDomain = true;
		if (wantsToSeeDomain)		 {setDefaultViewUp(new Vector3D(0,0,1));
	  	                           setDefaultViewpoint(new Vector3D(0,0,40));}
		multipleCopyOptions = new int[] {2};
		canShowConjugateSurface = true;
	}
	public View getDefaultView() {
		//SurfaceView view = (SurfaceView)super.getDefaultView();  // its actually a WMSView
		WMSView view = new WMSView();
		//view.setGridSpacing(12);
		float c0 = (float)0.3;
		//view.getLightSettings().setLight0(new Color(c0,c0,c0));
		view.getLightSettings().setAmbientLight(new Color(c0,c0,c0));
		//view.getLightSettings().getDirectionalLight2().setItsColor(new Color((float)0.5,(float)1.0,(float)0.0));
		//view.getLightSettings().getDirectionalLight2().setItsDirection (new Vector3D(0.66,-0.34,-0.66));
		view.getLightSettings ().setSpecularExponent(55);
		view.getLightSettings().setSpecularRatio( (float)0.5 );
		return view;
	}

	private int jP,um,vm;
	private double PL,r1,rB;
	private Complex cP,qP;
	private ComplexVector3D halfPeriodII;

	protected void createData() {
        super.createData();     // helperArray created, flag0 & flag05 are set
        data.discardGridTransforms();
        GridTransformMatrix[] trList = new GridTransformMatrix[40];
        		trList[0] = new GridTransformMatrix();
        		GridTransformMatrix a = new GridTransformMatrix();
        if ((!inAssociateMorph)&&(!wantsToSeeDomain)) {
        	if (flag0){
        		trList[1] = new GridTransformMatrix().scale(1,1,-1);
        		trList[2] = new GridTransformMatrix().scale(-1,-1,1).reverse();
        		trList[3] = new GridTransformMatrix().scale(-1,-1,-1).reverse();
        	} else if (flag05){
        		trList[1] = new GridTransformMatrix().scale(-1,-1,1).reverse();
        		trList[2] = new GridTransformMatrix().scale(1,1,-1);
        		trList[3] = new GridTransformMatrix().scale(-1,-1,-1).reverse();
        }
        		data.addGridTransform(trList[1]);
        		data.addGridTransform(trList[2]);
        		data.addGridTransform(trList[3]);
        	Vector3D trans = new Vector3D();
        		if (flag0)	
        		{ a = new GridTransformMatrix().scale(1,-1,-1).reverse(); trans.z = halfPeriodII.z.re; }
        	else if (flag05)
        		{ a = new GridTransformMatrix().scale(-1,1,1);            trans.x = halfPeriodII.x.im; }
        	for (int e=0; e < 4; e++){
        trList[e+4] = new GridTransformMatrix(trList[e]).leftMultiplyBy(a).translate(trans);
        data.addGridTransform(trList[e+4]);
        }
        
        if (getNumberOfPieces()==2) {
        	GridTransformMatrix b = new GridTransformMatrix();
        	Vector3D transs = new Vector3D();
        	if (flag0)		
        		{ b = new GridTransformMatrix().scale(-1,-1,1).reverse();  transs.y = 2*halfPeriod.y.re; }
        	else if (flag05)
        		{ b = new GridTransformMatrix().scale(-1,1,1);   transs.x = +3*halfPeriodII.x.im;}
        	for (int e=0; e < 8; e++){
            trList[e+8] = new GridTransformMatrix(trList[e]).leftMultiplyBy(b).translate(transs);
            data.addGridTransform(trList[e+8]);
            }
        }  
        } // if 
	}
	
	// override the default Cartesian Grid
	protected Complex domainGrid(double u, double v){
		// return z = exp(u+i*v) with branch point detour
		Complex z;
			double r = Math.exp(u);
			if (u>umax.getValue()-0.001)   
				{ r = r - Math.max(Math.max(0, 0.04 - Math.abs(v-PL) ), 0.04 - Math.abs(v+PL));}
			z = new Complex(r*Math.cos(v), r*Math.sin(v));
			if (wantsToSeeGaussImage) z = hPrime(z);
			return z;
	}
//	Index of grid point closest to branch value P. jP is the larger of the two branch point indices.
	protected void p_Index(){
		jP = (int) Math.floor((PL-vmin.getValue())/(vmax.getValue()-vmin.getValue())*(vcount-1));
	}
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		// return z = JD
		return new Complex(z);		
	    }
	
	protected Complex hPrime(Complex z) {
		// return 1/JPprime=1/(z*sqrt(1/z^2 + z^2 -P^2 - 1/P^2))
		// guide replaces analytic continuation. It is chosen so that 
		// w = w.squareRootNearer(guide); does not jump sheets.
		Complex w = z.times(z);
		Complex guide = new Complex(z.re,-z.im);
		double denom = w.re*w.re+w.im*w.im;
		        w = new Complex(w.re/denom + w.re - rB, -w.im*(1/denom - 1));
		        w = w.squareRootNearer(guide);
		        w.assignTimes(z);
		return w.inverse().times(r1);
		}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		PL = 0.25*Math.PI*(1 + Math.sin(Math.PI/2*aa.getValue()));
		cP  = new Complex(Math.cos(PL), Math.sin(PL));
		qP = new Complex(cP.times(0.99995));
		rB = 2*Math.cos(2*PL);
		r1 = 0.5;
		if (inAssociateMorph) r1=1.0;
		p_Index(); // needs dv
		//System.out.println(jP);
		um = (int) Math.floor((ucount-1));
		vm = (int) Math.floor(vcount/2);
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
	        //return new ComplexVector3D(helperArray[0][0].plus(helperArray[ucount-1][0]).times(0.5));  
              return new ComplexVector3D(ZERO_C,ZERO_C,ZERO_C);
		else {
			ComplexVector3D gC = new ComplexVector3D(helperArray[ucount-1][jP]);
			gC = gC.plus(ComplexVectorIntegrator(domainGrid(umax.getValue(),vmin.getValue()+jP*dv ), qP,32));
			return gC;
		}
	}
	
	public void computeHalfPeriod(){
	    halfPeriod = new ComplexVector3D(helperArray[um][vm].times(2.0));
        halfPeriod.assign( ZERO_C, halfPeriod.y.plus(halfPeriod.x).times(I_C), halfPeriod.z);
        halfPeriodII = new ComplexVector3D(helperArray[ucount-1][vcount-1].times(2.0));
        ComplexVector3D aux = new ComplexVector3D(helperArray[ucount-1][0]);
        halfPeriodII.assign( aux.y.plus(aux.x), ZERO_C, halfPeriodII.z);
        //System.out.println(halfPeriodII.z);
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
		if ((!inAssociateMorph)&&(!wantsToSeeDomain)&&(i==ucount-1)&&(j==jP))
			eW = new ComplexVector3D(ComplexVector3D.ORIGIN);
	    	else if ((!inAssociateMorph)&&(!wantsToSeeDomain)&&(i==ucount-1)&&(j==vcount-1-jP)) {
			eW = new ComplexVector3D(halfPeriod);
		}
		else 
		{
			auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));	
			eW = new ComplexVector3D(auxW.y.minus(auxW.x), (auxW.y.plus(auxW.x)).times(I_C), auxW.z );
		  if ((!inAssociateMorph)&&(!wantsToSeeDomain)){
			if (flag0) {
			if ((i==ucount-1)&&(j <= jP)&&(j >= vcount-1-jP))      eW.z.assign(ZERO_C);
			if ((i==ucount-1)&&(j > jP))            {eW.assign(ZERO_C, ZERO_C, eW.z); }
			if ((i==ucount-1)&&(j < vcount-1-jP))   { eW.assign(halfPeriod.x, halfPeriod.y, eW.z); } 
			} // if (flag0) 
			if (flag05) {
				if ((i==ucount-1)&&(j > jP))           eW.z.assign(ZERO_C);
				if ((i==ucount-1)&&(j < vcount-1-jP))  eW.z.assign(halfPeriod.z);
			} // if (flag05) 
		  }	
		} // else
		if (wantsToSeeDomain)     return new Vector3D(eW.z.re, eW.z.im, 0);
		else if (AFP==0)  return eW.re(); 
		else      return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));    
	}
	   
}


