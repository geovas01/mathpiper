/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.core.Axes2D;
import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.MouseTask;
import vmm.core.Prefs;
import vmm.core.SaveAndRestore;
import vmm.core.Transform;
import vmm.core.Util;
import vmm.core.VMMSave;
import vmm.core.View;

/**
 * Extends the View class to support three-dimensional viewing.
 */
public class View3D extends View {
	
	/**
	 * One of the possible values for the viewStyle, specifying a basic non-stereo view.  This is the default view style.
	 */
	public static final int MONOCULAR_VIEW = 0;

	/**
	 * One of the possible values for the viewStyle, specifying "anaglyph stereo". That is, left and right eye views
	 * are drawn in green and red, respectively, and the two views are composed into a single image that can be viewed
	 * with red/green or red/blue stereo glasses.  In this view, the background color is forced to black.
	 */
	public static final int RED_GREEN_STEREO_VIEW = 1;
	
	/**
	 * One of the possible values for the viewStyle, specifying a "stereograph" view.  That is, the left and
	 * right eye views are drawn side-by-side.  The user can view a 3D image by looking at a point in back of the
	 * images, to make the left and right eye views fuse into a single image.
	 */
	public static final int STEREOGRAPH_VIEW = 2;
	
	/**
	 * One of the possible values for the viewStyle, specifying a "stereograph" view.  That is, the left and
	 * right eye views are drawn side-by-side.  The user can view a 3D image by crossing his or her eyes to fuse
	 * the left ane right eye views.
	 */
	public static final int CROSS_EYE_STEREO_VIEW = 3;
	
	@VMMSave
	private int viewStyle = MONOCULAR_VIEW;

	private Color savedBackground;
	
	@VMMSave
	private boolean enableThreeD = true;
	
	private Transform saveTransform2DWhileThreeDEnabled = null;
	private Transform saveTransform3DWhileThreeDDisabled = null;
	private int saveViewStyleWhile3DDisabled = -1;

	
	/**
	 * The current transform of this view, if it is a Transform3D.  This is just a reference to
	 * the same object refereced by {@link View#getTransform()}, and is here for convenience only.
	 */
	protected Transform3D transform3D;

	/**
	 * Used when the view style is RED_GREEN_STEREO_VIEW.
	 */
	protected StereoComposite stereoComposite;
	
	private Rectangle leftEyeRect, rightEyeRect;  // for STEROGRAPH_VIEW and CROSS_EYE_STEREO_VIEW
	private Dimension stereographPreferredSize;
	private Rectangle stereographPreferredLeftEyeRect, stereographPreferredRightEyeRect;
	private int saveStereographDisplayWidth, saveStereographDisplayHeight;
	private Dimension savePreferredSizeForStereograph;
	
	private Graphics2D leftEyeGraphics, rightEyeGraphics, leftEyeUntransformedGraphics, rightEyeUntransformedGraphics;
	private Graphics2D saveGraphicsDuringStereo, saveUntransformedGraphicsDuringStereo;
	
	private Vector3D viewDirection;  // a copy of transform3D.getViewDirection(), for use for clipping during 3D drawing
	private double clipZ;     // z-value above which points are clipped, for use during 3D drawing
	private double eyeSeparationMultiplier;   // the current eyeSeparationMultiplier, for use during 3D drawing.
	
	/**
	 * Left and right offscreen images, used during stereograph viewing.
	 */
	protected BufferedImage leftStereographOSI, rightStereographOSI;

	/**
	 * A menu item for selecting one of the four possible view styles.  This is used in
	 * {@link #getViewCommands()}.
	 */
	protected ActionRadioGroup viewStyleCommands = new ActionRadioGroup(new String[] {
			I18n.tr("vmm.core3D.commands.Monocular"),
			I18n.tr("vmm.core3D.commands.RedGreenStereo"),
			I18n.tr("vmm.core3D.commands.Stereograph"),
			I18n.tr("vmm.core3D.commands.CrossEyeStereo")
	}, 0) {
		public void optionSelected(int selectedIndex) {
			setViewStyle(selectedIndex);
		}
	};

	/**
	 * A set of two radio items for selecting between orthograhic and prespective projection.
	 */
	 protected ActionRadioGroup projectionCommands = new ActionRadioGroup(new String[] {
			I18n.tr("vmm.core3D.commands.PerspectiveProjection"),
			I18n.tr("vmm.core3D.commands.OrthographicProjection")
	}, 0) {
		public void optionSelected(int selectedIndex) {
			setOrthographicProjection(selectedIndex == 1);
		}
	};
	
	/**
	 * A menu item that will show a Set Viewpoint dialog box.  This is created in
	 * {@link #getSettingsCommands()}
	 */
	protected AbstractActionVMM setViewpointAction;
	
	/**
	 * A menu item that will show a Set Eye Separation Multiplier dialog box.  This is created in
	 * {@link #getSettingsCommands()}
	 */
	protected AbstractActionVMM set3DViewOptionsAction;
	
	/**
	 * Returns the currently selected viewstyle.
	 * @see #setViewStyle(int)
	 */
	public int getViewStyle() {
		return viewStyle;
	}
	
	/**
	 * Set the current view styles.  This must be one of the four constants
	 * {@link #MONOCULAR_VIEW}, {@link #RED_GREEN_STEREO_VIEW}, {@link #CROSS_EYE_STEREO_VIEW},
	 * or {@link #STEREOGRAPH_VIEW}.
	 * @param style the new view style.  This is ignored if it is not one of the four legal options.
	 */
	public void setViewStyle(int style) {
		if (style < MONOCULAR_VIEW || style > CROSS_EYE_STEREO_VIEW)
			return;
		viewStyleCommands.setSelectedIndex(style);
		if (style == viewStyle)
			return;
		fullOSI = leftStereographOSI = rightStereographOSI = null;  // force creation of new offscreen image
		if (style == RED_GREEN_STEREO_VIEW) {
			Color c = getBackground();
			setBackground(Color.BLACK);
			savedBackground = c;  // must be done after setBackground, since that method sets savedBackground to null
			backgroundCommands.setEnabled(false);
		}
		else {
			if (viewStyle == RED_GREEN_STEREO_VIEW) {
				if ( savedBackground != null )
					setBackground(savedBackground);
			}
			backgroundCommands.setEnabled(true);
		}
		if (style == CROSS_EYE_STEREO_VIEW || style == STEREOGRAPH_VIEW) {  // Changing INTO sterograph view
			enterStereographView(viewStyle,style);
		}
		else if (viewStyle == CROSS_EYE_STEREO_VIEW || viewStyle == STEREOGRAPH_VIEW) {  // Changing OUT of stereograph view
			leaveStereographView();
		}
		viewStyle = style;
		if (transform3D != null) {
			if (viewStyle != RED_GREEN_STEREO_VIEW  || ! "yes".equals(Prefs.get("view3d.moveObjectsForwardInAnaglyph")))
				transform3D.setObjectDisplacementNormalToScreen(0);
			else {
				if (getViewStyle() == View3D.RED_GREEN_STEREO_VIEW && getTransform3D() != null) {
					double extent = Math.max(getTransform3D().getXmaxRequested() - getTransform3D().getXminRequested(),
							getTransform3D().getYmaxRequested() - getTransform3D().getYminRequested());
					getTransform3D().setObjectDisplacementNormalToScreen(extent/3);
				}
			}
		}
		forceRedraw();
	}
	
	private void enterStereographView(int oldViewStyle, int viewStyle) {
		int dotsPerInch = Toolkit.getDefaultToolkit().getScreenResolution();  // does not give correct answer on Mac ??-- gives 72
		if (dotsPerInch == 72 && Util.isMacOS())
			dotsPerInch = 100;
		if (oldViewStyle != CROSS_EYE_STEREO_VIEW && oldViewStyle != STEREOGRAPH_VIEW)
			savePreferredSizeForStereograph = getDisplay() == null ? null : new Dimension(getDisplay().getWidth(),getDisplay().getHeight());
		Dimension preferredSize = null;
//		String prefs;
//		if (viewStyle == CROSS_EYE_STEREO_VIEW)
//			prefs = Prefs.get("core3D.cross_eye_stereo.preferredSize");
//		else 
//			prefs = Prefs.get("core3D.parallel_stereo.preferredSize");
//		if (prefs != null) {
//			try {
//				String[] sizeStrings = Util.explode(prefs,",");
//				int[] sizeData = new int[10];
//				for (int i = 0; i < 10; i++) {
//					sizeData[i] = Integer.parseInt(sizeStrings[i]);
//					if (sizeData[i] < 50 || sizeData[i] > 2000)
//						throw new Exception();
//				}
//				leftEyeRect = new Rectangle(sizeData[0],sizeData[1],sizeData[2],sizeData[3]);
//				if (leftEyeRect.width <= 0 || leftEyeRect.height <= 0)
//					throw new Exception();
//				rightEyeRect = new Rectangle(sizeData[4],sizeData[5],sizeData[6],sizeData[7]);
//				if (rightEyeRect.width <= 0 || rightEyeRect.height <= 0)
//					throw new Exception();
//				preferredSize = new Dimension(sizeData[8],sizeData[9]);
//			}
//			catch (Exception e) {
//				preferredSize = null;  // if the data from Preferences is not of the correct form.
//			}
//		}
//		if (preferredSize == null) {
			if (viewStyle == CROSS_EYE_STEREO_VIEW) {
				leftEyeRect = new Rectangle(dotsPerInch-20,+5,7*dotsPerInch/2,7*dotsPerInch/2);
				rightEyeRect = new Rectangle(5*dotsPerInch-20,5,7*dotsPerInch/2,7*dotsPerInch/2);
				preferredSize = new Dimension(19*dotsPerInch/2-40, 12+7*dotsPerInch/2);
			}
			else {
				leftEyeRect = new Rectangle(dotsPerInch-20,5,(5*dotsPerInch)/2,(5*dotsPerInch)/2);
				rightEyeRect = new Rectangle(dotsPerInch+5+(5*dotsPerInch)/2,5,(5*dotsPerInch)/2,(5*dotsPerInch)/2);
				preferredSize = new Dimension(-14+7*dotsPerInch, 12+(5*dotsPerInch)/2);
			}
//		}
		stereographPreferredSize = preferredSize;
		stereographPreferredLeftEyeRect = (Rectangle)leftEyeRect.clone();
		stereographPreferredRightEyeRect = (Rectangle)rightEyeRect.clone();
		saveStereographDisplayWidth = preferredSize.width;
		saveStereographDisplayHeight = preferredSize.height;
		if (getDisplay() != null) {
			getDisplay().setPreferredSize(preferredSize);
			if (preferredSize.width > getDisplay().getWidth() || preferredSize.height > getDisplay().getHeight()) {
				double scale = Math.min((double)getDisplay().getWidth()/preferredSize.width, 
						(double)getDisplay().getHeight()/preferredSize.height);
				leftEyeRect.x = (int)(leftEyeRect.x * scale);
				rightEyeRect.x = (int)(rightEyeRect.x * scale);
				leftEyeRect.width = rightEyeRect.width = (int)(leftEyeRect.width * scale);
				leftEyeRect.height = rightEyeRect.height = (int)(leftEyeRect.height * scale);
			}
			int left = (getDisplay().getWidth() - (rightEyeRect.x + rightEyeRect.width - leftEyeRect.x))/2;
			int top = (getDisplay().getHeight() - leftEyeRect.height)/2;
			rightEyeRect.x = rightEyeRect.x + (left - leftEyeRect.x);
			leftEyeRect.x = left;
			rightEyeRect.y = leftEyeRect.y = top;
			saveStereographDisplayWidth = getDisplay().getWidth();
			saveStereographDisplayHeight = getDisplay().getHeight();
		}
	}
	
	private void adjustStereographToNewSize(int width, int height) { 
		leftEyeRect = (Rectangle)stereographPreferredLeftEyeRect.clone();
		rightEyeRect = (Rectangle)stereographPreferredRightEyeRect.clone();
		double scale = Math.min((double)width/stereographPreferredSize.width, 
				(double)height/stereographPreferredSize.height);
		leftEyeRect.x = (int)(leftEyeRect.x * scale);
		rightEyeRect.x = (int)(rightEyeRect.x * scale);
		leftEyeRect.width = rightEyeRect.width = (int)(leftEyeRect.width * scale);
		leftEyeRect.height = rightEyeRect.height = (int)(leftEyeRect.height * scale);
		int left = (width - (rightEyeRect.x + rightEyeRect.width - leftEyeRect.x))/2;
		int top = (height - leftEyeRect.height)/2;
		rightEyeRect.x = rightEyeRect.x + (left - leftEyeRect.x);
		leftEyeRect.x = left;
		rightEyeRect.y = leftEyeRect.y = top;
		saveStereographDisplayWidth = width;
		saveStereographDisplayHeight = height;
//		String prefs = "" + leftEyeRect.x + ',' +leftEyeRect.y + ',' +leftEyeRect.width + ',' +leftEyeRect.height + ',' +
//		               rightEyeRect.x + ',' +rightEyeRect.y + ',' +rightEyeRect.width + ',' +rightEyeRect.height + ',' +
//		               width + ',' + height;
//		if (viewStyle == CROSS_EYE_STEREO_VIEW)  {
//			Prefs.put("core3D.cross_eye_stereo.preferredSize",prefs);
//			Prefs.save("core3D.cross_eye_stereo.preferredSize");
//		}
//		else if (viewStyle == STEREOGRAPH_VIEW) {
//			Prefs.put("core3D.parallel_stereo.preferredSize",prefs);
//			Prefs.save("core3D.parallel_stereo.preferredSize");
//		}
	}
	
	private void leaveStereographView() {
		if (getDisplay() != null) {
			if (savePreferredSizeForStereograph == null)
				getDisplay().setPreferredSize(new Dimension(800,490));
			else
				getDisplay().setPreferredSize(savePreferredSizeForStereograph);
		}
	}
	
	/**
	 * This method moves the left- and right-eye views of the exhibit closer together or farther apart,
	 * within the limits of available space.  This method is called by {@link BasicMouseTask3D}, and
	 * will probably be used rarely if ever otherwise.
	 * @param offset The number of pixels to move the images.  A positive value moves them closer
	 * together.  A negative value moves them farther apart.  However, they can't be moved closer together
	 * than when they are touching or farther apart than the border of the window permits.
	 */
	public void moveStereographImages(int offset) {
		if (viewStyle != CROSS_EYE_STEREO_VIEW && viewStyle != STEREOGRAPH_VIEW)
			return;
		Display display = getDisplay();
		if (display == null)
			return;
		if (offset < 0) {
			if (leftEyeRect.x + offset < 0)
				offset = -leftEyeRect.x;
			if (offset >= 0)
				return;
		}
		else {
			if (leftEyeRect.x + leftEyeRect.width + offset + 1 > rightEyeRect.x - offset - 1)
				offset = (rightEyeRect.x - (leftEyeRect.x + leftEyeRect.width))/2 - 1;
			if (offset <= 0)
				return;
		}
		leftEyeRect.x += offset;
		rightEyeRect.x -= offset;
		forceRedraw();
//		String prefs = "" + leftEyeRect.x + ',' +leftEyeRect.y + ',' +leftEyeRect.width + ',' +leftEyeRect.height + ',' +
//                       rightEyeRect.x + ',' +rightEyeRect.y + ',' +rightEyeRect.width + ',' +rightEyeRect.height + ',' +
//                       display.getWidth() + ',' + display.getHeight();
//		if (viewStyle == CROSS_EYE_STEREO_VIEW)  {
//			Prefs.put("core3D.cross_eye_stereo.preferredSize",prefs);
//			Prefs.save("core3D.cross_eye_stereo.preferredSize");
//		}
//		else if (viewStyle == STEREOGRAPH_VIEW) {
//			Prefs.put("core3D.parallel_stereo.preferredSize",prefs);
//			Prefs.save("core3D.parallel_stereo.preferredSize");
//		}
	}
	
	
	/**
	 * This method is used in BasicMouseTask3D to determine the rectangle that containts
	 * the left-eye  image of a stereographic view.  It is also used in
	 * {@link vmm.ode.ODE_3D} for transforming screen points to object points.
	 */
	public Rectangle stereographLeftEyeRect() { // for use in BasicMouseTask3D
		return leftEyeRect;
	}
	
	/**
	 * This  method is used in BasicMouseTask3D to determine the rectangle that containts
	 * the left-eye  image of a stereographic view.  It is also used in
	 * {@link vmm.ode.ODE_3D} for transforming screen points to object points.
	 */
	public Rectangle stereographRightEyeRect() {
		return rightEyeRect;
	}
	
	//-------------------- Implementing hybrid 2D/3D View (July 2006) ---------------------------
	
	/**
	 * Gets the value of the enableThreeD property.
	 * @see #setEnableThreeD(boolean)
	 */
	public boolean getEnableThreeD() {
		return enableThreeD;
	}
	
	/**
	 * Sets the enableThreeD property.  When this property is set to false, three-D related items
	 * in the Settings menu are disabled.  When 3D is disabled, the view style is set to MONOCULAR, the Viewpoint is
	 * set to (1,0,0), and the projection is set to be orthographic, so any 3D drawing that is
	 * done while 3D is disabled will use a simple projection onto the yz-plane by discarding the
	 * x-coordinate.  (Although in general you should not be doing any 3D drawing while 3D is disabled.)
	 * When 3D is enabled after being disabled, the view style and viewpoint settings are restored to
	 * what they were when 3D was entered <b>unless</b> they have been changed in the meantime.
	 * <p>Note that if this view has an Axes decoration, they type of Axes decoration (Axes2D or Axes3D)
	 * is set to match the new state of the enableThreeD property.
	 * <p><b>WARNING:</b> This method does NOT do anything about mouse tasks, so you might want
	 * to change the mouse task in this View's display at the same time that you call this method.
	 */
	public void setEnableThreeD(boolean enable) {
		if (enable == enableThreeD)
			return;
		boolean showAxes = getShowAxes();
		if (showAxes)
			setShowAxes(false);
		enableThreeD = enable;
		if (showAxes)
			setShowAxes(true);
		if (enableThreeD) {
			saveTransform2DWhileThreeDEnabled = getTransform();
			if (saveTransform3DWhileThreeDDisabled != null) {
				setTransform(saveTransform3DWhileThreeDDisabled);
				saveTransform3DWhileThreeDDisabled = null;
			}
			else {
				setTransform(new Transform3D());
			}
			if (saveViewStyleWhile3DDisabled >= 0) {
				setViewStyle(saveViewStyleWhile3DDisabled);
				saveViewStyleWhile3DDisabled = -1;
			}
		}
		else {
			saveTransform3DWhileThreeDDisabled = getTransform();
			if (saveTransform2DWhileThreeDEnabled != null) {
				setTransform(saveTransform2DWhileThreeDEnabled);
				saveTransform2DWhileThreeDEnabled = null;
			}
			else {
				Transform3D tr = new Transform3D();
				if (getTransform() != null)  // copy only limits, not viewpoint
					tr.setLimits(getTransform().getXmin(),getTransform().getXmax(),getTransform().getYmin(),getTransform().getYmax());
				setTransform(tr);
			}
			saveViewStyleWhile3DDisabled = getViewStyle();
			setViewStyle(MONOCULAR_VIEW);
			setViewPoint( new Vector3D(1,0,0));
			setOrthographicProjection(true);
		}
		viewStyleCommands.setEnabled(enableThreeD);
		if (setViewpointAction != null)
			setViewpointAction.setEnabled(enableThreeD);
		projectionCommands.setEnabled(enableThreeD);
		forceRedraw();  // (probably many times redundant!)
	}
	
	/**
	 * Set the x and y ranges to be used when 3D has been disabled by {@link #setEnableThreeD(boolean)}.
	 * This method can be called at any time, even while 3D is enabled -- if it is, the specified
	 * ranges only come in to effect the next time 3D is disabled.
	 */
	public void setWindowForUseWhileThreeDDisabled(double xmin, double xmax, double ymin, double ymax) {
		if (!enableThreeD)
			setWindow(xmin,xmax,ymin,ymax);
		else {
			if (saveTransform2DWhileThreeDEnabled == null)
				saveTransform2DWhileThreeDEnabled = new Transform3D();
			saveTransform2DWhileThreeDEnabled.setLimits(xmin,xmax,ymin,ymax);
		}
	}
	
	/**
	 * If 3D is enabled, this returns the saved transform that is used when 3D is disabled;
	 * if 3D is disabled, this returns the saved transform that is used when 3D is enabled.
	 * This method was introduced to be used by {@link SaveAndRestore} and will probably not
	 * be used otehrwise.
	 */
	protected Transform getSavedAuxiliaryTransformForEnableThreeD() {
		if (enableThreeD)
			return saveTransform2DWhileThreeDEnabled;
		else
			return saveTransform3DWhileThreeDDisabled;
	}
	
	/**
	 * If 3D is enabled this sets the saved transform that is used when 3D is disabled;
	 * if 3D is disabled, this sets the saved transform that is used when 3D is enabled.
	 * This method was introduced to be used by {@link SaveAndRestore} and will probably not be
	 * used otherwise.
	 */
	protected void setSavedAuxiliaryTransformForEnableThreeD(Transform transform) {
		if (enableThreeD)
			saveTransform2DWhileThreeDEnabled = transform;
		else
			saveTransform3DWhileThreeDDisabled = transform;
	}
		
	//==========================================
	
	/**
	 * Returns true if this view is currently set to use an orthographic projection, and false if it is set to
	 * use a perspective projection.
	 */
	public boolean getOrthographicProjection() {
		if (transform3D == null)
			return true;
		else
			return transform3D.getOrthographicProjection();
	}
	
	/**
	 * Set to true to use an orthographic projection, and to false to use a perspective projection.
	 * The default is to use a perspective projection.
	 */
	public void setOrthographicProjection(boolean orthographic) {
		if (transform3D != null)
			transform3D.setOrthographicProjection(orthographic);
	}
	
	/**
	 * Returns the current viewpoint for this View's transform.  (In the unexpected case that this
	 * view is not using a 3D transform, returns null.)
	 * @see #setViewPoint(Vector3D)
	 */
	public Vector3D getViewPoint() {
		if (transform3D == null)
			return null;
		else
			return transform3D.getViewPoint();
	}
	
	/**
	 * Sets the viewpoint for this View's transform. (Assuming that this view is using a 3D transform --
	 * if not, nothing is done.)  Note that calling this method will also adjust the imagePlaneYDirection
	 * by projecting the current imagePlaneYDirection onto the new view plane.
	 * Note that when an Exhbit is installed, the Exhibit's default Transform replaces whatever
	 * transform already exists, so there is not much use calling this when no exhibit is installed.
	 * @see #setViewUp(Vector3D)
	 * @see Transform3D#setViewPoint(Vector3D)
	 */
	public void setViewPoint(Vector3D viewpoint) {
		if (transform3D != null && !transform3D.getViewPoint().equals(viewpoint))
			transform3D.setViewPoint(viewpoint);
	}
	
	/**
	 * Returns the current imagePlaneYDirection for this View's transform.  (In the unexpected case that this
	 * view is not using a 3D transform, returns null.)
	 * @see #setViewUp(Vector3D)
	 */
	public Vector3D getViewUp() {
		if (transform3D == null)
			return null;
		else
			return transform3D.getImagePlaneYDirection();
	}
	
	/**
	 * Sets the view up vector for this View's transform. (Assuming that this view is using a 3D transform --
	 * if not, nothing is done.)  Note that the specified viewUp vector will have to be projected
	 * onto the image plane to give the actual imagePlaneYDirection.
	 * @see Transform3D#setImagePlaneYDirection(Vector3D)
	 */
	public void setViewUp(Vector3D viewUp) {
		if (transform3D != null)
			transform3D.setImagePlaneYDirection(viewUp);
	}

	
	// --------------------- override some methods from the View class for 3D -----------------
	
	/**
	 * Set the exhibit shown in this view.  It is overridden in this class to set 
	 * {@link #transform3D} and to manage stereographic viewing if necessary.
	 */
	public void setExhibit(Exhibit exhibit) {
		if (exhibit == getExhibit())
			return;
		if (!enableThreeD)
			saveTransform2DWhileThreeDEnabled = getTransform();
		super.setExhibit(exhibit);
		if (getTransform() != null && getTransform() instanceof Transform3D)
			transform3D = (Transform3D)getTransform();
		else
			transform3D = null;
		savedBackground = null;
		if (exhibit != null && (viewStyle == STEREOGRAPH_VIEW || viewStyle == CROSS_EYE_STEREO_VIEW))
			enterStereographView(MONOCULAR_VIEW,viewStyle);
		if (viewStyle == RED_GREEN_STEREO_VIEW && transform3D != null && "yes".equals(Prefs.get("view3d.moveObjectsForwardInAnaglyph"))) {
			double extent = Math.max(transform3D.getXmaxRequested() - transform3D.getXminRequested(),
					transform3D.getYmaxRequested() - transform3D.getYminRequested());
			transform3D.setObjectDisplacementNormalToScreen(extent/3);
		}
		if (!enableThreeD) {
			saveTransform3DWhileThreeDDisabled = getTransform();
			setTransform(saveTransform2DWhileThreeDEnabled);
			saveTransform2DWhileThreeDEnabled = null;
		}
	}
	
	public void setTransform(Transform transform) {
		super.setTransform(transform);
		if (transform != null && transform instanceof Transform3D) {
			transform3D = (Transform3D)transform;
			projectionCommands.setSelectedIndex( transform3D.getOrthographicProjection() ? 1 : 0);
			if (viewStyle == RED_GREEN_STEREO_VIEW && "yes".equals(Prefs.get("view3d.moveObjectsForwardInAnaglyph"))) {
				double extent = Math.max(transform3D.getXmaxRequested() - transform3D.getXminRequested(),
						transform3D.getYmaxRequested() - transform3D.getYminRequested());
				transform3D.setObjectDisplacementNormalToScreen(extent/3);
			}
		}
		else
			transform3D = null;
	}
	
	/**
	 * Set the display where this view draws its exhibit.  This is overridden in this class
	 * to put the display into stereograpic view mode, if the view is currently set to
	 * use sterographic viewing.
	 */
	public void setDisplay(Display display) {
		super.setDisplay(display);
		if (getExhibit() != null && (viewStyle == STEREOGRAPH_VIEW || viewStyle == CROSS_EYE_STEREO_VIEW))
			enterStereographView(MONOCULAR_VIEW,viewStyle);
		else {
			String anaglyphViewPref = Prefs.get("view3d.initialAnaglyphMode", "default");
			if (anaglyphViewPref.equalsIgnoreCase("always")) {
				if (enableThreeD)
					setViewStyle(RED_GREEN_STEREO_VIEW);
				else
					saveViewStyleWhile3DDisabled = RED_GREEN_STEREO_VIEW;
			}
			else if (anaglyphViewPref.equalsIgnoreCase("never")) {
				if (enableThreeD)
					setViewStyle(MONOCULAR_VIEW);
				else
					saveViewStyleWhile3DDisabled = MONOCULAR_VIEW;
			}
		}
	}

	/**
	 * Called when a view is removed from its display.  In this case, it restores the viewStyle
	 * to MONOCULAR_VIEW to avoid leaving the display set up for stereo viewing.
	 */
	public void finish() {  // called when a view is removed from the display.
		setViewStyle(MONOCULAR_VIEW);
	}
	
	/**
	 * Set this View to view the same exhibit as another specififed view.
	 * This is overridden here to copy the 3D transform from the specified
	 * view (if it has one) and to copy its viewStyle if it is a View3D and
	 * to properly handle the enableThreeD propety.
	 * Since a 3D View cannot make effective use of a 2D transform, the
	 * sharedTransform parameter is ignored if the transform in the given
	 * view is not a 3D transform.
	 */
	public void takeExhibit(View view, boolean shareTransform) {
		savedBackground = null;
		if ( ! shareTransform || view.getTransform() == null || ! (view.getTransform() instanceof Transform3D)) {
			super.takeExhibit(view,false); // Can't share a 2D transform.
			return;
		}
		if (view instanceof View3D)
			setEnableThreeD(((View3D)view).getEnableThreeD());
		super.takeExhibit(view, true);
		transform3D = (Transform3D)getTransform();
		if (view instanceof View3D) {  // Also copy extra transform for implementing enableThreeD proeprty.
			View3D v3 = (View3D)view;
			if (enableThreeD) {  // Alread set this to match v3.enableThreeD
				saveTransform2DWhileThreeDEnabled = v3.saveTransform2DWhileThreeDEnabled;
				setViewStyle(v3.getViewStyle());
			}
			else {
				saveTransform3DWhileThreeDDisabled = v3.saveTransform3DWhileThreeDDisabled;
				saveViewStyleWhile3DDisabled = v3.saveViewStyleWhile3DDisabled;
			}
		}
	}
	
	/**
	 * If the transform associated with this view is a {@link Transform3D}, then that transform is returned.
	 * Otherwise, the return value is null.
	 */
	public Transform3D getTransform3D() {
		if (getTransform() instanceof Transform3D)
			return (Transform3D)getTransform();
		else
			return null;
	}
	
	/**
	 * Returns an Axes decoration that is appropriate for this view.  If
	 * 3D is enabled, returns an object of type {@link Axes3D} and returns
	 * an {@link Axes2D} if 3D is disabled.
	 * @see #setEnableThreeD(boolean)
	 */
	protected Axes2D createAxes() {
		if (enableThreeD)
			return new Axes3D();
		else
			return new Axes2D();
	}
	
	/**
	 * Returns a MouseTase of type {@link BasicMouseTask3D}, which is approriate for most 3D views.
	 */
	public MouseTask getDefaultMouseTask() {
		return new BasicMouseTask3D();
	} 
	
	public void setBackground(Color c) {
		super.setBackground(c);
		savedBackground = null;
	}
	
	/**
	 * React to a change by rebuilding the offscreen bit map.  This overrides the method in
	 * the View class for the following reason:  If the state change comes from a Transform3D,
	 * it might be a change in the "orthographicProjection" property of a Transform3D that
	 * is shared with another view, so the setting of the projectionCommands is changed to 
	 * match the setting in the Transform3D.
	 */
	public void stateChanged(ChangeEvent evt) {
		super.stateChanged(evt);
		if (evt.getSource() instanceof Transform3D) {
			boolean isOrtho = ((Transform3D)evt.getSource()).getOrthographicProjection();
			projectionCommands.setSelectedIndex( isOrtho? 1 : 0);
		}
	}

	// ------------------------- View and Setting Commands -----------------------------------------------------------------------
	
	/**
	 * Returns a list of view commands appropriate for a View3D.  This method adds commands for selecting
	 * between orthographic and perspective projection and for selecting the viewStyle to the list
	 * of commands obtained from the superclass.
	 */
	public ActionList getViewCommands() {
		ActionList commands = super.getViewCommands();
		commands.add(null);         // Add projection selection commands
		commands.add(projectionCommands);
		projectionCommands.setEnabled(enableThreeD);
		commands.add(null);
		commands.add(viewStyleCommands);
		return commands;
	}
	
	/**
	 * Adds a "Set Viewpoint" command to any settings commands created by the superclass.
	 */
	public ActionList getSettingsCommands() {
		ActionList commands = super.getSettingsCommands();
		setViewpointAction = new AbstractActionVMM(I18n.tr("vmm.core3D.commands.SetViewpoint")) { 
			public void actionPerformed(ActionEvent evt) {
				if (getDisplay() != null)
					getDisplay().stopAnimation();
				SetViewpointDialog.showDialog(View3D.this);
			}
		};
		set3DViewOptionsAction = new AbstractActionVMM(I18n.tr("vmm.core3D.commands.Set3DViewOptions") + "...") {
			public void actionPerformed(ActionEvent evt) {
				if (getDisplay() != null)
					getDisplay().stopAnimation();
				Set3DViewOptionsDialog.showDialog(View3D.this);
			}
		};
		commands.add(set3DViewOptionsAction);
		commands.add(null);
		setViewpointAction.setEnabled(enableThreeD);
		commands.add(setViewpointAction);
		return commands;
	}

	
	// -------------------- methods used in rendering, overridden in this class to --------------------------------
	// -------------------- modify their behavior in the case of stereo viewing. -----------------------------------
	
	
	/**
	 * This is called by <code>render</code> to check whether a new offscreen image needs to be
	 * created, given the specified width and height of the drawing area.
	 */
	protected boolean needsNewOSI(int width, int height) {
		if (viewStyle == MONOCULAR_VIEW || viewStyle == RED_GREEN_STEREO_VIEW)
			return super.needsNewOSI(width,height);
		else
			return leftStereographOSI == null || width != saveStereographDisplayWidth || height != saveStereographDisplayHeight;
	}
	
	/**
	 * This is called by <code>render</code> to copy the offscreen image to the screen.
	 * The width and height of the drawing area are given as parameters, since they
	 * are needed in the case of stereograph views.
	 */
	protected void putOSI(Graphics2D g, int width, int height) {
		if (viewStyle == MONOCULAR_VIEW || viewStyle == RED_GREEN_STEREO_VIEW)
			g.drawImage(fullOSI,0,0,null);
		else {
			Color saveColor = g.getColor();
			g.setColor(Color.WHITE);
			g.fillRect(0,0,width,height);
			g.setColor(Color.GRAY);
			g.drawRect(leftEyeRect.x-1, leftEyeRect.y-1, leftEyeRect.width+1, leftEyeRect.width+1);
			g.drawRect(rightEyeRect.x-1, rightEyeRect.y-1, rightEyeRect.width+1, rightEyeRect.width+1);
			g.drawImage(leftStereographOSI,leftEyeRect.x,leftEyeRect.y,null);
			g.drawImage(rightStereographOSI,rightEyeRect.x,rightEyeRect.y,null);
			g.setColor(saveColor);
		}
	}
	
	/**
	 * Create an offscreen image for drawing.  This is overridden here to handle the left- and right-eye views that are
	 * needed for anaglyph stereo and stereograph viewing.  It is not meant to be called directly.
	 */
	protected void createOSI(int width, int height) {
		fullOSI = leftStereographOSI = rightStereographOSI = null;
		if (viewStyle == MONOCULAR_VIEW)
			super.createOSI(width,height);
		else if (viewStyle == RED_GREEN_STEREO_VIEW) {
			if (stereoComposite == null)
				stereoComposite = new StereoComposite();
			stereoComposite.setSize(width,height);
			fullOSI = stereoComposite.getImage();
		}
		else if (viewStyle == STEREOGRAPH_VIEW || viewStyle == CROSS_EYE_STEREO_VIEW) {
			if (width != saveStereographDisplayWidth || height != saveStereographDisplayHeight)
				adjustStereographToNewSize(width,height);
			leftStereographOSI = new BufferedImage(leftEyeRect.width,leftEyeRect.height,offscreenImageType);
			rightStereographOSI = new BufferedImage(leftEyeRect.width,leftEyeRect.height,offscreenImageType);
			fullOSI = leftStereographOSI;  //so it won't be null
		}
	}
	
	/**
	 * Fills the offscreen image with the background color of this View.  This is overidden here
	 * to handle the left- and right-eye views in the case of anaglyph stereo and stereograph viewing.
	 */
	protected void clearOSI() {
		if (viewStyle == MONOCULAR_VIEW)
			super.clearOSI();
		else if (viewStyle == RED_GREEN_STEREO_VIEW) {
			Graphics g = stereoComposite.getLeftEyeGraphics();
			if (g != null) {
				g.setColor(Color.black);
				g.fillRect(0,0,stereoComposite.getWidth(),stereoComposite.getHeight());
				g.setColor(Color.white);
			}
			g.dispose();
			g = stereoComposite.getRightEyeGraphics();
			if (g != null) {
				g.setColor(Color.black);
				g.fillRect(0,0,stereoComposite.getWidth(),stereoComposite.getHeight());
				g.setColor(Color.white);
			}
			g.dispose();
		}
		else {
			Graphics g = leftStereographOSI.getGraphics();
			g.setColor(getBackground());
			g.fillRect(0,0,leftEyeRect.width,leftEyeRect.height);
			g.dispose();
			g = rightStereographOSI.getGraphics();
			g.setColor(getBackground());
			g.fillRect(0,0,leftEyeRect.width,leftEyeRect.height);
			g.dispose();
		}
	}
	
	/**
	 * This is called by methods in the View class to prepare the off-screen image for drawing.  It is overridden here
	 * to handle th special setup needed for anaglyph stereo and stereograph viewing.  It is not meant to be called directly.
	 */
	protected Graphics2D prepareOSIForDrawing() {
		viewDirection = transform3D.getViewDirection();
		viewDirection.negate();  // since the view direction returned above points from the viewpoint towards the origen, and I want the reverse for clipping
		clipZ = transform3D.getFocalLength() - transform3D.getClipDistance();
		eyeSeparationMultiplier = Prefs.getDouble("eyeSeparationMultiplier", 1);
		if (viewStyle == MONOCULAR_VIEW)
			return super.prepareOSIForDrawing();
		if (viewStyle == RED_GREEN_STEREO_VIEW) {
			Graphics2D g = (Graphics2D)stereoComposite.getLeftEyeGraphics().create();
			if (g != null) {
				g.setColor(Color.white);
				g.setBackground(Color.black);
				if (getAntialiased())
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				getTransform().setUpDrawInfo(g,0,0,stereoComposite.getWidth(),stereoComposite.getHeight(),getPreserveAspect(),getApplyGraphics2DTransform());
				leftEyeGraphics = g;
				leftEyeUntransformedGraphics = getTransform().getUntransformedGraphics();
			}
			g = (Graphics2D)stereoComposite.getRightEyeGraphics().create();
			if (g != null) {
				g.setColor(Color.white);
				g.setBackground(Color.black);
				if (getAntialiased())
					g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				getTransform().setUpDrawInfo(g,0,0,stereoComposite.getWidth(),stereoComposite.getHeight(),getPreserveAspect(),getApplyGraphics2DTransform());
				rightEyeGraphics = g;
				rightEyeUntransformedGraphics = getTransform().getUntransformedGraphics();
			}
			g = (Graphics2D)fullOSI.getGraphics();
			getTransform().setUpDrawInfo(g,0,0,stereoComposite.getWidth(),stereoComposite.getHeight(),getPreserveAspect(),getApplyGraphics2DTransform());
			currentGraphics = g;
			return g;
		}
		else {
			Graphics2D g = (Graphics2D)rightStereographOSI.getGraphics();
			g.setColor(getForeground());
			g.setBackground(getBackground());
			getTransform().setUpDrawInfo(g,0,0,leftEyeRect.width,leftEyeRect.height,getPreserveAspect(),getApplyGraphics2DTransform());
			if (getAntialiased())
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			rightEyeGraphics = g;
			rightEyeUntransformedGraphics = getTransform().getUntransformedGraphics();
			g = (Graphics2D)leftStereographOSI.getGraphics();
			if (getAntialiased())
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(getForeground());
			g.setBackground(getBackground());
			getTransform().setUpDrawInfo(g,0,0,leftEyeRect.width,leftEyeRect.height,getPreserveAspect(),getApplyGraphics2DTransform());
			leftEyeGraphics = g;
			leftEyeUntransformedGraphics = getTransform().getUntransformedGraphics();
			currentGraphics = g;
			return g;
		}
	}
	
	/**
	 * This is called by methods in the View class to clean up after drawing to the off-screen image.  It is overridden
	 * here to do the cleanup necessary for anaglyph stereo viewing.  It is not meant to be called directly.
	 */
	protected void finishOSIDraw() { 
		if (viewStyle == MONOCULAR_VIEW)
			super.finishOSIDraw();
		else {
			leftEyeGraphics.dispose();
			rightEyeGraphics.dispose();
			if (leftEyeUntransformedGraphics != null)
				leftEyeUntransformedGraphics.dispose();
			if (rightEyeUntransformedGraphics != null)
				rightEyeUntransformedGraphics.dispose();
			if (viewStyle == RED_GREEN_STEREO_VIEW) 
				stereoComposite.compose();
		}
	}
	
	
	//----------------------- XML routines -------------------------------------------
	
	/**
	 * Overridden to add extra transform info.
	 */
	public void addExtraXML(Document containingDocument, Element viewElement) {
		super.addExtraXML(containingDocument, viewElement);
		Transform savedTransform = getSavedAuxiliaryTransformForEnableThreeD();
		if (savedTransform != null) {
			Element transformElement = SaveAndRestore.makeTransformElement("savedtransform",containingDocument, savedTransform);
			viewElement.appendChild(transformElement);
		}
	}

	/**
	 * Overridden to read back the extra transform info.
	 */
	public void readExtraXML(Element viewInfo) throws IOException {
		super.readExtraXML(viewInfo);
		Element savedtransformElement = SaveAndRestore.getChildElement(viewInfo,"savedtransform");
		if (savedtransformElement != null) {
			Transform transform = SaveAndRestore.buildTransformFromElement(savedtransformElement);
			setSavedAuxiliaryTransformForEnableThreeD(transform);
		}
	}
	
	//----------------------- 3D drawing routines -------------------------------------
	
	/**
	 * This method can be called during a drawing operation, such as in {@link Exhibit3D#doDraw3D(Graphics2D, View3D, Transform3D)},
	 * to draw a single pixel.  This method is designed to work correctly in stereo views as well as in a standard monocular
	 * view; for stereo viewing, the pixel is drawn in both the left- and the right-eye view.
	 * @param v a non-null vector giving the coordinates of the pixel to be drawn, in world coordinates.
	 * @see #drawPixels(Vector3D[])
	 */
	public void drawPixel(Vector3D v) {
		Point2D pt = new Point2D.Double();
		if (clip(v))
			return;
		if (viewStyle == MONOCULAR_VIEW) {
			transform3D.objectToXYWindowCoords(v,pt);
			transform3D.windowToViewport(pt);
			drawPixelDirect(null,(int)pt.getX(),(int)pt.getY());
		}
		else {
			setUpForLeftEye();
			transform3D.objectToXYWindowCoords(v,pt);
			transform3D.windowToViewport(pt);
			drawPixelDirect(null,(int)pt.getX(),(int)pt.getY());
			setUpForRightEye();
			transform3D.objectToXYWindowCoords(v,pt);
			transform3D.windowToViewport(pt);
			drawPixelDirect(null,(int)pt.getX(),(int)pt.getY());
			finishStereoView();
		}
	}
	
	/**
	 * Draws a dot of specified diameter centered at a specified 3D point.
	 * This is done by calling the Graphics2D fill() command for an
	 * appropriate Ellipse2D.  The diameter is specified in <b>pixels</b>.
	 * Note that if the preserveAspectRatio is off for this View, then
	 * the dot will can be an oval rather than a circle.
	 */
	public void drawDot(Vector3D pt, double diameter) {
		double h = diameter*transform3D.getPixelWidth();
		double w = diameter*transform3D.getPixelHeight();
		Point2D pt2D = new Point2D.Double();
		if (viewStyle == MONOCULAR_VIEW) {
			transform3D.objectToDrawingCoords(pt, pt2D);
			currentGraphics.fill(new Ellipse2D.Double(pt2D.getX()-h/2,pt2D.getY()-w/2,h,w));
		}
		else {
			setUpForLeftEye();
			transform3D.objectToDrawingCoords(pt, pt2D);
			currentGraphics.fill(new Ellipse2D.Double(pt2D.getX()-h/2,pt2D.getY()-w/2,h,w));
			setUpForRightEye();
			transform3D.objectToDrawingCoords(pt, pt2D);
			currentGraphics.fill(new Ellipse2D.Double(pt2D.getX()-h/2,pt2D.getY()-w/2,h,w));
			finishStereoView();
		}
	}
	
	/**
	 * This method can be called during a drawing operation, such as in {@link Exhibit3D#doDraw3D(Graphics2D, View3D, Transform3D)},
	 * to draw a list of pixels.  This method is designed to work correctly in stereo views as well as in a standard monocular
	 * view; for stereo viewing, each pixel is drawn in both the left- and the right-eye view.  This method is more efficient
	 * than drawing each pixel individually when drawing in stereo.
	 * @param vlist a non-null array of vectors, where each vector contains the coordinates of the pixel to be drawn, in world coordinates.
	 * The individual vectors in the list can be null; null values in the array are ignored.
	 */
	public void drawPixels(Vector3D[] vlist) {
		Point2D pt = new Point2D.Double();
		if (viewStyle == MONOCULAR_VIEW) {
			for (int i = 0; i < vlist.length; i++)
				if (vlist[i] != null && !clip(vlist[i])) {
					transform3D.objectToXYWindowCoords(vlist[i],pt);
					transform3D.windowToViewport(pt);
					drawPixelDirect(null,(int)pt.getX(),(int)pt.getY());
				}
		}
		else {
			setUpForLeftEye();
			for (int i = 0; i < vlist.length; i++)
				if (vlist[i] != null && !clip(vlist[i])) {
					transform3D.objectToXYWindowCoords(vlist[i],pt);
					transform3D.windowToViewport(pt);
					drawPixelDirect(null,(int)pt.getX(),(int)pt.getY());
				}
			setUpForRightEye();
			for (int i = 0; i < vlist.length; i++)
				if (vlist[i] != null && !clip(vlist[i])) {
					transform3D.objectToXYWindowCoords(vlist[i],pt);
					transform3D.windowToViewport(pt);
					drawPixelDirect(null,(int)pt.getX(),(int)pt.getY());
				}
			finishStereoView();
		}
	}
	
	/**
	 * This method can be called during a drawing operation, such as in {@link Exhibit3D#doDraw3D(Graphics2D, View3D, Transform3D)},
	 * to draw a line segment.  This method is designed to work correctly in stereo views as well as in a standard monocular
	 * view; for stereo viewing, the line segment is drawn in both the left- and the right-eye view.
	 * @param v1 a non-null vector giving the coordinates of one endpoint of the line segment, in world coordinates.
	 * @param v2 a non-null vector giving the coordinates of a second endpoint of the line segment, in world coordinates.
	 */
	public void drawLine(Vector3D v1, Vector3D v2) {
		Point2D p1, p2;
		if (clip(v1) || clip(v2))
			return;
		if (viewStyle == MONOCULAR_VIEW) {
			p1 = transform3D.objectToDrawingCoords(v1);
			p2 = transform3D.objectToDrawingCoords(v2);
			currentGraphics.draw(new Line2D.Float(p1,p2));
		}
		else {
			setUpForLeftEye();
			p1 = transform3D.objectToDrawingCoords(v1);
			p2 = transform3D.objectToDrawingCoords(v2);
			currentGraphics.draw(new Line2D.Float(p1,p2));
			setUpForRightEye();
			p1 = transform3D.objectToDrawingCoords(v1);
			p2 = transform3D.objectToDrawingCoords(v2);
			currentGraphics.draw(new Line2D.Float(p1,p2));
			finishStereoView();
		}
	}
	
	/**
	 * This method can be called during a drawing operation, such as in {@link Exhibit3D#doDraw3D(Graphics2D, View3D, Transform3D)},
	 * to draw a string.  Only the basepoint of the string undergoes transformation and projection.  The string itself
	 * is always drawn flat on the screen, as if it is located in the view plane.
	 * This method is designed to work correctly in stereo views as well as in a standard monocular
	 * view; for stereo viewing, the string  is drawn in both the left- and the right-eye view.
	 * @param str the non-null string that is to be drawn.
	 * @param basepoint a non-null vector giving the coordinates of the basepoing of the string to be drawn, in world coordinates.
	 */
	public void drawString(String str, Vector3D basepoint) {
		if (clip(basepoint))
			return;
		if (viewStyle == MONOCULAR_VIEW) {
			Point2D p = transform3D.objectToXYWindowCoords(basepoint);
			super.drawString(str,p.getX(),p.getY());  // NOTE: Don't call drawString(str,Point2D) -- leads to infinite recursion
		}
		else {
			setUpForLeftEye();
			Point2D p = transform3D.objectToXYWindowCoords(basepoint);
			super.drawString(str,p.getX(),p.getY());
			setUpForRightEye();
			p = transform3D.objectToXYWindowCoords(basepoint);
			super.drawString(str,p.getX(),p.getY());
			finishStereoView();
		}
	}
	
	/**
	 * Draws a curve in three-space.  This is a convenience method that just calls <code>drawCurve(points,points.length)</code>.
	 * @see #drawCurve(Vector3D[], int)
	 */
	public void drawCurve(Vector3D[] points) {
		if (points != null)
			drawCurve(points,points.length);
	}
	
	
	/**
	 * Draws a curve in three-space.  The curve is drawn by connecting points in a given array of 3D points with
	 * line segments.  This method is designed to work correctly in stereo views as well as in a standard monocular
	 * view; for stereo viewing, the curve  is drawn in both the left- and the right-eye view.
	 * @param points  A non-null array containing the points on the curve.  Null values are allowed in this array.
	 * A null value is treated as a missing point on the curve -- no connecting line segment is drawn from the missing
	 * point to the points on either side.  This makes it possible for a single array to define multiple disconnected
	 * curve segments.
	 * @param pointCount The number of points on the curve.  Only the points in positions 0 through pointCount-1 in
	 * the array are used when drawing the curve.
	 */
	public void drawCurve(Vector3D[] points, int pointCount) {
		drawCurve(points,0,pointCount-1);
	}
	
	/**
	 * Draws a curve in three-space.  The curve is drawn by connecting points in a given array of 3D points with
	 * line segments.  This method is designed to work correctly in stereo views as well as in a standard monocular
	 * view; for stereo viewing, the curve  is drawn in both the left- and the right-eye view.  This version of
	 * drawCurve makes it possible to draw any contiguous sequence of points on the curve.
	 * @param points  A non-null array containing the points on the curve.  Null values are allowed in this array.
	 * A null value is treated as a missing point on the curve -- no connecting line segment is drawn from the missing
	 * point to the points on either side.  This makes it possible for a single array to define multiple disconnected
	 * curve segments.
	 * @param startIndex The number of points in the array that should be used for the curve.  A curve is drawn
	 * though points[startIndex], point[startIndex+1], ..., points[endIndex].  The value of startIndex
	 * is clamped to lie in the range 0 to points.length-1.
	 * @param endIndex The number of points in the array that should be used for the curve.  A curve is drawn
	 * though points[startIndex], point[startIndex+1], ..., points[endIndex].  The value of endIndex
	 * is clamped to lie in the range 0 to points.length-1.  If startIndex is less than or equal to endIndex,
	 * nothing is drawn.
	 */
	public void drawCurve(Vector3D[] points, int startIndex, int endIndex) {
		if (points == null)
			return;
		if (startIndex >= points.length)
			startIndex = points.length - 1;
		if (startIndex < 0)
			startIndex = 0;
		if (endIndex >= points.length)
			endIndex = points.length - 1;
		if (endIndex < 0)
			endIndex = 0;
		if (endIndex <= startIndex)
			return;
		Point2D[] projectedPoints = new Point2D[endIndex - startIndex + 1];
		if (viewStyle == MONOCULAR_VIEW) {
			for (int i = 0; i < projectedPoints.length; i++){
				if (points[i - startIndex] == null || clip(points[i - startIndex]))
					projectedPoints[i] = null;
				else
					projectedPoints[i] = transform3D.objectToXYWindowCoords(points[i - startIndex]);
			}
			super.drawCurve(projectedPoints);
		}
		else {
			setUpForLeftEye();
			for (int i = 0; i < projectedPoints.length; i++){
				if (points[i - startIndex] == null || clip(points[i - startIndex]))
					projectedPoints[i] = null;
				else
					projectedPoints[i] = transform3D.objectToXYWindowCoords(points[i - startIndex]);
			}
			super.drawCurve(projectedPoints);
			setUpForRightEye();
			for (int i = 0; i < projectedPoints.length; i++){
				if (points[i - startIndex] == null || clip(points[i - startIndex]))
					projectedPoints[i] = null;
				else
					projectedPoints[i] = transform3D.objectToXYWindowCoords(points[i - startIndex]);
			}
			super.drawCurve(projectedPoints);
			finishStereoView();
		}
	}
	
	/**
	 * A convenience method that simply calls <code>drawCollardCurve(points,0,points.length-1,reversed)</code>.
	 * @see #drawCollaredCurve(Vector3D[], int, int, boolean)
	 */
	public void drawCollaredCurve(Vector3D[] points, boolean reversed) {
		if (points != null)
			drawCollaredCurve(points,0,points.length-1,reversed);
	}
	
	/**
	 * A convenience method that simply calls <code>drawCollardCurve(points,0,pointCount-1,reversed)</code>.
	 * @see #drawCollaredCurve(Vector3D[], int, int, boolean)
	 */
	public void drawCollaredCurve(Vector3D[] points, int pointCount, boolean reversed) {
		if (points != null)
			drawCollaredCurve(points,0,pointCount-1,reversed);
	}
	
	/**
	 * Draws a curve in three-space, possibley using "collars" to produce a more three-dimensional effect.
	 * The collars can be used in two ways, either reversed or not reversed.  If reverse is false, then
	 * the curve is drawn in monocular views by connecting points in a given array of 3D points with
	 * line segments, except that where one part of the curve passes over another, the back segment is "broken",
	 * crossing-diagram style, to show which curve is in front.  (The "break" is actually the result of drawing a
	 * wide collar around the curve in the background color.)  However, this crossing diagram style is only
	 * used in a monocular view, not in stereo views; for stereo viewing, a solid curve is drawn using
	 * the {@link #drawCurve(Vector3D[], int)} method.  
	 * <p>If "reversed" is true, then the curve is drawn with reversed collars in both monocular and stereo views.
	 * For a reversed collar, the curve itself is drawn in the background color, and the collar is drawn in the
	 * foreground color.  This gives a nice 3D effect.
	 * @param points  A non-null array containing the points on the curve.  Null values are allowed in this array.
	 * A null value is treated as a missing point on the curve -- no connecting line segment is drawn from the missing
	 * point to the points on either side.  This makes it possible for a single array to define multiple disconnected
	 * curve segments.
	 * @param startIndex The number of points in the array that should be used for the curve.  A curve is drawn
	 * though points[startIndex], point[startIndex+1], ..., points[endIndex].  The value of startIndex
	 * is clamped to lie in the range 0 to points.length-1.
	 * @param endIndex The number of points in the array that should be used for the curve.  A curve is drawn
	 * though points[startIndex], point[startIndex+1], ..., points[endIndex].  The value of endIndex
	 * is clamped to lie in the range 0 to points.length-1.  If startIndex is less than or equal to endIndex,
	 * nothing is drawn.
	 * @param reversed tells whether or not to used reversed colors for drawing the collar.
	 */
	public void drawCollaredCurve(Vector3D[] points, int startIndex, int endIndex, boolean reversed) {
		if (points == null)
			return;
		if (!reversed && viewStyle != MONOCULAR_VIEW) {
			drawCurve(points,startIndex,endIndex);
			return;
		}
		if (startIndex >= points.length)
			startIndex = points.length - 1;
		if (startIndex < 0)
			startIndex = 0;
		if (endIndex >= points.length)
			endIndex = points.length - 1;
		if (endIndex < 0)
			endIndex = 0;
		if (endIndex <= startIndex)
			return;
		if (viewStyle == MONOCULAR_VIEW)
			drawCollaredCurveDirect(points,startIndex,endIndex,reversed);
		else {
			setUpForLeftEye();
			drawCollaredCurveDirect(points,startIndex,endIndex,reversed);
			setUpForRightEye();
			drawCollaredCurveDirect(points,startIndex,endIndex,reversed);
			finishStereoView();
		}
	}
	
	/**
	 * Draws a surface in wireframe. In general, surfaces should be drawn using the
	 * {@link View3DLit#drawSurface(Grid3D)} and {@link View3DLit#drawSurface(Grid3D, double, double)}
	 * methods, which will render the surface either as wireframe or as patches, as appropriate for the settings
	 * in a {@link View3DLit}.  However,
	 * this <code>drawWireframeSurface</code> method can be used to render a wireframe surface in a plain View3D
	 * or to draw a surface grid that really is intrinsically a wireframe in any 3D view.
	 * @param surfaceData contains a grid of points on the surface
	 */
	public void drawWireframeSurface(Grid3D surfaceData) { 
		if (getViewStyle() == MONOCULAR_VIEW) {
			surfaceData.applyTransform(transform3D, this);
			surfaceData.drawCurves(this, currentGraphics);
		}
		else {
			setUpForLeftEye();
			surfaceData.applyTransform(transform3D, this);
			surfaceData.drawCurves(this, currentGraphics);
			setUpForRightEye();
			surfaceData.applyTransform(transform3D, this);
			surfaceData.drawCurves(this, currentGraphics);
			finishStereoView();
		}
	}

	// ---------- Override 2D draw methods so they work for 3D (drawing into the xy-plane in 3-space) --------
	
	/**
	 * Sets the pixel with pixel coordinates (x,y) to be a spelcified color.  Note that this by-passes
	 * the support in the View3D class for stereo viewing (that is, x and y are used as untransformed
	 * pixel coordinates).  The pixel is set in the current off-screen
	 * image, which can be either the fullOSI for monocular viewing or either the left or right
	 * OSI for stereo viewing.  This method is meant mainly for use by lower level drawing methods,
	 * such as the phong lighting. 
	 * @see #drawPixel(Vector3D)
	 * @param color the color for the pixel; if null, the current drawing color is used.
	 */
	public void drawPixelDirect(Color color, int x, int y) {
		if (x < 0 || y < 0)
			return;
		int rgb;
		if (color == null)
			color = currentGraphics.getColor();
		rgb = color.getRGB();
		BufferedImage image;
		if (viewStyle == RED_GREEN_STEREO_VIEW) {
			if (currentGraphics == leftEyeGraphics)
				image = stereoComposite.getLeftEyeImage();
			else
				image = stereoComposite.getRightEyeImage();
		}
		else if (viewStyle == MONOCULAR_VIEW)
			image = fullOSI;
		else {
			if (currentGraphics == leftEyeGraphics)
				image = leftStereographOSI;
			else
				image = rightStereographOSI;
		}
		if (x >= image.getWidth() || y >= image.getHeight())
			return;
		image.setRGB(x,y,rgb);
	}


	// -----------  Helper stuff for 3D rendering -----------------------------

	private class CurveSegment extends Line2D.Double implements Comparable<CurveSegment> {
		Vector3D v1, v2;
		double midpoint_z;
		CurveSegment(Vector3D v1, Vector3D v2) {
			this.v1 = v1;
			this.v2 = v2;
			midpoint_z = (v1.z + v2.z)/2;
			Point2D p1 = new Point2D.Double(v1.x, v1.y);
			Point2D p2 = new Point2D.Double(v2.x, v2.y);
			if (!getTransform().appliedTransform2D()) {
				getTransform().windowToViewport(p1);
				getTransform().windowToViewport(p2);
			}
			setLine(p1,p2);
		}
		public int compareTo(CurveSegment c) {
			if (midpoint_z < c.midpoint_z)
				return -1;
			else if (midpoint_z > c.midpoint_z)
				return 1;
			else
				return 0;
		}
	}
	
	private void drawCollaredCurveDirect(Vector3D[] points, int startIndex, int endIndex, boolean reversed) {
		Vector3D p1 = points[startIndex]==null? null : transform3D.objectToViewCoords(points[startIndex]);
		ArrayList<CurveSegment> segments = new ArrayList<CurveSegment>();
		for (int i = startIndex + 1; i <= endIndex; i++) {
			Vector3D p2 = points[i]==null || clip(points[i])? null : transform3D.objectToViewCoords(points[i]);
			if (p1 != null && p2 != null)
				segments.add(new CurveSegment(p1,p2));
			p1 = p2;
		}
		java.util.Collections.sort(segments);
		Color saveColor = currentGraphics.getColor();
		Stroke saveStroke = currentGraphics.getStroke();
		Stroke wideStroke = new BasicStroke(getTransform().getDefaultStrokeSize() * 5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
		Stroke normalStroke = new BasicStroke(getTransform().getDefaultStrokeSize(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
		for (int i = 0; i < segments.size(); i++) {
			currentGraphics.setStroke(wideStroke);
			currentGraphics.setColor(reversed ? saveColor : currentGraphics.getBackground());
			currentGraphics.draw(segments.get(i));
			currentGraphics.setStroke(normalStroke);
			currentGraphics.setColor(reversed ? currentGraphics.getBackground() : saveColor);
			currentGraphics.draw(segments.get(i));
		}
		currentGraphics.setColor(saveColor);
		currentGraphics.setStroke(saveStroke);
	}
		
	// ------------------------- methods to support stereo rendering
	
	/**
	 * To be used with the <code>setUpForLeftEye</code> and <code>finishStereoView</code> methods to support
	 * 3D rendering.  When rendering in one of the three stereo view styles, the image needs to be rendered
	 * twice, once for the left eye and once for the right eye.  The <code>setUpForLeftEye</code> methods
	 * should be called first; this sets up the transform and current graphics context for rendering from
	 * the viewpoint of the left eye into the left eye image.  Next comes the code for drawing the object.
	 * then <code>setUpForRightEye</code>, followed by a repeat of the code for drawing the object.
	 * Finally, calling <code>finshStereoView</code> must be called to restore the original drawing 
	 * context. These three methods must always be used in this way.  Note that most programmers will not
	 * have to worry about this -- these methods will only be used when writing methods for rendering
	 * new graphics primitives such as lines and surfaces in this class and its subclasses.
	 */
	protected void setUpForLeftEye() {
		double separationFactor;
		if (viewStyle == CROSS_EYE_STEREO_VIEW)
			separationFactor = 0.04;
		else if (viewStyle == STEREOGRAPH_VIEW)
			separationFactor = -0.03;
		else
			separationFactor = 0.03;
		separationFactor *= eyeSeparationMultiplier;
		transform3D.selectLeftEye(separationFactor);
		saveGraphicsDuringStereo = currentGraphics;  // same as transform.getGraphics()
		saveUntransformedGraphicsDuringStereo = getTransform().getUntransformedGraphics();
		Color color = saveGraphicsDuringStereo.getColor();   // copying color and stroke to left and right views, in case they were changed in currentGraphics
		leftEyeGraphics.setColor(color);
		rightEyeGraphics.setColor(color);
		Stroke stroke = saveGraphicsDuringStereo.getStroke();
		leftEyeGraphics.setStroke(stroke);
		rightEyeGraphics.setStroke(stroke);
		Font font = saveGraphicsDuringStereo.getFont();
		leftEyeGraphics.setFont(font);
		rightEyeGraphics.setFont(font);
		transform3D.useGraphics(leftEyeGraphics,leftEyeUntransformedGraphics);
		currentGraphics = leftEyeGraphics;
	}
	
	/**
	 * To be used with {@link #setUpForLeftEye()} and <code>finishStereoView</code> to support
	 * 3D rendering.
	 */
	protected void setUpForRightEye() {
		double separationFactor;
		if (viewStyle == CROSS_EYE_STEREO_VIEW)
			separationFactor = 0.04;
		else if (viewStyle == STEREOGRAPH_VIEW)
			separationFactor = -0.03;
		else
			separationFactor = 0.03;
		separationFactor *= eyeSeparationMultiplier;
		transform3D.selectRightEye(separationFactor);
		transform3D.useGraphics(rightEyeGraphics,rightEyeUntransformedGraphics);
		currentGraphics = rightEyeGraphics;
	}
	
	/**
	 * To be used with {@link #setUpForLeftEye()} and {@link #setUpForRightEye()} to support
	 * 3D rendering.
	 */
	protected void finishStereoView() {
		transform3D.selectNoEye();
		transform3D.useGraphics(saveGraphicsDuringStereo,saveUntransformedGraphicsDuringStereo);
		currentGraphics = saveGraphicsDuringStereo;
		saveGraphicsDuringStereo = null;
		saveUntransformedGraphicsDuringStereo = null;
	}
	
	/**
	 * This  method is used to test whether a point should be clipped.  It is valid
	 * only during a 3D rendering operation.  It is needed in {@link Grid3D} and in
	 * {@link vmm.polyhedron.IFS}. 
	 */
	final public boolean clip(Vector3D objectPoint) {
		return objectPoint.dot(viewDirection) > clipZ;
	}

}
