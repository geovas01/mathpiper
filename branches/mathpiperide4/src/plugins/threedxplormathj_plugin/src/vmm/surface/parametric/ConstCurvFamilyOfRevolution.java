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
import vmm.core.I18n;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core.View;
import vmm.core3D.Vector3D;
import vmm.core3D.View3DLit;

/** 
 * Defines the family of constant Gaussian curvature K=1
*/
public class ConstCurvFamilyOfRevolution extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.ConstCurvFamilyOfRevolution.aa","0.7","0.5","1.5");
	//private RealParamAnimateable bb = new RealParamAnimateable("vmm.surface.parametric.ConstCurvFamilyOfRevolution.bb","1.0","0.5","1.5");
	boolean needsNewRange = true;
	boolean in2ndMorph    = false;
	double AA = aa.getValue();
	
	public ConstCurvFamilyOfRevolution() {
		uPatchCount.setValueAndDefault(12);
		vPatchCount.setValueAndDefault(12);
		vmin.reset("0");
		vmax.reset(2*Math.PI);
		umin.reset("-1.57");
		umax.reset("1.57");
		setDefaultViewpoint(new Vector3D(10,-10,10)); 
		setDefaultWindow(-1.5,1.5,-1.5,1.5);
		setDefaultOrientation(View3DLit.NO_ORIENTATION);
		//addParameter(bb);
		addParameter(aa);
	}
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
	    super.parameterChanged(param, oldValue, newValue);
	    needsNewRange = true;
	}
	
    protected void createData() {
      	AA = aa.getValue();
      	//if (in2ndMorph) AA = bb.getValue();
		if (needsNewRange && !in2ndMorph) redoConstants();
    	    super.createData();
    }
    
    public void redoConstants(){
	    // If AA > 1 we have to reduce the v-range:
	    double r1 = Math.asin(Math.min(1,1/AA));
	    umax.reset( Math.min(r1-0.00001,Math.PI/2));
	    umin.reset(-umax.getValue());
	    needsNewRange = false;
    }
    
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("vmm.surface.parametric.ConstCurvFamilyOfRevolution.IsometricMorph")) {
			public void actionPerformed(ActionEvent evt) {
				BasicAnimator animation = new BasicAnimator(); // has to be final for use in the actionPerformed method below.
				animation.setLooping(BasicAnimator.OSCILLATE);  // if not set, the animation will only play once through
				animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
				animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
				animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100.
				double ra = 0.5;
				double re = 1.5;
				double rr = Math.min(Math.asin(Math.min(1,1/re))-0.000001, Math.PI/2);
				animation.addWithCustomLimits(aa, ra, re);
				animation.addWithCustomValue(uPatchCount, 10);
				animation.addWithCustomValue(vPatchCount, 20);
				animation.addWithCustomValue(umin, -rr);
				animation.addWithCustomValue(umax, rr);
				animation.addWithCustomLimits(vmin, 0, 0);
				//animation.addWithCustomLimits(vmax, Math.PI/ra, Math.PI/re);
				animation.addWithCustomLimits(vmax, Math.PI, Math.PI);
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
	
	public Vector3D surfacePoint(double u, double v) {
		double x,y;
		if (in2ndMorph) {
			x = AA*Math.cos(u)*Math.cos(v/AA);     // = r(u)*cos(v/AA)
			y = AA*Math.cos(u)*Math.sin(v/AA);	
		}
		else {
		    x = AA*Math.cos(u)*Math.cos(v);       // = r(u)*cos(v)
		    y = AA*Math.cos(u)*Math.sin(v);
		}
		double z = height(u);    // Defined as integral of sqrt(1 - r'(u)*r'(u))
		return new Vector3D(x,y,z);
	}
	
	private double integrand(double s){
		return Math.sqrt(1-AA*AA*Math.sin(s)*Math.sin(s));
	}
	
	/**
	 * Simpson integration (1 4 2 4 2 ...2 4 1) of integrand = sqrt(1 - r'(s)*r'(s))
	 */
	private double height(double v) {
		long num = 2*Math.round(2+8*Math.abs(v));
		double w = 0;
		double y = integrand(0);
		if (v != 0){ 
		double dv = v/num;
		for (int i = 1; i < num; i=i+2){
		  y = integrand(i*dv);
		  w = w + 4*y;
		  y = integrand((i+1)*dv);
		  w = w + 2*y;
		  }
		  w = (w - y)*dv/3;
		}
		return w;
	}

}