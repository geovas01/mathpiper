package org.mathpiper.builtin.functions.optional;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.ui.gui.worksheets.ListPanel;
import org.mathpiper.ui.gui.worksheets.MathPanelController;
import org.mathpiper.ui.gui.worksheets.ScreenCapturePanel;

public class ViewList extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Exception {
        this.functionName = "ViewList";
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                this.functionName);
    }//end method.



    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        Cons expressionPointer = getArgumentPointer(aEnvironment, aStackTop, 1);

        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        frame.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);

        ListPanel listPanel = new ListPanel(aEnvironment, aStackTop, expressionPointer, 2);

        MathPanelController mathPanelScaler = new MathPanelController(listPanel, 2.0);

        JPanel screenCapturePanel = new ScreenCapturePanel();

        screenCapturePanel.add(listPanel);

        JScrollPane scrollPane = new JScrollPane(screenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        contentPane.add(scrollPane);
        contentPane.add(mathPanelScaler, BorderLayout.NORTH);

        frame.setAlwaysOnTop(false);
        frame.setTitle("List Viewer");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);

        JavaObject response = new JavaObject(frame);

        setTopOfStackPointer(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));

    }//end method.

}//end class.





/*
%mathpiper_docs,name="ViewList",categories="User Functions;Built In;Visualization"
*CMD ViewList --- display an expression in Lisp box diagram form

*CALL
    ViewList(expression)

*Params
{expression} -- an expression to view

*DESC
Display an expression in Lisp box diagram form.

*E.G.
In> ViewList(x^2)

In> ViewList(2*x^3+14*x^2+24*x)

 

The ViewXXX functions all return a reference to the Java JFrame windows which they are displayed in.
This JFrame instance can be used to hide, show, and dispose of the window.

In> frame := ViewList(x^2)
Result: javax.swing.JFrame

In> JavaCall(frame, "hide")
Result: True

In> JavaCall(frame, "show")
Result: True

In> JavaCall(frame, "dispose")
Result: True

*SEE LispForm, ViewMath
%/mathpiper_docs
*/