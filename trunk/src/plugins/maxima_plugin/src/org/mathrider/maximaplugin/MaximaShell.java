//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.maximaplugin;

import java.awt.Color;
//import java.io.File;
//import java.util.Enumeration;
//import java.util.Properties;
//import java.util.Vector;
import java.io.*;


import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.EditPlugin;

import console.Console;
import console.ConsolePane;
import console.Output;
import console.Shell;

import org.mathrider.maximaplugin.MaximaWrapper;

public class MaximaShell extends Shell implements org.mathrider.ResponseListener
{
	private MaximaWrapper maxima;
	private String maximaStartMessage;
	private Output lastRequestOutput;
	private Console lastRequestConsole;

	
	
	public MaximaShell()
	{
		super("Maxima");
		
		try{
		
			maxima = MaximaWrapper.getInstance(); 
			maximaStartMessage = maxima.getStartMessage();
			maxima.addResponseListener(this);
		}catch(Throwable t)
		{
			t.printStackTrace();
		}

		
	}//end constructor.
	
	
	
	//{{{Begin Shell implementation.
	public void printInfoMessage(Output output)
	{
		output.print(null,maximaStartMessage);
	
	}//end method.
	
	
	public void response(String response)
	{
		org.gjt.sp.jedit.View view = org.gjt.sp.jedit.jEdit.getActiveView();
		org.gjt.sp.jedit.gui.DockableWindowManager dockableWindowManager = view.getDockableWindowManager();
		lastRequestConsole = (Console) dockableWindowManager.getDockable("console");
		lastRequestConsole.setShell("Maxima");
		lastRequestOutput = lastRequestConsole.getOutput();
		
		if(response.indexOf(".mac") != -1 || response.indexOf(".lisp") != -1 )
		{
			lastRequestOutput.print(null, "\n" + response);
			printPrompt(lastRequestConsole, lastRequestOutput);
		}
		else
		{
			lastRequestOutput.print(null, response);
		}//end if/else/
		
		lastRequestOutput.commandDone();
	}//end method.
	
	public boolean remove()
	{
		return false;
	}//end method.
	
	public void response(java.util.HashMap response)
	{
	}
	

	public void execute(Console console, String input, Output output, Output error, String command)
	{
		//Note:tk: must set output and console another way for when user executes maxima fold
		//before using the shell.
		lastRequestOutput = output;
		lastRequestConsole = console;
		
		try 
		{
			maxima.send(command + "\n");
			
			
			
		}catch(Throwable t) 
		{
			output.print(null,t.getMessage() );
			output.commandDone();
		}

	}//end method.

	public void stop(Console console)
	{

	}

	public boolean waitFor(Console console)
	{

		return true;
	}//end method.
	
	
	public void printPrompt(Console console, Output output)
	{
		output.writeAttrs(ConsolePane.colorAttributes(console.getPlainColor()), maxima.getPrompt());
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
