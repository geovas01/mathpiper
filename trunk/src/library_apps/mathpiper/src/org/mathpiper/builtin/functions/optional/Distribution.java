

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.cern.Probability;
import org.mathpiper.builtin.library.statdistlib.normal;
import org.mathpiper.builtin.library.statdistlib.uniform;
import org.mathpiper.lisp.Environment;



public class Distribution extends BuiltinFunction{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Distribution");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        BigNumber mean = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);

        //LispError.check(mean.isInteger() && mean.toInt() >= 0, "The first argument must be an integer which is greater than 0.", "Distribution", aStackTop, aEnvironment);

        BigNumber sigma = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 2);

        //LispError.check(sigma.toDouble() >= 0, "The second argument must be greater than 0.", "Distribution", aStackTop, aEnvironment);

        double randomVariableDouble = normal.random(mean.toDouble(), sigma.toDouble(), new uniform());

        BigNumber randomVariable = new BigNumber(aEnvironment.getPrecision());

        randomVariable.setTo(randomVariableDouble);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(new org.mathpiper.lisp.cons.NumberCons(randomVariable));

    }//end method.

}//end class.



