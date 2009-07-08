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

package org.mathpiper.builtin.functions;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 *  
 */
public class IsBound extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        String str =  (String) getArgumentPointer(aEnvironment, aStackTop, 1).getCons().string();
        if (str != null)
        {
            ConsPointer val = new ConsPointer();
            aEnvironment.getGlobalVariable(str, val);
            if (val.getCons() != null)
            {
                UtilityFunctions.internalTrue(aEnvironment, getResult(aEnvironment, aStackTop));
                return;
            }
        }
        UtilityFunctions.internalFalse(aEnvironment, getResult(aEnvironment, aStackTop));
    }
}



/*
%mathpiper_docs,name="IsBound",categories="User Functions;Predicates;Built In"
*CMD IsBound --- test for a bound variable
*CORE
*CALL
	IsBound(var)

*PARMS

{var} -- variable to test

*DESC

This function tests whether the variable "var" is bound, i.e. whether
it has been assigned a value. The argument "var" is not evaluated.

*E.G.

	In> IsBound(x);
	Out> False;
	In> x := 5;
	Out> 5;
	In> IsBound(x);
	Out> True;

*SEE IsAtom
%/mathpiper_docs
*/