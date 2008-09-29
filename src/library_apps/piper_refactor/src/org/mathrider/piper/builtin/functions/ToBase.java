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
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;

/**
 *
 * @author 
 */
public class ToBase extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        // Get the base to convert to:
        // Evaluate first argument, and store result in oper
        ConsPointer oper = new ConsPointer();
        oper.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
        // check that result is a number, and that it is in fact an integer
        BigNumber num = oper.get().number(aEnvironment.precision());
        LispError.checkArgumentCore(aEnvironment, aStackTop, num != null, 1);
        // check that the base is an integer between 2 and 32
        LispError.checkArgumentCore(aEnvironment, aStackTop, num.IsInt(), 1);

        // Get a short platform integer from the first argument
        int base = (int) (num.Long());

        // Get the number to convert
        BigNumber x = org.mathrider.piper.builtin.Functions.getNumber(aEnvironment, aStackTop, 2);

        // convert using correct base
        String str;
        str = x.ToString(aEnvironment.precision(), base);
        // Get unique string from hash table, and create an atom from it.

        RESULT(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, aEnvironment.hashTable().lookUpStringify(str)));
    }
}
