//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.mathpiperplugin;

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
import org.mathpiper.exceptions.PiperException;
import org.mathrider.mathpiperplugin.MathPiperInterpreter;
//import org.mathrider.piper.LispOutput;

public class MathPiperShell extends Shell
{
	private MathPiperInterpreter interpreter;
	
	
	public MathPiperShell() throws PiperException
	{
		super("Piper");
		interpreter = MathPiperInterpreter.getInstance(); //new StreamOutput(System.out) 
		//Console console = (Console) jEdit.getPlugin("org.sageide.SAGEIDEPlugin").getPluginJAR().getClassLoader().loadClass("console.Console",1);
		
	}//end constructor.
	
	
	
	//{{{Begin Shell implementation.
	public void printInfoMessage(Output output)
	{
		
		//printUsage(jEdit.getProperty(PiperPlugin.NAME + ".shell.msg.info"), null, output);
		//output.print(null, jEdit.getProperty(PiperPlugin.NAME + ".shell.msg.usage"));
		try
		{
			output.print(null, "Piper version " + interpreter.evaluate("Version();") );
		}
		catch( PiperException pe )
		{
			output.print(java.awt.Color.RED,pe.getMessage() );
		}
	
	}//end method.

	public void execute(Console console, String input, Output output, Output error, String command)
	{
		try 
		{
			String result = interpreter.evaluate(command);
			output.print(java.awt.Color.BLUE,"Out> " + result);
			
			
		}catch(PiperException pe) 
		{
			output.print(java.awt.Color.RED,pe.getMessage() );
		}
		finally
		{
			output.commandDone();
		}
		/*
		stop(console);
		_output = output;
		command = command.trim();

		if (command.startsWith("!"))
		{
			if (_currentProject == null)
			{

				output.print(console.getErrorColor(),
					jEdit.getProperty(AntFarmPlugin.NAME
						+ ".shell.msg.non-selected"));
				output.commandDone();
				return;
			}
			_currentTarget = command.indexOf(" ") > 0 ? command.substring(1, command
				.indexOf(" ")) : command.substring(1);

			if (_currentTarget.equals(""))
			{
				_currentTarget = _currentProject.getDefaultTarget();
			}
			Target target = (Target) _currentProject.getTargets().get(_currentTarget);

			if (target == null)
			{
				printUsage("Not a valid target: " + _currentTarget, console
					.getErrorColor(), output);
				output.commandDone();
				return;
			}

			_targetRunner = new TargetRunner(target, _currentBuildFile, console
				.getView(), output, AntCommandParser
				.parseAntCommandProperties(command));
		}
		else if (command.equals("?"))
		{

			printUsage("", null, output);

			Vector buildFiles = getAntFarm(console).getAntBuildFiles();
			output.print(null, jEdit.getProperty(AntFarmPlugin.NAME
				+ ".shell.label.available-files"));

			String fileList = "";
			for (int i = 0; i < buildFiles.size(); i++)
			{
				fileList += "(" + (i + 1) + ") " + buildFiles.elementAt(i) + "\n";
			}
			output.print(console.getInfoColor(), fileList);

			printCurrentProjectInfo(console.getInfoColor(), output);

			output.commandDone();
		}
		else if (command.startsWith("="))
		{

			Vector buildFiles = getAntFarm(console).getAntBuildFiles();

			try
			{
				int fileNumber = Integer.parseInt(command.substring(1));

				if (fileNumber - 1 >= buildFiles.size())
				{
					printUsage(jEdit.getProperty(AntFarmPlugin.NAME
						+ ".shell.msg.no-build-file"), console
						.getErrorColor(), output);
					output.commandDone();
					return;
				}
				String buildFilePath = (String) buildFiles
					.elementAt(fileNumber - 1);
				File buildFile = new File(buildFilePath);
				Project project = null;
				try
				{
					project = getAntFarm(console).getProject(
						buildFile.getAbsolutePath());
					setCurrentProject(project, buildFile, console);
				}
				catch (Exception e)
				{
					setCurrentProject(null, buildFile, console);
					output.print(console.getErrorColor(), jEdit
						.getProperty(AntFarmPlugin.NAME
							+ ".shell.msg.broken-file")
						+ buildFile + "\n" + e);
					output.commandDone();
					return;
				}
				output.commandDone();
			}
			catch (NumberFormatException e)
			{
				printUsage(jEdit.getProperty(AntFarmPlugin.NAME
					+ ".shell.msg.invalid-usage"), console.getErrorColor(),
					output);
				output.commandDone();
				return;
			}
		}
		else if (command.startsWith("+"))
		{

			String filePath = command.substring(1);
			try
			{

				// Get the provided path, or look up the tree.
				File file = getFile(filePath, console);
				filePath = FileUtils.getAbsolutePath(file);

				// try to reload the project
				getAntFarm(console).reloadAntBuildFile(filePath);

				// initiate the project
				Project project = getAntFarm(console).getProject(filePath);
				setCurrentProject(project, file, console);
				getAntFarm(console).addAntBuildFile(filePath);
			}
			catch (Exception e)
			{
				output.print(console.getErrorColor(), jEdit
					.getProperty(AntFarmPlugin.NAME + ".shell.msg.broken-file")
					+ filePath + "\n" + e);
				output.commandDone();
				return;
			}
			output.commandDone();
		}
		else if (!command.equals(""))
		{
			printUsage(jEdit.getProperty(AntFarmPlugin.NAME
				+ ".shell.msg.invalid-usage"), console.getErrorColor(), output);
		}
		
		*/
		//output.commandDone();	
	}//end method.

	public void stop(Console console)
	{
		/*
		if (_targetRunner != null)
		{
			if (_targetRunner.isAlive())
			{
				_targetRunner.stop();

				if (_output != null)
				{
					_output.print(console.getErrorColor(), jEdit
						.getProperty(AntFarmPlugin.NAME
							+ ".shell.msg.killed"));
				}
			}
			_targetRunner.resetLogging();
			_targetRunner = null;
		}
		*/
	}

	public boolean waitFor(Console console)
	{
		/*
		if (_targetRunner != null)
		{
			try
			{
				synchronized (_targetRunner)
				{
					_targetRunner.join();
					_targetRunner = null;
				}
			}
			catch (InterruptedException ie)
			{
				return false;
			}
		}
		*/
		return true;
	}//end method.
	
	
	public void printPrompt(Console console, Output output)
	{
		output.writeAttrs(ConsolePane.colorAttributes(console.getPlainColor()), "\nIn> ");
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
