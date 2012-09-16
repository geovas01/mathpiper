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
public class TraceOn extends BuiltinFunction
{

    private TraceOn()
    {
    }

    public TraceOn(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
        Evaluator.traceOn();
        aEnvironment.write("Tracing is on.\n");
        setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
    }//end method.
    
}//end class.




/*
%mathpiper_docs,name="TraceOn",categories="Programmer Functions;Built In;Debugging",access="experimental"
*CMD TraceOn --- enables a complete trace of all the functions that are called when an expression is evaluated
*CALL
    TraceOn()

*DESC
This function enables a complete trace of all the functions that are called when an expression is evaluated.
The tracing output can become very long, very quickly so this form of complete tracing is only useful
for tracing relatively simple expressions.  TraceSome and TraceExcept can be used as an alternative to reduce
the amount of tracing output that is generated.

The first time a function is called during a MathPiper session, it needs to be loaded
and converted into Lisp code.  If tracing is enabled when functions are being loaded, the loading code
will also be traced.  This loading code can be caused to not appear in the trace by simply evaluating the
expression to be traced once with tracing off and then evaluating it again with tracing on.

In the example below, the {output} attribute of the {%mathpiper} fold is set to {trace} so that the output
is placed into a {%mathpiper_trace} fold.  This will enable the trace output to be syntax highlighted.

*E.G.
/%mathpiper,output="trace"

TraceOn();

2 + 3;

TraceOff();

/%/mathpiper

    /%mathpiper_trace,preserve="false"
      Result: True

      Side Effects:
      Tracing is on.
          Enter<**** user rulebase>{(+, 2+3);
              Arg(arg1 -> 2);
              Arg(arg2 -> 3);
              Enter<builtin>{(Number?, Number?(x));
                  Arg(parameter1 -> 2);
              Leave<builtin>}(Number?(x) -> True,    Local variables: y -> 3, x -> 2, arg2 -> 3, arg1 -> 2, );
              Enter<builtin>{(Number?, Number?(y));
                  Arg(parameter1 -> 3);
              Leave<builtin>}(Number?(y) -> True,    Local variables: y -> 3, x -> 2, arg2 -> 3, arg1 -> 2, );
              **** Rule in function (+) matched: Precedence: 50, Parameters: arg1<hold=false>, arg2<hold=false>, Predicates: (Pattern) Number?(x), Number?(y), True,     Variables: x, y,     Types: Variable, Variable,    Body: AddN(x, y)
              Enter<builtin>{(AddN, AddN(x,y));
                  Arg(parameter1 -> 2);
                  Arg(parameter2 -> 3);
              Leave<builtin>}(AddN(x,y) -> 5,    Local variables: y -> 3, x -> 2, arg2 -> 3, arg1 -> 2, );
          Leave<**** user rulebase>}(2+3 -> 5,    Local variables: y -> 3, x -> 2, arg2 -> 3, arg1 -> 2, );
          Enter<builtin>{(TraceOff, TraceOff());
      Tracing is off.

.   /%/mathpiper_trace

*SEE StackTrace, StackTraceOn, StackTraceOff, TraceSome, TraceExcept, TraceOff
%/mathpiper_docs
*/
