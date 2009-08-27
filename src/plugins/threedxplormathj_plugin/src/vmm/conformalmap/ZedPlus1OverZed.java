/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.conformalmap;

import vmm.core.Complex;

public class ZedPlus1OverZed extends ConformalMap {
	
	public ZedPlus1OverZed() {
		setGridType(POLARCONFORMAL);
		umin.reset(-1.0);
		umax.reset(1.0);
		vmin.reset(0.0);
		vmax.reset(Math.PI);
		ures.reset(10);
		vres.reset(21);
		setDefaultWindow2D(-4,4,-3,3);
	}

	protected Complex function(Complex argument) {
		return argument.plus(argument.inverse());
	}
	
	

}
