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
 * The fractal "Dragon" curve.
 */
public class Dragon extends RepeatedSegmentFractal {

	public Dragon() {
		fractality.reset(0.5, 0, 0.5);
		fractality.setMaximumValueForInput(0.5);
		fractality.setMinimumValueForInput(0.0);
		recursionLevel.reset(14);
		recursionLevel.setMaximumValueForInput(20);
		recursionLevel.setMinimumValueForInput(1);
		setDefaultWindow(-1,2,-2,1);
		colorRepeatFactor = 2;
		fastDrawRecursionLevel = 10;
	}

	protected Complex[] computeNextLevel(Complex[] dragonCurve, int computedLevel) {
		Complex[] newDragonCurve;
		if (computedLevel == 0) {
			newDragonCurve = new Complex[3];
			newDragonCurve[0] = new Complex(-1.0, 0.0);
			newDragonCurve[1] = new Complex( 0.0,-1.0);
			newDragonCurve[2] = new Complex( 1.0, 0.0);
		}
		else {
			newDragonCurve = new Complex[ 2*dragonCurve.length - 1];
			double aa = Math.min(0.5, Math.max(fractality.getValue(), 0.0));
			Complex aaI = new Complex(0,aa);
			int ct = 0;  // number of points in newDragonCurve so far
			for (int i = 0; i < dragonCurve.length -1 ; i++) {
				Complex p1 = dragonCurve[i];
				Complex p2 = dragonCurve[i+1];
				Complex midpoint = p1.plus(p2).times(0.5);
				Complex theDirection = p2.minus(p1);
				newDragonCurve[ct++] = p1;
				newDragonCurve[ct++] = midpoint.plus(aaI.times(theDirection));
				aaI.im = - aaI.im;
			}
			newDragonCurve[ct] = dragonCurve[dragonCurve.length-1];
		}
		return newDragonCurve;
	}
	

}