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

import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

/**
 *
 *  
 */
public class And_ extends BuiltinFunction
{
    
    private And_()
    {
    }

    public And_(String functionName)
    {
        this.functionName = functionName;
    }

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons argument = null;
        int numberOfArguments = 0;
        Cons evaluated;

        Cons consTraverser =  (Cons) getArgument(aEnvironment, aStackTop, 1).car();
        consTraverser = consTraverser.cdr();
        while (consTraverser != null)
        {
            evaluated = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, consTraverser);

            if (Utility.isFalse(aEnvironment, evaluated, aStackTop))
            {
                setTopOfStack(aEnvironment, aStackTop, Utility.getFalseAtom(aEnvironment));
                return;
            } else if (!Utility.isTrue(aEnvironment, evaluated, aStackTop))
            {
                numberOfArguments++;
                Cons ptr = evaluated.copy(false);
                ptr.setCdr(argument);
                argument = ptr;
            }

            consTraverser = consTraverser.cdr();
        }

        if (argument != null)
        {
            if (numberOfArguments == 1)
            {
                setTopOfStack(aEnvironment, aStackTop, argument);
            } else
            {
                

                Cons ptr = Utility.reverseList(aEnvironment, argument);
                argument = ptr;

                ptr = getArgument(aEnvironment, aStackTop, 0).copy(false);
                ptr.setCdr(argument);
                argument = ptr;
                setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment, argument));

            //aEnvironment.CurrentPrinter().Print(getTopOfStackPointer(aEnvironment, aStackTop), *aEnvironment.CurrentOutput());
            }
        } else
        {
            setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
        }
    }
}



/*
%mathpiper_docs,name="And?",categories="User Functions;Predicates;Built In"
*CMD And? --- logical conjunction
*CORE
*CALL
	a1 And? a2
Precedence:
*EVAL PrecedenceGet("And?")

	And?(a1, a2, a3, ..., aN)

*PARMS

{a}1, ..., {a}N -- boolean values (may evaluate to {True} or {False})

*DESC

This function returns {True} if all arguments are true. The
{And?} operation is "lazy", i.e. it returns {False} as soon as a {False} argument
is found (from left to right).  This is faster, but also means that none of the
arguments should cause side effects when they are evaluated.
 If an argument other than {True} or
{False} is encountered a new {And?} expression is returned with all
arguments that didn't evaluate to {True} or {False} yet.
{And?(...)} and {Or?(...)} do also exist, defined in the script
library. You can redefine them as infix operators yourself, so you have the
choice of precedence. In the standard scripts they are in fact declared as
infix operators, so you can write {expr1 And? expr}.

*E.G.

In> True And? False
Result: False;
In> And?(True,True)
Result: True;
In> False And? a
Result: False;
In> True And? a
Result: And?(a);
In> And?(True,a,True,b)
Result: b And? a;

*SEE Or?, Not?, Implies?
%/mathpiper_docs





%mathpiper,name="And?",subtype="automatic_test"

Verify(False And? False,False);
Verify(True And? False,False);
Verify(False And? True,False);
Verify(True And? True,True);

%/mathpiper

*/