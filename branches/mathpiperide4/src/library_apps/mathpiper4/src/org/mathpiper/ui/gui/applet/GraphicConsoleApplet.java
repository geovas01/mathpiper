package org.mathpiper.ui.gui.applet;

import java.applet.*;

import java.awt.*;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.ui.gui.consoles.GraphicConsole;

public class GraphicConsoleApplet extends JApplet {

    public void init() {

	//Execute a job on the event-dispatching thread; creating this applet's GUI.
	try {
	    SwingUtilities.invokeAndWait(new Runnable() {
		public void run() {
		    
		    GraphicConsole console = new GraphicConsole();

		    Interpreter interpreter = console.getInterpreter();
		    Interpreters.addOptionalFunctions(interpreter.getEnvironment(), "org/mathpiper/builtin/functions/optional/");
		    org.mathpiper.interpreters.Interpreters.addOptionalFunctions(interpreter.getEnvironment(),
		    "org/mathpiper/builtin/functions/plugins/jfreechart/");

		    getContentPane().add(console);
		}
	    });
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }



    public void stop() {

    }

}