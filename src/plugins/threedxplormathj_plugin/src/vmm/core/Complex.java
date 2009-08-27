/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
  package vmm.core;

/**
 * A complex number, with a real and an imaginary part.  (Possibley to be replaced with 
 * a class that has better support for complex arithmetic and functions of a complex variable.)
 */
public class Complex {
	
	public static final Complex ZERO_C = new Complex(0,0);
	
	public static final Complex ONE_C = new Complex(1,0);
	
	public static final Complex I_C = new Complex(0,1);
	

	public double re, im;

	/**
	 * Create a complex number initially equal to zero
	 */
	public Complex() {
	}
	
	/**
	 * Create a complex number initially equal to the real number x.
	 */
	public Complex(double x) {
		re = x;
	}
	
	/**
	 * Create a complex number initially equal to x + iy
	 */
	public Complex(double x, double y) {
		re = x;
		im = y;
	}
	
	/**
	 * Create a new complex number that is initially equal to a given complex number.
	 * @param c The complex number to be copied.  If null, it is treated as zero.
	 */
	public Complex(Complex c) {
		if (c != null) {
			re = c.re;
			im = c.im;
		}
	}
	
	/**
	 * Returns true if obj is equal to this complex number.  If obj is null or is not
	 * of type Complex, the return value is false.
	 */
	public boolean equals(Object obj) {
		try {
			Complex c = (Complex)obj;
			return c.re == re && c.im == im;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Computes the conjugate of a complex number.
	 */
	public Complex conj() {
		return new Complex( re, -im );
	}

	
	/**
	 * Returns the complex number (r*cos(theta)) + i*(r*sin(theta)).
	 */
	public static Complex polar(double r, double theta) {
		return new Complex(r*Math.cos(theta),r*Math.sin(theta));
	}
	
	/**
	 * Returns this + c; c must be non-null.
	 */
	public Complex plus(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}
		
	/**
	 * Returns this - c; c must be non-null.
	 */
	public Complex minus(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}
	
	
	/**
	 * Returns this * c; c must be non-null.
	 */
	public Complex times(Complex c) {
		return new Complex(re*c.re - im*c.im, re*c.im + im*c.re);
	}
	
	/**
	 * Returns this / c; c must be non-null.
	 */
	public Complex dividedBy(Complex c) {
		double denom = c.re*c.re + c.im*c.im;
		if (denom == 0)
			return new Complex(Double.NaN,Double.NaN);
		else
			return new Complex( (re*c.re+im*c.im)/denom, (im*c.re-re*c.im)/denom);
	}
	
	public Complex times(double x) {
		return new Complex(re*x, im*x);
	}
	
	public Complex plus(double x) {
		return new Complex(re+x, im);
	}
	
	public Complex minus(double x) {
		return new Complex(re-x, im);
	}
	
	public Complex dividedBy(double x) {
		return new Complex(re/x, im/x);
	}
	
	/**
	 * z.realLinComb(a,b,c) returns a*z+b*c, double a,b;
	 */
	public Complex realLinComb(double a, double b, Complex c) {
		return new Complex(a*re + b*c.re, a*im + b*c.im);
	}
	
	/**
	 * z.complexLinComb(a,b,c) returns a*z+b*c, Complex a,b;
	 */
	public Complex complexLinComb(Complex a, Complex b, Complex c) {
		return new Complex(this.times(a).plus(c.times(b)));
	}
	
	/**
	 * z.dot(c) returns the scalar product z.re*c.re + z.im*c.im
	 */
	public double dot(Complex c) {
		return (re*c.re + im*c.im);
	}

	
	/**
	 * Returns the absolute value squared of this.
	 * @return real part squared plus imaginary part squared
	 */
	public double abs2() {
		return (re*re + im*im);
	}
	
	/**
	 * Returns the absolute value, "r" in polar coordinates, of this.
	 * @return the square root of (real part squared plus imaginary part squared)
	 */
	public double r() {
		return Math.sqrt(re*re + im*im);
	}
	
	/**
	 * Returns arg(this), the angular polar coordinate of this complex number, in the range -pi to pi.
	 * The return value is simply Math.atan2(imaginary part, real part).
	 */
	public double theta() {
		return Math.atan2(im,re);
	}
	
	/**
	 * Computes the complex exponential function, e^z, where z is this complex number.
	 */
	public Complex exponential() {
		double length = Math.exp(re);
		return new Complex( length*Math.cos(im), length*Math.sin(im) );
	}
	
	/**
	 * Computes the complex reciprocal function, 1/z, where z is this complex number.
	 */
	public Complex inverse() {
		Complex result;
		double length = re*re+im*im;
		if (length == 0)
			result = new Complex(Double.NaN, Double.NaN);
		else
			result = new Complex( re/length, -im/length);
		return result;
	}
	
	public Complex log() {
		double modulus = Math.sqrt(re*re + im*im);
		double arg = Math.atan2(im,re);
		return new Complex(Math.log(modulus), arg);
	}
	
	/**
	 * Computes that complex logarithm of this complex number 
	 * that is nearest to previous.
	 * A test code is in fractals.TestAnalyticContinuation.
	 */
	public Complex logNearer(Complex previous) {
		Complex c = new Complex(this.log());
		double h = (c.im - previous.im)/(2*Math.PI);
		double d = (2*Math.PI)*Math.floor(h+0.5);
		c.im = c.im - d;
		return c;
	}
	
	public double sinh(double x) {
		   return (Math.exp(x) - Math.exp(-x))/2;
	}
	 
	public double cosh(double x) {
		   return (Math.exp(x) + Math.exp(-x))/2;
	}
	
	public Complex sine() {
		double x, y;
		Complex z = new Complex(0.0,0.0);
		x = re;
		y = im;
		z.re = Math.sin(x) * cosh(y);
		z.im = Math.cos(x) * sinh(y);
		return z;
	}
		
	public Complex power(double x) {
		double modulus = Math.sqrt(re*re + im*im);
		double arg = Math.atan2(im,re);
		double log_re = Math.log(modulus);
		double log_im = arg;
		double x_log_re = x * log_re;
		double x_log_im = x * log_im;
		double modulus_ans = Math.exp(x_log_re);
		return new Complex(modulus_ans*Math.cos(x_log_im), modulus_ans*Math.sin(x_log_im));
	}
	/**
	 * Returns new Complex(this^k), for |k|< 9 using multiplication, for larger |k| using exp(k*log(this))
	 */
	public Complex integerPower(int k) {
		double a,b,aux,bux,auy,buy;
		boolean neg = false;
		if (k < 0) {
			k = -k;
			neg = true;
		}
		if (k == 0) { 
			a = 1;
			b = 0;
		}
		else if (k == 1) {
			a = re;
			b = im;
		}
		else if (k == 2) {
			a = re*re-im*im;
			b = 2*re*im;
		}
		else if (k == 3) {
			aux = re*re-im*im;
			bux = 2*re*im;
			a = re*aux - im*bux;
			b = re*bux + im*aux;
		}
		else if (k == 4) {
			aux = re*re-im*im;
			bux = 2*re*im;
			a = aux*aux - bux*bux;
			b = 2*aux*bux;
		}
		else if (k == 5) {
			aux = re*re-im*im;
			bux = 2*re*im;
			auy = aux*aux - bux*bux;
			buy = 2*aux*bux;
			a = re*auy - im*buy;
			b = re*buy + im*auy;
		}
		else if (k == 6) {
			aux = re*re-im*im;
			bux = 2*re*im;
			auy = aux*aux - bux*bux;
			buy = 2*aux*bux;
			a = aux*auy - bux*buy;
			b = aux*buy + bux*auy;
		}
		else if (k == 7) {
			aux = re*re-im*im;
			bux = 2*re*im;
			a = aux*aux - bux*bux;
			b = 2*aux*bux;
			auy = aux*a - bux*b;
			buy = aux*b + bux*a;
			a = auy*re - buy*im;
			b = auy*im + buy*re;
		}
		else if (k == 8) {
			aux = re*re-im*im;
			bux = 2*re*im;
			auy = aux*aux - bux*bux;
			buy = 2*aux*bux;
			a = auy*auy - buy*buy;
			b = 2*auy*buy;
		}
		else if (k == 9) {
			aux = re*re-im*im;
			bux = 2*re*im;
			auy = aux*aux - bux*bux;
			buy = 2*aux*bux;
			aux = auy*auy - buy*buy;
			bux = 2*auy*buy;
			a = re*aux - im*bux;
			b = re*bux + im*aux;
		}
		else if (k == 10) {
			aux = re*re-im*im;
			bux = 2*re*im;
			auy = aux*aux - bux*bux;
			buy = 2*aux*bux;
			aux = re*auy - im*buy;
			bux = re*buy + im*auy;
			a = aux*aux - bux*bux;
			b = 2*aux*bux;
		}
		else {
			double length = r();
			double angle = theta();
			if (angle < 0)
				angle += Math.PI*2;
			length = Math.pow(length,k);
			angle = angle * k;
			a = length*Math.cos(angle);
			b = length*Math.sin(angle);
		}
		if (neg) {
			double denom = a*a + b*b;
			if (denom==0) 
			   {  a = Double.NaN; b = Double.NaN;  }
			else {
			a = a/denom;
			b = -b/denom;  }
		}
		return new Complex(a,b);
	}
	
	/**
	 * Returns a complex k-th root of this complex number.  The root that is returned is 
	 * the one with the smallest positive arg.
	 * (If k is 0, the return value is 1.  If k is negative, the value is 1/integerRoot(-k).)
	 */
	public Complex integerRoot(int k) {
		double a,b;
		boolean neg = false;
		if (k < 0) {
			k = -k;
			neg = true;
		}
		if (k == 0) { 
			a = 1;
			b = 0;
		}
		else if (k == 1) {
			a = re;
			b = im;
		}
		else {
			double length = r();
			double angle = theta();
			if (angle < 0)
				angle += Math.PI*2;
			length = Math.pow(length,1.0/k);
			angle = angle / k;
			a = length*Math.cos(angle);
			b = length*Math.sin(angle);
		}
		if (neg) {
			double denom = a*a + b*b;
			if (denom==0) 
			   {  a = Double.NaN; b = Double.NaN;  }
			else {
			a = a/denom;
			b = -b/denom;
			}
		}
		return new Complex(a,b);
	}
	
	/**
	 * Computes that square root of this complex number 
	 * that is nearer to previous than to minus previous.
	 * A test code is in fractals.TestAnalyticContinuation.
	 */
	public Complex squareRootNearer(Complex previous) {
		Complex c;
		c = this.integerRoot(2);
		if (c.re*previous.re + c.im*previous.im < 0){
			c.re=-c.re;
			c.im=-c.im;
		}
		return new Complex(c.re, c.im);
	}
	
	/**
	 * The Moebius transformation ((1+r)z + r)/(rz + 1+r) has +1,-1 as fixed points.
	 * It maps the real line and the unit circle to itsself
	 */
	public Complex mobius1_1(double r){
	   Complex wd = this.times(r);
	   Complex wn = wd.plus(this);
	           wd.re = wd.re + 1+r;
	           wn.re = wn.re + r;
	      return wn.dividedBy(wd);
	}
	
	public double[] stereographicProjection() {
	   
	    double rsquare,rsquarePlusOne;
	      double [] projPoint = new double[3];
	      rsquare = re * re + im * im;
	      rsquarePlusOne = rsquare + 1;
	      projPoint[0] = (2 * re)/rsquarePlusOne;
	      projPoint[1] = (2 * im)/rsquarePlusOne;
	      projPoint[2] = (rsquare - 1)/rsquarePlusOne;
	      return projPoint;
	    }
	
	public void assign( double x, double y ) {
		re=x;
		im=y;
	}
	

	/**
	 * Returns a string representation of the form a, i, -i, i*b, -i*b, a + i, a - i, a + i*b, or a - i*b.
	 * (Except when the real or imaginary part is undefined or infinite, the form is
	 * "(re) + i*(im)" where NaN, +INF, and -INF are used to indicate undefined and infinite values.)
	 */
	public String toString() {
		if (Double.isNaN(re) || Double.isNaN(im) || Double.isInfinite(re) || Double.isInfinite(im)) {
			String reStr = Double.isNaN(re) ? "NaN" : re == Double.POSITIVE_INFINITY ? "+INF" : re == Double.NEGATIVE_INFINITY ? "-INF" : "" + re;
			String imStr = Double.isNaN(im) ? "NaN" : im == Double.POSITIVE_INFINITY ? "+INF" : im == Double.NEGATIVE_INFINITY ? "-INF" : "" + im;
			return "(" + reStr + ") + i*(" + imStr + ")";
		}
		else if (im == 0)
			return "" + re;
		else if (im < 0) {
			if (re == 0)
				return im == -1 ? "-i" : "-i*" + (-im);
			else
				return re + (im == -1 ? " - i" : " - i*" + (-im));
		}
		else {
			if (re == 0)
				return im == 1 ? "i" : "i*" + im;
			else
				return re + (im == 1 ? " + i" : " + i*" + im);
		}
	}
	
	
	//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
	
	// The following methods were added because they are used in
	// several classes written by Hermann Karcher.
	

	public void assign(Complex c) {
		re = c.re;
		im = c.im;
	}

	public double det(Complex c) {
		return re * c.im - im * c.re;
	}

	public void assignTimes(double d) {
		re *= d;
		im *= d;
	}

	public void assignInvert() {
		double length = re*re+im*im;
		re = re/length;
		im = -im/length;
	}

	public void assignPow(Complex z, double x) {
		double modulus = Math.sqrt(z.re*z.re + z.im*z.im);
		double arg = Math.atan2(z.im,z.re);
		double log_re = Math.log(modulus);
		double log_im = arg;
		double x_log_re = x * log_re;
		double x_log_im = x * log_im;
		double modulus_ans = Math.exp(x_log_re);
		re = modulus_ans*Math.cos(x_log_im);
		im = modulus_ans*Math.sin(x_log_im);
	}

	public void assignPlus(Complex a, Complex b) {
		re = a.re + b.re;
		im = a.im + b.im;
	}

	public void assignPlus(Complex c) {
		re += c.re;
		im += c.im;
	}

	public void assignMinus(Complex c) {
		re += -c.re;
		im += -c.im;
	}

	public void assignTimes(Complex c, double d) {
		re = c.re*d;
		im = c.im*d;
	}

	public void assignTimes(Complex c) {
		double new_re = re*c.re - im*c.im;
		im = re*c.im + im*c.re;
		re = new_re;
	}

	public void assignTimes(Complex a, Complex b) {
		re = a.re*b.re - a.im*b.im;
		im = a.re*b.im + a.im*b.re;
	}

	public void assignDivide(Complex c) {
		double denom = c.re*c.re + c.im*c.im;
		double new_re = (re*c.re+im*c.im)/denom;
		im = (im*c.re-re*c.im)/denom;
		re = new_re;
	}

	public void assignTimesTimes(Complex a, double d) {
		assignTimes(a);
		assignTimes(d);
	}

	public void assignPlusTimes(Complex a, Complex b) {
		assignPlus(a);
		assignTimes(b);
	}

	public void assignTimesPlus(Complex a, Complex b) {
		assignTimes(a);
		assignPlus(b);
	}

	public void assign_PlusTimes(Complex a, Complex b) {
		re += a.re*b.re - a.im*b.im;
		im += a.re*b.im + a.im*b.re;
	}

	public void assign_PlusTimes(Complex a, double d) {
		re += a.re*d;
		im += a.im*d; 
	}

	public void assignTimes_PlusTimes(Complex a, Complex b, double d) {
		assignTimes(a);
		re += b.re*d;
		im += b.im*d;
	}

	public Complex invert() {
		return inverse();
	}
	
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
}
