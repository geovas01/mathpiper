/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.actions.ToggleAction;
import vmm.core.I18n;
import vmm.core.VMMSave;
import vmm.core.View;

public class View3DLit extends View3DWithLightSettings {
	
	
	/**
	 * A constant that can be used as a parameter to {@link #setRenderingStyle(int)} to specify that
	 * surfaces should always be rendered as wireframes.
	 */
	public static final int WIREFRAME_RENDERING = 0;

	/**
	 * A constant that can be used as a parameter to {@link #setRenderingStyle(int)} to specify that
	 * surfaces should always be rendered as solid patches.
	 */
	public static final int PATCH_RENDERING = 1;
	
	/**
	 * A constant that can be used as a parameter to {@link #setOrientation(int)} to specify that
	 * surfaces have normal orientation, in which the normal vector points out from the front face.
	 */
	public static final int NORMAL_ORIENTATION = 0;

	/**
	 * A constant that can be used as a parameter to {@link #setOrientation(int)} to specify that
	 * surfaces have reverse orientation, in which the normal vector points out from the back face.
	 */
	public static final int REVERSE_ORIENTATION = 1;
	
	/**
	 * A constant that can be used as a parameter to {@link #setOrientation(int)} to specify that
	 * surfaces have no orientation, so that both faces of a patch are treated as front faces.
	 */
	public static final int NO_ORIENTATION = 2;
	
	@VMMSave
	private int renderingStyle = PATCH_RENDERING;
	
	@VMMSave
	private int orientation = NORMAL_ORIENTATION;	
	
	@VMMSave
	private boolean phongShading;
	
	@VMMSave
	private boolean blackAndWhite;
	
	@VMMSave
	private double transparency;
	
	private boolean dragAsSurface;

	/**
	 * Commands for selecting a rendering style.
	 */
	protected ActionRadioGroup selectRenderingCommands = new ActionRadioGroup( new String[] {
			I18n.tr("vmm.core3D.commands.Wireframe"),
			I18n.tr("vmm.core3D.commands.PatchRendering")
	}, 1 ) { 
		public void optionSelected(int selectedIndex) {
			setRenderingStyle(selectedIndex);
		}
	};

	/**
	 * Commands for selecting orientation type.
	 */
	protected ActionRadioGroup selectOrientationCommands = new ActionRadioGroup( new String[] {
			I18n.tr("vmm.core3D.commands.NormalOrientation"),
			I18n.tr("vmm.core3D.commands.ReverseOrientation"),
			I18n.tr("vmm.core3D.commands.NoOrientation")
	}, 0 ) {
		public void optionSelected(int selectedIndex) {
			setOrientation(selectedIndex);
		}
	};

	/**
	 * Commands for choosing between phong shading and flat shading.
	 */
	protected ActionRadioGroup selectShadingCommands = new ActionRadioGroup( new String[] {
			I18n.tr("vmm.core3D.commands.PhongShading"),
			I18n.tr("vmm.core3D.commands.FlatShading")
	}, 0 ) {
		public void optionSelected(int selectedIndex) {
			setPhongShading(selectedIndex == 0);
		}
	};
	

	/**
	 * Commands for choosing between a black and white drawing or a color drawing.
	 */
	protected ActionRadioGroup selectColoredCommands = new ActionRadioGroup( new String[] {
			I18n.tr("vmm.core3D.commands.Color"),
			I18n.tr("vmm.core3D.commands.BlackAndWhite")
	}, 0 ) {
		public void optionSelected(int selectedIndex) {
			setBlackAndWhite(selectedIndex == 1);
		}
	};
	
	/**
	 * ToggleAction for turning the dragAsSurface property on and off.
	 * @see #setDragAsSurface(boolean)
	 */
	protected ToggleAction dragAsSurfaceToggle = new ToggleAction(I18n.tr("vmm.core3D.commands.DragAsSurface")) {
		public void actionPerformed(ActionEvent evt) {
			setDragAsSurface(getState());
		}
	};
	
	protected AbstractActionVMM setTransparencyCommand = new AbstractActionVMM(I18n.tr("vmm.core3D.commands.setTransparency")+"...") {
		public void actionPerformed(ActionEvent evt) {
			SetTransparencyDialog.showDialog(View3DLit.this);
		}
	};
	
	/**
	 * Create a View3DLit object, using default light settings and normal orientation.
	 */
	public View3DLit() {
		setOrientation(NORMAL_ORIENTATION);
	}
	
	/**
	 * Returns the current rendering style.
	 * @see #setRenderingStyle(int)
	 */
	public int getRenderingStyle() {
		return renderingStyle;
	}
	
	/**
	 * Sets the rendering style that is used to draw surfaces.  The two available styles are WIREFRAME_RENDERING
	 * and PATCH_RENDERING.  With the former, only surface grid lines are drawn.  With the latter, surface patches
	 * are drawn with color determined by their intrinsic color and lighting conditions.
	 * @param style the new rendering style; must be one of the constants WIREFRAME_RENDERING or PATCH_RENDERING
	 */
	public void setRenderingStyle(int style) {
		if (style != WIREFRAME_RENDERING && style != PATCH_RENDERING)
			return;
		selectRenderingCommands.setSelectedIndex(style);
		if (dragAsSurfaceToggle != null)
			dragAsSurfaceToggle.setEnabled(style == PATCH_RENDERING);
		if (style == renderingStyle)
			return;
		renderingStyle = style;
		forceRedraw();
	}
	
	/**
	 * Tells whether surfaces are rendered in gray-scale rather than color.
	 * @see #setBlackAndWhite(boolean)
	 */
	public boolean getBlackAndWhite() {
		return blackAndWhite;
	}

	/**
	 * Set whether surfaces should be rendered in gray-scale or in color.  In a gray-scale image, the color
	 * is computed as usual but then only the brightness is used to determine the level of gray for drawing.
	 * Note that this setting has no effectg in anaglyph stereo views, since the drawing there is effectively
	 * always done in gray-scale.
	 * @param blackAndWhite true if surfaces are to be drawn in gray-scale; false if they are to be drawn in color
	 */
	public void setBlackAndWhite(boolean blackAndWhite) {
		selectColoredCommands.setSelectedIndex(blackAndWhite ? 1 : 0);
		if (blackAndWhite != this.blackAndWhite) {
			this.blackAndWhite = blackAndWhite;
			offscreenImageType = blackAndWhite ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_INT_RGB;
			fullOSI = leftStereographOSI = rightStereographOSI = null;
			forceRedraw();
		}
	}

	/**
	 * Returns the current orientation setting for surfaces.
	 * @see #setOrientation(int)
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * Set how front and back faces of surface patches are determined.  In NORMAL_ORIENTATION, the standard normal vectorto a
	 * surface patch points out of the front face of the patch.  In REVERSE_ORIENTATION, the normal vector points out of
	 * the back face.  In NO_ORIENTATION, both faces are considered to be front faces.
	 * @param orientation the method for determining front/back faces of surface patches.  Must be one of the contstants
	 * NORMAL_ORIENTATION, REVERSE_ORIENTATION, or NO_ORIENTATION.
	 */
	public void setOrientation(int orientation) {
		if (orientation != NORMAL_ORIENTATION && orientation != REVERSE_ORIENTATION && orientation != NO_ORIENTATION)
			return;
		selectOrientationCommands.setSelectedIndex(orientation);
		if (this.orientation != orientation) {
			this.orientation = orientation;
			forceRedraw();
		}
	}

	/**
	 * Returns the current shading setting, true for Phong shading or false for flat shading.
	 * @see #getPhongShading()
	 */
	public boolean getPhongShading() {
		return phongShading;
	}

	/**
	 * Select the type of shading to be used for surface patches.  If phongShading is set to true, then
	 * the Phong shading algorithm is used to determine the color of each individual pixel in the surface
	 * patch.  This gives the highest quality results, but is time consuming.  If phongShading is set to false,
	 * and flat shading is used; in flat shading, the same color is used for all pixels in the patch.
	 */
	public void setPhongShading(boolean phongShading) {
		selectShadingCommands.setSelectedIndex(phongShading? 0 : 1);
		if (this.phongShading != phongShading) {
			this.phongShading = phongShading;
			forceRedraw();
		}
	}

	/**
	 * Determines how surfaces are rendered when the "fast draw" mode is in effect, such as during rotation, zooming, or
	 * dragging by the user.
	 * @see #setDragAsSurface(boolean)
	 * @see View#setFastDrawing(boolean)
	 */
	public boolean getDragAsSurface() {
		return dragAsSurface;
	}
	
	/**
	 * Specify how surfaces are rendered when the "fast draw" mode is in effect, such as during rotation, zooming, or
	 * dragging by the user.  If dragAsSurface is set to false, then wireframe rendering is used for fast drawing of
	 * surface.  If dragAsSurface is set to true, and the rendering method is set to PATCH_RENDERING, then "rough" 
	 * surfaces are drawn during fast drawing.  In a "rough" surface, flat shading is always used and all the subpatches
	 * in a patch are rendered as a single large patch.  Note that when WIREFRAME_RENDERING is in effect, fast drawing
	 * will be done in wireframe mode regardless of the setting of the dragAsSurface property.
	 */
	public void setDragAsSurface(boolean dragAsSurface) {
		dragAsSurfaceToggle.setState(dragAsSurface);
		this.dragAsSurface = dragAsSurface;
	}
	
	/**
	 * Returns the current degree of transparency.  The value is in the range 0.0 to 1.0.
	 * @see #setTransform(vmm.core.Transform)
	 */
	public double getTransparency() {
		return transparency;
	}

	/**
	 * Set the degree of transparency for surfaces displayed in this View3DLit.  The transparency degree
	 * is a number in the range 0.0 to 1.0, with 0.0 representing a fully opaque surface and 1.0 representing
	 * a fully transparent surface that will, in effect, be invisible.
	 * @param transparency  the desired degree of transparency.  The value is clamped so that it lies
	 * in the range 0 to 1, inclusive.
	 */
	public void setTransparency(double transparency) {
		if (transparency < 0)
			transparency = 0;
		else if (transparency > 1)
			transparency = 1;
		if (this.transparency == transparency)
			return;
		this.transparency = transparency;
		forceRedraw();
	}

	public void setEnableThreeD(boolean enable) {
		if (enable == getEnableThreeD())
			return;
		super.setEnableThreeD(enable);
		selectColoredCommands.setEnabled(enable);
		selectOrientationCommands.setEnabled(enable);
		selectRenderingCommands.setEnabled(enable);
		selectShadingCommands.setEnabled(enable);
		dragAsSurfaceToggle.setEnabled(enable);
		lightingEnabledToggle.setEnabled(enable);
		if (lightSettingsCommand != null)
			lightSettingsCommand.setEnabled(enable);
	}

	/**
	 * Adds a checkbox for setting the dragAsSurface property to any settings contributed by the superclass.
	 * @see View#getSettingsCommands()
	 */
	public ActionList getSettingsCommands() {
		ActionList commands = super.getSettingsCommands();
		commands.add(null);
		commands.add(setTransparencyCommand);
		commands.add(null);
		commands.add(dragAsSurfaceToggle);
		return commands;
	}
	
	public void setViewStyle(int style) {
		super.setViewStyle(style);
		selectColoredCommands.setEnabled(getViewStyle() != RED_GREEN_STEREO_VIEW);
	}
	
	public void takeExhibit(View view, boolean shareTransform) {
		super.takeExhibit(view,shareTransform);
		if (view instanceof View3DLit) {
			setOrientation(((View3DLit)view).getOrientation());
		}
	}

	/**
	 * Adds commands for setting various View3DLit options to any view commands contributed by the
	 * superclass.
	 * @see View#getViewCommands()
	 */
	public ActionList getViewCommands() {
		ActionList commands = super.getViewCommands();
		commands.add(null);
		commands.add(selectRenderingCommands);
		commands.add(null);
//		commands.add(selectShadingCommands);
//		commands.add(null);
		commands.add(selectColoredCommands);
		selectColoredCommands.setEnabled(getViewStyle() != RED_GREEN_STEREO_VIEW);
		commands.add(null);
		commands.add(selectOrientationCommands);
		return commands;
	}
	
	
	//----------------------------------------  Surface drawing commands -------------------------------------
	
	/**
	 * Draws an entire surface represented in the form of a Grid3D object, taking into 
	 * account current lighting and rendering settings.
	 */
	public void drawSurface(Grid3D surfaceData) {
		drawSurface(surfaceData, 0, 1);
	}
	
	/**
	 * Draws all or part of a surface that is represented in the form of a Grid3D object, taking into 
	 * account current lighting and rendering settings.  The ability to draw only part of a surface is
	 * used in surface build animations.
	 * <p>If fast drawing mode is in effect, the entire 
	 * surface is always drawn; the startIndex and endIndex are ignored in this case.  Also, if WIREFRAME_RENDERING
	 * is in effect, the entire surface is drawn in wireframe form.  For normal speed rendering with patch display, the
	 * startPercent and endPercent specify a range of subpatches to draw; these numbers are given as percentages
	 * of the total number of available patches (taking into account patches that have been clipped).
	 * The subpatches are sorted into  back-to-front order.  (The total number
	 * of subpatches can be determined by calling {@link Grid3D#getSubPatchCountInSurface()}, but note that some
	 * of the patches might not be included in the drawing or in the calculation of percentages because ofclipping.)
	 */
	public void drawSurface(Grid3D surfaceData, double startPercent, double endPercent) { 
		if (renderingStyle == WIREFRAME_RENDERING || (getFastDrawing() && !dragAsSurface)) {
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
		else if (renderingStyle == PATCH_RENDERING) {
			if (getViewStyle() == MONOCULAR_VIEW) {
				if (getAntialiased())
					currentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
				surfaceData.applyTransform(transform3D, this);
				if (getFastDrawing())
					surfaceData.drawMajorPatches(this, currentGraphics);
				else
					surfaceData.drawSubPatches(this, currentGraphics, startPercent, endPercent);
				if (getAntialiased())
					currentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			}
			else {
				setUpForLeftEye();
				if (getAntialiased())
					currentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
				surfaceData.applyTransform(transform3D, this);
				if (getFastDrawing())
					surfaceData.drawMajorPatches(this, currentGraphics);
				else
					surfaceData.drawSubPatches(this, currentGraphics, startPercent, endPercent);
				if (getAntialiased())
					currentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				setUpForRightEye();
				if (getAntialiased())
					currentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
				surfaceData.applyTransform(transform3D, this);
				if (getFastDrawing())
					surfaceData.drawMajorPatches(this, currentGraphics);
				else
					surfaceData.drawSubPatches(this, currentGraphics, startPercent, endPercent);
				if (getAntialiased())
					currentGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				finishStereoView();
			}
		}
	}
	
	/**
	 * Draws a filled-in polygon in current drawing color.  To projection of the polygon onto the xy-plane is drawn.
	 * Note: This is used by {@link vmm.polyhedron.IFS}.
	 * @param vertices  The vertices of the polygon, in object coordinates.
	 * @param outlineColor  If non-null, then the polygon is outlined with this color.
	 */
	public void fillPolygon(Vector3D[] vertices, Color outlineColor) {
		if (getViewStyle() == MONOCULAR_VIEW) {
			Color saveColor = currentGraphics.getColor();
			GeneralPath path = new GeneralPath();
			Point2D pt = transform3D.objectToDrawingCoords(vertices[0]);
			path.moveTo( (float)pt.getX(), (float)pt.getY() );
			for (int i = 1; i < vertices.length; i++) {
				pt = transform3D.objectToDrawingCoords(vertices[i]);
				path.lineTo( (float)pt.getX(), (float)pt.getY() );
			}
			path.closePath();
			currentGraphics.fill(path);
			if (outlineColor != null)
				currentGraphics.setColor(outlineColor);
			currentGraphics.draw(path);
			currentGraphics.setColor(saveColor);
		}
		else {
			setUpForLeftEye();
			Color saveColor = currentGraphics.getColor();
			GeneralPath path = new GeneralPath();
			Point2D pt = transform3D.objectToDrawingCoords(vertices[0]);
			path.moveTo( (float)pt.getX(), (float)pt.getY() );
			for (int i = 1; i < vertices.length; i++) {
				pt = transform3D.objectToDrawingCoords(vertices[i]);
				path.lineTo( (float)pt.getX(), (float)pt.getY() );
			}
			path.closePath();
			currentGraphics.fill(path);
			if (outlineColor != null)
				currentGraphics.setColor(outlineColor);
			currentGraphics.draw(path);
			currentGraphics.setColor(saveColor);
			setUpForRightEye();
			saveColor = currentGraphics.getColor();
			path.reset();
			pt = transform3D.objectToDrawingCoords(vertices[0]);
			path.moveTo( (float)pt.getX(), (float)pt.getY() );
			for (int i = 1; i < vertices.length; i++) {
				pt = transform3D.objectToDrawingCoords(vertices[i]);
				path.lineTo( (float)pt.getX(), (float)pt.getY() );
			}
			path.closePath();
			currentGraphics.fill(path);
			if (outlineColor != null)
				currentGraphics.setColor(outlineColor);
			currentGraphics.draw(path);
			currentGraphics.setColor(saveColor);
			finishStereoView();
		}
	}
	
	
}
