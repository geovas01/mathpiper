/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.conformalmap;

import vmm.core.Complex;
import vmm.core.ComplexParamAnimateable;


 public class Sine extends ConformalMap {
	 
	 private ComplexParamAnimateable a = new ComplexParamAnimateable("vmm.conformalmap.Sine.a", "1.0", "0.0", "1.0");
	 private ComplexParamAnimateable b = new ComplexParamAnimateable("vmm.conformalmap.Sine.b", "0.0", "1.0", "0.0");
	 
	 public Sine() {
		addParameter(b);
		addParameter(a);
		umin.reset(-1.5);
		umax.reset(1.5);
		vmin.reset(-1.5);
		vmax.reset(1.5);
		vres.setValueAndDefault(18);
		ures.setValueAndDefault(18);
		setDefaultWindow2D(-4,4,-3,3);
	}
				
	protected Complex function(Complex argument) {
		return ((argument.sine()).times(a.getValue())).plus (argument.times(b.getValue()));  // a * sin(argument) + b * argument
	}
}
