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

import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class FromBase extends BuiltinFunction
{

    private FromBase()
    {
    }

    public FromBase(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        // Get the base to convert to:
        // Evaluate car argument, and store getTopOfStackPointer in oper
        Cons oper = getArgument(aEnvironment, aStackTop, 1);
        // check that getTopOfStackPointer is a number, and that it is in fact an integer
//        LispError.check(oper.type().equals("Number"), LispError.KLispErrInvalidArg);
        BigNumber num = (BigNumber)  oper.getNumber(aEnvironment.iPrecision, aEnvironment);
        if( num == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        // check that the base is an integer between 2 and 32
        if(! num.isInteger()) LispError.checkArgument(aEnvironment, aStackTop, 1);

        // Get a short platform integer from the car argument
        int base = (int) (num.toDouble());

        // Get the number to convert
        Cons fromNum = getArgument(aEnvironment, aStackTop, 2);
        String str2;
        str2 =  (String) fromNum.car();
        if( str2 == null) LispError.checkArgument(aEnvironment, aStackTop, 2);

        // Added, unquote a string
        if(! Utility.isString(str2)) LispError.checkArgument(aEnvironment, aStackTop, 2);
        str2 = Utility.stripEndQuotesIfPresent(aEnvironment, -1, str2);

        // convert using correct base
        BigNumber z = new BigNumber(str2, aEnvironment.iPrecision, base);
        setTopOfStack(aEnvironment, aStackTop, new org.mathpiper.lisp.cons.NumberCons(z));
    }
}

/*
%mathpiper_docs,name="FromBase",categories="User Functions;Numbers (Operations);Built In"
*CMD FromBase --- conversion of a number from non-decimal base to decimal base
*CORE
*CALL
	FromBase(base,"string")

*PARMS

{base} -- integer, base to convert to/from

{number} -- integer, number to write out in a different base

{"string"} -- string representing a number in a different base

*DESC

In MathPiper, all numbers are written in decimal notation (base 10).


{FromBase} converts an integer, written as a string in base
{base}, to base 10. {ToBase} converts {number},
written in base 10, to base {base}.

*REM where is this p-adic capability? - sw
These functions use the p-adic expansion capabilities of the built-in
arbitrary precision math libraries.

Non-integer arguments are not supported.

*E.G.

Write the binary number {111111} as a decimal number:

In> FromBase(2,"111111")
Result: 63;


*SEE PAdicExpand,ToBase
%/mathpiper_docs





%mathpiper,name="FromBase",subtype="automatic_test"

Verify(FromBase(16,"1e"),30);

%/mathpiper
*/
