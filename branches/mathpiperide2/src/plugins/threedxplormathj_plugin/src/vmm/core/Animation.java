/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import javax.swing.event.ChangeListener;

/**
 * The common interface for two different types of animations, ThreadedAnimation and TimerAnimation.
 * Any Animation can be started, stopped, and paused.  Its speed can be modified using the
 * <code>setTimeDilation(double)</code> method.  All animations send change events when they are
 * stopped or started.  Although animations can run on their own, most animations will probably
 * be associated with Displays; see the {@link Display} for more information on this,
 * in particular {@link Display#installAnimation(Animation)}.
 * See the {@link ThreadedAnimation} class and the {@link TimerAnimation} class 
 * for information about writing animations. 
 */
public interface Animation {
	
	/**
	 * Start the animation running.  Animations don't start automatically.  The start() method must be
	 * called in order to start the animation.
	 */
	public void start();
	
	/**
	 * Stop the Animation.  Some animations will continue indefinitely until this method is called;
	 * others will stop automatically after some period of time.
	 */
	public void cancel();
	
	/**
	 * Tests whether the Animation is still running.  An animation is running from the time its
	 * start() method is called until either it stops on its own or its cancel() method is called.
	 * An animation that has been paused is still considered to be running.
	 */
	public boolean isRunning();
	
	/**
	 * Sets whether a running animation is paused or not.  Has no effect on an animation that is not running.
	 * @param paused whether the running animation should be in the paused state.
	 */
	public void setPaused(boolean paused);
	
	/**
	 * Tells whether the animation is paused.
	 * @return returns true if the animation is running but is in a paused state; returns false if it
	 * is not running or if it is running but is not paused.
	 */
	public boolean isPaused();
	
	/**
	 * Sets a factor that is multiplied by all time period specifications.  A value greater than one will slow
	 * down the animation; a value less than one will speed it up (although only within the limit imposed by
	 * the actual processing time needed to run the animation).
	 * @param dilationFactor A non-negative number that will be multiplied by all time measurements.
	 */
	public void setTimeDilation(double dilationFactor);
	
	/**
	 * Gets the factor that is multiplied by all time period specifications.  Defatult value is 1, meaning normal
	 * running speed for the animation.
	 */
	public double getTimeDilation();
	
	/**
	 * Register a ChangeListener to receive change events from the animation.  An animation sends a change event
	 * at least when it starts and when it stops.
	 * @param listener a ChangeListener that is registered to receive change events from this animation.
	 */
	public void addChangeListener(ChangeListener listener);
	
	/**
	 * De-register a ChangeListener from this animation, if it is currently registered to receive change events.
	 * @param listener to be de-registered as a ChangeListener from this animation.
	 */
	public void removeChangeListener(ChangeListener listener);

	/**
	 * Returns text to be displayed in the display's status bar while this animation is installed.
	 * @param running Tells whether the animation is running or paused, so text can depend on this value.
	 * @return the text to displayed, or null to use the default text "Animation Running" or "Animation Paused"
	 */
	public String getStatusText(boolean running);

}
