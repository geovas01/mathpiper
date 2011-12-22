

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.jscistats.FDistribution;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;



public class FScoreToProbability extends BuiltinFunction{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        this.functionName = "FScoreToProbability";
        aEnvironment.iBuiltinFunctions.setAssociation(
                this.functionName, new BuiltinFunctionEvaluator(this, 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        BigNumber degreesOfFreedom1 = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 1);

        if(!degreesOfFreedom1.isInteger() || degreesOfFreedom1.toInt() < 0) LispError.throwError(aEnvironment, aStackBase, "The first argument must be an integer which is greater than 0.");

        BigNumber degreesOfFreedom2 = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 2);

        if(!degreesOfFreedom2.isInteger() || degreesOfFreedom2.toInt() < 0) LispError.throwError(aEnvironment, aStackBase,  "The second argument must be an integer which is greater than 0.");

        BigNumber fScore = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 3);

        if(fScore.toDouble() < 0) LispError.throwError(aEnvironment, aStackBase, "The third argument must be greater than 0.");

        
        FDistribution fDistribution = new FDistribution(degreesOfFreedom1.toDouble(),degreesOfFreedom2.toDouble());

        double probability = fDistribution.cumulative(fScore.toDouble());

        BigNumber cumulativeProbability = new BigNumber(aEnvironment.iPrecision);

        cumulativeProbability.setTo(probability);

        setTopOfStack(aEnvironment, aStackBase, new org.mathpiper.lisp.cons.NumberCons(cumulativeProbability));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="FScoreToProbability",categories="User Functions;Statistics & Probability"
*CMD FScoreToProbability --- calculates the cumulative probability for a given f-score

*CALL

    FScoreToProbability(degreesOfFreedom1, degreesOfFreedom2, fScore)

*PARMS
{degreesOfFreedom1} -- integer, the first degree of freedom

{degreesOfFreedom2} -- integer, the second degree of freedom

{fScore} -- the fScore

*DESC
Calculates the cumulative probability for a given f-score.

*E.G.
In> FScoreToProbability(1,1,161.448)
Result> 0.9500000557


%/mathpiper_docs
*/