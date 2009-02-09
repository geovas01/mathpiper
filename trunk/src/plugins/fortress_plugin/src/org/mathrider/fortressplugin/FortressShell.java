//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.fortressplugin;

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
//import org.fortress.interpreters.EvaluationResponse;
//import org.mathrider.fortressplugin.FortressInterpreter;
import java.util.Map;
//import org.mathrider.piper.LispOutput;

public class FortressShell extends Shell
{
	//private org.fortress.interpreters.Interpreter interpreter;
	
	
	public FortressShell()
	{
		super("Fortress");
		//interpreter = FortressInterpreter.getInstance(); 
		//interpreter = org.fortress.interpreters.Interpreters.getSynchronousInterpreter();
		
		
		//Console console = (Console) jEdit.getPlugin("org.sageide.SAGEIDEPlugin").getPluginJAR().getClassLoader().loadClass("console.Console",1);
		
	}//end constructor.
	
	
	
	//{{{Begin Shell implementation.
	public void printInfoMessage(Output output)
	{
		
		//printUsage(jEdit.getProperty(PiperPlugin.NAME + ".shell.msg.info"), null, output);
		//output.print(null, jEdit.getProperty(PiperPlugin.NAME + ".shell.msg.usage"));
		/*EvaluationResponse response;

			response = interpreter.evaluate("Version();");
			output.print(null, "Fortress version " +  response.getResult());
			
			if(response.isExceptionThrown())
			{
				output.print(java.awt.Color.RED,response.getExceptionMessage() );
			}*/

	
	}//end method.

	public void execute(Console console, String input, Output output, Output error, String command)
	{
		/*EvaluationResponse response;
		

			response = interpreter.evaluate(command);
			
			output.print(java.awt.Color.BLUE,"Result> " + response.getResult());

			
			if(!response.getSideEffects().equalsIgnoreCase(""))
			{
				output.print(new java.awt.Color(0,120,0),"Side Effects>\n" + response.getSideEffects());
			}
			
			if(response.isExceptionThrown())
			{
				output.print(java.awt.Color.RED,response.getExceptionMessage() );
			}*/
			output.commandDone();
	

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
		output.writeAttrs(ConsolePane.colorAttributes(console.getPlainColor()), "In> ");
	}//end method.
	
	

	// }}}End Shell implementation
	
	
	
	//{{{ Print usage methods.
	private void printCurrentProjectInfo(Color color, Output output)
	{
		/*
		if (_currentProject == null)
			return;
		output.print(null, jEdit.getProperty(AntFarmPlugin.NAME
			+ ".shell.label.current-file"));
		String projectName = _currentProject.getName() != null ? _currentProject.getName()
			: "Untitled";

		output.print(color, projectName + " (" + _currentBuildFile.getAbsolutePath()
			+ ")\n");

		output.print(null, jEdit.getProperty(AntFarmPlugin.NAME
			+ ".shell.label.available-targets"));
		String info = "";
		Enumeration targets = _currentProject.getTargets().keys();
		while (targets.hasMoreElements())
		{
			String target = (String) targets.nextElement();
			info += target + printDefaultLabel(_currentProject, target) + "\t";
		}
		output.print(color, info); */

	}

	private String printDefaultLabel(String target)
	{
		//if (project.getDefaultTarget().equals(target))
			//return " [default]";
		return "";
	}

	private void printUsage(String additionalMessage, Color color, Output output)
	{
		//output.print(color, additionalMessage);
		//output.print(null, jEdit.getProperty(AntFarmPlugin.NAME + ".shell.msg.usage"));
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
