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
public class DestructiveReverse extends BuiltinFunction
{

    private DestructiveReverse()
    {
    }

    public DestructiveReverse(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons reversed = aEnvironment.iListAtom.copy(false);
        reversed.setCdr(Utility.reverseList(aEnvironment, ((Cons) getArgument(aEnvironment, aStackTop, 1).car()).cdr()));
        setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment,reversed));
    }
}



/*
%mathpiper_docs,name="DestructiveReverse",categories="Programming Functions;Lists (Operations);Built In"
*CMD DestructiveReverse --- reverse a list destructively
*CORE
*CALL
	DestructiveReverse(list)

*PARMS

{list} -- list to reverse

*DESC

This command reverses "list" in place, so that the original is
destroyed. This means that any variable bound to "list" will now have
an undefined content, and should not be used any more.
The reversed list is returned.

Destructive commands are faster than their nondestructive
counterparts. {Reverse} is the non-destructive version of
this function.

*E.G.

In> lst := [a,b,c,13,19];
Result: [a,b,c,13,19];

In> revlst := DestructiveReverse(lst);
Result: [19,13,c,b,a];

In> lst;
Result: [a];

*SEE FlatCopy, Reverse
%/mathpiper_docs
*/