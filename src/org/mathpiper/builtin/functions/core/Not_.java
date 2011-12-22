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

import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

/**
 *
 *  
 */
public class Not_ extends BuiltinFunction
{

    private Not_()
    {
    }

    public Not_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        Cons evaluated = getArgument(aEnvironment, aStackTop, 1);

        if (Utility.isTrue(aEnvironment, evaluated, aStackTop) || Utility.isFalse(aEnvironment, evaluated, aStackTop))
        {
            setTopOfStack(aEnvironment, aStackTop, Utility.not(aStackTop, aEnvironment, evaluated));
        } else
        {
            Cons ptr = getArgument(aEnvironment, aStackTop, 0).copy(false);
            ptr.setCdr(evaluated);
            setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment,ptr));
        }
    }
}



/*
%mathpiper_docs,name="Not?",categories="User Functions;Predicates;Built In"
*CMD Not? --- logical negation
*CORE
*CALL
	Not? expr

*PARMS

{expr} -- a boolean expression

*DESC

Not? returns the logical negation of the argument expr. If {expr} is
{False} it returns {True}, and if {expr} is {True}, {Not? expr} returns {False}.
If the argument is neither {True} nor {False}, it returns the entire
expression with evaluated arguments.

*E.G.

In> Not? True
Result: False;
In> Not? False
Result: True;
In> Not?(a)
Result: Not? a;

*SEE And?, Or?
%/mathpiper_docs





%mathpiper,name="Not?",subtype="automatic_test"

Verify(Not?(True),False);
Verify(Not?(False),True);

%/mathpiper

*/