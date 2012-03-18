/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.Color;
import java.awt.Graphics2D;


/**
 * A pair of lines representing axes in the xy-plane.
 * The Axes are in layer -100 by default, which will certainly put them behind the exhibit and
 * probably most everything.
 */
public class Axes2D extends Decoration {
	
	@VMMSave
	private Color color = Color.gray;
	
	/**
	 * Construct an Axes2D Decoration, with default color (gray), to be drawn in layer -100.
	 *
	 */
	public Axes2D() {
		setLayer(-100);
	}
	
	/**
	 * Set the color that is used for drawing the axes.
	 * @param c The color of the axes; if the value is null, the default color (gray) is used.
	 */
	public void setColor(Color c) {
		if (c == null)
			color = Color.gray;
		else
			color = c;
	}
	
	/**
	 * Get the color that is used for drawing the axes.
	 * @return the non-null color of the axes
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Draws the axes in a specified View.  Note that if an axis is outside the xy-limits on the view,
	 * then that axis is not drawn.
	 */
	public void doDraw(Graphics2D g, View view, Transform transform) {
		Color saveColor = g.getColor();
		g.setColor(color);
		if (between(0,transform.getYmin(),transform.getYmax()))
			view.drawLine(transform.getXmin(),0,transform.getXmax(),0);
		if (between(0,transform.getXmin(),transform.getYmax()))
			view.drawLine(0,transform.getYmax(),0,transform.getYmin());
		g.setColor(saveColor);
	}
	
	private boolean between(double x, double x1, double x2) {
		if (x <= x1 && x >= x2)
			return true;
		if (x >= x1 && x <= x2)
			return true;
		return false;
	}
	

}
