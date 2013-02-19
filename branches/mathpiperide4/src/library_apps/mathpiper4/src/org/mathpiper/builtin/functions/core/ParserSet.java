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
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.parsers.Parser;

/**
 *
 *  
 */
public class ParserSet extends BuiltinFunction
{

    private ParserSet()
    {
    }

    public ParserSet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        int nrArguments = Utility.listLength(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 0));
        if (nrArguments == 1)
        {
            aEnvironment.iParserName = "ParseMathPiper";
        } else
        {
            if(nrArguments != 2) LispError.throwError(aEnvironment, aStackTop, LispError.WRONG_NUMBER_OF_ARGUMENTS);

            Cons oper = getArgument(aEnvironment, aStackTop, 0);
            oper = oper.cdr();
            LispError.checkIsString(aEnvironment, aStackTop, oper, 1);
            
            String parserName = (String) oper.car();
            
            parserName = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, parserName);
            
            if(!Parser.isSupportedParser(parserName)) LispError.raiseError("A parser does not exist for <" + parserName + ">.", aStackTop, aEnvironment);
            
            aEnvironment.iParserName = parserName;
        }
        setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
    }
}



/*
%mathpiper_docs,name="ParserSet",categories="User Functions;Built In"
*CMD ParserSet --- set the current parser

*CORE

*CALL
	ParserSet(parser)
	ParserSet()

*PARMS

{parser} -- a string containing the name of a function that can parse an expression from current input.


*DESC

This function sets the current parser that will parse in the input on
the command line. This can be reset to the default parser with {ParserSet()} 
(when no argument is given, the system returns to the default).

Currently implemented Parsers are: {ParseLisp}, {OMRead}.


*E.G.

In> ParserSet("ParseLisp")
Result: True

In> (+ 1 (* 2 3))
Result: 7

In> (ParserSet "ParseMathPiper")
Result: True




In> Taylor(x,0,5)Sin(x)
Result: x-x^3/6+x^5/120

In> ParserSet("ParseLisp")
Result: True

In> (Taylor x 0 5 (Sin x))
Result: x-x^3/6+x^5/120

In> (ParserSet "ParseMathPiper")
Result: True

*SEE ParseMathPiper, ParseLisp, OMRead, UnparserSet, UnparserGet, ParserGet
%/mathpiper_docs
*/