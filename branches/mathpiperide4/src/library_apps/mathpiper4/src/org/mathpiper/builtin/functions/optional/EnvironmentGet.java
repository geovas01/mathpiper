
package org.mathpiper.builtin.functions.optional;


import org.mathpiper.builtin.BuiltinFunction;
import static org.mathpiper.builtin.BuiltinFunction.setTopOfStack;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.BuiltinObjectCons;


public class EnvironmentGet extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Throwable {
        this.functionName = "EnvironmentGet";
        aEnvironment.getBuiltinFunctions().put(
            this.functionName, new BuiltinFunctionEvaluator(this, 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
        JavaObject response = new JavaObject(aEnvironment);
        
        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));
    }//end method.
}//end class.



/*
%mathpiper_docs,name="EnvironmentGet",categories="Programming Functions;Built In;Native Objects",access="experimental"
*CMD EnvironmentGet --- displays an input dialog to the user

*CALL
	EnvironmentGet()

*DESC

This function returns a reference to the current environment object.

%/mathpiper_docs
*/


