/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.conformalmap;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import vmm.core.Complex;
import vmm.core.ComplexODE;
import vmm.core.ComplexParamAnimateable;
import vmm.core3D.Transform3D;
import vmm.core3D.View3D;


public class Weierstrass_p extends ConformalMap {

	private ComplexParamAnimateable branch = new ComplexParamAnimateable("vmm.conformalmap.Weierstrass_p.branchPoint",
			"0.8 + 0.6*i", "0.8", "0.8 + i");
	//private IntegerParam choice1_2 = new IntegerParam("vmm.conformalmap.Weierstrass_p.choice1_2",1);
	final private Complex zero = new Complex(Complex.ZERO_C);
	final private Complex one = new Complex(Complex.ONE_C);
	final private Complex iC = new Complex(Complex.I_C);

	private ComplexODE myODE = new ComplexODE(zero, one.times(2.0), zero, one.times(-2.0), zero); // f'^2 = -2*f*(f^2-1), elliptic

	public Weierstrass_p() {
		addParameter(branch);
		umin.reset(-1.0);
		umax.reset(1.0);
		vmin.reset(0.0);
		vmax.reset(0.5);
		removeParameter(umin);  // We want the function evaluated on its natural fundamental domain
		removeParameter(umax);
		removeParameter(vmin);
		removeParameter(vmax);
		vres.setValueAndDefault(8);
		ures.setValueAndDefault(4*vres.getDefaultValue());  
		setDefaultWindow2D(-5,5,-4,4);
		gridTypeSelect.setEnabled(false);  // -- disable the grid select options, since the
		                                   // grid type is ignored in this exhibit (in gridMap(u,v)).
		setFramesForMorphing(20);         // -- Since computing each frame is so slow, use fewer frames
		setUseFilmstripForMorphing(true); // and save them in a filmstrip.
	}

	private Complex branchP = branch.getValue();  
	private Complex approxInitDeriv = new Complex(one.plus(iC));
	private Complex period1 = new Complex(1.0, 0.0);
	private Complex period2 = new Complex(0.0, 1.0);
	private Complex periodAverage = new Complex(0.5,0.5); // -- introduced to avoid recomputing over and over in function()
	private boolean needsInvers = false;
	private double detPeriod = 1.0;
	private Complex localArg = new Complex(1.0, 0.0);
	private Complex localVal = new Complex(1.0, 0.0);
	//{System.out.println("POINTS_PER_INTERVAL = " + POINTS_PER_INTERVAL);}
	private Complex[][] argumentGridPart2;     // = new Complex[uCount+1][vCount+1];
	private Complex[][] ValueGridPart2;        // = new Complex[uCount+1][vCount+1]; 
	
	
	
	/**
	 * Overridden to compute half of the data as a transformation of the data for the first half.  The
	 * first half of the data is computed by solving an ODE, stepping through the array.  The
	 * second half is then computed from the first half by a symmetry of the function, and is stored in a 
	 * separate set of value/argument arrays.
	 */
	protected void computeDrawData3D(View3D view, boolean exhibitNeedsRedraw, Transform3D previousTransform3D, Transform3D newTransform3D) {
		if (exhibitNeedsRedraw) {		
			branchP = branch.getValue();
			myODE.setPoly(zero, one, branchP.minus(branchP.inverse()), one.times(-1.0), zero);
			period1 = ComputeFirstPeriod();
			period2 = ComputeSecondPeriod();
			periodAverage = period1.plus(period2).times(0.5);
			detPeriod = period1.det(period2);
			Complex[]val = new Complex[2];
			Complex storedDerivative = new Complex(one.plus(iC));
			//super.computeDrawData3D(view, exhibitNeedsRedraw, previousTransform3D, newTransform3D);  // Do not want usual way
			int uCount = ures.getValue() * POINTS_PER_INTERVAL;
			int vCount = vres.getValue() * POINTS_PER_INTERVAL;
			valueGrid = new Complex[uCount+1][vCount+1];
			argumentGrid = new Complex[uCount+1][vCount+1];
			double uStart = umin.getValue();
			double vStart = vmin.getValue();
			double uEnd = umax.getValue();
			double vEnd = vmax.getValue();
			double du = (uEnd - uStart) / uCount;
			double dv = (vEnd - vStart) / vCount;
			for (int i = 0; i <= uCount ; i++) {
				for (int j = 0; j <= vCount; j++) {
					argumentGrid[i][j] = gridMap(uStart + i*du,vStart + j*dv);
				}
			}
			
			for (int i = 0; i <= uCount ; i++) { // compute valueGrid by solving an ODE
				if (i==0)
					val = myODE.ODEstep4(periodAverage, argumentGrid[0][0], iC, one.plus(iC), 24);
				else
					val = myODE.ODEstep4(argumentGrid[i-1][0], argumentGrid[i][0], valueGrid[i-1][0], storedDerivative, 16);
				valueGrid[i][0] = val[0];	
				storedDerivative = val[1];
				for (int j = 1; j <= vCount; j++) {
					val = myODE.ODEstep4(argumentGrid[i][j-1], argumentGrid[i][j], val[0], val[1], 16);
					valueGrid[i][j] = val[0];		
				}
			}
			argumentGridPart2 = new Complex[uCount+1][vCount+1]; // compute second part of fundamental domain
			ValueGridPart2 = new Complex[uCount+1][vCount+1];    // compute the values on the second part by transforming the values on the first part
			for (int i = 0; i <= uCount ; i++) {
				for (int j = 0; j <= vCount; j++) {
					argumentGridPart2[i][j] = argumentGrid[i][j];  //  new Complex(argumentGrid[i][j]); -- no need to create a new object
					argumentGrid[i][j] = argumentGrid[i][j].times(-1.0).plus(period2);
					ValueGridPart2[i][j] = valueGrid[i][j];        // new Complex(valueGrid[i][j]); -- no need to create a new object
					valueGrid[i][j] = valueGrid[i][j].inverse().times(-1.0); 
				}
			}		
		}
	}

	
	/**
	 * This is overridded to draw the two halves of the data separately, only one of them in color.
	 * The colored part is where the ODE is solved, the other part needs a few extra lines.
	 * This is done by calling super.doDraw3D() twice, and swapping in the second half of the data
	 * before calling it the second time.
	 */
	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
		boolean savedColor = ((ConformalMapView)view).getUseColor();
		((ConformalMapView)view).setUseColor(false);
		super.doDraw3D(g, view, transform);
		if ( !((ConformalMapView)view).getDrawValueGrid() ){
//			 Draw many fundamental domains into the argument plane 
			int n = 2*3 + 1;  // needs to be odd
			for (int i = -n; i <= n ; i=i+2){
			g.draw(new Line2D.Double(-n*period1.re-i*period2.re,-n*period1.im-i*period2.im, n*period1.re-i*period2.re,n*period1.im-i*period2.im));
			g.draw(new Line2D.Double( i*period1.re-n*period2.re, i*period1.im-n*period2.im, i*period1.re+n*period2.re,i*period1.im+n*period2.im));
			}
		}
		Complex[][] temp;
		temp = argumentGrid;               // Swap out extra argument/value arrays
		argumentGrid = argumentGridPart2;
		argumentGridPart2 = temp;
		temp = valueGrid;
		valueGrid  = ValueGridPart2;
		ValueGridPart2 = temp;
		((ConformalMapView)view).setUseColor(savedColor);
		super.doDraw3D(g, view, transform);
		temp = argumentGrid;               // Swap in extra argument/value arrays
		argumentGrid = argumentGridPart2;
		argumentGridPart2 = temp;
		temp = valueGrid;
		valueGrid  = ValueGridPart2;
		ValueGridPart2 = temp;
	}

// -- it is not necessary to override this method, since the usual computeData3D model works OK
//    without special handling for computing the arrays and periods.
//	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
//		super.parameterChanged(param, oldValue, newValue);
//		needsPeriod = true;
//		needsArrays = true;
//	}

	private  Complex ComputeFirstPeriod(){  // -- Changed from public to private
		Complex [] w = new Complex[2];
		Complex middle = branchP.inverse();        middle.assignTimes(-0.5);
		w = myODE.ComplexMultiStepIntegrator(iC, middle, zero, approxInitDeriv,32);
		w = myODE.ComplexMultiStepIntegrator(middle, iC.times(-1.0), w[1], w[0],32);
//		System.out.println("period1 = "+w[1].re+" + "+w[1].im+"*i"); 
		w[1].assignTimes(-1.0);
		return w[1];
	}

	private  Complex ComputeSecondPeriod(){ // -- Changed from public to private
		Complex [] w = new Complex[2];
		w = myODE.ComplexMultiStepIntegrator(iC, branchP.times(0.5), zero, approxInitDeriv,32);
		w = myODE.ComplexMultiStepIntegrator(branchP.times(0.5), iC.times(-1.0), w[1], w[0],32);
//		System.out.println("period2 = "+w[1].re+" + "+w[1].im+"*i");
		w[1].assignTimes(-1.0);
		return w[1];  
	}

	protected Complex gridMap(double u,double v){ // period1, period2 are half the periods, used as basis
		//return period1.times(u).plus(period2.times(v));
		return new Complex(u*period1.re+v*period2.re, u*period1.im+v*period2.im);
	}
	
	private Complex reduceModPeriods(Complex argument) {
		//      Compute only in one half of the fundamental domain, stay away from infinity
		//		argument = period1*uComp + period2*vComp
		double uComp = argument.det(period2)/detPeriod;
		double vComp = period1.det(argument)/detPeriod;
		if (uComp > 1) {uComp = uComp - 2*Math.floor((1.0+uComp)/2.0);}
		else if (uComp < -1) {uComp = uComp - 2*Math.floor((1.0+uComp)/2.0);}
		if (vComp > 1) {vComp = vComp - 2*Math.floor((1.0+vComp)/2.0);}
		else if (vComp < -1) {vComp = vComp - 2*Math.floor((1.0+vComp)/2.0);}
		Complex w = new Complex(uComp*period1.re+vComp*period2.re, uComp*period1.im+vComp*period2.im);
		// w is in the fundamental domain, but may be close to the pole at u = 1, v = 1.
		if (vComp > 0.5) {
			needsInvers = true;
			w.re = -w.re + period1.re +period2.re;
			w.im = -w.im + period1.im +period2.im;
		}
		else if (vComp < -0.5) {
			needsInvers = true;
			w.re = -w.re + period1.re -period2.re;
			w.im = -w.im + period1.im -period2.im;
		}
		return w;
	}

	protected Complex function(Complex argument) { 
		/**
		 * Differential equation:  f'^2 = f + (P-1/P)*f^2 - f^3
		 * Initial condition: f( period1.plus(period2).times(0.5) ) = i
		 */
		needsInvers = false;
		localArg = reduceModPeriods(argument);
		localVal = (myODE.ODEstep4(periodAverage, localArg, iC, one.plus(iC), 16))[0]; // 4th order Tayolor step to one Third
		if (needsInvers) {
			localVal.assignInvert(); localVal.assignTimes(-1.0);
		}
		return localVal;
		//  return (myODE.ODEstep2_2(periodAverage, argument, iC, one.plus(iC), 16));// 2nd order step to midpoint
	}


}
