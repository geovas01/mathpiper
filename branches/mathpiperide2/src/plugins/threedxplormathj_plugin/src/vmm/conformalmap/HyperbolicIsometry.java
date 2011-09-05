/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.conformalmap;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.core.BasicAnimator;
import vmm.core.Complex;
import vmm.core.ComplexParamAnimateable;
import vmm.core.I18n;
import vmm.core.View;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;

public class HyperbolicIsometry extends ConformalMap {
	
	private ComplexParamAnimateable c = new ComplexParamAnimateable("c", "0.5", "-0.9", "0.9");
	
	private boolean in2ndMorph = false;
	private Vector3D[] equator; // for drawing the unit circle on the exhibit.
	private Point2D[] unitCircle;
	
	public HyperbolicIsometry() {
		addParameter(c);
		setGridType(POLAR);
		umin.reset(0.0);
		umax.reset(0.7);
		vmin.reset(0.0);
		vmax.reset("2 pi");
		ures.reset(6);
		vres.reset(36);
		setDefaultWindow2D(-1,1,-1,1);
		equator = new Vector3D[121];
		unitCircle = new Point2D[121];
		for (int i = 0; i <= 120; i++){
			double angle = (i*3) / 180.0 * Math.PI;
			equator[i] = new Vector3D(Math.cos(angle),Math.sin(angle),0);
			unitCircle[i] = new Point2D.Double(Math.cos(angle),Math.sin(angle));
		}
	}
	
	public ActionList getAdditionalAnimationsForView(final View view) {
		ActionList actions = super.getAdditionalAnimationsForView(view);
		actions.add(new AbstractActionVMM(I18n.tr("vmm.conformalmap.HyperbolicIsometry.Rotation")) {
			public void actionPerformed(ActionEvent evt) {
				BasicAnimator animation = new BasicAnimator(); // has to be final for use in the actionPerformed method below.
				//animation.setLooping(BasicAnimator.OSCILLATE);  // if not set, the animation will only play once through
				animation.setLooping(BasicAnimator.LOOP);
				animation.setUseFilmstrip(getUseFilmstripForMorphing());  // respect the setting of useFilmstripForMorphing
				animation.setFrames(getFramesForMorphing());  // Use number of frames as set for morphing this exhibit.
				animation.setMillisecondsPerFrame(200);  // The value used for the standard Morph animation is 100.
				//animation.addWithCustomLimits(bb, 0, 2*Math.PI);
				animation.addWithCustomValue(ures, 6);
				animation.addWithCustomValue(vres, 12);
				animation.addWithCustomValue(umin, 0.0);
				animation.addWithCustomValue(umax, 0.7);
				animation.addWithCustomLimits(vmin, 0, 2*Math.PI);
				animation.addWithCustomLimits(vmax, 0.5*Math.PI, 2.5*Math.PI);
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

	protected Complex function(Complex argument) {
		Complex x = c.getValue();
		return argument.plus(x).dividedBy(argument.times(x.conj()).plus(1));
	}

	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
		super.doDraw3D(g, view, transform);
		view.setColor(null);
		if (view.getEnableThreeD())
			view.drawCurve(equator);
		else
			view.drawCurve(unitCircle);
	}
	
	
	

}
