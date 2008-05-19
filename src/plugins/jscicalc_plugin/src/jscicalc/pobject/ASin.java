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
import jscicalc.AngleType;
import jscicalc.OObject;
import jscicalc.complex.Complex;

/**
 * Inverse sine operation.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.7 $
 */
public class ASin extends Trig {
    /**
     * Constructor. Sets a tooltip and shortcut.
     */
    public ASin( AngleType angleType ){
	super( angleType );
	ftooltip = "inverse sine function";
	fshortcut = 's';
    }

    /**
     * Inverse sine of x.
     * @param x The value (right of symbol)
     * @return The result of the operation
     */
    public double function( double x ){
	return Math.asin( x ) * iscale;
    }

    /**
     * Inverse sine of x.
     * @param x The value (right of symbol)
     * @return The result of the operation
     */
    public OObject function( OObject x ){
	if( x instanceof Complex ){
	    Complex c = (Complex)x;
	    if( scale != 1 && StrictMath.abs( c.imaginary() ) > 1e-6 )
		throw new RuntimeException( "Error" );
	    if( scale != 1 && StrictMath.abs( c.real() ) > 1 )
		throw new RuntimeException( "Error" );
	    return c.asin().scale( iscale );
	} else {
	    return x.asin( angleType );
	}
    }

    public String[] name_array(){
	return fname;
    }
    
    public static void main( String args[] ){
	PObject o = new ASin( AngleType.DEGREES );
	StringBuilder s = new StringBuilder( "<html>" );
	s.append( o.name() );
	s.append( "</html>" );
	javax.swing.JOptionPane.showMessageDialog( null, s.toString() );
    }
    
    private final static String[] fname = { "s", "i", "n",
						      "<sup>&#8722;</sup>",
						      "<sup>1</sup>", " " };
}
