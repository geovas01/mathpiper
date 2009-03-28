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
public class RightPrecedence extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        // Get operator
        LispError.checkArgument(aEnvironment, aStackTop, getArgumentPointer(aEnvironment, aStackTop, 1).getCons() != null, 1);
        String orig = getArgumentPointer(aEnvironment, aStackTop, 1).getCons().string();
        LispError.checkArgument(aEnvironment, aStackTop, orig != null, 1);

        ConsPointer index = new ConsPointer();
        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, index, getArgumentPointer(aEnvironment, aStackTop, 2));
        LispError.checkArgument(aEnvironment, aStackTop, index.getCons() != null, 2);
        LispError.checkArgument(aEnvironment, aStackTop, index.getCons().string() != null, 2);
        int ind = Integer.parseInt(index.getCons().string(), 10);

        aEnvironment.iInfixOperators.setRightPrecedence(UtilityFunctions.getSymbolName(aEnvironment, orig), ind);
        UtilityFunctions.internalTrue(aEnvironment, getResult(aEnvironment, aStackTop));
    }
}
