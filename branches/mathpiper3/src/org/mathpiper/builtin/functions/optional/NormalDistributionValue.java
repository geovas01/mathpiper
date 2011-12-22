

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.cern.Probability;
import org.mathpiper.builtin.library.statdistlib.Normal;
import org.mathpiper.builtin.library.statdistlib.Uniform;
import org.mathpiper.lisp.Environment;



public class NormalDistributionValue extends BuiltinFunction{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        this.functionName = "NormalDistributionValue";
        aEnvironment.iBuiltinFunctions.setAssociation(
                this.functionName, new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        BigNumber mean = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 1);

        //LispError.check(mean.isInteger() && mean.toInt() >= 0, "The first argument must be an integer which is greater than 0.", "NormalDistributionValue", aStackBase, aEnvironment);

        BigNumber sigma = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 2);

        //LispError.check(sigma.toDouble() >= 0, "The second argument must be greater than 0.", "NormalDistributionValue", aStackBase, aEnvironment);

        double randomVariableDouble = Normal.random(mean.toDouble(), sigma.toDouble(), new Uniform());

        BigNumber randomVariable = new BigNumber(aEnvironment.iPrecision);

        randomVariable.setTo(randomVariableDouble);

        setTopOfStack(aEnvironment, aStackBase, new org.mathpiper.lisp.cons.NumberCons(randomVariable));

    }//end method.

}//end class.



/*
%mathpiper_docs,name="NormalDistributionValue",categories="User Functions;Built In;Statistics & Probability",access="experimental
*CMD NormalDistributionValue --- returns a value from the normal distribution
*CALL
    NormalDistributionValue(mean, standardDeviation)

*PARMS
{mean} -- the mean of the distribution
{standardDeviation} -- the standard deviation of the distribution

*DESC
This function returns a value from the given normal distribution.

*E.G.
In> NormalDistributionValue(3,2)
Result> 5.440398494

%/mathpiper_docs
*/