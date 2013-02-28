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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.BuiltinObjectCons;

import org.mathpiper.ui.gui.help.FunctionTreePanel;

/**
 *
 *
 */
public class ViewHtml extends BuiltinFunction {

    public void plugIn(Environment aEnvironment)  throws Throwable
    {
        this.functionName = "ViewHtml";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {

        String htmlText = null;

        Object argument = getArgument(aEnvironment, aStackTop, 1).car();

        if (argument instanceof String)
        {
            htmlText = (String) argument;

            htmlText = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, htmlText);
        }
        else if (argument instanceof BuiltinContainer)
        {
            BuiltinContainer builtinContainer = (BuiltinContainer) argument;
            if(! builtinContainer.typeName().equals("java.lang.String")) LispError.throwError(aEnvironment, aStackTop, "Argument must be a MathPiper string or a Java String object.");
            htmlText = (String) builtinContainer.getObject();
        }
        else
        {
            LispError.raiseError("Argument must be a MathPiper string or a Java String object.", aStackTop, aEnvironment);
        }//end else.

        htmlText = FunctionTreePanel.processLatex(htmlText);

        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new java.awt.BorderLayout());
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorPane.setEditable(false);
        editorPane.setText(htmlText);
        contentPane.add(editorScrollPane);
        frame.setAlwaysOnTop(false);
        frame.setTitle("MathPiper");
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width/2, height/2);
        frame.setResizable(true);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JavaObject response = new JavaObject(frame);

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));

    }//end method.
}//end class.




/*
%mathpiper_docs,name="ViewHtml",categories="User Functions;Built In;Visualization"
*CMD ViewHtml --- display rendered HTML code

*CALL
    ViewHtml(string)

*PARMS
{string} -- a string which contains HTML code

*DESC
Display rendered HTML code.

*E.G.
/%html
<html>
    <title>
    HTML Demo
    </title>

    <body>
        <h1>HTML demo 1.</h1>


        <h2>LaTeX math formulas can be placed into the HTML code.</h2>
        \$x_{j}\$

    </body>
</html>
/%/html



The ViewXXX functions all return a reference to the Java JFrame windows which they are displayed in.
This JFrame instance can be used to hide, show, and dispose of the window.

In> frame := ViewHtml("<html><body>Hello</body></html>")
Result: javax.swing.JFrame

In> JavaCall(frame, "hide")
Result: True

In> JavaCall(frame, "show")
Result: True

In> JavaCall(frame, "dispose")
Result: True

%/mathpiper_docs
*/
