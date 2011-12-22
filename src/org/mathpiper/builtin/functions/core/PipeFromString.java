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
import org.mathpiper.io.InputStatus;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;


/**
 *
 * 
 */
public class PipeFromString extends BuiltinFunction
{

    private PipeFromString()
    {
    }

    public PipeFromString(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {

        int stackTop = aEnvironment.iArgumentStack.getStackTopIndex();
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, getArgument(aEnvironment, aStackTop, 1));
        Cons evaluated = aEnvironment.iArgumentStack.getElement(stackTop, aEnvironment);
        aEnvironment.iArgumentStack.popTo(stackTop, aEnvironment);

        // Get file name
        if( evaluated == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        String orig =  (String) evaluated.car();
        if( orig == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        String oper = Utility.toNormalString(aEnvironment, orig);

        InputStatus oldstatus = aEnvironment.getCurrentInput().iStatus;
        aEnvironment.getCurrentInput().iStatus.setTo("String");
        StringInputStream newInput = new StringInputStream(oper, aEnvironment.getCurrentInput().iStatus);

        MathPiperInputStream previous = aEnvironment.getCurrentInput();
        aEnvironment.setCurrentInput(newInput);
        try
        {
            // Evaluate the body
            stackTop = aEnvironment.iArgumentStack.getStackTopIndex();
            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, getArgument(aEnvironment, aStackTop, 2));
            Cons aResult = aEnvironment.iArgumentStack.getElement(stackTop, aEnvironment);
            aEnvironment.iArgumentStack.popTo(stackTop, aEnvironment);
            setTopOfStack(aEnvironment, aStackTop, aResult);
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            aEnvironment.setCurrentInput(previous);
            aEnvironment.getCurrentInput().iStatus.restoreFrom(oldstatus);
        }

    //Return the getTopOfStackPointer
    }
}



/*
%mathpiper_docs,name="PipeFromString",categories="User Functions;Input/Output;Built In"
*CMD PipeFromString --- connect current input to a string
*CORE
*CALL
	PipeFromString(str) body;

*PARMS

{str} -- a string containing the text to parse

{body} -- expression to be evaluated

*DESC

The commands in "body" are executed, but everything that is read
from the current input is now read from the string "str". The
result of "body" is returned.

*E.G.

In> PipeFromString("2+5; this is never read") \
	  res := Read();
Result: 2+5;
In> PipeFromString("2+5; this is never read") \
	  res := Eval(Read());
Result: 7;

*SEE PipeToString, PipeFromFile, Read, ReadToken
%/mathpiper_docs
*/