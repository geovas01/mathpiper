/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * A Display is a canvas where a {@link View} can draw an {@link Exhibit}.  A display can
 * manage an {@link Animation}; it has methods for installing, stopping, controlling the speed, and
 * pausing an animation.  It can also manage MouseTasks, which respond to user mouse
 * clicks on the Display.  A View can define a {@link vmm.core.MouseTask} that is active as long as the View
 * is installed in the Display.  It is also possible to install a "one-shot mouse task" that
 * is active only for one user mouse action.
 * <p>A display can have an associated container, which can be obtained by calling {@link #getHolder()}.
 * The ordinary way to add a display is to add this "holder" to a parent container,
 * rather than adding the display itself.  The holder holds a status bar in addition to the display, and
 * other components can be added as well using {@link #addBorderComponent(Component, Object)}.  The
 * status bar can be hidden.  In addition, if the display is in a holder, it is possible to "split"
 * the holder, with the display in one half and another component in the other half.  Note that
 * exhibits that use the capabilities associated with the holder might not function fully if the
 * display is not contained in a holder.  Ordinarily, you should not do anything with the holder
 * directly except add it to a parent component.  Note that whenever an exhibit is removed from
 * the display, the holder is reset to its default state, showing only the display and (if it has not
 * been hidden) the status bar.
 */
public class Display extends JPanel {
	
	private View mainView;
	private Animation animation;
	private MouseTask mouseTask;
	private MouseTask oneShotMouseTask;
	private View auxiliaryView;  // see public void setAuxliaryView()
	private int auxiliaryViewPosition;  // where to place the auxiliary view; a constant such as AUX_VIEW_ON_LEFT
	private MouseTask auxiliaryMouseTask;  // see public void installAuxiliaryMouseTask
	private boolean oneShotMouseTaskIsForMainView;
	private double auxiliaryViewFraction; // fraction of display devoted to auxiliary view.
	private boolean auxiliaryViewIsResizable;  // if true, user can drag the bar that separates the main and auxiliary views
	private Rectangle mainViewRect, auxViewRect, dividingBarRect;  // rectangles containind main view, auxiliary view, and the bar that divides them
	private Rectangle basisUsedForComputingRects;
	private MouseTask dragTask;  // When a mouse task is performing a drag operation, this is set equal to that
	                             // mouse task.  The mouse handler sends mouse-dragged and mouse-released
	                             // events to this task.
	private boolean draggingInMainView;
	private boolean mouseInMainView = true;
	private boolean mouseInAuxView = false;
	private double timeDilationForAnimations = 1;
	
	private boolean stopAnimationsOnResize = true;
	
	private Filmstrip savedFilmstrip;  // Filmstrip produced as the most recent animation ran, if any.
	private int savedFilmstripLooping; // looping style for the savedFilmstrip (if there is one)
	
	private String status;
	
	private Font saveOriginalFont;  // So it can be restored, in case it is changed by a view or exhibit.
	
	
	private DisplayHolder holder; // A container that is meant to hold the display, a
                                  // status bar and possibly other components.

	private boolean showStatusBar = true;  // Should the status bar be shown in holder?
	
	private Timer resizeTimer;



	/**
	 * The property name for PropertyChangeEvents that are sent when a filmstrip becomes
	 * available for replay, or when a filmstrip stops being availble.  The value of
	 * the property is of type Boolean.
	 */
	public final static String FILMSTRIP_AVAILABLE_PROPERTY = "FilmstripAvailable";
	
	/**
	 * The property name for PropertyChangeEvents that are sent when the
	 * status of the display changes.  The status of the Display tells
	 * whether an Exhibit is installed, whether an animation is running,
	 * and whether a one-shot mouse task is active.
	 * A Display sends PropertyChangeEvents when its status changes.
	 * The value of the STATUS property is one of the constants
	 * STATUS_EMPTY, STATUS_IDLE, STATUS_RUNNING, STATUS_ANIMATION_RUNNING,
	 * STATUS_ANIMATION_PAUSED, or STATUS_ONE_SHOT_MOUSE_TASK.
	 */
	public final static String STATUS_PROPERTY = "Status";
	
	/**
	 * A value for the STATUS property that indicates that no Exhibit
	 * is currently installed.
	 */
	public final static String STATUS_EMPTY = "empty";
	
	/**
	 * A value for the STATUS property that indicates that an Exhibit
	 * is currently installed, but no animation or one-shot mouse task
	 * is active.
	 */
	public final static String STATUS_IDLE = "idle";
	
	/**
	 * A value for the STATUS property that indicates that an animation
	 * is installed and is running.
	 */
	public final static String STATUS_ANIMATION_RUNNING = "running";
	
	/**
	 * A value for the STATUS property that indicates that an animation
	 * is installed but is currently paused.
	 */
	public final static String STATUS_ANIMATION_PAUSED = "paused";
	
	/**
	 * A value for the STATUS property that indicates that a one-shot mouse
	 * task is currently active.
	 */
	public final static String STATUS_ONE_SHOT_MOUSE_TASK = "mouse task";
	
	/**
	 * A constant that can be used in 
	 * to specify the position of the auxiliary view.
	 */
	public final static int AUX_VIEW_ON_LEFT = 0,
					    AUX_VIEW_ON_RIGHT = 1,
					    AUX_VIEW_ON_TOP = 2,
					    AUX_VIEW_ON_BOTTOM = 3;
	
	/**
	 * Construct an initially empty Display.
	 */
	public Display() {
		enableEvents(AWTEvent.COMPONENT_EVENT_MASK);
		MouseHandler listener = new MouseHandler();
		addMouseListener(listener);
		addMouseMotionListener(listener);
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setOpaque(true);
		status = STATUS_EMPTY;
		saveOriginalFont = getFont();
	}
	
	/**
	 * Returns the value of the STATUS property.
	 * @see #STATUS_PROPERTY
	 */
	public String getStatus() {
		return status;
	}
	
	private void setStatus(String status) {
		if (status == STATUS_IDLE && getExhibit() == null) {
		      // This is a fudge, needed in case an animation or one-shot
			  // mouse task was installed when no Exhibt is present -- this should not happen,
		      // and calls to setStatus elsewhere in this file assume that it does not happen.
		      // However, if it does happen, this line will set things right.
			status = STATUS_EMPTY;
		}
		if (status != this.status) {
			String oldVal = this.status;
			this.status = status;
			firePropertyChange(STATUS_PROPERTY, oldVal, status);
		}
		setStatusText(null);  // Sets the default status bar text for the current status.
	}
	
	private void setSavedFilmstrip(Filmstrip filmstrip, int looping) {
		if (savedFilmstrip == filmstrip && savedFilmstripLooping == looping)
			return;
		boolean prevVal, newVal;
		prevVal = savedFilmstrip != null;
		savedFilmstrip = filmstrip;
		savedFilmstripLooping = looping;
		newVal = savedFilmstrip != null;
		if (newVal != prevVal)
			firePropertyChange(FILMSTRIP_AVAILABLE_PROPERTY, new Boolean(prevVal), new Boolean(newVal));
	}
	
	/**
	 * Get the Filmstrip, if any, that has been created by a recent animation.  If there is
	 * no such Filmstrip, the return value is null.
	 */	
	public Filmstrip getSavedFilmstrip() {
		return savedFilmstrip;
	}

	/**
	 * Get the looping mode () of the Filmstrip that has been created by a recent animation.
	 * If there is no such filmstrip, the return value is -1.
	 */
	public int getSavedFilmstripLooping() {
		if (savedFilmstrip == null)
			return -1;
		else
			return savedFilmstripLooping;
	}

	/**
	 * Install a specified View and Exhibit into this Display.  If there is
	 * a previously installed View or Exhibit, it is removed first.  This method is intended to
	 * do all the busy work that is necessary to associate a Display, a View, and an
	 * Exhibit.  For example, it calls the <code>setExhibit</code> method of the view, which in
	 * turn will set up the View as a ChangeListener for the Exhibit.  Users of these
	 * classes should not have to worry about this make-work stuff.
	 * <p>Note that installing a view and exhibit will cancel any animation or mouse
	 * task associated with the Display.  It will also emove any extra components that
	 * have been added by {@link #addBorderComponent(Component, Object)} or
	 * {@link #split(Component)}.  After calling this method, the status
	 * of the Display will be STATUS_IDLE or STATUS_EMPTY.
	 * <p>Note: Calling this method ALWAYS removes any auxiliary view from the
	 * display.  Sett {@link #installAuxiliaryView(View, View)}.)
	 * @param view The View to be installed.  This can be null; If both parameters
	 * are null, then any previously installed View is removed and no View is 
	 * associated with the Display.  If the view parameter is null and the exhibit parameter is not, then the
	 * default view for the exhibit is obtained by calling {@link Exhibit#getDefaultView()}.
	 * @param exhibit An Exhibit for the View to draw.  It should be an Exhibit that  
	 * the specified view is capable of drawing.  If this parameter is not null and
	 * the view parameter is null, then the default View for the Exhibit is used.
	 * If the exhibit parameter is null and the view parameter is non-null and the view already
	 * has an exhibit installed, then the exhibit is set to the view's current exhibit.
	 */
	synchronized public void install(View view, Exhibit exhibit) {
		stopAnimation();
		cancelMouseTasks();
		resetHolder();
		auxiliaryView = null;
		mouseInMainView = true;
		mainViewRect = null;
		if (auxiliaryView != null) {
			auxiliaryView.setExhibit(null);
			auxiliaryView.finish();
		}
		setFont(saveOriginalFont);
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setStopAnimationsOnResize(true);
		if (exhibit != null && view == null)
			view = exhibit.getDefaultView();
		if (mainView != null && view != mainView) {
			mainView.setExhibit(null);
			mainView.finish();
		}
		if (mainView != null)
			mainView.setDisplay(null);  // Disassociate previous view, if any, from the display.
		if (view == null) { // Display will be empty
			mainView = null;
			repaint();
			setStatus(STATUS_EMPTY);
			setSavedFilmstrip(null,0);
			return;
		}
		if (exhibit != null)
			view.setExhibit(exhibit);  // associate the current exhibit with the current view.
		else
			exhibit = view.getExhibit();
		mainView = view;
		view.setDisplay(this);
		installMouseTask(view.getDefaultMouseTask());
		setStatus(exhibit == null? STATUS_EMPTY : STATUS_IDLE);
		setSavedFilmstrip(null,0);
		repaint();
	}
	
	/**
	 * Returns the View that is curently installed in this display.  If no
	 * View is installed, the return value is null.  Note that in the case
	 * where there is an auxiliary view, it is the main view that is returned
	 * by this method (see {@link #installAuxiliaryView(View, View, int, double, boolean)}).
	 */
	public View getView() {
		return mainView;
	}

	/**
	 * Add an extra view to the display, which will always occupy half of the display.
	 * The extra view will appear to the left of the main view.
	 * This method can be called with auxiliaryView == null to remove the
	 * current auxliary view, if any.  This method simply calls
	 * <code>installAuxiliaryView(mainView,auxiliaryView,Display.AUX_VIEW_ON_LEFT,0.5,false).
	 * @param mainView This parameter must equal the current main view of the display.  If
	 * not, nothing further is done and the return value of them method will be false.
	 * @param auxiliaryView The auxliary view that is to be installed to the left of the
	 * main view in this Display.  
	 * @return true if the auxiliary view is installed.  The only case where the return value
	 * is not true is if the mainView parameter in the method call does not match the current
	 * main view in the display.
	 */
	synchronized public boolean installAuxiliaryView(View mainView, View auxiliaryView) {
		return installAuxiliaryView(mainView,auxiliaryView,AUX_VIEW_ON_LEFT,0.5,false);
	}
	
	/**
	 * Add an extra view to the display.  This method can be called with auxiliaryView == null 
	 * to remove the current auxliary view, if any.
	 * @see #installAuxiliaryView(View, View)
	 * @param mainView This parameter must equal the current main view of the display.  If
	 * not, nothing further is done and the return value of them method will be false.
	 * @param auxliaryView The auxliary view that is to be installed to the left of the
	 * main view in this Display.
	 * @param auxViewPosition specifies where the extra view is placed in relation to the main
	 * view.  This must be one of the constants Display.AUX_VIEW_ON_LEFT, Display.AUX_VIEW_ON_RIGHT,
	 * Display.AUX_VIEW_ON_TOP, or Display.AUX_VIEW_ON_BOTTOM.  If not, an IllegalArgumentException
	 * is thrown.
	 * @param fractionOfDisplay a number in the range 0.0 to 1.0 specifying the fraction of the
	 * display that is to be occupied by the auxiliary view.
	 * @param resizable if true, then the user can drag the bar that separates the auxiliary
	 * view from the main view in order to change the fraction of the display that is allocated
	 * to the main view.
	 * @return true if the auxiliary view is installed.  The only case where the return value
	 * is not true is if the mainView parameter in the method call does not match the current
	 * main view in the display.
	 */
	synchronized public boolean installAuxiliaryView(View mainView, View auxliaryView, 
			int auxViewPosition, double fractionOfDisplay, boolean resizable) {
		if (this.mainView != mainView)
			return false;
		stopAnimation();
		if (auxViewPosition < 0 || auxViewPosition > 4)
			throw new IllegalArgumentException("Illegal auxiliary view positon.");
		if (fractionOfDisplay < 0 || fractionOfDisplay > 1)
			throw new IllegalArgumentException("Illegal fractional size for view.");
		if (oneShotMouseTask != null) {
			oneShotMouseTask.finish(this,mainView);
			oneShotMouseTask = null;
		}
		if (this.auxiliaryView != null && auxiliaryMouseTask != null) {
			auxiliaryMouseTask.finish(this,this.auxiliaryView);
			auxiliaryMouseTask = null;
		}
		if (this.auxiliaryView != null) {
			this.auxiliaryView.setDisplay(null);
			this.auxiliaryView.finish();
		}
		this.auxiliaryView = auxliaryView;
		if (auxiliaryView != null) {
			auxiliaryView.setDisplay(this);
			installAuxiliaryMouseTask(auxiliaryView.getDefaultMouseTask());
		}
		this.auxiliaryViewPosition = auxViewPosition;
		auxiliaryViewIsResizable = resizable;
		auxiliaryViewFraction = fractionOfDisplay;
		setStatus(STATUS_IDLE);
		setStatusText(null);
		mainViewRect = null;  // forces recompute of mainViewRect, auxViewRect, dividingBarRect
		repaint();
		return true;
	}
	
	private void makeViewRects() {
		Insets insets = getInsets();
		if (insets == null)
			mainViewRect = new Rectangle(0,0,getWidth(),getHeight());
		else
			mainViewRect = new Rectangle(insets.left,insets.top,getWidth()-insets.left-insets.right,getHeight()-insets.top-insets.bottom);
		if (auxiliaryView == null) {
			auxViewRect = dividingBarRect = null;
			return;
		}
		auxViewRect = new Rectangle(mainViewRect);
		int auxViewPixels, mainViewPixels;
		if (auxiliaryViewPosition == AUX_VIEW_ON_BOTTOM || auxiliaryViewPosition == AUX_VIEW_ON_TOP) {
			auxViewPixels = (int)(auxiliaryViewFraction*(mainViewRect.height - 7));
			mainViewPixels = mainViewRect.height - 7 - auxViewPixels;
		}
		else {
			auxViewPixels = (int)(auxiliaryViewFraction*(mainViewRect.width - 7));
			mainViewPixels = mainViewRect.width - 7 - auxViewPixels;
		}
		auxViewPixels = Math.max(auxViewPixels,0);
		mainViewPixels = Math.max(mainViewPixels,0);
		if (auxViewPixels == 0 && mainViewPixels == 0) {
			dividingBarRect = mainViewRect;
			mainViewRect = auxViewRect = new Rectangle(0,0,0,0);
			return;
		}
		switch (auxiliaryViewPosition) {
		case AUX_VIEW_ON_LEFT:
			mainViewRect.x = mainViewRect.x + 7 + auxViewPixels;
			mainViewRect.width = mainViewPixels;
			auxViewRect.width = auxViewPixels;
			dividingBarRect = new Rectangle(auxViewRect.x + auxViewRect.width, auxViewRect.y, 7, auxViewRect.height);
			break;
		case AUX_VIEW_ON_RIGHT:
			auxViewRect.x = auxViewRect.x + 7 + mainViewPixels;
			mainViewRect.width = mainViewPixels;
			auxViewRect.width = auxViewPixels;
			dividingBarRect = new Rectangle(mainViewRect.x + mainViewRect.width, mainViewRect.y, 7, mainViewRect.height);
			break;
		case AUX_VIEW_ON_TOP:
			mainViewRect.y = mainViewRect.y + 7 + auxViewPixels;
			mainViewRect.height = mainViewPixels;
			auxViewRect.height = auxViewPixels;
			dividingBarRect = new Rectangle(auxViewRect.x, auxViewRect.y + auxViewRect.height, auxViewRect.width, 7);
			break;
		case AUX_VIEW_ON_BOTTOM:
			auxViewRect.y = auxViewRect.y + 7 + mainViewPixels;
			mainViewRect.height = mainViewPixels;
			auxViewRect.height = auxViewPixels;
			dividingBarRect = new Rectangle(mainViewRect.x, mainViewRect.y + mainViewRect.height, mainViewRect.width, 7);
			break;
		}
	}

	/**
	 * Return the Exhibit that is currently installed in the display.  If no View is
	 * associated with the Display, this returns null.  Otherwise, it
	 * returns the exhibit associated with the current View; this value
	 * can also be null even if the view is not null;
	 */
	public Exhibit getExhibit() {
		return (mainView == null)? null : mainView.getExhibit();
	}
	
	/**
	 * Draw the Exhibit, if any.  This method always draws the Exhibit using a
	 * graphics context in which the upper left corner is (0,0) and such that any Border that
	 * has been added to the display is not included in the drawing area of this
	 * graphics context.  If a View is installed in the Display, then that view is
	 * responsible for all drawing.  If not, the Display is simply filled with the
	 * white backgroun color.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mainView == null) {
			g.setColor(getBackground());
			g.fillRect(0,0,getWidth(),getHeight());
		}
		else {
			((Graphics2D)g).setBackground(mainView.getBackground());
			g.setColor(mainView.getForeground());
			Insets insets = getInsets();
			if (insets == null)  // ?? impossible ??
				insets = new Insets(0,0,0,0);
			BufferedImage precomputedFrame = null;
			if (animation != null && animation instanceof TimerAnimation)
				precomputedFrame = ((TimerAnimation)animation).getCurrentFilmstripFrame();
			if (precomputedFrame != null) {
				g.drawImage(precomputedFrame, insets.left, insets.top, getWidth()-insets.left-insets.right,getHeight()-insets.top-insets.bottom, null);
			}
			else {
				Rectangle sizeRect = new Rectangle(insets.left,insets.top,getWidth()-insets.left-insets.right,getHeight()-insets.top-insets.bottom);
				if (mainViewRect == null || ! sizeRect.equals(basisUsedForComputingRects)) {
					makeViewRects();
					basisUsedForComputingRects = sizeRect;
				}
				if (dividingBarRect != null) {
					g.setColor(Color.GRAY);
					g.fillRect(dividingBarRect.x,dividingBarRect.y,dividingBarRect.width,dividingBarRect.height);
					g.setColor(Color.DARK_GRAY);
					g.drawRect(dividingBarRect.x,dividingBarRect.y,dividingBarRect.width-1,dividingBarRect.height-1);
					g.setColor(mainView.getForeground());
				}
				Graphics g1;
				Graphics auxG1 = null;
				g1 = g.create(mainViewRect.x,mainViewRect.y,mainViewRect.width,mainViewRect.height);
				mainView.render((Graphics2D)g1,mainViewRect.width,mainViewRect.height);
				if (auxiliaryView != null) {
					auxG1 = g.create(auxViewRect.x,auxViewRect.y,auxViewRect.width,auxViewRect.height);
					auxiliaryView.render((Graphics2D)auxG1,auxViewRect.width,auxViewRect.height);
					auxG1.dispose();
				}
				if (animation != null && animation instanceof TimerAnimation && ((TimerAnimation)animation).getUseFilmstrip()) {
//					System.out.println("From Display, availableFrames = " +  Filmstrip.maxFrames(getWidth(),getHeight(),true));
					String str = "";
					if (mainView.buildingImageForFilmstrip) {
						int frameNum = Math.max(0,((TimerAnimation)animation).getFrameNumber());
						int frames = ((TimerAnimation)animation).getFrames();
						if (frames <= 0)
							str = I18n.tr("vmm.core.Display.BuildingFrameNum",""+frameNum) + str;
						else
							str = I18n.tr("vmm.core.Display.BuildingFrameNumOf",""+frameNum,""+frames) + str;
					}
					else if (((TimerAnimation)animation).getFrameNumber() >= 0) {
						try {
							BufferedImage frameImage;
							if (auxiliaryView == null)
								frameImage = mainView.getImage(true);
							else {
								frameImage = new BufferedImage(getWidth() - insets.left - insets.right,
										getHeight() - insets.left - insets.right, BufferedImage.TYPE_INT_RGB);
								Graphics ig = frameImage.getGraphics();
								Graphics2D ig1 = (Graphics2D)ig.create(mainViewRect.x-insets.left,mainViewRect.y-insets.top,mainViewRect.width,mainViewRect.height);
								mainView.render(ig1,mainViewRect.width,mainViewRect.height);
								ig1.dispose();
								ig1 = (Graphics2D)ig.create(auxViewRect.x-insets.left,auxViewRect.y-insets.top,auxViewRect.width,auxViewRect.height);
								auxiliaryView.render(ig1,auxViewRect.width,auxViewRect.height);
								ig1.dispose();
								ig.setColor(Color.GRAY);
								ig.fillRect(dividingBarRect.x-insets.left,dividingBarRect.y-insets.top,dividingBarRect.width,dividingBarRect.height);
								ig.setColor(Color.DARK_GRAY);
								ig.drawRect(dividingBarRect.x-insets.left,dividingBarRect.y-insets.top,dividingBarRect.width-1,dividingBarRect.height-1);
								ig.dispose();
							}
							((TimerAnimation)animation).saveCurrentFrame(frameImage);
						}
						catch (OutOfMemoryError e) {
							str = I18n.tr("vmm.core.Display.OutOfMemDuringAnimation");
						}
						int frameNum = ((TimerAnimation)animation).getFrameNumber();
						int frames = ((TimerAnimation)animation).getFrames();
						if (frames <= 0)
							str = I18n.tr("vmm.core.Display.FrameNum",""+frameNum) + str;
						else
							str = I18n.tr("vmm.core.Display.FrameNumOf",""+frameNum,""+frames) + str;
					}
					if (str.length() > 0) {
						g1.dispose();
						g1 = g.create();
						FontMetrics fm = g1.getFontMetrics(g1.getFont());
						int height = fm.getAscent() + fm.getDescent() + 10;
						int width = fm.stringWidth(str) + 10;
						g1.setColor(Color.LIGHT_GRAY);
						g1.fillRect(5+insets.left,getHeight()-insets.bottom-height-5,width,height);
						g1.setColor(Color.BLACK);
						g1.drawRect(5+insets.left,getHeight()-insets.bottom-height-5,width-1,height-1);
						g1.drawString(str,10+insets.left,getHeight()-insets.bottom-10-fm.getDescent());
					}
				}
				g1.dispose();
				if (dragTask != null) {
					if (auxiliaryView == null || draggingInMainView) {
						g1 = g.create(mainViewRect.x,mainViewRect.y,mainViewRect.width,mainViewRect.height);
						dragTask.drawWhileDragging((Graphics2D)g1, this, mainView, mainViewRect.width, mainViewRect.height);
					}
					else {
						g1 = g.create(auxViewRect.x,auxViewRect.y,auxViewRect.width,auxViewRect.height);
						dragTask.drawWhileDragging((Graphics2D)g1, this, auxiliaryView, auxViewRect.width, auxViewRect.height);
					}
					g1.dispose();
				}
			}
		}
	}
	
	/**
	 * Sets the cursor for this Display.  This method is called at several points in this
	 * file to set the cursor.  If an animation is running, the curser is set to the
	 * predefined WAIT_CURSOR.  Otherwise, if a mouse task is active, the cursor
	 * is obtained by calling the <code>getCursor</code> method of the mouse task.
	 * If neither of these is true, then the default cursor is used.
	 */
	protected void chooseCursor() {
		Cursor cursor = null;
		if (animation != null && animation.isRunning())
			cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
		else if (oneShotMouseTask != null) {
			if (mouseInMainView && oneShotMouseTaskIsForMainView)
				cursor = oneShotMouseTask.getCursor(this, mainView);
			else if (mouseInAuxView && ! oneShotMouseTaskIsForMainView)
				cursor = oneShotMouseTask.getCursor(this, auxiliaryView);
		}
		else if (mouseInMainView && mouseTask != null)
			cursor = mouseTask.getCursor(this, mainView);
		else if (mouseInAuxView && auxiliaryMouseTask != null)
			cursor = auxiliaryMouseTask.getCursor(this, auxiliaryView);
		if (cursor == null)
			cursor = Cursor.getDefaultCursor();
		setCursor(cursor);
	}
	
	// --------------------------- Support for an animation -------------------------------
	
	/**
	 * Install an animation in the display and start it running. Any previously running
	 * animation is first canceled.  If a one-shot mouse task is active, it is canceled.
	 * (Animations and one-shot mouse tasks are mutually exclusive; only one can be
	 * installed at any given time.)
	 * @param anim The animation to be installed.  If this is null, no animation is installed,
	 * but any current animation or one-shot mouse task is still canceled.
	 */
	synchronized public void installAnimation(Animation anim) {
		if (animation != null)
			animation.cancel();
		if (oneShotMouseTask != null)
			oneShotMouseTask.finish(this, oneShotMouseTaskIsForMainView? mainView : auxiliaryView);
		oneShotMouseTask = dragTask = null;
		animation = anim;
		if (anim != null) {
			final Animation theAnimation = anim;
			anim.setTimeDilation(timeDilationForAnimations);
			anim.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					if ( ! theAnimation.isRunning() ) {
						synchronized(Display.this) {
							if (animation == theAnimation) {
								stopAnimation();
							}
						}
					}
				}
			});
			if (animation instanceof TimerAnimation) {
				((TimerAnimation)animation).setDisplay(this);
				if (((TimerAnimation)animation).getUseFilmstrip())
					setSavedFilmstrip(null,0);
			}
			anim.start();
			setStatus(STATUS_ANIMATION_RUNNING);
		}
		else {
			setStatus(STATUS_IDLE);
		}
		chooseCursor();
	}
	
	/**
	 * Stop any animation that is currently installed.  If no animation is installed,
	 * this has no effect.  Note that an animation that stops on its own is automatially
	 * removed from the display when it stops.  An animation that runs forever will continue to run until
	 * it is stopped explicitely by calling this method or by installing another animation or
	 * one-shot mouse task, or by removing the current exhibit from the display, or when
	 * the user clicks the mouse on this Display.
	 */
	synchronized public void stopAnimation() {
		if (animation != null) {
			if (animation.isRunning())
				animation.cancel();
			if (animation instanceof TimerAnimation) {
				((TimerAnimation)animation).setDisplay(null);
				Filmstrip f = ((TimerAnimation)animation).getFilmstrip();
				if (f != null) {
					f.stripNullFrames();
					int loop = ((TimerAnimation)animation).getLooping();
					if (loop == TimerAnimation.LOOP)
						f.setFrame(f.getFrameCount()+1, null);  // needed since last frame of oscillating animatin is not used.
					if (f.getFrameCount() > 1)
						setSavedFilmstrip(f,loop);
				}
			}
			animation = null;
			setStatus(STATUS_IDLE);
			repaint();
		}
		chooseCursor();
	}
	
	/**
	 * Pause the current animation, if it is currently running, or resume it if it is currently paused.
	 * If no animation is currently installed in the display, this has no effect.
	 */
	synchronized public void toggleAnimationPaused() {
		if (animation != null && animation.isRunning()) {  // In fact, it should not be possible to have an installed but not running animation.
			animation.setPaused( ! animation.isPaused() );
			setStatus( animation.isPaused()? STATUS_ANIMATION_PAUSED : STATUS_ANIMATION_RUNNING);
		}
	}
	
    /**
     * Sets the speed for any animation that runs in this display.  This is done by calling
     * the <code>setTimeDilation</code> method of each animation.  Note that this applies not
     * just the current animationn that is running in the display, but to any animation that
     * is installed in the future.
     * @param dilationFactor The time dilation factor for animations.  The default value, 1, means
     * that animations run at normal speed.  A factor of 2 means they run at half speed, of 3 means
     * that they run at 1/3 speed, and so on.  It is also possible to speed up animations by using
     * a time dilation factor between 0 and 1, but speed-up is always limited by processing time
     * required by the animation.
     * @see Animation#setTimeDilation(double)
     */
	synchronized public void setTimeDilationForAnimations(double dilationFactor) {
		if (dilationFactor < 0.2)
			dilationFactor = 0.2;
		else if (dilationFactor > 100)
			dilationFactor = 100;
		timeDilationForAnimations = dilationFactor;
		if (animation != null)
			animation.setTimeDilation(dilationFactor);
	}
	
	/**
	 * Returns the speed factor for animations that run in this display.
	 * @see #setTimeDilationForAnimations(double)
	 */
	public double getTimeDilationForAnimations() {
		return timeDilationForAnimations;
	}
	
	/**
	 * If this disply has saved a filmstrip from the most recent animation, the animation
	 * is discarded (freeing up the memory used by the filmstrip).
	 */
	public void discardFilmstrip() {
		setSavedFilmstrip(null,0);
	}
	
	/**
	 * This method plays a specified filmstrip animation.  After the animation is played,
	 * the Display returns to its previous state, showing the same Exhibit.
	 * @param filmstrip the filmstrip to be played.  If this value is null, then
	 *    the method call is simply ignored.
	 * @param looping determines whether the filmstrip is played once through, or in a loop, or 
	 *    oscillating back and forth.  The value is one of {@link TimerAnimation#LOOP},
	 *    {@link TimerAnimation#OSCILLATE}, or {@link TimerAnimation#ONCE}.
	 * @param preferredSize If this is non-null, then the preferred size of the display
	 *    is set to this value.  In the 3DXM application, this will cause the window to change
	 *    size.  After the animation, the preferred size of the display is set to the size
	 *    of the display  before the animation began (note: not the preferred size, which might 
	 *    be different from the actual size).
	 */
	public void playFilmstrip(final Filmstrip filmstrip, int looping, final Dimension preferredSize) {
//		setSavedFilmstrip(filmstrip,looping);
		if (filmstrip == null)
			return;
		final View dummyView = mainView == null ? new View() : null;
		final boolean saveStopOnResize = getStopAnimationsOnResize();
		final Dimension saveSize = preferredSize == null ? null : getSize();
		setStopAnimationsOnResize(false);
		if (preferredSize != null && !preferredSize.equals(getSize())) {
			if (preferredSize.equals(getPreferredSize()))
				preferredSize.height++;
			setPreferredSize(preferredSize);
		}
		TimerAnimation player = new TimerAnimation() {
			protected void drawFrame() {
				repaint();
			}
			BufferedImage getCurrentFilmstripFrame() {
				return filmstrip.getFrame(frameNumber);
			}
			protected void animationStarting() {
				if (dummyView != null)
					mainView = dummyView;
			}
			protected void animationEnding() {
				if (dummyView != null)
					mainView = null;
				if (saveSize != null)
					setPreferredSize(saveSize);
				setStopAnimationsOnResize(saveStopOnResize);
			}
		};
//		player.setFilmstrip(filmstrip);
		player.setFrames(filmstrip.getFrameCount() - 1);
		player.setLooping(looping);
		player.setMillisecondsPerFrame(100);
		installAnimation(player);
	}
	
	/**
	 * If there is a saved filmstrip in the display, this will play it back.
	 */
	public void playFilmstrip() {
		if (savedFilmstrip == null)
			return;
		TimerAnimation player = new TimerAnimation() {
			protected void drawFrame() { // nothing to do
			}
		};
		player.setFilmstrip(savedFilmstrip);
		player.setLooping(savedFilmstripLooping);
		player.setMillisecondsPerFrame(100);
		installAnimation(player);
	}
	
	/**
	 * Get the property that determines whether animations in this view are stopped
	 * when a resize occurs.
	 * @see #setStopAnimationsOnResize(boolean)
	 */
	public boolean getStopAnimationsOnResize() {
		return stopAnimationsOnResize;
	}

	/**
	 * Set the stopAnimationOnResize property, which determines whether animations in this
	 * view are stopped when a resize occurs and whether fastDrawing is used during resize.
	 * The default value is true, and should only be set to false on rare occasions.  This 
	 * property is meant to be managed by a View that is displayed in this display.
	 * Whenever a new View is installed, the value of this property is reset to true.
	 * <p>(This property was introduced only because resize events are generated when
	 * the display is split and when components are added to or removed from the
	 * "holder" (see {@link #getHolder()}).  When either of these two actions is taken
	 * at the time a view is installed in the display, the create animation will be stopped
	 * by the resize event unless the stopAnimationOnResize property has been set to false.
	 * This affects the ConformalMap category, where the display is split when the
	 * exhibit is first installed, and ODE categories, where a control panel is added to
	 * the holder when the exhibit is first installed.)
	 */
	public void setStopAnimationsOnResize(boolean stopAnimationsOnResize) {
		this.stopAnimationsOnResize = stopAnimationsOnResize;
	}	
	
	//----------------------------- Support for MouseTasks ---------------------------------
	
	/**
	 * Install a default mouse handler for this Display.  This mouse task will
	 * remain active until a new exhibit is installed or until a new default
	 * mouse task is installed using this method.  When a new Exhibit is installed,
	 * its default mouse task is automatically installed in the display.
	 * <p>Note that this installs the mouse task for the main view, in the case
	 * where there is also an auxiliary view (see {@link #installAuxiliaryView(View, View)}).
	 * <p>If a one-shot mouse task is active (in the main view) when this method is installed,
	 * the one-shot mouse task is canceled.  
	 */
	synchronized public void installMouseTask(MouseTask task) {
		if (oneShotMouseTask != null && oneShotMouseTaskIsForMainView) {
			oneShotMouseTask.finish(this,mainView);
			setStatus(STATUS_IDLE);
			oneShotMouseTask = null;
		}
		dragTask = null;
		if (mouseTask != null)
			mouseTask.finish(this,mainView);
		mouseTask = task;
		if (mouseTask != null)
			mouseTask.start(this,mainView);
		chooseCursor();
		setStatusText(null);
	}
	
	/**
	 * Returns the current mouse task.  Note that this returns the default mouse
	 * handler for the display.  The default mouse task is installed by 
	 * {@link #installMouseTask(MouseTask)} or is installed automatically when
	 * an Exhibit is installed.  The value can be null.
	 * Any one-shot mouse task, as installed
	 * with {@link #installOneShotMouseTask(MouseTask)},  is <b>not</b> the value 
	 * that is returned here.
	 */
	public MouseTask getMouseTask() {
		return mouseTask;
	}
	
	/**
	 * Install a default mouse handler for the auxiliary view in Display.  Nothing will be done
	 * if there is no auxiliary view in the display.
	 * <p>If a one-shot mouse task is active in the auxiliary view when this method is installed,
	 * the one-shot mouse task is canceled.  
	 */
	synchronized public void installAuxiliaryMouseTask(MouseTask task) {
		if (auxiliaryView == null)
			return;
		if (oneShotMouseTask != null && !oneShotMouseTaskIsForMainView) {
			oneShotMouseTask.finish(this,auxiliaryView);
			setStatus(STATUS_IDLE);
			oneShotMouseTask = null;
		}
		dragTask = null;
		if (auxiliaryMouseTask != null)
			auxiliaryMouseTask.finish(this,auxiliaryView);
		auxiliaryMouseTask = task;
		if (auxiliaryMouseTask != null)
			auxiliaryMouseTask.start(this,auxiliaryView);
		chooseCursor();
		setStatusText(null);
	}
	
	
	/**
	 * Install a mouse task that will fire just once (or maybe a few times).  After each click,
	 * the mouse task's {@link MouseTask#wantsMoreClicks(Display, View)} method is called to see whether
	 * it should be removed from the display.
	 * This takes precedence over any default mouse task installed by the <code>installMouseTask</code>
	 * method, but the default mouse task is restored after the one-shot mouse task is removed.
	 * The one-shot mouse task will be canceled if an animation is installed, or if another
	 * one-shot mouse task is installed, or if a new Exhbit is installed.  Since animations
	 * and one-shot mouse tasks are mutually exclusive, installing a one-shot mouse task
	 * will cancel any animation that is currently running in the display.
	 * <p>Note that in the case where there is an auxiliary view in this display, this installs
	 * a mouse task that is active only in the main display.  If the user clicks the
	 * auxiliary view while a one-shot mouse task is active in the main display, the
	 * user is told to click the main view instead.
	 * @see #installMouseTask(MouseTask)
	 */
	synchronized public void installOneShotMouseTask(MouseTask task) {
		if (oneShotMouseTask != null)
			oneShotMouseTask.finish(this,mainView);
		oneShotMouseTaskIsForMainView = true;
		dragTask = null;
		oneShotMouseTask = task;
		if (oneShotMouseTask != null) {
			stopAnimation();
			oneShotMouseTask.start(this,mainView);
			setStatus(STATUS_ONE_SHOT_MOUSE_TASK);
		}
		else
			setStatus(STATUS_IDLE);
		chooseCursor();
	}
	
	/**
	 * Install a mouse task in the auxiliary view
	 * that will fire just once (or maybe a few times).  After each click,
	 * the mouse task's {@link MouseTask#wantsMoreClicks(Display, View)} method is called to see whether
	 * it should be removed from the display.
	 * This takes precedence over any default mouse task installed by the <code>installAuxiliaryMouseTask</code>
	 * method, but the default mouse task is restored after the one-shot mouse task is removed.
	 * The one-shot mouse task will be canceled if an animation is installed, or if another
	 * one-shot mouse task is installed, or if a new Exhbit is installed.  Since animations
	 * and one-shot mouse tasks are mutually exclusive, installing a one-shot mouse task
	 * will cancel any animation that is currently running in the display.
	 * <p>Note that this installs
	 * a one-shot mouse task that is active only in the main display.  If the user clicks the
	 * main view while a one-shot mouse task is active in the auxiliary display, the
	 * user is told to click the main view instead.
	 * @see #installMouseTask(MouseTask)
	 * @see #installOneShotMouseTask(MouseTask)
	 */
	synchronized public void installAuxiliaryOneShotMouseTask(MouseTask task) {
		if (auxiliaryView == null)
			return;
		if (oneShotMouseTask != null)
			oneShotMouseTask.finish(this, oneShotMouseTaskIsForMainView? mainView : auxiliaryView);
		dragTask = null;
		oneShotMouseTaskIsForMainView = false;
		oneShotMouseTask = task;
		if (oneShotMouseTask != null) {
			stopAnimation();
			oneShotMouseTask.start(this,auxiliaryView);
			setStatus(STATUS_ONE_SHOT_MOUSE_TASK);
		}
		else
			setStatus(STATUS_IDLE);
		chooseCursor();
	}
	
	/**
	 * Cancels any mouse task and one-shot mouse task currently installed  in this Display.
	 * This is called when a new View and Exhibit is installed in this Display.
	 */
	synchronized protected void cancelMouseTasks() {
		if (oneShotMouseTask != null)
			oneShotMouseTask.finish(this, oneShotMouseTaskIsForMainView? mainView : auxiliaryView);
		if (auxiliaryMouseTask != null)
			auxiliaryMouseTask.finish(this, auxiliaryView);
		if (mouseTask != null)
			mouseTask.finish(this, mainView);
		dragTask = oneShotMouseTask = mouseTask = auxiliaryMouseTask  = null;
		chooseCursor();
	}
	
	/**
	 * This class defines the mouse hander that handles all mouse clicks on this
	 * Display.
	 */
	private class MouseHandler implements MouseListener, MouseMotionListener {
		Rectangle rect;
		boolean draggingDividingBar;
		int barOffset;
		public void mousePressed(MouseEvent evt) { 
			if (dragTask != null)
				return;
			draggingDividingBar = false;
			if (animation != null && animation.isRunning()) {
				stopAnimation();  // if an animation is running, a mouse click stops it and does nothing else
				return;
			}
			if (mainViewRect == null)
				makeViewRects();
			if (mainViewRect.contains(evt.getX(),evt.getY())) {
				rect = mainViewRect;
				mouseInMainView = draggingInMainView = true;
				mouseInAuxView = false;
			}
			else if (auxViewRect != null && auxViewRect.contains(evt.getX(),evt.getY())) {
				rect = auxViewRect;
				mouseInMainView = draggingInMainView = false;
				mouseInAuxView = true;
			}
			else if (dividingBarRect != null && auxiliaryViewIsResizable && dividingBarRect.contains(evt.getX(),evt.getY())) {
				draggingDividingBar = true;
				if (auxiliaryViewPosition == AUX_VIEW_ON_LEFT || auxiliaryViewPosition == AUX_VIEW_ON_RIGHT)
					barOffset = evt.getX() - dividingBarRect.x;
				else
					barOffset = evt.getY() - dividingBarRect.y;
				mainView.setFastDrawing(true);
				auxiliaryView.setFastDrawing(true);
				return;
			}
			else {
				mouseInMainView = mouseInAuxView = false;
				return;  // if click point is in insets area or dividing bar between main and auxiliary views
			}
			evt.translatePoint(-rect.x,-rect.y);
			if (oneShotMouseTask != null) {
				if (auxiliaryView != null && draggingInMainView != oneShotMouseTaskIsForMainView) {
					JOptionPane.showMessageDialog(Display.this, 
						oneShotMouseTaskIsForMainView ? I18n.tr("vmm.core.Display.PleaseClickRight") : 
							I18n.tr("vmm.core.Display.PleaseClickLeft"));
					return;
				}
				if (oneShotMouseTask.doMouseDown(evt,Display.this,
						oneShotMouseTaskIsForMainView? mainView : auxiliaryView,rect.width,rect.height))
					dragTask = oneShotMouseTask;
				else if ( ! oneShotMouseTask.wantsMoreClicks(Display.this, oneShotMouseTaskIsForMainView? mainView : auxiliaryView) ) {   
					oneShotMouseTask.finish(Display.this, oneShotMouseTaskIsForMainView? mainView : auxiliaryView);
					oneShotMouseTask = null;
					setStatus(STATUS_IDLE);
				}
			}
			else if (draggingInMainView) {
				if (mouseTask != null) {
					if (mouseTask.doMouseDown(evt,Display.this,mainView,rect.width,rect.height))
						dragTask = mouseTask;
				}
			}
			else if (auxiliaryMouseTask != null) {
				if (auxiliaryMouseTask.doMouseDown(evt,Display.this,auxiliaryView,rect.width,rect.height))
					dragTask = auxiliaryMouseTask;
			}
			Cursor cursor = null;
			if (dragTask != null) {
				cursor = dragTask.getCursorForDragging(evt,Display.this, draggingInMainView? mainView : auxiliaryView);
				if (cursor != null)	
					setCursor(cursor);
				else 
					setCursor(Cursor.getDefaultCursor());
			}
			else
				chooseCursor();
		}
		public void mouseReleased(MouseEvent evt) {
			if (draggingDividingBar) {
				mainView.setFastDrawing(false);
				auxiliaryView.setFastDrawing(false);
				draggingDividingBar = false;
				repaint();
			}
			else if (dragTask != null) {
				evt.translatePoint(-rect.x,-rect.y);
				View dragView = draggingInMainView? mainView : auxiliaryView;
				dragTask.doMouseUp(evt,Display.this,dragView,rect.width,rect.height);
				if (dragTask == oneShotMouseTask && oneShotMouseTask != null && 
						(! oneShotMouseTask.wantsMoreClicks(Display.this,dragView) )) {
					oneShotMouseTask.finish(Display.this,dragView);
					oneShotMouseTask = null;
					setStatus(STATUS_IDLE);
				}
				dragTask = null;
				chooseCursor();
				repaint();
			}
		}
		public void mouseDragged(MouseEvent evt) {
			if (draggingDividingBar) {
				Insets insets = getInsets();
				if (insets == null)
					insets = new Insets(0,0,0,0);
				if (auxiliaryViewPosition == AUX_VIEW_ON_LEFT || auxiliaryViewPosition == AUX_VIEW_ON_RIGHT) {
					int newLeft = evt.getX() - barOffset;
					if (newLeft < insets.left)
						newLeft = insets.left;
					else if (newLeft > getWidth() - insets.right - 7)
						newLeft = getWidth() - insets.right - 7;
					if (newLeft != dividingBarRect.x) {
						double ratio = (double)(newLeft - insets.left) / (getWidth() - insets.left - insets.right - 7);
						if (ratio < 0)
							ratio = 0;
						auxiliaryViewFraction = auxiliaryViewPosition == AUX_VIEW_ON_LEFT ? ratio : 1 - ratio;
						mainViewRect = null;
						repaint();
					}
				}
				else {
					int newTop = evt.getY() - barOffset;
					if (newTop < insets.top)
						newTop = insets.top;
					else if (newTop > getHeight() - insets.bottom - 7)
						newTop = getHeight() - insets.bottom - 7;
					if (newTop != dividingBarRect.y) {
						double ratio = (double)(newTop - insets.top) / (getHeight() - insets.top - insets.bottom - 7);
						if (ratio < 0)
							ratio = 0;
						auxiliaryViewFraction = auxiliaryViewPosition == AUX_VIEW_ON_TOP ? ratio : 1 - ratio;
						mainViewRect = null;
						repaint();
					}
				}
			}
			else if (dragTask != null) {
				evt.translatePoint(-rect.x,-rect.y); 
				View dragView = draggingInMainView? mainView : auxiliaryView;
				dragTask.doMouseDrag(evt,Display.this,dragView,rect.width,rect.height);
				Cursor cursor = dragTask.getCursorForDragging(evt,Display.this,dragView);
				if (cursor != null)	
					setCursor(cursor);
				else 
					setCursor(Cursor.getDefaultCursor());
			}
		}
		public void mouseMoved(MouseEvent evt) {
			checkCursor(evt.getX(),evt.getY());
		}
		public void mouseEntered(MouseEvent evt) {
			checkCursor(evt.getX(),evt.getY());
		}
		public void mouseExited(MouseEvent evt) {
			checkCursor(evt.getX(),evt.getY());
		}
		public void mouseClicked(MouseEvent evt) {
		}
		private void checkCursor(int x, int y) {
			if (mainViewRect == null)
				makeViewRects();
			mouseInMainView = mainViewRect.contains(x,y);
			mouseInAuxView = auxViewRect != null && auxViewRect.contains(x,y);
			if (auxiliaryView == null || animation != null)
				return;
			if (dividingBarRect.contains(x,y)) {
				if (!auxiliaryViewIsResizable)
					setCursor(Cursor.getDefaultCursor());
				else if (auxiliaryViewPosition == AUX_VIEW_ON_LEFT || auxiliaryViewPosition == AUX_VIEW_ON_RIGHT)
					setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				else
					setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			}
			else
				chooseCursor();
		}
	}
	
	
	//------------ Support for container and status bar ----------------------------------
	
	
	private class DisplayHolder extends JPanel {  // The class that implements the container.
		private JLabel statusBar;
		private JSplitPane splitPane;
		private DisplayHolder() {
			setLayout(new BorderLayout(1,1));
			setBackground(Color.BLACK);
			statusBar = new JLabel(I18n.tr("vmm.core.Display.statusbar.noExhibit"));
			statusBar.setBorder(BorderFactory.createEmptyBorder(2,2,1,1));
			statusBar.setBackground(Color.LIGHT_GRAY);
			statusBar.setForeground(Color.BLACK);
			statusBar.setOpaque(true);
			if (showStatusBar)
				add(statusBar, BorderLayout.SOUTH);
			add(Display.this, BorderLayout.CENTER);
		}
		private void reset() {
			removeAll();
			setLayout(new BorderLayout(1,1));
			setBackground(Color.BLACK);
			if (showStatusBar)
				add(statusBar,BorderLayout.SOUTH);
			add(Display.this, BorderLayout.CENTER);
			statusBar.setText(I18n.tr("vmm.core.Display.statusbar.noExhibit"));
			validate();
		}
	}
	
	/**
	 * Sets the value of the showStatusBar property.  If this property is true,
	 * then a status bar will be shown at the bottom of the container that
	 * is returned by {@link #getHolder()}.
	 */
	public void setShowStatusBar(boolean show) {
		if (show == showStatusBar)
			return;
		if (holder != null) {
			if (show)
				holder.add(holder.statusBar, BorderLayout.SOUTH);
			else
				holder.remove(holder.statusBar);
			validate();
		}
		showStatusBar = show;
	}
	
	/**
	 * Returns the value of the showStatusBar property.
	 * @see #setShowStatusBar(boolean)
	 */
	public boolean getShowStatusBar() {
		return showStatusBar;
	}

	/**
	 * Returns the string from the status bar.  If no holder holder has been
	 * created for this display (by calling {@link #getHolder()}), then the
	 * return value is null; otherwise, the return value is the text from the
	 * JLabel that is used in the status bar of the holder.  (This works even
	 * if the status bar is currently hidden.)
	 */
	public String getStatusText() {
		if (holder == null)
			return null;
		else
			return holder.statusBar.getText();
	}
	
	/**
	 * Calls <code>setStatusText(null)</code>.
	 * @see #setStatusText(String)
	 */
	public void setStatusText() {
		setStatusText(null);
	}
	
	/**
	 * Sets the text to be displayed in the status bar of the holder for this display.
	 * If no holder has been created for this display (by calling {@link #getHolder()}),
	 * then this method has no effect at all.  If the parameter is null, then a status
	 * string is obtained as follows: If there is no exhibit in the display, then the
	 * status string is "No Exhiibt" (in the default English locale); otherwise if there is an
	 * animation, the animation's {@link Animation#getStatusText(boolean)} method is called
	 * to get the status string, and if the return value is null the string "Animation Runing"
	 * or "Animation Paused" is used; otherwise if there is a one-shot mouse task its
	 * {@link MouseTask#getStatusText()} method is called, and if the return value is null
	 * then the string "Waiting for mouse input" is used; otherwise, if there is a mouse
	 * task <b>and</b> if the {@link MouseTask#getStatusText()} method in the mouse task returns
	 * a non-null value, then that is used as the status string; otherwise, the
	 * current view's {@link View#getStatusText()} method is called and if it returns a non-null
	 * value then that is used as the status text; otherwise, the status string is set
	 * to the current exhibit's title.  The idea to is provide a reasonable default string that
	 * describes the current status, but to allow animations, mouse tasks, and views to
	 * specify alternative strings as appropriate; this will probably be most useful
	 * for one-shot mouse tasks.  Note that the status string changes automatically when
	 * a new exhibit, animation, mouse task, or one-shot mouse task is installed, and the
	 * previous string is discarded.  It will be relatively rare to call this method
	 * directly.
	 * @param message the string to be displayed in the status bar, or null to construct
	 * a string based on the current state of the display.
	 */
	synchronized public void setStatusText(String message) {
		if (holder != null) {
			if (message == null) {
				if (status == STATUS_EMPTY || getExhibit() == null)
					message = I18n.tr("vmm.core.Display.statusbar.noExhibit");
				else if (status == STATUS_ANIMATION_RUNNING) {
					message = animation.getStatusText(true);
					if (message == null)
						message = I18n.tr("vmm.core.Display.statusbar.animationRunning");
				}
				else if (status == STATUS_ANIMATION_PAUSED) {
					message = animation.getStatusText(false);
					if (message == null)
						message = I18n.tr("vmm.core.Display.statusbar.animationPaused");
				}
				else if (status == STATUS_ONE_SHOT_MOUSE_TASK) {
					message = oneShotMouseTask.getStatusText();
					if (message == null)
						message = I18n.tr("vmm.core.Display.statusbar.oneShotMouseTask");
				}
				else {
					if (mouseTask != null)
						message = mouseTask.getStatusText();
					if (message == null)
						message = mainView.getStatusText();
					if (message == null)
						message = getExhibit().getTitle();
				}
			}
			holder.statusBar.setText(message);
//			holder.statusBar.paintImmediately(0,0,holder.statusBar.getWidth(),holder.statusBar.getHeight());
//                 previous line removed because it only works on Mac
		}
	}
	
	/**
	 * Adds an extra component to the display's holder.  Has no effect unless a holder has been
	 * created for the display by calling {@link #getHolder()}.  This method adds a component in
	 * the north, south, east, or west postions of the holder's BorderLayout.
	 * @param c  the component to be added.  This should be non-null.
	 * @param position specifies the position to be occupied by the component.  Must be one of the 
	 * values BorderLayout.NORTH, BorderLayout.EAST, BorderLayout.WEST, or BorderLayout.SOUTH.  If
	 * the value is BorderLayout.SOUTH, the status bar that usually occupies that postion will be
	 * removed, the showStatusBar property will be set to false, and the new component will be placed
	 * in the south position.  You should be careful not to add two components in the same position;
	 * no error-checking is done to prevent this.
	 * @throws IllegalArgumentException if position is not one of the four legal values
	 */
	public void addBorderComponent(Component c, Object position) {
		if (position != BorderLayout.NORTH && position != BorderLayout.EAST 
				&& position != BorderLayout.WEST && position != BorderLayout.SOUTH)
			throw new IllegalArgumentException("Internal Error:  Border component position must be north, south, east, or west.");
		if (holder != null) {
			if (position == BorderLayout.SOUTH && showStatusBar) {
				holder.remove(holder.statusBar);
				showStatusBar = false;
			}
			holder.add(c,position);
			holder.validate();
		}
	}
	
	/**
	 * Removes a specified component from the display's holder.  This has no effect if no
	 * holder has been created for the display (by calling {@link #getHolder()}).
	 * @param c the component to be removed.  Presumably, this is a component that has been
	 * added to the holder by calling {@link #addBorderComponent(Component, Object)}, but no
	 * error checking is done.  The holder's remove() method is simply called with c as
	 * its parameter.
	 */
	public void removeBorderComponent(Component c) {
		if (holder != null) {
			holder.remove(c);
			holder.validate();
		}
	}
	
	/**
	 * Calls <code>split(newComponent,false,false)</code>.
	 * @see #split(Component, boolean, boolean)
	 */
	public JSplitPane split(Component newComponent) {
		return split(newComponent,false,false);
	}
	
	/**
	 * Replaces the display in its holder with a JSplitPane that contains the display and another component.
	 * Has no effect if no holder has been created for this display (by calling {@link #getHolder()}.  If the
	 * display has aleady been split, the previous JSplitPane and its extra component are thrown away.
	 * Note that {@link #split(Component)} calls this method with the most likly value (false) for the second
	 * and third parameters.
	 * @param newComponent the other component, in addition to the display, that will replace the display
	 * in the center position of the holder
	 * @param splitVertically if true, the JSplitPane is split vertically; if false, it is split horizontally
	 * @param displayIsSecondComponent if true, the display is in the right or bottom position of the split pane;
	 * if false, it is in the left or top position
	 * @return the JSplitPane, in case you want to do further customization.  The pane is set to use
	 * half the available height or width for each component, to distribute new space equally during
	 * a resize of the pane, and to have no border.
	 */
	public JSplitPane split(Component newComponent, boolean splitVertically, boolean displayIsSecondComponent) {
		if (holder != null) {
			if (holder.splitPane != null)
				holder.remove(holder.splitPane);
			else
				holder.remove(this);
			JSplitPane pane;
			Component first = displayIsSecondComponent? newComponent : this;
			Component secnd = displayIsSecondComponent? this : newComponent;
			if (splitVertically)
				pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, first, secnd);
			else
				pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, first, secnd);
			pane.setBorder(null);
			holder.add(pane,BorderLayout.CENTER);
			holder.validate();
			pane.setDividerLocation(0.5);
			pane.setResizeWeight(0.5);
			holder.splitPane = pane;
			return holder.splitPane;
		}
		else
			return null;
	}
	
	/**
	 * Removes the component added by {@link #split(Component)} or {@link #split(Component, boolean, boolean)}.
	 * Has no effect if no such component has been added.
	 */
	public void unsplit() {
		if (holder != null && holder.splitPane != null) {
			holder.remove(holder.splitPane);
			holder.splitPane = null;
			holder.add(this,BorderLayout.CENTER);
			holder.validate();
		}
	}
	
	/**
	 * Restores the holder to its default state; this is called automatically by
	 * {@link #install(View, Exhibit)}. Has no effect if no holder has been created
	 * for this display.
	 * @see #getHolder()
	 */
	public void resetHolder() {
		if (holder != null)
			holder.reset();
	}
	
	/**
	 * Returns the container or "holder" for this display.  The usual way to use a Display is to call
	 * this method to get its holder, and add the holder to a parent container rather than the Display
	 * itself.  You should ordinarily not do anything else with the holder except to add it to a container.
	 * The holder uses a BorderLayout with the display in its center position.  It has a status bar in
	 * its south position; the status bar can be hidden and shown using {@link #setShowStatusBar(boolean)}.
	 * Other components can be added to the holder using {@link #addBorderComponent(Component, Object)},
	 * {@link #split(Component)}, and {@link #split(Component, boolean, boolean)}.   The holder
	 * uses black as its background color with gaps of 1 pixel between the components that it contains.
	 * When a new exhibit is installed (including when the exhibit is set to null), the holder is
	 * restored to its default state (BorderLayout with 1-pixel gaps, black background, display in center
	 * postion, and staus bar in south postion if the showStatusBar property is true).
	 */
	public Container getHolder() {
		if (holder == null)
			holder = new DisplayHolder();
		return holder;
	}
	
	//------- process component events to detect resizing -----------
	
	
	private Dimension previousSize;

	/**
	 * Used to call stopAnimation() if the size of the Display changes;
	 * not meant to be called directly.  Stops any ongoing animation and
	 * sets "fast drawing mode" to true so that the exhibit can be
	 * continually redrawn as the display is resized.
	 */
	public void processComponentEvent(ComponentEvent evt) {
		super.processComponentEvent(evt);
		if (evt.getID() == ComponentEvent.COMPONENT_RESIZED) {
			if (!stopAnimationsOnResize) {
				synchronized(this) {  // Note: I think this was here only to stop filmstrips from having different
                                    // sized frames, but I forgot exactly why I put it here.
					if ( animation == null || !(animation instanceof TimerAnimation) || 
							!((TimerAnimation)animation).getUseFilmstrip() )
						return;
				}
			}
			   // first, some ugly stuff to deal with extra resize events
			if (previousSize != null && previousSize.equals(getSize())) {
				//System.out.println("Bogus resize");
				return;
			}
			previousSize = getSize();
			stopAnimation();
			if (getView() != null && getExhibit() != null) {
				final View view = getView();
				getView().setFastDrawing(true,false);
				if (resizeTimer != null)
					resizeTimer.stop();
				resizeTimer = new Timer(0, new ActionListener() {
					public void actionPerformed(ActionEvent evt) { 
						view.setFastDrawing(false,false);
						resizeTimer.stop();
						resizeTimer = null;
					}
				});
				resizeTimer.setInitialDelay(500);
				resizeTimer.setRepeats(false);
				resizeTimer.start();
			}
		}
	}

}