/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.fractals;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.event.ChangeEvent;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.core.BasicMouseTask2D;
import vmm.core.Complex;
import vmm.core.TaskManager;
import vmm.core.Decoration;
import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.IntegerParam;
import vmm.core.MouseTask;
import vmm.core.Parameter;
import vmm.core.RealParam;
import vmm.core.Transform;
import vmm.core.Util;
import vmm.core.VMMSave;
import vmm.core.View;

/**
 * The Mandelbrot set.  The exhibit has an associated decoration that can show 
 * an approximation of the outline of the
 * Julia set and the zero-orbit associated with a point (cx,cy).
 * This decoration is not visible by default, but can be made visible in a view of the
 * exhibit.  If it is visible, the start point (cx,cy) is marked with a white cross,
 * and the user can drag this cross; in addition to the cross, either the Julia set
 * or the zero-orbit or both can be shown.  The coordinates cx, cy are given
 * by the values of two parameters that are associated with the exhibit, so they can
 * also be set directly.
 * <p>This exhibit differs from others in that the basic data needed to 
 * draw the exhibit is a bitmap.  To make this work properly, a nested View class is
 * defined that works only for Mandelbrot exhibits.  The view class overrides the render()
 * method from the base {@link View} class, which means that it is using a completly
 * different rendering system from other exhibits.  Various other changes were made
 * that are unique to the Mandelbrot exhibit.  Since this might be the only exhibit
 * where the basic data is a bitmap, no attempt was made to make the modifications in
 * a general way.  Note that the View for a Mandelbrot exhibit MUST be of type
 * Mandelbrot.MandelbrotView.
 */
public class Mandelbrot extends Exhibit {

	/**
	 * The decoration that is responsible for showing the Julia set and zero-orbit.  What is actually
	 * shown depends on the setting of the showJuliaAndOrbit property of the View class, but the
	 * decoration is always present on the exhibit, even if nothing is visible.
	 */
	private JuliaSetAndOrbitDecoration juliaAndOrbitDecoration;
	
	/**
	 * The number of points that are shown on an orbit.
	 */
	private IntegerParam pointsOnOrbit = new IntegerParam("vmm.fractals.Mandelbrot.PointsOnOrbit", 100);
	
	/**
	 * The x-coord of the point for which the Julia set and zero-orbit are shown.
	 */
	private RealParam juliaPointX = new RealParam("vmm.fractals.Mandelbrot.juliaPointX", 0.25);

	/**
	 * The y-coord of the point for which the Julia set and zero-orbit are shown.
	 */
	private RealParam juliaPointY = new RealParam("vmm.fractals.Mandelbrot.juliaPointY", 0.45);
	
	
	private TaskManager taskManager; // used for computing the image asynchronously
	
	private double[][] exampleData = {
		    { -0.7241608526756182,-0.7241608526694334,0.36158285101080495,0.3615828510154436,1000,250 },
		    { -1.9072326638218555,-1.9072326346322426,-1.0859414558092964E-8,1.103279515362283E-8,500,100 },
		    { 0.35471345463684467,0.35473822122425647,0.095401040922098,0.09541961586265685,4000,2500 },
		    { 0.2726031397857463,0.2726031483804942,0.0053565210436868176,0.005356527489747753,7500,2500 },
		    { 0.28601560167064516,0.2860156017191516,0.011537485975923616,0.011537486012303432,3000,250 },
		    { -1.6744096756044493,-1.6744096717769306,4.716419197284976E-5,4.7167062611931696E-5,7500,1000 },
		    { 0.25989953593561266,0.259899641345385,0.001612579325834812,0.0016126583831640785,2000,250 },
		    { -1.674409674093473,-1.6744096740931858,4.716540768697223E-5,4.716540790246652E-5,10000,250 },
		    { -1.4035289973308978,-1.4035289973294278,0.02930868838864931,0.029308688389751928,2500,500 },
		    { -1.9072311984370052,-1.9072311928858665,1.5197004423572747E-5,1.5201167777464862E-5,7500,250 },
		    { -0.753231765876289,-0.7532317565038811,0.04633550302364065,0.046335510285468194,10000,250 },
		    { -1.9963806954442953,-1.996380695443582,2.62870483517615E-7,2.628710361171417E-7,1500,250 },
		    { 0.29768460024540017,0.297684633418743,0.020961285679467805,0.020961312432163624,5000,1000}
		};
	
	public Mandelbrot() {
		addParameter(juliaPointY);
		addParameter(juliaPointX);
		addParameter(pointsOnOrbit);
		pointsOnOrbit.setMinimumValueForInput(1);
		pointsOnOrbit.setMaximumValueForInput(1000);
		juliaPointX.setMinimumValueForInput(-4);
		juliaPointX.setMaximumValueForInput(3);
		juliaPointY.setMinimumValueForInput(-3);
		juliaPointY.setMaximumValueForInput(3);
		juliaAndOrbitDecoration = new JuliaSetAndOrbitDecoration();
		juliaAndOrbitDecoration.setStartPoint(new Point2D.Double(juliaPointX.getValue(),juliaPointY.getValue()));
		addDecoration(juliaAndOrbitDecoration);
		setDefaultWindow(-2.25, 0.85, -1.25, 1.25);
	}


	public void removeView(View view) {
		super.removeView(view);
		if (taskManager != null && (getViews() == null  || getViews().size() == 0)) {
			taskManager.shutDown();
			taskManager = null;
		}			
	}


	/**
	 * This method is overridden so that in addition to redrawing the Exhibit, it also sets the
	 * Julia set point, (cx,cy), to match the values of the associated parameters.
	 */
	public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
		super.parameterChanged(param, oldValue, newValue);
		juliaAndOrbitDecoration.setStartPoint(new Point2D.Double(juliaPointX.getValue(),juliaPointY.getValue()));
	}


	/**
	 * Draws the Mandelbrot set to the view's bitmap.
	 */
	private void drawMandelbrot(final View view, final Transform transform) {
		int height = transform.getHeight();
		MandelbrotView mView = (MandelbrotView)view;
		mView.cancelAsyncComputeJob();
		if (view.getFastDrawing()) {
			int max = mView.maxCount.getValue();
			int paletteLength = mView.paletteLength.getValue();
			int width = transform.getWidth();
			Point2D pt = new Point2D.Double();
			int blockSize = 10;
			if (max <= 50)
				blockSize = 3;
			else if (max <= 500)
				blockSize = 6;
			for (int r = blockSize; r < height + blockSize; r += blockSize*2)
				for (int c = blockSize; c < width + blockSize; c += blockSize*2) {
					pt.setLocation(c,r);
					transform.viewportToWindow(pt);
					double x = pt.getX();
					double y = pt.getY();
					int ct = iterate(x,y,max);
					Color color;
					if (ct == max)
						color = Color.BLACK;
					else {
						float hue;
						if (paletteLength > 0)
							hue = (float)(ct % paletteLength)/paletteLength;
						else
							hue = (float)ct/max;
						color = Color.getHSBColor(hue, 1, 1);
					}
					mView.setColor(color);
					mView.fillRectDirect(c-blockSize,r-blockSize,blockSize*2,blockSize*2);
				}
		}
		else {
			view.getDisplay().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			if (taskManager == null)
				taskManager = new TaskManager();
			mView.fillCanvasWithGray();
			TaskManager.Job job = taskManager.createJob();
			int jobNumber = ++mView.asyncJobNum;
			for (int r = 0; r < height; r++)
				job.add(new ComputeRow(r, mView, jobNumber));
			job.close();
			if (!job.await(500))
				mView.startAsyncComputeJob(job);
			else
				view.getDisplay().setCursor(Cursor.getDefaultCursor());
		}
	}

	
	private class ComputeRow implements Runnable {
		int jobNum;
		int row;
		MandelbrotView view;
		Transform transform;
		int max;
		int paletteLength;
		ComputeRow(int row, MandelbrotView view, int jobNum) {
			this.row = row;
			this.view = view;
			this.jobNum = jobNum;
			transform = (Transform)view.getTransform().clone();
			max = view.maxCount.getValue();
			paletteLength = view.paletteLength.getValue();
		}
		public void run() {
			Point2D pt = new Point2D.Double();
			int width = transform.getWidth();
			int[] rgb = new int[width];
			for (int c = 0; c < width; c++) {
				pt.setLocation(c,row);
				transform.viewportToWindow(pt);
				double x = pt.getX();
				double y = pt.getY();
				int ct = iterate(x,y,max);
				if (ct == max)
					rgb[c] = 0;
				else {
					float hue;
					if (paletteLength > 0)
						hue = (float)(ct % paletteLength)/paletteLength;
					else
						hue = (float)ct/max;
					rgb[c] = Color.HSBtoRGB(hue, 1, 1);
				}
			}
			if (jobNum == view.asyncJobNum)
				view.pixelRow(rgb,row);
		}
	}
	
	/**
	 * Does the Mandelbrot iteration, z = z^2 + c, and counts the number of steps to
	 * get 2 units or more from the origin.
	 * @param cx  x-coord of the constant c
	 * @param cy  y-coord of the constant c
	 * @param max  the maximum number of iterations.
	 * @return the number of iterations to get more than 2 units from the origin, or the max
	 *    value if the process does not stop before that many iterations
	 */
	private int iterate(double cx, double cy, int max) {
		int ct = 0;
		double zx = cx;
		double zy = cy;
		while (ct < max && zx*zx + zy*zy <= 4) {
			double t = zx*zx - zy*zy + cx;
			zy = 2*zx*zy + cy;
			zx = t;
			ct++;
		}
		return ct;
	}

	public ActionList getActionsForView(final View view) {
		ActionList actions = super.getActionsForView(view);
		actions.add( new AbstractActionVMM(I18n.tr("vmm.fractals.Mandelbrot.DragToShowCoords")) {
			public void actionPerformed(ActionEvent evt) {
				view.getDisplay().installOneShotMouseTask(new ShowCoordsMouseTask());
			}
		});
		actions.add( new AbstractActionVMM(I18n.tr("vmm.fractals.Mandelbrot.RecenterOnPointMenuItem")+"...") {
			public void actionPerformed(ActionEvent evt) {
				doCenterOnUserPoint(view);
			}
		});
		ActionList zoomIn = new ActionList(I18n.tr("vmm.fractals.Mandelbrot.ZoomIn"));
		for (int zoom = 2; zoom <= 32; zoom  *= 2) {
			final double z = zoom;
			zoomIn.add(new AbstractActionVMM(I18n.tr("vmm.fractals.Mandelbrot.ZoomIn") + " " + zoom + "X") {
				public void actionPerformed(ActionEvent evt) {
					((MandelbrotView)view).doZoom(z);
				}
			});
		}
		actions.add(zoomIn);
		ActionList zoomOut = new ActionList(I18n.tr("vmm.fractals.Mandelbrot.ZoomOut"));
		for (int zoom = 2; zoom <= 32; zoom  *= 2) {
			final double z = 1.0/zoom;
			zoomOut.add(new AbstractActionVMM(I18n.tr("vmm.fractals.Mandelbrot.ZoomOut") + " " + zoom + "X") {
				public void actionPerformed(ActionEvent evt) {
					((MandelbrotView)view).doZoom(z);
				}
			});
		}
		actions.add(zoomOut);
		actions.add( new AbstractActionVMM(I18n.tr("vmm.fractals.Mandelbrot.RestoreDefaults")) {
			public void actionPerformed(ActionEvent evt) {
				MandelbrotView mView = (MandelbrotView)view;
				mView.maxCount.setValue(mView.maxCount.getDefaultValue());
				mView.paletteLength.setValue(mView.paletteLength.getDefaultValue());
				mView.setWindow(getDefaultWindow());
				mView.setShowJuliaAndOrbit(MandelbrotView.SHOW_NONE);
			}
		});
		ActionList examples = new ActionList(I18n.tr("vmm.fractals.Mandelbrot.Examples"));
		for (int i = 0; i < exampleData.length; i++) {
			final int exampleNum = i;
			examples.add(new AbstractActionVMM("  " + (i+1) + "  ") {
				public void actionPerformed(ActionEvent evt) {
					double[] data = exampleData[exampleNum];
					MandelbrotView mView = (MandelbrotView)view;
					mView.setWindow(data[0],data[1],data[2],data[3]);
					mView.maxCount.setValue((int)data[4]);
					mView.paletteLength.setValue((int)data[5]);
					mView.setShowJuliaAndOrbit(MandelbrotView.SHOW_NONE);
				}
			});
		}
		actions.add(examples);
		actions.add(null);
		actions.add( new AbstractActionVMM(I18n.tr("vmm.fractals.Mandelbrot.MoveJuliaPoint")) { 
			public void actionPerformed(ActionEvent evt) {
				view.getDisplay().installOneShotMouseTask(new MoveJuliaStartByMouseClick());
			}
		});
		return actions;
	}


	/**
	 * Lets user enter the x and y coords of the point at the center of the display.
	 */
	private void doCenterOnUserPoint(View view) {
		Point2D newCenter = Util.getPoint2DFromUser(view.getDisplay(), I18n.tr("vmm.fractals.Mandelbrot.RecenterOnPointPrompt"));
		if (newCenter == null)
			return;
		Transform tr = view.getTransform();
		double xCenter = (tr.getXmax() + tr.getXmin()) / 2;
		double yCenter = (tr.getYmax() + tr.getYmin()) / 2;
		double newX = newCenter.getX();
		double newY = newCenter.getY();
		double offsetX = newX - xCenter;
		double offsetY = newY - yCenter;
		if (offsetX == 0 && offsetY == 0)
			return;
		tr.setLimits( tr.getXmin() + offsetX, tr.getXmax() + offsetX, tr.getYmin() + offsetY, tr.getYmax() + offsetY);
	}


	/**
	 * Overridden to return an object belonging to the nested class MandelbrotView.  Note that
	 * the View for a Mandelbrot exhibit MUST be of this type.
	 */
	public View getDefaultView() {
		return new MandelbrotView();
	}


	/**
	 * A View class suitable for viewing a Mandelbrot exhibit.  The view for a Mandelbrot exhibit MUST
	 * be of this type.  This class modifies the usual behavior of a View extensively to account for
	 * the fact that the basic data for a Mandelbrot exhibit is the bitmap on which the Mandelbrot is
	 * drawn.  This view class has been rigged to make sure that this  bitmap is not redrawn more
	 * often than necessary.  For example, decorations are not drawn to the bitmap; instead, they
	 * are drawn over the bitmap each time the view is repainted.
	 */
	public class MandelbrotView extends View {
		public static final int SHOW_JULIA = 0;
		public static final int SHOW_ORBIT = 1;
		public static final int SHOW_BOTH = 2;
		public static final int SHOW_NONE = 3;
		@VMMSave private int showJuliaAndOrbit = SHOW_NONE;
		private ActionRadioGroup showJuliaAndOrbitSelect;
		private Transform previousTransform = null;
		private boolean bitmapNeedsRedraw = true;
		private volatile int asyncJobNum;
		private volatile Thread asyncComputeThread;
		private volatile TaskManager.Job asyncComputeJob;
		private IntegerParam maxCount = new IntegerParam("vmm.fractals.Mandelbrot.MaxIters", 50);
		private IntegerParam paletteLength = new IntegerParam("vmm.fractals.Mandelbrot.PaletteLength", 0);
		public MandelbrotView() {
			addParameter(paletteLength);
			addParameter(maxCount);
			maxCount.setMinimumValueForInput(10);
			maxCount.setMaximumValueForInput(100000);
			paletteLength.setMinimumValueForInput(0);
			paletteLength.setMaximumValueForInput(100000);
			showJuliaAndOrbitSelect = new ActionRadioGroup() {
				public void optionSelected(int selectedIndex) {
					setShowJuliaAndOrbit(selectedIndex);
				}
			};
			showJuliaAndOrbitSelect.addItem(I18n.tr("vmm.fractals.Mandelbrot.showJuliaAndOrbit.julia"));
			showJuliaAndOrbitSelect.addItem(I18n.tr("vmm.fractals.Mandelbrot.showJuliaAndOrbit.orbit"));
			showJuliaAndOrbitSelect.addItem(I18n.tr("vmm.fractals.Mandelbrot.showJuliaAndOrbit.both"));
			showJuliaAndOrbitSelect.addItem(I18n.tr("vmm.fractals.Mandelbrot.showJuliaAndOrbit.none"));
			showJuliaAndOrbitSelect.setSelectedIndex(showJuliaAndOrbit);
			backgroundCommands.setEnabled(false);
		}
		/**
		 * Zooms the display by a specified magnification factor.  A magnification factor greater than 1 is a zoom in;
		 * a magnification factor less than 1 is a zoom out.  The magnification factor must be greater than zero
		 * (this is not checked).
		 */
		public void doZoom(double magnificationFactor) {
			double[] window = getRequestedWindow();
			double width = window[1] - window[0];
			double height = window[3] - window[2];
			double centerX = window[0] + width/2;
			double centerY = window[2] + height/2;
			double newWidth = width / magnificationFactor;
			double newHeight = height / magnificationFactor;
			setWindow(centerX - newWidth/2, centerX + newWidth/2, centerY - newHeight/2, centerY + newHeight/2);
		}
	   /**
	    * Make a string showing the coordinates of the image point that corresponds
	    * to pixel coordinates (x,y).
	    */
		String getCoordString(int x, int y) {
			double [] window = getWindow();
			double xmin = window[0];
			double xmax = window[1];
			double ymin = window[2];
			double ymax = window[3];
			double width = getTransform().getWidth();
			double height = getTransform().getHeight();
			double xCoord = xmin + x/width*(xmax-xmin);
			double yCoord = ymax - y/height*(ymax-ymin);
			   // The next 10 lines try to avoid more digits after the decimal
			   // points than makes sense.  If it succeeds the coordinates
			   // that are shown should differ only in their last few digits.
			double diff = xmax - xmin;
			int scale = 4;
			if (diff > 0) {
				while (diff < 1) {
					scale++;
					diff *= 10;
				}
			}
			String xStr = String.format("%1." + scale + "f", xCoord);
			String yStr = String.format("%1." + scale + "f", yCoord);
			return "(" + xStr + ", " + yStr + ")";
		}
		/**
		 * Returns the value of the showJuliaAndOrbit property.
		 * @see #setShowJuliaAndOrbit(int)
		 */
		public int getShowJuliaAndOrbit() {
			return showJuliaAndOrbit;
		}
		/**
		 * Sets the value of the showJuliaAndOrbit property.  A Mandelbrot exhibit has an associated decoration
		 * that is capable of showing an outline of the Julia set and the zero orbit associated with some
		 * value of c.  The showJuliaAndOrbit property of the view determines what is actually shown of this
		 * decoration in this view.  The values are given by constants SHOW_NONE, SHOW_BOTH, SHOW_JUILA,
		 * and SHOW_ORBIT which are defined in this class.
		 */
		public void setShowJuliaAndOrbit(int showJuliaAndOrbit) {
			if (this.showJuliaAndOrbit == showJuliaAndOrbit)
				return;
			this.showJuliaAndOrbit = showJuliaAndOrbit;
			showJuliaAndOrbitSelect.setSelectedIndex(showJuliaAndOrbit);
			if (getDisplay() != null)
				getDisplay().setStatusText();
			forceRedraw();
		}
		public ActionList getActions() {
			ActionList actions = super.getActions();
			actions.add(showJuliaAndOrbitSelect);
			return actions;
		}
		public String getStatusText() {
			if (showJuliaAndOrbit == SHOW_NONE) {
				if (! Util.isMacOS())
					return I18n.tr("vmm.fractals.Mandelbrot.statusText.DragToZoom");
				else
					return I18n.tr("vmm.fractals.Mandelbrot.statusText.DragToZoomMac");
			}
			else
				return I18n.tr("vmm.fractals.Mandelbrot.statusText.dragCross");
		}
		/**
		 * Overridden to force a redraw of the Mandelbrot bitmap.
		 */
		public void setFastDrawing(boolean fast, boolean redrawSynchronizedViews) {
			super.setFastDrawing(fast,redrawSynchronizedViews);
			bitmapNeedsRedraw = true;
			forceRedraw();
		}
		/**
		 * Overridden to force a redraw of the Mandelbrot bitmap if it is the transform that
		 * is changing.
		 */
		public void stateChanged(ChangeEvent evt) {
			super.stateChanged(evt);
			if (evt.getSource() instanceof Transform)
				bitmapNeedsRedraw = true;
		}
		/**
		 * Overridden to force a redraw of the Mandelbrot bitmap if it is the maximum iteration
		 * count that is changing.  (In fact, this is the only parameter defined for this class.)
		 */
		public void parameterChanged(Parameter param, Object oldValue, Object newValue) {
			super.parameterChanged(param, oldValue, newValue);
			if (param == maxCount || param == paletteLength)
				bitmapNeedsRedraw = true;
		}
		public void drawOvalDirect(Color c, int x, int y, int width, int height) {
			Graphics g = getTransform().getUntransformedGraphics();
			g.setColor(c);
			g.drawOval(x,y,width,height);
		}
		public void fillOvalDirect(Color c, int x, int y, int width, int height) {
			Graphics g = getTransform().getUntransformedGraphics();
			g.setColor(c);
			g.fillOval(x,y,width,height);
		}
		/**
		 * The render method has been modified to draw the mandelbrot set directly to the bitmap.
		 * The usual rendering is not used; in particular, the computeDrawData and doDraw methods
		 * of the Mandelbrot exhibit are not called.
		 */
		public void render(Graphics2D g, int width, int height) {
			Graphics2D saveGraphics = (Graphics2D)g.create();
			getTransform().setUpDrawInfo(g,0,0,width,height,getPreserveAspect(),getApplyGraphics2DTransform());
			if (fullOSI == null || getTransform().getWidth() != fullOSI.getWidth() || getTransform().getHeight() != fullOSI.getHeight()) {
				bitmapNeedsRedraw = true;
				try {
					createOSI(width,height);
				}
				catch (OutOfMemoryError e) {
					saveGraphics.setColor(Color.WHITE);
					saveGraphics.fillRect(0,0,width,height);
					saveGraphics.setColor(Color.RED);
					saveGraphics.drawString(I18n.tr("vmm.core.OutOfMemoryError"),20,35);
					return;
				}
			}
			prepareOSIForDrawing();
			if (bitmapNeedsRedraw)	
				drawMandelbrot(this,getTransform());
			bitmapNeedsRedraw = false;
			saveGraphics.drawImage(fullOSI,0,0,null);
			g.setColor(getForeground());
			g.setBackground(getBackground());
			if (getAntialiased())
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			getTransform().setUpDrawInfo(saveGraphics,0,0,width,height,getPreserveAspect(),getApplyGraphics2DTransform());
			currentGraphics = saveGraphics;
			Decoration[] dec = getDecorations();
			for (int i = 0; i < dec.length; i++) {
				dec[i].computeDrawData(this,exhibitNeedsRedraw,previousTransform,getTransform());
				dec[i].doDraw(saveGraphics,this,getTransform());
			}
			dec = Mandelbrot.this.getDecorations();
			for (int i = 0; i < dec.length; i++) {
				dec[i].computeDrawData(this,exhibitNeedsRedraw,previousTransform,getTransform());
				dec[i].doDraw(saveGraphics,this,getTransform());
			}
			getTransform().finishDrawing();
			needsRedraw = false;
		}
		/**
		 * Has been overridden because the usual bitmap for this view does not contain the
		 * decorations.  A new BufferedImage is created that shows both the andelbrot set bitmap and
		 * the decorations.
		 */
		public BufferedImage getImage(boolean alwaysCopy) {
			if (getDisplay() == null || getExhibit() == null || fullOSI == null)
				return null;
			BufferedImage image;
			Graphics2D g;
			image = new BufferedImage(fullOSI.getWidth(),fullOSI.getHeight(),offscreenImageType);
			g = (Graphics2D)image.getGraphics();
			render(g,fullOSI.getWidth(),fullOSI.getHeight());
			g.dispose();
			return image;
		}
		/**
		 * Has been overridden to copy the maximum iteration count, if the shareTransform
		 * parameter is true.  (Otherwise, if the view has been zoomed in a lot, the 
		 * synchronized view might be entirely black.)
		 */
		public void takeExhibit(View view, boolean shareTransform) {
			super.takeExhibit(view,shareTransform);
			if (shareTransform && view instanceof MandelbrotView) {
				maxCount.setValue(((MandelbrotView)view).maxCount.getValue());
				paletteLength.setValue(((MandelbrotView)view).paletteLength.getValue());
			}
		}
		/**
		 * Overridden to return a mouse task that will let the user drag the Julia set
		 * point.  If the user does not click near the start point, the mouse task behaves
		 * the same as a usual BasicMouseTask2D.
		 */
		public MouseTask getDefaultMouseTask() {
			return new DragJuliaStartMouseTask();
		}
		private void fillCanvasWithGray() {
			Graphics g = fullOSI.getGraphics();
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0,0,fullOSI.getWidth(),fullOSI.getHeight());
			g.dispose();
		}
		synchronized private void pixelRow(int[] rgb, int rowNum) {
			fullOSI.setRGB(0, rowNum, fullOSI.getWidth(), 1, rgb, 0, fullOSI.getWidth());
		}
		synchronized private void startAsyncComputeJob(TaskManager.Job job) {
			asyncComputeJob = job;
			asyncComputeThread = new Thread() {
				TaskManager.Job myJob = asyncComputeJob;
				public void run() {
					while (!myJob.await(1000)) 
						doRepaint();
					synchronized(MandelbrotView.this) { 
						if (myJob == asyncComputeJob && !myJob.isCanceled()) {
							doRepaint(); 
							if (getDisplay() != null)
								getDisplay().setCursor(Cursor.getDefaultCursor());
						}
						if (myJob == asyncComputeJob) {
							asyncComputeJob = null;
							asyncComputeThread = null;
						}
					}
				}
				synchronized void doRepaint() {
					if (getDisplay() != null) {
						getDisplay().repaint();
						try {
							wait(10);
						}
						catch (InterruptedException e) {
						}
					}
				}
			};
			asyncComputeThread.start();
		}
		synchronized private void cancelAsyncComputeJob() {
			if (asyncComputeJob != null) { 
				asyncComputeJob.cancel();
				asyncComputeJob = null;
				asyncComputeThread = null;
				if (getDisplay() != null)
					getDisplay().setCursor(Cursor.getDefaultCursor());
			}
		}
	} // end class MandelbrotView

	
	private class ShowCoordsMouseTask extends MouseTask {
		public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
			display.setStatusText("(x,y) = " + ((MandelbrotView)view).getCoordString(evt.getX(), evt.getY()));
			return true;
		}
		public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
			display.setStatusText("(x,y) = " + ((MandelbrotView)view).getCoordString(evt.getX(), evt.getY()));
		}

		public Cursor getCursor(Display display, View view) {
			return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		}
		public String getStatusText() {
			return I18n.tr("vmm.fractals.Mandelbrot.DragToShowCoordsStatusText");
		}
	}


	/**
	 * Defines the mouse task that is used in a MandelbrotView.  Lets the user
	 * drag the point c that tells which Julia set/zero-orbit to draw.
	 */
	private class DragJuliaStartMouseTask extends BasicMouseTask2D {
		boolean draggingStartPoint;
		int offsetX, offsetY;
		public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
			draggingStartPoint = false;
			if (((MandelbrotView)view).getShowJuliaAndOrbit() == MandelbrotView.SHOW_NONE)
				return super.doMouseDown(evt,display,view,width,height);
			Point2D pt = new Point2D.Double(juliaPointX.getValue(),juliaPointY.getValue());
			view.getTransform().windowToViewport(pt);
			offsetX = (int)pt.getX() - evt.getX();
			offsetY = (int)pt.getY() - evt.getY();
			if (Math.abs(offsetX) <= 7 && Math.abs(offsetY) <= 7) {
				draggingStartPoint = true;
				return true;
			}
			else {
				return super.doMouseDown(evt, display, view, width, height);
			}
		}
		public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
			if (!draggingStartPoint) {
				super.doMouseDrag(evt, display, view, width, height);
				return;
			}
			Point2D pt = new Point2D.Double( evt.getX() + offsetX, evt.getY() + offsetY );
			view.getTransform().viewportToWindow(pt);
			juliaPointX.setValue(pt.getX());
			juliaPointY.setValue(pt.getY());
			display.setStatusText("(cx,cy) = " + ((MandelbrotView)view).getCoordString(evt.getX(),evt.getY()));
		}
		public void doMouseUp(MouseEvent evt, Display display, View view, int width, int height) {
			if (!draggingStartPoint)
				super.doMouseUp(evt, display, view, width, height);
			else
				display.setStatusText(null);
		}
		public void drawWhileDragging(Graphics2D g, Display display, View view, int width, int height) {
			if (!draggingStartPoint)
				super.drawWhileDragging(g, display, view, width, height);
		}
		public Cursor getCursorForDragging(MouseEvent evt, Display display, View view) {
			if (draggingStartPoint)
				return Cursor.getDefaultCursor();
			else
				return super.getCursorForDragging(evt, display,view);
		}
	}

	private class MoveJuliaStartByMouseClick extends MouseTask {
		public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
			Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
			view.getTransform().viewportToWindow(pt);
			juliaPointX.setValue(pt.getX());
			juliaPointY.setValue(pt.getY());
			if ( ((MandelbrotView)view).getShowJuliaAndOrbit() == MandelbrotView.SHOW_NONE)
				((MandelbrotView)view).setShowJuliaAndOrbit(MandelbrotView.SHOW_BOTH);
			return false;
		}
		public Cursor getCursor(Display display,View view) {
			return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		}
	}
	
	/**
	 * Defines the decoration that shows the outline of a julia set and zero-orbit.
	 */
	private class JuliaSetAndOrbitDecoration extends Decoration {
		Complex[] juliaStart = new Complex[1024];
		Complex[] juliaAux = new Complex[1024];
		Complex firstFixedPoint, secondFixedPoint;
		int currentJuliaLength = 0;
		double cx,cy;
		public void setStartPoint(Point2D pt) {
			cx = pt.getX();
			cy = pt.getY();
			forceRedraw();
		}
		private void computeFixedPoints() {
			Complex z = new Complex(0.25-cx,-cy);
			z = z.power(0.5);
			firstFixedPoint = z.plus(0.5);
			secondFixedPoint = z.times(-1.0).plus(0.5);
			if (firstFixedPoint.r() < secondFixedPoint.r()) {
				z = secondFixedPoint;
				secondFixedPoint =  firstFixedPoint;
				firstFixedPoint = z;
			}
		}
		private void makeJuliaStart() {
			Complex z;
			Complex c = new Complex(cx,cy);
			computeFixedPoints();
			juliaStart[0] = firstFixedPoint;
			juliaStart[2] = firstFixedPoint.times(-1.0);
			z = juliaStart[2].minus(c).power(0.5);
			if ( (z.re*juliaStart[0].im - z.im*juliaStart[0].re) < 0 ) 
				z = z.times(-1.0);
			juliaStart[1] = z;
			juliaStart[3] = z.times(-1.0);
			currentJuliaLength = 4;
			for (int i=1; i < 9; i++) {
				juliaAux[0] = juliaStart[0];
				juliaAux[0+currentJuliaLength] = juliaStart[0].times(-1.0);
				for (int j=1; j < currentJuliaLength; j++){
					juliaAux[j] = (juliaStart[j].minus(c)).squareRootNearer(juliaAux[j-1]);
					juliaAux[j + currentJuliaLength] = juliaAux[j].times(-1.0);
				}
				currentJuliaLength = 2*currentJuliaLength;
				for (int j=1; j < currentJuliaLength; j++) 
					juliaStart[j] = juliaAux[j]; 
			}
		}
		public void doDraw(Graphics2D g, View view, Transform transform) {
			MandelbrotView mbView = (MandelbrotView)view;
			if (mbView.getShowJuliaAndOrbit() == MandelbrotView.SHOW_NONE)
				return;
			Color saveColor = g.getColor();
			double zx = cx;
			double zy = cy;
			Point2D pt = new Point2D.Double();
			pt.setLocation(cx,cy);
			transform.windowToViewport(pt);
			int cxInt = (int)(pt.getX() + 0.499);
			int cyInt = (int)(pt.getY() + 0.499);
			g.setColor(Color.WHITE);
			if (mbView.getShowJuliaAndOrbit() == MandelbrotView.SHOW_JULIA ||
					mbView.getShowJuliaAndOrbit() == MandelbrotView.SHOW_BOTH) {
				//	// From here to jjj have Julia set as mouse task
				makeJuliaStart();
				for (int i = 0; i < currentJuliaLength; i++) {
					zx = juliaStart[i].re;
					zy = juliaStart[i].im;
					pt.setLocation(zx,zy);
					transform.windowToViewport(pt);
					int zxInt = (int)(pt.getX() + 0.499);
					int zyInt = (int)(pt.getY() + 0.499);
					mbView.drawLineDirect( zxInt - 1, zyInt, zxInt + 1, zyInt);
					mbView.drawLineDirect( zxInt, zyInt - 1, zxInt, zyInt + 1);  
				}	//  // jjj
			}
			int x1 = cxInt - 7;
			int x2 = cxInt + 7;
			int y1 = cyInt - 7;
			int y2 = cyInt + 7;
			g.setColor(Color.BLACK);
			mbView.drawLineDirect( cxInt-2, y1, cxInt-2, y2 );
			mbView.drawLineDirect( cxInt+1, y1, cxInt+1, y2 );
			mbView.drawLineDirect( x1, cyInt-2, x2, cyInt-2 );
			mbView.drawLineDirect( x1, cyInt+1, x2, cyInt+1 );
			if (mbView.getShowJuliaAndOrbit() == MandelbrotView.SHOW_ORBIT ||
					mbView.getShowJuliaAndOrbit() == MandelbrotView.SHOW_BOTH) {
				//	 // From here to end 0-orbit in mouse task
				zx = cx;
				zy = cy;
				int ct = pointsOnOrbit.getValue();
				for (int i = 0; i < ct; i++) {
					pt.setLocation(zx,zy);
					transform.windowToViewport(pt);
					int x = (int)pt.getX();
					int y = (int)pt.getY();
					mbView.fillOvalDirect(Color.getHSBColor( (float)(0.75*(ct-i))/ct, 0.6F, 1),x-3,y-3,5,5);
					mbView.drawOvalDirect(Color.GRAY,x-3,y-3,6,6);
					double t = zx*zx - zy*zy + cx;
					zy = 2*zx*zy + cy;
					zx = t;  
				}
			}
			g.setColor(Color.WHITE);
			mbView.drawLineDirect( cxInt-1, y1, cxInt-1, y2 );
			mbView.drawLineDirect( cxInt, y1, cxInt, y2 );
			mbView.drawLineDirect( x1, cyInt-1, x2, cyInt-1 );
			mbView.drawLineDirect( x1, cyInt, x2, cyInt );
			g.setColor(saveColor);
		}
	} // end class JuliaSetAndOrbitDecoration



}


