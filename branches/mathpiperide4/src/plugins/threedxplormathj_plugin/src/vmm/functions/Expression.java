/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

/**
 * Represents a real-valued expression.  An expression can only be produced by a
 * {@link Parser}.  An expression is implemented as a Function that has no arguments
 * (and no name).
 * @see Parser#parseExpression(String)
 */
public class Expression extends Function {
	
	/**
	 * Package-private method that is used by a Parser to create an Expression.
	 */
	Expression(ProgFunction expr) {
		super(null,expr);
	}
	
	/**
	 * Evaluate this expression (using an EvalStack that is unique to the current Thread).
	 */
	synchronized public double value() {
		return value(EvalStack.perThread());
	}
	
	/**
	 * Evaluate this expression using a stack provided as a parameter.
	 * @param stack a non-null stack to be used for evaluating the expression.
	 */
	public double value(EvalStack stack) {
		func.apply(stack);
		return stack.pop();
	}

}
