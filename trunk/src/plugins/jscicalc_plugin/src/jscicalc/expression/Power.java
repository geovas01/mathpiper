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
 * This class represents a power function
 *
 * @see Dyadic
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.5 $
 */
public class Power extends Dyadic<jscicalc.pobject.DFunction> {
    /**
     * Construct from DFunction.
     * @param expression1 The first expression
     * @param expression2 The second expression
     */
    public Power( OObject expression1, OObject expression2 ){
	super( new jscicalc.pobject.Power(), expression1, expression2 );
    }
    
    public Product negate(){
	Product p = new Product( this, false );
	return p.negate();
    }
    
    /**
     * Function to create an HTML string representation of the Power.
     * The arguments are ignored except for Complex values.
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
	s.addAll( expression1.toHTMLParenString( maxChars, precision, base, notation,
						 polarFactor ) );
	jscicalc.StringArray
	    t = expression2.toHTMLSubString( maxChars, precision, base, notation,
					     polarFactor );
	
	int l = t.lastElement().size();
	if( l > 0 ){
	    String sup = "<sup>";
	    t.firstElement().set( 0, sup.concat( t.firstElement().firstElement() ) );
	    t.lastElement().set( l - 1, t.lastElement().lastElement()
				 .concat( "</sup>" ) );
	}
	s.addAll( t );
	return s;
    }

    /**
     * Simplify a Power expression. Returns Error or Complex or jscicalc.Power.
     * @return A simpler OObject equivalent to this.
     */
    public OObject auto_simplify(){
	expression1 = expression1.auto_simplify();
	expression2 = expression2.auto_simplify();
	if( (expression1 instanceof jscicalc.Error) ||
	    (expression2 instanceof jscicalc.Error) ){
	    return new jscicalc.Error( "Power error" );
	}
	if( expression1 instanceof Complex ){
	    Complex v = (Complex)expression1;
	    if( expression2 instanceof Complex ){
		Complex w = (Complex)expression2;
		return v.pow( w ); // Complex power of Complex
	    }
	    Long l = v.isInteger();
	    if( l != null ){
		long i = l;
		if( i == 0 ){
		    return new jscicalc.Error( "Power error" );
		} else if( i == 1 ){
		    return new Complex( 1 );
		} else {
		    // integer expression1 as i
		}
	    }
	}
	if( expression2 instanceof Complex ){
	    Complex w = (Complex)expression2;
	    Long m = w.isInteger();
	    if( m != null ){
		long n = m;
		// An integer power.
		// Complex values of expression1 should be dealt with separately
		if( n == 0 ) return new Complex( 1 );
		else if( n == 1 ) return expression1;
		else if( expression1 instanceof Power ){
		    Power q = (Power)expression1;
		    OObject o = null;
		    if( q.expression2 instanceof Expression ){
			Product p = new Product( (Expression)(q.expression2), false );
			o = p.multiply( new Complex( n ) );
		    } else if( q.expression2 instanceof Complex ){
			o = q.expression2.multiply( new Complex( n ) );
		    } else
			return new jscicalc.Error( "Power error" );
		    return new Power( q.expression1, o );
		} else if( expression1 instanceof Product ){
		    Product p =(Product)expression1;
		    return p.integer_power( n ).auto_simplify();
		}
	    }
	}
	return this;
    }
    
    /**
     * The comparison operator.
     * @param power The Power to be compared.
     * @return integer indicating how expression is compared to this.
     */
    public int compareTo( Power power ){
	if( expression1 instanceof jscicalc.Error ){ // should be redundant
	    if( power.expression1 instanceof jscicalc.Error ){ // should be redundant
		return 0;
	    } else {
		return -1;
	    }
	} else if( power.expression1 instanceof jscicalc.Error ){ // should be redundant
	    return +1;
	} else if( expression1 instanceof Expression ){
	    if( power.expression1 instanceof Expression ){
		int compare = ((Expression)expression1)
		    .compareTo( (Expression)power.expression1 );
		if( compare == 0 ){
		    if( expression2 instanceof jscicalc.Error ){ // should be redundant
			if( power.expression2 instanceof jscicalc.Error ){ // ?redundant
			    return 0;
			} else {
			    return -1;
			}
		    } else if( power.expression2 instanceof jscicalc.Error ){//?redundant
			return +1;
		    } else if( expression2 instanceof Expression ){
			if( power.expression2 instanceof Expression ){
			    compare = ((Expression)expression2)
				.compareTo( (Expression)power.expression2 );
			} else {
			    compare = -1; // power.expression2 Complex
			}
		    }else { // expression2 Complex 
			if( power.expression2 instanceof Expression ){
			    compare = +1;
			} else {
			    compare = ((Complex)expression2)
				.compareTo( (Complex)power.expression2 );
			}
		    }
		}
		return compare;
	    } else {
		return -1; //(expression before complex)
	    }
	} else {
	    // expression1 is Complex
	    if( power.expression1 instanceof Expression ){
		return +1;
	    } else { // power.expression1 Complex
		return ((Complex)expression1)
		    .compareTo( (Complex)power.expression1 );
	    }
	}
    }

    /**
     * Get base expression.
     * @return base of power
     */
    OObject base(){
	return expression1;
    }

    /**
     * Get exponent expression.
     * @return exponent of power
     */
    OObject exponent(){
	return expression2;
    }
}
