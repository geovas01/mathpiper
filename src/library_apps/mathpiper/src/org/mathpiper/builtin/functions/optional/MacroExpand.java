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

package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *
 */
public class MacroExpand extends BuiltinFunction
{
    //todo:tk:this function is not complete yet.  It currently only expands backquoted expressions.
    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        org.mathpiper.lisp.behaviours.BackQuoteSubstitute behaviour = new org.mathpiper.lisp.behaviours.BackQuoteSubstitute(aEnvironment);
        ConsPointer result = new ConsPointer();
        ConsPointer argument = getArgumentPointer(aEnvironment, aStackTop, 1);
        Cons argumentCons = argument.getCons();
        argument = ((ConsPointer) argumentCons.first()).getCons().getRestPointer();
        UtilityFunctions.substitute(result, argument, behaviour);
        String substitutedResult = UtilityFunctions.printExpression(result, aEnvironment, 0);
        aEnvironment.write(substitutedResult);
        UtilityFunctions.internalTrue(aEnvironment, getResult(aEnvironment, aStackTop));
    }
}
