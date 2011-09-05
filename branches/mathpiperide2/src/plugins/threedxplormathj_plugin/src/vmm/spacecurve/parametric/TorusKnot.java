/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Random;

import vmm.actions.ActionList;
import vmm.actions.ToggleAction;
import vmm.core.I18n;
import vmm.core.RealParamAnimateable;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.DotCloudSurface;
import vmm.core3D.Vector3D;

/**
 * Defines a torus knot -- a curve that wraps around the surface of a torus.  The axis of the torus
 * is the z-axis, and the cross section of the torus can be elliptical.  A TorusKnot can show the
 * torus as a "dot cloud torus."
 * 
 */
public class TorusKnot extends SpaceCurveParametric {
	
	
	RealParamAnimateable aa = new RealParamAnimateable("vmm.spacecurve.parametric.TorusKnot.aa",6.25,6.25,6.25);
	RealParamAnimateable bb = new RealParamAnimateable("vmm.spacecurve.parametric.TorusKnot.bb",3,2,3.5);
	RealParamAnimateable cc = new RealParamAnimateable("vmm.spacecurve.parametric.TorusKnot.cc",3,5,2);
	RealParamAnimateable dd = new RealParamAnimateable("vmm.spacecurve.parametric.TorusKnot.dd",5,5,5);
	RealParamAnimateable ee = new RealParamAnimateable("vmm.spacecurve.parametric.TorusKnot.ee",2,2,2);
	
	public TorusKnot() {
		setDefaultViewpoint(new Vector3D(30,0,10));
		setDefaultWindow(-12,12,-10,10);
		tResolution.setValueAndDefault(300);
		tmin.setValueAndDefault(0);
		tmax.setValueAndDefaultFromString("2 * pi");
		addParameter(ee);
		addParameter(dd);
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		tubeSize.setValueAndDefault(1);
	}
	
	protected Vector3D value(double t) {
		double a = aa.getValue();
		double b = bb.getValue(); 
		double c = cc.getValue();
		double d = dd.getValue();
		double e = ee.getValue();
		d = (int)(d);
		e = (int)(e);
		double x = (a + b * Math.cos(d * t)) * Math.cos(e * t);
		double y = (a + b * Math.cos(d * t)) * Math.sin(e * t);
		double z = c * Math.sin(d * t);
		return new Vector3D(x,y,z);
	}
	
	public View getDefaultView() {
		View view = new TorusKnotView();
		view.setName("vmm.spacecurve.parametric.SpaceCurveParametric.view.ViewAsCurve");
		return view;
	}
	
	/**
	 * Adds to a generic SpaceCurveParametricView the ability to show a "cloud torus" on whose surface the curve lies.
	 */
	public class TorusKnotView extends SpaceCurveParametricView {
		@VMMSave private boolean showDotCloudTorus;
		private ToggleAction showCloudTorusOption = new ToggleAction(I18n.tr("vmm.spacecurve.parametric.TorusKnot.ShowCloudTorus")) {
			public void actionPerformed(ActionEvent evt) {
				setShowDotCloudTorus(getState());
			}
		};
		private DotCloudTorus cloudTorus;
		public void setShowDotCloudTorus(boolean show) {
			if (show != showDotCloudTorus) {
				showDotCloudTorus = show;
				showCloudTorusOption.setState(show);
				if (show) {
					if (cloudTorus == null) {
						cloudTorus = new DotCloudTorus();
						cloudTorus.setColor(Color.blue);
					}
					addDecoration(cloudTorus); 
				}
				else
					removeDecoration(cloudTorus);
			}
		}
		public ActionList getActions() {
			ActionList actions = super.getActions();
			actions.add(showCloudTorusOption);
			return actions;
		}
		public boolean getShowDotCloudTorus() {
			return showDotCloudTorus;
		}
	}
	
	private class DotCloudTorus extends DotCloudSurface {
				
		protected Vector3D makeRandomPixel(Random randomNumberGenerator) {
			double a = aa.getValue();
			double b = bb.getValue(); 
			double c = cc.getValue();
			double u = randomNumberGenerator.nextDouble() * Math.PI * 2;
			double v = randomNumberGenerator.nextDouble() * Math.PI * 2;
			double x = (a + b * Math.cos(u)) * Math.cos(v);
			double y = (a + b * Math.cos(u)) * Math.sin(v);
			double z = c * Math.sin(u);
			return new Vector3D(x,y,z);
		}

	}

}
