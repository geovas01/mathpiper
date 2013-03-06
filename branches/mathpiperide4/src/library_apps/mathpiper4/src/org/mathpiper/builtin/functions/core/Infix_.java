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
import org.mathpiper.lisp.Operator;
import org.mathpiper.lisp.Utility;

/**
 *
 *  
 */
public class Infix_ extends BuiltinFunction
{

    private Infix_()
    {
    }

    public Infix_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Operator op = Utility.operatorInfo(aEnvironment, aStackTop, aEnvironment.iInfixOperators);
        setTopOfStack(aEnvironment, aStackTop, Utility.getBooleanAtom(aEnvironment, op != null));
    }
}



/*
%mathpiper_docs,name="Infix?",categories="Programming Functions;Predicates;Built In"
*CMD Infix? --- check for function syntax
*CORE
*CALL
	Infix?("op")

*PARMS

{"op"} -- string, the name of a function

*DESC

Check whether the function with given name {"op"} has been declared as a
"bodied", infix, postfix, or prefix operator, and  return {True} or {False}.

*E.G.

In> Infix?("+");
Result: True;

*SEE Bodied, PrecedenceGet,Bodied?,Postfix?,Prefix?
%/mathpiper_docs
*/