/** @file
 * Copyright (C) 2008 John D Lamb (J.D.Lamb@btinternet.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package jscicalc;

/**
 * This panel is designed to show tiny triangles at the edges of the DisplayLabel.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.1 $
 */
public class MiniPanel extends javax.swing.JPanel {
    
    /**
     * The constructor. Set the parameter to <em>false</em> for the panel on the right
     * of the DisplayLabel
     * @param left Indicates whether panel is on left (<em>true</em>) or right
     * (<em>false</em>)
     */
    public MiniPanel( boolean left ){
	this.left = left;
	setBorder( new javax.swing.border.EmptyBorder( 0, 0, 0, 0 ) );
	setIlluminated( false );
    }
    
    /**
     * This does the work of drawing or redrawing the panel.
     * @param graphics The graphics context required.
     */
    public void paintComponent( java.awt.Graphics graphics ){
	java.awt.Graphics2D g = (java.awt.Graphics2D)graphics;
	g.setRenderingHint( java.awt.RenderingHints.KEY_ANTIALIASING,
			    java.awt.RenderingHints.VALUE_ANTIALIAS_ON );
	graphics.clearRect( 0, 0, getWidth(), getHeight() );
	if( illuminated ){
	    int l = 0;
	    int r = 0;
	    int w = (int)(0.8 * getWidth() + 0.5);
	    int c = (int)(0.8 * getHeight() + 0.5);
	    if( left ){
		r = (int)((getWidth() - w) * 0.5 + 0.5);
		l = r + w;
	    } else {
		l = (int)((getWidth() - w) * 0.5 + 0.5);
		r = l + w;
	    }
	    g.setPaint( java.awt.Color.gray );
	    java.awt.Polygon triangle = new java.awt.Polygon();
	    triangle.addPoint( l, c );
	    triangle.addPoint( l, c + w );
	    triangle.addPoint( r, c );
	    triangle.addPoint( l, c - w );
	    g.fill( triangle );
	}
    }

    /**
     * Set whether or not the panel is illuminated. If it is illuminated, it will show a
     * small grey triamngle.
     * @param illuminate Takes the values <em>true</em> or <em>false</em> according as
     * panel is to be illuminated.
     */
    
    /**
     * Denotes whether or not this panel is illuminated (triangle shows).
     */
    void setIlluminated( boolean illuminated ){
	this.illuminated = illuminated;
    }

    private boolean illuminated;
    /**
     * Denotes whether this panel is a left panel or a right one.
     */
    private final boolean left;
    private static final long serialVersionUID = 1L;   
}
	
