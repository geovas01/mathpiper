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

import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;

/**
 *
 *  
 */
public class ShiftRight extends BuiltinFunction
{

    private ShiftRight()
    {
    }

    public ShiftRight(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        BigNumber x = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 1);
        BigNumber n = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackTop, 2);
        long nrToShift = n.toLong();
        BigNumber z = new BigNumber(aEnvironment.getPrecision());
        z.shiftRight(x, (int) nrToShift, null, aStackTop);
        setTopOfStack(aEnvironment, aStackTop, new org.mathpiper.lisp.cons.NumberCons(z));
    }
}//end class.




/*
%mathpiper_docs,name="ShiftRight",categories="User Functions;Built In"
*CMD ShiftRight --- built-in bitwise shift right operation
*CORE
*CALL
	ShiftRight(expr,bits)

*DESC

Shift bits to the right.

*SEE ShiftLeft
%/mathpiper_docs
*/