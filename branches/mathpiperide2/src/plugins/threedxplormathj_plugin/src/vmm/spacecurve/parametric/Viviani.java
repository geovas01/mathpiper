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

import vmm.core.RealParamAnimateable;
import vmm.core.Transform;
import vmm.core.View;
import vmm.core3D.DotCloudSurface;
import vmm.core3D.Vector3D;


/**
 * Defines the Viviani curve parametrically, 
 * the intersection of a sphere and a cylinder that touches the sphere.
 */
public class Viviani extends SphericalCurve {
	
	private RealParamAnimateable CylinderRadius;
	
	protected DotCloudCylinder cloudCylinder;
	
	public Viviani() {
		tResolution.setValueAndDefault(150);
		tmin.setValueAndDefaultFromString("-2 * pi");
		tmax.setValueAndDefaultFromString("2 * pi");
		CylinderRadius = new RealParamAnimateable("vmm.spacecurve.parametric.Viviani.CylinderRadius",0.5,0.1,0.9);
		addParameter(CylinderRadius);
		setDefaultWindow(-1.2,1.2,-1.2,1.2);
		setDefaultViewpoint(new Vector3D(2,20,15));
		tubeSize.setValueAndDefault(0.2);
		cloudCylinder=new DotCloudCylinder();
		cloudCylinder.setColor(Color.red);
		addDecoration(cloudCylinder);
	}
	
	public View getDefaultView() {
		SpaceCurveParametricView view = (SpaceCurveParametricView)super.getDefaultView();
		view.setUseReverseCollar(true);
		return view;
	}

	protected Vector3D value(double t) {
		double r = CylinderRadius.getValue();
		double x = 2*Math.sqrt(r*(1-r))* Math.sin(t/2);
		double y = r * Math.sin(t);
		double z = 1 + r * (Math.cos(t)-1);
		return new Vector3D(x,y,z);
	}
	
	protected Vector3D deriv1(double t) {
		double r = CylinderRadius.getValue();
		double x = Math.sqrt(r*(1-r)) * Math.cos(t/2);
		double y = r * Math.cos(t);
		double z = r * (-Math.sin(t));
		return new Vector3D(x,y,z);
	}

	protected Vector3D deriv2(double t) {
		double r = CylinderRadius.getValue();
		double x = -Math.sqrt(r*(1-r)) * Math.sin(t/2)/2;
		double y = -r * Math.sin(t);
		double z = -r * Math.cos(t);
		return new Vector3D(x,y,z);
	}

	protected class DotCloudCylinder extends DotCloudSurface {
		private int j=0;  // used in putRandomPixel to project every tenth random pixel onto bounding circles
		protected Vector3D makeRandomPixel(Random randomNumberGenerator) {
			double u = randomNumberGenerator.nextDouble() * Math.PI * 2;
			double VV = randomNumberGenerator.nextDouble();
			j = j+1;
		    if ((j%10) == 0)
		    	VV = Math.round(VV); // round to 0 or 1
			double x = VV * 3 - 1.5;
			double r = CylinderRadius.getValue();
			double z = 1 + r * (Math.cos(u)-1);
			double y = r * Math.sin(u);
			return new Vector3D(x,y,z);
		}
		public void computeDrawData(View view, boolean exhibitNeedsRedraw, Transform previousTransform, Transform newTransform) {
			j = 0;
			super.computeDrawData(view, exhibitNeedsRedraw, previousTransform,
							newTransform);
		}
   }

}