/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.pseudospherical;

import vmm.core.Complex;


/**
 * A 2D vector with components of type Complex named x, y.
 */
public class ComplexVector2D  { 
	/**
	 * The first component of the vector.
	 */
	public Complex x;

	/**
	 * The second component of the vector.
	 */

	public Complex y;

	/**
	 * Construct a vector with all components initially equal to zero.
	 */
	public ComplexVector2D() {   
           this.x = new Complex(Complex.ZERO_C);  
           this.y = new Complex(Complex.ZERO_C);
	}


	/**
	 * Construct a vector that initially has coordinates (x,y).  
	 */
	public ComplexVector2D(Complex x, Complex y) {
		this.x = new Complex(x);
		this.y = new Complex(y);
	}

	/**
	 * Construct a vector that is initially a copy of a specified vector.
	 * @param v the constructed vector is (v.x, v.y), or is (ZERO_C,ZERO_C) if v is null.   
	 */
	public ComplexVector2D(ComplexVector2D v) {
		if (v == null)
			x = y = new Complex(0,0);   
		else {
			x = new Complex(v.x);
			y = new Complex(v.y);
		}
	}

	/**
	 * Returns true <code>obj</code> is a non-null object whose class in Vector2D and such that
	 * both coordinates of the vector <code>obj</code> are the same as the coordinates of this
	 * vector.
	 */
	public boolean equals(Object obj) {
		if (obj == null || ! (obj.getClass().equals(ComplexVector2D.class)))
			return false;
		ComplexVector2D v = (ComplexVector2D)obj;
		return v.x.equals(x) && v.y.equals(y);
	}

	/**
	 * Multiplies each component of this vector by -1.  This method modifies the vector.
	 */
	public void negate() {       
		x = x.times(-1.0);
		y = y.times(-1.0);
	}


	/**
	 * Returns the vector sum of this vector and v.  A new vector object is constructed to contain
	 * the result; neither input vector is modified.
	 * @param v a non-null vector
	 */
	public ComplexVector2D plus(ComplexVector2D v) {
		return new ComplexVector2D(x.plus(v.x), y.plus(v.y));
	}

	/**
	 * Returns the vector difference of this vector and v.  A new vector object is constructed to contain
	 * the result; neither input vector is modified.
	 * @param v a non-null vector
	 */
	public ComplexVector2D minus(ComplexVector2D v) {
		return new ComplexVector2D(x.minus(v.x), y.minus(v.y));
	}

    /**
	 * Returns the Hermitian inner-prodct of this vector and v. 
     */
	public Complex dot(ComplexVector2D v)   {
		Complex c1 = new Complex(x.times((v.x).conj()));
		Complex c2 = new Complex(y.times((v.y).conj()));		
                return c1.plus(c2);
	}

	/**
	 * Returns the scalar product of this vector times d.  A new vector object is constructed to contain
	 * the result; the vector is not modified.
	 * @param d the scalar that is to be multiplied times this vector
	 */

	public ComplexVector2D times(Complex d) {
		return new ComplexVector2D(d.times(x), d.times(y));
	}

	/**
	 * Returns the norm square of the vector. The norm is calculated by sqrt(|x|^2+|y|^2)
	 */
	public double norm2()  {
		return this.dot(this).re;
	}

	/**
	 * Returns a string representation of this vector of the form "(x,y)" where x, y are 
	 * the numerical components of the vector.
	 */
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
