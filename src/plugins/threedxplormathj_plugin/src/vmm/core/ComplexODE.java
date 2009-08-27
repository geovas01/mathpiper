/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;


public class ComplexODE {

/**
 * input format ODEstep(zInitial, zFinal, initialVal, approxInitDeriv, numSubdivision)
 * returns the value at zFinal
 * The ODE is f'(z)^2 = Polynomial(z) = a0 + a1 z + a2 z^2 + a3 z^3 + a4 z^4
 * The program needs to know the branch of sqrt(f'(zInitial)^2), it will compute
 * it as (f'(zInitial)^2).sqrtNearer(approxInitDeriv)
 * The polynomial coefficients are supplied by the constructor ComplexODE(a0, a1, a2, a3, a4)
 * 
 * At the end is a high order integrator, since high order methods are good for analytic functions.
 * The integrand is 1/sqrt(Polynomial(z)).
 */	
	Complex [] polyCoeff;
	int polyDegree;
	
	public ComplexODE(Complex a0,Complex a1,Complex a2,Complex a3, Complex a4) {
		this.polyCoeff = new Complex[] {a0, a1, a2, a3, a4};		
		this.polyDegree = polyCoeff.length-1;
	}
	
	public Complex [] setPoly(Complex [] a){
		polyCoeff = a;
		return polyCoeff;
	}
	
	public Complex [] setPoly(Complex a0,Complex a1,Complex a2,Complex a3, Complex a4){
		polyCoeff = new Complex[] {a0, a1, a2, a3, a4};
		return polyCoeff;
	}


	 /** All routines below depend on the polynomial above.
	  * They are written to avoid creation of unused objects.
	  * Complex wv is always used to contain the return value.
	 */
	Complex wv = new Complex();
	public Complex valueOfPoly(Complex z){
		//Complex w = new Complex(polyCoeff[4]);
		wv.assign(polyCoeff[4]);
		for (int i = 0; i < 4; i++){
			wv.assignTimesPlus(z,polyCoeff[3-i]);
		}
		return wv;
	}
	
	public Complex firstDerivOfPoly(Complex z){
		//Complex w = (polyCoeff[4].times(4.0));
		wv.assignTimes(polyCoeff[4],4.0);
		//w = w.times(z).plus(polyCoeff[3].times(3.0));
		wv.assignTimes_PlusTimes(z,polyCoeff[3],3.0); // (w*z)+Coeff*3
		//w = w.times(z).plus(polyCoeff[2].times(2.0));
		wv.assignTimes_PlusTimes(z,polyCoeff[2],2.0);
		//w = w.times(z).plus(polyCoeff[1]);
		wv.assignTimesPlus(z,polyCoeff[1]);
		return wv;
	}
	
	public Complex secondDerivOfPoly(Complex z){
		//Complex w = (polyCoeff[4].times(12.0));
		wv.assignTimes(polyCoeff[4],12.0);
		wv.assignTimes_PlusTimes(z,polyCoeff[3],6.0);
		//w = w.times(z).plus(polyCoeff[3].times(6.0));
		wv.assignTimes_PlusTimes(z,polyCoeff[2],2.0);
		//w = w.times(z).plus(polyCoeff[2].times(2.0));
		return wv;
	}
	
	public Complex thirdDerivOfPoly(Complex z){
		//Complex w = (polyCoeff[4].times(24.0));
		wv.assignTimes(polyCoeff[4],24.0);
		wv.assignTimes_PlusTimes(z,polyCoeff[3],6.0);
		//w = w.times(z).plus(polyCoeff[3].times(6.0));
		return wv;
	}
	
	/**
	 * A first quadratic Taylorstep to the middle, then average the second derivative at these two points,
	 * complete with a second quadratic step using the average second derivative. A 4th order method.
	 * (f')^2 = sqrt(Polynomial(f)).
	 */
	public Complex[] ODEstep2_2(Complex zInitial, Complex zFinal, Complex initialVal, Complex approxInitDeriv, int numSubdivision) {
		Complex w = new Complex(initialVal);
		Complex aux = new Complex(zFinal.minus(zInitial));
		double newSub = Math.floor(1.0+aux.r())*numSubdivision;
		Complex dz= new Complex( (zFinal.minus(zInitial)).times(1.0/newSub) );
		Complex pVal, d1Val, d2Val;
		Complex midVal, d2MidVal, d2Averag;
		
		for (int i = 0; i < newSub ; i++) 
		  {
			pVal = (valueOfPoly(w));
			d1Val= (pVal.squareRootNearer(approxInitDeriv));
			d2Val= (firstDerivOfPoly(w).times(0.5));
			aux = (d2Val.times(dz.power(2)).times(0.125) );// 0.5*f''(zInitial)*(dz/2)^2
			midVal= (w.plus( dz.times(d1Val.times(0.5)) ).plus(aux));
			d2MidVal=  (firstDerivOfPoly(midVal).times(0.5)); // f'' = poly'(f)/2
			d2Averag=  ( (d2MidVal.times(2).plus(d2Val) ).times(1.0/3) );
			w = w.plus(dz.times(d1Val)).plus(d2Averag.times(dz.power(2)).times(0.5));
			zInitial = zInitial.plus(dz);
			approxInitDeriv = d1Val.plus( dz.times(d2MidVal) );
		  }
		Complex[] val = new Complex[2];
		val[0] = w;
		val[1] = approxInitDeriv;
		return val;
	}
	
	/**
	 * A first 4th order Taylorstep to one third, then average the 4th derivative at these two points,
	 * complete with a second 4th order step using the average 4th derivative. A 6th order method.
	 * (f')^2 = sqrt(Polynomial(f)).
	 * The use of .assign in this routine reduced the created Complex objects from 5 000 000 to 111 000.
	 */
	Complex w, app, aux;
	double newSub = 0.0;
	Complex dz;
	Complex pVal=new Complex(), d1Val=new Complex(), d2Val=new Complex(), d3Val=new Complex(), d4Val=new Complex();
	Complex wThird = new Complex(), d4Third = new Complex();
	
	public Complex[] ODEstep4(Complex zInitial, Complex zFinal, Complex initialVal, Complex approxInitDeriv, int numSubdivision) {
		w = new Complex(initialVal);
		app = new Complex(approxInitDeriv);
		aux = zFinal.minus(zInitial);
		newSub = Math.floor(1.0+aux.r())*numSubdivision;
		dz = zFinal.minus(zInitial);                      dz.assignTimes(1.0/newSub);

		for (int i = 0; i < newSub ; i++) 
		  {
			pVal.assign(valueOfPoly(w));  
			d1Val.assign(pVal.squareRootNearer(app));
			d2Val.assignTimes(firstDerivOfPoly(w),0.5);
			d3Val.assignTimes(secondDerivOfPoly(w),0.5);  d3Val.assignTimes(d1Val);
			d4Val.assignTimes(thirdDerivOfPoly(w),0.5);   d4Val.assignTimes(pVal);
			d4Val.assign_PlusTimes(d2Val,d1Val);
			aux.assignTimes(d4Val,1.0/72.0);              aux.assignTimes(dz);  // aux = d4Val/72*dz
			aux.assign_PlusTimes(d3Val,1.0/18.0);         aux.assignTimes(dz);
			aux.assign_PlusTimes(d2Val,0.5/3.0);          aux.assignTimesPlus(dz,d1Val);
			aux.assignTimesTimes(dz, 1.0/3.0);
			wThird.assignPlus(w,aux);                     //wThird = w.plus(aux);    // saves 60 000 creations
			//wThird = w.plus( (aux.plus(d2Val.times(0.5/3.0)).times(dz).plus(d1Val)).times(dz.times(1.0/3.0)) );
			d4Third.assign(valueOfPoly(wThird));          d4Third.assignTimesTimes(thirdDerivOfPoly(wThird), 0.5);
			aux.assignTimes(secondDerivOfPoly(wThird),0.25);
			d4Third.assign_PlusTimes(aux,firstDerivOfPoly(wThird));
			//d4Third = d4Third.plus( secondDerivOfPoly(wThird).times(0.25).times(firstDerivOfPoly(wThird)) );
			d4Third.assignTimes(0.6);                    d4Third.assign_PlusTimes(d4Val,0.4);   // Average
			d4Third.assignTimesTimes(dz, 1.0/24.0);      d4Third.assign_PlusTimes(d3Val,1.0/6.0);
			aux.assignTimes(d4Third, dz);                aux.assign_PlusTimes(d2Val,0.5); 
			aux.assignTimes(dz);                         aux.assignPlusTimes(d1Val, dz);
			//aux = ( (d4Third.times(1.0/24.0).times(dz)).plus(d3Val.times(1.0/6.0)) ).times(dz);
			//w = w.plus( ((aux.plus(d2Val.times(0.5)).times(dz).plus(d1Val)).times(dz)) );
			w.assignPlus(aux);
			d3Val.assignTimesTimes(dz, 1.0/3.0);         d3Val.assignPlusTimes(d2Val,dz);
			//approxInitDeriv = d1Val.plus( dz.times(d2Val.plus(dz.times(1.0/3.0).times(d3Val))) );
			app.assignPlus(d1Val,d3Val);     //approxInitDeriv = d1Val.plus(d3Val); // save 60 000 creations
			// zInitial = zInitial.plus(dz); //is not needed because the ODE f'^2 = P(f) does not depend on z 
		  }
		Complex[] val = new Complex[2];
		val[0] = w;     // outputValue
		val[1] = app;   // outputApproximateDerivative
		return val;
	}
	
	public Complex RootOfPolynomial(Complex f, Complex approxInitDeriv){
		Complex w = valueOfPoly(f);
		return w.squareRootNearer(approxInitDeriv);
	}
	
	public Complex RootOfPolynomialInverse(Complex f, Complex approxInitDerivInv){
		Complex w = valueOfPoly(f);      
		w.assignInvert();
		return w.squareRootNearer(approxInitDerivInv);
	}

	
	 /** 
	  * The following integrates 1/sqrt(Poly) and continues the value of the root.
	  * The integrater is exact on polynomials of degree 7.
	  * The indefinite integral is the inverse function of the elliptic function
	  * defined by (f')^2 = Poly(f). This is used to compute periods of f.
	  */
	public  Complex[] ComplexMultiStepIntegrator(Complex zInitial, Complex zFinal, Complex valInitial, Complex approxInitDeriv,double subs){
	     Complex uPrime = RootOfPolynomialInverse(zInitial,approxInitDeriv.invert());
	     //uPrime.assignInvert();
	     Complex w = new Complex();
		 final double weight1 = 32.0/90.0; 
		 final double weight2 = 9.0/90.0; 
		 final double weight3 = 49.0/90.0;
		 final double sqrtOf3Over28= Math.sqrt(3.0/28.0);
		 Complex dz = zFinal.minus(zInitial);           dz.assignTimes(1/subs);
		 Complex zIni = new Complex(zInitial);
		 Complex zFin = new Complex(zFinal);
		 Complex zMiddle = new Complex();
		 Complex zGaussLeft = new Complex();
		 Complex zGaussRight = new Complex();
		 
		 for (int j =0; j < subs; j++){
		 zFin.assignPlus(zIni,dz);
		 zMiddle.assignPlus(zIni,zFin);                zMiddle.assignTimes(0.5);
		 zGaussLeft.assignTimes(dz,-sqrtOf3Over28);    zGaussLeft.assignPlus(zMiddle);
		 zGaussRight.assignTimes(dz,sqrtOf3Over28);    zGaussRight.assignPlus(zMiddle);
		 
		 uPrime = RootOfPolynomialInverse(zIni,uPrime);
		 w.assign_PlusTimes(uPrime,0.5*weight2);
		 
		 uPrime = RootOfPolynomialInverse(zGaussLeft,uPrime);
		 w.assign_PlusTimes(uPrime,0.5*weight3);
		 
		 uPrime = RootOfPolynomialInverse(zMiddle,uPrime);
		 w.assign_PlusTimes(uPrime,weight1);
		 
		 uPrime = RootOfPolynomialInverse(zGaussRight,uPrime);
		 w.assign_PlusTimes(uPrime,0.5*weight3);
		 
		 uPrime = RootOfPolynomialInverse(zFin,uPrime);
		 w.assign_PlusTimes(uPrime,0.5*weight2);
		 zIni.assign(zFin);
		 }
		 uPrime.assignInvert();
		return new Complex[]{uPrime, w.times(dz).plus(valInitial)};
	}
}
