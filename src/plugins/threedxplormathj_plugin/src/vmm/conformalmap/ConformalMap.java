/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.conformalmap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.actions.ToggleAction;
import vmm.core.Animation;
import vmm.core.BasicMouseTask2D;
import vmm.core.Complex;
import vmm.core.Decoration;
import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.MouseTask;
import vmm.core.RealParamAnimateable;
import vmm.core.SaveAndRestore;
import vmm.core.ThreadedAnimation;
import vmm.core.TwoPointInput;
import vmm.core.Util;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.BasicMouseTask3D;
import vmm.core3D.Exhibit3D;
import vmm.core3D.Transform3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;

/**
 * This class represents a map from a region in the complex plane to the
 * complex plane.  (Note:  For most of the subclasses of this map, the map is
 * conformal, but that is not in fact true in every case.)
 * <p>Note that a ConformalMap by default uses a mouse task that lets the
 * user switch between a view of the domain and a view of the image by
 * shift-right-clicking.  Ordinarily, the image is shown.  The domain is
 * shown when the user shift-right-clicks until the mouse is released.
 */
abstract public class ConformalMap extends Exhibit3D {
	/**
	 * Code for the identity function as one of the possible functions that can
	 * be precomposed or postcomposed with a map.
	 */
	public final static int IDENTITY = 0;

	/**
	 * Code for the function 1/z as one of the possible functions that can
	 * be precomposed or postcomposed with a map.
	 */
	public final static int INVERSION = 1;
	
	/**
	 * Code for the function (1-z)/(1+z) as one of the possible functions that can
	 * be precomposed or postcomposed with a map.
	 */
	public final static int FRACTIONAL = 2;
	
	/**
	 * Code for the square root function as one of the possible functions that can
	 * be precomposed or postcomposed with a map.
	 */
	public final static int SQRROOT = 3;
	
	
	/**
	 * Code for using a cartesian grid on the domain of the function.
	 */
	public final static int CARTESIAN = 0;  // Note: the gridData array, below assumes value is 0; do not change.

	/**
	 * Code for using a polar grid on the domain of the function.
	 */
	public final static int POLAR = 1; // Note: the gridData array, below assumes value is 1; do not change.
	
	/**
	 * Code for using a conformal polar grid on the domain of the function.
	 */
	public final static int POLARCONFORMAL = 2;  // Note: the gridData array, below assumes value is 2; do not change.
	
	
	private double[][] gridData = new double[][] {
			{ -1, 1, -1, 1, 20, 20 },         // umin, umax, vmin, vmax, ures, vres for CARTESIAN grid
			{ 0, 1, 0, 2*Math.PI, 7, 42 },    // umin, umax, vmin, vmax, ures, vres for POLAR grid
			{ -0.5, 0.5, 0, 2*Math.PI, 7, 42 } // umin, umax, vmin, vmax, ures, vres for POLARCONFORMAL grid
		};                                     // See setGridType() method, where this array is used.
	

	/**
	 * Minimum u-value for the domain of the function.
	 */
	protected RealParamAnimateable umin = new RealParamAnimateable("vmm.conformalmap.ConformalMap.umin", -1);

	/**
	 * Maximum u-value for the domain of the function.
	 */
	protected RealParamAnimateable umax = new RealParamAnimateable("vmm.conformalmap.ConformalMap.umax", 1);

	/**
	 * Minimum v-value for the domain of the function.
	 */
	protected RealParamAnimateable vmin = new RealParamAnimateable("vmm.conformalmap.ConformalMap.vmin", -1);

	/**
	 * Maximum v-value for the domain of the function.
	 */
	protected RealParamAnimateable vmax = new RealParamAnimateable("vmm.conformalmap.ConformalMap.vmax", 1);
	
	/**
	 * Number of grid lines in the grid in the u direction.
	 */
	protected IntegerParam ures = new IntegerParam("vmm.conformalmap.ConformalMap.ures", 20);
	
	/**
	 * Number of grid lines in the grid in the v direction.
	 */
	protected IntegerParam vres = new IntegerParam("vmm.conformalmap.ConformalMap.vres", 20);
	
	
	/**
	 * The actual resolutions in the u- and v- directions are obtained by multiplying the
	 * nominal resolutions give by the ures and vres parameters by this value.  That is, there are
	 * this many points between each pair of neighboring grid lines.
	 */
	protected final static int POINTS_PER_INTERVAL = 4;
	
	/**
	 * The number of points to be used for a ConformalMapFigure with a circle shape,
	 * when the user adds a circle to the exhibit.
	 * This is set here eqaul to the default from ConformalMapFigure, but can be reset
	 * in a subclass.
	 */
	protected int pointsOnCircleFigure = ConformalMapFigure.DEFAULT_POINTS_ON_CIRCLE;

	/**
	 * The number of points to be used for a ConformalMapFigure that is a line,
	 * when the user adds a line to the exhibit.
	 * This is set here eqaul to the default from ConformalMapFigure, but can be reset
	 * in a subclass.
	 */
	protected int pointsOnLineFigure = ConformalMapFigure.DEFAULT_POINTS_ON_LINE;

	/**
	 * The number of points to be used for a ConformalMapFigure that is a line segment,
	 * when the user adds a line to the exhibit.
	 * This is set here eqaul to the default from ConformalMapFigure, but can be reset
	 * in a subclass.
	 */
	protected int pointsOnLineSegmentFigure = ConformalMapFigure.DEFAULT_POINTS_ON_SEGMENT;
	
	
	/**
	 * Stores all the points in the grid that covers the domain of the function.
	 * This is part of the basic drawing data.  It is computed in the computeDrawData3D method
	 * and is used in the doDraw3D method.
	 */
    protected Complex[][]argumentGrid;

	/**
	 * Stores the images of the points in the argumentGrid.
	 * This is part of the basic drawing data.  It is computed in the computeDrawData3D method
	 * and is used in the doDraw3D method.
	 */
    protected Complex[][]valueGrid;

    @VMMSave
	private int preCompFunction =IDENTITY;  // which function should be precomposed with the map.

    @VMMSave
    private int postCompFunction = IDENTITY;  // which function should be postcomposed with the map.
	
    @VMMSave
    private int gridType = CARTESIAN;  // which grid type should be used on the domain.
	
	private ActionRadioGroup preCompSelect;  // lets user select the function for precomposition
	private ActionRadioGroup postCompSelect; // lets user select the function for postcomposition
	protected ActionRadioGroup gridTypeSelect; // lets user select the type of grid to use on the domain
	                                           // (made "protected" so it can be disabled in Weierstrass_p.java)
	
	private double[] defaultWindow2D = {-3,3,-3,3};

	   
	/**
	 * The function that is to be graphed, to
	 * be supplied by any concrete subclass.
	 */
	protected abstract Complex function(Complex argument);
	
	
	public ConformalMap() {
		setDefaultBackground(Color.BLACK);
		addParameter(vres);
		addParameter(ures);
		addParameter(vmax);
		addParameter(vmin);
		addParameter(umax);
		addParameter(umin);
		vres.setMinimumValueForInput(1);
		ures.setMinimumValueForInput(1);
		setDefaultWindow(-1.5,1.5,-1.5,1.5);
		setDefaultViewpoint(new Vector3D(10,-10,10) );
		setUseFilmstripForMorphing(true);
		setFramesForMorphing(25);
		ActionRadioGroup group = new ActionRadioGroup() {
			public void optionSelected(int selectedIndex) {
				setPreCompFunction(selectedIndex);
			}
		};
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.id"));
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.inverse"));
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.fractlin"));
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.sqrt"));
		group.setSelectedIndex(0);
		preCompSelect = group;
		group = new ActionRadioGroup() {
			public void optionSelected(int selectedIndex) {
				setPostCompFunction(selectedIndex);
			}
		};
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.id"));
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.inverse"));
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.fractlin"));
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.sqrt"));
		group.setSelectedIndex(0);
		postCompSelect = group;
		group = new ActionRadioGroup() {
			public void optionSelected(int selectedIndex) {
				setGridType(selectedIndex);
			}
		};
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.cartesian"));
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.polar"));
		group.addItem(I18n.tr("vmm.conformalmap.ConformalMap.polarconformal"));
		group.setSelectedIndex(0);
		gridTypeSelect = group;
	}
	
	
	/**
	 * Gets the code number for the function that is postcomposed with the basic map.
	 * @see #setPostCompFunction(int)
	 */
	public int getPostCompFunction() {
		return postCompFunction;
	}


	/**
	 * Sets the function that is postcomposed with the basic map.  The value should be one
	 * of the constants IDENTITY, INVERSION, FRACTIONAL, or SQRROOT.  The default is IDENTITY.
	 * (This property is managed by an ActionRadioGroup, although it could also be set directly.)
	 */
	public void setPostCompFunction(int postCompFunction) {
		if (this.postCompFunction != postCompFunction) {
			this.postCompFunction = postCompFunction;
			postCompSelect.setSelectedIndex(postCompFunction);
			forceRedraw();
		}
	}


	/**
	 * Gets the code number for the function that is precomposed with the basic map.
	 * @see #setPreCompFunction(int)
	 */
	public int getPreCompFunction() {
		return preCompFunction;
	}


	/**
	 * Sets the function that is precomposed with the basic map.  The value should be one
	 * of the constants IDENTITY, INVERSION, FRACTIONAL, or SQRROOT.  The default is IDENTITY.
	 * (This property is managed by an ActionRadioGroup, although it could also be set directly.)
	 */
	public void setPreCompFunction(int preCompFunction) {
		if (this.preCompFunction != preCompFunction) {
			this.preCompFunction = preCompFunction;
			preCompSelect.setSelectedIndex(preCompFunction);
			forceRedraw();
		}
	}
	
	
	/**
	 * Gets the code number for the type of grid that is used on the domain of the function.
	 * @see #setGridType(int)
	 */
	public int getGridType() {
		return gridType;
	}
	

	/**
	 * Sets the type of grid that is used on the domain of the map.  The value should be one of
	 * the constants CARTESIAN, POLAR, or POLARCONFORMAL; other values are ignored.  The default is CARTESIAN.
	 * (This property is managed by an ActionRadioGroup, although it could also be set directly.)
	 * This method changes the values of umin, umax, vmin, vmax, ures, and vres to match the new grid type.
	 * Separate values for each grid type are stored for these parameters.  When this method is used
	 * to change the grid type, the values of the paramters are set to the values saved for that
	 * grid type.
	 */
	public void setGridType(int gridType) {
		if (gridType != CARTESIAN && gridType != POLAR && gridType != POLARCONFORMAL)
			return;
		if (this.gridType != gridType) {
			// swap values of umin, umax, vmin, vmax, ures, vres for current grid type into gridData array
			gridData[this.gridType][0] = umin.getValue();
			gridData[this.gridType][1] = umax.getValue();
			gridData[this.gridType][2] = vmin.getValue();
			gridData[this.gridType][3] = vmax.getValue();
			gridData[this.gridType][4] = ures.getValue();
			gridData[this.gridType][5] = vres.getValue();
			this.gridType = gridType;
			// swap values for new grid type out of gridData array into umin, umax, vmin, vmax, ures, vres
			umin.reset(gridData[this.gridType][0]);
			umax.reset(gridData[this.gridType][1]);
			vmin.reset(gridData[this.gridType][2]);
			vmax.reset(gridData[this.gridType][3]);
			ures.reset((int)gridData[this.gridType][4]);
			vres.reset((int)gridData[this.gridType][5]);
			gridTypeSelect.setSelectedIndex(gridType);
			forceRedraw();
		}
	}

	/**
	 * For use in a subclass where the gridType has to be modified without
	 * changing the values of umin, umax, vmin, vmax.  The sets the value
	 * of the gridType property variable without making any other changes.
	 * It does change the setting of {@link #gridTypeSelect} to match the
	 * new grid type.
	 * (This was introduced specifically for use in {@link UserConformalMap}.
	 */
	protected void resetGridType(int gridType) {
		this.gridType = gridType;
		gridTypeSelect.setSelectedIndex(gridType);
	}
	
	/**
	 * Computes the output value for a given input argument, taking into 
	 * account the precomposed and postcomposed functions.  This is the
	 * function that is actually displayed.
	 */
	protected Complex composedFunction(Complex z){
		Complex w=null;
		switch (preCompFunction){
		case IDENTITY:
			w =z;
			break;
		case INVERSION:
			w=z.inverse();
			break;
		case FRACTIONAL:
			w = z.minus(1).dividedBy(z.plus(1)).times(-1);
			break;
		case SQRROOT:
		    w=z.power(0.5);
			break;
		}
		z = function(w);
		switch (postCompFunction){
		case IDENTITY:
			w =z;
			break;
		case INVERSION:
			w=z.inverse();
			break;
		case FRACTIONAL:
			w = z.minus(1).dividedBy(z.plus(1)).times(-1);
			break;
		case SQRROOT:
		    w=z.power(0.5);
			break;
		}
		return w;
	}

	
	/**
	 * Returns an array of four numbers representing the default 2D window.
	 * @see #setDefaultWindow2D(double[])
	 */
	public double[] getDefaultWindow2D() {
		return defaultWindow2D;
	}


	/**
	 * Sets the default 2D window for this exhibit.  This is the window that is used
	 * when the view is in 2D mode, that is, when the domain is displayed or when the
	 * image is displayed in the complex plane.  It is not used when the image is
	 * displayed on the Riemann Sphere.
	 * @param defaultWindow2D an array containing the default window, xmin, xmax, ymin, and ymax.
	 * If this parameter is null, nothing is done.  If the array has fewer than four elements, an
	 * IllegalArgumentException is thrown.
	 */
	public void setDefaultWindow2D(double[] defaultWindow2D) {
		if (defaultWindow2D != null) {
			if (defaultWindow2D.length < 4)
				throw new IllegalArgumentException("Internal Error: Array is too short.");
			this.defaultWindow2D = defaultWindow2D;
		}
	}


	/**
	 * Sets the default 2D window for this exhibit.  This is the window that is used
	 * when the view is in 2D mode, that is, when the domain is displayed or when the
	 * image is displayed in the complex plane.  It is not used when the image is
	 * displayed on the Riemann Sphere.  This convenience method just calls 
	 * {@link #setDefaultWindow2D(double[])}.
	 */
	public void setDefaultWindow2D(double xmin, double xmax, double ymin,double ymax) {
		setDefaultWindow2D(new double[] { xmin, xmax, ymin, ymax });
	}

	
	/**
	 * Finds the input grid point corresponding to (u,v).  For a Cartesian grid, this
	 * is just (u,v) itself.  For a polar grid, it is (u*cos(v),u*sin(v)).  For a
	 * polar conformal grid, it is (exp(u)*cos(v),exp(u)*sin(v)) which is the same
	 * as exp(u+iv).
	 */
    protected Complex gridMap(double u,double v){
		Complex w = new Complex(u,v);
		switch (gridType){
		case CARTESIAN:
			break;
		case POLAR:
			w.re=u*Math.cos(v);
			w.im=u*Math.sin(v);
			break;
		case POLARCONFORMAL:
			w= w.exponential();
			break;
		}
		return w;
	}
    
    
    protected void computeDrawData3D(View3D view, boolean exhibitNeedsRedraw, Transform3D previousTransform3D, Transform3D newTransform3D) {
		if (exhibitNeedsRedraw) {
			int uCount = ures.getValue() * POINTS_PER_INTERVAL;
			int vCount = vres.getValue() * POINTS_PER_INTERVAL;
			valueGrid = new Complex[uCount+1][vCount+1];
			argumentGrid = new Complex[uCount+1][vCount+1];
			double uStart = umin.getValue();
			double vStart = vmin.getValue();
			double uEnd = umax.getValue();
			double vEnd = vmax.getValue();
			double du = (uEnd - uStart) / uCount;
			double dv = (vEnd - vStart) / vCount;
			for (int i = 0; i <= uCount ; i++) {
				for (int j = 0; j <= vCount; j++) {
					argumentGrid[i][j] = gridMap(uStart + i*du,vStart + j*dv);
					valueGrid[i][j] = composedFunction(argumentGrid[i][j]);
				}
			}
		}
	}
	

	protected void doDraw3D(Graphics2D g, View3D view, Transform3D transform) {
		int uCount = ures.getValue() * POINTS_PER_INTERVAL;
		int vCount = vres.getValue() * POINTS_PER_INTERVAL;
		if ( !( view instanceof ConformalMapView) || ((ConformalMapView)view).getDrawValueGrid() )
			if (!view.getEnableThreeD()){
			  drawGrid(g,view,valueGrid,uCount,vCount);
			}
			else{
				 drawGrid3D(g,view,valueGrid,uCount,vCount);
			}
		else
			  drawGrid(g,view,argumentGrid,uCount,vCount);
	}
	
	
	private void drawGrid(Graphics2D g, View3D view, Complex[][] grid, int uCount, int vCount) {
		boolean whiteBackground = Color.WHITE.equals(view.getBackground());
		boolean useColor = true;
		int uHilite = -1;
		int vHilite = -1;
		Color saveColor = view.getColor();
		if (view instanceof ConformalMapView) {
			useColor = ((ConformalMapView)view).getUseColor();
			uHilite = ((ConformalMapView)view).getUHilite();
			vHilite = ((ConformalMapView)view).getVHilite();
		}
		boolean useHilite = uHilite >= 0 || vHilite >= 0;
		for (int i = 0; i <= uCount; i = i + POINTS_PER_INTERVAL) {
			Point2D[] curve = new Point2D[vCount + 1];
			boolean hilited = uHilite == i;
			Color c = null;
			if (useColor) {
				if ( !(useHilite && hilited) ) {
					int brightness = useHilite && !whiteBackground? 175 : 255;
					c = new Color(brightness, (int)(brightness * ((double)(uCount-i)/uCount)), 0 );
				}
			}
			else {
				if (useHilite && hilited)
					c = Color.RED;
			}
			view.setColor(c);
			for (int j = 0; j <= vCount; j++)
				curve[j] = new Point2D.Double(grid[i][j].re, grid[i][j].im);
			if (useHilite && hilited) {
				view.setStrokeSizeMultiplier(2);
				view.drawCurve(curve);
				view.setStrokeSizeMultiplier(1);
			}
			else
				view.drawCurve(curve);
		}
		for (int i = 0; i <= vCount; i = i + POINTS_PER_INTERVAL) {
			Point2D[] curve = new Point2D[uCount + 1];
			boolean hilited = vHilite == i;
			Color c = null;
			if (useColor) {
				if ( !(useHilite && hilited)) {
					int brightness = useHilite && !whiteBackground? 175 : 255;
					c = new Color(brightness/3, (int)(brightness* ((double)(vCount-i)/vCount)), (int)(brightness * ((double)i/vCount)) );
				}
			}
			else {
				if (useHilite && hilited)
					c = Color.RED;
			}
			view.setColor(c);
			for (int j = 0; j <= uCount; j++)
				curve[j] = new Point2D.Double(grid[j][i].re, grid[j][i].im);
			if (useHilite && hilited) {
				view.setStrokeSizeMultiplier(2);
				view.drawCurve(curve);
				view.setStrokeSizeMultiplier(1);
			}
			else
				view.drawCurve(curve);
		}
		view.setColor(saveColor);
	}
	
	
	private void drawGrid3D(Graphics2D g, View3D view, Complex[][] grid, int uCount, int vCount) {
		boolean whiteBackground = Color.WHITE.equals(view.getBackground());
		boolean useColor = true;
		int uHilite = -1;
		int vHilite = -1;
		if (view instanceof ConformalMapView) {
			useColor = ((ConformalMapView)view).getUseColor();
			uHilite = ((ConformalMapView)view).getUHilite();
			vHilite = ((ConformalMapView)view).getVHilite();
		}
		boolean useHilite = uHilite >= 0 || vHilite >= 0;
		Color saveColor = view.getColor();
		for (int i = 0; i <= uCount; i = i + POINTS_PER_INTERVAL) {
			Vector3D[] curve = new Vector3D[vCount + 1];
			boolean hilited = uHilite == i;
			Color c = null;
			if (useColor) {
				if ( !(useHilite && hilited) ) {
					int brightness = useHilite && !whiteBackground? 175 : 255;
					c = new Color(brightness, (int)(brightness * ((double)(uCount-i)/uCount)), 0 );
				}
			}
			else {
				if (useHilite && hilited)
					c = Color.RED;
			}
			view.setColor(c);
			for (int j = 0; j <= vCount; j++) {
				if (grid[i][j].abs2() > 1000000) 
					curve[j] = new Vector3D(0, 0, 1);
				else {
					double[] p = grid[i][j].stereographicProjection();
					curve[j] = new Vector3D(p[0],p[1],p[2]);
				}
			}
			if (useHilite && hilited) {
				view.setStrokeSizeMultiplier(2);
				view.drawCurve(curve);
				view.setStrokeSizeMultiplier(1);
			}
			else
				view.drawCurve(curve);
		}
		for (int i = 0; i <= vCount; i = i + POINTS_PER_INTERVAL) {
			Vector3D[] curve = new Vector3D[uCount + 1];
			boolean hilited = vHilite == i;
			Color c = null;
			if (useColor) {
				if ( !(useHilite && hilited)) {
					int brightness = useHilite && !whiteBackground? 175 : 255;
					c = new Color(brightness/3, (int)(brightness* ((double)(vCount-i)/vCount)), (int)(brightness * ((double)i/vCount)) );
				}
			}
			else {
				if (useHilite && hilited)
					c = Color.RED;
			}
			view.setColor(c);
			for (int j = 0; j <= uCount; j++) {
				if (grid[j][i].abs2() > 1000000) 
					curve[j] = new Vector3D(0, 0, 1);
				else{
					double[] p = grid[j][i].stereographicProjection();
					curve[j] = new Vector3D(p[0],p[1],p[2]);
				}
			}
			if (useHilite && hilited) {
				view.setStrokeSizeMultiplier(2);
				view.drawCurve(curve);
				view.setStrokeSizeMultiplier(1);
			}
			else
				view.drawCurve(curve);
		}
		view.setColor(saveColor);
	}


	/**
	 * Returns an animation that shows the domain grid for a short time, then switches
	 * to the image.  (However, no create animation is shown in "stereograph" views,
	 * since it would involve changing the window size back and forth.)
	 */
	public Animation getCreateAnimation(View view) {
		if ( ! (view instanceof ConformalMapView ) )
			return null;
		final ConformalMapView cmView = (ConformalMapView)view;
		if (cmView.getViewStyle() != View3D.MONOCULAR_VIEW && cmView.getViewStyle() != View3D.RED_GREEN_STEREO_VIEW)
			return null;
		return new ThreadedAnimation() {
			public void runAnimation() {
				try {
					int pauseTime = cmView.getShowBothArgAndValue()? 100 : 50;
					int uCount = ures.getValue() * POINTS_PER_INTERVAL;
					int vCount = vres.getValue() * POINTS_PER_INTERVAL;
					if ( ! cmView.getShowBothArgAndValue() )
						cmView.setDrawValueGrid(false);
					pause(300);
					for (int i = 0; i <= uCount; i += POINTS_PER_INTERVAL) {
						cmView.setHilite(i,vCount+1);
						pause(pauseTime);
					}
					cmView.setHilite(uCount + 1, vCount+1);
					pause(120);
					for (int i = 0; i <= vCount; i += POINTS_PER_INTERVAL) {
						cmView.setHilite(uCount+1,i);
						pause(pauseTime);
					}
					if (cmView.getShowBothArgAndValue())
						return;
					cmView.setHilite(-1,-1);
					pause(300);
					cmView.setDrawValueGrid(true);
					pause(300);
					pauseTime = 80;
					for (int i = 0; i <= uCount; i += POINTS_PER_INTERVAL) {
						cmView.setHilite(i,vCount+1);
						pause(pauseTime);
					}
					cmView.setHilite(uCount + 1, vCount+1);
					pause(120);
					for (int i = 0; i <= vCount; i += POINTS_PER_INTERVAL) {
						cmView.setHilite(uCount+1,i);
						pause(pauseTime);
					}
				}
				finally  {
					cmView.setHilite(-1,-1);
					cmView.setDrawValueGrid(true);
				}
			}
		};
	}
	

	/**
	 * Overridden to return a view of type ConformalMapView, defined by a nested class in this class.
	 */
	public View getDefaultView() {
		ConformalMapView view = new ConformalMapView();
		view.setShowBothArgAndValue(true);
		return view;
	}
	
	
	public ActionList getActionsForView(final View view) {
		ActionList actions = super.getActionsForView(view);
		if (! (view instanceof ConformalMapView))
			return actions;
		actions.add(null); // separator in menu
		actions.add(new AbstractActionVMM(I18n.tr("vmm.conformalmap.ConformalMap.GetLineSegment")) {
			public void actionPerformed(ActionEvent evt) {
				new GetFigure((ConformalMapView)view,ConformalMapFigure.LINE_SEGMENT,TwoPointInput.DRAW_LINE);
			}
		});
		actions.add(new AbstractActionVMM(I18n.tr("vmm.conformalmap.ConformalMap.GetLine")) {
			public void actionPerformed(ActionEvent evt) {
				new GetFigure((ConformalMapView)view,ConformalMapFigure.LINE,TwoPointInput.DRAW_LINE);
			}
		});
		actions.add(new AbstractActionVMM(I18n.tr("vmm.conformalmap.ConformalMap.GetLCircle")) {
			public void actionPerformed(ActionEvent evt) {
				new GetFigure((ConformalMapView)view,ConformalMapFigure.CIRCLE,TwoPointInput.DRAW_CIRCLE_FROM_RADIUS);
			}
		});
		actions.add(new AbstractActionVMM(I18n.tr("vmm.conformalmap.ConformalMap.RemoveFigures")) {
			public void actionPerformed(ActionEvent evt) {
				((ConformalMapView)view).removeFigures();
			}
		});
		actions.add(null);
		ActionList submenu = new ActionList(I18n.tr("vmm.conformalmap.ConformalMap.preComp"));
		submenu.add(preCompSelect);
		actions.add(submenu);
		submenu = new ActionList(I18n.tr("vmm.conformalmap.ConformalMap.postComp"));
		submenu.add(postCompSelect);
		actions.add(submenu);
		
		submenu = new ActionList(I18n.tr("vmm.conformalmap.ConformalMap.gridChoice"));
		submenu.add(gridTypeSelect);
		actions.add(submenu);
		
		return actions;
	}
	
	
	
	
	
	public void addExtraXML(Document containingDocument, Element exhibitElement) {
		super.addExtraXML(containingDocument, exhibitElement);
		gridData[gridType][0] = umin.getValue();  // make sure that saved gridData for current grid type agrees with u,v limits
		gridData[gridType][1] = umax.getValue();
		gridData[gridType][2] = vmin.getValue();
		gridData[gridType][3] = vmax.getValue();
		gridData[gridType][4] = ures.getValue();
		gridData[gridType][5] = vres.getValue();
		Element gridElement = containingDocument.createElement("gridLimits");
		gridElement.setAttribute("cartesian", Util.toExternalString(gridData[0]));
		gridElement.setAttribute("polar", Util.toExternalString(gridData[1]));
		gridElement.setAttribute("polarconformal", Util.toExternalString(gridData[2]));
		exhibitElement.appendChild(gridElement);
	}


	public void readExtraXML(Element exhibitInfo) throws IOException {
		super.readExtraXML(exhibitInfo);
		Element gridElement = SaveAndRestore.getChildElement(exhibitInfo, "gridLimits");
		if (gridElement == null)
			return;
		String s;
		s = gridElement.getAttribute("cartesian").trim();
		if (s.length() == 0)
			throw new IOException(I18n.tr("vmm.conformalmap.ConformalMap.MissingAttributeError","cartesian"));
		double[] cartesian = (double[])Util.externalStringToValue(s, double[].class);
		if (cartesian.length != 6)
			throw new IOException(I18n.tr("vmm.conformalmap.ConformalMap.IncorrectArrayLength","cartesian"));
		s = gridElement.getAttribute("polar").trim();
		if (s.length() == 0)
			throw new IOException(I18n.tr("vmm.conformalmap.ConformalMap.MissingAttributeError","polar"));
		double[] polar = (double[])Util.externalStringToValue(s, double[].class);
		if (polar.length != 6)
			throw new IOException(I18n.tr("vmm.conformalmap.ConformalMap.IncorrectArrayLength","polar"));
		s = gridElement.getAttribute("polarconformal").trim();
		if (s.length() == 0)
			throw new IOException(I18n.tr("vmm.conformalmap.ConformalMap.MissingAttributeError","polarconformal"));
		double[] polarconformal = (double[])Util.externalStringToValue(s, double[].class);
		if (polarconformal.length != 6)
			throw new IOException(I18n.tr("vmm.conformalmap.ConformalMap.IncorrectArrayLength","polarconformal"));
		gridData = new double[][] { cartesian, polar, polarconformal };
		umin.reset(gridData[gridType][0]);  // make sure u,v limikts agree with gridData for current grid type
		umax.reset(gridData[gridType][1]);
		vmin.reset(gridData[gridType][2]);
		vmax.reset(gridData[gridType][3]);
		ures.reset((int)gridData[gridType][4]);
		vres.reset((int)gridData[gridType][5]);
	}





	/**
	 * Used as a replacement for BasicMouseTask2D.  Responds to a Shift-right-click (or shift-command-click on Mac)
	 * by showing the domain grid of the map until the mouse button is released.  A mouse task of
	 * this type is used in a ConformalMapView when 3D is disabled.
	 */
	private static class ConformalMapMouseTask2D extends BasicMouseTask2D {
        private boolean commandShifted;  // Is it this class that's handling the mouse operation?
		public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
			if (evt.isShiftDown() && evt.isMetaDown()) {
				ConformalMapView cmView = (ConformalMapView)display.getView();
				cmView.setDrawValueGrid(false);
				cmView.forceRedraw();
				commandShifted = true;
				return true;
			}
			else {
				commandShifted = false;
				return super.doMouseDown(evt, display, view, width, height);
			}
		}
		public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
			if ( ! commandShifted )
				super.doMouseDrag(evt, display, view, width, height);
		}
		public void doMouseUp(MouseEvent evt, Display display, View view, int width, int height) {
			if ( commandShifted ) {
				ConformalMapView cmView = (ConformalMapView)display.getView();
				cmView.setDrawValueGrid(true);
				cmView.forceRedraw();
			}
			else
				super.doMouseUp(evt, display, view, width, height);
		}
	}

	
	/**
	 * Used as a replacement for BasicMouseTask3D.  Responds to a Shift-right-click (or shift-command-click on Mac)
	 * by showing the domain grid of the map until the mouse button is released.   A mouse task of
	 * this type is used in a ConformalMapView when 3D is enabled.
	 */
	private static class ConformalMapMouseTask3D extends BasicMouseTask3D {
        private boolean commandShifted;  // Is it this class that's handling the mouse operation?
		public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
			if (evt.isShiftDown() && evt.isMetaDown()) {
				ConformalMapView cmView = (ConformalMapView)display.getView();
				cmView.setDrawValueGrid(false);
				cmView.forceRedraw();
				commandShifted = true;
				return true;
			}
			else {
				commandShifted = false;
				return super.doMouseDown(evt, display, view, width, height);
			}
		}
		public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
			if ( ! commandShifted )
				super.doMouseDrag(evt, display, view, width, height);
		}
		public void doMouseUp(MouseEvent evt, Display display,View view, int width, int height) {
			if ( commandShifted ) {
				ConformalMapView cmView = (ConformalMapView)display.getView();
				cmView.setDrawValueGrid(true);
				cmView.drawValueGrid = true;
				cmView.forceRedraw();
			}
			else
				super.doMouseUp(evt, display, view, width, height);
		}
	}

	
	/**
	 * A one-shot mouse task that is used to add a ConformalMapFigure to a conformal map.
	 */
	private class GetFigure extends TwoPointInput {
		boolean canceled = true;
		int shape;
		int pointCount;
		ConformalMapView mainView;
		boolean forAuxiliaryView;
		GetFigure(ConformalMapView view, int shape, int whatToDraw) {
			mainView = view;
			view.getDisplay().stopAnimation();
			view.use3DToggle.setEnabled(false);
			this.shape = shape;
			pointCount = shape == ConformalMapFigure.CIRCLE ? pointsOnCircleFigure :
									shape == ConformalMapFigure.LINE ? pointsOnLineFigure : pointsOnLineSegmentFigure;
			setFigureToDraw(whatToDraw);
			setDrawColor(view.getForeground());
			if (view.getShowBothArgAndValue()) {
				view.getDisplay().installAuxiliaryOneShotMouseTask(this);
				forAuxiliaryView = true;
			}
			else {
				view.setDrawValueGrid(false);
				view.getDisplay().installOneShotMouseTask(this);
				forAuxiliaryView = false;
			}
		}
		protected void gotPoints(Display display, View viewWhereDragged, int startX, int startY, int endX, int endY) {
			canceled = false;
			Point2D p1, p2;
			p1 = new Point2D.Double(startX,startY);
			p2 = new Point2D.Double(endX,endY);
			viewWhereDragged.getTransform().viewportToWindow(p1);
			viewWhereDragged.getTransform().viewportToWindow(p2);
			ConformalMapFigure dec = new ConformalMapFigure(p1,p2,shape,pointCount);
			if (shape == ConformalMapFigure.CIRCLE)
				dec.setColor(Color.GREEN);
			else
				dec.setColor(Color.RED);
			mainView.addDecoration(dec);
			if ( ! forAuxiliaryView ) {
				display.installAnimation( new ThreadedAnimation() {
					protected void runAnimation() {
						try {
							pause(1000);
						}
						finally {
							mainView.setDrawValueGrid(true);
						}
					} 
				});
			}
		}
		public void finish(Display display, View view) {
			if (canceled && ! forAuxiliaryView)
				mainView.setDrawValueGrid(true);
			mainView.use3DToggle.setEnabled(true);
		}
		public String getStatusText() {
			return I18n.tr("vmm.conformalmap.ConformalMap.inputFigurePrompt");
		}
	}

	
	//------------ The rest of this file defines the ConformalMapView class --------------
	
	/**
	 * A class that defines the default view for a ConformalMap.  This subclass of View3D uses
	 * the enableThreeD property of View3D -- that property was introduced to make it possible to
	 * switch back and forth between 2D and 3D, which is something that is needed for conformal maps
	 * (and possibly nowhere else).  See {@link View3D#setEnableThreeD(boolean)}.
	 */
	public static class ConformalMapView extends View3D {
		@VMMSave private boolean use3D;
		@VMMSave private boolean useColor = true;
		private boolean drawValueGrid = true;
		private int uHilite = -1;  // For create animation -- if >= 0, tells which u-curve to highlight
		private int vHilite = -1;  // For create animation -- if >= 0, tells which v-curve to highlight
		@VMMSave private boolean showBothArgAndValue;
		private ConformalMapView argView;  // for when the display is split, while showing both domain and image
		private boolean isArgView;  // set to true if this is the argView for a main ConformalMapView 
		private ToggleAction useColorToggle = new ToggleAction(I18n.tr("vmm.conformalmap.ConformalMap.ToggleUseColor"), true) {
			public void actionPerformed(ActionEvent evt) {
				setUseColor( !useColor );
			}
		};
		private ToggleAction showArgAndValueToggle = new ToggleAction(I18n.tr("vmm.conformalmap.ConformalMap.ToggleShowArgAndValue"), false) {
			public void actionPerformed(ActionEvent evt) {
				if (getDisplay() != null)
					getDisplay().stopAnimation();
				setShowBothArgAndValue( getState() );
			}
		};
		protected ToggleAction use3DToggle = new ToggleAction(I18n.tr("vmm.conformalmap.ConformalMap.ToggleUse3D"), false) {
			public void actionPerformed(ActionEvent evt) {
				if (getDisplay() != null)
					getDisplay().stopAnimation();
				setUse3D( use3DToggle.getState() );
			}
		};
		public ConformalMapView() {
			setAntialiased(true);
			setEnableThreeD(false);
			setShowAxes(true);
		}
		public void removeFigures() {
			Decoration[] decs = getDecorations();
			for (int i = decs.length-1; i >= 0; i--) {
				if (decs[i] instanceof ConformalMapFigure)
					removeDecoration(decs[i]);
			}
			if (argView != null)
				argView.removeFigures();
		}
		public void setExhibit(Exhibit exhibit) {
			if (exhibit == getExhibit())
				return;
			super.setExhibit(exhibit);
			if (exhibit != null && exhibit instanceof ConformalMap) {
				ConformalMap c = (ConformalMap)exhibit;
				double[] d = c.getDefaultWindow2D();
				setWindowForUseWhileThreeDDisabled(d[0], d[1], d[2], d[3]);
				if (showBothArgAndValue)
					setUpArgView();
			}
		}
		/**
		 * Tells whether grid lines are drawn in colors.
		 */
		public boolean getUseColor() {
			return useColor;
		}
		/**
		 * Set whether to use a range of colors for the grid lines.  The alternative is to draw them
		 * all using the view's foreground color.  (This property is managed by a a checkbox.)
		 */
		public void setUseColor(boolean useColor) {
			if (useColor == this.useColor)
				return;
			useColorToggle.setState(useColor);  // make sure useColor property and ToggleAction are in sync
			forceRedraw();
			this.useColor = useColor;
			if (argView != null) {
				argView.useColor = useColor;
				argView.forceRedraw();
			}
		}
		/**
		 * Tells whether the value grid (the image of the function) is currently displayed, as opposed
		 * to the domain grid.  (Note that the set method for this property if private.)
		 */
		public synchronized boolean getDrawValueGrid() {
			return drawValueGrid;
		}
		private synchronized void setDrawValueGrid(boolean drawValues) {
			if (drawValues != drawValueGrid) {
				drawValueGrid = drawValues;
				if (use3D)
					setEnableThreeD(drawValueGrid);
				forceRedraw();
			}
		}
		/**
		 * Overridden to return a mouse task that lets the user switch between the view of the
		 * domain grid and the view of the value grid by shift-right-clicking (or shift-command-clicking
		 * on a Macintosh).
		 */
		public MouseTask getDefaultMouseTask() {
			if (isArgView) {
				return new BasicMouseTask2D();
			}
			else if (showBothArgAndValue) {
				if (use3D)
					return new BasicMouseTask3D();
				else
					return new BasicMouseTask2D();
			}
			else {
				if (use3D)
					return new ConformalMapMouseTask3D();
				else
					return new ConformalMapMouseTask2D();
			}
		}
		public ActionList getActions() {
			ActionList actions = super.getActions();
			actions.add(showArgAndValueToggle);
			actions.add(useColorToggle);
			actions.add(use3DToggle);
			return actions;
		}
		public void takeExhibit(View view, boolean shareTransform) {
			super.takeExhibit(view,shareTransform);
			if (shareTransform && (view instanceof ConformalMapView))
				setUse3D( ((ConformalMapView)view).getUse3D());
		}
		public boolean getUse3D() {
			return use3D;
		}
		/**
		 * Sets whether to enable 3D in the view; this method is really not meant to
		 * be called except by this class.  This method is public only for use with
		 * SaveAndRestore.
		 */
		public void setUse3D(boolean use3D) {
			if (this.use3D == use3D)
				return;
			this.use3D = use3D;
			use3DToggle.setState(use3D);
			setEnableThreeD(use3D);
			if (getDisplay() != null)
				getDisplay().installMouseTask(getDefaultMouseTask());
			if (showBothArgAndValue)
				viewStyleCommands.setEnabled(false);
		}
		synchronized private void setHilite(int uHilite, int vHilite) {
			this.uHilite = uHilite;
			this.vHilite = vHilite;
			forceRedraw();
			if (argView != null) {
				argView.uHilite = uHilite;
				argView.vHilite = vHilite;
				argView.forceRedraw();
			}
		}
		synchronized private int getUHilite() {
			return uHilite;
		}
		synchronized private int getVHilite() {
			return vHilite;
		}
		public boolean getShowBothArgAndValue() {
			return showBothArgAndValue;
		}
		public void setShowBothArgAndValue(boolean showBothArgAndValue) {
			if (this.showBothArgAndValue == showBothArgAndValue)
				return;
			this.showBothArgAndValue = showBothArgAndValue;
			showArgAndValueToggle.setState(showBothArgAndValue);
			if (showBothArgAndValue) {
				argView = new ConformalMapView();
				argView.isArgView = true;
				setUpArgView();
				if (getDisplay() != null) {
					getDisplay().installMouseTask(getDefaultMouseTask());
					getDisplay().installAuxiliaryView(this,argView);
				}
			}
			else {
				if (getDisplay() != null) {
					getDisplay().installMouseTask(getDefaultMouseTask());
					getDisplay().installAuxiliaryView(this, null);
				}
				argView = null;
				if (getUse3D())
					viewStyleCommands.setEnabled(true);
			}
		}
		private void setUpArgView() {
			if (argView != null) {
				if (getUse3D())
					setViewStyle(View3D.MONOCULAR_VIEW);
				viewStyleCommands.setEnabled(false);
				if (getExhibit() != null)
					argView.takeExhibit(this, true);
				argView.setUse3D(false);
				argView.setDrawValueGrid(false);
				argView.setBackground(getBackground());
				argView.setShowAxes(getShowAxes());
				argView.useColor = useColor;
				for (Decoration d : getDecorations()) {
					if (d instanceof ConformalMapFigure) {
						ConformalMapFigure orig = (ConformalMapFigure)d;
						ConformalMapFigure copy = new ConformalMapFigure(orig.getP1(),orig.getP2(),orig.getShape(),orig.getPointCount());
						copy.setColor(orig.getColor());
						argView.addDecoration(copy);
					}
				}
			}
		}
		public void setDisplay(Display display) {
			super.setDisplay(display);
			if (display != null)
				display.setStopAnimationsOnResize(false);
			if (display != null && showBothArgAndValue) {
				display.installAuxiliaryView(this, argView);
			}
		}
		public void setBackground(Color c) {
			super.setBackground(c);
			if (argView != null)
				argView.setBackground(c);
		}
		public void setShowAxes(boolean show) {
			super.setShowAxes(show);
			if (argView != null)
				argView.setShowAxes(show);
		}
		public void addDecoration(Decoration d) {
			super.addDecoration(d);
			if (d instanceof ConformalMapFigure && argView != null) {
				ConformalMapFigure orig = (ConformalMapFigure)d;
				ConformalMapFigure copy = new ConformalMapFigure(orig.getP1(),orig.getP2(),orig.getShape(),orig.getPointCount());
				copy.setColor(orig.getColor());
				argView.addDecoration(copy);
			}
		}
		public void readExtraXML(Element viewInfo) throws IOException {
			super.readExtraXML(viewInfo);
			if (showBothArgAndValue) {
				argView.setTransform( getEnableThreeD()? getSavedAuxiliaryTransformForEnableThreeD(): getTransform());
			}
		}
	} // end class ConformalMapView
	

}