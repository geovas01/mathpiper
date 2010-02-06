

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.jscistats.FDistribution;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;



public class ProbabilityToFScore extends BuiltinFunction{

    public void plugIn(Environment aEnvironment)
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ProbabilityToFScore");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        BigNumber degreesOfFreedom1 = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);

        LispError.check(degreesOfFreedom1.isInteger() && degreesOfFreedom1.toInt() >= 0, "The first argument must be an integer which is greater than 0.", "ProbabilityToFScore", aStackTop, aEnvironment);

        BigNumber degreesOfFreedom2 = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 2);

        LispError.check(degreesOfFreedom2.isInteger() && degreesOfFreedom2.toInt() >= 0, "The second argument must be an integer which is greater than 0.", "ProbabilityToFScore", aStackTop, aEnvironment);

        BigNumber probability = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 3);

        LispError.check(probability.toDouble() >= 0, "The third argument must be greater than 0.", "ProbabilityToFScore", aStackTop, aEnvironment);


        FDistribution fDistribution = new FDistribution(degreesOfFreedom1.toDouble(),degreesOfFreedom2.toDouble());

        double fScoreValue = fDistribution.inverse(probability.toDouble());

        BigNumber fScore = new BigNumber(aEnvironment.getPrecision());

        probability.setTo(fScoreValue);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(new org.mathpiper.lisp.cons.NumberCons(probability));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="ProbabilityToFScore",categories="User Functions;Statistics & Probability"
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