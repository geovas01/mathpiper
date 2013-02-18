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
            aEnvironment.iPrettyReaderName = "";
        } else
        {
            if(nrArguments != 2) LispError.throwError(aEnvironment, aStackTop, LispError.WRONG_NUMBER_OF_ARGUMENTS);

            Cons oper = getArgument(aEnvironment, aStackTop, 0);
            oper = oper.cdr();
            LispError.checkIsString(aEnvironment, aStackTop, oper, 1);
            
            String parserName = (String) oper.car();
            
            parserName = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, parserName);
            
            if(!Parser.isSupportedParser(parserName)) LispError.raiseError("A parser does not exist for <" + parserName + ">.", aStackTop, aEnvironment);
            
            aEnvironment.iPrettyReaderName = parserName;
        }
        setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
    }
}



/*
%mathpiper_docs,name="ParserSet",categories="User Functions;Built In"
*CMD ParserSet --- set routine to use as pretty-reader

*CORE

*CALL
	ParserSet(reader)
	ParserSet()

*PARMS

{reader} -- a string containing the name of a function that can read an expression from current input.


*DESC

This function sets up the function reader to read in the input on
the command line. This can be reset to the internal reader with {ParserSet()} (when no argument is given, the system returns to the default).

Currently implemented PrettyReaders are: {LispRead}, {OMRead}.

MathPiper allows you to configure a few things at startup. The file
{~/.mathpiperrc} is written in the MathPiper language and
will be executed when MapthPiper is run. This function
can be useful in the {~/.MathPiperrc} file.

*E.G.

In> Taylor(x,0,5)Sin(x)
Result: x-x^3/6+x^5/120

In> ParserSet("ParseLisp")
Result: True

In> (Taylor x 0 5 (Sin x))
Result: x-x^3/6+x^5/120

In> (ParserSet "ParseMathPiper")
Result: True

*SEE Read, ParseLisp, OMRead, PrettyPrinterSet, PrettyPrinterGet, ParserGet
%/mathpiper_docs
*/