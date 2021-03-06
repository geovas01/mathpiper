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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *///}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.test;

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
        
        EvaluationResponse response;
       
        final Interpreter interpreter = Interpreters.getSynchronousInterpreter();

        /*
         final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                interpreter.haltEvaluation();
                timer.cancel();
            }

        }, 1000); //Time out after one second.
        */

        
        // response = interpreter.evaluate("Tell(a);");
       // System.out.println("Straight: " + "Result: " + response.getResult() + "  Side Effects: " + response.getSideEffects() + "  Errors: " + response.getExceptionMessage());
         
        //Load("/home/tkosan/NetBeansProjects/mathpiper/src/org/mathpiper/test/test.mpi");
        response = interpreter.evaluate("LoadScript(\"/home/tkosan/NetBeansProjects/mathpiper/src/org/mathpiper/test/test.mpi\");");

        //timer.cancel();
        
        System.out.println("Load test: " + "Result: " + response.getResult() + "  Side Effects: " + response.getSideEffects() + "  Errors: " + response.getExceptionMessage() + "   File: " + response.getSourceFileName() + "  Line number: " + response.getLineNumber()  + " Line index: " + response.getLineIndex());
        
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

    	geogebra.GeoGebraPanel ggbPanel = new geogebra.GeoGebraPanel();
    	ggbPanel.setShowAlgebraInput(true);
        ggbPanel.setShowAlgebraView(false);
    	ggbPanel.setMaxIconSize(24);
    	ggbPanel.setShowMenubar(true);
    	ggbPanel.setShowToolbar(true);
    	ggbPanel.buildGUI();
    	contentPane.add(ggbPanel);
        frame.setBounds ( 10 , 10 , 700 , 700 );
        frame.setVisible(true);

        geogebra.plugin.GgbAPI ggbAPI = ggbPanel.getGeoGebraAPI();


        //Wait until the frame is shown.
        try
        {
        Thread.sleep(3000);
        }
        catch(InterruptedException ie)
        {

        }


        ggbAPI.setRepaintingActive(false);



        //Plot 1000 points to the drawing pad.
        for(double x = -5; x < 5; x = x + .01)
        {
            ggbAPI.evalCommand("(" + x + "," + x + ")");
        }//end for.
        */

    }//end main.
}
