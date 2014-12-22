package org.mathpiper.builtin.functions.optional;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.mathpiper.builtin.BuiltinFunction;
import static org.mathpiper.builtin.BuiltinFunction.getArgument;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.builtin.functions.plugins.jfreechart.ChartUtility;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.ui.gui.worksheets.ListPanel;
import org.mathpiper.ui.gui.worksheets.MathPanelController;
import org.mathpiper.ui.gui.worksheets.ScreenCapturePanel;

public class ViewList extends BuiltinFunction {
    
    private Map defaultOptions;

    public void plugIn(Environment aEnvironment) throws Throwable {
        this.functionName = "ViewList";
        aEnvironment.getBuiltinFunctions().put(
                this.functionName, new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.VariableNumberOfArguments | BuiltinFunctionEvaluator.EvaluateArguments));
    
        defaultOptions = new HashMap();
        defaultOptions.put("metaData", false);
    }//end method.



    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {

        Cons argument = getArgument(aEnvironment, aStackTop, 1);

        if(! Utility.isSublist(argument)) LispError.throwError(aEnvironment, aStackTop, LispError.INVALID_ARGUMENT, "");

        argument = (Cons) argument.car(); //Go to sub list.

        argument = argument.cdr(); //Strip List tag.

        //if(! Utility.isList(argument)) LispError.throwError(aEnvironment, aStackTop, LispError.NOT_A_LIST, "");

        Cons dataList = (Cons) argument.car(); //Grab the first member of the list.

        Cons options = argument.cdr();

        Map userOptions = ChartUtility.optionsListToJavaMap(aEnvironment, aStackTop, options, defaultOptions);

        JavaObject response = new JavaObject(showFrame(argument, userOptions));

        setTopOfStack(aEnvironment, aStackTop, BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));

    }//end method.


    public static JFrame showFrame(Cons expression, Map options) throws Throwable
    {
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        frame.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);

        ListPanel listPanel = new ListPanel(null, -1, expression, 2, options);

        MathPanelController mathPanelScaler = new MathPanelController(listPanel, 2.0);

        JPanel screenCapturePanel = new ScreenCapturePanel();

        screenCapturePanel.add(listPanel);

        JScrollPane scrollPane = new JScrollPane(screenCapturePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scrollPane);
        contentPane.add(mathPanelScaler, BorderLayout.NORTH);

        //frame.setAlwaysOnTop(false);
        frame.setTitle("List Viewer");
        frame.setResizable(true);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width/2, height/2);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        return frame;
    }

}//end class.





/*
%mathpiper_docs,name="ViewList",categories="Mathematics Functions;Built In;Visualization"
*CMD ViewList --- display an expression in Lisp box diagram form

*CALL
    ViewList(expression)

*PARMS
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

*SEE UnparseLisp, ViewMath
%/mathpiper_docs
*/