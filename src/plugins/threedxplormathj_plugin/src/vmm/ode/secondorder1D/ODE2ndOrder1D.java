/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder1D;

import vmm.ode.firstorder2D.ODE1stOrder2D;

/**
 * Represents a second order ODE exhibit x'' = f(x,x',t).
 * This is subsumed under the ODE_2D first order class by the standard trick;
 * namely setting  x'(t) = y and  y'' = f(x,y,t).
 */
    //  public abstract class ODE2ndOrder1D extends ODE1stOrder2DNonAutonomous {
	
	public abstract class ODE2ndOrder1D extends ODE1stOrder2D {
	
	// abstract protected double f(double x1, double x2, double t);
	
	abstract protected double f(double x1, double x2);


	//protected double x1Prime(double x1, double x2, double t){
	protected double x1Prime(double x1, double x2){
		return x2;
	}

	//  protected double x2Prime(double x1, double x2, double t){
	protected double x2Prime(double x1, double x2){
		return f(x1,x2);
	}
	    

  }
	

