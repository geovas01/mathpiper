/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.actions.ToggleAction;
import vmm.core.Animation;
import vmm.core.Decoration;
import vmm.core.Display;
import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.RealParam;
import vmm.core.SaveAndRestore;
import vmm.core.TimerAnimation;
import vmm.core.Transform;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.Grid3D;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;
import vmm.core3D.View3DLit;
import vmm.spacecurve.SpaceCurve;
import vmm.spacecurve.SpaceCurveView;

/**
 * A space curve that is defined by parametric equations, giving points of the form (x(t),y(t),z(t)).
 * A parametric curve can be rendered as a "tube" that surrounds the curve; this tube has square cross section,
 * with the orientation of the square at a given point depending on the torsion of the curve at that point.
 * A SpaceCurveParametric renders itself as a tube if it is being drawn in a {@link vmm.core3D.View3DLit}.  In a plain
 * {@link vmm.core3D.View3D}, it renders itself as a curve.
 */
abstract public class SpaceCurveParametric extends SpaceCurve {
	
	/**
	 * The number of t-values for points on the curve.  Points of the form (x(t),y(t),z(t))
	 * are computed for 1 plus this many values of t, and these points are joined by line segments
	 * to draw the curve.  Note that the starting and ending values of t are given by
	 * the values of {@link #tmin} and {@link #tmax}.  This interval is divided into
	 * tResolution sub-intervals, giving tResolution+1 points.  The default value is 200.
	 */
	protected IntegerParam tResolution;
	
	/**
	 * The minimum value of t for points on the curve.  The default value is -5.
	 */
	protected RealParam tmin;
	
	/**
	 * The maximum value of t for points on the curve.  The default value is 5.
	 */
	protected RealParam tmax;
	
	/**
	 * The t-values used to compute the points (x(t),y(t),z(t)) on the curve.  These are
	 * evenly spaced between the minimum and maximum t-values.
	 */
	protected double[] tVals;  // input t-values for computing the points on the curve.

	/**
	 * Represents the surface of the tube, when this exhibit is rendered as a tube.
	 */
	protected Grid3D tube;
	
	/**
	 * The length of one of the square cross sections of the tube, when this exhibit is rendered
	 * as a tube.  The default value is 0.25.
	 */
	protected RealParam tubeSize;
	
	/**
	 * The number of sides around the tube, from 3 to 20.
	 */
	protected IntegerParam tubeSides;


	public SpaceCurveParametric() {
		tmin = new RealParam("vmm.spacecurve.parametric.SpaceCurveParameteric.tmin",-5);
		tmax = new RealParam("vmm.spacecurve.parametric.SpaceCurveParameteric.tmax",5);
		tResolution = new IntegerParam("vmm.spacecurve.parametric.SpaceCurveParameteric.tResolution",200);
		tResolution.setMinimumValueForInput(4);
		tResolution.setMaximumValueForInput(2000);
		tubeSize = new RealParam("vmm.spacecurve.parametric.SpaceCurveParameteric.TubeSize",0.25);
		tubeSize.setMinimumValueForInput(0.01);
		tubeSides = new IntegerParam("vmm.spacecurve.parametric.SpaceCurveParametric.TubeSides",4);
		tubeSides.setMinimumValueForInput(3);
		tubeSides.setMaximumValueForInput(20);
		addParameter(tubeSides);
		addParameter(tubeSize);
		addParameter(tResolution);
		addParameter(tmax);
		addParameter(tmin);
		setFramesForMorphing(25);
		setUseFilmstripForMorphing(true);
	}
	
	/**
	 * Computes the array of points on the curve, using the {@link #value(double)} function and the
	 * values of the tmin, tmax, and tResolution parameters.  Subclasses should not need to override this method.
	 */
	protected void makePoints() {
		int subintervals = tResolution.getValue();
		tVals = new double[subintervals+1];
		points = new Vector3D[subintervals+1];
		double t = tmin.getValue();
		double dt = (tmax.getValue() - t)/subintervals;
		for (int i = 0; i <= subintervals; i++) {
			tVals[i] = t + dt*i;
			points[i] = value(tVals[i]);
			if (Double.isNaN(points[i].x) || Double.isInfinite(points[i].x) || Double.isNaN(points[i].y) || 
					Double.isInfinite(points[i].y) || Double.isNaN(points[i].z) || Double.isInfinite(points[i].z))
				points[i] = null;
		}
	}
	/**
	 * Returns the center of the first numPoints of the array pointSet.
	 */
	protected Vector3D getCenterOfPoints(Vector3D[] pointSet, int numPoints){
		double x=0.0, y=0.0, z=0.0;
		for (int i = 0; i < numPoints; i++) {
			x = x + pointSet[i].x;
			y = y + pointSet[i].y;
			z = z + pointSet[i].z;
		}
		return new Vector3D(x/numPoints, y/numPoints, z/numPoints);
	}
	
	/**
	 * Returns a point (x(t), y(t), z(t)) on the curve, given a value of t.
	 * The return value can be null, indicating a break in the curve.  Subclasses must
	 * define this method to specify a particular curve.
	 */
	abstract protected Vector3D value(double t);
	
	/**
	 * Finds the derivative (x'(t),y'(t),z'(t)) at a given point on the curve.  This class
	 * defines the derivative using an approximation based on nearby points on the curve.  While
	 * this is probably good enough, subclasses can override this if they want, to provide an
	 * exact formula for the derivative.  The return value can be null if the curve or its
	 * derivative is undefined at the specified value of t.
	 */
	protected Vector3D deriv1(double t) {
		double dx = 0.00005;
		Vector3D f1 = value(t + dx);
		Vector3D f2 = value(t + 2*dx);
		Vector3D g1 = value(t - dx);
		Vector3D g2 = value(t - 2*dx);
		f1.x = ((8 * f1.x + g2.x) - (f2.x + 8 * g1.x))/(12 * dx);
		f1.y = ((8 * f1.y + g2.y) - (f2.y + 8 * g1.y))/(12 * dx);
		f1.z = ((8 * f1.z + g2.z) - (f2.z + 8 * g1.z))/(12 * dx);
		if (f1 == null || f2 == null || g1 == null || g2 == null)
			return null;
		return f1;
	}
	
	/**
	 * Finds the second derivative (x''(t),y''(t),z''(t)) at a given point on the curve.  This class
	 * defines the second derivative using an approximation based on nearby points on the curve.  While
	 * this is probably good enough, subclasses can override this if they want, to provide an
	 * exact formula for the derivative.  The return value can be null if the curve or its second
	 * derivative is undefined at the specified value of t.
	 */
	protected Vector3D deriv2(double t) {
		double dx = 0.00005;
		Vector3D f0 = value(t);
		Vector3D f1 = value(t + dx);
		Vector3D f2 = value(t + 2*dx);
		Vector3D g1 = value(t - dx);
		Vector3D g2 = value(t - 2*dx);
		if (f0 == null || f1 == null || f2 == null || g1 == null || g2 == null)
			return null;
		f0.x = ((16 * f1.x  + 16 * g1.x) - 30 * f0.x - f2.x - g2.x) / (12 * dx * dx);
		f0.y = ((16 * f1.y  + 16 * g1.y) - 30 * f0.y - f2.y - g2.y) / (12 * dx * dx);
		f0.z = ((16 * f1.z  + 16 * g1.z) - 30 * f0.z - f2.z - g2.z) / (12 * dx * dx);
		return f0;
	}
	
	/**
	 * Computes spherical coordinates (x,y,z)(theta, phi).
     * The z-axis is the pol-direction, theta = 0 gives (0,0,1), theta \in  [0, pi],   phi \in  [0, 2 pi].
	 */
	public Vector3D geographicCoordinates(double theta, double phi) {
		double x = Math.sin(theta)*Math.cos(phi);
		double y = Math.sin(theta)*Math.sin(phi);
		double z = Math.cos(theta);
		return new Vector3D(x,y,z);
	}
	
	/**
	 * Returns the t-value used to calculate the i-th point (x(t),y(t),z(t)) on the curve.
	 */
	public double getT(int index) {
		return tVals[index];
	}
	
	/**
	 * Returns the t-resolution, the number of subintervals into which the interval is divided.
	 * The size of the array that defines the curve is one plus this value.
	 */
	public int getTResolution() {
		return tResolution.getValue();
	}
	
	/**
	 * Returns an array of four vectors representing the Repere Mobile to the curve at a specified t value.
	 * The array has length 4.  The first vector is the point on the curve, and the other three vectors are
	 * the unit tangent, unit normal, and unit bi-normal to the curve at that point.  The return value can
	 * be null, if the curve or its first or second derivative is not defined at the specified point.
	 * If the return value is non-null, then all four vectors in the returned array are non-null.
	 */
	public Vector3D[] makeRepereMobile(double t) {
		Vector3D point = value(t);
		Vector3D deriv = deriv1(t);
		Vector3D deriv2 = deriv2(t);
		if (point == null || deriv == null || deriv2 == null)
			return null;
		Vector3D B = deriv.cross(deriv2);
		deriv.normalize();
		B.normalize();
		Vector3D N = B.cross(deriv);
		if (Double.isNaN(N.x) || Double.isInfinite(N.y) || Double.isNaN(N.y) || Double.isInfinite(N.y) || Double.isNaN(N.z) || Double.isInfinite(N.z))
			return null;
		return new Vector3D[] { point, deriv, N, B };
	}
	
	/**
	 * Returns a View of type {@link SpaceCurveParametricView} as the default view of this curve.  In this view,
	 * the curve appears as a curve.
	 */
	public View getDefaultView() {
		View view = new SpaceCurveParametricView();
		view.setName("vmm.spacecurve.parametric.SpaceCurveParametric.view.ViewAsCurve");
		return view;
	}
	
	/**
	 * Returns an array containing a single item, which is a View of type {@link View3DLit}.  This is an alternative
	 * view of the curve in which the curve appears as a tube that surrounds the curve itself.
	 */
	public View[] getAlternativeViews() {
		View view = new SpaceCurveParametricViewAsTube();
		view.setAntialiased(true);
		view.setName("vmm.spacecurve.parametric.SpaceCurveParametric.view.ViewAsTube");
		return new View[] { view };
	}
	
	/**
	 * Returns a list of actions that can be applied to this exhibit in the specified view.  The list
	 * depends on whether the view is of type SpaceCurveParametricView or View3DLit.
	 */
	public ActionList getActionsForView(final View view) {
		ActionList commands = super.getActionsForView(view);
		if (view instanceof SpaceCurveParametricView) {
			commands.add(null);
			commands.add( new AbstractActionVMM(I18n.tr("vmm.spacecurve.parametric.SpaceCurveParameteric.showRepereMobile")) {
				public void actionPerformed(ActionEvent evt) {
					Display display = view.getDisplay();
					display.installAnimation( new TimerAnimation(getTResolution(),40) {
						RepereMobileDecoration dec = new RepereMobileDecoration();
						protected void animationEnding() {
							view.removeDecoration(dec);
						}

						protected void animationStarting() {
							dec.setCurve(SpaceCurveParametric.this);
							view.addDecoration(dec);
						}

						protected void drawFrame() {
							dec.setIndex(getFrameNumber());
						}
					});
				}
			});
			commands.add(((SpaceCurveParametricView)view).showOsculatingCirclesAction);
			commands.add(((SpaceCurveParametricView)view).showEvoluteToggle);
		}
		else if (view instanceof SpaceCurveParametricViewAsTube) {
			commands.add(null);
			commands.add(((SpaceCurveParametricViewAsTube)view).showGridToggle);
		}
		return commands;
	}
	
	/**
	 * Returns an animation that shows the curve being drawn bit-by-bit.
	 * @param view The View where the creation animation will be shown.  If this
	 * is null or if it is not an instance of {@link SpaceCurveParametricView}, then the return
	 * value is null.  The create animation is not used for a tube view of the curve;
	 * a build animation is used instead.  A curce can be shown in a plaine View3D,
	 * but in that case no creation or build animation is used.
	 * @see #getBuildAnimation(View)
	 */
	public Animation getCreateAnimation(final View view) {
		if (view == null || (! (view instanceof SpaceCurveParametricView)))
			return null;
		return new TimerAnimation(75,20) { // 75 frames, 20 mulliseconds per frame
			protected void drawFrame() { 
				((SpaceCurveParametricView)view).fractionToDraw = frameNumber/75.0;
				view.forceRedraw();
			}
			public void animationStarting() {
				((SpaceCurveParametricView)view).fractionToDraw = 0;
			}
			public void animationEnding() {
				((SpaceCurveParametricView)view).fractionToDraw = 1;
				view.forceRedraw();
			}
		};
	}
	
	/**
	 * Returns a build animation of the tube view of the curve, that shows the tube being constructed
	 * from back to front.  If the view is not a View3DLit, then the return value is null.
	 * Note that a build animation is used only for the tube view of the curve; for the
	 * regular view, a create animation is used instead.
	 * @see #getCreateAnimation(View)
	 */
	public Animation getBuildAnimation(View view) {
		if ( ! (view instanceof View3DLit) )
			return null;
		if ( ((View3DLit)view).getRenderingStyle() == View3DLit.WIREFRAME_RENDERING )
			return null;
		final View3DLit view3D = (View3DLit)view;
		return new TimerAnimation(0,20) {
			private double percentDrawn;
			private double batchSize = 1.0/50.0 + 0.0001;
			protected void drawFrame() {
				if (percentDrawn > 1) {
					cancel();
					return;
				}
				if (! view3D.beginDrawToOffscreenImage())
					return;
				view3D.drawSurface(tube,percentDrawn,percentDrawn+batchSize);
				view3D.endDrawToOffscreenImage();
				view3D.getDisplay().repaint();
				percentDrawn += batchSize;
			}
		};
	}

	/**
	 * Computes the data needed to render the exhibit.  For a regular curve view, the computeDrawData method from the superclass
	 * is called.  For a tube view in a View3DLit, the data for the tube surface is computed.
	 */
	protected void computeDrawData3D(View3D view, boolean exhibitNeedsRedraw, Transform3D previousTransform, Transform3D newTransform) {
		super.computeDrawData3D(view,exhibitNeedsRedraw,previousTransform,newTransform);
		if (tube == null || exhibitNeedsRedraw) {
			int uPatches = tResolution.getValue();
			int vPatches = tubeSides.getValue();
			if (tube == null || tube.getUPatchCount() != uPatches || tube.getVPatchCount() != vPatches)
				tube = new Grid3D(uPatches,vPatches,1);
			for (int i = 0; i <= uPatches; i++) {
				Vector3D[] rm = makeRepereMobile(tVals[i]);  // array contains newly constructed vectors, so it's OK to modify them
				if (rm == null) {
					for (int j = 0; j <= vPatches; j++)
						tube.setVertex(i,j,null);
				}
				else {
					Vector3D N = rm[2];
					Vector3D B = rm[3];
					N = N.times(tubeSize.getValue() / 2);
					B = B.times(tubeSize.getValue() / 2);
					for (int j = 0; j <= vPatches; j++) {
						double angle = j * 2*Math.PI / vPatches;
						Vector3D v = rm[0].plus(N.times(Math.cos(angle))).plus(B.times(Math.sin(angle)));
						tube.setVertex(i,j,v);
					}
				}
			}
		}
		boolean showGrid = true;
		if (view instanceof SpaceCurveParametricViewAsTube)
			showGrid = ((SpaceCurveParametricViewAsTube)view).getShowGrid();
		tube.setUCurveIncrement(showGrid? 1 : 0);
		tube.setVCurveIncrement(showGrid? 1 : 0);
	}
	
	/**
	 * Draws either a tube view or a regular curve view of this exhibit, depending on the type of View in
	 * which the curve is being rendered.
	 */
	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
		if (view instanceof View3DLit) {
			((View3DLit)view).drawSurface(tube);
		}
		else {
			if (points.length == 0)
				return;
			int pointCt = points.length;
			if (view instanceof SpaceCurveParametricView) {
				double fraction = ((SpaceCurveParametricView)view).fractionToDraw;
				if (fraction >= 0 && fraction < 1)
					pointCt = (int)(fraction*pointCt);
				if (pointCt == 0)
					pointCt = 1;
			}
			boolean useReverseCollar = false;
			if (view instanceof SpaceCurveView)
				useReverseCollar = ((SpaceCurveView)view).getUseReverseCollar();
			view.drawCollaredCurve(points,pointCt,useReverseCollar);
		}
	}

	/**
	 * Defines the default View of a SpaceCurveParametric.  The only differences
	 * between this and a regular View3D is that SpaceCurveParametricView is antialiased by default
	 * and it includes a member variable "fractionToDraw" that tells what fraction
	 * of the curve should actually be drawn in the view; this feature is only
	 * used in the creation animation, which shows a sequence of frames in which the
	 * fractionToDraw is gradually increased.
	 * <p>NOTE:  This has to be a public static class so that objects of this type
	 * can be created when a saved exbhit is restored in {@link SaveAndRestore}.
	 */
	public class SpaceCurveParametricView extends SpaceCurveView {
		public SpaceCurveParametricView() {
			setAntialiased(true);
		}
		double fractionToDraw = -1;
		@VMMSave private boolean showEvolute;
		EvoluteDecoration evolute;
		ToggleAction showEvoluteToggle = new ToggleAction(I18n.tr("vmm.spacecurve.parametric.SpaceCurveParametric.ShowEvolute")) {
			public void actionPerformed(ActionEvent evt) {
				setShowEvolute(getState());
			}
		};
		AbstractActionVMM showOsculatingCirclesAction = new AbstractActionVMM(I18n.tr("vmm.spacecurve.parametric.SpaceCurveParametric.ShowOsculatingCircles")) {
			public void actionPerformed(ActionEvent evt) {
				getDisplay().installAnimation(new TimerAnimation() { 
					protected void drawFrame() {
						if (evolute == null)
							cancel();
						else {
							evolute.circleIndex++;
							if (evolute.circleIndex >= getPointCount())
								cancel();
							else
								forceRedraw();
						}
					}
					protected void animationEnding() {
						evolute.showCompleteEvolute = true;
						forceRedraw();
					}
					protected void animationStarting() {
						if (!showEvolute)
							evolute = new EvoluteDecoration();
						evolute.showCompleteEvolute = false;
						evolute.showCompleteEvolute = false;
						evolute.circleIndex = 0;
						setShowEvolute(true);
						forceRedraw();
					}
				});
			}
		};
		public boolean getShowEvolute() {
			return showEvolute;
		}
		public void setShowEvolute(boolean showEvolute) {
			if (this.showEvolute == showEvolute)
				return;
			this.showEvolute = showEvolute;
			showEvoluteToggle.setState(showEvolute);
			if (showEvolute) {
				if (evolute == null)  // evolute can be set up by the osculatingCircleAnimation
					evolute = new EvoluteDecoration();
				addDecoration(evolute);
			}
			else {
				removeDecoration(evolute);
				evolute = null;
			}
		}
	}

	/**
	 * Defines a tube view of a SpaceCurveParametric.  Adds to View3DLit a ToggleAction
	 * to determine whether the grid on the curve is shown.
	 */
	public static class SpaceCurveParametricViewAsTube extends View3DLit {
		private ToggleAction showGridToggle = new ToggleAction(I18n.tr("vmm.spacecurve.parametric.SpaceCurveParametric.ShowTubeGrid"),true) {
			public void actionPerformed(ActionEvent evt) {
				setShowGrid(getState());
			}
		};
		@VMMSave private boolean showGrid = true;
		public boolean getShowGrid() {
			return showGrid;
		}
		public void setShowGrid(boolean showGrid) {
			if (showGrid == this.showGrid)
				return;
			this.showGrid = showGrid;
			showGridToggle.setState(showGrid);
			forceRedraw();
		}
	}

	private class EvoluteDecoration extends Decoration {
		private final int POINTS_ON_CIRCLE = 180;
		private Vector3D[] evolutePoints;
		private boolean showCompleteEvolute = true;
		private int circleIndex;
		private final Color COLOR = Color.BLUE;
		public void computeDrawData(View view, boolean exhibitNeedsRedraw, Transform previousTransform, Transform newTransform) {
			if (exhibitNeedsRedraw || decorationNeedsRedraw) {
				int curvePointCount = getPointCount();
				if (curvePointCount == 0) {
					evolutePoints = null;
					return;
				}
				evolutePoints = new Vector3D[curvePointCount];
				int pts;
				if (showCompleteEvolute)
					pts = curvePointCount;
				else {
					pts = circleIndex;
					if (pts > curvePointCount)
						pts = curvePointCount;
				}
				for (int i = 0; i < pts; i++)
					evolutePoints[i] = centerOfOsculatingCircle(i);
			}
		}
		public void doDraw(Graphics2D g, View view, Transform transform) {
			if (evolutePoints == null)
				return;  // nothing to draw
			View3D v3 = (View3D)view;
			Color saveColor = view.getColor();
			view.setColor( v3.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW ? Color.LIGHT_GRAY : COLOR);
			if (showCompleteEvolute) {
				view.setStrokeSizeMultiplier(2);
				v3.drawCurve(evolutePoints);
				view.setStrokeSizeMultiplier(1);
			}
			else {
				if (circleIndex >= 0 && circleIndex < evolutePoints.length)
					evolutePoints[circleIndex] = drawOsculatingCircle(v3);
				if (circleIndex > 0) {
					view.setStrokeSizeMultiplier(2);
					v3.drawCurve(evolutePoints,circleIndex+1);
					view.setStrokeSizeMultiplier(1);
				}
			}
			view.setColor(saveColor);
		}
		private Vector3D drawOsculatingCircle(View3D view) {  // returns the center of the circle
			double t = getT(circleIndex);
			Vector3D pt = value(t);
			Vector3D deriv = deriv1(t);
			Vector3D deriv2 = deriv2(t);
			if (pt == null || deriv == null || deriv2 == null)
				return null;
			double kappa = deriv.cross(deriv2).norm() / Math.pow(deriv.norm(),3);
			double radius = 1/kappa;
			if (Double.isNaN(radius) || radius > 10000)
				return null;
			Vector3D B = deriv.cross(deriv2);
			deriv.normalize();
			B.normalize();
			Vector3D N = B.cross(deriv);
			if (Double.isNaN(N.x) || Double.isInfinite(N.y) || Double.isNaN(N.y) || Double.isInfinite(N.y) || Double.isNaN(N.z) || Double.isInfinite(N.z))
				return null;
			N.normalize();
			Vector3D v1 = N.times(radius);
			Vector3D v2 = deriv.times(radius);
			Vector3D center = pt.plus(v1);
			v1.negate();
			Vector3D pt1 = center.plus(v1);
			for (int i = 1; i <= POINTS_ON_CIRCLE; i++) {
				double angle = (2*Math.PI*i)/POINTS_ON_CIRCLE;
				Vector3D pt2 = center.plus(v1.times(Math.cos(angle))).plus(v2.times(Math.sin(angle)));
				view.drawLine(pt1,pt2);
				pt1 = pt2;
			}
			view.drawLine(center,pt);
			return center;
		}
		private Vector3D centerOfOsculatingCircle(int index) {
			double t = getT(index);
			Vector3D pt = value(t);
			Vector3D deriv = deriv1(t);
			Vector3D deriv2 = deriv2(t);
			if (pt == null || deriv == null || deriv2 == null)
				return null;
			double kappa = deriv.cross(deriv2).norm() / Math.pow(deriv.norm(),3);
			double radius = 1/kappa;
			if (Double.isNaN(radius) || radius > 10000)
				return null;
			Vector3D B = deriv.cross(deriv2);
			deriv.normalize();
			B.normalize();
			Vector3D N = B.cross(deriv);
			N.normalize();
			if (Double.isNaN(N.x) || Double.isInfinite(N.y) || Double.isNaN(N.y) || Double.isInfinite(N.y) || Double.isNaN(N.z) || Double.isInfinite(N.z))
				return null;
			Vector3D center = pt.plus(N.times(radius));
			return center;
		}
	}

}
