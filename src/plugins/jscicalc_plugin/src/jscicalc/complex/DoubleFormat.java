/**
 * @file
 *
 * Copyright (C) 2004, 2007, 2008 John D Lamb (J.D.Lamb@btinternet.com)
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

package jscicalc.complex;
import jscicalc.Base;
import jscicalc.Notation;

/**
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.13 $
 */
public class DoubleFormat {
    /**
     * Constructor
     * @param number The number to format
     * @param base The base
     */
    public DoubleFormat( double number, Base base ){
	this.number = number;
	this.base = base;
	this.notation = new Notation();
	setPrecision( maxPrecision() );
    }

    /**
     * Set the precision. The precision is the maximum number of significant
     * digits that can be displayed. Setting the precision is equivalent to
     * resetting the number and rounding.
     * @param precision The maximum number of significant digits
     */
    public void setPrecision( int precision ){
	if( precision == this.precision ) return;
	this.precision = precision; // remember value
	reset(); // create digits
	if( digits.size() <= precision ) return; // no further work
	/* truncate */
	int lastDigit = digits.get( precision );
	digits.setSize( precision );
	/* check if rounding necessary */
	if( lastDigit < midDigit( base ) ){
	    /* remove trailing zeros */
	    while( digits.size() > 0 && digits.lastElement() == 0 )
		digits.setSize( digits.size() - 1 );
	    return;
	}
	/* round */
	if( roundUp() ) return; // no further rounding needed
	++exponent; // otherwise exponent increases
    }

    /**
     * Set the base.
     * @param base The base
     */
    public void setBase( final Base base ){
	this.base = base;
    }

    /**
     * Set the notation. Use this every time you want a change of notation.
     * @param notation The notation
     */
    public void setNotation( Notation notation ){
	this.notation = notation;
    }

    /**
     * The recursive rounding function. Do not call this directly. It gets called from
     * setPrecision.
     * @return <em>false</em> or <em>true</em> according as the exponent should increase
     * or not
     */
    private boolean roundUp(){
	int position = digits.size() - 1;
	if( position == -1 ){
	    /* deal with case where we've rounded all the way to
	     * the beginning */
	    digits.add( 1 );
	    return false;
	}
	int digit = digits.get( position );
	if( ++digit == baseInt( base ) ){
	    digits.remove( position );
	    return roundUp();
	} // no further rounding
	digits.set( position, digit );
	return true;
    }

    /**
     * Inner class used to represent HTML string representations of doubles. These are
     * used to build up representations of complex numbers. The HTML allows us to format
     * the minus sign and exponent nicely. It also allows us to use siymbols for
     * infinity. The HTML here idoes not include the usual start and end tags. These
     * get added later when the number is formatted in Complex.toHTMLString().
     *
     * The length is used to try to stop overflow in the output. Roughly digits and
     * symbols in the output have about the same width. So this gives a good enough
     * measure of width of output to create a reasonable representation.
     */
    public class HTMLStringRepresentation {
	/**
	 * Constructor. This version is only used inside this class.
	 * @param string The string representation
	 * @param length Its length
	 */
  	private HTMLStringRepresentation( jscicalc.StringArray string, int length ){
  	    this.string = string;
  	    this.length = length;
  	}
	/**
	 * The usual constructor. We call it indirectly through representation().
	 */
 	public HTMLStringRepresentation(){
	    if( Double.isNaN( number ) ){
		String[] nan = { "N", "a", "N" };
		string = new jscicalc.StringArray();
		string.add( nan );
		length = 3;
	    } else if( Double.isInfinite( number ) ){
		string = new jscicalc.StringArray();
		if( number < 0 ){
		    string.add( minusInfinity );
		    length = 2;
		} else {
		    string.add( infinity );
		    length = 1;
		}
	    } else {
		HTMLStringRepresentation h;
		if( notation.standard() ){
		    h = standard();
		} else {
		    h = scientific();
		}
		string = h.string;
		length = h.length;
	    }
	}

	/**
	 * Test whether this matches one
	 * @return <em>true</em> or <em>false</em> according as this matches 1 or not
	 */
	public boolean isOne() {
	    if( string.size() != 1 ) return false;
	    if( string.firstElement().size() != 1 ) return false;
	    if( string.firstElement().elementAt( 0 ) != "1" ) return false;
	    return true;
	}
	
	/**
	 * Test whether this matches minus 1
	 * @return <em>true</em> or <em>false</em> according as this matches minus 1 or
	 * not
	 */
	public boolean isMinusOne() {
	    if( string.size() != 1 ) return false;
	    if( string.firstElement().size() != 2 ) return false;
	    if( string.firstElement().elementAt( 0 ) != minus.elementAt( 0 ) )
		return false;
	    if( string.firstElement().elementAt( 1 ) != "1" ) return false;
	    return true;
	}

	/**
	 * A string to hold the HTML representation of the number.
	 */ 
	public final jscicalc.StringArray string;
        /**
	 * A string to hold the length of the representation. This is not just the length
	 * of the string, but rather a rough character count, ignoring, for example, thin
	 * spaces and XML, and counting a minus sign as a single character.
	 */
	public final int length;
    }

    /**
     * Produce a string representing the number in current Notation and
     * precision.
     * @return representation of number without an exponent
     */
    public HTMLStringRepresentation representation(){
	return new HTMLStringRepresentation();
    }

    /**
     * Produce a string representing the number without using
     * scientific notation. This can produce very long strings.
     * @return representation of number without an exponent
     */
    private HTMLStringRepresentation standard(){
	java.util.Vector<String> stringBuffer = new java.util.Vector<String>();
	int length = 0;
	if( number < 0 ){
	    stringBuffer.addAll( minus );
	    ++length;
	}
	if( exponent < 0 ){
	    stringBuffer.add( "0" );
	    ++length;
	    stringBuffer.addAll( basePoint );
	    ++length;
	    for( int i = 0; i < -exponent - 1; ++i ){
		stringBuffer.add( "0" );
		++length;
	    }
	    for( int i = 0; i < digits.size(); ++i ){
		stringBuffer.add( Character
				  .toString( getDigit( digits.elementAt( i ) ) ) );
		++length;
	    }
	} else {
	    for( int i = 0; i < StrictMath.max( exponent + 1, digits.size() );
		 ++i ){
		if( i >= StrictMath.max( exponent + 1, digits.size() ) ) break;
		if( i == exponent + 1 ){
		    stringBuffer.addAll ( basePoint );
		    ++length;
		}
		if( i < digits.size() ){
		    stringBuffer.add( Character
				      .toString( getDigit( digits.elementAt( i ) ) ) );
		} else {
		    stringBuffer.add( "0" );
		}
		++length; // just added a digit
	    }
	}
	jscicalc.StringArray t = new jscicalc.StringArray();
	t.add( stringBuffer );
	return new HTMLStringRepresentation( t, length );
    }
    
    /**
     * Produce a string representing the number using
     * scientific notation. All significant digits are used; so use round() to
     * shorten if necessary.
     * @return representation of number with an exponent
     */
    public HTMLStringRepresentation scientific(){
	java.util.Vector<String> stringBuffer = new java.util.Vector<String>();
	int length = 0;
	// fill in mantissa
	if( number < 0 ){
	    stringBuffer.addAll( minus );
	    ++length;
	}
	boolean first = digits.size() > 1; // allow for base point
	for( Integer i : digits ){
	    stringBuffer.add( Character.toString( getDigit( i ) ) );
	    ++length;
	    if( first ){
		stringBuffer.addAll( basePoint ); // base point
		++length;
		first = false;
	    }
	}
	// fill in exponent
	stringBuffer.add( getEString() );
	length += getEStringLength();
	//stringBuffer.append( startExponent );
	int e = (int)exponent;
	String q = new String( startExponent );
	if( exponent < 0 ){
	    //stringBuffer.append( minus );
	    q = q.concat( minus.firstElement() ); // add minus sign
	    stringBuffer.add( q );
	    ++length;
	    e = -e ;
	}
	String s = null; // initialise
	switch( base ){
	    case OCTAL:
		s = Integer.toOctalString( e );
		break;
	    case HEXADECIMAL:
		s = Integer.toHexString( e ).toUpperCase();
		break;
	    default: // decimal or binary
		s = Integer.toString( e );
	}
	for( int i = 0; i < s.length(); ++i ){
	    String t = Character.toString( s.charAt( i ) );
	    if( exponent >= 0 )
		t = q.concat( t );
	    if( i == s.length() - 1 )
		t = t.concat( endExponent );
	    stringBuffer.add( s );
	}
	length += s.length();
	jscicalc.StringArray t = new jscicalc.StringArray();
	t.add( stringBuffer );
	return new HTMLStringRepresentation( t, length );
    }

    /**
     * Use this function whenever you change base or notation. It also gets called when
     * you change the precision of the output. It reconstructs the original number so
     * that it is ready for formatting.
     */
    private void reset(){
	digits = new java.util.Vector<Integer>();
	// deal with special case
	if( number == 0 ){
	    digits.add( 0 );
	    exponent = 0;
	    return;
	}
	if( Double.isInfinite( number ) || Double.isNaN( number ) ) return;
	switch( base ){
	    case BINARY:
		setupBin();
		break;
	    case OCTAL:
		setupOct();
		break;
	    case HEXADECIMAL:
		setupHex();
		break;
	    default:
		setupDec();
	}
	if( StrictMath.abs( number ) < 1 )
	    exponent =- exponent;
    }

    /**
     * This method handles setting up decimals
     */
    private void setupDec(){
	java.math.BigDecimal b
	    = new java.math.BigDecimal( number < 0 ? -number : number );
	exponent = b.precision() - b.scale() - 1;
	int exponentSign = (int)StrictMath.signum( exponent );
	if( exponentSign < 0 )
	    exponent = -1 * exponent;
	String s = b.unscaledValue().toString();
 	for( Character c : s.toCharArray() ){
	    digits.add( getInt( c ) );
	}
	while( digits.size() > 0 && digits.lastElement() == 0 )
	    digits.setSize( digits.size() - 1 );
    }
    /**
     * This method handles setting up hexadecimals
     */
    private void setupHex(){
	long bits = Double.doubleToRawLongBits( number );
	exponent = (bits & 0x7ff0000000000000L) >>> 52;
	long significand = bits & 0x000fffffffffffffL;
	exponent -= BIAS;
	boolean denormalised = exponent == E_MIN - 1;
	int exponentSign = (int)StrictMath.signum( exponent );
	if( exponentSign == -1 ){
	    exponent = -exponent;
	}
	if( !denormalised ){ // easy case
	    significand |= 0x0010000000000000L;
	} else { // denormalised case
	    significand <<= 1;
	    if( significand != 0 )
		while( (significand & 0x0008000000000000L) == 0 ){
		    ++exponent;
		    significand <<= 1;
		}
	}
	while( exponent % 4 != 0 ){
	    significand <<= 1;
	    if( exponentSign == -1 )
		++exponent;
	    else
		--exponent;
	}
	exponent >>>= 2;
	for( int i = 0; i < 14; ++i ){
	    int digit = (int)(significand & 0xfL);
	    digits.add( 0, digit );
	    significand >>>= 4;
	}
    }
    /**
     * This method handles setting up octals
     */
    private void setupOct(){
	long bits = Double.doubleToRawLongBits( number );
	exponent = (bits & 0x7ff0000000000000L) >>> 52;
	long significand = bits & 0x000fffffffffffffL;
	exponent -= BIAS;
	boolean denormalised = exponent == E_MIN - 1;
	int exponentSign = (int)StrictMath.signum( exponent );
	if( exponentSign == -1 )
	    exponent = -exponent;
	if( !denormalised ){ // easy case
	    significand |= 0x0010000000000000L;
	} else { // denormalised case
	    significand <<= 1;
	    if( significand != 0 )
		while( (significand & 0x0008000000000000L) == 0 ){
		    ++exponent;
		    significand <<= 1;
		}
	}
	while( exponent % 3 != 0 ){
	    significand <<= 1;
	    if( exponentSign == -1 )
		++exponent;
	    else
		--exponent;
	}
	exponent /= 3;
	significand <<= 2;
	for( int i = 0; i < 19; ++i ){
	    int digit = (int)(significand & 0x7L);
	    digits.add( 0, digit );
	    significand >>>= 3;
	}
    }
    /**
     * This method handles setting up binary numbers
     */
    private void setupBin(){
	long bits = Double.doubleToRawLongBits( number );
	exponent = (bits & 0x7ff0000000000000L) >>> 52;
	long significand = bits & 0x000fffffffffffffL;
	exponent -= BIAS;
	boolean denormalised = exponent == E_MIN - 1;
	int exponentSign = (int)StrictMath.signum( exponent );
	if( exponentSign == -1 )
	    exponent = -exponent;
	if( !denormalised ){ // easy case
	    significand |= 0x0010000000000000L;
	} else { // denormalised case
	    significand <<= 1;
	    if( significand != 0 )
		while( (significand & 0x0008000000000000L) == 0 ){
		    ++exponent;
		    significand <<= 1;
		}
	}
	for( int i = 0; i < 53; ++i ){
	    int digit = (int)(significand & 0x1L);
	    digits.add( 0, digit );
	    significand >>>= 1;
	}
    }

    /**
     * Get method for finding the number stored in this object.
     */
    public double getNumber(){
	return number;
    }
    
    /**
     * Get method for finding the absolute value of the number stored in this object.
     */
    public double getAbsNumber(){
	return StrictMath.abs( number );
    }
    
    /**
     * We need to know how to represent integers up to 15 as char. This function
     * does it.
     * @param d An integer (should be in range 0-15)
     * @return The corresponding char
     */
    private static char getDigit( int d ){
	switch( d ){
	case 0:
	    return '0';
	case 1:
	    return '1';
	case 2:
	    return '2';
	case 3:
	    return '3';
	case 4:
	    return '4';
	case 5:
	    return '5';
	case 6:
	    return '6';
	case 7:
	    return '7';
	case 8:
	    return '8';
	case 9:
	    return '9';
	case 10:
	    return 'A';
	case 11:
	    return 'B';
	case 12:
	    return 'C';
	case 13:
	    return 'D';
	case 14:
	    return 'E';
	case 15:
	    return 'F';
	default:
	    return '?';
	}
    }
    /**
     * This is effectively the reverse of getDigit(). We use it when extracting
     * digits from BigDecimal.toString().
     * @param c A char corresponding to a digit
     * @return The corresponding int
     */
    private static int getInt( char c ){
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
	    default:
		return -1;
	}
    }
    
    /**
     * This is used during rounding. Numbers at least as big as midDigit are rounded up.
     * Others are rounded down. The returned values are 1, 4, 5 and 8 for Base.BINARY,
     * Base.OCTAL, Base.DECIMAL and Base.HEXADECIMAL.
     * @param base A Base to work with
     * @return The middle digit of the base
     */
    public static int midDigit( Base base ){
	switch( base ){
	    case BINARY:
		return 1;
	    case OCTAL:
		return 4;
	    case HEXADECIMAL:
		return 8;
	    default:
		return 5;
	}
    }

    /**
     * This is used during rounding. If baseInt is rounded up it must be rounded to
     * 10 in the Base. This implies a carry operation.
     * Others are rounded down. The returned values are 1, 7, 9 and F for Base.BINARY,
     * Base.OCTAL, Base.DECIMAL and Base.HEXADECIMAL.
     * @param base A Base to work with
     * @return The greatest digit in the base
     */
    public static int baseInt( Base base ){
	switch( base ){
	    case BINARY:
		return 2;
	    case OCTAL:
		return 8;
	    case HEXADECIMAL:
		return 16;
	    default:
		return 10;
	}
    }

    /**
     * Numbers are stored internally in IEEE-754 format. The number of digits of
     * precision available depdends on the base and this function gives that number.
     * The returned values are 52, 17, 14 and 13 for Base.BINARY,
     * Base.OCTAL, Base.DECIMAL and Base.HEXADECIMAL.
     * @return The greatest available precision
     */
    private int maxPrecision(){
	switch( base ){
	    case BINARY:
		return 52;
	    case OCTAL:
		return 17;
	    case HEXADECIMAL:
		return 13;
	    default:
		return 14;
	}
    }

    /**
     * Exponents are represented as superscripts with a prefix, which may depend on the
     * Base. In fact, it is always a thin space, but in principle, you can change it by
     * changing this function.
     * @return A string to represent the exponent.
     * @see getEStringLength()
     */
    public String getEString(){
	switch( base ){
	    case BINARY:
		return ""; //"&#8201;"; // thin space
	    case OCTAL:
		return "";
	    case HEXADECIMAL:
		return "";
	    default:
		return "";
	}
    }

    /**
     * Exponents are represented as superscripts with a prefix, which may depend on the
     * Base. This function returns the nominal length of the prefix.
     * @return A string to represent the exponent.
     * @see getEString()
     */
    public int getEStringLength(){
	switch( base ){
	    case BINARY:
		return 0;
	    case OCTAL:
		return 0;
	    case HEXADECIMAL:
		return 0;
	    default:
		return 0;
	}
    }

    /**
     * Standard test function.
     * @param args Standard arguments.
     */
    public static void main( String[] args ){
	DoubleFormat d = new DoubleFormat( -4.2035492222884317E-246, Base.DECIMAL );
	Notation n = new Notation();
	n.setScientific();
	d.setNotation( n );
	DoubleFormat.HTMLStringRepresentation s = d.representation();
	System.out.print( s.string + " (length = " );
	System.out.print( s.length );
	System.out.println( ")" );
	for( int p = 14; p > 0; --p ){
	    System.out.print( p );
	    System.out.print( ": " );
	    d.setPrecision( p );
	    s = d.representation();
	    System.out.print( s.string + " (length = " );
	    System.out.print( s.length );
	    System.out.println( ")" );
	}
    }

    /**
     * Keep a copy of the double this object represents
     */
    private final double number;
    /**
     * The current Base
     */
    private Base base;
    /**
     * The current Notation
     */
    private Notation notation;
    /**
     * The current Precision
     */
    private int precision;
    /**
     * Representation of significand digits as integers in correct base.
     */
    private java.util.Vector<Integer> digits;
    /**
     * exponent
     */
    private long exponent;
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
    /**
     * This is how we represent a
     * base point
     * in output strings. In principle you can change this.
     */
    public static final java.util.Vector<String> basePoint
	= new java.util.Vector<String>( java.util.Arrays.asList( "." ) );
    /**
     * This is how we represent the
     * start of an exponent
     * in output strings.
     */
    public static final String startExponent = "<sup>";
    /**
     * This is how we represent the
     * end of an exponent
     * in output strings.
     */
    public static final String endExponent = "</sup>";
    /**
     * This is how we represent a
     * minus sign
     * in output strings. In principle you can change this.
     */
    public static final java.util.Vector<String> minus
	= new java.util.Vector<String>( java.util.Arrays.asList( "&#8722;" ) );
    /**
     * This is how we represent a
     * NaN (not a number)
     * in output strings. In principle you can change this.
     */
    public static final java.util.Vector<String> NaN
	= new java.util.Vector<String>( java.util.Arrays.asList( "N", "a", "N" ) );
    /**
     * This is how we represent the number
     * infinity
     * in output strings. In principle you can change this.
     */
    public static final java.util.Vector<String> infinity
	= new java.util.Vector<String>( java.util.Arrays.asList( "&#8734;" ) );
    /**
     * This is how we represent the number
     * negative infinity
     * in output strings. In principle you can change this.
     */
    public static final java.util.Vector<String> minusInfinity
	= new java.util.Vector<String>( java.util.Arrays.asList( "&#8722;", "&#8734;" ) );
    /**
     * This is how we represent the
     * start of HTML
     * in output strings.
     */
    public static final String startHTML = "<html>";
    /**
     * This is how we represent the
     * end of HTML
     * in output strings.
     */
    public static final String endHTML = "</html>";
    /**
     * This is how we represent the number
     * i (square root of negative 1)
     * in output strings. In principle you can change this.
     */
    public static final java.util.Vector<String> imPrefix
	= new java.util.Vector<String>( java.util.Arrays.asList( "i" ) );
    /**
     * This is how we represent the number
     * e (used for natural logarithms)
     * in output strings. In principle you can change this.
     */
    public static final java.util.Vector<String> argumentPrefix
	= new java.util.Vector<String>( java.util.Arrays.asList( "e" ) );
    /**
     * This is how we represent a
     * plus sign
     * in output strings. In principle you can change this.
     */
    public static final java.util.Vector<String> plus
	= new java.util.Vector<String>( java.util.Arrays.asList( "+" ) );
    
    /**
     * This is used to assign HTMLStringRepresentation objects to a null value.
     * It allows us to make a null assignment that can still be used.
     */
    public final HTMLStringRepresentation
    NullRepresentation =
	new HTMLStringRepresentation( new jscicalc.StringArray(), 0 );
}
