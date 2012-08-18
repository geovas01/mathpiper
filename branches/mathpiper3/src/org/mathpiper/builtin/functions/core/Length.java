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

import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class Length extends BuiltinFunction
{

    private Length()
    {
    }

    public Length(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        Object argument = getArgument(aEnvironment, aStackBase, 1).car();
        

        if (argument instanceof Cons)
        {
            int num = Utility.listLength(aEnvironment, aStackBase, (((Cons)argument).cdr()));
            setTopOfStack(aEnvironment, aStackBase, AtomCons.getInstance(aEnvironment, aStackBase, "" + num));
            return;
        }//end if.
        
        
        
        if (argument instanceof BuiltinContainer)
        {
            BuiltinContainer gen = (BuiltinContainer) getArgument(aEnvironment, aStackBase, 1).car();
            if (gen.typeName().equals("\"Array\""))
            {
                int size = ((Array) gen).size();
                setTopOfStack(aEnvironment, aStackBase, AtomCons.getInstance(aEnvironment, aStackBase, "" + size));
                return;
            }
        //  CHK_ISLIST_CORE(aEnvironment,aStackBase,getArgumentPointer(aEnvironment, aStackBase, 1),1);
        }//end if.



        if(! (argument instanceof String)) 
        {
            LispError.throwError(aEnvironment, aStackBase, LispError.INVALID_ARGUMENT, argument);
        }
        
        String string =  (String) argument;
        if (Utility.isString(string))
        {
            int num = string.length() - 2;
            setTopOfStack(aEnvironment, aStackBase, AtomCons.getInstance(aEnvironment, aStackBase, "" + num));
            return;
        }//end if.
        

    }//end method..

}//end class.



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
Result: 3;
In> Length("abcdef");
Result: 6;

*SEE First, Rest, Nth, Count
%/mathpiper_docs
*/