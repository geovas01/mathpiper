/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;


/**
 * Represents a settable numeric parameter associated with another object, such as a View
 * or Exhibit.  A parameter is meant to be an attribute of a single object, which
 * is its "owner."  The owner implements interface Parameterizable, which defines
 * a method {@link vmm.core.Parameterizable#parameterChanged(Parameter, Object, Object)}.
 * When a Parameter's value is changed, it calls this method in its owner.
 * <p>The value of a parameter can be set from a String that represents a constant expression.
 * When the value is set in this way, the String is saved so that it can be returned when
 * the parameter is converted to string form with the <code>getValueAsString</code> method.
 * This allows parameters to have values such as "2*pi".
 * <p>In addition to its value, a parameter has a "default value" that is used (in the VMM core) only
 * when the user clicks the "Defaults" button in a {@link vmm.core.ParameterDialog}.  The default
 * value is set the first time the parameter's value is set, and it can be explicitely reset later.
 * <p>Parameters can be (at least) integers, real numbers, or complex numbers.  These possibilities
 * are repesented by different subclasses.  Some parameters are "animated"; animated
 * parameters implement the interface {@link vmm.core.Animateable}.
 */
abstract public class Parameter {

	/**
     * The value of this Parameter.  The type of object will be different in different
     * subclasses.   A null value object is taken to represent the number zero.
     */
	private Object value;
	
	/**
	 * When the value of the parameter is set from a string, that string is stored
	 * here.  When the value is set directly, valueString is set to null.  If valueString
	 * is non-null, then it is the official string representations of the parameter's value.
	 */
	private String valueString;
	
	/**
	 * The default value of this Parameter.  This is set the first time that the
	 * value object is set to a non-null value.  It can also be set by calling
	 * setDefaultValueObject() directly.
	 */
	private Object defaultValue;
	
	/**
	 * The default value of this parameter as a String. This is saved when the default
	 * value is set from a string; otherwise, it is null.
	 */
	private String defaultValueString;
	
	/**
	 * The object with which this Parameter is associated.  A Parameter can have only 
	 * one owner.
	 */
	private Parameterizable owner;
	
	/**
	 * The name of this parameter.  The name is for internal, programmatic use but
	 * might be mapped to a human-readable "title" in a proprties file. 
	 */
	private String name;
	
	/**
	 * Creates a parameter with a specified name and value object.
	 * The owner of the Parameter is initially
	 * set to null.  Ordinarily, it will be set automatically when the parameter is
	 * associated with Parameterizable object. This is done, for example, in the
	 * View and Exhibit classes, so that most programmers don't have to think about the owner.
	 * @param name The name for the parameter.  If null, the parameter has no name.
	 * @param value The initial value and default value for this parameter.  This should be of
	 * the correct type for the particular kind of parameter that is being constructed.  If
	 * this is null, the parameter is considered to have value zero.
	 */
	protected Parameter(String name, Object value) {
		if (value != null)
			setValueObject(value); // set value before owner to avoid calling parameterChanged.
		this.name = name;
	}
	
	/**
	 * Create a parameter with a specified name and value, where the value is given as
	 * a string.  The string will be run through the <code>stringToValueObject</code> method to convert
	 * the string to a numerical object; this can generate an error if the string does not   
	 * represent a legal value.  The owner of the Parameter is initially
	 * set to null.  Ordinarily, the owner will be set automatically when the parameter is
	 * associated with Parameterizable object.
	 * <p>For "animated" parameters, this constructor should also set the animation start and
	 * end values equal to the value.
	 * @param name The name for the parameter.  If null, the parameter has no name.
	 * @param valueAsString The value of the parameter, as a string that will be parsed to get the
	 * numeric value.  This value also becomes the default value of the parameter.  If null, the
	 * parameter is considered to have value zero.
	 */
	public Parameter(String name, String valueAsString) {
		if (valueAsString != null)
			setValueFromString(valueAsString);
		this.name = name;
	}
	
	/**
	 * Set both the value and the default value of the parameter to the specified value Object,
	 * as if that object had been passed to the constructor.  For animated parameters, this
	 * should include setting the animation start and end values and default values to the
	 * same value.
	 */
	protected void reset(Object value) {
		setDefaultValueObject(value);
		setValueObject(value);
	}

	/**
	 * Set both the value and the default value of the parameter to the value encoded in the given string,
	 * as if that string had been passed to the constructor.  For animated parameters, this
	 * should include setting the animation start and end values and default values to the
	 * same value. This is primarily meant to be called in the constructor of an Exhibit when it wants to
	 * change the value of a parameter that is inherited from a superclass.
	 * <p>Note: In this class, this method simply calls <code>setValueAndDefaultFromString(valueAsString)</code>.
	 * @see #reset(Object)
	 * @see #stringToValueObject(String)
	 */
	public void reset(String valueAsString) {
		setValueAndDefaultFromString(valueAsString);
	}
	
	/**
	 * Returns a string representation of this Parameter's value.  It shoudl be possible to pass the
	 * string that is returned by this method to the <code>setValueFromString</code> method to restore the value
	 * of the Parameter.  In the top-level Parameter class, the return value is computed using the
	 * <code>toString</code> method of the value object, except that if the value object is null, then
	 * the return value is the string "0".
	 * This should be OK, but subclasses can override this method if necessary.
	 * <p>This method is called in the Parameter class to convert betweeen the object and the string
	 * representation of a parameter's value and default value.
	 * @param obj A parameter value represented as an object.
	 * @return A string representation of the value.
	 * @see #stringToValueObject(String)
	 */
	protected String valueObjectToString(Object obj) {
		return (obj == null)? "0" : obj.toString();
	}

	/**
	 * Convert a string rerpesentation of a parameter value to a representation of that same value
	 * as an object.  The object will be of the correct type for this parameter, presumably Integer,
	 * Double, or Complex, depending on the parameter type.  The value should be obtained by
	 * parsing the string to allow for constant expressions such as "2*pi".  This parsing might generate
	 * an error.  If an error is generated, it should be of type NumberFormatException.
	 * @param str A string representation of a value for the parameter, possibly as a constant expression such as "2*pi"
	 * @return An object representing the value, presumably of type Integer, Double, or Complex.
	 */
	abstract protected Object stringToValueObject(String str);

	/**
	 * Returns the owner of this parameter.  The value will be null if this parameter has no owner.
	 */
	public Parameterizable getOwner() {
		return owner;
	}

	/**
	 * Sets the owner of this parameter.  The owner can be null, meaning that the parameter has no owner.
	 * This method is called automatically when the parameter is added to an Exhibit or View.
	 */
	public void setOwner(Parameterizable owner) {
		this.owner = owner;
	}

	/**
	 * Returns the value of this object as an Object.  The value can be null.
	 * It is not guaranteed that the value will be of the correct type, but subclasses
	 * might provide such a guarantee.
	 */
	public Object getValueObject() {
		return value;
	}
	
	/**
	 * Sets a new value for this parameter and calls the <code>parameterChanged</code> method in the
	 * parameter's owner (if there is an owner).  In this class, "owner.parameterChanged()" is called
	 * only if the value of the parameter actually does change; the equals() method is
	 * used to check for object equality.  No other method is provided for setting the
	 * private value object, so all changes to the value object must go throught this method.
	 * 
	 * @param value The new value of this parameter.  The value is set without checking whether
	 *    the value is of the correct type for this parameter.  A type-cast error might result
	 *    at some later time.  Subclasses might override this method to guarantee that the
	 *    object is of the correct type.
	 */
	public void setValueObject(Object value) {
		Object oldValue = this.value;
		this.value = value;
		valueString = null;
		if (value != null && defaultValue == null)
			setDefaultValueObject(value);
		if (owner != null) {
			if (oldValue == null) {
				if (value == null)
					return;
			}
			else if (oldValue.equals(value))
				return;
			owner.parameterChanged(this,oldValue,value);
		}
 	}
	
	/**
	 * Explicitely set the default value object for this Parameter.  The default
	 * value is also set, automatically, the first time that a non-null value object is
	 * specified, either in the constructor or in a call to setValueObject().  The
	 * value is set without checking whether the value of the correct type for this
	 * parameter.  Note that only a pointer to the specified value is saved -- if the
	 * object is modified later, the default value of the parameter will change.
	 * The default value is used only in the <code>getDefaultValueObject</code> method.
	 */
	public void setDefaultValueObject(Object object) {
		defaultValue = object;
		defaultValueString = null;
	}
	
	/**
	 * Returns the default value of this parameter.
	 * @see #setDefaultValueObject(Object)
	 */
	public Object getDefaultValueObject() {
		return defaultValue;
	}
	
	/**
	 * Sets the name of this object.  The name is used internally in programs.  It is not
	 * meant to be human-readable.  In many cases, it will be in a format that can be used as a key in
	 * a properties file, which can map the name to a human-readable "title".  The name can
	 * be null. A typical name might consist of the class name of the owner combined with
	 * a name for this particular parameter of the owner.  In general, the name of a parameter
	 * should uniquely identify the parameters among all parameters associated with an Exhibit and
	 * with any View that displays that Exhibit.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of this parameter.  The name can be null.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method is meant to return a human-readable title or description for this parameter (one
	 * that could be used in a menu, for example).  In this class, the title is obtained by calling
	 * the method I18n.tr(name) where "name" is the name of this parameter as returned by <code>getName</code>,
	 * or is "unnamed.parameter" if the name is null.  Note that as a result of the definition of <code>I18n.tr</code>,
	 * if the name is non-null and is not mapped to a property, then the title will be the same as the name.
	 * @see #setName(String)
	 * @see I18n#tr(String)
	 */
	public String getTitle() {
		String name = getName();
		if (name == null)
			return I18n.tr("unnamed.parameter");
		else
			return I18n.tr(name);
	}
	
	/**
	 * The text returned by this method, if any, is used as a ToolTip on the name of the parameter in
	 * parameter dialogs.  If the return value in this class is null, no ToolTip is associated with the parameter name.
	 * The method in this class returns the value of I18n.trIfFound(getName() + ".hint").  If the name
	 * is null, the return value is null.
	 */
	public String getHint() {
		String name = getName();
		String hint = null;
		if (name != null)
			hint =  I18n.trIfFound(name + ".hint");
		return hint;
	}
	
	/**
	 * Returns a non-null string representation of this Parameter's value.  It should be
	 * possible to use the returned value as a parameter to <code>setValueFromString</code> to restore
	 * the parameter to the same value.  The value is obtained as follows:  If this parameter's
	 * value was set most recently from a String, then that string is returned.  Otherwise,
	 * if the parameter's value object is null, then the string "0" is returned.  And if not,
	 * then the return value is obtained by applying the <code>valueObjectToString</code> method
	 * to the value object.
	 * @see #valueObjectToString(Object)
	 */
	final public String getValueAsString() {
		if (valueString != null)
			return valueString;
		else if (value == null)
			return "0";
		else
			return valueObjectToString(value);
	}
	
	/**
	 * Returns a non-null string representation of this Parameter's default value.
	 * If the Parameter's default value was set from a String, then that string is returned.
	 * Otherwise, if this Parameter's default value object is null, the return value is the string "0",
	 * and if not, then the return value is constructed by applying the <code>valueObjectToString</code> method
	 * to the default value object.
	 */
	public String getDefaultValueAsString() {
		if (defaultValueString != null)
			return defaultValueString;
		else if (defaultValue == null)
			return "0";
		else 
			return valueObjectToString(defaultValue);
	}
	
	/**
	 * Sets the value of this parameter from a string representation of the value.  The actual
	 * value is obtained from the <code>stringToValueObject</code> method.
	 * @param valueAsString A string representing the new value for this parameter.  This string
	 * is passed to the <code>stringToValueObject</code> method to get the actual parameter value.
	 * This might result in a NumberFormatExcpetion or other error if the string does not represent
	 * a legal value for this parameter.
	 * @see #stringToValueObject(String)
	 */
	final public void setValueFromString(String valueAsString) {
		boolean newDefaultVal = (defaultValue == null);
		setValueObject(stringToValueObject(valueAsString));
		valueString = valueAsString;  // The previous line will have set valueString to null
		if (newDefaultVal)
			defaultValueString = valueAsString;
	}
	
	/**
	 * Sets the default value of this parameter from a string representation of the value.  The actual
	 * value is obtained from the <code>stringToValueObject</code> method.
	 * @param valueAsString A string representing the new default value for this parameter.  This string
	 * is passed to the <code>stringToValueObject</code> method to get the actual default value.  This
	 * might result in an error if the string does not represent a legal value.
	 * @see #stringToValueObject(String)
	 */
	final public void setDefaultValueFromString(String valueAsString) {
		setDefaultValueObject(stringToValueObject(valueAsString));
		defaultValueString = valueAsString;
	}
	
	/**
	 * Sets both the value and the default value of this parameter from a string representation of the
	 * value.  
	 * @param valueAsString  string representing the value.  This string
	 * is passed to the <code>stringToValueObject</code> method to get the actual value.
	 */
	final public void setValueAndDefaultFromString(String valueAsString) {
		Object obj = stringToValueObject(valueAsString);
		setValueObject(obj);
		valueString = valueAsString;
		setDefaultValueObject(obj);
		defaultValueString = valueAsString;
	}
	
	/**
	 * Returns an input box for getting the value, animation start value, or animation
	 * end value from the user.
	 * @param inputType One of the constants {@link ParameterInput#VALUE},
	 *   {@link ParameterInput#ANIMATION_START} or {@link ParameterInput#ANIMATION_END},
	 *   indicating which type of value this method is meant to return.
	 * @return a ParameterInput consructed by calling <code>new ParameterInput(this,inputType)</code>.
	 */
	public ParameterInput createParameterInput(int inputType) {
		return new ParameterInput(this,inputType);
	}
	
	/**
	 * Returns a string including both the title and value of this Parameter.
	 * The string is constructed as: getTitle() + "(" + getValueAsString() + ")".
	 * @see #getValueAsString()
	 */
	public String toString() {
		return getTitle() + "(" + getValueAsString() + ")";
	}
	
}
