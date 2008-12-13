/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.ui.gui.calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JApplet;
import org.mathpiper.interpreters.EvaluationResponse;

/**
 *
 * @author tkosan
 */
public class Applet extends JApplet {

private CalculatorPanel calculatorPanel;

   public void init(){
     setLayout(new BorderLayout(1, 2));
     setBackground(Color.white);

     String docBase = getDocumentBase().toString();



     calculatorPanel = new CalculatorPanel(docBase);
     add("Center", calculatorPanel);

   }

   
public String evaluate(String expression)
{
    EvaluationResponse evaluationResponse;

    evaluationResponse = calculatorPanel.evaluate(expression + ";");

    return evaluationResponse.getResult();
}//end method.

  public void start(){
     System.out.println("Applet starting.");

  }

  public void stop(){
     System.out.println("Applet stopping.");
  }

  public void destroy(){
     System.out.println("Destroy method called.");
  }

  


}
