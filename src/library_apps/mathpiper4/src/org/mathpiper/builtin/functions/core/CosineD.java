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
public class CosineD extends BuiltinFunction
{

    private CosineD()
    {
    }

    public CosineD(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        BigNumber x;

        x = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);

        double xDouble = x.toDouble();

        double result = Math.cos(xDouble);

        if(Double.isNaN(result))
        {
            LispError.raiseError("The result is NaN.", aStackTop, aEnvironment);
        }

        BigNumber z = new BigNumber(aEnvironment.getPrecision());

        z.setTo(result);

        setTopOfStack(aEnvironment, aStackTop, new org.mathpiper.lisp.cons.NumberCons(z));
    }
}//end class.




/*
%mathpiper_docs,name="CosineD",categories="Mathematics Functions;Numeric;Trigonometry (Numeric)"
*CMD CosineD --- double-precision math function
*CORE
*CALL
	CosineD(x)

*PARMS
{x} -- a number

*DESC
A numerical version of the Cosine function. The reason for the postfix {N} is the library needs 
to define equivalent non-numerical functions for symbolic computations, such as {Sine}.

*SEE SineD, TangentD, ArcSineD, ArcCosineD, ArcTangentD

*E.G.
In> CosineD(.7)
Result: 0.7648421873

%/mathpiper_docs
*/
