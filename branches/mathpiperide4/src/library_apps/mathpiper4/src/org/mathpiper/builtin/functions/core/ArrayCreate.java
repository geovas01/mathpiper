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
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.Cons;


/**
 *
 *  
 */
public class ArrayCreate extends BuiltinFunction
{
    
    private ArrayCreate()
    {
    }

    public ArrayCreate(String functionName)
    {
        this.functionName = functionName;
    }

    
    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons sizearg = getArgument(aEnvironment, aStackTop, 1);

        if( sizearg == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        if(! (sizearg.car() instanceof String)) LispError.checkArgument(aEnvironment, aStackTop, 1);

        int size = Integer.parseInt( (String) sizearg.car(), 10);

        Cons initarg = getArgument(aEnvironment, aStackTop, 2);

        Array array = new Array(aEnvironment, size);
        Cons initializeCons = initarg;

        for(int index = 1; index <= size; index++)
        {
            array.setElement(index, initializeCons, aStackTop, aEnvironment);
        }
        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, array));
    }
}//end class.



/*
%mathpiper_docs,name="ArrayCreate",categories="Programming Functions;Native Objects;Built In"
*CMD ArrayCreate --- create array
*CORE
*CALL
	ArrayCreate(size,init)

*DESC
Creates an array with {size} elements, all initialized to the
value {init}.

%/mathpiper_docs
*/
