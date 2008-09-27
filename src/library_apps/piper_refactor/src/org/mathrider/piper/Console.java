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
	Interpreter

    
    static void addDirectory(CPiper piper, String directory)
    {
                 String toEvaluate = "DefaultDirectory(\""+ directory +  File.separator + "\");";

            String result = "";
            try
            {
                result = piper.evaluate(toEvaluate);
            } catch (PiperException pe)
            {
                pe.printStackTrace();
            }

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
            addDirectory(piper, defaultDirectory );
        }
        

        


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
                    piper.evaluate("Load(\"" + argv[scriptsToRun] + "\");");
                }
            } catch (PiperException pe)
            {
                pe.printStackTrace();
            }

            return;
        }//end if.




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
            String input = readLine(System.in);
            input = input.trim();


            String rs = "";
            try
            {
                rs = piper.evaluate(input);
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

