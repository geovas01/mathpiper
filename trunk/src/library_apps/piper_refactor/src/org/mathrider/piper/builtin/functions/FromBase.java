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

package org.mathrider.piper.builtin.functions;

import org.mathrider.piper.builtin.BigNumber;
import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Standard;

/**
 *
 * @author 
 */
public class FromBase extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        // Get the base to convert to:
        // Evaluate first argument, and store result in oper
        Pointer oper = new Pointer();
        oper.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
        // Check that result is a number, and that it is in fact an integer
        BigNumber num = oper.get().number(aEnvironment.precision());
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, num != null, 1);
        // check that the base is an integer between 2 and 32
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, num.IsInt(), 1);

        // Get a short platform integer from the first argument
        int base = (int) (num.Double());

        // Get the number to convert
        Pointer fromNum = new Pointer();
        fromNum.set(ARGUMENT(aEnvironment, aStackTop, 2).get());
        String str2;
        str2 = fromNum.get().string();
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, str2 != null, 2);

        // Added, unquote a string
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, Standard.internalIsString(str2), 2);
        str2 = aEnvironment.hashTable().lookUpUnStringify(str2);

        // convert using correct base
        BigNumber z = new BigNumber(str2, aEnvironment.precision(), base);
        RESULT(aEnvironment, aStackTop).set(new org.mathrider.piper.lisp.Number(z));
    }
}
