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

import org.mathpiper.builtin.Array;
import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 *  
 */
public class GenArraySet extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer evaluated = new ConsPointer();
        evaluated.set(argumentPointer(aEnvironment, aStackTop, 1).get());

        BuiltinContainer gen = evaluated.get().generic();
        LispError.checkArgumentCore(aEnvironment, aStackTop, gen != null, 1);
        LispError.checkArgumentCore(aEnvironment, aStackTop, gen.typeName().equals("\"Array\""), 1);

        ConsPointer sizearg = new ConsPointer();
        sizearg.set(argumentPointer(aEnvironment, aStackTop, 2).get());

        LispError.checkArgumentCore(aEnvironment, aStackTop, sizearg.get() != null, 2);
        LispError.checkArgumentCore(aEnvironment, aStackTop, sizearg.get().string() != null, 2);

        int size = Integer.parseInt(sizearg.get().string(), 10);
        LispError.checkArgumentCore(aEnvironment, aStackTop, size > 0 && size <= ((Array) gen).size(), 2);

        ConsPointer obj = new ConsPointer();
        obj.set(argumentPointer(aEnvironment, aStackTop, 3).get());
        ((Array) gen).setElement(size, obj.get());
        UtilityFunctions.internalTrue(aEnvironment, result(aEnvironment, aStackTop));
    }
}
