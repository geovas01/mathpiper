/**
 * @file
 *
 * Copyright (C) 2007, 2008 John D Lamb (J.D.Lamb@btinternet.com)
 * Portions of this file are adapted from the complex functions of the
 * GNU Scientific Library, http://gsl.gnu.org/,
 * Copyright (C) 1996, 1997, 1998, 1999, 2000 Jorma Olavi Tätinen, Brian Gough.
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
import jscicalc.OObject;

/**
 * Complex numbers. This is based on the GNU Scientific Library, http://gsl.gnu.org/,
 * Copyright (C) 1996, 1997, 1998, 1999, 2000 Jorma Olavi Tätinen, Brian Gough and was
 * suggested as an addition to the calculator by Sebastian Schneider ihi0 at
 * sourceforge dot net.
 *
 * @author J.&nbsp;D.&nbsp;Lamb 
 * @version $Revision: 1.15 $
 */
public class Complex extends OObject {

    /**
     * Default constructor. Gives the complex number zero.
     */
    public Complex(){
	this( 0, 0 );
    } 

    /**
     * Constructor. Converts real number to complex representation.
     * @param real The number to be converted
     */
    public Complex( double real ){
	this( real, 0 );
    }

    /**
     * Constructor. Converts real and imaginary parts to complex number.
     * @param real The real part
     * @param imaginary The imaginary part
     */
    public Complex( double real, double imaginary ){
	real_part = real;
	imaginary_part = imaginary;
    }

    /**
     * Constructor. Converts real and imaginary parts to complex number.
     * Probably not needed.
     * @param r The distance from zero
     * @param theta The angle
     */
    public static Complex polar( double r, double theta ){
	return new Complex( r * StrictMath.cos( theta ), r * StrictMath.cos( theta ) );
    }

    /**
     * Test for equality to zero.
     * @return <em>true</em> or <em>false</em> according as this is zero or not.
     */
    public boolean isZero(){
	return real() == 0 && imaginary() == 0;
    }

    /**
     * Test if an integer.
     * @return Long if it is, null if it is not an integer.
     */
    public Long isInteger(){
	if( imaginary() == 0 ){
	    long l =(long)real();
	    if( l == real() ) return l;
	}
	return null;
    }
    
    /**
     * Indicate whether or not Complex has leading negative sign.
     */
    public boolean isNegative(){
	return real() < 0 || (real() == 0 && imaginary() < 0);
    }


    /**
     * Get the real part of the complex number.
     * @return The real part of the complex number.
     */
    public double real() {
	return real_part;
    }
    
    /**
     * Get the real part of the complex number.
     * @return The real part of the complex number.
     */
    public double imaginary() {
	return imaginary_part;
    }
    
    /**
     * Get the conjugate of this.
     * @return The conjugate of the complex number.
     */
    public Complex conjugate(){
	return new Complex( real(), -imaginary() );
    }

    /**
     * Get the negative of this.
     * @return The negative of the complex number.
     */
    public Complex negate(){
	return new Complex( -real(), -imaginary() );
    }

    /**
     * Get the absolute value of this.
     * @return The absolute value of the complex number.
     */
    public double abs(){
	return StrictMath.hypot( real(), imaginary() );
    }

    /**
     * Get the argument of this. Returns a value in the range (-Pi, Pi ).
     * @return The argument of the complex number.
     */
    public double arg(){
	if( real() == 0 && imaginary() == 0 ){
	    return 0;
	} else {
	    return StrictMath.atan2( imaginary(), real() );
	}
    }

    /**
     * Get the inverse of this.
     * @return The argument of the complex number.
     */
    public Complex inverse(){
	//double s = 1 / abs();
	//return new Complex( (real() * s ) * s, -((imaginary() * s) * s ) );
	if( real() == 0 && imaginary() == 0 )
	    return new Complex( Double.POSITIVE_INFINITY, 0 );
	if( Double.isInfinite( real() ) || Double.isInfinite( imaginary() ) ){
	    Complex one = new Complex( 1, 0 );
	    return one.divide( this );
	}
	java.math.BigDecimal a = new java.math.BigDecimal( real() );
	java.math.BigDecimal b = new java.math.BigDecimal( imaginary() );
	java.math.BigDecimal s = a.multiply( a ).add( b.multiply( b ) );
	double c = a.divide( s, java.math.MathContext.DECIMAL64 ).doubleValue();
	double d = b.negate().divide( s, java.math.MathContext.DECIMAL64 ).doubleValue();
	return new Complex( c, d );
    }

    /**
     * Multiply this by i. This function is used so that the calculator can enter
     * complex numbers in convenient form without having to know when parsing a double
     * that it will later be converted to a complex imaginary part.
     * @return The complex number multiplied by i.
     */
    public Complex imultiply(){
	return new Complex( -imaginary(), real() );
    }

    /**
     * Scale this.
     * @param real The scale factor
     * @return The product of this and real.
     */
    public Complex scale( double real ){
	return new Complex( real * this.real(), real * imaginary() );
    }

    /**
     * Add a complex number to this.
     * @param complex The addend
     * @return The sum of this and complex.
     */
    public Complex add( Complex complex ){
	return new Complex( real() + complex.real(), imaginary() + complex.imaginary() );
    }

    /**
     * Subtract a complex number from this.
     * @param complex The subtrahend
     * @return The value of this minus complex.
     */
    public Complex subtract( Complex complex ){
	return new Complex( real() - complex.real(), imaginary() - complex.imaginary() );
    }

    /**
     * Multiply this by a complex number.
     * @param complex The multiplier.
     * @return The product of this and complex.
     */
    public Complex multiply( Complex complex ){
	//new Complex( real() * complex.real() - imaginary() * complex.imaginary(),
	//real() * complex.imaginary() + imaginary() * complex.real() );
	if( Double.isInfinite( real() ) || Double.isInfinite( imaginary() ) ||
		   Double.isInfinite( complex.real() ) ||
		   Double.isInfinite( complex.imaginary() ) ){
	    double a = real();
	    double b = imaginary();
	    double c = complex.real();
	    double d = complex.imaginary();
	    double ac = a * c;
	    double bd = b * d;
	    double ad = a * d;
	    double bc = b * c;
	    if( Double.isNaN( ac ) && (a == 0 || c == 0) ) ac = 0;
	    if( Double.isNaN( bd ) && (b == 0 || d == 0) ) bd = 0;
	    if( Double.isNaN( ad ) && (a == 0 || d == 0) ) ad = 0;
	    if( Double.isNaN( bc ) && (b == 0 || c == 0) ) bc = 0;
	    double x = ac - bd;
	    double y = bc + ad;
	    if( x == 0 && y == 0 ) return new Complex( Double.NaN, Double.NaN );
	    return new Complex( x, y );
	} else if( Double.isNaN( real() ) || Double.isNaN( imaginary() ) ||
	    Double.isNaN( complex.real() ) || Double.isNaN( complex.imaginary() ) ){
	    return new Complex( Double.NaN, Double.NaN );
	}
	java.math.BigDecimal a = new java.math.BigDecimal( real() );
	java.math.BigDecimal b = new java.math.BigDecimal( imaginary() );
	java.math.BigDecimal c = new java.math.BigDecimal( complex.real() );
	java.math.BigDecimal d = new java.math.BigDecimal( complex.imaginary() );
	double x = a.multiply( c ).subtract( b.multiply( d ) ).doubleValue();
	double y = a.multiply( d ).add( b.multiply( c ) ).doubleValue();
	return new Complex( x, y );
    }

    /**
     * Divide this by a complex number.
     * @param complex The divisor.
     * @return The result of this divided by complex
     */
    public Complex divide( Complex complex ){
	//double r = real();
	//double i = imaginary();
	//double s = 1 / complex.abs();
	//double sr = s * complex.real();
	//double si = s * complex.imaginary();

	//double nr = (r * sr + i * si) * s;
	//double ni = (i * sr - r * si) * s;
	
	//return new Complex( nr, ni );
	// deal with special case of division by zero
	if( complex.real() == 0 && complex.imaginary() == 0 ){
	    if( real() == 0 && imaginary() == 0 )
		return new Complex( Double.NaN );
	    double x = 0;
	    if( real() > 0 ) x = Double.POSITIVE_INFINITY;
	    else if( real() < 0 ) x = Double.NEGATIVE_INFINITY;
	    double y = 0;
	    if( imaginary() > 0 ) y = Double.POSITIVE_INFINITY;
	    else if( imaginary() < 0 ) y = Double.NEGATIVE_INFINITY;
	    return new Complex( x, y );
	}
	double test = complex.real() * complex.real()
	    + complex.imaginary() * complex.imaginary();
	if( Double.isNaN( test ) ){
	    return new Complex( Double.NaN, Double.NaN );
	} else if( Double.isInfinite( test ) ){
	    if( real() == 0 && imaginary() == 0 )
		return new Complex( Double.NaN, Double.NaN );
	    else
		return new Complex();
	}
	java.math.BigDecimal a = new java.math.BigDecimal( real() );
	java.math.BigDecimal b = new java.math.BigDecimal( imaginary() );
	java.math.BigDecimal c = new java.math.BigDecimal( complex.real() );
	java.math.BigDecimal d = new java.math.BigDecimal( complex.imaginary() );
	java.math.BigDecimal s = c.multiply( c ).add( d.multiply( d ) );
	double x = (a.multiply( c ).add( b.multiply( d ) ))
	    .divide( s, java.math.MathContext.DECIMAL64 ).doubleValue();
	double y = (b.multiply( c ).subtract( a.multiply( d ) ))
	    .divide( s, java.math.MathContext.DECIMAL64 ).doubleValue();
	return new Complex( x, y );
    }
    
    /*
     * Basic functions: square root, square, cube, exponent, power, cube root, logs
     */

    /**
     * Get the square of this.
     * @return The square of this
     */
    public Complex square(){
	return multiply( this );
    }

    /**
     * Get the cube of this.
     * @return The cube of this
     */
    public Complex cube(){
	return multiply( square() );
    }

    /**
     * Get the square root of this.
     * @return The square root of this
     */
    public Complex sqrt(){
	if( real() == 0.0 && imaginary() == 0.0 ){
	    return new Complex();
	} else {
	    double x = StrictMath.abs( real() );
	    double y = StrictMath.abs( imaginary() );
	    double w = 0;

	    if( x >= y ){
		double t = y / x;
		w = StrictMath.sqrt( x ) * StrictMath
		    .sqrt( 0.5 * (1.0 + StrictMath.sqrt( 1.0 + t * t )) );
	    } else {
		double t = x / y;
		w = StrictMath.sqrt( y ) * StrictMath
		    .sqrt( 0.5 * (t + StrictMath.sqrt( 1.0 + t * t)) );
	    }
	    
	    if( real() >= 0.0 ){
		return new Complex( w, imaginary() / (2 * w ));
	    } else {
		double vi = (imaginary() >= 0) ? w : -w;
		return new Complex( imaginary() / (2 * vi ), vi );
	    }
	}
    }
	
    /**
     * Find exp( this ).
     * @return exp( this ).
     */
    public Complex exp(){
	double rho = StrictMath.exp( real() );
	double theta = imaginary();

	return new Complex( rho * StrictMath.cos( theta ),
			    rho * StrictMath.sin( theta ) );
    }

    /**
     * Find 10 to the power of this.
     * @return 10 to the power of this.
     */
    public Complex tenx(){
	return multiply( LOG10 ).exp();
    }

    /**
     * Find log |this|.
     * @return log |this|.
     */
    private double logabs(){
	double xabs = StrictMath.abs( real() );
	double yabs = StrictMath.abs( imaginary() );
	double max, u;
	
	if( xabs >= yabs ){
	    max = xabs;
	    u = yabs / xabs;
	} else {
	    max = yabs;
	    u = xabs / yabs;
	}
	
	/* Handle underflow when u is close to 0 */
	
	return StrictMath.log( max ) + 0.5 * StrictMath.log1p( u * u );
    }

    /**
     * Find power of this for integral powers.
     * @param r the power
     * @return this to the power of r.
     */
    private Complex pow( int r ){
	Complex p = new Complex( real(), imaginary() ); // multiplier
	Complex y = new Complex( 1, 0 );                // result
	boolean flag = false;                           // do we need to multiply?
	
	while( r > 0 ){
	    if( r % 2 == 1 ){
		if( flag ){
		    y = y.multiply( p );
		} else {
		    flag = true;
		    y = new Complex( p.real(), p.imaginary() );
		}
	    }
	    r /= 2;  // next binary digit
	    p = p.square();
	}
	return y;
    }
    /**
     * Find root of this for integer.
     * @param r the root
     * @return rth root of this.
     */
    private Complex root( int r ){
	final double abs_error = 2.2204460492503131e-16;
	final int limit = 10;
	
	if( r == 2 ) return sqrt();
	
	// estimate root.
	double logr = logabs();
	double theta = arg();
	
	double rho = StrictMath.exp( logr / r );
	double beta = theta / r;
	
	Complex x = new Complex( rho * StrictMath.cos( beta ),
				 rho * StrictMath.sin( beta ) );
	// now try to improve
	int count = 0;
	for( double error = 1; error > abs_error && count < limit; ++count ){
	    Complex x_next = x.scale( r - 1 ).add( divide( x.pow( r - 1 ) ) );
	    x_next = x_next.scale( 1 / (double)r );
	    error = subtract( x_next.pow( r ) ).abs() + (x_next.subtract( x ) ).abs();
	    x = new Complex( x_next.real(), x_next.imaginary() );
	} 
	return x;
    }

    /**
     * Find this to the power of r.
     * @param r the power
     * @return this to the power of r.
     */
    public Complex pow( Complex r ){
	if( real() == 0 && imaginary() == 0 ){
	    if( r.real() == 0 && r.imaginary() == 0 )
		return new Complex( 1, 0 );  // 0^0 = 1
	    else
		return new Complex();
	} else {
	    if( r.imaginary() == 0 ){
		int n = (int)r.real();
		if( r.real() == n ){
		    if( n > 0 ) return pow( n );
		    else return pow( n ).inverse();
		}
	    }
	    double logr = logabs();
	    double theta = arg();
	    
	    double rr = r.real();
	    double ri = r.imaginary();
	    
	    double rho = StrictMath.exp( logr * rr - ri * theta );
	    double beta = theta * rr + ri * logr;
	    
	    return new Complex( rho * StrictMath.cos( beta ),
				rho * StrictMath.sin( beta ) );
	}
    }  
    /**
     * Find rth root of this.
     * @param r The value such that pow( result, r ) = this
     * @return this to the power of r.
     */
    public Complex root( Complex r ){
	if( r.imaginary() == 0 ){
	    if( r.real() == 0 ){
		// 0th root
		if( arg() != 0 ) return new Complex( Double.NaN, Double.NaN );
		if( abs() == 1 ) return new Complex( 1, 0 );
		if( abs() < 1 ) return new Complex( 0, 0 );
		if( abs() > 1 ) return new Complex( Double.POSITIVE_INFINITY, 0 );
		return null;
	    }
	    int n = (int)r.real();
	    if( r.real() == n ){
		if( n > 0 ) return root( n );
		else return root( n ).inverse();
	    }
	}
	return pow( r.inverse() );
    }

    /**
     * Find log( this ).
     * @return log( this ).
     */
    public Complex log(){
	double logr = logabs();
	double theta = arg();

	return new Complex( logr, theta );
    }

    /**
     * Find log10( this ).
     * @return log10( this ).
     */
    public Complex log10(){
	double sc = 1 / StrictMath.log( 10 );
	return log().scale( sc );
    }
	
    
    /**
     * Get the cube root of this.
     * @return The cube root of this
     */
    public Complex cuberoot(){
	return root( 3 );
    }

    /*
     * Trig functions and their inverses
     */

    /**
     * Get the sine of this.
     * @return The sine of this
     */
    public Complex sin(){
	double r = real();
	double i = imaginary();
	
	if( i == 0 ){
	    /* avoid returing negative zero (-0.0) for the imaginary part  */
	    return new Complex( StrictMath.sin( r ), 0 );
	} else {
	    return new Complex( StrictMath.sin( r ) * StrictMath.cosh( i ),
				StrictMath.cos( r ) * StrictMath.sinh( i ) );
	}
    }

    /**
     * Get the cosine of this.
     * @return The cosine of this
     */
    public Complex cos(){
	double r = real();
	double i = imaginary();
	
	if( i == 0 ){
	    /* avoid returing negative zero (-0.0) for the imaginary part  */
	    return new Complex( StrictMath.cos( r ), 0 );
	} else {
	    return new Complex( StrictMath.cos( r ) * StrictMath.cosh( i ),
				StrictMath.sin( r ) * StrictMath.sinh( -i ) );
	}
	
    }

    /**
     * Get the tangent of this.
     * @return The tangent of this
     */
    public Complex tan(){
	double r = real();
	double i = imaginary();
	if( i == 0 ){
	    double s = 2 * r - StrictMath.PI;
	    long t = StrictMath.round( s / StrictMath.PI );
	    if( t % 2 == 0 && s == t * StrictMath.PI ){
		return new Complex( Double.NaN, Double.NaN );
	    }
	    return new Complex( StrictMath.tan( r ), 0 );
	}
	
	if( StrictMath.abs( i ) < 1) {
	    double cr = StrictMath.cos( r );
	    double si = StrictMath.sinh( i );
	    double d = cr * cr + si * si;
	    
	    return new Complex( 0.5 * StrictMath.sin( 2 * r ) / d,
				0.5 * StrictMath.sinh( 2 * i ) / d );
	} else {
	    double u = StrictMath.exp( -i );
	    double c = 2 * u / (1 - u * u);
	    double cr = StrictMath.cos( r );
	    double s = c * c;
	    double d = 1 + cr * cr * s;
	    
	    double t = 1.0 / StrictMath.tanh( i );
	    
	    return new Complex( 0.5 * StrictMath.sin( 2 * r ) * s / d, t / d );
	}
    }
    
    /**
     * Used to compute arccosh where y greater than or equal to 1
     * @param y The number
     * @return the inverse hypebolic cosine
     */
    private double acosh_real( double y ){
	return StrictMath.log( y + StrictMath.sqrt( y * y - 1 ) );
    }

    /**
     * Used to compute arcsin where imaginary part is zero.
     * @param a The real part
     * @return the arc sin
     */
    private Complex asin_real( double a ){
	if( StrictMath.abs( a ) <= 1.0 )
	    return new Complex( StrictMath.asin( a ), 0 );
	else
	    if( a < 0.0 )
		return new Complex( -StrictMath.PI / 2, acosh_real( -a ) );
	    else
		return new Complex( StrictMath.PI / 2, -acosh_real( a ) );
    }

    /**
     * Get the arcsine of this.
     * @return The arcsine of this
     */
    public Complex asin(){
	double R = real();
	double I = imaginary();
	
	if( I == 0 ){
	    return asin_real( R );
	} else {
	    double x = StrictMath.abs( R );
	    double y = StrictMath.abs( I );
	    double r = StrictMath.hypot( x + 1, y );
	    double s = StrictMath.hypot( x - 1, y );
	    double A = 0.5 * (r + s);
	    double B = x / A;
	    double y2 = y * y;
	    
	    double real, imag;
	    
	    final double A_crossover = 1.5;
	    final double B_crossover = 0.6417;
	    
	    if( B <= B_crossover ){
		real = StrictMath.asin( B );
	    } else {
		if( x <= 1 ){
		    double D = 0.5 * (A + x) * (y2 / (r + x + 1) + (s + (1 - x)));
		    real = StrictMath.atan( x / StrictMath.sqrt( D ) );
		} else {
		    double Apx = A + x;
		    double D = 0.5 * (Apx / (r + x + 1) + Apx / (s + (x - 1)));
		    real = StrictMath.atan( x / (y * StrictMath.sqrt( D )) );
		}
	    }
	    
	    if( A <= A_crossover ){
		double Am1;
		
		if (x < 1) {
		    Am1 = 0.5 * (y2 / (r + (x + 1)) + y2 / (s + (1 - x)));
		} else {
		    Am1 = 0.5 * (y2 / (r + (x + 1)) + (s + (x - 1)));
		}
		
		imag = StrictMath.log1p( Am1 + StrictMath.sqrt (Am1 * (A + 1)) );
	    } else {
		imag = StrictMath.log( A + StrictMath.sqrt( A * A - 1 ) );
	    }
	    
	    return new Complex( (R >= 0) ? real : -real, (I >= 0) ? imag : -imag );
	}
    }
    
    /**
     * Used to compute arccos where imaginary part is zero.
     * @param a The real part
     * @return the arc cos
     */
    private Complex acos_real( double a ){
	if( StrictMath.abs( a ) <= 1.0 )
	    return new Complex( StrictMath.acos( a ), 0 );
	else
	    if( a < 0.0 )
		return new Complex( StrictMath.PI, -acosh_real( -a ) );
	    else
		return new Complex( 0, acosh_real( a ) );
    }

    /**
     * Get the arccosine of this.
     * @return The arccosine of this
     */
    public Complex acos(){
	double R = real();
	double I = imaginary();
	
	if( I == 0 ){
	    return acos_real( R );
	} else {
	    double x = StrictMath.abs( R );
	    double y = StrictMath.abs( I );
	    double r = StrictMath.hypot( x + 1, y );
	    double s = StrictMath.hypot( x - 1, y );
	    double A = 0.5 * (r + s);
	    double B = x / A;
	    double y2 = y * y;
	    
	    double real;
	    double imag;
	    
	    final double A_crossover = 1.5;
	    final double B_crossover = 0.6417;
	    
	    if( B <= B_crossover ){
		real = StrictMath.acos (B);
	    } else {
		if( x <= 1 ){
		    double D = 0.5 * (A + x) * (y2 / (r + x + 1) + (s + (1 - x)));
		    real = StrictMath.atan( StrictMath.sqrt( D ) / x );
		} else {
		    double Apx = A + x;
		    double D = 0.5 * (Apx / (r + x + 1) + Apx / (s + (x - 1)));
		    real = StrictMath.atan( (y * StrictMath.sqrt( D )) / x );
		}
	    }
	    
	    if (A <= A_crossover ){
		double Am1;
		
		if( x < 1 ){
		    Am1 = 0.5 * (y2 / (r + (x + 1)) + y2 / (s + (1 - x)));
		} else {
		    Am1 = 0.5 * (y2 / (r + (x + 1)) + (s + (x - 1)));
		}
		
		imag = StrictMath.log1p( Am1 + StrictMath.sqrt( Am1 * (A + 1) ) );
	    } else {
		imag = StrictMath.log( A + StrictMath.sqrt( A * A - 1 ) );
	    }
	    
	    return new Complex( (R >= 0) ? real : StrictMath.PI - real,
				(I >= 0) ? -imag : imag );
	}
    }
    
    
    /**
     * Get the arctangent of this.
     * @return The arctangent of this
     */
    public Complex atan(){
	double R = real();
	double I = imaginary();
	
	if( I == 0 ){
	    return new Complex( StrictMath.atan( R ), 0 );
	} else {
	    double r = StrictMath.hypot( R, I );
	    double imag;
	    double u = 2 * I / (1 + r * r);
	    
	    if( StrictMath.abs( u ) < 0.1 ){
		imag = 0.25 * (StrictMath.log1p( u ) - StrictMath.log1p( -u ));
	    } else {
		double A = StrictMath.hypot( R, I + 1 );
		double B = StrictMath.hypot( R, I - 1 );
		imag = 0.5 * StrictMath.log( A / B );
	    }
	    
	    if( R == 0 ){
		if( I > 1 ){
		    return new Complex( StrictMath.PI * 0.5, imag );
		} else if( I < -1 ){
		    return new Complex( -StrictMath.PI * 0.5, imag );
		} else {
		    return new Complex( 0, imag );
		}
	    } else {
		return new Complex( 0.5 * StrictMath.atan2 (2 * R, ((1 + r) * (1 - r))),
				    imag );
	    }
	}
    }

    /*
     * Logic, Permutations, combinations and the like
     */

    /**
     * And with another complex
     * @param z The number to AND with this.
     * @return The bitwise result of a logical and operation on this and z.
     */
    public Complex and( Complex z ){ 
	return new Complex( and( real(), z.real() ), and( imaginary(), z.imaginary() ) );
    }

    /**
     * Calculates x AND y. Used internally for and( z ).
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    private static double and( double x, double y ){
	if( Double.isNaN( x ) || Double.isNaN( y )
	    || Double.isInfinite( x ) 
	    || Double.isInfinite( y ) )
	    throw new RuntimeException( "Boolean Error" );
	if( StrictMath.abs( y ) > StrictMath.abs( x ) ){
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
	x_significand &= y_significand;
	
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
	if( !x_sign & !y_sign )
	    result =- result;
	return result;
    }

    /**
     * Or with another complex
     * @param z The number to OR with this.
     * @return The bitwise result of a logical or operation on this and z.
     */
    public Complex or( Complex z ){ 
	return new Complex( or( real(), z.real() ), or( imaginary(), z.imaginary() ) );
    }

    /**
     * Calculates x OR y. Used internally for and( z ).
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    public static double or( double x, double y ){
	if( Double.isNaN( x ) || Double.isNaN( y )
	    || Double.isInfinite( x ) 
	    || Double.isInfinite( y ) )
	    throw new RuntimeException( "Boolean Error" );
	if( StrictMath.abs( y ) > StrictMath.abs( x ) ){
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
     * Xor with another complex.
     * @param z The number to XOR with this.
     * @return The bitwise result of a logical xor operation on this and z.
     */
    public Complex xor( Complex z ){ 
	return new Complex( xor( real(), z.real() ), xor( imaginary(), z.imaginary() ) );
    }

    /**
     * Calculates x XOR y. Used internally by xor().
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    public double xor( double x, double y ){
	if( Double.isNaN( x ) || Double.isNaN( y )
	    || Double.isInfinite( x ) 
	    || Double.isInfinite( y ) )
	    throw new RuntimeException( "Boolean Error" );
	if( StrictMath.abs( y ) > StrictMath.abs( x ) ){
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
	x_significand ^= y_significand;
	
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
	if( x_sign ^ y_sign )
	    result =- result;
	return result;
    }

    /**
     * Calculate factorial of this.
     * @return The factorial of this (or error if not a nonnegative integer).
     */
    public Complex factorial(){
	double x = real();
	if( imaginary() != 0 || x < 0 || StrictMath.round( x ) - x != 0 )
	    throw new ArithmeticException( "Factorial error" );
	if( x > 1024 )
	    return new Complex( Double.POSITIVE_INFINITY );
	
	try {
	    return new Complex( factorial( StrictMath.round( x ) ), 0 );
	} catch( Exception e ){
	    throw new ArithmeticException( "Factorial error" );
	}
    } 
    
    /**
     * Calculate factorial of x. Used internally.
     * @param x The value (left of symbol)
     * @return The result of the operation
     */
    private static double factorial( long x ){
	if( x == 0 )
	    return 1;
	else
	    return x * factorial( x - 1 );
    }

    /**
     * Calculates the number of unordered ways to choose y objects from this.
     * Produces an error if this doesn&rsquo;t make sense.
     * @param z The second value (right of symbol)
     * @return The result of the operation
     */
    public Complex combination( Complex z ){
	double x = real();
	double y = z.real();
	if( imaginary() != 0 || x < 0 || StrictMath.round( x ) - x != 0 )
	    throw new ArithmeticException( "Combination error" );
	if(  z.imaginary() != 0 || y < 0 || y > x || StrictMath.round( y ) - y != 0 )
	    throw new ArithmeticException( "Combination error" );
	
	try {
	    return new Complex( combination( StrictMath.round( x ),
					     StrictMath.round( y ) ), 0 );
	} catch( Exception e ){
	    throw new ArithmeticException( "Combination error" );
	}
    }
    
    /**
     * Calculates the number of unordered ways to choose y objects from x.
     * Produces an error if this doesn&rsquo;t make sense. Used internally.
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    private static double combination( long x, long y ){
	if( y == 0 )
	    return 1;
	else
	    return (double)x  / y * combination( x - 1, y - 1 );
    }

    /**
     * Calculates the number of ordered ways to choose y objects from this.
     * Produces an error if this doesn&rsquo;t make sense.
     * @param z The second value (right of symbol)
     * @return The result of the operation
     */
    public Complex permutation( Complex z ){
	double x = real();
	double y = z.real();
	if( imaginary() != 0 || x < 0 || StrictMath.round( x ) - x != 0 )
	    throw new ArithmeticException( "Combination error" );
	if(  z.imaginary() != 0 || y < 0 || y > x || StrictMath.round( y ) - y != 0 )
	    throw new ArithmeticException( "Combination error" );
	
	try {
	    return new Complex( permutation( StrictMath.round( x ),
					     StrictMath.round( y ) ), 0 );
	} catch( Exception e ){
	    throw new ArithmeticException( "Permutation error" );
	}
    }

    /**
     * Calculates the number of ordered ways to choose y objects from x.
     * Produces an error if this doesn&rsquo;t make sense. Used internally.
     * @param x The first value (left of symbol)
     * @param y The second value (right of symbol)
     * @return The result of the operation
     */
    private static double permutation( long x, long y ){
	if( y == 0 )
	    return 1;
	else
	    return x * permutation( x - 1, y - 1 );
    }
    
    /**
     * Function to create an HTML string representation of the complex number.
     * This has six arguments. First, we need to know the maximum number of characters
     * available and the desired precision of the complex numbers. These are given
     * as two arguments.
     * Then we need to know the base
     * Then we need to know whether the Complex number is to be represented in
     * standard or scientific notation. These give the last two arguments.
     * Finally we need to know if we must use complex notation.
     * If not we can ignore small imaginary parts (and zero real part).
     * @param maxChars The maximum number of (visible) characters we can use
     * @param precision The desired number of significant figures
     * @param base The required base
     * @param notation  The notation to use to display this value
     * @param polarFactor Not used
     * @return a vector of Strings of at most maxChars HTML characters representing
     * the complex number
     */
    public jscicalc.StringArray
	toHTMLSubString( int maxChars, int precision, final Base base,
			 final Notation notation, double polarFactor ){
	// set up vector
	jscicalc.StringArray result = new jscicalc.StringArray();
	/* Show NaNs as Error*/
	if( Double.isNaN( real() ) || Double.isNaN( imaginary() ) ){
	    String[] error = { "E", "r", "r", "o", "r" };
	    result.add( error );
	    return result;
	}
	    
	/* weak sanity checks */
	if( maxChars < 4 )
	    throw new RuntimeException( "Complex.toHTMLString" +
						  "maxChars must be at least four" );
	if( precision < 1 )
	    throw new RuntimeException( "Complex.toHTMLString" +
						  "precision must be positive" );

	Notation n = new Notation();
	if( notation.standard() ) n.setStandard();
	else n.setScientific();
	if( notation.complex() ) n.setComplex();
	else n.setNonComplex();
	if( notation.rectangular() ) n.setRectangular();
	else n.setPolar();

	if( n.standard() ){
	    result = tryHTMLString( maxChars, precision, base, n ).stringVector;
	    if( result != null ) return result;
	}
	// force scientific notation
	n.setScientific();
	
	result = tryHTMLString( maxChars, precision, base, n ).stringVector;
	if( result != null ) return result;
	else {
	    result = new jscicalc.StringArray();
	    String[] overflow = { "O", "v", "e", "r", "f", "l", "o", "w" };
	    result.add( overflow );
	    return result;
	}
    }

    public jscicalc.StringArray
	toHTMLParenString( int maxChars, int precision, final Base base,
			   final Notation notation, double polarFactor ){ 
	// set up vector
	jscicalc.StringArray v = new jscicalc.StringArray();
	/* Show NaNs as Error*/
	if( Double.isNaN( real() ) || Double.isNaN( imaginary() ) ){
	    String[] error = { "E", "r", "r", "o", "r" };
	    v.add( error );
	    return v;
	}
	    
	/* weak sanity checks */
	if( maxChars < 4 )
	    throw new RuntimeException( "Complex.toHTMLString" +
						  "maxChars must be at least four" );
	if( precision < 1 )
	    throw new RuntimeException( "Complex.toHTMLString" +
						  "precision must be positive" );

	Notation n = new Notation();
	if( notation.standard() ) n.setStandard();
	else n.setScientific();
	if( notation.complex() ) n.setComplex();
	else n.setNonComplex();
	if( notation.rectangular() ) n.setRectangular();
	else n.setPolar();

	HTMLStringResult result;
	if( n.standard() ){
	    result = tryHTMLString( maxChars, precision, base, n );
	    if( result.stringVector != null ){
		if( result.parentheses ){
		    v.add( "(" );
		    v.addAll( result.stringVector );
		    v.add( ")" );
		    return v;
		} else
		    return result.stringVector;
	    }
	}
	// force scientific notation
	n.setScientific();
	
	result = tryHTMLString( maxChars, precision, base, n );
	if( result.stringVector != null ){
	    if( result.parentheses ){
		v.add( "(" );
		v.addAll( result.stringVector );
		v.add( ")" );
		return v;
	    } else
		return result.stringVector;
	} else {
	    String[] overflow = { "O", "v", "e", "r", "f", "l", "o", "w" };
	    v.add( overflow );
	    return v;
	}
    }

    /**
     * internal class used for result of tryHTMLString
     */
    private class HTMLStringResult {
	/**
	 * Constructor. Sets String to null and parentheses to false.
	 */
	HTMLStringResult(){
	    stringVector = null;
	    parentheses = false;
	}
	/**
	 * Store the string.
	 */
	public jscicalc.StringArray stringVector;
	/**
	 * Store the boolean that tells us whether or not parentheses should be
	 * placed around the value.
	 */
	public Boolean parentheses;
	    
    }

    /**
     * Internal function to create an HTML string representation of the complex number.
     * This has five arguments. First, we need to know the maximum number of characters
     * available and the desired precision of the complex numbers. These are given
     * as two arguments.
     * Then we need to know the base
     * Then we need to know whether the Complex number is to be represented in
     * standard or scientific notation. These give the last two arguments.
     * Finally we need to know if we must use complex notation.
     * If not we can ignore small imaginary parts (and zero real part).
     * The fifth parameter is returned as TRUE or FALSE according as the
     * representation would or would not need parentheses if expressed as part
     * of a product.
     *
     * @param maxChars The maximum number of (visible) characters we can use
     * @param precision The desired number of significant figures
     * @param base The required base
     * @param notation  The notation to use to display this value
     * @return a String of at most maxChars HTML characters representing the complex
     * number
     */
    private HTMLStringResult tryHTMLString( final int maxChars, final int precision,
					    final Base base, final Notation notation ){
	// set up
	HTMLStringResult result = new HTMLStringResult();
	DoubleFormat x;
	DoubleFormat y;
	Notation xn = new Notation();
	Notation yn = new Notation();
	if( notation.rectangular() ){
	    x = new DoubleFormat( real(), base );
	    y = new DoubleFormat( imaginary(), base );
	    if( notation.scientific() ){
		xn.setScientific();
		yn.setScientific();
	    }
	} else { // polar
	    x = new DoubleFormat( abs(), base );
	    y = new DoubleFormat( arg(), base );
	    if( notation.scientific() ){
		xn.setScientific();
	    }
	}
	x.setNotation( xn );
	y.setNotation( yn );
	int a = precision; // precision for x
	int b = precision; // precision for y
	// main loop
	while( b > 0 ){
	    // set up representations for x and y
	    DoubleFormat.HTMLStringRepresentation xr = x.NullRepresentation;
	    DoubleFormat.HTMLStringRepresentation yr = y.NullRepresentation;
	    // change y if necessary
	    if( notation.complex() || y.getAbsNumber() > smallImaginary ){
		y.setPrecision( b );
		yr = y.representation();
	    }
	    // change x if necessary
	    if( yr == y.NullRepresentation || notation.complex()
		|| x.getAbsNumber() != (notation.rectangular() ? 0 : 1) 
		|| notation.polar() && notation.scientific() ){
		x.setPrecision( a );
		xr = x.representation();
	    }
	    // Find length
	    int length = xr.length + yr.length;
	    // Maybe add to length or subtract from it
	    if( notation.rectangular() ){
		if( xr != x.NullRepresentation && yr != y.NullRepresentation
		    && imaginary() >= 0 ) ++length; // need a plus sign
		if( yr != y.NullRepresentation ) ++length; // need an i sign
		if( yr.isOne() || yr.isMinusOne() )
		    --length; // don't show 1
	    } else { // polar
		if( yr != y.NullRepresentation ){
		    ++length; // need an e sign
		    ++length; // need an i sign
		}
		if( yr.isOne() || yr.isMinusOne() )
		    --length; // don't show 1
	    }
	    // Check length
	    if( length <= maxChars ){ // we can format it
		jscicalc.StringArray stringBuffer = new jscicalc.StringArray();
		stringBuffer.addAll( xr.string ); // x part 
		if( notation.rectangular() ){
		    if( xr != x.NullRepresentation && yr != y.NullRepresentation ){
			result.parentheses = true;
			if( imaginary() >= 0 ){
			    stringBuffer.add( DoubleFormat.plus );
			}
		    }
		} else { // polar
		    if( yr != y.NullRepresentation ){
			stringBuffer.add( DoubleFormat.argumentPrefix );
			stringBuffer.lastElement()
			    .setElementAt( stringBuffer.lastElement().lastElement()
					   .concat( DoubleFormat.startExponent ),
					   stringBuffer.lastElement().size() - 1 );
			//stringBuffer.add( DoubleFormat.startExponent );
		    }
		}
		if( yr.string.equals( "1" ) )
		    ; // do nothing
		else if( yr.isMinusOne() )
		    stringBuffer.add( DoubleFormat.minus ); 
		else
		    stringBuffer.addAll( yr.string ); // y part
		if( notation.rectangular() ){
		    if( yr != y.NullRepresentation )
			stringBuffer.add( DoubleFormat.imPrefix );
		} else { // polar
		    if( yr != y.NullRepresentation ){
			stringBuffer.add( DoubleFormat.imPrefix );
			stringBuffer.lastElement()
			    .setElementAt( stringBuffer.lastElement().lastElement()
					   .concat( DoubleFormat.endExponent ),
					   stringBuffer.lastElement().size() - 1 );
			//stringBuffer.append( DoubleFormat.endExponent );
		    }
		}
		result.stringVector = new jscicalc.StringArray();
		result.stringVector.addAll( stringBuffer );
		return result;
	    }
	    // loop
	    if( a == b )
		--b;
	    else
		--a;
	}
	return result;
    }

    /**
     * Used by tryHTMLString. Finds the smaller of x and y
     * @param x The first operand
     * @param y The second operand
     * @return The smaller of x and y
     */
    private static int min( int x, int y ){
	return x > y ? y : x;
    }
    
    /**
     * The comparison operator.
     * @param complex The Complex to be compared.
     * @return integer indicating how expression is compared to this.
     */
    public int compareTo( Complex complex ){
	double a = abs();
	double b = complex.abs();
	if( a < b ) return -1;
	if( a > b ) return +1;
	a = arg();
	b = complex.arg();
	if( a > b ) return -1;
	if( a < b ) return +1;
	return 0;
    }

    /**
     * Test code.
     */
    public static void main( String args[] ){
	Complex a = new Complex( 243, 0 );
	Complex z = a.root( 37 );
	System.out.print( z.real() );
	System.out.print( "+" );
	System.out.print( z.imaginary() );
	System.out.println( "i" );
    }	    

    /**
     * The real part of the complex number.
     */
    private final double real_part;
    
    /**
     * The imaginary part of the complex number.
     */
    private final double imaginary_part;

    /**
     * Used for cube roots
     */
    private static final Complex THIRD = new Complex( 1 / (double)3 );
    
    /**
     * Used for tenx.
     */
    private static final Complex LOG10 = new Complex( StrictMath.log( 10 ) );

    /**
     * i
     */
    public static final Complex I = new Complex( 0, 1 );
    /**
     * A calculation (e.g. sin) might create a small imaginary part. If it is smaller
     * than this number in size AND we want to ignore small complex parts, ignore it.
     */
    public static final double smallImaginary = 1e-10;
}
