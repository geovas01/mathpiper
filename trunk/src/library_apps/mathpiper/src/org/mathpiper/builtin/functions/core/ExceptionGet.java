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
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.BuiltinObjectCons;

/**
 *
 *  
 */
public class ExceptionGet extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        if(aEnvironment.iException == null)
        {
            Utility.putFalseInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
        }
        else
        {
            JavaObject response = new JavaObject(aEnvironment.iException);
            getTopOfStackPointer(aEnvironment, aStackTop).setCons(BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));
        }
    }
}



/*
%mathpiper_docs,name="ExceptionGet",categories="Programmer Functions;Built In"
*CMD ExceptionGet --- returns the exception object which was thrown.
*CORE
*CALL
	ExceptionGet()

*DESC

ExceptionGet returns the exception object which was thrown.
ExceptionCatch and ExceptionGet can be used in combination to write
a custom error handler error reporting facility that does not stop the execution is provided by the function {Assert}.

**E.G.

	In>

*SEE Assert, Check, TrapError

%/mathpiper_docs
*/