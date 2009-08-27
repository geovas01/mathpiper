/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core.Complex;
import vmm.core.Parameter;
import vmm.core3D.ComplexVector3D;
import vmm.core3D.Vector3D;

public class Helicoid_Weierstrass extends WeierstrassMinimalSurface {
	
	
	
	public Helicoid_Weierstrass() {
		super();
		uPatchCount.setValueAndDefault(8);
		vPatchCount.setValueAndDefault(14);
		umin.reset(-1.6); 
		umax.reset(1.6);
		vmin.reset(- Math.PI);
		vmax.reset(Math.PI);
		setDefaultViewpoint(new Vector3D(10.0,-10.0,10.0));
		setDefaultWindow(-8,8,-8,8);
		canShowConjugateSurface = true;
	}
	
	/**
	 * The following two functions are the Weierstrass Data that
	 * define this surface.
	 */
	protected Complex gauss(Complex z)  {
		return z.exponential();
	}
	protected Complex hPrime(Complex z) {
		return new Complex(0,1);
	}
}
