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
public class Retract extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        // Get operator
        ConsPointer evaluated = new ConsPointer();
        evaluated.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());

        LispError.checkArgument(aEnvironment, aStackTop, evaluated.getCons() != null, 1);
        String orig = evaluated.getCons().string();
        LispError.checkArgument(aEnvironment, aStackTop, orig != null, 1);
        String oper = UtilityFunctions.symbolName(aEnvironment, orig);

        ConsPointer arity = new ConsPointer();
        arity.setCons(getArgumentPointer(aEnvironment, aStackTop, 2).getCons());
        LispError.checkArgument(aEnvironment, aStackTop, arity.getCons().string() != null, 2);
        int ar = Integer.parseInt(arity.getCons().string(), 10);
        aEnvironment.retract(oper, ar);
        UtilityFunctions.internalTrue(aEnvironment, getResult(aEnvironment, aStackTop));
    }
}
