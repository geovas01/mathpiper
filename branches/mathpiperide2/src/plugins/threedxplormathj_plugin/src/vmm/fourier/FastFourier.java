/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.fourier;

import vmm.core.Complex;

public class FastFourier {
	static final int MAXFFTSIZE = 512;

	static Complex twid[]; 
	static Complex Cadd(Complex a,Complex b) {return a.plus(b);}
	static Complex Csub(Complex a,Complex b) {return a.minus(b);}
    static Complex Cmul(Complex a, Complex b) {return a.times(b);}
	static Complex Cdiv(Complex a,Complex b) {return a.dividedBy(b);}
	static Complex RCmul(double a, Complex b) {return b.times(a);}
	static Complex Cexp(Complex a) {return a.exponential();}
	static Complex Comp(double a,double b){Complex c = Complex.ZERO_C; c.assign(a,b);return c;}	
	static Complex cexp(double x,double y) { return RCmul(Math.exp(x),Comp(Math.cos(y),Math.sin(y)));}
	static Complex Cconjg(Complex c) { return c.conj();}
	static double Cabs(Complex c) {return c.r();}
	static Complex Cexpi(double y) {return Comp(Math.cos(y),Math.sin(y));}

	private static void SetupTwid() {
		if (twid == null) {
			 twid = new Complex[MAXFFTSIZE];
	         double h = 2*Math.PI/MAXFFTSIZE;
	         for (int j = 0; j< MAXFFTSIZE;j++) {
	    	         twid[j] = new Complex();
	                 twid[j].re = Math.cos(j*h);
	                 twid[j].im = Math.sin(j*h);
	      }
	   }
	}
	
	static void fft( Complex[] c, int J) {
		int i,j,bit;
		int numtrans,sizetrans,halfsize;
		int level,in,sub;
		int iminus,iplus;
		Complex twiddle;
		Complex but1,but2;
		Complex temp;
		SetupTwid();
		/* bit reversal */
		i=0;
		for (j=1; j< J; j++) { /* count with j */
		/* binary add 1 to i in mirror */
		bit=J/2;
		while ( i >= bit ) {  /* until you encounter 0, change 1 to 0 */
		i=i-bit;
		bit=bit/2;}
		i=i+bit;		/* then change 0 to 1 */
		if (i<j) {
		temp=c[i]; c[i]=c[j]; c[j]=temp;  /* swap once for each pair */
		}
	   }
		numtrans=J;
		sizetrans=1;
		while (numtrans > 1)  {
		numtrans=numtrans/2;     /* at each level, do half as many */
		halfsize=sizetrans;
		sizetrans=sizetrans*2;   /* subtransforms of twice the size   */
		for (in=0; in<halfsize; in++) { /* index in each subtransform */
		twiddle=twid[in*numtrans];	/* sharing common twiddle */
		for (sub=0; sub< numtrans; sub++) { /* index of subtransform */
		iplus=sub*sizetrans+in;     /* indices for butterfly */
		iminus=iplus+halfsize;
		but1=c[iplus];
		but2=Cmul(twiddle,c[iminus]);  /* lower one gets twiddled */
		c[iplus]=Cadd(but1,but2);      /* butterfly */
		c[iminus]=Csub(but1,but2);
		}
	   }
	  }
	 }

	static void ifft ( Complex[] c, int J) {   /* inverse fourier transform */
		int k;
		double oJ;
		SetupTwid();
		oJ=1.0/J;	
		for (k=0; k<J; k++) c[k]=Cconjg(c[k]);
		fft(c,J);
		for (k=0; k<J; k++) c[k]=RCmul(oJ,Cconjg(c[k]));
	 }
		
    public static Complex[] ffTransform( Complex[] c, int J){
	   Complex[] ffTransform_c = new Complex [c.length];
	   for (int i = 0; i< c.length; i++) 
		   ffTransform_c[i] = new Complex(c[i]);
	   fft(ffTransform_c,J);
	   return ffTransform_c;
    }
 
    public static Complex[] iffTransform( Complex[] c, int J){
	  Complex[] inverseTransform_c = new Complex [c.length];
	  for (int i = 0; i< c.length; i++) 
	     inverseTransform_c[i] = new Complex(c[i]);
	  ifft(inverseTransform_c,J);
	  return inverseTransform_c;
   }
		
    
}
