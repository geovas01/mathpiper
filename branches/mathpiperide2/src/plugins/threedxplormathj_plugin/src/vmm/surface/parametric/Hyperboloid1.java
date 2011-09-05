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


/**
 * Defines the Single Sheeted Hyperboloid surface family.
 */
public class Hyperboloid1 extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa","1.0","1.0","0.5");
	private RealParamAnimateable bb = new RealParamAnimateable("genericParam.bb","1.0","1.0","1.8");
	private RealParamAnimateable cc = new RealParamAnimateable("genericParam.cc","1.0","1.0","1.0");
	private RealParamAnimateable dd = new RealParamAnimateable("genericParam.dd","1.0","0.5","2.0");
	private boolean in2ndMorph    = false;
	private double AA = aa.getValue();
	private double BB = bb.getValue();
	private double CC = cc.getValue();
	private double DD = dd.getValue();
	
	public Hyperboloid1() {
		uPatchCount.setValueAndDefault(18);
		vPatchCount.setValueAndDefault(18);
		umin.reset("-2");
		umax.reset("2");
		vmin.reset("0");
		vmax.reset("2 pi");
		setDefaultViewpoint(new Vector3D(30,20,10)); 
		setDefaultWindow(-5.5,5.5,-5.5,5.5);
		addParameter(dd);
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
	}
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
	    super.parameterChanged(param, oldValue, newValue);
	    AA = aa.getValue();
		BB = bb.getValue();
		CC = cc.getValue();
		DD = dd.getValue();
	}
	
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("vmm.surface.parametric.Hyperboloid1.Ruling")) {
			public void actionPerformed(ActionEvent evt) {
				BasicAnimator animation = new BasicAnimator(); // has to be final for use in the actionPerformed method below.
				animation.setLooping(BasicAnimator.OSCILLATE);  // if not set, the animation will only play once through
				//animation.setLooping(BasicAnimator.LOOP);
				animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
				animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
				animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100.
				animation.addWithCustomLimits(dd, 0.5, 2.0);
				//animation.addWithCustomValue(uPatchCount, 24);
				//animation.addWithCustomValue(vPatchCount, 12);
				/*animation.addWithCustomValue(umin, 0);
				animation.addWithCustomValue(umax, 2*Math.PI);
				animation.addWithCustomLimits(vmin, 0, 0);
				animation.addWithCustomLimits(vmax, 0.2, 2*Math.PI);*/
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
	
	static private double sinh(double t){
		return 0.5*(Math.exp(t)-Math.exp(-t));
	}
	
	static private double cosh(double t){
		return 0.5*(Math.exp(t)+Math.exp(-t));
	}
	
	public Vector3D surfacePoint(double u, double v){
		double x=0;
		double y=0;
		double z=0;
		if (!in2ndMorph)  {
		x = AA * cosh(u) * Math.cos(v);
		y = BB * cosh(u) * Math.sin(v);
		z = CC * sinh(u);
		}
		else {
		double uu = u*Math.PI/2;
		double vv = v - (vmax.getValue()+vmin.getValue())/2;
		z = AA*(Math.cos(uu)-vv*Math.sin(uu));	
		y = BB*(Math.sin(uu)+vv*Math.cos(uu));	
		x = DD*CC*vv;
		}
		return new Vector3D(x,y,z);	
	}

}
