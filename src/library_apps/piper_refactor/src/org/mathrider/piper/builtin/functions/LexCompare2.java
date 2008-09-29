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
import org.mathrider.piper.lisp.HashTable;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.Standard;

/**
 *
 *  
 */
abstract public class LexCompare2
{

    abstract boolean lexfunc(String f1, String f2, HashTable aHashTable, int aPrecision);

    abstract boolean numfunc(BigNumber n1, BigNumber n2);

    void Compare(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer result1 = new ConsPointer();
        ConsPointer result2 = new ConsPointer();
        result1.set(BuiltinFunction.argument(aEnvironment, aStackTop, 1).get());
        result2.set(BuiltinFunction.argument(aEnvironment, aStackTop, 2).get());
        boolean cmp;
        BigNumber n1 = result1.get().number(aEnvironment.precision());
        BigNumber n2 = result2.get().number(aEnvironment.precision());
        if (n1 != null && n2 != null)
        {
            cmp = numfunc(n1, n2);
        } else
        {
            String str1;
            String str2;
            str1 = result1.get().string();
            str2 = result2.get().string();
            LispError.checkArgumentCore(aEnvironment, aStackTop, str1 != null, 1);
            LispError.checkArgumentCore(aEnvironment, aStackTop, str2 != null, 2);
            // the precision argument is ignored in "lex" functions
            cmp = lexfunc(str1, str2,
                    aEnvironment.hashTable(),
                    aEnvironment.precision());
        }

        Standard.internalBoolean(aEnvironment, BuiltinFunction.result(aEnvironment, aStackTop), cmp);
    }
}
