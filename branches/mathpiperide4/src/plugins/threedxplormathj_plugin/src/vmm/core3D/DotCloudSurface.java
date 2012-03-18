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
import java.util.Random;

import vmm.core.Decoration;
import vmm.core.Transform;
import vmm.core.VMMSave;
import vmm.core.View;

abstract public class DotCloudSurface extends Decoration {
	
	@VMMSave
	private int dotCount = 10000;
	
	@VMMSave
	private Color color = Color.GRAY;
	
	private Vector3D[] points;
	
	abstract protected Vector3D makeRandomPixel(Random randomNumberGenerator);
	
	public void setDotCount(int count) {
		dotCount = count;
		forceRedraw();
	}
	
	public int getDotCount() {
		return dotCount;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		if (color == null)
			this.color = Color.GRAY;
		else
			this.color = color;
	}
	
	

	public void computeDrawData(View view, boolean exhibitNeedsRedraw, Transform previousTransform, Transform newTransform) {
		if (points == null || exhibitNeedsRedraw || decorationNeedsRedraw) {
			points = new Vector3D[dotCount];
			Random rand = new Random(42);
			for (int i = 0; i < points.length; i++)
				points[i] = makeRandomPixel(rand);
		}
	}

	public void doDraw(Graphics2D g, View view, Transform transform) {
		View3D view3D = (View3D)view;
		Color saveColor = g.getColor();
		if ( view3D.getViewStyle() == View3D.RED_GREEN_STEREO_VIEW )
			g.setColor(Color.WHITE);
		else
			g.setColor(color);
		view3D.drawPixels(points);
		g.setColor(saveColor);
	}

}
