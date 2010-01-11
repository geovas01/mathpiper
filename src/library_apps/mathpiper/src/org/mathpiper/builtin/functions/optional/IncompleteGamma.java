

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.cern.Gamma;
import org.mathpiper.lisp.Environment;



public class IncompleteGamma extends BuiltinFunction{

    public void plugIn(Environment aEnvironment)
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "IncompleteGamma");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        BigNumber a = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);

        BigNumber x = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 2);

        double resultValue = Gamma.incompleteGammaComplement(x.toDouble(),  a.toDouble());

        BigNumber result = new BigNumber(aEnvironment.getPrecision());

        result.setTo(resultValue);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(new org.mathpiper.lisp.cons.NumberCons(aEnvironment, result));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="IncompleteGamma",categories="User Functions;Statistics & Probability"
*CMD IncompleteGamma --- the incomplete gamma function
*CORE
*CALL
    IncompleteGamma(a, x)

*PARMS
{a} -- the parameter of the gamma distribution

{x} -- the integration end point

*DESC

The incomplete gamma function.

*E.G.
In> IncompleteGamma(2.5,3.6)
Result> 0.3188972206


%/mathpiper_docs
*/
