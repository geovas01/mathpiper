/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import vmm.functions.ComplexVariable;

/**
 * A Complex-valued parameter whose value is also the value of a ComplexVariable, as defined in
 * the class vmm.functions.ComplexVariable.  When the value of the parameter is
 * changed, the value of the variable is changed to match, and vice-versa.
 * Parameters of this type are mostly for use in user-defined objects.  The
 * name of a ComplexVariableParam is shared with the associated variable and therefor should probably
 * be of human-readable form, such as "a" or "b".
 */

public class ComplexVariableParam extends ComplexParam {
	
	private ComplexVariable variable;
	
	/**
	 * Creates a ComplexVariableParam that initially has no name and has value and default value equal to 0.
	 */
	public ComplexVariableParam() {
		this(null,(Complex)null);
	}
		
	/**
	 * Create a VComplexariableParam with a specified name and initial value.  A variable of the
	 * same name is created that will share the value of this parameter.  The variable can
	 * be retrieved with the {@link #getVariable()} method.
	 * @param name The name of the Parameter, which can be null but ordinarily should not be.
	 * The name also becomes the name of the associated variable.
	 * @param initialValue The initial value of the parameter.  This also becomes its default value.
	 *   A null value is treated as zero.
	 */
	public ComplexVariableParam(String name, Complex initialValue) {
		super(name, initialValue);
		if (initialValue == null)
			variable = new ComplexVariable(name);
		else
			variable = new ComplexVariable(name, initialValue.re, initialValue.im);
	}
	
	/**
	 * Create a ComplexVariableParam with a specified name and initial value, where the initial value
	 * is given as a string.  A variable of the same name is created that will share the value of
	 * this parameter.  The variable can be retrieved with the {@link #getVariable()} method.
	 * @param name The name of the Parameter.
	 * @param value The initial value of the parameter, given as a constant expression
	 * that represents a legal value of type double.  If this is not the case, a NumberFormatException is thrown.
	 * This also becomes its default value.
	 */
	public ComplexVariableParam(String name, String value) throws NumberFormatException {
		super(name,value);
		Complex val = getValue();
		variable= new ComplexVariable(name, val.re, val.im);
	}
	
	/**
	 * Create a ComplexVariableParam that will share the value of the specified variable.  The parameter's name and
	 * initial value are copied from the variable.
	 * @param v the non-null variable that will be associated with the VariableParam
	 */
	public ComplexVariableParam(ComplexVariable v) {
		super(v.getName(),v.getVal());
		variable = v;
	}
	
	/**
	 * Sets the name of this parameter and of the associated variable.
	 */
	public void setName(String name) {
		super.setName(name);
		variable.setName(name);
	}
	
	/**
	 * Sets the value of this parameter and of the associated variable.
	 */
	public void setValueObject(Object object) {
		super.setValueObject(object);
		if (variable != null)  // it will be null when setValueObject() is called during the constructor.
			variable.setVal((Complex)object);
	}
	
	/**
	 * Get the value object of this parameter.  This is overriden here so that when the value
	 * object is requested, the value of the associated variable is checked.  If the value of
	 * the variable is different from the value object, then the value object is first reset
	 * to match the variable's value.  This makes it possible for the apparent values of the parameter
	 * and of the variable to be kept in sync when the variable's value is set using the variable's
	 * <code>setVal</code> method.
	 */
	public Object getValueObject() {
		Complex val = (Complex)super.getValueObject();
		if ( ! variable.getVal().equals(val) ) {
			val = variable.getVal();
			super.setValueObject(val);
		}
		return val;
	}
	
	/**
	 * Returns the non-null variable associated with this VariableParam.
	 */
	public ComplexVariable getVariable() {
		return variable;
	}

}