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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.ui.gui.help.FunctionTreePanel;

/**
 *
 *
 */
public class ViewHelp extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Throwable {
        this.functionName = "ViewHelp";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 0, BuiltinFunctionEvaluator.FixedNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {


        try {

            JavaObject response = new JavaObject(showFrame());

            setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));

        } catch (FileNotFoundException fnfe) {
            LispError.raiseError("The help application data file was not found.", aStackTop, aEnvironment);
        }

    }//end method.

    public static JFrame showFrame() throws Throwable {
        JFrame frame = new javax.swing.JFrame();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        FunctionTreePanel functionTreePanel = null;


        functionTreePanel = new FunctionTreePanel();

        Container contentPane = frame.getContentPane();
        contentPane.add(functionTreePanel.getToolPanel(), BorderLayout.NORTH);
        contentPane.add(functionTreePanel, BorderLayout.CENTER);

        frame.pack();

        frame.setTitle("MathPiper Help");
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width/2, height/2);
        frame.setLocationRelativeTo(null); // added

        frame.setVisible(true);

        return frame;

    }//end method.



    public static void main(String[] args)
    {
        try{showFrame();
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
    }

}//end class.




/*
%mathpiper_docs,name="ViewHelp",categories="Mathematics Functions;Built In"
*CMD ViewHelp --- display the function help window
*CORE
*CALL
    ViewHelp()

*DESC

Displays the function help window.

*E.G.
The ViewXXX functions all return a reference to the Java JFrame windows which they are displayed in.
This JFrame instance can be used to hide, show, and dispose of the window.

In> frame := ViewHelp()
Result: javax.swing.JFrame

In> JavaCall(frame, "hide")
Result: True

In> JavaCall(frame, "show")
Result: True

In> JavaCall(frame, "dispose")
Result: True

%/mathpiper_docs
*/
