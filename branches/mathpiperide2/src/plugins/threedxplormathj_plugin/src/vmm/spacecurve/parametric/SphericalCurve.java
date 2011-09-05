/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import java.awt.Color;
import java.util.Random;

import vmm.core.View;
import vmm.core3D.DotCloudSurface;
import vmm.core3D.Vector3D;

/**
 * A curve that lies on the surface of a sphere.  The
 * sphere is shown as a random dot cloud.  The sphere is of
 * radius 1.  Note that in any subclass, the value() method
 * <b>must</b> return a vector of length one (unless the
 * return value is null); this is not checked or enforced
 * anywhere in this class.
 */
abstract public class SphericalCurve extends SpaceCurveParametric {
	
	/**
	 * This variable refers to the dot-cloud sphere decoration that
	 * is installed in this exhibit by the constructor.
	 */
	protected DotCloudSphere cloudSphere;
	
	/**
	 * Constructs a SphericalCurve with a blue dot-cloud sphere
	 * and a default window of (-1.2,1.2,-1.2,1.2) that should be
	 * OK for any subclass.  It also sets a default viewpoint
	 * of (16,9,19) which might or might need to be changed in
	 * individual subclasses.
	 */
	public SphericalCurve() {
		setDefaultWindow(-1.2,1.2,-1.2,1.2);
		setDefaultViewpoint(new Vector3D(16,8,19));
		cloudSphere = new DotCloudSphere();
		cloudSphere.setColor(Color.blue);
		addDecoration(cloudSphere);
	}
	
	/**
	 * Defines the dot cloud sphere of radius one on which the
	 * curve lies.
	 */
	protected class DotCloudSphere extends DotCloudSurface {
		protected Vector3D makeRandomPixel(Random randomNumberGenerator) {
			double u = randomNumberGenerator.nextDouble() * Math.PI * 2;
			double z = randomNumberGenerator.nextDouble() * 2 - 1;
			double r = Math.sqrt(1-z*z);
			double x = r * Math.cos(u);
			double y = r * Math.sin(u);
			return new Vector3D(x,y,z);
		}
	}
	
	/**
	 * Returns a View of type {@link SphericalCurveView}, which is a subclass of
	 * {@link SpaceCurveParametric.SpaceCurveParametricView} that defines some additional Actions.
	 */
	public View getDefaultView() {
		View view = new SphericalCurveView();
		view.setName("vmm.spacecurve.parametric.SpaceCurveParametric.view.ViewAsCurve");
		return view;
	}

	/**
	 * The default view for a SphericalCurve.
	 */
	public class SphericalCurveView extends SpaceCurveParametricView {
		// Many "decorations" and actions should be defined here.
	}

}