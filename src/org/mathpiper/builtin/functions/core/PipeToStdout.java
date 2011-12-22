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
import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.lisp.cons.Cons;


/**
 *
 *  
 */
public class PipeToStdout extends BuiltinFunction
{

    private PipeToStdout()
    {
    }

    public PipeToStdout(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        MathPiperOutputStream previous = aEnvironment.iCurrentOutput;
        aEnvironment.iCurrentOutput = aEnvironment.iInitialOutput;
        try
        {
            int oldStackTop = aEnvironment.iArgumentStack.getStackTopIndex();
            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackBase, getArgument(aEnvironment, aStackBase, 1));
            Cons aResult = aEnvironment.iArgumentStack.getElement(oldStackTop, aStackBase, aEnvironment);
            aEnvironment.iArgumentStack.popTo(oldStackTop, aStackBase, aEnvironment);
            setTopOfStack(aEnvironment, aStackBase, aResult);
        } catch (Exception e)
        {
            throw e;
        } finally
        {
            aEnvironment.iCurrentOutput = previous;
        }
    }
}



/*
%mathpiper_docs,name="PipeToStdout",categories="User Functions;Input/Output;Built In"
*CMD PipeToStdout --- select initial output stream for output
*CORE
*CALL
	PipeToStdout() body

*PARMS

{body} -- expression to be evaluated

*DESC

When using {PipeToString} or {PipeToFile}, it might happen that something needs to be
written to the standard default initial output (typically the screen). {PipeToStdout} can be used to select this stream.

*E.G.

In> PipeToString()[Echo("aaaa");PipeToStdout()Echo("bbbb");];
	bbbb
Result: "aaaa
	"

*SEE PipeToString, PipeToFile
%/mathpiper_docs
*/