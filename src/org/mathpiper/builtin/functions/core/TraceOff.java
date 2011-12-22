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
public class TraceOff extends BuiltinFunction
{

    private TraceOff()
    {
    }

    public TraceOff(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
         Evaluator.traceOff();
         aEnvironment.write("Tracing is off.\n");
         setTopOfStack(aEnvironment, aStackBase, Utility.getTrueAtom(aEnvironment));
    }
}




/*
%mathpiper_docs,name="TraceOff",categories="Programmer Functions;Built In;Debugging",access="experimental"
*CMD TraceOff --- disables a complete trace of all the functions that are called when an expression is evaluated
*CALL
    TraceOff()

*DESC
This function disables a complete trace of all the functions that are called when an expression is evaluated.

See TraceOn for more information.

*SEE StackTrace, StackTraceOn, StackTraceOff, TraceSome, TraceExcept, TraceOn
%/mathpiper_docs
*/
