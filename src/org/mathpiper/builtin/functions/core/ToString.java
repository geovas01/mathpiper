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
public class ToString extends BuiltinFunction
{

    private ToString()
    {
    }

    public ToString(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        Cons evaluated = getArgument(aEnvironment, aStackTop, 1);

        // Get operator
        if(evaluated  == null) LispError.checkArgument(aEnvironment, aStackTop, 1);

        String orig = null;
        if(evaluated.car() instanceof String)
        {
                 orig = (String) evaluated.car();
        }
        else if(evaluated.car() instanceof BuiltinContainer)
        {
            BuiltinContainer container = (BuiltinContainer) evaluated.car();
            orig = container.getObject().toString();
        }

        if( orig == null) LispError.checkArgument(aEnvironment, aStackTop, 1);

        setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, Utility.toMathPiperString(aEnvironment, orig)));
    }
}



/*
%mathpiper_docs,name="ToString",categories="User Functions;String Manipulation;Built In"
*CMD ToString --- convert atom to string
*CORE
*CALL
	ToString(atom)

*PARMS

{atom} -- an atom

*DESC

{ToString} is the inverse of {ToAtom}: turns {atom} into {"atom"}.

*E.G.
In> ToString(a)
Result: "a";

*SEE ToAtom, ExpressionToString
%/mathpiper_docs





%mathpiper,name="ToString",subtype="automatic_test"

 Verify(ToString(a),"a");

%/mathpiper
*/