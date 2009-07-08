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

import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 * 
 */
public class Write extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        if (getArgumentPointer(aEnvironment, aStackTop, 1).getCons().first() instanceof ConsPointer) {

            ConsPointer subList = (ConsPointer) getArgumentPointer(aEnvironment, aStackTop, 1).getCons().first();
            
            ConsTraverser consTraverser = new ConsTraverser(subList);
            consTraverser.goNext();
            while (consTraverser.getCons() != null)
            {
                aEnvironment.iCurrentPrinter.print(consTraverser.getPointer(), aEnvironment.iCurrentOutput, aEnvironment);
                consTraverser.goNext();
            }
        }
        UtilityFunctions.internalTrue(aEnvironment, getResult(aEnvironment, aStackTop));
    }
}



/*
%mathpiper_docs,name="Write",categories="User Functions;Input/Output;Built In"
*CMD Write --- low-level printing routine
*CORE
*CALL
	Write(expr, ...)

*PARMS

{expr} -- expression to be printed

*DESC

The expression "expr" is evaluated and written to the current
output. Note that Write accept an arbitrary number of arguments, all
of which are written to the current output (see second
example). {Write} always returns {True}.

*E.G. notest

	In> Write(1);
	1Out> True;
	In> Write(1,2);
	 1 2Out> True;

Write does not write a newline, so the {Out>} prompt
immediately follows the output of {Write}.

*SEE Echo, WriteString
%/mathpiper_docs
*/