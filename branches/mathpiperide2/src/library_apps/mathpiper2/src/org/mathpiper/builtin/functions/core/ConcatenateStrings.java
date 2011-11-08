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
public class ConcatenateStrings extends BuiltinFunction
{

    private ConcatenateStrings()
    {
    }

    public ConcatenateStrings(String functionName)
    {
        this.functionName = functionName;
    }


    void ConcatenateStrings(StringBuffer aStringBuffer, Environment aEnvironment, int aStackTop) throws Exception
    {
        aStringBuffer.append('\"');
        int arg = 1;

        Cons consTraverser =  (Cons) getArgumentPointer(aEnvironment, aStackTop, 1).car();
        consTraverser = consTraverser.cdr();
        while (consTraverser != null)
        {
            LispError.checkIsString(aEnvironment, aStackTop, consTraverser, arg, "ConcatenateStrings");
            String thisString =  (String) consTraverser.car();
            String toAppend = thisString.substring(1, thisString.length() - 1);
            aStringBuffer.append(toAppend);
            consTraverser = consTraverser.cdr();
            arg++;
        }
        aStringBuffer.append('\"');
    }

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        StringBuffer strBuffer = new StringBuffer("");
        ConcatenateStrings(strBuffer, aEnvironment, aStackTop);
        setTopOfStackPointer(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, strBuffer.toString()));
    }
}




/*
%mathpiper_docs,name="ConcatStrings",categories="User Functions;String Manipulation;Built In"
*CMD ConcatStrings --- concatenate strings
*CORE
*CALL
	ConcatStrings(strings)

*PARMS

{strings} -- one or more strings

*DESC

Concatenates strings.

*E.G.

In> ConcatStrings("a","b","c")
Result: "abc";

*SEE :
%/mathpiper_docs





%mathpiper,name="ConcatStrings",subtype="automatic_test"

Verify(ConcatStrings("a","b","c"),"abc");

%/mathpiper
*/