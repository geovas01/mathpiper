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
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.Cons;


/**
 *
 *  
 */
public class StringMidGet extends BuiltinFunction
{

    private StringMidGet()
    {
    }

    public StringMidGet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        
        Cons evaluated = getArgument(aEnvironment, aStackTop, 3);
        LispError.checkIsString(aEnvironment, aStackTop, evaluated, 3);
        String orig = (String) evaluated.car();

        Cons index = getArgument(aEnvironment, aStackTop, 1);
        if( index  == null) LispError.checkArgument(aEnvironment, aStackTop, 1);
        if(! (index.car() instanceof String)) LispError.checkArgument(aEnvironment, aStackTop, 1);
        int from = Integer.parseInt( (String) index.car(), 10);
        if( from <= 0) LispError.checkArgument(aEnvironment, aStackTop, 1);

        index = getArgument(aEnvironment, aStackTop, 2);
        if( index == null) LispError.checkArgument(aEnvironment, aStackTop, 2);
        if(! (index.car() instanceof String)) LispError.checkArgument(aEnvironment, aStackTop, 2);
        int count = Integer.parseInt( (String) index.car(), 10);


        String str = "\"" + orig.substring(from, from + count) + "\"";
        setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, str));
    }
}



/*
%mathpiper_docs,name="StringMidGet",categories="User Functions;String Manipulation;Built In"
*CMD StringMidGet --- retrieve a substring
*CORE
*CALL
	StringMidGet(index,length,string)

*PARMS

{index} -- index of substring to get

{length} -- length of substring to get

{string} -- string to get substring from

*DESC

{StringMidGet} returns a part of a string. Substrings can also be
accessed using the {[]} operator.

*E.G.

In> StringMidGet(3,2,"abcdef")
Result: "cd";

In> "abcdefg"[2 .. 4]
Result: "bcd";

*SEE StringMidSet, Length
%/mathpiper_docs
*/