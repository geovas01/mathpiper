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
import org.mathpiper.io.StringOutput;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.ConsTraverser;

/**
 *
 *
 */
public class SysOut extends BuiltinFunction {
    
    public void plugIn(Environment aEnvironment)
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "SysOut");
    }//end method.



    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
        StringOutput out = new StringOutput();
        if (getArgumentPointer(aEnvironment, aStackTop, 1).car() instanceof ConsPointer) {

            ConsPointer subList = (ConsPointer) getArgumentPointer(aEnvironment, aStackTop, 1).car();
            
            ConsTraverser consTraverser = new ConsTraverser(subList);
            consTraverser.goNext();
            while (consTraverser.getCons() != null)
            {
                aEnvironment.iCurrentPrinter.print(consTraverser.getPointer(), out, aEnvironment);
                consTraverser.goNext();
            }
        }
        String output = out.toString();
        output = output.replace("\"", "");
        System.out.println(output);
        aEnvironment.iCurrentOutput.write(output);
        aEnvironment.iCurrentOutput.write("\n");
        UtilityFunctions.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));

    }//end method.


}
