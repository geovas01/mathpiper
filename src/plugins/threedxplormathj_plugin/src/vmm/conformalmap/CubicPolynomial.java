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
import vmm.core.IntegerParam;

public class CubicPolynomial extends ConformalMap {

	private ComplexParamAnimateable coeffOfzexp = new ComplexParamAnimateable("vmm.conformalmap.CubicPolynomial.coeffOfzexp",
			new Complex(1,0), new Complex(0,0), new Complex(1,0));
	private IntegerParam exponent = new IntegerParam("vmm.conformalmap.CubicPolynomial.exponent",2);
	
	public CubicPolynomial() {
	    setGridType(POLARCONFORMAL);
		umin.reset(-1.0);
		umax.reset(1.0);
		vmin.reset(0.0);
		vmax.reset("2*pi");
		ures.setValueAndDefault(10);
		vres.setValueAndDefault(31);
		setDefaultWindow2D(-16,16,-12,12);
		addParameter(coeffOfzexp);
		addParameter(exponent);
	}

	protected Complex function(Complex argument) {
		Complex w = new Complex(argument.power(exponent.getValue()).times(coeffOfzexp.getValue()));
		return w.plus( argument.times(exponent.getValue()));
	}

}
