

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.cern.Gamma;
import org.mathpiper.lisp.Environment;



public class IncompleteGamma extends BuiltinFunction{

    public void plugIn(Environment aEnvironment) throws Exception
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

        setTopOfStackPointer(aEnvironment, aStackTop, new org.mathpiper.lisp.cons.NumberCons(result));

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
In> IncompleteBeta(.2,.2,.2)
Result> 0.3927221644


%/mathpiper_docs
*/
