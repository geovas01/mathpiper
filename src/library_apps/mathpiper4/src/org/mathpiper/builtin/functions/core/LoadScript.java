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
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class LoadScript extends BuiltinFunction
{

    private LoadScript()
    {
    }

    public LoadScript(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        if(aEnvironment.iSecure != false) LispError.throwError(aEnvironment, aStackTop, LispError.SECURITY_BREACH);

        Cons evaluated = getArgument(aEnvironment, aStackTop, 1);

        if(evaluated == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        
        String scriptString = (String) evaluated.car();

        if( scriptString == null) LispError.checkArgument(aEnvironment, aStackTop, 1);

        scriptString = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, scriptString);

        InputStatus status = new InputStatus("LOADSCRIPT_EVALUATE");

        StringInputStream functionInputStream = new StringInputStream(scriptString,status); //aEnvironment.iCurrentInput.iStatus);

        Environment.saveDebugInformation = true;

        Utility.doInternalLoad(aEnvironment, aStackTop, functionInputStream);

        Environment.saveDebugInformation = false;
        
        setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
         
    }
}



/*
%mathpiper_docs,name="LoadScript",categories="Programming Functions;Input/Output;Built In"
*CMD LoadScript --- evaluate all expressions that are in a string
*CORE
*CALL
	LoadScript(string)

*PARMS

{string} -- a string that contains MathPiper code

*DESC

All MathPiper expressions in the string are read and
evaluated. {LoadScript} always returns {True}.

*SEE LoadScriptFile
%/mathpiper_docs
*/