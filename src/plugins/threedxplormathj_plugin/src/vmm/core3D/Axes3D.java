/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core3D;

import java.awt.Color;
import java.awt.Graphics2D;

import vmm.core.Axes2D;
import vmm.core.Transform;
import vmm.core.View;

/**
 * A Decoration that displays x-, y-, and z-axes in three-space.  The axes are drawn as line segments
 * from the origin to the points (1,0,0), (0,1,0), and (0,0,1).  This is a subclass of <code>Axes2D</code>
 * so that both 2D and 3D axes can be treated uniformly.
 */
public class Axes3D extends Axes2D {
	
	/**
	 * Create an Axes3D decoration, which is in layer 1 by default.
	 * @see vmm.core.Decoration#setLayer(int)
	 */
	public Axes3D() {
		setLayer(1);
	}

	/**
	 * Draws a set of axes in the specified View.  If the view is not in fact
	 * a three dimensionsal view, then a set of two dimensional axes is drawn.
	 */
	public void doDraw(Graphics2D g, View view, Transform transform) {
		if (! (view instanceof View3D && transform instanceof Transform3D))
			super.doDraw(g,view,transform);
		else {
			Color saveColor = g.getColor();
			if ( ((View3D)view).getViewStyle() == View3D.RED_GREEN_STEREO_VIEW )
				g.setColor(Color.white); // Needs to be white in Anaglyph stereo view to look prominant enough.
			else
				g.setColor(getColor());
			View3D v = (View3D)view;
			v.drawLine(Vector3D.ORIGIN, Vector3D.UNIT_X);
			v.drawLine(Vector3D.ORIGIN, Vector3D.UNIT_Y);
			v.drawLine(Vector3D.ORIGIN, Vector3D.UNIT_Z);
			v.drawString("x",Vector3D.UNIT_X);
			v.drawString("y",Vector3D.UNIT_Y);
			v.drawString("z",Vector3D.UNIT_Z);
			g.setColor(saveColor);
		}
	}
	

}
