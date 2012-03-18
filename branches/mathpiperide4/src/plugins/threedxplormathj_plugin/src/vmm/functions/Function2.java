/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

/**
 * Represents a real-valued function of two real arguments.  A Function2 can
 * only be created by a Parser.
 * @see Parser#parseFunction2(String, String, String, String)
 */
public class Function2 extends Function {
	
	/**
	 * Used by a Parser to create a Function2.
	 */
	Function2(String name, ProgFunction func) {
		super(name,func);
		assert func.getArgType() == Type.REAL && func.getType() == Type.REAL && func.getArgCount() == 2;
	}
	
	/**
	 * Find the value of this Function at a specified pair of argument values, using an EvalStack
	 * that is unique to the current Thread.
	 */	
	synchronized public double value(double x, double y) {
		return value(x, y, EvalStack.perThread());
	}
	
	/**
	 * Find the value of this Function at a specified pair of argument values, using a non-null EvalStack
	 * that is provided as a parameter.
	 */	
	public double value(double x, double y, EvalStack stack) {
		stack.push(x);
		stack.push(y);
		func.apply(stack);
		return stack.pop();
	}

}
