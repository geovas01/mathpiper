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
import org.mathpiper.lisp.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 *  
 */
public class ApplyPure extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer oper = new ConsPointer();
        oper.set(argumentPointer(aEnvironment, aStackTop, 1).get());
        ConsPointer args = new ConsPointer();
        args.set(argumentPointer(aEnvironment, aStackTop, 2).get());

        LispError.checkArgumentCore(aEnvironment, aStackTop, args.get().subList() != null, 2);
        LispError.checkCore(aEnvironment, aStackTop, args.get().subList().get() != null, 2);

        // Apply a pure string
        if (oper.get().string() != null)
        {
            UtilityFunctions.internalApplyString(aEnvironment, result(aEnvironment, aStackTop),
                    oper.get().string(),
                    args.get().subList().get().cdr());
        } else
        {   // Apply a pure function {args,body}.

            ConsPointer args2 = new ConsPointer();
            args2.set(args.get().subList().get().cdr().get());
            LispError.checkArgumentCore(aEnvironment, aStackTop, oper.get().subList() != null, 1);
            LispError.checkArgumentCore(aEnvironment, aStackTop, oper.get().subList().get() != null, 1);
            UtilityFunctions.internalApplyPure(oper, args2, result(aEnvironment, aStackTop), aEnvironment);
        }
    }
}
