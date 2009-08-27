/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

/**
 * Represents a real-valued function of one real argument.  A Function3 can
 * only be created by a Parser.
 * @see Parser#parseFunction3(String, String, String, String, String)
 */
public class Function3 extends Function {
	
	/**
	 * Used by a Parser to construct a Function3.
	 */
	Function3(String name, ProgFunction func) {
		super(name,func);
		assert func.getArgType() == Type.REAL && func.getType() == Type.REAL && func.getArgCount() == 3;
	}
	
	/**
	 * Evaluate this Function3 for arguments (x,y,z), using an EvalStack that is unique to
	 * the current thread.
	 */
	synchronized public double value(double x, double y, double z) {
		return value(x, y, z, EvalStack.perThread());
	}
	
	/**
	 * Evaluate this Function3 for arguments (x,y,z), using a non-null EvalStack that is 
	 * provided as a parameter to this method.
	 */
	public double value(double x, double y, double z, EvalStack stack) {
		stack.push(x);
		stack.push(y);
		stack.push(z);
		func.apply(stack);
		return stack.pop();
	}

}
