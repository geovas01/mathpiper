
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
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Evaluator;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.Cons;

import org.mathpiper.lisp.printers.MathPiperPrinter;

/**
 *
 *
 */
public class TraceSome extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        this.functionName = "TraceSome";
        aEnvironment.iBuiltinFunctions.setAssociation(
                this.functionName, new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        aEnvironment.iBodiedOperators.setOperator(MathPiperPrinter.KMaxPrecedence, "TraceSome");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {

        Cons functionList = getArgument(aEnvironment, aStackTop, 1);
        Cons body = getArgument(aEnvironment, aStackTop, 2);

        // Get function list.
        if(functionList == null) LispError.checkArgument(aEnvironment, aStackTop, 1);

        int stackTop = aEnvironment.iArgumentStack.getStackTopIndex();
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, functionList);
        Cons result = aEnvironment.iArgumentStack.getElement(stackTop, aStackTop, aEnvironment);
        aEnvironment.iArgumentStack.popTo(stackTop, aStackTop, aEnvironment);
        
        String functionNamesString =  (String) result.car();
        if(functionNamesString == null) LispError.checkArgument(aEnvironment, aStackTop, 1);

        
        //Place function names into a List and then set this as the trace function list in Evaluator.
        functionNamesString = functionNamesString.replace("\"", "");
        String[] functionNames = functionNamesString.split(",");
        ArrayList functionNamesList = new ArrayList();
        for(String functionName : functionNames)
        {
            functionNamesList.add(functionName.trim());
        }//end for.
        Evaluator.setTraceFunctionList(functionNamesList);


        //Evaluate expresstion with tracing on.
        Evaluator.traceOn(); 
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, body);
        Evaluator.traceOff();
        Evaluator.setTraceFunctionList(null);


       // UtilityFunctions.internalTrue(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));


    }//end method.

}//end class.




/*
%mathpiper_docs,name="TraceSome",categories="Programmer Functions;Built In;Debugging",access="experimental"
*CMD TraceSome --- trace the given functions which are evaluated in the given expression
*CORE
*CALL
	TraceSome("function_name,function_name,function_name,...") expression

*PARMS

{"function_name,function_name,function_name,..."} -- a string which contains the names of functions to trace separated by commas.

{expression} -- an expression to trace.

*DESC

Outputs a trace of the functions which are listed in the given string and are evaluated in the given expression


*E.G.
In> TraceSome("Factors,FactorizeInt") Factor(8)
Result> True
Side Effects>
Enter<**** user rulebase>{(Factors,Factors(p));
    Arg(p->8);
    **** Rule in function (Factors) matched: Precedence: 10, Parameters: arg1<hold=false>, Predicates: (Pattern) Integer?(p), True,     Variables: p,    Types: Variable, Body: FactorizeInt(p)
    Enter<**** user rulebase>{(FactorizeInt,FactorizeInt(p));
        Arg(p->8);
        **** Rule in function (FactorizeInt) matched: Precedence: 3, Parameters: arg1<hold=false>, Predicates: (Pattern) Integer?(n), True,     Variables: n,    Types: Variable, Body: [    Local(small'powers);    n:=Abs(n);    If(Gcd(ProductPrimesTo257(),n)>1,small'powers:=TrialFactorize(n,257),small'powers:={n});    n:=small'powers[1];    If(n=1,Tail(small'powers),[        If(InVerboseMode(),Echo({"FactorizeInt: Info: remaining number ",n}));        SortFactorList(PollardCombineLists(Tail(small'powers),PollardRhoFactorize(n)));]);]
    Leave<**** user rulebase>}(FactorizeInt(p)->{{2,3}});
Leave<**** user rulebase>}(Factors(p)->{{2,3}});


*SEE TraceExcept, StackTrace, StackTraceOn, StackTraceOff, TraceOff

%/mathpiper_docs
*/