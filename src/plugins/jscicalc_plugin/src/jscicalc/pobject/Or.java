/** @file
 * Copyright (C) 2005 John D Lamb (J.D.Lamb@btinternet.com)
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
 * Bitwise OR operation. For this operation the sign is not particularly
 * meaningful. numbers are stored as IEEE 754 doubles which use a bit for sign
 * and this operation carries out a logical OR on that bit.
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.5 $
 */
public class Or extends BoolFunction {
    /**
     * Constructor. Sets a tooltip and shortcut.
     */
    public Or(){
	ftooltip = "Logical bitwise inclusive or";
	fshortcut = '|';
    }

    /**
     * Calculates x OR y.
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    public double function( double x, double y ){
	if( Double.isNaN( x ) || Double.isNaN( y )
	    || Double.isInfinite( x ) 
	    || Double.isInfinite( y ) )
	    throw new RuntimeException( "Boolean Error" );
	if( Math.abs( y ) > Math.abs( x ) ){
	    double tmp = x;
	    x = y;
	    y = tmp;
	}
	long x_bits = Double.doubleToLongBits( x );
	boolean x_sign = (x_bits >> 63) == 0;
	int x_exponent = (int)((x_bits >> 52) & 0x7FFL);
	long x_significand = x_exponent == 0 ? (x_bits & 0xFFFFFFFFFFFFFL) << 1 
	    : (x_bits & 0xFFFFFFFFFFFFFL) | 0x10000000000000L;
	long y_bits = Double.doubleToLongBits( y );
	boolean y_sign = (y_bits >> 63) == 0;
	int y_exponent = (int)((y_bits>>52) & 0x7FFL);
	long y_significand = y_exponent == 0 ? (y_bits & 0xFFFFFFFFFFFFFL) << 1 
	    : (y_bits & 0xFFFFFFFFFFFFFL) | 0x10000000000000L;
	y_significand >>= (x_exponent - y_exponent);

	// actually carry out the operation
	x_significand |= y_significand;
	
	// now reconstruct result
	if( x_exponent == 0 )
	    x_significand >>= 1;
	else {
	    if( x_significand == 0 ) return 0;
	    while( (x_significand & 0x10000000000000L) == 0 ){
		x_significand <<= 1;
		--x_exponent;
		if( x_exponent == 0 ){
		    x_significand >>= 1;
		    break;
		}
	    }
	    x_significand &= 0xFFFFFFFFFFFFFL;
	}
	
	x_bits = ((long)x_exponent) << 52;
	x_bits |= x_significand;

	double result = Double.longBitsToDouble( x_bits );

	// deal with signs
	if( !x_sign | !y_sign )
	    result =- result;
	return result;
    }

    /**
     * Calculates x OR y.
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    public OObject function( OObject x, OObject y ){
	return x.or( y );
    }

    public String[] name_array(){
	return fname;
    }
    
    public static void main( String args[] ){
	PObject o = new Or();
	StringBuilder s = new StringBuilder( "<html>" );
	s.append( o.name() );
	s.append( "</html>" );
	javax.swing.JOptionPane.showMessageDialog( null, s.toString() );
    }
    
    private final static String[] fname = { " ", "o", "r", " " };
}
