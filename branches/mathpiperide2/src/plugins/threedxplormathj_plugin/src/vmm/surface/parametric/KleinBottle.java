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
import vmm.core.RealParamAnimateable;
import vmm.core.View;
import vmm.core3D.Vector3D;
import vmm.core3D.View3DLit;

/**
 * Defines a Klein Bottle surface.
 * @author palais
 *
 */
public class KleinBottle extends SurfaceParametric {
	
	private RealParamAnimateable aa = new RealParamAnimateable("genericParam.aa",3,3,3);
	
	boolean in2ndMorph = false;
	
	public KleinBottle() {
		setDefaultViewpoint(new Vector3D(10,-10,10));
		setDefaultWindow(-4,4,-4,4);
		umin.reset("0.0","-0.3","-pi");
		umax.reset("2*pi","0.3","pi");
		vmin.reset("0.0","0.1","0.1");
		vmax.reset("2*pi","2*pi","2*pi");
		uPatchCount.setValueAndDefault(18);
		vPatchCount.setValueAndDefault(24);
		setDefaultOrientation(View3DLit.NO_ORIENTATION);
		addParameter(aa);
	}
	
	static private double sin(double t){return Math.sin(t);}
	static private double cos(double t){return Math.cos(t);}
	
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("vmm.surface.parametric.KleinBottle.Twisting8")) {
			public void actionPerformed(ActionEvent evt) {
				BasicAnimator animation = new BasicAnimator(); // has to be final for use in the actionPerformed method below.
				animation.setLooping(BasicAnimator.OSCILLATE);  // if not set, the animation will only play once through
				//animation.setLooping(BasicAnimator.LOOP);
				animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
				animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
				animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100.
				//animation.addWithCustomLimits(bb, 0, 2*Math.PI);
				//animation.addWithCustomValue(uPatchCount, 24);
				//animation.addWithCustomValue(vPatchCount, 12);
				animation.addWithCustomValue(umin, 0);
				animation.addWithCustomValue(umax, 2*Math.PI);
				animation.addWithCustomLimits(vmin, 0, 0);
				animation.addWithCustomLimits(vmax, 0.2, 2*Math.PI);
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
		double AA = aa.getValue();
		return new Vector3D((AA + cos(v / 2) * sin(u) - sin(v / 2) * sin(2 * u)) * cos(v),  
				           (AA + cos(v / 2) * sin(u) - sin(v / 2) * sin(2 * u)) * sin(v),  
				           sin(v / 2) * sin(u) + cos(v / 2) * sin(2 * u));
	}

}