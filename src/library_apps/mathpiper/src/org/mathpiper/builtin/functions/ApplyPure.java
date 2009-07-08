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
public class ApplyPure extends BuiltinFunction
{

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer oper = new ConsPointer();
        oper.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());
        ConsPointer args = new ConsPointer();
        args.setCons(getArgumentPointer(aEnvironment, aStackTop, 2).getCons());

        LispError.checkArgument(aEnvironment, aStackTop, args.getCons().first() instanceof ConsPointer, 2);
        LispError.check(aEnvironment, aStackTop, ((ConsPointer) args.getCons().first()).getCons() != null, 2);

        // Apply a pure string
        if (oper.getCons().string() != null)
        {
            UtilityFunctions.internalApplyString(aEnvironment, getResult(aEnvironment, aStackTop),
                    oper.getCons().string(),
                    ((ConsPointer) args.getCons().first()).getCons().getRestPointer());
        } else
        {   // Apply a pure function {args,body}.

            ConsPointer args2 = new ConsPointer();
            args2.setCons(((ConsPointer) args.getCons().first()).getCons().getRestPointer().getCons());
            LispError.checkArgument(aEnvironment, aStackTop, oper.getCons().first() instanceof ConsPointer, 1);
            LispError.checkArgument(aEnvironment, aStackTop, ((ConsPointer) oper.getCons().first()).getCons() != null, 1);
            UtilityFunctions.internalApplyPure(oper, args2, getResult(aEnvironment, aStackTop), aEnvironment);
        }
    }
}
