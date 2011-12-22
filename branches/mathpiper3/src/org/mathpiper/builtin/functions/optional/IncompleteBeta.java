

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.library.cern.Gamma;
import org.mathpiper.lisp.Environment;



public class IncompleteBeta extends BuiltinFunction{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        this.functionName = "IncompleteBeta";
        aEnvironment.iBuiltinFunctions.setAssociation(
                this.functionName, new BuiltinFunctionEvaluator(this, 3, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        BigNumber a = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 1);

        BigNumber b = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 2);

        BigNumber x = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 3);

        double resultValue = Gamma.incompleteBeta(a.toDouble(), b.toDouble(), x.toDouble());

        BigNumber result = new BigNumber(aEnvironment.iPrecision);

        result.setTo(resultValue);

        setTopOfStack(aEnvironment, aStackBase, new org.mathpiper.lisp.cons.NumberCons(result));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="IncompleteBeta",categories="User Functions;Statistics & Probability"
*CMD IncompleteBeta --- the incomplete beta function
*CALL
    IncompleteBeta(a, b, x)

*PARMS
{a} -- the alpha parameter of the beta distribution

{b} -- the beta parameter of the beta distribution

{x} -- the integration end point

*DESC

The incomplete gamma function.

*E.G.
In> IncompleteGamma(2.5,3.6)
Result> 0.3188972206


%/mathpiper_docs
*/
