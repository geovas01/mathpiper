/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.builtin.functions;

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;

/**
 *
 *  
 */
	public class Floor extends BuiltinFunction
	{
		public void evaluate(Environment aEnvironment,int aStackTop) throws Exception
		{
			BigNumber x = org.mathpiper.lisp.UtilityFunctions.getNumber(aEnvironment, aStackTop, 1);
			BigNumber z = new BigNumber(aEnvironment.getPrecision());
			z.floor(x);
			getResult(aEnvironment, aStackTop).setCons(new org.mathpiper.lisp.cons.NumberCons(z));
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
	Out> 63;


*SEE PAdicExpand,ToBase
%/mathpiper_docs
 */