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
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 *  
 */
public class Length extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer subList = getArgumentPointer(aEnvironment, aStackTop, 1).getCons().getSublistPointer();
        if (subList != null)
        {
            int num = UtilityFunctions.listLength(subList.getCons().getRestPointer());
            getResult(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, "" + num));
            return;
        }
        String string = getArgumentPointer(aEnvironment, aStackTop, 1).getCons().string();
        if (UtilityFunctions.internalIsString(string))
        {
            int num = string.length() - 2;
            getResult(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, "" + num));
            return;
        }
        BuiltinContainer gen = getArgumentPointer(aEnvironment, aStackTop, 1).getCons().getGeneric();
        if (gen != null)
        {
            if (gen.typeName().equals("\"Array\""))
            {
                int size = ((Array) gen).size();
                getResult(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, "" + size));
                return;
            }
        //  CHK_ISLIST_CORE(aEnvironment,aStackTop,getArgumentPointer(aEnvironment, aStackTop, 1),1);
        }
    }
}
