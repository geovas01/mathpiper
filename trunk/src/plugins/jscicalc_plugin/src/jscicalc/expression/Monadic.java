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

/**
 * This class represents a monadic function of an expression.
 *
 * @see OObject
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.3 $
 */
public abstract class Monadic extends Expression {
    /**
     * Construct from SFunction and Expression.
     * @param function The function
     * @param expression The OObject
     */
    public Monadic( jscicalc.pobject.SFunction function, Expression expression ){
	this.function = function;
	this.expression = expression;
    }

    public Product negate(){
	Product p = new Product( this, false );
	return p.negate();
    }

    /**
     * Function to create an HTML string representation of the function.
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
	s.add( function.name_array() );
	s.addAll( expression.toHTMLParenString( maxChars, precision, base, notation,
						polarFactor ) );
	return s;
    } 
    /**
     * Carry out automatic simplifications on the OObject.
     * @return A simplified OObject
     */
    public jscicalc.OObject auto_simplify(){
	jscicalc.OObject o = expression.auto_simplify();
	if( o instanceof jscicalc.complex.Complex ){
	    return function.function( (jscicalc.complex.Complex)o );
	} else if( o instanceof Expression ){
	    return this;
	} else {
	    return new jscicalc.Error( "Function.auto_simplify() error" );
	}
    }
    /**
     * The Sfunction.
     */
    protected final jscicalc.pobject.SFunction function;
    /**
     * The subexpression.
     */
    protected Expression expression;
}
