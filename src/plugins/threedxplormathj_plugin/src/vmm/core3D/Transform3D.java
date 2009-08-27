/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import vmm.core.Transform;
import vmm.core.VMMSave;

/**
 * A transform that encodes a 3D view of an exhibit and the projection of that view onto a 2D view plane.
 * This class inherits the window-to-viewport transformation from its superclass, and adds a 3D viewing
 * transformation.  
 * <p>The projection is defined by three unit vectors: ViewDirection, ImagePlaneYDirection,
 * and ImagePlaneXDirection.  The ViewDirection is determined by the viewpoint that is specified for the
 * view; ViewDirection is a unit vector that points in the direction from the viewpoint towards (0,0,0).
 * The ViewDirection determines the viewing plane, which is a plane through (0,0,0) normal to the view
 * direction.  The ImagePlaneYDirection vector is obtained by projecting a "view up" vector onto the
 * viewing plane, and then normalizing the projection.  Initially, the view up vector is (0,0,1)
 * or, if (0,0,1) almost normal to the view plane, (0,1,0).  When the viewpoint is modified, the new
 * ImagePlaneYDirection is obtained by using the previous ImagePlaneYDirection as the view up vector, if 
 * possible.  After the ViewDirection and ImagePlaneYDirection are determined, the ImagePlaneXDirection
 * is simply the cross product of these two vectors.
 * <p>The transform also has an associated "focalLength" which is set to the distance of the viewpoint
 * from the origin, and a clipDistance, which is set to 25% of the focal length.
 * <p>A Transform3D fires a ChangeEvent when any part of the data that determines a 3D view is changed,
 * such as the viewpoint.
 * <p>(Note that in the default view, the yz-plane projects onto the screen, and the x-axis is perpendicular
 * to the screen.  Nevertheless, the screen is thought of as being the xy-plane.)
 * TODO: Possibly add support for using a point other than (0,0,0) as the view reference point; check how
 * clip distance and focal length should be changed when viewpoint is changed.
 */
public class Transform3D extends Transform {
	
	@VMMSave private Vector3D viewPoint;
	@VMMSave private double clipDistance;  // initially set to 0.25*focalLength whenever viewPoint changes, but can be reset
	@VMMSave private Vector3D imagePlaneYDirection = new Vector3D(0,0,1);  // changed automatically when viewPoint changes, but can be reset
	@VMMSave private boolean orthographicProjection = false;

	private Vector3D viewDirection;       
	private double focalLength;   // always set to norm(viewPoint).  Is this correct?
	private Vector3D imagePlaneXDirection;
	
	private Vector3D tempVector = new Vector3D();  // for use in some methods (note: not thread-safe!)
	
	private Vector3D saveViewDirection, saveImagePlaneXDirection, saveImagePlaneYDirection, saveViewPoint;
	                     // These are the values as set, witout modification for left/right eye.

	private double objectDisplacementNormalToScreen;  // Object coordinates are displaced by this amount into or out of the screeen.


	/**
	 * Creates a Transform3D object with default viewpoint (20,0,0) and a default window with x and y ranges from -5 to 5.
	 */
	public Transform3D() {
		this(null,-5,5,-5,5);
	}
	
	/**
	 * Creates a Transform3D object with a specified viewpoint and a default window with x and y ranges from -5 to 5.
	 * @param viewPoint the viewpoint of the transformation.  If null, the default, (20,0,0), is used.
	 */
	public Transform3D(Vector3D viewPoint) {
		this(viewPoint,-5,5,-5,5);
	}
	
	/**
	 * Creates a Transform3D with a specifed viewpoint and with an xy-window determined by a given "graphic scale"
	 * @param viewPoint the viewpoint of the transformation.  If null, the default, (20,0,0), is used.
	 * @param nominalGraphicScale number of pixels per unit along the x- and y-axes, assuming that the size of
	 * the window is "normal".  The normal size is given by a private constant <code>NORMAL_SIZE</code>, which
	 * is set to 600 at the time this comment was written.
	 */
	public Transform3D(Vector3D viewPoint, double nominalGraphicScale) {
		super(nominalGraphicScale);
		setViewPoint(viewPoint);
	}
	
	/**
	 * Construct a Transform3D with specified viewpoint and xy-window.  The transformation is not
	 * fully determined until a viewport in the viewing plane is also specified; this is done when
	 * {@link Transform#setUpDrawInfo(Graphics2D, int, int, int, int, boolean, boolean)} is called.
	 * Note that when that method is called with its preserveAspect option set to true, the
	 * requested xmin, xmax, ymin, and ymax values might be adjusted to make the aspect ratio of
	 * the xy-window match the aspect ratio of the viewport.
	 * @param viewPoint The viewpoint of the transformation; if null, the default, (20,0,0), is used.
	 * @param xmin the requested mimimum x-value for the transformation
	 * @param xmax the requested maximum x-value for the transformation
	 * @param ymin the requested mimimum y-value for the transformation
	 * @param ymax the requested maximum y-value for the transformation
	 */
	public Transform3D(Vector3D viewPoint, double xmin, double xmax, double ymin, double ymax) {
		super(xmin,xmax,ymin,ymax);
		setViewPoint(viewPoint);
	}
	
	/**
	 * Construct a Transform3D with the same transform data as a specified transform.  If the specfied
	 * transform is not a Transform3D, only the xy-window is copied; if it is a Transform3D, the 3D transformation
	 * data is also copied.
	 * @param tr the non-null transform whose data is to be copied.
	 */
	public Transform3D(Transform tr) {
		super(tr);
		if (tr instanceof Transform3D) {
			Transform3D tr3 = (Transform3D)tr;
			viewPoint = new Vector3D(tr3.viewPoint);
			viewDirection = new Vector3D(tr3.viewDirection);
			imagePlaneXDirection = new Vector3D(tr3.imagePlaneXDirection);
			imagePlaneYDirection = new Vector3D(tr3.imagePlaneYDirection);
			orthographicProjection = tr3.orthographicProjection;
			clipDistance = tr3.clipDistance;
			focalLength = tr3.focalLength;
		}
	}
	
	/**
	 * Returns true if the projection is an orthographic projection, or false if it is a perspective projection.
	 * @see #setOrthographicProjection(boolean)
	 */
	public boolean getOrthographicProjection() {
		return orthographicProjection;
	}

	/**
	 * Sets whether the projection from 3D onto the viewplane should be a perspective projection or
	 * an orthographic projection.
	 * @param orthographicProjection "true" to use an orthographic projection; "false" to use a perspective projection.
	 */
	public void setOrthographicProjection(boolean orthographicProjection) {
		if (this.orthographicProjection == orthographicProjection)
			return;
		this.orthographicProjection = orthographicProjection;
		fireTransformChangeEvent();
	}

	/**
	 * Sets the viewpoint of the transformation.  Note that when this method is called, 
	 * a new transformation is defined which has a viewDirection specified by the viewpoint.
	 * This method then sets ImagePlaneYDirection and ImagePlaneXDirection by
	 * calling {@link #setImagePlaneYDirection(Vector3D)} with the current ImagePlaneYDirection
	 * as parameter.  In addition, the focal length is set to the distance of the viewpoint
	 * from (0,0,0), the clip distance is set to 0.25 times the focal length, and the
	 * ViewDirection is set to be a unit vector that points from the viewpoint towards (0,0,0).
	 * @param viewPoint the new viewpoint; if null, the default, (20,0,0), is used.
	 * The viewpoint cannot be (0,0,0), or the result will be an undefined transformation.
	 * Note that a copy of the viewPoint parameter is made (if it is non-null).
	 * (Note: Also turns off left/right eye selection, if in use.)
	 */
	public void setViewPoint(Vector3D viewPoint) {
		if (viewPoint == null)
			this.viewPoint = viewPoint = new Vector3D(20,0,0);
		else
			this.viewPoint = new Vector3D(viewPoint);
		viewDirection = new Vector3D(this.viewPoint);
		viewDirection.normalize();
		viewDirection.negate();
		focalLength = viewPoint.norm();
		clipDistance = 0.25*focalLength;
		saveViewPoint = new Vector3D(viewPoint);
		saveViewDirection = new Vector3D(viewDirection);
		setImagePlaneYDirection(imagePlaneYDirection); // calls fireTransformChangeEvent()
	}

	/**
	 * Returns the current viewpoint of the transformation.
	 * @return a non-null vector that is the curent viewpoint.
	 */
	public Vector3D getViewPoint() {
		return new Vector3D(viewPoint);
	}
	
	/**
	 * Returns the clip distance.
	 * @see #setClipDistance(double)
	 */
	public double getClipDistance() {
		return clipDistance;
	}

	/**
	 * Set the clip distance.  This value is also set when the viewpoint is modified by
	 * {@link #setViewPoint(Vector3D)}.  That method sets the clipDistance to 0.25 times the distance
	 * from the viewpoint to the origin.
	 */
	public void setClipDistance(double clipDistance) {
		if (this.clipDistance == clipDistance)
			return;
		this.clipDistance = clipDistance;
		fireTransformChangeEvent();
	}

	/**
	 * Gets the focal length, which is just the distance from the viewpoint to the origin.
	 */
	public double getFocalLength() {
		return focalLength;
	}
	

	/**
	 * Get the amount by which object coordinates are displaed normal to the screen.
	 * @see #setObjectDisplacementNormalToScreen(double)
	 */
	public double getObjectDisplacementNormalToScreen() {
		return objectDisplacementNormalToScreen;
	}

	/**
	 * Set the amount by which object coordinates are displaed normal to the screen.
	 * Value is clamped so that its absolute value is less than or equal to 3/4
	 * of the focal length.
	 * Before the object-to-view coordinate transformation is applied to any point,
	 * that point is displaced in the direction of the viewDirection vector by an
	 * amount equal to the setting of objectDisplacementNormalToScreen.  The
	 * default value is zero.  (In View3D, the amount is always 0 in monocular
	 * view modes, but can be chaned for the stereo views.  BasicMouseTask3D allows the
	 * user to adjust this value for anaglyph views by holding down the shift key
	 * while dragging with the middle mouse button or by holding down the shift and
	 * option/ALT keys while dragging.)  A ChangeEvent is generated when the value
	 * of this property is changed.
	 */
	public void setObjectDisplacementNormalToScreen(
			double objectDisplacementNormalToScreen) {
		if (objectDisplacementNormalToScreen < -0.75*focalLength)
			objectDisplacementNormalToScreen = -0.75*focalLength;
		else if (objectDisplacementNormalToScreen > 0.75*focalLength)
			objectDisplacementNormalToScreen = 0.75*focalLength;
		if (this.objectDisplacementNormalToScreen == objectDisplacementNormalToScreen)
			return;
		this.objectDisplacementNormalToScreen = objectDisplacementNormalToScreen;
		System.out.println("ObjectDisplacementNormalToScreen = " + objectDisplacementNormalToScreen);
		fireTransformChangeEvent();
	}

	/**
	 * Gets the current ImagePlaneXDirection, one of the three unit vectors that determine the projection.
	 * @see #setImagePlaneYDirection(Vector3D)
	 */
	public Vector3D getImagePlaneXDirection() {
		return new Vector3D(imagePlaneXDirection);
	}

	/**
	 * Gets the current ImagePlaneYDirection, one of the three unit vectors that determine the projection.
	 * @see #setImagePlaneYDirection(Vector3D)
	 */
	public Vector3D getImagePlaneYDirection() {
		return new Vector3D(imagePlaneYDirection);
	}

	/**
	 * Gets the current ViewDirection, one of the three unit vectors that determine the projection.
	 * @see #setViewPoint(Vector3D)
	 * @see #setImagePlaneYDirection(Vector3D)
	 */
	public Vector3D getViewDirection() {
		return new Vector3D(viewDirection);
	}
	
	/**
	 * Sets both the imagePlaneYDirection and imagePlaneXDirection so that these two vectors
	 * and the viewDirection (which points to the viewpoint) form an orthonomal system.
	 * (Note: Also turns off left/right eye selection, if in use.)
	 * @param viewUp the new imagePlaneYDirection, which will point upwards on the screen.
	 */
	public void setImagePlaneYDirection(Vector3D viewUp) {
		viewDirection = new Vector3D(saveViewDirection); // Just in case a left or right eye view is selected -- it is unselected after this method executes
		viewPoint = new Vector3D(saveViewPoint);
		double projection = viewDirection.dot(viewUp);
		imagePlaneYDirection = new Vector3D(viewUp.x - projection*viewDirection.x, viewUp.y - projection*viewDirection.y, viewUp.z - projection*viewDirection.z);
		// imagePlaneYDirection = viewUp - (viewDirection dot viewUp)*viewDirection  -- projection of viewUp onto image plane
		if (imagePlaneYDirection.norm() < 0.00001)  // try (0,0,1) as the default  ( viewDirection.dot(0,0,1) is just viewDirection.z )
			imagePlaneYDirection = new Vector3D(-viewDirection.z*viewDirection.x, -viewDirection.z*viewDirection.y, 1-viewDirection.z*viewDirection.z);
		if (imagePlaneYDirection.norm() < 0.00001)  // try (0,1,0 if both viewUp and (0,0,1) fail (because they are both too close to being multiples of viewDirection)
			imagePlaneYDirection = new Vector3D(-viewDirection.y*viewDirection.x, 1-viewDirection.y*viewDirection.y,  -viewDirection.y*viewDirection.z);
		imagePlaneYDirection.normalize();
		imagePlaneXDirection = viewDirection.cross(imagePlaneYDirection);
		saveImagePlaneXDirection = new Vector3D(imagePlaneXDirection);
		saveImagePlaneYDirection = new Vector3D(imagePlaneYDirection);
		fireTransformChangeEvent();
	}


	/**
	 * Rotates vector e1 onto vector e2, resulting in a change of view.  A ChangeEvent is generated
	 */
	public void applyTransvection(Vector3D e1, Vector3D e2) {  // These must be unit vectors!
		Vector3D e = new Vector3D(e1.x+e2.x, e1.y+e2.y, e1.z+e2.z);
		e.normalize();
		Vector3D temp = new Vector3D();
		reflectInAxis(e,saveViewDirection,temp);
		reflectInAxis(e1,temp,saveViewDirection);
		reflectInAxis(e,saveImagePlaneXDirection,temp);
		reflectInAxis(e1,temp,saveImagePlaneXDirection);
		reflectInAxis(e,saveImagePlaneYDirection,temp);
		reflectInAxis(e1,temp,saveImagePlaneYDirection);
		double vn = saveViewPoint.norm();
		saveViewPoint.x = -vn*saveViewDirection.x;
		saveViewPoint.y = -vn*saveViewDirection.y;
		saveViewPoint.z = -vn*saveViewDirection.z;
		selectNoEye();
		fireTransformChangeEvent();
	}
	
	private void doTransvection(Vector3D e1, Vector3D e2) {
		Vector3D e = new Vector3D(e1.x+e2.x, e1.y+e2.y, e1.z+e2.z);
		e.normalize();
		Vector3D temp = new Vector3D();
		reflectInAxis(e,viewDirection,temp);
		reflectInAxis(e1,temp,viewDirection);
		reflectInAxis(e,imagePlaneXDirection,temp);
		reflectInAxis(e1,temp,imagePlaneXDirection);
		reflectInAxis(e,imagePlaneYDirection,temp);
		reflectInAxis(e1,temp,imagePlaneYDirection);
		double vn = viewPoint.norm();
		viewPoint.x = -vn*viewDirection.x;
		viewPoint.y = -vn*viewDirection.y;
		viewPoint.z = -vn*viewDirection.z;
	}
	
	private void reflectInAxis(Vector3D axis, Vector3D source, Vector3D destination) {
		double s = 2 * (axis.x * source.x + axis.y * source.y + axis.z * source.z);
		destination.x = s*axis.x - source.x;
		destination.y = s*axis.y - source.y;
		destination.z = s*axis.z - source.z;
	}
	
	/**
	 * Changes the view data to reflect a view from the left eye position.
	 * No change event is generated.  This package-private method is intended for use in View3D to
	 * support stereographic viewing.
	 */
	void selectLeftEye(double separationFactor) {
		viewDirection = new Vector3D(saveViewDirection);
		imagePlaneXDirection = new Vector3D(saveImagePlaneXDirection);
		imagePlaneYDirection = new Vector3D(saveImagePlaneYDirection);
		viewPoint = new Vector3D(saveViewPoint);
		Vector3D angularEyeSeparation = imagePlaneXDirection.times(separationFactor);
		Vector3D leftEyeRay = viewDirection.plus(angularEyeSeparation);
		leftEyeRay.normalize();
		doTransvection(viewDirection,leftEyeRay);
	}
	
	/**
	 * Changes the view data to reflect a view from the right eye position.
	 * No change event is generated.  This package-private method is intended for use in View3D to
	 * support stereographic viewing.
	 */
	void selectRightEye(double separationFactor) {
		viewDirection = new Vector3D(saveViewDirection);
		imagePlaneXDirection = new Vector3D(saveImagePlaneXDirection);
		imagePlaneYDirection = new Vector3D(saveImagePlaneYDirection);
		viewPoint = new Vector3D(saveViewPoint);
		Vector3D angularEyeSeparation = imagePlaneXDirection.times(separationFactor);
		Vector3D leftEyeRay = viewDirection.minus(angularEyeSeparation);
		leftEyeRay.normalize();
		doTransvection(viewDirection,leftEyeRay);
	}
	
	/**
	 * Changes the view data to reflect non-sterographic viewing.
	 * No change event is generated.  This package-private method is intended for use in View3D to
	 * support stereographic viewing.
	 */
	void selectNoEye() {
		viewDirection = new Vector3D(saveViewDirection);
		imagePlaneXDirection = new Vector3D(saveImagePlaneXDirection);
		imagePlaneYDirection = new Vector3D(saveImagePlaneYDirection);
		viewPoint = new Vector3D(saveViewPoint);
	}
	
	/**
	 * Tests whether obj is a Transform3D with the same transform data as this transform,
	 * that is, both {@link Transform#hasSameViewTransform(Transform)} and
	 * {@link #hasSameProjection(Transform3D)} are true.
	 */
	public boolean equals(Object obj) {
		if (obj == null || !Transform3D.class.equals(obj.getClass()) )
			return false;
		Transform3D tr = (Transform3D)obj;
		return hasSameProjection(tr) && hasSameViewTransform(tr);
	}
	
	/**
	 * Tests whether tr has the same projection from 3D to the view plane as this transform
	 * (but not necessarily the same window and viewport).
	 */
	public boolean hasSameProjection(Transform3D tr) {
		if (tr == null)
			return false;
		return ( orthographicProjection == tr.orthographicProjection && clipDistance == tr.clipDistance&&
				viewPoint.equals(tr.viewPoint) && imagePlaneYDirection.equals(tr.imagePlaneYDirection) );
	}
	
	/**
	 * Creates a copy of this Transform3D.
	 */	
	public Object clone() {
		Transform3D tr = (Transform3D)super.clone();
		tr.viewPoint = new Vector3D(viewPoint);
		tr.viewDirection = new Vector3D(viewDirection);
		tr.imagePlaneXDirection = new Vector3D(imagePlaneXDirection);
		tr.imagePlaneYDirection = new Vector3D(imagePlaneYDirection);
		return tr;
	}
	
	/**
	 * This package-scope method exists to make it possible for View3D and View3DLit to swap
	 * graphics contexts in and out of this transform while doing stereo viewing.  It is not meant
	 * for general use, and using it improperly would mess things up pretty thoroughly
	 */
	void useGraphics(Graphics2D g, Graphics2D untransformedGraphics) {
		this.g = g;
		this.untransformedGraphics = untransformedGraphics;
	}
	
	//----------------------------------------- Projection and Transformation -------------------------------------------
	
	/**
	 * Transform a point from object coordinates to viewing coordinates.  After this method, the x- and y- coordinates
	 * of the resulting vector are coordinates for the viewing plane, while the z-coordinate encodes the distance of
	 * the object point from the viewing plane.
	 * @param objectPoint The non-null point whose coordinates are to be transformed.  This vector is not modified.
	 * @param viewCoords The non-null vector that will contain the result after this method is called.  The previous
	 * components of this vector are replaced with the transformed version of objectPoint.
	 */
	public void objectToViewCoords(Vector3D objectPoint, Vector3D viewCoords) {
		double x1 = objectPoint.x;
		double y1 = objectPoint.y;
		double z1 = objectPoint.z;
		if (objectDisplacementNormalToScreen != 0) {
			x1 -= objectDisplacementNormalToScreen * saveViewDirection.x;
			y1 -= objectDisplacementNormalToScreen * saveViewDirection.y;
			z1 -= objectDisplacementNormalToScreen * saveViewDirection.z;
		}
		if (orthographicProjection) {
			    // just project onto x- and y-axes of image plane
			viewCoords.x = x1 * imagePlaneXDirection.x + y1 * imagePlaneXDirection.y + z1 * imagePlaneXDirection.z;
			viewCoords.y = x1 * imagePlaneYDirection.x + y1 * imagePlaneYDirection.y + z1 * imagePlaneYDirection.z;
		}
		else {
			     // Vector A = objectPoint - viewPoint          -- copied from DrawContext in XPlorMath3D
			     // dP = A dotproduct viewDirecction            -- projects A into normal plan to viewDirection
			     // Vector B = (focalLength / dP) times A
			     // Vector projectedPoint3D = ViewPoint + B     (I want to do it without creating Vector3D objects)
			double x = x1 - viewPoint.x;
			double y = y1 - viewPoint.y;    // Computes A
			double z = z1 - viewPoint.z;
			double dP = x * viewDirection.x + y * viewDirection.y + z*viewDirection.z;
			x = (focalLength / dP) * x;
			y = (focalLength / dP) * y;    // Computes B
			z = (focalLength / dP) * z;
			x += viewPoint.x;
			y += viewPoint.y;    // Computes projectedPoint.
			z += viewPoint.z;
			      // Now, compute projections onto x- and y-axes of image plane.
		    viewCoords.x = x * imagePlaneXDirection.x + y * imagePlaneXDirection.y + z * imagePlaneXDirection.z;
		    viewCoords.y = x * imagePlaneYDirection.x + y * imagePlaneYDirection.y + z * imagePlaneYDirection.z;
		}  
		viewCoords.z = -(x1 * viewDirection.x + y1 * viewDirection.y + z1 * viewDirection.z);
		    // z is negative because viewDirection points into the screen, and so is the negative of the unit z vvector
	}
	
	/**
	 * Transform a point in world coordinates to viewing coodinates.  This is done by
	 * calling {@link #objectToViewCoords(Vector3D, Vector3D)} with a newly created vector as its
	 * second argument, and then returning that vector.
	 * @param objectPoint The non-null point that is to be transformed
	 * @return The transformed version of objectPoint
	 */
	public Vector3D objectToViewCoords(Vector3D objectPoint) {
		Vector3D viewCoords = new Vector3D();
		objectToViewCoords(objectPoint,viewCoords);
		return viewCoords;
	}
	
	/**
	 * Project a world-coordinate point onto the view plane.  This is the same as taking just the x- and y-coordinate
	 * from the vector computed by {@link #objectToViewCoords(Vector3D, Vector3D)}.
	 * @param objectPoint  a non-null vector whose corrdinates are to be transformed
	 * @param p a non-null point whose coordinates will be set to the projected (x,y) point.
	 */
	public void objectToXYWindowCoords(Vector3D objectPoint, Point2D p) {
		objectToViewCoords(objectPoint, tempVector);
		p.setLocation(tempVector.x, tempVector.y);
	}
	
	/**
	 * Project a world-coordinate point onto the view plane.  This is done by calling
	 * {@link #objectToDrawingCoords(Vector3D, Point2D)} with a newly created Poin2D as its second
	 * parameter, and then returning that point.
	 */
	public Point2D objectToXYWindowCoords(Vector3D objectPoint) {
		Point2D p = new Point2D.Double();
		objectToXYWindowCoords(objectPoint, p);
		return p;
	}
	
	/**
	 * Transform a point given in world coordinates to a 2D point that can be used for drawing on the
	 * view plane.  If the {@link Transform#appliedTransform2D} property is true, the result is
	 * the same as the result of {@link #objectToXYWindowCoords(Vector3D, Point2D)}, since regular 
	 * xy window coordinates can be used for drawing directly.  If the {@link Transform#appliedTransform2D} property is
	 * false, then the xy-coordinates are further transformed to the viewport (pixel) coordintates that
	 * are needed for drawing.
	 * @param objectPoint The point whose coordinates are to be projeted and transformed.
	 * @param drawingCoords A pre-allocated non-null point to contain the result.
	 */
	public void objectToDrawingCoords(Vector3D objectPoint, Point2D drawingCoords) {
		objectToXYWindowCoords(objectPoint, drawingCoords);
		windowToDrawingCoords(drawingCoords);
	}
	
	/**
	 * Transform a point given in world coordinates to a 2D point that can be used for drawing on the
	 * view plane.  This is done by applying {@link #objectToDrawingCoords(Vector3D, Point2D)} to
	 * a newly allocated Point2D, and then returning that point.
	 */
	public Point2D objectToDrawingCoords(Vector3D objectPoint) {
		Point2D p = new Point2D.Double();
		objectToXYWindowCoords(objectPoint, p);
		windowToDrawingCoords(p);
		return p;
	}
	
	/**
	 * Compute just the z-coordinate of a given point in view coordinates.
	 * This is the same as the z value in the vector that would be returned by
	 * {@link #objectToViewCoords(Vector3D)}
	 * @param objectPoint the untransformed point in object coordinates
	 * @return the z-coordinate of the transformed point.
	 */
	public double objectToViewZ(Vector3D objectPoint) {
		double x1 = objectPoint.x;
		double y1 = objectPoint.y;
		double z1 = objectPoint.z;
		if (objectDisplacementNormalToScreen != 0) {
			x1 -= objectDisplacementNormalToScreen * saveViewDirection.x;
			y1 -= objectDisplacementNormalToScreen * saveViewDirection.y;
			z1 -= objectDisplacementNormalToScreen * saveViewDirection.z;
		}
		return -(x1 * viewDirection.x + y1 * viewDirection.y + z1 * viewDirection.z);
	}
	

}
