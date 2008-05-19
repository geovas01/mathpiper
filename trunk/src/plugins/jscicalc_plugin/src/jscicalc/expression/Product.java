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

package jscicalc.expression;
import jscicalc.Base;
import jscicalc.Notation;
import jscicalc.OObject;
import jscicalc.complex.Complex;

/**
 * This class represents a product of Expression objects.
 * expression.
 *
 * @see OObject
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public class Product extends SumOrProduct implements Cloneable {
    /**
     * Construct a Product.
     * @param expression The expression from which to construct the Product.
     * @param inverse Set to <em>true</em> or <em>false</em> according as you want
     * expression or 1/expression
     */
    public Product( Expression expression, final boolean inverse ){
	if( expression instanceof Product ){
	    Product product = (Product)expression;
	    if( inverse ){
		complex = new Complex( 1 );
		complex = complex.divide( product.complex );
		expressionList = product.divisorList;
		divisorList = product.expressionList;
	    } else {
		complex = product.complex;
		expressionList = product.expressionList;
		divisorList = product.divisorList;
	    }
	} else {
	    complex = new Complex( 1 ); //one
	    if( inverse ){
		expressionList = new java.util.LinkedList<Expression>();
		divisorList = new java.util.LinkedList<Expression>();
		divisorList.add( expression );
	    } else {
		expressionList = new java.util.LinkedList<Expression>();
		expressionList.add( expression );
		divisorList = new java.util.LinkedList<Expression>();
	    }
	}
    }

    /**
     * Set the Complex value
     * @param z The new value of the complex in the Product.
     */
    void setComplex( Complex z ){
	complex = z;
    }
   

    /**
     * Construct a Product.
     */
    public Product(){
	complex = new Complex(); //zero
	expressionList = new java.util.LinkedList<Expression>();
	divisorList = new java.util.LinkedList<Expression>();
    }
    
    /**
     * Create a copy of this Product.
     * @return a copy of this
     */
    public Product clone(){
	Product copy = new Product();
	copy.complex = new Complex( complex.real(), complex.imaginary() );
	copy.expressionList = new java.util.LinkedList<Expression>();
	for( java.util.ListIterator<Expression>
		 i = getExpressionList().listIterator(); i.hasNext(); ){
	    copy.expressionList.add( i.next() );
	}
	copy.divisorList = new java.util.LinkedList<Expression>();
	for( java.util.ListIterator<Expression>
		 i = divisorList.listIterator(); i.hasNext(); ){
	    copy.divisorList.add( i.next() );
	}
	return copy;
    }

    /**
     * Multiply a Complex by this.
     * @param z The complex to multiply by
     * @return The product
     */
    public OObject multiply( Complex z ){
	Product product = clone();
	product.complex = complex.multiply( z );
	return product;
    }

    /**
     * Divide this by a Complex.
     * @param z The complex to divide by
     * @return The product
     */
    public OObject divide( Complex z ){
	Product product = clone();
	product.complex = complex.divide( z );
	return product;
    }

    /**
     * Get the negative of this.
     * @return The negative of the complex number.
     */
    public Product negate(){
	Product p = clone();
	p.complex = complex.negate();
	return p;
    }

    /**
     * multiply this by a product
     *
     * @param x The product to multiply by.
     * @return The product of this and x.
     */
    public OObject multiply( Product x ){
	System.out.println( "Multiplying two products" );
	Product s = clone();
	// Multiply complex
	s.complex = s.complex.multiply( x.complex );
	for( java.util.ListIterator<Expression> i = x.expressionList.listIterator();
	     i.hasNext(); ){
	    s.expressionList.add( i.next() );
	}
	for( java.util.ListIterator<Expression> i = x.divisorList.listIterator();
	     i.hasNext(); ){
	    s.divisorList.add( i.next() );
	}
	return s;
    }

    /**
     * Divide this by a product
     *
     * @param x The product to divide by.
     * @return The quotient of this and x.
     */
    public OObject divide( Product x ){
	System.out.println( "Dividing one product by another" );
	Product s = clone();
	// Multiply complex
	s.complex = s.complex.divide( x.complex );
	for( java.util.ListIterator<Expression> i = x.divisorList.listIterator();
	     i.hasNext(); ){
	    s.expressionList.add( i.next() );
	}
	for( java.util.ListIterator<Expression> i = x.expressionList.listIterator();
	     i.hasNext(); ){
	    s.divisorList.add( i.next() );
	}
	return s;
    }

    public boolean isNegative(){
	if( complex.isZero() ) return false;
	boolean negative = complex.isNegative();
	for( java.util.ListIterator<Expression> i = expressionList.listIterator();
	     i.hasNext(); ){
	    Expression e = i.next();
	    if( e.isZero() ) return false;
	    boolean enegative = e.isNegative();
	    negative = negative ? !enegative : enegative;
	}
	return negative;
    }

    /**
     * Return either this or the first expression in this if this is just a container
     * for a single object.
     * @return simplified expression
     */
    public OObject unBox(){
	if( divisorList.isEmpty() && expressionList.size() == 1 &&
	    complex.subtract( new Complex( 1 ) ).isZero() ){
	    return expressionList.getFirst();
	} else if( divisorList.isEmpty() && expressionList.isEmpty() ){
	    return complex;
	} else {
	    return this;
	}
    }
    
    /**
     * The comparison operator.
     * @param product The Product to be compared.
     * @return integer indicating how expression is compared to this.
     */
    public int compareTo( Product product ){
	// ignore complex
	sort(); // needs to be in order
	int r = compare( expressionList, product.expressionList );
	if( r != 0 )
	    return r;
	else
	    return compare( divisorList, product.divisorList );
    }

    
    /**
     * Function to create an HTML string representation of the Product.
     * The arguments are ignored except for the Complex.
     *
     * @param maxChars ignored
     * @param precision ignored
     * @param base ignored
     * @param notation ignored
     * @param polarFactor ignored
     * @return a String representing the variable.
     */    
    public jscicalc.StringArray
	toHTMLSubString( int maxChars, int precision, final Base base,
			 final Notation notation, double polarFactor ){ 
	jscicalc.StringArray s = new jscicalc.StringArray();
	jscicalc.StringArray
	    t = complex.toHTMLParenString( maxChars, precision, base, notation,
					   polarFactor );
	boolean unity = false;
	if( !expressionList.isEmpty() ){
	    if( t.isMinusOne() ){
		s.add( jscicalc.complex.DoubleFormat.minus.firstElement() );
	    } else if( t.isOne() ){
		unity = true;
		// do nothing
	    } else {
		s.addAll( t );
	    }
	} else {
	    s.addAll( t );
	}
	// now expression
	if( unity == true && expressionList.size() == 1 && divisorList.isEmpty() ){
	    Expression expression = expressionList.getFirst();
	    return expression.toHTMLSubString( maxChars, precision, base, notation,
					       polarFactor );
	}
	for( java.util.ListIterator<Expression> i = expressionList.listIterator();
	     i.hasNext(); ){
	    Expression expression = i.next();
	    if( expression instanceof Power )
		s.addAll( expression.toHTMLSubString( maxChars, precision, base,
						      notation, polarFactor ) );
	    else if( expression != null )
		s.addAll( expression.toHTMLParenString( maxChars, precision, base,
							notation, polarFactor ) );
	}
	// now divisors
	if( !divisorList.isEmpty() ){
	    s.add( "&#247;" ); 
	    if( divisorList.size() == 1 ){
		s.addAll( divisorList.getFirst()
			  .toHTMLParenString( maxChars, precision, base, notation,
					      polarFactor ) );
	    } else {
		s.add( "(" );
		for( java.util.ListIterator<Expression> i = divisorList.listIterator();
		     i.hasNext(); ){
		    Expression expression = i.next();
		    s.addAll( expression.toHTMLParenString( maxChars, precision, base,
							    notation, polarFactor ) );
		}
		s.add( ")" );
	    }
	}
	return s;
    }
	    
    public jscicalc.StringArray
	toHTMLParenString( int maxChars, int precision, final Base base,
			   final Notation notation, double polarFactor ){ 
	jscicalc.StringArray
	    t = complex.toHTMLParenString( maxChars, precision, base, notation,
					   polarFactor );
	if( getExpressionList().isEmpty() && divisorList.isEmpty() )
	    return t; // only Complex
	else if( getExpressionList().size() == 1 && divisorList.isEmpty()
		 && t.isOne() )
	    // only one expression
	    return getExpressionList().getFirst()
		.toHTMLParenString( maxChars, precision, base, notation,
				    polarFactor );
	else if( getExpressionList().size() == 1 && divisorList.isEmpty()
		 && t.isMinusOne() ){
	    // -(expression)
	    jscicalc.StringArray s = new jscicalc.StringArray();
	    s.add( jscicalc.complex.DoubleFormat.minus.firstElement() );
	    s.addAll( getExpressionList().getFirst()
		      .toHTMLParenString( maxChars, precision, base, notation,
					  polarFactor ) );
	    return s;
	} else {
	    jscicalc.StringArray s = new jscicalc.StringArray();
	    s.add( "(" );
	    s.addAll( toHTMLSubString( maxChars, precision, base, notation,
				       polarFactor ) );
	    s.add( ")" );
	    return s;
	}
    }

    /**
     * Function to create an HTML string representation of the expression.
     * This has six arguments.
     * See Complex for a definition of the arguments.
     * 
     * This version may put parentheses round the expression.
     *
     * This version of the function is designed to be used with LFunctions like square
     * and cube, where, for example the square of minus x has to use parentheses (as
     * opposed to cos, where it might not need them.
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
    public jscicalc.StringArray
	toHTMLParenStringL( int maxChars, int precision, final Base base,
			    final Notation notation, double polarFactor ){ 
	jscicalc.StringArray
	    t = complex.toHTMLParenString( maxChars, precision, base, notation,
					   polarFactor );
	if( expressionList.isEmpty() && divisorList.isEmpty() ){
	    return t;
	} else if( expressionList.size() == 1 && divisorList.isEmpty() ){
	    if( t.isOne() ){
		return expressionList.getFirst()
		    .toHTMLParenString(maxChars, precision, base, notation, polarFactor );
	    } else if( t.isMinusOne() ){
		jscicalc.StringArray u = expressionList.getFirst()
		    .toHTMLParenString( maxChars, precision, base, notation,
					polarFactor );
		if( u.size() > 0 && !u.firstElement().firstElement().equals( "(" ) ){
		    jscicalc.StringArray v = new jscicalc.StringArray();
		    v.add( jscicalc.complex.DoubleFormat.minus.firstElement() );
		    v.addAll( u );
		    return v;
		}
	    }
	}
	jscicalc.StringArray s = new jscicalc.StringArray();
	s.add( "(" );
	s.addAll( toHTMLSubString( maxChars, precision, base, notation, polarFactor ) ); 
	s.add( ")" );
	return s;
    }

    /**
     * Compute an integer power. This just means taking the integer power of each
     * expression and creating a new Product. Note that this will not work for
     * non-integer powers.
     * @param n The integer (actually a long)
     * @return An integer power
     */
    OObject integer_power( long n ){
	Complex z = new Complex( n );
	Product result = new Product();
	result.complex = complex.pow( z );
	for( java.util.ListIterator<Expression> i = expressionList.listIterator();
	     i.hasNext(); ){
	    Expression e = i.next();
	    result.expressionList.add( new Power( e, z ) );
	}
	for( java.util.ListIterator<Expression> i = divisorList.listIterator();
	     i.hasNext(); ){
	    Expression e = i.next();
	    result.divisorList.add( new Power( e, z ) );
	}
	return result;
    }

    /**
     * Sort the class.
     */
    public void sort(){
	super.sort();
	for( Expression e : divisorList )
	    e.sort();
	java.util.Collections.sort( divisorList );
    }

    /**
     * Simplify a Product expression. Returns Error or Complex or jscicalc.Product.
     * @return A simpler OObject equivalent to this.
     */
    public OObject auto_simplify(){
	// first remove quotients
	for( java.util.ListIterator<Expression> i = divisorList.listIterator();
	     i.hasNext(); ){
	    Expression e = i.next();
	    OObject o = new Power( e, new Complex( -1 ) ).auto_simplify();
	    if( o instanceof Complex ){
		complex = complex.divide( (Complex)o );
	    } else if( o instanceof Expression ){
		expressionList.add( (Expression)o );
	    } else {
		return new jscicalc.Error( "Product.auto_simplify() error" );
	    }
	}
	divisorList.clear();
	// simplify remaining expressions
	for( java.util.ListIterator<Expression> i = expressionList.listIterator();
	     i.hasNext(); ){
	    OObject o = i.next().auto_simplify();
	    if( o instanceof Complex ){
		complex = complex.multiply( (Complex)o );
		i.remove();
	    } else if( o instanceof Expression ){
		i.set( (Expression)o );
	    } else {
		return new jscicalc.Error( "Product.auto_simplify() error" );
	    }
	}
	sort();
	if( expressionList.isEmpty() ) return unBox(); // should be a Complex
	// now work through expressions in product
	java.util.ListIterator<Expression> i = expressionList.listIterator();
	Expression f = null; // initialise for loop
	for( Expression e = i.next(); i.hasNext(); e = f ){
	    OObject base_e = e;
	    OObject exponent_e = new Complex( 1 );
	    if( e instanceof Power ){
		base_e = ((Power)e).base();
		exponent_e = ((Power)e).exponent();
	    }
	    f = i.next();
	    OObject base_f = f;
	    OObject exponent_f = new Complex( 1 );
	    if( f instanceof Power ){
		base_f = ((Power)f).base();
		exponent_f = ((Power)f).exponent();
	    }
	    if( base_e.compareTo( base_f ) == 0 ){
		OObject exponent = exponent_e.add( exponent_f );
		if( exponent instanceof Expression )
		    exponent = ((Expression)exponent).auto_simplify();
		OObject expression = new Power( base_e, exponent ).auto_simplify();
		if( expression instanceof Complex ){
		    complex = complex.multiply( (Complex)expression );
		    i.remove(); // remove f
		    i.previous();
		    i.remove(); // remove e
		    if( i.hasNext() ) e = i.next(); // new e
		} else if( expression instanceof Expression ){
		    i.remove(); // remove f
		    i.previous();
		    i.set( (Expression)expression ); // replace e;
		    f = i.next(); // update f
		} else {
		    return new jscicalc.Error( "Product.auto_simplify() error" );
		    
		}
	    }
	}
	return unBox(); // might be nothing left
    }
    
    public static void main( String[] args ){
    }
    /**
     * The divisor Expression objects.
     */
    protected java.util.LinkedList<Expression> divisorList;
}
