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
 * This class represents a sum of Expression objects.
 * expression.
 *
 * @see OObject
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public class Sum extends SumOrProduct implements Cloneable {
    /**
     * Construct a Sum.
     * @param expression The expression from which to construct the Sum.
     */
    public Sum( Expression expression ){
	if( expression instanceof Sum ){
	    Sum sum = (Sum)expression;
	    complex = sum.complex;
	    expressionList = sum.expressionList;
	}
	complex = new Complex(); //zero
	expressionList = new java.util.LinkedList<Expression>();
	expressionList.add( expression );
    }
    
    /**
     * Construct a Sum.
     */
    public Sum(){
	complex = new Complex(); //zero
	expressionList = new java.util.LinkedList<Expression>();
    }

    /**
     * Add a Complex to this.
     * @param z The complex to add
     * @return The sum
     */
    public Sum add( Complex z ){
	Sum sum = clone();
	sum.complex = complex.add( z );
	return sum;
    }

    /**
     * Create a copy of this Sum.
     * @return a copy of this
     */
    public Sum clone(){
	Sum copy = new Sum();
	copy.complex = new Complex( complex.real(), complex.imaginary() );
	copy.expressionList = new java.util.LinkedList<Expression>();
	for( java.util.ListIterator<Expression>
		 i = getExpressionList().listIterator(); i.hasNext(); ){
	    copy.expressionList.add( i.next() );
	}
	return copy;
    }

    public boolean isNegative(){
	if( complex.isZero() ){
	    if( expressionList.isEmpty() ) return false;
	    else return expressionList.getFirst().isNegative();
	} else {
	    return complex.isNegative();
	}
    }
    
    /**
     * Function to create an HTML string representation of the Sum.
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
	boolean displayPlus = false;
	// handle expression
	for( java.util.ListIterator<Expression> i = expressionList.listIterator();
	     i.hasNext(); ){
	    Expression expression = i.next();
	    jscicalc.StringArray
		u = expression.toHTMLSubString( maxChars, precision, base, notation,
						polarFactor );
	    if( displayPlus && (!expression.isNegative() || 
				(u.size() > 0 &&
				 u.firstElement().firstElement().equals( "(" ) ) ) ){
		s.add( "+" );
	    }
	    s.addAll( u );
	    displayPlus = true;
	}
	jscicalc.StringArray
	    t = complex.toHTMLSubString( maxChars, precision, base, notation,
					 polarFactor );
	if( !t.isZero() ){
	    // display complex
	    if( displayPlus && !complex.isNegative() ){
		s.add( "+" );
	    }
	    s.addAll( t );
	}
	return s;
    }

    public jscicalc.StringArray
	toHTMLParenString( int maxChars, int precision, final Base base,
			   final Notation notation, double polarFactor ){ 
	if( getExpressionList().isEmpty() )
	    return complex.toHTMLParenString( maxChars, precision, base, notation,
					      polarFactor );
	else if( complex.isZero() && getExpressionList().size() == 1 )
	    return getExpressionList().getFirst()
		.toHTMLParenString( maxChars, precision, base, notation, polarFactor );
	else {
	    jscicalc.StringArray s = new jscicalc.StringArray();
	    s.add( "(" );
	    s.addAll( toHTMLSubString( maxChars, precision, base, notation,
				       polarFactor ) );
	    s.add( ")" );
	    return s;
	}
    }
    
    /**
     * Add a Product to this.
     *
     * @param x The addend
     * @return The sum of this and x.
     */
    public OObject add( Product x ){
	System.out.println( "Adding product to sum: FIXME" );
	/* Method: clone this.
	 * Go through sum expressions e
	 * If e and x are equivalent, add them and replace e
	 *   If e replacement is zero, do not replace.
	 *   If expressionList is now empty, replace with Complex only.
	 */
	Sum sum = clone();
	System.out.println( "Clone complete: FIXME" );
	// Product was not already in sum 
	OObject o = x.unBox();
	if( o instanceof Complex )
	    sum.complex = sum.complex.add( (Complex)o );
	else if( o instanceof Expression )
	    sum.expressionList.add( (Expression)o );
	else
	    return new jscicalc.Error( "Sum.add() error" );
	return sum;
    }

    /**
     * Add a sum to this.
     *
     * @param x The addend
     * @return The sum of this and x.
     */
    public OObject add( Sum x ){
	Sum s = clone();
	// Add complex
	s.complex = s.complex.add( x.complex );
	OObject sum = (OObject)s;
	for( java.util.ListIterator<Expression> i = x.expressionList.listIterator();
	     i.hasNext(); ){
	    sum = sum.add( i.next() );
	}
	return sum;
    }
    
    /**
     * Get the negative of this.
     * @return The negative of the complex number.
     */
    public Sum negate(){
	Sum p = new Sum();
	p.complex = complex.negate();
	for( java.util.ListIterator<Expression> i = expressionList.listIterator();
	     i.hasNext(); ){
	    p.expressionList.add( i.next().negate() );
	}
	return p;
    }

    /**
     * Right multiply this by o: that is, find this * o.
     * @param o An OObject
     * @result The result of the multiplication
     */
    private OObject rMultiply( OObject o ){
	if( !((o instanceof Complex) || (o instanceof Expression)) )
	    return new jscicalc.Error( "Error in Sum.rMultiply" );
	Sum s = new Sum();
	OObject p = o.multiply( complex );
	if( p instanceof Complex ){
	    s.complex = s.complex.add( (Complex)p );
	} else if( p instanceof Expression ){
	    s.expressionList.add( (Expression)p );
	} else
	    return new jscicalc.Error( "Error in Sum.lMultiply" );
	for( java.util.ListIterator<Expression> i = getExpressionList().listIterator();
	     i.hasNext(); ){
	    p = i.next().multiply( o );
	    if( p instanceof Complex ){
		s.complex = s.complex.add( (Complex)p );
	    } else if( p instanceof Expression ){
		s.expressionList.add( (Expression)p );
	    } else
		return new jscicalc.Error( "Error in Sum.rMultiply" );
	}
	return s;
    }

    /**
     * Multiply this by a Sum t: that is, find this * t.
     * @param t Another Sum
     * @result The result of the multiplication
     */
    OObject multiply( Sum t ){
	Sum s = new Sum();
	OObject p = rMultiply( t.complex );
	if( p instanceof Complex ){
	    s.complex = s.complex.add( (Complex)p );
	} else if( p instanceof Expression ){
	    s.expressionList.add( (Expression)p );
	} else
	    return new jscicalc.Error( "Error in Sum.multiply" );
	for( java.util.ListIterator<Expression> i = t.getExpressionList().listIterator();
	     i.hasNext(); ){
	    Expression e = i.next();
	    // get one term in new sum
	    p = rMultiply( e );
	    if( p instanceof Complex ){
		s.complex = s.complex.add( (Complex)p );
	    } else if( p instanceof Expression ){
		s.expressionList.add( (Expression)p );
	    } else
		return new jscicalc.Error( "Error in Sum.multiply" );
	}
	return s;
    }

    /**
     * Return either this or the first expression in this if this is just a container
     * for a single object.
     * @return simplified expression
     */
    public OObject unBox(){
	if( expressionList.size() == 1 && complex.isZero() ){
	    return expressionList.getFirst();
	} else if( expressionList.isEmpty() ){
	    return complex;
	} else {
	    return this;
	}
    }

    /**
     * The comparison operator.
     * @param sum The Sum to be compared.
     * @return integer indicating how expression is compared to this.
     */
    public int compareTo( Sum sum ){
	// ignore complex
	sort(); // needs to be in order
	int r = compare( expressionList, sum.expressionList );
	if( r != 0 )
	    return r;
	else
	    return complex.compareTo( sum.complex );
    }

    /**
     * Simplify a Sum expression. Returns Error or Complex or jscicalc.Sum.
     * @return A simpler OObject equivalent to this.
     */
    public OObject auto_simplify(){
	// recursively simplify expressions
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
	// now work through expressions in sum
	java.util.ListIterator<Expression> i = expressionList.listIterator();
	Expression f = null; // initialise for loop
	for( Expression e = i.next(); i.hasNext(); e = f ){
	    Product product_e = null;
	    if( e instanceof Product ){
		product_e = (Product)e;
	    } else {
		product_e = new Product( e, false );
	    }
	    f = i.next();
	    Product product_f = null;
	    if( f instanceof Product ){
		product_f = (Product)f;
	    } else {
		product_f = new Product( f, false );
	    }	    
	    if( product_e.compareTo( product_f ) == 0 ){
		System.out.println( "Adding comparable expressions" );
		Product product = new Product( product_e, false ); // start with e.
		product.setComplex( product_e.getComplex()
				    .add( product_f.getComplex() ) );
		OObject o = product.unBox();
		if( o instanceof Complex ){
		    complex = complex.add( (Complex)o );
		    i.remove(); // remove f
		    i.previous();
		    i.remove(); // remove e
		    if( i.hasNext() ) e = i.next(); // new e
		} else if( o instanceof Expression ){
		    i.remove(); // remove f
		    i.previous();
		    i.set( (Expression)o ); // replace e;
		    f = i.next(); // update f
		} else {
		    return new jscicalc.Error( "Sum.auto_simplify() error" );
		}
	    }
	}
	return unBox(); // might be nothing left
    }    

    public static void main( String[] args ){
	// check equivalence of x and x
	jscicalc.expression.Variable x
	    = new jscicalc.expression.Variable( new jscicalc.pobject.Variable( 'x' ) );
	Sum s = new Sum( x );
    }

}
