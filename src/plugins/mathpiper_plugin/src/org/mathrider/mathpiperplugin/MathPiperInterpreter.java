//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.mathpiperplugin;

//import org.mathrider.piper.CPiper;
//import org.mathrider.piper.Piperexception;
import org.mathpiper.Interpreter;
import org.mathpiper.exceptions.MathPiperException;
import console.Output;
import org.mathrider.ResponseListener;
import java.io.*;
import errorlist.*;
import java.util.ArrayList;

/**
 *
 * @author tk
 */


public class MathPiperInterpreter  implements Runnable{

	private static MathPiperInterpreter instance = null;
	private Interpreter mathPiper;
	private StringOutput stringOutput;

	private String result;
	private String input;
	private ArrayList<ResponseListener> removeListeners;
	private ArrayList<ResponseListener> responseListeners;

	//java.util.zip.ZipFile scriptsZip;

	private static DefaultErrorSource errorSource;

	/** Creates a new instance of MathPiperInterpreter */
	protected MathPiperInterpreter() throws MathPiperException {

		stringOutput = new StringOutput();

		mathPiper = new Interpreter(stringOutput);

		responseListeners = new ArrayList<ResponseListener>();
		removeListeners = new ArrayList<ResponseListener>();


	}//end constructor.

	public static MathPiperInterpreter getInstance() throws MathPiperException{
		if(instance == null) {
			instance = new MathPiperInterpreter();
		}
		return instance;
	}//end method.



	public java.util.zip.ZipFile getScriptsZip()
	{
		return mathPiper.getScriptsZip();
	}//end method.


	/** Use this method to pass an expression to the MathPiper interpreter.
	 *  Returns the output of the interpreter.
	 */
	public Object[] evaluate(String input) throws MathPiperException
	{
		result = mathPiper.evaluate(input);

		result = result.trim();

		String loadResult = mathPiper.evaluate("LoadResult;");

		loadResult = loadResult.trim();

		mathPiper.evaluate(result + ";");//Note:tk:eventually reengineer previous result mechanism in MathPiper.

		String sideEffect = stringOutput.toString();

		Object[] resultsAndSideEffect = new Object[5];
		
		resultsAndSideEffect[4] = -1;

		if(sideEffect != null && sideEffect.length() != 0){
			resultsAndSideEffect[1] = sideEffect;
		}
		else
		{
			resultsAndSideEffect[1] = null;
		}


		if(loadResult != null && loadResult.length() != 0){
			resultsAndSideEffect[2] = loadResult;
		}
		else
		{
			resultsAndSideEffect[2] = null;
		}

		resultsAndSideEffect[0] = result;

		return resultsAndSideEffect;

	}

	public void evaluateAsync(String input)  {

		/*
			http://saloon.javaranch.com/cgi-bin/ubb/ultimatebb.cgi?ubb=get_topic&f=27&t=002774
			If you are using java 5 then you can use callable
			If not, then write an abstract class that exposes a new 
			abstract method that implementations must override
			to do the *required* job. (This will contain code that 
			otherwise would have been in run())This class implements 
			Runnable and implements the run() method. In the run method
			call the abstract method. If it throws an exception then store it 
			and give a getter for this exception. You also have to provide a 
			method to enquire whether the task has finished or not!
		*/

		this.input = input;
		new Thread(this,"MathPiper").start();


	}//end method.

	public void run()
	{
		try
		{
			Object[] resultsAndSideEffect = evaluate(input);
			notifyListeners(resultsAndSideEffect);
		}
		catch(Exception e)
		{
			Object[] error = new Object[5];
			error[3] = e.getMessage();
			if(e instanceof MathPiperException)
			{
				MathPiperException mpe  = (MathPiperException) e;
				int errorLineNumber = mpe.getLineNumber();
				error[4] = mpe.getLineNumber();
			}

			notifyListeners(error);
		}

	}


	public static DefaultErrorSource getErrorSource()
	{
		if(errorSource == null)
		{
			errorSource = new DefaultErrorSource("MathRider");
			ErrorSource.registerErrorSource(errorSource);
		}//end if.

		return errorSource;
	}//end method.


	public void addResponseListener(ResponseListener listener)
	{
		responseListeners.add(listener);
	}//end method.

	public void removeResponseListener(ResponseListener listener)
	{
		responseListeners.remove(listener);
	}//end method.

	protected void notifyListeners(Object[] response)
	{
		//notify listeners.
		for(ResponseListener listener : responseListeners)
		{
			listener.response(response);

			if(listener.remove())
			{
				removeListeners.add(listener);
			}//end if.
		}//end for.


		//Remove certain listeners.
		for(ResponseListener listener : removeListeners)
		{

			if(listener.remove())
			{
				responseListeners.remove(listener);
			}//end if.
		}//end for.

		removeListeners.clear();

	}//end method.



	public void stopCurrentCalculation()
	{
		mathPiper.stopCurrentCalculation();
		stringOutput.clear();
	}




}//end class.


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
 */ //}}}


// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
