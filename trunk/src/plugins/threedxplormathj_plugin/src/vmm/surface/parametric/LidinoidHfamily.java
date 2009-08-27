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

public class LidinoidHfamily extends WeierstrassMinimalSurface {
// This program is presently experimentally. The Lidinoid is not fully understood	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.LidinoidHfamily.aa",0.5353,0.3,0.8);
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.surface.parametric.LidinoidHfamily.bb",0.71399180,0.5,0.9);
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public LidinoidHfamily() {
		super();
		addParameter(bb);
		bb.setMaximumValueForInput(4.0); // if one wants to morph that far, increase resolution
		bb.setMinimumValueForInput(0.0);
		addParameter(aa);
		aa.setMaximumValueForInput(0.99); // if one wants to morph that far, increase resolution
		aa.setMinimumValueForInput(0.01);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		setDefaultViewUp(new Vector3D(-0.52,  0.84,  -0.15));
		setDefaultViewpoint(new Vector3D(-13, 0.33, 47.4));
		setDefaultWindow(-4,4,-3.5,3.1);
			uPatchCount.setValueAndDefault(18);
			vPatchCount.setValueAndDefault(12);
			umin.reset(0.005);
			umax.reset(0.9995);
			vmin.reset(0.0); 
			vmax.reset(2.0);
		removeParameter(vmin);
		removeParameter(vmax);
		removeParameter(umin);
		removeParameter(umax);
		afp.reset(Math.PI/2*0.71399180); //,Math.PI/2*0.681,Math.PI/2*0.772); // for Lidinoid
		iFirstInHelper = false;
		iBeginMiddleInHelper = true;
		// change the next two booleans for testing (or later for education)
		wantsToSeeDomain = false;//true;//
		wantsToSeeGaussImage = false;//true;//
		if (wantsToSeeGaussImage)  wantsToSeeDomain = true;
		if (wantsToSeeDomain)		 {setDefaultViewUp(new Vector3D(0,0,1));
	  	                           setDefaultViewpoint(new Vector3D(0,0,40));}
		multipleCopyOptions = new int[] {2,3};
		//canShowConjugateSurface = true;
	}

	private boolean adaptAFP = true; // used in createHelperArray
	private double gbranch,a3,scale;
	private Vector3D trans, transY, transZ, surf_1, surf_2, surf_3, norm_3;
	private GridTransformMatrix Msym;
// ===================================== // =================================== //
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
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		   // overridden to keep state of showConjugateSurfaceToggle in sync with the value of afp.
		super.parameterChanged(param, oldValue, newValue);
	     if (param == afp);
	    	 	adaptAFP = false;  // used in createHelperArray to disallow adaption of AFP to close period.
	    	 if (needsValueArray)  adaptAFP = true; // always correct AFP if an essential parameter changed
	}

	protected void createData() {
        super.createData();    // helperArray created, flag0 & flag05 are set
        data.discardGridTransforms(); 
        computePeriodData();
        GridTransformMatrix[] trList = new GridTransformMatrix[64];
        		trList[0] = new GridTransformMatrix();
        		// GridTransformMatrix b = new GridTransformMatrix().scale(-1,1,-1).reverse();
        		GridTransformMatrix Z120 = new GridTransformMatrix().rotateZ(120);
        if (!wantsToSeeDomain)		{
        	for (int e=0; e < 2; e++){
        		trList[1+e] = new GridTransformMatrix(trList[e]).leftMultiplyBy(Z120);
        		data.addGridTransform(trList[1+e]); 
        		}
        	for (int e=0; e < 3; e++){
        		trList[3+e] = new GridTransformMatrix(trList[e]).scale(-1,-1,-1).reverse();
        		data.addGridTransform(trList[3+e]); 
        		}
        		trList[6] = new GridTransformMatrix(trList[0]).scale(-1,-1,-1).reverse().translate(surf_1);
        		data.addGridTransform(trList[6]);
        		trList[7] = new GridTransformMatrix(trList[4]).scale(-1,-1,-1).reverse().translate(surf_1);
        		data.addGridTransform(trList[7]);
        	for (int e=0; e < 4; e++){
        		trList[8+e] = new GridTransformMatrix(trList[6+e]).leftMultiplyBy(Z120);
        		data.addGridTransform(trList[8+e]);	
        		}
        	for (int e=0; e < 6; e++){
        		trList[12+e] = new GridTransformMatrix(trList[6+e]).scale(-1,-1,-1).reverse();
        		data.addGridTransform(trList[12+e]);	
        		}
    	if ((getNumberOfPieces()==2)||(getNumberOfPieces()==3)) {
        		trList[18] = new GridTransformMatrix(trList[0]).leftMultiplyBy(Msym);
        		data.addGridTransform(trList[18]);
        		trList[19] = new GridTransformMatrix(trList[7]).leftMultiplyBy(Msym);
        		data.addGridTransform(trList[19]);
        for (int e=0; e < 4; e++){
            	trList[20+e] = new GridTransformMatrix(trList[18+e]).leftMultiplyBy(Z120);
            	data.addGridTransform(trList[20+e]); 
            	}
        for (int e=0; e < 6; e++){
            	trList[24+e] = new GridTransformMatrix(trList[18+e]).scale(-1,-1,-1).reverse();
            	data.addGridTransform(trList[24+e]); 
            	}
        	  } // if (getNumberOfPieces()==2)
    	if ((getNumberOfPieces()==3)&&(!inAssociateMorph)) {
    			trList[30] = new GridTransformMatrix(trList[6]).leftMultiplyBy(Msym);
    			data.addGridTransform(trList[30]);
    			trList[31] = new GridTransformMatrix(trList[4]).leftMultiplyBy(Msym);
    			data.addGridTransform(trList[31]);
    			//trList[32] = new GridTransformMatrix(trList[2]).leftMultiplyBy(Msym);// further out
        for (int e=0; e < 4; e++){
        		trList[32+e] = new GridTransformMatrix(trList[30+e]).leftMultiplyBy(Z120);
        		data.addGridTransform(trList[32+e]); 
        		}
    		 } // if (getNumberOfPieces()==3)
        } // (!wantsToSeeDomain)
	}
	
	public static double paramRescaleEnd(double x){
		double y;
		y = Math.sin(Math.PI/2*x);
		return y;
	}
	public static double paramRescaleStart(double x){
		double y;
		y = 1-Math.cos(Math.PI/2*x);
		return y;
	}
	public static double paramRescaleBoth(double x){
		double y;
		y = (1.0-Math.cos(Math.PI/2*x))/2.0;
		return y;
	}
	
	// override the default Cartesian Grid
	protected Complex domainGrid(double u, double v){
		double deps = 1.0/2048.0;
		double ph = 0;
		double r = 0;
		Complex z;
			
		    if (u <= 2.0/3.0)	
		    		r = gbranch*paramRescaleBoth(3*u);
			else if (u<=1)
				r = gbranch + (1.0 - gbranch)*paramRescaleStart(3*u-2.0);
		    ph = 2*Math.PI/3.0*paramRescaleBoth(v);
		    
			if (Math.abs(u-2.0/3.0)<deps) {
	 		if (v <= 2*deps)
	 			ph = 2*Math.PI/3.0*paramRescaleBoth(+deps - 0.5*(deps - v));
	 		else if ((2-v) <= 2*deps)
	 			ph = 2*Math.PI/3.0*paramRescaleBoth(2-deps + 0.5*(deps - (2-v)));
			}
			z = new Complex(r*Math.cos(ph), r*Math.sin(ph));
			if (wantsToSeeGaussImage) z = mu(z);
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
		return new Complex(z);		
	    }
	
	protected Complex mu(Complex z)  {
		Complex z3 = new Complex(z.integerPower(3));
		Complex w3 = z3.inverse();
		Complex m2 = z3.plus(w3);   
				m2.re = - m2.re + a3;  m2.im = -m2.im;
				m2.assignInvert();
		Complex m; 
		m = m2.squareRootNearer(ONE_C);
		return m;		
	    }
	
	protected Complex hPrime(Complex z) {
		// return mu(z)/gauss(z)
		Complex w = mu(z);
		        w.assignDivide(z);
		        w.assignTimes(scale);
		return w;
		}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		gbranch = aa.getValue();
		a3 = gbranch*gbranch*gbranch;   a3 = a3 + 1/a3;
		scale= Math.sqrt(0.5/gbranch);
	}
	
	protected double findGoodAFP(double x){   // this interpolation is no longer used, see closePeriodWithAFP()
		double a = Math.PI/2*0.713;
		double s = 0.0;
		if (x <= 0.5353) {
			s = (x-0.3)/(0.5353-0.3);
			a = Math.PI/2*0.71399180*s + Math.PI/2*0.6814925*(1-s);
		}
		else {
			s = (x-0.8)/(0.5353-0.8);
			a = Math.PI/2*0.71399180*s + Math.PI/2*0.771777*(1-s);
		}
		return a;
	}
	
	/**
	 * We want to center the surface (one hexagon) already at the helper Level.
	 */
	protected ComplexVector3D getCenter(){
		// gC is computed by assuming that half the i=0 parameter line is a 30 degree circle arc

		double fac = 0.5/Math.sin(Math.PI/12.0);
		Complex rot = new Complex(fac*Math.cos(Math.PI*5.0/12.0), fac*Math.sin(Math.PI*5.0/12.0));
		Vector3D aux;
		ComplexVector3D MS0 = new ComplexVector3D(helperToMinimal(helperArray[0][0]));
		ComplexVector3D MSE = new ComplexVector3D(helperToMinimal(helperArray[0][(int)Math.floor(vcount/2)]));
		ComplexVector3D base30 = MSE.minus(MS0);
					   base30.assignTimes(rot);
					   base30.assignPlus(MS0);  base30.z.re = 0.0;
		ComplexVector3D gC = new ComplexVector3D(minimalToHelper(base30));
		return gC;
	}
	
	public void computePeriodData(){
		surf_1 = surfacePoint(1.5/3.0, 0);
		transY = new Vector3D(2*surf_1.x, 0, 0);
		
		surf_3 = surfacePoint(1.0, 1.0); 
		transZ = new Vector3D(0, 0, 2*surf_3.z);
		
		surf_1 = surfacePoint(2.0/3.0, 0);  surf_1.assignTimes(2.0);
		norm_3 = surfaceNormal(1.0, 1.0);
		// These two vectors have to be orthogonal for the correct AFP
		double sc = norm_3.dot(surf_3);
		trans = new Vector3D(surf_3.x-sc*norm_3.x, surf_3.y-sc*norm_3.y, surf_3.z-sc*norm_3.z);
		trans.assignTimes(2.0);
		double c120 = Math.cos(2*Math.PI/3.0);
		double s120 = Math.sin(2*Math.PI/3.0);
		Msym = GridTransformMatrix.SetGridTransformMatrix(c120,s120,0,trans.x, s120,-c120,0,trans.y, 0,0,-1,trans.z,false);
        //System.out.println(norm_3.dot(surf_1)); System.out.println(2*AFP/Math.PI);//System.out.println(2/Math.PI*cAFP); 
	}
	
	protected double closePeriodWithAFP(){   // used in override createHelperArray
		double savedAFP = AFP;
		Vector3D n3 = surfaceNormal(1.0, 1.0);
		AFP = 0.0;
		Vector3D sP0 = surfacePoint(2.0/3.0, 0);
		AFP = Math.PI/2;
		Vector3D sP1 = surfacePoint(2.0/3.0, 0);
		double a0 = n3.dot(sP0);
		double a1 = n3.dot(sP1);
		double a = Math.PI/2-Math.atan(-a1/a0);
		AFP = savedAFP;
		return a;
	}
	
	public void createHelperArray(){
		super.createHelperArray();
		double cAFP = closePeriodWithAFP();
		if (adaptAFP)    {
			afp.setValue(cAFP);
			AFP = afp.getValue(); }  
		adaptAFP = true;
	}
	
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("vmm.surface.parametric.LidinoidHfamily.AssociateMorph")) {
			public void actionPerformed(ActionEvent evt) {
				BasicAnimator animation = new BasicAnimator(); // has to be final for use in the actionPerformed method below.
				animation.setLooping(BasicAnimator.OSCILLATE);  // if not set, the animation will only play once through
				//animation.setLooping(BasicAnimator.LOOP);
				// We want to use "inAssociateMorph==true" to modify helperArray:
				needsValueArray = true; createHelperArray();
				animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
				final int numFrames = getFramesForMorphing();
				setFramesForMorphing(24);
				animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
				animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100.
				double aS = Math.PI/2*bb.getAnimationStart();
				double aE = Math.PI/2*bb.getAnimationEnd();
				animation.addWithCustomLimits(afp, aS, aE);
				view.setShowAxes(true);
				animation.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent evt) {
						if (((BasicAnimator)evt.getSource()).isRunning())
							inAssociateMorph = true;  // This happens when the animation starts.
						else{
							inAssociateMorph = false; // This will happen when the animation ends.
							needsValueArray = true; createHelperArray();
							setFramesForMorphing(numFrames);
							view.setShowAxes(false);
							}
					}	
				}); 
				view.getDisplay().installAnimation(animation);
			}
		});
		return actions;
	}  
}


