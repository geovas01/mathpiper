/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.fractals;

import java.awt.Color;
import java.awt.Graphics2D;

import vmm.core.Animation;
import vmm.core.Complex;
import vmm.core.Exhibit;
import vmm.core.IntegerParam;
import vmm.core.RealParamAnimateable;
import vmm.core.SaveAndRestore;
import vmm.core.TimerAnimation;
import vmm.core.Transform;
import vmm.core.View;

/**
 * A fractal curve in which a level (N+1) approximation of the fractal is computed
 * from a level N approximation by replacing each line segement with a scaled and
 * transformed copy of the entire curve.  Examples include {@link Koch} and {@link Hilbert}.
 */
abstract public class RepeatedSegmentFractal extends Exhibit {

	private Complex [] curvePoints;  // Points on the curve approximation that has been computed.
	private int computedLevel = 0;   // Number of levels that have been computed to get curvePoints.
	
	
	/**
	 * Used in coloring the curve.  The color spectrum is repeated this many times from one
	 * end of the curve to the next.  Subclasses should set the value in their constructors.
	 * The default value, as set in this class, is 3.
	 */
	protected int colorRepeatFactor = 3;
	
	/**
	 * Used during "fast drawing", such as when the curve is being dragged.  This is the maximum
	 * level of recursion that is used during fast drawing.  Subclasses should set the value
	 * in their constructors.  The default value, as set in this class, is 5.
	 */
	protected int fastDrawRecursionLevel = 5;
	
	/**
	 * This parameter determines the fractal dimension of the curve.  The default value is 1/3.  The default
	 * limits for animation are 0.25 to 0.5.  These are also set as the minimum and maximum allowed values
	 * for user input.  (These values are suitable for the Koch curve; other subclasses should change them
	 * if necessary.)
	 */
	protected RealParamAnimateable fractality = new RealParamAnimateable("vmm.fractals.RepeatedSegmentFractal.fractality",1/3.0,0.25,0.5);

	/**
	 * Determines the number of "levels" that are computed.  The process of replacing segments of the curve
	 * with copies of the curve is repeated this many times to produce an approximation of the true fractal
	 * curve.  The default value is 6.  The minimum and maximum allowed values for input are 1 and 10.  These
	 * values should be changed by subclasses, if necessary.
	 */
	protected IntegerParam recursionLevel = new IntegerParam("vmm.fractals.RepeatedSegmentFractal.recurseLvl",6);

	
	/**
	 * This abstract method computes one approximation level from the previous one, which has already
	 * been computed.  This abstract method must be defined in any concrete subclass.
	 * @param curvePoints This array stores the points that have been computed for the current approximation level.
	 *    This array can be null, if the computedLevel is zero.
	 * @param computedLevel This is the approximation level that has been comptuted to get the points in
	 *    curvePoints. A zero value for this paramter means that the first, or base level, approximation should
	 *    be computed.
	 * @return An array containing the points in the next level approximation of the curve.
	 */
	abstract protected Complex[] computeNextLevel(Complex[] curvePoints, int computedLevel);

	
	/**
	 * Constructor sets a default color of BLACK and a default window of (-1,1,-1,1).
	 * It also adds "fractility" and "recursionLevel" parameters to the exhibit.
	 */
	public RepeatedSegmentFractal() {
		setDefaultBackground(Color.BLACK);
		addParameter(fractality);
		fractality.setMaximumValueForInput(0.5);
		fractality.setMinimumValueForInput(0.25);
		addParameter(recursionLevel);
		recursionLevel.setMaximumValueForInput(10);
		recursionLevel.setMinimumValueForInput(1);
		setDefaultWindow(-1,1,-1,1);
		setUseFilmstripForMorphing(true);
		setFramesForMorphing(25);
	}


    /**
     * Computes the data for the curve.
     */
	protected void computeDrawData(View view, boolean exhibitNeedsRedraw, Transform previousTransform, Transform newTransform) {
		RSFView fsfView = (RSFView)view;
		int newLevel;
		if (fsfView.desiredLevel == -1)  // A -1 here means we should use the recursion level specified in the exhibit
			newLevel = recursionLevel.getValue();
		else  // otherwise use the recursion level specified in the view
			newLevel = fsfView.desiredLevel;
		if (fsfView.getFastDrawing()) // true during drag, resize, ...
			newLevel = Math.min(newLevel,fastDrawRecursionLevel);
		if (exhibitNeedsRedraw || newLevel < computedLevel)
			computedLevel = 0;  // Force recompute of all data.
		while (computedLevel < newLevel) {
			curvePoints = computeNextLevel(curvePoints,computedLevel);
			computedLevel++;
		}
	}
	
	
	/**
	 * Draws the curve in continuous rainbow colors.
	 */
	protected void doDraw(Graphics2D g, View view, Transform transform) {
		for (int i = 0; i < curvePoints.length-1; i++){
			g.setColor(Color.getHSBColor((float)colorRepeatFactor*i/curvePoints.length, 1, 1));
			view.drawLine( curvePoints[i].re, curvePoints[i].im, curvePoints[i+1].re, curvePoints[i+1].im);
		}
	}

	
	/**
	 * Returns a create animation that shows the curve at each approximation level from 1 up to
	 * the currently selected level of recursion.
	 */
	public Animation getCreateAnimation(View view) {
		final RSFView rsfView = (RSFView)view;
		return new TimerAnimation(recursionLevel.getValue() -1,400){
			protected void animationStarting() {
				rsfView.desiredLevel = 1;
			}
			protected void animationEnding() {
				rsfView.desiredLevel = -1;
				rsfView.forceRedraw();
			}
			protected void drawFrame() {
				rsfView.desiredLevel = frameNumber + 1;
				rsfView.forceRedraw();
			}
		};
	}


	/**
	 * Returns a default view of type RSFView, where RSFView is a nested class inside this class.
	 */
	public View getDefaultView() {
		return new RSFView();
	}
	

	/**
	 *  Defines the default View of an exhibit of type RepeatedSegmentFractal.  This is a
	 *  trivial extension of View that just adds an integer intstance variable that specifies
	 *  the approximation level that should be drawn in this view.  This class is public only
	 *  so that it will work with {@link SaveAndRestore}.
	 */
	public class RSFView extends View {
		private int desiredLevel = -1;  // -1 means draw the final version.
	}

}
