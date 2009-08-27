/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import vmm.core.Parameter;
import vmm.core.VariableParamAnimateable;
import vmm.core3D.UserExhibit3D;
import vmm.core3D.Vector3D;

/**
 * Defines a space curve from three functions that give the coordinates of points on the curve as
 * functions of t, where the data is input by the user.
 */
public class UserSpaceCurveKappaTau extends SpaceCurveParametric implements UserExhibit3D {
		
	private UserExhibit3D.Support userExhibitSupport;
	private UserExhibit3D.FunctionInfo Kappa, Tau;
	
	//private int exampleNumber = 0;
	//private ActionRadioGroup exampleSelect;
	
	public UserSpaceCurveKappaTau() {
		setDefaultViewpoint(new Vector3D(0,-16,-6));
		setDefaultWindow(-2,2, -1.7,1.7);
		tResolution.setValueAndDefault(300);
		tmin.setValueAndDefault(0);
		tmax.setValueAndDefaultFromString("4*pi");
		tubeSize.reset(0.1);
		
		userExhibitSupport = new UserExhibit3D.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",2,2,2));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",0.0,0.0,0.0));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("c",0.2,0.2,0.2));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("d",2.0,1.5,3.5));
		Kappa = userExhibitSupport.addRealFunction("kappa", "(a + b*cos(t))", "t");
		Tau = userExhibitSupport.addRealFunction("tau", "(c + d*sin(3*t))", "t");
	}	
		int tr = tResolution.getValue();
		Vector3D startPoint = new Vector3D(0,0,0);
		Vector3D P = new Vector3D(0,0,-1);
		Vector3D T = new Vector3D(1,0,0);
		Vector3D N = new Vector3D(0,1,0);
		Vector3D B = new Vector3D(0,0,1);
		Vector3D[] initialFrenet = new Vector3D [] {startPoint,T,N,B};
		Vector3D[] repere = new Vector3D[]{P, T, N, B};
		Vector3D[] pointSet = new Vector3D[tr+1];
		Vector3D[] helperFrame = new Vector3D[4*(tr + 1)];
		boolean needsNewArray = true;
		//double kappa = 1;
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
	    super.parameterChanged(param, oldValue, newValue);
		needsNewArray = true;
	}
	
	protected double kappa(double t){
		return Kappa.realFunctionValue(t);
	}
	
	protected double tau(double t){
		return Tau.realFunctionValue(t);
	}
	
	/*protected double dtau(double t){
		return ;
	}*/
	
	/**
	 * Second order method, "Step to midpoint"
	 */
	protected Vector3D[] frenetODEstep2(double tInitial, double tFinal, Vector3D[]initialVal, int numSubdivision){
		P = initialVal[0];
		T = initialVal[1];
		N = initialVal[2];
		B = initialVal[3];
		double t = tInitial;
		double newSub = (1.0+Math.abs(tFinal-tInitial))*numSubdivision;
		double dt = (tFinal-tInitial)/newSub;
		Vector3D midT = new Vector3D(T);
		Vector3D midN = new Vector3D(N);
		Vector3D midB = new Vector3D(B);
		
		for (int i = 0; i < newSub ; i++) 
		  {
			midT = T.plus(N.times(kappa(t)*dt/2));
			midN = N.plus(T.times(-kappa(t)*dt/2)).plus(B.times(tau(t)*dt/2));
			midB = B.plus(N.times(-tau(t)*dt/2));
			
			P = P.plus( T.plus(midT.times(4)).times(dt/6.0) );
			t = t+dt/2;
			
			T = T.plus(midN.times(kappa(t)*dt));
			N = N.plus(midT.times(-kappa(t)*dt)).plus(midB.times(tau(t)*dt));
			B = B.plus(midN.times(-tau(t)*dt));
			
			P = P.plus(T.times(dt/6)); // Simpson
			t=t+dt/2;
		  }
		return new Vector3D[]{P,T,N,B};
	}
	
	/**
	 * Runge Kutta integrator, 4th order
	 */
	Vector3D dT1, dN1, dB1, dT2, dN2, dB2, dT3, dN3, dB3, dT4, dN4, dB4, Vvv;
	{dT1=new Vector3D(0,0,0); dN1=new Vector3D(0,0,0); dB1=new Vector3D(0,0,0);
	dT2=new Vector3D(0,0,0); dN2=new Vector3D(0,0,0); dB2=new Vector3D(0,0,0);
	dT3=new Vector3D(0,0,0); dN3=new Vector3D(0,0,0); dB3=new Vector3D(0,0,0);
	dT4=new Vector3D(0,0,0); dN4=new Vector3D(0,0,0); dB4=new Vector3D(0,0,0);
	Vvv=new Vector3D(0,0,0);}
	
	protected Vector3D[] frenetODEstep4(double tInitial, double tFinal, Vector3D[]initialVal, int numSubdivision){
		P = initialVal[0];
		T = initialVal[1];
		N = initialVal[2];
		B = initialVal[3];
		double t = tInitial;
		double newSub = Math.floor( (1.0+Math.abs(tFinal-tInitial))*numSubdivision );
		double dt = (tFinal-tInitial)/newSub/2.0;
		newSub = newSub - 1.0/16.0;
		
		for (int i = 0; i < newSub; i++) {
			for (int j = 1; j < 3; j++) { // Two Runge-Kutta steps for one Simpson 1-4-1
				dT1.assignTimes(kappa(t)*dt/2, N);
				dN1.assignLinComb(-kappa(t)*dt/2, T, tau(t)*dt/2, B); //T.times(-kappa*dt/2).plus(B.times(tau(t)*dt/2));
				dB1.assignTimes(-tau(t)*dt/2, N); //N.times(-tau(t)*dt/2);
				
				t = t+dt/2;
				
				dT2.assignSumTimes(N, dN1, kappa(t)*dt);    //(N.plus(dN1)).times(kappa*dt);
				  dN2.assignSumTimes(T, dT1, -kappa(t)*dt);   Vvv.assignSumTimes(B, dB1, tau(t)*dt);
				dN2.assignPlus(Vvv);     //(T.plus(dT1)).times(-kappa*dt).plus((B.plus(dB1)).times(tau(t)*dt));
				dB2.assignSumTimes(N, dN1, -tau(t)*dt);  //(N.plus(dN1)).times(-tau(t)*dt);
				dT3.assignLinComb(kappa(t)*dt,N, kappa(t)*dt/2,dN2);   //(N.plus(dN2.times(0.5))).times(kappa*dt);
				 dN3.assignLinComb(-kappa(t)*dt,T, -kappa(t)*dt/2,dT2); Vvv.assignLinComb(tau(t)*dt,B, tau(t)*dt/2,dB2);
				dN3.assignPlus(Vvv);	//(T.plus(dT2.times(0.5))).times(-kappa*dt).plus((B.plus(dB2.times(0.5))).times(tau(t)*dt));
				dB3.assignLinComb(-tau(t)*dt,N, -tau(t)*dt/2,dN2);   //(N.plus(dN2.times(0.5))).times(-tau(t)*dt);
				
				t=t+dt/2;
				
				dT4.assignSumTimes(N, dN3, kappa(t)*dt/2);      //(N.plus(dN3)).times(kappa*dt/2);
				  dN4.assignSumTimes(T, dT3, -kappa(t)*dt/2);   Vvv.assignSumTimes(B, dB3, tau(t)*dt/2);
				dN4.assignPlus(Vvv);	  //(T.plus(dT3)).times(-kappa*dt/2).plus((B.plus(dB3)).times(tau(t)*dt/2));
				dB4.assignSumTimes(N, dN3, -tau(t)*dt/2);    //(N.plus(dN3)).times(-tau(t)*dt/2);
				
				// now one needs to return new vectors:
				P = P.plus(T.times(dt*j*j/3.0));// part of Simpson .assignLinComb(1, j*j*dt/3.0, T);   //
				    Vvv.assignSumTimes(dT1, dT2, dT3, dT4, 1.0/3.0);
				T = T.plus(Vvv);
				    // T = T.plus( ( dT1.plus(dT2.plus(dT3.plus(dT4))) ).times(1.0/3.0) );
			        Vvv.assignSumTimes(dN1, dN2, dN3, dN4, 1.0/3.0);
			    N = N.plus(Vvv);
			        // N = N.plus( ( dN1.plus(dN2.plus(dN3.plus(dN4))) ).times(1.0/3.0) );
			        Vvv.assignSumTimes(dB1, dB2, dB3, dB4, 1.0/3.0);
			    B = B.plus(Vvv);    
				    // B = B.plus( ( dB1.plus(dB2.plus(dB3.plus(dB4))) ).times(1.0/3.0) );
			}
			P.assignLinComb(1, dt/3.0, T); 
				//P.plus(T.times(dt/3.0)); // Simpson integration. Because of the two Runge Kutta steps, dt is half the step size
		}
		return new Vector3D[]{P,T,N,B};
	}

	
	private void createFrenetArray(){
		//getConstants();
		int tr = tResolution.getValue();
		double dt = (tmax.getValue() - tmin.getValue())/tr;
		double t = tmin.getValue();
		helperFrame = new Vector3D [4*(tr + 1)];
		pointSet = new Vector3D[tr+1];
		
		helperFrame[0]= new Vector3D(0,0,0);
		helperFrame[1]= new Vector3D(1,0,0);
		helperFrame[2]= new Vector3D(0,1,0);
		helperFrame[3]= new Vector3D(0,0,1);
		pointSet[0] = new Vector3D(helperFrame[0]);
		
		for (int i = 0; i < tr ; i++) { //tr = tResolution.getValue()
			   for (int j = 0; j < 4 ; j++){
				   initialFrenet[j] = helperFrame[j+4*i];
			   }
			repere = frenetODEstep4(t,t+dt,initialFrenet,32);
			   for (int j = 0; j < 4 ; j++){
				   helperFrame[j+4*(i+1)] = new Vector3D(repere[j]);
			   }
			   pointSet[i+1] = (helperFrame[4*(i+1)]);
			t = t+dt;
		}
		startPoint = getCenterOfPoints(pointSet, tr+1);
		startPoint.x = - startPoint.x; startPoint.y = - startPoint.y; startPoint.z = - startPoint.z;
		for (int i = 0; i <= tr ; i++) {
			helperFrame[4*i].assignPlus(startPoint);
		}
		// See the line 'needsNewArray = true;' in the above override of public void parameterChanged(...
	}

	
	// The following override method does not use the default numerical differentiation but integrates
	// the Frenet ODE. The precomputed array is used to speed up the computation.
	public Vector3D[] makeRepereMobile(double t) {
		int i = 0;
		double tStart = 0.0;
		  if (needsNewArray) {
			  createFrenetArray();
			  needsNewArray = false;
		  }
		  
		  i = (int) Math.round(tResolution.getValue()*(t-tmin.getValue())/(tmax.getValue()-tmin.getValue()));
			tStart = tmin.getValue() + i*(tmax.getValue()-tmin.getValue())/tResolution.getValue();
			  for (int j = 0; j < 4 ; j++){
				   initialFrenet[j] = helperFrame[j+ 4*i];
			       }
		//repere = frenetODEstep2(tStart,t,initialFrenet,128);
		repere = frenetODEstep4(tStart,t,initialFrenet,16);
		return repere;
	}

    // This method has to be supplied by the Class. It behaves like a function call,
	// but the values are produced by solving an ODE.
	protected Vector3D value(double t) {
		int i = 0;
		double tStart = 0.0;
		if (needsNewArray) {
			  createFrenetArray();
			  needsNewArray = false;
		  }
	i = (int) Math.round(tResolution.getValue()*(t-tmin.getValue())/(tmax.getValue()-tmin.getValue()));
		tStart = tmin.getValue() + i*(tmax.getValue()-tmin.getValue())/tResolution.getValue();
		  for (int j = 0; j < 4 ; j++){
			   initialFrenet[j] = helperFrame[j+ 4*i];
		       }  
		//Vector3D point=new Vector3D(frenetODEstep2(tStart,t,initialFrenet,128)[0]);
		Vector3D point=(frenetODEstep4(tStart,t,initialFrenet,16)[0]);
		return point;
	}
	


	public vmm.core.UserExhibit.Support getUserExhibitSupport() {
		return userExhibitSupport;
	}
	
	

}
