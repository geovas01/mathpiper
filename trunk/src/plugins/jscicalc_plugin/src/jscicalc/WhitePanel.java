/** @file
 * Copyright (C) 2004&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
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
 * This class creates a JPanel whose background is always white. It&rsquo;s used by
 * EntryLabel to make sure the background colour of the entry part of the
 * DisplayPanel is always white.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.6 $
 */
public class WhitePanel extends javax.swing.JPanel {
    /**
     * The constructor sets the border insets to zero.
     */
    public WhitePanel(){
	setBorder( new javax.swing.border.EmptyBorder( 0, 0, 0, 0 ) );
    }

    /**
     * I&rsquo;m not quite sure I&rsquo;ve got this right.
     * I&rsquo;m trying to make sure the
     * background is always white: probably even that should be replaced by a
     * specified colour stored in CalculatorApplet.
     * @param graphics The graphics context required.
     */
     public void paintComponent( java.awt.Graphics graphics ){
	 super.paintComponent( graphics );
	 graphics.setColor( java.awt.Color.WHITE );
	 graphics.fillRect( 0, 0, getWidth(), getHeight() );
     }
    private static final long serialVersionUID = 1L;   
}
