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

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons; /*PKHG added*/
import org.mathpiper.lisp.cons.Cons;


/**
 *
 *  
 */
public class BuiltinPrecisionSet extends BuiltinFunction
{

    private BuiltinPrecisionSet()
    {
    }

    public BuiltinPrecisionSet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons index = getArgument(aEnvironment, aStackTop, 1);
        if( index == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        if(! (index.car() instanceof String)) LispError.checkArgument(aEnvironment, aStackTop, 1);

        try
        {
	        int ind = Integer.parseInt( (String) index.car(), 10);
	        
	        if( ind <= 0) LispError.checkArgument(aEnvironment, aStackTop, 1);
 setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "" + aEnvironment.iPrecision));
	        aEnvironment.setPrecision(ind);

		//PKHG>>080314 setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
        }
        catch(Exception e)
        {
        	LispError.checkArgument(aEnvironment, aStackTop, 1);
        }
    }
}



/*
%mathpiper_docs,name="BuiltinPrecisionSet",categories="Programming Functions;Numerical (Arbitrary Precision);Built In"
*CMD BuiltinPrecisionSet --- set the precision
*CORE
*CALL
	BuiltinPrecisionSet(n)

*PARMS

{n} -- integer, new value of precision

returns old precsion!

*DESC

This command sets the number of decimal digits to be used in calculations.
All subsequent floating point operations will allow for
at least {n} digits of mantissa.

This is not the number of digits after the decimal point.
For example, {123.456} has 3 digits after the decimal point and 6 digits of mantissa.
The number {123.456} is adequately computed by specifying {BuiltinPrecisionSet(6)}.

The call {BuiltinPrecisionSet(n)} will not guarantee that all results are precise to {n} digits.

When the precision is changed, all variables containing previously calculated values
remain unchanged.
The {BuiltinPrecisionSet} function only makes all further calculations proceed with a different precision.

Also, when typing floating-point numbers, the current value of {BuiltinPrecisionSet} is used to implicitly determine the number of precise digits in the number.

*E.G.

In> NM(Sin(1))
Result: 0.8414709848;

In> BuiltinPrecisionSet(20)
Result: 10;

In> x:=NM(Sin(1))
Result: 0.84147098480789650665;

The value {x} is not changed by a {BuiltinPrecisionSet()} call:

In> [ BuiltinPrecisionSet(10); x; ]
Result: 0.84147098480789650665;

The value {x} is rounded off to 10 digits after an arithmetic operation:

In> x+0.
Result: 0.8414709848;

In the above operation, {0.} was interpreted as a number which is precise to 10 digits (the user does not need to type {0.0000000000} for this to happen).
So the result of {x+0.} is precise only to 10 digits.

*SEE BuiltinPrecisionGet, N

%/mathpiper_docs
*/

