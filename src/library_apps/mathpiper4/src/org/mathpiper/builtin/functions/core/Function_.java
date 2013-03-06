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
public class Function_ extends BuiltinFunction {

    private Function_() {
    }

    public Function_(String functionName) {
        this.functionName = functionName;
    }

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
        Cons result = getArgument(aEnvironment, aStackTop, 1);
        setTopOfStack(aEnvironment, aStackTop, Utility.getBooleanAtom(aEnvironment,
                result.car() instanceof Cons));
    }
}



/*
%mathpiper_docs,name="Function?",categories="Programming Functions;Predicates;Built In"
*CMD Function? --- test for a composite object
*CORE
*CALL
	Function?(expr)

*PARMS

{expr} -- expression to test

*DESC

This function tests whether "expr" is a composite object, i.e. not an
atom. This includes not only obvious functions such as {f(x)}, but also expressions such as {x+5} and lists.

*E.G.

In> Function?(x+5);
Result: True;

In> Function?(x);
Result: False;

*SEE Atom?, List?, Type
%/mathpiper_docs





%mathpiper,name="Function?",subtype="automatic_test"

RulebaseHoldArguments("a", [b]);
Verify(Function?(a(b)),True);
Retract("a", 1);
Verify(Function?(a),False);

%/mathpiper
*/
