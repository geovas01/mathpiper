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
import vmm.surface.parametric.WeierstrassMinimalSurface.WMSView;

public class DoublyPeriodicJE extends WeierstrassMinimalSurface {
	
	//private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.DoublyPeriodicJE.aa",Math.PI/4,1.5,0.1);
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.DoublyPeriodicJE.aa",0,0.6,-0.6);
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public DoublyPeriodicJE() {
		super();
		addParameter(aa);
		aa.setMaximumValueForInput(0.9); // if one wants to morph that far, increase resolution
		aa.setMinimumValueForInput(-0.8);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		//setDefaultViewUp(new Vector3D(-0.7,  -0.15,  -0.7));
		//setDefaultViewpoint(new Vector3D(32, -30, -26));
		setDefaultViewpoint(new Vector3D(-42, 19, 19));
		setDefaultWindow(-5,4,-4,3.2);
			uPatchCount.setValueAndDefault(30);
			vPatchCount.setValueAndDefault(12);
			umin.reset(-4.43);
			umax.reset(-umin.getValue());
			vmin.reset(-Math.PI/2); 
			vmax.reset(Math.PI/2);
		removeParameter(vmin);
		removeParameter(vmax);
		removeParameter(umax);
		multipleCopyOptions = new int[] {2,3};
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
		float c0 = (float)0.2;
		//view.getLightSettings().setLight0(new Color(c0,c0,c0));
		view.getLightSettings().setAmbientLight(new Color(c0,c0,c0));
		//view.getLightSettings().getDirectionalLight2().setItsColor(new Color((float)0.5,(float)1.0,(float)0.0));
		//view.getLightSettings().getDirectionalLight2().setItsDirection (new Vector3D(0.66,-0.34,-0.66));
		view.getLightSettings ().setSpecularExponent(55);
		view.getLightSettings().setSpecularRatio( (float)0.5 );
		return view;
	}

	private int iP,um,vm;
	private double PL,r1,rB,rTest,detour;
	private Complex cP,cP_,qP;
	private ComplexVector3D halfPeriodII;
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		     super.parameterChanged(param, oldValue, newValue);
		     if (param == umin)  umax.reset(-umin.getValue());
	}
	
	protected void createData() {
        super.createData();     // helperArray created, flag0 & flag05 are set
        data.discardGridTransforms();
        GridTransformMatrix[] trList = new GridTransformMatrix[40];
        		trList[0] = new GridTransformMatrix();
      if ((!inAssociateMorph)&&(!wantsToSeeDomain)&&((flag0)||(flag05)) ) {
        	if (flag0) {
        		trList[1] = new GridTransformMatrix().scale(-1,1,1);
        	  	trList[2] = new GridTransformMatrix().scale(1,-1,-1).reverse();
        	  	trList[3] = new GridTransformMatrix().scale(-1,-1,-1).reverse();
        	} else if (flag05) {
        		trList[1] = new GridTransformMatrix().scale(1,-1,-1).reverse();
        	  	trList[2] = new GridTransformMatrix().scale(-1,1,1);
        	  	trList[3] = new GridTransformMatrix().scale(-1,-1,-1).reverse();	
        	}
    	  	data.addGridTransform(trList[1]);
    	  	data.addGridTransform(trList[2]);
    	  	data.addGridTransform(trList[3]);
        //   		GridTransformMatrix a = new GridTransformMatrix().scale(1,-1,-1).reverse();
        
      if ((getNumberOfPieces()==2)||(getNumberOfPieces()==3)) {  
        for (int e=2; e < 4; e++){
        if (flag0)
        		trList[e+2] = new GridTransformMatrix(trList[e]).translate(0,2*halfPeriodII.y.re,0);
        else if (flag05)
        		trList[e+2] = new GridTransformMatrix(trList[e]).translate(2*halfPeriod.x.im,0,0);
        		data.addGridTransform(trList[e+2]);
        		}
        	for (int e=2; e < 4; e++){
        		 if (flag0)
        			 trList[e+4] = new GridTransformMatrix(trList[e]).translate(0,0,2*halfPeriod.z.re);
        		 if (flag05)
                  trList[e+4] = new GridTransformMatrix(trList[e]).scale(-1,-1,-1).reverse().translate(-2*halfPeriod.x.im,0,0);
              data.addGridTransform(trList[e+4]);
             }
      if ((getNumberOfPieces()==3)&&(flag0)) {
        	for (int e=2; e < 4; e++){
                trList[e+6] = new GridTransformMatrix(trList[e]).translate(0,2*halfPeriodII.y.re,2*halfPeriod.z.re);
                data.addGridTransform(trList[e+6]);
             }
        	for (int e=0; e < 2; e++){
                trList[e+10] = new GridTransformMatrix(trList[e]).translate(0,0,-2*halfPeriod.z.re);
                data.addGridTransform(trList[e+10]);
             }
        	}
      if ((getNumberOfPieces()==3)&&(flag05)) {
      	for (int e=2; e < 3; e++){
              trList[e+6] = new GridTransformMatrix(trList[e]).translate(0,0,-2*halfPeriodII.z.im);
              trList[e+7] = new GridTransformMatrix(trList[e]).translate(0,0,-2*halfPeriodII.z.im).scale(-1,1,1);
              data.addGridTransform(trList[e+6]);
              data.addGridTransform(trList[e+7]);
           }
      	for (int e=0; e < 1; e++){
              trList[e+10] = new GridTransformMatrix(trList[e]).translate(-2*halfPeriod.x.im,0,-2*halfPeriodII.z.im);
              trList[e+11] = new GridTransformMatrix(trList[e]).translate(-2*halfPeriod.x.im,0,-2*halfPeriodII.z.im).scale(-1,1,1);
              data.addGridTransform(trList[e+10]);
              data.addGridTransform(trList[e+11]);
           }
      	}
      	} // if pieces
       } // if
	}
	
	// override the default Cartesian Grid
	protected Complex domainGrid(double u, double v){
		// return z = exp(u+i*v) with branch point detour
		Complex z;
			double r = Math.exp(u);
			z = new Complex(r*Math.cos(v), r*Math.sin(v));
			Complex aux = new Complex(z.re+1, z.im);
			z.re = 1- z.re;  z.im = - z.im;
			z.assignDivide(aux);
			// now the detours:
			if (v < 0.0001){
				double s = z.minus(cP).r();
				   if (s <= detour) { z.assignTimes(1+s-detour); }
				   else { s = z.minus(cP_).r();
				   		 if (s <= detour) { z.assignTimes(1+s-detour);}
				   }
				}
			if (v >= vmax.getValue()- 0.0001){
				double s = z.minus(cP.conj()).r();
				   if (s <= detour)  { z.assignTimes(1+s-detour); }
				   else { s = z.minus(cP_.conj()).r();
			   		 	if (s <= detour) { z.assignTimes(1+s-detour);}
				   }
				}
			if (wantsToSeeGaussImage) z = hPrime(z);
			return z;
		}
//	Index of grid point closest to branch value P. jP is the larger of the two branch point indices.
	protected void p_Index(){
		iP = (int) Math.floor((rTest-umin.getValue())/(umax.getValue()-umin.getValue())*(ucount-1));
	}
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		// return w = (1-JD)/(JD+1)
		Complex w = new Complex(1-z.re,-z.im);
		w.assignDivide(new Complex(z.re+1, z.im));
		return w;		
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
		cP_  = new Complex(-Math.cos(PL), Math.sin(PL));
		qP = new Complex(cP.times(0.9995));
		rB = 2*Math.cos(2*PL);
		rTest = Math.log(Math.sin(PL)/(1+Math.cos(PL)));
		//System.out.println(rTest);
		detour = (0.05*(aa.getValue() + 0.7) + 0.005*(0.9-aa.getValue()))/2.0 ; // in domainGrid
		r1 = 0.5*(1 + aa.getValue())*(1 - 0.075*(getNumberOfPieces()-1));       // controls the size
		if (inAssociateMorph) r1=r1*1.5;
		p_Index(); // needs dv
		// System.out.println(iP);
		um = (int) Math.floor((ucount/2.0));
		vm = (int) Math.floor(vcount/2.0);
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
		/*	ComplexVector3D gC = new ComplexVector3D(helperArray[iP][0]);
			gC = gC.plus(ComplexVectorIntegrator(domainGrid(umin.getValue()+iP*du,vmin.getValue()), qP,64));
			return gC;*/
		//  design a detour method
			double eps = 0.005;
			Complex cP1 = new Complex(Math.cos(PL-eps), Math.sin(PL-eps));
			Complex cP2 = new Complex(Math.cos(PL)-0.7*eps, Math.sin(PL)-0.7*eps);
			Complex cP3 = new Complex(Math.cos(PL+eps), Math.sin(PL+eps));
			//Complex cP4 = new Complex(cP.re+eps, cP.im);
			ComplexVector3D gC = new ComplexVector3D(helperArray[iP][0]);
			gC.assignPlus(ComplexVectorIntegrator(domainGrid(umin.getValue()+iP*du,vmin.getValue()), cP1,32));
			ComplexVector3D aux = ComplexVectorIntegrator(cP1, cP2, 32);
			aux = aux.plus(ComplexVectorIntegrator(cP2, cP3, 32)).times(new Complex(0.5,-0.5));
			return gC.plus(aux); 
		}
	}
	
	public void computeHalfPeriod(){
		ComplexVector3D surfMaxMin = helperToMinimal(helperArray[ucount-1][0]);
		ComplexVector3D surfMinMin = helperToMinimal(helperArray[0][0]);
		halfPeriod = (surfMaxMin.minus(surfMinMin));
		halfPeriod.x.re = 0; halfPeriod.y.re = 0;
		halfPeriod.y.im = 0;
		
		halfPeriodII = helperToMinimal(helperArray[um][vm].times(2.0));
		halfPeriodII.x.re = 0; halfPeriodII.z.re = 0;
		halfPeriodII.y.im = 0;
        /*     //System.out.println(halfPeriodII.z); */
	}  
	
	/**
	 * Override to close the hole around the images of the four branch points of JD
	 */
	public Vector3D surfacePoint(double u, double v) {
		int i = (int) Math.floor(0.25 + (u-umin.getValue())/du );
		int j = (int) Math.floor(0.25 + (v-vmin.getValue())/dv );
			//Complex zInitial = domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv);
			//Complex z = domainGrid(u,v);
		ComplexVector3D auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));	
		ComplexVector3D eW = helperToMinimal(auxW);
		  if ((!inAssociateMorph)&&(!wantsToSeeDomain))	 {
				if ((i==iP)&&(j==0))
					eW = new ComplexVector3D(ComplexVector3D.ORIGIN);
				else if ((i==ucount-1-iP)&&(j==0)) 
					eW = new ComplexVector3D(halfPeriod);
				else if ((i==iP)&&(j==vcount-1)) 
					eW = new ComplexVector3D(halfPeriodII);
				else if ((i==ucount-1-iP)&&(j==vcount-1)) {
						if (flag0) 
						eW = new ComplexVector3D(halfPeriod.plus(halfPeriodII));
						else if (flag05)
						eW = new ComplexVector3D((halfPeriod.times(-1.0)).plus(halfPeriodII));
					}
			if (flag0) {
			  if (((j == 0)||(j==vcount-1))&&( (i > iP)&&(i < ucount-1-iP)))   eW.x.assign(ZERO_C);
			  if ((j==0)&&(i < iP))                    { eW.assign(eW.x, ZERO_C, ZERO_C); }
			  if ((j==0)&&(i > ucount-1-iP))           { eW.assign(eW.x, ZERO_C, halfPeriod.z); }
			  if ((j == vcount-1)&&(i < iP))           { eW.assign(eW.x, halfPeriodII.y, ZERO_C); }
			  if ((j == vcount-1)&&(i > ucount-1-iP))  { eW.assign(eW.x, halfPeriodII.y, halfPeriod.z); } 
			} // if (flag0) 
			if (flag05) {
			  if ((j == 0)&&( (i > iP)&&(i < ucount-1-iP)))  eW.assign(eW.x, ZERO_C, ZERO_C);
			  if ((j==vcount-1)&&( (i > iP)&&(i < ucount-1-iP)))  eW.assign(eW.x, ZERO_C, halfPeriodII.z);
			} //if (flag05)
		   } // !wantsToSeeDomain 

		if (wantsToSeeDomain)     return new Vector3D(eW.z.re, eW.z.im, 0);
		else if (AFP==0)  return eW.re(); 
		else      return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));    
	}
	   
}


