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
import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.lisp.Environment;

/**
 *
 *  
 */
public class CustomEvalResult extends BuiltinFunction
{

    private CustomEvalResult()
    {
    }

    public CustomEvalResult(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        aEnvironment.write("Function not yet implemented : CustomEvalResult");////TODO fixme

        throw new EvaluationException("Function not yet supported",aEnvironment.getCurrentInput().iStatus.getSourceName(), aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1, aEnvironment.getCurrentInput().iStatus.getLineIndex());
    }
}

/*
%mathpiper_docs,name="CustomEvalResult",categories="Programming Functions;Native Objects;Built In"
*CMD CustomEvalResult --- Not yet implemented
*CORE
*CALL
	CustomEvalResult()

*DESC
Not yet implemented

%/mathpiper_docs
*/