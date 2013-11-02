/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:

package org.mathpiper.builtin.functions.core;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;

/**
 *
 *
 */
public class ArcCosineN extends BuiltinFunction
{

    private ArcCosineN()
    {
    }

    public ArcCosineN(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        BigNumber x;

        x = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);

        double xDouble = x.toDouble();

        double result = Math.acos(xDouble);

        if(Double.isNaN(result))
        {
            LispError.raiseError("The argument must have a value between -1 and 1.", aStackTop, aEnvironment);
        }

        BigNumber z = new BigNumber(aEnvironment.getPrecision());

        z.setTo(result);

        setTopOfStack(aEnvironment, aStackTop, new org.mathpiper.lisp.cons.NumberCons(z));
    }
}//end class.




/*
%mathpiper_docs,name="ArcCosineN",categories="Mathematics Functions;Numeric;Trigonometry (Numeric)"
*CMD ArcCosineN --- double-precision math function
*CORE
*CALL
	ArcCosineN(x)

*PARMS
{x} -- a decimal number (0 and 1 allowed too).

*DESC
A numerical version of the ArcCosine function. The reason for the postfix {N} is the library needs 
to define equivalent non-numerical functions for symbolic computations, such as {Sine}.
Math.acos(aDouble) is used, result has about 16 decimal places precision.

*SEE SineN, CosineN, TangentN, ArcSineN, ArcTangentN

*E.G.

In> ArcCosineN(0.5)
Result: 1.047197551

In> BuiltinPrecisionSet(40)
Result: True

In> ArcCosineN(0.5)
Result: 1.0471975511965979

In> Cosine(NM(Pi/3))
Result: Cosine(1.047197551196597786975774432671607628066)

In> CosineN(NM(Pi/3))
Result: 0.4999999999999999

In> 

%/mathpiper_docs
*/
