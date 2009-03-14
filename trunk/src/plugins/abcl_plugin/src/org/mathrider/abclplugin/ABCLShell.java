//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)
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

package org.mathrider.abclplugin;

//import javax.script.*;
import org.armedbear.lisp.*;

import java.awt.Color;
import java.io.*;


import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.EditPlugin;

import console.Console;
import console.ConsolePane;
import console.Output;
import console.Shell;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



public class ABCLShell extends Shell
{
	//private static ScriptEngine interpreter;
	private static Interpreter interpreter;

	public ABCLShell() //throws org.mathrider.abcl.Piperexception
	{
		super("ABCL");
		getInterpreter();

	}//end constructor.
	
	public static synchronized Interpreter getInterpreter()
	{
		if(interpreter == null)
		{
			//interpreter = new ScriptEngineManager().getEngineByExtension("lisp");
			interpreter = Interpreter.createInstance();
			return interpreter;
		}
		else
		{
			return interpreter;
		}
	}//end method.



	//{{{Begin Shell implementation.
	public void printInfoMessage(Output output)
	{

		//printUsage(jEdit.getProperty(ABCLPlugin.NAME + ".shell.msg.info"), null, output);
		//output.print(null, jEdit.getProperty(ABCLPlugin.NAME + ".shell.msg.usage"));
		//try
		//{
		//	output.print(null, "Piper_ME version " + interpreter.evaluate("Version();") );
		//}
		//catch( org.mathrider.abcl.Piperexception pe )
		//{
		//	output.print(java.awt.Color.RED,pe.getMessage() );
		//}

	}//end method.

	public void execute(Console console, String input, Output output, Output error, String script)
	{

		try {
			
			LispObject response = interpreter.eval(script);
			output.print(null, response.writeToString() );
		}
		catch(Throwable e)
		{

			output.print(java.awt.Color.RED,e.getMessage() );
			
			e.printStackTrace();
		}
		finally {
			//Var.popThreadBindings();
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
		output.writeAttrs(ConsolePane.colorAttributes(console.getPlainColor()), "\n=> ");
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
		//output.print(color, additionalMessage);
		//output.print(null, jEdit.getProperty(AntFarmPlugin.NAME + ".shell.msg.usage"));
	}

	//}}}

}//end class.



