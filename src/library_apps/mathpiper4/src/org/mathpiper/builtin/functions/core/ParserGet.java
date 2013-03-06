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
public class ParserGet extends BuiltinFunction
{

    private ParserGet()
    {
    }

    public ParserGet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        if (aEnvironment.iParserName == null)
        {
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "\"\""));
        } else
        {
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, aEnvironment.iParserName));
        }
    }
}



/*
%mathpiper_docs,name="ParserGet",categories="Mathematics Functions;Built In"
*CMD ParserGet --- get the name of the current parser

*CORE

*CALL
	ParserGet()

*DESC

{ParserGet()} returns the current parser, or it returns
an empty string if the default parser is used.



*E.G.

In> ParserGet()
Result: ""

*SEE ParseMathPiper, ParseLisp, OMRead, UnparserSet, UnparserGet, ParserSet
%/mathpiper_docs
*/