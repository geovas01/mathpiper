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
package org.mathpiper.builtin.functions;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.Environment;

/**
 *
 *  
 */
public class DestructiveReplace extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        UtilityFunctions.internalReplace(aEnvironment, aStackTop, true);
    }
}



/*
%mathpiper_docs,name="DestructiveReplace",categories="User Functions;Lists (Operations);Built In"
*CMD DestructiveReplace --- replace an entry destructively in a list

*CORE

*CALL
	DestructiveReplace(list, n, expr)

*PARMS

{list} -- list of which an entry should be replaced

{n} -- index of entry to replace

{expr} -- expression to replace the n-th entry with

*DESC

This is the destructive counterpart of {Replace}. This
command yields the same result as the corresponding call to
{Replace}, but the original list is modified. So if a
variable is bound to "list", it will now be bound to the list with
the expression "expr" inserted.

Destructive commands run faster than their nondestructive counterparts
because the latter copy the list before they alter it.

*E.G.

	In> lst := {a,b,c,d,e,f};
	Out> {a,b,c,d,e,f};
	In> Replace(lst, 4, x);
	Out> {a,b,c,x,e,f};
	In> lst;
	Out> {a,b,c,d,e,f};
	In> DestructiveReplace(lst, 4, x);
	Out> {a,b,c,x,e,f};
	In> lst;
	Out> {a,b,c,x,e,f};

*SEE Replace, DestructiveDelete, DestructiveInsert
%/mathpiper_docs
*/