

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.cern.Probability;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;



public class OneTailAlphaToTScore extends BuiltinFunction{

    public void plugIn(Environment aEnvironment)
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "OneTailAlphaToTScore");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        BigNumber degreesOfFreedom = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);

        LispError.check(degreesOfFreedom.isInteger() && degreesOfFreedom.toInt() >= 0, "The first argument must be an integer which is greater than 0.");

        BigNumber alpha = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 2);

        LispError.check(alpha.toDouble() >= 0, "The second argument must be greater than 0.");

        double cdf = Probability.studentTInverse(alpha.toDouble()*2, (int) degreesOfFreedom.toLong());

        BigNumber tScore = new BigNumber(aEnvironment.getPrecision());

        tScore.setTo(cdf);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(new org.mathpiper.lisp.cons.NumberCons(aEnvironment, tScore));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="OneTailAlphaToTScore",categories="User Functions;Statistics & Probability"
*CMD OneTailAlphaToTScore --- convert a one-tail alpha to a t-score

*CALL

    OneTailAlphaToTScore(degreesOfFreedom, alpha)

*PARMS

{degreesOfFreedom} -- integer, the degrees of freedom

{alpha} -- the one tailed alpha value

*DESC

Calculates the t value for the given one tail alpha value and degrees of freedom.

*E.G.
In> OneTailAlphaToTScore(9,.025)
Result> 2.262157163


%/mathpiper_docs
*/