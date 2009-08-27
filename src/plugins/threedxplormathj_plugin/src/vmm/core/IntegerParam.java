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
 * A parameter of type int.  The value object for a paremeter of this type belongs to the class Integer.
 */

public class IntegerParam extends Parameter {
	
	private int minimumValueForInput = Integer.MIN_VALUE;
	private int maximumValueForInput = Integer.MAX_VALUE;
	
	/**
	 * Creates an IntegerParam that initially has no name and has value and default value equal to 0.
	 */
	public IntegerParam() {
		this(null,0);
	}
	
	/**
	 * Create an IntegerParam with a specified name and initial value.
	 * @param name The name of the Parameter, which can be null but ordinarily should not be.
	 * The name shoud identify the Parameter uniquely among parameters associated with a given
	 * Parameterizeable object.  The name is for internal use in VMM.  A human-readable name
	 * is returned by {@link Parameter#getTitle()}.
	 * @param initialValue The initial value of the parameter.  This also becomes its default value.
	 */
	public IntegerParam(String name, int initialValue) {
		super(name, new Integer(initialValue));
	}
	
	/**
	 * Create an IntegerParam with a specified name and initial value.
	 * @param name The name of the Parameter.
	 * @param valueAsString The initial value of the parameter, given as a constant expression
	 * that represents a legal value of type int.  If this is not the case, a NumberFormatException is thrown.
	 * This also becomes its default value.
	 */
	public IntegerParam(String name, String valueAsString) throws NumberFormatException {
		super(name,valueAsString);
	}
	
	/**
	 * Sets both the default value and the value of this parameter to the specified integer.
	 * This is primarily meant to be called in the constructor of an Exhibit when it wants to
	 * change the value of a parameter that is inherited from a superclass.
	 * <p>The version in this class is essentially the same as {@link #setValueAndDefault(int)}.
	 * @see Parameter#reset(String)
	 */
	public void reset(int value) {
		reset(new Integer(value));
	}
	
	/**
	 * Set the value of this parameter to a specified int.
	 * @see Parameter#setValueObject(Object)
	 */
	public void setValue(int val) {
		setValueObject(new Integer(val));
	}
	
	/**
	 * Get the value of this parameter in the form of an int.
	 * @see Parameter#getValueObject()
	 */
	public int getValue() {
		Integer obj = (Integer)getValueObject();
		if (obj == null)
			return 0;
		else
			return obj.intValue();
	}
	
	/**
	 * Sets the default value of the parameter to a specified int.  The default value is
	 * used when the user clicks the "Defaults" button in an {@link AnimationLimitsDialog}.
	 * @see Parameter#setDefaultValueObject(Object)
	 */
	public void setDefaultValue(int val) {
		setDefaultValueObject(new Integer(val));
	}
	
	/**
	 * Set both the value and the default value of this parameter to a specified int.
	 */
	public void setValueAndDefault(int val) {
		Integer v = new Integer(val);
		setValueObject(v);
		setDefaultValueObject(v);
	}
	
	/**
	 * Returns the default value of this parameter as an int.
	 * @see Parameter#getDefaultValueObject()
	 */
	public int getDefaultValue() {
		Object object = getDefaultValueObject();
		if (object == null)
			return 0;
		else
			return ((Integer)object).intValue();
	}
	
	
	/**
	 * Returns the maximum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.
	 * @see #setMaximumValueForInput(int)
	 */
	public int getMaximumValueForInput() {
		return maximumValueForInput;
	}

	/**
	 * Set the maximum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.  The default value
	 * is Integer.MAX_VALUE, which means that there is no limit.
	 */
	public void setMaximumValueForInput(int maximumValueForInput) {
		this.maximumValueForInput = maximumValueForInput;
	}

	/**
	 * Returns the minimum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.
	 * @see #setMinimumValueForInput(int)
	 */
	public int getMinimumValueForInput() {
		return minimumValueForInput;
	}

	/**
	 * Set the minimum value that is allowed for this parameter when its value is input.  In the VMM core, this
	 * limit is only enforced when the value is input into a {@link ParameterInput}.  The default value
	 * is Integer.MIN_VALUE, which means that there is no limit.
	 */
	public void setMinimumValueForInput(int minimumValueForInput) {
		this.minimumValueForInput = minimumValueForInput;
	}

	/**
	 * Converts a string to an object of type Integer.  This is used for converting strings
	 * to object that can be used as this Parameter's value object or default value object.
	 * A NumberFormatException is thrown if the string does not represent a legal
	 * constant expression that has an integer value.
	 */
	protected Object stringToValueObject(String str) throws NumberFormatException {
		try {
			return new Integer(str);
		}
		catch (NumberFormatException e) {
		}
		Expression exp;
		try {
			exp = Util.parseConstantExpression(str);
		}
		catch (ParseError e) {
			throw new NumberFormatException(I18n.tr("vmm.core.IntegerParam.badExpression", str, e.getMessage()));
		}
		double d = exp.value();
		if (Double.isNaN(d) || Double.isInfinite(d))
			throw new NumberFormatException(I18n.tr("vmm.core.IntegerParam.undefined", str));
		int i;
		if (d > 0)
			i = (int)(d + 1e-8);
		else
			i = (int)(d - 1e-8);
		if (Math.abs(i - d) > 5e-9)
			throw new NumberFormatException(I18n.tr("vmm.core.IntegerParam.notAnInteger", str));
		return new Integer(i);
	}
	

}