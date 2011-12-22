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
import org.mathpiper.lisp.Utility;

/**
 *
 *  
 */
public class UnicodeToString extends BuiltinFunction
{

    private UnicodeToString()
    {
    }

    public UnicodeToString(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        String str;
        str =  (String) getArgument(aEnvironment, aStackTop, 1).car();
        if( str == null) LispError.checkArgument(aEnvironment, aStackTop, 2);
        if(! Utility.isNumber(str, false)) LispError.checkArgument(aEnvironment, aStackTop, 2);
        char asciiCode = (char) Integer.parseInt(str, 10);
        setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, "\"" + asciiCode + "\""));
    }
}



/*
%mathpiper_docs,name="UnicodeToString",categories="User Functions;String Manipulation;Built In",access="experimental"
*CMD UnicodeToString --- creates a single character string from the character's unicode value
*CORE
*CALL
	UnicodeToString(n)
 
*PARMS
 {n} - a unicode value

*DESC
This function creates a single character string from the character's unicode value.

*E.G.
In> UnicodeToString(65)
Result> "A"

%/mathpiper_docs
*/