/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.spacecurve.parametric;

import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.core.I18n;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core.View;
import vmm.core3D.Vector3D;

/**
 * Defines a space curve of constant curvature kappa by integrating the Frenet equations with kappa=aa
 * and a torsion function tau(t) = bb + cc*sin(t) + dd*sin(2t) + ee*sin(3t).
 * This Class behaves like an explicitly parametrized curve, because the program precomputes the
 * Frenet frame on 100 points between tmin and tmax so that, when the Superclass calls the method
 *  Vector3D value(), the ODEsolver only has to integrate from the nearest precomputed point.
 */
public class ConstantCurvature extends SpaceCurveParametric {
	
	
	RealParamAnimateable aa = new RealParamAnimateable("vmm.spacecurve.parametric.ConstantCurvature.aa",1.0, 1.0, 1.0);
	RealParamAnimateable bb = new RealParamAnimateable("vmm.spacecurve.parametric.ConstantCurvature.bb",0.0, 0.0, 0.0);
	RealParamAnimateable cc = new RealParamAnimateable("vmm.spacecurve.parametric.ConstantCurvature.cc",1.00844728,1.354743, 0.314);
	RealParamAnimateable dd = new RealParamAnimateable("vmm.spacecurve.parametric.ConstantCurvature.dd",0.0, 0.0, 0.0);
	RealParamAnimateable ee = new RealParamAnimateable("vmm.spacecurve.parametric.ConstantCurvature.ee",0.0, 0.0, 0.0);
	double kappa = 0.0;
	double b = 0.0; 
	double c = 0.0;
	double d = 0.0;
	double e = 0.0;
	
	private int exampleNumber = 0;
	private ActionRadioGroup exampleSelect;
	
	public ConstantCurvature() {
		setDefaultViewpoint(new Vector3D(0,-16,-6));
		setDefaultWindow(-4,4,-3,3);
		tResolution.setValueAndDefault(300);
		tmin.setValueAndDefault(0);
		tmax.setValueAndDefaultFromString("4*pi");
		addParameter(ee);
		addParameter(dd);
		addParameter(cc);
		addParameter(bb);
		addParameter(aa);
		tubeSize.setValueAndDefault(0.2);

		// Next the constructor part for the radio buttons:	
		ActionRadioGroup group = new ActionRadioGroup() {
			public void optionSelected(int selectedIndex) {
				setExampleNumberFunction(selectedIndex);
			}
		};
		group.addItem(I18n.tr("vmm.spacecurve.parametric.ConstantCurvature.example0"));
		group.addItem(I18n.tr("vmm.spacecurve.parametric.ConstantCurvature.example1"));
		group.addItem(I18n.tr("vmm.spacecurve.parametric.ConstantCurvature.example2"));
		group.addItem(I18n.tr("vmm.spacecurve.parametric.ConstantCurvature.example3"));
		group.addItem(I18n.tr("vmm.spacecurve.parametric.ConstantCurvature.example4"));
		group.setSelectedIndex(0);
		exampleSelect = group;
	}
	
	// First the code connected with the radio buttons:
	
	public int getExampleNumberFunction() {
		return exampleNumber;
	}
	public void setExampleNumberFunction(int exampleNumber) {
		if (this.exampleNumber != exampleNumber) {
			this.exampleNumber = exampleNumber;
			//exampleSelect.setSelectedIndex(exampleNumber);
			System.out.println("exampleNumber = "+exampleNumber);
			resetFourierConstants(exampleNumber); // here we need to prepare the selected example
			forceRedraw();
		}
	}
	
	public ActionList getActionsForView(final View view) {
		ActionList actions = super.getActionsForView(view);
		actions.add(null); // separator in menu
		ActionList submenu = new ActionList(I18n.tr("vmm.spacecurve.parametric.ConstantCurvature.closedCurves"));
		submenu.add(exampleSelect);
		actions.add(submenu);
		return actions;
	}
	
	private void resetFourierConstants(int examplNum){
		needsNewArray = true;
		tResolution.reset(300);
		aa.reset(1.0);
		switch (examplNum){
			case 0:
				tmax.reset("4*pi");
				bb.reset(0.0);
				cc.reset(1.0084472);
				break;
			case 1:
				tmax.reset("6*pi");
				bb.reset(0.0);
				cc.reset(1.3547);
				break;
			case 2:
				tmax.reset("10*pi");
				bb.reset(0.2470);
				cc.reset(0.4053);
				break;
			case 3:
				tmax.reset("10*pi");
				bb.reset(0.5117);
				cc.reset(0.8551);
				break;
			case 4:
				tResolution.reset(600);
				tmax.reset("22*pi");
				aa.reset(0.8944);
				bb.reset(0.5005);
				cc.reset(0.4129);
				break;
			}
		getConstants();
	}
	
	// The code for the curve starts here
	
	private void getConstants(){
		tr = tResolution.getValue();
		kappa = aa.getValue();
		b = bb.getValue(); 
		c = cc.getValue();
		d = dd.getValue();
		e = ee.getValue();
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
	
	/*{
		System.out.println(tr);
	}*/
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
	    super.parameterChanged(param, oldValue, newValue);
		needsNewArray = true;
	}
	
	protected double tau(double t){
		return (b + c*Math.sin(t) + d*Math.sin(2*t) + e*Math.sin(3*t));
	}
	
	/*protected double dtau(double t){
		return (c*Math.cos(t) + 2*d*Math.cos(2*t) + 3*e*Math.cos(3*t));
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
			midT = T.plus(N.times(kappa*dt/2));
			midN = N.plus(T.times(-kappa*dt/2)).plus(B.times(tau(t)*dt/2));
			midB = B.plus(N.times(-tau(t)*dt/2));
			
			P = P.plus( T.plus(midT.times(4)).times(dt/6.0) );
			t = t+dt/2;
			
			T = T.plus(midN.times(kappa*dt));
			N = N.plus(midT.times(-kappa*dt)).plus(midB.times(tau(t)*dt));
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
				dT1.assignTimes(kappa*dt/2, N);
				dN1.assignLinComb(-kappa*dt/2, T, tau(t)*dt/2, B); //T.times(-kappa*dt/2).plus(B.times(tau(t)*dt/2));
				dB1.assignTimes(-tau(t)*dt/2, N); //N.times(-tau(t)*dt/2);
				
				t = t+dt/2;
				
				dT2.assignSumTimes(N, dN1, kappa*dt);    //(N.plus(dN1)).times(kappa*dt);
				  dN2.assignSumTimes(T, dT1, -kappa*dt);   Vvv.assignSumTimes(B, dB1, tau(t)*dt);
				dN2.assignPlus(Vvv);     //(T.plus(dT1)).times(-kappa*dt).plus((B.plus(dB1)).times(tau(t)*dt));
				dB2.assignSumTimes(N, dN1, -tau(t)*dt);  //(N.plus(dN1)).times(-tau(t)*dt);
				dT3.assignLinComb(kappa*dt,N, kappa*dt/2,dN2);   //(N.plus(dN2.times(0.5))).times(kappa*dt);
				 dN3.assignLinComb(-kappa*dt,T, -kappa*dt/2,dT2); Vvv.assignLinComb(tau(t)*dt,B, tau(t)*dt/2,dB2);
				dN3.assignPlus(Vvv);	//(T.plus(dT2.times(0.5))).times(-kappa*dt).plus((B.plus(dB2.times(0.5))).times(tau(t)*dt));
				dB3.assignLinComb(-tau(t)*dt,N, -tau(t)*dt/2,dN2);   //(N.plus(dN2.times(0.5))).times(-tau(t)*dt);
				
				t=t+dt/2;
				
				dT4.assignSumTimes(N, dN3, kappa*dt/2);      //(N.plus(dN3)).times(kappa*dt/2);
				  dN4.assignSumTimes(T, dT3, -kappa*dt/2);   Vvv.assignSumTimes(B, dB3, tau(t)*dt/2);
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
		getConstants();
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
		  
		  i = (int) Math.floor(tResolution.getValue()*(t-tmin.getValue())/(tmax.getValue()-tmin.getValue()));
			i = Math.max(0, Math.min(i, tResolution.getValue()));
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
		
	i = (int) Math.floor(tResolution.getValue()*(t-tmin.getValue())/(tmax.getValue()-tmin.getValue()));
	i = Math.max(0, Math.min(i, tResolution.getValue()));
		tStart = tmin.getValue() + i*(tmax.getValue()-tmin.getValue())/tResolution.getValue();
		  for (int j = 0; j < 4 ; j++){
			   initialFrenet[j] = helperFrame[j+ 4*i]; 
		  }  
		//Vector3D point=new Vector3D(frenetODEstep2(tStart,t,initialFrenet,128)[0]);
		Vector3D point=(frenetODEstep4(tStart,t,initialFrenet,16)[0]);
		return point;
	}
	
	
}
