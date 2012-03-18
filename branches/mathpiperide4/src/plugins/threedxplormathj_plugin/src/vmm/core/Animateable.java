/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

/**
 * This interface is implemented by "Animateable" Parameters such as {@link vmm.core.RealParamAnimateable}.
 * It includes the <code>setFractionComplete</code> method, which is used for morphing. It
 * also includes several methods for getting and setting the start and end values for the
 * animation; these methods are used in several dialogs, such as {@link vmm.core.AnimationLimitsDialog}.
 * Note that an Animateable object could be used in other
 * places besides in actual animations.  For example, frames might be selected
 * with a slider or with Prev and Next buttons, or <code>fractionComplete</code> might even
 * be called directly.  In general, however, this interface will not be used by ordinary programmers.
 */
public interface Animateable {

	/**
	 * Called during the course of a morphing animation so that the Animateable object
	 * can adjust itself to the current stage of the animation.  In fact,
	 * an Animateable Parameter could set its value to "<code>startValue + fractionComplete * (endValue - startValue)</code>"
	 * when this metod is called.
	 * @param fractionComplete A number in the range 0 to 1 that tells what
	 *    stage the animation is at.  The Animateable object adjusts itself
	 *    accordingly.
	 * @see BasicAnimator
	 */
	public void setFractionComplete(double fractionComplete);
	
	/**
	 * Set the starting Animation value for this Animateable object from a string.  This method will probably
	 * only be used by Dialog boxes such as {@link AnimationLimitsDialog}.
	 * @param startVal The start value, as a string.  This string will be parsed as a constant expression,
	 * and a NumberFormatException will be thrown if the string is not a legal expression.
	 */
	public void setAnimationStartFromString(String startVal);
	
	/**
	 * Set the ending Animation value for this Animateable object from a string. 
	 * @param endVal The end value, as a string.  This string will be parsed as a constant expression,
	 * and a NumberFormatException will be thrown if the string is not a legal expression.
	 */
	public void setAnimationEndFromString(String endVal);
	
	/**
	 * Returns a string representation of the start value for the animation.
	 */
	public String getAnimationStartAsString();
	
	/**
	 * Returns a string representation of the end value for the animation.
	 */
	public String getAnimationEndAsString();

	/**
	 * Sets the default start animation value for this Animateable object from a string
	 * representation of the value.  The default values are used, for example, when the user clicks the
	 * "Defaults" button in {@link vmm.core.AnimationLimitsDialog}.
	 * @param startVal The default start value, as a string.  This string will be parsed as a constant expression,
	 * and a NumberFormatException will be thrown if the string is not a legal expression.
	 */
	public void setDefaultAnimationStartFromString(String startVal);

	/**
	 * Sets the default ending animation value for this Animateable object from a string
	 * representation of the value.  The default values are used, for example, when the user clicks the
	 * "Defaults" button in {@link vmm.core.AnimationLimitsDialog}.
	 * @param endVal The default end value, as a string.  This string will be parsed as a constant expression,
	 * and a NumberFormatException will be thrown if the string is not a legal expression.
	 */
	public void setDefaultAnimationEndFromString(String endVal);

	/**
	 * Returns a string representation of the default start value for the animation.
	 */
	public String getDefaultAnimationStartAsString();
	
	/**
	 * Returns a string representation of the default end value for the animation.
	 */
	public String getDefaultAnimationEndAsString();
	
	/**
	 * Check whether this Animateable will really change during an animation.
	 * (If the start and end values are the same, then the return value will
	 * presumably be false.)
	 */
	public boolean reallyAnimated();

		
}
