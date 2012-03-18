/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import vmm.functions.Variable;

/**
 * A real-valued parameter whose value is also the value of a Variable, as defined in
 * the class {@link vmm.functions.Variable}.  When the value of the parameter is
 * changed, the value of the variable is changed to match, and vice-versa.
 * Parameters of this type are mostly for use in user-defined objects.  The
 * name of a VariableParam is shared with the associated variable and therefor should probably
 * be of human-readable form, such as "a" or "b".
 */

public class VariableParam extends RealParam {
	
	private Variable variable;
	
	/**
	 * Creates a VariableParam that initially has no name and has value and default value equal to 0.
	 */
	public VariableParam() {
		this(null,0);
	}
		
	/**
	 * Create a VariableParam with a specified name and initial value.  A variable of the
	 * same name is created that will share the value of this parameter.  The variable can
	 * be retrieved with the {@link #getVariable()} method.
	 * @param name The name of the Parameter, which can be null but ordinarily should not be.
	 * The name shoud identify the Parameter uniquely among parameters associated with a given
	 * Parameterizeable object.  The name is for internal use in VMM.  A human-readable name
	 * is returned by {@link Parameter#getTitle()}.  The name also becomes the name of the associated
	 * variable.
	 * @param initialValue The initial value of the parameter.  This also becomes its default value.
	 */
	public VariableParam(String name, double initialValue) {
		super(name, initialValue);
		variable = new Variable(name, initialValue);
	}
	
	/**
	 * Create a VariableParam with a specified name and initial value, where the initial value
	 * is given as a string.  A variable of the same name is created that will share the value of
	 * this parameter.  The variable can be retrieved with the {@link #getVariable()} method.
	 * @param name The name of the Parameter.
	 * @param value The initial value of the parameter, given as a constant expression
	 * that represents a legal value of type double.  If this is not the case, a NumberFormatException is thrown.
	 * This also becomes its default value.
	 */
	public VariableParam(String name, String value) throws NumberFormatException {
		super(name,value);
		variable= new Variable(name, getValue());
	}
	
	/**
	 * Create a VariableParam that will share the value of the specified variable.  The parameter's name and
	 * initial value are copied from the variable.
	 * @param v the non-null variable that will be associated with the VariableParam
	 */
	public VariableParam(Variable v) {
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
			variable.setVal(((Double)object).doubleValue());
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
		Double val = (Double)super.getValueObject();
		if (val.doubleValue() != variable.getVal()) {
			val = new Double(variable.getVal());
			super.setValueObject(val);
		}
		return val;
	}
	
	/**
	 * Returns the non-null variable associated with this VariableParam.
	 */
	public Variable getVariable() {
		return variable;
	}

}