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
 * Represents a complex-valued function of two complex arguments.  A ComplexFunction2 can
 * only be created by a Parser.
 * @see Parser#parseComplexFunction2(String, String, String, String)
 */

public class ComplexFunction2 extends ComplexFunction {
	
	/**
	 * Used by a parser to construct an object of type ComplexFunction2.
	 */
	ComplexFunction2(String name, ProgFunction func) {
		super(name,func);
		assert func.getArgType() == Type.COMPLEX && func.getType() == Type.COMPLEX && func.getArgCount() == 2;
	}
	
	/**
	 * Evaluate this function at a specified pair of arguments, using an EvalStack that is unique to the current Thread..
	 * @param z a non-null complex number that is passed as the first argument to the function
	 * @param w a non-null complex number that is passed as the second argument to the function
	 */
	synchronized public Complex value(Complex z, Complex w) {
		return value(z, w, EvalStack.perThread());
	}
	
	/**
	 * Evaluate this function at a specified pair of arguments, using an EvalStack that is unique to the current Thread..
	 * @param z_re the real part of the first argument
	 * @param z_im the imaginary part of the first argument
	 * @param w_re the real part of the second argument
	 * @param w_im the imaginary part of the second argument
	 */
	synchronized public Complex value(double z_re, double z_im, double w_re, double w_im) {
		return value(z_re, z_im, w_re, w_im, EvalStack.perThread());
	}
	
	/**
	 * Evaluate this function at a specified pair of arguments, using an EvalStack that is provided as a parameter.
	 * @param z a non-null complex number that is passed as the first argument to the function
	 * @param w a non-null complex number that is passed as the second argument to the function
	 * @param stack a non-null EvalStack that will be used for computing the value.
	 */
	public Complex value(Complex z, Complex w, EvalStack stack) {
		Complex answer = new Complex();
		value(z,w,stack,answer);
		return answer;
	}
	
	/**
	 * Evaluate this function at a specified pair of arguments, using an EvalStack that is provided as a parameter.
	 * @param z_re the real part of the first argument
	 * @param z_im the imaginary part of the first argument
	 * @param w_re the real part of the second argument
	 * @param w_im the imaginary part of the second argument
	 * @param stack a non-null EvalStack that will be used for computing the value.
	 */
	public Complex value(double z_re, double z_im, double w_re, double w_im, EvalStack stack) {
		Complex answer = new Complex();
		value(z_re,z_im,w_re,w_im,stack,answer);
		return answer;
	}

	/**
	 * Evaluate this function at a specified pair of arguments, using an EvalStack that is provided as a parameter,
	 * placing the answer in an existing Complex object.
	 * @param z a non-null complex number that is passed as the first argument to the function
	 * @param w a non-null complex number that is passed as the second argument to the function
	 * @param stack a non-null EvalStack that will be used for computing the value.
	 * @param answer the non-null Complex object where the answer will be placed.
	 */
	public void value(Complex z, Complex w, EvalStack stack, Complex answer) {
		if (answer == null)
			throw new IllegalArgumentException("internal error: answer object cannot be null");
		stack.push(z);
		stack.push(w);
		func.apply(stack);
		stack.popComplex(answer);
	}

	/**
	 * Evaluate this function at a specified pair of arguments, using an EvalStack that is provided as a parameter,
	 * placing the answer in an existing Complex object.
	 * @param z_re the real part of the first argument
	 * @param z_im the imaginary part of the first argument
	 * @param w_re the real part of the second argument
	 * @param w_im the imaginary part of the second argument
	 * @param stack a non-null EvalStack that will be used for computing the value.
	 * @param answer the non-null Complex object where the answer will be placed.
	 */
	public void value(double z_re, double z_im, double w_re, double w_im, EvalStack stack, Complex answer) {
		if (answer == null)
			throw new IllegalArgumentException("internal error: answer object cannot be null");
		stack.push(z_re,z_im);
		stack.push(w_re,w_im);
		func.apply(stack);
		stack.popComplex(answer);
	}

}
