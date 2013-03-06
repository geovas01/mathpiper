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
public class Rest extends BuiltinFunction
{

    private Rest()
    {
    }

    public Rest(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons first = Utility.tail(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 1));
        setTopOfStack(aEnvironment, aStackTop, Utility.tail(aEnvironment, aStackTop, first));
        Cons head = aEnvironment.iListAtom.copy(false);
        head.setCdr(((Cons) getTopOfStack(aEnvironment, aStackTop).car()));
        getTopOfStack(aEnvironment, aStackTop).setCar(head);
    }
}



/*
%mathpiper_docs,name="Rest",categories="Programming Functions;Lists (Operations);Built In"
*CMD Rest --- returns a list without its car element
*CORE
*CALL
	Rest(list)

*PARMS

{list} -- a list

*DESC

This function returns "list" without its car element.

*E.G.

In> Rest([a,b,c])
Result: [b,c];

*SEE First, Length
%/mathpiper_docs
*/