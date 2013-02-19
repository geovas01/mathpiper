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

/**
 *
 *  
 */
public class UnparserSet extends BuiltinFunction
{

    private UnparserSet()
    {
    }

    public UnparserSet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        int nrArguments = Utility.listLength(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 0));
        if (nrArguments == 1)
        {
            aEnvironment.iUnparserName = null;
        } else
        {
            if(nrArguments != 2) LispError.throwError(aEnvironment, aStackTop, LispError.WRONG_NUMBER_OF_ARGUMENTS);
            Cons oper = getArgument(aEnvironment, aStackTop, 0);
            oper = oper.cdr();
            LispError.checkIsString(aEnvironment, aStackTop, oper, 1);
            aEnvironment.iUnparserName = (String) oper.car();
        }
        setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
    }
}



/*
%mathpiper_docs,name="UnparserSet",categories="User Functions;Built In"
*CMD UnparserSet --- set the current unparser

*CORE

*CALL
	UnparserSet(unparser)
	UnparserSet()

*PARMS

{unparser} -- a string containing the name of an unparser function


*DESC

This function sets the current unparser that outputs results in text form. 
This can be reset to the default printer with {UnparserSet()} 
(when no argument is given, the system returns to the default).

Currently implemented unparsers are: {UnparseMath2D}, {UnparseLatex}, {Print}, {OMForm}, {UnparseC} and {DefaultPrint}.


*E.G.

In> Taylor(x,0,5)Sin(x)
Result: (x-x^3/6)+x^5/120

In> UnparserSet("UnparseMath2D");
Result: True
Side Effects:

True


In> Taylor(x,0,5)Sin(x)
Result: True
Side Effects:

     3    5 
    x    x  
x - -- + ---
    6    120



In> UnparserSet();
Result: True

In> Taylor(x,0,5)Sin(x)
Result: (x-x^3/6)+x^5/120

*SEE UnparseMath2D, Write, UnparseLatex, UnparseC, OMForm, ParserSet, ParserGet, UnparserGet
%/mathpiper_docs
*/