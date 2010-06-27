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

package org.mathpiper.ide.fricasplugin;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mathpiperide.ResponseListener;
import java.io.*;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.EBMessage;
import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.msg.*;

/**
 *
 * @author tk
 */

public class FriCASWrapper implements Runnable, EBComponent
{

	private static FriCASWrapper fricasInstance = null;

	private StringBuffer responseBuffer;
	private Pattern inputPromptPattern;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String response;
	private String startMessage;
	private String fileSearchFriCASAppendResponse;
	private String fileSearchLispAppendResponse;
	private ArrayList<ResponseListener> responseListeners;
	private boolean keepRunning;
	private String prompt;
	private ArrayList<ResponseListener> removeListeners;
	private Process fricasProcess = null;

	/** Creates a new instance of FriCASWrapper */
	protected FriCASWrapper() throws Throwable
	{

		ArrayList command = new ArrayList();
		String path = jEdit.getProperty(FriCASPlugin.OPTION_PREFIX + "path");


		if(path.equals(""))
		{
			throw new IOException("No path to the CAS executible specified.  A path must be specified in Plugins -> Plugin Options.");
		}


		if(! new File(path).exists() )
		{
			throw new IOException("The path to the CAS executable is invalid: " + path);
		}

		org.gjt.sp.jedit.EditBus.addToBus(this);


		responseListeners = new ArrayList<ResponseListener>();
		removeListeners = new ArrayList<ResponseListener>();

		command.add(path);
		command.add("-nox");
		command.add("-noclef");


		try{

			ProcessBuilder processBuilder = new ProcessBuilder(command);
			fricasProcess = processBuilder.start();
			inputStream = fricasProcess.getInputStream();
			outputStream = fricasProcess.getOutputStream();
			responseBuffer = new StringBuffer();
			inputPromptPattern = Pattern.compile("\\n\\([0-9]+\\) \\->");
			startMessage = getResponse();


			new Thread(this,"fricas").start();

		}catch(IOException ioe)
		{
			if(fricasProcess != null)
			{
				fricasProcess.destroy();
			}

			throw new IOException("Invalid path to executible or target CAS failed to launch: " + path);
		}

	}//end constructor.

	public String getStartMessage()
	{
		return startMessage;
	}//end method.

	public String getPrompt()
	{
		return prompt;
	}//end method.

	public static FriCASWrapper getInstance() throws Throwable
	{
		if(fricasInstance == null) {
			fricasInstance = new FriCASWrapper();
		}
		return fricasInstance;
	}//end method.



	public synchronized void send(String send) throws Throwable
	{
		outputStream.write(send.getBytes());
		outputStream.flush();
	}//end send.


	public void run()
	{
		keepRunning = true;

		String response;

		while(keepRunning == true)
		{
			try
			{
				response = getResponse();
				notifyListeners(response);
			}catch(IOException ioe)
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


	protected String getResponse() throws Throwable
	{
		boolean keepChecking = true;

mainLoop: while(keepChecking)
		{
			int serialAvailable = inputStream.available();
			if (serialAvailable == 0)
			{
				try
				{
					Thread.sleep(100);
				}
				catch(InterruptedException ie)
				{
					System.out.println("FriCAS session interrupted.");
				}
				continue mainLoop;
			}//end while

			byte[] bytes = new byte[serialAvailable];

			inputStream.read( bytes, 0, serialAvailable );
			responseBuffer.append(new String(bytes));
			response = responseBuffer.toString();
			//System.out.println("SSSSS " + response);
			Matcher matcher = inputPromptPattern.matcher(response);
			if(matcher.find())
			{
				//System.out.println("PPPPPP found end");
				responseBuffer.delete(0,responseBuffer.length());


				int promptIndex = response.lastIndexOf("\n") + 1;

				prompt = response.substring(promptIndex,response.length());

				response = response.substring(0,promptIndex);

				keepChecking = false;

			}//end if.

		}//end while.

		return response;

	}//end method

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
			if(fricasProcess != null)
			{
				//fricasProcess.destroy();
				try{

					send(")quit\n");
				}
				catch(Throwable t)
				{
					t.printStackTrace();
				}
			}
		}

	}//end method.


}//end class.



