/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import javax.swing.JTextField;

import vmm.functions.ParseError;
import vmm.functions.Parser;

/**
 * An input box that lets the user enter a value for a {@link vmm.core.Parameter}.
 * A ParameterInput box can be used to get a value for a parameter, or the animation start
 * value or animation end value for an {@link vmm.core.Animateable} parameter.
 */
public class ParameterInput extends JTextField {
	
	private Parameter param;  // the parameter whose value is to be input
	private int inputType;
	private String originalString;
	
	private static Parser parser = new Parser();
	
	/**
	 * Indicates that this input box is used to input a parameter's value.
	 * This constant is for use in the constructor {@link #ParameterInput(Parameter, int)}.
	 */
	public final static int VALUE = 0;
	
	/**
	 * Indicates that this input box is used to input the animation start value for an Animateable parameter.
	 * This constant is for use in the constructor {@link #ParameterInput(Parameter, int)}.
	 */
	public final static int ANIMATION_START = 1;

	/**
	 * Indicates that this input box is used to input the animation end value for an Animateable parameter.
	 * This constant is for use in the constructor {@link #ParameterInput(Parameter, int)}.
	 */
	public final static int ANIMATION_END = 2;
	
	/**
	 * Creates a text box for inputting the value of a specified parameter.
	 * @param param The non-null parameter whose value is to be input.
	 */
	public ParameterInput(Parameter param) {
		this(param, VALUE);
	}
	
	/**
	 * Creates a text box for inputting the value, animation start value, or animation end value
	 * of a specified parameter.  Animation start and end values can only be input for animated
	 * parameters such as {@link RealParamAnimateable}.
	 * @param param The non-null parameter whose value or other property is to be input.
	 * @param propertyToInput The property of the parameter that is to be input. This must be one of:
	 * ParameterInput.VALUE, ParameterInput.ANIMATION_START, or ParameterInput.ANIMATION_END.
	 * Illegal vaues have the same effect as ParameterInput.VALUE.
	 */
	public ParameterInput(Parameter param, int propertyToInput) {
		super(12);
		this.param = param;
		if (param instanceof Animateable ) {
			inputType = propertyToInput;
			if (inputType != VALUE && inputType != ANIMATION_END && inputType != ANIMATION_START)
				inputType = VALUE;
			switch (inputType) {
				case VALUE:
					originalString = param.getValueAsString();
					break;
				case ANIMATION_START:
					originalString = ((Animateable)param).getAnimationStartAsString();
					break;
				default:
					originalString = ((Animateable)param).getAnimationEndAsString();
					break;
			}
		}
		else {
			inputType = VALUE;
			originalString = param.getValueAsString();
		}
		setText(originalString);
		String tooltip = null;
		if (param instanceof IntegerParam) {
			tooltip = "<html>" + I18n.tr("vmm.core.ParameterInput.isInteger");
			int min = ((IntegerParam)param).getMinimumValueForInput();
			int max = ((IntegerParam)param).getMaximumValueForInput();
			if (max == Integer.MAX_VALUE) {
				if (min == 1)
					tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.positive");
				else if (min == 0)
					tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.nonnegative");
				else if (min > Integer.MIN_VALUE)
					tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.greater",min);
				}
			else if (min == Integer.MIN_VALUE)
				tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.less",max);
			else
				tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.range",min,max);
		}
		else if (param instanceof RealParam) {
			tooltip = "<html>" + I18n.tr("vmm.core.ParameterInput.isReal");
			double min = ((RealParam)param).getMinimumValueForInput();
			double max = ((RealParam)param).getMaximumValueForInput();
			if (max == Double.POSITIVE_INFINITY) {
				if (min == Double.MIN_VALUE)
					tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.positive");
				else if (min == 0)
					tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.nonnegative");
				else if (min > Double.NEGATIVE_INFINITY)
					tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.greater",min);
				}
			else if (min == Double.NEGATIVE_INFINITY)
				tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.less",max);
			else
				tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.range",min,max);
		}
		else if (param instanceof ComplexParam) {
			tooltip = "<html>" + I18n.tr("vmm.core.ParameterInput.isComplex");
			Complex min = ((ComplexParam)param).getMinimumValueForInput();
			Complex max = ((ComplexParam)param).getMaximumValueForInput();
			if (min.re == Double.NEGATIVE_INFINITY && max.re < Double.POSITIVE_INFINITY)
				tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.less.realpart",max.re);
			else if (min.re > Double.NEGATIVE_INFINITY && max.re == Double.POSITIVE_INFINITY)
				tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.greater.realpart",min.re);
			else if (min.re > Double.NEGATIVE_INFINITY && max.re < Double.POSITIVE_INFINITY)
				tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.range.realpart",min.re,max.re);
			if (min.im == Double.NEGATIVE_INFINITY && max.im < Double.POSITIVE_INFINITY)
				tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.less.imaginarypart",max.im);
			else if (min.im > Double.NEGATIVE_INFINITY && max.im == Double.POSITIVE_INFINITY)
				tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.greater.imaginarypart",min.im);
			else if (min.im > Double.NEGATIVE_INFINITY && max.im < Double.POSITIVE_INFINITY)
				tooltip += "<br>" + I18n.tr("vmm.core.ParameterInput.range.imaginarypart",min.im,max.im);
		}
		setToolTipText(tooltip);
	}
	
	/**
	 * Sets the preferred size to the specified number of columns.  In addtion, if the content
	 * has not been modified and its length is too big for the preferred size, then the content
	 * is truncated to a size that will fit in the specified number of columns, if possible.
	 * (The content is changed only in the case of a RealParam.)
	 */
	public void setColumns(int columns) {
		super.setColumns(columns);
		String content = getText();
		if (content.length() < columns || !content.equals(originalString) || !(param instanceof RealParam))
			return;
		double value;
		switch (inputType) {
		case VALUE:
			value = ((RealParam)param).getValue();
			break;
		case ANIMATION_START:
			value = ((RealParamAnimateable)param).getAnimationStart();
			break;
		default:
			value = ((RealParamAnimateable)param).getAnimationEnd();
			break;
		}
		String str = String.format("%"+columns+"g",value);
		setText(str);
		originalString = str;
	}
	
	/**
	 * Returns the parameter whose value is input by this ParameterInput.
	 */
	public Parameter getParameter() {
		return param;
	}
	
	/**
	 * Checks whether the contents of the text box represent a legal value for the parameter.
	 * Calling this method does not change the value of the parameter.
	 * @return returns null if the string in the text box is a legal value for the parameter, or an
	 * error message if the content is not legal
	 * @see #setValueFromContents()
	 */
	public String checkContents() {
		String contents = getText();
		double val = 0;
		Complex cval = null;
		try {
			if (param instanceof ComplexParam)
				cval = parser.parseComplexExpression(contents).value();
			else
				val = parser.parse(contents).value();
		}
		catch (ParseError e) {
			requestFocus();
			selectAll();
			if (inputType == VALUE)
				return I18n.tr("vmm.core.ParameterInput.badExpression",  param.getTitle(), contents, e.getMessage() );
			else if (inputType == ANIMATION_START)
				return I18n.tr("vmm.core.ParameterInput.badStartExpression", param.getTitle(), contents, e.getMessage() );
			else
				return I18n.tr("vmm.core.ParameterInput.badEndExpression",  param.getTitle(), contents, e.getMessage() );
		}
		if ( (cval == null && (Double.isNaN(val) || Double.isInfinite(val))) ||
				(cval != null && (Double.isNaN(cval.re) || Double.isInfinite(cval.re) || Double.isNaN(cval.im) || Double.isInfinite(cval.im))) ){
			requestFocus();
			selectAll();
			if (inputType == VALUE)
				return I18n.tr("vmm.core.ParameterInput.undefinedExpression", param.getTitle(), contents);
			else if (inputType == ANIMATION_START)
				return I18n.tr("vmm.core.ParameterInput.undefinedStartExpression", param.getTitle(), contents);
			else
				return I18n.tr("vmm.core.ParameterInput.undefinedEndExpression", param.getTitle(), contents);
		}
		if (param instanceof IntegerParam) {
			IntegerParam intParam = (IntegerParam)param;
			int intval;
			if (val > 0)
				intval = (int)(val + 1e-8);
			else
				intval = (int)(val - 1e-8);
			if (Math.abs(val - intval) > 5e-9) {
				requestFocus();
				selectAll();
				return I18n.tr("vmm.core.ParameterInput.badint", param.getTitle());
			}
			if (intval < intParam.getMinimumValueForInput() || intval > intParam.getMaximumValueForInput()) {
				requestFocus();
				selectAll();
				return I18n.tr("vmm.core.ParameterInput.intOutOfRange", param.getTitle(),
						""+intParam.getMinimumValueForInput(),""+intParam.getMaximumValueForInput());
			}
		}
		else if (param instanceof RealParam) {
			RealParam realParam = (RealParam)param;
			if (val < realParam.getMinimumValueForInput() ||val > realParam.getMaximumValueForInput()) {
				requestFocus();
				selectAll();
				if (realParam.getMinimumValueForInput() == Double.MIN_VALUE) {
					if (realParam.getMaximumValueForInput() == Double.POSITIVE_INFINITY)
						return I18n.tr("vmm.core.ParameterInput.rangeError1", param.getTitle());
					else 
						return I18n.tr("vmm.core.ParameterInput.rangeError2", param.getTitle(), ""+realParam.getMaximumValueForInput());
				}
				else if (realParam.getMaximumValueForInput() == Double.POSITIVE_INFINITY)
					return I18n.tr("vmm.core.ParameterInput.rangeError3", param.getTitle(), ""+realParam.getMinimumValueForInput());
				else if (realParam.getMinimumValueForInput() == Double.NEGATIVE_INFINITY)
					return I18n.tr("vmm.core.ParameterInput.rangeError4", param.getTitle(), ""+realParam.getMaximumValueForInput());
				else
					return I18n.tr("vmm.core.ParameterInput.rangeError5", param.getTitle(), ""+realParam.getMinimumValueForInput(),
							""+realParam.getMaximumValueForInput());
			}
		}
		else if (param instanceof ComplexParam) {
			ComplexParam realParam = (ComplexParam)param;
			if (cval.re < realParam.getMinimumValueForInput().re || cval.re > realParam.getMaximumValueForInput().re ||
					cval.im < realParam.getMinimumValueForInput().im || cval.im > realParam.getMaximumValueForInput().im) {
				requestFocus();
				selectAll();
				return I18n.tr("vmm.core.ParameterInput.rangeErrorComplex");
			}
		}
		return null;
	}
	
	/**
	 * Sets the parameter's value, animation start value, or animation end value (as appropriate) from the contents of the text box.
	 * The parameter is not touched if the contents of the text box has not been modified since it was created (or since
	 * a previous call to this method or to {@link #setValueAndDefaultFromContents()}, if there was one).  It is assumed
	 * that the <code>checkContents</code> method is called before this method is called, so if an error occurs while the
	 * value is being set, it is simply ignored and the parameter value is not changed.
	 * @see #checkContents()
	 */
	public void setValueFromContents() {
		String contents = getText();
		if (contents.equals(originalString))
			return;
		try {
			switch (inputType) {
			case VALUE:
				param.setValueFromString(contents);
				break;
			case ANIMATION_START:
				((Animateable)param).setAnimationStartFromString(contents);
				break;
			default:
				((Animateable)param).setAnimationEndFromString(contents);
			}
			originalString = contents; // Reset "original state" to match new value.
		}
		catch (Exception e) {
		}
	}
	
	/**
	 * Sets the parameter's value, animation start value, or animation end value (as appropriate) from the contents of the text box,
	 * and also sets the corresponding default for the parameter to the same value.
	 * The parameter is not touched if the contents of the text box has not been modified since it was created (or since
	 * a previous call to this method or to {@link #setValueFromContents()}, if there was one).  It is assumed
	 * that the <code>checkContents</code> method is called before this method is called, so if an error occurs while the
	 * value is being set, it is simply ignored and the parameter value is not changed.
	 * @see #checkContents()
	 */
	public void setValueAndDefaultFromContents() {
		String contents = getText();
		if (contents.equals(originalString))
			return;
		try {
			switch (inputType) {
			case VALUE:
				param.setValueAndDefaultFromString(contents);
				break;
			case ANIMATION_START:
				((Animateable)param).setAnimationStartFromString(contents);
				((Animateable)param).setDefaultAnimationStartFromString(contents);
				break;
			default:
				((Animateable)param).setAnimationEndFromString(contents);
			((Animateable)param).setDefaultAnimationEndFromString(contents);
			}
			originalString = contents; // Reset "original state" to match new value.
		}
		catch (Exception e) {
		}
	}
	
	/**
	 * Reset the contents of the text box to its original value.  The original string is taken from the parameter when this
	 * object is constructed.
	 */
	public void revert() {
		setText(originalString);
	}
	
	/**
	 * Set the contents of the text box to the appropriate default value, as stored in the Parameter.
	 */
	public void defaultVal() {
		switch (inputType) {
			case VALUE:
				setText(param.getDefaultValueAsString());
				break;
			case ANIMATION_START:
				setText(((Animateable)param).getDefaultAnimationStartAsString());
				break;
			default:
				setText(((Animateable)param).getDefaultAnimationEndAsString());
			break;
		}
	}

}
