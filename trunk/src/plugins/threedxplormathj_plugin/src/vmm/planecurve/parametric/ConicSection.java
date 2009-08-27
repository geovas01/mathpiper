/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;

import java.awt.Graphics2D;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vmm.core.Animation;
import vmm.core.BasicAnimator;
import vmm.core.Decorateable;
import vmm.core.Decoration;
import vmm.core.ThreadedAnimation;
import vmm.core.Transform;
import vmm.core.View;

/**
 * A common base class for Elippse, Parabola, and Hyperbola, to show foci and "directrix" during morphing
 * and extra stuff during the creation animation.  Note that Circle is not a subclass of ConicSection since
 * it does not have these extra decorations.
 */
abstract public class ConicSection extends PlaneCurveParametric {
	
	/**
	 * This is overridden in the sub-classes to do the actual drawing of the extra decorations.
	 */
	abstract protected void drawFociAndDirectrix(Graphics2D g, View view, Transform limits, double t);
	
	/**
	 * Returns a creation animation that adds foci, directrix, and othe stuff during the animation.
	 * @param view The View where the animation will be installed.  If this is null, then the return value is null.
	 */
	public Animation getCreateAnimation(View view) {
		if (! (view instanceof PlaneCurveParametricView))
			return null;
		final PlaneCurveParametricView myView = (PlaneCurveParametricView)view;
		return new ConicAnimation(myView);
	}

	/**
	 * Returns a modified morphing animation that adds foci and directrix during the animation.
	 * The decoration is added to view, if it is non-null, and otherwise is added directly to
	 * this exhibit.
	 */
	public BasicAnimator getMorphingAnimation(View view, int looping) {
		final BasicAnimator anim = super.getMorphingAnimation(view, looping);
		if (anim == null)
			return null;
		final Decorateable owner = (view == null)? (Decorateable)this : (Decorateable)view;
		anim.addChangeListener( new ChangeListener() {
			   // This change listener is reponsible for adding the decoration when the animation
			   // starts and for removing it when the animation ends.
			Decoration dec = new FocusDirectrixDecoration();
			public void stateChanged(ChangeEvent evt) {
				if (anim.isRunning()) {
					owner.addDecoration(dec);
				}
				else {
					owner.removeDecoration(dec);
				}
			}
		});
		return anim;
	}
	
	private final class ConicAnimation extends ThreadedAnimation {
		private final PlaneCurveParametricView view;

		private ConicAnimation(PlaneCurveParametricView view) {
			super();
			this.view = view;
		}

		public void runAnimation() {
			FocusDirectrixDecoration dec = null;
			try {
				for (int i = 0; i < 50; i++) {
					view.fractionToDraw = i/50.0;
					view.forceRedraw();
					pause(20);
				}
				view.fractionToDraw = 1;
				dec = new FocusDirectrixDecoration();
				view.addDecoration(dec);
				pause(200);
				int points = tResolution.getValue();
				double t = tmin.getValue(); 
				double dt = (tmax.getValue() - t) / points;
				for (int reps = 0; reps < 10; reps++) {
					for (int i = 0; i <= points; i++) {
						dec.setT(t + i*dt);
						pause(35);
					}
					pause(300);
				}
			}
			finally {
				view.fractionToDraw = 1;
				view.forceRedraw();
				if (dec != null)
					view.removeDecoration(dec);
			}
		}
	}

	private  class FocusDirectrixDecoration extends Decoration {
		double t = Double.NaN;  // if set to a real value, extra stuff is drawn besides foci and directrix
		void setT(double t) {
			this.t = t;
			fireDecorationChangeEvent();
		}
		public void doDraw(Graphics2D g, View view, Transform limits) {
			drawFociAndDirectrix(g,view,limits,t);
		}
	}
	

}
