/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.planecurve.parametric;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import vmm.actions.AbstractActionVMM;
import vmm.actions.ActionList;
import vmm.core.Complex;
import vmm.core.Decoration;
import vmm.core.Display;
import vmm.core.I18n;
import vmm.core.MouseTask;
import vmm.core.RealParamAnimateable;
import vmm.core.Transform;
import vmm.core.View;

/**
 * A parabola with a focus on the x-axis.  The parabola is defined by its focal length.
 */
public class Parabola extends ConicSection {
	
	private RealParamAnimateable focalLength;

	/**
	 * Construct a parabola with a focus initially at the point (0.75.0).  The focal length
	 * is an animateable parameter with animation start and end values initially set to 0.5 and 1.5.
	 * The parabola is acutally computed using the parametric equations (t,t*t/(4*f)), where f is the focal
	 * length and t ranges from -8 to 8.
	 *
	 */
	public Parabola() {
		focalLength = new RealParamAnimateable("vmm.planecurve.parametric.Parabola.FocalLength",0.75,0.5,1.5);
		tResolution.setValueAndDefault(192); // should be divisible by (tmin+tmax)
		tmin.setValueAndDefault(-8);
		tmax.setValueAndDefault(8);
		addParameter(focalLength);
		setDefaultWindow(-8,8,-8,8);
	}

	public double xValue(double t) {
		return (t * t) / (4 * focalLength.getValue());
	}

	public double yValue(double t) {
		return t;
	}

	public double xDerivativeValue(double t) {
		return t / (2 * focalLength.getValue());
	}

	public double yDerivativeValue(double t) {
		return 1;
	}

	public double x2ndDerivativeValue(double t) {
		return 1 / (2 * focalLength.getValue());
	}

	public double y2ndDerivativeValue(double t) {
		return 0;
	}
	
	protected void drawFociAndDirectrix(Graphics2D g, View view, Transform limits, double t) {
		double f = focalLength.getValue();
		Color saveColor = g.getColor();
		double width = Math.abs(limits.getXmax() - limits.getXmin());
		double crossSize = 3*limits.getPixelWidth();
		g.setColor(Color.red);
		g.draw(new Line2D.Double(f-crossSize, -crossSize, f+crossSize, crossSize));
		g.draw(new Line2D.Double(f-crossSize, crossSize, f+crossSize, -crossSize));
		g.setColor(Color.blue);
		g.draw(new Line2D.Double(-f, limits.getYmin(), -f, limits.getYmax()));
		if (Double.isNaN(t)) {
			g.setColor(saveColor);
			return;
		}
		double x = t*t / (4*f);
		double y = t;
		g.setColor(new Color(0,150,0)); // For the tangent line.
		double dx = t / (2*f);
		double dy = 1;
		g.draw(new Line2D.Double(x - width*dx, y - width*dy, x + width*dx, y+width*dy));
		g.setColor(Color.red); // for the line from directix thorugh point on curve
		if (f > 0)
			g.draw(new Line2D.Double(x,y,x+width,y));
		else
			g.draw(new Line2D.Double(x,y,x-width,y));
		g.setColor(Color.magenta); // for the triangle
		g.draw(new Line2D.Double(x,y,-f,y));
		g.draw(new Line2D.Double(-f,y,f,0));
		g.draw(new Line2D.Double(f,0,x,y));
		view.drawString("F",f+limits.getPixelWidth(), 4*limits.getPixelHeight());
		if (focalLength.getValue() > 0)
			view.drawString("S",-f - 10*limits.getPixelWidth(), y);
		else
			view.drawString("S",-f + 3*limits.getPixelWidth(), y);
		g.setColor(saveColor);
	}

    /**
     * Returns a list of actions that are applicable to the exhibit.  To the actions
     * set up by the superclass, this adds a "Drag Mouse to Show Normals" action that
     * lets the user drag the mouse and draws all normal vectors of the parabola that
     * pass through the mouse location.
     */	
	public ActionList getActionsForView(final View view) {
		ActionList actions = super.getActionsForView(view);
		if (view == null)
			return actions;
		actions.add(null);  // Puts a separator in the menu
		actions.add(new AbstractActionVMM(I18n.tr("vmm.planecurve.parametric.Parabola.showNormalsWithMouse")) {
			public void actionPerformed(ActionEvent evt) {
				view.getDisplay().installOneShotMouseTask(new ShowNormalsMouseTask(view));
			}
		});
		return actions;
	}
	
	private class ShowNormalsMouseTask extends MouseTask {
		View view;
		NormalBundleDecoration backgroundDecoration;
		NormalsThroughPoint normals = new NormalsThroughPoint();
		boolean saveShowAxes;
		ShowNormalsMouseTask(View view) {
			this.view = view;
		}
		public void start(Display display,View view) {
			backgroundDecoration = new NormalBundleDecoration(Parabola.this);
			backgroundDecoration.setLayer(-2);
			backgroundDecoration.setNormalsColor(Color.lightGray);
			backgroundDecoration.setPointCount(tResolution.getValue()+1);
			backgroundDecoration.setEvoluteColor(new Color(0,180,180));
			backgroundDecoration.setShowEvolute(true);
			view.addDecoration(backgroundDecoration);
			saveShowAxes = view.getShowAxes();
			view.setShowAxes(true);
		}
		public void finish(Display display,View view) {
			view.removeDecoration(backgroundDecoration);
			view.removeDecoration(normals);
			view.setShowAxes(saveShowAxes);
		}
		public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
			normals = new NormalsThroughPoint();
			normals.setLayer(-1);
			normals.setPixel(evt.getX(), evt.getY());
			view.addDecoration(normals);
			return true;
		}
		public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
			normals.setPixel(evt.getX(), evt.getY());
		}
		public Cursor getCursor(Display display,View view) {
			return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		}
	}
	// The shortest of the three normals is too short so that the triple intersection point is not reached
	private class NormalsThroughPoint extends Decoration {
		int x,y;
		Line2D[] lines = new Line2D.Double[3];
		double[] params;
		Color darkGreen = new Color(0,160,0);
		void setPixel(int x, int y) {
			if (this.x == x && this.y == y)
				return;
			this.x = x;
			this.y = y;
			fireDecorationChangeEvent();
		}
		Complex[] solveCubic(double p, double q) { // solve reduced cubic x^3 - 3*p*x - q = 0
			Complex[] roots = new Complex[3];
			double quarterDiscriminant = q*q/4 - p*p*p;  // one-quarter the the Discriminant of u^2 - qu + p^3 = 0
			Complex halfDiscriminantSqrt = (new Complex(quarterDiscriminant)).integerRoot(2);
			Complex quadraticRoot = (new Complex(q/2)).plus(halfDiscriminantSqrt);  // r1^3 = (x+v)^3, a root of u^2 - qu + p^3 = 0
			Complex xPlusV = quadraticRoot.integerRoot(3);
			     // the other root r2^3 which is (-v)^3 equals ComplexDifference(RealToComplex(q/2),HalfDiscriminantSqrt)
			     // but we use instead the fact that r1*r2 = p to get  (-v)
			Complex minusV = (new Complex(p)).dividedBy(xPlusV);  // (-v) 
			roots[0] = xPlusV.plus(minusV);  // x = (x+v) + (-v) 
			Complex w1 = Complex.polar(1,2*Math.PI/3);  // complex 3-rd roots of unity
			Complex w2 = Complex.polar(1,4*Math.PI/3);
			roots[1] = w1.times(xPlusV).plus(w2.times(minusV));
			roots[2] = w2.times(xPlusV).plus(w1.times(minusV));
			return roots;
		}
		public void computeDrawData(View view, boolean needsRedraw, Transform previousLimits, Transform newLimits) {
			double xcoord = newLimits.getXmin() + (x - newLimits.getX()) *  newLimits.getPixelWidth();
			double ycoord = newLimits.getYmax() - (y - newLimits.getY()) * newLimits.getPixelHeight();
			double normalLength = 10*Math.abs(newLimits.getXmax() - newLimits.getXmin());
			double a = focalLength.getValue();
			Complex[] roots = solveCubic((4.0/3.0) * a * xcoord - (8.0/3.0) * a * a, 8 * a * a * ycoord);
			for (int i = 0; i < 3; i++) {
				if (Math.abs(roots[i].im) > 1e-5) {
					lines[i] = null;
				}
				else {
					double x1 = xValue(roots[i].re);
					double y1 = yValue(roots[i].re);
					double dx = xcoord - x1;
					double dy = ycoord - y1;
					double length = Math.sqrt(dx*dx + dy*dy);
					lines[i] = new Line2D.Double(x1,y1,x1+dx*normalLength/length,y1+dy*normalLength/length);
				}
			}
			if (Math.abs(roots[0].im) > 1e-5 || Math.abs(roots[1].im) > 1e-5 || Math.abs(roots[2].im) > 1e-5)
				params = null;
			else
				params = new double[] { roots[0].re, roots[1].re, roots[2].re };
		}
		public void doDraw(Graphics2D g, View view, Transform limits) {
			Color saveColor = g.getColor();
			if (lines[0] != null) {
				g.setColor(Color.red);
				g.draw(lines[0]);
			}
			if (lines[1] != null) {
				g.setColor(darkGreen);
				g.draw(lines[1]);
			}
			if (lines[2] != null) {
				g.setColor(Color.blue);
				g.draw(lines[2]);
			}
			if (params != null) {
				double scale = limits.getPixelWidth();
				if (focalLength.getValue() < 0)
					scale = -scale;
				double gap = limits.getPixelHeight();
				g.setColor(Color.red);
				g.draw(new Line2D.Double(-scale*90,0,-scale*90,params[0]));
				g.setColor(darkGreen);
				g.draw(new Line2D.Double(-scale*80,0,-scale*80,params[1]));
				g.setColor(Color.blue);
				g.draw(new Line2D.Double(-scale*70,0,-scale*70,params[2]));
				double p[] = new double[] {Math.abs(params[0]),Math.abs(params[1]),Math.abs(params[2]) };
				if (p[0] >= p[1] && p[0] >= p[2]) {
					g.setColor(Color.red);
					g.draw(new Line2D.Double(-scale*150,0,-scale*150,p[0]));
					g.setColor(darkGreen);
					g.draw(new Line2D.Double(-scale*145,0,-scale*145,p[1]-gap));
					g.setColor(Color.blue);
					g.draw(new Line2D.Double(-scale*145,p[1]+gap,-scale*145,p[1]+p[2]));
				}
				else if (p[1] >= p[0] && p[1] >= p[2]) {
					g.setColor(darkGreen);
					g.draw(new Line2D.Double(-scale*150,0,-scale*150,p[1]));
					g.setColor(Color.red);
					g.draw(new Line2D.Double(-scale*145,0,-scale*145,p[0]-gap));
					g.setColor(Color.blue);
					g.draw(new Line2D.Double(-scale*145,p[0]+gap,-scale*145,p[0]+p[2]));
				}
				else {
					g.setColor(Color.blue);
					g.draw(new Line2D.Double(-scale*150,0,-scale*150,p[2]));
					g.setColor(darkGreen);
					g.draw(new Line2D.Double(-scale*145,0,-scale*145,p[1]-gap));
					g.setColor(Color.red);
					g.draw(new Line2D.Double(-scale*145,p[1]+gap,-scale*145,p[1]+p[0]));
				}
			}
			g.setColor(saveColor);
		}
	}

}
