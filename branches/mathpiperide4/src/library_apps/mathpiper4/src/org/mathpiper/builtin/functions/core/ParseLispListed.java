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
import org.mathpiper.lisp.parsers.Parser;
import org.mathpiper.lisp.parsers.LispParser;

/**
 *
 *  
 */
public class ParseLispListed extends BuiltinFunction
{

    private ParseLispListed()
    {
    }

    public ParseLispListed(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Parser parser = new LispParser(aEnvironment.iCurrentTokenizer, aEnvironment.getCurrentInput(),
                aEnvironment);
        parser.iListed = true;
        // ParseMathPiper expression
        setTopOfStack(aEnvironment, aStackTop, parser.parse(aStackTop));
    }
}



/*
%mathpiper_docs,name="ParseLispListed",categories="User Functions;Input/Output;Built In"
*CMD ParseLispListed --- parse expressions in LISP syntax
*CORE
*CALL
	ParseLispListed()

*DESC

The function {ParseLispListed} reads a LISP expression and returns
it in a list, instead of the form usual to MathPiper (expressions).
The result can be thought of as applying {FunctionToList} to {ParseLisp}.
The function {ParseLispListed} is more useful for reading arbitrary LISP expressions, because the
first object in a list can be itself a list (this is never the case for MathPiper expressions where 
the first object in a list is always a function atom).

*E.G. notest

In> PipeFromString("(+ a b)")ParseLispListed()
Result: [+,a,b];

*SEE PipeFromFile, PipeFromString, ParseMathPiper, ParseMathPiperToken, UnparseLisp, ParseLisp
%/mathpiper_docs
*/