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

/**
 *
 *  
 */
public class List_ extends BuiltinFunction
{

    private List_()
    {
    }

    public List_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        Cons result = getArgument(aEnvironment, aStackTop, 1);
        setTopOfStack(aEnvironment, aStackTop, Utility.getBooleanAtom(aEnvironment, Utility.isSublist(result)));
    }
}



/*
%mathpiper_docs,name="List?",categories="User Functions;Predicates;Built In"
*CMD List? --- test for a list
*CORE
*CALL
	List?(expr)

*PARMS

{expr} -- expression to test

*DESC

This function tests whether "expr" is a list. A list is a sequence
between curly braces, e.g. {{2, 3, 5}}.

*E.G.

In> List?({2,3,5});
Result: True;
In> List?(2+3+5);
Result: False;

*SEE Function?
%/mathpiper_docs





%mathpiper,name="List?",subtype="automatic_test"

Verify(List?({a,b,c}),True);
Verify(List?(a),False);

%/mathpiper
*/