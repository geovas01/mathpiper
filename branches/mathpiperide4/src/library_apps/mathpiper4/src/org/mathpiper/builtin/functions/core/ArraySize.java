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

import org.mathpiper.builtin.Array;
import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class ArraySize extends BuiltinFunction
{
    
    private ArraySize()
    {
    }

    public ArraySize(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons evaluated = getArgument(aEnvironment, aStackTop, 1);

        BuiltinContainer gen = (BuiltinContainer) evaluated.car();
        if(gen == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        if(! gen.typeName().equals("\"Array\"")) LispError.checkArgument(aEnvironment, aStackTop, 1);
        int size = ((Array) gen).size();
        setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "" + size));
    }
}//end class.



/*
%mathpiper_docs,name="ArraySize",categories="Programming Functions;Native Objects;Built In"
*CMD ArraySize --- get array size
*CORE
*CALL
	ArraySize(array)

*DESC
Returns the size of an array (number of elements in the array).

%/mathpiper_docs
*/
