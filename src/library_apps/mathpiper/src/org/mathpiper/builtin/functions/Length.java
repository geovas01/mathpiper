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
package org.mathpiper.builtin.functions;

import org.mathpiper.builtin.Array;
import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Atom;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 *  
 */
public class Length extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer subList = argumentPointer(aEnvironment, aStackTop, 1).get().subList();
        if (subList != null)
        {
            int num = UtilityFunctions.internalListLength(subList.get().cdr());
            result(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, "" + num));
            return;
        }
        String string = argumentPointer(aEnvironment, aStackTop, 1).get().string();
        if (UtilityFunctions.internalIsString(string))
        {
            int num = string.length() - 2;
            result(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, "" + num));
            return;
        }
        BuiltinContainer gen = argumentPointer(aEnvironment, aStackTop, 1).get().generic();
        if (gen != null)
        {
            if (gen.typeName().equals("\"Array\""))
            {
                int size = ((Array) gen).size();
                result(aEnvironment, aStackTop).set(Atom.getInstance(aEnvironment, "" + size));
                return;
            }
        //  CHK_ISLIST_CORE(aEnvironment,aStackTop,argumentPointer(aEnvironment, aStackTop, 1),1);
        }
    }
}
