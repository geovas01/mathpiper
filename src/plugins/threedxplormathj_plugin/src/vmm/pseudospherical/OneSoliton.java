/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.pseudospherical;

import vmm.core.Complex;
import vmm.core3D.Vector3D;

public class OneSoliton extends nSolitons
{
	public OneSoliton()  
	{
		addParameter(s);
		umin.reset(-3);
		umax.reset(3);
		vmin.reset("-pi/2");
		vmax.reset("pi/2");
		setDefaultWindow(-2.25,2.25,-1.75,1.75);
		setDefaultViewpoint(new Vector3D(5.5,4.25,4.0));
		setDefaultViewUp(new Vector3D(-0.16,-0.55,0.82));
		
		//get s and solitonNum
		lambda = new Complex(0,1);
		
		//set up storage for E at various values
		E = new ComplexMatrix2D[2][6];
		for(int i = 0; i <= 1; i++)
			for(int j = 0; j < 6; j++)
				E[i][j] = new ComplexMatrix2D();
		
		
		//set up storage for g tilde at various values
		g = new ComplexMatrix2D[2][5];
		for(int i = 0; i <= 1; i++)
			for(int j = 0; j < 5; j++)
				g[i][j] = new ComplexMatrix2D();
		
		
		//set up E0, projection matrix and identity matrix
		E0 = new ComplexMatrix2D();
		getE0(xVar, s.getValue(), lambda);
		proj = ComplexMatrix2D.getProj(initVec);
		
		id = new ComplexMatrix2D();
		id.setMatrixEntry(Complex.ONE_C, 1, 1);
		id.setMatrixEntry(Complex.ZERO_C, 1, 2);
		id.setMatrixEntry(Complex.ZERO_C, 2, 1);
		id.setMatrixEntry(Complex.ONE_C, 2, 2);
		
		//initialize g1, g2, g3
		g1 = new ComplexMatrix2D();
		
	}
	
	synchronized public Vector3D surfacePoint(double x, double t)
	{
		ComplexMatrix2D ret = firstSoliton(x,t);
		
		double aa =  ret.entries[1][1].im;
		double bb = ret.entries[1][2].re;
		double cc = ret.entries[1][2].im;
		
		if (Double.isNaN(aa) || Double.isNaN(aa) || Double.isNaN(aa))
			aa = bb = cc= 0;
		return new Vector3D(aa,bb,cc);
	}
	
}