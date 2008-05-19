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
import jscicalc.complex.Complex;

/**
 * DFunction that allows scientific notation entry. Probably now obsolete. It
 * did allow expressions like 1e1.3, but was problematic because of rounding errors.
 * @see Parser
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.5 $
 */
public class E extends DFunction {
    /**
     * Constructor. Sets a tooltip and shortcut.
     */
    public E(){
	ftooltip = "use to enter scientific notation<br>" +
	    "in the form <i>x</i>e<i>y</i> = " +
	    "<i>x</i> &#215; 10<sup><i>y</i></sup>";
	fshortcut = 'e';
	base( 10 );
    }

    /**
     * Calculates a double xey. Obsolete.
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    public double function( double x, double y ){
	return x * Math.exp( y * Math.log( base ) );
    }

    /**
     * Calculates a OObject xey. Obsolete.
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    public OObject function( OObject x, OObject y ){
	return x.multiply( (y.multiply( (new Complex( base, 0 )).log()) ).exp() );
    }

    public String[] name_array(){
	return fname;
    }
    
    public static void main( String args[] ){
	E o = new E();
	StringBuilder s = new StringBuilder( "<html>" );
	s.append( o.name() );
	s.append( "</html>" );
	javax.swing.JOptionPane.showMessageDialog( null, s.toString() );
    }

    /**
     * Get the base used (always 10).
     * @return base
     */
    public int base(){
	return base;
    }

    /**
     * Set the base used (always set to 10 but obsolete).
     * @param base The new base (should be 10)
     */
    public void base( int base ){
	this.base = base;
    }

    /// The base used (10).
    private int base;
    
    private final static String[] fname
	= { "e" };
}
