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
import vmm.core.BasicAnimator;
import vmm.core.Complex;
import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core.View;
import vmm.core3D.ComplexVector3D;
import vmm.core3D.Vector3D;
import vmm.core3D.GridTransformMatrix;

public class LopezRosNoGo extends WeierstrassMinimalSurface {
	
	//private IntegerParam exponent = new IntegerParam("vmm.surface.parametric.LopezRosNoGo.MainEx",2);
	private RealParamAnimateable pos = new RealParamAnimateable("vmm.surface.parametric.LopezRosNoGo.pos",3,7,1.01);
	private RealParamAnimateable lrp = new RealParamAnimateable("vmm.surface.parametric.LopezRosNoGo.lrp",1.06,1,1);
	
	boolean in2ndMorph = false;
	
	public LopezRosNoGo() {
		super();
		setFramesForMorphing(20);
		afp.reset(0,0,0);
		addParameter(pos);
		pos.setMinimumValueForInput(0.1);
		pos.setMaximumValueForInput(10);
		addParameter(lrp);
		//addParameter(exponent);
		setDefaultViewpoint(new Vector3D(25,50,50));
		setDefaultViewpoint(new Vector3D(-62,-40,10));
		setDefaultWindow(-4,4,-3,3);
			uPatchCount.setValueAndDefault(24);
			vPatchCount.setValueAndDefault(12);
			umin.reset(-1.7,-1.7,-1.7); 
			umax.reset(2.0,2.6,1.6);
			vmin.reset(-0.9999);
			vmax.reset(0.9999);	
		removeParameter(vmax);
		removeParameter(vmin);
		wantsToSeeDomain = false;
		if (wantsToSeeDomain)		 {setDefaultViewUp(new Vector3D(0,0,1));
          setDefaultViewpoint(new Vector3D(0,0,40));}
		//umin.setMaximumValueForInput(-0.05);
		canShowConjugateSurface = false;
	}

	//private int itest;
	private double LRP, POS, r1;
	private Complex q1;
	
    protected void createData() {
        super.createData();
        data.discardGridTransforms();
        GridTransformMatrix tr = new GridTransformMatrix();
        if ((!inAssociateMorph)&&(!wantsToSeeDomain)){
        if (flag0)
        		tr = new GridTransformMatrix().scale(-1,1,-1).reverse();
        else if (flag05)
        		;//tr = new GridTransformMatrix().scale(1,-1,1);
        data.addGridTransform(tr);
        }
    }
	
	// override the default Cartesian Grid
	protected Complex domainGrid(double u, double v){
		// return pos*sqrt(1+exp(u+iv)), with concentration of lines
		Complex z,aux,w;
			double r = Math.exp(u*Math.abs(u));
			double a = Math.PI*Math.sin(v*Math.PI/2);
			z = new Complex(r*Math.cos(a) + 1, r*Math.sin(a));
			w = z.squareRootNearer(ONE_C);
		    return w.times(POS);
	}
	
	protected double closingLRP(double p){
		return 1/Math.sqrt((3*p*p-2)*p*p - 1.0);
	}
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		// return lrp*(z^2 - 1)
		// return new Complex(ONE_C);  // for viewing the domain
		Complex w = z.times(z);
		        w.re = LRP*(w.re - 1);
		        w.im = LRP*w.im;
		return w;
	}
	
	protected Complex hPrime(Complex z) {
		// return new Complex(ONE_C);
		Complex w = z.times(z);
         w.re = r1*(w.re - POS*POS);
         w.im = r1*w.im;
         w.assignTimes(w);
         return gauss(z).dividedBy(w);
	}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		POS  = pos.getValue()/10.0;
		POS  = Math.exp(POS*POS);
		r1 = Math.sqrt(8/pos.getValue()); // controls the size, in denominator of hPrime
		double lrploc = closingLRP(POS);
		LRP = lrp.getValue();
		//lrp.reset(LRP,lrploc,lrploc+1);
		if ( !in2ndMorph){
		    LRP = lrploc;   // this does not allow to see the period opening
		}
		else{
			r1 = r1*Math.sqrt(pos.getValue()/3.5)*1.1;
		}
	}
	
	protected ComplexVector3D getCenter(){
		if ((inAssociateMorph)||(wantsToSeeDomain)){
			int um = (int) Math.floor((ucount-1)/2.0);
			int vm = (int) Math.floor((vcount-1)/2.0);
			return new ComplexVector3D(helperArray[um][vm]);
		}
		else {
			ComplexVector3D gC = new ComplexVector3D(helperArray[ucount-1][vcount-1]);
			return (gC.plus(helperArray[ucount-1][0])).times(0.5);  // a bit accidental
		}
	}

	/**
	 * Override  surfacePoint  to close the hole around the center critical point of Gauss map.
	 */
	public Vector3D surfacePoint(double u, double v) {
		ComplexVector3D eW;
		int i = (int) Math.floor(0.25 + (u-umin.getValue())/du );
		int j = (int) Math.floor(0.25 + (v-vmin.getValue())/dv );
		//Complex zInitial = domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv);
		Complex z = domainGrid(u,v);
		
		ComplexVector3D auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), z) ));
		eW = helperToMinimal(auxW); 
		if ((z.r() < 1.01*distFrom0)&&( !inAssociateMorph)&&(!wantsToSeeDomain)  ) {
				eW.x = new Complex(ZERO_C); eW.z = new Complex(ZERO_C);
				eW.y = new Complex((helperArray[0][j].y.plus(helperArray[0][j].x)).times(I_C)); // correction of saddle
				/*	double r;  // for correction debugging
					System.out.println(i);System.out.println(j);
					r = helperArray[i-8][j].y.im+helperArray[i-8][j].x.im; System.out.println(r);
					r = helperArray[i-4][j].y.im+helperArray[i-4][j].x.im; System.out.println(r);
					r = helperArray[i-0][j].y.im+helperArray[i-0][j].x.im; System.out.println(r);
					r = helperArray[i-4][j].y.im+helperArray[i+4][j].x.im; System.out.println(r);
					r = helperArray[i-0][j].y.im+helperArray[i+8][j].x.im; System.out.println(r); */
			} 
			
		if (wantsToSeeDomain)     return new Vector3D(eW.z.re, eW.z.im, 0);
		else if (AFP==0)  return eW.re(); 
		else      return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));     
	}
 
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("vmm.surface.parametric.LopezRosNoGo.PeriodOpenMorph")) {
			public void actionPerformed(ActionEvent evt) {
				BasicAnimator animation = new BasicAnimator(); // has to be final for use in the actionPerformed method below.
				animation.setLooping(BasicAnimator.OSCILLATE);  // if not set, the animation will only play once through
				animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
				animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
				animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100.
				animation.addWithCustomLimits(pos, 3.5, 1);
				animation.addWithCustomValue(lrp, 1.05);
				//animation.addWithCustomValue(uPatchCount, 10);
				//animation.addWithCustomValue(vPatchCount, 20);
				animation.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent evt) {
						if (((BasicAnimator)evt.getSource()).isRunning())
							in2ndMorph = true;  // This happens when the animation starts.
						else
							in2ndMorph = false; // This will happen when the animation ends.
					}
				}); 
				view.getDisplay().installAnimation(animation);
			}
		});
		return actions;
	}
	   
}

