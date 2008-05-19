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

/**
 * This class represents a dyadic function of two OObjects, at least one of which
 * is an expression.
 *
 * @see OObject
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public abstract class Dyadic<E extends jscicalc.pobject.PObject> extends Expression {
    /**
     * Construct from DFunction and OObject objects
     * @param function The function
     * @param expression1 The first expression
     * @param expression2 The second expression
     */
    public Dyadic( E function, OObject expression1, OObject expression2 ){
	this.function = function;
	this.expression1 = expression1;
	this.expression2 = expression2;
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
	s.addAll( expression1.toHTMLParenString( maxChars, precision, base, notation,
						 polarFactor ) );
	s.add( function.name_array() );
	s.addAll( expression2.toHTMLParenString( maxChars, precision, base, notation,
						 polarFactor ) );
	return s;
    }    

    /**
     * Carry out automatic simplifications on the OObject.
     * @return A simplified OObject
     */
    public OObject auto_simplify(){
	expression1 = expression1.auto_simplify();
	expression2 = expression2.auto_simplify();
	if( (expression1 instanceof jscicalc.Error) ||
	    (expression1 instanceof jscicalc.Error) )
	    return new jscicalc.Error( "Function auto_simplify() error" );
	else
	    return this;
    }

    /**
     * The Dfunction or BoolFunction.
     */
    protected E function;
    /**
     * The first subexpression.
     */
    protected OObject expression1;
    /**
     * The second subexpression.
     */
    protected OObject expression2;
}
