/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.surface;

import java.awt.Graphics2D;

import vmm.core.Animation;
import vmm.core.Exhibit;
import vmm.core.IntegerParam;
import vmm.core.TimerAnimation;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.Exhibit3D;
import vmm.core3D.Grid3D;
import vmm.core3D.Transform3D;
import vmm.core3D.View3D;
import vmm.core3D.View3DLit;

/**
 * Represents a surface in three-space defined by a rectangular array of points.
 */
abstract public class Surface extends Exhibit3D {

	/**
	 * The object that holds all the data for the surface.  This object is computed by the
	 * {@link #createData()} method.
	 */
	protected Grid3D data;
	
	/**
	 * The U resolution, that is, the number of patches in the u direction.  By convention, the number of points along the
	 * u-direction of the surface is 1 plus 6 times the value of uPatchCount, since there are 6 "subpatches" along each
	 * side of a patch. (However, the number of subpatches per patch is actually selected by a subclass when it
	 * creates the data for the surface.)
	 */
	protected IntegerParam uPatchCount = new IntegerParam("vmm.surface.Surface.uPatchCount",18);

	/**
	 * The U resolution, that is, the number of patches in the u direction.  By convention, the number of points along the
	 * u-direction of the surface is 1 plus 6 times the value of vPatchCount.
	 */
	protected IntegerParam vPatchCount = new IntegerParam("vmm.surface.Surface.vPatchCount",18);
	
	@VMMSave private int defaultOrientation = View3DLit.NORMAL_ORIENTATION;
	
	/**
	 * Creates the data needed to represent the surface.  This method must create a Grid3D object and
	 * assign it to the protected variable {@link #data}.  Note: the gridSpacing for the grid is  
	 * completely managed by the top-level surface class, so subclasses
	 * do not have to concern themselves with which grid lines will be drawn.  In general, subclasses only
	 * need to compute the veritces and intrinsic colors for the grid.  They could also compute normal vectors.
	 */
	abstract protected void createData();
	
	/**
	 * Adds uPatchCount and vPatchCount as parameters of the surface, each with a range of possible input values
	 * from 1 to 40.  Also calls {@link Exhibit#setFramesForMorphing(int)} to set the number of frames for morphing 
	 * to 12 and calls {@link Exhibit#setUseFilmstripForMorphing(boolean)} to turn on the use of filmstrips for
	 * the morphing animation.
	 */
	public Surface() {
		addParameter(vPatchCount);
		addParameter(uPatchCount);
		vPatchCount.setMinimumValueForInput(1);
		uPatchCount.setMinimumValueForInput(1);
		vPatchCount.setMaximumValueForInput(40);
		uPatchCount.setMaximumValueForInput(40);
		setFramesForMorphing(12);
		setUseFilmstripForMorphing(true);
	}
	
	/**
	 * Returns the default orientation setting for this surface.  This property determines which
	 * side of the surface, if any, is front-facing.
	 * @see #setDefaultOrientation(int)
	 */
	public int getDefaultOrientation() {
		return defaultOrientation;
	}

	/**
	 * Set the default orientation setting for this surface.  This property determines which side
	 * of the surface, if any, is front-facing.  The possible settings are:
	 * View3DLit.NORMAL_ORIENTATION, View3DLit.REVERSE_ORIENTATION, and View3DLit.NO_ORIENTATION.
	 * Other values of the parameter are ignored.  If it is not changed by calling this method, the
	 * default orientation is View3DLit.NORMAL_ORIENTATION.
	 * <p>Note that the default orientation is used only when a View is created by the
	 * {@link #getDefaultView()} method.
	 * @see View3DLit#setOrientation(int)
	 */
	public void setDefaultOrientation(int defaultOrientation) {
		if ( defaultOrientation != this.defaultOrientation && (defaultOrientation == View3DLit.NORMAL_ORIENTATION ||
				defaultOrientation == View3DLit.NO_ORIENTATION || defaultOrientation == View3DLit.REVERSE_ORIENTATION)) {
			this.defaultOrientation = defaultOrientation;
		}
	}

	/**
	 * Returns a default view of this exhibit, a View of type {@link SurfaceView}.
	 * (Note:  If a Surface is displayed in a plain View3D, it will always appear as a wireframe.)
	 */
	public View getDefaultView() {
		SurfaceView view = new SurfaceView();
		return view;
	}
	
	/**
	 * Returns a surface build animation, which shows patches being drawn bit-by-bit in back to front
	 * order.
	 */
	public Animation getBuildAnimation(View view) {
		if ( ! (view instanceof View3DLit) )
			return null;
		if ( ((View3DLit)view).getRenderingStyle() == View3DLit.WIREFRAME_RENDERING )
			return null;
		final View3DLit view3D = (View3DLit)view;
		return new TimerAnimation(0,20) {
			private double percentDrawn;
			private double batchSize = 1.0/50.0 + 0.00001;
			protected void drawFrame() {
				if (percentDrawn > 1) {
					cancel();
					return;
				}
				if (! view3D.beginDrawToOffscreenImage())
					return;
				if (view3D instanceof SurfaceView) {
					SurfaceView v = (SurfaceView)view3D;
					data.setUCurveIncrement(v.getShowUGridLines()? v.getGridSpacing() : 0);
					data.setVCurveIncrement(v.getShowVGridLines()? v.getGridSpacing() : 0);
				}
				view3D.drawSurface(data,percentDrawn,percentDrawn+batchSize);
				view3D.endDrawToOffscreenImage();
				view3D.getDisplay().repaint();
				percentDrawn += batchSize;
			}
		};
	}

	
	protected void computeDrawData3D(View3D view, boolean exhibitNeedsRedraw, Transform3D previousTransform, Transform3D newTransform) {
		if (exhibitNeedsRedraw)
			createData();
	}

	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
		if (view instanceof SurfaceView) {
			SurfaceView v = (SurfaceView)view;
			data.setUCurveIncrement(v.getShowUGridLines()? v.getGridSpacing() : 0);
			data.setVCurveIncrement(v.getShowVGridLines()? v.getGridSpacing() : 0);
		}
		if (view instanceof View3DLit)
			((View3DLit)view).drawSurface(data);
		else
			view.drawWireframeSurface(data);
	}


	
}
