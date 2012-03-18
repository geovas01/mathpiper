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

public class SchwarzPDsurface extends WeierstrassMinimalSurface {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.SchwarzPDsurface.aa",0.2,0.48,0.02);
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.surface.parametric.SchwarzPDsurface.bb",0.6,0.52,0.98);
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public SchwarzPDsurface() {
		super();
		addParameter(bb);
		bb.setMaximumValueForInput(0.99); // if one wants to morph that far, increase resolution
		bb.setMinimumValueForInput(0.05);
		addParameter(aa);
		aa.setMaximumValueForInput(0.95); // if one wants to morph that far, increase resolution
		aa.setMinimumValueForInput(0.02);
		setDefaultOrientation(View3DLit.REVERSE_ORIENTATION);
		setDefaultViewUp(new Vector3D(-0.045,  0.4,  0.915));
		setDefaultViewpoint(new Vector3D(12, -44, 20));
		setDefaultWindow(-3,3,-2.8,2.2);
			uPatchCount.setValueAndDefault(12);
			vPatchCount.setValueAndDefault(12);
			umin.reset(0.005);
			umax.reset(0.995);
			vmin.reset(0.0); 
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
		if (wantsToSeeGaussImage)  wantsToSeeDomain = true;
		if (wantsToSeeDomain)		 {setDefaultViewUp(new Vector3D(0,0,1));
	  	                           setDefaultViewpoint(new Vector3D(0,0,40));}
		multipleCopyOptions = new int[] {2};
		canShowConjugateSurface = true;
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
		view.getLightSettings ().setSpecularExponent(100);
		view.getLightSettings().setSpecularRatio( (float)0.8 );
		return view;
	}

	private int um,vm;
	private double Phi1,Phi2,rp1,RP2,r1_,R2_,scale;
	private Complex xWidth, yWidth, zWidth;
	private Vector3D centerRE, centerRE_, centerIM, trans;
	
	protected void createData() {
        super.createData();     // helperArray created, flag0 & flag05 are set
        computePeriodData();
        data.discardGridTransforms();
        GridTransformMatrix[] trList = new GridTransformMatrix[64];
        		trList[0] = new GridTransformMatrix();
        		GridTransformMatrix a = new GridTransformMatrix();
        		trList[1] = new GridTransformMatrix().scale(-1,-1,-1).reverse();
        if (!wantsToSeeDomain)		{ data.addGridTransform(trList[1]);
        if (!inAssociateMorph)  {
        	if (flag0){
        		a = new GridTransformMatrix().scale(-1,-1,1);
        		trans = new Vector3D(centerRE.x, centerRE.y, 0);
        		trList[2] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[3] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[2]);
        		data.addGridTransform(trList[3]);
        		a = new GridTransformMatrix().scale(1,-1,-1);
        		trans = new Vector3D(0, centerRE.y, centerRE.z);
        		for (int e=0; e < 4; e++){
        		trList[4+e] = new GridTransformMatrix(trList[e]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[4+e]);   		
        		}
        		a = new GridTransformMatrix().scale(-1,-1,-1); //.reverse();
        		for (int e=0; e < 8; e++){
            		trList[8+e] = new GridTransformMatrix(trList[e]).leftMultiplyBy(a).translate(centerRE);
            		data.addGridTransform(trList[8+e]); 
            		}
        		trans = new Vector3D(centerRE.x, centerRE.y, -centerRE.z);
        		for (int e=0; e < 4; e++){
            		trList[16+e] = new GridTransformMatrix(trList[e]).leftMultiplyBy(a).translate(trans);
            		data.addGridTransform(trList[16+e]);
            		trList[20+e] = new GridTransformMatrix(trList[12+e]).leftMultiplyBy(a).translate(trans);
            		data.addGridTransform(trList[20+e]);
            		}
        		if (getNumberOfPieces()==2) {
        			trans = new Vector3D(4*xWidth.re,0,0);
        			for (int e=0; e < 24; e++){
        				trList[24+e] = new GridTransformMatrix(trList[e]).translate(trans);
                		data.addGridTransform(trList[24+e]);	
        			}
        		} // if (getNumberOfPieces()==2)
        	} // if (flag0)
        	if (flag05){
        		a = new GridTransformMatrix().scale(-1,-1,1).reverse();
        		trans = new Vector3D(centerIM.x, centerIM.y, 0);
        		trList[2] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[3] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[2]);
        		data.addGridTransform(trList[3]);
        		a = new GridTransformMatrix().scale(1,-1,-1).reverse();
        		trans = new Vector3D(0, centerIM.y, centerIM.z);
        		trList[4] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[5] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[4]);
        		data.addGridTransform(trList[5]);
        		a = new GridTransformMatrix().scale(-1,1,-1);
        		trans = new Vector3D(centerIM.x, 0, centerIM.z);
        		trList[6] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[7] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[6]);
        		data.addGridTransform(trList[7]);
        		a = new GridTransformMatrix().scale(1,-1,-1).reverse();
        		trans = new Vector3D(0, -centerIM.y, -centerIM.z);
        		trList[8] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[9] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[8]);
        		data.addGridTransform(trList[9]);
        		trans = new Vector3D(2*centerIM.x, 0, 2*centerIM.z);
        		trList[10] = new GridTransformMatrix(trList[8]).translate(trans);
        		trList[11] = new GridTransformMatrix(trList[9]).translate(trans);
        		data.addGridTransform(trList[10]);
        		data.addGridTransform(trList[11]);
        		a = new GridTransformMatrix().scale(-1,1,-1).reverse();
        		trans = new Vector3D(centerIM.x, 0, -1*centerIM.z);
        		trList[12] = new GridTransformMatrix(trList[8]).leftMultiplyBy(a).translate(trans);
        		trList[13] = new GridTransformMatrix(trList[9]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[12]);
        		data.addGridTransform(trList[13]);
        		trans = new Vector3D(0, -2*centerIM.y, 0);
        		trList[14] = new GridTransformMatrix(trList[0]).translate(trans);
        		trList[15] = new GridTransformMatrix(trList[1]).translate(trans);
        		data.addGridTransform(trList[14]);
        		data.addGridTransform(trList[15]);
        		if (getNumberOfPieces()==2) {
        			a = new GridTransformMatrix().scale(-1,-1,1).reverse();
        			trans = new Vector3D(-centerIM.x, -centerIM.y, 0);
        			for (int e=0; e < 16; e++){
        				trList[16+e] = new GridTransformMatrix(trList[e]).leftMultiplyBy(a).translate(trans);
                		data.addGridTransform(trList[16+e]);	
        			}
        		} // if (getNumberOfPieces()==2)
        	} // if (flag05)
        	} // if (!inAssociateMorph)
        } // (!wantsToSeeDomain)
	}
	
	public static double paramRescale(double x){
		double y;
		y = Math.sin(Math.PI/2*x);
		return y*y;
	}
	
	// override the default Cartesian Grid
	protected Complex domainGrid(double u, double v){
		double ph = 0;
		Complex z;
			double r = Math.sin(Math.PI/2*u);
			if (v <= 1.0/3.0)
				ph = -Math.PI + Phi1*paramRescale(3*v);
			else if ((v > 1.0/3.0)&&(v <= 2.0/3.0))
				ph = -Math.PI + Phi1 + (Phi2-Phi1)*paramRescale(3*v-1);
			else if (v > 2.0/3.0)
				ph = -Math.PI + Phi2 + (Math.PI-Phi2)*paramRescale(3*v-2);
			z = new Complex(r*Math.cos(ph), r*Math.sin(ph));
			if (wantsToSeeGaussImage) z = gauss(z);
			return z;
	}
//	Index of grid point closest to branch value P. jP is the larger of the two branch point indices.
/*	protected void p_Index(){
		jP = (int) Math.floor((PL-vmin.getValue())/(vmax.getValue()-vmin.getValue())*(vcount-1));
	} */
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		Complex z2 = z.times(z);
		Complex z2_ = new Complex(1-z2.re, -z2.im);
			    z2.re = 1 + z2.re;
	    Complex w  = z2_.inverse().times(z2);
	    double  x  = -2*w.im - r1_;
	            w.im = 2*w.re;
	            w.re = x;
	    Complex aux  = w.times(-2/(R2_ - r1_));
	            aux.re = -aux.re -1;
	            aux.im = -aux.im;
	    Complex aux2 = aux.times(aux);
	            aux2.re = aux2.re - 1;
	    // The next two lines are crucial, they achieve an analytic continuation:
	    Complex g2  = (aux2.squareRootNearer(aux)).minus(aux); 
		return  g2.squareRootNearer(I1_C);		
	    }
	
	protected Complex hPrime(Complex z) {
		// return g^2 / ((z^2-1)*(g^4-1))
		Complex z2 = z.times(z);
		        z2.re = z2.re - 1;
		Complex w = gauss(z);
		Complex g2 = w.times(w);
		Complex g4 = g2.times(g2);
		        g4.re = g4.re - 1;
		        g4.assignTimes(z2);
		        w  = (g4.inverse()).times(g2);
		        w.assignTimes(scale);
		return w;
		}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		Phi1 = Math.PI*aa.getValue();
		Phi2 = Math.PI*bb.getValue();
		rp1  = Math.tan(Phi1/2.0);
		RP2  = Math.tan(Phi2/2.0);
		r1_  = rp1 - 1.0/rp1;
		R2_  = RP2 - 1.0/RP2;
		scale= Math.sqrt(Math.sqrt((0.1+aa.getValue()) * (1.1 - bb.getValue()) ) /(bb.getValue() - aa.getValue()) );
		um = 1;//(int) Math.floor((ucount-1));
		vm = 1;//(int) Math.floor(vcount/2);
	}
	
	/**
	 * We want to center the surface (one hexagon) already at the helper Level.
	 */
	protected ComplexVector3D getCenter(){
			ComplexVector3D gC = new ComplexVector3D(helperArray[0][0]);
			gC = (gC.plus(helperArray[0][vcount-1])).times(0.5);
			return gC;
	}
	
	public void computePeriodData(){
		if (flag0) {
		ComplexVector3D aux = new ComplexVector3D(helperToMinimal(helperArray[ucount-1][(int)Math.floor(vcount*5/6)]));
		xWidth = aux.x;
		aux = new ComplexVector3D(helperToMinimal(helperArray[ucount-1][(int)Math.floor(vcount/2)]));
		zWidth = aux.z;
		aux = new ComplexVector3D(helperToMinimal(helperArray[ucount-1][(int)Math.floor(vcount/6)]));
		yWidth = aux.y;
		centerRE = new Vector3D(-2*xWidth.re, -2*yWidth.re, -2*zWidth.re);
		centerRE_ = new Vector3D(2*xWidth.re, 2*yWidth.re, 2*zWidth.re);
		}
		if (flag05) {
		ComplexVector3D aux = new ComplexVector3D(helperToMinimal(helperArray[ucount-1][(int)Math.floor(vcount*5/6)]));
		zWidth = aux.z;
		aux = new ComplexVector3D(helperToMinimal(helperArray[ucount-1][(int)Math.floor(vcount/2)]));
		yWidth = aux.y;
		aux = new ComplexVector3D(helperToMinimal(helperArray[ucount-1][(int)Math.floor(vcount/6)]));
		xWidth = aux.x;
		centerIM = new Vector3D(2*xWidth.im, 2*yWidth.im, 2*zWidth.im);
		}
        //System.out.println(centerRE.z);
	}
	
	/**
	 * Override to close the hole around the images of P, -1/P.
	 */
/*	public Vector3D surfacePoint(double u, double v) {
		ComplexVector3D eW,auxW;
		int i = (int) Math.floor(0.25 + (u-umin.getValue())/du );
		int j = (int) Math.floor(0.25 + (v-vmin.getValue())/dv );
			//Complex zInitial = domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv);
			//Complex z = domainGrid(u,v);		
		if ((!inAssociateMorph)&&(i==ucount-1)&&(j==jP))
			eW = new ComplexVector3D(ComplexVector3D.ORIGIN);
	    	else if ((!inAssociateMorph)&&(i==ucount-1)&&(j==vcount-1-jP)) {
			eW = new ComplexVector3D(halfPeriod);
		} 
		else 
		{
			auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));	
			eW = new ComplexVector3D(auxW.y.minus(auxW.x), (auxW.y.plus(auxW.x)).times(I_C), auxW.z );
			if ((!inAssociateMorph)&&(flag0)) {
			if ((i==ucount-1)&&(j <= jP)&&(j >= vcount-1-jP))      eW.z.assign(ZERO_C);
			if ((i==ucount-1)&&(j > jP))            {eW.assign(ZERO_C, ZERO_C, eW.z); }
			if ((i==ucount-1)&&(j < vcount-1-jP))   { eW.assign(halfPeriod.x, halfPeriod.y, eW.z); } 
			} 
		}
		if (wantsToSeeDomain)     return new Vector3D(eW.z.re, eW.z.im, 0);
		else if (AFP==0)  return eW.re(); 
		else      return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));    
	}
	*/   
}


