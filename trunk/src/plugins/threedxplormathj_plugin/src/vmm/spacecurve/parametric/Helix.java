/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;

/**
 * Defines an elliptical helix with axis along the y-axis.
 */
public class Helix extends SpaceCurveParametric {
	
	private RealParamAnimateable radiusX;
	private RealParamAnimateable radiusZ;
	private RealParamAnimateable risePerTurn;
	
	public Helix() {
		tResolution.setValueAndDefault(150);
		tmin.setValueAndDefaultFromString("-3 * pi");
		tmax.setValueAndDefaultFromString("3 * pi");
		radiusX = new RealParamAnimateable("vmm.spacecurve.parametric.Helix.radiusX",1.5,1.5,1.5);
		radiusZ = new RealParamAnimateable("vmm.spacecurve.parametric.Helix.radiusZ",1.5,1.5,1.5);
		risePerTurn = new RealParamAnimateable("vmm.spacecurve.parametric.Helix.risePerTurn",2.5,1,3);
		addParameter(risePerTurn);
		addParameter(radiusZ);
		addParameter(radiusX);
		setDefaultWindow(-5,5,-3,3);
		setDefaultViewpoint(new Vector3D(20,5,2));
		tubeSize.setValueAndDefault(0.5);
	}

	protected Vector3D value(double t) {
		double x = radiusX.getValue() * Math.cos(t);
		double y = t * risePerTurn.getValue() / (2*Math.PI);
		double z = radiusZ.getValue() * Math.sin(t);
		return new Vector3D(x,y,z);
	}

	protected Vector3D deriv1(double t) {
		double x = -radiusX.getValue() * Math.sin(t);
		double y = risePerTurn.getValue() / (2*Math.PI);
		double z = radiusZ.getValue() * Math.cos(t);
		return new Vector3D(x,y,z);
	}

	protected Vector3D deriv2(double t) {
		double x = -radiusX.getValue() * Math.cos(t);
		double y = 0;
		double z = -radiusZ.getValue() * Math.sin(t);
		return new Vector3D(x,y,z);
	}

}
