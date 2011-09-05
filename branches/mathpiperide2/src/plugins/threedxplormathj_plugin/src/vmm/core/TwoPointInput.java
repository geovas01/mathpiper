/*  This file is part of the source code for 3D-XplorMath-J, Version 1.0 (January 2008).
 *  Copyright (c) 2008 The 3D-XplorMath Consortium (http://3d-xplormath.org).
 *  This source code is released under a BSD License, which allows redistribution   
 *  in source and binary form, with or without modification, provided copyright
 *  and license information are included, and with no warranty or guarantee of
 *  any kind.  For details, see http://3d-xplormath.org/j/source/BSDLicense.txt
 */
 
 package vmm.core;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Meant for use as a "one-shot" mouse task that lets the user specify two points
 * by clicking and dragging.  The first point is where the mouse is pressed, the
 * second is where the mouse is released.
 */
abstract public class TwoPointInput extends MouseTask {
	
	abstract protected void gotPoints(Display display, View view, int startX, int startY, int endX, int endY);
	
	public final static int DRAW_NOTHING = 0;  // Values for the figureToDraw property.
	public final static int DRAW_LINE = 1;
	public final static int DRAW_CIRCLE_FROM_RADIUS = 2;
	public final static int DRAW_CIRCLE_FROM_DIAMETER = 3;
	public final static int DRAW_RECT = 4;
	
	private int minimumDrag = 2;                    // Properties.
	private double maximumDrag = Integer.MAX_VALUE;
	private boolean allowOutOfBoundsDrag = true;
	private int figureToDraw = DRAW_LINE;
	private Color drawColor = null;
	
	private int startX, endX, startY, endY;

	
	public boolean getAllowOutOfBoundsDrag() {
		return allowOutOfBoundsDrag;
	}

	public void setAllowOutOfBoundsDrag(boolean allowOutOfBoundsDrag) {
		this.allowOutOfBoundsDrag = allowOutOfBoundsDrag;
	}

	public Color getDrawColor() {
		return drawColor;
	}

	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
	}

	public int getFigureToDraw() {
		return figureToDraw;
	}

	public void setFigureToDraw(int figureToDraw) {
		if (figureToDraw == DRAW_NOTHING  || figureToDraw == DRAW_LINE  || figureToDraw == DRAW_CIRCLE_FROM_DIAMETER ||
				figureToDraw == DRAW_CIRCLE_FROM_RADIUS  || figureToDraw == DRAW_RECT )
		this.figureToDraw = figureToDraw;
	}

	public int getMaximumDrag() {
		return (int)maximumDrag;
	}

	public void setMaximumDrag(int maximumDrag) {
		this.maximumDrag = maximumDrag;
	}

	public int getMinimumDrag() {
		return minimumDrag;
	}

	public void setMinimumDrag(int minimumDrag) {
		this.minimumDrag = minimumDrag;
	}

	public boolean doMouseDown(MouseEvent evt, Display display, View view, int width, int height) {
		startX = endX = evt.getX();
		startY = endY = evt.getY();
		return true;
	}

	public void doMouseDrag(MouseEvent evt, Display display, View view, int width, int height) {
		endX = evt.getX();
		endY = evt.getY();
		display.repaint();
	}

	public void doMouseUp(MouseEvent evt, Display display, View view, int width, int height) {
		int dragDistSquared = (startX-endX)*(startX-endX) + (startY-endY)*(startY-endY);
		if (dragDistSquared < minimumDrag*minimumDrag || dragDistSquared > maximumDrag*maximumDrag)
			return;
		if (!allowOutOfBoundsDrag && (endX < 0 || endY < 0 || endX >= width || endY >= height))
			return;
		gotPoints(display, view, startX, startY, endX, endY);
	}

	public void drawWhileDragging(Graphics2D g, Display display, View view, int width, int height) {
		if (figureToDraw == DRAW_NOTHING)
			return;
		int dragDistSquared = (startX-endX)*(startX-endX) + (startY-endY)*(startY-endY);
		if (dragDistSquared < minimumDrag*minimumDrag || dragDistSquared > maximumDrag*maximumDrag)
			return;
		if (!allowOutOfBoundsDrag && (endX < 0 || endY < 0 || endX >= width || endY >= height))
			return;
		if (drawColor != null)
			g.setColor(drawColor);
		switch (figureToDraw) {
		case DRAW_LINE:
			g.drawLine(startX,startY,endX,endY);
			break;
		case DRAW_CIRCLE_FROM_DIAMETER:
			int diameter = (int)(0.5*Math.sqrt(dragDistSquared));
			int centerX = (endX + startX)/2;
			int centerY = (endY + startY)/2;
			g.drawOval(centerX-diameter/2, centerY-diameter/2, diameter, diameter);
			break;
		case DRAW_CIRCLE_FROM_RADIUS:
			int radius = (int)(0.5 + Math.sqrt(dragDistSquared));
			g.drawOval(startX-radius,startY-radius,2*radius,2*radius);
			break;
		case DRAW_RECT:
			int x1 = Math.min(startX,endX);
			int x2 = Math.max(startX,endX);
			int y1 = Math.min(startY,endY);
			int y2 = Math.max(startY,endY);
			g.drawRect(x1,y1,x2-x1,y2-y1);
			break;
		}
		super.drawWhileDragging(g, display, view, width, height);
	}

	public Cursor getCursor(Display display, View view) {
		return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	}

}
