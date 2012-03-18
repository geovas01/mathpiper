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

public class NonConformal extends ConformalMap {

	private ComplexParamAnimateable coeffOfSquare = new ComplexParamAnimateable("vmm.conformalmap.NonConformal.coeffOfSquare", 
			"0.2", "0.0", "1.0");
	
	public NonConformal() {
		addParameter(coeffOfSquare);
		umin.reset(-1.0);
		umax.reset(1.0);
		vmin.reset(-1.0);
		vmax.reset(0.0);
		ures.setValueAndDefault(18);
		vres.setValueAndDefault(9);
		setDefaultWindow2D(-2,2,-1.5,1.5);
	}

	protected Complex function(Complex argument) {
		Complex w = new Complex(argument.power(2).times(coeffOfSquare.getValue()));
		return w.plus(argument.conj());
	}

}
