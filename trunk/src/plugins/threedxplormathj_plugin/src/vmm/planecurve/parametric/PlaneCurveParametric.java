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
import java.awt.geom.Point2D;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.actions.ToggleAction;
import vmm.core.Animation;
import vmm.core.Display;
import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.RealParam;
import vmm.core.SaveAndRestore;
import vmm.core.TimerAnimation;
import vmm.core.Transform;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.planecurve.PlaneCurve;

/**
 * A curve in the plane that is defined by differentiable functions x(t) and y(t).
 */
abstract public class PlaneCurveParametric extends PlaneCurve {
	
	/**
	 * The number of t-values for points on the curve.  Points of the form (x(t),y(t))
	 * are computed for 1 plus this many values of t, and these points are joined by line segments
	 * to draw the curve.  Note that the starting and ending values of t are given by
	 * the values of {@link #tmin} and {@link #tmax}.  This interval is divided into
	 * tResolution sub-intervals, giving tResolution+1 points.
	 */
	protected IntegerParam tResolution;
	
	/**
	 * The minimum valur of t for points on the curve.
	 */
	protected RealParam tmin;
	
	/**
	 * The maximum value of t for points on the curve.
	 */
	protected RealParam tmax;
	
	/**
	 * The t-values used to compute the points (x(t),y(t)) on the curve.  These are
	 * evenly spaced between the minimum and maximum t-values.
	 */
	protected double[] tVals;  // input t-values for computing the points on the curve.
	
	/**
	 * Computes x(t) for a given value of t.
	 */
	abstract public double xValue(double t);
	
	/**
	 * Computes y(t) for a given value of t.
	 */
	abstract public double yValue(double t);
	
	/**
	 * Computes x'(t) for a given value of t.  The default version defined in this class computes an approximate
	 * value numerically, using values of the function itself at points near t.
	 */
	public double xDerivativeValue(double t) {
		double dx = 0.001; // TODO: decide what value to use here and in second derivative
		double f1 = xValue(t + dx);
		double f2 = xValue(t + 2*dx);
		double g1 = xValue(t - dx);
		double g2 = xValue(t - 2*dx);
		return ((8 * f1 + g2) - (f2 + 8 * g1))/(12 * dx);
	}
	
	/**
	 * Computes y'(t) for a given value of t.  The default version defined in this class computes an approximate
	 * value numerically, using values of the function itself at points near t.
	 */
	public double yDerivativeValue(double t) {
		double dx = 0.001; 
		double f1 = yValue(t + dx);
		double f2 = yValue(t + 2*dx);
		double g1 = yValue(t - dx);
		double g2 = yValue(t - 2*dx);
		return ((8 * f1 + g2) - (f2 + 8 * g1))/(12 * dx);
	}
	
	/**
	 * Computes x''(t) for a given value of t.  The default version defined in this class computes an approximate
	 * value numerically, using values of the function itself at points near t.
	 */
	public double x2ndDerivativeValue(double t) {
		double dx = 0.001;
		double f0 = xValue(t);
		double f1 = xValue(t + dx);
		double f2 = xValue(t + 2*dx);
		double g1 = xValue(t - dx);
		double g2 = xValue(t - 2*dx);
		return ((16 * f1  + 16 * g1) - 30 * f0 - f2 - g2) / (12 * dx * dx);
	}
	
	/**
	 * Computes y''(t) for a given value of t.  The default version defined in this class computes an approximate
	 * value numerically, using values of the function itself at points near t.
	 */
	public double y2ndDerivativeValue(double t) {
		double dx = 0.001;
		double f0 = yValue(t);
		double f1 = yValue(t + dx);
		double f2 = yValue(t + 2*dx);
		double g1 = yValue(t - dx);
		double g2 = yValue(t - 2*dx);
		return ((16 * f1  + 16 * g1) - 30 * f0 - f2 - g2) / (12 * dx * dx);
	}
	
	
	/**
	 * Construct a plane curve with Parameters tmin, tmax, and tResolution.  The
	 * curve will be defined by the functions <code>xValue</code> and <code>yValue</code>
	 * in a concrete subclass.
	 */
	public PlaneCurveParametric() {
		tmin = new RealParam("vmm.planecurve.parametric.PlaneCurveParameteric.tmin",-5);
		tmax = new RealParam("vmm.planecurve.parametric.PlaneCurveParameteric.tmax",5);
		tResolution = new IntegerParam("vmm.planecurve.parametric.PlaneCurveParameteric.tResolution",100);
		tResolution.setMinimumValueForInput(4);
		tResolution.setMaximumValueForInput(2000);
		addParameter(tResolution);
		addParameter(tmax);
		addParameter(tmin);
	}
	
	/**
	 * Returns the number of intervals into which the curve is divided.  Note that the number
	 * of points that are drawn on the curve is one more than the tResolution.
	 */
	public int getTResolution() {
		return tResolution.getValue();
	}
	
	/**
	 * Return the t-value for one of the points on the curve.  The number of points
	 * is getTResolution() + 1, and they are numbered from 0 to getTResolution().
	 * If this method is called before the curve has been
	 * drawn, the return value will be Double.NaN.  The return value is also Double.NaN
	 * if the specified index is not in the range 0 to getTResolution(), inclusive.
	 * The corresponding point on the curve can be obtained by calling xValue() and yValue()
	 * <p>The value returned by this method is only valid after the curve has been drawn;
	 * if it is called before that time, the return value will be Double.NaN.
	 * @param index A position in the array of t-values that specifies which t-value shoud
	 * be returned.
	 * @see #getTResolution()
	 * @see #xValue(double)
	 * @see #yValue(double)
	 */
	public double getT(int index) {
		if (tVals == null || index < 0 || index > tVals.length)
			return Double.NaN;
		else
			return tVals[index];
	}
	
	/**
	 * parametrized circle to draw circle arcs in different colors.
	 */
	public Point2D[] myCircle(double mx, double my, double rad, int numPoints){
		Point2D[] circPts = new Point2D[numPoints+1];
		double s = 0.0;
		
		for (int i = 0; i <= numPoints; i++) {
			s = i*2*Math.PI/numPoints;
				circPts[i] = new Point2D.Double(mx +rad*Math.cos(s),my +rad*Math.sin(s));
		}
		return circPts;
	}
	
	/**
	 * Calcululates the array of points for this curve using the functions {@link #xValue(double)} and
	 * {@link #yValue(double)} to compute the points at equally spaced t-values between {@link #tmin} and {@link #tmax}.
	 * The number of points is one plus the value of {@link #tResolution}.
	 */
	protected void makePoints() {
		int subintervals = tResolution.getValue();
		tVals = new double[subintervals+1];
		points = new Point2D[subintervals+1];
		double t = tmin.getValue();
		double dt = (tmax.getValue() - t)/subintervals;
		for (int i = 0; i <= subintervals; i++) {
			tVals[i] = t + dt*i;
		}
		for (int i = 0; i <= subintervals; i++) {
			double x = xValue(tVals[i]);
			double y = yValue(tVals[i]);
			if (Double.isNaN(x) || Double.isNaN(y) || Double.isInfinite(x) || Double.isInfinite(y))
				points[i] = null;
			else
				points[i] = new Point2D.Double(x,y);
		}
	}
		
	/**
	 * Draw the curve in a specified View.  If the View belogs to the nested class
	 * {@link PlaneCurveParametricView} (which is the default), then only a fraction of the curve
	 * might be drawn, as specified in the View; this feature is used in the
	 * creation animation for the curve.
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
		view.drawCurve(points,pointCt);
	}
	
	/**
	 * Returns an animation that shows the curve being drawn bit-by-bit.
	 * @param view The View where the creation animation will be shown.  If this
	 * is null or if it is not an instance of {@link PlaneCurveParametricView}, then the return
	 * value is null.
	 */
	public Animation getCreateAnimation(final View view) {
		if (view == null || (! (view instanceof PlaneCurveParametricView)))
			return null;
		return new TimerAnimation(50,20) { // 50 frames, 20 mulliseconds per frame
			protected void drawFrame() { 
				((PlaneCurveParametricView)view).fractionToDraw = frameNumber/50.0;
				forceRedraw();
			}
			public void animationStarting() {
				((PlaneCurveParametricView)view).fractionToDraw = 0;
			}
			public void animationEnding() {
				((PlaneCurveParametricView)view).fractionToDraw = 1;
				forceRedraw();
			}
		};
	}
	
	/**
	 * Returns a new instance of the nested class {@link PlaneCurveParametricView}.
	 */
	public View getDefaultView() {
		View view = new PlaneCurveParametricView();
		view.setShowAxes(true);
		return view;
	}
	
	
	/**
	 * Returns a list of actions that can be applied to a PlaneCurveParametric.
	 * This adds a separator and four actions that run various animations to
	 * the list obtained from super.getActionsForView(view).
	 * @param view The view for which the actions should apply.  If this is null,
	 * then no new actions are added to those inherited from the superclass.
	 */
	public ActionList getActionsForView(final View view) {
		ActionList actions = super.getActionsForView(view);
		if (view == null)
			return actions;
		actions.add(null);
		double[] window = getDefaultWindow();
		final double width = Math.max(Math.abs(window[1] - window[0]), Math.abs(window[3] - window[2]));
		actions.add( new AbstractActionVMM(I18n.tr("vmm.planecurve.parametric.PlaneCurveParameteric.showParallelCurves")) {
			public void actionPerformed(ActionEvent evt) {
				Display display = view.getDisplay();
				display.installAnimation(new ParallelCurveAnimation(view, PlaneCurveParametric.this, false, width, width/200));
			}
		});
		actions.add( new AbstractActionVMM(I18n.tr("vmm.planecurve.parametric.PlaneCurveParameteric.showParallelCurvesWithNormals")) {
			public void actionPerformed(ActionEvent evt) {
				Display display = view.getDisplay();
				display.installAnimation(new ParallelCurveAnimation(view, PlaneCurveParametric.this, true, width, width/200));
			}
		});
		actions.add( new AbstractActionVMM(I18n.tr("vmm.planecurve.parametric.PlaneCurveParameteric.showOsculatingCircles")) {
			public void actionPerformed(ActionEvent evt) {
				Display display = view.getDisplay();
				display.installAnimation(new OsculatingCircleAnimation(view, PlaneCurveParametric.this, false, true));
			}
		});
		actions.add( new AbstractActionVMM(I18n.tr("vmm.planecurve.parametric.PlaneCurveParameteric.showOsculatingCirclesWithNormals")) {
			public void actionPerformed(ActionEvent evt) {
				Display display = view.getDisplay();
				display.installAnimation(new OsculatingCircleAnimation(view, PlaneCurveParametric.this));
			}
		});
		actions.add( new AbstractActionVMM(I18n.tr("vmm.planecurve.parametric.PlaneCurveParameteric.showTangentsAndNormals")) {
			public void actionPerformed(ActionEvent evt) {
				Display display = view.getDisplay();
				display.installAnimation( new TimerAnimation(getTResolution(),40) {
					TangentAndNormalDecoration dec = new TangentAndNormalDecoration();
					protected void animationEnding() {
						view.removeDecoration(dec);
					}

					protected void animationStarting() {
						dec.setCurve(PlaneCurveParametric.this);
						view.addDecoration(dec);
					}

					protected void drawFrame() {
						dec.setIndex(getFrameNumber());
					}
				});
			}
		});
		if (view instanceof PlaneCurveParametricView) {
			actions.add(null);
			actions.add(((PlaneCurveParametricView)view).showEvoluteAction);
		}
		return actions;
	}
	
	/**
	 * Defines the default View of a PlaneCurveParametric.  The only differences
	 * between this and the top-level class is that PlaneCurveParametricView is antialiased by default
	 * and it includes a member variable "fractionToDraw" that tells what fraction
	 * of the curve should actually be drawn in the view; this feature is only
	 * used in the creation animation, which shows a sequence of frames in which the
	 * fractionToDraw is gradually increased.
	 * <p>NOTE: This muste be public static so that it can be accessed when an object of this
	 * type is created by {@link SaveAndRestore} while reading a file.
	 */
	public static class PlaneCurveParametricView extends View {
		@VMMSave private boolean showEvolute;
		private ToggleAction showEvoluteAction;
		private NormalBundleDecoration evolute;  // for displaying the evolute;
		double fractionToDraw = -1;
		public PlaneCurveParametricView() {
			setAntialiased(true);
			showEvoluteAction = new ToggleAction(I18n.tr("vmm.planecurve.parametric.PlaneCurveParameteric.DisplayEvolute")) {
				public void actionPerformed(ActionEvent evt) {
					setShowEvolute( getState() );
				}
			};
		}
		public boolean getShowEvolute() {
			return showEvolute;
		}
		public void setShowEvolute(boolean showEvolute) {
			if (this.showEvolute == showEvolute)
				return;
			this.showEvolute = showEvolute;
			showEvoluteAction.setState(showEvolute);
			if (showEvolute) {
				evolute = new NormalBundleDecoration();
				evolute.setShowEvolute(true);
				addDecoration(evolute);
			}
			else {
				removeDecoration(evolute);
				evolute = null;
			}
			forceRedraw();
		}
		NormalBundleDecoration getEvoluteDecoration() {  // for use in ParallelCurveAnimation and OsculatingCircleAnimation
			return evolute;
		}
	}

	
}
