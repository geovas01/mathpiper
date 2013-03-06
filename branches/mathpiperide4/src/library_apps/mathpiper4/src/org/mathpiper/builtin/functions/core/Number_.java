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

import org.mathpiper.lisp.Utility;

/**
 *
 *  
 */
public class Number_ extends BuiltinFunction
{

    private Number_()
    {
    }

    public Number_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        setTopOfStack(aEnvironment, aStackTop, Utility.getBooleanAtom(aEnvironment, getArgument(aEnvironment, aStackTop, 1).getNumber(aEnvironment.iPrecision, aEnvironment) != null));
    }
}



/*
%mathpiper_docs,name="Number?",categories="Programming Functions;Predicates;Built In"
*CMD Number? --- test for a number
*CORE
*CALL
	Number?(expr)

*PARMS

{expr} -- expression to test

*DESC

This function tests whether "expr" is a number. There are two kinds
of numbers, integers (e.g. 6) and reals (e.g. -2.75 or 6.0). Note that a
complex number is represented by the {Complex}
function, so {Number?} will return {False}.  The value {False} will be returned
for all expressions which are lists, but the user should be especially aware of expression
lists which might appear to be numbers, such as those returned by Hold(-1) (see below).
 

*E.G.
In> Number?(6);
Result: True;

In> Number?(3.25);
Result: True;

In> Number?(I);
Result: False;

In> Number?(-1)
Result: True

In> UnparseLisp(-1)
Result: -1
Side Effects:
-1

In> Hold(-1)
Result: -1

In> Number?(Hold(-1))
Result: False

In> UnparseLisp(Hold(-1))
Result: -1
Side Effects:
(- 1 )

In> Number?("rock");
Result: False;

*SEE Atom?, String?, Integer?, Decimal?, PositiveNumber?, NegativeNumber?, Complex?
%/mathpiper_docs





%mathpiper,name="Number?",subtype="automatic_test"

Verify(Number?(123),True);
Verify(Number?(123.123),True);
Verify(Number?(a),False);
Verify(Number?([a]),False);

%/mathpiper
 
*/






