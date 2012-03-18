/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder2D;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import vmm.core.BasicMouseTask2D;
import vmm.core.Display;
import vmm.core.I18n;
import vmm.core.MouseTask;
import vmm.core.Util;
import vmm.core.View;
import vmm.ode.ODE_2D;

abstract public class ODE1stOrder2DNonAutonomous extends ODE_2D {

	public ODE1stOrder2DNonAutonomous() {
		super(true, false, "t", "x", "y");
		initialDataDefault = new double[] { 0, 1, 1 };  // default initial data in control panel.
	}

	/**
	 * Defines the x1-component of the vector field.
	 */
	abstract protected double x1Prime(double x, double y, double t);
	
	/**
	 * Defines the x2-component of the vector field.
	 */
	abstract protected double x2Prime(double x, double y, double t);
	
	
	final protected double vectorField_x(double x, double y, double t) {
		return x1Prime(x,y,t);
	}
	
	final protected double vectorField_y(double x, double y, double t) {
		return x2Prime(x,y,t);
	}
	
	protected void nextEulerPoint(double[] pointData, double dt) {
		double t = pointData[0];
		double x1 = pointData[1];
		double x2 = pointData[2];
		double dx1 = dt * x1Prime(x1,x2,t);
		double dx2 = dt * x2Prime(x1,x2,t);
		pointData[0] = t + dt;
		pointData[1] = x1 + dx1;
		pointData[2] = x2 + dx2;
	}

	protected void nextRungeKuttaPoint(double[] pointData, double dt) {
		double t = pointData[0];
		double x1 = pointData[1];
		double x2 = pointData[2];
		double h1,h2,h3,h4,k1,k2,k3,k4;
		h1 = dt * x1Prime(x1,x2,t);
		k1 = dt * x2Prime(x1,x2,t);
		h2 = dt * x1Prime(x1+ h1/2,x2+k1/2,t+dt/2);
		k2 = dt * x2Prime(x1+ h1/2,x2+k1/2,t+dt/2);
		h3 = dt * x1Prime(x1+ h2/2,x2+k2/2,t+dt/2);
		k3 = dt * x2Prime(x1+ h2/2,x2+k2/2,t+dt/2);
		h4 = dt * x1Prime(x1+ h3,x2+k3,t+dt);
		k4 = dt * x2Prime(x1+ h3,x2+k3,t+dt);
		double dx1 = h1/6 + h2/3 + h3/3 + h4/6; 
		double dx2 = k1/6 + k2/3 + k3/3 + k4/6;
		pointData[0] = t + dt;
		pointData[1] = x1 + dx1;
		pointData[2] = x2 + dx2;
	}

	protected MouseTask makeDefaultMouseTask(ODEView view) {
		return new StartOrbit();
	}
	
	/**
	 * The mouse task that is used on an ODEView.  It lets the user start an orbit
	 * by ALT-clicking (Option-clicking) or clicking with the middle mouse button.
	 * Other than that, it is a standard BasicMouseTask2D.
	 */
	private class StartOrbit extends BasicMouseTask2D {
		public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
			if (evt.isAltDown()) {
				ODEView odeView = (ODEView)view;
				Point2D pt = new Point2D.Double(evt.getX(),evt.getY());
				view.getTransform().viewportToWindow(pt);
				double time = odeView.getCurrentTimeFromControlPanel();
				odeView.startOrbitAtPoint(new double[] { time, pt.getX(), pt.getY() });
				return false;
			}
			else
				return super.doMouseDown(evt, display, view, width, height);
		}
		public String getStatusText() {
			if (Util.isMacOS())
				return I18n.tr("vmm.ode.firstorder2D.mouseTaskStatusText.mac");
			else
				return I18n.tr("vmm.ode.firstorder2D.mouseTaskStatusText");
		}
	}
}
