

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.cern.Probability;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;



public class OneTailAlphaToTScore extends BuiltinFunction{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        this.functionName = "OneTailAlphaToTScore";
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                this.functionName);
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        BigNumber degreesOfFreedom = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);

        if(!degreesOfFreedom.isInteger() || degreesOfFreedom.toInt() < 0) LispError.throwError(aEnvironment, aStackTop, "The first argument must be an integer which is greater than 0.");

        BigNumber alpha = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 2);

        if(alpha.toDouble() < 0 || alpha.toDouble() > .5) LispError.throwError(aEnvironment, aStackTop, "The second argument must be greater than 0 and less than or equal to .5.");

        double cdf = Probability.studentTInverse(alpha.toDouble()*2, (int) degreesOfFreedom.toLong());

        BigNumber tScore = new BigNumber(aEnvironment.getPrecision());

        tScore.setTo(cdf);

        setTopOfStackPointer(aEnvironment, aStackTop, new org.mathpiper.lisp.cons.NumberCons(tScore));

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