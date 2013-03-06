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

import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *
 */
public class ToString extends BuiltinFunction
{

    private ToString()
    {
    }

    public ToString(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons expression = getArgument(aEnvironment, aStackTop, 1);


        if(expression  == null) LispError.checkArgument(aEnvironment, aStackTop, 1);

        String resultString = null;
        
        if(expression.car() instanceof String)
        {
            if(Utility.isString(expression.car()))
            {
               resultString = (String) expression.car();
            }
            else
            {
        	resultString = Utility.toMathPiperString(aEnvironment, aStackTop, (String) expression.car());
            }
        }
        else if(expression instanceof SublistCons)
        {
            resultString =  Utility.toMathPiperString(aEnvironment, aStackTop, Utility.printMathPiperExpression(aStackTop, expression, aEnvironment, 0));
        }
        else if(expression.car() instanceof BuiltinContainer)
        {
            BuiltinContainer container = (BuiltinContainer) expression.car();
            
            resultString = Utility.toMathPiperString(aEnvironment, aStackTop,container.getObject().toString());
        }

        if( resultString == null) LispError.checkArgument(aEnvironment, aStackTop, 1);

        setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, resultString));
    }
}



/*
%mathpiper_docs,name="ToString",categories="Programming Functions;String Manipulation;Built In"
*CMD ToString --- convert atom to string
*CORE
*CALL
	ToString(expression)

*PARMS

{expression} -- an expression

*DESC

This function converts a MathPiper expression into a string. If the expression is already a string,
it is returned unchanged.

*E.G.
In> ToString(a)
Result: "a";

In> ToString(x^2)
Result> "x^2"

*SEE ToAtom
%/mathpiper_docs





%mathpiper,name="ToString",subtype="automatic_test"

 Verify(ToString(a),"a");
 Verify(ToString(x^2),"x^2");

%/mathpiper
*/
