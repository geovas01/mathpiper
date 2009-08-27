/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.util.ArrayList;

/**
 * A BasicAnimator can animate one or more Animateable objects. It is a fairly
 * simple extension of TimerAnimation that keeps a list of Animateable items
 * and in each frame calls each item's {@link Animateable#setFractionComplete(double)} method.  
 * In fact, all Animateable objects in the vmm core
 * are Animateable Parameters such as {@link RealParamAnimateable}, and
 * BasicAnimators are used only for Morphing animations.
 * <p>It is also possible to add a {@link RealParamAnimateable} or
 * {@link ComplexParamAnimateable} with specified start and end values, which
 * will be used instead of the start and end values stored in the parameter itself.
 */
public class BasicAnimator extends TimerAnimation {
	
	private ArrayList<Animateable> animatedItems = new ArrayList<Animateable>();
	private ArrayList<Object[]> customLimitAnimatedParameters = null;
	private ArrayList<Object[]> initialParameterValues;  // used for saving parameter values at start of animation
	
	private boolean restoreParameterValues = true;
	
	/**
	 * Create a BasicAnimator with no items, with 100 frames and 50 millseconds per frame.
	 * Animated items can be added with the <code>addAnimatedItem</code> method.
	 */
	public BasicAnimator() {
		this((Animateable[])null,100,50);
	}
	
	/**
	 * Create a BasicAnimator with no items, with a specified number of frames, and with 50 millisconds.
	 * Animated items can be added with the <code>addAnimatedItem</code> method.
	 */
	public BasicAnimator(int frames) {
		this((Animateable[])null,frames,50);
	}
	
	/**
	 * Create a BasicAnimator with one Animateable item, with 100 frames and 50 millseconds per frame.
	 * More animated items could be added with the <code>addAnimatedItem</code> method.
	 * @param item An item to be animated by this animation. Can be null, in which case
	 * the animation has no items initially.
	 */
	public BasicAnimator(Animateable item) {
		this(new Animateable[] { item }, 100, 50);
	}
	
	/**
	 * Create a BasicAnimator with one item, with a specified number of frames and delay per frame.
	 * More animated items could be added with the <code>addAnimatedItem</code> method.
	 * @param item An item to be animated by this animation. Can be null, in which case
	 * the animation has no items initially.
	 * @param frames The number of frames in the animation
	 * @param millisecondsPerFrame The desired number of milliseconds for each frame
	 */
	public BasicAnimator(Animateable item, int frames, int millisecondsPerFrame) {
		this(new Animateable[] { item }, frames, millisecondsPerFrame);
	}
	
	/**
	 * Create a BasicAnimator with several animated itemss, and with a specified number of frames and delay per frame.
	 * @param items An array of items to be animated by this animation. Can be null, in which case
	 * the animation has no items initially.
	 * @param frames The number of frames in the animation.
	 * @param millisecondsPerFrame The desired number of milliseconds for each frame
	 */
	public BasicAnimator(Animateable[] items, int frames, int millisecondsPerFrame) {
		super(frames,millisecondsPerFrame);
		if (items != null)
			for (int i = 0; i < items.length; i++)
				animatedItems.add(items[i]);
	}
	
	/**
	 * Get the setting of the restoreParameterValues property.
	 * @see #setRestoreParameterValues(boolean)
	 */
	public boolean getRestoreParameterValues() {
		return restoreParameterValues;
	}

	/**
	 * Set the restoreParameterValues property.  If this property is true, then this BasicAnimator will save
	 * the value of any Parameter among its Animateable objects at the beginning of the animation, and it
	 * will restore those values when the animation ends.  The default value is true.  The value must be set
	 * before the animation is started for it to have any effect.
	 */
	public void setRestoreParameterValues(boolean restoreParameterValues) {
		this.restoreParameterValues = restoreParameterValues;
	}

	/**
	 * Add an Animateable item to the list of items that change during this animation.
	 * @param item The item to be added.  If null, nothing is done.
	 */
	synchronized public void addAnimatedItem(Animateable item) {
		if (item != null && !(animatedItems.contains(item)))
			animatedItems.add(item);
	}
	
	/**
	 * Adds an IntegerParam to this animator, with specified value for
	 * the animation.  This value is assigned to the paramter at the beginning of the
	 * animation and is used throughout the animation, the old value is restored at the
	 * end of the animation, provided that the restoreParameterValues property of this
	 * animation is true. 
	 * @param item the parameter whose value is to be fixed at a specified value throughout
	 * the animation.  If this is null, nothing is done and no error occurs.
	 */
	synchronized public void addWithCustomValue(IntegerParam item, int value) {
		if (item == null)
			return;
		if (customLimitAnimatedParameters == null)
			customLimitAnimatedParameters = new ArrayList<Object[]>();
		customLimitAnimatedParameters.add(new Object[] { item, value });
	}
		
	/**
	 * Adds a RealParam to this animator, with specified value for
	 * the animation.  This value is assigned to the paramter at the beginning of the
	 * animation and is used throughout the animation, the old value is restored at the
	 * end of the animation, provided that the restoreParameterValues property of this
	 * animation is true. 
	 * @param item the parameter whose value is to be fixed at a specified value throughout
	 * the animation.  If this is null, nothing is done and no error occurs.
	 */
	synchronized public void addWithCustomValue(RealParam item, double value) {
		if (item == null)
			return;
		if (customLimitAnimatedParameters == null)
			customLimitAnimatedParameters = new ArrayList<Object[]>();
		customLimitAnimatedParameters.add(new Object[] { item, value });
	}
		
	/**
	 * Adds a ComplexParam to this animator, with specified value for
	 * the animation.  This value is assigned to the paramter at the beginning of the
	 * animation and is used throughout the animation, the old value is restored at the
	 * end of the animation, provided that the restoreParameterValues property of this
	 * animation is true. 
	 * @param item the parameter whose value is to be fixed at a specified value throughout
	 * the animation.  If this is null, nothing is done and no error occurs.
	 * @param value the value for the parameter.  If this is null, 0 is used.
	 */
	synchronized public void addWithCustomValue(ComplexParam item, Complex value) {
		if (item == null)
			return;
		if (customLimitAnimatedParameters == null)
			customLimitAnimatedParameters = new ArrayList<Object[]>();
		if (value == null)
			value = new Complex();
		customLimitAnimatedParameters.add(new Object[] { item, value });
	}
		
	/**
	 * Adds a RealParamAniamteable to this animator, with specified start and end values for
	 * the animation.  These values are used instead of the start and end values stored in
	 * the parameter itself, and those values are not modified by the animation.  (This is meant to
	 * allow, for example, the creation of "custom morphs" in addition to the standard Morph
	 * and Cyclic Morph, which always use the start and end values specified by the user and
	 * stored in the parameters.)
	 * @param item the parameter to be animated.  If this is null, nothing is done and no error occurs.
	 */
	synchronized public void addWithCustomLimits(RealParamAnimateable item, double start, double end) {
		if (item == null)
			return;
		if (customLimitAnimatedParameters == null)
			customLimitAnimatedParameters = new ArrayList<Object[]>();
		customLimitAnimatedParameters.add(new Object[] { item, start, end });
	}
		
	/**
	 * Adds a ComplexParamAniamteable to this animator, with specified start and end values for
	 * the animation.  These values are used instead of the start and end values stored in
	 * the parameter itself, and those values are not modified by the animation.  If the
	 * start or end value is null, 0 is used.
	 * @param item the parameter to be animated.  If this is null, nothing is done and no error occurs.
	 */
	synchronized public void addWithCustomLimits(ComplexParamAnimateable item, Complex start, Complex end) {
		if (item == null)
			return;
		if (start == null)
			start = new Complex();
		if (end == null)
			end = new Complex();
		if (customLimitAnimatedParameters == null)
			customLimitAnimatedParameters = new ArrayList<Object[]>();
		customLimitAnimatedParameters.add(new Object[] { item, start, end });
	}
		
	final synchronized public void start() {
		if (isRunning())
			cancel();
		initialParameterValues = null;
		if (restoreParameterValues) {
			initialParameterValues = new ArrayList<Object[]>();
			for (Animateable item : animatedItems) {
				if (item instanceof Parameter) {
					Parameter p = (Parameter)item;
					initialParameterValues.add( new Object[] { p, p.getValueObject() } );
				}
			}
			if (customLimitAnimatedParameters != null) {
				for (Object[] item : customLimitAnimatedParameters) {
					Parameter p = (Parameter)item[0];
					initialParameterValues.add( new Object[] { p, p.getValueObject() } );
				}
			}
		}
		super.start();
	}
	
	final synchronized public void cancel() {
		if (initialParameterValues != null) {
			for (Object[] info : initialParameterValues) {
				Parameter p = (Parameter)info[0];
				p.setValueObject(info[1]);
			}
			initialParameterValues = null;
		}
		super.cancel();
	}
	
	/**
	 * Creates one frame of the animation by calling the {@link vmm.core.Animateable#setFractionComplete(double)}
	 * method of each object in this BasicAnimator's list of Animateable object.
	 */
	protected void drawFrame() {
		if (animatedItems.size() == 0 && customLimitAnimatedParameters == null)
			cancel();
		double fractionComplete = (double)frameNumber / getFrames();
		for (Animateable item : animatedItems)
			item.setFractionComplete(fractionComplete);
		if (customLimitAnimatedParameters != null) {
			for (Object[] item : customLimitAnimatedParameters) {
				if (item.length == 2) {
					if (item[0] instanceof IntegerParam)
						((IntegerParam)item[0]).setValue((Integer)item[1]);
					else if (item[0] instanceof RealParam)
						((RealParam)item[0]).setValue((Double)item[1]);
					else
						((ComplexParam)item[0]).setValue((Complex)item[1]);
				}
				else if (item[0] instanceof RealParamAnimateable) {
					RealParamAnimateable param = (RealParamAnimateable)item[0];
					double start = (Double)item[1];
					double end = (Double)item[2];
					double val = start + fractionComplete*(end - start);
					param.setValue(val);
				}
				else {
					ComplexParamAnimateable param = (ComplexParamAnimateable)item[0];
					Complex start = (Complex)item[1];
					Complex end = (Complex)item[2];
					double valRe = start.re + fractionComplete*(end.re - start.re);
					double valIm = start.im + fractionComplete*(end.re - start.re);
					param.setValue(new Complex(valRe,valIm));
				}
			}
		}
		if (display != null && display.getView() != null)
			display.getView().forceRedraw();  // This is necessary becasue if none of the parameter values actually change, then the frame will not be created.
	}

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
