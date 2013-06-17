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
public class ObjectToMeta extends BuiltinFunction
{

    private ObjectToMeta()
    {
    }

    public ObjectToMeta(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
	Cons cons = getArgument(aEnvironment, aStackTop, 1).copy(false);
		
        org.mathpiper.lisp.astprocessors.ObjectToMetaSubstitute behaviour = new org.mathpiper.lisp.astprocessors.ObjectToMetaSubstitute(aEnvironment);

        setTopOfStack(aEnvironment, aStackTop, Utility.substitute(aEnvironment, aStackTop, cons, behaviour));
	
    }
}

/*
%mathpiper_docs,name="VariablesToSymbols",categories="Programming Functions;Miscellaneous;Built In"
*CMD SymbolToVariable --- keep expression unevaluated
*CORE
*CALL
	VariablesToSymbols(expr)

*PARMS

{expr} -- expression to keep unevaluated

*DESC

The 

*E.G.


*SEE VariablesToSymbols
%/mathpiper_docs
*/
