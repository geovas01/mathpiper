

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.cern.Gamma;
import org.mathpiper.lisp.Environment;



public class IncompleteGamma extends BuiltinFunction{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        this.functionName = "IncompleteGamma";
        aEnvironment.iBuiltinFunctions.setAssociation(
                this.functionName, new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        BigNumber a = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 1);

        BigNumber x = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 2);

        double resultValue = Gamma.incompleteGammaComplement(x.toDouble(),  a.toDouble());

        BigNumber result = new BigNumber(aEnvironment.iPrecision);

        result.setTo(resultValue);

        setTopOfStack(aEnvironment, aStackBase, new org.mathpiper.lisp.cons.NumberCons(result));

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
