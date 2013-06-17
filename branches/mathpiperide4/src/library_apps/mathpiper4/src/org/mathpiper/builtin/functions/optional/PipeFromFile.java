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
import org.mathpiper.io.InputStatus;
import org.mathpiper.lisp.Environment;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.unparsers.MathPiperUnparser;

/**
 *
 * 
 */
public class PipeFromFile extends BuiltinFunction
{
	
    public void plugIn(Environment aEnvironment) throws Throwable {
        this.functionName = "PipeFromFile";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.HoldArguments));
        
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "PipeFromFile");
    }//end method.

    
    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        if(aEnvironment.iSecure == true) LispError.throwError(aEnvironment, aStackTop, LispError.SECURITY_BREACH);
        
        Cons evaluated = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 1));

        // Get file name
        if(evaluated == null) LispError.checkArgument(aEnvironment, aStackTop,  1);
        String mathPiperStringFilename =  (String) evaluated.car();
        if(mathPiperStringFilename == null) LispError.checkArgument(aEnvironment, aStackTop,  1);
        String fileName = Utility.toNormalString(aEnvironment, aStackTop, mathPiperStringFilename);
        
        InputStatus oldstatus = aEnvironment.getCurrentInput().iStatus;
        MathPiperInputStream previous = aEnvironment.getCurrentInput();
        
        
        
        
        
        try
        {
            // Open file.
            aEnvironment.iInputStatus.setTo(fileName);
            MathPiperInputStream input = org.mathpiper.builtin.functions.optional.support.Utility.openInputFile(fileName, aEnvironment.iInputStatus);
            
            if(input == null) LispError.throwError(aEnvironment, aStackTop, LispError.FILE_NOT_FOUND);
            
            aEnvironment.setCurrentInput(input);

            // Evaluate the body.
            setTopOfStack(aEnvironment, aStackTop, aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 2)));
        } catch (Throwable e)
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
%mathpiper_docs,name="PipeFromFile",categories="Programming Functions;Input/Output;Built In"
*CMD PipeFromFile --- connect current input to a file
*CORE
*CALL
	PipeFromFile(name) body

*PARMS

{name} - string, the name of the file to read

{body} - expression to be evaluated

*DESC

The current input is connected to the file "name". Then the expression
"body" is evaluated. If some functions in "body" try to read
from current input, they will now read from the file "name". Finally, the
file is closed and the result of evaluating "body" is returned.

*E.G.

Suppose that the file foo contains

	2 + 5;

Then we can have the following dialogue:

In> PipeFromFile("foo") res := ParseMathPiper();
Result: 2+5;

In> PipeFromFile("foo") res := ParseMathPiperToken();
Result: 2;

*SEE PipeToFile, PipeFromString, ParseMathPiper, ParseMathPiperToken
%/mathpiper_docs
*/