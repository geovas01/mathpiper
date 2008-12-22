/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.ui.gui.calculator;

import javax.swing.JApplet;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;

/**
 *
 * @author tkosan
 */
public class JSApplet extends JApplet {

private Interpreter mathPiper;

   public void init(){


     String docBase = getDocumentBase().toString();



     mathPiper = Interpreters.newSynchronousInterpreter(docBase);


   }


public String evaluate(String expression)
{
    EvaluationResponse evaluationResponse;

    evaluationResponse = mathPiper.evaluate(expression + ";");

    String response = null;

    if(evaluationResponse.isExceptionThrown())
    {
        response = evaluationResponse.getExceptionMessage();
    }
    else
    {
        response = evaluationResponse.getResult();
    }

    return response;
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
