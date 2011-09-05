/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The transform that should be applied when an Exhibit is drawn.  This class represents
 * a 2D "Window-to-Viewport" transformation.  (A  3D transform subclass will also include
 * the projection transformation from 3D to 2D.)  For example, an object of this type is used as
 * a parmeter to {@link vmm.core.Exhibit#doDraw(Graphics2D, View, Transform)}.
 * <p>An object of this class represents a rectangular "window" in the xy-plane
 * and possibly a corresponding rectagular "viewport" of pixels.  (The viewport is only
 * valid during actual drawing operations, where the destination of the drawing is known;
 * however, the transform is only likely to be used when this is true.)  The window-to-viewport
 * transformation is the one that is applied to xy-coordinates to transform them to pixel
 * coordinates -- this transformation can be applied either automatically (when the
 * <code>apply2DTransform</code> parameter to the {@link #setUpDrawInfo} method is set to true),
 * or it can be performed by hand using information contained in the Transform object.
 * <p>The rectangle in the xy-plane is represented by four numbers xmin, xmax,
 * ymin, and ymax.  The pixel area is represented by the coordinates of its upper left
 * corner (x and y) and by its width and height.  The values of x, y, width, and height
 * can be set only by calling the <code>setUpDrawInfo</code> method.  Otherwise, they are 0.
 * Note that this class does NOT enforce xmax greater than xmin or ymax greater than ymin.
 * It does enforce that width and height are greater than 0.  Note that the xmin, xmax, ymin, ymax
 * might be adjusted from the values requested when <code>setUpDrawInfo</code> is called with
 * <code>preserveAspect</code> set to true.
 * <p>A Transform emits a ChangeEvent when the requested window (xmin,xmax,ymin,ymax) changes;
 * it does not emit a ChangeEvent when the viewport (pixel rectangle to which the window is mapped)
 * changes.  A Transform3D also emits a ChangeEvent when the 3D view changes. 
 */
public class Transform implements Cloneable {
	
	/**
	 * Used in the conversion of "nominal graphics scale" in the constructor {@link #Transform(double)}
	 * into a range of x and y values.
	 */
	private static double NORMAL_SIZE = 600;

	private double xmin_requested, xmax_requested, ymin_requested, ymax_requested; // Values from setLimits()
	private double xmin,xmax,ymin,ymax; // Actual values used, which might be different becasue of preserving aspect ratio.
	private int x,y,width,height; // The "viewport" in terms of pixels in the drawing area.
	                              // In the VMM core, x and y are always 0.
	
	private ArrayList<ChangeListener> changeListeners;
	private ChangeEvent changeEvent;
	
	private boolean fastDrawing;
	

	/**
	 * Create a Transform object with a default window with range -5 to 5 in both the horizontal and vertical
	 * direction in the xy-plane.
	 */
	public Transform() {
		this(-5,5,-5,5);
	}
	
	/**
	 * Create a Transform object representing a specified window in the xy-plane. The values specified here might be
	 * modified later in order to preserve aspect ratio.
	 * @param xmin The left limit of the horizontal range of the window.
	 * @param xmax The right limit of the horizontal range of the window.  It is not enforced that xmax is greater
	 * than xmin.  (In fact, xmax less than xmin should probalby be OK, while xmax equal to xmin will probably not work.)
	 * @param ymin The bottom limit of the vertical range of the window.
	 * @param ymax The top limit of the vertical range of the window.  It is not enforced that ymax is greater
	 * than ymin.  (In fact, ymax less than ymin should probalby be OK, while ymax equal to ymin will probably not work.)
	 */
	public Transform(double xmin, double xmax, double ymin, double ymax) {
		setLimits(xmin,xmax,ymin,ymax);
	}
	
	/**
	 * Sets the limits on the x- and y-axis so that the number of pixels per unit along the x- and y-axis is
	 * given by the parameter <code>nominalGraphicScale</code>, assuming that the viewport has a "normal size".
	 * @param nominalGraphicScale number of pixels per unit along the x- and y-axes, assuming that the size of
	 * the window is "normal".  The normal size is given by a private constant <code>NORMAL_SIZE</code>, which
	 * is set to 600 at the time this comment was written.
	 */
	public Transform(double nominalGraphicScale) {
		double size = 0.5 * NORMAL_SIZE / nominalGraphicScale;
		setLimits(-size,size,-size,size);
	}
	
	/**
	 * Constructs a Transform by copying the requested xmin, xmax, ymin, and ymax from a specified transform.
	 * @param tr the non-null transoform to be copied.
	 */
	public Transform(Transform tr) {
		this(tr.xmin_requested, tr.xmax_requested, tr.ymin_requested, tr.ymax_requested);
	}
	
	/**
	 * The fastDrawing property is set to true at times when an exhibit should be drawn as quickly as possilble, such as when
	 * a drag, zoom, or scale is in effect.  Exhibits and Decorations can check its value by calling the 
	 * {@link View#getFastDrawing()} method in the View class.  This method is
	 * package-private; the value of the fastDrawing property is actually managed by the View class.  (It seems strange
	 * that this variable should be here, rather than in the View class, but fast
	 * drawing has to be shared by all "synchronized" views, and the only thing those view objects actually share
	 * is their transform.)
	 * @param fast The new value of the fastDrawing property.
	 * @param fireChangeEvent If true, then a ChangeEvent is sent to any ChangeListeners registered with this Transform (but only
	 * if the value of fastDrawing is actually being changed).  The ChangeListeners are, presumably, just the Views that are
	 * sharing this Transform, so the effect of sending the change event will be to force those views to be redrawn.
	 */
	void setFastDrawing(boolean fast, boolean fireChangeEvent) {
		if (fast != fastDrawing) {
			fastDrawing = fast;
			if (fireChangeEvent)
				fireTransformChangeEvent();
		}
	}
	
	/**
	 * Get the value of the fastDrawing property.
	 * @see #setFastDrawing(boolean, boolean)
	 */
	boolean getFastDrawing() {
		return fastDrawing;
	}

	/**
	 * Set the window  in the xy-plane for this Transform.   The values specified here might be
	 * modified later in order to preserve aspect ratio.  (This method also sets the x, y, width, and height
	 * properties of the viewport to zero, but the values of these properties are generally not used until a drawing
	 * operation is initiated by calling <code>setUpDrawInfo</code>.)
	 * @see #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean).
	 * @param xmin The left limit of the horizontal range of the window.
	 * @param xmax The right limit of the horizontal range of the window. It is not enforced that xmax is greater
	 * than xmin.  (In fact, xmax less than xmin should probalby be OK, while xmax equal to xmin will probably not work.)
	 * @param ymin The bottom limit of the vertical range of the window.
	 * @param ymax The top limit of the vertical range of the window.  It is not enforced that ymax is greater
	 * than ymin.  (In fact, ymax less than ymin should probalby be OK, while ymax equal to ymin will probably not work.)
	 */
	public void setLimits(double xmin, double xmax, double ymin, double ymax) {
		if (xmin == this.xmin && ymin == this.ymin &&
				xmax == this.xmax && ymax == this.ymax)
			return;
		this.xmin = xmin_requested = xmin;
		this.xmax = xmax_requested = xmax;
		this.ymin = ymin_requested = ymin;
		this.ymax = ymax_requested = ymax;
		x = y = width = height = 0;
		fireTransformChangeEvent();
	}
	
	/**
	 * Resets the x- and y-limits to specified values; for very limited use in subclasses.  This method does nothing
	 * except store the specified limits in the corresponding member variables.  It could be used to reset
	 * the limits during drawing, but only in a View that has the preserveAspect and applyTransform2D properties
	 * turned off.
	 * (See the nested ProjectedOrbitView class in {@link vmm.ode.ODE_2D} for an example.)
	 * @param xmin
	 * @param xmax
	 * @param ymin
	 * @param ymax
	 */
	protected void resetLimits(double xmin, double xmax, double ymin, double ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}
	
	/**
	 * Returns the xmax value specified in the constructor or in the <code>setLimits</code> method.  This
	 * can differ from the value returned by getXmax(), which can return a value that
	 * has been modified in order to preserve the aspect ratio.
	 */
	public double getXmaxRequested() {
		return xmax_requested;
	}
	
	/**
	 * Returns the xmin value specified in the constructor or inthe <code>setLimits</code> method.  This
	 * can differ from the value returned by <code>getXmin</code>, which can return a value that
	 * has been modified in order to preserve the aspect ratio.
	 */
	public double getXminRequested() {
		return xmin_requested;
	}
	
	/**
	 * Returns the ymax value specified in the constructor or in the <code>setLimits</code> method.  This
	 * can differ from the value returned by <code>getYmax</code>, which can return a value that
	 * has been modified in order to preserve the aspect ratio.
	 */
	public double getYmaxRequested() {
		return ymax_requested;
	}
	
	/**
	 * Returns the ymin value specified in the constructor or in the <code>setLimits</code> method.  This
	 * can differ from the value returned by <code>getYmin</code>, which can return a value that
	 * has been modified in order to preserve the aspect ratio.
	 */
	public double getYminRequested() {
		return ymin_requested;
	}
	
	/**
	 * Get the xmax value.  Note that this might be different from the value
	 * specified in the constructor or in <code>setLimits</code>, if the values have been
	 * modified to preserve the aspect ratio in <code>setUpDrawInfo</code>.
	 * @see #getXmaxRequested()
	 */
	public double getXmax() {
		return xmax;
	}
	
	/**
	 * Get the xmin value.  Note that this might be different from the value
	 * specified in the constructor or in <code>setLimits</code>, if the values have been
	 * modified to preserve the aspect ratio in <code>setUpDrawInfo</code>.
	 * Before a drawing operation is initiated, this returns the same value as the
	 * <code>getXminRequested</code> method.
	 * @see #getXminRequested()
	 */
	public double getXmin() {
		return xmin;
	}
	
	/**
	 * Get the ymax value.  Note that this might be different from the value
	 * specified in the constructor or in <code>setLimits</code>, if the values have been
	 * modified to preserve the aspect ratio in <code>setUpDrawInfo</code>.
	 * @see #getYmaxRequested()
	 */
	public double getYmax() {
		return ymax;
	}
	
	/**
	 * Get the ymin value.  Note that this might be different from the value
	 * specified in the constructor or in <code>setLimits</code>, if the values have been
	 * modified to preserve the aspect ratio in <code>setUpDrawInfo</code>.
	 * @see #getYminRequested()
	 */
	public double getYmin() {
		return ymin;
	}
	
	/**
	 * Get the height, in pixels of the drawing area.  This is guaranteed to be 
	 * valid only during drawing of an Exhibit (after it has been set by
	 * <code>setUpDrawInfo</code>).
	 * @see #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the width, in pixels of the drawing area.  This is guaranteed to be 
	 * valid only during drawing of an Exhibit (after it has been set by
	 * <code>setUpDrawInfo</code>).
	 * @see #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get the x-coordinate, measured in pixels, of the upper left corner of the drawing area.  This is guaranteed to be 
	 * valid only during drawing of an Exhibit (after it has been set by
	 * <code>setUpDrawInfo</code>).  In the VMM core, this value is always 0.
	 * @see #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y-coordinate, measured in pixels, of the upper left corner of the drawing area.  This is guaranteed to be 
	 * valid only during drawing of an Exihbit (after it has been set by
	 * <code>setUpDrawInfo</code>).  In the VMM core, this value is always 0.
	 * @see #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * This object equals obj if obj is a non-null object of type Transform with the
	 * same window and viewport limits as this object.
	 */
	public boolean equals(Object obj) {
		if (obj == null || !Transform.class.equals(obj.getClass()))
			return false;
		return hasSameViewTransform((Transform)obj);
	}
	
	public boolean hasSameViewTransform(Transform tr) {
		if (tr == null)
			return false;
		return (xmin == tr.xmin && xmax == tr.xmax && ymin == tr.ymin && ymax == tr.ymax
				  && x == tr.x && y == tr.y && width == tr.width && height == tr.height);
	}
	
	/**
	 * Returns a copy of this Transform.  (Note that the list of ChangeListeners that are registered
	 * with the transform is NOT copied.)
	 */
	public Object clone(){
		Transform o = null;
		try {
			o = (Transform)super.clone();
			o.changeListeners = null;  // Cloned version has no listeners.
			o.changeEvent = null;
		} 
		catch (CloneNotSupportedException e) { // won't happen.
		}
		return o;
	}
	
	//--------------------------------------------------------------------------
	
	private double pixelWidth, pixelHeight;  // Valid only after setUpDrawInfo has been called,
                                             // i.e, during drawing (or just after drawing before anything changes)
	private boolean appliedTransform2D;  // Tells whether a 2D transform was applied to the Graphics2D object;
									   // valid only after setUpDrawInfo() has been called.
	private float strokeWidth;
	private AffineTransform transform;

	/**
	 * The graphics context that this transorm has been set up for drawing to.  This was forced to be
	 * protected to support stereo drawing, which requires that graphics contexts be swapped in and out
	 * as drawing switches between left and right eye view.
	 */
	protected Graphics2D g;

	/**
	 * The untransformed graphics context (see {@link #getUntransformedGraphics()}).  This was forced to be
	 * protected to support stereo drawing, which requires that graphics contexts be swapped in and out
	 * as drawing switches between left and right eye view.
	 */
	protected Graphics2D untransformedGraphics;
  	

	/**
	 * Sets up this Transform for drawing in a given graphics context.  When this method is called, it
	 * sets up the values that are returned by other methods in this class, such as <code>getPixelWidth</code>.
	 * These values can be used by the Exhibit during drawing, but they do not return useful values at other times.
	 * This is called automatically by a {@link vmm.core.View} before it draws its {@link vmm.core.Exhibit}, 
	 * and ordinary programmers will generally not have to call it directly.
	 * Note that width and height must be positive.  If they are not, then nothing is done by this method.
	 * <p>If the <code>applyTransform2D</code> parameter is true, then in addition to applying
	 * the transform, the size of the graphic context's stroke is adjusted.
	 * The stroke width is multiplied by the pixel size (or, if <code>preserverAspect</code> is false, by
	 * the minimum of the pixel width and the pixel height).  
	 * The result should be that -- provided <code>preserveAspect</code> is true -- the
	 * transformation will not affect the apparent width of the stroke.
	 * @param g If <code>applyTransform</code> is true (and if g is non-null), then a 2D 
	 * window-to-viewport transform is set up in this graphics context.  If <code>applyTransform</code> is false,
	 * this parameter is ignored.  (See {@link View#setApplyGraphics2DTransform(boolean)} and
	 * {@link View#setPreserveAspect(boolean)}.)
	 * @param x The left edge of the viewport (the rectangular drawing area, in pixel coords).
	 * At least in the VMM core, this is always zero.
	 * @param y The top edge of the viewport (the rectangular drawing area, in pixel coords).
	 * At least in the VMM core, this is always zero.
	 * @param width The width of the viewport (the rectangular drawing area, in pixel coords).
	 * @param height The height of the viewport (the rectangular drawing area, in pixel coords).
	 * @param preserveAspect If this is true, then the values of xmin, xmax, ymin, ymax
	 * will be modified, if necessary, so that the aspect ratio of the "window" in the
	 * xy-plan matches the aspect ratio of the "viewport" in the display area.  (See
	 * {@link #getXmin()} and {@link #getXminRequested()}.)
	 * @param applyTransform2D If this is true, then the window-to-viewport transformtion will
	 * be set up in the Graphics2D drawing context. This will allow drawing to be done using
	 * world (real x,y) coordinates directly, rather than using viewport (pixel) coordinates.  
	 */
	public void setUpDrawInfo(Graphics2D g, int x, int y, int width, int height, 
			boolean preserveAspect, boolean applyTransform2D) {
		this.g = g;
		untransformedGraphics = null;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.appliedTransform2D = applyTransform2D;
		transform = null;
		pixelWidth = pixelHeight = strokeWidth = 1;
		if (width <= 0 || height <= 0) {
			this.width = this.height = 0;
			return;
		}
		pixelWidth = pixelHeight = strokeWidth = 1;
		if (g != null) {
			Stroke stroke = g.getStroke();
			if (stroke instanceof BasicStroke)
				strokeWidth = ((BasicStroke)stroke).getLineWidth();
		}
		if (preserveAspect) {
			double viewAspect = (double)width / height;
			double aspect = Math.abs((xmax_requested - xmin_requested) / (ymax_requested - ymin_requested));
			if (viewAspect < aspect) {
				double ycenter = (ymin_requested + ymax_requested) / 2;
				double newheight = (ymax_requested - ymin_requested) * (aspect / viewAspect);
				ymin = ycenter - newheight / 2;
				ymax = ycenter + newheight / 2;
				xmin = xmin_requested;
				xmax = xmax_requested;
			}
			else {
				double xcenter = (xmin_requested + xmax_requested)/2;
				double newwidth = (xmax_requested - xmin_requested) * (viewAspect / aspect);
				xmin = xcenter - newwidth / 2;
				xmax = xcenter + newwidth / 2;
				ymin = ymin_requested;
				ymax = ymax_requested;
			}
		}
		else {
			xmin = xmin_requested;
			xmax = xmax_requested;
			ymin = ymin_requested;
			ymax = ymax_requested;
		}
		if (applyTransform2D && g != null) {
			untransformedGraphics = (Graphics2D)g.create();
			pixelWidth = Math.abs(xmax - xmin) / width;
			pixelHeight = Math.abs(ymax - ymin) / height;
			double vScale = height / (ymax - ymin);
			double hScale = width / (xmax - xmin);
			transform = new AffineTransform();
			transform.translate(width/2,height/2);
			transform.scale(hScale,-vScale);
			transform.translate(-(xmin + xmax)/2, -(ymin + ymax)/2);
			g.transform(transform);
			strokeWidth *= (float)(Math.min(pixelWidth,pixelHeight));
			g.setStroke(new BasicStroke(strokeWidth));
		}
	}
	
	/**
	 * This is called by View, and is not meant to be called directly.  It is called after
	 * a drawing action initiated by <code>setUpDrawInfo</code> is complete, to give the Transform
	 * object a chance to do any necessary post-drawing cleanup.
	 * @see #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)
	 */
	public void finishDrawing() {
		g = null;
		if (untransformedGraphics != null)
			untransformedGraphics.dispose();
		untransformedGraphics = null;
	}
	
	/**
	 * Returns the regular graphics context used during the current drawing operation, or returns null if
	 * no drawing operation is in progress.  This is the drawing context passed as the first parameter to
	 * {@link #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)}.  That context is saved during
	 * a drawing operation and is reset to null when {@link #finishDrawing()} is called.
	 * @see #getUntransformedGraphics()
	 * @return The regular graphics context for this Transform.  The value is null if no drawing operation is in progress.
	 */
	public Graphics2D getGraphics() {
		return g;
	}

	/**
	 * This can be called during drawing to get a graphics context to which no transform was applied.
	 * If the <code>applyTransform2D<code> parameter in <code>setUpDrawInfo</code>
	 *  was false, the return value is just a pointer to the same
	 * drawing context that is being used for drawing.  Otherwise, it is a graphics context that
	 * draws to the same location but to which the 2D transform has not been applied.  (However, the
	 * color and font of the transformed graphics context have been copied to the untransformed graphics context.)
	 * @see #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)
	 * @see #getGraphics()
	 */
	public Graphics2D getUntransformedGraphics() {
		if (untransformedGraphics == null)
			return g;
		else {
			untransformedGraphics.setColor(g.getColor());
			untransformedGraphics.setFont(g.getFont());
			return untransformedGraphics;
		}
	}
	
  	
	/**
	 * This can be called during drawing to convert a point given in terms of pixels to 
	 * xy-coordinates.  This is also valid after drawing, applying the same transform that
	 * was used in the recent drawing operation.
	 * @param pt  A non-null point containing coords given in pixel coordinates
	 * that will be replaced by the corresponding coords in the xy-plane.
	 */
	public void viewportToWindow(Point2D pt) {
		if (width == 0)
			return;
		double px = xmin + ((pt.getX() - x) / width) * (xmax - xmin);
		double py = ymax - ((pt.getY() - y) / height) * (ymax - ymin);
		pt.setLocation(px,py);
	}
	
	/**
	 * This can be called during drawing to convert a point given in terms of xy-coordinates
	 * to the corresponding pixel coordinates.   This is also valid after drawing, applying the same transform that
	 * was used in the recent drawing operation.  The point returned by this method can be used for drawing
	 * in the untransformed graphics context that is returned by {@link #getUntransformedGraphics()}.
	 * If {@link #appliedTransform2D()} returns false, then the point can also be used for drawing in the
	 * normal graphics context of the transform, since that graphics context is itself untransformed.
	 * @param pt  A non-null point containing coords in the xy-plane that will be replaced by the corresponding pixel
	 * coordinates.
	 */
	public void windowToViewport(Point2D pt) {
		if (width == 0)
			return;
		double px = x + ((pt.getX() - xmin) / (xmax - xmin)) * width;
		double py = y + ((ymax - pt.getY()) / (ymax - ymin)) * height;
		pt.setLocation(px,py);
	}
	
	/**
	 * Transforms a point that is in window xy-coordinates to a point that can be used in this
	 * transform's regular drawing context.  If {@link #appliedTransform2D()}  is true, then 
	 * this method has no effect, since the transform is applied automatically in the drawing context and
	 * so the window coordinates should be used directly for drawing.
	 * However, if <code>appliedTransform2D()</code> is false, then the transform is not being applied automatically 
	 * in the drawing context, and this method will
	 * apply the transformation to the point, so that it becomes appropriate for drawing.
	 * (In fact, this method has the same effect as calling {@link #windowToViewport(Point2D)} in the case where 
	 * <code>appliedTransform2D</code> is false, and does nothing in the true case.)
	 * @param pt  A non-null point whose coordinates are modified, if necessary, so they are
	 * appropriate for drawing in this transform's normal drawing context.
	 */
	public void windowToDrawingCoords(Point2D pt) {
		if (!appliedTransform2D && width != 0) {
			double px = x + ((pt.getX() - xmin) / (xmax - xmin)) * width;
			double py = y + ((ymax - pt.getY()) / (ymax - ymin)) * height;
			pt.setLocation(px,py);
		}
	}
	
	
	/**
	 * This can be called during drawing to determine the actual width
	 * of a pixel in the drawing area.
	 * If the <code>applyTransform2D</code> parameter in <code>setUpDrawInfo</code> was false, the value
	 * that is returned will be 1.  If that parameter was true, the return value
	 * will be the height of a pixel in the transformed graphics system.
	 * @see View#setApplyGraphics2DTransform(boolean)
	 * @see #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)
	 */
	public double getPixelWidth() {
		if (width == 0)
			return 1;
		return pixelWidth;
	}
	
	/**
	 * This can be called during drawing to determine the actual height
	 * of a pixel in the drawing area.
	 * If the <code>applyTransform2D</code> parameter in <code>setUpDrawInfo</code> was false, the value
	 * that is returned will be 1.  If that parameter was true, the return value
	 * will be the height of a pixel in the transformed graphics system.
	 * @see View#setApplyGraphics2DTransform(boolean)
	 * @see #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)
	 */
	public double getPixelHeight() {
		if (height == 0)
			return 1;
		return pixelHeight;
	}
	
	/**
	 * This can be called during drawing to determine the width of the default stroke
	 * in the graphics context.  You can make thicker stokes by using 
	 * multiples of the value returned by this method.
	 * @see View#setStrokeSizeMultiplier(int)
	 */
	public float getDefaultStrokeSize() {
		if (width == 0)
			return 1;
		return strokeWidth;
	}
	
	/**
	 * This can be called during drawing to determine whether a 2D transform
	 * was already applied to the Graphics2D drawing context.  This returns the 
	 * value of the <code>applyTransform2D</code> parameter when <code>setUpDrawInfo</code> was called. 
	 * @see #setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)
	 */
	public boolean appliedTransform2D() {
		return appliedTransform2D;
	}
	
	//----------------- support for ChangeEvents --------------------------------------
	
	/**
	 * Add a ChangeListener to this Transform.
	 * Change events are sent when the window (xmin,xmax,ymin,ymax) is changed. It should not
	 * be necessary for ordinary programmers to call or to override this method.
	 * Views are automatically set up as ChangeListeners for their transforms.
	 * @param listener The listener to be added.  If the value is null, nothing is done.
	 */
	synchronized public void addChangeListener(ChangeListener listener) {
		if (listener == null)
			return;
		if (changeListeners == null)
			changeListeners = new ArrayList<ChangeListener>();
		changeListeners.add(listener);
	}
	
	/**
	 * Remove a ChangeListener from this Transform.
	 * @see #addChangeListener(ChangeListener)
	 * @param listener The listener to be removed.  If it is not currently registered with the
	 * Transform, then nothing is done.
	 */
	synchronized public void removeChangeListener(ChangeListener listener) {
		if (listener != null && changeListeners != null) {
			changeListeners.remove(listener);
			if (changeListeners.isEmpty())
				changeListeners = null;
		}
	}
	
	/**
	 * Sends a ChangeEvent to registered ChangeListeners.
	 */
	synchronized protected void fireTransformChangeEvent() { 
		if (changeListeners == null)
			return;
		if (changeEvent == null)
			changeEvent = new ChangeEvent(this);
		for (int i = 0; i < changeListeners.size(); i++)
			changeListeners.get(i).stateChanged(changeEvent);
	}

	
	    
}