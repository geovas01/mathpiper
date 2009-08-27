/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import vmm.core.Complex;

/**
 * A 3D vector with components of type Complex named x, y, and z.
 */
public class ComplexVector3D {


	/**
	 * The first component of the vector.
	 */
	public Complex x;

	/**
	 * The second component of the vector.
	 */
	public Complex y;

	/**
	 * The third component of the vector.
	 */
	public Complex z;


	final static Complex ZERO_C = new Complex(0,0);
	final static Complex  ONE_C = new Complex(1,0);

	/**
	 * Construct a vector with all three components initially equal to zero.
	 */
	public ComplexVector3D() {
		this.x = new Complex(0,0);
		this.y = new Complex(0,0);
		this.z = new Complex(0,0);
	}

	/**
	 * The origin, (ZERO_C,ZERO_C,ZERO_C).
	 */
	public static final ComplexVector3D ORIGIN = new ComplexVector3D();

	/**
	 * The unit vector in the x direction, (ONE_C,ZERO_C,ZERO_C).
	 */
	public static final ComplexVector3D UNIT_X = new ComplexVector3D(ONE_C,ZERO_C,ZERO_C);

	/**
	 * The unit vector in the y direction, (ZERO_C,ONE_C,ZERO_C).
	 */
	public static final ComplexVector3D UNIT_Y = new ComplexVector3D(ZERO_C,ONE_C,ZERO_C);

	/**
	 * The uint vector in the z direction, (ZERO_C,ZERO_C,ONE_C).
	 */
	public static final ComplexVector3D UNIT_Z = new ComplexVector3D(ZERO_C,ZERO_C,ONE_C);

	/**
	 * Construct a vector that initially has coordinates (x,y,z).
	 */
	public ComplexVector3D(Complex x, Complex y, Complex z) {
		if ((x==null)||(y==null)||(z==null)){
			this.x = new Complex(0,0); this.y = new Complex(0,0); this.z = new Complex(0,0);
		}
		else {
		this.x = new Complex(x);
		this.y = new Complex(y);
		this.z = new Complex(z);
		}
	}
	public void assign(Complex a, Complex b, Complex c) {
		this.x.assign(a); this.y.assign(b); this.z.assign(c);
	}

	/**
	 * Construct a vector that initially has real part rea and imaginary part ima.
	 */
	public ComplexVector3D(Vector3D rea, Vector3D ima) {
		if ((rea == null)&&(ima == null)){
			this.x = new Complex(0,0); this.y = new Complex(0,0); this.z = new Complex(0,0);
		}
		else if (ima == null){
			this.x = new Complex(rea.x,0); this.y = new Complex(rea.y,0); this.z = new Complex(rea.z,0);
		}
		else if (rea == null){
			this.x = new Complex(0,ima.x); this.y = new Complex(0,ima.y); this.z = new Complex(0,ima.z);
		}
		else{
		this.x = new Complex(rea.x, ima.x);
		this.y = new Complex(rea.y, ima.y);
		this.z = new Complex(rea.z, ima.z);
		}
	}

	/**
	 * Construct a vector that is initially a copy of a specified vector.
	 * @param v the constructed vector is (v.x, v.y, v.z), or is (0,0,0) if v is null.
	 */
	public ComplexVector3D(ComplexVector3D v) {
		if (v == null){
			x = new Complex(0,0); y = new Complex(0,0); z = new Complex(0,0);}
		else if ((v.x==null)||(v.y==null)||(v.z==null)){
			x = new Complex(0,0); y = new Complex(0,0); z = new Complex(0,0);}
		else {
			x = new Complex(v.x);
			y = new Complex(v.y);
			z = new Complex(v.z);
		}
	}
	public void assign(ComplexVector3D v) {
		this.x.assign(v.x); this.y.assign(v.y); this.z.assign(v.z);
	}

	/**
	 * Returns true if <code>obj</code> is a non-null object whose class in ComplexVector3D and such that
	 * the three corrdinates of the vector <code>obj</code> are the same as the coordinates of this
	 * vector.
	 */
	public boolean equals(Object obj) {
		try {
			ComplexVector3D v = (ComplexVector3D)obj;
			if (x == null && v.x != null)
				return false;
			if (y == null && v.y != null)
				return false;
			if (z == null && v.z != null)
				return false;
			return x.equals(v.x) && y.equals(v.y) && z.equals(v.z);
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * The real part of a ComplexVector.
	 */
	public Vector3D re() {
		Vector3D v = new Vector3D();
		v.x = this.x.re;
		v.y = this.y.re;
		v.z = this.z.re;
		return v;
	}

	/**
	 * The imaginary part of a ComplexVector.
	 */
	public Vector3D im() {
		Vector3D v = new Vector3D();
		v.x = this.x.im;
		v.y = this.y.im;
		v.z = this.z.im;
		return v;
	}

	/**
	 * Returns the length of this vector, computed as <code>Math.sqrt(x*x+y*y+z*z)</code>
	 */
	public double norm() {
		return Math.sqrt(this.re().norm2() + this.im().norm2());
	}

	/**
	 * Divides each component of the vector by the norm of the vector, giving a vector that has length 1.
	 * However, if the vector is zero, or if any of its components are infinite or undefined, then the
	 * result of calling this method is to set all the components of the vector to Double.NaN.
	 * This method modifies the vector.
	 */
	public void normalize() {
		double lengthRecip = 1.0 / Math.sqrt(this.norm());
		if (	Double.isNaN(lengthRecip) || Double.isInfinite(lengthRecip) ){
			x.re = y.re = z.re = Double.NaN;
			x.im = y.im = z.im = Double.NaN;
		}
		else {
			this.x = this.x.times(lengthRecip);
			this.y = this.y.times(lengthRecip);
			this.z = this.z.times(lengthRecip);
		}
	}

	/**
	 * Multiplies each component of this vector by -1.  This method modifies the vector.
	 */
	public void negate() {
		x = x.times(-1.0);
		y = y.times(-1.0);
		z = z.times(-1.0);
	}

	/**
	 * Returns the vector sum of this vector and v.  A new vector object is constructed to contain
	 * the result; neither input vector is modified.
	 * @param v a non-null vector
	 */
	public ComplexVector3D plus(ComplexVector3D v) {
		return new ComplexVector3D(x.plus(v.x), y.plus(v.y), z.plus(v.z));
	}
	public void assignPlus(ComplexVector3D v) {
		x.assignPlus(v.x);   y.assignPlus(v.y);   z.assignPlus(v.z);
	}

	/**
	 * Returns the vector difference of this vector and v.  A new vector object is constructed to contain
	 * the result; neither input vector is modified.
	 * @param v a non-null vector
	 */
	public ComplexVector3D minus(ComplexVector3D v) {
		return new ComplexVector3D(x.minus(v.x), y.minus(v.y), z.minus(v.z));
	}
	public void assignMinus(ComplexVector3D v) {
		x.assignMinus(v.x);   y.assignMinus(v.y);   z.assignMinus(v.z);
	}

	/**
	 * Returns the scalar product of this vector times d.  A new vector object is constructed to contain
	 * the result; the vector is not modified.
	 * @param d the scalar that is to be multiplied times this vector
	 */
	public ComplexVector3D times(Complex d) {
		return new ComplexVector3D(x.times(d), y.times(d), z.times(d));
	}

	public ComplexVector3D times(double r) {
		return new ComplexVector3D(x.times(r), y.times(r), z.times(r));
	}
	
	/**
	 * This vector is scalar multiplied by d. No new object is constructed.
	 */
	public void assignTimes(Complex d) {
		x.assignTimes(d); y.assignTimes(d); z.assignTimes(d);
	}

	public void assignTimes(double r){
		x.assignTimes(r); y.assignTimes(r); z.assignTimes(r);
	}
	/*
    Define the following function where the integration is wanted

	   public static ComplexVector3D ComplexVectorFunction(Complex argument){
		    ComplexVector3D V = new ComplexVector3D(argument.power(8), argument.power(7),argument.power(6));
		   return V;
	   } 

	   public static ComplexVector3D ComplexVectorOneStepIntegrator(Complex zInitial, Complex zFinal){
			 // Integrates polynomials of order up to 7 correctly
		     ComplexVector3D w = new ComplexVector3D();
			 final double weight1 = 32.0/90.0; 
			 final double weight2 = 9.0/90.0; 
			 final double weight3 = 49.0/90.0;
			 final double sqrtOf3Over28= Math.sqrt(3.0/28.0);
			 Complex dz = new Complex(zFinal.minus(zInitial));
			 Complex zMiddle = new Complex(zInitial.plus(zFinal).times(0.5));
			 Complex zGaussLeft = new Complex(zMiddle.minus(dz.times(sqrtOf3Over28)));
			 Complex zGaussRight = new Complex(zMiddle.plus(dz.times(sqrtOf3Over28)));
			 w = ComplexVectorFunction(zMiddle).times(weight1);
			 w =  w.plus( (ComplexVectorFunction(zInitial).plus(ComplexVectorFunction(zFinal))).times(0.5).times(weight2) );
			 w =  w.plus( (ComplexVectorFunction(zGaussLeft).plus(ComplexVectorFunction(zGaussRight))).times(0.5).times(weight3) );

			return w.times(dz);
		}

		public static ComplexVector3D ComplexVectorIntegrator(Complex zInitial, Complex zFinal,int numSteps){
			ComplexVector3D w = new ComplexVector3D();
			Complex dz = new Complex(zFinal.minus(zInitial));
			double newNumSteps  = Math.floor( (1+dz.r())*numSteps );
			dz = dz.times(1.0/newNumSteps);
			  for (int j=0; j < (int)newNumSteps; j++){
				  Complex z = new Complex( (zInitial.times(1.0*(newNumSteps - j)).plus(zFinal.times(1.0*j)) ).times(1.0/newNumSteps) );
				  w = w.plus( ComplexVectorOneStepIntegrator(z, z.plus(dz)) );
			  }

			  return w;
		}
	 */
	/* System.out.println(w.x.re);
		System.out.println(w.x.im);
		System.out.println(w.y.re);
		System.out.println(w.y.im);
		System.out.println(w.z.re);
		System.out.println(w.z.im); */	

}


