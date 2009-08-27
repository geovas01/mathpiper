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
public class DoublyHopfFiberedTori extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.DoublyHopfFiberedTori.aa",Math.PI/4,Math.PI*0.25,Math.PI*0.45);
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.surface.parametric.DoublyHopfFiberedTori.bb",0,0,0);//Morph to 2*Math.PI

	double AA;
    double BB;
    Quaternion q0;
    boolean in2ndMorph = false;
	
	public DoublyHopfFiberedTori() {
		uPatchCount.setValueAndDefault(24);
		vPatchCount.setValueAndDefault(12);
		umin.reset("0");
		umax.reset("2 pi");
		vmin.reset("0");
		vmax.reset("pi");
		setDefaultViewpoint(new Vector3D(10,-10,10)); 
		setDefaultWindow(-2.5,2.5,-2.5,2.5);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		addParameter(bb);
		addParameter(aa);
	}
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
	    super.parameterChanged(param, oldValue, newValue);
	    AA = aa.getValue();
	    BB = bb.getValue();
	    q0 = new Quaternion(Math.cos(AA)*Math.cos(0),Math.cos(AA)*Math.sin(0),
                Math.sin(AA)*Math.cos(0),-Math.sin(AA)*Math.sin(0));
	}
	
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		final BasicAnimator animation = new BasicAnimator(); // has to be final for use in the actionPerformed method below.
		//animation.setLooping(BasicAnimator.OSCILLATE);  // if not set, the animation will only play once through
		animation.setLooping(BasicAnimator.LOOP);
		animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
		animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
		animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100.
		animation.addWithCustomLimits(bb, 0, 2*Math.PI);
		//animation.addWithCustomValue(uPatchCount, 24);
		//animation.addWithCustomValue(vPatchCount, 12);
		animation.addWithCustomValue(vmin, 0);
		animation.addWithCustomValue(vmax, Math.PI);
		animation.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				if (animation.isRunning())
					in2ndMorph = true;  // This happens when the animation starts.
				else
					in2ndMorph = false; // This will happen when the animation ends.
			}
		}); 
		actions.add(new AbstractActionVMM(I18n.tr("vmm.surface.parametric.DoublyHopfFiberedTori.RotateAroundCircle")) {
			public void actionPerformed(ActionEvent evt) {
				view.getDisplay().installAnimation(animation);
			}
		});
		return actions;
	}
	
	public Vector3D surfacePoint(double u, double v) {
	    Quaternion eiu = new Quaternion(Math.cos(u),Math.sin(u),0,0);
	    Quaternion qv = new Quaternion(Math.cos(AA)*Math.cos(v),Math.cos(AA)*Math.sin(v),
	    		                          Math.sin(AA)*Math.cos(v),-Math.sin(AA)*Math.sin(v));
	    Quaternion uv = eiu.times(qv); // Torus(u,v) in S^3
	    //uv = uv.times(q0.inverse());
	    Quaternion uvb = uv.rotateAroundHopfFibre(BB,q0);
	    //uvb = uvb.times(q0);
		return new Vector3D(uvb.StereographicProjection());
	}

}
