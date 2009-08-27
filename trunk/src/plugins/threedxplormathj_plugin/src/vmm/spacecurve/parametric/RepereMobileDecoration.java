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

import vmm.core.Decoration;
import vmm.core.Exhibit;
import vmm.core.Transform;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;

/**
 * A decoration that appears as a three perpendicular line segments at a point
 * on the curve, representing the tangent, normal, and binormal vectors at that point.
 * If the curve or its derivatives are undefined at that point, nothing is drawn.
 */
public class RepereMobileDecoration extends Decoration {

	private SpaceCurveParametric curve;
	@VMMSave private double t = Double.NaN;
	
	/**
	 * Creates a RepereMobileDecoration with no associated curve.  A curve can be
	 * set later with {@link #setCurve(SpaceCurveParametric)}, or it will be set
	 * implicitly when the decoration is drawn.
	 */
	public RepereMobileDecoration() {
	}
	
	/**
	 * Returns the curve to which this decoration applies, or null if no
	 * curve has been set yet.
	 * @see #setCurve(SpaceCurveParametric)
	 */
	public SpaceCurveParametric getCurve() {
		return curve;
	}
	
	/**
	 * Sets the curve to which this decoration applies.  Note that the curve
	 * will also be set automatically the firt time the decoration is drawn.
	 */
	public void setCurve(SpaceCurveParametric c) {
		curve = c;
	}

	/**
	 * Change the value of t where the decoration appears.  This should
	 * be within the range of t-values shown on the curve, although
	 * this is not checked.
	 * @param t the t-value where the decoration is to be drawn.  A value of
	 * Double.NaN means that nothing is to be drawn; this is the initial value.
	 */
	public void setT(double t) {
		this.t = t;
		fireDecorationChangeEvent();
	}
	
	/**
	 * Returns the t-value of the point (x(t),y(t),z(t)) where the decoration
	 * is drawn on the curve.  A value of Double.NaN means that the
	 * decoration is not drawn; this is the value when the decoration is
	 * first constructed.
	 */
	public double getT() {
		return t;
	}
	
	/**
	 * Set the t-value to be at one of the points in the array of points that
	 * defines the curve. If the curve is null when this is called, it
	 * has no effect.
	 * @param tIndex An index into the array of t-values that the curve
	 * uses to generate the curve.  The index should be in the legal
	 * range; if not, nothing is drawn.  The range is from 0 to
	 * getCurve().getTResolution().  If the value is outside this range,
	 * then no decoration will be drawn
	 */
	public void setIndex(int tIndex) {
		if (curve != null && tIndex >= 0 && tIndex <= curve.getTResolution())
			setT(curve.getT(tIndex));
		else
			setT(Double.NaN);  // show no decoration
	}

	/**
	 * Draw the decoration.  If the curve is null when this is called, then
	 * curve will be set to the exhibit displayed in the View, which should
	 * be of type SpaceCurveParameteric, as long as this decoration is being used correctly.
	 * (If this is not the case, then nothing will be drawn.)  Also, the
	 * View must be of type View3D, or nothing is drawn.
	 */
	public void doDraw(Graphics2D g, View view, Transform transform) {
		if (curve == null && view != null) {
			Exhibit c = view.getExhibit();
			if (c instanceof SpaceCurveParametric)  // It better be!
				curve = (SpaceCurveParametric)c;
			else
				return;
		}
		if (Double.isNaN(t))  // indicates that decoration is not to be drawn.
			return;
		if (! (view instanceof View3D))
			return;
		View3D view3D = (View3D)view;
		Vector3D[] frame = curve.makeRepereMobile(t);
		if (frame == null)
			return;
		Color saveColor = view3D.getColor();
		view3D.setStrokeSizeMultiplier(2);
		boolean anaglyph = view3D.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW;
		double length = transform.getPixelWidth()*50;  // lines are 50 pixels long
		if (anaglyph)
			view3D.setColor(Color.white);
		else
			view3D.setColor(Color.red);
		view3D.drawLine(frame[0], frame[0].plus(frame[1].times(length)));
		if (!anaglyph)
			view3D.setColor(Color.blue);
		view3D.drawLine(frame[0], frame[0].plus(frame[2].times(length)));
		if (!anaglyph)
			view3D.setColor(Color.green);
		view3D.drawLine(frame[0], frame[0].plus(frame[3].times(length)));
		view3D.setColor(saveColor);
		view3D.setStrokeSizeMultiplier(1);
	}
	

}
