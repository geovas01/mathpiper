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
import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;

/**
 *
 *  
 */
public class BitsToDigits extends BuiltinFunction
{

    private BitsToDigits()
    {
    }

    public BitsToDigits(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        BigNumber x = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 1);
        BigNumber y = org.mathpiper.lisp.Utility.getNumber(aEnvironment, aStackBase, 2);
        long result = 0;  // initialize just in case

        if (x.isInteger() && x.isSmall() && y.isInteger() && y.isSmall())
        {
            // bits_to_digits uses unsigned long, see numbers.h
            int base = (int) y.toDouble();
            result = Utility.bitsToDigits((long) (x.toDouble()), base);
        } else
        {
            throw new EvaluationException("BitsToDigits: error: arguments (" + x.toDouble() + ", " + y.toDouble() + ") must be small integers",aEnvironment.getCurrentInput().iStatus.getFileName(), aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1, aEnvironment.getCurrentInput().iStatus.getLineIndex());
        }
        BigNumber z = new BigNumber(aEnvironment.iPrecision);
        z.setTo((long) result);
        setTopOfStack(aEnvironment, aStackBase, new org.mathpiper.lisp.cons.NumberCons(z));
    }
}
