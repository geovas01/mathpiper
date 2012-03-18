/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

/**
 * A parameter that has a Complex value and a start and end value to be used for animation.
 */
public class ComplexParamAnimateable extends ComplexParam implements Animateable {
	
	private Complex animationStart, animationEnd;
	private Complex defaultAnimationStart, defaultAnimationEnd;
	
	private String animationStartString, animationEndString;
	private String defaultStartString, defaultEndString;
	
	/**
	 * Creates a ComplexParamAnimateable that initially has no name and has value and default value equal to 0,
	 * with animation limits also equal to 0.
	 */
	public ComplexParamAnimateable() {
		this(null,(Complex)null);
	}
	
	/**
	 * Create a ComplexParamAnimateable with a specified name and initial value and with
	 * animation start and end values equal to the same value
	 * @param name The name of the Parameter, which can be null but ordinarily should not be.
	 * The name shoud identify the Parameter uniquely among parameters associated with a given
	 * Parameterizeable object.
	 * @param value The initial value of the parameter.  This also becomes its default value.
	 * A null value is treated as zero.
	 */
	public ComplexParamAnimateable(String name, Complex value) {
		this(name, value, value, value);
	}
	
	/**
	 * Create a ComplexParamAnimateable with a specified name, initial value, and start and end values.
	 * @param name The name of the Parameter, which can be null but ordinarily should not be.
	 * The name shoud identify the Parameter uniquely among parameters associated with a given
	 * Parameterizeable object.
	 * @param value The initial value of the parameter.  This also becomes its default value.
	 * A null value is treated as zero.
	 * @param start the animation start value for this parameter.
	 * A null value is treated as zero.
	 * @param end the animation end value for this parameter.
	 * A null value is treated as zero.
	 */
	public ComplexParamAnimateable(String name, Complex value, Complex start, Complex end) {
		super(name,value);
		animationStart = defaultAnimationStart = new Complex(start);
		animationEnd = defaultAnimationEnd = new Complex(end);
	}
	
	/**
	 * Create a ComplexParamAnimateable with a specified name and initial value and with
	 * animation start and end values equal to the same value.
	 * @param name The name of the Parameter, which can be null but ordinarily should not be.
	 * The name shoud identify the Parameter uniquely among parameters associated with a given
	 * Parameterizeable object. 
	 * @param valueAsString The initial value of the parameter, given as a string. An error
	 * of type NumberFormatException occurs if this string does not represent a legal value.
	 */
	public ComplexParamAnimateable(String name, String valueAsString) {
		this(name, valueAsString, valueAsString, valueAsString);
	}
	
	/**
	 * Create a ComplexParamAnimateable with a specified name, initial value, and start and end values.
	 * The values are specified as strings.
	 * @param name The name of the Parameter, which can be null but ordinarily should not be.
	 * The name shoud identify the Parameter uniquely among parameters associated with a given
	 * Parameterizeable object.
	 * @param valueAsString The initial value of the parameter, given as a string. An error
	 * of type NumberFormatException occurs if this string does not represent a legal value.
	 * @param startString the animation start value given as a string.  A NumberFormatError can
	 * occur, if this is not a legal value.
	 * @param endString the animation end value given as a string.  A NumberFormatError can
	 * occur, if this is not a legal value.
	 */
	public ComplexParamAnimateable(String name, String valueAsString, String startString, String endString) {
		super(name,valueAsString);
		setAnimationStartFromString(startString);
		setAnimationEndFromString(endString);
		setDefaultAnimationStartFromString(startString);
		setDefaultAnimationEndFromString(endString);
	}
	
	/**
	 * Sets both the default value and the value of this parameter to the specified complex number.
	 * In addition the values and default values for the animation start and end properities
	 * are also set to the same value.
	 * This is primarily meant to be called in the constructor of an Exhibit when it wants to
	 * change the value of a parameter that is inherited from a superclass.
	 * @see Parameter#reset(String)
	 */
	public void reset(Complex value) {
		value = new Complex(value);
		super.reset(value);
		setAnimationLimitsAndDefaults(value,value);
	}
	
	/**
	 * Sets both the value and default value of the parameter, as well as the value and default
	 * value of the animation start and end properties.
	 */
	public void reset(Complex value, Complex animationStart, Complex animationEnd) {
		super.reset(new Complex(value));
		setAnimationLimitsAndDefaults(new Complex(animationStart), new Complex(animationEnd));
	}
	
	/**
	 * Set both the value and the default value of the parameter to the value encoded in the given string,
	 * as if that string had been passed to the constructor.
	 * In addition the values and default values for the animation start and end properities
	 * are also set to the same value.
	 * This is primarily meant to be called in the constructor of an Exhibit when it wants to
	 * change the value of a parameter that is inherited from a superclass.
	 * @see #stringToValueObject(String)
	 */
	public void reset(String valueAsString) {
		reset(valueAsString,valueAsString,valueAsString);
	}
	
	/**
	 * Sets both the value and default value of the parameter, as well as the value and default
	 * value of the animation start and end properties.
	 */
	public void reset(String valueAsString, String animationStartAsString, String animationEndAsString) {
		super.reset(valueAsString);
		setAnimationStartFromString(animationStartAsString);
		setAnimationEndFromString(animationEndAsString);
		setDefaultAnimationStartFromString(animationStartAsString);
		setDefaultAnimationEndFromString(animationEndAsString);
	}
	
	public Complex getDefaultAnimationStart() {
		return defaultAnimationStart;
	}
	
	public Complex getDefaultAnimationEnd() {
		return defaultAnimationEnd;
	}
	
	public void setDefaultAnimationLimits(Complex defaultStart, Complex defaultEnd) {
		defaultAnimationStart = new Complex(defaultStart);
		defaultAnimationEnd = new Complex(defaultEnd);
		defaultStartString = defaultEndString = null;
	}
	
	public String getDefaultAnimationStartAsString() {
		return (defaultStartString != null)? defaultStartString : Util.toExternalString(defaultAnimationStart);
	}
	
	public String getDefaultAnimationEndAsString() {
		return (defaultEndString != null)? defaultEndString : Util.toExternalString(defaultAnimationEnd);
	}
	
	public void setDefaultAnimationStartFromString(String startString) {
		Complex d = (Complex)stringToValueObject(startString);
		defaultAnimationStart = d;
		defaultStartString = startString;
	}
	
	public void setDefaultAnimationEndFromString(String endString) {
		Complex d = (Complex)stringToValueObject(endString);
		defaultAnimationEnd = d;
		defaultEndString = endString;
	}
	
	public void setAnimationLimits(Complex start, Complex end) {
		animationStart = new Complex(start);
		animationEnd = new Complex(end);
	}
	
	public void setAnimationLimitsAndDefaults(Complex start, Complex end) {
		setAnimationLimits(start,end);
		setDefaultAnimationLimits(start,end);
	}
	
	public void setAnimationStart(Complex x) {
		animationStart =  new Complex(x);
		animationStartString = null;
	}
	
	public void setAnimationEnd(Complex x) {
		animationEnd = new Complex(x);
		animationEndString = null;
	}
	
	public Complex getAnimationStart() {
		return animationStart;
	}
	
	public Complex getAnimationEnd() {
		return animationEnd;
	}
	
	public void setAnimationStartFromString(String startVal) {
		Complex d = (Complex)stringToValueObject(startVal);
		animationStart = d;
		animationStartString = startVal;
	}

	public void setAnimationEndFromString(String endVal) {
		Complex d = (Complex)stringToValueObject(endVal);
		animationEnd = d;
		animationEndString = endVal;
	}

	public String getAnimationStartAsString() {
		return (animationStartString != null)? animationStartString : Util.toExternalString(animationStart);
	}

	public String getAnimationEndAsString() {
		return (animationEndString != null)? animationEndString : Util.toExternalString(animationEnd);
	}


	public void setFractionComplete(double fractionComplete) {
		setValueObject(new Complex(animationStart.re + fractionComplete*(animationEnd.re - animationStart.re),
				animationStart.im + fractionComplete*(animationEnd.im - animationStart.im)));
	}

	public boolean reallyAnimated() {
		return ! animationStart.equals(animationEnd);
	}


}
