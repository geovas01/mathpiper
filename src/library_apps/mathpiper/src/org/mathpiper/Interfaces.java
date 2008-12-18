/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper;

import javax.swing.JPanel;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.ui.gui.calculator.Calculator;
import org.mathpiper.ui.gui.calculator.CalculatorPanel;

/**
 *
 */
public class Interfaces
{

    public static void calculatorFrame()
    {
             java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculator().setVisible(true);
            }
        });
    }//end method.


    public static JPanel newCalculatorPanel()
    {
         return new CalculatorPanel();
    }//end method.

    public static Interpreter newSynchronousInterpreter()
    {
        return Interpreters.newSynchronousInterpreter();
    }

    public static Interpreter newSynchronousInterpreter(String docBase)
    {
        return Interpreters.newSynchronousInterpreter(docBase);
    }

    public static Interpreter getSynchronousInterpreter()
    {
        return Interpreters.getSynchronousInterpreter();
    }

     public static Interpreter getSynchronousInterpreter(String docBase)
    {
        return Interpreters.getSynchronousInterpreter(docBase);
    }


    public static Interpreter newAsynchronousInterpreter()
    {
        return Interpreters.newAsynchronousInterpreter();
    }

     public static Interpreter newAsynchronousInterpreter(String docBase)
    {
        return Interpreters.newAsynchronousInterpreter(docBase);
    }

     public static Interpreter getAsynchronousInterpreter()
    {
        return Interpreters.getAsynchronousInterpreter();
    }

     public static Interpreter getAsynchronousInterpreter(String docBase)
    {
        return Interpreters.getAsynchronousInterpreter(docBase);
    }
    
    
    public static void main(String[] args)
    {
        calculatorFrame();
    }
}
