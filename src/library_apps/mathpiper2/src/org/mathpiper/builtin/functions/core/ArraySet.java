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
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;

import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class ArraySet extends BuiltinFunction
{
    
    private ArraySet()
    {
    }

    public ArraySet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        Cons evaluated = getArgumentPointer(aEnvironment, aStackTop, 1);

        BuiltinContainer gen = (BuiltinContainer) evaluated.car();
        if( gen == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "ArraySet");
        if(! gen.typeName().equals("\"Array\"")) LispError.checkArgument(aEnvironment, aStackTop, 1, "ArraySet");

        Cons sizearg = getArgumentPointer(aEnvironment, aStackTop, 2);

        if(sizearg == null) LispError.checkArgument(aEnvironment, aStackTop, 2, "ArraySet");
        if(! (sizearg.car() instanceof String)) LispError.checkArgument(aEnvironment, aStackTop, 2, "ArraySet");

        int size = Integer.parseInt( (String) sizearg.car(), 10);
        if( size <= 0 || size > ((Array) gen).size()) LispError.checkArgument(aEnvironment, aStackTop, 2, "ArraySet");

        Cons obj = getArgumentPointer(aEnvironment, aStackTop, 3);
        ((Array) gen).setElement(size, obj, aStackTop, aEnvironment);
        setTopOfStackPointer(aEnvironment, aStackTop, Utility.putTrueInPointer(aEnvironment));
    }
}//end class.



/*
%mathpiper_docs,name="ArraySet",categories="Programmer Functions;Native Objects;Built In"
*CMD ArraySet --- set array element
*CORE
*CALL
	ArraySet(array,index,element)

*DESC
Sets the element at position index in the array passed to the value
passed in as argument to element. Arrays are treated
as base-one, so {index} set to 1 would set car element.

Arrays can also be accessed through the {[]} operators. So
{array[index] := element} would do the same as {ArraySet(array, index,element)}.

%/mathpiper_docs
*/
