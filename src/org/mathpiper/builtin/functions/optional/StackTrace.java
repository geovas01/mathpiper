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

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;

/**
 *
 *
 */
public class StackTrace extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        this.functionName = "StackTrace";
        aEnvironment.iBuiltinFunctions.setAssociation(
                this.functionName, new BuiltinFunctionEvaluator(this, 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
    }//end method.


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
         String dump = aEnvironment.iArgumentStack.dump(aEnvironment);

         aEnvironment.write(dump);

         dump = aEnvironment.dumpLocalVariablesFrame();

         aEnvironment.write(dump);
         
         setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="StackTrace",categories="Programmer Functions;Built In;Debugging",access="experimental"
*CMD StackTrace --- shows the current state of the user function stack and the built in function stack
*CALL
    StackTrace()

*DESC
This function shows the current state of the user function stack and the built in function stack.

It is currently somewhat difficult to follow the stack traces at points where user functions call built in
functions and vice versa because there are no clear markers which indicate where control leave one stack
and enters the other.  However, even with this difficulty, the StackTrace function has still been proven
to be a useful debugging tool.

*E.G.
/%mathpiper
TestFunction() :=
[
    index := 1;
    While(index < 10)
    [
        If(index = 5, StackTrace());

        index++;
    ];

];
/%/mathpiper


In> TestFunction()
Result: True
Side Effects:


========================================= Start Of Built In Function Stack Trace
0: Prog
   1: -> TestFunction()
-----------------------------------------
2: Prog
   3: -> index:=1
   4: -> While(index<10)[
    If(index=5,StackTrace());
    index++;
]

-----------------------------------------
5: index<10
-----------------------------------------
6: [
    If(index=5,StackTrace());
    index++;
]

-----------------------------------------
7: Prog
   8: -> If(index=5,StackTrace())
   9: -> index++
-----------------------------------------
10: index=5
-----------------------------------------
11: {StackTrace()}
-----------------------------------------
12: StackTrace
========================================= End Of Built In Function Stack Trace



========================================= Start Of User Function Stack Trace
0: Prog
-----------------------------------------
1: Prog
-----------------------------------------
2: TestFunction
-----------------------------------------
3: Prog
-----------------------------------------
4: <START>
========================================= End Of User Function Stack Trace

*SEE StackTraceOn, StackTraceOff, TraceSome, TraceExcept, TraceOn, TraceOff
%/mathpiper_docs
*/
