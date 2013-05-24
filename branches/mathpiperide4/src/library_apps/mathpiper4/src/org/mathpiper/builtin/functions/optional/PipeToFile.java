
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

import java.io.FileOutputStream;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.io.MathPiperOutputStream;

import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.unparsers.MathPiperUnparser;


public class PipeToFile extends BuiltinFunction
{
	
    public void plugIn(Environment aEnvironment) throws Throwable {
        this.functionName = "PipeToFile";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Macro));
        
        aEnvironment.iBodiedOperators.setOperator(MathPiperUnparser.KMaxPrecedence, "PipeToFile");
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

        // Open file for writing
        FileOutputStream fileOutputStream = new FileOutputStream(fileName, true);

        if(fileOutputStream == null) LispError.throwError(aEnvironment, aStackTop, LispError.FILE_NOT_FOUND);

        org.mathpiper.builtin.functions.optional.support.FileOutputStream newStream = new org.mathpiper.builtin.functions.optional.support.FileOutputStream(fileOutputStream);

        MathPiperOutputStream originalStream = aEnvironment.iCurrentOutput;

        aEnvironment.iCurrentOutput = newStream;

        try
        {
            setTopOfStack(aEnvironment, aStackTop, aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 2)));
        } catch (Throwable e)
        {
            throw e;
        } finally
        {
            fileOutputStream.flush();
            fileOutputStream.close();
            aEnvironment.iCurrentOutput = originalStream;
        }
    }
}



/*
%mathpiper_docs,name="PipeToFile",categories="Programming Functions;Input/Output;Built In"
*CMD PipeToFile --- connect current output to a file
*CORE
*CALL
	PipeToFile(name) body

*PARMS

{name} -- string, the name of the file to write the result to

{body} -- expression to be evaluated

*DESC

The current output is connected to the file "name". Then the expression
"body" is evaluated. Everything that the commands in "body" print
to the current output ends up in the file "name". Finally, the
file is closed and the result of evaluating "body" is returned.

If the file is opened again, the new information will be appended to the
existing information in the file.

*E.G.
In> PipeToFile("test2.txt") WriteString("Hello");
Result: True


*SEE PipeFromFile, PipeToString, Echo, Write, WriteString
%/mathpiper_docs
*/