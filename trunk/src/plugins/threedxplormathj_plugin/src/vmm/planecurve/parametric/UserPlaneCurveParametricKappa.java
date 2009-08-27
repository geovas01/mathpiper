/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;

import vmm.core.Parameter;
import vmm.core.UserExhibit;
import vmm.core.VariableParamAnimateable;

/**
 * A plane curve c(s) = (x(s),y(s)) is defined by its curvature function kappa(s).
 * kappa(s) is to be entered by the user.
 * The Frenet equation c''(s) = kappa(s)*normal(s) gives c'(s) = (cos(alpha(s)), sin(alpha(s)) ),
 * where alpha'(s) = kappa(s). Therefore c(s) is obtained by a double integration.
 * We use a precomputed antiderivative alphs of kappa, stored in helpArray. This reduces the
 * double integration to a single one - and this is fast enough so that these Frenet curves
 * behave as if they were given by an explicit parametrization.
 * If tmin =< 0 =< tmax then alpha(0) = 0.
 */
public class UserPlaneCurveParametricKappa extends PlaneCurveParametric implements UserExhibit {
	
	private UserExhibit.Support userExhibitSupport;
	private UserExhibit.FunctionInfo kappa;
	double[] helpArray = new double[513];
	boolean needsNewHelpArray;
	double DT,Tstart;
	
	public UserPlaneCurveParametricKappa() {
		tmin.reset(-10);
		tmax.reset(+10);
		tResolution.reset(257);
		userExhibitSupport = new UserExhibit.Support(this);
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("a",1.0,1.0,1.0));
		userExhibitSupport.addFunctionParameter(new VariableParamAnimateable("b",1.0,0.5,1));
		kappa = userExhibitSupport.addRealFunction("kappa", "b *(2+cos(a*t))", "t");
		setDefaultWindow(-2,2,-2,2);
		needsNewHelpArray = true;
		DT = (tmax.getValue()-tmin.getValue())/(helpArray.length-1);
		Tstart = Math.max(Math.min(0, tmax.getValue()), tmin.getValue());
	}
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
	    super.parameterChanged(param, oldValue, newValue);
		needsNewHelpArray = true;
		Tstart = Math.max(Math.min(0, tmax.getValue()), tmin.getValue());
	}
	
	public double xValue(double t) {
		long num = 2*Math.round(2+4*Math.abs(t-Tstart));
		double x = 0;
		double f = Math.cos(angle(Tstart));
		if ((t-Tstart) != 0){
		  x = x + f;
		double dt = (t-Tstart)/num;
		for (int i = 1; i < num; i=i+2){
		  f = Math.cos(angle(Tstart+i*dt));
		  x = x + 4*f;
		  f = Math.cos(angle(Tstart+(i+1)*dt));
		  x = x + 2*f;
		  }
		  x = (x - f)*dt/3;
		}
		return x ;
	}
	
	public double yValue(double t) {
		long num = 2*Math.round(2+4*Math.abs(t-Tstart));
		double y = 0;
		double f = Math.sin(angle(Tstart));
		if ((t-Tstart) != 0){ 
		  y = y + f;
		double dt = (t-Tstart)/num;
		for (int i = 1; i < num; i=i+2)
		 {
		  y = y + 4*Math.sin(angle(Tstart+i*dt));
		  f = Math.sin(angle(Tstart+(i+1)*dt));
		  y = y + 2*f;
		  }
		  y = (y - f)*dt/3;
		}
		return y ;
	}

	public double xDerivativeValue(double t) {
		return Math.cos(angle(t));
	}
	
	public double yDerivativeValue(double t) {
		return Math.sin(angle(t));
	}
	
	public double x2ndDerivativeValue(double t) {
		return -Math.sin(angle(t))*kappa.realFunctionValue(t);
	}

	public double y2ndDerivativeValue(double t) {
		return Math.cos(angle(t))*kappa.realFunctionValue(t);
	}
	
	public Support getUserExhibitSupport() {
		return userExhibitSupport;
	}
	
	private void recomputeHelpArray(){
		int num = 4;
		double tm = tmin.getValue();
		DT = (tmax.getValue()-tm)/(helpArray.length-1);
		double w;
		double f = 0;
		double dt = DT/num;
		helpArray[0] = 0;
		for (int j = 1; j < helpArray.length; j++){
			      w = kappa.realFunctionValue(tm);
			for (int i = 1; i < num; i=i+2){
				  w = w + 4*kappa.realFunctionValue(tm+i*dt);
				  f = kappa.realFunctionValue(tm+(i+1)*dt);
				  w = w + 2*f;
				  }
				  w = (w - f)*dt/3;
				  tm = tm + DT;
			helpArray[j] = helpArray[j-1] + w;
		  }	
		needsNewHelpArray = false;
		double an = angle(Tstart);
		for (int j = 0; j < helpArray.length; j++){
			helpArray[j] = helpArray[j] - an;
		}
	}
	
	/**
	 * Simpson integration (1 4 2 4 2 ...2 4 1) of integrand = kappa(s)
	 */
	private double angle(double t) {
		if (needsNewHelpArray)  
		   { recomputeHelpArray(); }
		double w = 0;  double y = 0;   double dt;
		int k = (int) Math.max(0, Math.min(Math.round((t-tmin.getValue())/DT), helpArray.length-1) );	
		double ta = 	tmin.getValue() + k*DT;
		if (t == ta )  { w = helpArray[k];}
		else
		{ 
		   int num = (int) (2*Math.round(2 + 8*Math.abs(t-ta)));
	        dt = (t-ta)/num;
		    w = w + kappa.realFunctionValue(ta);
		for (int i = 1; i < num; i=i+2){
			w = w + 4*kappa.realFunctionValue(ta+i*dt);
			y = kappa.realFunctionValue(ta+(i+1)*dt);
			w = w + 2*y;
		}
			w = helpArray[k] + (w - y)*dt/3;
		}
			return w;
	}

	/*	private double angle(double t) { // same function as before, without helper array
		long num = 2*Math.round(2+4*Math.abs(t));
		double w = 0;
		if (t != 0){ 
		double y = kappa.realFunctionValue(0);
		double dt = t/num;
		  w = w + y;
		for (int i = 1; i < num; i=i+2){
		  y = kappa.realFunctionValue(i*dt);
		  w = w + 4*y;
		  y = kappa.realFunctionValue((i+1)*dt);
		  w = w + 2*y;
		  }
		  w = (w - y)*dt/3;
		}
		return w;
	}*/
	
}