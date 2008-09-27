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

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathrider.piper;

//import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.exceptions.PiperException;
import org.mathrider.piper.io.CachedStdFileInput;
import org.mathrider.piper.io.StdFileOutput;
import org.mathrider.piper.lisp.Standard;
import java.io.*;

/**
 * Provides a command line console which can be used to interact with a piper instance.
 * @author
 */
public class Console extends Thread
{
	Interpreter interpreter;
                
	public Console()
	{
		//Piper needs an output stream to send "side effect" output to.
		StdFileOutput stdoutput = new StdFileOutput(System.out);
		interpreter = new Interpreter(stdoutput);
	}
        
        void addDirectory(String directory)
        {
            interpreter.addDirectory(directory);
        }
        
        String readLine(InputStream aStream)

	{
		StringBuffer line = new StringBuffer();
		try
		{
			int c = aStream.read();
			while (c != '\n')
			{
				line.append((char)c);
				c = aStream.read();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return line.toString();
	}
        
        String evaluate(String input) throws PiperException
        {
            return interpreter.evaluate(input);
        }

    


    /**
     * The normal entry point for running piper from a command line.  It processes command line arguments,
     * sets piper's standard output to System.out, then enters a REPL (Read, Evaluate, Print Loop).  Currently,
     * the console only supports the --rootdir and --archive command line options.
     *
     * @param argv
     */
    public static void main(String[] argv)
    {
        Console console = new Console();
        String defaultDirectory = null;
        String archive = null;
        
        
        
        int i = 0;
        while (i < argv.length)
        {
            if (argv[i].equals("--rootdir"))
            {
                i++;
                defaultDirectory = argv[i];
            }
            if (argv[i].equals("--archive"))
            {
                i++;
                archive = argv[i];
            } else
            {
                break;
            }
            i++;
        }
        int scriptsToRun = i;





        //Change the default directory. tk.
        if (defaultDirectory != null)
        {
            console.addDirectory(defaultDirectory );
        }
        

        
/*

            if (scriptsToRun == argv.length)
            {
                System.out.println("Out> " + result);
            }
        
            
        if (scriptsToRun < argv.length)
        {

            try
            {
                for (; scriptsToRun < argv.length; scriptsToRun++)
                {
                    interpreter.evaluate("Load(\"" + argv[scriptsToRun] + "\");");
                }
            } catch (PiperException pe)
            {
                pe.printStackTrace();
            }

            return;
        }//end if.

*/


        System.out.println("\nPiper version '" + Version.version + "'.");

        System.out.println("See http://mathrider.org for more information and documentation on Piper.");

        System.out.println("\nTo exit Piper, enter \"Exit()\" or \"exit\" or \"quit\" or Ctrl-c.\n");
        /*TODO fixme
        System.out.println("Type ?? for help. Or type ?function for help on a function.\n");
        System.out.println("Type 'restart' to restart Piper.\n");
         */
        System.out.println("To see example commands, keep typing Example()\n");

        //piper.Evaluate("BubbleSort(N(PSolve(x^3-3*x^2+2*x,x)), \"<\");");

        //System.out.println("Piper in Java");
        boolean quitting = false;
        while (!quitting)
        {
            System.out.print("In> ");
            String input = console.readLine(System.in);
            input = input.trim();


            String rs = "";
            try
            {
                rs = console.evaluate(input);
            } catch (PiperException pe)
            {
                pe.printStackTrace();
            }


            System.out.println("Out> " + rs);

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit"))
            {

                quitting = true;
            }
        }
    }
}

