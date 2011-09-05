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

abstract public class ODE2ndOrder3DNonAutonomous extends ODE_3D {

	public ODE2ndOrder3DNonAutonomous() {
		super(false,false,"t","x","y","z","x'","y'","z'");
		initialDataDefault = new double[] { 0, 1, 1, 0, 0, 0, 1, 1 };
		timeSpanDefault = 30;
	}
	
	protected void nextEulerPoint(double[] pointData, double dt) {
		double t = pointData[0];
		double x = pointData[1];
		double y = pointData[2];
		double z = pointData[3];
		double xdot = pointData[4];
		double ydot = pointData[5];
		double zdot = pointData[6];
		pointData[0] = t + dt;
		pointData[1] = x + dt*xdot;
		pointData[2] = y + dt*ydot;
		pointData[3] = z + dt*zdot;
		pointData[4] = xdot + dt*xdotdot(x,y,z,xdot,ydot,zdot,t);
		pointData[5] = ydot + dt*ydotdot(x,y,z,xdot,ydot,zdot,t);
		pointData[6] = zdot + dt*zdotdot(x,y,z,xdot,ydot,zdot,t);
	}

	protected void nextRungeKuttaPoint(double[] pointData, double dt) {
		double t = pointData[0];
		double x = pointData[1];
		double y = pointData[2];
		double z = pointData[3];
		double xdot = pointData[4];
		double ydot = pointData[5];
		double zdot = pointData[6];
		double i1,i2,i3,i4, j1,j2,j3,j4, m1,m2,m3,m4, h1,h2,h3,h4, k1,k2,k3,k4, n1,n2,n3,n4;
		i1 = dt * xdot;
		j1 = dt * ydot;
		m1 = dt * zdot;
		h1 = dt * xdotdot(x,y,z,xdot,ydot,zdot,t);
		k1 = dt * ydotdot(x,y,z,xdot,ydot,zdot,t);
		n1 = dt * zdotdot(x,y,z,xdot,ydot,zdot,t);
		i2 = dt * (xdot + h1/2);
		j2 = dt * (ydot + k1/2);
		m2 = dt * (zdot + n1/2);
		h2 = dt * xdotdot(x+i1/2,y+j1/2,z+m1/2,xdot+h1/2,ydot+k1/2,zdot+n1/2,t+dt/2);
		k2 = dt * ydotdot(x+i1/2,y+j1/2,z+m1/2,xdot+h1/2,ydot+k1/2,zdot+n1/2,t+dt/2);
		n2 = dt * zdotdot(x+i1/2,y+j1/2,z+m1/2,xdot+h1/2,ydot+k1/2,zdot+n1/2,t+dt/2);
		i3 = dt * (xdot + h2/2);
		j3 = dt * (ydot + k2/2);
		m3 = dt * (zdot + n2/2);
		h3 = dt * xdotdot(x+i2/2,y+j2/2,z+m2/2,xdot+h2/2,ydot+k2/2,zdot+n2/2,t+dt/2);
		k3 = dt * ydotdot(x+i2/2,y+j2/2,z+m2/2,xdot+h2/2,ydot+k2/2,zdot+n2/2,t+dt/2);
		n3 = dt * zdotdot(x+i2/2,y+j2/2,z+m2/2,xdot+h2/2,ydot+k2/2,zdot+n2/2,t+dt/2);
		i4 = dt * (xdot + h3);
		j4 = dt * (ydot + k3);
		m4 = dt * (zdot + n3);
		h4 = dt * xdotdot(x+i3,y+j3,z+m3,xdot+h3,ydot+k3,zdot+n3,t+dt);
		k4 = dt * ydotdot(x+i3,y+j3,z+m3,xdot+h3,ydot+k3,zdot+n3,t+dt);
		n4 = dt * zdotdot(x+i3,y+j3,z+m3,xdot+h3,ydot+k3,zdot+n3,t+dt);
		double dx = i1/6 + i2/3 + i3/3 + i4/6;
		double dy = j1/6 + j2/3 + j3/3 + j4/6;
		double dz = m1/6 + m2/3 + m3/3 + m4/6;
		double dxdot = h1/6 + h2/3 + h3/3 + h4/6; 
		double dydot = k1/6 + k2/3 + k3/3 + k4/6;
		double dzdot = n1/6 + n2/3 + n3/3 + n4/6;
		pointData[0] = t + dt;
		pointData[1] = x + dx;
		pointData[2] = y + dy;
		pointData[3] = z + dz;
		pointData[4] = xdot + dxdot;
		pointData[5] = ydot + dydot;
		pointData[6] = zdot + dzdot;
	}
	
	abstract protected double xdotdot(double x, double y, double z, double xdot, double ydot, double zdot, double t);

	abstract protected double ydotdot(double x, double y, double z, double xdot, double ydot, double zdot, double t);
	
	abstract protected double zdotdot(double x, double y, double z, double xdot, double ydot, double zdot, double t);
	
	
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
				double t = odeView.getCurrentTimeFromControlPanel();
				Vector3D pt1 = screenPointTo3DPoint(odeView, startX, startY);
				Vector3D pt2 = screenPointTo3DPoint(odeView, currentX, currentY);
				double[] data = new double[] { t, pt1.x, pt1.y, pt1.z, pt2.x-pt1.x, pt2.y-pt1.y, pt2.z-pt1.z };
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
