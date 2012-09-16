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
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *  
 */
public class Assigned_ extends BuiltinFunction
{

    private Assigned_()
    {
    }

    public Assigned_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        
        if (getArgument(aEnvironment, aStackTop, 1).car() instanceof String)
        {
            String str =  (String) getArgument(aEnvironment, aStackTop, 1).car();

            Cons val = aEnvironment.getLocalOrGlobalVariable(aStackTop, str);
            if (val != null)
            {
                setTopOfStack(aEnvironment, aStackTop, Utility.getTrueAtom(aEnvironment));
                return;
            }
        }
        setTopOfStack(aEnvironment, aStackTop, Utility.getFalseAtom(aEnvironment));
    }
}



/*
%mathpiper_docs,name="Assigned?",categories="User Functions;Predicates;Built In"
*CMD Assigned? --- test for a bound variable
*CORE
*CALL
	Assigned?(var)

*PARMS

{var} -- variable to test

*DESC

This function tests whether the variable "var" is bound, i.e. whether
it has been assigned a value. The argument "var" is not evaluated.

*E.G.

In> Assigned?(x);
Result: False;
In> x := 5;
Result: 5;
In> Assigned?(x);
Result: True;

*SEE Atom?
%/mathpiper_docs
*/