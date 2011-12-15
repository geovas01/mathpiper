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

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.ui.gui.consoles.GraphicConsole;

/**
 *
 *
 */
public class ViewGraphicConsole extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        this.functionName = "ViewGraphicConsole";
        aEnvironment.iBuiltinFunctions.setAssociation(
                this.functionName, new BuiltinFunctionEvaluator(this, 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        GraphicConsole console = new GraphicConsole();

        JFrame frame = new javax.swing.JFrame();
        Container contentPane = frame.getContentPane();
        contentPane.add(console, BorderLayout.CENTER);
        //frame.setAlwaysOnTop(true);
        frame.setSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        frame.setTitle("Graphic Console");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null); // added
        frame.pack();
        frame.setVisible(true);

        JavaObject response = new JavaObject(frame);

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));

    }//end method.

}//end class.




/*
%mathpiper_docs,name="ViewGraphicConsole",categories="User Functions;Built In",access="experimental"
*CMD ViewConsole --- show the console window
*CORE
*CALL
    ViewGraphicConsole()

*DESC

Shows the graphic console window.

*E.G.
The ViewXXX functions all return a reference to the Java JFrame windows which they are displayed in.
This JFrame instance can be used to hide, show, and dispose of the window.

In> frame := ViewGraphicConsole()
Result: javax.swing.JFrame

In> JavaCall(frame, "hide")
Result: True

In> JavaCall(frame, "show")
Result: True

In> JavaCall(frame, "dispose")
Result: True

%/mathpiper_docs
*/