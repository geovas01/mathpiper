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

public class SchoenGyroid extends WeierstrassMinimalSurface {
	
	//private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.SchwarzPDsurface.aa",0.2,0.48,0.02);
	
	public SchoenGyroid() {
		super();
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		setDefaultViewUp(new Vector3D(-0.01,  0.69,  0.72));
		setDefaultViewpoint(new Vector3D(-25, -32, 29));
		setDefaultWindow(-3,3,-2.8,2.2);
			uPatchCount.setValueAndDefault(12);
			vPatchCount.setValueAndDefault(12);
			umin.reset(0.005);
			umax.reset(0.997);
			vmin.reset(0.0); 
			vmax.reset(1.0);
		removeParameter(vmin);
		removeParameter(vmax);
		removeParameter(umin);
		removeParameter(umax);
		afp.setValue(0.5777/2.0*Math.PI);
		iFirstInHelper = false;
		// change the next two booleans for testing (or later for education)
		wantsToSeeDomain = false;
		wantsToSeeGaussImage = false;
		if (wantsToSeeGaussImage)  wantsToSeeDomain = true;
		if (wantsToSeeDomain)		 {setDefaultViewUp(new Vector3D(0,0,1));
	  	                           setDefaultViewpoint(new Vector3D(0,0,40));}
		multipleCopyOptions = new int[] {2};
		// canShowConjugateSurface = true;
	}
	public View getDefaultView() {
		//SurfaceView view = (SurfaceView)super.getDefaultView();  // its actually a WMSView
		WMSView view = new WMSView();
		view.setGridSpacing(12);
		float c0 = (float)0.25;
		//view.getLightSettings().setLight0(new Color(c0,c0,c0));
		view.getLightSettings().setAmbientLight(new Color(c0,c0,c0));
		//view.getLightSettings().getDirectionalLight2().setItsColor(new Color((float)0.5,(float)1.0,(float)0.0));
		//view.getLightSettings().getDirectionalLight2().setItsDirection (new Vector3D(0.66,-0.34,-0.66));
		view.getLightSettings ().setSpecularExponent(95);
		view.getLightSettings().setSpecularRatio( (float)0.8 );
		return view;
	}

	private double Phi1,Phi2,rp1,RP2,r1_,R2_,scale;
	private Vector3D trans, transX, transY, transZ;
	private Vector3D N_6, N_2, N5_6, surf_6, surf_2, surf5_6, trans_6, trans_2, trans5_6;
	private GridTransformMatrix m_6, m_2, m5_6;
	
	protected void createData() {
        super.createData();     // helperArray created, flag0 & flag05 are set
        computePeriodData();
        data.discardGridTransforms();
        GridTransformMatrix[] trList = new GridTransformMatrix[80];
        		trList[0] = new GridTransformMatrix();
        		GridTransformMatrix a = new GridTransformMatrix();
        		trList[1] = new GridTransformMatrix().scale(-1,-1,-1).reverse();
        if (!wantsToSeeDomain)		{ 
        	    data.addGridTransform(trList[1]);
        		a = new GridTransformMatrix().scale(-1,-1,1);
        		trans = new Vector3D(transZ.x, transZ.y, 0);
        		trList[2] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[3] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[2]);
        		data.addGridTransform(trList[3]);
        		trans = new Vector3D(-transZ.x, -transZ.y, 0);
        		trList[4] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[5] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[4]);
        		data.addGridTransform(trList[5]);
        		a = new GridTransformMatrix().scale(-1,1,-1);
        		trans = new Vector3D(transY.x, 0, transY.z);
        		trList[6] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[7] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[6]);
        		data.addGridTransform(trList[7]);
        		trans = new Vector3D(-transY.x, 0, -transY.z);
        		trList[8] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[9] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[8]);
        		data.addGridTransform(trList[9]);
        		a = new GridTransformMatrix().scale(1,-1,-1);
        		trans = new Vector3D(0, transX.y, transX.z);
        		trList[10] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[11] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[10]);
        		data.addGridTransform(trList[11]);
        		trans = new Vector3D(0, -transX.y, -transX.z);
        		trList[12] = new GridTransformMatrix(trList[0]).leftMultiplyBy(a).translate(trans);
        		trList[13] = new GridTransformMatrix(trList[1]).leftMultiplyBy(a).translate(trans);
        		data.addGridTransform(trList[12]);
        		data.addGridTransform(trList[13]);
        	
        		trList[14] = new GridTransformMatrix(trList[0]).leftMultiplyBy(m_6).translate(trans_6);
        		trList[15] = new GridTransformMatrix(trList[1]).leftMultiplyBy(m_6).translate(trans_6);
        		data.addGridTransform(trList[14]);
        		data.addGridTransform(trList[15]);
        		trans = new Vector3D(trans_6.negated());
        		trList[16] = new GridTransformMatrix(trList[0]).leftMultiplyBy(m_6).translate(trans);
        		trList[17] = new GridTransformMatrix(trList[1]).leftMultiplyBy(m_6).translate(trans);
        		data.addGridTransform(trList[16]);
        		data.addGridTransform(trList[17]);
        		trList[18] = new GridTransformMatrix(trList[0]).leftMultiplyBy(m_2).translate(trans_2);
        		trList[19] = new GridTransformMatrix(trList[1]).leftMultiplyBy(m_2).translate(trans_2);
        		data.addGridTransform(trList[18]);
        		data.addGridTransform(trList[19]);
        		trans = new Vector3D(trans_2.negated());
        		trList[20] = new GridTransformMatrix(trList[0]).leftMultiplyBy(m_2).translate(trans);
        		trList[21] = new GridTransformMatrix(trList[1]).leftMultiplyBy(m_2).translate(trans);
        		data.addGridTransform(trList[20]);
        		data.addGridTransform(trList[21]);
        		trList[22] = new GridTransformMatrix(trList[0]).leftMultiplyBy(m5_6).translate(trans5_6);
        		trList[23] = new GridTransformMatrix(trList[1]).leftMultiplyBy(m5_6).translate(trans5_6);
        		data.addGridTransform(trList[22]);
        		data.addGridTransform(trList[23]);
        		trans = new Vector3D(trans5_6.negated());
        		trList[24] = new GridTransformMatrix(trList[0]).leftMultiplyBy(m5_6).translate(trans);
        		trList[25] = new GridTransformMatrix(trList[1]).leftMultiplyBy(m5_6).translate(trans);
        		data.addGridTransform(trList[24]);
        		data.addGridTransform(trList[25]);
        		
        		if (getNumberOfPieces()==2) {	
        			trans = new Vector3D(trans_2.negated());
        		trList[26] = new GridTransformMatrix(trList[12]).leftMultiplyBy(m_2).translate(trans);
        		trList[27] = new GridTransformMatrix(trList[13]).leftMultiplyBy(m_2).translate(trans);
        		data.addGridTransform(trList[26]);
        		data.addGridTransform(trList[27]);
        		trList[28] = new GridTransformMatrix(trList[2]).leftMultiplyBy(m_6).translate(trans_6);
        		trList[29] = new GridTransformMatrix(trList[3]).leftMultiplyBy(m_6).translate(trans_6);
        		      // trans = new Vector3D(trans_2.negated());
        		data.addGridTransform(trList[28]);//
        		data.addGridTransform(trList[29]);
        		trList[30] = new GridTransformMatrix(trList[6]).leftMultiplyBy(m5_6).translate(trans5_6);
        		trList[31] = new GridTransformMatrix(trList[7]).leftMultiplyBy(m5_6).translate(trans5_6);
        		data.addGridTransform(trList[30]);//
        		data.addGridTransform(trList[31]);
        		trList[32] = new GridTransformMatrix(trList[6]).leftMultiplyBy(m_6).translate(trans_6);
        		trList[33] = new GridTransformMatrix(trList[7]).leftMultiplyBy(m_6).translate(trans_6);
        		data.addGridTransform(trList[32]);//
        		data.addGridTransform(trList[33]);
        		trList[34] = new GridTransformMatrix(trList[4]).leftMultiplyBy(m_2).translate(trans_2);
        		trList[35] = new GridTransformMatrix(trList[5]).leftMultiplyBy(m_2).translate(trans_2);
        		data.addGridTransform(trList[34]);
        		data.addGridTransform(trList[35]);
        		// The following pieces stick out as flags:
        		// //trList[26] = new GridTransformMatrix(trList[4]).leftMultiplyBy(m_6).translate(trans);
        		// //trList[27] = new GridTransformMatrix(trList[5]).leftMultiplyBy(m_6).translate(trans_6);
        		// //trList[26] = new GridTransformMatrix(trList[2]).leftMultiplyBy(m5_6).translate(trans);
        		// //trList[27] = new GridTransformMatrix(trList[3]).leftMultiplyBy(m5_6).translate(trans5_6);
        		//trList[26] = new GridTransformMatrix(trList[2]).leftMultiplyBy(m_2).translate(trans);
        		//trList[27] = new GridTransformMatrix(trList[3]).leftMultiplyBy(m_2).translate(trans_2);
        		//trList[26] = new GridTransformMatrix(trList[6]).leftMultiplyBy(m_2).translate(trans);
        		//trList[27] = new GridTransformMatrix(trList[7]).leftMultiplyBy(m_2).translate(trans_2);
        		//trList[36] = new GridTransformMatrix(trList[4]).leftMultiplyBy(m5_6).translate(trans);
        		//trList[37] = new GridTransformMatrix(trList[5]).leftMultiplyBy(m5_6).translate(trans5_6);
        		//data.addGridTransform(trList[36]);
        		//data.addGridTransform(trList[37]);
        		
        		} // if (getNumberOfPieces()==2)
        	 	
          /*   a = new GridTransformMatrix().scale(-1,-1,-1);
        			trans = new Vector3D(0, 0, 0);
        			for (int e=0; e < 38; e++){
        				trList[38+e] = new GridTransformMatrix(trList[e]).leftMultiplyBy(a).translate(trans);
                		data.addGridTransform(trList[38+e]);	
        			}  */
        		
        	if (!inAssociateMorph)  {	
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
		AFP = afp.getValue();
		Phi1 = Math.PI/3.0;
		Phi2 = Math.PI*2.0/3.0;
		rp1  = Math.tan(Phi1/2.0);
		RP2  = Math.tan(Phi2/2.0);
		r1_  = rp1 - 1.0/rp1;
		R2_  = RP2 - 1.0/RP2;
		scale= 1.0;
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
		ComplexVector3D aux = new ComplexVector3D(helperToMinimal(helperArray[ucount-1][(int)Math.floor(0)]));
		transZ = aux.re().times(2*Math.cos(AFP)).plus(aux.im().times(2*Math.sin(AFP)));
		aux = new ComplexVector3D(helperToMinimal(helperArray[ucount-1][(int)Math.floor(vcount*1.0/3.0)]));
		transX = aux.re().times(2*Math.cos(AFP)).plus(aux.im().times(2*Math.sin(AFP)));
		aux = new ComplexVector3D(helperToMinimal(helperArray[ucount-1][(int)Math.floor(vcount*2.0/3.0)]));
		transY = aux.re().times(2*Math.cos(AFP)).plus(aux.im().times(2*Math.sin(AFP)));
		surf_6 = surfacePoint(umax.getValue(),1.0/6.0);
		N_6 = surfaceNormal(umax.getValue(),1.0/6.0);
		double sc = surf_6.dot(N_6);
		trans_6 = surf_6.linComb(2,-2*sc, N_6);
		m_6 = GridTransformMatrix.SetGridTransformMatrix(0,0,1,0, 0,1,0,0, 1,0,0,0, true);
		surf_2 = surfacePoint(umax.getValue(),1.0/2.0);
		N_2 = surfaceNormal(umax.getValue(),1.0/2.0);
		sc = surf_2.dot(N_2);
		trans_2 = surf_2.linComb(2,-2*sc, N_2);
		m_2 = GridTransformMatrix.SetGridTransformMatrix(0,-1,0,0, -1,0,0,0, 0,0,1,0, true);
		surf5_6 = surfacePoint(umax.getValue(),5.0/6.0);
		N5_6 = surfaceNormal(umax.getValue(),5.0/6.0);
		sc = surf5_6.dot(N5_6);
		trans5_6 = surf5_6.linComb(2,-2*sc, N5_6);
		m5_6 = GridTransformMatrix.SetGridTransformMatrix(1,0,0,0, 0,0,1,0, 0,1,0,0, true);
        //System.out.println(N5_6.x);System.out.println(N5_6.y);System.out.println(N5_6.z);
        //System.out.println(surf5_6.x);System.out.println(surf5_6.y);System.out.println(surf5_6.z);
	}

}


