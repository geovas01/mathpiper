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
 * Represents an Escher version of Koch "snowflake" curve. 
 */
public class KochEscher extends RepeatedSegmentFractal {
	
	public KochEscher() {
		//setDefaultWindow(-1.7,1.7,-1.4,1.4); // two tiles
		//setDefaultWindow(-1.4,1.4,-0.4,2.1); // six tiles
		setDefaultWindow(-2.2,1.2,-0.4,2.1);
		fractality.reset(0.25, 0, 1.0/3.0);
		fractality.setMaximumValueForInput(1.0/3.0);
		fractality.setMinimumValueForInput(0.0);
		recursionLevel.reset(9);
		colorRepeatFactor = 20; // 13 for 6 tiles, 5 for two tiles
	}
	
	protected Complex[] computeNextLevel(Complex[] kochCurve, int computedLevel) {
		Complex[] newKochCurve;
		double sqrt3 = Math.sqrt(3);
		if (computedLevel == 0) {
		/*	newKochCurve = new Complex[6];
			newKochCurve[0] = new Complex(0,1);
			newKochCurve[1] = new Complex(0,-1);
			newKochCurve[2] = new Complex(sqrt3, 0);
			newKochCurve[3] = new Complex(0,1);
			newKochCurve[4] = new Complex(-sqrt3, 0);
			newKochCurve[5] = new Complex(0,-1);    // two tiles     */
			//newKochCurve = new Complex[14];
			newKochCurve = new Complex[21];
			newKochCurve[0] = new Complex(+0.5,sqrt3/2);
			newKochCurve[1] = new Complex(0,0);
			newKochCurve[2] = new Complex(1,0);
			newKochCurve[3] = new Complex(+0.5,sqrt3/2);
			newKochCurve[4] = new Complex(0, sqrt3);
			newKochCurve[5] = new Complex(1, sqrt3);
			newKochCurve[6] = new Complex(+0.5,sqrt3/2);
			newKochCurve[7] = new Complex(-0.5,sqrt3/2);
			newKochCurve[8] = new Complex(-1, sqrt3);
			newKochCurve[9] = new Complex(0, sqrt3);
			newKochCurve[10] = new Complex(-0.5,sqrt3/2);
			newKochCurve[11] = new Complex(-1,0);
			newKochCurve[12] = new Complex(0, 0);
			newKochCurve[13] = new Complex(-0.5,sqrt3/2); // 6tiles to here
			newKochCurve[14] = new Complex(-1.5,sqrt3/2);
			newKochCurve[15] = new Complex(-1, sqrt3);
			newKochCurve[16] = new Complex(-2, sqrt3);
			newKochCurve[17] = new Complex(-1.5,sqrt3/2);
			newKochCurve[18] = new Complex(-1,0);
			newKochCurve[19] = new Complex(-2,0);
			newKochCurve[20] = new Complex(-1.5,sqrt3/2);
		}
		else {
			newKochCurve = new Complex[ 3*kochCurve.length - 2];
			double phi = -Math.PI*fractality.getValue();
			Complex dir = new Complex((-0.5+Math.cos(phi))/3, Math.sin(phi)/3 );
			int ct = 0;  // counts number of points in newKochCurve so far
			for (int i = 0; i < kochCurve.length -1 ; i++) {
				Complex p1 = kochCurve[i];
				Complex p2 = kochCurve[i+1];
				Complex midpoint = p1.plus(p2).times(0.5);
				Complex theDirection = p1.minus(p2).times(dir);
				newKochCurve[ct++] = new Complex(p1);
				newKochCurve[ct++] = midpoint.plus(theDirection);
				newKochCurve[ct++] = midpoint.minus(theDirection);
			}
			newKochCurve[ct] = kochCurve[kochCurve.length -1];
		}
		return newKochCurve;
	}


}
