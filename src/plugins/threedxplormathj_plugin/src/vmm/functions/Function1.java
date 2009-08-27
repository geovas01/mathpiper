/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

/**
 * Represents a real-valued function of one real argument.  A Function1 can
 * only be created by a Parser.
 * @see Parser#parseFunction1(String, String, String)
 */
public class Function1 extends Function {

	/**
	 * Used by a Parser to create a Function1.
	 */
	Function1(String name, ProgFunction func) {
		super(name,func);
		assert func.getArgType() == Type.REAL && func.getType() == Type.REAL && func.getArgCount() == 1;
	}
	
	/**
	 * Find the value of this Function at a specified argument value, using an EvalStack
	 * that is unique to the current Thread.
	 * @param x the argument value for which the function will be evaluated
	 */
	synchronized public double value(double x) {
		return value(x, EvalStack.perThread());
	}
	
	/**
	 * Find the value of this Function at a specified argument value, using an EvalStack
	 * that is provided as a parameter to this method.
	 * @param x the argument value for which the function will be evaluated
	 * @param stack a non-null EvalStack that will be used for computing the value
	 */
	public double value(double x, EvalStack stack) {
		stack.push(x);
		func.apply(stack);
		return stack.pop();
	}

}
