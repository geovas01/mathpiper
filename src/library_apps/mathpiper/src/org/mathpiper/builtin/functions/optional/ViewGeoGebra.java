package org.mathpiper.builtin.functions.optional;

import geogebra.GeoGebraPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;

public class ViewGeoGebra extends BuiltinFunction {

    private GeoGebraPanel ggbPanel;

    public void plugIn(Environment aEnvironment) throws Exception {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 0, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ViewGeoGebra");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {


        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        frame.setBackground(Color.WHITE);
        contentPane.setBackground(Color.WHITE);


        ggbPanel = new GeoGebraPanel();

    	// hide input bar
    	ggbPanel.setShowAlgebraInput(true);
    	// use smaller icons in toolbar
    	ggbPanel.setMaxIconSize(24);

    	// show menu bar and toolbar
    	ggbPanel.setShowMenubar(true);
    	ggbPanel.setShowToolbar(true);

    	// build the user interface of the GeoGebraPanel
    	ggbPanel.buildGUI();




        try{
                org.mathpiper.interpreters.Interpreter synchronousInterpreter = org.mathpiper.interpreters.Interpreters.getSynchronousInterpreter();
                org.mathpiper.lisp.Environment environment = synchronousInterpreter.getEnvironment();
                org.mathpiper.builtin.JavaObject javaObject = new org.mathpiper.builtin.JavaObject(ggbPanel.getGeoGebraAPI());
                environment.setGlobalVariable(-1, "geogebra", new org.mathpiper.lisp.cons.ConsPointer( org.mathpiper.lisp.cons.BuiltinObjectCons.getInstance(environment, -1, javaObject)), false);

                //geoGebraApplet.registerAddListener("GeoGebraAddListener");
                //geoGebraApplet.registerUpdateListener("GeoGebraUpdateListener");

                //System.out.println("HHHHHHHHHHHHHHHHHHH GeoGebra listeners registered.");
        }catch(Exception e)
        {
                e.printStackTrace();
        }


        contentPane.add(ggbPanel);


        frame.setAlwaysOnTop(false);
        frame.setTitle("GeoGebra (Experimental Version)");
        frame.setSize(new Dimension(300, 200));
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);


        Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
    }//end method.
}//end class.

