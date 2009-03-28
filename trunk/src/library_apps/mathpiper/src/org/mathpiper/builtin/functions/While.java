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
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 *  
 */
public class While extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer arg1 = getArgumentPointer(aEnvironment, aStackTop, 1);
        ConsPointer arg2 = getArgumentPointer(aEnvironment, aStackTop, 2);

        ConsPointer predicate = new ConsPointer();
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, predicate, arg1);

        while (UtilityFunctions.isTrue(aEnvironment, predicate))
        {
            ConsPointer evaluated = new ConsPointer();
            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, evaluated, arg2);
            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, predicate, arg1);

        }
        LispError.checkArgument(aEnvironment, aStackTop, UtilityFunctions.isFalse(aEnvironment, predicate), 1);
        UtilityFunctions.internalTrue(aEnvironment, getResult(aEnvironment, aStackTop));
    }
}
