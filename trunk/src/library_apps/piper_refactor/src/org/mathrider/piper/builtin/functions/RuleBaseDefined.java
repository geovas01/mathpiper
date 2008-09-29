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
import org.mathrider.piper.lisp.userfunctions.UserFunction;

/**
 *
 * @author 
 */
public class RuleBaseDefined extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer name = new ConsPointer();
        name.set(ARGUMENT(aEnvironment, aStackTop, 1).get());
        String orig = name.get().string();
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, orig != null, 1);
        String oper = Standard.internalUnstringify(orig);

        ConsPointer sizearg = new ConsPointer();
        sizearg.set(ARGUMENT(aEnvironment, aStackTop, 2).get());
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, sizearg.get() != null, 2);
        LispError.CHK_ARG_CORE(aEnvironment, aStackTop, sizearg.get().string() != null, 2);

        int arity = Integer.parseInt(sizearg.get().string(), 10);

        UserFunction userFunc = aEnvironment.userFunction(aEnvironment.hashTable().lookUp(oper), arity);
        Standard.internalBoolean(aEnvironment, RESULT(aEnvironment, aStackTop), userFunc != null);
    }
}
