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
package org.mathpiper.builtin;

// new-style evaluator, passing arguments onto the stack in Environment
import org.mathpiper.lisp.Evaluator;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.Cons;

public class BuiltinFunctionEvaluator extends Evaluator {
    // FunctionFlags can be ORed when passed to the constructor of this function

    public static int Function = 0;    // Function: evaluate arguments. todo:tk:not used.
    public static int Macro = 1;       // Function: don't evaluate arguments
    public static int Fixed = 0;     // fixed number of arguments. todo:tk:not used.
    public static int Variable = 2;  // variable number of arguments
    public BuiltinFunction iCalledBuiltinFunction;
    public int iNumberOfArguments;
    public int iFlags;
    public boolean showFlag = false;

    public BuiltinFunctionEvaluator(BuiltinFunction aCalledBuiltinFunction, int aNumberOfArguments, int aFlags) {
        iCalledBuiltinFunction = aCalledBuiltinFunction;
        iNumberOfArguments = aNumberOfArguments;
        iFlags = aFlags;
    }

    public void evaluate(Environment aEnvironment, int aStackBase, Cons aArguments) throws Exception {

    }
}


