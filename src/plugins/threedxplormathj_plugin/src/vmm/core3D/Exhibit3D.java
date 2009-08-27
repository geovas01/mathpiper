/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Graphics2D;
import java.util.ArrayList;

import vmm.core.Exhibit;
import vmm.core.Transform;
import vmm.core.VMMSave;
import vmm.core.View;

/**
 * An exhibit that can be rendered in 3D can be defined as a subclass of this class.  It is not
 * an absolute requirement that a 3D exhibit be a subclass of this class, since an Exhibit is always
 * in charge of rendering itself. However, this class does provide some convenient default behavior
 * for 3D exhibits.  Remember that it is possible for a single exhibit to have both 2D and 3D views.
 */
abstract public class Exhibit3D extends Exhibit {
	
	/**
	 * When the {@link #computeDrawDataHook(View, Transform)} method is called with a 3D View and Transform, the
	 * Transform3D that is being used is recorded here so that it can be compared with the Transform3D that is
	 * used the next time the method is called.  This is only for use in rare circumstances when am Exhibit has
	 * cached data that depends on the transform.
	 */
	protected Transform3D previousTransform3D;

	/**
	 * The value of this variable is returned by the {@link #getDefaultViewpoint()} method.  When the Exhibit
	 * is installed in a View3D, this vector is used as the initial viewpoint for viewing the exhibit.
	 */
	@VMMSave protected Vector3D defaultViewpoint = new Vector3D(20,0,0);
	
	/**
	 * The value of this variable is returned by the {@link #getDefaultViewUp()} method.  When the Exhibit
	 * is installed in a View3D, this vector is used as the initial view up vector.  A null value indicates
	 * that the default should be used.
	 */
	@VMMSave protected Vector3D defaultViewUp = null;
		
	/**
	 * Returns the default transform for use in the specified View.  If the View is an instance of the {@link View3D}
	 * class, then a {@link Transform3D} object is returned; this object is constructed from the
	 * exhibit's default viewpoint and default window.  If the view is not a View3D, then a 2D {@link Transform} is
	 * returned that is constructed using the exhibit's default window only.
	 * @see #setDefaultViewpoint(Vector3D)
	 * @see Exhibit#setDefaultWindow(double, double, double, double)
	 */
	public Transform getDefaultTransform(View view) {
		double[] window = getDefaultWindow();
		if (view instanceof View3D) {
			Transform3D tr = new Transform3D(defaultViewpoint, window[0], window[1], window[2], window[3]);
			if (defaultViewUp != null)
				tr.setImagePlaneYDirection(defaultViewUp);
			return tr;
		}
		else
			return new Transform(window[0], window[1], window[2], window[3]);
	}
	
	/**
	 * Returns the default View of this Exhibit.  In the Exhibit3D class, the return value is a basic {@link View3D}.
	 */
	public View getDefaultView() {
		return new View3D();
	}
	
	/**
	 * Returns the default viewpoint for viewing this Exhibit in 3D.
	 * @see #setDefaultViewpoint(Vector3D)
	 */
	public Vector3D getDefaultViewpoint() {
		return defaultViewpoint;
	}

	/**
	 * Set the default viewpoint for viewing this exhibit in 3D.  The default viewpoint is used
	 * to construct the default 3D view of this Exhibit in {@link #getDefaultTransform(View)}.
	 * Subclasses of Exhibit3D can call this method -- probably in a construtor -- to set a resonable viewpoint
	 * for the particular exhibit.  If the default viewpoint is set to null, then the default
	 * of the {@link Transform3D} class is used; this default is the point (20,0,0).
	 * @param defaultViewpoint the default viewpoint for this Exhibit3D, or null to indicate that the
	 * default viewpoint in Transform3D is to be used.
	 */
	public void setDefaultViewpoint(Vector3D defaultViewpoint) {
		this.defaultViewpoint = defaultViewpoint == null ? new Vector3D(20,0,0) : defaultViewpoint;
	}

	/**
	 * Returns the default view up for viewing this Exhibit in 3D.
	 * @see #setDefaultViewUp(Vector3D)
	 */
	public Vector3D getDefaultViewUp() {
		return defaultViewpoint;
	}

	/**
	 * Set the default view up vector for viewing this exhibit in 3D. The value of this vector
	 * is used to set the view up vector when the exhibit is installed.  The initial value is null,
	 * which indicates that the default view up should be accepeted.
	 * @param defaultViewUp the default view up vector for this Exhibit3D, or null to indicate that the
	 * default view up should be accepted.
	 */
	public void setDefaultViewUp(Vector3D defaultViewUp) {
		this.defaultViewUp = defaultViewUp;
	}

	/**
	 * Called by {@link Exhibit#render(Graphics2D, View, Transform, ArrayList)} to give the exhibit a chance
	 * to compute its cached data, if necessary.  This method is not meant to be called directly.  It exists
	 * only to make it possible to write a unified <code>render</code> method that will work for both the
	 * basic Exhibit class and for its subclasses.  The method in this Exhibit3D class simply calls either
	 * {@link Exhibit#computeDrawData(View, boolean, Transform, Transform)} or
	 * {@link Exhibit3D#computeDrawData3D(View3D, boolean, Transform3D, Transform3D)}, depending on
	 * whether the View is a 2D or 3D View.
	 * <p>Subclasses should almost always override <code>computeDrawData</code> and/or
	 * <code>computeDrawData3D</code> instead of overriding this method.
	 */
	protected void computeDrawDataHook(View view, Transform transform) {
		if (view instanceof View3D && transform instanceof Transform3D) {
			computeDrawData3D((View3D)view, exhibitNeedsRedraw, previousTransform3D, (Transform3D)transform);
			previousTransform3D = (Transform3D)(transform.clone());
		}
		else {
			computeDrawData(view, exhibitNeedsRedraw, previousTransform, transform);
			previousTransform = (Transform)(transform.clone());
		}
	}
	
	/**
	 * Called by {@link Exhibit#doDraw(Graphics2D, View, Transform)} when the exhibit needs to
	 * redraw itself.  This method is not meant to be called directly.  It exists
	 * only to make it possible to write a unified <code>doDraw</code> method that will work for both the
	 * basic Exhibit class and for its subclasses.  The method in this Exhibit3D class simply calls either
	 * {@link Exhibit#doDraw(Graphics2D, View, Transform)} or
	 * {@link Exhibit3D#doDraw3D(Graphics2D, View3D, Transform3D)}, depending on
	 * whether the View is a 2D or 3D View.
	 * <p>Subclasses should almost always override <code>doDraw</code> and/or
	 * <code>doDraw3D</code> instead of overriding this method.
	 */
	protected void doDrawHook(Graphics2D g, View view, Transform transform) {
		if (view instanceof View3D && transform instanceof Transform3D)
			doDraw3D(g,(View3D)view,(Transform3D)transform);
		else
			doDraw(g,view,transform);
	}
	
	/**
	 * Recopmputes cached data, if necessary, for a 3D rendering of this exhibit.
	 * The method in the Exhibit3D class does nothing.  Subclasses should override this method as appropriate.
	 * Note that this method is called only if the Exhibit is being rendered in a View3D.  Exhibits that have
	 * both two and three dimensional renderings can also override {@link Exhibit#computeDrawData(View, boolean, Transform, Transform)}
	 * to say what should be drawn in a 2D view.
	 * @param view the 3D View where the Exhibit is about to be drawn.  
	 * @param exhibitNeedsRedraw if true, then something about the Exhibit has changed that probably requires
	 * recomputation of cached data.  For example, this is set to true when one of the Parameters of the Exhibit
	 * has changed since the previous redraw.
	 * @param previousTransform3D the Transform3D that was used the last time this exhibit was drawn.  This can be null, if
	 * this is the first time that the Exhibit is being drawn (in 3D).
	 * @param newTransform3D the transform that will be used to draw the Exhibit during the current drawing operation.
	 * This parameter and the previousTransform3D parameter are provided so that the Exhbit can detect those rare
	 * cases where cached data exists that depends on the transform.  Most Exhibits will just ignore the
	 * transform parameters.
	 */
	protected void computeDrawData3D(View3D view, boolean exhibitNeedsRedraw, Transform3D previousTransform3D, Transform3D newTransform3D) {
	}
	
	/**
	 * Draws this Exhibit in a 3D view. The method in the Exhibit3D class does nothing.  Subclasses should override
	 * this method to do the actual drawing.  When this method is called, {@link #computeDrawData3D(View3D, boolean, Transform3D, Transform3D)}
	 * has already been called, so that any cached data should be correct.
	 * Note that this method is called only if the Exhibit is being drawn in a View3D.  Exhibits that have
	 * both two and three dimensional renderings should also override {@link Exhibit#doDraw(Graphics2D, View, Transform)}.
	 * <p>When drawing a 3D exhibit, you are strongly advised to use the drawing routines supplied by the 
	 * {@link View3D} and {@link View3DLit} classes.  These routines will automatically produce the correct results
	 * in stereo views -- the same is not true about direct drawing to the graphics context.
	 * @param g the graphics context where the exhibit is being drawn.
	 * @param view The View3D in which the exhibit is being drawn.  In general, it is advisable to use the view for
	 * all drawing operations.
	 * @param transform The transform that is being used to draw the exhibit.
	 */
	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
	}
	
}
