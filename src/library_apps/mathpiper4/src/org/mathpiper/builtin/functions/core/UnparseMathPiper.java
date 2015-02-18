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
import static org.mathpiper.builtin.BuiltinFunction.getArgument;
import static org.mathpiper.builtin.BuiltinFunction.setTopOfStack;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.unparsers.MathPiperUnparser;

/**
 *
 * 
 */
public class UnparseMathPiper extends BuiltinFunction
{

    private UnparseMathPiper()
    {
    }

    public UnparseMathPiper(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons expression = getArgument(aEnvironment, aStackTop, 1);
        
        setTopOfStack(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 1));
        String string = Utility.toMathPiperString(aEnvironment, aStackTop, Utility.printMathPiperExpression(aStackTop, expression, aEnvironment, 0));
        setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, string));
    }
}



/*
%mathpiper_docs,name="UnparseMathPiper",categories="Programming Functions;Input/Output;Built In"
*CMD UnparseMathPiper --- return an expression as a string in MathPiper format
*CALL
	UnparseMathPiper(expr)

*PARMS

{expr} -- expression to be unparsed to a string

*DESC

Unparses an expression to a string that is in MathPiper format.

*E.G.
In> UnparseMathPiper('(1 + 2))
Result: "1 + 2"

*SEE ParseMathPiper
%/mathpiper_docs




%mathpiper,name="UnparseMathPiper",subtype="automatic_test"


Verify(UnparseMathPiper('(1 + 2)), "1 + 2");

%/mathpiper
*/