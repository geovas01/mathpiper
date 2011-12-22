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

/**
 *
 *  
 */
public class UnFence extends BuiltinFunction
{

    private UnFence()
    {
    }

    public UnFence(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        // Get operator
        if(getArgument(aEnvironment, aStackBase, 1) == null) LispError.checkArgument(aEnvironment, aStackBase, 1);
        String orig = (String) getArgument(aEnvironment, aStackBase, 1).car();
        if( orig == null) LispError.checkArgument(aEnvironment, aStackBase, 1);

        // The arity
        if(getArgument(aEnvironment, aStackBase, 2) == null) LispError.checkArgument(aEnvironment, aStackBase, 2);
        if(! (getArgument(aEnvironment, aStackBase, 2).car() instanceof String)) LispError.checkArgument(aEnvironment, aStackBase, 2);
        int arity = Integer.parseInt( (String) getArgument(aEnvironment, aStackBase, 2).car(), 10);

        aEnvironment.unfenceRule(aStackBase, Utility.getSymbolName(aEnvironment, orig), arity);

        // Return true
        setTopOfStack(aEnvironment, aStackBase, Utility.getTrueAtom(aEnvironment));
    }
}



/*
%mathpiper_docs,name="UnFence",categories="User Functions;Built In"
*CMD UnFence --- change local variable scope for a function
*CORE
*CALL
	UnFence("operator",arity)

*PARMS
{"operator"} -- string, name of function

{arity} -- positive integers

*DESC

When applied to a user function, the bodies
defined for the rules for "operator" with given
arity can see the local variables from the calling
function. This is useful for defining macro-like
procedures (looping and such).

The standard library functions {For} and {ForEach} use {UnFence}.
%/mathpiper_docs
*/