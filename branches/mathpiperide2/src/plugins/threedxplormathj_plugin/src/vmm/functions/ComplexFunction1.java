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
 * Represents a complex-valued function of one complex argument.  A ComplexFunction1 can
 * only be created by a Parser.
 * @see Parser#parseComplexFunction1(String, String, String)
 */
public class ComplexFunction1 extends ComplexFunction {
	
	/**
	 * Used by a parser to construct an object of type ComplexFunction1.
	 */
	ComplexFunction1(String name, ProgFunction func) {
		super(name,func);
		assert func.getArgType() == Type.COMPLEX && func.getType() == Type.COMPLEX && func.getArgCount() == 1;
	}
	
	/**
	 * Evaluate this function at a specified argument, using an EvalStack that is unique to the current Thread..
	 * @param z a non-null complex number that is passed as an argument to the function
	 */
	synchronized public Complex value(Complex z) {
		return value(z, EvalStack.perThread());
	}
	
	/**
	 * Evaluate this function at a specified argument, using an EvalStack that is unique to the current Thread..
	 * @param z_re the real part of the argument where the function will be evaluated
	 * @param z_im the imaginary part of the argument where the function will be evaluated
	 */
	synchronized public Complex value(double z_re, double z_im) {
		return value(z_re, z_im, EvalStack.perThread());
	}
	
	/**
	 * Evaluate this function at a specified argument, using an EvalStack that is passed as a parameter to this method.
	 * @param z a non-null complex number that is passed as an argument to the function
	 */
	public Complex value(Complex z, EvalStack stack) {
		Complex answer = new Complex();
		value(z,stack,answer);
		return answer;
	}
	
	/**
	 * Evaluate this function at a specified argument, using an EvalStack that is passed as a parameter to this method.
	 * @param z_re the real part of the argument where the function will be evaluated
	 * @param z_im the imaginary part of the argument where the function will be evaluated
	 * @param stack the non-null stack that will be used for computing the value
	 * @return the value of the function at the specified argument
	 */
	public Complex value(double z_re, double z_im, EvalStack stack) {
		Complex answer = new Complex();
		value(z_re,z_im,stack,answer);
		return answer;
	}

	/**
	 * Evaluate this function at a specified argument, using an EvalStack that is passed as a parameter to this method,
	 * and placing the value in an existing Complex object.
	 * @param z a non-null complex number that is passed as an argument to the function
	 * @param stack the non-null stack that will be used for computing the value
	 * @param answer the non-null Complex where the value will be placed
	 */
	public void value(Complex z, EvalStack stack, Complex answer) {
		if (answer == null)
			throw new IllegalArgumentException("internal error: answer object cannot be null");
		stack.push(z);
		func.apply(stack);
		stack.popComplex(answer);
	}

	/**
	 * Evaluate this function at a specified argument, using an EvalStack that is passed as a parameter to this method,
	 * and placing the value in an existing Complex object.
	 * @param z_re the real part of the argument where the function will be evaluated
	 * @param z_im the imaginary part of the argument where the function will be evaluated
	 * @param stack the non-null stack that will be used for computing the value
	 * @param answer the non-null Complex where the value will be placed
	 */
	public void value(double z_re, double z_im, EvalStack stack, Complex answer) {
		if (answer == null)
			throw new IllegalArgumentException("internal error: answer object cannot be null");
		stack.push(z_re,z_im);
		func.apply(stack);
		stack.popComplex(answer);
	}

}
