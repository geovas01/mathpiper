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
import org.mathpiper.exceptions.BreakException;
import org.mathpiper.exceptions.ContinueException;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;

import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;


/**
 *
 *  
 */
public class While extends BuiltinFunction
{

    private While()
    {
    }

    public While(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
        Cons arg1 = getArgument(aEnvironment, aStackTop, 1);
        Cons arg2 = getArgument(aEnvironment, aStackTop, 2);

        Cons predicate = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, arg1);

        Cons evaluated;

        int beforeStackTop = -1;
        int beforeEvaluationDepth = -1;
        
        try {
            while (Utility.isTrue(aEnvironment, predicate, aStackTop)) {

                beforeStackTop = aEnvironment.iArgumentStack.getStackTopIndex();
                beforeEvaluationDepth = aEnvironment.iEvalDepth;

                try {

                    evaluated = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, arg2);

                } catch (ContinueException ce) {
                    aEnvironment.iArgumentStack.popTo(beforeStackTop, aStackTop, aEnvironment);
                    aEnvironment.iEvalDepth = beforeEvaluationDepth;
                    setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
                }//end continue catch.

                predicate = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, arg1);

            }//end while.

            if(! Utility.isFalse(aEnvironment, predicate, aStackTop)) LispError.checkArgument(aEnvironment, aStackTop, 1);

        } catch (BreakException be) {
              aEnvironment.iArgumentStack.popTo(beforeStackTop, aStackTop, aEnvironment);
              aEnvironment.iEvalDepth = beforeEvaluationDepth;
        }

        setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
    }


}

/*
%mathpiper_docs,name="While",categories="Programming Functions;Control Flow;Built In"
*CMD While --- loop while a condition is met
*CORE
*CALL
While(pred) body

*PARMS
{pred} -- predicate deciding whether to keep on looping

{body} -- expression to loop over

*DESC

Keep on evaluating "body" while "pred" evaluates to {True}. More precisely, {While}
evaluates the predicate "pred", which should evaluate to either {True} or {False}. 
If the result is {True}, the expression "body" is evaluated and then
the predicate "pred" is again evaluated. If it is still {True}, the expressions "body" and "pred" are again
evaluated and so on until "pred" evaluates to {False}. At that point, the loop terminates and {While}
returns {True}.

In particular, if "pred" immediately evaluates to {False}, the body is never executed. {While} 
is the fundamental looping construct on which all other loop commands are based. It is equivalent 
to the {while} command in the programming language C.

*E.G. notest

In> x := 0;
Result: 0

In> While (x ! <? 10^6) [ Echo([x, x!]); x++; ];
Result: True
Side Effects:
[0,1] 
[1,1] 
[2,2] 
[3,6] 
[4,24] 
[5,120] 
[6,720] 
[7,5040] 
[8,40320] 
[9,362880] 

*SEE Until, For, ForEach, Break, Continue
%/mathpiper_docs
 */
