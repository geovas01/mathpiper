/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import vmm.core.Complex;
import vmm.core.IntegerParam;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core3D.ComplexVector3D;
import vmm.core3D.GridTransformMatrix;
import vmm.core3D.Vector3D;
import vmm.surface.SurfaceView;

public class Scherk_Weierstrass extends WeierstrassMinimalSurface {
	
	private IntegerParam exponent = new IntegerParam("vmm.surface.parametric.Scherk_Weierstrass.MainEx",2);
	private RealParamAnimateable aa = new RealParamAnimateable("vmm.surface.parametric.Scherk_Weierstrass.aa",0.0,0,0.9);
	//private IntegerParam pieces = new IntegerParam("vmm.surface.parametric.Scherk_Weierstrass.pieces",1);
	//ComplexVector3D[][] integrationGrid;
	//boolean needsIntegrationGrid = true;
	
	public Scherk_Weierstrass() {
		super();
		setFramesForMorphing(12);
		afp.reset(0,0,0);
		addParameter(aa);
		addParameter(exponent);
		//setDefaultViewpoint(new Vector3D(-44,9,25))
		setDefaultViewpoint(new Vector3D(-7,17,47));
		setDefaultWindow(-4,4,-3,3);
			uPatchCount.setValueAndDefault(12);
			vPatchCount.setValueAndDefault(12);
			//SurfaceView.setGridSpacing(6);
			umin.reset(-3.5); 
			umax.reset(0.0);
			vmin.reset(-Math.PI/2);
			vmax.reset(Math.PI/2);
		wantsToSeeDomain = false;
		if (wantsToSeeDomain) {setDefaultViewUp(new Vector3D(0,0,1));
		                       setDefaultViewpoint(new Vector3D(0,0,40));}
		removeParameter(umax);
		removeParameter(vmax);
		removeParameter(vmin);
		umin.setMaximumValueForInput(-0.05);
		iFirstInHelper = false;
		multipleCopyOptions = new int[] {2};  // indicates that we can show twice the usual number of copies of fundamntal piece
		canShowConjugateSurface = true;
	}


	private int ex,j0;
	private double AA,r1,rex,re_e,mob;
	//private Complex q1;

	protected void createData() {
        super.createData();
        data.discardGridTransforms();
        
        if ((!inAssociateMorph)&&(flag0||flag05)&&(!wantsToSeeDomain)  ) {
         GridTransformMatrix[] trList = new GridTransformMatrix[ex*8];
        	trList[0] = new GridTransformMatrix();
        	if (flag0){
        	trList[1] = new GridTransformMatrix().scale(1,-1,1); // reflect in x-z-plane
        data.addGridTransform(trList[1]);
        	}  else if (flag05)  {
        		trList[1] = new GridTransformMatrix().scale(-1,1,-1).reverse(); // rotate around y-axis
                data.addGridTransform(trList[1]);
        	}
        for (int e = 1; e < ex; e++){
        		trList[2*e] = new GridTransformMatrix(trList[2*e-2]).rotateZ(360.0/(double)ex);
        		data.addGridTransform(trList[2*e]);
        		trList[2*e+1] = new GridTransformMatrix(trList[2*e-1]).rotateZ(360.0/(double)ex);
        		data.addGridTransform(trList[2*e+1]);
        }
        if (flag0)
        for (int e = 0; e < 2*ex; e++){
        	  trList[2*ex +e] = new GridTransformMatrix(trList[e]).scale(1,1,-1);
        	  data.addGridTransform(trList[2*ex+e]);
        }
        if (flag05)
            for (int e = 0; e < 2*ex; e++){
            	  trList[2*ex +e] = new GridTransformMatrix(trList[e]).scale(-1,-1,1).reverse().translate(halfPeriod.im().times(2.0));
            	  data.addGridTransform(trList[2*ex+e]);
        }
        
        if (getNumberOfPieces()==2) {
        		if (flag0)
        			for (int e = 0; e < 4*ex; e++){
        			  //data.addGridTransform(trList[e].translate(halfPeriod.re().times(-1.0))); 
        	        	  trList[4*ex +e] = new GridTransformMatrix(trList[e]);
        	        	  data.addGridTransform(trList[4*ex+e].translate(halfPeriod.re().times(2.0)));
        	        }
        		if (flag05){
        			if  ((ex/2.0 - Math.floor(ex/2.0))==0)
			for (int e = 0; e < 4*ex; e++){
	        	  trList[4*ex +e] = new GridTransformMatrix(trList[e]).rotateZ(180);
	        	  data.addGridTransform(trList[4*ex+e].translate(halfPeriod.im().times(-2.0)));
	        }  else  { 
	        	for (int e = 0; e < 4*ex; e = e+2){
	        		trList[4*ex +e] = new GridTransformMatrix(trList[e+2*ex]).rotateZ(720.0/(double)ex);
	        		data.addGridTransform(trList[4*ex+e]);
	        		trList[4*ex +e+1] = new GridTransformMatrix(trList[e+1+2*ex]).rotateZ(-720.0/(double)ex);
	        		data.addGridTransform(trList[4*ex+e+1]);
	        	} // for
	        }} // flag05
        } // if pieces
        } // if 
	}
	
	/**
	 * This grid is precisely adapted to the symmetry lines and ends of the surface,
	 * overrides default Cartesian grid
	 */
	protected Complex domainGrid(double u, double v){
		// return ( (i*(-z+1)/(z+1)).mobius1_1(mob) )^(1/ex), z = exp(u+i*v)
		Complex z,aux,w;
			double r = Math.exp(u);
			z = new Complex(r*Math.cos(v), r*Math.sin(v));
			w = new Complex(-z.re + 1, -z.im);
			aux = new Complex(z.im, -z.re -1);
		    w.assignDivide(aux);  // Polar centers at +i,-i
		    w = w.mobius1_1(mob); // Polar centers at ck+i*sk, ck-i*sk
		    if (w.r() < 0.000001) z.re = -7.0;
		    else  z=w.logNearer(I1_C);
		    z.re = Math.max(z.re,-7.0);
		    z.assignTimes(1.0/(double)ex);
		    z = z.exponential();// Polar centers at the punctures of the Weierstrass data
		    return z;
	}
	// Index of grid point closest to zero and on real line
	protected void zeroIndex(){
		double aux;
		double test = 10;
		Complex z;
		for (int j = 0; j < vcount ; j++){
			z = domainGrid(umax.getValue(),vmin.getValue() +j*dv);
			aux = z.r();
			if ((aux <= test)&&(Math.abs(z.im)< 0.00001)) {
				test = aux; j0=j;
			}
		}
		//if (domainGrid(umax.getValue(),vmin.getValue() +j0*dv).re == 0) j0 = j0+1;
	}
	
	/**
	 * The following two functions are the Weierstrass data that
	 * define this surface. It is best shown on the above domainGrid.
	 */
	protected Complex gauss(Complex z)  {
		  return new Complex(z.integerPower(ex-1));
	}
	
	protected Complex hPrime(Complex z) {
		// return r1*z^(2ex-1)/(z^2ex +1 -2ck)
		Complex aux = z.integerPower(ex);
		Complex w = aux.minus(4*re_e);
		        w.assignTimes(aux);
		        w.re = w.re+1;
		        w = gauss(z).dividedBy(w);
		        w.assignTimes(r1);
		return  w;
	}
	
	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		double ck,sk;
		AA  = Math.sqrt(Math.abs(aa.getValue()));
		ex = exponent.getValue();
			//rex = Math.pow(AA,ex);
			rex = 1.0; for (int e = 1; e < ex+1; e++) {rex = rex*AA;}
			re_e = rex/(rex*rex + 1);
			ck = 2.0*re_e;
			sk = Math.sqrt(1.0-ck*ck);
			mob= Math.sqrt((1.0-sk)/(1.0-ck)/2.0); 
			// mob is the Mobius-parameter so that the polar center at I_C is mapped
			// to ck+i*sk, and then with the ex-root to the poles of hPrime.
			r1 = 1.3*Math.sqrt(ex/2.0)*Math.exp(2.0/ex*Math.log((1-AA*AA*AA*AA))); // controls the size
			//if (flag0)  r1 = r1/Math.sqrt(pieces.getValue());
		zeroIndex(); // needs dv
		System.out.println(j0);
	}
	
	/**
	 * We want to center the surface already at the helper Level. We cannot use the symmetry
	 * lines of the surface and therefore need to integrate towards the image of ZERO_C.
	 * getCenter is called from inside createHelperArray and not in Associate Morph.
	 */
	protected ComplexVector3D getCenter(){
		Complex zj0 = domainGrid(umax.getValue(),vmin.getValue()+j0*dv );
		System.out.println(zj0.re);
		Complex around = new Complex(0.005,0);
		ComplexVector3D gC = new ComplexVector3D(helperArray[ucount-1][j0]);
			// gC = gC.plus(ComplexVectorOneStepIntegrator(zj0, del));
		gC = gC.plus(ComplexVectorIntegrator(zj0, around, ex));
		ComplexVector3D aux = ComplexVectorIntegrator(around, around.times(I_C), ex);
		aux= aux.plus(ComplexVectorIntegrator(around.times(I_C), around.times(-1.0), ex));
		gC = gC.plus(aux.times(0.5));  
		if ( (inAssociateMorph)||(wantsToSeeDomain)	) {  // Keeps the saddle fixed
		}
		else
		    gC.z = new Complex(helperArray[ucount-1][0].z); 
		return gC;
	}
	
	public void computeHalfPeriod(){
	    halfPeriod = new ComplexVector3D(helperArray[ucount-1][0]);
	    ComplexVector3D aux = new ComplexVector3D(helperArray[ucount-1][vcount-1]);
        halfPeriod = new ComplexVector3D( halfPeriod.y.minus(halfPeriod.x),halfPeriod.y.plus(halfPeriod.x).times(I_C),halfPeriod.z.times(2.0) );
        aux = new ComplexVector3D(aux.y.minus(aux.x),aux.y.plus(aux.x).times(I_C),aux.z.times(2.0));
        halfPeriod.x.re = 0;  halfPeriod.y.re = 0; halfPeriod.z.re = (halfPeriod.z.re - aux.z.re)/2.0;
        /*  halfPeriodII = new ComplexVector3D(helperArray[um][vm].times(2.0));
        halfPeriodII.assign( ZERO_C, halfPeriodII.y.plus(halfPeriodII.x).times(I_C), ZERO_C);
            //System.out.println(halfPeriodII.z); */
	} 
	
	/**
	 * Override to close the hole around the image of ZERO_C.
	 */
	public Vector3D surfacePoint(double u, double v) {
		int i = (int) Math.floor(0.25 + (u-umin.getValue())/du );
		int j = (int) Math.floor(0.25 + (v-vmin.getValue())/dv );
			//Complex zInitial = domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv);
			//Complex z = domainGrid(u,v);
		ComplexVector3D auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));
		ComplexVector3D eW = new ComplexVector3D(auxW.y.minus(auxW.x), (auxW.y.plus(auxW.x)).times(I_C), auxW.z ) ;
		if ((i==ucount-1)&&(j==j0))
			eW = new ComplexVector3D(ZERO_C,ZERO_C,eW.z);
		if (AFP==0)
			if (wantsToSeeDomain)     return new Vector3D(eW.z.re, eW.z.im, 0);
			else                      return eW.re();
		else
			return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));    
	}
	   
}
