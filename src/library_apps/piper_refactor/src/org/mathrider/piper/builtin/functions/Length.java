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

import org.mathrider.piper.builtin.Array;
import org.mathrider.piper.builtin.BuiltinContainer;
import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.lisp.Atom;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.Utility;

/**
 *
 *  
 */
public class Length extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer subList = argument(aEnvironment, aStackTop, 1).get().subList();
        if (subList != null)
        {
            int num = Utility.internalListLength(subList.get().cdr());
            result(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, "" + num));
            return;
        }
        String string = argument(aEnvironment, aStackTop, 1).get().string();
        if (Utility.internalIsString(string))
        {
            int num = string.length() - 2;
            result(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, "" + num));
            return;
        }
        BuiltinContainer gen = argument(aEnvironment, aStackTop, 1).get().generic();
        if (gen != null)
        {
            if (gen.typeName().equals("\"Array\""))
            {
                int size = ((Array) gen).size();
                result(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, "" + size));
                return;
            }
        //  CHK_ISLIST_CORE(aEnvironment,aStackTop,argument(aEnvironment, aStackTop, 1),1);
        }
    }
}
