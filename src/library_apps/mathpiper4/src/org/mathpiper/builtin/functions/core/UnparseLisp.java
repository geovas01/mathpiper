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
import org.mathpiper.lisp.unparsers.LispUnparser;

/**
 *
 * 
 */
public class UnparseLisp extends BuiltinFunction
{

    private UnparseLisp()
    {
    }

    public UnparseLisp(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        setTopOfStack(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 1));
        LispUnparser printer = new LispUnparser();
        printer.print(aStackTop, getTopOfStack(aEnvironment, aStackTop), aEnvironment.iCurrentOutput, aEnvironment, false);
        aEnvironment.write("\n");
    }
}



/*
%mathpiper_docs,name="UnparseLisp",categories="Programming Functions;Input/Output;Built In"
*CMD UnparseLisp --- print an expression in LISP-format
*CORE
*CALL
	UnparseLisp(expr)

*PARMS

{expr} -- expression to be printed in LISP-format

*DESC

Evaluates "expr", and prints it in LISP-format on the current
output. It is followed by a newline. The evaluated expression is also
returned.

This can be useful if you want to study the internal representation of
a certain expression.

*E.G. notest

In> UnparseLisp(a+b+c);
Result: (a+b)+c
Side Effects:
(+ (+ a b) c)

In> UnparseLisp(2*I*b^2);
Result: Complex(0,2)*b^2
Side Effects:
(* (Complex 0 2) (^ b 2))

The first example shows how the expression {a+b+c} is
internally represented. In the second example, {2*I} is
first evaluated to {Complex(0,2)} before the expression
is printed.

*SEE ParseLisp, ViewList
%/mathpiper_docs
*/