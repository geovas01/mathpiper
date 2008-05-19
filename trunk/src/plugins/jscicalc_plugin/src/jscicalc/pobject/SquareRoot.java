/** @file
 * Copyright (C) 2004&ndash;5 John D Lamb (J.D.Lamb@btinternet.com)
 * Copyright (C) 2007, 2008 John D Lamb (J.D.Lamb@btinternet.com)
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
import jscicalc.OObject;

/**
 * Square root operation.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.5 $
 */
public class SquareRoot extends RFunction {
    /**
     * Constructor. Sets a tooltip and shortcut.
     */
    public SquareRoot(){
	ftooltip = "square root of <i>x</i>";
	fshortcut = 'r';
    }

    /**
     * Calculates square root of x.
     * @param x The value (right of symbol)
     * @return The result of the operation
     */
    public double function( double x ){
	return Math.sqrt( x );
    }

    /**
     * Calculates square root of x.
     * @param x The value (right of symbol)
     * @return The result of the operation
     */
    public OObject function( OObject x ){
	return x.sqrt();
    }

    public String[] name_array(){
	return fname;
    }
    
    public static void main( String args[] ){
	PObject o = new SquareRoot();
	StringBuilder s = new StringBuilder( "<html>" );
	s.append( o.name() );
	s.append( "</html>" );
	javax.swing.JOptionPane.showMessageDialog( null, s.toString() );
    }
    
    private final static String[] fname = { "&#8730;" };
}
