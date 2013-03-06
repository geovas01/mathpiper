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
import org.mathpiper.lisp.Evaluator;
import org.mathpiper.lisp.Utility;

/**
 *
 *
 */
public class StackTraceOff extends BuiltinFunction
{

    private StackTraceOff()
    {
    }

    public StackTraceOff(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
         Evaluator.stackTraceOff();
         aEnvironment.write("Stack tracing is off.\n");
         setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
    }
}




/*
%mathpiper_docs,name="StackTraceOff",categories="Programming Functions;Built In;Debugging",access="experimental"
*CMD StackTraceOff --- clears the flag which will show a stack trace when an exception is thrown
*CALL
    StackTraceOff()

*DESC
This function clears the flag which will show the current state of the user function stack and the built in function stack
when an exception is thrown.

See the StackTraceOn function for more information.

*SEE StackTrace, StackTraceOn, TraceSome, TraceExcept, TraceOn, TraceOff
%/mathpiper_docs
*/