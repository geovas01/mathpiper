/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.pseudospherical;

//import java.io.IOException;
//import java.math.*;
//import java.util.ArrayList;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import vmm.core.Display;
//import vmm.core.I18n;
//import vmm.core.Parameter;
//import vmm.core.RealParamAnimateable;
//import vmm.core.SaveAndRestore;
//import vmm.core.VariableParam;
//import vmm.core3D.ComplexVector3D;
//import vmm.core3D.UserExhibit3D;
//import vmm.functions.*;

import vmm.surface.parametric.*;
import vmm.core.Complex;
import vmm.core.RealParamAnimateable;
import vmm.core3D.Vector3D;
import vmm.core.VariableParamAnimateable;

public class Solitons extends SurfaceParametric  {
	Complex lambda;
	double xVar, tVar; //, sVar;
	private RealParamAnimateable s = new RealParamAnimateable("genericParam.s", 0.85, 0.2, 1.0 );
	private VariableParamAnimateable solitonNum = new VariableParamAnimateable("genericParam.solitonNum",1,1,4);
	private double epsilon = 0.00001;
	private ComplexVector2D initVec = new ComplexVector2D(new Complex(1,0), new Complex(1,0));
	public ComplexMatrix2D E0, g1,E1, proj, id, g1Perp;
	
	public Solitons()  {
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
		
		//set up E0, projection matrix and identity matrix
		E0 = new ComplexMatrix2D();
		getE0(xVar, s.getValue(), lambda);
		proj = ComplexMatrix2D.getProj(initVec);
		id = new ComplexMatrix2D();
		id.setMatrixEntry(Complex.ONE_C, 1, 1);
		id.setMatrixEntry(Complex.ZERO_C, 1, 2);
		id.setMatrixEntry(Complex.ZERO_C, 2, 1);
		id.setMatrixEntry(Complex.ONE_C, 2, 2);
		
		//get matrix g1
		g1 = new ComplexMatrix2D();
		getg1(lambda);
	}
	
//	get the projection onto vector v
	public ComplexMatrix2D getProj(ComplexVector2D v) {	
		return ComplexMatrix2D.getProj(v);
	}
	
	public void getg1( Complex lbda)  {
		ComplexMatrix2D projPerp = id.subtract(proj);		//get the perp of projection
		Complex i = new Complex(0,1);
		i = i.times(2*s.getValue()).dividedBy(lbda.minus(i.times(s.getValue())));  	//2is/(lbda-is)
		g1.entries[1][1] = new Complex(i.times(projPerp.entries[1][1]).plus(1));
		g1.entries[1][2] = new Complex(i.times(projPerp.entries[1][2]));
		g1.entries[2][1] = new Complex(i.times(projPerp.entries[2][1]));
		g1.entries[2][2] = new Complex(i.times(projPerp.entries[2][2]).plus(1));
	}
	
	public void getE0(double x, double t, Complex lmbda)  {
		Complex i = new Complex(0,1);
		Complex i1 = new Complex(i.times(lmbda).times(x/2+t/2)); 			//i*lmbda*(x/2+t/2)
		Complex i2 = new Complex(i.times(x/2-t/2).dividedBy(lmbda)); 		//i/(lmbda)*(x/2-t/2)
		E0.entries[1][1] = (i2.minus(i1)).exponential();              //  exp( (i/lmbda) ((x-t)/2) - i * lmbda ( (x+t)/2 )
		E0.entries[1][2] = new Complex(0,0);
		E0.entries[2][1] = new Complex(0,0);
		E0.entries[2][2] = (i1.minus(i2)).exponential();              //  exp( -(i/lmbda) ((x-t)/2) + i * lmbda ( (x+t)/2 )
	}
	
	//get g1 tilde
	public ComplexMatrix2D getgPerp(ComplexMatrix2D E, double sVal, Complex lbda, ComplexVector2D v)  {	
		//get pi tilde perp
		
		ComplexMatrix2D ret = new ComplexMatrix2D(E);
		ret = ret.conjugate().transpose();
		
		ret = ComplexMatrix2D.getProj(ret.vectMul(v));		//get the projection matrix onto vector ret.vectMul(v)
		ret = id.subtract(ret);
		
		
		//get g perp
		ComplexMatrix2D mat = new ComplexMatrix2D();
		Complex i = new Complex(0,1);
		i = new Complex(i.times(2*sVal).dividedBy(lbda.minus(i.times(sVal)))); 	//2is/(lbda-is)
		mat.entries[1][1] = i.times(new Complex(ret.entries[1][1])).plus(1);
		mat.entries[1][2] = i.times(new Complex(ret.entries[1][2]));
		mat.entries[2][1] = i.times(new Complex(ret.entries[2][1]));
		mat.entries[2][2] = (i.times(new Complex(ret.entries[2][2]))).plus(1);
		return mat;
	}
	
	synchronized public Vector3D surfacePoint(double x, double t) {		
		xVar = x;
		tVar = t;
		
		
		//evaluate E0*(-is) = E0^(-1)(is)
		getE0(x,t, new Complex(0,-s.getValue()));  // lambda = -is
		ComplexMatrix2D temp = new ComplexMatrix2D(E0);		//do this to avoid temp changing as E0 changes
		
		//compute projection onto vector E0^(-1)(-is)*initVec
		lambda = new Complex(1+epsilon,0);	//lambda = 1+epsilon
		ComplexMatrix2D mat = new ComplexMatrix2D(getgPerp(temp, s.getValue(), lambda, initVec));
		
		getg1(lambda);
		getE0(x,t,lambda);
		
		ComplexMatrix2D ret = new ComplexMatrix2D((g1).multiply(E0.multiply(mat.conjugate().transpose())));
		
		
		lambda = new Complex(1-epsilon,0);		//lambda = 1-epsilon
		
		mat = new ComplexMatrix2D(getgPerp(temp, s.getValue(),  lambda, initVec));
		
		getg1( lambda);
		getE0(x,t,lambda);
		
		ComplexMatrix2D ret1 = new ComplexMatrix2D((g1).multiply(E0.multiply(mat.conjugate().transpose())));
		
		
		ret = new ComplexMatrix2D(ret.subtract(ret1));
		ret = new ComplexMatrix2D(ret.scalarMul(new Complex(1/(2*epsilon),0)));
		
		
		lambda = (new Complex(1,0));
		mat = new ComplexMatrix2D(getgPerp(temp,s.getValue(), lambda, initVec));
		
		getg1( lambda);
		getE0(x,t, lambda);


		ret = ret.multiply(mat.multiply((E0.inverse()).multiply(g1.inverse())));

		
		double aa = ret.entries[1][1].im;
		double bb = ret.entries[1][2].re;
		double cc = ret.entries[1][2].im;
		
		if (Double.isNaN(aa) || Double.isNaN(aa) || Double.isNaN(aa))
			aa = bb = cc= 0;
		return new Vector3D(aa,bb,cc);
	}
}