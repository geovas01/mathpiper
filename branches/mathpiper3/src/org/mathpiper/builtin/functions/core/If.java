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
public class If extends BuiltinFunction
{

    private If()
    {
    }

    public If(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        int nrArguments = Utility.listLength(aEnvironment, aStackBase, getArgument(aEnvironment, aStackBase, 0));
        if( nrArguments != 3 && nrArguments != 4) LispError.throwError(aEnvironment, aStackBase, LispError.WRONG_NUMBER_OF_ARGUMENTS);


        int oldStackTop = aEnvironment.iArgumentStack.getStackTopIndex();
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackBase, getArgument(aEnvironment, aStackBase, 1));
        Cons predicate = aEnvironment.iArgumentStack.getElement(oldStackTop, aStackBase, aEnvironment);
        aEnvironment.iArgumentStack.popTo(oldStackTop, aStackBase, aEnvironment);


        if (Utility.isTrue(aEnvironment, predicate, aStackBase))
        {   
            oldStackTop = aEnvironment.iArgumentStack.getStackTopIndex();
            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackBase, getArgument(aEnvironment, aStackBase, getArgument(aEnvironment, aStackBase, 0), 2));
            Cons aResult = aEnvironment.iArgumentStack.getElement(oldStackTop, aStackBase, aEnvironment);
            aEnvironment.iArgumentStack.popTo(oldStackTop, aStackBase, aEnvironment);
            setTopOfStack(aEnvironment, aStackBase, aResult);        
        } else
        {
            if( Utility.isTrue(aEnvironment, predicate, aStackBase)) LispError.checkArgument(aEnvironment, aStackBase, 1);

            if (nrArguments == 4)
            {
                oldStackTop = aEnvironment.iArgumentStack.getStackTopIndex();
                aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackBase, getArgument(aEnvironment, aStackBase, getArgument(aEnvironment, aStackBase, 0), 3));
                Cons aResult = aEnvironment.iArgumentStack.getElement(oldStackTop, aStackBase, aEnvironment);
                aEnvironment.iArgumentStack.popTo(oldStackTop, aStackBase, aEnvironment);
                setTopOfStack(aEnvironment, aStackBase, aResult);
            } else
            {
                setTopOfStack(aEnvironment, aStackBase, Utility.getFalseAtom(aEnvironment));
            }
        }
    }
}


/*
%mathpiper_docs,name="If",categories="User Functions;Control Flow;Built In"
*CMD If --- branch point
*CORE
*CALL
	If(pred, then)
	If(pred, then, else)

*PARMS

{pred} -- predicate to test

{then} -- expression to evaluate if "pred" is {True}

{else} -- expression to evaluate if "pred" is {False}

*DESC

This command implements a branch point. The predicate "pred" is
evaluated, which should result in either {True} or {False}. In the first case, the expression "then" is
evaluated and returned. If the predicate yields {False}, the expression "else" (if present) is evaluated and
returned. If there is no "else" branch (i.e. if the first calling
sequence is used), the {If} expression returns {False}.

*E.G.

The sign function is defined to be 1 if its argument is positive and
-1 if its argument is negative. A possible implementation is
In> mysign(x) := If (IsPositiveReal(x), 1, -1);
Result: True;
In> mysign(Pi);
Result: 1;
In> mysign(-2.5);
Result: -1;
Note that this will give incorrect results, if "x" cannot be
numerically approximated.
In> mysign(a);
Result: -1;
Hence a better implementation would be
In> mysign(_x)_Number?(N(x)) <-- If \
	  (IsPositiveReal(x), 1, -1);
Result: True;
%/mathpiper_docs
*/