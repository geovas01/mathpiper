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
import vmm.actions.ActionRadioGroup;
import vmm.actions.ToggleAction;
import vmm.core.BasicAnimator;
import vmm.core.Complex;
import vmm.core.I18n;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.ComplexVector3D;
import vmm.core3D.Vector3D;
import vmm.surface.SurfaceView;

/**
  Abstract Weierstrass Minimal Surface
 */
abstract public class WeierstrassMinimalSurface extends SurfaceParametric {
	
	final public static Complex ZERO_C = new Complex(0,0);
	final public static Complex ONE_C =  new Complex(1.0,0.0);
	final public static Complex ONE__C =  new Complex(-1.0,0.0);
	final public static Complex IP_C =    new Complex(0.0,Math.PI);
	final public static Complex IP__C =    new Complex(0.0,-Math.PI);
	final public static Complex I_C =    new Complex(0.0,1.0);
	final public static Complex I__C =   new Complex(0.0,-1.0);
	final public static Complex I1_C =   new Complex(Math.sqrt(0.5),Math.sqrt(0.5));
	final public static Complex I2_C =   new Complex(-Math.sqrt(0.5),Math.sqrt(0.5));
	final public static Complex I3_C =   new Complex(-Math.sqrt(0.5),-Math.sqrt(0.5));
	final public static Complex I4_C =   new Complex(Math.sqrt(0.5),-Math.sqrt(0.5));
	final protected static Complex del = new Complex(0.00000001,0.0);
	
	public RealParamAnimateable afp = new RealParamAnimateable("vmm.surface.parametric.WeierstrassMinimalSurface.AssocFamParam",0.0,0.0,0.0);
	
	
	/**
	 * Set this variable to true in the constructor of a subclass to add the
	 * showConjugagteSurfaceAction  to the Action menu for that subclass.
	 */
	protected boolean canShowConjugateSurface = false;
	
	/**
	 * The number of copies of the basic surface that are to be displayed.  The default value is 1.
	 * Any other legal values should be specified in the {@link #multipleCopyOptions} array.
	 */
	@VMMSave private int numberOfPieces = 1;

	/**
	 * Some subclasses of WeirstrassMinimalSurface offer the possibility of showing different
	 * numbers of copies of the fundamental piece.  The default view shows a certain number of
	 * pieces, and there can be options for showing double, triple, etc. that nubmer of pieces.
	 * This array can be set to a non-null value in the constructor of a subclass to indicate
	 * that such options are available.  The integers in the array should give the allowable
	 * multiples of the default number of copies.  For example, setting multipleCopySelections
	 * to {2} indicates that double the basic number of copies can be shown.  Setting it
	 * to {2,3} indicates that double or triple the number of copies can  be shown.
	 */
	protected int[] multipleCopyOptions = null;
	
 
	public WeierstrassMinimalSurface() {
		setFramesForMorphing(20); // reset in AssociateMorph
		uPatchCount.setValueAndDefault(12);
		vPatchCount.setValueAndDefault(12);
		umin.reset(-1.5); 
		umax.reset(1.5);
		vmin.reset(-1.5);
		vmax.reset(1.5);
		setDefaultViewpoint(new Vector3D(20.0,-20.0,20.0));
		setDefaultWindow(-4,4,-4,4);
		addParameter(afp);
	}
	
	protected int ucount,vcount; // set in redoConstants
	protected boolean iFirstInHelper = true;
	protected boolean iBeginMiddleInHelper = false;
	protected double du,dv; // set in redoConstants
	protected double distFrom0; // used in helperArray
	protected double AFP = afp.getValue();
	protected double LRPclosed = 1;
	protected ComplexVector3D[][] helperArray;  // initialized in redoConstants, set in createData
	protected ComplexVector3D halfPeriod=new ComplexVector3D(ComplexVector3D.ORIGIN);
	protected boolean flag0, flag05;  // set in createData
	protected boolean wantsToSeeDomain = false;
	protected boolean wantsToSeeGaussImage = false;
	protected boolean needsValueArray = true;
	protected boolean needsPeriodClosed = false;
	protected boolean inAssociateMorph = false;
	
	/**
	 * Overrides createData from SurfaceParametric with the goal of recomputing all
	 * constants that go into the Weierstrass Data, and then do the helperArray.
	 * The recomputation should be done before super.createData() is called.
	 */
	protected void createData() {
		flag0 = ((Math.abs(afp.getValue()) < 0.001)||(Math.abs(afp.getValue()-Math.PI) < 0.001)||(Math.abs(afp.getValue()-2*Math.PI) < 0.001));
		flag05=((Math.abs(afp.getValue()+Math.PI/2) < 0.001)||(Math.abs(afp.getValue()-Math.PI/2) < 0.001));
		if (needsValueArray) {
	        redoConstants();
			createHelperArray();
			System.out.println("created helperArray");
			
			if (needsPeriodClosed){
				doClosingJob();
				needsPeriodClosed = false;
			}
			needsValueArray = false;  
			computeHalfPeriod(); // periods for periodic surfaces. 
			// Needs to be called before super.createData(); because it is used in surfacePoint overrides
		}
		super.createData();
	}
	
/**
 * Most minimal surfaces need a specially adapted domain grid.
 * The default grid is the standard cartesian one.
 */
	protected Complex domainGrid(double u, double v){
		return new Complex(u,v);
	}
	
//	 ************************** Weierstrass Data start ************************************** //	
	/**
	 * The following two abstract functions are the Weierstrass Data, they
	 * need to be specified to define a minimal surface.
	 * Locally, no other information is needed.
	 */
	abstract protected Complex gauss(Complex z);
	
	abstract protected Complex hPrime(Complex z);
//	 ************************** Weierstrass Data end **************************************** //		
	
	/**
	 * The next function is used to center the helperArray, mostly overridden
	 */
	protected ComplexVector3D getCenter(){
		int um = (int) Math.floor((ucount-1)/2.0);
		int vm = (int) Math.floor((vcount-1)/2.0);
		return new ComplexVector3D(helperArray[um][vm]);
	}

	/**
	 * Auxiliary function. Intersects the lines through Complex z1,z2 and through Complex w1,w2.
	 */
	protected Complex intersectLines(Complex z1,Complex z2, Complex w1, Complex w2){
		// z1 + s(z2-z1) = w1 + t(w2-w1)
		Complex wPerp = w2.minus(w1).times(I_C);
		Complex zDiff = z2.minus(z1);
		double s = (w1.minus(z1)).dot(wPerp)/zDiff.dot(wPerp);
		return z1.plus(zDiff.times(s));
	}

	/**
	 * Auxiliary function. monotonPow(u,e) = u*|u|^(e-1)
	 */
	protected static double monotonPow(double u, int e){
		double w = 1.0;
		if (e > 0) w = u;
		if (e > 1) for (int i=2; i <= e; i++) {   w = w*Math.abs(u);  }
		return w;
	}
	
	/**
	 * Dummy function. Needs to be specified if (needsPeriodClosing) is ever TRUE
	 */
	protected void doClosingJob(){
	}
	
/*	protected void adjustLopezRosInHelper(double ro){
	// Does not work in this simple form, since getCenter also depends on ro.
		for (int i = 0; i < ucount ; i++) 
			for (int j = 0; j < vcount ; j++){
				helperArray[i][j].x.assignTimes(ro);
				helperArray[i][j].y.assignTimes(1.0/ro);
			}
	}  */
	
	/**
	 * redoConstants is called in createData above, before any other computations start.
	 */
	protected void redoConstants(){
		ucount = 1+uPatchCount.getValue()*6;
		vcount = 1+vPatchCount.getValue()*6;
		helperArray = new ComplexVector3D [ucount][vcount];
		du = (umax.getValue() - umin.getValue())/(ucount-1);
		dv = (vmax.getValue() - vmin.getValue())/(vcount-1);
	}
	
	protected Complex gaussTimesHPrime(Complex z){
		Complex w = gauss(z);
		w.assignTimes(hPrime(z));
		return w;
	}
	
	
	/*
	 * The next two functions are abbreviation. Often one can take advantage of cancellations.
	 */
	protected Complex gaussInverseTimesHPrime(Complex z) {
		 if ((gauss(z).re == 0.0) && (gauss(z).im == 0.0)) 
			 return gauss(z.plus(del)).inverse().times(hPrime(z.plus(del)));
	    else {
	    		Complex w = hPrime(z);
			w.assignDivide(gauss(z));
			return w;
	    }
	}
	
	/**  Integrate g*dh, 1/g*dh and compute first and second component of the Weierstrass
	 *  integral \int ( 1/g - g, (1/g + g)*I_C, 2) * dh after the integration
	 */	
	
	protected Complex component3(Complex z) {
		Complex w = hPrime(z);
		w.assignTimes(2);
		return w;
		//return hPrime(z).times(2.0);	 
	} 
	
	/**
	 * The surface normal is called often while rendering the surface.
	 * For Weierstrass surfaces the normal is known well before the surface
	 * points are known. The normal should never be computed numerically.
	 * Therefore the following overrides the numerical differentiation.
	 */
	public Vector3D surfaceNormal (double u, double v) {
		Complex z = domainGrid(u,v);
		Complex g = gauss(z);
		Vector3D VV = new Vector3D(g.re*2.0, g.im*2.0, +g.abs2() - 1.0 );
		//System.out.println("In override Gauss"); // presently gaussMap is not used
		double norm = VV.norm();
		if (Double.isInfinite(norm) || Double.isNaN(norm) || norm == 0) { 
			VV.x = 1.0;
			VV.y = 0.0;
			VV.z = 0.0;
		}
		else
			VV.normalize();
		return VV;
	}
	
	/**
	 * The auxiliary Weierstrass Integrand: ComplexVector3D(dh*g, dh/g, 2dh)
	 * See helperToMinimal for converting the integral values into surface points.
	 * Weierstrass  integral: \int ( 1/g - g, (1/g + g)*I_C, 2) * dh 
	 */ 
	protected  ComplexVector3D ComplexVectorFunction(Complex z){
		Complex g,h;
		if (wantsToSeeDomain) {
			g = new Complex(ONE_C);  h = new Complex(ONE_C);
		} else {
			g = gauss(z);  h = hPrime(z);
		}
		   return new ComplexVector3D(h.times(g), h.dividedBy(g), h.times(2.0));
   }
	
	
	 final static double weight1 = 32.0/90.0;  // 32/90*QuadTang
	 final static double weight2 = 9.0/90.0/2; //  9/90*QuadSecant
	 final static double weight3 = 49.0/90.0/2;// 49/90*QuadGauss
	 final static double sqrtOf3Over28= Math.sqrt(3.0/28.0);
	 final static double almost0 = 1.0/4096/4096; // has to be smaller than differences in numerical differentiation
	 
	 
	   public  ComplexVector3D ComplexVectorOneStepIntegrator(Complex zInitial, Complex zFinal){
			 // Integrates polynomials of order up to 7 correctly
		     // Minimizes number of created objects
		     ComplexVector3D w = new ComplexVector3D();
			 
		     //if (!(zInitial.equals(zFinal))) {    //if ((Math.abs(dz.re)+(Math.abs(dz.im)) > almost0) {  
		     // the second if is faster but riskier with numerical differentiation
			 Complex dz = zFinal.minus(zInitial);
			 if ((Math.abs(dz.re)+Math.abs(dz.im)) > almost0) {
			 Complex zMiddle = zInitial.plus(zFinal);  zMiddle.assignTimes(0.5);
			 Complex zGaussRight = dz.times(sqrtOf3Over28);
			 Complex zGaussLeft =  zMiddle.minus(zGaussRight);
			 zGaussRight.assignPlus(zMiddle);   // zMiddle + dz*sqrtOf3Over28
		     
			 ComplexVector3D aux;
			 w = this.ComplexVectorFunction(zMiddle);       
			 w.assignTimes(weight1);
			   aux = this.ComplexVectorFunction(zInitial);
			   aux.assignPlus(this.ComplexVectorFunction(zFinal));
			   aux.assignTimes(weight2);       // (f(zI) + f(zF))*weight2
			 w.assignPlus(aux);
			   aux = this.ComplexVectorFunction(zGaussLeft);
			   aux.assignPlus(this.ComplexVectorFunction(zGaussRight));
			   aux.assignTimes(weight3);       // (f(zL) + f(zR))*weight3
			 w.assignPlus(aux);
			 w.assignTimes(dz);
			 }
			 return w;
		}
	   
		public ComplexVector3D ComplexVectorIntegrator(Complex zInitial, Complex zFinal,int numSteps){
			ComplexVector3D w = new ComplexVector3D();
			      // if (!(zInitial.equals(zFinal))) { 
			Complex dz = zFinal.minus(zInitial);
			if ((Math.abs(dz.re)+Math.abs(dz.im)) > almost0) {    // return 0 otherwise
			int newNumSteps  = (int) Math.floor( (1+dz.r())*numSteps );
			dz = dz.times(1.0/newNumSteps);
			for (int j=0; j < (int)newNumSteps; j++){
				Complex z = new Complex( (zInitial.times(1.0*(newNumSteps - j)).plus(zFinal.times(1.0*j)) ).times(1.0/newNumSteps) );
				w = w.plus( this.ComplexVectorOneStepIntegrator(z, z.plus(dz)) );
			    }
			}
			return w;
		}

		/*  The following is without the helperArray:
		 public Vector3D surfacePoint(double u, double v) {
		 	double AFP = afp.getValue();
		 	Complex z = domainGrid(u,v);
		 	ComplexVector3D auxW = new ComplexVector3D (ComplexVectorIntegrator(ZERO_C, z, 4) );
		 	ComplexVector3D eW = helperToMinimal(auxW);	
	     }*/
		
	/**
	 * This function lets a minimal surface that is defined by Weierstrass Data behave as
	 * if it were given by an explicit parametrization.
	 */
		public Vector3D surfacePoint(double u, double v) {
		int i = (int) Math.floor(0.25 + (u-umin.getValue())/du );
		int j = (int) Math.floor(0.25 + (v-vmin.getValue())/dv );
			//Complex zInitial = domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv);
			//Complex z = domainGrid(u,v);
		ComplexVector3D auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));
		ComplexVector3D eW = helperToMinimal(auxW);
		
		if (wantsToSeeDomain)     return new Vector3D(eW.z.re, eW.z.im, 0);
		else if (AFP==0)  return eW.re(); 
		else      return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));    
	}
		
	/**
	 * The integrated values that are collected in ComplexVector3D[][] helperArray are
	 * here converted to points of the holomorphic null curve in C^3 whose real and imaginary
	 * parts are minimal surfaces in R^3.
	 */
	public ComplexVector3D helperToMinimal(ComplexVector3D hp){
		return new ComplexVector3D(hp.y.minus(hp.x), hp.y.plus(hp.x).times(I_C), hp.z);
	}
	
	/**
	 * Inverse function to the previous helperToMinimal.
	 */
	public  ComplexVector3D minimalToHelper(ComplexVector3D mn){
		return new ComplexVector3D((mn.x.plus(mn.y.times(I_C))).times(-0.5), mn.x.minus(mn.y.times(I_C)).times(0.5), mn.z);
	}
	
	
	/**
	 * The helperArray is filled with auxiliary data from which the surface points can be computed
	 * quickly in public Vector3D surfacePoint(double u, double v). 
	 * The routine can either start with the i-line j=0 first and use these values to start the
	 * integration along the j-curves -- or it can do the j-curve i=0 first and compute the i-curves
	 * from there. One tries to have no singular or numerically difficult points on the first curve.
	 */
	public void createHelperArray(){
		distFrom0 = 1;
		int nsteps = 4;  // To increase accuracy on more difficult parameter lines.
		double u = umin.getValue();
		double v = vmin.getValue();
		Complex zInitial = domainGrid(u,v);
		distFrom0 = Math.min(zInitial.r(), distFrom0);
		Complex z;
		helperArray[0][0]= new ComplexVector3D(ZERO_C, ZERO_C, ZERO_C);
		
	if (iFirstInHelper) {
		for (int i = 0; i < ucount-1 ; i++) {
			u = u + du;
			z = domainGrid(u,v);
			helperArray[i+1][0]= helperArray[i][0].plus(ComplexVectorIntegrator(zInitial, z, nsteps) );
			//helperArray[i+1][0]= helperArray[i][0].plus(ComplexVectorOneStepIntegrator(zInitial, z) );
			zInitial = z;
			distFrom0 = Math.min(zInitial.r(), distFrom0);
		}
		u = umin.getValue();
		for (int i = 0; i < ucount ; i++) {
			zInitial = domainGrid(u,v);
			for (int j = 1; j < vcount ; j++){
				v = v + dv;
				z = domainGrid(u,v);
				if ((i==0)||(i==ucount-1))
					helperArray[i][j]= helperArray[i][j-1].plus(ComplexVectorIntegrator(zInitial, z, nsteps) );
				else
					helperArray[i][j]= helperArray[i][j-1].plus(ComplexVectorOneStepIntegrator(zInitial, z) );
				zInitial = z;
				distFrom0 = Math.min(zInitial.r(), distFrom0);
			}
			v = vmin.getValue();
			zInitial = domainGrid(u,v);
			u = u + du;
		}

	} else if (!iBeginMiddleInHelper) {                 // jFirst
		for (int j = 0; j < vcount-1 ; j++) {
			v = v + dv;
			z = domainGrid(u,v);
			helperArray[0][j+1]= helperArray[0][j].plus(ComplexVectorIntegrator(zInitial, z, nsteps) );
			//helperArray[0][j+1]= helperArray[0][j].plus(ComplexVectorOneStepIntegrator(zInitial, z) );
			zInitial = z;
			distFrom0 = Math.min(zInitial.r(), distFrom0);
		}
		v = vmin.getValue();
		for (int j = 0; j < vcount ; j++) {
			zInitial = domainGrid(u,v);
			for (int i = 1; i < ucount ; i++){
				u = u + du;
				z = domainGrid(u,v);
				if ((j==0)||(j==vcount-1)){
					// if (z.r() < 0.125) nsteps = 16; else nsteps = 4;
					helperArray[i][j]= helperArray[i-1][j].plus(ComplexVectorIntegrator(zInitial, z, nsteps) );
				}
				else
					helperArray[i][j]= helperArray[i-1][j].plus(ComplexVectorOneStepIntegrator(zInitial, z) );
				zInitial = z;
				distFrom0 = Math.min(zInitial.r(), distFrom0);
			}
			u = umin.getValue();
			zInitial = domainGrid(u,v);
			v = v + dv;
		}
		} else {  // begin with the middle i-line

			int um = (int) Math.floor(0.5*(ucount-1));
			int vm = (int) Math.floor(0.5*(vcount-1));
			helperArray[0][vm]= new ComplexVector3D(ZERO_C, ZERO_C, ZERO_C);
				u = umin.getValue();
			    v = vmin.getValue() + dv*vm;
			    zInitial = domainGrid(u,v);
			for (int i = 0; i < ucount-1 ; i++) {
				u = u + du;
				z = domainGrid(u,v);
				helperArray[i+1][vm]= helperArray[i][vm].plus(ComplexVectorIntegrator(zInitial, z, 2*nsteps) );
				//helperArray[i+1][vm]= helperArray[i][vm].plus(ComplexVectorOneStepIntegrator(zInitial, z) );
				zInitial = z;
				distFrom0 = Math.min(zInitial.r(), distFrom0);
			}
			u = umin.getValue();
			for (int i = 0; i < ucount ; i++) {
				v = vmin.getValue() + dv*vm;
				zInitial = domainGrid(u,v);
				for (int j = vm+1; j < vcount ; j++){
					v = v + dv;
					z = domainGrid(u,v);
					if ((z.r() < 0.125)||(i==ucount-1))
						helperArray[i][j]= helperArray[i][j-1].plus(ComplexVectorIntegrator(zInitial, z, nsteps) );
					else 
						helperArray[i][j]= helperArray[i][j-1].plus(ComplexVectorOneStepIntegrator(zInitial, z) );
					zInitial = z;
					distFrom0 = Math.min(zInitial.r(), distFrom0);
				}
				v = vmin.getValue() + dv*vm;
				zInitial = domainGrid(u,v);
				for (int j = vm-1; j >= 0 ; j=j-1){
					v = v - dv;
					z = domainGrid(u,v);
					if  ((z.r() < 0.125)||(i==ucount-1))
						helperArray[i][j]= helperArray[i][j+1].plus(ComplexVectorIntegrator(zInitial, z, nsteps) );
					else 
						helperArray[i][j]= helperArray[i][j+1].plus(ComplexVectorOneStepIntegrator(zInitial, z) );
					zInitial = z;
					distFrom0 = Math.min(zInitial.r(), distFrom0);
				}
				u = u + du;
			}
		}
		
		ComplexVector3D hA00 = getCenter();
		for (int i = 0; i < ucount ; i++) 
			for (int j = 0; j < vcount ; j++)
				helperArray[i][j].assignMinus(hA00);  // subtract value at 0 to center image
	}
	
	public void computeHalfPeriod(){
        // This has to be overridden in the individual classes. It has to appear here because
	    // it must be called in createData() before(!!) surfacePoint(u,v) in super.createData. 
		// If the halfPeriod is not used then this routine may stay empty.
	}
	
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("vmm.surface.parametric.WeierstrassMinimalSurface.AssociateMorph")) {
			public void actionPerformed(ActionEvent evt) {
				BasicAnimator animation = new BasicAnimator(); // has to be final for use in the actionPerformed method below.
				//animation.setLooping(BasicAnimator.OSCILLATE);  // if not set, the animation will only play once through
				animation.setLooping(BasicAnimator.LOOP);
				// We want to use "inAssociateMorph==true" to modify helperArray:
				needsValueArray = true; createHelperArray();
				animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
				final int numFrames = getFramesForMorphing();
				setFramesForMorphing(24);
				animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
				animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100.
				animation.addWithCustomLimits(afp, 0, 2*Math.PI);
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
	
	//----------- Implementing the numberOfPieces and showConjugateSurface options --------------
	
	private ToggleAction numberOfPiecesToggle;  // used only if there is exactly one alternative in multipleCopyOptions
	private ActionRadioGroup numberOfPiecesSelect;  // used only if there is more than one alternative in multipleCopyOptions
	private ToggleAction showConjugateSurfaceToggle;  // used only if canShowConjugateSurface is true
	

	public int getNumberOfPieces() {
		return numberOfPieces;
	}

	public void setNumberOfPieces(int numberOfPieces) {
		if (multipleCopyOptions == null || numberOfPieces == this.numberOfPieces)
			return;
		boolean valueOK = false;
		if (numberOfPieces == 1)
			valueOK = true;
		for (int n : multipleCopyOptions)
			if (numberOfPieces == n)
				valueOK = true;
		if (!valueOK)
			return;
		this.numberOfPieces = numberOfPieces;
		if (multipleCopyOptions.length == 1) {
			if (numberOfPiecesToggle != null)
				numberOfPiecesToggle.setState( numberOfPieces > 1 );
		}
		else {
			if (numberOfPiecesSelect != null) {
				if (numberOfPieces == 1)
					numberOfPiecesSelect.setSelectedIndex(0);
				else {
					for (int i = 0; i < multipleCopyOptions.length; i++)
						if (numberOfPieces == multipleCopyOptions[i]) {
							numberOfPiecesSelect.setSelectedIndex(i+1);
							break;
						}
				}
			}
		}
		forceRedraw();
	}
	
	/**
	 * The View class for WeierstrassMinimalSurfaces, overridden *only* to add the
	 * Show Conjugate Surface and number of pieces actions to the correct place in
	 * the Action menu.
	 */
	public class WMSView extends SurfaceView {
		public ActionList getActions() {
			ActionList actions = super.getActions();
			if (canShowConjugateSurface || multipleCopyOptions != null)
				actions.add(null);
			if (canShowConjugateSurface) {
				if (showConjugateSurfaceToggle == null)
					showConjugateSurfaceToggle = new ToggleAction(I18n.tr("vmm.surface.parametric.WeierstrassMinimalSurface.ShowConjugateSurface")) {
					public void actionPerformed(ActionEvent evt) {
						if (getState())
							afp.reset("pi/2");  // changes morphing limits as well as value.
						else
							afp.reset(0);
					}
				};
				actions.add(showConjugateSurfaceToggle);
			}
			if (multipleCopyOptions != null) {
				if (multipleCopyOptions.length == 1) {
					if (numberOfPiecesToggle == null) {
						numberOfPiecesToggle = new ToggleAction(I18n.tr("vmm.surface.parametric.WeierstrassMinimalSurface.ShowMoreCopies")) {
							public void actionPerformed(ActionEvent evt) {
								if (getState())
									setNumberOfPieces(multipleCopyOptions[0]);
								else
									setNumberOfPieces(1);
							}
						};
						numberOfPiecesToggle.setState( numberOfPieces > 1 );
					}
					actions.add(numberOfPiecesToggle);
				}
				else {
					if (numberOfPiecesSelect == null) {
						numberOfPiecesSelect = new ActionRadioGroup() {
							public void optionSelected(int selectedIndex) {
								if (selectedIndex == 0)
									setNumberOfPieces(1);
								else
									setNumberOfPieces(multipleCopyOptions[selectedIndex-1]);
							}
						};
						numberOfPiecesSelect.addItem(I18n.tr("vmm.surface.parametric.WeierstrassMinimalSurface.NumberOfCopies.Default"));
						for (int n : multipleCopyOptions)
							numberOfPiecesSelect.addItem(I18n.tr("vmm.surface.parametric.WeierstrassMinimalSurface.NumberOfCopies.NTimesDefault",""+n));
						if (numberOfPieces == 1)
							numberOfPiecesSelect.setSelectedIndex(0);
						for (int i = 0; i < multipleCopyOptions.length; i++)
							if (numberOfPieces == multipleCopyOptions[i]) {
								numberOfPiecesSelect.setSelectedIndex(i+1);
								break;
							}
					}
					ActionList list = new ActionList(I18n.tr("vmm.surface.parametric.WeierstrassMinimalSurface.NumberOfCopies"));
					list.add(numberOfPiecesSelect);
					actions.add(list);
				}
			} 
			return actions;
		}
	}
	
	
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		   // overridden to keep state of showConjugateSurfaceToggle in sync with the value of afp.
		super.parameterChanged(param, oldValue, newValue);
		if (showConjugateSurfaceToggle != null && param == afp)
			showConjugateSurfaceToggle.setState(Math.abs( Math.PI/2 - afp.getValue()) < 0.001 );
		
	     AFP = afp.getValue();
	     if (param != afp)
	    	 needsValueArray = true;
	}

	public View getDefaultView() {
		return new WMSView();
	}
	
}