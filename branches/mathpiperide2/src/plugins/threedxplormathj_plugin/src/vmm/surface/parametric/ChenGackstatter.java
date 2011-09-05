/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import java.awt.Color;
/*
import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.core.BasicAnimator;
import vmm.core.I18n;
import vmm.surface.SurfaceView; 
*/
import vmm.core.Complex;
import vmm.core.IntegerParam;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core.View;
import vmm.core3D.ComplexVector3D;
import vmm.core3D.GridTransformMatrix;
import vmm.core3D.Vector3D;
import vmm.core3D.View3DLit;

/**
 * The Chen-Gackstatter surface posed surprising numerical difficulties, which increased with exponent.
 * 1.) One cannot integrate the Weierstrass Data close enough into the three saddles to close gaping holes,
 * starting at exponent = 5. 
 * 2.) For putting the pieces together one has to find the intersection of the symmetry planes. Only after
 * experimentation did I find points on these symmetry lines that allowed to compute the intersection
 * with sufficient accuracy. This then worked also in the Lopez-Ros morph.
 * 3.) Finally, the period-closing Lopez-Ros value had to be found. One only has to solve a linear equation
 * with coefficients depending on the minimal surface. For reasons that I could not find this worked only
 * for symmetry parameters 2,3,4,5 and then developed visible inaccuracies. These went away after repeating
 * the determination of the Lopez-Ros parameter three times. This indicates that the linear equation has
 * inaccurately defined coefficients, but I do not see a reason for that. 
 * 4.) For esthetic reasons the big holes had to be closed. For the top and bottom symmetry points this
 * worked satisfactorily. The middle symmetry point still has gaps for exponent > 5.    H. Karcher
 */
public class ChenGackstatter extends WeierstrassMinimalSurface {
	
	private IntegerParam exponent = new IntegerParam("vmm.surface.parametric.ChenGackstatter.exponent",3);
	private RealParamAnimateable lrp = new RealParamAnimateable("vmm.surface.parametric.ChenGackstatter.lrp",1,0.7,1.1);
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public ChenGackstatter() {
		super();
		addParameter(lrp);
		lrp.setMinimumValueForInput(0.1);
		addParameter(exponent);
		exponent.setMinimumValueForInput(2);
		exponent.setMaximumValueForInput(11);
		setDefaultOrientation(View3DLit.NORMAL_ORIENTATION);
		setDefaultViewpoint(new Vector3D(38.4, 30.7, -11.2));
		setDefaultViewUp(new Vector3D(-0.125, -0.2, -0.97));
		setDefaultWindow(-7.5,7.5,-6,6);
		uPatchCount.setValueAndDefault(18);
		vPatchCount.setValueAndDefault(12);
		umin.reset(-0.9); 
		umax.reset(1.72);   // umax determines the size of the Enneper wings.
		umax.setMinimumValueForInput(0.2);
		vmin.reset(-0.9995);
		vmax.reset(0.9995);	
		removeParameter(umin);
		removeParameter(vmin);
		removeParameter(vmax);
		iFirstInHelper = false;
		iBeginMiddleInHelper = true;
		needsPeriodClosed = true;
		wantsToSeeDomain = false;
		if (wantsToSeeDomain)		 {setDefaultViewUp(new Vector3D(0,0,1));
        						  	  setDefaultViewpoint(new Vector3D(0,0,40));}
		//multipleCopyOptions = new int[] {2,3};  // indicates that we can show twice the usual number of copies of fundamntal piece
		canShowConjugateSurface = false;
	}
	public View getDefaultView() {
		WMSView view = new WMSView();
		view.setGridSpacing(6);
		float c0 = (float)0.34;
		view.getLightSettings().setLight0(new Color(c0,c0,c0));
		view.getLightSettings().getDirectionalLight2().setItsColor(new Color((float)0.5,(float)1.0,(float)0.0));
		view.getLightSettings().getDirectionalLight2().setItsDirection (new Vector3D(0.66,-0.34,-0.66));
		view.getLightSettings().getDirectionalLight3().setItsDirection (new Vector3D(0.57,0.2,-0.8)); 
		view.getLightSettings ().setSpecularExponent(55);
		view.getLightSettings().setSpecularRatio( (float)0.5 );
		return view;
	}

	protected int Ex,iP,um,vm;
	double amp; // for domainGrid
	protected double r1,LRP;
	//private Complex qP;
	
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		     super.parameterChanged(param, oldValue, newValue);
		     AFP = afp.getValue();
		     if (param != afp)   needsValueArray = true;
		     if (param == exponent) 
		     	{ needsPeriodClosed = true;  LRP = 1.0;
		     	if (param != umax) umax.reset(Math.max(0.9, 1.9 -(exponent.getValue()-2.0)*0.18));
		     	super.parameterChanged(param, oldValue, newValue);
		     	}
	}
	
	protected void createData() {
        super.createData();     // helperArray created
        data.discardGridTransforms();
        GridTransformMatrix[] trList = new GridTransformMatrix[4*Ex];
				trList[0] = new GridTransformMatrix();
		
		if ((!inAssociateMorph)&&(!wantsToSeeDomain)&&flag0 ) {
			trList[1] = new GridTransformMatrix().scale(1,-1,1);
			data.addGridTransform(trList[1]);  
        for (int e = 2; e < 2*Ex; e++){
    			trList[e] = new GridTransformMatrix(trList[e-2]).rotateZ(360.0/(double)Ex);
    			data.addGridTransform(trList[e]);
        		}
        if (flag0)
        for (int e = 0; e < 2*Ex; e++){
			trList[e+2*Ex] = new GridTransformMatrix(trList[e]).scale(1,-1,-1).rotateZ(180.0/(double)Ex).reverse();
			data.addGridTransform(trList[e+2*Ex]);
    			}
		}
	}
	
	/** 
	 * Override the default Cartesian Grid. It is critical that the domain grid is adapted to
	 * the minimal surface.
	 */
	protected Complex domainGrid(double u, double v){
		// return pos*sqrt(1+exp(u+iv)), with concentration of lines
		Complex z,w;
		//if ((Math.abs(u-umin.getValue())<amp)&&(Math.abs(v-vmin.getValue())<amp))
		//	{u=u+512*amp;  v=v+1024*amp;} // was used to identify initial grid point in image	
			double r = myRad(u,Ex);
			double a = Math.PI*Math.sin(v*Math.PI/2);
			z = new Complex(r*Math.cos(a) + 1, r*Math.sin(a));
			w = z.squareRootNearer(ONE_C);
			double n = w.r();
			if (n < amp) {
				//w.re = Math.max(w.re, amp - Math.abs(z.im));
				w.assignTimes(Math.max(1.0,(amp-n)/n));
			}
		    return w;
	}

	/**
     * Last index of grid point before symmetry line switches to straight line.
     */
	protected void p_Index(){
		Complex z;
		for (int i=0; i<ucount; i++){
			z = domainGrid(umin.getValue()+i*du, vmin.getValue());
			if ((z.re > 0.0001)&&(Math.abs(z.im)<0.0001)) iP=i;
		}
		iP = iP + (int)Math.floor((Ex-3.0)*(Ex-2.0)/2.0);
	}
	/**
	 * Strictly monotone function with critical points of order e at (-1,0) and (0,1).
	 * Used to adjust the parameter lines in domainGrid.
	 */
	protected static double myRad(double u, int e){
		//return Math.log(2 + monotonPow( -1.0 + monotonPow(u+1.0, e),e))/Math.log(2);
		if (u <= -1) return 0.0;
		else if (u <= 0.0) return (1 + monotonPow( -1.0 + monotonPow(u+1.0, (int)Math.floor(2+e/2.0)),e));
		else return 1.0 + monotonPow(u,e)*((double)e)/(e - 1.0);
	}
	
// ************************** Weierstrass Data *********************************** //	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		// return LRP/u^(Ex-1),  u^(-Ex) = (1/z - z)
		Complex aux;
		double denom= z.re*z.re+z.im*z.im;
		Complex w = new Complex(z.re/denom -z.re, -z.im*(1.0/denom +1));// w = 1/z - z
		if (z.im > 0 )
			{aux = (w.logNearer(ZERO_C)).times(1.0 - 1.0/(double)Ex);}
		else
			aux = (w.logNearer(IP__C)).times(1.0 - 1.0/(double)Ex);
		w = aux.exponential();  
		w.assignTimes(LRP);
        return w;		
	    }
	
	protected Complex hPrime(Complex z) {
		// return r1. z generalizes the Weierstrass-p-function
		return new Complex(r1,0);
		}

	
	protected double closingLopezRos(){
		int off = ucount-1-iP; // last point on the straight line
		int mid = (int)Math.min(40,ucount-6); // not very far out on the unbroken symmetry line
		double ro,ro1,constLeft,factRight;
		// This needs only three points from the helperArray
		// Use the last point on the straight line and the middle point on the unbroken symmetry line
		// Method: zero on straight line  equals  zero on intersection of symmetry planes
		//         zero = x - y*ctg(); The points are over the negative x-axis and the negative y-axis
		//+ (cs + ro^2*ds)*ts - (as + ro^2*bs) = + (cl + ro^2*dl)*tl - (al + ro^2*bl)
		double as = +helperArray[mid][vm].y.re;
		double bs = -helperArray[mid][vm].x.re;
		double cs = -helperArray[mid][vm].y.im;
		double ds = -helperArray[mid][vm].x.im;
		double ts = Math.cos(Math.PI/(1.0*Ex))/Math.sin(Math.PI/(1.0*Ex));
		{ // The second version is sometimes numerically better
			double al = +helperArray[iP+off][vcount-1].y.re;
			double bl = -helperArray[iP+off][vcount-1].x.re;
			double cl = -helperArray[iP+off][vcount-1].y.im;
			double dl = -helperArray[iP+off][vcount-1].x.im;
			double tl = Math.cos(1.0*Math.PI/(2.0*Ex))/Math.sin(1.0*Math.PI/(2.0*Ex)); 
			constLeft = +cs*ts - as - cl*tl + al;
			factRight = +dl*tl - bl - ds*ts + bs; 
			ro = Math.min(Math.sqrt(constLeft/factRight), Math.sqrt((cl*tl-al)/(bl-dl*tl)));
		} {
			double al = +helperArray[iP+off][0].y.re;
			double bl = -helperArray[iP+off][0].x.re;
			double cl = -helperArray[iP+off][0].y.im;
			double dl = -helperArray[iP+off][0].x.im;
			double tl = Math.cos(3.0*Math.PI/(2.0*Ex))/Math.sin(3.0*Math.PI/(2.0*Ex)); 
			constLeft = +cs*ts - as - cl*tl + al;
			factRight = +dl*tl - bl - ds*ts + bs; 
			ro1= Math.min(Math.sqrt(constLeft/factRight), Math.sqrt((cl*tl-al)/(bl-dl*tl)));
		}
		//System.out.println(constLeft); System.out.println(factRight);
		ro = Math.min(ro,ro1);  // ro, the minimum of 4 computations, starts being too large at Ex = 5;
		                        // The following does the previous computation twice again.
		for (int i=1; i < 3; i++){
		LRP = ro;
		createHelperArray();
		double al = +helperArray[iP+off][0].y.re;
		double bl = -helperArray[iP+off][0].x.re;
		double cl = -helperArray[iP+off][0].y.im;
		double dl = -helperArray[iP+off][0].x.im;
		double tl = Math.cos(3.0*Math.PI/(2.0*Ex))/Math.sin(3.0*Math.PI/(2.0*Ex)); 
		ro= ro*Math.sqrt((cl*tl-al)/(bl-dl*tl));
		}
		LRP = ro*lrp.getValue();
		return ro;
	}
	
	protected void doClosingJob(){
		LRPclosed = closingLopezRos(); // Sets LRP = LRPclosed*lrp.getValue();
		createHelperArray();
		// System.out.println(LRPclosed); //"adjusted Lopez-Ros"
	}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		if (needsPeriodClosed) 
			LRP = 1.0;
		else 
			LRP  = LRPclosed*lrp.getValue(); // Lopez-Ros Parameter
		Ex = exponent.getValue();
		r1 = 1.0*Math.sqrt(Math.sqrt(2.0/((double) Ex)));        // size control
		amp = 1.0/((double)(Ex*Ex*Ex*Ex*Ex*Ex*Ex*Ex*Ex*Ex)); // detour parameter in domainGrid, for central saddle
		p_Index(); // needs dv
		// System.out.println(iP);
		//iBeginMiddleInHelper = true;
		um = (int) Math.floor((ucount-1)/2.0);
		vm = (int) Math.floor((vcount-1)/2.0);
	}
	
	/**
	 * We want to center the surface already at the helper Level. We cannot use the symmetry
	 * lines of the surface and therefore need to integrate towards the image of P.
	 */
	protected ComplexVector3D getCenter(){
	   	// return new ComplexVector3D(helperArray[um][vm]); // default
		if (inAssociateMorph)	 {  // Keeps the midle fixed
        		return new ComplexVector3D(helperArray[um][vm]);
        }
		else {
			ComplexVector3D minMaxMin2 = helperToMinimal(helperArray[18][0]);
			ComplexVector3D minMaxMin1 = helperToMinimal(helperArray[30][0]);
			ComplexVector3D minMaxMid1 = helperToMinimal(helperArray[12][vm]);
			ComplexVector3D minMaxMid2 = helperToMinimal(helperArray[um][vm]);
			ComplexVector3D zeroLevel  = helperToMinimal(helperArray[ucount-1][0]);
			Complex z1 = new Complex(minMaxMid1.x.re, minMaxMid1.y.re);
			Complex z2 = new Complex(minMaxMid2.x.re, minMaxMid2.y.re);
			Complex w1 = new Complex(minMaxMin1.x.re, minMaxMin1.y.re);
			Complex w2 = new Complex(minMaxMin2.x.re, minMaxMin2.y.re);
			Complex cs = intersectLines(z1,z2,w1,w2);
			return minimalToHelper(new ComplexVector3D(cs,cs.times(I__C), zeroLevel.z));
		}
	}
	
	/**
	 * Override  surfacePoint  to close the hole around the center saddle.
	 */
	public Vector3D surfacePoint(double u, double v) {
		ComplexVector3D eW,auxW;
		int i = (int) Math.floor(0.25 + (u-umin.getValue())/du );
		int j = (int) Math.floor(0.25 + (v-vmin.getValue())/dv );
			//Complex zInitial = domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv);
			//Complex z = domainGrid(u,v);		
		if ((lrp.getValue()==1)&&(!inAssociateMorph)&&(!wantsToSeeDomain)&&((i>iP-Ex+2)&&(i<iP+Ex))&&((j==vcount-1)||(j==0)))
			eW = new ComplexVector3D(ComplexVector3D.ORIGIN);
		else 
		{
			auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));	
			eW = helperToMinimal(auxW);
	
			if ((lrp.getValue()==1)&&(!inAssociateMorph)&&(!wantsToSeeDomain))
			   if (i==0)  eW.assign(ZERO_C, ZERO_C, eW.z);
		}
		
		if (wantsToSeeDomain)     return new Vector3D(eW.z.re, eW.z.im, 0);
		else if (AFP==0)  return eW.re(); 
		else      return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));    
	}
	  
}
