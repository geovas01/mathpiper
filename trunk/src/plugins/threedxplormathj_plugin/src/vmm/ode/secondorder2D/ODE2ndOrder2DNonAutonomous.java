/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.ode.secondorder2D;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import vmm.core.BasicMouseTask2D;
import vmm.core.Display;
import vmm.core.I18n;
import vmm.core.MouseTask;
import vmm.core.Util;
import vmm.core.View;
import vmm.ode.ODE_2D;

abstract public class ODE2ndOrder2DNonAutonomous extends ODE_2D {

	public ODE2ndOrder2DNonAutonomous() {
		super(false,false,"t","x","y","x'","y'");
		initialDataDefault = new double[] { 0, 1, 0, 0, 1 };
		timeSpanDefault = 30;
	}
	
	protected void nextEulerPoint(double[] pointData, double dt) {
		double t = pointData[0];
		double x = pointData[1];
		double y = pointData[2];
		double xdot = pointData[3];
		double ydot = pointData[4];
		pointData[0] = t + dt;
		pointData[1] = x + dt*xdot;
		pointData[2] = y + dt*ydot;
		pointData[3] = xdot + dt*xdotdot(x,y,xdot,ydot,t);
		pointData[4] = ydot + dt*ydotdot(x,y,xdot,ydot,t);
	}

	protected void nextRungeKuttaPoint(double[] pointData, double dt) {
		double t = pointData[0];
		double x = pointData[1];
		double y = pointData[2];
		double xdot = pointData[3];
		double ydot = pointData[4];
		double i1,i2,i3,i4, j1,j2,j3,j4, h1,h2,h3,h4, k1,k2,k3,k4;
		i1 = dt * xdot;
		j1 = dt * ydot;
		h1 = dt * xdotdot(x,y,xdot,ydot,t);
		k1 = dt * ydotdot(x,y,xdot,ydot,t);
		i2 = dt * (xdot + h1/2);
		j2 = dt * (ydot + k1/2);
		h2 = dt * xdotdot(x+i1/2,y+j1/2,xdot+h1/2,ydot+k1/2,t+dt/2);
		k2 = dt * ydotdot(x+i1/2,y+j1/2,xdot+h1/2,ydot+k1/2,t+dt/2);
		i3 = dt * (xdot + h2/2);
		j3 = dt * (ydot + k2/2);
		h3 = dt * xdotdot(x+i2/2,y+j2/2,xdot+h2/2,ydot+k2/2,t+dt/2);
		k3 = dt * ydotdot(x+i2/2,y+j2/2,xdot+h2/2,ydot+k2/2,t+dt/2);
		i4 = dt * (xdot + h3);
		j4 = dt * (ydot + k3);
		h4 = dt * xdotdot(x+i3,y+j3,xdot+h3,ydot+k3,t+dt);
		k4 = dt * ydotdot(x+i3,y+j3,xdot+h3,ydot+k3,t+dt);
		double dx = i1/6 + i2/3 + i3/3 + i4/6;
		double dy = j1/6 + j2/3 + j3/3 + j4/6;
		double dxdot = h1/6 + h2/3 + h3/3 + h4/6; 
		double dydot = k1/6 + k2/3 + k3/3 + k4/6;
		pointData[0] = t + dt;
		pointData[1] = x + dx;
		pointData[2] = y + dy;
		pointData[3] = xdot + dxdot;
		pointData[4] = ydot + dydot;
	}
	
	abstract protected double xdotdot(double x, double y, double xdot, double ydot, double t);

	abstract protected double ydotdot(double x, double y, double xdot, double ydot, double t);
	
	
	protected MouseTask makeDefaultMouseTask(ODEView view) {
		return new ThrowIt();
	}


	private class ThrowIt extends BasicMouseTask2D {
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
				Point2D pt1 = new Point2D.Double(startX,startY);
				Point2D pt2 = new Point2D.Double(currentX,currentY);
				view.getTransform().viewportToWindow(pt1);
				view.getTransform().viewportToWindow(pt2);
				double time = ((ODEView)view).getCurrentTimeFromControlPanel();
				double[] data = new double[] { time, pt1.getX(), pt1.getY(), pt2.getX()-pt1.getX(), pt2.getY()-pt1.getY() };
				((ODEView)view).startOrbitAtPoint(data);
			}
			else
				super.doMouseUp(evt, display, view, width, height);
		}
		public void drawWhileDragging(Graphics2D g, Display display, View view, int width, int height) {
			if (throwing) {
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
