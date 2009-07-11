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

import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;

/**
 *
 *
 */
public class SimulatorPlot extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment)
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 2, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "SimulatorPlot");
    }//end method.
    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
         ConsPointer consPointer = new ConsPointer();
         aEnvironment.getGlobalVariable("Simulator", consPointer);
         org.mathpiper.ui.gui.simulator.SimulatorFrame simulator =  (org.mathpiper.ui.gui.simulator.SimulatorFrame)  ((BuiltinContainer)consPointer.car()).getJavaObject();
         
         Cons xCons = getArgumentPointer(aEnvironment, aStackTop, 1).getCons();
         Cons yCons = getArgumentPointer(aEnvironment, aStackTop, 2).getCons();
         int xValue = Integer.parseInt( (String) xCons.car());
         int yValue = Integer.parseInt( (String) yCons.car());
         simulator.plotPoint(xValue,yValue);
         Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
    }
}
