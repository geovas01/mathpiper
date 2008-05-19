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
import jscicalc.Notation;
import jscicalc.Base;
import jscicalc.OObject;
import jscicalc.complex.Complex;

/**
 * This class represents an output variable.
 *
 * @see OObject
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public class Variable extends Expression {
    /**
     * Construct from a Variable of jscicalc.pobject
     * @param variable The PObject representation.
     */
    public Variable( jscicalc.pobject.Variable variable ){
	this.variable = variable;
    }

    /**
     * Get the jscicalc.pobject.Variable represented by this.
     * @return The variable as PObject
     */
    public jscicalc.pobject.PObject pObject(){
	return variable;
    }

    /**
     * Useful to create a representation.
     * @return The character reprenting this Variable
     */
    public char get(){
	return variable.get();
    }
    
    /**
     * Useful to create a representation.
     * @return A String reprenting this Variable
     */
    public String name(){
	return variable.name();
    }

    /**
     * Useful to create a representation.
     * @return An array of String objects reprenting this Variable
     */
    public String[] name_array(){
	return variable.name_array();
    }
    

    /**
     * Function to create an HTML string representation of the variable
     * The arguments are ignored because they are only needed for a Complex.
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
	jscicalc.StringArray r = new jscicalc.StringArray();
	r.add( name_array() );
	return r;
    }

    public jscicalc.StringArray
	toHTMLParenString( int maxChars, int precision, final Base base,
			   final Notation notation, double polarFactor ){ 
	return toHTMLSubString( maxChars, precision, base, notation, polarFactor );
    }

    public Product negate(){
	Product p = new Product( this, false );
	return p.negate();
    }

    /**
     * The comparison operator.
     * @param variable The Variable to be compared.
     * @return integer indicating how expression is compared to this.
     */
    public int compareTo( Variable variable ){
	return get() < variable.get() ? -1 : get() == variable.get() ? 0 : +1;
    }

    /**
     * The variable is a reference to a pobject Variable
     */
    private jscicalc.pobject.Variable variable;
}
