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
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 *  
 */
public class Head extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        UtilityFunctions.nth(getTopOfStackPointer(aEnvironment, aStackTop), getArgumentPointer(aEnvironment, aStackTop, 1), 1);
    }
}



/*
%mathpiper_docs,name="Head",categories="User Functions;Lists (Operations);Built In"
*CMD Head --- the first element of a list
*CORE
*CALL
	Head(list)

*PARMS

{list} -- a list

*DESC

This function returns the first element of a list. If it is applied to
a general expression, it returns the first operand. An error is
returned if "list" is an atom.

*E.G.

	In> Head({a,b,c})
	Out> a;
	In> Head(f(a,b,c));
	Out> a;

*SEE Tail, Length
%/mathpiper_docs
*/