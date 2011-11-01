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
public class RulebaseHoldArguments extends BuiltinFunction
{

    private RulebaseHoldArguments()
    {
    }

    public RulebaseHoldArguments(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        org.mathpiper.lisp.Utility.rulebase(aEnvironment, aStackTop, false);
    }
}



/*
%mathpiper_docs,name="RulebaseHoldArguments",categories="Programmer Functions;Programming;Built In"
*CMD RulebaseHoldArguments --- define function with a fixed number of arguments
*CORE
*CALL
	RulebaseHoldArguments(name,params)

*PARMS

{name} -- string, name of function

{params} -- list of arguments to function

*DESC
Define a new rules table entry for a
function "name", with {params} as the parameter list. Name can be
either a string or simple atom.

In the context of the transformation rule declaration facilities
this is a useful function in that it allows the stating of argument
names that can he used with HoldArgument.

Functions can be overloaded: the same function can be defined
with different number of arguments.


*SEE MacroRulebase, RulebaseListedHoldArguments, MacroRulebaseListed, HoldArgument, Retract
%/mathpiper_docs
*/