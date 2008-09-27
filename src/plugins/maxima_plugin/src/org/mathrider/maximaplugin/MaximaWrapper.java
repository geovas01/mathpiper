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

package org.mathrider.maximaplugin;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mathrider.ResponseListener;
import java.io.*;
import errorlist.*;

/**
 *
 * @author tk
 */

public class MaximaWrapper implements Runnable
{

	private static MaximaWrapper maximaInstance = null;

	private StringBuffer responseBuffer;
	private Pattern inputPromptPattern;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String response;
	private String startMessage;
	private String fileSearchMaximaAppendResponse;
	private String fileSearchLispAppendResponse;
	private ArrayList<ResponseListener> responseListeners;
	private boolean keepRunning;
	private String prompt;
	private ArrayList<ResponseListener> removeListeners;

	/** Creates a new instance of MaximaWrapper */
	protected MaximaWrapper() throws IOException
	{

		responseListeners = new ArrayList<ResponseListener>();
		removeListeners = new ArrayList<ResponseListener>();
		ArrayList command = new ArrayList();
		command.add("C:\\Program Files\\Maxima-5.15.0\\bin\\maxima.bat");
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Process maximaProcess = processBuilder.start();
		inputStream = maximaProcess.getInputStream();
		outputStream = maximaProcess.getOutputStream();
		responseBuffer = new StringBuffer();
		inputPromptPattern = Pattern.compile("\\n\\(%i[0-9]+\\)|MAXIMA>");
		startMessage = getResponse();

		//Add temporary files directory to maxima search path.
		File tempFile = File.createTempFile("mathrider", ".tmp");
		tempFile.deleteOnExit();
		String searchDirectory = tempFile.getParent() + File.separator + "###.{mac,mc}";
		searchDirectory = searchDirectory.replace("\\","/");
		send("file_search_maxima: append (file_search_maxima, [\"" + searchDirectory + "\"])$\n");
		fileSearchMaximaAppendResponse = getResponse();


		//Add temporary files directory to lisp search path.
		searchDirectory = tempFile.getParent() + File.separator + "###.{lisp,lsp}";
		searchDirectory = searchDirectory.replace("\\","/");
		send("file_search_lisp: append (file_search_lisp, [\"" + searchDirectory + "\"])$\n");
		fileSearchLispAppendResponse = getResponse();
		//System.out.println("FFF " + fileSearchMaximaAppendResponse);

		new Thread(this,"maxima").start();

	}//end constructor.

	public String getStartMessage()
	{
		return startMessage;
	}//end method.

	public String getPrompt()
	{
		return prompt;
	}//end method.

	public static MaximaWrapper getInstance() throws IOException
	{
		if(maximaInstance == null) {
			maximaInstance = new MaximaWrapper();
		}
		return maximaInstance;
	}//end method.



	public synchronized void send(String send) throws IOException
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
		}//end while.

	}//end method.

	public void stop()
	{
		keepRunning = false;
	}//end method.


	protected String getResponse() throws IOException
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
					System.out.println("Maxima session interrupted.");
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
				int promptIndex = response.lastIndexOf("(%");
				if(promptIndex == -1)
				{
					promptIndex = response.lastIndexOf("MAX");
				}
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


}//end class.



