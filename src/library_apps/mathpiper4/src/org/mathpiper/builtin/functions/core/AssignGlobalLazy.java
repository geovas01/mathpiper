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
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.Environment;

/**
 *
 *  
 */
public class AssignGlobalLazy extends BuiltinFunction
{

    private AssignGlobalLazy()
    {
    }

    public AssignGlobalLazy(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Utility.setVariableOrConstant(aEnvironment, aStackTop, false, true, false);
    }
}




/*
%mathpiper_docs,name="AssignGlobalLazy",categories="Programming Functions;Variables;Built In"
*CMD AssignGlobalLazy --- global variable is to be evaluated lazily
*CORE
*CALL
	AssignGlobalLazy(var,value)

*PARMS

{var} -- variable (held argument)

{value} -- value to be set to (evaluated before it is assigned)

*DESC

{AssignGlobalLazy} enforces that a global variable will re-evaluate
when used. This functionality doesn't survive if {Unassign(var)}
is called afterwards.

Places where this is used include the global variables {%} and {I}.

The use of lazy in the name stems from the concept of lazy evaluation.
The object the global variable is bound to will only be evaluated when
called. The {AssignGlobalLazy} property only holds once: after
that, the result of evaluation is stored in the global variable, and it won't be reevaluated again:

In> AssignGlobalLazy(a,Hold(Taylor(x,0,30)Sin(x)))
Result: True

Then the first time you call {a} it evaluates {Taylor(...)} and assigns the result to {a}. The next time
you call {a} it immediately returns the result.
{AssignGlobalLazy} is called for {%} each time {%} changes.

The following example demonstrates the sequence of execution:

In> AssignGlobalLazy(test,Hold(Write("hello")))
Result: True

The text "hello" is not written out to screen yet. However, evaluating
the variable {test} forces the expression to be evaluated:

In> test
Result: True
Side Effects:
"hello"

*E.G.

In> Assign(a,Hold(2+3))
Result: True

In> a
Result: 2+3

In> AssignGlobalLazy(a,Hold(2+3))
Result: True

In> a
Result: 5


*SEE Assign, Unassign, Local, #, I
Note: # works only in the mathpiper console!
%/mathpiper_docs
*/