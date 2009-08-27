/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import vmm.actions.ActionList;
import vmm.actions.ToggleAction;
import vmm.core.Animation;
import vmm.core.Decoration;
import vmm.core.I18n;
import vmm.core.ThreadedAnimation;
import vmm.core.Transform;
import vmm.core.VMMSave;
import vmm.core.View;

/**
 * @author Traudel
 but allmost all copied from David Eck
 */
 abstract public class DecoratedCurve extends PlaneCurveParametric {
	 
protected Color wantedColor;
protected int strokeSize;

public void setWantedColor(Color color){
	this.wantedColor = color;
    }
public Color getWantedColor(){
	Color color = this.wantedColor;
	return color;
}
	
public void setStrokeSize(int size){
	this.strokeSize = size;
    }
public int getStrokeSize(){
	int size = this.strokeSize;
	return size;
}


abstract protected void drawNeededStuff(Graphics2D g, View view, Transform limits, double t); 
	
	public Animation getCreateAnimation(View view) {
		if (! (view instanceof MMOView))
			return null;
		
		final MMOView myView = (MMOView)view;
		wantedColor = Color.green;
		strokeSize =1;
		return new ThreadedAnimation() {
			public void runAnimation() {
				NeededStuffDecoration dec = null;
				try {
					pause(100);
					dec = new NeededStuffDecoration();
					myView.addDecoration(dec);
					pause(35);
					int numPoints = tResolution.getValue();
					double t = tmin.getValue();
					double dt = (tmax.getValue() - t) / numPoints;
					for (int reps = 0; reps < 2; reps++) {
						for (int i = 0; i <  numPoints; i++) {
							myView.curveColor = wantedColor;
							dec.setT(t + i*dt, i);
							pause(35);
						}
					}
				}
				finally {
					 myView.fractionToDraw = 1;
					 myView.curveColor = null;
					 if (dec != null)
						 myView.removeDecoration(dec);
					 myView.forceRedraw();
				}
			}
		};
	}
	
	
	private class NeededStuffDecoration extends Decoration {
		double t = Double.NaN;  // if set to a real value, extra stuff is drawn besides foci and directrix
		int i=0;
		
		void setT(double t, int i) {
			this.t = t;
			this.i = i;
			fireDecorationChangeEvent();
		}
		public void doDraw(Graphics2D g, View view, Transform limits) {
			drawNeededStuff(g,view,limits,t);
			final MMOView myView = (MMOView)view;
			myView.drawCurve(points, i,points.length-1); 
		}
	}
	
	
	// ------------------ Added by Eck to implement color correctly -------------
	
	/**
	 * Draw the curve in a specified View.  If the View belongs to the class
	 * {@link PlaneCurveParametric.PlaneCurveParametricView}, then only a fraction of the curve
	 * might be drawn, as specified in the View; this feature is used in the
	 * creation animation for the curve.  If the View belongs to the subclass
	 * {@link MMOView} of PlaneCurveParametricView (which is the default), then
	 * the curve can be in some other color besides the default (this happens during
	 * the create animation, when the curve is being constructed).
	 */
	public void doDraw(Graphics2D g, View view, Transform transform) {
		if (points.length == 0)
			return;
		int pointCt = points.length;
		if (view instanceof PlaneCurveParametricView) {
			double fraction = ((PlaneCurveParametricView)view).fractionToDraw;
			if (fraction >= 0 && fraction < 1)
				pointCt = (int)(fraction*pointCt);
			if (pointCt == 0)
				pointCt = 1;
		}
		Color saveColor = null;
		if (view instanceof MMOView) { 
			if ( ((MMOView)view).curveColor != null ) {
				saveColor = g.getColor();
				g.setColor( ((MMOView)view).curveColor );
				view.setStrokeSizeMultiplier(strokeSize);
			}
		}
		view.drawCurve(points,pointCt);
		if (saveColor != null)
			g.setColor(saveColor);
	}

	
	/**
	 * Overridden to return a view of type MMOView.
	 */
	public View getDefaultView() {
		View view = new MMOView();
		view.setShowAxes(true);
		return view;
	}
	

	/**
	 * A trivial subclass of PlaneCurveParametricView that is needed to get the desired
	 * color effects during the create animation.
	 */
	public static class MMOView extends PlaneCurveParametricView {
		Color curveColor = null; // If set to non-null, this color is used to draw the curve.
		                         // This is used only during the create animation.  When this
		                         // is null, the default foreground color is used.

		protected boolean simplifyActionMenu = true; // "true" turns the appearance in the Menu off
		                                             // If "false" is wanted one has to redefine getDefaultView in the subclass
		@VMMSave private boolean useCloud = false;   // The simpler Action (= Default) should be selected with "false"
		protected ToggleAction useCloudToggle = new ToggleAction(I18n.tr("vmm.planecurve.parametric.PlaneCurveParameteric.ToggleUseCloud"), false) {
			public void actionPerformed(ActionEvent evt) {
				setUseCloud( !useCloud );
			}
		};
		/**
		 * Tells whether grid lines are drawn in colors.
		 */
		public boolean getUseCloud() {
			return useCloud;
		}

		/**
		 * Set whether to use a range of colors for the grid lines.  The alternative is to draw them
		 * all using the view's foreground color.  (This property is managed by a a checkbox.)
		 */
		
		public void setUseCloud(boolean useCloud) {
			if (useCloud == this.useCloud){
				return;
			}
			useCloudToggle.setState(useCloud);  // make sure ?? and ToggleAction are in sync
			forceRedraw();
			this.useCloud = useCloud;
		}
		
		public ActionList getActions() {
			ActionList actions = super.getActions();
			if (!simplifyActionMenu)      // allows to remove this action from the menu of some curves
			actions.add(useCloudToggle);
			return actions;
		}
		
	} // end of MMOView
	
	
 }
 