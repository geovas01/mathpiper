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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import vmm.core.Decoration;
import vmm.core.Exhibit;
import vmm.core.Transform;
import vmm.core.VMMSave;
import vmm.core.View;

/**
 * A decoration that computes normal lines to points on a ParametricPlaneCurve and
 * that can also show osculating circles, the evolute of the curve, and one or two parallel curves.
 * (The points are those that are in the point array that defines the curve.)
 * Either unit normals or normals whose length is given by the radius of curvature
 * can be shown, and they can be shown at any number of points, including zero;
 * this allows a partial normal bundle to be shown, so that the bundle can be built
 * up gradually on the screen by adding one vector at a time.
 * The decoration can also show several features that are computed based on
 * the normal bundle: the evolute of the curve, an osculating circle at one point,
 * and parallel curves.
 */
public class NormalBundleDecoration extends Decoration {

	private PlaneCurveParametric curve;
	@VMMSave private int pointCt;  // How many points are normals shown on?
	private Line2D.Double[] unitNormals; // Unit normals to the curve
	private Line2D.Double[] evoluteNormals;  // Normals with length equal to radius of osculatting circles
	private Line2D.Double[] lines; // Actual normals drawn; pointer to either unitNormals or evoluteNormals
	
	@VMMSave private boolean useUnitNormals = false;
	@VMMSave private boolean showEvolute = false;
	private boolean showEvoluteWithOsculatingCircle = false;
	@VMMSave private boolean showTwoParallelCurves = true;
	@VMMSave private double parallelCurveOffset = Double.NaN;
	@VMMSave private int osculatingCircleIndex = -1;
	private double maxNormalLength;
	@VMMSave private Color normalColor = Color.red;
	@VMMSave private Color evoluteColor = new Color(0,200,0);
	@VMMSave private Color osculatingCircleColor = Color.blue;
	@VMMSave private Color parallelCurveColor = new Color(60,180,180);
	@VMMSave private Color parallelCurveColor2 = new Color(200,100,100);
	
	/**
	 * Creates a normal bundle with no associated curve.  The curve can be set later with
	 * {@link #setCurve(PlaneCurveParametric)}.  This constructor
	 * exists mostly because it is required when the decoration is saved to an XML file.
	 */
	public NormalBundleDecoration() {
		
	}
	
	/**
	 * Create a decoration that can be used on a specified curve.  By default, nothing is
	 * actually visible.  Use the methods setShowEvolute(), setPointCount(), and setOsculatingCircleIndex()
	 * to make various things visible.
	 * @param curve The  curve object to which the decoration applies. (A null value is OK but is unusual.)
	 */
	public NormalBundleDecoration(PlaneCurveParametric curve) {
		this.curve = curve;
	}
	
	/**
	 * Returns the curve pn which the decoration is shown.
	 */
	public PlaneCurveParametric getCurve() {
		return curve;
	}
	
	/**
	 * Set the curve on which this decoration is shown.  (Usually, the curve is set in
	 * the constructor, and if it is not, it will be set implicitely when <code>computeDrawData</code>
	 * is called.)
	 */
	public void setCurve(PlaneCurveParametric c) {
		curve = c;
	}

	/**
	 * Sets the number of points on the curve at which normals appear.  If the
	 * specified number of points is less than zero, it is treated as zero.
	 * In the specified number is greater than the number of points on the curve
	 * (that is, greater than <code>1+curve.getTResolution()</code>), it is not considered an
	 * error, and all normals are drawn.  Thus, setting the number of points
	 * to a very large value will ensure that all normals are drawn.
	 */
	public void setPointCount(int pointCt) {
		if (pointCt < 0)
			pointCt = 0;
		if (this.pointCt != pointCt) {	
			this.pointCt = pointCt;
			fireDecorationChangeEvent();
		}
	}
	
	/**
	 * Gets the number of points on which normals appear.
	 * @see #setPointCount(int)
	 */
	public int getPointCount() {
		return pointCt;
	}
	
	/**
	 * Tells whether the evolute is curve is drawn.
	 * @see #setShowEvolute(boolean)
	 */
	public boolean getShowEvolute() {
		return showEvolute;
	}
	
	/**
	 * If set to true, then the evolute curve will be drawn. This is the curve through the
	 * centers of the osculating circles.  By default, it is not shown.
	 */
	public void setShowEvolute(boolean show) {
		if (show != showEvolute) {
			showEvolute = show;
			fireDecorationChangeEvent();
		}
	}
	
	/**
	 * Tells the distance between the curve and any parallel curves that are drawn.
	 * The default value, Double.NaN, indicates that the parallel curves are not drawn.
	 * @see #setParallelCurveOffset(double)
	 */
	public double getParallelCurveOffset() {
		return parallelCurveOffset;
	}
	
	/**
	 * If the parallelCurveOffset is set to Double.NaN, then no parallel curve is drawn; this is 
	 * the default.  Otherwise, a parallel curve is drawn that connects the endpoints of normal lines
	 * of length given by the offsets.  If in addition the value of showTwoParallelCurves is true, then
	 * a second parallel curve is drawn with the negative of the specified offset.
	 * @see #setShowTwoParallelCurves(boolean)
	 */
	public void setParallelCurveOffset(double offset) {
		if ( ! (offset == parallelCurveOffset || (Double.isNaN(offset) && Double.isNaN(parallelCurveOffset))) ) {
			parallelCurveOffset = offset;
			fireDecorationChangeEvent();
		}
	}

	/**
	 * Retruns the color used for drawing normal vectors.
	 * @see #setNormalsColor(Color)
	 */
	public Color getNormalsColor() {
		return normalColor;
	}

	/**
	 * Set the color used for the normal lines; the default is red.
	 * @param color The color to be used.  If this is null, the default (red) is used.
	 */
	public void setNormalsColor(Color color) {
		if (color == null)
			color = Color.red;
		if (!color.equals(normalColor)) {
			normalColor = color;
			if (pointCt > 0)
				fireDecorationChangeEvent();
		}
	}

	/**
	 * Returns the color used for drawing the evolute.
	 * @see #setEvoluteColor(Color)
	 */
	public Color getEvoluteColor() {
		return evoluteColor;
	}

	/**
	 * Set the color used for the evolute; the default is a medium dark green.
	 * @param color The color to be used.  If this is null, the default is used.
	 */
	public void setEvoluteColor(Color color) {
		if (color == null)
			color = new Color(0,200,0);
		if (!color.equals(evoluteColor)) {
			evoluteColor = color;
			if (showEvolute)
				fireDecorationChangeEvent();
		}
	}

	/**
	 * Retruns the color used for drawing the osculating circle.
	 * @see #setOsculatingCircleColor(Color)
	 */
	public Color getOsculatingCircleColor() {
		return osculatingCircleColor;
	}

	/**
	 * Set the color used for the osculating circle; the default is blue.
	 * @param color The color to be used.  If this is null, the default (blue) is used.
	 */
	public void setOsculatingCircleColor(Color color) {
		if (color == null)
			color = Color.blue;
		if (!color.equals(osculatingCircleColor)) {
			osculatingCircleColor = color;
			if (osculatingCircleIndex >= 0)
				fireDecorationChangeEvent();
		}
	}

	/**
	 * Returns the color used for drawing the (first) parallel curve.
	 * @see #setParallelCurveColor(Color)
	 */
	public Color getParallelCurveColor() {
		return parallelCurveColor;
	}

	/**
	 * Set the color used for the parallel curve; the default is a dark-ish cyan.
	 * @param color The color to be used.  If this is null, the default  is used.
	 */
	public void setParallelCurveColor(Color color) {
		if (color == null)
			color = new Color(60,180,180);
		if (!color.equals(parallelCurveColor)) {
			parallelCurveColor = color;
			if ( ! Double.isNaN(parallelCurveOffset) )
				fireDecorationChangeEvent();
		}
	}

	/**
	 * Returns the color used for drawing the second parallel curve.
	 * @see #setParallelCurveColor2(Color)
	 */
	public Color getParallelCurveColor2() {
		return parallelCurveColor;
	}

	/**
	 * Set the color used for the second parallel curve, if there is one; the default is a light red.
	 * @param color The color to be used.  If this is null, the default  is used.
	 * @see #setShowTwoParallelCurves(boolean)
	 */
	public void setParallelCurveColor2(Color color) {
		if (color == null)
			color = new Color(200,100,100);
		if (!color.equals(parallelCurveColor2)) {
			parallelCurveColor2 = color;
			if ( ! Double.isNaN(parallelCurveOffset) && showTwoParallelCurves)
				fireDecorationChangeEvent();
		}
	}
	
	/**
	 * Tells whether two parallel curves are drawn, or just one.
	 * @see #setShowTwoParallelCurves(boolean)
	 */
	public boolean getShowTwoParallelCurves() {
		return showTwoParallelCurves;
	}
	
	/**
	 * If set to true, two parallel curves are drawn whenever the parallelCurveOffset
	 * property is set to a real number.  The offset for the second curve is the
	 * negative of the value of parallelCurveOffset.  The default value is true.
	 * @see #setParallelCurveOffset(double)
	 */
	public void setShowTwoParallelCurves(boolean show) {
		if (show != showTwoParallelCurves) {
			showTwoParallelCurves = show;
			if ( ! Double.isNaN(parallelCurveOffset))
				fireDecorationChangeEvent();
		}
	}

	/**
	 * Tells whether unit normals are drawn, or normals of length equal to the radius of the osculatting circle.
	 * @see #setUseUnitNormals(boolean)
	 */
	public boolean getUseUnitNormals() {
		return useUnitNormals;
	}
	
	/**
	 * Tells the index of the point where an osculatting circle is drawn.
	 * @see #setOsculatingCircleIndex(int)
	 */
	public int getOsculatingCircleIndex() {
		return osculatingCircleIndex;
	}
	
	/**
	 * Set the index of the point on the curve where an osculating circle is to be shown.
	 * If the index is less than zero, no circle is shown; this is the default.  Otherwise,
	 * the value is clamped to the range 0 to curve.getTResolution(), inclusive, and an
	 * osculating circle appears at the specified point on the curve.  When an osculating
	 * circle is drawn, its radius is also drawn, and the evolute curve is also drawn.
	 */
	public void setOsculatingCircleIndex(int i) {
		setOsculatingCircleIndex(i,false);
	}

	/**
	 * Set the index of the point on the curve where an osculating circle is to be shown.
	 * If the index is less than zero, no circle is shown; this is the default.  Otherwise,
	 * the value is clamped to the range 0 to curve.getTResolution(), inclusive, and an
	 * osculating circle appears at the specified point on the curve.  When an osculating
	 * circle is drawn, its radius is also drawn.  If showEvoluteSoFar is true, then
	 * the evolute curve is shown from the first point on the curve to the point specified for
	 * the osculating circle.
	 */
	public void setOsculatingCircleIndex(int i, boolean showEvoluteSoFar) {
		if (i < 0)
			i = -1;
		if (i != osculatingCircleIndex || showEvoluteSoFar != showEvoluteWithOsculatingCircle) {
			this.osculatingCircleIndex = i;
			showEvoluteWithOsculatingCircle = showEvoluteSoFar;
			fireDecorationChangeEvent();
		}
	}

	
	/**
	 * Set whether unit normals are drawn.  The default is false, meaning
	 * that the length of a normal will be the radius of the osculating circle
	 * (except that it will be limited to a large maximum length).
	 */
	public void setUseUnitNormals(boolean useUnitNormals) {
		if (useUnitNormals != this.useUnitNormals) {
			this.useUnitNormals = useUnitNormals;
			lines = (useUnitNormals? unitNormals : evoluteNormals);
			fireDecorationChangeEvent();
		}
	}

	/**
	 * Gets an array containing unit normals for all points on the curve.  If this method is
	 * called before the decoration has been drawn, the return value will be null.  An elemeent
	 * of the array can be null, if the curve or its derivative is undefined as the point.
	 */
	public Line2D[] getUnitNormals() {
		return unitNormals;
	}
		
	/**
	 * Computes the normal vectors that are needed for drawing the array.
	 */
	public void computeDrawData(View view, boolean exhibitNeedsRedraw, Transform previousLimits, Transform newLimits) {
		if (!(exhibitNeedsRedraw || decorationNeedsRedraw))
			return;
		if (curve == null && view != null) {
			Exhibit c = view.getExhibit();
			if (c instanceof PlaneCurveParametric) // it better be!
				curve = (PlaneCurveParametric)c;
		}
		int points = curve.getTResolution();
		unitNormals = new Line2D.Double[points+1];
		evoluteNormals = new Line2D.Double[points+1];
		maxNormalLength = newLimits.getPixelWidth() * 5000;
		for (int i = 0; i <= points; i++) {
			double t = curve.getT(i);
			double x = curve.xValue(t);
			double y = curve.yValue(t);
			double dx = curve.xDerivativeValue(t);
			double dy = curve.yDerivativeValue(t);
			double dx2 = curve.x2ndDerivativeValue(t);
			double dy2 = curve.y2ndDerivativeValue(t);
			double length = Math.sqrt(dx*dx + dy*dy);
			double unit_dx = dx/length;
			double unit_dy = dy/length;
			if (Double.isNaN(x) || Double.isInfinite(x) || Double.isNaN(y) || Double.isInfinite(y) ||
					Double.isNaN(unit_dx) || Double.isInfinite(unit_dx) || Double.isNaN(unit_dy) || Double.isInfinite(unit_dy))
				continue;
			unitNormals[i] = new Line2D.Double(x, y, x - unit_dy, y + unit_dx);
			if (Double.isNaN(dx2) || Double.isInfinite(dx2) || Double.isNaN(dy2) || Double.isInfinite(dy2))
				continue;
			double curvature = (dx*dy2 - dy*dx2) / (length*length*length);
			double radius = 1/curvature;
			if (Math.abs(radius) > maxNormalLength)
				radius = (radius > 0)? maxNormalLength : -maxNormalLength;
			evoluteNormals[i] = new Line2D.Double(x, y, x - unit_dy*radius, y + unit_dx*radius);
		}
		lines = (useUnitNormals? unitNormals : evoluteNormals);
		decorationNeedsRedraw = false;
	}
	
	public void doDraw(Graphics2D g, View view, Transform limits) {
		Color saveColor = g.getColor();
		double maxJump = Math.max(limits.getXmax() - limits.getXmin(), limits.getYmax() - limits.getYmin())/4;
		int pointsToDraw = pointCt;
		if (pointsToDraw > curve.getTResolution())
			pointsToDraw = curve.getTResolution() + 1;
		if (pointsToDraw > 0) {
			g.setColor(normalColor);
			for (int i = 0; i < pointsToDraw; i++)
				if (lines[i] != null)
					g.draw(lines[i]);
		}
		int oci = osculatingCircleIndex;
		if (oci > curve.getTResolution())
			oci = curve.getTResolution();
		if (showEvolute || oci >= 0 ) {
			g.setColor(evoluteColor);
			double maxLength = 5000*limits.getPixelWidth();
			double x = Double.NaN;
			double y = Double.NaN;
			int points = (oci >= 0)? oci + 1: evoluteNormals.length;
			for (int i = 0; i < points; i++) {
				double x1, y1, length = 0;
				if (evoluteNormals[i] == null)
					x1 = y1 = Double.NaN;
				else {
					x1 = evoluteNormals[i].getX2();
					y1 = evoluteNormals[i].getY2();
					length = Math.sqrt(Math.pow(x1-evoluteNormals[i].getX1(),2) + Math.pow(y1-evoluteNormals[i].getY1(),2));
				}
				if ( ! (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(x1) || Double.isNaN(y1)) 
						&& Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1)) < maxJump && length <= maxLength)
					g.draw(new Line2D.Double(x,y,x1,y1));
				x = x1;
				y = y1;
			}
		}
		if (oci >= 0 && evoluteNormals[oci] != null) {
			g.setColor(osculatingCircleColor);
			Line2D.Double v = evoluteNormals[oci];
			g.draw(v);  // The radius of the circle.
			double x = v.getX1();
			double y = v.getY1();
			double dx = v.getX2() - v.getX1();
			double dy = v.getY2() - v.getY1();
			double radius = Math.sqrt(dx*dx + dy*dy);
			g.draw( new Ellipse2D.Double(x+dx-radius,y+dy-radius,2*radius,2*radius));
		}
		if (! (Double.isNaN(parallelCurveOffset) || Double.isInfinite(parallelCurveOffset)) ) {
			g.setColor(parallelCurveColor);
			double x = Double.NaN;
			double y = Double.NaN;
			for (int i = 0; i < unitNormals.length; i++) {
				double x1, y1;
				if (unitNormals[i] == null)
					x1 = y1 = Double.NaN;
				else {
					Line2D.Double v = unitNormals[i];
					x1 = v.getX1() + parallelCurveOffset*(v.getX2() - v.getX1());
					y1 = v.getY1() + parallelCurveOffset*(v.getY2() - v.getY1());
				}
				if ( ! (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(x1) || Double.isNaN(y1)) ) {
					if (Math.abs(x-x1) + Math.abs(y-y1) <= maxJump)
						g.draw(new Line2D.Double(x,y,x1,y1));
				}
				x = x1;
				y = y1;
			}
			if (showTwoParallelCurves) {
				g.setColor(parallelCurveColor2);
				x = Double.NaN;
				y = Double.NaN;
				for (int i = 0; i < unitNormals.length; i++) {
					double x1, y1;
					if (unitNormals[i] == null)
						x1 = y1 = Double.NaN;
					else {
						Line2D.Double v = unitNormals[i];
						x1 = v.getX1() - parallelCurveOffset*(v.getX2() - v.getX1());
						y1 = v.getY1() - parallelCurveOffset*(v.getY2() - v.getY1());
					}
					if ( ! (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(x1) || Double.isNaN(y1)) ) {
						if (Math.abs(x-x1) + Math.abs(y-y1) <= maxJump)
							g.draw(new Line2D.Double(x,y,x1,y1));
					}
					x = x1;
					y = y1;
				}
			}
		}
		g.setColor(saveColor);
	}
	

}
