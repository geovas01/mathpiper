/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

import vmm.core.Complex;

/**
 * A complex-valued variable.  A ComplexVariable can be added to a {@link Parser}
 * so that it can be used in the defintion of functions and expressions
 * that are parsed by that parser.  It is possible to change the value
 * of a variable with the {@link #setVal(Complex)} or {@link #setVal(double, double)} method, which will
 * affect the value of any expressions and functions in which it is used.
 */

public class ComplexVariable {
	
	private String name;
	private double re, im;
	
	/**
	 * Create a ComplexVariable with a specified name and initial value.
	 * @param name the name of the variable.  This can be null.  If the variable is
	 * to  be added to a Parser, then the name must be non-null and should be 
	 * a legal identifier (starting with a letter and containing only letters,
	 * digits and the underscore character).
	 * @param re the real part of the initial value of the variable
	 * @param im the imaginary part of the initial value of the variable
	 */
	public ComplexVariable(String name, double re, double im) {
		this.name = name;
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Create a ComplexVariable with a specified name and initial value zero.
	 */
	public ComplexVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Create a ComplexVariable with a specified initial value and with name initially
	 * equal to null.
	 * @param re the real part of the initial value of the variable
	 * @param im the imaginary part of the initial value of the variable
	 */
	public ComplexVariable(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Gets the real part of the value of this variable.
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Gets the imaginary part of the value of this variable.
	 */
	public double getIm() {
		return im;
	}
	
	/**
	 * Returns the value of this variable as an object of type Complex.
	 */
	public Complex getVal() {
		return new Complex(re,im);
	}
	
	/**
	 * Gets the current value of this variable by storing the value in a pre-existing
	 * object of type Complex.
	 * @param c the object where the value will be stored.  This must be non-null.
	 */
	public void getVal(Complex c) {
		c.re =re;
		c.im = im;
	}
	
	/**
	 * Sets the value of this variable
	 * @param re the new value of the real part
	 * @param im the new value of the imaginary part
	 */
	public void setVal(double re, double im) {
		this.re = im;
		this.im = im;
	}

	/**
	 * Set the value of this variable.
	 * @param c the new value of the variable.  A null value is treated as zero.
	 */
	public void setVal(Complex c) {
		if (c == null) {
			this.re = 0;
			this.im = 0;
		}
		else {
			this.re = c.re;
			this.im = c.im;
		}
	}
	
	/**
	 * Get the name of this variable.  This can be null.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of this variable.  The name should not be changed after
	 * the variable has been added to a Parser; even if the name is changed, the
	 * Parser will continue to use it under the name it had at the time it was
	 * added to the parser.
	 */
	public void setName(String name) {
		this.name = name;
	}
	


}
