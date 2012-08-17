/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper.builtin.functions.core;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;

/**
 *
 *  
 */
public class RulebaseListedEvaluateArguments extends BuiltinFunction
{

    private RulebaseListedEvaluateArguments()
    {
    }

    public RulebaseListedEvaluateArguments(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
        org.mathpiper.lisp.Utility.defineRulebase(aEnvironment, aStackTop, true);
    }

}



/*
%mathpiper_docs,name="RulebaseListedEvaluateArguments",categories="Programmer Functions;Programming;Built In"
*CMD RulebaseListedEvaluateArguments --- define rules in functions
*CORE
*DESC

This function has the same effect as its non-macro counterpart, except
that its arguments are evaluated before the required action is performed.
This is useful in macro-like procedures or in functions that need to define new
rules based on parameters.

Make sure that the arguments of {Macro}... commands evaluate to expressions that would normally be used in the non-macro version!

*SEE Assign, Unassign, Local, RulebaseHoldArguments, RuleHoldArguments, `, MacroAssign, MacroUnassign, MacroLocal, MacroRulebase, RuleEvaluateArguments
%/mathpiper_docs
*/
