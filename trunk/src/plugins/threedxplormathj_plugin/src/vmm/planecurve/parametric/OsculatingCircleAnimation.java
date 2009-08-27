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
 * Shows an osculating circle moving along a PlaneCurveParametric.  The
 * animation can, optionally, show the normal bundle; one normal vector is shown
 * for each point on the curve, and the length of the nomral vector is equal to
 * the radius of the osculating circle at that point.  The animation can also, optionally,
 * show the evolute curve being drawn as the osculating circle moves around the curve.
 * A NormalBundleDecoration is used to do the actual work.
 */
public class OsculatingCircleAnimation extends ThreadedAnimation {
	
	private PlaneCurveParametric curve;
	private Decorateable owner;
	private boolean showNormals = true;
	private boolean showEvolute = false;
	
	/**
	 * Create an animation that will show the animation on the specified curve.
	 * The curve should be non-null.  The NormalBundleDecoration is added to the
	 * curve itself.  Normals are shown but the evolute curve is not.
	 */
	public OsculatingCircleAnimation(PlaneCurveParametric curve) {
		this(curve,curve);
	}
	
	/**
	 * Create an animation that will show the animation on the specified curve.
	 * The curve should be non-null.  The NormalBundleDecoration is added to the
	 * specified Decorateable owner, which should be either the curve itself or
	 * a View containing the curve.  Normals are shown but the evolute curve is not.
	 * @param owner The Decorateable object to which the NormalBundleDecoration is to be added.
	 * If the value is null, then the curve will be used as the owner.
	 * @param curve The curve to which parallel curves are to be drawn.
	 */
	public OsculatingCircleAnimation(Decorateable owner, PlaneCurveParametric curve) {
		this.owner = (owner != null)? owner : curve;
		this.curve = curve;
	}
	

	/**
	 * Create an animation that will show the animation on the specified curve, with the option of showing
	 * or not showing the evolute and the normal vectors.
	 * The curve should be non-null.  The NormalBundleDecoration is added to the
	 * specified Decorateable owner, which should be either the curve itself or
	 * a View containing the curve. 
	 * @param owner The Decorateable object to which the NormalBundleDecoration is to be added.
	 * If the value is null, then the curve will be used as the owner.
	 * @param curve The curve to which parallel curves are to be drawn.
	 * @param showNormals If set to true, the normal bundle is constructed and shown before the osculating circles are shown.
	 * @param showEvolute If set to true, the evolute curve is shown being drawn as the osculating circles move.
	 */
	public OsculatingCircleAnimation(Decorateable owner, PlaneCurveParametric curve, boolean showNormals, boolean showEvolute) {
		this.owner = (owner != null)? owner : curve;
		this.curve = curve;
		this.showNormals = showNormals;
		this.showEvolute = showEvolute;
	}
	

	protected void runAnimation() {
		NormalBundleDecoration dec = new NormalBundleDecoration(curve);
		owner.addDecoration(dec);
		try {
			int points = curve.getTResolution() + 1;
			if (showNormals) {
				for (int i = 1; i <= points; i++) {
					dec.setPointCount(i);
					pause(20);
				}
			}
			for (int pass = 0; pass < 10; pass++) {
				for (int i = 0; i <= points; i++) {
					dec.setOsculatingCircleIndex(i,showEvolute);
					pause(50);
				}
				pause(450);
			}
		}
		finally {
			owner.removeDecoration(dec);
		}
	}

}
