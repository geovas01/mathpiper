/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface.parametric;

import java.util.HashMap;
import java.util.Map;

import vmm.core.Complex;
import vmm.core.IntegerParam;
import vmm.core.Parameter;
import vmm.core.RealParamAnimateable;
import vmm.core3D.ComplexVector3D;
import vmm.core3D.Vector3D;

public class WavyEnneper_Weierstrass extends WeierstrassMinimalSurface {
	
	private IntegerParam exponent = new IntegerParam("vmm.surface.parametric.WavyEnneper_Weierstrass.MainEx",2);
	private IntegerParam higherExponent = new IntegerParam("vmm.surface.parametric.WavyEnneper_Weierstrass.HighP",20);
	private RealParamAnimateable bb = new RealParamAnimateable("vmm.surface.parametric.WavyEnneper_Weierstrass.CoeffA",0.4,0.4,0.4);
	private RealParamAnimateable cc = new RealParamAnimateable("vmm.surface.parametric.WavyEnneper_Weierstrass.CoeffP",Math.PI, 0.0,2*Math.PI);
	
	//ComplexVector3D[][] helperArray;
	//boolean needsValueArray = true;
	private boolean wantsCartesianGrid = false;
	
	public WavyEnneper_Weierstrass() {
		super();
		afp.reset(0,0,0);
		addParameter(cc);
		addParameter(bb);
		addParameter(higherExponent);
		addParameter(exponent);
		uPatchCount.setValueAndDefault(12);
		vPatchCount.setValueAndDefault(12);
		umin.reset(-0.95); 
		umax.reset(0.95);
		vmin.reset(-0.95);
		vmax.reset(0.95);
		setDefaultViewpoint(new Vector3D(0,0,20));
		setDefaultWindow(-2,2,-2,2);
		if (!wantsCartesianGrid) {
			uPatchCount.setValueAndDefault(8);
			vPatchCount.setValueAndDefault(20);
			setDefaultWindow(-1.4,1.4,-1.4,1.4);
			umin.reset(-2); 
			umax.reset(0.0);
			vmin.reset(0.0);
			vmax.reset(2*Math.PI);	
		}
		canShowConjugateSurface = true;
	}

	private double ee,ff;
	private Complex b;
	
	protected Complex domainGrid(double u, double v){
		Complex z;
		if (wantsCartesianGrid)
		    z = new Complex(u,v);
		else{
			double r = Math.exp(u);
			z = new Complex(r*Math.cos(v), r*Math.sin(v));
		}
		return z;
	}
	
	/**
	 * The next two functions are the Weierstrass Data that
	 * defines this surface.
	 */
	protected Complex gauss(Complex z)  {
		//return z.power(ee).plus(z.power(ff).times(b));
		Complex aux1 = new Complex();
		Complex aux2 = new Complex();
		Complex aux3 = new Complex();
		aux1.assignPow(z,ee);
		aux2.assignPow(z,ff);
		aux2.assignTimes(b);
		aux3.assignPlus(aux1, aux2);
		return aux3;
	}
	
	protected Complex hPrime(Complex z) {
		return gauss(z);
	}
	
	protected Complex gaussTimesHPrime(Complex z){
		Complex w = gauss(z);
		return w.times(w);
	}
	protected Complex gaussInverseTimesHPrime(Complex z) {
		return new Complex(1,0);
	}

	protected void redoConstants(){
		// global variables independent of the points, hence thread safe
		super.redoConstants();
		ee = 1.0*exponent.getValue();
		ff = 1.0*higherExponent.getValue();
		b = new Complex(bb.getValue()*Math.cos(cc.getValue()), bb.getValue()*Math.sin(cc.getValue()) );
	}
	
	protected ComplexVector3D getCenter(){
		ComplexVector3D gC = new ComplexVector3D(helperArray[0][(int)Math.floor(vcount/2)]);
		return (gC.plus(helperArray[0][0])).times(0.5);
	}

}
/* ============================= Help the Memory ==============================
 
 	public Vector3D surfacePoint(double u, double v) {
		
		//Parameter2D uv = new Parameter2D(u,v);
		
		//ComplexVector3D eW = hashInverse.get(uv);		
		//if( eW==null) {
		
			int i = (int) Math.floor(0.25 + (u-umin.getValue())/du );
			int j = (int) Math.floor(0.25 + (v-vmin.getValue())/dv );
			//Complex zInitial = domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv);
			//Complex z = domainGrid(u,v);
			ComplexVector3D auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorOneStepIntegrator(domainGrid(umin.getValue() + i*du, vmin.getValue() + j*dv), domainGrid(u,v)) ));
			//ComplexVector3D auxW = new ComplexVector3D(helperArray[i][j].plus( ComplexVectorIntegrator(zInitial, z, 1) ));
			ComplexVector3D eW = new ComplexVector3D(auxW.y.minus(auxW.x), (auxW.y.plus(auxW.x)).times(I_C), auxW.z ) ;
			
			//hashInverse.put( uv, eW );
		 // } end if
		if (AFP==0)
		    return eW.re();
		else
			return (eW.re().times(Math.cos(AFP))).plus((eW.im().times(Math.sin(AFP))));
	}  
	   
} 
 
   This is a suggestion to use only 40 new objects in a long computation.
   Most likely not thread-safe
   
private Complex [] result = new Complex[] {
	new Complex(), new Complex(), new Complex(), new Complex(), new Complex(),
	new Complex(), new Complex(), new Complex(), new Complex(), new Complex(),
	new Complex(), new Complex(), new Complex(), new Complex(), new Complex(),
	new Complex(), new Complex(), new Complex(), new Complex(), new Complex(),
	new Complex(), new Complex(), new Complex(), new Complex(), new Complex(),
	new Complex(), new Complex(), new Complex(), new Complex(), new Complex(),
	new Complex(), new Complex(), new Complex(), new Complex(), new Complex(),
	new Complex(), new Complex(), new Complex(), new Complex(), new Complex(),
};
int resultCounter=0; 
 
protected Complex gauss(Complex z)  {
//return z.power(ee).plus(z.power(ff).times(b));
Complex aux1 = new Complex();
Complex aux2 = new Complex();
aux1.assignPow(z,ee);
aux2.assignPow(z,ff);
aux2.assignTimes(b);
resultCounter++;     // attempt to minimize newly created objects
result[resultCounter%40].assignPlus(aux1, aux2);
return new Complex(result[resultCounter%40]);
//return aux2.plus(aux1);
}*/
/*	This was part of an attempt to use the HashMap
private static class Parameter2D {
	double u,v;
	Parameter2D( double u, double v ) {
		this.u=u;
		this.v=v;
	}
	
	public long hashValue() {
		return (long)((8192*u + v)*8192); 
	}
	
	public boolean equals( Object other ) {
		if( other == null || ! (other instanceof Parameter2D) )
			return false;
		
		Parameter2D otherParameter = (Parameter2D)other;
		
		return u==otherParameter.u && v == otherParameter.v;	
	}
}

//Map<Parameter2D,ComplexVector3D> hashInverse = new HashMap<Parameter2D,ComplexVector3D>();
Using the hashInverse was no visible improvement, but may be risky for numerical differentiation.
*/
