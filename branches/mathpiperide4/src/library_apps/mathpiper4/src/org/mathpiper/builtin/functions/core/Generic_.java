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

import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class Generic_ extends BuiltinFunction
{

    private Generic_()
    {
    }

    public Generic_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
         Cons evaluated = getArgument(aEnvironment, aStackTop, 1);
         setTopOfStack(aEnvironment, aStackTop, Utility.getBooleanAtom(aEnvironment, evaluated.car() instanceof BuiltinContainer));
    }
}//end class.



/*
%mathpiper_docs,name="Generic?",categories="Programming Functions;Native Objects;Built In"
*CMD Generic? --- check for generic object
*CORE
*CALL
	Generic?(object)

*DESC
Returns {True} if an object is of a generic object type.

%/mathpiper_docs
*/
