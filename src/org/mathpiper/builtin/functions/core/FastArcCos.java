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
public class FastArcCos extends BuiltinFunction
{

    private FastArcCos()
    {
    }

    public FastArcCos(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        BigNumber x;

        x = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 1);

        double xDouble = x.toDouble();

        double result = Math.acos(xDouble);

        if(Double.isNaN(result))
        {
            LispError.raiseError("The argument must have a value between -1 and 1.", aStackBase, aEnvironment);
        }

        BigNumber z = new BigNumber(aEnvironment.iPrecision);

        z.setTo(result);

        setTopOfStack(aEnvironment, aStackBase, new org.mathpiper.lisp.cons.NumberCons(z));
    }
}//end class.




/*
%mathpiper,name="FastArcCos",categories="Programmer Functions;Built In"
*CMD FastArcCos --- double-precision math function
*CORE
*CALL
	FastArcCos(x)

*PARMS
{a} -- a number

*DESC
This function uses the Java math library. It
should be faster than the arbitrary precision version.

*SEE FastLog, FastPower

%/mathpiper_docs
*/
