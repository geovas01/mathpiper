/**
 * @file
 *
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

package jscicalc.complex;
import jscicalc.Base;
import jscicalc.Notation;
import jscicalc.AngleType;
import jscicalc.Parser;
import jscicalc.pobject.*;

/**
 * This class is only used to test formatting.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.8 $
 */
public class Test {
    /**
     * Call this function to process a test file. The file should be called
     * <code>test.jsc</code>, located in the build directory and the function
     * called as <code>java jscicalc/complex/Test</code>.
     * The function reads a number of numbers, together with a base, denoted
     * <code>Bin</code>, <code>Oct</code>, <code>Hex</code> or nothing for
     * binary, octal, hexadecimal or decimal formats. All eight possible combinations
     * of notation are tested for the number and for each both input/internal and
     * input/output checks are done.
     * @param args Standard argument: not used
     */
    public static void main( String[] args ){
	angleType = AngleType.RADIANS;
	java.io.File file = new java.io.File( "test.jsc" );
	java.io.FileInputStream inputStream;
	try {
	    inputStream = new java.io.FileInputStream( file );
	} catch( java.io.FileNotFoundException e ){
	    System.out.println( "File not found" );
	    return;
	}
	java.io.InputStreamReader reader = new java.io.InputStreamReader( inputStream );
	java.io.LineNumberReader lines = new java.io.LineNumberReader( reader );
	try {
	    int tests = 0;
	    int successes = 0;
	    int reasonables = 0;
	    for( String line = lines.readLine(); line != null; line = lines.readLine() ){
		java.util.regex.Matcher m = octothorp.matcher( line );
		if( m.find() ){
		    System.out.println( m.replaceAll( "" ) );
		    continue;
		} 
		Base b = Base.DECIMAL;
		m = hex.matcher( line );
		if( m.find() ){
		    line = m.replaceAll( "" );
		    b = Base.HEXADECIMAL;
		}
		m = oct.matcher( line );
		if( m.find() ){
		    line = m.replaceAll( "" );
		    b = Base.OCTAL;
		}
		m = bin.matcher( line );
		if( m.find() ){
		    line = m.replaceAll( "" );
		    b = Base.BINARY;
		}
		m = calc.matcher( line );
		if( !m.find() ){
		    String expressionString = line;
		    Object o = parse( expressionString, b );
		    if( o instanceof Complex ){
			Notation n = new Notation();
			n.setRectangular();
			n.setComplex();
			n.setStandard();
			++tests;
			if( checkNumber( expressionString, (Complex)o, n, b ) )
			    ++successes;
			n.setScientific();
			++tests;
			if( checkNumber( expressionString, (Complex)o, n, b ) )
			    ++successes;
			n.setNonComplex();
			n.setStandard();
			++tests;
			if( checkNumber( expressionString, (Complex)o, n, b ) )
			    ++successes;
			n.setScientific();
			++tests;
			if( checkNumber( expressionString, (Complex)o, n, b ) )
			    ++successes;
			n.setPolar();
			n.setComplex();
			n.setStandard();
			++tests;
			if( checkNumber( expressionString, (Complex)o, n, b ) )
			    ++successes;
			n.setScientific();
			++tests;
			if( checkNumber( expressionString, (Complex)o, n, b ) )
			    ++successes;
			n.setNonComplex();
			n.setStandard();
			++tests;
			if( checkNumber( expressionString, (Complex)o, n, b ) )
			    ++successes;
			n.setScientific();
			++tests;
			if( checkNumber( expressionString, (Complex)o, n, b ) )
			    ++successes;
		    } else {
			System.out.print( "Failed on: " );
			System.out.println( line );
		    }
		} else {
		    String expressionString = m.group();
		    String resultString = m.replaceAll( "" );
		    m = comma.matcher( expressionString );
		    expressionString = m.replaceAll( "" );
		    Object o1 = parse2( expressionString, b );
		    Object o2 = parse( resultString, b );
		    ++tests;
		    System.out.println( expressionString );
		    if( o1 instanceof Complex && o2 instanceof Complex ){
			Complex z1 = (Complex)o1;
			Complex z2 = (Complex)o2;
			if( testRelative( z1.real(), z2.real(), 10 * DBL_EPSILON ) &&
			    testRelative( z1.imaginary(), z2.imaginary(),
					  10 * DBL_EPSILON ) ){
			    ++successes;
// 			    double d1 = z1.real() - z2.real();
// 			    double d2 = z1.imaginary() - z2.imaginary();
// 			    System.out.print( d1 );
// 			    System.out.print( " " );
// 			    System.out.println( d2 );
			} else {
			    double d1 = StrictMath.abs( z1.real()
								  - z2.real() );
			    double d2 = StrictMath.abs( z1.imaginary()
								  - z2.imaginary() );
			    System.out.println();
			    System.out.print( "real 1: " );
			    System.out.println( z1.real() );
			    System.out.print( "real 2: " );
			    System.out.println( z2.real() );
			    System.out.print( "imaginary 1: " );
			    System.out.println( z1.imaginary() );
			    System.out.print( "imaginary 2: " );
			    System.out.println( z2.imaginary() );
			    if( d1 / z2.abs() <= 10 * DBL_EPSILON &&
				d2 / z2.abs() <= 10 * DBL_EPSILON ){
				++reasonables;
			    } else {
// 			    System.out.print( d1 );
// 			    System.out.print( " " );
// 			    System.out.println( d2 );
				System.out.print( "Failed on: " );
				System.out.println( line );
			    }
			}
		    } else {
			System.out.print( "Failed on: " );
			System.out.println( line );
		    }
		}
	    }
	    System.out.print( successes );
	    System.out.print( " successes and " );
	    System.out.print( reasonables );
	    System.out.print( " good results compared to magnitude of result " );
	    System.out.print( "(total " );
	    System.out.print( successes + reasonables );
	    System.out.print( ") in " );
	    System.out.print( tests );
	    System.out.println( " tests." );
	} catch( java.io.IOException e ){
	    System.out.println( "I/O Exception" );
	    return;
	}
    }
	
    /**
     * Used to try to create a Complex from a String expression. Used internally. A base
     * is needed to be sure the expression is interpreted correctly.
     * @param expressionString The expression
     * @param b The Base
     * @return An Object, hopefully a Complex
     */
    private static Object parse( String expressionString, Base b ){
	//System.out.println( expressionString );
	parser.clearExpression(); // just in case
	parser.base( b );
	    for( Character c : expressionString.toCharArray() ){
		switch( c ){
		    case '+':
			parser.add( new Add() );
			break;
		    case '-':
			parser.add( new Subtract() );
			break;
		    case 'e':
			parser.add( new E() );
			break;
		    case 'i':
			parser.add( new I() );
			break;
		    default:
			parser.add( new Numeral( c ) );
		}
	    }
	    return parser.evaluate( angleType );
    }

    /**
     * Used to try to create a Complex from a String expression. Used internally. A base
     * is needed to be sure the expression is interpreted correctly.
     * @param expressionString The expression
     * @param b The Base
     * @return An Object, hopefully a Complex
     */
    private static Object parse2( String expressionString, Base b ){
	//System.out.println( expressionString );
	parser.clearExpression(); // just in case
	parser.base( b );
	String[] elements = expressionString.split( " " );
	for( String s : elements ){
	    if( s.equals( "sqrt" ) )
		parser.add( new SquareRoot() );
	    else if( s.equals( "log" ) )
		parser.add( new Ln() );
	    else if( s.equals( "log10" ) )
		parser.add( new Log() );
	    else if( s.equals( "exp" ) )
		parser.add( new Exp() );
	    else if( s.equals( "sin" ) )
		parser.add( new Sin( angleType ) );
	    else if( s.equals( "cos" ) )
		parser.add( new Cos( angleType ) );
	    else if( s.equals( "tan" ) )
		parser.add( new Tan( angleType ) );
	    else if( s.equals( "asin" ) )
		parser.add( new ASin( angleType ) );
	    else if( s.equals( "acos" ) )
		parser.add( new ACos( angleType ) );
	    else if( s.equals( "atan" ) )
		parser.add( new ATan( angleType ) );
	    else if( s.equals( "(" ) )
		parser.add( new LParen() );
	    else if( s.equals( ")" ) )
		parser.add( new RParen() );
	    else if( s.equals( "inverse" ) )
		parser.add( new Inverse() );
	    else if( s.equals( "square" ) )
		parser.add( new Square() );
	    else if( s.equals( "cube" ) )
		parser.add( new Cube() );
	    else if( s.equals( "cuberoot" ) )
		parser.add( new CubeRoot() );
	    else if( s.equals( "power" ) )
		parser.add( new Power() );
	    else if( s.equals( "root" ) )
		parser.add( new Root() );
	    else if( s.equals( "conjugate" ) )
		parser.add( new Conjugate() );
	    else if( s.equals( "add" ) )
		parser.add( new Add() );
	    else if( s.equals( "subtract" ) )
		parser.add( new Subtract() );
	    else if( s.equals( "multiply" ) )
		parser.add( new Multiply() );
	    else if( s.equals( "divide" ) )
		parser.add( new Divide() );
	    else
		for( Character c : s.toCharArray() ){
		    switch( c ){
			case '+':
			    parser.add( new Add() );
			    break;
			case '-':
			    parser.add( new Subtract() );
			    break;
			case 'e':
			    parser.add( new E() );
			    break;
			case 'i':
			    parser.add( new I() );
			    break;
			default:
			    parser.add( new Numeral( c ) );
		    }
		}
	}
	return parser.evaluate( angleType );
    }
    /**
     * Compare expression and result strings. This is done in two phases. First, the
     * expression is compared with an internal representation to make sure that the
     * calculator is storing the value accurately. Second the expression is compared
     * with a calculator output to make sure that the output matches the input.
     * @param expression the expression string
     * @param z The input result
     * @param n The Notation
     * @param base The Base
     * @return <em>true</em> or <em>false</em> according as the expression matches
     * internal representation and result.
     */
    private static boolean
    checkNumber( String expression, Complex z, Notation n, Base base ){
	// Print compact details of what is being tested
	if( n.standard() ) System.out.print( "St" );
	else System.out.print( "Sc" );
	if( n.rectangular() ) System.out.print( "R" );
	else System.out.print( "P" );
	if( n.complex() ) System.out.print( "C" );
	else System.out.print( "N" );
	System.out.print( ": " );
	// convert z to string in base.
	String s = z.toHTMLString( 20, 10, base, n, 1 );
	s = html.matcher( s ).replaceAll( "" ); //dump html
	s = minus.matcher( s ).replaceAll( "-" ); //Change - sign
	s = sup.matcher( s ).replaceAll( "" ); //dump </sup>
	if( n.polar() ){
	    s = esymbol.matcher( s ).replaceAll( "+" ); // change e^x to + x;
	    s = plus.matcher( s ).replaceAll( "" ); // remove leading + sign
	}
	s = decexp.matcher( s ).replaceAll( "e" ); //Change x 10
	if( overflow.matcher( s ).matches() ){
	    System.out.println( "overflow" );
	    return true;
	}
	////System.out.println( s );
	String re = new String( "0" );
	if( n.polar() ) re = new String( "1" );
	String im = new String( "0" );
	// find imaginary part (or argument)
	java.util.regex.Matcher m = deci.matcher( s );
	if( m.find() && m.group().length() > 0 ){
	    im = m.group();
	    s = m.replaceAll( "" );
	    // get rid of i
	    im = J.matcher( im ).replaceAll( "" );
	}
	im = plus.matcher( im ).replaceAll( "" ); //dump leading + sign
	s = plus2.matcher( s ).replaceAll( "" ); // remove trailing + sign
	// find real part
	m = dec.matcher( s );
	if( m.matches() && s.length() > 0 ){
	    re = s;
	}
	////System.out.println( "{"+re+";"+im+"}" );
	// re, im now contain strings -- convert to doubles
	Pair aP = parseGeneral( re, getRadix( base ), getRadix2( base ) );
	Pair bP = parseGeneral( im, getRadix( base ), getRadix2( base ) );
	int sigDigitsRe = sigDigits( re );
	int sigDigitsIm = sigDigits( im );
	double a = aP.value;
	double b = bP.value;
	////System.out.println( "{"+Double.toString( a )+":"+Double.toString( b )+"}" );
	// find dre
	boolean dre = n.rectangular() ?
	    compare( Math.abs( z.real() ),
		     Math.abs( a ), 0, sigDigitsRe,
		     getRadix( base ) ) :
	    compare( Math.abs( z.abs() ),
		     Math.abs( a ), 0, sigDigitsRe,
		     getRadix( base ) );
	// find dim
	boolean dim = false; {
	    double small = 0;
	    double p = 0;
	    if( n.rectangular() ){
		p = Math.abs( z.imaginary() );
		if( n.nonComplex() && p <= Complex.smallImaginary )
		    small = Complex.smallImaginary;
	    } else { //polar 
		p = Math.abs( z.arg() );
		if( n.nonComplex() && p <= Complex.smallImaginary )
		    small = Complex.smallImaginary;
	    }
	    ////System.out.print( "now {" );
	    ////System.out.print( z.imaginary() );
	    ////System.out.print( ", " );
	    ////System.out.print( p );
	    ////System.out.print( ", " );
	    ////System.out.print( Math.abs( b ) );
	    ////System.out.print( ", " );
	    ////System.out.print( small );
	    ////System.out.println( "}" );
	    dim = compare( p, Math.abs( b ), small, sigDigitsIm,
			   getRadix( base ) );
	    ////System.out.println( "done" );
	}
	boolean result = true;
	if( !dre || !dim ){
	    System.out.print( "failed (internal--output): " );
	    result = false;
	}
	// z HTML string matches sucessfully
	if( n.rectangular() ){
	    // second test input--output
	    re = new String( "0" );
	    im = new String( "0" );
	    // find imaginary part 
	    s = expression;
	    m = deci.matcher( s );
	    if( m.find() && m.group().length() > 0 ){
		im = m.group();
		s = m.replaceAll( "" );
		// get rid of i
		im = J.matcher( im ).replaceAll( "" );
		// im might now be just "" or just "-";
		if( minus2.matcher( im ).matches() )
		    im = "-1";
		else if( empty.matcher( im ).matches() )
		    im = "1";
		else if( plus.matcher( im ).matches() )
		    im = "1";
	    }
	    im = plus.matcher( im ).replaceAll( "" ); //dump leading + sign
	    s = plus2.matcher( s ).replaceAll( "" ); // remove trailing + sign
	    // find real part
	    m = dec.matcher( s );
	    if( m.matches() && s.length() > 0 ){
		re = s;
	    }
	    re = plus.matcher( re ).replaceAll( "" ); //dump leading + sign
	    ////System.out.println( "["+re+";"+im+"]" );
	    // re, im now contain strings -- convert to doubles
	    aP = parseGeneral( re, getRadix( base ), getRadix( base ) );
	    bP = parseGeneral( im, getRadix( base ), getRadix( base ) );
	    if( sigDigits( re ) < sigDigitsRe ) sigDigitsRe = sigDigits( re );
	    if( sigDigits( im ) < sigDigitsIm ) sigDigitsIm = sigDigits( im );
	    double c = aP.value;
	    double d = bP.value;
	    // find dre
	    ////System.out.println( "DRE" );
	    dre = compare( Math.abs( c ),
			   Math.abs( a ), 0, sigDigitsRe,
			   getRadix( base ) );
	    ////System.out.println( "DRE done" );
	    // find dim
	    dim = false; {
		double p = Math.abs( d );
		double small = 0;
		if( n.nonComplex() && p <= Complex.smallImaginary )
		    small = Complex.smallImaginary;
		////System.out.println( "DIM" );
		////System.out.print( "dim {" );
		////System.out.print( p );
		////System.out.print( ", " );
		////System.out.print( Math.abs( b ) );
		////System.out.print( ", " );
		////System.out.print( small );
		////System.out.println( "}" );
		dim = compare( p, Math.abs( b ), small, sigDigitsIm,
			       getRadix( base ) );
		////System.out.println( "DIM done" );
	    }
	    if( !dre || !dim ){
		System.out.print( "failed (input--output): " );
		result = false;
	    }
	}
	// Now test z against input string
	System.out.print( expression );
	System.out.print( " == " );
	System.out.print( a );
	System.out.print( "+" );
	System.out.print( b );
	System.out.println( "i" );
	return result;
    }

    /**
     * Find the number of significant digits in the number.
     * @param number A string representation (including e )
     * @return The number of significant digits
     */
    private static int sigDigits( String number ){
	String s = end.matcher( number ).replaceAll( "" );
	s = trailingzero.matcher( s ).replaceAll( "" );
	s = begin.matcher( s ).replaceAll( "" );
	java.util.regex.Matcher m = digit.matcher( s );
	int count = 0;
	while( m.find() ) ++count;
	if( count > 10 ) count = 10;
	return count;
    }

    /**
     * Parses an integer. The base to use is given by radix and e is used to
     * handle exponents.
     * @param s The integer as a string
     * @param radix Used for the base
     * @param e Used for exponent
     * @return A number and its number of significant digits
     * @see parseGeneral()
     */
    public static Pair parseStandard( String s, int radix, int e ){
	java.util.regex.Matcher m = basepoint.matcher( s ); 
	int place = 0;
	boolean flag = m.find();
	if( flag ){
	    place = m.start();
	    s = m.replaceAll( "" );
	}
	double value = 0;
	int length = -1;
	for( boolean parsed = false; parsed == false; )
	    try {
		value = (double)(Long.parseLong( s, radix ) );
		length = s.length() - (value < 0 ? 1 : 0);
		parsed = true;
	    } catch( NumberFormatException exception ) {
		if( flag && place == s.length() ) flag = false;
		s = s.substring( 0, s.length() - 1 );
		if( !flag ) ++e;
	    }
	place -= (value < 0 ? 1 : 0);
	if( !flag ) place = length;
	value *= StrictMath.pow( radix, place - length );
	value *= StrictMath.pow( radix, e );
	return new Pair( length, value );
    }

    /**
     * Parses a number. The base to use is given by radix.
     * @param s The integer as a string
     * @param radix Used for the base
     * @param radix2 Used for the base
     * @return A number and its number of significant digits
     */
    public static Pair parseGeneral( String s, int radix, int radix2 ){
	java.util.regex.Matcher m = exponent2.matcher( s ); 
	int e = 0;
	if( m.find() ){
	    String t = m.group();
	    s = m.replaceAll( "" );
	    t = esymbol2.matcher( t ).replaceAll( "" );
	    t = plus.matcher( t ).replaceAll( "" );
	    e = (int)(Long.parseLong( t, radix2 ));
	}
	if( minus2.matcher( s ).matches() )
	    s = "-1";
	else if( empty.matcher( s ).matches() )
	    s = "1";
	else if( plus.matcher( s ).matches() )
	    s = "1";
	return parseStandard( s, radix, e );
    }

    /**
     * Local class used by parseGeneral().
     */
    private static class Pair {
	/**
	 * Constructor
	 * @param length The length of the number represented.
	 * @param value The value of the number represented.
	 */
	public Pair( int length, double value ){
	    this.length = length;
	    this.value = value;
	}
	/**
	 * The length of the number represented.
	 */
	public int length;
	/**
	 * The value of the number represented.
	 */
	public double value;
    }

    /**
     * Used to convert Base to corresponding int.
     * @param b A Base
     * @return The base as an integer: 2, 8, 10 or 16.
     */
    private static int getRadix( Base b ){
	switch( b ){
	    case BINARY:
		return 2;
	    case OCTAL:
		return 8;
	    case DECIMAL:
		return 10;
	    default:
		return 16;
	}
    }

    /**
     * Used to convert Base to corresponding int. Differs from getRadix() by returning
     * 10 if b is Base.Binary. Hence appropriate for evaluating output strings.
     * @param b A Base
     * @return The base as an integer: 8, 10 or 16.
     */
    private static int getRadix2( Base b ){
	if( b == Base.BINARY ) return 10;
	else return getRadix( b );
    }

    /**
     * The main comparison function. This compares a number with an approximation
     * to it. The comparison considers numbers to sigDigits significat digits in the
     * approximation, but also allows the absolute difference between two numbers to
     * be considered and if the difference exceeds absolute, the numbers are considered
     * the same.
     * @param number The number
     * @param rounded An approximation to the number
     * @param absolute The allowable absolute difference between the number and
     * approximation.
     * @param sigDigits The number of digits that should match
     * @param base The base: 2, 8, 10 or 16
     * @return <em>true</em> or <em>false</em> according as the numbers match or not
     */
    private static boolean compare( double number, double rounded, double absolute,
				    int sigDigits, int base ){
	double difference = Math.abs( number - rounded );
	if( difference < absolute ) return true;
	if( difference == 0 ) return true;
	// convert both number and difference
	double n = Math.abs( number );
	while( difference > base / 2 ){
	    difference /= base;
	    n /= base;
	}
	while( difference <= 0.5 ){
	    difference *= base;
	    n *= base;
	}
	int digits;
	for( digits = 0; n > 1; ++digits )
	    n /= base;
	////System.out.println( "Number = " + Double.toString( number ) );
	////System.out.println( "Rounded = " + Double.toString( rounded ) );
	////System.out.println( "sig digits = " + Integer.toString( sigDigits ) );
	////System.out.println( "digits = " + Integer.toString( digits ) );
	return digits >= sigDigits;
    }

    /**
     * Test if result matches expected accurately. Based on functions in the GNU
     * scientific library.
     * @param result The result of a calculation
     * @param expected The expected result
     * @param relative_error The relative error in the calculation
     * @return <em>true</em> or <em>false</em> according as result matches
     * expected
     */
    private static boolean testRelative( double result, double expected,
					 double relative_error ){
	if( Double.isNaN( result ) )
	    return Double.isNaN( expected );
	else if( Double.isInfinite( expected ) )
	    return result == expected;
	else if( expected != 0 ){
	    if( StrictMath.abs( result - expected )
		/ StrictMath.abs( expected ) <= relative_error ){
		return true;
	    } else {
		System.out.print( "Relative error = " );
		System.out.print( StrictMath.abs( result - expected )
				    / StrictMath.abs( expected ) );
		System.out.print( " " );
		return false;
	    }
	} else {
	    if( StrictMath.abs( result ) <= relative_error ){
		return true;
	    } else {
		System.out.print( "Absolute error = " );
		System.out.print( StrictMath.abs( result ) );
		System.out.print( " " );
		return false;
	    }
	}
    }
    
    /**
     * Used for relative errors.
     */
    static final double DBL_EPSILON = 2.2204460492503131e-16;

    /**
     * Not used
     */
    private final static java.util.EnumSet<Base> bases
    = java.util.EnumSet.of( Base.BINARY, Base.OCTAL, Base.DECIMAL, Base.HEXADECIMAL );

    /**
     * The number e
     */
    private final static double e = Math.exp( 1 );
    /**
     * Could be used for angles.
     */
    private final static double p = 1.0 / 180;
    /**
     * A Parser. Used to evaluate expressions: hence automatic testing.
     */
    private static Parser parser = new Parser();
    /**
     * Leading plus sign
     */
    private static final java.util.regex.Pattern plus
    = java.util.regex.Pattern.compile( "^\\+" );
    /**
     * Trailing plus sign
     */
    private static final java.util.regex.Pattern plus2
    = java.util.regex.Pattern.compile( "\\+$" );
    /**
     * HTML minus sign
     */
    private static final java.util.regex.Pattern minus
    = java.util.regex.Pattern.compile( "&#8722;" );
    /**
     * Java minus sign
     */
    private static final java.util.regex.Pattern minus2
    = java.util.regex.Pattern.compile( "-" );
    /**
     * Nothing
     */
    private static final java.util.regex.Pattern empty
    = java.util.regex.Pattern.compile( "" );
    /**
     * The number i
     */
    private static final java.util.regex.Pattern J
    = java.util.regex.Pattern.compile( "i" );
    /**
     * Start or end html
     */
    private static final java.util.regex.Pattern html
    = java.util.regex.Pattern.compile( "</?html>" );
    /**
     * Polar representation start of exponent.
     */
    private static final java.util.regex.Pattern esymbol
    = java.util.regex.Pattern.compile( "e<sup>" );
    /**
     * Scientific representation start of exponent.
     */
    private static final java.util.regex.Pattern decexp
    = java.util.regex.Pattern.compile( "<sup>" );
    /**
     * End of exponent.
     */
    private static final java.util.regex.Pattern sup
    = java.util.regex.Pattern.compile( "</sup>" );
    /**
     * A number (originally decimal but now any)
     */
    private static final java.util.regex.Pattern dec
    = java.util.regex.Pattern.compile( "[+-]?[0-9A-F]*\\.?[0-9A-F]*(e[+-]?[0-9A-F]+)?" );
    /**
     * A number (originally decimal but now any) follwoed by i
     */
    private static final java.util.regex.Pattern deci = java.util.regex.Pattern
    .compile( "[+-]?[0-9A-F]*\\.?[0-9A-F]*(e[+-]?[0-9A-F]+)?i" );
    /**
     * Start of number smaller than 1 in size.
     */
    private static final java.util.regex.Pattern begin
    = java.util.regex.Pattern.compile( "[+-]?0?\\.0*" );
    /**
     * End of number
     */
    private static final java.util.regex.Pattern end
    = java.util.regex.Pattern.compile( "e[+-]?[0-9A-F]+" );
    /**
     * Trailing zero
     */
    private static final java.util.regex.Pattern trailingzero
    = java.util.regex.Pattern.compile( "0*$" );
    /**
     * Any digit
     */
    private static final java.util.regex.Pattern digit
    = java.util.regex.Pattern.compile( "[0-9A-F]" );
    /**
     * Octothorp (hash): used for comments
     */
    private static final java.util.regex.Pattern octothorp
    = java.util.regex.Pattern.compile( "^#" );
    /**
     * Used to identify if there is more than one expression on a line.
     */
    private static final java.util.regex.Pattern calc
    = java.util.regex.Pattern.compile( "^.*, " );
    /**
     * Used to identify comma.
     */
    private static final java.util.regex.Pattern comma
    = java.util.regex.Pattern.compile( ", $" );
    /**
     * Used to identify overflow (from Parser).
     */
    private static final java.util.regex.Pattern overflow
    = java.util.regex.Pattern.compile( "^overflow$" );
    /**
     * The base point
     */
    private static final java.util.regex.Pattern basepoint
    = java.util.regex.Pattern.compile( "\\." );
    /**
     * The exponent symbol e
     */
    private static final java.util.regex.Pattern esymbol2
    = java.util.regex.Pattern.compile( "e" );
    /**
     * An exponent
     */
    private static final java.util.regex.Pattern exponent2
    = java.util.regex.Pattern.compile( "e[+-]?[0-9A-F]+" );
    /**
     * Start of hexadecimal expression.
     */
    private static final java.util.regex.Pattern hex
    = java.util.regex.Pattern.compile( "^Hex " );
    /**
     * Start of binary expression.
     */
    private static final java.util.regex.Pattern bin
    = java.util.regex.Pattern.compile( "^Bin " );
    /**
     * Start of octal expression.
     */
    private static final java.util.regex.Pattern oct
    = java.util.regex.Pattern.compile( "^Oct " );
    /**
     * AngleType.
     */
    private static AngleType angleType;
}
