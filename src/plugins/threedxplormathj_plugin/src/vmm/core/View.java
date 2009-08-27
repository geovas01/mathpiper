/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionItem;
import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;

/**
 * A View represents one of possibly several views of an {@link vmm.core.Exhibit}.
 * A View displays its Exhbit in a {@link vmm.core.Display}.  These three classes
 * are the central classes in VMM.   Drawing takes place when the View's {@link #render(Graphics2D, int, int)}
 *  method is called.  Note that this
 * might potentially be called to draw the Exhibit to other destinations besides a Display, such as
 * to the graphics device associated with a printer.
 * <p>This top-level View class might be used directly for simple two-dimensional Exhibits, but
 * it is also likely to be used as a base class for customized Views for particular 
 * Exhibits or types of Exhibits.  In particular, a specialized View class will be used for 3D views.
 * <p>The View class contains several methods that can be used for drawing, such as
 * {@link #drawCurve(Point2D[])}, {@link #setColor(Color)}, and {@link #drawPixel(double, double)}.
 * <p>Note that to work correctly with the XML save/restore facility defined in {@link vmm.core.SaveAndRestore},
 * a subclass of View must have a parameterless constructor, and it must be an independent class or
 * a public nested class.
 */
public class View implements Parameterizable, Decorateable, ChangeListener {
	
	private Display display;
	private Exhibit exhibit;
	private String name;
	
	@VMMSave
	private boolean applyGraphics2DTransform = true;
	
	@VMMSave
	private boolean preserveAspect = true;
	
	@VMMSave
	private boolean antialiased;
	
	@VMMSave
	private boolean showAxes;
	
	@VMMSave
	private Color background = Color.white;
	
	@VMMSave
	private Color foreground = Color.black;
	
	private Point2D tempPoint = new Point2D.Double();  // For use in drawing ops -- not thread safe !!
	private int previousWidth, previousHeight;
	
	private Transform transform;  // The transform that is used for this View. The view is automatically redrawn when this transform changes.

	
	protected Animation buildAnimation;
	
	/**
	 * Used to determine when the off-screen image needs to be redrawn.  This is set to true
	 * when {@link #forceRedraw()} is called, and it is checked and reset to false in
	 * {@link #render(Graphics2D, int, int)}. 
	 */
	protected boolean needsRedraw;

	/**
	 * An offscreen image that is used for drawing.
	 */
	protected BufferedImage fullOSI;
		
	
	/**
	 * When an exhibit is being drawn, this variable is set to the graphics context where the exhibit
	 * is being drawn.  When the exhibit drawing is finished, the value is set to null.  This variable
	 * is used in some of the rendering methods that are provided by this class and its 3D subclass.
	 * @see #render(Graphics2D, int, int)
	 */
	protected Graphics2D currentGraphics;
	
	/**
	 * Parameters that have been added to this View.
	 */
	protected ArrayList<Parameter> parameters;
	
	/**
	 * Decorations that have been added to this view.
	 */
	protected ArrayList<Decoration> decorations;
	
	/**
	 * This variable was introduced to handle a specific special case: when an image is
	 * created asynchronously over a period of time, as in ray-traced views of implicit 
	 * surfaces.  This variable is used only during morphing of implicit surfaces (or 
	 * similar cases) to prevent the {@link Display} from grabbing an incomplete image and
	 * saving it to the {@link Filmstrip} of the morph that is being created.  The value
	 * of this variable is set to true while the image is only partially complete, and
	 * is reset to false when the image is complete.
	 */
	protected volatile boolean buildingImageForFilmstrip;
	
	/**
	 * The action for setting the showAxisProperty, which is one one of the commands returned
	 * by {@link #getViewCommands()}.  (This is created along with the View object and is non-null.)
	 */
	protected AbstractActionVMM showAxesAction = new AbstractActionVMM(I18n.tr("vmm.core.commands.ShowAxes")) {
		public void actionPerformed(ActionEvent evt) {
			setShowAxes( ! getShowAxes() );
		}
	};
	
	/**
	 * Used for setting the background color of this View, this contains three commands
	 * for black background, white background, and custom background.  It is one
	 * of the objects returned by {@link #getViewCommands()}.  (This is created along with
	 * the View object and is non-null.)
	 */
	protected ActionRadioGroup backgroundCommands = new ActionRadioGroup( new String[] {
			I18n.tr("vmm.core.commands.BlackBackground"),
			I18n.tr("vmm.core.commands.WhiteBackground"),
			I18n.tr("vmm.core.commands.CustomBackground")
	}, 1) {
		public void optionSelected(int selectedIndex) {
			if (selectedIndex == 0) {
				setBackground(Color.black);
				setSelectedIndex(0);
			}
			else if (selectedIndex == 1) {
				setBackground(Color.white);
				setSelectedIndex(1);
			}
			else if (getDisplay() != null) {
				Color c = JColorChooser.showDialog(getDisplay(),I18n.tr("vmm.core.dialogtitle.ChooseBackground"), getBackground());
				if (c != null) {
					setBackground(c);
					setSelectedIndex(2);
				}
				else {
					if (getBackground().equals(Color.black))
						setSelectedIndex(0);
					else if (getBackground().equals(Color.white))
						setSelectedIndex(1);
					else
						setSelectedIndex(2);
					return;
				}
			}
			forceRedraw();
		}
	};
	
	
	/**
	 * This is set to a true by {@link #beginDrawToOffscreenImage()} when a direct off-screen drawing operation is
	 * initiated.  It is reset to false when {@link #endDrawToOffscreenImage()} is called (or when a normal drawing
	 * operation begins in {@link #render(Graphics2D, int, int)}).  A subclass could test its value to tell whether 
	 * direct offscreen drawing is in progress.
	 */
	protected boolean directOffscreenDrawing;
	
	/**
	 * The type parameter used for creating athe offscreen image -- this is reset in View3DLit when 
	 * black and white image is selected.  It is not used at all in anaglyph stereo mode.
	 */
	protected int offscreenImageType = BufferedImage.TYPE_INT_RGB;
	
	
	/**
	 * Returns a name for this View.  By default, the name that is returned
	 * will be the same as the full class name of the class to which the View object belongs.
	 * However, a different name can be set using the <code>setName</code> method.  The name
	 * is intended to be something that can be used internally to distinguish one
	 * view from other views of the same Exhibit.  For a human-readable name,
	 * the <code>getTitle</code> method should be used instead of <code>getName</code>.
	 * (In practice, names are rarely used for views.)
	 * @see #getTitle()
	 */
	public String getName() {
		if (name == null)
			return this.getClass().getName();
		else
			return name;	
	}
	
	/**
	 * Sets the name for this view.
	 * @see #getName()
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns a human-readable title for this View that could, for example, be used in
	 * a menu that is presented to the user.  The title is obtained by using the name
	 * returned by <code>getName</code> as a key in the method {@link I18n#tr(String)}.  This allows
	 * for easy internationalization.  Note that if the <code>I18n.tr</code> method does not
	 * find a translation for the name, then the title will be the same as the name.
	 * (In practice, titles are rarely used for views.)
	 * @see #getName()
	 * @see I18n#tr(String)
	 */
	public String getTitle() {
		return I18n.tr(getName());
	}
	
	/**
	 * Returns the Display where this View is installed.  If it is not installed
	 * in a Display, the return value is null.
	 * @see Display#install(View, Exhibit)
	 */
	public Display getDisplay() {
		return display;
	}
	
	/**
	 * Sets the Display where this View is installed.  This method will ordinarily be
	 * called only by the <code>install</code> method in the Display class, which is used to install a
	 * View into a Display.  It is not meant to be called directly (since that would bypass a lot of
	 * the set-up performed automatically by the <code>install</code> method).
	 * @see Display#install(View, Exhibit)
	 */
	public void setDisplay(Display display) {
		this.display = display;
	}
	
	/**
	 * Get the Exhibit that is shown in this View.  If no Exibit is currently associated with 
	 * the view, then the return value is null.
	 */
	public Exhibit getExhibit() {
		return exhibit;
	}
	
	/**
	 * Set the Exhibit that is shown in this View.  If the View is already showing an
	 * Exhibit, it is first removed from the View.  When an Exhibit is installed, the
	 * transform associated with the View is set to the default transform of the Exhibit.
	 * If there is a Display associated with the View, its <code>repaint</code> method is called.
	 * This setExhibit method will be called automatically when an Exhibit is installed in a Display;
	 * although it might be called directly, that would be much less usual since it would
	 * by-pass all the set-up that is done automatically by the Display's <code>install</code> method.
	 * <p>If the exhibit is non-null, then the foreground and background colors of the view are set
	 * to the default foreground and background colors of the exhibit.  If the exhibit is null,
	 * then the view's foreground and background are set to black and white.
	 * <p>Note when overriding this method:  You should almost certainly start by saying
	 * "if (exhibit == getExhibit()) return".  That is, in general, this method should do nothing
	 * when the exhibit that is being installed is already installed.
	 * @see Display#install(View, Exhibit)
	 * @param exhibit The exhibit to be installed in this View.  The value can be null.
	 * In that case, no Exhibit will be associated with the View after the method executes.
	 * If exhibit is already installed in this View, then no changes are made.
	 */
	public void setExhibit(Exhibit exhibit) {
		if (exhibit == this.exhibit)
			return;
		if (this.exhibit != null) {
			this.exhibit.removeChangeListener(this);
			this.exhibit.removeView(this);
		}
		this.exhibit = exhibit;
		if (exhibit == null) {
			setTransform(null);
			setBackground(null);
			setForeground(null);
		}
		else {
			exhibit.addChangeListener(this);
			exhibit.addView(this);
			setTransform(exhibit.getDefaultTransform(this));
			setBackground(exhibit.getDefaultBackground());
			setForeground(exhibit.getDefaultForeground());
		}
		forceRedraw();
	}
	
	/**
	 * This method is called by the {@link Display} that contains this View, just before the
	 * view is removed from the display.  It gives the view a chance to clean up.  The method
	 * in this top-level view class does nothing.  It is not likely that this method will be called
	 * directly.
	 */
	public void finish() {		
	}
	
	/**
	 * Sets the exhibit of this View equal to the same Exhibit that is displayed by another specified View.
	 * This has the same effect as {@link #setExhibit(Exhibit)}, except that instead of taking the
	 * colors and transform from the default properties of the Exhibit, they are copied from the
	 * specified View.  
	 * @param view The view whose Exhibit is to be copied.  After the method is called, <em>both</em> views will
	 * be drawing the same exhibit.  If null, then the exhibit of this view will also be set to null.
	 * @param shareTransform If true, the two views will share the same Transform, so that changes made to the transform in 
	 * one will also result in a change in the other.
	 */
	public void takeExhibit(View view, boolean shareTransform) {
		Exhibit exhibit = (view == null)? null : view.getExhibit();
		if (exhibit == null)
			setExhibit(null);
		else {
			setExhibit(exhibit);
			setBackground(exhibit.getDefaultBackground());
			setForeground(exhibit.getDefaultForeground());
			if (shareTransform)
				setTransform(view.getTransform());
			else
				setTransform((Transform)view.getTransform().clone());
		}
	}
	
	/**
	 * This method is called by the display to determine what message to show in the status
	 * bar when this view is active.  This message is used when there is no animation or one-shot
	 * mouse task active and the default mouse task's getStatusText returns null.
	 * If the return value is null, then the title of the exhibit is used
	 * as the status string (or, if there is no exhibit, then the string "No Exhibit" is used (in the
	 * English version)).
	 * @return returns null to use the default string.
	 */
	public String getStatusText() {
		return null;
	}
	
	
	/**
	 * Returns the value of the showAxes property.
	 * @see #setShowAxes(boolean)
	 */
	public boolean getShowAxes() {
		return showAxes;
	}
	
	/**
	 * If set to true, an Axes decoration is added to the View.  When set to false,
	 * the decoration is removed.  The decoration is actually whatever is returned
	 * by the {@link #createAxes()}.
	 */
	public void setShowAxes(boolean show) {
		if (show != showAxes) {
			if (show) {
				Axes2D axes = createAxes();
				addDecoration(axes);
			}
			else {
				for (int i = decorations.size() - 1; i >= 0; i--)
					if (decorations.get(i) instanceof Axes2D)
						decorations.remove(i);
				forceRedraw();
			}
			if (show)
				showAxesAction.putValue(Action.NAME, I18n.tr("vmm.core.commands.HideAxes"));
			else
				showAxesAction.putValue(Action.NAME, I18n.tr("vmm.core.commands.ShowAxes"));
			showAxes = show;
		}
	}
	
	/**
	 * Create an "Axes" decoration that can be added to this view.  A subclass should override
	 * this to return an object of the appropriate class for the view.  Note that this assumes
	 * that Axes3D will be a subclass of Axes2D.
	 */
	protected Axes2D createAxes() {
		return new Axes2D();
	}
	
	/**
	 * Returns a MouseTask that can be installed in the Display attached to this View.  This method
	 * is called automatically by the Display class when a View is intalled, and it should not be
	 * necessary to call it directly.  A sub-class of View can override this to provide an appropriate
	 * MouseTask that will let the user interact with the View; the method can return null if there
	 * should be no default mouse interaction.
	 * @return The value returned in the top-level View class is a {@link BasicMouseTask2D}, which allows
	 * dragging and zooming of the 2D window.
	 * @see MouseTask
	 */
	public MouseTask getDefaultMouseTask() {
		return new BasicMouseTask2D();
	}
	
	
	/**
	 * Returns the value of the applyGraphics2DTransform property.
	 * @see #setApplyGraphics2DTransform(boolean)
	 */
	public boolean getApplyGraphics2DTransform() {
		return applyGraphics2DTransform;
	}
	
	/**
	 * If the applyGraphics2DTransform property is set to true, then a transformation will be
	 * applied to the Graphics2D objects that are used to draw this View's Exhibit.  After the
	 * transformation is applied, real-valued xy-coordinates can be used for drawing the Exhibit
	 * instead of pixel coordinates.  The default value of the property is true.  Subclasses that
	 * want to use direct pixel coordinates should set it to false.  (For cases where direct pixel
	 * coordinates are used only part of the time, see {@link Transform#getUntransformedGraphics()}.)
	 * <p>Note: When this property is true, a stroke size is also set in the graphics context used
	 * to draw the exhibit.  If the <code>preserveAspect</code> property is also true, which is the default, then the
	 * stroke size is one pixel width.  However, if preserveAspect is set to false, the stroke size
	 * is set to the smaller of the pixel width and pixel height.
	 */
	public void setApplyGraphics2DTransform(boolean applyGraphics2DTransform) {
		if (applyGraphics2DTransform != this.applyGraphics2DTransform) {
			forceRedraw();
			this.applyGraphics2DTransform = applyGraphics2DTransform;
		}
	}
	
	/**
	 * Returns the value of the preserveAspect property.
	 * @see #setPreserveAspect(boolean)
	 */
	public boolean getPreserveAspect() {
		return preserveAspect;
	}
	
	/**
	 * If the preservAspect property is set to true, then the 2D window requested for
	 * the View is adjusted so that the shape of the rectanglar window in the xy-plane
	 * has the same aspect ratio as the rectangle of pixels to which the view is being drawn.
	 * The adjusted 2D window is the smallest window that will show the entire ranges of 
	 * x and y values that were requested.  The default value of the preserveAspect property is true.
	 * @see #setWindow(double, double, double, double)
	 * @see #setApplyGraphics2DTransform(boolean)
	 * @see Transform#setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)
	 */
	public void setPreserveAspect(boolean preserveAspect) {
		if (preserveAspect != this.preserveAspect) {
			forceRedraw();
			this.preserveAspect = preserveAspect;
		}
	}
	
	/**
	 * Returns the value of the antialiased property.
	 * @see #setAntialiased(boolean)
	 */
	public boolean getAntialiased() {
		return antialiased;
	}
	
	/**
	 * If the antialiased property is true, then antialiasing is enabled in the Graphics2D
	 * objects that are used to draw this View's exhibit.  The default value is false.
	 * @param antialiased
	 */
	public void setAntialiased(boolean antialiased) {
		if (antialiased != this.antialiased) {
			forceRedraw();
			this.antialiased = antialiased;
		}
	}
	
	/**
	 * Sets the rectangular "window" in the xy-plane for this View.  This is ignored unless an
	 * Exhibit has been installed in the View.  When an Exhibit is installed, the
	 * window is set to that Exhibit's default window.  Note that it is not
	 * enforced that xmax must be greater than xmin and that ymax must be greater
	 * than ymin.  The actual range of values shown in the drawing area might be
	 * adjusted if the preserveAspect property is true, but the window specified here is guaranteed
	 * to be visible in the drawing area.
	 * @see Exhibit#getDefaultWindow()
	 * @see #setPreserveAspect(boolean)
	 * @param xmin The lower limit of x-values in the xy-plane.
	 * @param xmax The upper limit of x-values in the xy-plane.
	 * @param ymin The lower limit of y-values in the xy-plane.
	 * @param ymax The upper limit of y-values in the xy-plane.
	 */
	public void setWindow(double xmin, double xmax, double ymin, double ymax) {
		if (transform != null)
			transform.setLimits(xmin,xmax,ymin,ymax);
	}
	
	/**
	 * Sets the window in the xy-plane for this View.  The limits are set to
	 * (window[0], window[1], window[2], window[3]).
	 * @see #setWindow(double, double, double, double)
	 * @param window If non-null, must be an array containing at least four doubles.  If
	 * the value is null, this method does nothing.  A non-null value with fewer than four
	 * elements will cause an array index out of bounds exception.
	 */
	public void setWindow(double[] window) {
		if (window != null)
			setWindow(window[0],window[1],window[2],window[3]);
	}
	
	/**
	 * Returns a four-element array containing the limits of the window for this View in the
	 * xy-plane, assuming that an Exhibit has been installed in the View.  If no Exhibit
	 * has been installed, the return value is null.  The four elements of the array are
	 * the xmin, xmax, ymin, and ymax of the rectangle, in that order.  The values here
	 * might be different from those specified in <code>setWindow</code>, if the preserveAspect property is true;
	 * the actual value adjustment is made only when the Exhibit is drawn.
	 * @see #setWindow(double, double, double, double)
	 * @see #getRequestedWindow()
	 * @see #setPreserveAspect(boolean)
	 */
	public double[] getWindow() {
		if (transform == null)
			return null;
		else
			return new double[] { transform.getXmin(), transform.getXmax(), transform.getYmin(), transform.getYmax() };
	}
	
	/**
	 * Returns the transform that is used when drawing this View.  
	 * This is the transform that is applied to xy-points (or, in 3D views, to xyz-points) to convert them to
	 * pixel coordinates for drawing on the screen.  This transform is initially obtained by calling
	 * an exhibit's {@link Exhibit#getDefaultTransform(View)} method when that exhibit is installed
	 * in this View.  Note that the transform is not necessarily completely valid unless a drawing
	 * operation is in progress during a call to {@link #render(Graphics2D, int, int)}.
	 * <p>This is not guaranteed to be
	 * valid in all respects unless a drawing operation is in progress.  However, the window in the xy-plane
	 * and the 3D projection, if any, are valid.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	/**
	 * Sets the transform for this view to a specified transform, with no error checking.
	 * This is meant mainly for use in the {@link SaveAndRestore} class.
	 */
	public void setTransform(Transform transform) {
		if (transform != null)
			transform.removeChangeListener(this);
		this.transform = transform;
		if (transform != null)
			transform.addChangeListener(this);
	}
	
	/**
	 * Get the window that was originally requested in <code>setWindow</code>.  This might differ from the values returned
	 * by <code>getWindow</code> if the preserveAspect property is true.
	 * @see #setWindow(double, double, double, double)
	 * @see #getWindow()
	 */
	public double[] getRequestedWindow() {
		if (transform == null)
			return null;
		else
			return new double[] { transform.getXminRequested(), transform.getXmaxRequested(), transform.getYminRequested(), transform.getYmaxRequested() };
	}
	
	/**
	 * Gets the background color used when rendering this view.
	 * @see #setBackground(Color)
	 */
	public Color getBackground() {
		return background;
	}
	
	/**
	 * Set the background color that will be used for rendering this view.  If the specified color is null,
	 * then the default color (white) is used.  Note that the background color is automatically reset when
	 * an exhibit is installed into this View.  If the exhibit is non-null, then the background color is
	 * set using {@link Exhibit#getDefaultBackground()}; if a null exhibit is installed, the background color
	 * is set to white.  So, this method is only for adjusting the background color while an exhibit is displayed.
	 * Changing the background color will cause a redraw. 
	 * <p>In addition to setting the background color, this method will set the foreground color to either 
	 * black or white, depending on whehter the background color is light or dark.  It is possible to change
	 * the foreground color to something else after setting the background.
	 */
	public void setBackground(Color c) {
		if (c == null) {
			if (exhibit != null)
				c = exhibit.getDefaultBackground();
			else
				c = Color.white;
		}
		if (c.equals(background))
			return;
		background = c;
		if (c.getRed() < 150 && c.getGreen() < 120 && c.getBlue() < 150)
			setForeground(Color.white);
		else 
			setForeground(Color.black);
		forceRedraw();
		if (Color.black.equals(getBackground()))
			backgroundCommands.setSelectedIndex(0);
		else if (Color.white.equals(getBackground()))
			backgroundCommands.setSelectedIndex(1);
		else
			backgroundCommands.setSelectedIndex(2);
	}
	
	/**
	 * Get the foreground color used for rendering this view.
	 * @see #setForeground(Color)
	 */
	public Color getForeground() {
		return foreground;
	}
	
	/**
	 * Set the foreground color to be used for rendering this view.  If the specified color is null, then
	 * the default (black) is used.
	 * The foreground color is set automatically when a new exhibit is installed
	 * or when the background color is changed by calling {@link #setBackground(Color)}.  As a result, this
	 * method will rarely be used.
	 */
	public void setForeground(Color c) {
		if (c == null) {
			if (exhibit != null)
				c = exhibit.getDefaultBackground();
			else
				c = Color.white;
		}
		if (c.equals(foreground))
			return;
		foreground = c;
		forceRedraw();
	}
	
	/**
	 * Sets the fastDrawing property, which tells whether the Exhibt in this View should be drawn as quickly as possible.
	 * Most programmers will not need to set this property, but should test it with {@link #getFastDrawing()} when the
	 * usual rendering method takes a long time.  (Note that the "fastDrawing" property is shared by any "synchronized"
	 * views, and the actual variable that represents this property is stored in the Transform object that is shared
	 * by the synchronized views.)
	 * <p>When the value of fastDrawing is changed by this method, the view will be redrawn.  Any synchronized views will also be redrawn, 
	 * unless the redrawSynchronizedViews parameter is false.  (This method was
	 * introduced to be used during resizing of a DisplayXM in the 3DXM application, where only one view should be redrawn.)
	 * @see #setFastDrawing(boolean)
	 */
	public void setFastDrawing(boolean fast, boolean redrawSynchronizedViews) {
		if (transform != null) {
			transform.setFastDrawing(fast,redrawSynchronizedViews);
			if (!redrawSynchronizedViews)
				forceRedraw();  // redraw this view in any case.
		}
	}
	
	/**
	 * Sets the fastDrawing property, which tells whether the Exhibit should be drawn as quickly as possible.
	 * The property actually applies to any views that are "synchronized" with this view (because they share its transform).
	 * Most programmers will not need to set this property, but should test it with {@link #getFastDrawing()} when the
	 * usual rendering method takes a long time.
	 * <p>When the value of fastDrawing is changed by this method, this view and any synchronized views will be redrawn.
	 */
	public void setFastDrawing(boolean fast) {
		setFastDrawing(fast,true);
	}
	
	/**
	 * Tells whether the Exhibit should be drawn as quickly as possible.  Exhibits and Decorations
	 * can check the setting of this fastDrawing property if they ordinarily take a long time to draw.
	 * If their ordinary drawing is fast, they don't have to worry about it.  Fast drawing might be used, for example,
	 * during a mouse drag, zoom, or rotate operation.  The <code>fastDrawing</code> property is set, for example
	 * in {@link BasicMouseTask2D} and {@link vmm.core3D.BasicMouseTask3D} during mouse manipulation of the exhibit.
	 */
	public boolean getFastDrawing() {
		return (transform == null)? false : transform.getFastDrawing();
	}

	
	
	
	//----------------- XML IO -------------------------------------------------------
	
	/**
	 * This method is called when an XML representation of this view is being constructed by the 
	 * {@link SaveAndRestore} class to give the View a chance to add any extra infomation that
	 * is not saved by default.  Any Parameters  associated with the View are
	 * saved automatically, as are the transform and the applyGraphics2DTransform,
	 * antialiased, and preserveAspect properties.  Decorations associated with the View are
	 * <b>not</b> saved automatically UNLESS they are marked with the {@link vmm.core.VMMSave}
	 * annotation.  Property variables will also be saved automatically IF they are marked
	 * with {@link vmm.core.VMMSave} annotations.
	 * <p>The method in this top-level View class does nothing.
	 * <p>When a subclass overrides this method, it should start by calling
	 * super.addExtraXML(containingDocument,viewElement) to make sure that information from the superclass
	 * is saved.
	 * @param containingDocument The overall XML document that contains the view Element that is being created.
	 * This parameter is necessary because it is needed to create any nested subelements that are to be added
	 * to the view element.
	 * @param viewElement The XML element that is being constructed.  This element already exists; the
	 * purpose of this method to add any extra information that would be needed to reconstruct this view
	 * object from the XML represenation.
	 * @see #readExtraXML(Element)
	 */
	public void addExtraXML(Document containingDocument, Element viewElement) {
	}
	
	/**
	 * This method is called when this View is being reconstructed from an XML representation by the
	 * {@link SaveAndRestore} class.  The View object has already been created, and default information
	 * (parameters, colors, and transform) have been retrieved.
	 * This method is responsible
	 * for retrieving any data that was written by {@link #addExtraXML(Document, Element)},
	 * except that properties written with {@link SaveAndRestore#addProperty(Object, String, Document, Element)}
	 * are retrieved automatically, and decorations written with {@link SaveAndRestore#addDecorationElement(Document, Element, Decoration)}
	 * are retrieved automatically.
	 * The method in this top-level View class does nothing.
	 * <p>In general, when a subclass overrides this method, it should be sure to call
	 * super.readExtraXML(viewInfo).
	 * @param viewInfo The &lt;view&gt; element from the XML file that contains the information about this
	 * view.  Some methods from the {@link SaveAndRestore} class might be useful for getting the data.
	 * @throws IOException If an error is found, an exception of type IOException should be thrown.
	 * This will abort the whole processing of the XML file.
	 */
	public void readExtraXML(Element viewInfo) throws IOException {
	}
	

			
	//------------------------------- Parameter Handling --------------------------
	
	/**
	 * Returns an array containing all the parameters associated with this View.
	 * No parameters are defined by the View class itself.  Subclasses can add
	 * parameters using the <code>addParameter</code> method.  Note that in the array returned 
	 * by this method, parameters are listed in the REVERSE of the order in which
	 * they were added to the View.
	 * @return An array containing the parameters that have been added to this view; these are
	 * the actual parameters, not copies.  Changing a parameter will ordinarily cause
	 * the View to be redrawn to reflect the change.  The return value is non-null.  If
	 * there are no parameters, a zero-length array is returned.
	 */
	public Parameter[] getParameters() {
		if (parameters == null)
			return new Parameter[] {};
		else {
			Parameter[] params = new Parameter[parameters.size()];
			for (int i = params.length-1; i >= 0; i--)
				params[params.length-i-1] = parameters.get(i);
			return params;
		}
	}
	
	/**
	 * Returns an array of Parameters containing those that have been added to 
	 * the Exhibit (if any) that is displayed in this view, followed by those that
	 * have been added to this view.
	 * In addition, if the Exhibit is a {@link UserExhibit}, then the View parameters
	 * from the user exhibit are also added, between the Exhibit and the Exhibtit parameters.
	 * @see Exhibit#getParameters()
	 * @see UserExhibit.Support#getFunctionParameters()
	 */
	public Parameter[] getViewAndExhibitParameters() {
		Parameter[] exhibitParams = null; 
		Parameter[] functionParams = null;
		if (exhibit != null) {
			exhibitParams = exhibit.getParameters();
			if (exhibit instanceof UserExhibit)
				functionParams = ((UserExhibit)exhibit).getUserExhibitSupport().getFunctionParameters();
		}
		int ct = (parameters == null)? 0 : parameters.size();
		ct += (exhibitParams == null)? 0 : exhibitParams.length;
		ct += (functionParams == null)? 0 : functionParams.length;
		Parameter[] params = new Parameter[ct];
		int pos = 0;
		if (functionParams != null)
			for (int i = 0; i < functionParams.length; i++)
				params[pos++] = functionParams[i];
		if (exhibitParams != null)
			for (int i = 0; i < exhibitParams.length; i++)
				params[pos++] = exhibitParams[i];
		if (parameters != null)
			for (int i = 0; i < parameters.size(); i++)
				params[pos++] = parameters.get(i);
		return params;
	}
	
	/**
	 * Returns a paramter that has been added to this View and that has the specified name. 
	 * If name is null, or if there is no parameter with the specified name, then the
	 * return value is null.
	 */
	public Parameter getParameterByName(String name) {
		if (parameters != null && name != null) {
			for (int i = 0; i < parameters.size(); i++)
				if (name.equals(parameters.get(i).getName()))
					return parameters.get(i);
		}
		return null;
	}
	
	/**
	 * Associate a parameter with this view.  This method is protected, so it can only be
	 * called by a subclass.  This View is set to be the owner of the Parameter.  (In practice,
	 * parameters are rarely associated with views; they are much more likely to be associated
	 * with exhibits.)
	 * @param param The parameter to be added.  If the parameter is null, it is ignored.
	 * If the parameter has already been added to the View, it is not added for a second time.
	 */
	protected void addParameter(Parameter param) {
		if (param == null)
			return;
		if (parameters == null)
			parameters = new ArrayList<Parameter>();
		if (parameters.contains(param))
			return;
		parameters.add(param);
		param.setOwner(this);
	}
	
	
	/**
	 * Removes a parameter from this View.  The method is protected, so it can only be used by
	 * subclasses (and, in fact, it will only rarely be used).
	 * @param param The parameter to be removed.  If the value is null or if the parameter is
	 * not associated with this View, then nothing is done.
	 */
	protected void removeParameter(Parameter param) {
		if (param == null || parameters == null || (!parameters.contains(param)))
			return;
		parameters.remove(param);
		param.setOwner(null);
	}
	
	/**
	 * This method will be called automatically when a parameter that has been added to this View
	 * is changed.  It is not meant to be called directly.  Note that in fact, this method
	 * simply calls <code>forceRedraw</code>.
	 * @see #forceRedraw()
	 */
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		forceRedraw();
	}
	
	
	//--------------------------------------- Decorations ------------------------------------
	
	/**
	 * Add a decoration that will appear in this View only.  Of course, the
	 * decoration must be one that is compatible with the View's Exhibit.  Note
	 * that adding or removing a Decoration from a View causes a call to <code>forceRedraw</code>. 
	 * @param d The decoration to be added.  If this is null or if it has already been added to the View, 
	 * then nothing is done.  The View is added
	 * as a ChangeListener to the decoration.  This will cause the View to be automatically
	 * redrawn when the Decoration is changed.
	 */
	public void addDecoration(Decoration d) {
		if (d == null)
			return;
		if (decorations == null)
			decorations = new ArrayList<Decoration>();
		if (decorations.contains(d))
			return;
		decorations.add(d);
		d.addChangeListener(this);
		forceRedraw();
	}
	
	/**
	 * Remove a specified decoration from this View, if present.
	 */
	public void removeDecoration(Decoration d) {
		if (decorations != null && d != null && decorations.contains(d)) {
			decorations.remove(d);
			if (decorations.size() == 0)
				decorations = null;
			d.removeChangeListener(this);
			forceRedraw();
		}
	}
	
	/**
	 * Remove all decorations from this View.
	 */
	public void clearDecorations() {
		if (decorations != null) {
			for (int i = 0; i < decorations.size(); i++)
				decorations.get(i).removeChangeListener(this);
			decorations = null;
			forceRedraw();
		}
	}
	
	/**
	 * Returns a list of decorations that have been added to this View
	 * @return a non-null array, possibly of length zero, containing the decorations.
	 */
	public Decoration[] getDecorations() {
		if (decorations == null)
			return new Decoration[0];
		else {
			Decoration[] decs = new Decoration[decorations.size()];
			decorations.toArray(decs);
			return decs;
		}
	}
	
	//------------------------------------ View and Settings Commands --------------------------------------
	
	/**
	 * Returns a list of "view commands" that can be applied to this View.
	 * The commands must implement the {@link vmm.actions.ActionItem} interface and will generally
	 * belong to one of the classes {@link vmm.actions.AbstractActionVMM},
	 * {@link vmm.actions.ToggleAction}, {@link vmm.actions.ActionRadioGroup}, or
	 * {@link vmm.actions.ActionList}.  Null items represent separators.
	 * The idea is that the list items will be added to a menu or otherwise presented to the user
	 * so that the user can invoke the commands (with ActionList items representing submenus).
	 * Null values can occur in the list; they are meant to become separaters in the menu.
	 * <p>In this top-level View class, the list contains a "Show/Hide Axes" Action, a null
	 * value, and an ActionRadioGroup with three items that can be used to set the background color. 
	 * <p>In general, when overriding this method, a subclass should call super.getViewCommands()
	 * to obtain a list of commands from the superclass. It can then add additional
	 * commands or remove or disable commands that are in the list from the superclass.
	 */
	public ActionList getViewCommands() {
		ActionList commands = new ActionList();
		commands.add(showAxesAction);
		commands.add(null);
		commands.add(backgroundCommands);
		return commands;
	}
	
	/**
	 * Generates a list of "Settings commands" that can be applied to this View.
	 * The commands must implement the {@link vmm.actions.ActionItem} interface and will generally
	 * belong to one of the classes {@link vmm.actions.AbstractActionVMM},
	 * {@link vmm.actions.ToggleAction}, {@link vmm.actions.ActionRadioGroup}, or
	 * {@link vmm.actions.ActionList}.  Null items represent separators.
	 * The idea is that the list items will be added to a menu or otherwise presented to the user
	 * so that the user can invoke the commands (with ActionList items representing submenus).
	 * Null values can occur in the list; they are meant to become separaters in the menu.
	 * <p>Subclasses that override this method should generally call super.getSettingsCommands() and add their
	 * commands to the list retured by that method.  This top-level View class does not define any Settings
	 * commands; the return value is an empty non-null ArrayList.
	 */
	public ActionList getSettingsCommands() {
		return new ActionList();
	}
	
	/**
	 * Returns a list containing Settings commands for this View and for the Exhibit that is contained in this View.
	 * The Exhibit commands, if any, occur first in the list.  This is a convenience method
	 * that simply combines the results of {@link #getSettingsCommands()} and
	 * {@link Exhibit#getSettingsCommandsForView(View)}.   In addtion, if the exhibit is a {@link UserExhibit}, and
	 * if the exhbit allows the user data to be changed, then a "ChangeUserData" command is also included,
	 * between the other Exhibit commands and the View commands.  If there is currently no Exhibit in the View,
	 * only the view commands are returned.
	 * There is presumably no need to override this method.
	 * <p>Note that of the three types of commands defined by Views and Exhibits ("Settings", "Action", and
	 * "View" commands), "View" commands are constructed entirely by Views, while "Action" commands and
	 * "Settings" commands are constructed by both.
	 */
	public ActionList getSettingsCommandsForViewAndExhibit() {
		ActionList exhibitCommands = null;
		AbstractActionVMM changeUserData = null;
		if (exhibit != null) {
			exhibitCommands = exhibit.getSettingsCommandsForView(this);
			if (exhibit instanceof UserExhibit) {
				UserExhibit.Support s = ((UserExhibit)exhibit).getUserExhibitSupport();
				if (s.getAllowChangeUserDataCommand())
					changeUserData = s.makeChangeUserDataAction(this);
			}
		}
		ActionList viewCommands = getSettingsCommands();
		if (exhibitCommands == null && changeUserData == null && viewCommands == null)
			return null;
		ActionList combinedCommands = new ActionList();
		if (exhibitCommands != null)
			combinedCommands.addAll(exhibitCommands);
		if (changeUserData != null) {
			if (combinedCommands.getItemCount() > 0)
				combinedCommands.add(null);
			combinedCommands.add(changeUserData);
		}
		if (viewCommands != null && viewCommands.getItemCount() > 0) {
			if (combinedCommands.getItemCount() > 0)
				combinedCommands.add(null);
			combinedCommands.addAll(viewCommands);
		}
		return combinedCommands;
	}
	
	/**
	 * Generates a list of "Action commands" that can be applied to this View.
	 * The commands must implement the {@link vmm.actions.ActionItem} interface and will generally
	 * belong to one of the classes {@link vmm.actions.AbstractActionVMM},
	 * {@link vmm.actions.ToggleAction}, {@link vmm.actions.ActionRadioGroup}, or
	 * {@link vmm.actions.ActionList}.  Null items represent separators.
	 * The idea is that the list items will be added to a menu or otherwise presented to the user
	 * so that the user can invoke the commands (with ActionList items representing submenus).
	 * Null values can occur in the list; they are meant to become separaters in the menu.
	 * <p>Subclasses that override this method should generally call super.getActionss() and add their
	 * commands to the list retured by that method.  This top-level View class does not define any Action
	 * commands; the return value is an empty non-null ArrayList.
	 */
	public ActionList getActions() {
		return new ActionList();
	}
	
	/**
	 * Returns a list containing Action commands for this View and for the Exhibit that is contined in this View.
	 * The Exhibit commands, if any, occur first in the list.  This is a convenience method
	 * that simply combines the results of {@link #getActions()} and
	 * {@link Exhibit#getActionsForView(View)}. 
	 * If there is currently no Exhibit in the View,
	 * only the view commands are returned.
	 * There is presumably no need to override this method.
	 * <p>Note that of the three types of commands defined by Views and Exhibits ("Settings", "Action", and
	 * "View" commands), "View" commands are constructed entirely by Views, while "Action" commands and
	 * "Settings" commands are constructed by both.
	 */
	public ActionList getActionsForViewAndExhibit() {
		ActionList exhibitCommands = null;
		if (exhibit != null) {
			exhibitCommands = exhibit.getActionsForView(this);
		}
		ActionList viewCommands = getActions();
		if (exhibitCommands == null && viewCommands == null)
			return null;
		ActionList combinedCommands = new ActionList();
		if (exhibitCommands != null)
			combinedCommands.addAll(exhibitCommands);
		if (viewCommands != null && viewCommands.getItemCount() > 0) {
			if (combinedCommands.getItemCount() > 0)
				combinedCommands.add(null);
			combinedCommands.addAll(viewCommands);
		}
		return combinedCommands;
	}
	
	/**
	 * This method returns a list of {@link ActionItem} that will be added to
	 * the Animation menu of the 3dxm applicaiton.  The values in the list should
	 * ordinarily be commands (i.e. {@link vmm.actions.AbstractActionVMM})
	 * that run animations (although there is no way to enforce
	 * this restriction).  These animations commands are in addition to the standard "Morph"
	 * and "Cyclic Morph" commands.  Note that these commands are added to those returned
	 * by {@link View#getAdditionalAnimations()}.
	 * <p>In this top-level View class, the return value is a an empty list  (but not null).  
	 * In general, when overriding this method, subclasses should call "super.getAdditionalAnimations()"
	 * to obtain a list of actions from the superclass. It can then add additional
	 * actions or remove or disable actions that are in the list from the superclass.
	 */
	public ActionList getAdditionalAnimations() {
		return new ActionList();
	}
	
	/**
	 * Returns the combined contents of the lists obtained from {@link #getAdditionalAnimations()}
	 * and {@link Exhibit#getAdditionalAnimationsForView(View)}.  There should not be any 
	 * need to override this method.
	 */
	public ActionList getAdditionalAnimationsForViewAndExhibit() {
		ActionList exhibitCommands = null;
		if (exhibit != null) {
			exhibitCommands = exhibit.getAdditionalAnimationsForView(this);
		}
		ActionList viewCommands = getAdditionalAnimations();
		if (exhibitCommands == null && viewCommands == null)
			return null;
		ActionList combinedCommands = new ActionList();
		if (exhibitCommands != null)
			combinedCommands.addAll(exhibitCommands);
		if (viewCommands != null && viewCommands.getItemCount() > 0) {
			if (combinedCommands.getItemCount() > 0)
				combinedCommands.add(null);
			combinedCommands.addAll(viewCommands);
		}
		return combinedCommands;
	}

	
	//------------------------------------ drawing, etc. --------------------------------------
		
	/**
	 * This method is called by the <code>render</code> method when the Exhibit in this View
	 * needs to be redrawn.  The default as defined here is simply to call the
	 * exhibit's <code>render</code> method.  Subclasses can override this method if some
	 * other action is needed.
	 * <p>Drawing is likely to need information about the transform (including the rectangular area in the
	 * xy-plane that is being drawn and  the rectangle of pixels in the graphics
	 * context where it is drawn) and the decorations that have been added to the view.  This
	 * information is available from {@link #getTransform()} and {@link #getDecorations()}.
	 * @param g The graphics context where the exhibit is to be drawn.  The drawing
	 * area has already been cleared to the background color.
	 */
	protected void doDraw(Graphics2D g) {
		if (exhibit != null)
			exhibit.render(g,this,transform,decorations);
	}
	
	/**
	 * Force the Exhibit to be completely redrawn.  This is stronger than just repainting
	 * because it forces the off-screen image to be redrawn as well.  Note
	 * that simply calling <code>repaint</code> on the Display where this View is rendered will NOT
	 * cause the off-screen image to be redrawn and so will not necessarily cause what is
	 * displayed on the screen to change.  The system is designed so that this method
	 * or its equivalent will be called automatically in most cases, such as 
	 * when Parameters of the View or Exhibit are changed or when a Decoration is added or removed or changed.
	 * It is also called when other properties defined in this top-level View
	 * classe are changed.  Subclasses of View might need to call this method when changes are made
	 * that affect the appearance of the Exhibit.
	 */
	public void forceRedraw() {
		needsRedraw = true;
		if (buildAnimation != null)
			buildAnimation.cancel();
		if (display != null)
			display.repaint();
	}
	
	/**
	 * A View listens for state change events that are generated when the Exhibit or Decoration that it is
	 * displaying is changed, or by the transform (that converts real coords to pixel coords).  
	 * This method is automatically called by the Exhibit, Decoration, or Transform when it
	 * is changed.  It will not ordinarly be called directly.  Note that in fact,
	 * this method simply calls forceRedraw(), which causes the View to be completely resdrawn.
	 * @see #forceRedraw()
	 */
	public void stateChanged(ChangeEvent evt) {
		forceRedraw();
	}
	
	/**
	 * Used by the Exhibit class to install a build animation for an exhibit.  The current
	 * build animation, if any, is canceled.
	 * <p>This method has package scope and so is not visible outside the vmm.core package.
	 * @param builder The build animation, or null if the intent is just to cancel the current build animation.
	 */
	void installBuildAnimation(final Animation builder) {
		if (buildAnimation != null)
			buildAnimation.cancel();
		buildAnimation = builder;
		if (builder != null)
			display.installAnimation(builder);
	}
	
	/**
	 * Return the current build animation running in this View, or null if there is none.
	 */
	Animation getBuildAnimation() {
		return buildAnimation;
	}
	
	/**
	 * This method is called to draw this View's Exhibit.  This will most often
	 * be called from the <code>paintComponent</code> method of the Display class.  The process of
	 * rendering a View is rather complicated because of the need to manage off-screen images and
	 * various properties of the View.  Subclasses of
	 * View should probably not override this method.  They should change the <code>doDraw</code>
	 * method, which is called by this method, instead.
	 * <p>While the exhibit is being rendered, this method sets the value of the protected
	 * variable {@link #currentGraphics} to the graphics context in which the exhibit is being
	 * drawn.  This variable is used implicitely for drawing in the rendering methods that are provided
	 * by the View class and its 3D subclass.  This graphics context is set only for the duration of the call
	 * to the {@link #doDraw(Graphics2D)} method in this class, and hence during rendering
	 * of an exhibit (in its {@link Exhibit#doDraw(Graphics2D, View, Transform)} method.  
	 * It is also set during direct drawing to the off-screen image initiated by
	 * {@link #beginDrawToOffscreenImage()}.  At other
	 * times currentGraphics is null, and calling the rendering methods that use this graphics context at those
	 * times will result in an error.
	 * <p>Note that this render method always completely paints the rectangle in which it draws.
	 * <p>(In the rare case that creation of an offscreen image fails because of low memory,
	 * an error message is placed in the drawing area.)
	 * @param g The graphics context where the Exhibit is to be drawn.  It is assumed
	 * that the upper left corner of  the drawing area has coordinates (0,0).  The off-screen
	 * image is simply drawn in g, except that when no exhibit exists, it is filled with
	 * the background color.
	 * @param width The width, in pixels, of the drawing area where the exhibit is to be drawn. 
	 * @param height The height, in pixels, of the drawing area where the exhibit is to be drawn.
	 */
	public void render(Graphics2D g, int width, int height) { 
		if (directOffscreenDrawing)
			endDrawToOffscreenImage();
		if (width == 0 || height == 0)
			return;
		if (exhibit == null) {  // ordinarily, this won't happen since the Display class won't call this method if exhibit is null
			Color saveColor = g.getColor();
			g.setColor(background);
			g.fillRect(0,0,width,height);
			g.setColor(saveColor);
			return;
		}
		if (previousWidth != width || previousHeight != height) {  // force full redraw if size has changed
			needsRedraw = true;
			previousWidth = width;
			previousHeight = height;
		}
		if (needsNewOSI(width,height)) {  // force redraw if size of offscreen canvas is incorrect
			needsRedraw = true;
			try {
				createOSI(width,height);
			}
			catch (OutOfMemoryError e) {
				previousWidth = 0;
				Color saveColor = g.getColor();
				g.setColor(Color.WHITE);
				g.fillRect(0,0,width,height);
				g.setColor(Color.RED);
				g.drawString(I18n.tr("vmm.core.OutOfMemoryError"),20,35);
				g.setColor(saveColor);
				return;
			}
		}
		if (needsRedraw) { 
			clearOSI();
			Graphics2D OSG = prepareOSIForDrawing();
			doDraw(OSG);
			finishOSIDraw();
			currentGraphics = null;
			transform.finishDrawing();
			OSG.dispose();
			needsRedraw = false;
		}
		putOSI(g,width,height);
	}

	     
	      // The following six methods were introduced mainly to make it possible to write a render method
	      // that would also work for the View3D class.  They are meant to be overridden in subclasses.
	
	/**
	 * This is called by <code>render</code> to check whether a new offscreen image needs to be
	 * created, given the specified width and height of the drawing area.
	 */
	protected boolean needsNewOSI(int width, int height) {
		return fullOSI == null || fullOSI.getWidth() != width || fullOSI.getHeight() != height;
	}
	
	/**
	 * This is called by <code>render</code> to copy the offscreen image to the screen.
	 * The width and height of the drawing area are given as parameters, although they
	 * is not used in the version of this method in class View.
	 */
	protected void putOSI(Graphics2D g, int width, int height) {
		g.drawImage(fullOSI,0,0,null);
	}
	
	/**
	 * This method is called whenever
	 * an off-screen image needs to be created.  This method sets the value of the
	 * protected variable {@link #fullOSI} to the newly created image.  (Subclasses should
	 * respect this.)  The default is to create a 
	 * new BufferedImage whose type is specified by the protected variable
	 * {@link #offscreenImageType}.  This is most likely what is desired.
	 * However, sub-classes can override this method if a different type of image
	 * is needed.
	 */
	protected void createOSI(int width, int height) {
		fullOSI = null;
		fullOSI = new BufferedImage(width,height,offscreenImageType);
	}
	
	/**
	 * Fills the offscreen image with the background color of this View.
	 */
	protected void clearOSI() {
		Graphics2D g = (Graphics2D)fullOSI.getGraphics();
		g.setColor(background);
		g.fillRect(0,0,fullOSI.getWidth(),fullOSI.getHeight());
		g.dispose();
	}
	
	/**
	 * Prepare the offscreen image for drawing and return a graphics context for drawing to it.
	 * This method is called by {@link #render(Graphics2D, int, int)} before it begins drawing
	 * to the offscreen image.  It is also called by {@link #beginDrawToOffscreenImage()}.
	 * The offscreen image exists before this is called.  In the 
	 * top-level View class, this applies the transform and (if approprieate) antialiasing to
	 * the OSI drawing context and sets the foregraund and background colors.
	 * <p>Note: It is important that the protected variable {@link #fullOSI} point to a
	 * BufferedImage that contains the image that is shown on the screen.  Subclasses that
	 * override this method should respect this.  This is true in View3D, for example,
	 * even when the actual drawing is being done elsewhere during RED_GREEN_STEREO rendering.
	 * @return A graphics context for drawing to the off-screen image.
	 */
	protected Graphics2D prepareOSIForDrawing() {
		Graphics2D g = (Graphics2D)fullOSI.getGraphics();
		g.setColor(foreground);
		g.setBackground(background);
		if (antialiased)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		transform.setUpDrawInfo(g,0,0,fullOSI.getWidth(),fullOSI.getHeight(),preserveAspect,applyGraphics2DTransform);
		currentGraphics = g;
		return currentGraphics;
	}
	
	/**
	 * This is called when drawing to the off-screen image is finished, to give it a chance for clean-up.
	 * The method in the top-level view does nothing.
	 */
	protected void finishOSIDraw() {
	}
	
	//----------------------- Rendering methods for use during drawing of an Exhibit -----------------------
	
	/**
	 * This method will set the color in the current graphics context.  This method should only
	 * be used while drawing an exhibit or after {@link #beginDrawToOffscreenImage()}.  If called
	 * at other times, there is no effect.
	 * @param c the color to be used for subsequent drawing; if c is null, then the default foreground
	 * color of the View is restored.
	 */
	public void setColor(Color c) {
		if (currentGraphics != null)
			currentGraphics.setColor( c == null? getForeground() : c );
	}
	
	/**
	 * Returns the drawing color of the current graphics context.  This method should only be
	 * used while drawing an exhibit or after {@link #beginDrawToOffscreenImage()}.  If called
	 * at other times, the return value is null.
	 */
	public Color getColor() {
		return (currentGraphics == null)? null : currentGraphics.getColor();
	}
	
	/**
	 * Sets the width of the stroke used for drawing lines and curves in the current graphics context
	 * to have a width equal to a specified number of pixels.  This method should only be
	 * used while drawing an exhibit or after {@link #beginDrawToOffscreenImage()}. If called
	 * at other times, the call has no effect.  Note that this just sets the stroke width
	 * to be a multiple of the default stroke size, as defined by the Transform.  Calling this
	 * method with a parameter value of 1 will restore the default stoke width.
	 * @see Transform#getDefaultStrokeSize()
	 */
	public void setStrokeSizeMultiplier(int pixels) {
		if (currentGraphics != null)
			currentGraphics.setStroke(new BasicStroke(pixels * transform.getDefaultStrokeSize()));
	}
	
	/**
	 * Draws a point by turning on a single pixel.  This is done by transforming (x,y) to pixel coordinates,
	 * then using <code>drawPixelDirect</code> to set the color of the single pixel.
	 * @see #drawPixelDirect(Color, int, int)
	 * @param x The x-coordinate of the point, in window (real xy) coodinates.
	 * @param y The y-coordinate of the point, in window (real xy) coodinates.
	 */
	public void drawPixel(double x, double y) {
		tempPoint.setLocation(x,y);
		transform.windowToViewport(tempPoint);
		int xInt = (int)(tempPoint.getX() + 0.4999);
		int yInt = (int)(tempPoint.getY() + 0.4999);
		drawPixelDirect(null,xInt,yInt);
	}
	
	/**
	 * Draws a point by turning on a single pixel.  This just calls
	 * <code>drawPixel(pt.getX(),pt.getY())</code>.
	 * @param pt The non-null point, in window (real xy) coodinates.
	 */
	public void drawPixel(Point2D pt) {
		drawPixel(pt.getX(), pt.getY());
	}	
	
	/**
	 * Draws a dot of specified diameter centered at a specified point.
	 * This is done by calling the Graphics2D fill() command for an
	 * appropriate Ellipse2D.  The diameter is specified in <b>pixels</b>.
	 * Note that if the preserveAspectRatio is off for this View, then
	 * the dot will can be an oval rather than a circle.
	 */
	public void drawDot(Point2D pt, double diameter) {
		tempPoint.setLocation(pt);
		transform.windowToDrawingCoords(tempPoint);
		double h = diameter*transform.getPixelWidth();
		double w = diameter*transform.getPixelHeight();
		currentGraphics.fill(new Ellipse2D.Double(pt.getX()-h/2,pt.getY()-w/2,h,w));
	}
	
	/**
	 * Draws a list of pixels in the current drawing context, where the pixels are specified in object coordinates.
	 * This should only be called while drawing is in progress.*/
	public void drawPixels(Point2D[] points, int pointIndexStart, int pointIndexEnd) {  // points in window coordinates
		if (points == null)
			return;
		if (pointIndexStart >= points.length)
			pointIndexStart = points.length - 1;
		if (pointIndexStart < 0)
			pointIndexStart = 0;
		if (pointIndexEnd >= points.length)
			pointIndexEnd = points.length - 1;
		if (pointIndexEnd < 0)
			pointIndexEnd = 0;
		if (pointIndexEnd <= pointIndexStart)
			return;
		Color color = currentGraphics.getColor();
		for (int i = pointIndexStart; i <= pointIndexEnd; i++) { 
			if (points[i] != null) {
				tempPoint.setLocation(points[i]);
				transform.windowToViewport(tempPoint);
				drawPixelDirect(color,(int)(tempPoint.getX()+0.499), (int)(tempPoint.getY()+0.499));
			}
		}
	}
	
	/**
	 * This can be called during drawing to draw a string at a specified point, given in window (real x,y) coordinates.
	 * The font is NOT transformed, as it would be if you simply used the <code>drawString</code>
	 * method of a drawing context to which a transform has been applied.
	 * The point (x,y) is properly transformed from xy-coordinates to pixel coordinates, whether a
	 * transform has been applied ot the drawing context or not.  After conversion, if necessary, the
	 * graphics context returned by {@link Transform#getUntransformedGraphics()} is used to draw the string.
	 * @see #drawString(String, Point2D)
	 */
	public void drawString(String s, double x, double y) {
		Point2D pt = new Point2D.Double(x,y);
		transform.windowToViewport(pt);
		transform.getUntransformedGraphics().drawString(s,(float)pt.getX(),(float)pt.getY());
	}
	

	/**
	 * This can be called during drawing to draw a string at a specified point, given in window (real x,y) coordinates.
	 * The font is NOT transformed, as it would be if you simply used the <code>drawString</code>
	 * method of a drawing context to which a transform has been applied.
	 * @see #drawString(String, double, double)
	 * @param xyCoords The non-null point containting the coordinates of the basepoint of the string in real xy-coordinates.
	 * This point is not modified.
	 */
	public void drawString(String s, Point2D xyCoords) {
		drawString(s, xyCoords.getX(), xyCoords.getY());
	}
	
	/**
	 * Draws a line in the current drawing context.  This should only be called while drawing is
	 * in progress.  The line has endpoints (x1,y1) and (x2,y2), where the coordinates are
	 * in window (real xy) coordinates.  This should only be called when a drawing operation is in
	 * progress.
	 */
	public void drawLine(double x1, double y1, double x2, double y2) {
		double tx1, ty1;
		tempPoint.setLocation(x1,y1);
	    transform.windowToDrawingCoords(tempPoint);
	    tx1 = tempPoint.getX();
	    ty1 = tempPoint.getY();
	    tempPoint.setLocation(x2,y2);
		transform.windowToDrawingCoords(tempPoint);
		currentGraphics.draw(new Line2D.Double(tx1, ty1, tempPoint.getX(), tempPoint.getY()));
	}
	
	/**
	 * Draws a line in the current drawing context.  This should only be called while drawing is
	 * in progress.
	 * @param pt1 The first endpoint of the line, with coordinates given in the window (real xy) coordinates system.
	 * @param pt2 The second endpoint of the point, with coordinates given in the window (real xy) coordinates system.
	 */
	public void drawLine(Point2D pt1, Point2D pt2) {
		drawLine(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
	}
	
	/**
	 * Draws a curve in the current drawing context.  The points on the curve are given in window (real xy) coordinates.
	 * This should only be called while drawing is in progress. 
	 * @param points The curve is drawn through some or all of the points in this array.  If the array is null, nothing is done.
	 * The curve is acutually just made up of lines from one point to the next.  An element in the array can be null.
	 * In that case, one or two segments are missing from the curve -- the segments on either side of the missing point.
	 * Consecutive points are also not joined by a segment if the jump from one point to the next is too large.
	 * @param pointIndexStart The number of points in the array that should be used for the curve.  A curve is drawn
	 * though points[pointIndexStart], point[pointIndexStart+1], ..., points[pointIndexEnd].  The value of pointIndexStart
	 * is clamped to lie in the range 0 to points.length-1.
	 * @param pointIndexEnd The number of points in the array that should be used for the curve.  A curve is drawn
	 * though points[pointIndexStart], point[pointIndexStart+1], ..., points[pointIndexEnd].  The value of pointIndexEnd
	 * is clamped to lie in the range 0 to points.length-1.  If pointIndexEnd is less than or equal to pointIndexStart,
	 * nothing is drawn.
	 */
	public void drawCurve(Point2D[] points, int pointIndexStart, int pointIndexEnd) {  // points in window coordinates
		if (points == null)
			return;
		if (pointIndexStart >= points.length)
			pointIndexStart = points.length - 1;
		if (pointIndexStart < 0)
			pointIndexStart = 0;
		if (pointIndexEnd >= points.length)
			pointIndexEnd = points.length - 1;
		if (pointIndexEnd < 0)
			pointIndexEnd = 0;
		if (pointIndexEnd <= pointIndexStart)
			return;
		GeneralPath curve = new GeneralPath();
		double maxJumpX = Math.abs(transform.getXmax() - transform.getXmin())/4;
		double maxJumpY = Math.abs(transform.getYmax() - transform.getYmin())/4;
		if (applyGraphics2DTransform)
			maxJumpX = maxJumpY = Math.max(maxJumpX,maxJumpY);
		boolean moved = false;
		for (int i = pointIndexStart; i < pointIndexEnd; i++) { 
			if (points[i] != null) {
				tempPoint.setLocation(points[i]);
				transform.windowToDrawingCoords(tempPoint);
				if (i == pointIndexStart) 
				     curve.moveTo((float)tempPoint.getX(), (float)tempPoint.getY());
				else if (i > pointIndexStart && points[i-1] != null && Math.abs(points[i].getX() - points[i-1].getX()) <= maxJumpX 
						&&  Math.abs(points[i].getY() - points[i-1].getY()) <= maxJumpY)
				     curve.lineTo((float)tempPoint.getX(), (float)tempPoint.getY());
				else {
					curve.moveTo((float)tempPoint.getX(), (float)tempPoint.getY());
					moved = true;
				}
			}
		}
		if (points[pointIndexEnd] != null) {
			if ( (pointIndexStart == 0) && (pointIndexEnd == points.length-1) 
					&& (points[0] != null) && (Math.abs(points[0].getX() - points[pointIndexEnd].getX()) <= transform.getPixelWidth()/100 )  
					&&  (Math.abs(points[0].getY() - points[pointIndexEnd].getY()) <= transform.getPixelHeight()/100 ) 
					&& (!moved) ) { 
				curve.closePath(); // System.out.println("Path was closed");  // Do NOT close if moved = true - leads to errors.
			}  
			else {
				// replaced from here
				tempPoint.setLocation(points[pointIndexEnd]);
				transform.windowToDrawingCoords(tempPoint);
				if (points[pointIndexEnd-1] != null && Math.abs(points[pointIndexEnd].getX() - points[pointIndexEnd-1].getX()) <= maxJumpX 
						&&  Math.abs(points[pointIndexEnd].getY() - points[pointIndexEnd-1].getY()) <= maxJumpY)
					curve.lineTo((float)tempPoint.getX(), (float)tempPoint.getY());
				else
					curve.moveTo((float)tempPoint.getX(), (float)tempPoint.getY());
			}
		} 
		currentGraphics.draw(curve);
	}
	
	/**
	 * Draws a curve through all the points in an array by calling <code>drawCurve(points,points.length)</code>
	 * @param points The array of points on the curve.  If this is null, nothing is done.
	 */
	public void drawCurve(Point2D[] points) {
		if (points != null)
			drawCurve(points, points.length);
	}
	
	/**
	 * Draws a curve in the current drawing context.  The points on the curve are given in window (real xy) coordinates.
	 * This should only be called while drawing is in progress. 
	 * @param points The curve is drawn through some of all of the points in this array.  If the array is null, nothing is done.
	 * The curve is acutually just made up of lines from one point to the next.  An element in the array can be null.
	 * In that case, one or two segments are missing from the curve -- the segments on either side of the missing point.
	 * Consecutive points are also not joined by a segment if the jump from one point to the next is too large.
	 * @param pointCount The number of points in the array that should be used for the curve.  A curve is drawn
	 * though points[0], point[1], ..., points[pointCount-1].  If pointCount is greater than points.length, then
	 * its value is changed to points.length.
	 */
	public void drawCurve(Point2D[] points, int pointCount) {  // points in window coordinates
		drawCurve(points,0,pointCount-1);
	}
	
	//--------------------------- Some pixel-oriented drawing methods -----------------------
	
	/**
	 * Sets the pixel with pixel coordinates (x,y) to be a specified color.
	 * The pixel color is changed in the off-screen image, not on the screen immmediately.
	 * The current transformation is <b>not</b> applied to the coordinates.
	 * @param color the color for the pixel; if null, the current drawing color is used.
	 * @param x the horizontal pixel coordinate.
	 * @param y the vertical pixel coordinate.
	 */
	public void drawPixelDirect(Color color, int x, int y) {
		if (x < 0 || y < 0 || x >= fullOSI.getWidth() || y >= fullOSI.getHeight())
			return;
		if (color == null)
			color = currentGraphics.getColor();
		int rgb;
		rgb = (color.getRed() << 16) | (color.getGreen() << 8) | (color.getBlue());
		fullOSI.setRGB(x,y,rgb);
	}
		
	/**
	 * Draws a line in the current color between points that are specified using
	 * pixel coordinates.
	 */
	public void drawLineDirect(int x1, int y1, int x2, int y2) {
		Graphics g = getTransform().getUntransformedGraphics();
		g.drawLine(x1,y1,x2,y2);
	}
	
	/**
	 * Draws a filled-in rectangle in the current color, where the rectangle is specified in pixel coordinates.
	 */
	public void fillRectDirect(int x, int y, int width, int height) {
		Graphics g = getTransform().getUntransformedGraphics();
		g.fillRect(x,y,width,height);
	}
	
	// ---------------------------- Some public methods generally for system use -------------------------------
	
	/**
	 * This method, along with {@link #endDrawToOffscreenImage()}, allow you to draw directly to this view's off-screen
	 * image, if there is one.  This operation is allowed only if (1) An offscreen image exists, (2) the View has
	 * already been drawn to the screen at least once since its current exhibit was installed, and (3) no drawing
	 * operation is in progress (including a regular draw by the <code>render</code> method or a draw operation 
	 * initiated by a previous call to this method that was not matched by a call to <code>endDrawToOffscreenImage</code>).
	 * If all these conditions hold, this method initiates a drawing operation and returns true.  If any of them
	 * fail, it returns false and no drawing operation is initiated.  To terminate the drawing operation, you must
	 * call <code>endDrawToOffscreenImage</code>.  A drawing operation should not remain open for any length of time.
	 * It should complete before the method in which <code>beginDrawToOffscreenImage</code> is called.
	 * <p>During a drawing operation initiated by this method, drawing done using the draw methods in this
	 * class, such as {@link #drawCurve(Point2D[])}, will draw to the off-screen image.  This also applies to 
	 * drawing operations in the View3D subclass and presumably in other subclasses.  The point of this is
	 * to allow you to draw extra stuff incrementally on the off-screen canvas, without redrawing the whole
	 * thing from scratch each time. 
	 * <p>After calling <code>endDrawToOffscreenImage</code>, you will probably want to call the repaint() method of
	 * this View's display to cause the changes to appear on the screen.  Do NOT call {@link #forceRedraw()},
	 * since that will generate a call to <code>render</code>, which will erase the changes you just made!
	 * @return Returns true if the drawing operation was actually begun.  If the return value is false,
	 * attemps to draw will not draw to the off-screen image and will probably cause errors.
	 */
	public boolean beginDrawToOffscreenImage() {
		if (transform == null || transform.getWidth() == 0 || currentGraphics != null || fullOSI == null)
			return false;
		prepareOSIForDrawing();
		directOffscreenDrawing = true;
		return true;
	}
	
	/**
	 * This method, along with {@link #beginDrawToOffscreenImage()}, allow you to draw directly to this view's off-screen
	 * image, if there is one.   See that method for more information.  No error occurs if this is called
	 * when no draw-to-image operation is in progress.
	 */
	public void endDrawToOffscreenImage() {
		if (!directOffscreenDrawing)
			return;
		transform.finishDrawing();
		finishOSIDraw();
		directOffscreenDrawing = false;
		currentGraphics = null;
	}
	
	/**
	 * Get a buffered image that contains the picture shown on this View's display.  
	 * If the parameter alwaysCopy is false, then the View's off-screen image is returned (provided one
	 * has already been created).
	 * Otherwise, a new image is created and the
	 * View is rendered to this image by calling its render method.  
	 * The return value can be null if this method is called when there is 
	 * no Display associated with this view, or if there is no Exhibit associated with this View,
	 * or if this method is called before a picture has been
	 * drawn to the screen.  It is possible that an OutOfMemoryError might occur when
	 * an attempt is made to create an image.
	 * <p>Note that if you need to draw to the offscreen image, you should use the facility provided
	 * by {@link #beginDrawToOffscreenImage()}.  You should not generally simply draw to the image returned by
	 * this method (even though that will work in some cases).  This method was introduced mainly to
	 * make it possible to save the screen image to a file or to a "filmstrip" animation.
	 * @param alwaysCopy If this is false, it is possible that the image that is returned is the
	 * actual image that the View uses to store its content; this image will be modified whenever 
	 * the view is redrawn.  (alwaysCopy = true is used to get the frame for a filmstrip;
	 * alwaysCopy = false is used to get an image to save to a file)
	 */
	public BufferedImage getImage(boolean alwaysCopy) {
		if (display == null || exhibit == null || previousWidth == 0)
			return null;
		if (fullOSI != null && !alwaysCopy)
			return fullOSI;
		BufferedImage image;
		Graphics2D g;
		image = new BufferedImage(previousWidth,previousHeight,offscreenImageType);
		g = (Graphics2D)image.getGraphics();
		if (fullOSI == null)
			render(g,previousWidth,previousHeight);
		else
			g.drawImage(fullOSI,0,0,null);
		g.dispose();
		return image;
	}
	
}