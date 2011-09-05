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

public class nSolitons extends SurfaceParametric {
	Complex lambda;
	double xVar, tVar; //, sVar;
	public RealParamAnimateable s = new RealParamAnimateable("s1", 	1, 0.2, 1.0 );
	public RealParamAnimateable s1 = new RealParamAnimateable("s2", 1, 0.2, 1.0 );
	public RealParamAnimateable s2 = new RealParamAnimateable("s3", 1, 0.2, 1.0 );
	public int solitonNum = 2;
	public double epsilon = 0.0000001;
	public ComplexVector2D initVec = new ComplexVector2D(new Complex(1,0), new Complex(1,0));
	public ComplexVector2D initVec1 = new ComplexVector2D(new Complex(1,0), new Complex(-5,0));
	public ComplexVector2D initVec2 = new ComplexVector2D(new Complex(1,0), new Complex(1,0));
	public ComplexMatrix2D E0, g1,g2, g3, E1, proj, proj1, proj2, projPerp, projPerp1, projPerp2, id, g1Perp;
	public ComplexMatrix2D E[][];
	public ComplexMatrix2D g[][];
	
	public nSolitons()  {
		
		//get s and solitonNum
		lambda = new Complex(0,1);
		
		
		
		//set up storage for E at various values
		E = new ComplexMatrix2D[4][6];
		for(int i = 0; i <= 3; i++)
			for(int j = 0; j < 6; j++)
				E[i][j] = new ComplexMatrix2D();
		
		
		//set up storage for g tilde at various values
		g = new ComplexMatrix2D[4][5];
		for(int i = 0; i <= 3; i++)
			for(int j = 0; j < 5; j++)
				g[i][j] = new ComplexMatrix2D();
		
		
		//set up E0, projection matrix and identity matrix
		E0 = new ComplexMatrix2D();
		getE0(xVar, s.getValue(), lambda);
		proj = ComplexMatrix2D.getProj(initVec);
		proj1 = ComplexMatrix2D.getProj(initVec1);
		proj2 = ComplexMatrix2D.getProj(initVec2);
		
		id = new ComplexMatrix2D();
		id.setMatrixEntry(Complex.ONE_C, 1, 1);
		id.setMatrixEntry(Complex.ZERO_C, 1, 2);
		id.setMatrixEntry(Complex.ZERO_C, 2, 1);
		id.setMatrixEntry(Complex.ONE_C, 2, 2);
		
		//initialize g1, g2, g3
		g1 = new ComplexMatrix2D();
		g2 = new ComplexMatrix2D();
		g3 = new ComplexMatrix2D();
		
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
	
	public void getg2( Complex lbda)  {
		ComplexMatrix2D projPerp = id.subtract(proj1);		//get the perp of projection
		Complex i = new Complex(0,1);
		i = i.times(2*s1.getValue()).dividedBy(lbda.minus(i.times(s1.getValue())));  	//2is/(lbda-is)
		g2.entries[1][1] = new Complex(i.times(projPerp.entries[1][1]).plus(1));
		g2.entries[1][2] = new Complex(i.times(projPerp.entries[1][2]));
		g2.entries[2][1] = new Complex(i.times(projPerp.entries[2][1]));
		g2.entries[2][2] = new Complex(i.times(projPerp.entries[2][2]).plus(1));
	}
	
	public void getg3( Complex lbda)  {
		ComplexMatrix2D projPerp = id.subtract(proj2);		//get the perp of projection
		Complex i = new Complex(0,1);
		i = i.times(2*s2.getValue()).dividedBy(lbda.minus(i.times(s2.getValue())));  	//2is/(lbda-is)
		g3.entries[1][1] = new Complex(i.times(projPerp.entries[1][1]).plus(1));
		g3.entries[1][2] = new Complex(i.times(projPerp.entries[1][2]));
		g3.entries[2][1] = new Complex(i.times(projPerp.entries[2][1]));
		g3.entries[2][2] = new Complex(i.times(projPerp.entries[2][2]).plus(1));
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
	
	public ComplexMatrix2D getg1Perp( Complex lbda)  {
		ComplexMatrix2D g1Perp = new ComplexMatrix2D();
		ComplexMatrix2D prjPerp = id.subtract(projPerp);		//get the perp of projection
		Complex i = new Complex(0,1);
		i = i.times(2*s.getValue()).dividedBy(lbda.minus(i.times(s.getValue())));  	//2is/(lbda-is)
		g1Perp.entries[1][1] = new Complex(i.times(prjPerp.entries[1][1]).plus(1));
		g1Perp.entries[1][2] = new Complex(i.times(prjPerp.entries[1][2]));
		g1Perp.entries[2][1] = new Complex(i.times(prjPerp.entries[2][1]));
		g1Perp.entries[2][2] = new Complex(i.times(prjPerp.entries[2][2]).plus(1));
		return g1Perp;
	}
	
	public ComplexMatrix2D getg2Perp( Complex lbda)  {
		ComplexMatrix2D g2Perp = new ComplexMatrix2D();
		ComplexMatrix2D prjPerp = id.subtract(projPerp1);		//get the perp of projection
		Complex i = new Complex(0,1);
		i = i.times(2*s1.getValue()).dividedBy(lbda.minus(i.times(s1.getValue())));  	//2is/(lbda-is)
		g2Perp.entries[1][1] = new Complex(i.times(prjPerp.entries[1][1]).plus(1));
		g2Perp.entries[1][2] = new Complex(i.times(prjPerp.entries[1][2]));
		g2Perp.entries[2][1] = new Complex(i.times(prjPerp.entries[2][1]));
		g2Perp.entries[2][2] = new Complex(i.times(prjPerp.entries[2][2]).plus(1));
		return g2Perp;
	}
	
	public ComplexMatrix2D getg3Perp( Complex lbda)  {
		ComplexMatrix2D g3Perp = new ComplexMatrix2D();
		ComplexMatrix2D prjPerp = id.subtract(projPerp2);		//get the perp of projection
		Complex i = new Complex(0,1);
		i = i.times(2*s2.getValue()).dividedBy(lbda.minus(i.times(s2.getValue())));  	//2is/(lbda-is)
		g3Perp.entries[1][1] = new Complex(i.times(prjPerp.entries[1][1]).plus(1));
		g3Perp.entries[1][2] = new Complex(i.times(prjPerp.entries[1][2]));
		g3Perp.entries[2][1] = new Complex(i.times(prjPerp.entries[2][1]));
		g3Perp.entries[2][2] = new Complex(i.times(prjPerp.entries[2][2]).plus(1));
		return g3Perp;
	}
	
	public ComplexMatrix2D getgTilde(ComplexMatrix2D g, Complex lambda, double sVal)
	{
		ComplexMatrix2D perp;
		Complex val = new Complex(0,sVal);
		perp = g.subtract(id).scalarMul((lambda.minus(val)).dividedBy(new Complex(0,2*sVal)));
		perp = id.subtract(perp);
		return perp;
	}
	
	//get g1 tilde
	public ComplexMatrix2D getgPerp(ComplexMatrix2D E, double sVal, Complex lbda, ComplexVector2D v)  {	
		//get pi tilde perp
		
		ComplexMatrix2D ret = new ComplexMatrix2D(E);
		ret = ret.conjugate().transpose();
		
		ret = ComplexMatrix2D.getProj(ret.vectMul(v));		//get the projection matrix onto vector ret.vectMul(v)
		
		ret = id.subtract(ret);
		
		
		//get g1 perp
		ComplexMatrix2D mat = new ComplexMatrix2D();
		Complex i = new Complex(0,1);
		i = new Complex(i.times(2*sVal).dividedBy(lbda.minus(i.times(sVal)))); 	//2is/(lbda-is)
		mat.entries[1][1] = i.times(new Complex(ret.entries[1][1])).plus(1);
		mat.entries[1][2] = i.times(new Complex(ret.entries[1][2]));
		mat.entries[2][1] = i.times(new Complex(ret.entries[2][1]));
		mat.entries[2][2] = (i.times(new Complex(ret.entries[2][2]))).plus(1);
		return mat;
	}
	
	//given E_n(-is) and prPerp, get E_(n+1)(-is)
	//this is the double pole formula
	public ComplexMatrix2D getNextE(ComplexMatrix2D En, ComplexMatrix2D En1, ComplexMatrix2D En2, ComplexMatrix2D proj, ComplexMatrix2D prPerp, double sVal)
	{
		ComplexMatrix2D nextE;
		
		//proj*En(-is)
		nextE = new ComplexMatrix2D(proj.multiply(En));
		
		//(id-proj)*En(-is)*(id-prPerp)
		ComplexMatrix2D temp = new ComplexMatrix2D(id.subtract(proj));
		temp = temp.multiply(En1).multiply(id.subtract(prPerp));
		nextE = new ComplexMatrix2D(nextE.add(temp));
		
		//-2is*(proj)*[En(-is+eps)-En(-is-eps)]*(id-prPerp)/(2*eps)
		temp = new ComplexMatrix2D(proj);

		temp = temp.scalarMul(new Complex(0,-2*sVal));
		temp = temp.multiply((En1.subtract(En2)).multiply(id.subtract(prPerp)).scalarMul(new Complex(1/(2*epsilon),0)));
		nextE = nextE.add(temp);
		
		return nextE;
	}
	
	//triple pole formula
	public ComplexMatrix2D getNextE1(ComplexMatrix2D En, ComplexMatrix2D En1, ComplexMatrix2D En2, ComplexMatrix2D prPerp, ComplexMatrix2D prPerp1, double sVal)
	{
		ComplexMatrix2D nextE1;
		
		ComplexMatrix2D temp1 = (proj.add(proj1)).subtract(proj1.multiply(proj).scalarMul(new Complex(2,0)));
		//temp1 = temp1.subtract((proj.add(proj1)));
		
		ComplexMatrix2D deriv = (En1.subtract(En2)).scalarMul(new Complex(1/(2*epsilon),0));
		ComplexMatrix2D doubleDeriv = (En1.add(En2).subtract(En.scalarMul(new Complex(2,0)))).scalarMul(new Complex(1/(epsilon*epsilon),0));
		
		ComplexMatrix2D temp2 = (id.subtract(prPerp)).add(id.subtract(prPerp1));
		ComplexMatrix2D temp3 = (id.subtract(prPerp)).multiply(id.subtract(prPerp1));
		ComplexMatrix2D temp4 = (id.subtract(proj1)).multiply(id.subtract(proj));
		temp4 = temp4.subtract(proj1.multiply(id.subtract(proj)));
		temp4 = temp4.subtract((id.subtract(proj1)).multiply(proj));
		
		nextE1 = En.add(deriv.multiply(temp2).scalarMul(new Complex(0,-2*sVal)));
		nextE1 = nextE1.add(doubleDeriv.multiply(temp3).scalarMul(new Complex(-2*sVal*sVal,0)));
		nextE1 = proj1.multiply(proj).multiply(nextE1);
		
		nextE1 = nextE1.add(temp1.multiply(En).multiply(temp2));
		nextE1 = nextE1.add(temp1.multiply(deriv).multiply(temp3).scalarMul(new Complex(0,-2*sVal)));
		nextE1 = nextE1.add(temp4.multiply(En).multiply(temp3));
		
		//ComplexMatrix2D temp5 = proj1.multiply(proj).scalarMul(new Complex(2,0));
		//temp5 = temp5.subtract(proj).subtract(proj1);
		
		//nextE1 = nextE1.add(temp5.multiply(En).multiply(temp3));
		return nextE1;
	}
	
	public ComplexMatrix2D firstSoliton(double x, double t)
	{
		//evaluate E0*(-is) = E0^(-1)(is)
		getE0(x,t, new Complex(0,-s.getValue()));  // lambda = -is
		ComplexMatrix2D temp = new ComplexMatrix2D(E0);		//do this to avoid temp changing as E0 changes
		
		//compute projection onto vector E0^(-1)(-is)*initVec
		lambda = new Complex(1+epsilon,0);	//lambda = 1+epsilon
		
		//compute g1_til(1 + eps)
		ComplexMatrix2D mat = new ComplexMatrix2D(getgPerp(temp, s.getValue(), lambda, initVec));
		projPerp = getgTilde(mat, lambda, s.getValue()); 
		
		getg1(lambda);
		getE0(x,t,lambda);
		
		//store E0(1+eps), g1_til(1+eps)
		E[0][0] = new ComplexMatrix2D(E0);
		//E[0][5] = new ComplexMatrix2D(temp);
		g[0][0] = new ComplexMatrix2D(mat);
		
		
		//E1(1+epsilon)
		ComplexMatrix2D ret = new ComplexMatrix2D((g1).multiply(E0.multiply(mat.conjugate().transpose())));
		
		//store E1(1 + eps)
		//if(solitonNum.getDefaultValue() > 1)
		E[1][0] = new ComplexMatrix2D(ret);
		
		lambda = new Complex(1-epsilon,0);		//lambda = 1-epsilon
		
		//compute g1_til(1-eps)
		mat = new ComplexMatrix2D(getgPerp(temp, s.getValue(),  lambda, initVec));
		
		getg1( lambda);
		getE0(x,t,lambda);
		
		//store E0(1-eps), g1_til(1-eps)
		E[0][1] = new ComplexMatrix2D(E0);
		g[0][1] = new ComplexMatrix2D(mat);
		
		//E1(1-epsilon)
		ComplexMatrix2D ret1 = new ComplexMatrix2D((g1).multiply(E0.multiply(mat.conjugate().transpose())));
		
		//store E1(1 - eps)
		//if(solitonNum.getDefaultValue() > 1)
		E[1][1] = new ComplexMatrix2D(ret1);
		
		ret = new ComplexMatrix2D(ret.subtract(ret1));
		ret = new ComplexMatrix2D(ret.scalarMul(new Complex(1/(2*epsilon),0)));
		
		
		lambda = (new Complex(1,0));
		
		//compute g1_til(1)
		mat = new ComplexMatrix2D(getgPerp(temp,s.getValue(), lambda, initVec));
		
		getg1( lambda);
		getE0(x,t, lambda);
		
		//store E0(1), g1_til(1)
		E[0][2] = new ComplexMatrix2D(E0);
		g[0][2] = new ComplexMatrix2D(mat);
		
		ret1 = new ComplexMatrix2D((g1).multiply(E0.multiply(mat.conjugate().transpose())));
		
		//store E1(1)
		//if(solitonNum.getDefaultValue() > 1)
		E[1][2] = new ComplexMatrix2D(ret1);
				
		ret = ret.multiply(mat.multiply((E0.inverse()).multiply(g1.conjugate().transpose())));
		
		
		
		
		return ret;
	}
	
	public void afterFirstSol(double x, double t)
	{
		getE0(x,t, new Complex(0,-s.getValue()));  // lambda = -is
		ComplexMatrix2D temp = new ComplexMatrix2D(E0);	
		
		double tt = s.getValue();
		if(solitonNum==2)
			tt = s1.getValue();
		if(solitonNum==3)
		{
			tt = s2.getValue();
		}
		//store E0(-is2)
		lambda = new Complex(0, -tt);
		
		getE0(x,t, lambda);
		E[0][5] = new ComplexMatrix2D(E0);
		
		//store E0(eps - is1)
		lambda = new Complex(epsilon, -tt);
		getg1(lambda);
		getE0(x,t, lambda);
		
		//compute g1_til(eps-is1)
		ComplexMatrix2D mat = new ComplexMatrix2D(getgPerp(temp,tt, lambda, initVec));
		//mat = new ComplexMatrix2D(getg1Perp(lambda));
		
		//store E0(eps-is1), E1(eps-is1), g1_til(eps-is1)
		E[0][3] = new ComplexMatrix2D(E0);
		g[0][3] = new ComplexMatrix2D(mat);
		
		//compute and store E1(eps-is1)
		
		ComplexMatrix2D ret1 = new ComplexMatrix2D((g1).multiply(E0.multiply(mat.inverse())));
		E[1][3] = new ComplexMatrix2D(ret1);
		
		//store E0(-eps - is1)
		lambda = new Complex(-epsilon, -tt);
		getg1(lambda);
		getE0(x,t, lambda);
		
		//compute g1_til(-eps-is1)
		mat = new ComplexMatrix2D(getgPerp(temp,tt, lambda, initVec));
		//mat = new ComplexMatrix2D(getg1Perp(lambda));
		
		
		//store E0(-eps-is1), E1(-eps-is1), g1_til(-eps-is1)
		
		E[0][4] = new ComplexMatrix2D(E0);
		g[0][4] = new ComplexMatrix2D(mat);
		
		//compute and store E1(-eps-is1), E1(-is1)
		ret1 = new ComplexMatrix2D((g1).multiply(E0.multiply(mat.inverse())));
		E[1][4] = new ComplexMatrix2D(ret1);
			
		//in the if clause the single pole formula is used
		if(s.getValue()==s1.getValue())
			E[1][5] = getNextE(E[0][5], E[0][3], E[0][4], proj, projPerp, s.getValue());
		else
		{
			lambda = new Complex(0,-s1.getValue());
			getg1(lambda);
			getE0(x,t,lambda);
			mat = new ComplexMatrix2D(getgPerp(temp, s.getValue(), lambda, initVec));
			E[1][5] = g1.multiply(E0).multiply(mat.inverse());
		}
		//store g2_til
		lambda = new Complex(1+epsilon,0);
		g[1][0] = getgPerp(E[1][5],s1.getValue(), lambda, initVec1);
		projPerp1 = getgTilde(g[1][0], lambda, s1.getValue());
		lambda = new Complex(1-epsilon,0);
		g[1][1] = getgPerp(E[1][5],s1.getValue(), lambda, initVec1);
		lambda = new Complex(1,0);
		g[1][2] = getgPerp(E[1][5],s1.getValue(), lambda, initVec1);
		lambda = new Complex(epsilon,-tt);
		g[1][3] = getgPerp(E[1][5],tt, lambda, initVec1);
		lambda = new Complex(-epsilon,-tt);
		g[1][4] = getgPerp(E[1][5],tt, lambda, initVec1);
	}
	
	
	public ComplexMatrix2D iterate1(double x, double t)
	{
		ComplexMatrix2D mat;
		int num = 1;
		//next E(1+eps)
		lambda = new Complex(1+epsilon,0);
		getg2(lambda);
		mat = new ComplexMatrix2D(g2.multiply(E[num][0].multiply(g[num][0].conjugate().transpose())));
		
		E[num+1][0] = mat;
		
		//next E(1-eps)
		lambda = new Complex(1-epsilon,0);
		getg2(lambda);
		mat = new ComplexMatrix2D(g2.multiply(E[num][1].multiply(g[num][1].conjugate().transpose())));
		
		
		E[num+1][1] = mat;
		
		//next E(1)
		lambda = new Complex(1,0);
		getg2(lambda);
		mat = new ComplexMatrix2D(g2.multiply(E[num][2].multiply(g[num][2].conjugate().transpose())));
		
		
		E[num+1][2] = mat;
		
		
		ComplexMatrix2D ret = new ComplexMatrix2D(E[num+1][0]);
		ret = ret.subtract(E[num+1][1]);
		ret = ret.multiply(E[num+1][2].inverse());
		ret = ret.scalarMul(new Complex(1/(2*epsilon),0));
		
		return ret;
	}
	
	public void afterIterate1(double x, double t)
	{
		int num = 1;
		double tempVal = s2.getValue();
		if(s.getValue()==s1.getValue() && s1.getValue()==s2.getValue())
		{
			//E[num+1][5] = getNextE1(E[num-1][5], E[num-1][3], E[num-1][4], projPerp, projPerp1, s.getValue());
			s2.setValue(tempVal+0.01);
		}
		
		//next E(eps-is2)
		lambda = new Complex(epsilon,-s2.getValue());
		getg2(lambda);
		ComplexMatrix2D mat = new ComplexMatrix2D(g2.multiply(E[num][3].multiply(g[num][3].inverse())));
		
		
		E[num+1][3] = mat;
		
		//next E(-eps-is2)
		lambda = new Complex(-epsilon,-s2.getValue());
		getg2(lambda);
		mat = new ComplexMatrix2D(g2.multiply(E[num][4].multiply(g[num][4].inverse())));
		
		
		E[num+1][4] = mat;
		
		//next E(-is2)
		lambda = new Complex(0, -s2.getValue());
		
		
		if(s2.getValue()==s1.getValue() && s1.getValue() != s.getValue())
		{
			getg1(lambda);
			getE0(x,t, lambda);
			mat = getg1Perp(lambda);
			ComplexMatrix2D temp1 = new ComplexMatrix2D(g1.multiply(E0).multiply(mat.inverse()));
			
			lambda = new Complex(epsilon, -s2.getValue());
			getg1(lambda);
			getE0(x,t, lambda);
			mat = getg1Perp(lambda);
			ComplexMatrix2D temp2 = new ComplexMatrix2D(g1.multiply(E0).multiply(mat.inverse()));
			
			lambda = new Complex(-epsilon, -s2.getValue());
			getg1(lambda);
			getE0(x,t, lambda);
			mat = getg1Perp(lambda);
			ComplexMatrix2D temp3 = new ComplexMatrix2D(g1.multiply(E0).multiply(mat.inverse()));
			
			E[num+1][5] = getNextE(temp1, temp2, temp3, proj1, projPerp1, s2.getValue());
		}
		
		else if(s2.getValue()==s.getValue() && s.getValue() != s1.getValue())
		{
			getE0(x,t,lambda);
			getg2(lambda);
			ComplexMatrix2D temp = new ComplexMatrix2D(getg2Perp(lambda));
			ComplexMatrix2D temp1 = new ComplexMatrix2D(E0);
			lambda = new Complex(epsilon, -s2.getValue());
			getE0(x,t,lambda);
			ComplexMatrix2D temp2 = new ComplexMatrix2D(E0);
			lambda = new Complex(-epsilon, -s2.getValue());
			getE0(x,t,lambda);
			ComplexMatrix2D temp3 = new ComplexMatrix2D(E0);
			mat = new ComplexMatrix2D(getNextE(temp1,temp2,temp3, proj, projPerp, s2.getValue()));
			E[num+1][5] = g2.multiply(mat).multiply(temp.inverse());
			
		}
		
		else		//all s2 is distinct from s and s1
		{
			getE0(x,t,lambda);
			ComplexMatrix2D temp = new ComplexMatrix2D(getg1Perp(lambda));
			ComplexMatrix2D temp1 = new ComplexMatrix2D(getg2Perp(lambda));
			getg1(lambda);
			getg2(lambda);
			E[num+1][5] = g2.multiply(g1).multiply(E0).multiply(temp.inverse()).multiply(temp1.inverse());
			
		}
		//store g3_til
		lambda = new Complex(1+epsilon,0);
		g[num+1][0] = getgPerp(E[num+1][5],s2.getValue(), lambda, initVec2);
		projPerp2 = getgTilde(g[num+1][0], lambda, s2.getValue());
		lambda = new Complex(1-epsilon,0);
		g[num+1][1] = getgPerp(E[num+1][5],s2.getValue(), lambda, initVec2);
		lambda = new Complex(1,0);
		g[num+1][2] = getgPerp(E[num+1][5],s2.getValue(), lambda, initVec2);
		lambda = new Complex(epsilon,-s2.getValue());
		g[num+1][3] = getgPerp(E[num+1][5],s2.getValue(), lambda, initVec2);
		lambda = new Complex(-epsilon,-s2.getValue());
		g[num+1][4] = getgPerp(E[num+1][5],s2.getValue(), lambda, initVec2);
		
		s2.setValue(tempVal);
	}
	
	
	ComplexMatrix2D iterate2()
	{
		ComplexMatrix2D mat;
		int num = 2;
		//next E(1+eps)
		lambda = new Complex(1+epsilon,0);
		getg3(lambda);
		mat = new ComplexMatrix2D(g3.multiply(E[num][0].multiply(g[num][0].conjugate().transpose())));
		
		E[num+1][0] = mat;
		
		//next E(1-eps)
		lambda = new Complex(1-epsilon,0);
		getg3(lambda);
		mat = new ComplexMatrix2D(g3.multiply(E[num][1].multiply(g[num][1].conjugate().transpose())));
		
		
		E[num+1][1] = mat;
		
		//next E(1)
		lambda = new Complex(1,0);
		getg3(lambda);
		mat = new ComplexMatrix2D(g3.multiply(E[num][2].multiply(g[num][2].conjugate().transpose())));
		
		
		E[num+1][2] = mat;
		
		ComplexMatrix2D ret = new ComplexMatrix2D(E[num+1][0]);
		ret = ret.subtract(E[num+1][1]);
		ret = ret.multiply(E[num+1][2].inverse());
		ret = ret.scalarMul(new Complex(1/(2*epsilon),0));
		
		return ret;
	}
	
	synchronized public Vector3D surfacePoint(double x, double t) {		
		return new Vector3D(0,0,0);
	}
}