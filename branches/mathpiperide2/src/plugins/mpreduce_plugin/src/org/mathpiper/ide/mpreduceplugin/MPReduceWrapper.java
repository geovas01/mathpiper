//Copyright (C) 2008 Ted Kosan.
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

package org.mathpiper.ide.mpreduceplugin;


import org.mathpiperide.ResponseListener;
import java.io.*;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.EBMessage;
import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.msg.*;
import java.util.ArrayList;

/**
 *
 * @author tk
 */

public class MPReduceWrapper implements Runnable, EBComponent
{

	private static MPReduceWrapper mpreduceInstance = null;
	private org.mathpiper.mpreduce.Interpreter2 mpreduce = null;
	private boolean keepRunning = true;
	private ArrayList<ResponseListener> responseListeners;
	private ArrayList<ResponseListener> removeListeners;
	private String expression = null;



	/** Creates a new instance of MPReduceWrapper */
	protected MPReduceWrapper() throws Throwable
	{
		responseListeners = new ArrayList<ResponseListener>();
		removeListeners = new ArrayList<ResponseListener>();
		mpreduce = new org.mathpiper.mpreduce.Interpreter2();

		new Thread(this,"reduce").start();

	}//end constructor.

	public String getStartMessage()
	{
		return mpreduce.getStartMessage();
	}//end method.

	public String getPrompt()
	{
		return "\nIn> ";
	}//end method.

	public static MPReduceWrapper getInstance() throws Throwable
	{
		if(mpreduceInstance == null) {
			mpreduceInstance = new MPReduceWrapper();
		}
		return mpreduceInstance;
	}//end method.



	public synchronized void evaluate(String expression) throws Throwable
	{
		this.expression = expression;

	}//end send.


	public void run()
	{
		keepRunning = true;

		String response = null;

		while(keepRunning == true) 
		{
			try
			{
				if(expression != null)
				{
					response = mpreduce.evaluate(expression);
					
					expression = null;
				}
				
				if(response != null)
				{
					notifyListeners(response);
					
					response = null;
				}
				
				Thread.sleep(100);


			}
			catch(InterruptedException ioe)
			{
			}
			catch(IOException ioe)
			{
				notifyListeners(ioe.toString());
			}
			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}//end while.

	}//end method.

	public void stop()
	{
		keepRunning = false;
	}//end method.

	public void interruptEvaluation()
	{
		mpreduce.interruptEvaluation();
	}




	public void addResponseListener(ResponseListener listener)
	{
		responseListeners.add(listener);
	}//end method.

	public void removeResponseListener(ResponseListener listener)
	{
		responseListeners.remove(listener);
	}//end method.

	protected void notifyListeners(String response)
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


	public void handleMessage(EBMessage message) {
		if (message instanceof PropertiesChanged) {
			//String propertyFilename = jEdit.getProperty(MaximaPlugin.OPTION_PREFIX + "filepath");
		}
		if (message instanceof org.gjt.sp.jedit.msg.EditorExiting) {
			if(mpreduce != null)
			{
				try
				{
					mpreduce.evaluate("bye;");
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
			}

		}

	}//end method.


}//end class.



