/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * A MouseTask can be installed in a {@link vmm.core.Display} to respond 
 * to a mouse gesture starting from
 * a mouse press, through a sequence of mouse drags, and ending
 * with a mouse release.  It has access to the mouse events and
 * to the Display object and View objects that were clicked, and through the View
 * object, it has access to the {@link vmm.core.Exhibit} in the View.
 * @see vmm.core.Display#installMouseTask(MouseTask)
 */
abstract public class MouseTask {
	
	/**
	 *  Called when the user presses the mouse in the Display in which
	 *  this MouseTask is installed.  The return value indicates
	 *  whether or not the MouseTask wants to recieve the mouse
	 *  drag and mouse up events that follow the mouse down event.
	 *  If the return value is false, then the Display will not call
	 *  the other two methods.  This is an abstract method that must
	 *  be redefined in a subclass.  It should not be necessary to
	 *  call this method directly.
	 *  @param evt The mouse-down event, except that the x and y coordinates
	 *  have been translated so that the top left corner of the View is at
	 *  coordinates (0,0). 
	 *  @param width The width the drawing area (not including any insets).
	 *  @param height The height the drawing area (not including any insets).
	 */
	abstract public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height);
	
	/**
	 *  Continue processing a mouse gesture for which <code>doMouseDown</code>
	 *  returned a value of true.  If not redefined in a subclass, this method
	 *  does nothing.
	 *  @param evt The mouse-dragged event, except that the x and y coordinates
	 *  have been translated by display.getInsets().left and display.getInsets().top
	 *  to account for any border that has been applied to the display.  
	 *  @param width The width the drawing area (not including any insets).
	 *  @param height The height the drawing area (not including any insets).
	 */
	public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
	}
	
	/**
	 *  Finish processing a mouse gesture for which <code>doMouseDown</code>
	 *  returned a value of true.  If not redefined in a subclass,
	 *  does nothing.
	 *  @param evt The mouse-up event, except that the x and y coordinates
	 *  have been translated by display.getInsets().left and display.getInsets().top
	 *  to account for any border that has been applied to the display.  
	 *  @param width The width the drawing area (not including any insets).
	 *  @param height The height the drawing area (not including any insets).
	 */
	public void doMouseUp(MouseEvent evt, Display display, View view, int width, int height) {
	}
	
	
	/**
	 * This method is called by a Display when this MouseTask is installed in the Display,
	 * to give the MouseTask a chance to do any display-specific initialization (such as
	 * adding a decoration).  The default, as defined in this class, is to do nothing;
	 * subclasses can override this behavior if necessary.  It should not be necessary
	 * to call this method directly.
	 */
	public void start(Display display, View view) {
	}
	
	/**
	 * This method is called by a Display when this MouseTask is removed from the Display,
	 * to give the MouseTask a chance to do any display-specific clean-up (such as
	 * removing a decoration).  The default, as defined in this class, is to do nothing;
	 * subclasses can override this behavior if necessary.  It should not be necessary
	 * to call this method directly.
	 */
	public void finish(Display display, View view) {
	}
	
	
	/**
	 *  Return a cursor to be used in the display when this MouseTask
	 *  is installed and waiting for a mouse-down event.  If the return value is null, the default cursor
	 *  will be used.  If not redefined in a subclass, returns null.
	 *  @see #getCursorForDragging(MouseEvent, Display, View)
	 */
	public Cursor getCursor(Display display, View view) {
		return null;
	}
	
	/**
	 * When <code>doMouseDown</code> returns true, the Display class calls this method to
	 * determine which cursor to use during dragging. The first parameter
	 * is the same mouseDownEvent that was passed to <code>doMouseDown</code>; it can
	 * can be used to decide the appropriate cursor.  The version in
	 * this class simply returns "getCursor(display)".  Override it if
	 * you want the possibility of using a different cursor during dragging.
	 * @see #doMouseDown(MouseEvent, Display, View, int, int)
	 * @see #getCursor(Display, View)
	 */
	public Cursor getCursorForDragging(MouseEvent mouseDownEvent, Display display, View view) {
		return getCursor(display,view);
	}
	
	/**
	 * When the Display is repainted during a drag operation, this method will be
	 * called after the Exhibit is drawn, to give the MouseTask a chance to draw
	 * some extra stuff on top of the Exhibit.  The graphics context is a newly
	 * created one, which has been translated and clipped, if necessary, to
	 * account for any border that has been applied to the Display.  The width and
	 * height parameters give the width and height of the drawing area, in pixels.
	 */
	public void drawWhileDragging(Graphics2D g, Display display, View view, int width, int height) {
	}
	
	/**
	 * This method is called by a Display immediately after processing a click or
	 * click-and-drag for a one-shot mouse task to test whether the one-shot
	 * mouse task wants to receive additional mouse events.  This method is called
	 * only for one-shot mouse tasks.  It will probably be fairly unusual for this to
	 * occur; however, some one-shot mouse tasks might need two or more clicks to do
	 * their jobs.  If this method returns true, the Display will call this MouseTask's <code>getCursor</code>
	 * method immediately after calling this method; 
	 * a one-shot MouseTask that processes more than one click could use a distinctive
	 * cursor to help the user know what is going on, and it should consider setting the
	 * status bar text to tell the user what is going on.
	 * @return returns false, in the top-level MouseTask class.
	 * @see Display#installOneShotMouseTask(MouseTask)
	 * @see #getStatusText()
	 */
	public boolean wantsMoreClicks(Display display, View view) {
		return false;
	}

	/**
	 * Returns a string to be displayed in the status bar while this mouse task is active, or
	 * null to use the default text.
	 * @return returns null to indicate that the default status text is to be used.  For a one-shot mouse task,
	 * the default message is "Waiting for mouse input" (in the English version).  For a default mouse task,
	 * the default message is set by the view.
	 */
	public String getStatusText() {
		return null;
	}
}


