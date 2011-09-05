/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.conformalmap;

import vmm.core.Complex;
import vmm.core.RealParamAnimateable;

/**
 * Represents the conformal map z --&gt; z^c, for a real constant c.  The value of c is a parameter
 * of the exhibit (named "exponent"), with default value 2, so that with the default value the exhibit
 * represents the squaring function.
 */
public class Squaring extends ConformalMap {
	
	private RealParamAnimateable exponent = new RealParamAnimateable("vmm.conformalmap.Squaring.exponent", 2, 1, 2);
	
	public Squaring() {
		addParameter(exponent);
		umin.reset(0.05);
		umax.reset(1.25);
		vmin.reset("- pi / 2");
		vmax.reset("pi / 2");
		vres.setValueAndDefault(18);
		ures.setValueAndDefault(6);
		setDefaultWindow2D(-4,4,-3,3);
	}
				
	protected Complex function(Complex argument) {
		return argument.power(exponent.getValue());
	}

	
}
