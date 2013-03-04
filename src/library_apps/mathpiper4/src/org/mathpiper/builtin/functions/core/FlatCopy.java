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
public class FlatCopy extends BuiltinFunction
{

    private FlatCopy()
    {
    }

    public FlatCopy(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        
        Cons copied = Utility.flatCopy(aEnvironment, aStackTop, (Cons) getArgument(aEnvironment, aStackTop, 1).car());
        setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment, copied));
    }
}



/*
%mathpiper_docs,name="FlatCopy",categories="User Functions;Lists (Operations);Built In"
*CMD FlatCopy --- copy the top level of a list
*CORE
*CALL
	FlatCopy(list)

*PARMS

{list} -- list to be copied

*DESC

A copy of "list" is made and returned. The list is not recursed
into, only the car level is copied. This is useful in combination
with the destructive commands that actually modify lists in place (for
efficiency).

*E.G.

The following shows a possible way to define a command that reverses a
list nondestructively.

In> reverse(l_List?) <-- DestructiveReverse \
	  (FlatCopy(l));
Result: True;

In> lst := {a,b,c,d,e};
Result: {a,b,c,d,e};

In> reverse(lst);
Result: {e,d,c,b,a};

In> lst;
Result: {a,b,c,d,e};
%/mathpiper_docs





%mathpiper,name="FlatCopy",subtype="automatic_test"

//Reverse and FlatCopy (and some friends) would segfault in the past if passed a string as argument.
//I am not opposed to overloading these functions to also work on strings per se, but for now just
//check that they return an error in stead of segfaulting.
//
Verify(ExceptionCatch(FlatCopy("abc"),"Exception"), "Exception");

%/mathpiper
*/