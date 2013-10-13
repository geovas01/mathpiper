//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathpiper.ide.sqldbplugin;

import java.awt.Color;
//import java.io.File;
//import java.util.Enumeration;
//import java.util.Properties;
//import java.util.Vector;
import java.io.*;


import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.Macros;
import org.gjt.sp.jedit.EditPlugin;

import console.Console;
import console.ConsolePane;
import console.Output;
import console.Shell;

import org.mathpiper.ide.sqldbplugin.SQLDBWrapper;

public class SQLDBShell extends Shell implements org.mathpiperide.ResponseListener
{
	private SQLDBWrapper sqldb;
	private String sqldbStartMessage;
	private Output lastRequestOutput;
	private Console lastRequestConsole;



	public SQLDBShell()
	{
		super("SQLDB");

		try{

			sqldb = SQLDBWrapper.getInstance();
			//sqldbStartMessage = sqldb.getStartMessage();
			sqldbStartMessage = "";

		}catch(java.io.IOException ioe)
		{

			Macros.message(jEdit.getActiveView(), ioe.getMessage());

			sqldbStartMessage = "xxx";
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}


	}//end constructor.



	//{{{Begin Shell implementation.
	public void printInfoMessage(Output output)
	{
		output.print(null,sqldbStartMessage);

	}//end method.


	public void response(String response)
	{
		org.gjt.sp.jedit.View view = org.gjt.sp.jedit.jEdit.getActiveView();
		org.gjt.sp.jedit.gui.DockableWindowManager dockableWindowManager = view.getDockableWindowManager();
		lastRequestConsole = (Console) dockableWindowManager.getDockable("console");
		lastRequestConsole.setShell("SQLDB");
		lastRequestOutput = lastRequestConsole.getOutput();

		
		lastRequestOutput.print(null, response);


		lastRequestOutput.commandDone();
	}//end method.

	public boolean remove()
	{
		return true;
	}//end method.

	public void response(java.util.HashMap response)
	{
	}


	public void execute(Console console, String input, Output output, Output error, String command)
	{
		try{
			sqldb = SQLDBWrapper.getInstance();



			if(! command.equals(""))
			{
				//Note:tk: must set output and console another way for when user executes sqldb fold
				//before using the shell.
				lastRequestOutput = output;
				lastRequestConsole = console;

				try
				{
					sqldb.addResponseListener(this);

					sqldb.send(command + "\n");
					


				}catch(Throwable t)
				{
					output.print(null,t.getMessage() );
					output.commandDone();
				}

			}//end if.

		}
		catch(IOException ioe)
		{
			output.print(null, ioe.getMessage());
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}

	}//end method.

	public void stop(Console console)
	{
		//sqldb.interruptEvaluation();
	}

	public boolean waitFor(Console console)
	{

		return true;
	}//end method.


	public void printPrompt(Console console, Output output)
	{
		if(sqldb != null)
		{
			output.writeAttrs(ConsolePane.colorAttributes(console.getPlainColor()), "sql>"); //sqldb.getPrompt());
		}
		else
		{
			output.print(null, " ");
		}
	}//end method.



	// }}}End Shell implementation



	//{{{ Print usage methods.
	private void printCurrentProjectInfo(Color color, Output output)
	{

	}

	private String printDefaultLabel(String target)
	{

		return "";
	}

	private void printUsage(String additionalMessage, Color color, Output output)
	{

	}

	//}}}

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
