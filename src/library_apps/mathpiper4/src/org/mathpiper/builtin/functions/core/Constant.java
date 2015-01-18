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
public class Constant extends BuiltinFunction
{

    private Constant()
    {
    }

    public Constant(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        boolean isSetConstant = Utility.isTrue(aEnvironment, BuiltinFunction.getArgument(aEnvironment, aStackTop, 2), aStackTop);
        
        Utility.setVariableOrConstant(aEnvironment, aStackTop, false, false, isSetConstant, true);
    }
}



/*
%mathpiper_docs,name="Constant",categories="Programming Functions;Variables;Built In"
*CMD Constant --- make a symbol a constant or a nonconstant
*CALL
	Constant(symbol, boolean)

*PARMS

{symbol} -- the symbol to be modified

{boolean} -- True means make the symbol constant, and False means make the symbol nonconstant

*DESC
This function makes a symbol a constant or a nonconstant.

*E.G.
In> rock
Result: Exception
Exception: Error: The variable <rock> does not have a value assigned to it. Starting at index 0.

In> Constant('rock, True);
Result: True

In> rock
Result: rock

In> Constant(rock, False);
Result: True

In> rock
Result: Exception
Exception: Error: The variable <rock> does not have a value assigned to it. Starting at index 0.
%/mathpiper_docs



%mathpiper,name="Constant",subtype="automatic_test"
// Testing a local variable.
{
    Local(v1);
    Unassign(v1);
    Verify(ExceptionCatch(v1,"Exception"), "Exception");
    Constant('v1, True);
    Verify(v1,v1);
    Constant(v1, False);
    Verify(ExceptionCatch(v1,"Exception"), "Exception");
    Unassign(v1);
};

// Testing a global variable.
{
    Unassign(v1);
    Verify(ExceptionCatch(v1,"Exception"), "Exception");
    Constant('v1, True);
    Verify(v1,v1);
    Constant(v1, False);
    Verify(ExceptionCatch(v1,"Exception"), "Exception");
    Unassign(v1);
};
%/mathpiper
*/