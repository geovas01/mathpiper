/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.builtin.functions.core;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.astprocessors.ExpressionSubstitute;
import org.mathpiper.lisp.astprocessors.LocalSymbolSubstitute;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  tkosan
 */
public class MetaToObject extends BuiltinFunction
{

    private MetaToObject()
    {
    }

    public MetaToObject(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
	Cons cons = getArgument(aEnvironment, aStackTop, 1).copy(false);
		
        org.mathpiper.lisp.astprocessors.MetaToObjectSubstitute behaviour = new org.mathpiper.lisp.astprocessors.MetaToObjectSubstitute(aEnvironment);

        setTopOfStack(aEnvironment, aStackTop, Utility.substitute(aEnvironment, aStackTop, cons, behaviour));
	
    }
}

/*
%mathpiper_docs,name="MetaToObject",categories="Programming Functions;Variables;Built In",access="experimental"
*CMD MetaToObject --- Converts a meta-level expression into an object-level expression.

*CALL
	MetaToObject(expr)

*PARMS

{expr} -- expression to convert

*DESC

Converts a meta-level expression into an object-level expression. This is done by removing underscore
characters from all constant names that are in the expression.

*E.G.
In> MetaToObject(_a+_b)
Result: a+b

*SEE ObjectToMeta
%/mathpiper_docs
*/
