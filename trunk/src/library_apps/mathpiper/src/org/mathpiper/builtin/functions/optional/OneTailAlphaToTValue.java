

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.cern.Probability;
import org.mathpiper.lisp.Environment;



public class OneTailAlphaToTValue extends BuiltinFunction{

    public void plugIn(Environment aEnvironment)
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "OneTailAlphaToTValue");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        BigNumber degreesOfFreedom = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);

        BigNumber alpha = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 2);

        double cdf = Probability.studentTInverse(alpha.toDouble()*2, (int) degreesOfFreedom.toLong());

        BigNumber tValue = new BigNumber(aEnvironment.getPrecision());

        tValue.setTo(cdf);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(new org.mathpiper.lisp.cons.NumberCons(aEnvironment, tValue));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="OneTailAlphaToTValue",categories="User Functions;Statistics & Probability"
*CMD OneTailAlphaToTValue --- show the function help window

*CALL

    OneTailAlphaToTValue(degreesOfFreedom, alpha)

*PARMS

{degreesOfFreedom} -- integer, the degrees of freedom

{alpha} -- the one tailed alpha value

*DESC

Calculates the t value for the given one tail alpha value and degrees of freedom.

*E.G.
In> OneTailAlphaToTValue(9,.025)
Result> 2.262157163


%/mathpiper_docs
*/