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
import org.mathpiper.exceptions.ReturnException;
import org.mathpiper.lisp.Environment;

/**
 *
 *
 */
public class Return extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
         throw new ReturnException();
    }

}//end class.



/*
%mathpiper_docs,name="Return",categories="User Functions;Control Flow;Built In"
*CMD Return --- return from a function
*CORE
*CALL

    Return(value)

*DESC

Return from a function.

*E.G.

/%mathpiper

TestFunction() :=
[
    Echo("1");

    If(True, Return(3));

    Echo("2");
];


TestFunction();

/%/mathpiper

    /%output,preserve="false"
      Result: 3

      Side Effects:
      1
.   /%/output

*SEE Prog, [, ]
%/mathpiper_docs
*/