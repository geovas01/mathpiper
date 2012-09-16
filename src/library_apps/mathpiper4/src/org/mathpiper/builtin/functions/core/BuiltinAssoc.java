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
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;

import org.mathpiper.lisp.Utility;


/**
 *
 *  
 */
public class BuiltinAssoc extends BuiltinFunction
{

    private BuiltinAssoc()
    {
    }

    public BuiltinAssoc(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
        // key to find
        Cons key = getArgument(aEnvironment, aStackTop, 1);

        // assoc-list to find it in
        Cons list = getArgument(aEnvironment, aStackTop, 2);

        Cons listCons;

        //check that it is a compound object
        if(! (list.car() instanceof Cons)) LispError.checkArgument(aEnvironment, aStackTop, 2);
        listCons = (Cons) list.car();
        if( listCons == null) LispError.checkArgument(aEnvironment, aStackTop, 2);
        listCons = listCons.cdr();

        Cons result = Utility.associativeListGet(aEnvironment, aStackTop, key, listCons);

        if (result != null) {
            setTopOfStack(aEnvironment, aStackTop, result);

        } else {
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "Empty"));
        }


    }//end method.


}//end class.

