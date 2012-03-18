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
import vmm.core.Quaternion;
import vmm.core.View;
import vmm.core3D.Vector3D;
import vmm.core3D.View3DLit;


/**
 * Defines the Torus surface family, 
 */
public class BianchiPinkall extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.BianchiPinkall.aa",0.25,0.25,0.25);
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.surface.parametric.BianchiPinkall.bb",0.1,0.0,0.1);
	private RealParamAnimateable cc = new RealParamAnimateable("vmm.surface.parametric.BianchiPinkall.cc",3,3,3);
	private RealParamAnimateable dd = new RealParamAnimateable("vmm.surface.parametric.BianchiPinkall.dd",0,0,0);//Morph to 2
	
	double AA;
    double BB;
    double CC;
    double DD;
    Quaternion q0;
    boolean in2ndMorph = false;
	
	public BianchiPinkall() {
		uPatchCount.setValueAndDefault(36);
		vPatchCount.setValueAndDefault(18);
		umin.reset("0");
		umax.reset("2 pi");
		vmin.reset("0.01"); //This emphasizes the Hopf circle around which dd controls the rotation
		vmax.reset("pi");// On the quotient S^2 the polar angle is 2v
		setDefaultViewpoint(new Vector3D(-18.5,-5.1,-3.4)); 
		setDefaultWindow(-4.5,4.5,-4.5,4.5);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		addParameter(dd);
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		aa.setMaximumValueForInput(0.5);
		aa.setMinimumValueForInput(0.0);
	}
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
	    super.parameterChanged(param, oldValue, newValue);
	    AA = Math.PI*aa.getValue();
	    BB = Math.PI*bb.getValue();
	    BB = Math.max(0.0, Math.min(BB,Math.min(AA,Math.PI-AA)));
	    CC = 2*cc.getValue();
	    DD = Math.PI*dd.getValue();
	    q0 = vFunction(AA,BB,CC, 0);
	}
	
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("vmm.surface.parametric.BianchiPinkall.RotateAroundCircle")) {
			public void actionPerformed(ActionEvent evt) {
				BasicAnimator animation = new BasicAnimator(); // has to be final for use in the actionPerformed method below.
				//animation.setLooping(BasicAnimator.OSCILLATE);  // if not set, the animation will only play once through
				animation.setLooping(BasicAnimator.LOOP);
				animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
				animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
				animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100.
				animation.addWithCustomLimits(dd, 0, 2);
				//animation.addWithCustomValue(uPatchCount, 24);
				//animation.addWithCustomValue(vPatchCount, 12);
				//animation.addWithCustomValue(vmin, 0);
				//animation.addWithCustomValue(vmax, Math.PI);
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
	
	public Quaternion vFunction(double a, double b, double c, double v){
		double am = a+b*Math.sin(c*v);
		return new Quaternion(Math.cos(am)*Math.cos(v),Math.cos(am)*Math.sin(v),
                Math.sin(am)*Math.cos(v),-Math.sin(am)*Math.sin(v));
	}
	
	public Vector3D surfacePoint(double u, double v) {
	    Quaternion eiu = new Quaternion(Math.cos(u),Math.sin(u),0,0);
	    Quaternion qv = vFunction(AA, BB,CC, v);
	    Quaternion uv = eiu.times(qv); // Torus(u,v) in S^3
	    Quaternion uvd = uv.rotateAroundHopfFibre(DD,q0);
		return new Vector3D(uvd.StereographicProjection());
	}

}

