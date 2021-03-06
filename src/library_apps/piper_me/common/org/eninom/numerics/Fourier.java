/*
(C) Oliver Glier 2008

Eninom-Lib is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.
 
Eninom-Lib is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package org.eninom.numerics;

import org.eninom.func.*;

//!FFT Routines
/*<literate>*/
/**
 * Routines for FFT. Array lengths must be powers of 2.
 */
public final class Fourier {
  /**
   * This class has no instances.
   */
  private Fourier() {
   
      }
//array lengths must be powers of 2
//convolution that runs in O(n^2) time. Use this function
//for small arrays or in order to test fastConvolute(...)
  public static double[] convolute(double[] F1, double F2[])
  {
    int k1 = F1.length-1;
    int k2 = F2.length-1;
    int n = k1+k2+1;
    double[] F = new double[n];
    for (int i=0; i < n; i++)
      F[i] = 0.0;
      for (int i = 0; i <= k1; i++)
        for (int j = 0; j <= k2; j++)
          F[i+j] += F1[i]*F2[j];
    return F;
  }

//fast convolution that runs in O(n log n) time using FFT.
//The current implementation copies a lot of arrays and might
//be a bit inefficient but therefore easier to debug.
  public static double[] fastConvolute(double[] F1, double F2[])
  {
    int k1 = F1.length;
    int k2 = F2.length;
    if (k1*k2 <= 512*512)
      return convolute(F1,F2);
    int n = k1+k2-1;
    double[] F = new double[n];
    int K = 1; while(K < n) K <<= 1;
    double aRe[] = new double[K];
    double aIm[] = new double[K];
    double bRe[] = new double[K];
    double bIm[] = new double[K];
    for(int i = 0; i < K; i++)
      aIm[i] = bIm[i] = 0.0;
    for (int i = 0; i < k1; i++)
      aRe[i] = F1[i];
    for (int i = k1; i < K; i++)
      aRe[i] = 0.0;
    for (int i = 0; i < k2; i++)
      bRe[i] = F2[i];
    for (int i = k2; i < K; i++)
      bRe[i] = 0.0;
    computeFFT(aRe,aIm,aRe,aIm);
    computeFFT(bRe,bIm,bRe,bIm);
    for (int i=0; i < K; i++)
    {
      double _aRe = aRe[i];
      double _aIm = aIm[i];
      double _bRe = bRe[i];
      double _bIm = bIm[i];
      aRe[i] = _aRe*_bRe - _aIm*_bIm;
      aIm[i] = _aRe*_bIm + _aIm*_bRe;
    };
    computeIFFT(aRe,aIm,aRe,aIm);
    for(int i = 0; i<n; i++)
      F[i] = aRe[i];

    return F;
  }

//several bit and index manipulation routines
  public static int log2(int K)
  {
    int k = 0;
    int N = K;
    int S = 1;
    while (N != 1)
    {
      S <<= 1;
      N >>= 1;
      k += 1;
    }
    if (S != K)
      System.out.println("K is not a power of 2.");
    return k;

  }

  public static int reverseBits(int i, int k)
  {
    int j = 0;
    for (; k > 0; k--)
    { j <<= 1;
      j |= i & 1;
      i >>= 1;
    }
    return j;
  }

  private static void reverseBitsOfIndex(double[] src, double[] dst)
  {
    int K = src.length;
    int k = log2(K);
    boolean tmp = (src == dst);
    if (tmp) dst = new double[K];

    for (int i = 0; i < K; i++)
    {
      int j = reverseBits(i,k);
      dst[i] = src[j];
    }

    if (tmp) for (int i = 0; i<K; i++)
      src[i] = dst[i];
  }

//compute 2^n roots on the circle, the arrays must have a
//size of 2^n
  private static void computeFourierCoeff(double[] wRe, double[] wIm)
  {
    int K = wRe.length;
    if (K != wIm.length)
      System.out.println("wRe and wIm have different length.");
    if (K == 0) return;
    int k = log2(K);
    wRe[0] = 1.0; wIm[0] = 0.0;
    if (K == 1) return;
    wRe[1] = -1.0; wIm[1] = 0.0;
    if (K == 2) return;
    wRe[2] = 0.0; wIm[2] = 1.0;
    wRe[3] = 0.0; wIm[3] = -1.0;
    int j = 4;
    int c = 2;
    for (int r = 2; r < k; r++)
    {
      double pRe = Math.sqrt((1+wRe[c])/2);
      double pIm = wIm[c] / (2*pRe);
      wRe[j] = pRe; wIm[j] = pIm; c = j;
      j++;
      for (int l = 1; l < c; l++)
      {
        wRe[j] = wRe[l]*pRe - wIm[l]*pIm;
        wIm[j] = wRe[l]*pIm + wIm[l]*pRe;
        j++;
      }
    }
  }


//compute FT in O(n log n) time:
  public static void computeFFT(double[] aRe, double[] aIm,
                                double[] bRe, double[] bIm)
  {
    int K = aRe.length;
    if ((bRe.length != K) || (bIm.length != K) || (aIm.length != K))
      System.out.println("Arrays for FFT have different lengths.");

    double[] uRe = new double[K];
    double[] uIm = new double[K];
    for(int i = 0; i < K; i++)
    {  uRe[i] = aRe[i];
       uIm[i] = aIm[i];
    }
    double[] wRe = new double[K];
    double[] wIm = new double[K];
    computeFourierCoeff(wRe,wIm);
    int T = K >> 1;
    int F = 1;
    boolean stop = false;

    while (!stop)
    {
      for (int s = 0; s <  F; s++)
      {
        int s0Offs = s*2*T;
        int s1Offs = s0Offs+T;
        double w_s0Re = wRe[2*s];
        double w_s0Im = wIm[2*s];
        for (int t = 0; t < T; t++)
        {
          int s0t = s0Offs+t;
          int s1t = s1Offs+t;
          double u_s0tRe = uRe[s0t];
          double u_s0tIm = uIm[s0t];
          double u_s1tRe = uRe[s1t];
          double u_s1tIm = uIm[s1t];
          double pRe = u_s1tRe*w_s0Re-u_s1tIm*w_s0Im;
          double pIm = u_s1tRe*w_s0Im+u_s1tIm*w_s0Re;
          uRe[s0t] = u_s0tRe + pRe;
          uIm[s0t] = u_s0tIm + pIm;
          uRe[s1t] = u_s0tRe - pRe;
          uIm[s1t] = u_s0tIm - pIm;
        }
      }
      if (T == 1) stop = true;
      else T >>= 1;
      F <<= 1;
    }
    reverseBitsOfIndex(uRe,bRe);
    reverseBitsOfIndex(uIm,bIm);
  }
  
//compute FT in O(n log n) time:
  public static Cons<double[],double[]> computeFFT(double[] aRe, double[] aIm) {
    int n = aRe.length;
    double[] bRe = new double[n];
    double[] bIm = new double[n];
    computeFFT(aRe,aIm,bRe,bIm);
    return new Cons<double[],double[]>(bRe,bIm);
  }

//compute inverse FT in O(n log n) time using FFT
public static void computeIFFT(double[] aRe, double[] aIm,
                               double[] bRe, double[] bIm)
{
  int K = aRe.length;
  computeFFT(aRe,aIm,bRe,bIm);
  double r = 1/(double)K;
  for (int i = 0; i < K; i++)
  {
    bRe[i] *= r;
    bIm[i] *= r;
  }
  int i = 1;
  int j = K-1;
  while(i<j)
  {
    double tmp = bRe[i]; bRe[i] = bRe[j]; bRe[j] = tmp;
    tmp = bIm[i]; bIm[i] = bIm[j]; bIm[j] = tmp;
    i++; j--;
  }
}

//compute IFFT in O(n log n) time:
public static Cons<double[],double[]> computeIFFT(double[] aRe, double[] aIm) {
  int n = aRe.length;
  double[] bRe = new double[n];
  double[] bIm = new double[n];
  computeIFFT(aRe,aIm,bRe,bIm);
  return new Cons<double[],double[]>(bRe,bIm);
}

}
