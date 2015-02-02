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


import java.util.Map;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;


/**
 *
 *  
 */
public class Sequence extends BuiltinFunction
{

    private Sequence()
    {
    }

    public Sequence(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {
        // Allow accessing previous locals.
        aEnvironment.pushLocalFrame(false, "Sequence");
        
        Cons consTraverser = null;

        try {



            Cons result = Utility.getTrueAtom(aEnvironment);

            // Evaluate args one by one.
            consTraverser = (Cons) getArgument(aEnvironment, aStackTop, 1).car();
            consTraverser =  consTraverser.cdr();
            while (consTraverser != null) {
                result = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, consTraverser);
                consTraverser = consTraverser.cdr();
            }

            setTopOfStack(aEnvironment, aStackTop, result);

        } catch (Throwable e) {
            throw e;
        } finally {
            aEnvironment.popLocalFrame(aStackTop);
        }
    }


}



/*
%mathpiper_docs,name="Sequence",categories="Programming Functions;Miscellaneous;Built In"
*CMD Sequence --- block of statements
*CORE
*CALL
	Sequence(statement1, statement2, ...)

*PARMS

{statement1}, {statement2} -- expressions

*DESC

The {Sequence} and the {&#123; ... &#125;} construct have the same effect: they evaluate all
arguments in order and return the result of the last evaluated expression.

{Sequence(_a,_b);} is the same as typing {&#123;_a;_b;&#125;} and is very useful for writing out
function bodies. The {&#123; ... &#125;} construct is a syntactically nicer version of the
{Sequence} call; it is converted into {Sequence(...)} during the parsing stage.

*SEE {, }
%/mathpiper_docs
*/