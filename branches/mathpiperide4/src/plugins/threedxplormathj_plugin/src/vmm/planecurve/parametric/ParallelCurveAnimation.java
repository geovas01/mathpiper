/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;

import vmm.core.Decorateable;
import vmm.core.ThreadedAnimation;

/**
 * Shows a moving set of parallel curves to a PlaneCurveParametric curve.  Optionally,
 * it first shows the construction fo the normal bundle to the curve.
 * The evolute is also shown.  (The animation uses a NormalBundleDecoration for the actual work.)
 */
public class ParallelCurveAnimation extends ThreadedAnimation {
	
	private PlaneCurveParametric curve;
	private Decorateable owner;
	private double offsetMax = 10;
	private double offsetIncrement = 0.05;
	private boolean showNormals;
	
	/**
	 * Create an Animation that will show the animation on the specified curve.
	 * The curve should be non-null.  The NormalBundleDecoration is added to the
	 * curve itself.  
	 * <p>A default offsetMax of 10 is used, meaning that parallel curves are drawn with 
	 * offsets ranging from 0 up to 10.  A default offsetIncrement of 0.05 is used, meaning
	 * that the increment of the offset from one parallel curve to the next is 0.05.
	 */
	public ParallelCurveAnimation(PlaneCurveParametric curve) {
		this(curve,curve);
	}
	
	/**
	 * Create an animation that will show the animation on the specified curve.
	 * The curve should be non-null.  The NormalBundleDecoration is added to the
	 * specified Decorateable owner, which should be either the curve itself or
	 * a View containing the curve.  
	 * <p>A default offsetMax of 10 is used, meaning that parallel curves are drawn with 
	 * offsets ranging from 0 up to 10.  A default offsetIncrement of 0.05 is used, meaning
	 * that the increment of the offset from one parallel curve to the next is 0.05.
	 * @param owner The Decorateable object to which the NormalBundleDecoration is to be added.
	 * If the value is null, then the curve will be used as the owner.  If the value
	 * is not null, it should be equal to either the curve or to a View that is showing the curve.
	 * @param curve The curve to which parallel curves are to be drawn.
	 */
	public ParallelCurveAnimation(Decorateable owner, PlaneCurveParametric curve) {
		this.owner = (owner != null)? owner : curve;
		this.curve = curve;
	}

	/**
	 * Create an animation that will show the animation on the specified curve.
	 * The curve should be non-null.  The NormalBundleDecoration is added to the
	 * specified Decorateable owner, which should be either the curve itself or
	 * a View containing the curve.  
	 * @param owner The Decorateable object to which the NormalBundleDecoration is to be added.
	 * If the value is null, then the curve will be used as the owner. If the value
	 * is not null, it should be equal to either the curve or to a View that is showing the curve.
	 * @param curve The curve to which parallel curves are to be drawn.
	 * @param showNormals If set to true, then the animation will first add the normal vectors to the curve, before
	 * drawing the parallel curves.
	 * @param offsetMax Parallel curves are drawn with offsets ranging from 0 up to this value.
	 * @param offsetIncrement This is the increment of the offset between one parallel curve and the next.
	 */
	public ParallelCurveAnimation(Decorateable owner, PlaneCurveParametric curve, boolean showNormals, double offsetMax, double offsetIncrement) {
		this.owner = (owner != null)? owner : curve;
		this.curve = curve;
		this.showNormals = showNormals;
	}

	/**
	 * Run the paralleCurveAnimation.
	 */
	protected void runAnimation() {
		NormalBundleDecoration dec;
		dec = new NormalBundleDecoration(curve);
		dec.setLayer(2);
		owner.addDecoration(dec);
		try {
			int points = curve.getTResolution();
			if (showNormals) {
				for (int i = 1; i <= points + 1; i++) {
					dec.setPointCount(i);
					pause(20);
				}
				pause(100);
				dec.setPointCount(0);
			}
			dec.setShowEvolute(true);
			pause(200);
			for (double offset = 0; offset <= offsetMax; offset += offsetIncrement) {
				dec.setParallelCurveOffset(offset);
				pause(50);
			}
		}
		finally {
			owner.removeDecoration(dec);
		}
	}

}
