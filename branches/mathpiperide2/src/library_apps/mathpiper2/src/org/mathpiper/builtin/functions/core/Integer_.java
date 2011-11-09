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
public class Integer_ extends BuiltinFunction
{

    private Integer_()
    {
    }

    public Integer_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        Cons result = getArgumentPointer(aEnvironment, aStackTop, 1);

//        LispError.check(result.type().equals("Number"), LispError.KLispErrInvalidArg);
        BigNumber num = (BigNumber) result.getNumber(aEnvironment.getPrecision(), aEnvironment);
        if (num == null)
        {
            setTopOfStackPointer(aEnvironment, aStackTop, Utility.putFalseInPointer(aEnvironment));
        } else
        {
            setTopOfStackPointer(aEnvironment, aStackTop, Utility.putBooleanInPointer(aEnvironment, num.isInteger()));
        }
    }
}



/*
%mathpiper_docs,name="Integer?",categories="User Functions;Predicates;Built In"
*CMD Integer? --- test to see if a number is an integer
*CORE
*CALL
	Integer?(expr)

*PARMS

{expr} -- expression to test

*DESC

This function tests whether "expr" is an integer number. There are two kinds
of numbers, integers (e.g. 6) and decimals (e.g. -2.75 or 6.0).
*E.G.

In> Integer?(6);
Result: True;

In> Integer?(3.25);
Result: False;

In> Integer?(1/2);
Result: False;

In> Integer?(3.2/10);
Result: False;

*SEE String?, Atom?, Integer?, Decimal?, PositiveNumber?, NegativeNumber?, Number?
%/mathpiper_docs





%mathpiper,name="Integer?",subtype="automatic_test"

Verify(Integer?(123),True);
Verify(Integer?(123.123),False);
Verify(Integer?(a),False);
Verify(Integer?({a}),False);

%/mathpiper
 
*/