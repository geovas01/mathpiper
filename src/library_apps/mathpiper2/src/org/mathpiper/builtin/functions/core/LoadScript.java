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
import org.mathpiper.io.StringInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.Utility;

/**
 *
 *  
 */
public class LoadScript extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        LispError.check(aEnvironment, aStackTop, aEnvironment.iSecure == false, LispError.SECURITY_BREACH);

        ConsPointer evaluated = new ConsPointer();
        evaluated.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());

        // Get file name
        LispError.checkArgument(aEnvironment, aStackTop, evaluated.getCons() != null, 1, "LoadScript");
        String scriptString = (String) evaluated.car();

        LispError.checkArgument(aEnvironment, aStackTop, scriptString != null, 1, "LoadScript");

        scriptString = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, scriptString);

        StringInputStream functionInputStream = new StringInputStream(scriptString, aEnvironment.iCurrentInput.iStatus);

        Utility.doInternalLoad(aEnvironment, aStackTop, functionInputStream);
        
        Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
         
    }
}



/*
%mathpiper_docs,name="LoadScript",categories="User Functions;Input/Output;Built In"
*CMD LoadScript --- evaluate all expressions in a string
*CORE
*CALL
	LoadScript(string)

*PARMS

{string} -- a string which contains MathPiper code

*DESC

All MathPiper expressions in {string} are read and
evaluated. {LoadScript} always returns {true}.

*SEE DefLoad, DefaultDirectory, FindFile
%/mathpiper_docs
*/