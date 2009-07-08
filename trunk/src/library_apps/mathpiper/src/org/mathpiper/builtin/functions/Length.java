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
        Object subList =getArgumentPointer(aEnvironment, aStackTop, 1).getCons().first();
        if (subList instanceof ConsPointer)
        {
            int num = UtilityFunctions.listLength(((ConsPointer)subList).getCons().getRestPointer());
            getResult(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, "" + num));
            return;
        }
        String string =  (String) getArgumentPointer(aEnvironment, aStackTop, 1).getCons().string();
        if (UtilityFunctions.internalIsString(string))
        {
            int num = string.length() - 2;
            getResult(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, "" + num));
            return;
        }
        BuiltinContainer gen = getArgumentPointer(aEnvironment, aStackTop, 1).getCons().getJavaObject();
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



/*
%mathpiper_docs,name="Length",categories="User Functions;Lists (Operations);Built In"
*CMD Length --- the length of a list or string
*CORE
*CALL
	Length(object)

*PARMS

{object} -- a list, array or string

*DESC

Length returns the length of a list.
This function also works on strings and arrays.

*E.G.

	In> Length({a,b,c})
	Out> 3;
	In> Length("abcdef");
	Out> 6;

*SEE Head, Tail, Nth, Count
%/mathpiper_docs
*/