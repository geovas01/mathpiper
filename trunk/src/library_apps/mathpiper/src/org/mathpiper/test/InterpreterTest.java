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
 *///}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.test;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.ResponseListener;

/**
 *
 */
public class InterpreterTest implements ResponseListener
{

    public InterpreterTest()
    {
        Interpreter interpreter;
        EvaluationResponse response;
       
        interpreter = Interpreters.getSynchronousInterpreter();

        
        // response = interpreter.evaluate("Tell(a);");
       // System.out.println("Straight: " + "Result: " + response.getResult() + "  Side Effects: " + response.getSideEffects() + "  Errors: " + response.getExceptionMessage());
         
        
        response = interpreter.evaluate("Load(\"/home/tkosan/NetBeansProjects/test.mpi\");");
        System.out.println("Load test: " + "Result: " + response.getResult() + "  Side Effects: " + response.getSideEffects() + "  Errors: " + response.getExceptionMessage());
        
       /* response = interpreter.evaluate("3+3;");
         System.out.println("Straight: " + "Result: " + response.getResult() + "  Side Effects: " + response.getSideEffects() + "  Errors: " + response.getExceptionMessage());
        * */
        
       /* interpreter = Interpreters.newAsynchronousInterpreter();
        interpreter.addResponseListener(this);
        response = interpreter.evaluate("2+2;");
        System.out.println("AsynchronousInterpreter evaluation request sent.");*/
        
        
    }
    
    public void response(EvaluationResponse response)
    {
         System.out.println("AsynchronousInterpreter: " + response.getResult());
    }
    
    public boolean remove()
    {
        return true;
    }
    
    public static void main(String[] args)
    {
        new InterpreterTest();

        /*
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        JButton haltButton = org.mathpiper.ui.gui.controlpanel.HaltButton.getInstance();
        contentPane.add(haltButton);
        frame.setBounds ( 10 , 10 , 200 , 90 );
        frame.setVisible(true);
         */

    }
}
