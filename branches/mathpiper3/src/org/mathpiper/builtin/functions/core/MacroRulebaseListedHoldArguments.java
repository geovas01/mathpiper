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

/**
 *
 *  
 */
public class MacroRulebaseListedHoldArguments extends BuiltinFunction
{

    private MacroRulebaseListedHoldArguments()
    {
    }

    public MacroRulebaseListedHoldArguments(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        org.mathpiper.lisp.Utility.defineMacroRulebase(aEnvironment, aStackBase, true);
    }
}



/*
%mathpiper_docs,name="MacroRulebaseListedHoldArguments",categories="Programmer Functions;Programming;Built In"
*CMD MacroRulebaseListedHoldArguments --- define macro with variable number of arguments
*CORE
*CALL
	MacroRulebaseListedHoldArguments("name", params)

*PARMS

{"name"} -- string, name of function

{params} -- list of arguments to function

*DESC

This does the same as {MacroRulebaseHoldArguments} (define a macro), but with a variable
number of arguments, similar to {RulebaseListedHoldArguments}.

*SEE RulebaseHoldArguments, RulebaseListedHoldArguments, `, MacroRulebaseHoldArguments
%/mathpiper_docs
*/