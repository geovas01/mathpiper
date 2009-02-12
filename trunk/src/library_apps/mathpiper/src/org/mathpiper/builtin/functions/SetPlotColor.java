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
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 *
 */
public class SetPlotColor extends BuiltinFunction
{

    public void eval(Environment aEnvironment, int aStackTop) throws Exception
    {
         ConsPointer consPointer = new ConsPointer();
         aEnvironment.getVariable("Simulator", consPointer);
         org.mathpiper.ui.gui.simulator.SimulatorFrame simulator =  (org.mathpiper.ui.gui.simulator.SimulatorFrame) consPointer.getCons().getGeneric().getJavaObject();

         Cons redCons = getArgumentPointer(aEnvironment, aStackTop, 1).getCons();
         Cons greenCons = getArgumentPointer(aEnvironment, aStackTop, 2).getCons();
         Cons blueCons = getArgumentPointer(aEnvironment, aStackTop, 3).getCons();
         int redValue = Integer.parseInt(redCons.string());
         int greenValue = Integer.parseInt(greenCons.string());
         int blueValue = Integer.parseInt(blueCons.string());
         simulator.setColor(redValue, greenValue, blueValue);
         UtilityFunctions.internalTrue(aEnvironment, getResult(aEnvironment, aStackTop));
    }
}
