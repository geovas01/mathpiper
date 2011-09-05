/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.fractals;

import vmm.core.Complex;

/**
 * Represents a Koch "snowflake" curve.  The standard Koch curve is produced when
 * the "fractality" parameter is 1/3.
 */
public class Koch extends RepeatedSegmentFractal {
		
	protected Complex[] computeNextLevel(Complex[] kochCurve, int computedLevel) {
		Complex[] newKochCurve;
		double sqrt3Over6 = Math.sqrt(3)/6;
		if (computedLevel == 0) {
			newKochCurve = new Complex[4];
			newKochCurve[0] = new Complex(-2*sqrt3Over6, 0);
			newKochCurve[1] = new Complex(sqrt3Over6, 0.5);
			newKochCurve[2] = new Complex(sqrt3Over6, -0.5);
			newKochCurve[3] = newKochCurve[0];
		}
		else {
			newKochCurve = new Complex[ 4*kochCurve.length - 3];
			double aa = Math.min(0.5, Math.max(fractality.getValue(), 0.25));
			double aaVertical = Math.sqrt(aa - 0.25) / aa;
			Complex aaVerticalI = new Complex(0,aaVertical);
			int ct = 0;  // number of points in newKochCurve so far
			for (int i = 0; i < kochCurve.length -1 ; i++) {
				Complex p1 = kochCurve[i];
				Complex p2 = kochCurve[i+1];
				Complex midpoint = p1.plus(p2);
				midpoint.re /= 2;
				midpoint.im /= 2;
				Complex theDirection = p2.minus(p1).times(aa);
				newKochCurve[ct++] = p1;
				newKochCurve[ct++] = p1.plus(theDirection);
				newKochCurve[ct++] = midpoint.plus(aaVerticalI.times(theDirection));
				newKochCurve[ct++] = p2.minus(theDirection);
			}
			newKochCurve[ct] = kochCurve[kochCurve.length -1];
		}
		return newKochCurve;
	}


}
