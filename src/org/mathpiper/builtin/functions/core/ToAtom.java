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
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class ToAtom extends BuiltinFunction
{

    private ToAtom()
    {
    }

    public ToAtom(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackBase) throws Exception
    {
        Cons evaluated = getArgument(aEnvironment, aStackBase, 1);

        // Get operator
        if(evaluated == null) LispError.checkArgument(aEnvironment, aStackBase, 1);
        String orig =  (String) evaluated.car();
        if( orig == null) LispError.checkArgument(aEnvironment, aStackBase, 1);
        setTopOfStack(aEnvironment, aStackBase, AtomCons.getInstance(aEnvironment, aStackBase, Utility.stripEndQuotesIfPresent(aEnvironment, aStackBase, orig)));
    }
}




/*
%mathpiper_docs,name="ToAtom",categories="User Functions;String Manipulation"
*CMD ToAtom --- convert string to atom
*CORE
*CALL
	ToAtom("string")

*PARMS

{"string"} -- a string

*DESC

Returns an atom with the string representation given
as the evaluated argument. Example: {ToAtom("foo");} returns
{foo}.


*E.G.

In> ToAtom("a")
Result: a;

*SEE ToString, ExpressionToString
%/mathpiper_docs





%mathpiper,name="ToAtom",subtype="automatic_test"

 Verify(ToAtom("a"),a);

%/mathpiper
*/