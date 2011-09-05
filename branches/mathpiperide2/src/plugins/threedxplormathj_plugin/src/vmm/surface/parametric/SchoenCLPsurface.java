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

public class SchoenCLPsurface extends WeierstrassMinimalSurface {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.SchoenCLPsurface.aa",0.6,0.52,0.98);
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.surface.parametric.SchoenCLPsurface.bb",0.2,0.48,0.02);
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public SchoenCLPsurface() {
		super();
		addParameter(bb);
		bb.setMaximumValueForInput(0.95); // if one wants to morph that far, increase resolution
		bb.setMinimumValueForInput(0.02);
		addParameter(aa);
		aa.setMaximumValueForInput(0.99); // if one wants to morph that far, increase resolution
		aa.setMinimumValueForInput(0.05);
		setDefaultViewUp(new Vector3D(-0.57,  0.6,  0.58));
		setDefaultViewpoint(new Vector3D(15, -25, 40));
		setDefaultWindow(-5,5,-4,4);
			uPatchCount.setValueAndDefault(12);
			vPatchCount.setValueAndDefault(24);
			umin.reset(0.005);
			umax.reset(0.999);
			vmin.reset(-1.0); 
			vmax.reset(1.0);
		removeParameter(vmin);
		removeParameter(vmax);
		removeParameter(umin);
		removeParameter(umax);
		//afp.setValue(Math.PI/2);
		iFirstInHelper = false;
		// change the next two booleans for testing (or later for education)
		wantsToSeeDomain = false;
		wantsToSeeGaussImage = false;
		if (wantsToSeeGaussImage)  wantsToSeeDomain = true;//
		if (wantsToSeeDomain)		 {setDefaultViewUp(new Vector3D(0,0,1));
	  	                           setDefaultViewpoint(new Vector3D(0,0,40));}
		multipleCopyOptions = new int[] {2};
		canShowConjugateSurface = true;
	}
	public View getDefaultView() {
		//SurfaceView view = (SurfaceView)super.getDefaultView();  // its actually a WMSView
		WMSView view = new WMSView();
		view.setGridSpacing(12);
		setDefaultOrientation(View3DLit.REVERSE_ORIENTATION); // where should this go?
		float c0 = (float)0.2;
		//view.getLightSettings().setLight0(new Color(c0,c0,c0));
		view.getLightSettings().setAmbientLight(new Color(c0,c0,c0));
		//view.getLightSettings().getDirectionalLight2().setItsColor(new Color((float)0.5,(float)1.0,(float)0.0));
		//view.getLightSettings().getDirectionalLight2().setItsDirection (new Vector3D(0.66,-0.34,-0.66));
		view.getLightSettings ().setSpecularExponent(100);
		view.getLightSettings().setSpecularRatio( (float)0.8 );
		return view;
	}

	private int um,vm;
	private double alpha,beta, c2a, c2b, coeff,coecc,scale;
	private Complex ccff;
	private Vector3D surf_1,surf_2 ;

	protected void createData() {
        super.createData();     // helperArray created, flag0 & flag05 are set
        computePeriodData();
        data.discardGridTransforms();
        GridTransformMatrix[] trList = new GridTransformMatrix[64];
        		trList[0] = new GridTransformMatrix();
        		GridTransformMatrix a = new GridTransformMatrix();
        		trList[1] = new GridTransformMatrix().scale(-1,-1,1);
        if (!wantsToSeeDomain)		{ 
        		data.addGridTransform(trList[1]);
        		for (int e=0; e < 2; e++){
            		trList[2+e] = new GridTransformMatrix(trList[e]).scale(-1,-1,-1).reverse().translate(surf_1);
            		data.addGridTransform(trList[2+e]);
        			}
        if (!inAssociateMorph)  {		
      	if (flag0||flag05){
        		a = new GridTransformMatrix().scale(-1,-1,1).reverse().translate(surf_1.x,surf_1.y,0);
        		for (int e=0; e < 4; e++){
        		trList[4+e] = new GridTransformMatrix(trList[e]).leftMultiplyBy(a);
        		data.addGridTransform(trList[4+e]);   		
        		}
        		a = new GridTransformMatrix().scale(-1,-1,1);
        		for (int e=0; e < 4; e++){
        		trList[8+e] = new GridTransformMatrix(trList[2+e]).leftMultiplyBy(a);
        		data.addGridTransform(trList[8+e]);   		
        		}
        	if (getNumberOfPieces()==2){
        		if (flag0)
        		a = new GridTransformMatrix().scale(1,1,-1).translate(0,0,surf_2.z);
        		else if (flag05)
        		a = new GridTransformMatrix().scale(1,1,-1).translate(surf_2.x,surf_2.y,surf_2.z);
        		for (int e=0; e < 12; e++){
        		trList[12+e] = new GridTransformMatrix(trList[e]).leftMultiplyBy(a);
        		//trList[15+e] = new GridTransformMatrix(trList[1+(3+e)*e]).leftMultiplyBy(a);
        		data.addGridTransform(trList[12+e]);
        		//data.addGridTransform(trList[15+e]); 
        		}
        	} //if (getNumberOfPieces()==2)
      	}
        	} // if (!inAssociateMorph)
        } // (!wantsToSeeDomain)
	}
	
	public static double paramRescaleEnd(double x){
		double y;
		y = Math.sin(Math.PI/2*x);
		return y;
	}
	public static double paramRescaleStart(double x){
		double y;
		y = 1 - Math.cos(Math.PI/2*x);
		return y;
	}
	public static double paramRescaleBoth(double x){
		double y;
		y = (1.0 - Math.cos(Math.PI*x))/2.0;
		return y;
	}
	public static double paramRescaleMiddle(double x){
		double y;
		y = (1.0 + x*Math.abs(x))/2.0;
		return y;
	}
	
	// override the default Cartesian Grid
	protected Complex domainGrid(double u, double v){
		boolean vflag = false;
		double ph = 0;
		Complex z;
		// This is the domainGrid if one wants the Gauss map as coordinate function
		double r = paramRescaleEnd(u);
			if (v < 0 ) {v = -v;  vflag = true;}
			if (v <= 1.0/3.0)
				ph = beta*paramRescaleEnd(3*v);
			else if (v <= 2.0/3.0)
				ph = beta - (beta-alpha)*paramRescaleBoth(3*v-1);
			else if (v <= 1)
				ph = alpha + (Math.PI*0.5 - alpha)*paramRescaleStart(3*v-2);
			z = new Complex(r*Math.cos(ph), r*Math.sin(ph));
			if (wantsToSeeGaussImage) z = mu(z);
			if (vflag)  z = z.conj();
			return z.conj();
	
	/*	// This is the Grid for using the hexagon function, needs to take sqrt of a slit
		double r = paramRescaleEnd(u);
		ph = Math.PI*paramRescaleBoth(v);
		z = new Complex(r*Math.cos(ph), r*Math.sin(ph));
		if (wantsToSeeGaussImage) z = gauss(z);
		return z; */
	}
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
	  return z;
	    }
	
	protected Complex mu(Complex z) {
		boolean vflag = false;
		if (z.im < 0) {z = z.conj();  vflag = true;}
		Complex g2 = z.times(z);
	    Complex w2  = g2.inverse();
	    		    w2.assignPlus(g2); 
	    Complex v2 = w2.minus(2*c2a);	    
	    		    w2.re =  w2.re - 2*c2b;
	    	        w2.assignTimes(v2);
	            v2 = w2.squareRootNearer(I_C);
	   if (vflag) v2 = v2.conj();
	   return v2;
	}
	
	protected Complex hPrime(Complex z) {
		Complex w = mu(z);
		        w.assignTimes(z);
		        w.assignInvert();
		return w;
		}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		alpha= 0.5*Math.PI*aa.getValue();
		beta = 0.5*Math.PI*bb.getValue();
		c2a  = Math.cos(2*alpha);
		c2b  = Math.cos(2*beta);
		coeff= 0.5*(c2a - c2b);   ccff = new Complex(0, -2.0*coeff);
		coecc= (c2a + c2b);
		scale= 1;
	}
	
	/**
	 * We want to center the surface (one hexagon) already at the helper Level.
	 */
	protected ComplexVector3D getCenter(){
			ComplexVector3D gC = new ComplexVector3D(helperArray[0][0]);
			gC = (gC.plus(helperArray[0][vcount-1])).times(0.5);
			return gC;       // new ComplexVector3D(ZERO_C,ZERO_C,ZERO_C);
	}
	
	public void computePeriodData(){
		surf_1 = surfacePoint(umax.getValue(), 1.0/3.0).times(2.0);
		surf_2 = surfacePoint(umax.getValue(), 2.0/3.0).times(2.0);
        //System.out.println(surf_1.z);
	}
	
	/**
	 * Override to close the hole around the images of P, -1/P.
	 */
/*	public Vector3D surfacePoint(double u, double v) {
	}
	*/   
}


