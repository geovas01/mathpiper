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
public class Atom_ extends BuiltinFunction
{
    
    private Atom_()
    {
    }

    public Atom_(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Cons result = getArgument(aEnvironment, aStackTop, 1);
        setTopOfStack(aEnvironment, aStackTop, Utility.getBooleanAtom(aEnvironment, result.car() instanceof String));
    }
}



/*
%mathpiper_docs,name="Atom?",categories="Programming Functions;Predicates;Built In"
*CMD Atom? --- test for an atom
*CORE
*CALL
	Atom?(expr)

*PARMS

{expr} -- expression to test

*DESC

This function tests whether "expr" is an atom. Numbers, strings, and
variables are all atoms.

*E.G.

In> Atom?('x+5);
Result: False;

In> Atom?(5);
Result: True;

*SEE Function?, Number?, String?
%/mathpiper_docs





%mathpiper,name="Atom?",subtype="automatic_test"

Verify(Atom?([_a,_b,_c]),False);
Verify(Atom?(_a),True);
Verify(Atom?(123),True);

%/mathpiper
*/
