/** @file
 * Copyright (C) 2005 John D Lamb (J.D.Lamb@btinternet.com)
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

package jscicalc;
import jscicalc.complex.Complex;

/**
 * This class is designed to convert a string in base Base.BINARY, Base.OCTAL,
 * Base.HEXADECIMAL into a jscicalc.complex.Complex.
 * It also handles Base.DECIMAL by using a function in DOUBLE.
 *
 * It was origainlly designed to convert a string in base Base.BINARY, Base.OCTAL,
 * Base.HEXADECIMAL into a double. It also handles Base.DECIMAL by using a function
 * in java.Math.BigDecimal. In fact it only needs to do this conversion because any
 * number that we convert is a real or imaginary part, absolute value or argument.
 *
 * When you enter an expression as a sequence of Numeral objects, Parser converts
 * them to a string and then must convert the string to a double. This class
 * does the conversion for Parser. 
 *
 * @see Parser
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.5 $
 */
class ParseBase {
    
    /**
     * Private inner class used by ParseBase. It is similar to
     * DisplayFormat.Format.
     *
     * @author J. D. Lamb 
     * @version 1.0
     */
    private static class Number {
	/**
	 * The constructor must know what Base we are working with.
	 * @param b The Base we need to parse
	 */
	public Number( Base b ){
	    base = b;
	    significand = exponent = 0;
	}
	
	/**
	 * The significand of the double.
	 */
	public long significand;
	/**
	 * The significand of the double.
	 */
	public long exponent;
	/**
	 * The exponent of the double.
	 */
	public boolean negative;
	/**
	 * Is the exponent negative
	 */
	public boolean exponentNegative;
	/**
	 * The Base.
	 */
	public Base base;

	/**
	 * Reads the string and extracts the significand.
	 * @param s The string
	 * @param b The Base we are working in
	 */
	public static Number parseSignificand( String s, Base b ){
	    Number number = new Number( b );
	    int end = 15;
	    int shift = 4;
	    if( b == Base.BINARY ){
		end = 60;
		shift = 1;
	    }
	    else if( b == Base.OCTAL ){
		end = 20;
		shift = 3;
	    }
	    number.negative = s.charAt( 0 ) == '-';
	    int offset = number.negative ? 1 : 0;
	    boolean leading = true;
	    for( int i = 0; i < end; ++i ){
		long digit = 0;
		if( i + offset < s.length() )
		    digit = getDigit( s.charAt( i + offset ) );
		if( digit < 0 ){
		    ++offset; // deal with baseimal point, etc.
		    --i;
		    continue;
		} else if( leading )
		    if( digit == 0 && offset < end ){
			++offset; // deal with leading zeros
			--i;
			continue;
		    } else
			leading = false;
		number.significand <<= shift;
		number.significand |= digit;
	    }
	    return number;
	}
    
	/**
	 * Reads the string and extracts the exponent.
	 * @param s The string
	 */
	private void parseExponent( String s ){
	    //System.out.println( s );
	    int shift = 0;
	    switch( base ){
	    case BINARY:
		shift = 1;
		break;
	    case OCTAL:
		shift = 3;
		break;
	    case HEXADECIMAL:
	    default:
		shift = 4;
	    }
	    exponentNegative = false;
	    for( Character c : s.toCharArray() ){
		if( c == '-' )
		    exponentNegative = !exponentNegative;
		long digit = getDigit( c );
		if( digit < 0 ) continue;
		exponent <<= shift;
		if( (exponent & 0x1000L) != 0 ){
		    break;
		}
		//System.out.println( "++++++++++++++++++++++++++" );
		//System.out.println( exponent );
		//System.out.println( digit );
		//System.out.println( digit * shift );
		//System.out.println( exponent + digit * shift );
		//System.out.println( "--------------------------" );
		exponent += digit * shift;
	    }
	    if( exponentNegative )
		exponent =- exponent;
	    //System.out.println( exponent );
	}
	
	/**
	 * Finds the base point, useful for making sure the exponent is correct.
	 * @param s The string
	 */
	private void parsePoint( String s ){
	    //System.out.println( s );
	    boolean fraction = false;
	    int leadingZeros = 0;
	    int fractionalZeros = 0;
	    for( Character c : s.toCharArray() ){
		if( c == '.' )
		    fraction = true;
		else if( c != '0' ) break;
		else if( fraction )
		    ++fractionalZeros;
		else 
		    ++leadingZeros;
	    }
	    int leadingDigits = 0;
	    for( Character c : s.toCharArray() ){
		if( c == '.' ) break;
		else ++leadingDigits;
	    }
	    int count = leadingDigits - leadingZeros - fractionalZeros - 1;
	    //System.out.println( exponent );
	    //System.out.println( count );
	    // there are count nonzero digits in front of the base point
	    if( base == Base.HEXADECIMAL )
		count *= 4;
	    else if( base == Base.OCTAL )
		count *= 3;
	    exponent += count;
	    //System.out.println( exponent );
	    //System.out.println( "**************" );
	}
	
	/**
	 * Reads two strings, one contains significand, the other exponent. These
	 * will have been obtained using a regular expression.
	 * @param s The string containing the significand
	 * @param e The string containing the exponent
	 * @param b The Base we are working in
	 */
	private static Number parseStrings( String s, java.lang.String e,
				     Base b ){
	    Number number = Number.parseSignificand( s, b );
	    if( e.length() > 0 )
		number.parseExponent( e );
	    number.parsePoint( s );
	    //System.out.println( number.exponent );
	    //System.out.println( "$$$$$$$$$$$$$$" );
	    return number;
	}

	/**
	 * Parses an entire string. Uses a regular expression to split it into
	 * significand and exponent and then parses these separately.
	 * @param s The string containing the significand
	 * @param b The Base we are working in
	 */
	public static Number parseString( String s, Base b ){
	    java.util.regex.Matcher m = pattern.matcher( s );
	    m.matches();
	    return parseStrings( m.group( 1 ), m.group( 2 ), b );
	}

	/**
	 * A regular expression pattern used to split a string into significand
	 * and exponent.
	 */
	private static java.util.regex.Pattern pattern
	    = java.util.regex.Pattern .compile( "([-|+]?[0-9A-F]+" +
						"|[-|+]?[0-9A-F]+[.][0-9A-F]*" + 
						"|[-|+]?[0-9A-F]*[.][0-9A-F]+)" +
						"(e[-|+]?[0-9A-F]+|)" );
	
    }
    
    /**
     * String is composed from Numeral PObject objects. Each contains a char and
     * eventually we need to extract the char from a string and convert it into
     * the corresponding integer
     * @param c a char representing a Numeral
     * @return the corresponding integer
     */
    private static long getDigit( char c ){
	switch( c ){
	case '0':
	    return 0;
	case '1':
	    return 1;
	case '2':
	    return 2;
	case '3':
	    return 3;
	case '4':
	    return 4;
	case '5':
	    return 5;
	case '6':
	    return 6;
	case '7':
	    return 7;
	case '8':
	    return 8;
	case '9':
	    return 9;
	case 'A':
	    return 10;
	case 'B':
	    return 11;
	case 'C':
	    return 12;
	case 'D':
	    return 13;
	case 'E':
	    return 14;
	case 'F':
	    return 15;
	default:
	    return -1;
	}
    }

    /**
     * This is the function that Parser actually uses. Converts a string to
     * a double in Base b.
     * @param s The string (a number composed from Numeral objects)
     * @param b The Base
     * @return The number as a double
     * @see Parser
     */
    public static Complex parseString( String s, Base b ){
	return new Complex( dparseString( s, b ) );
    }

    /**
     * This is the function that Parser uses for doubles. Converts a string to
     * a double in Base b.
     * @param s The string (a number composed from Numeral objects)
     * @param b The Base
     * @return The number as a double
     * @see Parser
     */
    public static double dparseString( String s, Base b ){
	//System.out.println( s );
	if( b == Base.DECIMAL ){
	    java.util.regex.Matcher m = Number.pattern.matcher( s );
	    m.matches(); // throws an exception if s is not a suitable number
	    return (new java.math.BigDecimal( s )).doubleValue();
	    //return Double.valueOf( s ); /* older version */
	}
	Number number = Number.parseString( s, b );
	if( number.significand == 0 )
	    return 0;
	// significand has up to 60 binary digits - reduce to 53
	switch( b ){
	case HEXADECIMAL:
	    number.significand >>>= 4;
	    break;
	case OCTAL:
	    number.significand >>>= 5;
	    break;
	default:
	    number.significand >>>= 7;
	}
	for(; (number.significand & 0xFFE0000000000000L) != 0; ++number.exponent )
	    number.significand >>>= 1;
	// exponent has possible overflow/underflow
	if( number.exponent > E_MAX )
	    if( number.negative )
		return Double.NEGATIVE_INFINITY;
	    else
		return Double.POSITIVE_INFINITY;
	else if( number.exponent < E_MIN ){
	    // underflow
	    if( number.exponent < E_MIN - 52 )
		return 0;
	    else {
		for(; number.exponent < E_MIN; ++number.exponent )
		    number.significand >>>= 1;
		number.exponent = -BIAS;
	    }
	}
	long result = number.exponent + BIAS;
	//System.out.println( number.exponent );
	result <<= 52; // shift exponent into position
	//System.out.println( Long.toHexString( result ) );
	if( number.negative )
	    result |= 0x8000000000000000L;
	else
	    result &= 0x7FFFFFFFFFFFFFFFL;
	result |= (number.significand & 0x000FFFFFFFFFFFFFL);
	//System.out.println( Long.toHexString( result ) );
	//System.out.println( Long.toBinaryString( result ) );
	return Double.longBitsToDouble( result );
    }

    public static void main( String[] args ){
 	System.out.println( parseString( "777", Base.OCTAL ).real() );
 	System.out.println( parseString( "77.7", Base.OCTAL ).real() );
 	System.out.println( parseString( "177", Base.OCTAL ).real() );
 	System.out.println( parseString( "077", Base.OCTAL ).real() );
 	System.out.println( parseString( "0.01", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "0.01", Base.OCTAL ).real() );
 	System.out.println( parseString( "0.01", Base.BINARY ).real() );
 	System.out.println( parseString( "-0.01", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "-0.01", Base.OCTAL ).real() );
 	System.out.println( parseString( "-0.01", Base.BINARY ).real() );
 	System.out.println( parseString( "1e3", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "1e3", Base.OCTAL ).real() );
 	System.out.println( parseString( "1e11", Base.BINARY ).real() );
 	System.out.println( parseString( "1e-3", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "1e-3", Base.OCTAL ).real() );
 	System.out.println( parseString( "-1e-11", Base.BINARY ).real() );
 	System.out.println( parseString( "1e-13", Base.OCTAL ).real() );
 	System.out.println( parseString( "1e+1111111111", Base.BINARY ).real() );
 	System.out.println( parseString( "1e+10000000000", Base.BINARY ).real() );
 	System.out.println( parseString( "1e-10000000000", Base.BINARY ).real() );
 	System.out.println( parseString( "1e-10000000000", Base.BINARY ).real() );
 	System.out.println( parseString( "1e-100000000000", Base.BINARY ).real() );
 	System.out.println( parseString( "1e-1000000000000", Base.BINARY ).real() );
 	System.out.println( parseString( "8e+FF", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "1e+100", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "8e2", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "8e-FF", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "1e-108", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "1e-109", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "1e-109", Base.HEXADECIMAL ).real() );
 	System.out.println( parseString( "1e-10A", Base.HEXADECIMAL ).real() );
	System.out.println( parseString( "1e-10C", Base.HEXADECIMAL ).real() );
	System.out.println( parseString( "1e+8", Base.HEXADECIMAL ).real() );
    }
    
    /**
     * Standard constant for IEEE 754 doubles
     */
    public static final long BIAS = 1023;
    /**
     * Standard constant for IEEE 754 doubles
     */
    public static final long E_MAX = 1023;
    /**
     * Standard constant for IEEE 754 doubles
     */
    public static final long E_MIN = -1022;
    
}
