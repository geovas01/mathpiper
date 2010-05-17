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
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;

/**
 *
 *
 */
public class JavaAccess extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Exception {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "JavaAccess");
    }//end method.


    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        ConsPointer args = new ConsPointer();

        args.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());

        args.goSub(aStackTop, aEnvironment);

        args.goNext(aStackTop, aEnvironment);

        ConsPointer result = new ConsPointer();

        Utility.applyString(aEnvironment, aStackTop, result, "\"JavaCall\"", args);

        Utility.applyString(aEnvironment, aStackTop, result, "\"JavaValue\"", result);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(result.getCons());

    }//end method.
}
