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

/**
 *
 *
 */
public class StringToUnicode extends BuiltinFunction
{

    private StringToUnicode()
    {
    }

    public StringToUnicode(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        if( getArgumentPointer(aEnvironment, aStackTop, 1) == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "StringToUnicode");
        String str = (String) getArgumentPointer(aEnvironment, aStackTop, 1).car();
        if( str == null) LispError.checkArgument(aEnvironment, aStackTop, 1, "StringToUnicode");
        if(str.length() != 3) LispError.throwError(aEnvironment, aStackTop, "The string must be one character long.", "StringToUnicode");
        if(str.charAt(0) != '\"') LispError.checkArgument(aEnvironment, aStackTop, 1, "StringToUnicode");
        if(str.charAt(str.length() - 1) != '\"') LispError.checkArgument(aEnvironment, aStackTop, 1, "StringToUnicode");

        int unicodeValue = (int) str.charAt(1);

        setTopOfStackPointer(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "" + unicodeValue));
    }
}



/*
%mathpiper_docs,name="StringToUnicode",categories="User Functions;String Manipulation;Built In",access="experimental"
*CMD StringToUnicode --- returns the unicode value of the character in a single character string
*CORE
*CALL
	StringToUnicode(s)

*PARMS
 {s} - a single character string

*DESC
This function returns the unicode value of the character in a single character string.

*E.G.
In> StringToUnicode("A")
Result> 65

%/mathpiper_docs
*/




