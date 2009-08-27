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


public class Breather extends nSolitons
{
	public ComplexParamAnimateable alpha = new ComplexParamAnimateable("alpha", new Complex(0.6, 0.8), new Complex(1,0), new Complex(0,1)); 
	public ComplexVector2D vect = new ComplexVector2D(new Complex(1,0), new Complex(2,1));
	private ComplexMatrix2D g1_alpha, g2_alpha;
	
	public Breather()  
	{
		s.setName("s");
		addParameter(alpha);
		addParameter(s);
		umin.reset(-4.5);
		umax.reset(3.5);
		vmin.reset("-3*pi");
		vmax.reset("3*pi");
		setDefaultWindow(-1.47301,3.02699,-1.40071,2.09929);
		setDefaultViewpoint(new Vector3D(-3.78115,-6.81146,1.90246));
		setDefaultViewUp(new Vector3D(-0.855955,0.505503,0.108658));
	
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
		
		//initialize g1, g1_alpha, g2_alpha
		g1 = new ComplexMatrix2D();
		g1_alpha = getG1_alpha(lambda, alpha.getValue(), vect);
		g2_alpha = getG2_alpha(lambda, alpha.getValue(), vect);
	}
	
	//g = I + (alpha - alpha.conj())/(lbda-alpha)*(I-proj(vect))
	public ComplexMatrix2D getG1_alpha(Complex lbda, Complex alpha, ComplexVector2D vect)
	{
		ComplexMatrix2D projection = getProj(vect);
		ComplexMatrix2D projPerp = id.subtract(projection);
		
		Complex i = alpha.minus(alpha.conj());
		i = i.dividedBy(lbda.minus(alpha));
		ComplexMatrix2D g = new ComplexMatrix2D();
		g.entries[1][1] = new Complex(i.times(projPerp.entries[1][1]).plus(1));
		g.entries[1][2] = new Complex(i.times(projPerp.entries[1][2]));
		g.entries[2][1] = new Complex(i.times(projPerp.entries[2][1]));
		g.entries[2][2] = new Complex(i.times(projPerp.entries[2][2]).plus(1));
		return g;
	}
	
	
	public ComplexMatrix2D getG2_alpha(Complex lbda, Complex alpha, ComplexVector2D vect)
	{
		
		ComplexMatrix2D temp = getG1_alpha(Complex.ZERO_C.minus(alpha.conj()), alpha, vect);
		ComplexVector2D vectConj = new ComplexVector2D(vect.x.conj(),vect.y.conj());
		ComplexVector2D w = temp.vectMul(vectConj);
		return getG1_alpha(lbda, Complex.ZERO_C.minus(alpha.conj()), w);
	}
	
	//added is true when the breather is added to a 1-soliton
	//otherwise, the breather is added to the vacuous frame
	public ComplexMatrix2D compBreather(double x, double t, boolean added)
	{
		lambda = new Complex(1+epsilon, 0);
		ComplexMatrix2D h1 = getH_alpha(lambda, alpha.getValue(), vect);
		lambda = new Complex(1-epsilon, 0);
		ComplexMatrix2D h2 = getH_alpha(lambda, alpha.getValue(), vect);
		lambda = new Complex(1, 0);
		ComplexMatrix2D h3 = getH_alpha(lambda, alpha.getValue(), vect);
		ComplexMatrix2D ret = new ComplexMatrix2D();
		//ComplexVector2D vectConj = new ComplexVector2D(vect.x.conj(),vect.y.conj());
		ComplexVector2D vectTilde;
		ComplexMatrix2D g11, g22;
		
		if(added == false)
		{
			getE0(x,t, alpha.getValue().conj());
			ComplexMatrix2D temp = new ComplexMatrix2D(E0.conjugate().transpose());
			vectTilde = temp.vectMul(vect);
			
			
			lambda = new Complex(1+epsilon, 0);
			g11 = new ComplexMatrix2D(getG1_alpha(lambda,alpha.getValue(),vectTilde));
			g22 = new ComplexMatrix2D(getG2_alpha(lambda, alpha.getValue(), vectTilde));
			ComplexMatrix2D h1_tilde = new ComplexMatrix2D(g22.multiply(g11));
			ret = h1.multiply(E[0][0]).multiply(h1_tilde.conjugate().transpose());
			
			lambda = new Complex(1-epsilon, 0);
			g11 = new ComplexMatrix2D(getG1_alpha(lambda,alpha.getValue(),vectTilde));
			g22 = new ComplexMatrix2D(getG2_alpha(lambda, alpha.getValue(), vectTilde));
			h1_tilde = new ComplexMatrix2D(g22.multiply(g11));
			
			ret = ret.subtract(h2.multiply(E[0][1]).multiply(h1_tilde.conjugate().transpose()));
			ret = ret.scalarMul(new Complex(1/(2*epsilon),0));
			
			lambda = new Complex(1,0);
			g11 = new ComplexMatrix2D(getG1_alpha(lambda,alpha.getValue(),vectTilde));
			g22 = new ComplexMatrix2D(getG2_alpha(lambda, alpha.getValue(), vectTilde));
			h1_tilde = new ComplexMatrix2D(g22.multiply(g11));
			ret = ret.multiply(h1_tilde.multiply(E[0][2].conjugate().transpose()).multiply(h3.conjugate().transpose()));
		}
		else
		{
			getE0(x,t, alpha.getValue().conj());
			getg1(alpha.getValue().conj());
			ComplexMatrix2D temp = getg1Perp(alpha.getValue().conj());
			temp = g1.multiply(E0).multiply(temp.inverse());
			
			temp = temp.conjugate().transpose();
			vectTilde = temp.vectMul(vect);
			
			lambda = new Complex(1+epsilon, 0);
			g11 = new ComplexMatrix2D(getG1_alpha(lambda,alpha.getValue(),vectTilde));
			g22 = new ComplexMatrix2D(getG2_alpha(lambda, alpha.getValue(), vectTilde));
			ComplexMatrix2D h1_tilde = g22.multiply(g11);
			ret = h1.multiply(E[1][0]).multiply(h1_tilde.conjugate().transpose());
			
			lambda = new Complex(1-epsilon, 0);
			g11 = new ComplexMatrix2D(getG1_alpha(lambda,alpha.getValue(),vectTilde));
			g22 = new ComplexMatrix2D(getG2_alpha(lambda, alpha.getValue(), vectTilde));
			h1_tilde = g22.multiply(g11);
			
			ret = ret.subtract(h2.multiply(E[1][1]).multiply(h1_tilde.conjugate().transpose()));
			ret = ret.scalarMul(new Complex(1/(2*epsilon),0));
			
			lambda = new Complex(1,0);
			g11 = new ComplexMatrix2D(getG1_alpha(lambda,alpha.getValue(),vectTilde));
			g22 = new ComplexMatrix2D(getG2_alpha(lambda, alpha.getValue(), vectTilde));
			h1_tilde = g22.multiply(g11);
			ret = ret.multiply(h1_tilde.multiply(E[1][2].conjugate().transpose()).multiply(h3.conjugate().transpose()));
		}
		
		return ret;
	}
	
	public ComplexMatrix2D getH_alpha(Complex lbda, Complex alpha, ComplexVector2D vect)
	{
		g1_alpha = getG1_alpha(lbda, alpha, vect);
		g2_alpha = getG2_alpha(lbda, alpha, vect);
		return g2_alpha.multiply(g1_alpha);
	}
	
	
	
	synchronized public Vector3D surfacePoint(double x, double t)
	{
		ComplexMatrix2D ret = firstSoliton(x,t);
		ret = compBreather(x,t,false);
		
		double aa =  ret.entries[1][1].im;
		double bb = ret.entries[1][2].re;
		double cc = ret.entries[1][2].im;
		
		if (Double.isNaN(aa) || Double.isNaN(aa) || Double.isNaN(aa))
			aa = bb = cc= 0;
		return new Vector3D(aa,bb,cc);
	}
	
}