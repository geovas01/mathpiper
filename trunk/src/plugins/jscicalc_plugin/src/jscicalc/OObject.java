/** @file
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

package jscicalc;
import jscicalc.complex.Complex;
import jscicalc.complex.DoubleFormat;
import jscicalc.expression.Sum;
import jscicalc.expression.Product;
import jscicalc.expression.Expression;
import jscicalc.expression.Variable;

/**
 * This class represents any output token that can be handled by Parser
 *
 * @see Parser, GObject
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.6 $
 */
public abstract class OObject extends GObject {
    /**
     * Indicate whether or not expression is negative. The default is not negative.
     */
    public boolean isNegative(){
	return false;
    }

    /**
     * Generic function of one parameter
     * @param x The value
     * @return An error unless subclass defines something
     */
    public Error function( OObject x ){
	return new Error( "OObject function( x ) error" );
    }

    /**
     * Generic function of two parameters
     * @param x The first parameter
     * @param y The second parameter
     * @return An error unless subclass defines something
     */
    public Error function( OObject x, OObject y ){
	return new Error( "OObject function( x ) error" );
    }

    /**
     * Calculate the inverse.
     * @return The result of the operation
     */
    public OObject inverse(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Power( (Expression)this, new Complex( -1 ) );
	}
	return new Error( "OObject inverse( x ) error" );
    }

    /**
     * Calculate the square.
     * @return The result of the operation
     */
    public OObject square(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Power( (Expression)this, new Complex( 2 ) );
	}
	return new Error( "OObject square( x ) error" );
    }

    /**
     * Calculate the cube.
     * @return The result of the operation
     */
    public OObject cube(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Power( (Expression)this, new Complex( 3 ) );
	}
	return new Error( "OObject cube( x ) error" );
    }

    /**
     * Calculate the factorial.
     * @return The result of the operation
     */
    public OObject factorial(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Factorial( (Expression)this );
	}
	return new Error( "OObject factorial( x ) error" );
    }

    /**
     * Negate x
     * @return The negative of x
     */
    public OObject negate(){
	return new Error( "OObject negate( x ) error" );
    }

    /**
     * Add an OObject to this.
     * @param x The addend
     * @return The sum of this and x.
     */
    public OObject add( OObject x ){
	if( (this instanceof Error) || (x instanceof Error) )
	    return new Error( "OObject add( x ) error" );
	else if( this instanceof Complex ){
	    if( x instanceof Complex ){
		return ((Complex)this).add( (Complex)x );
	    } else if( x instanceof Variable ){
		Sum s = new Sum( (Variable)x );
		return s.add( (Complex)this );
	    } else if( x instanceof Sum ){
		return ((Sum)x).add( (Complex)this );
	    } else if( x instanceof Product ){
		Sum s = new Sum( (Product)x );
		return s.add( (Complex)this );
	    } else if( x instanceof Expression ){
		Sum s = new Sum( (Expression)x );
		return s.add( (Complex)this );
	    }
	} else if( this instanceof Variable ){
	    if( x instanceof Complex ){
		Sum s = new Sum( (Variable)this );
		return s.add( (Complex)x );
	    } else if( x instanceof Variable ){
		Sum s = new Sum( (Variable)this );
		return s.add( x );
	    } else if( x instanceof Sum ){
		Sum s = new Sum( (Variable)this );
		return s.add( (Sum)x );
	    } else if( x instanceof Product ){
		Sum s = new Sum( (Variable)this );
		return s.add( (Product)x );
	    } else if( x instanceof Expression ){
		Sum s = new Sum( (Variable)this );
		return s.add( new Product( (Expression)x, false ) );
	    }
	} else if( this instanceof Sum ){
	    if( x instanceof Complex ){
		return ((Sum)this).add( (Complex)x );
	    } else if( x instanceof Variable ){
		Product s = new Product( (Variable)x, false );
		return ((Sum)this).add( s );
	    } else if( x instanceof Product ){
		return ((Sum)this).add( (Product)x );
	    } else if( x instanceof Sum ){
		return ((Sum)this).add( (Sum)x );
	    } else if( x instanceof Expression ){
		return ((Sum)this).add( new Product( (Expression)x, false ) );
	    }
	} else if( this instanceof Product ){
	    if( x instanceof Complex ){
		Sum s = new Sum( (Product)this );
		return s.add( (Complex)x );
	    } else if( x instanceof Variable ){
		Sum s = new Sum( (Product)this );
		Product p = new Product( (Variable)x, false );
		return s.add( p );
	    } else if( x instanceof Sum ){
		Sum s = new Sum( (Product)this );
		return s.add( (Sum)x );
	    } else if( x instanceof Product ){
		Sum s = new Sum( (Product)this );
		return s.add( (Product)x );
	    } else if( x instanceof Expression ){
		Sum s = new Sum( (Product)this );
		return s.add( new Product( (Expression)x, false ) );
	    }
	} else if( this instanceof Expression ){
	    Sum s = new Sum( new Product( (Expression)this, false ) );
	    if( x instanceof Complex ){
		return s.add( (Complex)x );
	    } else if( x instanceof Sum ){
		return s.add( (Sum)x );
	    } else if( x instanceof Product ){
		return s.add( (Product)x );
	    } else if( x instanceof Expression ){
		return s.add( new Product( (Expression)x, false ) );
	    }
	}
	// default
	return new Error( "OObject add( x ) error" );
    }

    /**
     * Subtract an OObject from this.
     * @param x The subtrahend
     * @return This minus x.
     */
    public OObject subtract( OObject x ){
	if( (this instanceof Error) || (x instanceof Error) )
	    return new Error( "OObject multiply( x ) error" );
	else if( (this instanceof Complex) && (x instanceof Complex) ){
	    return ((Complex)this).subtract( (Complex)x );
	} else if( x instanceof Complex ){
	    return add( ((Complex)x).negate() );
	} else if( x instanceof Expression ){
	    return add( ((Expression)x).negate() );
	}
	return new Error( "OObject subtract( x ) error" );
    }

    /**
     * Multiply this by an OObject.
     * @param x The multiplier.
     * @return The product of this and x.
     */
    public OObject multiply( OObject x ){
	if( (this instanceof Error) || (x instanceof Error) )
	    return new Error( "OObject multiply( x ) error" );
	else if( this instanceof Complex ){
	    if( x instanceof Complex ){
		return ((Complex)this).multiply( (Complex)x );
	    } else if( x instanceof Variable ){
		Product p = new Product( (Variable)x, false );
		return p.multiply( (Complex)this );
	    } else if( x instanceof Sum ){
		Product p = new Product( (Expression)x, false );
		return p.multiply( this );
	    } else if( x instanceof Product ){
		Product p = (Product)x;
		return p.multiply( (Complex)this );
	    } else if( x instanceof Expression ){
		Product p = new Product( (Expression)x, false );
		return p.multiply( (Complex)this );
	    }
	} else if( this instanceof Variable ){
	    if( x instanceof Complex ){
		Product p = new Product( (Variable)this, false );
		return p.multiply( (Complex)x );
	    } else if( x instanceof Variable ){
		Product p = new Product( (Variable)this, false );
		Product q = new Product( (Variable)x, false );
		return p.multiply( q );
	    } else if( x instanceof Sum ){
		Product p = new Product( (Variable)this, false );
		return p.multiply( (Expression)this );
	    } else if( x instanceof Product ){
		Product p = new Product( (Variable)this, false );
		return p.multiply( (Product)x );
	    } else if( x instanceof Expression ){
		Product p = new Product( (Variable)this, false );
		return p.multiply( (Expression)x );
	    }
	} else if( this instanceof Sum ){
	    if( x instanceof Complex ){
		Product p = new Product( (Expression)this, false );
		return p.multiply( (Complex)x );
	    } else if( x instanceof Expression ){
		Product p = new Product( (Expression)x, false );
		return p.multiply( (Expression)this );
	    }
	} else if( this instanceof Product ){
	    if( x instanceof Complex ){
		Product p = (Product)this;
		return p.multiply( (Complex)x );
	    } else if( x instanceof Product ){
		return ((Product)this).multiply( (Product)x );
	    } else if( x instanceof Expression ){
		return ((Product)this).multiply( new Product( (Expression)x, false ) );
	    }
	} else if( this instanceof Expression ){
	    if( x instanceof Complex ){
		Product p = new Product( (Expression)this, false );
		return p.multiply( (Complex)x );
	    } else if( x instanceof Product ){
		return (new Product( (Expression)this, false )).multiply( (Product)x );
	    } else if( x instanceof Expression ){
		Product p = new Product( (Expression)this, false );
		return p.multiply( (Expression)x );
	    }
	}
	return new Error( "OObject multiply( x ) error" );
    }

    /**
     * Divide this by an OObject.
     * @param x The divisor.
     * @return The result of this divided by x
     */
    public OObject divide( OObject x ){
	if( (this instanceof Error) || (x instanceof Error) )
	    return new Error( "OObject multiply( x ) error" );
	else if( this instanceof Complex ){
	    if( x instanceof Complex ){
		return ((Complex)this).divide( (Complex)x );
	    } else if( x instanceof Variable ){
		Product p = new Product( (Variable)x, true );
		return p.multiply( (Complex)this );
	    } else if( x instanceof Sum ){
		Product p = new Product( (Expression)x, true );
		return p.multiply( this );
	    } else if( x instanceof Product ){
		Product p = new Product( (Expression)x, true );
		return p.multiply( (Complex)this );
	    } else if( x instanceof Expression ){
		Product p = new Product( (Expression)x, true );
		return p.multiply( (Complex)this );
	    }
	} else if( this instanceof Variable ){
	    if( x instanceof Complex ){
		Product p = new Product( (Variable)this, false );
		return p.divide( (Complex)x );
	    } else if( x instanceof Variable ){
		Product p = new Product( (Variable)this, false );
		Product q = new Product( (Variable)x, true );
		return p.multiply( q );
	    } else if( x instanceof Sum ){
		Product p = new Product( (Variable)this, false );
		Product q = new Product( (Sum)x, true );
		return p.multiply( q );
	    } else if( x instanceof Product ){
		Product p = new Product( (Variable)this, false );
		return p.divide( (Product)x );
	    } else if( x instanceof Expression ){
		Product p = new Product( (Variable)this, false );
		Product q = new Product( (Expression)x, true );
		return p.multiply( q );
	    }
	} else if( this instanceof Sum ){
	    if( x instanceof Complex ){
		Product p = new Product( (Expression)this, false );
		return p.multiply( (Complex)x );
	    } else if( x instanceof Expression ){
		Product p = new Product( (Expression)x, false );
		Product q = new Product( (Expression)x, true );
		return p.multiply( q );
	    }
	} else if( this instanceof Product ){
	    if( x instanceof Complex ){
		Product p = (Product)this;
		return p.divide( (Complex)x );
	    } else if( x instanceof Product ){
		return ((Product)this).divide( (Product)x );
	    } else if( x instanceof Expression ){
		Product q = new Product( (Expression)x, true );
		return ((Product)this).multiply( q );
	    }
	} else if( this instanceof Expression ){
	    if( x instanceof Complex ){
		Product p = new Product( (Expression)this, false );
		return p.divide( (Complex)x );
	    } else if( x instanceof Product ){
		Product p = new Product( (Expression)this, false );
		return p.divide( (Product)x );
	    } else if( x instanceof Expression ){
		Product p = new Product( (Expression)this, false );
		Product q = new Product( (Expression)x, true );
		return p.multiply( q );
	    }
	}
	return new Error( "OObject divide( x ) error" );
    }

    /**
     * Exponential function.
     * @return The exponential function applied to x
     */
    public OObject exp(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Exp( (Expression)this );
	}
	return new Error( "OObject exp() error" );
    }

    /**
     * Combination function applied to x and this.
     * @param x The value (right of symbol)
     * @return The result of this choose x
     */
    public OObject combination( OObject x ){
	if( (this instanceof Complex) && (x instanceof Complex) ) {
	    return ((Complex)this).combination( (Complex)x );
	} else if( !((this instanceof Error) || (x instanceof Error)) ){
	    return new jscicalc.expression.Combination( this, x );
	}
	return new Error( "OObject combination( x ) error" );
    }

    /**
     * Permutation function applied to x and this.
     * @param x The value (right of symbol)
     * @return The result of this P x.
     */
    public OObject permutation( OObject x ){
	if( (this instanceof Complex) && (x instanceof Complex) ) {
	    return ((Complex)this).permutation( (Complex)x );
	} else if( !((this instanceof Error) || (x instanceof Error)) ){
	    return new jscicalc.expression.Permutation( this, x );
	}
	return new Error( "OObject permutation( x ) error" );
    }

    /**
     * Power function applied to x
     * @param y The value (right of symbol)
     * @return The result of this to the power of y
     */
    public OObject pow( OObject y ){
	if( (this instanceof Complex) && (y instanceof Complex) ) {
	    return ((Complex)this).pow( (Complex)y );
	} else if( !((this instanceof Error) || (y instanceof Error)) ){
	    return new jscicalc.expression.Power( this, y );
	}
	return new Error( "OObject pow( y ) error" );
    }
    
    /**
     * Root function applied to y
     * @param y The value (right of symbol)
     * @return The result of this to the power of 1/y
     */
    public OObject root( OObject y ){
	if( (this instanceof Complex) && (y instanceof Complex) ) {
	    return ((Complex)this).root( (Complex)y );
	} else if( !(this instanceof Error) ){
	    if( y instanceof Complex )
		return new jscicalc.expression.Power( this, ((Complex)y).inverse() );
	    else if( y instanceof Expression ){
		Product p = new Product( (Expression)y, true ); // inverse
		return new jscicalc.expression.Power( this, p );
	    }
	}
	return new Error( "OObject root( y ) error" );
    } 

    /**
     * Calculate the sine.
     * @param angleType The AngleType
     * @return The result of the operation
     */
    public OObject sin( AngleType angleType ){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Sin( (Expression)this, angleType );
	}
	return new Error( "OObject sin( x ) error" );
    }

    /**
     * Calculate the inverse sine.
     * @param angleType The AngleType
     * @return The result of the operation
     */
    public OObject asin( AngleType angleType ){
	if( this instanceof Expression ){
	    return new jscicalc.expression.ASin( (Expression)this, angleType );
	}
	return new Error( "OObject asin( x ) error" );
    }

    /**
     * Calculate the cosine.
     * @param angleType The AngleType
     * @return The result of the operation
     */
    public OObject cos( AngleType angleType ){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Cos( (Expression)this, angleType );
	}
	return new Error( "OObject cos( x ) error" );
    }

    /**
     * Calculate the inverse cosine.
     * @param angleType The AngleType
     * @return The result of the operation
     */
    public OObject acos( AngleType angleType ){
	if( this instanceof Expression ){
	    return new jscicalc.expression.ACos( (Expression)this, angleType );
	}
	return new Error( "OObject acos( x ) error" );
    }

    /**
     * Calculate the tangent.
     * @param angleType The AngleType
     * @return The result of the operation
     */
    public OObject tan( AngleType angleType ){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Tan( (Expression)this, angleType );
	}
	return new Error( "OObject tan( x ) error" );
    }

    /**
     * Calculate the inverse tangent.
     * @param angleType The AngleType
     * @return The result of the operation
     */
    public OObject atan( AngleType angleType ){
	if( this instanceof Expression ){
	    return new jscicalc.expression.ATan( (Expression)this, angleType );
	}
	return new Error( "OObject atan( x ) error" );
    }

    /**
     * Calculate the log (base 10).
     * @return The result of the operation
     */
    public OObject log10(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Log( (Expression)this );
	}
	return new Error( "OObject log10( x ) error" );
    }

    /**
     * Calculate the natural logarithm.
     * @return The result of the operation
     */
    public OObject log(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Ln( (Expression)this );
	}
	return new Error( "OObject log( x ) error" );
    }

    /**
     * Calculate the square root.
     * @return The result of the operation
     */
    public OObject sqrt(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Power( (Expression)this, new Complex( 0.5 ) );
	}
	return new Error( "OObject sqrt( x ) error" );
    }

    /**
     * Calculate the cube root.
     * @return The result of the operation
     */
    public OObject cuberoot(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Power( (Expression)this,
						  new Complex( (double)1 / 3 ) );
	}
	return new Error( "OObject cuberoot( x ) error" );
    }

    /**
     * Calculate ten to the power of x.
     * @return The result of the operation
     */
    public OObject tenx(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Power( new Complex( 10 ), (Expression)this );
	}
	return new Error( "OObject tenx( x ) error" );
    }

    /**
     * Calculate the conjugate.
     * @return The result of the operation
     */
    public OObject conjugate(){
	if( this instanceof Expression ){
	    return new jscicalc.expression.Conjugate( (Expression)this );
	}
	return new Error( "OObject tan( x ) error" );
    }
    
    /**
     * And with another OObject.
     * @param z The number to AND with this.
     * @return The bitwise result of a logical and operation on this and z.
     */
    public OObject and( OObject z ){ 
	if( (this instanceof Complex) && (z instanceof Complex) ) {
	    return ((Complex)this).and( (Complex)z );
	} else if( !((this instanceof Error) || (z instanceof Error)) ){
	    return new jscicalc.expression.And( this, z );
	}
	return new Error( "OObject and( z ) error" );
    }

    /**
     * Or with another OObject.
     * @param z The number to AND with this.
     * @return The bitwise result of a logical or operation on this and z.
     */
    public OObject or( OObject z ){ 
	if( (this instanceof Complex) && (z instanceof Complex) ) {
	    return ((Complex)this).or( (Complex)z );
	} else if( !((this instanceof Error) || (z instanceof Error)) ){
	    return new jscicalc.expression.Or( this, z );
	}
	return new Error( "OObject or( z ) error" );
    }

    /**
     * Xor with another OObject.
     * @param z The number to AND with this.
     * @return The bitwise result of a logical xor operation on this and z.
     */
    public OObject xor( OObject z ){ 
	if( (this instanceof Complex) && (z instanceof Complex) ) {
	    return ((Complex)this).xor( (Complex)z );
	} else if( !((this instanceof Error) || (z instanceof Error)) ){
	    return new jscicalc.expression.Xor( this, z );
	}
	return new Error( "OObject xor( z ) error" );
    }

    /**
     * Test for equality to zero. 
     * @return <em>false</em>.
     */
    public boolean isZero(){
	return false;
    }

    /**
     * Function to create an HTML string representation of the OObject.
     * This has six arguments.
     * See Complex for a definition of the arguments.
     * 
     * The default function simply returns &lsquo;Error&rsquo; to indicate that no
     * result is known.
     *
     * @param maxChars The maximum number of (visible) characters we can use
     * @param precision The desired number of significant figures
     * @param base The required base
     * @param notation  The notation to use to display this value
     * @param polarFactor Not used
     * @return a String of at most maxChars HTML characters representing the expression
     */
    public StringArray toHTMLStringVector( int maxChars, int precision, final Base base,
					   final Notation notation, double polarFactor ){ 
	StringArray
	    v = toHTMLSubString( maxChars, precision, base, notation, polarFactor );
	return v;
    }

    /**
     * Function to create an HTML string representation of the OObject.
     * This has six arguments.
     * See Complex for a definition of the arguments.
     * 
     * The default function simply returns &lsquo;Error&rsquo; to indicate that no
     * result is known.
     *
     * The result is really a string. But we want to scroll over it; so we separate
     * (1) HTML tags, (2) special characters like minus signs, so that each substring
     * represents what will appear on the screen as a single character. When we scroll,
     * we also sometimes want to jump across several characters at once. So we use another
     * level of indirection. Thus, at the first level we get the entities between which
     * we can move the caret. And at the second level, we get a representation of each
     * &lsquo;character&rsquo; on the screen (which may correspond to more than one
     * character of HTML code.
     *
     * @param maxChars The maximum number of (visible) characters we can use
     * @param precision The desired number of significant figures
     * @param base The required base
     * @param notation  The notation to use to display this value
     * @param polarFactor Not used
     * @return a String of at most maxChars HTML characters representing the expression
     */
    public String
	toHTMLString( int maxChars, int precision, final Base base,
		      final Notation notation, double polarFactor ){ 
	java.util.Vector<java.util.Vector<String>>
	    v = toHTMLSubString( maxChars, precision, base, notation, polarFactor );
	StringBuilder s = new StringBuilder( DoubleFormat.startHTML ); // <html>
	for( java.util.ListIterator<java.util.Vector<String>>
		 i = v.listIterator(); i.hasNext(); ){
	    for( java.util.ListIterator<String>
		     j = i.next().listIterator(); j.hasNext(); ){
		s.append( j.next() );
	    }
	}
	s.append( DoubleFormat.endHTML ); // </html>
	return s.toString();
    }
    
    /**
     * Function to create an HTML string representation of the expression.
     * This has six arguments.
     * See Complex for a definition of the arguments.
     *
     * The default function simply returns &lsquo;Error&rsquo; to indicate that no
     * result is known.
     *
     * This function does not include the <em>html</em> tags.
     *
     * @param maxChars The maximum number of (visible) characters we can use
     * @param precision The desired number of significant figures
     * @param base The required base
     * @param notation  The notation to use to display this value
     * @param polarFactor Not used
     * @return a String of at most maxChars HTML characters representing the expression
     */
    public StringArray
	toHTMLSubString( int maxChars, int precision, final Base base,
			 final Notation notation, double polarFactor ){ 
	StringArray v = new StringArray();
	String[] error = { "E", "r", "r", "o", "r" };
	v.add( error );
	return v;
    }
    
    /**
     * Function to create an HTML string representation of the expression.
     * This has six arguments.
     * See Complex for a definition of the arguments.
     * 
     * This version may put parentheses round the expression.
     *
     * The default function simply returns &lsquo;Error&rsquo; to indicate that no
     * result is known.
     *
     * This function does not include the <em>html</em> tags.
     *
     * @param maxChars The maximum number of (visible) characters we can use
     * @param precision The desired number of significant figures
     * @param base The required base
     * @param notation  The notation to use to display this value
     * @param polarFactor Not used
     * @return a String of at most maxChars HTML characters representing the expression
     */
    public StringArray toHTMLParenString( int maxChars, int precision, final Base base,
					  final Notation notation, double polarFactor ){ 
	StringArray s = new StringArray();
	s.add( "(" );
	s.addAll( toHTMLSubString( maxChars, precision, base, notation, polarFactor ) ); 
	s.add( ")" );
	return s;
    }

    /**
     * The comparison operator. By default returns 0 (Errors are equivalent).
     * @param o The Expression to be compared.
     * @return integer indicating how expression is compared to this.
     */
    public int compareTo( OObject o ){
	if( this instanceof jscicalc.Error ){
	    if( o instanceof jscicalc.Error ){
		return 0; // errors are equivalent
	    } else {
		return -1;
	    }
	} else if( this instanceof Complex ){
	    if( o instanceof Complex ){
		return ((Complex)this).compareTo( (Complex)o );
	    } else {
		return -1;
	    }
	} else if( this instanceof Expression ){
	    if( !(o instanceof Expression) ){
		return +1;
	    } else {
		return ((Expression)this).compareTo( (Expression)o );
	    }
	} else
	    return 0;
    }

    /**
     * Sort the class. Does nothing, but subclasses will override.
     */
    public void sort(){}

    /**
     * Carry out automatic simplifications on the OObject. The auto_simplification method
     * is loosely based on the method of Joel S Cohen, <em>Computer Algebra and Symbolic
     * Computation</em>, A K Peters, 2003.
     * @return A simplified OObject
     */
    public OObject auto_simplify(){
	return this;
    }

    /**
     * Used internally
     */
    private final static Complex LOG10INV = new Complex( 1 / StrictMath.log( 10 ) );
 
}
