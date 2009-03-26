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
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 *  
 */
public class UnList extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        LispError.checkArgument(aEnvironment, aStackTop, getArgumentPointer(aEnvironment, aStackTop, 1).getCons() != null, 1);
        LispError.checkArgument(aEnvironment, aStackTop, getArgumentPointer(aEnvironment, aStackTop, 1).getCons().getSublistPointer() != null, 1);
        Cons subList = getArgumentPointer(aEnvironment, aStackTop, 1).getCons().getSublistPointer().getCons();
        LispError.checkArgument(aEnvironment, aStackTop, subList != null, 1);
        LispError.checkArgument(aEnvironment, aStackTop, subList.string() == aEnvironment.iListAtom.string(), 1);
        UtilityFunctions.internalTail(getResult(aEnvironment, aStackTop), getArgumentPointer(aEnvironment, aStackTop, 1));
    }
}
