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

public class Inverse extends ConformalMap {
	
	private ComplexParamAnimateable preTranslate = new ComplexParamAnimateable("vmm.conformalmap.Inverse.preTranslate", 
			"0.0", "0.0", "1.0");
	
	public Inverse() {
		addParameter(preTranslate);
		umin.reset(-1.0);
		umax.reset(1.0);
		vmin.reset(-1.0);
		vmax.reset(1.0);
		vres.setValueAndDefault(18);
		ures.setValueAndDefault(18);
		setDefaultWindow2D(-4,4,-3,3);
	}
				
	protected Complex function(Complex argument) {
		Complex z = new Complex();
		Complex t = preTranslate.getValue();
		z.re = argument.re - t.re;
		z.im = argument.im - t.im;
		return z.inverse();
	}

}
