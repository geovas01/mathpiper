/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * An animation that runs in its own thread, separate from the Swing user interface
 * thread.  Because Swing is not thread-safe and the animation runs in its own thread,
 * it is not safe to call arbitrary Swing and Graphics2D methods.  (If it is necessary
 * to call them, the <code>invokeAndWait</code> or <code>invokeLater</code> methods
 * from class <code>javax.swing.SwingUtilities</code> can be used.)  However, calling
 * <code>repaint</code> is OK.  Since setting parameter values and adding or removing
 * decorations generate calls to <code>repaint</code>, they are also safe.
 * Note that an animation does not start running automatically, but only when its
 * {@link #start()} method is called.
 * <p>To create a ThreadedAnimation, it is usually only necessary to create a subclass
 * and implement {@link #runAnimation()}, the single abstract method defined in this
 * class.  This method is a script for the animation, which is run from beginning to end.
 * When this method returns, the animation ends; if the method never returns, then the
 * animation must be canceled by some external agent that calls its <code>cancel</code>
 * method.
 * <p>The <code>runAnimation</code> method can call the {@link #pause(int)} method to insert
 * a pause into the animation.  Behind the scenes, this method also checks to see whether
 * the animation has been canceled.  If so, it throws an exception that aborts the animation.
 * To avoid delays between the time when the animation's <code>cancel</code> method is called and
 * tha time when the animation actually stops, it is important that <code>pause</code> be called
 * regularly.  If no delay is desired, pause can be called with a parameter of zero.
 * <p>Some animations will have some "clean-up" to do when the animation ends, whether it
 * ends because the <code>runAnimation</code> method returns or because it has been canceled.
 * to make sure that the clean-up is done in all cases, it is advisable to do the clean-up in
 * a <code>finally</code> clause in the <code>runAnimation</code> method.  For a simple
 * example of this, see {@link vmm.planecurve.parametric.OsculatingCircleAnimation#runAnimation()}.
 * <p>A ThreadedAnimation emits ChangeEvents when it is started and when it ends.  A ChangeListener
 * can tell which event generated the ChangeEvent by calling the <code>isRunning</code> method of
 * the animation.  Note that in some circumstances, an alternative method for doing set-up and
 * clean-up for the animation is to install a ChangeListener that does the set-up/clean-up
 * in response to ChangeEvents.
 */
abstract public class ThreadedAnimation implements Animation {
	
	private Thread runner;
	volatile private boolean running, canceled, paused;
	volatile double timeDilation;
	
	private ChangeEvent changeEvent;
	private ArrayList<ChangeListener> changeListeners;
	
	/**
	 * The animation consists of running this method. To create an animation, it is generally
	 * only necessary to implement this method.  This method should call <code>pause</code>
	 * regularly.  The animation ends when this method returns or the first time <code>pause</code> is
	 * called after the <code>cancel</code> method has been called; the <code>pause</code> method
	 * generates an {@link AnimationCanceledException} in this case.  If there is clean-up that
	 * must be done when the animation ends, it is advisable to do it in a <code>finally</code>
	 * clause in this method.
	 * <p>(Note that if some error other than an <code>AnimationCanceledException</code> occurs
	 * during the animation, it will also abort the animation.  Since this is presumably a programming
	 * error, a stack trace for the exception is printed to standard output.)
	 * @see #pause(int)
	 * @see #cancel()
	 */
	abstract protected void runAnimation();
	
	/**
	 * A trivial exception class that exists only to make it possible to cancel ThreadAnimations.
	 * If an animation's <code>cancel</code> method is called, then an AnimationCanceledException is
	 * thrown the next time <code>pause</code> is called.  (Programming note:  The <code>cancel</code>
	 * method can't throw the exception itself because it is presumably being called in another
	 * thread -- the exception would abort that thread instead of the animation thread.)
	 */
	protected class AnimationCanceledException extends RuntimeException {
	}

	/**
	 * Can be called in <code>runAnimation</code> to insert a delay into an animation.  This method
	 * also checks whether the <code>cancel</code> method has been called.  If so, it will throw
	 * an excpetion of type {@link AnimationCanceledException}.  This method can be called with
	 * a parameter of zero to check for cancelation without inserting a delay (in this case, it
	 * calls <code>Thread.yield</code> to give other threads that are waiting for CPU time 
	 * an opportunity to run.  It is important that this method be called regularly during the course
	 * of an animation so that cancelations will take effect in a timely manner.
	 * <p>In addition to checking for cancellation, this method also checks to see whether the
	 * animation has been paused by calling its <code>setPaused</code> method.  If so, this
	 * method will wait for the animtion to be unpaused before returning.  Again, it is important
	 * for <code>pause</code> to be called regularly for <code>setPaused</code> to take effect
	 * in a timely way.
	 * @param milliseconds a delay equal to this many milliseconds, if the animation is running at normal speed,
	 * is inserted into the animation.  If a time dilation factor other than 1 has been set, however, then
	 * this parameter is multiplied by the time dilation factor to give the actual number of milliseconds
	 * used in the delay.  A delay of zero will cause <code>Thread.yield</code> to be called, which gives
	 * other threads a chance to run.
	 * @see #setTimeDilation(double)
	 * @see #runAnimation()
	 * @see #cancel()
	 * @see #setPaused(boolean)
	 */
	protected void pause(int milliseconds) throws AnimationCanceledException {
		if (canceled)
			throw new AnimationCanceledException();
		int delay = (int)(milliseconds*timeDilation + 0.49);
		if (milliseconds > 0 && delay == 0)
			delay = 1;
		if (delay <= 0)
			Thread.yield();
		else {
			synchronized(this) {
				try {
					wait(delay);
				}
				catch (InterruptedException e) {
				}
			}
		}
		if (canceled)
			throw new AnimationCanceledException();
		if (paused) {
			synchronized(this) {
				while (paused && !canceled) {
					try {
						wait();
					}
					catch (InterruptedException e) {
					}
				}
			}
			if (canceled)
				throw new AnimationCanceledException();
		}
	}

	/**
	 * This method must be called to start the animation running.  An animation does not start
	 * automatically, but only when this method is called.  If the animation is already running,
	 * this has no effect.  If the animation has already run and ended, this will restart thea
	 * animation from the beginning.  The animation will run until the <code>runAnimation</code>
	 * ends or until the animation is canceled.
	 */
	synchronized public void start() {
		if (running)
			return;
		runner = new Thread() {
			public void run() {
				doRun();
			}
		};
		runner.start();
		running = true;
		canceled = false;
		paused = false;
		fireAnimationChangeEvent();
	}
	
	/**
	 * Pauses or unpauses a running animation.  If the animation is not running, this has no effect.
	 * @param paused if true, then the animation is paused; if false, then the animation is unpaused
	 */
	synchronized public void setPaused(boolean paused) {
		if (running) {
			this.paused = paused;
			notify();
		}
	}
	
	/**
	 * Tests whether the animation is paused.  Only a running animation can be paused.
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * Cancels the animation.  The animation will be stopped as soon as possible (that is, the next
	 * time the {@link #pause(int)} method is called by {@link #runAnimation()}).  If the animation is
	 * not running, this has no effect.
	 */
	synchronized public void cancel() {
		if (!running)
			return;
		canceled = true;
		notify();
	}
	
	/**
	 * Tests whether the animation is running.
	 */
	public boolean isRunning() {
		return running;
	}
	
	/**
	 * Tests whether the animation has been canceled.  This can be called after the animation ends to determine
	 * whether the animation ended on its own or because it was canceled.
	 */
	public boolean wasCanceled() {
		return canceled;
	}
	
	/**
	 * Slows down or speeds up the animation by multiplying all delay times (as specified in the parameter
	 * the <code>pause</code> method) by a time dilation factor.  Note that the dilation applies only to
	 * delay times, not to processing times, so the animation speed is only approximately multiplied by
	 * the dilation factor.  In particular, only a limited amout of speed-up can be obtained, no matter
	 * how close to zero you make the dilation factor.  The default value of the time dilation factor
	 * is 1, which corresponds to normal run speed.
	 * @param dilationFactor delay times for the <code>pause</code> method are multiplied by this factor to
	 * give the actual time delay. A dilationFactor less than zero is treated as zero.
	 * @see #pause(int)
	 */
	public void setTimeDilation(double dilationFactor) {
		if (dilationFactor < 0)
			dilationFactor = 0;
		timeDilation = dilationFactor;
	}
	
	/**
	 * Returns the time dilation factor that is currently set for this animation.
	 * @see #setTimeDilation(double)
	 */
	public double getTimeDilation() {
		return timeDilation;
	}

	private void doRun() {
		try {
			runAnimation();
		}
		catch (AnimationCanceledException e) {
		}
		catch (Exception e) {
			System.out.println("Animation aborted by unexpected exception:  " + e);
			e.printStackTrace();
		}
		finally {
			doneRunning();
		}
	}
	
	synchronized private void doneRunning() {
		running = false;
		paused = false;
		runner = null;
		fireAnimationChangeEvent();
	}

	//----------------- support for ChangeEvents --------------------------------------
	
	/**
	 * Add a ChangeListener to this animation.  Change events are sent when the animation
	 * stops and when it stops for any reason.  The ChangeListener can distinguish the two events
	 * by calling the <code>isRunning</code> method, which will retrun true if the
	 * animation is starting and false if the animation has ended.
	 */
	synchronized public void addChangeListener(ChangeListener listener) {
		if (listener == null)
			return;
		if (changeListeners == null)
			changeListeners = new ArrayList<ChangeListener>();
		changeListeners.add(listener);
	}
	
	/**
	 * Remove a ChangeListener from the animation, if it is currently registered as a listener.
	 */
	synchronized public void removeChangeListener(ChangeListener listener) {
		if (listener != null && changeListeners != null) {
			changeListeners.remove(listener);
			if (changeListeners.isEmpty())
				changeListeners = null;
		}
	}
	
	/**
	 * Sends a ChangeEvent to any registered ChangeListeners.  This is not likely to be used in a subclass,
	 * unless the subclass wants to send additional change events besides those sent when the
	 * animation starts and stops.
	 */
	synchronized protected void fireAnimationChangeEvent() {
		if (changeListeners == null)
			return;
		if (changeEvent == null)
			changeEvent = new ChangeEvent(this);
		for (int i = 0; i < changeListeners.size(); i++)
			changeListeners.get(i).stateChanged(changeEvent);
	}
	
	// -------------------------- status bar text -------------------------

	/**
	 * Returns null to indicate that the default text ("Animation Running" or "Animation Paused" in the
	 * English version) should be shown in the display's status bar.  This can be overridden in
	 * a subclass to show a different status message.
	 * @param running tells whether the animation is currently running.
	 */
	public String getStatusText(boolean running) {
		return null;
	}
	
}
