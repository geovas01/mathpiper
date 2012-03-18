/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder2D;

import vmm.core.*;


	public class HarmonicOscillator extends ODE1stOrder2D {
		
		private RealParamAnimateable springConstant;
		private RealParamAnimateable friction;
		
		public HarmonicOscillator() { 
			setDefaultWindow(-5,5,-3,3);
			springConstant = new RealParamAnimateable("vmm.ode.firstorder2D.HarmonicOscillator.springConstant", 1, 0.5, 1.5);
			friction = new RealParamAnimateable("vmm.ode.firstorder2D.HarmonicOscillator.friction", 0, 0, 1);
			addParameter(friction);
			addParameter(springConstant);
		}

		protected double x1Prime(double x1, double x2) {
			return  x2;
		}

		protected double x2Prime(double x1, double x2) {
			return - springConstant.getValue()*x1 - friction.getValue()*x2;
		}
		
	}