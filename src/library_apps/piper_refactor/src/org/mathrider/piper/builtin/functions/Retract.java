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
package org.mathrider.piper.builtin.functions;

import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.Standard;

/**
 *
 * @author 
 */
public class Retract extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        // Get operator
        ConsPointer evaluated = new ConsPointer();
        evaluated.set(ARGUMENT(aEnvironment, aStackTop, 1).get());

        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, evaluated.get() != null, 1);
        String orig = evaluated.get().string();
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, orig != null, 1);
        String oper = Standard.symbolName(aEnvironment, orig);

        ConsPointer arity = new ConsPointer();
        arity.set(ARGUMENT(aEnvironment, aStackTop, 2).get());
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, arity.get().string() != null, 2);
        int ar = Integer.parseInt(arity.get().string(), 10);
        aEnvironment.retract(oper, ar);
        Standard.internalTrue(aEnvironment, RESULT(aEnvironment, aStackTop));
    }
}
