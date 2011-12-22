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
public class RightPrecedenceSet extends BuiltinFunction
{

    private RightPrecedenceSet()
    {
    }

    public RightPrecedenceSet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        // Get operator
        if( getArgument(aEnvironment, aStackTop, 1) == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        String orig = (String) getArgument(aEnvironment, aStackTop, 1).car();
        if( orig == null) LispError.checkArgument(aEnvironment, aStackTop, 1);

        int stackTop = aEnvironment.iArgumentStack.getStackTopIndex();
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 2));
        Cons index = aEnvironment.iArgumentStack.getElement(stackTop, aStackTop, aEnvironment);
        aEnvironment.iArgumentStack.popTo(stackTop, aStackTop, aEnvironment);

        if( index == null) LispError.checkArgument(aEnvironment, aStackTop, 2);
        if(! (index.car() instanceof String)) LispError.checkArgument(aEnvironment, aStackTop, 2);
        int ind = Integer.parseInt ( (String) index.car(), 10);

        aEnvironment.iInfixOperators.setRightPrecedence(aStackTop, Utility.getSymbolName(aEnvironment, orig), ind);
        setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
    }
}



/*
%mathpiper_docs,name="RightPrecedenceSet",categories="User Functions;Built In"
*CMD RightPrecedenceSet --- set operator precedence
*CORE
*CALL
	RightPrecedenceSet("op",precedence)

*PARMS

{"op"} -- string, the name of a function

{precedence} -- nonnegative integer

*DESC

{"op"} should be an infix operator. This function call tells the
infix expression printer to bracket the right hand side of
the expression if its precedence is larger than precedence.

This functionality was required in order to display expressions like {a-(b-c)}
correctly. Thus, {a+b+c} is the same as {a+(b+c)}, but {a-(b-c)} is not
the same as {a-b-c}.

Note that the right precedence of an infix operator does not affect the way MathPiper interprets expressions typed by the user. You cannot make MathPiper parse {a-b-c} as {a-(b-c)} unless you declare the operator "{-}" to be right-associative.

*SEE PrecedenceGet, LeftPrecedenceGet, RightPrecedenceGet, LeftPrecedenceSet, RightAssociativeSet
%/mathpiper_docs
*/