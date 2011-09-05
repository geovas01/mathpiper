/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import vmm.functions.Expression;
import vmm.functions.ParseError;

/**
 * A parameter of type Real.  The value object for a paremeter of this type belongs to the class Double.
 */

public class RealParam extends Parameter {
	
	private double minimumValueForInput = Double.NEGATIVE_INFINITY;
	private double maximumValueForInput = Double.POSITIVE_INFINITY;
	
	/**
	 * Creates a RealParam that initially has no name and has value and default value equal to 0.
	 */
	public RealParam() {
		this(null,0.0);
	}
	
	/**
	 * Create a RealParam with a specified name and initial value.
	 * @param name The name of the Parameter, which can be null but ordinarily should not be.
	 * The name shoud identify the Parameter uniquely among parameters associated with a given
	 * Parameterizeable object.  The name is for internal use in VMM.  A human-readable name
	 * is returned by {@link Parameter#getTitle()}.
	 * @param initialValue The initial value of the parameter.  This also becomes its default value.
	 */
	public RealParam(String name, double initialValue) {
		super(name, new Double(initialValue));
	}
	
	/**
	 * Create a RealParam with a specified name and initial value.
	 * @param name The name of the Parameter.
	 * @param valueAsString The initial value of the parameter, given as a constant expression
	 * that represents a legal value of type double.  If this is not the case, a NumberFormatException is thrown.
	 * This also becomes its default value.
	 */
	public RealParam(String name, String valueAsString) throws NumberFormatException {
		super(name,valueAsString);
	}
	
	/**
	 * Sets both the default value and the value of this parameter to the specified number.
	 * This is primarily meant to be called in the constructor of an Exhibit when it wants to
	 * change the value of a parameter that is inherited from a superclass.
	 * <p>The version in this class is essentially the same as {@link #setValueAndDefault(double)},
	 * but for animated parameters (defined by subclasses of this class), the animation start and
	 * end values are also set.
	 * @see Parameter#reset(String)
	 */
	public void reset(double value) {
		reset(new Double(value));
	}
	
	/**
	 * Set the value of this parameter to a specified value of type double.
	 * @see Parameter#setValueObject(Object)
	 */
	public void setValue(double val) {
		setValueObject(new Double(val));
	}
	
	/**
	 * Returns the value of this object as a number of type double.
	 * @see Parameter#getValueObject()
	 */
	public double getValue() {
		Double d = (Double)getValueObject();
		if (d == null)
			return 0;
		else
			return d.doubleValue();
	}
	
	/**
	 * Sets the default value of the parameter to a specified double.  The default value is
	 * used when the user clicks the "Defaults" button in an {@link AnimationLimitsDialog}.
	 * @see Parameter#setDefaultValueObject(Object)
	 */
	public void setDefaultValue(double val) {
		setDefaultValueObject(new Double(val));
	}
	
	/**
	 * Set both the value and the default value of this parameter to a specified int.
	 */
	public void setValueAndDefault(double val) {
		Double d = new Double(val);
		setValueObject(d);
		setDefaultValueObject(d);
	}
	
	/**
	 * Returns the default value of this parameter as an int.
	 * @see Parameter#getDefaultValueObject()
	 */
	public double getDefaultValue() {
		Double  d = (Double)getDefaultValueObject();
		if (d == null)
			return 0;
		else
			return d.doubleValue();
	}
	
	/**
	 * Returns the maximum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.
	 * @see #setMaximumValueForInput(double)
	 */
	public double getMaximumValueForInput() {
		return maximumValueForInput;
	}

	/**
	 * Set the maximum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.  The default value
	 * is Double.NEGATIVE_INFINITY, which means that there is no limit.
	 */
	public void setMaximumValueForInput(double maximumValueForInput) {
		this.maximumValueForInput = maximumValueForInput;
	}

	/**
	 * Returns the minimum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.
	 * @see #setMinimumValueForInput(double)
	 */
	public double getMinimumValueForInput() {
		return minimumValueForInput;
	}

	/**
	 * Set the minimum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.  The default value
	 * is Double.NEGATIVE_INFINITY, which means that there is no limit. If you want to allow only strictly
	 * positive numbers, set the value to Double.MIN_VALUE, which is the smallest positive number 
	 * that can be represented as a value of type double.
	 */
	public void setMinimumValueForInput(double minimumValueForInput) {
		this.minimumValueForInput = minimumValueForInput;
	}
	
	/**
	 * Converts a string to an object of type Double.  This is used for converting strings
	 * to objects that can be used as this Parameter's value object or default value object.
	 * A NumberFormatException is thrown if the string does not represent a legal 
	 * constant expression that has a defined, finite real value.  
	 * <p>Exceptions to this
	 * are the special strings "##NAN##", "##INF##", "##NEGINF##", and ##EPSILON## which represent
	 * the numbers Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, and Double.MIN_VALUE.
	 * These strings are used when the parameter is written to an XML settings file.
	 * They are necessary since it is always possible to set a value programmatically
	 * to one of these numbers.  These numbers are just not allowed as the value of
	 * an ordinary expression.
	 */
	protected Object stringToValueObject(String str) throws NumberFormatException {
		Double val= null;
		try {
			val = new Double(str);
		}
		catch (Exception e) {
		}
		if (val != null) {
			if (val.isNaN() || val.isInfinite())
				throw new NumberFormatException(I18n.tr("vmm.core.RealParam.undefined", str));
			return val;
		}
		Expression exp;
		try {
			exp = Util.parseConstantExpression(str);
		}
		catch (ParseError e) {
			if (str != null) {
				try {
					return (Util.externalStringToValue(str,Double.TYPE));
				}
				catch (Exception e1) {
				}
			}
			throw new NumberFormatException(I18n.tr("vmm.core.RealParam.badExpression", str, e.getMessage()));
		}
		double d = exp.value();
		if (Double.isNaN(d) || Double.isInfinite(d))
			throw new NumberFormatException(I18n.tr("vmm.core.RealParam.undefined", str));
		return new Double(d);
	}
	
	/**
	 * Returns a string representation of a value object of this Parameters.  In this class, the
	 * value object is of type Double, with a null value interpreted as zero. 
	 * This is compatible with the {@link #stringToValueObject(String)} method.
	 * The conversion is actually done by {@link Util#toExternalString(Object)}}
	 */
	protected String valueObjectToString(Object obj) {
		return Util.toExternalString(((Double)obj).doubleValue());
	}

	
}