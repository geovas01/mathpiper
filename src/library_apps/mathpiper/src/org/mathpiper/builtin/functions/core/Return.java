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
*CMD Return --- returns from a code block
*CORE
*CALL

    Return()

*DESC

If Return is executed inside of a code block, the code block will immediately
return and the result of the last expression which was executed inside of the
code block will be the result that is returned from the code block.  This
function can be used to return from functions if the function uses a code
block to enclose the expressions which implement it.

*E.G.

/%mathpiper

TestFunction() :=
[
    Echo("1");

    3;

    Return();

    Echo("2");
];


TestFunction();

/%/mathpiper

    /%output,preserve="false"
      Result: 3

      Side Effects:
      1
      2
.   /%/output

*SEE Prog, [, ]
%/mathpiper_docs
*/