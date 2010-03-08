package org.mathpiper.builtin.functions.optional;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.ui.gui.worksheets.ListPanel;
import org.mathpiper.ui.gui.worksheets.MathPanelController;

public class ViewList extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Exception {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ViewList");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        ConsPointer consPointer = getArgumentPointer(aEnvironment, aStackTop, 1);

        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        frame.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);

        ListPanel listPanel = new ListPanel(aEnvironment, aStackTop, consPointer, 2);

        MathPanelController mathPanelScaler = new MathPanelController(listPanel, 2.0);

        JScrollPane scrollPane = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        contentPane.add(scrollPane);
        contentPane.add(mathPanelScaler, BorderLayout.NORTH);

        frame.setAlwaysOnTop(false);
        frame.setTitle("List Viewer");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);


        Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
    }//end method.
}//end class.

