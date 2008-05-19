/** @file
 * Copyright (C) 2008 John D Lamb (J.D.Lamb@btinternet.com)
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
 * This class represents a complex conjugate function of an expression.
 *
 * @see OObject, Monadic
 *
 * @author J.&nbsp;D.&nbsp;Lamb
 * @version $Revision: 1.4 $
 */
public class Factorial extends Monadic {
    /**
     * Construct from Expression.
     * @param expression The OObject
     */
    public Factorial( Expression expression ){
	super( new jscicalc.pobject.Factorial(), expression );
    }

    public jscicalc.StringArray
	toHTMLSubString( int maxChars, int precision, final Base base,
			 final Notation notation, double polarFactor ){ 
	jscicalc.StringArray s = new jscicalc.StringArray();
	s.addAll( expression.toHTMLParenString( maxChars, precision, base, notation,
						polarFactor ) );
	s.add( function.name_array() );
	return s;
    }
}
