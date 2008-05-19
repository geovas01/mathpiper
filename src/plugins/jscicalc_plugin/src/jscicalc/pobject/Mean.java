/** @file
 * Copyright (C) 2004&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
 * Copyright (C) 2007 John D Lamb (J.D.Lamb@btinternet.com)
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

package jscicalc.pobject;
import jscicalc.complex.Complex;

/**
 * Container to hold most recently evaluated mean value.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.6 $
 */
public class Mean extends Container {
    /**
     * Constructor. Sets a tooltip and shortcut.
     */
    public Mean(){
	ftooltip = "the mean of the numbers stored in statistics memory";
	fshortcut = 'm';
    }

    /**
     * Set the Container error state.
     * @param error Error state
     */
    public void setError( boolean error ){
	this.error = error;
    }

    /**
     * Set the value of this Container.
     * @param d The value to store.
     */
    public void setValue( double d ){
	this.d = d;
    }

    /**
     * Set the value of this Container.
     * @param c The value to store.
     */
    public void setValue( Complex c ){
	this.c = c;
    }

    public String[] name_array(){
	return fname;
    }

    public static void main( String args[] ){
	Mean o = new Mean();
	StringBuilder s = new StringBuilder( "<html>" );
	s.append( o.name() );
	s.append( "</html>" );
	javax.swing.JOptionPane.showMessageDialog( null, s.toString() );
    }
    
    //private final static String[] fname = { "<i>x", "&#773;</i>" };
    private final static String[] fname = { "m", "e", "a", "n" };
}
