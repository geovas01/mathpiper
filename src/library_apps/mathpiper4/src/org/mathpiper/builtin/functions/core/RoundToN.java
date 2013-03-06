/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.builtin.functions.core;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.NumberCons;
import org.mathpiper.lisp.cons.SublistCons;

/**
 *
 *
 */
public class RoundToN extends BuiltinFunction
{

    private RoundToN()
    {
    }

    public RoundToN(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {

        BigNumber requestedPrecision = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 2);



        Cons argument1 = getArgument(aEnvironment, aStackTop, 1);

        if(argument1 instanceof NumberCons)
        {

            BigNumber decimalToBeRounded = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);

            if(decimalToBeRounded.getPrecision() != requestedPrecision.toInt())
            {
                decimalToBeRounded.setPrecision(requestedPrecision.toInt());
            }

            setTopOfStack(aEnvironment, aStackTop, new org.mathpiper.lisp.cons.NumberCons(decimalToBeRounded));

            return;

        }
        else if (argument1 instanceof SublistCons)
        {
            Cons cons = argument1;

            cons = (Cons) cons.car();

            String functionName = ((String) cons.car());

            if(functionName.equals("Complex"))
            {
                cons = cons.cdr();

                BigNumber realPart = (BigNumber) ((NumberCons) cons).getNumber(aEnvironment.iPrecision, aEnvironment);

                if(realPart.getPrecision() != requestedPrecision.toInt())
                {
                    realPart.setPrecision(requestedPrecision.toInt());
                }//end if.

                cons = cons.cdr();

                BigNumber imaginaryPart = (BigNumber) ((NumberCons) cons).getNumber(aEnvironment.iPrecision, aEnvironment);

                if(imaginaryPart.getPrecision() != requestedPrecision.toInt())
                {
                    imaginaryPart.setPrecision(requestedPrecision.toInt());
                }//end if.



                Cons complexAtomCons = AtomCons.getInstance(aEnvironment, aStackTop, "Complex");

                Cons realNumberCons = new NumberCons(realPart);

                complexAtomCons.setCdr(realNumberCons);

                Cons imaginaryNumberCons = new NumberCons(imaginaryPart);

                realNumberCons.setCdr(imaginaryNumberCons);

                Cons complexSublistCons = SublistCons.getInstance(aEnvironment, complexAtomCons);

                setTopOfStack(aEnvironment, aStackTop, complexSublistCons);
                
                return;
                
            }//end if.


        }//end else.

        LispError.raiseError("The first argument must be a number.", aStackTop, aEnvironment);

    }//end method.


}//end class.



/*
%mathpiper_docs,name="RoundToN",categories="Mathematics Functions;Numeric;Built In"
*CMD RoundToN --- rounds a decimal number to a given precision
*CORE
*CALL
	RoundToN(decimalNumber, precision)

*PARMS
{decimalNumber} -- a decimal number to be rounded
{precision} -- precision to round the number to

*DESC

This command rounds a decimal number to a given precision.

*E.G.
In> RoundToN(7.57809824,2)
Result> 7.6

%/mathpiper_docs
*/
