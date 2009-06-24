
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

import java.util.ArrayList;
import java.util.Arrays;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Evaluator;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.ConsPointer;

/**
 *
 *
 */
public class TraceExcept extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer functionListPointer = new ConsPointer();
        functionListPointer.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());

        ConsPointer bodyPointer = getArgumentPointer(aEnvironment, aStackTop, 2);

        // Get function list.
        LispError.checkArgument(aEnvironment, aStackTop, functionListPointer.getCons() != null, 1);
        String functionNamesString = functionListPointer.getCons().string();
        LispError.checkArgument(aEnvironment, aStackTop, functionNamesString != null, 1);


        //Place function names into a List and then set this as the trace function list in Evaluator.
        functionNamesString = functionNamesString.replace("\"", "");
        String[] functionNames = functionNamesString.split(",");
        ArrayList functionNamesList = new ArrayList();
        for(String functionName : functionNames)
        {
            functionNamesList.add(functionName.trim());
        }//end for.
        Evaluator.setTraceExceptFunctionList(functionNamesList);


        //Evaluate expresstion with tracing on.
        Evaluator.traceOn();
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, getResult(aEnvironment, aStackTop), bodyPointer);
        Evaluator.traceOff();
        Evaluator.setTraceExceptFunctionList(null);

    }//end method.

}//end class.




/*
%mathpiper_docs,name="TraceExcept"
*CMD TraceExcept --- trace all the functions but the given functions an expression
*CORE
*CALL
	TraceExcept("function_name,function_name,function_name,...", expression))

*PARMS

{"function_name,function_name,function_name,..."} -- a string which contains the names of functions not to trace separated by commas.

{expression} -- an expression to trace.

*DESC

Outputs a trace of all the functions which are evaluated in the given expression except those which are listed in the given string.  An empty
function list string means trace all of the functions which are evaluated in the given expression.


*E.G.

In> In> TraceExcept("",2+3-6) //An empty function list means trace all functions.
Result> True<In
Side Effects>
Enter<**** user rulebase>{(-,2+3-6);
    Enter<**** user rulebase>{(+,2+3);
        Arg(2->2);
        Arg(3->3);
        Enter<builtin>{(IsNumber,IsNumber(x));
            Arg(x->2);
        Leave<builtin>}(IsNumber(x)->True);
        Enter<builtin>{(IsNumber,IsNumber(y));
            Arg(y->3);
        Leave<builtin>}(IsNumber(y)->True);
        **** Rule in function (+) matched: Precedence: 50, Parameters: arg1<hold=false>, arg2<hold=false>, Predicates: (Pattern) IsNumber(x), IsNumber(y), True,     Variables: x, y,    Types: Variable, Variable, Body: AddN(x,y)
        Enter<builtin>{(AddN,AddN(x,y));
            Arg(x->2);
            Arg(y->3);
        Leave<builtin>}(AddN(x,y)->5);
    Leave<**** user rulebase>}(2+3->5);
    Arg(2+3->5);
    Arg(6->6);
    Enter<builtin>{(IsList,IsList($x8));
        Arg($x8->5);
    Leave<builtin>}(IsList($x8)->False);
    Enter<builtin>{(IsNumber,IsNumber(x));
        Arg(x->5);
    Leave<builtin>}(IsNumber(x)->True);
    Enter<builtin>{(IsNumber,IsNumber(y));
        Arg(y->6);
    Leave<builtin>}(IsNumber(y)->True);
    **** Rule in function (-) matched: Precedence: 50, Parameters: arg1<hold=false>, arg2<hold=false>, Predicates: (Pattern) IsNumber(x), IsNumber(y), True,     Variables: x, y,    Types: Variable, Variable, Body: SubtractN(x,y)
    Enter<builtin>{(SubtractN,SubtractN(x,y));
        Arg(x->5);
        Arg(y->6);
    Leave<builtin>}(SubtractN(x,y)->-1);
Leave<**** user rulebase>}(2+3-6->-1);


In> TraceExcept("IsList, IsNumber",2+3-6)
Result> True
Side Effects>
Enter<**** user rulebase>{(-,2+3-6);
    Enter<**** user rulebase>{(+,2+3);
        Arg(2->2);
        Arg(3->3);
        **** Rule in function (+) matched: Precedence: 50, Parameters: arg1<hold=false>, arg2<hold=false>, Predicates: (Pattern) IsNumber(x), IsNumber(y), True,     Variables: x, y,    Types: Variable, Variable, Body: AddN(x,y)
        Enter<builtin>{(AddN,AddN(x,y));
            Arg(x->2);
            Arg(y->3);
        Leave<builtin>}(AddN(x,y)->5);
    Leave<**** user rulebase>}(2+3->5);
    Arg(2+3->5);
    Arg(6->6);
    **** Rule in function (-) matched: Precedence: 50, Parameters: arg1<hold=false>, arg2<hold=false>, Predicates: (Pattern) IsNumber(x), IsNumber(y), True,     Variables: x, y,    Types: Variable, Variable, Body: SubtractN(x,y)
    Enter<builtin>{(SubtractN,SubtractN(x,y));
        Arg(x->5);
        Arg(y->6);
    Leave<builtin>}(SubtractN(x,y)->-1);
Leave<**** user rulebase>}(2+3-6->-1);


 *SEE Trace

%/mathpiper_docs
*/