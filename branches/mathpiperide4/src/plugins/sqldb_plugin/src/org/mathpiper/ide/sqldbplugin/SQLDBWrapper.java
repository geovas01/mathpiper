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



package org.mathpiper.ide.sqldbplugin;

import java.sql.Connection;
import java.sql.SQLException;
import org.hsqldb.lib.RCData;
import org.mathpiper.ide.sqldbplugin.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mathpiperide.ResponseListener;
import java.io.*;
//import errorlist.*;
import org.gjt.sp.jedit.jEdit;

/**
 *
 * @author tk
 */

public class SQLDBWrapper implements Runnable
{

	private static SQLDBWrapper sqldbInstance = null;
	private static String databaseName = "test";

	private StringBuffer responseBuffer;
	private Pattern inputPromptPattern;
	final PipedWriter writer = new PipedWriter();
	final PipedInputStream inputStream = new PipedInputStream();
	private String response;
	private String startMessage;
	private String fileSearchMaximaAppendResponse;
	private String fileSearchLispAppendResponse;
	private ArrayList<ResponseListener> responseListeners;
	private boolean keepRunning;
	private String prompt = "sql> ";
	private ArrayList<ResponseListener> removeListeners;
	private SqlFile sqlFile;
	private ByteArrayOutputStream baos;
	private boolean isExecute = false;


	protected SQLDBWrapper() throws Throwable
	{

		responseListeners = new ArrayList<ResponseListener>();
		removeListeners = new ArrayList<ResponseListener>();

		// RCData conData = new RCData("TestID",
		// "jdbc:hsqldb:file:/home/tkosan/databases/aip/aip", "SA", "", null,
		// null, null, null, null);
		RCData conData = new RCData("TestID", "jdbc:hsqldb:hsql://localhost/" + SQLDBWrapper.databaseName, "SA", "", null, null,
				null, null, null);

		PipedReader reader = new PipedReader(writer);

		baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);

		// String content = baos.toString(charsetName);

		sqlFile = new SqlFile(new StringReader(""), "HSQLDB", printStream, null, true, null);

		sqlFile.setConnection(conData.getConnection());

		new Thread(this,"hsql").start();

	}//end constructor.

	public String getStartMessage()
	{
		return startMessage;
	}//end method.

	public String getPrompt()
	{
		return prompt;
	}//end method.

	public static SQLDBWrapper getInstance(String databaseName) throws Throwable
	{
		SQLDBWrapper.databaseName = databaseName;
		if(sqldbInstance == null) {
			sqldbInstance = new SQLDBWrapper();
		}
		return sqldbInstance;
	}//end method.

	public static SQLDBWrapper getInstance() throws Throwable
	{
		return getInstance("test");
	}

	public synchronized void send(String send) throws Throwable
	{
		Reader reader = new StringReader(send);
		sqlFile.reader = reader;
		isExecute = true;
	}//end send.


	public void run()
	{
		keepRunning = true;

		while(keepRunning == true)
		{
			try
			{
				while(isExecute == false)
				{
					try
					{
						Thread.sleep(100);
					}
					catch(InterruptedException ie)
					{
						System.out.println("HSQL session interrupted.");
					}
		
				}//end while.
				
				isExecute = false;
				
				sqlFile.execute();
				
				String response = baos.toString("UTF-8");
				baos.reset();
				response = response.replace("sql> ", "");
				response = response.replace("  +> ", "");
				notifyListeners(response);
			}catch(IOException ioe)
			{
				notifyListeners(ioe.toString());
			}
			catch(Throwable e)
			{
				notifyListeners(e.toString());
			}
		}//end while.

	}//end method.

	public void stop()
	{
		keepRunning = false;
	}//end method.



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



