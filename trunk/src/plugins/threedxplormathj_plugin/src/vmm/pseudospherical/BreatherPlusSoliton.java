/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.pseudospherical;

import vmm.core.Complex;
import vmm.core.ComplexParamAnimateable;
import vmm.core3D.Vector3D;

public class BreatherPlusSoliton extends Breather
{
	public BreatherPlusSoliton()
	{
		super();
		alpha.setValue(new Complex(0.8,0.6));
		setDefaultWindow(-2.49198,2.00802,-0.240364,3.25964);
		setDefaultViewpoint(new Vector3D(-2.45611,-7.56097,1.05439));
		setDefaultViewUp(new Vector3D(-0.312026,0.229910,0.921836));
	}
	synchronized public Vector3D surfacePoint(double x, double t)
	{
		ComplexMatrix2D ret = firstSoliton(x,t);
		ret = compBreather(x,t,true);
		
		double aa =  ret.entries[1][1].im;
		double bb = ret.entries[1][2].re;
		double cc = ret.entries[1][2].im;
		
		if (Double.isNaN(aa) || Double.isNaN(aa) || Double.isNaN(aa))
			aa = bb = cc= 0;
		return new Vector3D(aa,bb,cc);
	}
}