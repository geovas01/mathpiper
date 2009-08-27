/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * An animation that is driven by a Swing Timer.  A concrete subclass of this
 * class must implement the {@link #drawFrame()} method, which is called each
 * time the Timer fires.  A subclass that has some set-up to do at the
 * beginning of the animation should override the {@link #animationStarting()}
 * method; if the subclass has some clean-up that needs to be done when the
 * animation ends, it should override {@link #animationEnding()}.  Note that
 * the animation does not start until its {@link #start()} method is invoked.
 * Once it has been started, it will run until it ends on its own or until
 * its {@link #cancel()} method is called, either by an external agent or
 * in the <code>drawFrame</code> method.  While it is running, a TimerAnimation
 * can be paused and resumed by calling the {@link #setPaused(boolean)} method. 
 * <p>Each frame in a TimerAnimation has a frame number.  It can have either a
 * specified finite number of frames, or the frame number can be allowed to increase
 * indiefinitely.  An animation with a finite number of frames can run in any
 * of three "looping modes":  <code>TimerAnimation.ONCE</code>, meaning that it
 * runs through the frames from first to last then stops; <code>TimerAnimation.LOOP</code>,
 * which means that it runs from first to last frame over and over indefinitely; and
 * <code>TimerAnimation.OSCILLATE</code>, which means that it runs from first frame to last
 * then from last frame back to first in revese order, and the repeats this sequence indefinitely. 
 * <p>A Timere animation is capable of using a "filmstrip" to store the frames of the
 * animation.  See {@link #setUseFilmstrip(boolean)}.  If a filmstrip is used, each
 * frame of the animation is saved as a bitmap the first time it is computed.  Then, when
 * it is necessary to show the same frame again, the precomputed bitmap is just copied
 * to the screen.
 */
abstract public class TimerAnimation implements Animation {
	
	/**
	 * Indicates a looping mode in which the frames are generated from first frame to last
	 * frame, and then the animation ends.  For use as a parameter to {@link #setLooping(int)}
	 */
	public static final int ONCE = 0;
	
	/**
	 * Indicates a looping mode in which the frames are generated from first frame to last
	 * frame, and then sequence repeats indefinitely.  For use as a parameter to {@link #setLooping(int)}
	 */
	public static final int LOOP = 1;
	
	/**
	 * Indicates a looping mode in which the frames are generated from first frame to last
	 * frame, then in reverse order from last to first, and then then the same sequence is
	 * repeated indefinitely.  For use as a parameter to {@link #setLooping(int)}
	 */
	public static final int OSCILLATE = 2;
	
	private int looping = ONCE;
	private Timer timer;
	private int frames;
	private int millisecondsPerFrame;
	private int initialDelay;
	private boolean forward;
	private ArrayList<ChangeListener> changeListeners;
	private ChangeEvent changeEvent;
	private boolean fireFrameEvents;
	private boolean paused;
	private Filmstrip filmstrip;  // One image for each frame that has been created in a filmstrip, if one is being created.
	protected Display display;  // where the animation is being displayed -- only used when a filmstrip is being created.
								// As of June 25, 2007, it is also used in the subclass, BasicAnimator, in the drawFrame
	                            // method to ensure that the exhibit is actually redrawn even if the paramter values
	                            // don't change.
	/**
	 * The time dilation fact that is multiplied by all time specifications.
	 */
	protected double timeDilation;
	
	/**
	 * The current frame number.
	 */
	protected int frameNumber;

	/**
	 * This is called to draw each frame.  "Drawing" will probably mean setting some
	 * parameters and calling repaint, rather than doing the drawing directly.  The
	 * protected member variable <code>frameNumber<code> is the number of the current frame;
	 * you can use this number to determine what to draw in the current frame.  You can
	 * call the {@link #cancel()} method to terminate the animation.  The animation can also
	 * be canceled at any time when an external agent calls the <code>cancel</code> method.
	 * Since you can't control how the animation will end, any clean-up that has to be
	 * done when the animation ends should be done in the {@link #animationStarting()} method.
	 * <p>Note that if this animation is creating a filmstrip, this method is called only
	 * when the frame is actually being created, ant not when a saved image of a frame
	 * is being redrawn.  Saving and using the frames is managed by the Display where
	 * the animation is running. 
	 */
	abstract protected void drawFrame();
	
	/**
	 * Construct a TimerAnimation in which the frame number will increase indefinitely forever.
	 * The time per frame is 50 milliseconds.
	 * The initial delay will be zero, unless it is set by {@link #setInitialDelay(int)}.
	 * No filmstrip is created.
	 */
	public TimerAnimation() {
		this(-1, 50, false);
	}

	/**
	 * Construct a TimerAnimation with a specified number of frames and nominal time per frame.
	 * There is no initial delay, and the nominal inter-frame delay time is 50 milliseconds.
	 * The initial delay will be zero, unless it is set by {@link #setInitialDelay(int)}
	 * No filmstrip is created.
	 * @param frames The number of frames in the animation.  Any value less than or equal to zero means
	 * that the number of frames will increase indefinitely.
	 * @param millisecondsPerFrame The nominal number of milliseconds for each frame.  This is used (after adjustment
	 * by the time dilation factor) as the delay time for the Swing Timer that drives the animation.
	 */
	public TimerAnimation(int frames, int millisecondsPerFrame) {
		this(frames,millisecondsPerFrame,false);
	}

	/**
	 * Construct a TimerAnimation with a specified number of frames and time per frame, with the 
	 * possibility of creating a filmstrip of the animation.  
	 * The initial delay will be zero, unless it is set by {@link #setInitialDelay(int)}
	 * (The actual delay times can be affected by the {@link #setTimeDilation(double)} method,
	 * and the processing time used by the animation means that the actual delay cannot be made
	 * arbitrarily small.)
     * <p>Calling this method with
	 * the createFilmstrip parameter set to true is the only way to create a filmstrip animation.
	 * Note that creating a filmstrip
	 * requires the cooperaiton of the {@link Display} where the animation is being shown.  In fact,
	 * it is the display that actually creates the image of each frame and stores it in the TimerAnimation
	 * object.
	 * @param frames The number of frames in the animation.  Any value less than or equal to zero means
	 * that the number of frames will increase indefinitely.
	 * @param millisecondsPerFrame The nominal number of milliseconds for each frame.  This is used (after adjustment
	 * by the time dilation factor) as the delay time for the Swing Timer that drives the animation.
	 * @param createFilmstrip If this is true, then a "filmstrip" will be created as the animation is
	 * created.  Once a frame has been created, the frame image is saved so that it does not have to
	 * be recreated when the frame is shown again.  Note that if memory is not available for all
	 * the frames of the amimation, then frames for which there is no memory will have to be
	 * reconstructed each time they are shown.  Use {@link Filmstrip#maxFrames(int, int, boolean)} to
	 * get an estimate of the number of frames that can be created. 
	 */
	public TimerAnimation(int frames, int millisecondsPerFrame, boolean createFilmstrip) {
		if (frames <= 0)
			frames = -1;
		this.frames = frames;
		if (millisecondsPerFrame > 0)
			this.millisecondsPerFrame = millisecondsPerFrame;
		if (createFilmstrip)
			filmstrip = new Filmstrip();
	}
	
	/**
	 * Returns true if this animation is creating a filmstrip. 
	 */
	public boolean getUseFilmstrip() {
		return filmstrip != null;
	}
	
	/**
	 * Set whether or not this animation uses a filmstrip.  If the value of the useFilmstrip is changed
	 * from true to false, the flimstrip and any frames that it contains are discarded.
	 */
	public void setUseFilmstrip(boolean useFilmstrip) {
		if (useFilmstrip != (filmstrip != null)) {
			if (useFilmstrip)
				filmstrip = new Filmstrip();
			else
				filmstrip = null;
		}
	}
	
	/**
	 * Returns the filmstrip that is being used to store the frames of this animation.
	 * If no filmstrip is being created, then the return value will be null.
	 */
	public Filmstrip getFilmstrip() {
		return filmstrip;
	}
	
	/**
	 * If this animation is creating a filmstrip and if the current frame has already been 
	 * created, then the image of that frame is returned.  Otherwise, null is returned.
	 */
	public BufferedImage getFilmstripFrameImage() {
		if (filmstrip != null && filmstrip.getFrameCount() > frameNumber)
			return filmstrip.getFrame(frameNumber);
		else
			return null;
	}
	
	/**
	 * Adds a frame to the filmstrip.  This is called by the display where the
	 * animation is running and is not meant to be called directly.
	 */
	void saveCurrentFrame(BufferedImage image) {
		if (frameNumber >= 0 && (frames <= 0 || frameNumber <= frames))
			filmstrip.setFrame(frameNumber, image);
	}
	
	/**
	 * If this animation is creating a filmstrip, this returns the image for the current frameNumber,
	 * if one exists.  In other cases, it returns null.  This method is called by the display
	 * where the animation is running.
	 */
	BufferedImage getCurrentFilmstripFrame() {
		if (filmstrip != null && frameNumber >= 0 && frameNumber < filmstrip.getFrameCount())
			return filmstrip.getFrame(frameNumber);
		else
			return null;
	}
	
	/**
	 * Sets the display where this animation is running.  This is only used in the case
	 * of a filmstrip animation.
	 */
	void setDisplay(Display d) {
		display = d;
	}
	
	/**
	 * Sets the filmstrip for this animation, and sets the number of frames to match the
	 * size of the filmstrip.  This is called by a Display when it is creating an
	 * animation to replay a filmstrip.
	 */
	void setFilmstrip(Filmstrip f) {
		filmstrip = f;
		if (f != null)
			setFrames(f.getFrameCount() - 1);
	}

	/**
	 * Sets the looping mode of the animation.  This determines what happens when the last frame of 
	 * the animation is reached (and therefore only has an effect on an animation that has a finite number
	 * of frames.)  When the animation reaches its last frame, it can either end (<code>TimerAnimation.ONCE</code>),
	 * return immediately to the first frame (<code>TimerAnimation.LOOP</code>), or go into reverse and
	 * show the frames from last back to first and then repeating this sequence (<code>TimerAnimation.OSCILLATE</code>).
	 * @param loopingMode The looping mode for the animation.  This must be one of <code>TimerAnimation.ONCE</code>,
	 * <code>TimerAnimation.LOOP</code>, or <code>TimerAnimation.OSCILLATE</code>; other values are ignored.
	 */
	public void setLooping(int loopingMode) {
		if (loopingMode >= ONCE && loopingMode <= OSCILLATE) {
			looping = loopingMode;
			if (loopingMode != OSCILLATE)
				forward = true;
		}
	}

	/**
	 * Returns the current looping mode of the animation.
	 * @see #setLooping(int)
	 */
	public int getLooping() {
		return looping;
	}
	
	/**
	 * Returns the maximum frame number for this animation.  A return value of -1 indicates that the
	 * frame number will increase indefinitely.
	 */
	public int getFrames() {
		return frames;
	}

	/**
	 * Sets the maximum frame number for this animation. Frames are numbered from 0 up to this number
	 * (so that number of frames is actually one more than the number specified in this method).
	 * If set to a value less than or equal to 0, the number of frames will be unlimited.
	 * Also in this case, looping mode is forced to ONCE and the direction of the animation is set to
	 * be forward if it is currently going backwards in the OSCILLATE mode.
	 */
	public void setFrames(int frames) {
		if (frames <= 0)
			frames = -1;
		this.frames = frames;
		if (frames <= 0)
			setLooping(ONCE);
	}
	
	/**
	 * Tells whether frame events are fired by this animation.
	 * @see #setFireFrameEvents(boolean)
	 */
	public boolean getFireFrameEvents() {
		return fireFrameEvents;
	}

	/**
	 * If set to true, then change events are generated each time the
	 * frame changes.  The default value is false.  In any case, change events
	 * are sent when the animation starts and when it stops.
	 */
	public void setFireFrameEvents(boolean fireFrameEvents) {
		this.fireFrameEvents = fireFrameEvents;
	}
	
	/**
	 * Gets the current frame number. (Note that subclasses can use the protected
	 * member variable, <code>frameNumber</code>, instead of calling this method.)
	 */
	public int getFrameNumber() {
		return frameNumber;  // value is -1 when the "start" event is fired
	}

	/**
	 * Gets the initial delay for this animation.
	 * @see #setInitialDelay(int)
	 */
	public int getInitialDelay() {
		return initialDelay;
	}

	/**
	 * Sets the nominal initial delay for this animation to a specified number of milliseconds.
	 * This is used as the initial delay of the Swing Timer that drives the animation.  The actual
	 * delay time is adjusted by the time dilation factor.
	 * @see #setTimeDilation(double)
	 */
	public void setInitialDelay(int initialDelayInMilliseconds) {
		if (initialDelayInMilliseconds >= 0)
			this.initialDelay = initialDelayInMilliseconds;
	}

	/**
	 * Returns the nominal number of milliseconds per frame.
	 * @see #setMillisecondsPerFrame(int)
	 */
	public int getMillisecondsPerFrame() {
		return millisecondsPerFrame;
	}
	
	/**
	 * Sets the nominal number of milliseconds per frame.  This is used as the delay time of
	 * the Swing Timer that drives the animation.  The actual time is adjusted by the
	 * time dilation factor.  Because of processing time used by the animation and other tasks
	 * being run by the system, the effective delay time cannot be arbitrarily short.
	 * @param millisecondsPerFrame the nominal number of milliseconds per frame.  The value should
	 * be positive; values less than or equal to zero are ignored.
	 * @see #setTimeDilation(double)
	 */
	public void setMillisecondsPerFrame(int millisecondsPerFrame) {
		if (millisecondsPerFrame > 0) {
			this.millisecondsPerFrame = millisecondsPerFrame;
			if (timer != null)
				timer.setDelay(applyDilation(millisecondsPerFrame));
		}
	}
	
	/**
	 * Pauses or unpauses a running animation.  This has no effect if the animation is not running.
	 * While an animation is paused, no new frames are generated, and the <code>drawFrame</code> method will not
	 * be called.
	 * @param paused Tells whether or not the animation should be paused.
	 */
	public void setPaused(boolean paused) {
		if (timer == null || this.paused == paused)
			return;
		this.paused = paused;
		if (paused)
			timer.stop();
		else {
			timer.setInitialDelay(0);
			timer.restart();
		}
	}
	
	/**
	 * Tests whether the animation is currently paused.
	 * @see #setPaused(boolean)
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * Sets a time dilation factor that is multiplied by all time periods related to the animation.
	 * This is used for slowing down or speeding up the animation, although only a certain amount
	 * of speed-up is possible, becasue of the actual processing time used by the animation.
	 * This applies to the initial delay and to the inter-frame time.
	 * @param dilationFactor The factor by which initial delay and inter-frame times are to be multiplied.
	 * The value should be non-negative.  Negative values are treated as zero.  
	 */
	synchronized public void setTimeDilation(double dilationFactor) {
		if (dilationFactor < 0)
			dilationFactor = 0;
		if (dilationFactor != timeDilation) {
			timeDilation = dilationFactor;
			if (timer != null)
				timer.setDelay(applyDilation(millisecondsPerFrame));
		}
	}
	
	/**
	 * Returns the current time dilation factor.
	 * @see #setTimeDilation(double)
	 */
	public double getTimeDilation() {
		return timeDilation;
	}
	
	private int applyDilation(int milliseconds) {
		int x =(int)(milliseconds*timeDilation + 0.49);
		if (milliseconds > 0 && x == 0)
			return 1;  // don't return 0 unless milliseconds was zero
		else
			return x;
	}

	/**
	 * Starts the animation running.  An animation does not start running automatically, but only
	 * when this method is called.  If the animation is already runnning, this has no effect.
	 * If the animation has already run and has finished for any reason, it is restarted from the beginning.
	 */
	synchronized public void start() {
		if (timer != null)
			return;
		timer = new Timer(applyDilation(millisecondsPerFrame), new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				nextFrame(evt);
			}
		});
		timer.setInitialDelay(applyDilation(initialDelay));
		frameNumber = -1;  // Changes to zero before the first time drawFrame is called.
		forward = true;
		paused = false;
		animationStarting();
		fireAnimationChangeEvent();
		timer.start();
	}
	
	/**
	 * Respond to an event from the timer that drives the animation.
	 * This is "protected" so it can be overridden in very rare
	 * circumstances, such as in the SurfaceImplicit class.
	 */
	synchronized protected void nextFrame(ActionEvent evt) {
		if (timer != evt.getSource())
			return; // evt.getSource() could be null if an extra event is received after animation is stopped (?);
		            // and possibly there could be events left over from previous runs that should be ignored.
		if (forward)
			frameNumber++;
		else
			frameNumber--;
		switch (looping) {
			case ONCE:
				if (frames > 0 && frameNumber > frames) {
					cancel();
					return;
				}
				break;
			case LOOP:
				if (frameNumber > frames)
					frameNumber = 1;  // changed from 0 by HK
				break;
			case OSCILLATE:
				if (frameNumber > frames) {
					forward = false;
					frameNumber = frames -1;
				}
				else if (frameNumber < 0) {
					forward = true;
					frameNumber = 1;   // frame 0 has been played.
				}
				break;
		}
		if (timer != null) {
			if (filmstrip != null && filmstrip.getFrameCount() > frameNumber 
					&& filmstrip.getFrame(frameNumber) != null && display != null)
				display.repaint();
			else
				drawFrame();
			if (fireFrameEvents)
				fireAnimationChangeEvent();
			Thread.yield();
		}
	}

	/**
	 * Cancels a running animation.  If this animation is not running, this has no effect.
	 */
	synchronized public void cancel() {
		if (timer != null) {
			timer.stop();
			animationEnding();
			timer = null;
			paused = false;
			fireAnimationChangeEvent();
		}
	}
	
	/**
	 * Called when the animation is started to give a subclass a chance to do any necessary set-up.
	 * In this class, the method does nothing.
	 */
	protected void animationStarting() {
	}

	/**
	 * Called when the animation ends, either on its own or becauce it has been canceled from somewhere
	 * else, to give a subclass a chance to do any necessary clean-up.  In this class, the method does nothing.
	 */
	protected void animationEnding() {
	}

	/**
	 * Returns true if the animation is currently running.  When the "start" event
	 * is fired, the return value is true.  When the "end" event is fired, the
	 * return value is false.
	 */
	synchronized public boolean isRunning() {
		return timer != null;
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
	
    // --------------------- Status Bar text ----------------------------
	
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
