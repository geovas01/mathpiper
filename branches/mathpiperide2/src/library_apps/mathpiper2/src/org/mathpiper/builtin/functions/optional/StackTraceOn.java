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
import org.mathpiper.lisp.Evaluator;
import org.mathpiper.lisp.Utility;

/**
 *
 *
 */
public class StackTraceOn extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Exception {
        this.functionName = "StackTraceOn";
        aEnvironment.getBuiltinFunctions().setAssociation(
                this.functionName, new BuiltinFunctionEvaluator(this, 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
        Evaluator.stackTraceOn();
        aEnvironment.write("Stack tracing is on.\n");
        setTopOfStackPointer(aEnvironment, aStackTop, Utility.putTrueInPointer(aEnvironment));
    }//end method.

}//end class.




/*
%mathpiper_docs,name="StackTraceOn",categories="Programmer Functions;Built In;Debugging",access="experimental"
*CMD StackTraceOn --- sets the flag which will show a stack trace when an exception is thrown
*CALL
    StackTraceOn()

*DESC
This function sets the flag which will show the current state of the user function stack and the built in function stack
when an exception is thrown.

It is currently somewhat difficult to follow the stack traces at points where user functions call built in
functions and vice versa because there are no clear markers which indicate where control leave one stack
and enters the other.  However, even with this difficulty, the StackTrace function has still been proven
to be a useful debugging tool.

*E.G.
/%mathpiper

TestFunction() :=
[
    LessThan?(Complex(1,1),3);
];


StackTraceOn();

TestFunction();

StackTraceOff();

/%/mathpiper

    /%error,preserve="false"
      Result: In function "LessThan?" :
      bad argument number 1(counting from 1) :
      The first argument must be a non-complex decimal number or a string.
      The offending argument Complex(1,1) evaluated to Complex(1,1)


      ========================================= Start Of Built In Function Stack Trace
      0: LoadScript
         1: -> "/tmp/mathpiperide917565545585604790.mpw_tmp"
      -----------------------------------------
      2: Prog
         3: -> LessThan?(Complex(1,1),3)
      -----------------------------------------
      4: LessThan?
         5: -> Complex(1,1)
         6: -> 3
      ========================================= End Of Built In Function Stack Trace



      ========================================= Start Of User Function Stack Trace
      0: Prog
      -----------------------------------------
      1: TestFunction
      -----------------------------------------
      2: <START>
      ========================================= End Of User Function Stack Trace

       In function: TestFunction, Error near line 14

      Side Effects:
      Stack tracing is on.

.   /%/error

*SEE StackTrace, StackTraceOff, TraceSome, TraceExcept, TraceOn, TraceOff
%/mathpiper_docs
*/