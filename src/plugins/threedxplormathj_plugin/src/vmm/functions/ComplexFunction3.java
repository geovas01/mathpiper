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
 * Represents a complex-valued function of three complex arguments.  A ComplexFunction2 can
 * only be created by a Parser.
 * @see Parser#parseComplexFunction3(String, String, String, String, String)
 */

public class ComplexFunction3 extends ComplexFunction {
	
	/**
	 * Used by a parser to construct an object of type ComplexFunction3.
	 */
	ComplexFunction3(String name, ProgFunction func) {
		super(name,func);
		assert func.getArgType() == Type.COMPLEX && func.getType() == Type.COMPLEX && func.getArgCount() == 3;
	}
	
	/**
	 * Evaluate this function at a specified list of arguments, using an EvalStack that is unique to the current Thread.
	 * @param z a non-null complex number that is passed as the first argument to the function
	 * @param w a non-null complex number that is passed as the second argument to the function
	 * @param v a non-null complex number that is passed as the third argument to the function
	 */
	synchronized public Complex value(Complex z, Complex w, Complex v) {
		return value(z, w, v, EvalStack.perThread());
	}
	
	/**
	 * Evaluate this function at a specified list of arguments, using an EvalStack that is unique to the current Thread..
	 * @param z_re the real part of the first argument
	 * @param z_im the imaginary part of the first argument
	 * @param w_re the real part of the second argument
	 * @param w_im the imaginary part of the second argument
	 * @param v_re the real part of the third argument
	 * @param v_im the imaginary part of the third argument
	 */
	synchronized public Complex value(double z_re, double z_im, double w_re, double w_im, double v_re, double v_im) {
		return value(z_re, z_im, w_re, w_im, v_re, v_im, EvalStack.perThread());
	}
	
	/**
	 * Evaluate this function at a specified list of arguments, using an EvalStack that is provided as a parameter.
	 * @param z a non-null complex number that is passed as the first argument to the function
	 * @param w a non-null complex number that is passed as the second argument to the function
	 * @param v a non-null complex number that is passed as the third argument to the function
	 * @param stack a non-null EvalStack that will be used for computing the value.
	 */
	public Complex value(Complex z, Complex w, Complex v, EvalStack stack) {
		Complex answer = new Complex();
		value(z,w,v,stack,answer);
		return answer;
	}
	
	/**
	 * Evaluate this function at a specified list of arguments, using an EvalStack that is provided as a parameter.
	 * @param z_re the real part of the first argument
	 * @param z_im the imaginary part of the first argument
	 * @param w_re the real part of the second argument
	 * @param w_im the imaginary part of the second argument
	 * @param v_re the real part of the third argument
	 * @param v_im the imaginary part of the third argument
	 * @param stack a non-null EvalStack that will be used for computing the value.
	 */
	public Complex value(double z_re, double z_im, double w_re, double w_im, double v_re, double v_im, EvalStack stack) {
		Complex answer = new Complex();
		value(z_re,z_im,w_re,w_im,v_re,v_im,stack,answer);
		return answer;
	}

	/**
	 * Evaluate this function at a specified list of arguments, using an EvalStack that is provided as a parameter,
	 * placing the answer in an existing Complex object.
	 * @param z a non-null complex number that is passed as the first argument to the function
	 * @param w a non-null complex number that is passed as the second argument to the function
	 * @param v a non-null complex number that is passed as the third argument to the function
	 * @param stack a non-null EvalStack that will be used for computing the value.
	 * @param answer the non-null Complex object where the answer will be placed.
	 */
	public void value(Complex z, Complex w, Complex v, EvalStack stack, Complex answer) {
		if (answer == null)
			throw new IllegalArgumentException("internal error: answer object cannot be null");
		stack.push(z);
		stack.push(w);
		stack.push(v);
		func.apply(stack);
		stack.popComplex(answer);
	}

	/**
	 * Evaluate this function at a specified list of arguments, using an EvalStack that is provided as a parameter,
	 * placing the answer in an existing Complex object.
	 * @param z_re the real part of the first argument
	 * @param z_im the imaginary part of the first argument
	 * @param w_re the real part of the second argument
	 * @param w_im the imaginary part of the second argument
	 * @param v_re the real part of the third argument
	 * @param v_im the imaginary part of the third argument
	 * @param stack a non-null EvalStack that will be used for computing the value.
	 * @param answer the non-null Complex object where the answer will be placed.
	 */
	public void value(double z_re, double z_im, double w_re, double w_im, double v_re, double v_im, EvalStack stack, Complex answer) {
		if (answer == null)
			throw new IllegalArgumentException("internal error: answer object cannot be null");
		stack.push(z_re,z_im);
		stack.push(w_re,w_im);
		stack.push(v_re,v_im);
		func.apply(stack);
		stack.popComplex(answer);
	}

}
