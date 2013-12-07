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
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *
 */
public class Decimal_ extends BuiltinFunction
{

    private Decimal_()
    {
    }

    public Decimal_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons result = getArgument(aEnvironment, aStackTop, 1);

        Object cons = result.getNumber(aEnvironment.getPrecision(), aEnvironment);

        BigNumber bigNumber;
        if(cons instanceof BigNumber)
        {
            bigNumber = (BigNumber) cons;

             setTopOfStack(aEnvironment, aStackTop, Utility.getBooleanAtom(aEnvironment,  bigNumber.isDecimal()));
        }
        else
        {
            setTopOfStack(aEnvironment, aStackTop, Utility.getFalseAtom(aEnvironment));
        }


    }
}



/*
%mathpiper_docs,name="Decimal?",categories="Programming Functions;Predicates;Built In"
*CMD Decimal? --- test to see if a number is a decimal
*CORE
*CALL
	Decimal?(expr)

*PARMS

{expr} -- expression to test

*DESC

This function tests whether "expr" is a decimal number. There are two kinds
of numbers, integers (e.g. 6) and decimals (e.g. -2.75 or 6.0).
*E.G.

In> Decimal?(3.25);
Result: True;

In> Decimal?(6);
Result: False;

In> Decimal?(1/2);
Result: False;

In> Decimal?(3.2/10);
Result: True;

*SEE String?, Atom?, Integer?, PositiveNumber?, NegativeNumber?, Number?
%/mathpiper_docs
*/