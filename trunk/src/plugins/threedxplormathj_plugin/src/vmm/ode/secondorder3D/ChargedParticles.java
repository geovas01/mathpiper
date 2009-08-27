/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder3D;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import vmm.core.Display;
import vmm.core.I18n;
import vmm.core.MouseTask;
import vmm.core.Util;
import vmm.core.View;
import vmm.core3D.BasicMouseTask3D;
import vmm.core3D.Vector3D;
import vmm.core3D.View3D;
import vmm.ode.ODE_3D;

abstract public class ChargedParticles extends ODE_3D {

	public ChargedParticles() {
		super(false,true,"x","y","z","x'","y'","z'");
		showAxes = true;
	}
	
	protected void nextEulerPoint(double[] pointData, double dt) {
		Vector3D deriv = new Vector3D();
		double x = pointData[0];
		double y = pointData[1];
		double z = pointData[2];
		double xdot = pointData[3];
		double ydot = pointData[4];
		double zdot = pointData[5];
		dotdot(x,y,z,xdot,ydot,zdot,deriv);
		pointData[0] = x + dt*xdot;  // Why not x + dt(xdot+dt*deriv.x)/2; ??
		pointData[1] = y + dt*ydot;
		pointData[2] = z + dt*zdot;
		pointData[3] = xdot + dt*deriv.x;
		pointData[4] = ydot + dt*deriv.y;
		pointData[5] = zdot + dt*deriv.z;
	}

	protected void nextRungeKuttaPoint(double[] pointData, double dt) {
		Vector3D deriv = new Vector3D();
		double x = pointData[0];
		double y = pointData[1];
		double z = pointData[2];
		double xdot = pointData[3];
		double ydot = pointData[4];
		double zdot = pointData[5];
		double i1,i2,i3,i4, j1,j2,j3,j4, m1,m2,m3,m4, h1,h2,h3,h4, k1,k2,k3,k4, n1,n2,n3,n4;
		i1 = dt * xdot;
		j1 = dt * ydot;
		m1 = dt * zdot;
		dotdot(x,y,z,xdot,ydot,zdot,deriv);
		h1 = dt * deriv.x;
		k1 = dt * deriv.y;
		n1 = dt * deriv.z;
		//dotdot(x,y,z,xdot,ydot,zdot,deriv); // moved 3 lines up HKOct16
		i2 = dt * (xdot + h1/2);
		j2 = dt * (ydot + k1/2);
		m2 = dt * (zdot + n1/2);
		dotdot(x+i1/2,y+j1/2,z+m1/2,xdot+h1/2,ydot+k1/2,zdot+n1/2,deriv);
		h2 = dt * deriv.x;
		k2 = dt * deriv.y;
		n2 = dt * deriv.z;
		i3 = dt * (xdot + h2/2);
		j3 = dt * (ydot + k2/2);
		m3 = dt * (zdot + n2/2);
		dotdot(x+i2/2,y+j2/2,z+m2/2,xdot+h2/2,ydot+k2/2,zdot+n2/2,deriv);
		h3 = dt * deriv.x;
		k3 = dt * deriv.y;
		n3 = dt * deriv.z;
		i4 = dt * (xdot + h3);
		j4 = dt * (ydot + k3);
		m4 = dt * (zdot + n3);
		dotdot(x+i3,y+j3,z+m3,xdot+h3,ydot+k3,zdot+n3,deriv);
		h4 = dt * deriv.x;
		k4 = dt * deriv.y;
		n4 = dt * deriv.z;
		double dx = i1/6 + i2/3 + i3/3 + i4/6;
		double dy = j1/6 + j2/3 + j3/3 + j4/6;
		double dz = m1/6 + m2/3 + m3/3 + m4/6;
		double dxdot = h1/6 + h2/3 + h3/3 + h4/6; 
		double dydot = k1/6 + k2/3 + k3/3 + k4/6;
		double dzdot = n1/6 + n2/3 + n3/3 + n4/6;
		pointData[0] = x + dx;
		pointData[1] = y + dy;
		pointData[2] = z + dz;
		pointData[3] = xdot + dxdot;
		pointData[4] = ydot + dydot;
		pointData[5] = zdot + dzdot;
	}
	
	private void dotdot(double x, double y, double z, double xdot, double ydot, double zdot, Vector3D answer) {	
		magneticField(x,y,z,answer);
		/*double magX = answer.x;  // Not thread-safe HK??
		double magY = answer.y;
		double magZ = answer.z;*/
		Vector3D magfield = new Vector3D();
		magneticField(x,y,z,magfield);
		double magX = magfield.x;
		double magY = magfield.y;
		double magZ = magfield.z;
		// MagneticField(t, x, y, z).y * w - MagneticField(t, x, y, z).z * v
		answer.x = magY * zdot - magZ * ydot;
		// (MagneticField(t, x, y, z).z * u - MagneticField(t, x, y, z).x * w)
		answer.y = magZ * xdot - magX * zdot;
		// (MagneticField(t, x, y, z).x * v - MagneticField(t, x, y, z).y * u)
		answer.z = magX * ydot - magY *xdot;
	}
	
	/**
	 * Store the value of the magnetice field at x,y,z into the prexisting vector answer
	 */
	abstract protected void magneticField(double x, double y, double z, Vector3D answer);
	
	
	
	protected MouseTask makeDefaultMouseTask(ODEView view) {
		return new ThrowIt();
	}


	private class ThrowIt extends BasicMouseTask3D {
		int startX, startY;
		int currentX, currentY;
		boolean throwing;
		public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
			if (evt.isAltDown()) {
				throwing = true;
				startX = currentX = evt.getX();
				startY = currentY = evt.getY();
				return true;
			}
			return super.doMouseDown(evt, display, view, width, height);
		}
		public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
			if (throwing) {
				currentX = evt.getX();
				currentY = evt.getY();
				view.forceRedraw();
			}
			else
				super.doMouseDrag(evt, display, view, width, height);
		}
		public void doMouseUp(MouseEvent evt, Display display, View view, int width, int height) {
			if (throwing) {
				throwing = false;
				ODEView odeView = (ODEView)view;
				Vector3D pt1 = screenPointTo3DPoint(odeView, startX, startY);
				Vector3D pt2 = screenPointTo3DPoint(odeView, currentX, currentY);
				double[] data = new double[] { pt1.x, pt1.y, pt1.z, pt2.x-pt1.x, pt2.y-pt1.y, pt2.z-pt1.z };
				((ODEView)view).startOrbitAtPoint(data);
			}
			else
				super.doMouseUp(evt, display, view, width, height);
		}
		public void drawWhileDragging(Graphics2D g, Display display, View view, int width, int height) {
			if (throwing) {
				if (((View3D)view).getViewStyle() == View3D.RED_GREEN_STEREO_VIEW)
					g.setColor(Color.YELLOW);
				else
					g.setColor(view.getForeground());
				g.drawLine(startX,startY,currentX,currentY);
			}
			else
				super.drawWhileDragging(g,display,view,width,height);
		}
		public String getStatusText() {
			if (Util.isMacOS())
				return I18n.tr("vmm.ode.secondorder2D.mouseTaskStatusText.mac");
			else
				return I18n.tr("vmm.ode.secondorder2D.mouseTaskStatusText");
		}
	}

}
