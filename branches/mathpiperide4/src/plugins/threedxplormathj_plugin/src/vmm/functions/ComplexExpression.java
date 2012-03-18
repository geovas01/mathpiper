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
 * Represents a complex-valued expression.  An expression can only be produced by a
 * {@link Parser}.  An expression is implemented as a Function that has no arguments
 * (and no name).
 * @see Parser#parseComplexExpression(String)
 */

public class ComplexExpression extends ComplexFunction {
			
	/**
	 * Package-private method that is used by a Parser to create a ComplexExpression.
	 */
	ComplexExpression(ProgFunction expr) {
		super(null,expr);
	}
	
	/**
	 * Evaluate this expression (using an EvalStack that is unique to the current Thread).
	 */
	synchronized public Complex value() {
		return value(EvalStack.perThread());
	}
	
	/**
	 * Evaluate this expression (using an EvalStack that is unique to the current Thread),
	 * and put the value in a non-null Complex object that is provided as a parameter
	 * to this method.
	 */
	synchronized public void value(Complex answer) {
		value(EvalStack.perThread(), answer);
	}
	
	/**
	 * Evaluate this expression using a non-null stack provided as a parameter.
	 */
	public Complex value(EvalStack stack) {
		func.apply(stack);
		return stack.popComplex();
	}
	
	/**
	 * Evaluate this expression using a non-null stack provided as a parameter,
	 * and put the value in a non-null Complex object that is provided as a parameter,
	 */
	public void value(EvalStack stack, Complex answer){
		func.apply(stack);
		stack.popComplex(answer);
	}

}
