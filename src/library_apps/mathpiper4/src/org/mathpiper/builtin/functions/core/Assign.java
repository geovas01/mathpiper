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

/**
 *
 *  
 */
public class Assign extends BuiltinFunction
{

    private Assign()
    {
    }

    public Assign(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        Utility.setVariableOrConstant(aEnvironment, aStackTop, false, false, false);
    }
}



/*
%mathpiper_docs,name="Assign",categories="Programming Functions;Variables;Built In"
*CMD Assign --- assignment
*CORE
*CALL
	Assign(var, exp)

*PARMS

{var} -- variable that should be assigned or a string that contains the name of the variable

{exp} -- expression to assign to the variable

*DESC

The expression "exp" is evaluated and assigned it to the variable
named "var". The first argument is not evaluated. The value True
is returned.

The statement {Assign(var, exp)} is equivalent to {var := exp}, but the {:=} operator
has more uses, e.g. changing individual entries in a list.

*E.G.

In> Assign(a, Sine(_x)+3);
Result: True;

In> a;
Result: Sine(_x)+3;

*SEE Unassign, :=
%/mathpiper_docs
*/