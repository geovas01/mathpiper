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
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.Environment;

/**
 *
 *
 */
public class SystemTimer extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        long currentTime = System.currentTimeMillis();

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, aStackTop, "" + currentTime));
    }//end method.

}//end class.



/*
%mathpiper_docs,name="SystemTimer",categories="User Functions;Built In;Input/Output"
*CMD SystemTimer --- return the current time in nanoseconds
*CORE
*CALL
	SystemTimer()

*DESC
This function returns the current value of the system timer in nanoseconds.

*E.G.
In> SystemTimer()
Result: 1624308347733;

*SEE Time, EchoTime

%/mathpiper_docs
*/