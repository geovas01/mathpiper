
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
import org.mathpiper.builtin.functions.optional.support.FileInputStream;
import org.mathpiper.io.InputStatus;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;


public class LoadScriptFile extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment) throws Throwable
    {
        this.functionName = "LoadScriptFile";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {

        if(aEnvironment.iSecure != false) LispError.throwError(aEnvironment, aStackTop, LispError.SECURITY_BREACH);

        Cons evaluated = getArgument(aEnvironment, aStackTop, 1);

        if(evaluated == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        
        String fileName = (String) evaluated.car();

        if( fileName == null) LispError.checkArgument(aEnvironment, aStackTop, 1);

        fileName = Utility.stripEndQuotesIfPresent(fileName);

        InputStatus status = new InputStatus("File: " + fileName);

        FileInputStream functionInputStream = new FileInputStream(fileName, status); //aEnvironment.iCurrentInput.iStatus);

        Environment.saveDebugInformation = true;

        Utility.doInternalLoad(aEnvironment, aStackTop, functionInputStream);

        Environment.saveDebugInformation = false;
        
        setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="LoadScriptFile",categories="Programming Functions;Input/Output;Built In"
*CMD LoadScriptFile --- evaluate all expressions that are in a file
*CORE
*CALL
	LoadScriptFile(fileName)

*PARMS

{fileName} -- a string that contains the path and name of a file that contains MathPiper code

*DESC

All MathPiper expressions in the file are read and
evaluated. {LoadScriptFile} always returns {True}.

*SEE LoadScript
%/mathpiper_docs
*/
