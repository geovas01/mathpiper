/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.functions;

/**
 * Represents a real-valued function of any number of real arguments.  A Function
 * can only be created by a {@link Parser}.  This class has subclasses
 * {@link Expression}, {@link Function1}, {@link Function2}, and {@link Function3}, 
 * that represent functions of zero, one, two, and three arguments; the subclasses
 * exist only to provide more natural <code>value()</code> methods that take the
 * expected number of arguments.
 * @see Parser#parseFunction(String, String, String[])
 */
public class Function {
	
	protected ProgFunction func;  // The value of the function is computed by this ProgFunction.
	private String name;          // The name of the function, which can be null.
	
	/**
	 * Used by a Parser to create a Function.
	 */
	Function(String name, ProgFunction func) {
		assert func.getArgType() == Type.REAL && func.getType() == Type.REAL;
		this.func = func;
		this.name = name;
	}
	
	/**
	 * Retrieve the ProgFunction that is used to evaluate this Function.
	 */
	ProgFunction getProgFunction() {
		return func;
	}
	
	/**
	 * Get the name of the Function.  The name was specified in the call to 
	 * {@link Parser#parseFunction(String, String, String[])}
	 * that created this function.  It can be null.  A non-null name is really only
	 * required if this Function is to be added to a Parser so that it can be used
	 * in expressions that are parsed by that parser.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Change the name of this function.  It should not be changed after the function
	 * has been added to a Parser; even if it is changed, the Parser will continue to
	 * use the old name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the number of arguments required by this function.
	 */
	public int getArity() {
		return func.getArgCount();
	}

	/**
	 * Find the value of this function for a specified list of arguments.  This method
	 * evaluates the function using an EvalStack that is unique to the current Thread,
	 * which allows thread-safe evaluation.
	 * @param argumentValues The length of this array
	 *    must be the same as the number of arguments of the function.  If the 
	 *    function has arrity zero, then the argumentValues array can be null.
	 * @return the value of the function at the specified arguments.
	 * @throws IllegalArgumentException if the number of arguments is incorrect.
	 */
	synchronized public double value(double[] argumentValues) {
		return value(argumentValues, EvalStack.perThread());
	}
	
	/**
	 * Find the value of this function for a specified list of arguments.  This method
	 * evaluates the function using an EvalStack that is provided as a parameter to this method.
	 * @param argumentValues the values of the arguments.  The length of this array
	 *    must be the same as the number of arguments of the function.  If the 
	 *    function has arrity zero, then the argumentValues array can be null.
	 * @param stack a non-null EvalStack that will be used for computing the value of this Function
	 * @return the value of the function at the specified arguments.
	 * @throws IllegalArgumentException if the number of arguments is incorrect.
	 */
	public double value(double[] argumentValues, EvalStack stack) {
		int argCt = func.getArgCount();
		if (argCt == 0) {
			if (argumentValues != null && argumentValues.length > 0)
				throw new IllegalArgumentException("internal error: incorrect number of arguments provided to function");
			func.apply(stack);
			return stack.pop();
		}
		else {
			if (argumentValues == null || argumentValues.length != argCt)
				throw new IllegalArgumentException("internal error: incorrect number of arguments provided to function");
			for (double x : argumentValues)
				stack.push(x);
			func.apply(stack);
			return stack.pop();
		}
	}

}
