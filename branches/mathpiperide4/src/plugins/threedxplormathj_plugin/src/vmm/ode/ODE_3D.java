/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.actions.ActionRadioGroup;
import vmm.actions.ToggleAction;
import vmm.core.Decoration;
import vmm.core.Display;
import vmm.core.Exhibit;
import vmm.core.I18n;
import vmm.core.MouseTask;
import vmm.core.SaveAndRestore;
import vmm.core.TimerAnimation;
import vmm.core.Transform;
import vmm.core.Util;
import vmm.core.VMMSave;
import vmm.core.View;
import vmm.core3D.BasicMouseTask3D;
import vmm.core3D.Exhibit3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;

/**
 * Represents an ODE exhibit that will be displayed in three dimensions.
 */
abstract public class ODE_3D extends Exhibit3D {
	
	public final static int ORBIT_TYPE_RUNGE_KUTTA = 0, ORBIT_TYPE_BOTH = 1, ORBIT_TYPE_EULER = 2; // (actual values are important)

	private final static int DF_SPACING = 30;  // number of pixels between points in direction field
	
	private final static Color DIRECTION_FIELD_COLOR = Color.GRAY;
	
	private final static Color RUNGE_KUTTA_ORBIT_COLOR = Color.RED;
	private final static Color EULER_ORBIT_COLOR = Color.GREEN;
	private final static Color OLD_RUNGE_KUTTA_ORBIT_COLOR = new Color(150,80,80);
	private final static Color OLD_EULER_ORBIT_COLOR = new Color(80,150,80);
	private final static Color RUNGE_KUTTA_PROJECTED_ORBIT_X_COLOR = Color.CYAN;
	private final static Color RUNGE_KUTTA_PROJECTED_ORBIT_Y_COLOR = Color.MAGENTA;
	private final static Color RUNGE_KUTTA_PROJECTED_ORBIT_Z_COLOR = new Color(220,220,0);
	private final static Color EULER_PROJECTED_ORBIT_X_COLOR = new Color(100,150,150);
	private final static Color EULER_PROJECTED_ORBIT_Y_COLOR = new Color(150,100,150);
	private final static Color EULER_PROJECTED_ORBIT_Z_COLOR = new Color(150,150,80);
	
	/**
	 * Tells whether the ODE is autonomous (i.e. independent of time).  This final
	 * variable is set by the constructor {@link #ODE_3D(boolean, boolean, String[])}, with the actual value 
	 * coming from the constructor of each subclass.  Autonomous and non-autonomous
	 * vector fields have different behaviors.
	 */
	protected final boolean isAutonomous;
	
	/**
	 * If this is set to true in a subclass's constructor, then the view will be
	 * set to show axes when it is created.  The default is for the axes to be hidden.
	 */
	protected boolean showAxes = false;
	
	/**
	 * If this is set to true in a subclass's constructor, then the view will be
	 * put in anaglyph mode when it is created.  The default is a monocular view.
	 */
	protected boolean anaglyphIsDefault = false;
	
	/**
	 * If set to true in the constructor of a subclass, then the radio buttons
	 * for selecting RungeKutta/Euler/Both will be added to the control
	 * pandl. (Even when not added to the control panel, they will still 
	 * be in the Action menu).
	 */
	protected boolean addOrbitTypesToControlPanel = false;
	
	/**
	 * If set to false in the constructor of a subclass, then the check box
	 * for turning animatino of orbit drawing will not be added to the control
	 * pandl (although the command will still be in the Action menu).
	 */
	protected boolean addAnimateCheckBoxontrolPanel = true;
	
	/**
	 * If set to false in the constructor of a subclass, then the check box
	 * for controling whether orbits are drawn as solid lines will not be added to the control
	 * pandl (although the command will still be in the Action menu).
	 */
	protected boolean addLinesCheckBoxontrolPanel = true;
	
	
	/**
	 * Tells whether it is possible to draw a vector field for the exhibit.  This is
	 * set in the contructor {@link #ODE_3D(boolean, boolean, String[])}, with the actual value coming
	 * from the contructor of each subclass.  If the value is false, then the
	 * methods {@link #vectorField_x(double, double, double, double)}  and
	 * {@link #vectorField_y(double, double, double,double)} are never called.  If the
	 * value is true, then these methods must be overridden to return the correct
	 * values.  For an autonomous ODE, the display of the vector field
	 * is under control of the user.  For a non-autonomous ODE, the vector field
	 * is shown only when an integral curve is being constructed.  (Actually,
	 * a direction field rather than a vector field is drawn.)
	 */
	protected final boolean canShowVectorField;
	
	/**
	 * The names used for the input boxes where the user can type in the
	 * initial values for an integeral curves.  This array is set from the
	 * String parameters to the constructor {@link #ODE_3D(boolean, boolean, String[])}.  There
	 * must be exactly as many strings as there are data items in the
	 * pointData array that is passed to {@link #nextEulerPoint(double[], double)},
	 * {@link #nextRungeKuttaPoint(double[], double)} and {@link #extractPointFromData(double[])}.
	 */
	protected final String[] inputLabelNames;
	

	/**
	 * Computes the next point on an integral curve, using Euler's method.  
	 * The pointData parameter array contains the data for the current point.
	 * This data should be replaced with the data for the next point.  The
	 * size of the array and the meaning of the data that it contains will
	 * vary from one subclass to another.  However, for non-autonomous ODEs,
	 * the time should always be the first parameter in the array.
	 * The second parameter, dt, gives  the time step from the current 
	 * point to the next point.
	 */
	abstract protected void nextEulerPoint(double[] pointData, double dt);
	
	/**
	 * Computes the next point on an integral curve, using the Runge-Kutta method.  
	 * The pointData parameter array contains the data for the current point.
	 * This data should be replaced with the data for the next point.  The
	 * size of the array and the meaning of the data that it contains will
	 * vary from one subclass to another.  But for the non-autonmous case,
	 * the time should always be the first value in the array.
	 * The second parameter, dt, gives the time step from the current point to the next point.
	 */
	abstract protected void nextRungeKuttaPoint(double[] pointData, double dt);
	
	/**
	 * Extract from the data for a point on the integral curve the actual
	 * point that is to be displayed on the screen.  The default implementation
	 * in this class takes the point from the first three components of the
	 * array for an autonomous ODE and from the second, third, and fourth components
	 * for a non-autonomous ODE, allowing the first spot for the current time.
	 * Note that for non-autonomous ODEs, the time <b>must</b> be in the
	 * first spot in the array.
	 */
	protected Vector3D extractPointFromData(double[] pointData) {
		if (isAutonomous)
			return new Vector3D(pointData[0],pointData[1],pointData[2]);
		else
			return new Vector3D(pointData[1],pointData[2],pointData[3]);
	}
	
	/**
	 * Returns the x-component of the vector field at point (x,y,z) and time t.
	 * For autonomous ODEs, the result should be independent of t.  The
	 * default implementation here simply returns 0.  An exhibit that
	 * sets the {@link #canShowVectorField} propery to true should
	 * override this method to compute the correct value.
	 */
	protected double vectorField_x(double x, double y, double z, double t) {
		return 0;
	}
	
	/**
	 * Returns the x-component of the vector field at point (x,y,z) and time t.
	 * For autonomous ODEs, the result should be independent of t.  The
	 * default implementation here simply returns 0.  An exhibit that
	 * sets the {@link #canShowVectorField} propery to true should
	 * override this method to compute the correct value.
	 */
	protected double vectorField_y(double x, double y, double z, double t) {
		return 0;
	}
	
	
	/**
	 * Should construct and return a mouse task appropriate for this exhibit, to be used
	 * in the specified view.  The default return value is a standard
	 * BasicMouseTask3D; subclasses can redefine this to add the ability to input
	 * the initial conditions with the mouse.
	 */
	protected MouseTask makeDefaultMouseTask(ODEView view) {
		return new BasicMouseTask3D();
	}
	
	
	/**
	 * The value of this variable is used as the initial value of dt in the Control Panel
	 * of any view of this exhbit.  The default is 0.05, but it can be reset in the constructor
	 * of a subclass.
	 */
	protected double dtDefault = 0.05;	
	
	/**
	 * The value of this variable is used as the initial value of Time Span in the Control Panel
	 * of any view of this exhbit.  The default is 10.0, but it can be reset in the constructor
	 * of a subclass.
	 */
	protected double timeSpanDefault = 10;
	
	/**
	 * Contains the initial conditions that are placed in the control panel when the
	 * exhibit is first created.  The value in this class is null, which means that
	 * the initial condition input boxed in the control panel will be empty when it
	 * first appears.  Subclasses can set a different value.  The value should be
	 * an array of doubles, where the first several items have the same meaning as 
	 * the parameter in such methods as {@link #nextEulerPoint(double[], double)} and
	 * {@link #extractPointFromData(double[])}.  In addition there can be up to
	 * two additional items.  The first additional item, if present and greater than
	 * zero, specifies a dt for the initial orbit.  The second additional item, if
	 * present and greater than zero, specifies a timeSpan for the initial orbit.
	 * If these values are not present or  are less than or equal to zero, then
	 * the default dt/TimeSpan of the exhbit is used.
	 */
	protected double[] initialDataDefault = null;

	/**
	 * Constructor sets the default background to be black, and sets the
	 * value of the final "isAutonomous" property and the lables to be used
	 * for the input boxes for initial conditions.  For a non-autonomous ODE,
	 * the first lable should be the label for the initial time input, generally
	 * just "t".  Also sets the default viewpoint to 10, 10, 5
	 */
	protected ODE_3D(boolean canShowVectorField, boolean isAutonomous, String... inputLabelName) {
		setDefaultBackground(Color.BLACK);
		setDefaultViewpoint(new Vector3D(10,10,5));
		this.canShowVectorField = canShowVectorField;
		this.isAutonomous = isAutonomous;
		this.inputLabelNames = inputLabelName;
	}
	
	/**
	 * For an ODE view, returns the animation that redraws the current orbit, if there is one,
	 * or draws the default orbit as defined by {@link #initialDataDefault}, if the value of
	 * that variable is non-null.  Otherwise, the return value is null.
	 */
	public TimerAnimation getCreateAnimation(View view) {
		if (view instanceof ODEView)
			return ((ODEView)view).makeCreateAnimation();
		else
			return null;
	}
	
	/**
	 * Transform a point on the screen to a point in 3D space.  (The inverse
	 * transformation that is used here to get from 3D viewing coords to 
	 * 3D object coords is valid only for a point that lies in the plane of
	 * the screen (or for an orthographic projection).
	 * <p>For cross-eye and stereograph view, where (x,y) might not lie in
	 * one of the view rects, the return value is null when (x,y) is outside
	 * the view rects.
	 */
	protected Vector3D screenPointTo3DPoint(View3D view, int x, int y) {
		if (view.getViewStyle() == View3D.CROSS_EYE_STEREO_VIEW || view.getViewStyle() == View3D.STEREOGRAPH_VIEW) {
			Rectangle r1 = view.stereographLeftEyeRect();
			Rectangle r2 = view.stereographRightEyeRect();
			if (r1.contains(x, y)) {
				x = x - r1.x;
				y = y - r1.y;
			}
			else if (r2.contains(x,y)) {
				x = x - r2.x;
				y = y - r2.y;
			}
			else
				return null;
		}
		Point2D p = new Point2D.Double(x,y);
		view.getTransform3D().viewportToWindow(p);
		Vector3D v = new Vector3D(p.getX(), 0, p.getY());
		Vector3D xDir = view.getTransform3D().getImagePlaneXDirection();
		Vector3D zDir = view.getTransform3D().getImagePlaneYDirection();
		Vector3D yDir = view.getTransform3D().getViewDirection().negated();
		Vector3D v2 = new Vector3D();
		v2.x = v.x * xDir.x + v.y * yDir.x + v.z * zDir.x;
		v2.y = v.x * xDir.y + v.y * yDir.y + v.z * zDir.y;
		v2.z = v.x * xDir.z + v.y * yDir.z + v.z * zDir.z;
		return v2;
	}
	
	/**
	 * Draws a direction field, but only for an exhbit in which the {@link #canShowVectorField}
	 * property is true and then only if the exhibit is being displayed in an ODEView whose
	 * showDirectionField property is turned on (or in the unlikely event that it is being 
	 * displayed in some other type of View).
	 * @see ODEView#setShowDirectionField(boolean)
	 */
	protected void doDraw(Graphics2D g, View view, Transform transform) {
		if ( ! canShowVectorField )
			return;
		if ( !(view instanceof ODEView) || ((ODEView)view).showDirectionField)
			drawDirectionField(view,transform);
	}
	
	private void drawDirectionField(View view, Transform transform) { // draws the direction field
//		double spacing = DF_SPACING * transform.getPixelWidth(); // spacing between points, in terms of xy-coords, not pixels.
//		int startX, endX, startY, endY;  // limits for the for loops, computed so that the direction field fills the view
//		startX = (int)( transform.getXmin()/spacing ) - 1;
//		endX = (int)( transform.getXmax()/spacing ) + 1;
//		startY = (int)( transform.getYmin()/spacing ) - 1;
//		endY = (int)( transform.getYmax()/spacing ) + 1;
//		Color c = view.getColor();
//		view.setColor(DIRECTION_FIELD_COLOR);
//		double currentTime = 0;
//		if (view instanceof ODEView)
//			currentTime = ((ODEView)view).currentTime;
//		for (int i = startX; i <= endX; i++) {
//			double x = i*spacing;
//			for (int j = startY; j <= endY; j++) {
//				double y = j*spacing;
//				double dx = vectorField_x(x, y, z, currentTime);
//				double dy = vectorField_y(x, y, z, currentTime);
//				double length = Math.sqrt(dx*dx + dy*dy);
//				if (Double.isNaN(length)) {
//				}
//				else if (Math.abs(length) < 0.000001) {
//					view.drawPixel(x,y);
//				}
//				else {
//					dx = dx/length * (spacing/4);
//					dy = dy/length * (spacing/4);
//					view.drawLine(x-dx,y-dy,x+dx,y+dy);
//				}
//			}
//		}
//		view.setColor(c);
	}

	/**
	 * Returns a view of type {@link ODEView}.
	 */
	public View getDefaultView() {
		return new ODEView();
	}
		
	/**
	 * Represents the default view for an ODEFirstOrder2D.
	 */
	public class ODEView extends View3D {
		private boolean showDirectionField;  // (Saved, by hand, for the autonomous case only).
		@VMMSave private boolean showProjectedOrbits = false;
		@VMMSave private boolean showControlPanel = true;
		@VMMSave private int orbitType = ORBIT_TYPE_RUNGE_KUTTA;
		@VMMSave private boolean connectDotsOnOrbit;  // Should orbits be drawn as dots or as solid lines?
		private boolean animateDrawing = true;
		private Orbit currentOrbit;  // The most recently added orbit, shown in green; some commands operate on this orbit.
		private ControlPanel controlPanel;
		private ProjectedOrbitView projectedOrbitView;
		private double[] initialDataForCreateAnimation;
		private double currentTime;  // used only for non-autonomous ODES, while drawing an orbit;
		protected ToggleAction showDirectionFieldToggle;
		protected ToggleAction showProjectedOrbitsToggle = new ToggleAction(I18n.tr("vmm.ode.command.ShowProjectedOrbits"),false) {
			public void actionPerformed(ActionEvent evt) {
				setShowProjectedOrbits(getState());
			}
		};
		protected ToggleAction showControlPanelToggle = new ToggleAction(I18n.tr("vmm.ode.command.ShowControlPanel"),true) {
			public void actionPerformed(ActionEvent evt) {
				setShowControlPanel(getState());
			}
		};
		protected ToggleAction animateDrawingToggle = new ToggleAction(I18n.tr("vmm.ode.command.AnimateDrawing"),true) {
			public void actionPerformed(ActionEvent evt) {
				setAnimateDrawing(getState());
			}
		};
		protected AbstractActionVMM continueOrbitAction = new AbstractActionVMM(I18n.tr("vmm.ode.command.ContinueOrbit")) {
			public void actionPerformed(ActionEvent evt) {
				Orbit orbit = getCurrentOrbit();
				if (orbit != null) {
					double timeSpan;
					try {
						timeSpan = Double.parseDouble(controlPanel.timeSpanInput.getText());
					}
					catch (Exception e) {
						timeSpan = timeSpanDefault;
						controlPanel.timeSpanInput.setText(""+timeSpanDefault);
					}
					int numberOfPoints = (int)(timeSpan/orbit.dt + 0.5);
					controlPanel.dtInput.setText(""+orbit.dt);
					if (animateDrawing)
						getDisplay().installAnimation(new ExtendOrbitAnimation(ODEView.this,orbit,numberOfPoints));
					else
						orbit.setPointCount(orbit.getPointCount() + numberOfPoints);
				}
			}
		};
		protected ToggleAction connectDotsToggle = new ToggleAction(I18n.tr("vmm.ode.command.ConnectDotsOnOrbit"),false) {
			public void actionPerformed(ActionEvent evt) {
				setConnectDotsOnOrbit(getState());
			}
		};
		protected AbstractActionVMM eraseOrbitsAction = new AbstractActionVMM(I18n.tr("vmm.ode.command.EraseOrbits")) {
			public void actionPerformed(ActionEvent evt) {
				if (getDisplay() != null)
					getDisplay().stopAnimation();
				setCurrentOrbit(null);
				Decoration[] decorations = getDecorations();
				for (Decoration dec : decorations)
					if (dec instanceof Orbit)
						removeDecoration(dec);
			}
		};
		protected ActionRadioGroup orbitTypeSelect = new ActionRadioGroup( new String[] {
				I18n.tr("vmm.ode.command.orbitType.RungeKutta"),
				I18n.tr("vmm.ode.command.orbitType.Euler"),
				I18n.tr("vmm.ode.command.orbitType.Both"),
		}, 0) {
			public void optionSelected(int option) {
				int style;
				if (option == 0)
					style = ORBIT_TYPE_RUNGE_KUTTA;
				else if (option == 1)
					style = ORBIT_TYPE_EULER;
				else
					style = ORBIT_TYPE_BOTH;
				setOrbitType(style);
			}
		};
		public ODEView() {
			if (showAxes)
				setShowAxes(true);
			if (anaglyphIsDefault)
				setViewStyle(View3D.RED_GREEN_STEREO_VIEW);
			setAntialiased(true);
			continueOrbitAction.setEnabled( getCurrentOrbit() != null );
			controlPanel = new ControlPanel(this);
			showDirectionField = isAutonomous && canShowVectorField;
			if (isAutonomous && canShowVectorField)
				showDirectionFieldToggle = new ToggleAction(I18n.tr("vmm.ode.command.ShowDirectionField"),true) {
					public void actionPerformed(ActionEvent evt) {
						setShowDirectionField(getState());
					}
				};
		}
		public boolean getShowDirectionField() {
			return showDirectionField;
		}
		/**
		 * The showDirectionField property determines whether a direction field is drawn for the exhibit in this view.
		 * This property is true by default.  This method has no effect if the exhbit can't show direction fields.
		 */
		public void setShowDirectionField(boolean showDirectionField) {
			if (!canShowVectorField)
				return;
			if (this.showDirectionField == showDirectionField)
				return;
			this.showDirectionField = showDirectionField;
			if (showDirectionFieldToggle != null)
				showDirectionFieldToggle.setState(showDirectionField);
			forceRedraw();
		}
		public boolean getAnimateDrawing() {
			return animateDrawing;
		}
		public void setAnimateDrawing(boolean animateDrawing) {
			this.animateDrawing = animateDrawing;
			animateDrawingToggle.setState(animateDrawing);
		}
		/**
		 * Returns the current orbit (shown in green), if any.
		 */
		protected Orbit getCurrentOrbit() {
			return currentOrbit;
		}
		/**
		 * Sets the current orbit (shown in green).  When a new orbit is added to the exhibit,
		 * this method is called, so that the new orbit becomes the current orbit.
		 */
		protected void setCurrentOrbit(Orbit orbit) {
			if (currentOrbit == orbit)
				return;
			if (currentOrbit != null)
				currentOrbit.setIsCurrentOrbit(false);
			currentOrbit = orbit;
			if (currentOrbit != null)
				currentOrbit.setIsCurrentOrbit(true);
			continueOrbitAction.setEnabled( currentOrbit != null);
		}
		/**
		 * Tells whether orbits should be drawn as dots or as solid lines.
		 * @see #setConnectDotsOnOrbit(boolean)
		 */
		public boolean getConnectDotsOnOrbit() {
			return connectDotsOnOrbit;
		}
		/**
		 * Set the property that tells whether an orbit should be drawn simply as a sequence of dots,
		 * or the dots should be connected to make a solid curve.  If there is a current (green) orbit
		 * when the property is changed, the new value is applied immediately to the current orbit.
		 * It also applies to new orbits created in the future.  It does not change the appearance
		 * of old (red) orbits that exist when the value of the property is set.
		 */
		public void setConnectDotsOnOrbit(boolean connectDotsOnOrbit) {
			if (this.connectDotsOnOrbit == connectDotsOnOrbit)
				return;
			this.connectDotsOnOrbit = connectDotsOnOrbit;
			connectDotsToggle.setState(connectDotsOnOrbit);
			if (currentOrbit != null)
				currentOrbit.setStyle(connectDotsOnOrbit ? OrbitPoints2D.LINES : OrbitPoints2D.DOTS);
		}
		public boolean getShowProjectedOrbits() {
			return showProjectedOrbits;
		}
		public int getOrbitType() {
			return orbitType;
		}
		public void setOrbitType(int type) {
			if (type == orbitType)
				return;
			orbitType = type;
			if (currentOrbit != null) {
				currentOrbit.setOrbitType(orbitType);
				if (showProjectedOrbits)
					projectedOrbitView.forceRedraw();
			}
		}
		public boolean getShowControlPanel() {
			return showControlPanel;
		}
		public void setShowControlPanel(boolean showControlPanel) {
			if (this.showControlPanel == showControlPanel)
				return;
			this.showControlPanel = showControlPanel;
			showControlPanelToggle.setState(showControlPanel);
			if (showControlPanel) {
				if (getDisplay() != null) {
					getDisplay().getHolder().add(controlPanel,BorderLayout.EAST);
					getDisplay().getHolder().validate();
				}
			}
			else {
				if (getDisplay() != null) {
					getDisplay().getHolder().remove(controlPanel);
					getDisplay().getHolder().validate();
				}
			}
		}
		/**
		 * When the showProjectedOrbits property is true, an auxiliary view is added to the bottom of the
		 * display where the x- and y-coordinates of the points on the current orbit are plotted.
		 * This property is false by default.
		 */
		public void setShowProjectedOrbits(boolean showProjectedOrbits) {
			if (this.showProjectedOrbits == showProjectedOrbits)
				return;
			this.showProjectedOrbits = showProjectedOrbits;
			showProjectedOrbitsToggle.setState(showProjectedOrbits);
			if (showProjectedOrbits) {
				projectedOrbitView = new ProjectedOrbitView(this);
				if (getCurrentOrbit() != null) {
					projectedOrbitView.setMaxPoints(getCurrentOrbit().getPointCount());
					projectedOrbitView.resetPointsFromOrbit(getCurrentOrbit());
				}
				if (getDisplay() != null)
					getDisplay().installAuxiliaryView(this, projectedOrbitView, Display.AUX_VIEW_ON_BOTTOM, 0.2, true);
			}
			else {
				projectedOrbitView = null;
				if (getDisplay() != null)
					getDisplay().installAuxiliaryView(this, null);
			}
		}
		public ActionList getActions() {
			ActionList actions = super.getActions();
			actions.add(continueOrbitAction);
			actions.add(eraseOrbitsAction);
			actions.add(connectDotsToggle);
			actions.add(animateDrawingToggle);
			actions.add(null);
			actions.add(orbitTypeSelect);
			actions.add(null);
			if (showDirectionFieldToggle != null)
				actions.add(showDirectionFieldToggle);
			actions.add(showControlPanelToggle);
			actions.add(showProjectedOrbitsToggle);
			return actions;
		}
		public void setDisplay(Display display) {
			super.setDisplay(display);
			if (display != null)
				display.setStopAnimationsOnResize(false);
			if (display != null && projectedOrbitView != null)
				display.installAuxiliaryView(this, projectedOrbitView, Display.AUX_VIEW_ON_BOTTOM, 0.2, true);
			if (display != null && showControlPanel) {
				display.getHolder().add(controlPanel,BorderLayout.EAST);
				display.getHolder().validate();
			}
		}
		public void setExhibit(Exhibit ex) {
			super.setExhibit(ex);
			if (ex != null && ex == ODE_3D.this)  // It better equal ODE_3D.this!
				initialDataForCreateAnimation = initialDataDefault;
		}
		public void stateChanged(ChangeEvent evt) {
			super.stateChanged(evt);
			Object source = evt.getSource();
			if (projectedOrbitView != null && source instanceof Transform)
				projectedOrbitView.forceRedraw();
		}
		public MouseTask getDefaultMouseTask() {
			return makeDefaultMouseTask(this);
		}
		public double getCurrentTimeFromControlPanel() { // for use by mouse tasks to construct initialPointData
			if (isAutonomous)
				return 0;
			try {
				return Double.parseDouble(controlPanel.icInputs[0].getText());
			}
			catch (NumberFormatException e) {
				controlPanel.icInputs[0].setText("0");
				return 0;
			}
		}
		public void startOrbitAtPoint(double[] initialPointData) {
			double dt, timeSpan;
			try {
				dt = Double.parseDouble(controlPanel.dtInput.getText());
				if (dt <= 0)
					throw new Exception();
			}
			catch (Exception e) {
				dt = dtDefault;
				controlPanel.dtInput.setText(""+dtDefault);
			}
			try {
				timeSpan = Double.parseDouble(controlPanel.timeSpanInput.getText());
				if (timeSpan <= 0)
					throw new Exception();
			}
			catch (Exception e) {
				timeSpan = timeSpanDefault;
				controlPanel.timeSpanInput.setText(""+timeSpanDefault);
			}
			controlPanel.resetStartPointInputText(initialPointData);
			startOrbitAtPoint(initialPointData, dt, timeSpan);
		}
		private TimerAnimation makeCreateAnimation() {
			if (!animateDrawing)
				return null;
			if (currentOrbit == null && initialDataForCreateAnimation == null)
				return null;
			Orbit orbit;
			double dt;
			double timeSpan;
			if (currentOrbit != null) {
				orbit = currentOrbit;
				dt = currentOrbit.dt;
				timeSpan = dt * currentOrbit.pointCount;
				currentOrbit.removePoints();
			}
			else {
				int dataCt = inputLabelNames.length; // number of intial condition values in initialDataDefault
				if (initialDataForCreateAnimation.length > dataCt && initialDataForCreateAnimation[dataCt] > 0)
					dt = initialDataForCreateAnimation[dataCt];
				else
					dt = dtDefault;
				if (initialDataForCreateAnimation.length > dataCt + 1 && initialDataForCreateAnimation[dataCt+1] > 0)
					timeSpan = initialDataForCreateAnimation[dataCt+1];
				else
					timeSpan = timeSpanDefault;
				double[] data = new double[dataCt];
				for (int i = 0; i < dataCt; i++)
					data[i] = initialDataForCreateAnimation[i];
				orbit = new Orbit(this,data,ORBIT_TYPE_RUNGE_KUTTA,dt);
				controlPanel.resetStartPointInputText(initialDataForCreateAnimation);
				addDecoration(orbit);
				setCurrentOrbit(orbit);
			}
			initialDataForCreateAnimation = null;
			int numberOfPoints = (int)(timeSpan/dt) + 1;  // how many points should be added to curve during animation
			controlPanel.dtInput.setText(""+dt);
			controlPanel.timeSpanInput.setText(""+timeSpan);
			if (!isAutonomous)
				currentTime = orbit.initialData[0];
			return new ExtendOrbitAnimation(this,orbit,numberOfPoints);
		}
		private void startOrbitAtPoint(double[] initialPointData, double dt, double timeSpan) {
			Orbit orbit;
			orbit = new Orbit(this,initialPointData,orbitType,dt);
			addDecoration(orbit);
			setCurrentOrbit(orbit);
			if (getConnectDotsOnOrbit())
				orbit.setStyle(OrbitPoints2D.LINES);
			int numberOfPoints = (int)(timeSpan/dt + 0.5);  // how many points should be added to curve during animation
			if (!isAutonomous && animateDrawing)
				currentTime = initialPointData[0];
			if (animateDrawing)
				getDisplay().installAnimation( new ExtendOrbitAnimation(this,orbit,numberOfPoints));
			else
				orbit.setPointCount(numberOfPoints+1);
		}
		public void addExtraXML(Document containingDocument, Element viewElement) {
			super.addExtraXML(containingDocument, viewElement);
			if (isAutonomous && canShowVectorField)
				SaveAndRestore.addProperty(this, "showDirectionField", containingDocument, viewElement);
			for (Decoration d : getDecorations())
				if (d instanceof Orbit) {
					Orbit orbit = (Orbit)d;
					Element orbElm = containingDocument.createElement("orbit");
					orbElm.setAttribute("start", Util.toExternalString(orbit.initialData));
					orbElm.setAttribute("type", Util.toExternalString(orbit.orbitType));
					orbElm.setAttribute("dt", Util.toExternalString(orbit.dt));
					orbElm.setAttribute("points", Util.toExternalString(orbit.pointCount));
					orbElm.setAttribute("isCurrentOrbit", Util.toExternalString(orbit.isCurrentOrbit));
					orbElm.setAttribute("style", "" + orbit.eulerPoints.getStyle());
					viewElement.appendChild(orbElm);
				}
		}
		public void readExtraXML(Element viewInfo) throws IOException {
			super.readExtraXML(viewInfo);
			NodeList nodes = viewInfo.getElementsByTagName("orbit");
			for (int i = 0; i < nodes.getLength(); i++) {
				Element orbElm = (Element)nodes.item(i);
				double[] startPt = (double[])Util.externalStringToValue(orbElm.getAttribute("start"), double[].class);
				double dt = (Double)Util.externalStringToValue(orbElm.getAttribute("dt"), Double.TYPE);
				int type = (Integer)Util.externalStringToValue(orbElm.getAttribute("type"), Integer.TYPE);
				int pointCount = (Integer)Util.externalStringToValue(orbElm.getAttribute("points"), Integer.TYPE);
				boolean isCurrentOrbit = (Boolean)Util.externalStringToValue(orbElm.getAttribute("isCurrentOrbit"), Boolean.TYPE);
				int style = (Integer)Util.externalStringToValue(orbElm.getAttribute("style"), Integer.TYPE);
				Orbit orbit = new Orbit(this,startPt,type,dt);
				orbit.pointCount = pointCount;
				orbit.setIsCurrentOrbit(isCurrentOrbit);
				orbit.setStyle(style);
				addDecoration(orbit);
				if (isCurrentOrbit) {
					controlPanel.dtInput.setText(""+dt);
					controlPanel.resetStartPointInputText(startPt);
					currentOrbit = orbit;
					continueOrbitAction.setEnabled(true);
				}
			}
		}
	}
	
	/**
	 * This class defines the auxiliary view that can be added to the bottom of the display,
	 * where the x- and y-coordinates of the current orbit are plotted.  It is a rather kludgy hack.
	 */
	private class ProjectedOrbitView extends View {
		ArrayList<Vector3D> eulerPoints;
		ArrayList<Vector3D> rungeKuttaPoints;
		int maxNumberOfPoints;  // The number of points that CAN be drawn.
		ODEView owner;
		ProjectedOrbitView(ODEView owner) {
			this.owner = owner;
			setPreserveAspect(false);
			setApplyGraphics2DTransform(false);
			setAntialiased(true);
			eulerPoints = new ArrayList<Vector3D>();
			rungeKuttaPoints = new ArrayList<Vector3D>();
			setExhibit(new Exhibit(){  // I need to have an Exhbit in this view; otherwise, its render method is never called.
				protected void doDraw(Graphics2D g, View view, Transform transform) {
					draw(g,transform);
				}
				public Color getDefaultBackground() {
					return Color.BLACK;
				}
				public Transform getDefaultTransform(View view) {
					return new POVTransform();
				}
			});
		}
		private class POVTransform extends Transform {
			void getLimitsFromOwner() {
				Transform tr = owner.getTransform();
				resetLimits(-1,1,tr.getYmin(),tr.getYmax());  // x-limits are not important; y limits should match those on main view
			}
		}
		public MouseTask getDefaultMouseTask() {
			return null;
		}
		void setMaxPoints(int ct) {
			maxNumberOfPoints = ct;
			forceRedraw();
		}
		void resetPointsFromOrbit(Orbit orbit) {
			eulerPoints.clear();
			rungeKuttaPoints.clear();
			if (orbit.getOrbitType() >= ORBIT_TYPE_BOTH) {
				OrbitPoints3D euler = orbit.getEulerPoints();
				int ct = euler.getPointCount();
				for (int i = 0; i < ct; i++)
					eulerPoints.add(euler.getPoint(i));
			}
			if (orbit.getOrbitType() <= ORBIT_TYPE_BOTH) {
				OrbitPoints3D rk = orbit.getRungeKuttaPoints();
				int ct = rk.getPointCount();
				for (int i = 0; i < ct; i++)
					rungeKuttaPoints.add(rk.getPoint(i));
			}
			forceRedraw();
		}
		void clear() {
			eulerPoints.clear();
			rungeKuttaPoints.clear();
			forceRedraw();
		}
		void addPoints(Vector3D eulerPt, Vector3D rkPoint) {
			eulerPoints.add(eulerPt);
			rungeKuttaPoints.add(rkPoint);
			if (eulerPt == null && rkPoint == null)
				return;
			if (eulerPt != null && eulerPoints.size() > 1) {
				int ptNum = eulerPoints.size()-1;
				if (! drawLineNow(ptNum, eulerPoints.get(ptNum-1), eulerPoints.get(ptNum), true)) {
					forceRedraw();
					return;
				}
			}
			if (rkPoint != null && rungeKuttaPoints.size() > 1) {
				int ptNum = rungeKuttaPoints.size()-1;
				if (! drawLineNow(ptNum, rungeKuttaPoints.get(ptNum-1), rungeKuttaPoints.get(ptNum), false))
					forceRedraw();
			}
		}
		boolean drawLineNow(int ptNum, Vector3D pt1, Vector3D pt2, boolean eulerColors) {
			if (pt1 == null || pt2 == null)
				return true;
			if (!beginDrawToOffscreenImage())
				return false;
			Transform transform = getTransform();
			((POVTransform)transform).getLimitsFromOwner();
			double pixelWidth = (transform.getXmax() - transform.getXmin()) / transform.getWidth();
			double leftSpace = 15 * pixelWidth;
			double left = transform.getXmin() + 3*leftSpace;
			double dt = (transform.getXmax() - transform.getXmin() - 3*leftSpace)/(maxNumberOfPoints - 1);
			double t = left + ptNum*dt;
			if (eulerColors) {
				setColor(EULER_PROJECTED_ORBIT_X_COLOR);
				drawLine(t-dt,pt1.x,t,pt2.x);
				setColor(EULER_PROJECTED_ORBIT_Y_COLOR);
				drawLine(t-dt,pt1.y,t,pt2.y);
				setColor(EULER_PROJECTED_ORBIT_Z_COLOR);
				drawLine(t-dt,pt1.z,t,pt2.z);
			}
			else {
				setColor(RUNGE_KUTTA_PROJECTED_ORBIT_X_COLOR);
				drawLine(t-dt,pt1.x,t,pt2.x);
				setColor(RUNGE_KUTTA_PROJECTED_ORBIT_Y_COLOR);
				drawLine(t-dt,pt1.y,t,pt2.y);
				setColor(RUNGE_KUTTA_PROJECTED_ORBIT_Z_COLOR);
				drawLine(t-dt,pt1.z,t,pt2.z);
			}
			return true;
		}
		void draw(Graphics2D g, Transform transform) {
			((POVTransform)transform).getLimitsFromOwner();
			   // Note:  The previous line changes the y-limits in the transform to match those in the main
			   // view.  Canging the limits in the draw method will not work, unless preserveAspect and
			   // applyTransform2D have been turned off, as they are here.  This is becuase by the time
			   // draw() is called, these two properties have already been applied to the limits and to
			   // the graphics context respectively.
			double pixelWidth = (transform.getXmax() - transform.getXmin()) / transform.getWidth();
			double leftSpace = 15 * pixelWidth;
			g.setColor(Color.LIGHT_GRAY);
			drawLine(transform.getXmin() + 2*leftSpace,0,transform.getXmax(),0);
			drawLine(transform.getXmin() + 3*leftSpace,transform.getYmin(),transform.getXmin() + 3*leftSpace,transform.getYmax());
			double height = transform.getYmax() - transform.getYmin();
			g.setColor(RUNGE_KUTTA_PROJECTED_ORBIT_X_COLOR);
			drawString("x", transform.getXmin() + leftSpace, transform.getYmax() - height/4 - 3*pixelWidth);
			g.setColor(RUNGE_KUTTA_PROJECTED_ORBIT_Y_COLOR);
			drawString("y", transform.getXmin() + leftSpace, transform.getYmax() - height/2 - 3*pixelWidth);
			g.setColor(RUNGE_KUTTA_PROJECTED_ORBIT_Z_COLOR);
			drawString("z", transform.getXmin() + leftSpace, transform.getYmax() - 3*height/4 - 3*pixelWidth);
			double left = transform.getXmin() + 3*leftSpace;
			double dt = (transform.getXmax() - transform.getXmin() - 3*leftSpace)/(maxNumberOfPoints - 1);
			if (owner.getOrbitType() >= ORBIT_TYPE_BOTH && eulerPoints.size() > 0) {
				Vector3D pt1 = eulerPoints.get(0);
				for (int i = 1; i < eulerPoints.size(); i++) {
					double t = left + i*dt;
					Vector3D pt2 = eulerPoints.get(i);
					if (pt1 != null && pt2 != null) {
						g.setColor(EULER_PROJECTED_ORBIT_X_COLOR);
						drawLine(t-dt,pt1.x,t,pt2.x);
						g.setColor(EULER_PROJECTED_ORBIT_Y_COLOR);
						drawLine(t-dt,pt1.y,t,pt2.y);
						g.setColor(EULER_PROJECTED_ORBIT_Z_COLOR);
						drawLine(t-dt,pt1.z,t,pt2.z);
					}
					pt1 = pt2;
				}
			}
			if (owner.getOrbitType() <= ORBIT_TYPE_BOTH && rungeKuttaPoints.size() > 0) {
				Vector3D pt1 = rungeKuttaPoints.get(0);
				for (int i = 1; i < rungeKuttaPoints.size(); i++) {
					double t = left + i*dt;
					Vector3D pt2 = rungeKuttaPoints.get(i);
					if (pt1 != null && pt2 != null) {
						g.setColor(RUNGE_KUTTA_PROJECTED_ORBIT_X_COLOR);
						drawLine(t-dt,pt1.x,t,pt2.x);
						g.setColor(RUNGE_KUTTA_PROJECTED_ORBIT_Y_COLOR);
						drawLine(t-dt,pt1.y,t,pt2.y);
						g.setColor(RUNGE_KUTTA_PROJECTED_ORBIT_Z_COLOR);
						drawLine(t-dt,pt1.z,t,pt2.z);
					}
					pt1 = pt2;
				}
			}
		}
	}
	
	/**
	 * Represents one integral curve of the vector field.
	 */
	private class Orbit extends Decoration {
		double[] initialData;
		double[] currentEulerData;
		double[] currentRungeKuttaData;
		OrbitPoints3D eulerPoints, rungeKuttaPoints;
		ODEView view;
		boolean isCurrentOrbit;
		int orbitType;
		double dt;
		int pointCount;
		Orbit(ODEView view, double[] initialData, int orbitType, double dt) {
			this.view = view;
			this.initialData = initialData;
			this.orbitType = orbitType;
			this.dt = dt;
			eulerPoints = new OrbitPoints3D();
			rungeKuttaPoints = new OrbitPoints3D();
			eulerPoints.setColor(EULER_ORBIT_COLOR);
			eulerPoints.setStyle(OrbitPoints2D.DOTS);
			rungeKuttaPoints.setColor(RUNGE_KUTTA_ORBIT_COLOR);
			rungeKuttaPoints.setStyle(OrbitPoints2D.DOTS);
			Vector3D startPoint = extractPointFromData(initialData);
			eulerPoints.addPoint(startPoint);
			rungeKuttaPoints.addPoint(startPoint);
			currentEulerData = orbitType == ORBIT_TYPE_RUNGE_KUTTA ? null : initialData.clone();
			currentRungeKuttaData = orbitType == ORBIT_TYPE_EULER ? null : initialData.clone();
			pointCount = 1;
		}
		void setStyle(int style) {
			eulerPoints.setStyle(style);
			rungeKuttaPoints.setStyle(style);
		    fireDecorationChangeEvent();
		}
		int getOrbitType() {
			return orbitType;
		}
		OrbitPoints3D getEulerPoints() {
			return eulerPoints;
		}
		OrbitPoints3D getRungeKuttaPoints() {
			return rungeKuttaPoints;
		}
		int getPointCount() {
			return pointCount;
		}
		void setPointCount(int ct) {
			pointCount = ct;
			if (isCurrentOrbit && view.projectedOrbitView != null)
				view.projectedOrbitView.setMaxPoints(pointCount);
			forceRedraw();
		}
		void removePoints() {  // called only when the Create animation is going to redraw the curve.
			eulerPoints.clear();
			rungeKuttaPoints.clear();
			Vector3D startPoint = extractPointFromData(initialData);
			eulerPoints.addPoint(startPoint);
			rungeKuttaPoints.addPoint(startPoint);
			currentEulerData = orbitType == ORBIT_TYPE_RUNGE_KUTTA ? null : initialData.clone();
			currentRungeKuttaData = orbitType == ORBIT_TYPE_EULER ? null : initialData.clone();
			pointCount = 1;
			forceRedraw();
		}
		Vector3D getEulerPoint(int i) {
			if (i >= eulerPoints.getPointCount())
				return null;
			else
				return eulerPoints.getPoint(i);
		}
		Vector3D getRungeKuttaPoint(int i) {
			if (i >= rungeKuttaPoints.getPointCount())
				return null;
			else
				return rungeKuttaPoints.getPoint(i);
		}
		void setOrbitType(int type) {
			orbitType = type;
			forceRedraw();
		}
		void setIsCurrentOrbit(boolean b) {
			if (isCurrentOrbit && view.projectedOrbitView != null)
				view.projectedOrbitView.clear();
			isCurrentOrbit = b;
			eulerPoints.setColor( isCurrentOrbit ? EULER_ORBIT_COLOR : OLD_EULER_ORBIT_COLOR);
			rungeKuttaPoints.setColor( isCurrentOrbit ? RUNGE_KUTTA_ORBIT_COLOR : OLD_RUNGE_KUTTA_ORBIT_COLOR);
			if (isCurrentOrbit && view.projectedOrbitView != null) {
				view.projectedOrbitView.setMaxPoints(pointCount);
				view.projectedOrbitView.resetPointsFromOrbit(this);
			}
		}
		boolean addNextPoint() {
			Vector3D eulerPt = null, rkPoint = null;
			if (orbitType >= ORBIT_TYPE_BOTH) {
				if (currentEulerData != null) {
					nextEulerPoint(currentEulerData, dt);
					for (double d : currentEulerData) {
						if (Double.isNaN(d) || Double.isInfinite(d)) {
							currentEulerData = null;
							break;
						}
					}
					if (currentEulerData != null)
						eulerPt = extractPointFromData(currentEulerData);
				}
			}
			if (orbitType <= ORBIT_TYPE_BOTH) {
				if (currentRungeKuttaData != null) {
					nextRungeKuttaPoint(currentRungeKuttaData, dt);
					for (double d : currentRungeKuttaData) {
						if (Double.isNaN(d) || Double.isInfinite(d)) {
							currentRungeKuttaData = null;
							break;
						}
					}
					if (currentRungeKuttaData != null)
						rkPoint = extractPointFromData(currentRungeKuttaData);
				}
			}
			if (eulerPt == null && rkPoint == null)
				return false;
			if (!isAutonomous) {
				double d = Double.NEGATIVE_INFINITY;
				if (currentEulerData != null)
					d = currentEulerData[0];
				if (currentRungeKuttaData != null && currentRungeKuttaData[0] > d)
					d = currentRungeKuttaData[0];
				view.currentTime = d;
			}
			pointCount++;
			boolean ok = true;
			if (eulerPt != null)
				ok = eulerPoints.addNow(view,eulerPt);
			if (rkPoint != null)
				ok = ok && rungeKuttaPoints.addNow(view,rkPoint);
			if (!ok)
				view.forceRedraw();
			if (isCurrentOrbit && view.projectedOrbitView != null)
				view.projectedOrbitView.addPoints(eulerPt,rkPoint);
			return true;
		}
		public void computeDrawData(View v, boolean exhibitNeedsRedraw, Transform previousTransform, Transform newTransform) {
			if ((decorationNeedsRedraw || exhibitNeedsRedraw) && pointCount > 1) { 
				    // Recompute all the points on the orbit when exhibit properties change
				currentEulerData = currentRungeKuttaData = null;
				if (orbitType >= ORBIT_TYPE_BOTH) {
					currentEulerData = initialData.clone();
					eulerPoints.clear();
					eulerPoints.addPoint(extractPointFromData(initialData));
					for (int i = 1; i < pointCount; i++) {
						nextEulerPoint(currentEulerData, dt);
						for (double d : currentEulerData) {
							if (Double.isNaN(d) || Double.isInfinite(d)) {
								currentEulerData = null;
								break;
							}
						}
						if (currentEulerData == null)
							break;
						else
							eulerPoints.addPoint(extractPointFromData(currentEulerData));
					}
				}
				if (orbitType <= ORBIT_TYPE_BOTH) {
					currentRungeKuttaData = initialData.clone();
					rungeKuttaPoints.clear();
					rungeKuttaPoints.addPoint(extractPointFromData(initialData));
					for (int i = 1; i < pointCount; i++) {
						nextRungeKuttaPoint(currentRungeKuttaData, dt);
						for (double d : currentRungeKuttaData) {
							if (Double.isNaN(d) || Double.isInfinite(d)) {
								currentRungeKuttaData = null;
								break;
							}
						}
						if (currentRungeKuttaData == null)
							break;
						else
							rungeKuttaPoints.addPoint(extractPointFromData(currentRungeKuttaData));
					}
				}
				if (isCurrentOrbit && view.projectedOrbitView != null)
					view.projectedOrbitView.resetPointsFromOrbit(this);
			}
		}
		public void doDraw(Graphics2D g, View view, Transform transform) {
			if (orbitType >= ORBIT_TYPE_BOTH)
				eulerPoints.draw(g,(View3D)view,transform);
			if (orbitType <= ORBIT_TYPE_BOTH)
				rungeKuttaPoints.draw(g,(View3D)view,transform);
		}
	}
	
	/**
	 * An animation that extends an integral curve, point-by-point over a period of time, for a
	 * specified number of points.  An animation of this type is used when the user starts a
	 * new orbit or uses the "Continue Orbit" command.
	 */
	private class ExtendOrbitAnimation extends TimerAnimation {
		ODEView view;
		Orbit orbit;
		int finalNumberOfPoints;
		ExtendOrbitAnimation(ODEView view, Orbit orbit, int numberOfPointsToAdd) {
			super(numberOfPointsToAdd,10);
			this.view = view;
			this.orbit = orbit;
			this.finalNumberOfPoints = orbit.getPointCount() + numberOfPointsToAdd;
			if (orbit.isCurrentOrbit && view.projectedOrbitView != null)
				view.projectedOrbitView.setMaxPoints(orbit.getPointCount() + numberOfPointsToAdd + 1);
		}
		protected void drawFrame() {
			if (!view.getAnimateDrawing()) {  // in case animation is turned off while this animation is in progress.
				orbit.setPointCount(finalNumberOfPoints);
				cancel();
				return;
			}
			boolean nextPoint = orbit.addNextPoint();
			if (nextPoint == false)
				cancel();
			if (canShowVectorField && !isAutonomous)
				view.forceRedraw();
		}
		protected void animationStarting() {
			if (canShowVectorField && !isAutonomous)
				view.setShowDirectionField(true);
		}
		protected void animationEnding() {
			orbit.forceRedraw();  // Make sure orbit appears in all views after it is fully drawn.
			if (canShowVectorField && !isAutonomous)
				view.setShowDirectionField(false);
		}
	}
	
	private class ControlPanel extends JPanel {
		ODEView owner;
		JTextField[] icInputs;  // initial condition inputs
		JTextField dtInput, timeSpanInput;
		JButton startOrbitButton;
		ControlPanel(ODEView view) {
			setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			JPanel componentPanel = new JPanel();
			componentPanel.setLayout(new GridLayout(0,1));
			add(componentPanel);
			owner = view;
			Font font = new Font("SansSerif", Font.BOLD, 10);
			Font inputFont = new Font("SansSerif", Font.PLAIN, 10);
			startOrbitButton = new JButton(I18n.tr("vmm.ode.command.StartOrbitAt"));
			startOrbitButton.setFont(font);
			componentPanel.add(startOrbitButton);
			icInputs = new JTextField[inputLabelNames.length];
			for (int i = 0; i < inputLabelNames.length; i++) {
				icInputs[i] = new JTextField(6);
				icInputs[i].setFont(inputFont);
				JPanel p = new JPanel();
				JLabel lbl = new JLabel(inputLabelNames[i] + " =");
				lbl.setFont(font);
				p.add(lbl);
				p.add(icInputs[i]);
				componentPanel.add(p);
			}
			dtInput = new JTextField(""+dtDefault,4);
			timeSpanInput = new JTextField(""+timeSpanDefault,3);
			dtInput.setFont(inputFont);
			timeSpanInput.setFont(inputFont);
			JPanel p = new JPanel();
			JLabel lbl = new JLabel(I18n.tr("vmm.ode.StepSize") + "=");
			lbl.setFont(font);
			p.add(lbl);
			p.add(dtInput);
			componentPanel.add(p);
			p = new JPanel();
			lbl = new JLabel(I18n.tr("vmm.ode.TimeSpan") + "=");
			lbl.setFont(font);
			p.add(lbl);
			p.add(timeSpanInput);
			componentPanel.add(p);
			componentPanel.add(Box.createVerticalStrut(1));
			JButton b = new JButton(view.continueOrbitAction);
			b.setFont(font);
			componentPanel.add(b);
			b = new JButton(view.eraseOrbitsAction);
			b.setFont(font);
			componentPanel.add(b);
			if (addAnimateCheckBoxontrolPanel) {
				JCheckBox cb = owner.animateDrawingToggle.createCheckBox();
				cb.setFont(font);
				cb.setText(I18n.tr("vmm.ode.command.AnimateDrawing.short"));
				componentPanel.add(cb);
			}
			if (addLinesCheckBoxontrolPanel) {
				JCheckBox cb = owner.connectDotsToggle.createCheckBox();
				cb.setFont(font);
				cb.setText(I18n.tr("vmm.ode.command.ConnectDotsOnOrbit.short"));
				componentPanel.add(cb);
			}
			if (addOrbitTypesToControlPanel) {
				JRadioButton[] radioButtons = view.orbitTypeSelect.createRadioButtons();
				for (JRadioButton rb : radioButtons) {
					rb.setFont(font);
					componentPanel.add(rb);
				}
			}
			startOrbitButton.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					owner.getDisplay().stopAnimation();
					double[] ic;
					double dt,timeSpan;
					ic = new double[icInputs.length];
					for (int i = 0; i < icInputs.length; i++) {
						try{
							ic[i] = Double.parseDouble(icInputs[i].getText());
						}
						catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(owner.getDisplay(), 
									I18n.tr("vmm.ode.error.BadNumberInput",inputLabelNames[i]));
							return;
						}
					}
					try {
						dt = Double.parseDouble(dtInput.getText());
						if (dt <= 0)
							throw new NumberFormatException();
					}
					catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(owner.getDisplay(), I18n.tr("vmm.ode.error.BadPositiveNumberInput","dt"));
						return;
					}
					try {
						timeSpan = Double.parseDouble(timeSpanInput.getText());
						if (timeSpan <= 0)
							throw new NumberFormatException();
					}
					catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(owner.getDisplay(), I18n.tr("vmm.ode.error.BadPositiveNumberInput",I18n.tr("vmm.ode.TimeSpan")));
						return;
					}
					owner.startOrbitAtPoint(ic,dt,timeSpan);
				}
			});
		}
		void resetStartPointInputText(double[] ic) {
			for (int i = 0; i < icInputs.length; i++) {
				try {
					if ( Math.abs( ic[i] - Double.parseDouble(icInputs[i].getText())) < 5e-10 )
						continue;
				}
				catch (Exception e) {
				}
				String str = String.format("%.4g", ic[i]);  // 4 significant digits
				icInputs[i].setText(str);
			}
		}
	}
	
	
	
}
