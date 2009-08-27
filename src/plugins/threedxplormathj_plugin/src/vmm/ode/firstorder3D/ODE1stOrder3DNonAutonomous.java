/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.firstorder3D;

import java.awt.event.MouseEvent;

import vmm.core.Display;
import vmm.core.I18n;
import vmm.core.MouseTask;
import vmm.core.Util;
import vmm.core.View;
import vmm.core3D.BasicMouseTask3D;
import vmm.core3D.Vector3D;
import vmm.ode.ODE_3D;

abstract public class ODE1stOrder3DNonAutonomous extends ODE_3D {
	

	/**
	 * Defines the x-component of the vector field.
	 */
	abstract protected double xPrime(double x, double y, double z, double t);
	
	/**
	 * Defines the y-component of the vector field.
	 */
	abstract protected double yPrime(double x, double y, double z, double t);

	
	/**
	 * Defines the z-component of the vector field.
	 */
	abstract protected double zPrime(double x, double y, double z, double t);

		
	protected void nextEulerPoint(double[] pointData, double dt) {
		double t = pointData[0];
		double x = pointData[1];
		double y = pointData[2];
		double z = pointData[3];
		double dx = dt * xPrime(x,y,z,t);
		double dy = dt * yPrime(x,y,z,t);
		double dz = dt * zPrime(x,y,z,t);
		pointData[0] = t + dt;
		pointData[1] = x + dx;
		pointData[2] = y + dy;
		pointData[3] = z + dz;
	}

	protected void nextRungeKuttaPoint(double[] pointData, double dt) {
		double t = pointData[0];
		double x = pointData[1];
		double y = pointData[2];
		double z = pointData[3];
		double h1,h2,h3,h4,k1,k2,k3,k4,j1,j2,j3,j4;
		h1 = dt * xPrime(x,y,z,t);
		k1 = dt * yPrime(x,y,z,t);
		j1 = dt * zPrime(x,y,z,t);
		h2 = dt * xPrime(x+h1/2,y+k1/2,z+j1/2,t+dt/2);
		k2 = dt * yPrime(x+h1/2,y+k1/2,z+j1/2,t+dt/2);
		j2 = dt * zPrime(x+h1/2,y+k1/2,z+j1/2,t+dt/2);
		h3 = dt * xPrime(x+h2/2,y+k2/2,z+j2/2,t+dt/2);
		k3 = dt * yPrime(x+h2/2,y+k2/2,z+j2/2,t+dt/2);
		j3 = dt * zPrime(x+h2/2,y+k2/2,z+j2/2,t+dt/2);
		h4 = dt * xPrime(x+h3,y+k3,z+j3,t+dt);
		k4 = dt * yPrime(x+h3,y+k3,z+j3,t+dt);
		j4 = dt * zPrime(x+h3,y+k3,z+j3,t+dt);
		double dx = h1/6 + h2/3 + h3/3 + h4/6; 
		double dy = k1/6 + k2/3 + k3/3 + k4/6;
		double dz = j1/6 + j2/3 + j3/3 + j4/4;
		pointData[0] = t + dt;
		pointData[1] = x + dx;
		pointData[2] = y + dy;
		pointData[3] = z + dz;
	}

	public ODE1stOrder3DNonAutonomous() {
		super(false, false, "t", "x", "y", "z");
		initialDataDefault = new double[] { 0, 1, 1, 1 };
	}
	
	protected MouseTask makeDefaultMouseTask(ODEView view) {
		return new StartOrbit();
	}
	
	private class StartOrbit extends BasicMouseTask3D {
		public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
			if (evt.isAltDown()) {						
				ODEView odeView = (ODEView)view;
				double t = odeView.getCurrentTimeFromControlPanel();
				Vector3D pt = screenPointTo3DPoint(odeView, evt.getX(), evt.getY());
				if (pt != null)
					odeView.startOrbitAtPoint(new double[] { t, pt.x, pt.y, pt.z });
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
