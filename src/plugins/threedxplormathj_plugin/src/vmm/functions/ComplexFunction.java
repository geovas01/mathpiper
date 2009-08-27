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
 * Represents a complex-valued function of any number of complex arguments.  A ComplexFunction
 * can only be created by a {@link Parser}.  This class has subclasses
 * {@link ComplexExpression}, {@link ComplexFunction1}, {@link ComplexFunction2}, and {@link ComplexFunction3}, 
 * that represent functions of zero, one, two, and three arguments; the subclasses
 * exist only to provide more natural <code>value()</code> methods that take the
 * expected number of arguments.
 * @see Parser#parseComplexFunction(String, String, String[])
 */
public class ComplexFunction {
	
	protected ProgFunction func; // Evaluating this ComplexFunction means evaluating this ProgFunction.
	
	private String name;  // The name of the function.
	
	
	/**
	 * Used by a parser to create a ComplexFunction.
	 */
	ComplexFunction(String name, ProgFunction func) {
		assert func.getArgType() == Type.COMPLEX && func.getType() == Type.COMPLEX;
		this.func = func;
		this.name = name;
	}
	
	/**
	 * Gets the ProgFunction that is used to find the value of this function.
	 */
	ProgFunction getProgFunction() {
		return func;
	}
	
	/**
	 * Gets the name of this function.  The name is specified in
	 * {@link Parser#parseComplexFunction(String, String, String[])} and can be null.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Changes the name of this function.  The name should not be changed after the
	 * function has been added to a Parser; if it is, the parser will still use the old name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the number of arguments that are required by this function.
	 */
	public int getArity() {
		return func.getArgCount();
	}
	
	/**
	 * Finds the value of the function at a specified list of argument values, using an EvalStack
	 * that is unique to the current Thread, allowing evaluation in a thread-safe manner.
	 * @param argumentValues the values of the arguments.  The length of this array
	 *    must be the same as the number of arguments of the function.  If the 
	 *    function has arrity zero, then the argumentValues array can be null.
	 *    The Complex objects in this array must be non-null.
	 */
	synchronized public Complex value(Complex[] argumentValues) {
		return value(argumentValues, EvalStack.perThread());
	}
	
	/**
	 * Finds the value of the function at a specified list of argument values, using an EvalStack
	 * that is unique to the current Thread, allowing evaluation in a thread-safe manner.
	 * @param arg the values of the arguments.  The number of arguments passed
	 * must be equal to the arity of the function multiplied by two.  The real numbers
	 * passed to this method are grouped into pairs to produce the complex arguements
	 * that are passed to the function.
	 */
	synchronized public Complex value(double... arg) {
		return value(arg, EvalStack.perThread());
	}
	
	/**
	 * Finds the value of the function at a specified list of argument values, using an EvalStack
	 * that is passed as a parameter.
	 * @param argumentValues the values of the arguments.  The length of this array
	 *    must be the same as the number of arguments of the function.  If the 
	 *    function has arrity zero, then the argumentValues array can be null.
	 *    The Complex objects in this array must be non-null.
	 * @param stack the EvalStack that is used for computing the value.
	 */
	public Complex value(Complex[] argumentValues, EvalStack stack) {
		Complex answer = new Complex();
		value(argumentValues,stack,answer);
		return answer;
	}

	/**
	 * Finds the value of the function at a specified list of argument values, using an EvalStack
	 * that is passed as a parameter.
	 * @param argumentValues the values of the arguments.  The length of the array
	 * must be equal to the arity of the function multiplied by two.  The real numbers
	 * iin this array are grouped into pairs to produce the complex arguements
	 * that are passed to the function.
	 * @param stack the EvalStack that is used for computing the value.
	 */
	public Complex value(double[] argumentValues, EvalStack stack) {
		Complex answer = new Complex();
		value(argumentValues,stack,answer);
		return answer;
	}

	/**
	 * Finds the value of the function at a specified list of argument values, using an EvalStack
	 * that is passed as a parameter, and place the answer in an existing Complex object.
	 * @param argumentValues the values of the arguments.  The length of this array
	 *    must be the same as the number of arguments of the function.  If the 
	 *    function has arrity zero, then the argumentValues array can be null.
	 *    The Complex objects in this array must be non-null.
	 * @param stack the EvalStack that is used for computing the value.
	 * @param answer the non-null Complex object where the value will be placed
	 */
	public void value(Complex[] argumentValues, EvalStack stack, Complex answer) {
		if (answer == null)
			throw new IllegalArgumentException("internal error: answer object cannot be null");
		int argCt = func.getArgCount();
		if (argCt == 0) {
			if (argumentValues != null && argumentValues.length > 0)
				throw new IllegalArgumentException("internal error: incorrect number of arguments provided to function");
			func.apply(stack);
			stack.popComplex(answer);
		}
		else {
			if (argumentValues == null || argumentValues.length != argCt)
				throw new IllegalArgumentException("internal error: incorrect number of arguments provided to function");
			for (Complex z : argumentValues)
				stack.push(z);
			func.apply(stack);
			stack.popComplex(answer);
		}
	}

	/**
	 * Finds the value of the function at a specified list of argument values, using an EvalStack
	 * that is passed as a parameter, and place the answer in an existing Complex object.
	 * @param argumentValues the values of the arguments.  The length of the array
	 * must be equal to the arity of the function multiplied by two.  The real numbers
	 * iin this array are grouped into pairs to produce the complex arguements
	 * that are passed to the function.
	 * @param stack the EvalStack that is used for computing the value.
	 * @param answer the non-null Complex object where the value will be placed
	 */
	public void value(double[] argumentValues, EvalStack stack, Complex answer) {
		if (answer == null)
			throw new IllegalArgumentException("internal error: answer object cannot be null");
		int argCt = func.getArgCount();
		if (argCt == 0) {
			if (argumentValues != null && argumentValues.length > 0)
				throw new IllegalArgumentException("internal error: incorrect number of arguments provided to function");
			func.apply(stack);
			stack.popComplex(answer);
		}
		else {
			if (argumentValues == null || argumentValues.length != 2*argCt)
				throw new IllegalArgumentException("internal error: incorrect number of arguments provided to function");
			for (double d : argumentValues)
				stack.push(d);
			func.apply(stack);
			stack.popComplex(answer);
		}
	}

}
