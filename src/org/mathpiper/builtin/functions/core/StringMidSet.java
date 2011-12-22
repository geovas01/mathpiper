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

import org.mathpiper.builtin.*;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.Cons;


/**
 *
 *  
 */
public class StringMidSet extends BuiltinFunction
{

    private StringMidSet()
    {
    }

    public StringMidSet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        Cons evaluated = getArgument(aEnvironment, aStackTop, 3);
        LispError.checkIsString(aEnvironment, aStackTop, evaluated, 3);
        String orig = (String) evaluated.car();

        Cons index = getArgument(aEnvironment, aStackTop, 1);
        if(index  == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        if(! (index.car() instanceof String)) LispError.checkArgument(aEnvironment, aStackTop, 1);
        int from = Integer.parseInt( (String) index.car(), 10);

        if( from <= 0) LispError.checkArgument(aEnvironment, aStackTop, 1);

        Cons ev2 = getArgument(aEnvironment, aStackTop, 2);
        LispError.checkIsString(aEnvironment, aStackTop, ev2, 2);
        String replace =(String)  ev2.car();

        if(from + replace.length() - 2 >= orig.length()) LispError.throwError(aEnvironment, LispError.INVALID_ARGUMENT);
        String str;
        str = orig.substring(0, from);
        str = str + replace.substring(1, replace.length() - 1);
        //System.out.println("from="+from+replace.length()-2);
        str = str + orig.substring(from + replace.length() - 2, orig.length());
        setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, str));
    }
}



/*
%mathpiper_docs,name="StringMidSet",categories="User Functions;String Manipulation;Built In"
*CMD StringMidSet --- change a substring
*CORE
*CALL
	StringMidSet(index,substring,string)

*PARMS

{index} -- index of substring to get

{substring} -- substring to store

{string} -- string to store substring in.

*DESC

Set (change) a part of a string. It leaves the original alone, returning
a new changed copy.

*E.G.

In> StringMidSet(3,"XY","abcdef")
Result: "abXYef";

*SEE StringMidGet, Length
%/mathpiper_docs
*/