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
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.Environment;

/**
 *
 *  
 */
public class UnparserGet extends BuiltinFunction
{

    private UnparserGet()
    {
    }

    public UnparserGet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        if (aEnvironment.iUnparserName == null)
        {
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "\"\""));
        } else
        {
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, aEnvironment.iUnparserName));
        }
    }
}



/*
%mathpiper_docs,name="UnparserGet",categories="Mathematics Functions;Built In"
*CMD UnparserGet --- get the name of the current unparser

*CORE

*CALL
	UnparserGet()

*DESC

{UnparserGet()} returns the current unparser, or it returns
an empty string if the default unparser is used.


*E.G.

In> UnparserGet()
Result: ""

*SEE UnparseMath2D, Write, UnparseLatex, UnparseC, OMForm, ParserSet, ParserGet, UnparserSet
%/mathpiper_docs
*/