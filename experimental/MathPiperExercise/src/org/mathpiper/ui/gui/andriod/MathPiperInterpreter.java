package org.mathpiper.ui.gui.andriod;

import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;

public class MathPiperInterpreter implements Runnable {
	private Thread casThread;
	private EvaluationResponse evaluationResponse;
	private Interpreter interpreter;
	private String inputText = null;
	private static MathPiperInterpreter singleton = null;

	private MathPiperInterpreter() {

	    casThread = new Thread(new ThreadGroup("mathiper"), this,
		    "mathpiper", 50000);
	    casThread.start();
	    interpreter = Interpreters.getSynchronousInterpreter();
	}
	
	
	public static MathPiperInterpreter getInterpreter()
	{
	    if(singleton == null)
	    {
		singleton = new MathPiperInterpreter();
	    }
	    
	    return singleton;
	}

	public synchronized EvaluationResponse evaluate(String input) {
	    
	    input = input.replace("\\", "\\\\");
	    input = input.replace("\"", "\\\"");
	    
	    inputText = "LoadScript(\"" + input + "\");";

	    while (inputText != null) {
		try {
		    Thread.sleep(100);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }

	    return evaluationResponse;
	}

	public void run() {
	    while (true) {
		if (inputText == null) {
		    try {
			Thread.sleep(100);
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		} else {

		    evaluationResponse = interpreter.evaluate(inputText);

		    /*
		     * runOnUiThread(new Runnable() { public void run() {
		     * 
		     * String response;
		     * 
		     * if(evaluationResponse.isExceptionThrown()) { response =
		     * evaluationResponse.getException().getMessage(); } else {
		     * response = evaluationResponse.getResult(); }
		     * 
		     * } });
		     */

		    inputText = null;
		}

	    }
	}// end method
}// end class.