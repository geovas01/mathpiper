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

import javax.swing.JFrame;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.BuiltinObjectCons;

/**
 *
 *  
 */
public class ViewEnvironment extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        this.functionName = "ViewEnvironment";
        aEnvironment.getBuiltinFunctions().setAssociation(
                this.functionName, new BuiltinFunctionEvaluator(this, 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
         org.mathpiper.ui.gui.EnvironmentViewer viewer = new org.mathpiper.ui.gui.EnvironmentViewer();

         JFrame frame = viewer.getViewerFrame(aEnvironment);

        JavaObject response = new JavaObject(frame);

        setTopOfStackPointer(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));
    }
}




/*
%mathpiper_docs,name="ViewEnvironment",categories="User Functions;Built In"
*CMD ViewEnvironment --- show the console window
*CORE
*CALL
    ViewEnvironment()

*DESC

Shows the MathPiper environment.

*E.G.
The ViewXXX functions all return a reference to the Java JFrame windows which they are displayed in.
This JFrame instance can be used to hide, show, and dispose of the window.

In> frame := ViewEnvironment()
Result: javax.swing.JFrame

In> JavaCall(frame, "hide")
Result: True

In> JavaCall(frame, "show")
Result: True

In> JavaCall(frame, "dispose")
Result: True

%/mathpiper_docs
*/