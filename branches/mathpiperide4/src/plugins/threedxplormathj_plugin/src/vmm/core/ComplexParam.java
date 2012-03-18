/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import vmm.functions.ComplexExpression;
import vmm.functions.ParseError;

/**
 * A parameter of type Complex.  The value object for a paremeter of this type belongs to the class Complex.
 */

public class ComplexParam extends Parameter {
	
	private Complex minimumValueForInput = new Complex(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
	private Complex maximumValueForInput = new Complex(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
	
	/**
	 * Creates a ComplexParam that initially has no name and has value and default value equal to 0.
	 */
	public ComplexParam() {
		this(null, (Complex)null);
	}
	
	/**
	 * Create a ComplexParam with a specified name and initial value.
	 * @param name The name of the Parameter, which can be null but ordinarily should not be.
	 * The name shoud identify the Parameter uniquely among parameters associated with a given
	 * Parameterizeable object.  The name is for internal use in VMM.  A human-readable name
	 * is returned by {@link Parameter#getTitle()}.
	 * @param initialValue the initial value of the parameter.  A null value is treated as 0
	 */
	public ComplexParam(String name, Complex initialValue) {
		super(name, new Complex(initialValue));
	}
	
	/**
	 * Create a ComplexParam with a specified name and initial value.
	 * @param name The name of the Parameter.
	 * @param valueAsString The initial value of the parameter, given as a constant complex expression
	 * that represents a legal value of type Complex.  If this is not the case, a NumberFormatException is thrown.
	 * This also becomes its default value.
	 */
	public ComplexParam(String name, String valueAsString) throws NumberFormatException {
		super(name,valueAsString);
	}
	
	/**
	 * Sets both the default value and the value of this parameter to the specified number.
	 * This is primarily meant to be called in the constructor of an Exhibit when it wants to
	 * change the value of a parameter that is inherited from a superclass.
	 * <p>The version in this class is essentially the same as {@link #setValueAndDefault(Complex)},
	 * but for animated parameters (defined by subclasses of this class), the animation start and
	 * end values are also set.
	 * @see Parameter#reset(String)
	 * @param value the new value and default value.  A null value is treated as zero.
	 */
	public void reset(Complex value) {
		reset(new Complex(value));
	}
	
	/**
	 * Set the value of this parameter to a specified value of type Complex.
	 * @see Parameter#setValueObject(Object)
	 * @param val the new value.  A null value is treated as zero.
	 */
	public void setValue(Complex val) {
		setValueObject(new Complex(val));
	}
	
	/**
	 * Returns the value of this object as a number of type Complex.
	 * @see Parameter#getValueObject()
	 */
	public Complex getValue() {
		Complex d = (Complex)getValueObject();
		if (d == null)
			return new Complex(0,0);
		else
			return d;
	}
	
	/**
	 * Sets the default value of the parameter to a specified complex value.  The default value is
	 * used when the user clicks the "Defaults" button in an {@link AnimationLimitsDialog}.
	 * @see Parameter#setDefaultValueObject(Object)
	 * @param val the default value.  A null value is treated as zero.
	 */
	public void setDefaultValue(double val) {
		setDefaultValueObject(new Complex(val));
	}
	
	/**
	 * Set both the value and the default value of this parameter to a specified int.
	 * @param val the default value.  A null value is treated as zero.
	 */
	public void setValueAndDefault(Complex val) {
		Complex d = new Complex(val);
		setValueObject(d);
		setDefaultValueObject(d);
	}
	
	/**
	 * Returns the default value of this parameter.
	 * @see Parameter#getDefaultValueObject()
	 */
	public Complex getDefaultValue() {
		Complex  d = (Complex)getDefaultValueObject();
		if (d == null)
			return new Complex();
		else
			return d;
	}
	
	/**
	 * Returns the maximum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.
	 * @see #setMaximumValueForInput(Complex)
	 * @return a Complex whose real part is the maximum allowed for the real part of the value of this parameter
	 * and whose imaginary part is the maximum allowed for the imaginary part of this parameter.
	 */
	public Complex getMaximumValueForInput() {
		return maximumValueForInput;
	}

	/**
	 * Set the maximum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.  The default value
	 * is Double.POSITIVE_INFINITY + i*Double.POSITIVE_INFINITY, which means that there is no limit.
	 * @param maximumValueForInput a Complex whose real part is the maximum allowed for the real part of the value of this parameter
	 * and whose imaginary part is the maximum allowed for the imaginary part of this parameter.  A null
	 * value is changed to the default value, Double.POSITIVE_INFINITY + i*Double.POSITIVE_INFINITY
	 */
	public void setMaximumValueForInput(Complex maximumValueForInput) {
		if (maximumValueForInput == null)
			maximumValueForInput = new Complex(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
		this.maximumValueForInput = new Complex(maximumValueForInput);
	}

	/**
	 * Returns the minimum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.
	 * @see #setMinimumValueForInput(Complex)
	 * @return a Complex whose real part is the minimum allowed for the real part of the value of this parameter
	 * and whose imaginary part is the minimum allowed for the imaginary part of this parameter.
	 */
	public Complex getMinimumValueForInput() {
		return minimumValueForInput;
	}

	/**
	 * Set the minimum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.  The default value
	 * is Double.NEGATIVE_INFINITY + i*Double.NEGATIVE_INFINITY, which means that there is no limit. If you want to allow only strictly
	 * positive numbers, set the value to Double.MIN_VALUE, which is the smallest positive number 
	 * that can be represented as a value of type double.
	 * @param minimumValueForInput a Complex whose real part is the minimum allowed for the real part of the value of this parameter
	 * and whose imaginary part is the minimum allowed for the imaginary part of this parameter.  A null
	 * value is changed to the default value, Double.NEGATIVE_INFINITY + i*Double.NEGATIVE_INFINITY
	 */
	public void setMinimumValueForInput(Complex minimumValueForInput) {
		if (minimumValueForInput == null)
			minimumValueForInput = new Complex(Double. NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
		this.minimumValueForInput = new Complex(minimumValueForInput);
	}
	
	/**
	 * Converts a string to an object of type Complex.  This is used for converting strings
	 * to object that can be used as this Parameter's value object or default value object.
	 * A NumberFormatException is thrown if the string does not represent a legal 
	 * constant complex expression that has a defined, finite Complex value.  
	 * <p>Exceptions to this
	 * are the special strings "##NAN##", "##INF##", "##NEGINF##", and ##EPSILON## which represent
	 * the numbers Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, and Double.MIN_VALUE.
	 * These strings are used when the parameter is written to an XML settings file.
	 * They are necessary since it is always possible to set a value programmatically
	 * to one of these numbers.  These numbers are just not allowed as the value of
	 * an ordinary expression.
	 */
	protected Object stringToValueObject(String str) throws NumberFormatException {
		ComplexExpression exp;
		try {
			exp = Util.parseComplexConstantExpression(str);
		}
		catch (ParseError e) {
			try {
				return Util.externalStringToValue(str,Complex.class);
			}
			catch (Exception e1) {
			}
			throw new NumberFormatException(I18n.tr("vmm.core.ComplexParam.badExpression", str, e.getMessage()));
		}
		Complex d = exp.value();
		if (Double.isNaN(d.re) || Double.isInfinite(d.re) || Double.isNaN(d.im) || Double.isInfinite(d.im))
			throw new NumberFormatException(I18n.tr("vmm.core.ComplexParam.undefined", str));
		return d;
	}
	
	/**
	 * Returns a string representation of a value object of this Parameters.  In this class, the
	 * value object is of type Complex, with a null value interpreted as zero. 
	 * The conversion is actually done by {@link Util#toExternalString(Object)}.
	 */
	protected String valueObjectToString(Object obj) {
		return Util.toExternalString(obj);
	}

	
}