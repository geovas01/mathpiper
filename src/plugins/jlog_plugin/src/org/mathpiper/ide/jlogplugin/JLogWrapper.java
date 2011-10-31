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

package org.mathpiper.ide.jlogplugin;


import org.mathpiperide.ResponseListener;
import java.io.*;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.EBMessage;
import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.msg.*;
import java.util.ArrayList;

import java.util.*;
import java.io.*;
import ubc.cs.JLog.Foundation.*;

/**
 *
 * @author tk
 */

public class JLogWrapper implements Runnable, EBComponent
{

	private static JLogWrapper jlogInstance = null;
	private jPrologAPI   jlog = null;
	private boolean keepRunning = true;
	private ArrayList<ResponseListener> responseListeners;
	private ArrayList<ResponseListener> removeListeners;
	private String expression = null;



	/** Creates a new instance of JLogWrapper */
	protected JLogWrapper() throws Throwable
	{
		responseListeners = new ArrayList<ResponseListener>();
		removeListeners = new ArrayList<ResponseListener>();
		jlog = new jPrologAPI("");

		new Thread(this,"jlog").start();
	}//end constructor.

	public String getStartMessage()
	{
		return jlog.getRequiredCreditInfo();
	}//end method.

	public String getPrompt()
	{
		return "\n?- ";
	}//end method.

	public static JLogWrapper getInstance() throws Throwable
	{
		if(jlogInstance == null) {
			jlogInstance = new JLogWrapper();
		}
		return jlogInstance;
	}//end method.



	public synchronized void evaluate(String expression) throws Throwable
	{
		this.expression = expression;

	}//end send.


	public void run()
	{
		keepRunning = true;

		Hashtable response = null;

		while(keepRunning == true) 
		{
			try
			{
				if(expression != null)
				{
					
				    if(expression.trim().equals(";"))
				    {
				        response = jlog.retry();
				    }
				    else
				    {
				        response = jlog.query(expression,null);
				    }
					
					expression = null;
				}
				
				if(response != null)
				{
				    StringBuilder stringBuilder = new StringBuilder();
				    
				    Enumeration enumeration = response.elements();
				    
				    while(enumeration.hasMoreElements())
				    {
				        Object object = enumeration.nextElement();
				        stringBuilder.append(object.toString());
				    }
				    
					notifyListeners(stringBuilder.toString() + "\nyes");
					
					response = null;
				}
				else
				{
				    notifyListeners("no");
				}
				
				Thread.sleep(100);


			}
			catch(InterruptedException ioe)
			{
			}
			catch(Throwable e)
			{
				notifyListeners(e.toString());
			}
		}//end while.
		
		interruptEvaluation();

	}//end method.
	
	
	public void consultSource(String code)
	{
	    jlog.consultSource(code);
	}

	public void stop()
	{
		keepRunning = false;
	}//end method.

	public void interruptEvaluation()
	{
		jlog.stop();
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
			if(jlog != null)
			{
				try
				{
					jlog.stop();
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
			}

		}

	}//end method.


}//end class.



