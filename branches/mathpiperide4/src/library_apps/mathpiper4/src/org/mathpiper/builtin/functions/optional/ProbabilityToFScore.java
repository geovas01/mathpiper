

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.jscistats.FDistribution;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;



public class ProbabilityToFScore extends BuiltinFunction{

    public void plugIn(Environment aEnvironment) throws Throwable
    {
        this.functionName = "ProbabilityToFScore";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 3, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        BigNumber degreesOfFreedom1 = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);

        if(!degreesOfFreedom1.isInteger() || degreesOfFreedom1.toInt() < 0) LispError.throwError(aEnvironment, aStackTop, "The first argument must be an integer which is greater than 0.");

        BigNumber degreesOfFreedom2 = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 2);

        if(!degreesOfFreedom2.isInteger() || degreesOfFreedom2.toInt() < 0) LispError.throwError(aEnvironment, aStackTop, "The second argument must be an integer which is greater than 0.");

        BigNumber probability = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 3);

        if(probability.toDouble() < 0) LispError.throwError(aEnvironment, aStackTop, "The third argument must be greater than 0.");


        FDistribution fDistribution = new FDistribution(degreesOfFreedom1.toDouble(),degreesOfFreedom2.toDouble());

        double fScoreValue = fDistribution.inverse(probability.toDouble());

        BigNumber fScore = new BigNumber(aEnvironment.getPrecision());

        probability.setTo(fScoreValue);

        setTopOfStack(aEnvironment, aStackTop, new org.mathpiper.lisp.cons.NumberCons(probability));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="ProbabilityToFScore",categories="Mathematics Functions;Statistics & Probability"
*CMD ProbabilityToFScore --- calculates the f-score for a given cumulative probability

*CALL

    ProbabilityToFScore(degreesOfFreedom1, degreesOfFreedom2, probability)

*PARMS

{degreesOfFreedom1} -- integer, the first degree of freedom

{degreesOfFreedom2} -- integer, the second degree of freedom

{probability} -- the cumulative probability

*DESC
Calculates the calculates the f-score for a given cumulative probability.

*E.G.
In> ProbabilityToFScore(1,1,.95)
Result> 161.4476388


%/mathpiper_docs
*/