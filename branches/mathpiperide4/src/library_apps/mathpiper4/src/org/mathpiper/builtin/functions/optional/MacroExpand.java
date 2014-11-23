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

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;

import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.unparsers.MathPiperUnparser;

/**
 *
 *
 */
public class MacroExpand extends BuiltinFunction
{
    public void plugIn(Environment aEnvironment) throws Throwable
    {
        this.functionName = "MacroExpand";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
	
	aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "MacroExpand");
    }//end method.


    //todo:tk:this function is not complete yet.  It currently only expands backquoted expressions.
    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        org.mathpiper.lisp.astprocessors.BackQuoteSubstitute behaviour = new org.mathpiper.lisp.astprocessors.BackQuoteSubstitute(aEnvironment);

        //Cons argument = getArgumentPointer(aEnvironment, aStackTop, 1);

        Cons argumentCons = getArgument(aEnvironment, aStackTop, 1);

        Cons argument = ((Cons) argumentCons.car()).cdr();

        Cons result = Utility.substitute(aEnvironment, aStackTop, argument, behaviour);

        String substitutedResult = Utility.printMathPiperExpression(aStackTop, result, aEnvironment, 0);

        aEnvironment.write(substitutedResult);
	
	aEnvironment.write("\n");

         setTopOfStack(aEnvironment, aStackTop, aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, result));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="MacroExpand",categories="Programming Functions;Built In;Programming",access="experimental"
*CMD MacroExpand --- shows the expanded form of a macro
*CALL
    MacroExpand() macro

*PARMS
{macro} -- a macro to expand

*DESC
This function shows the expanded form of the Lisp-like macros that MathPiper supports.
Note: only back quoted macros are supported at this time.

*E.G.
//Assign the variable var to the atom Echo.
In> var := 'Echo;
Result: Echo

//Show the macro in expanded form.
In> MacroExpand()`(@var(2,"Hello"))
Result: True
Side Effects:
Echo(2,"Hello")
2 Hello

//Execute the macro.
In> `(@var(2,"Hello"))
Result: True
Side Effects:
2 Hello

*SEE `
%/mathpiper_docs
*/