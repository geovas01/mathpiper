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
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *
 */
public class LoadLibraryFunction extends BuiltinFunction
{

    private LoadLibraryFunction()
    {
    }

    public LoadLibraryFunction(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        if(aEnvironment.iSecure != false) LispError.throwError(aEnvironment, aStackTop, LispError.SECURITY_BREACH);

        Cons evaluated = getArgumentPointer(aEnvironment, aStackTop, 1);

        // Get file name
        if(evaluated == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        String scriptString = (String) evaluated.car();

        if( scriptString == null) LispError.checkArgument(aEnvironment, aStackTop, 1);

        scriptString = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, scriptString);

        Utility.loadFunction(scriptString, aEnvironment, aStackTop);

        setTopOfStackPointer(aEnvironment, aStackTop, Utility.putTrueInPointer(aEnvironment));

    }
}



/*
%mathpiper_docs,name="LoadLibraryFunction",categories="User Functions;Input/Output;Built In"
*CMD LoadLibraryFunction --- load a function from the library.
*CORE
*CALL
	LoadLibraryFunction(string)

*PARMS

{string} -- a string which contains a function name

*DESC

Loads a function from the library.

%/mathpiper_docs
*/