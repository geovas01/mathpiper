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

public class Exponentialfct extends ConformalMap {

	private ComplexParamAnimateable factor = new ComplexParamAnimateable("vmm.conformalmap.Exponentialfct.factor",
			new Complex(1,0.1), new Complex(1,0), new Complex(1,0.3));
	
	public Exponentialfct() {
		addParameter(factor);
	    setGridType(CARTESIAN);
		umin.reset(-1.0);
		umax.reset(1.0);
		vmin.reset(-Math.PI);
		vmax.reset(Math.PI);
		ures.reset(10);
		vres.reset(31);
		setDefaultWindow2D(-4,4,-3,3);
	}

	protected Complex function(Complex argument) {
		Complex w = factor.getValue();
		w = argument.times(w);
		return w.exponential();
	}
}

