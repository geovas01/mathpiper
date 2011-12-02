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
public class Prog extends BuiltinFunction
{

    private Prog()
    {
    }

    public Prog(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
        // Allow accessing previous locals.
        aEnvironment.pushLocalFrame(false, "Prog");

        try {



            Cons resultPointer = Utility.putTrueInPointer(aEnvironment);

            // Evaluate args one by one.
            Cons consTraverser = (Cons) getArgumentPointer(aEnvironment, aStackTop, 1).car();
            consTraverser =  consTraverser.cdr();
            while (consTraverser != null) {
                resultPointer = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, consTraverser);
                consTraverser = consTraverser.cdr();
            }

            setTopOfStackPointer(aEnvironment, aStackTop, resultPointer);

        } catch (Exception e) {
            throw e;
        } finally {
            aEnvironment.popLocalFrame(aStackTop);
        }
    }


}



/*
%mathpiper_docs,name="Prog",categories="Programmer Functions;Programming;Built In"
*CMD Prog --- block of statements
*CORE
*CALL
	Prog(statement1, statement2, ...)

*PARMS

{statement1}, {statement2} -- expressions

*DESC

The {Prog} and the {[ ... ]} construct have the same effect: they evaluate all
arguments in order and return the result of the last evaluated expression.

{Prog(a,b);} is the same as typing {[a;b;];} and is very useful for writing out
function bodies. The {[ ... ]} construct is a syntactically nicer version of the
{Prog} call; it is converted into {Prog(...)} during the parsing stage.

*SEE [, ], ReturnFromBlock
%/mathpiper_docs
*/