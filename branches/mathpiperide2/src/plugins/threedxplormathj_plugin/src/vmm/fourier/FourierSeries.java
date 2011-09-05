/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.fourier;

import vmm.core.Complex;

public class FourierSeries {
	
	    static final int SAMPLINGARRAYSIZE =  1025;

	   
	   private static Complex[] SamplingArray;
	   private static Complex[] SamplingArrayTransform;
	   
	   public static boolean IsAPowerOf2(int N) {
		   if ((N == 4) || (N == 8) || (N == 16)  || (N == 32) || (N == 64) || (N ==128) || (N ==256) || (N == 512) )
			   return true;
		   else 
			   return false;
	   }
	   

	   private static void InitializeSamplingArrays() {
		   if (SamplingArray == null ) {
			   SamplingArray = new Complex[SAMPLINGARRAYSIZE];
		      for (int i = 0; i < SAMPLINGARRAYSIZE; i++)
			   SamplingArray[i] = new Complex();
		     }
		   if (SamplingArrayTransform == null ) {
	          SamplingArrayTransform = new Complex[SAMPLINGARRAYSIZE];
		      for (int i = 0; i < SAMPLINGARRAYSIZE; i++)
			   SamplingArrayTransform[i] = new Complex();
		   }
	   }
	   
	   private static Complex eToThe2piIalpha(double alpha) {
	       return (Complex.I_C.times(2.0*Math.PI*alpha)).exponential();
	  }
	   
	   public static void  SFT(Complex [] f,Complex [] G, int N) {   //The Discrete Fourier Transform of f is returned in G
		    if (G == null){
		    	G = new Complex[N];
		    	for (int k = 0; k < N; k++)
		    	  G[k] = new Complex();
		    }
		    for (int k = 0; k < N; k++) {
		          G[k] = Complex.ZERO_C;
		          for (int j = 0; j < N; j++) { 
		        	  double alpha = (double)(-j*k)/N;
		               G[k] = G[k].plus(f[j].times(eToThe2piIalpha(alpha)));   // was  eToThe2piIalpha(-j*k/N))  !!!
		               G[k] = G[k].times(1/N);
		          }
		       }
		    }
	   
	    
	   public static void ISFT(Complex [] G, Complex [] f, int N)  { //The Inverse Discrete Fourier Transform of G is returned in f 
		   if (f == null){
		    	f = new Complex[N];
		    	for (int k = 0; k < N; k++)
		    	  f[k] = new Complex();
		    }
	       for (int k = 0; k < N; k++) {
	            f[k] = Complex.ZERO_C;
	            for (int j = 0; j < N; j++) { 
	            	  double alpha = (double)(-j*k)/N;
	                 f[k] = f[k].plus(G[j].times(eToThe2piIalpha(alpha)));      // was  eToThe2piIalpha(-j*k/N))  !!!
	         }
	     }
	  }
	     
	   public static void  DFT(Complex[] f, Complex[] G, int N) {  //The Discrete Fourier Transform of f is returned in G
		   if (G == null){
		    	G = new Complex[N];
		    	for (int k = 0; k < N; k++)
		    	  G[k] = new Complex();
		    }
		   if ((N == 4) || (N ==8) || (N == 16)  || (N == 32) || (N == 64) || (N ==128) || (N ==256) || (N == 512) ) {
	            for (int i = 0; i < N; i++) 
	                 G[i] = f[i];
	             FastFourier.fft(G,N);
	           }
	        else
	           SFT(f,G, N);
	      }
	      
	      
	   public static void IDFT(Complex [] G, Complex [] f, int N) {  //The Inverse Discrete Fourier Transform of G is returned in f
		   if (f == null){
		    	f = new Complex[N];
		    	for (int k = 0; k < N; k++)
		    	  f[k] = new Complex();
		    }
		   if ((N == 4) || (N ==8) || (N == 16)  || (N == 32) || (N == 64) || (N ==128) || (N ==256) || (N == 512) ) {
	            for (int i = 0; i < N; i++)
	                 f[i] = G[i];
	            FastFourier.ifft(f,N);
	           }
	        else
	           ISFT(G,f,N); 
	     }
	     
	      
	   public static void  DFTinPlace(Complex [] f, int N) {  //Replaces f by its Discrete Fourier Transform
		   InitializeSamplingArrays();
		   if ((N == 4) || (N ==8) || (N == 16)  || (N == 32) || (N == 64) || (N ==128) || (N ==256) || (N == 512) ) 
	        FastFourier.fft(f,N);
	      else {
	           SFT(f,SamplingArrayTransform, N);
	            for (int i = 0; i < N; i++)
	                f[i] = SamplingArrayTransform[i];
	         }
	      }
	      
	      
	   public static void IDFTinPlace(Complex [] G, int N) {  //Replaces G by its Inverse Discrete Fourier Transform 
		   InitializeSamplingArrays();
		   if ((N == 4) || (N ==8) || (N == 16)  || (N == 32) || (N == 64) || (N ==128) || (N ==256) || (N == 512) ) 
	         FastFourier.ifft(G,N);
	         else {
	           ISFT(G,SamplingArray, N);
	           for (int i = 0; i < N; i++)
	                 G[i] = SamplingArray[i];
	         }
	      }
	      
	   public static void  DST(double [] f, double [] G, int N) {   //The Discrete Sine Transform of f returned in G
		     InitializeSamplingArrays();
	         for (int i = 0; i < N; i++)
	             f[2*N - i] = - f[i]; 
	         for (int i = 0; i < 2*N; i++) {
	              SamplingArray[i].re = f[i];
	              SamplingArray[i].im = 0;
	          }
	         DFTinPlace(SamplingArray, 2 * N);
	         for (int i = 0; i < N; i++)
	           G[i] = 0.5 * SamplingArray[i].im;
	     }
	   
	   public static void IDST(double [] G, double []  f, int N) { //The Inverse Discrete Sine Transform of G returned in f
	       double TwoOverN = 2.0/N;  // was 2/N  !!!  
	       DST(f,G,N);
	       for (int i = 0; i < N; i++)
	          G[i] = TwoOverN*G[i];
	   }
	      
	   public static void DSTinPlace(double [] f, int N)  {   //The Discrete Sine Transform
		   InitializeSamplingArrays();
	        for (int i = 0; i < N; i++){
	           f[2*N - i] = - f[i];
	        }
	           
	        for (int i = 1; i <= 2*N; i++) {
	              SamplingArray[i].re = f[i];
	              SamplingArray[i].im = 0;
	           }
	         DFTinPlace(SamplingArray, 2*N);
	         for (int i = 1; i <= N; i++) {
	        	 f[i] = 0.5 * SamplingArray[i].im;
	         }
	   }
	     
	   public static void IDSTinPlace(double []  f, int N) {   //The Inverse Discrete Sine Transform}
	      double TwoOverN = 2.0/N;   // was 2/N  !!!
	      DSTinPlace(f,N);
	        for (int i = 0; i < N; i++)
	          f[i] = TwoOverN * f[i];
	     } 

}
