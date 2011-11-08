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
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class Retract extends BuiltinFunction
{

    private Retract()
    {
    }

    public Retract(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        // Get operator
        Cons evaluated = getArgumentPointer(aEnvironment, aStackTop, 1);

        if( evaluated == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "Retract");
        String orig = (String) evaluated.car();

        orig = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, orig);
        
        if( orig == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "Retract");
        String oper = Utility.getSymbolName(aEnvironment, orig);

        Cons arityPointer = getArgumentPointer(aEnvironment, aStackTop, 2);
        if(!(arityPointer.car() instanceof String)) LispError.checkArgument(aEnvironment, aStackTop, 2, "Retract");
        String arityString = (String) arityPointer.car();
        if(arityString.equalsIgnoreCase("*"))
        {
            aEnvironment.retractRule(oper, -1, aStackTop, aEnvironment);
        }
        else
        {
            int arity = Integer.parseInt(arityString, 10);
            aEnvironment.retractRule(oper, arity, aStackTop, aEnvironment);
        }
  
        setTopOfStackPointer(aEnvironment, aStackTop, Utility.putTrueInPointer(aEnvironment));
    }
}



/*
%mathpiper_docs,name="Retract",categories="User Functions;Built In"
*CMD Retract --- erase rules for a function
*CORE
*CALL
	Retract("function",arity)

*PARMS
{"function"} -- string, name of function

{arity} -- positive integer or *

*DESC

Remove a rulebase for the function named {"function"} with the specific {arity}, if it exists at all. This will make
MathPiper forget all rules defined for a given function with the given arity. Rules for functions with
the same name but different arities are not affected unless the * wildcard character is used.  If * is used for the
arity, then all arities of the rulebase are removed.

Assignment {:=} of a function automatically does a single arity retract to the function being (re)defined.

*SEE RulebaseArgumentsList, RulebaseHoldArguments, :=
%/mathpiper_docs
*/