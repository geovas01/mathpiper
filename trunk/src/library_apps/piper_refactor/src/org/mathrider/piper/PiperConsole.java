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
import org.mathrider.piper.lisp.Standard;
import java.io.*;

/**
 * Provides a command line console which can be used to interact with a piper instance.
 * @author
 */
public class PiperConsole extends Thread
{

    /**
     * This method is not called by any code in the piper implementation so
     * I am not sure what its purpose is. tk.
     * @param aStream    a stream of bytes.
     * @return                  a string containing one line of characters.
     */
    public static String readLine(InputStream aStream)
    {
        StringBuffer line = new StringBuffer();
        try
        {
            int c = aStream.read();
            while (c != '\n')
            {
                line.append((char) c);
                c = aStream.read();
            }
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return line.toString();
    }
    //static boolean quitting = false;
    
    static void addDirectory(CPiper piper, String directory)
    {
                 String toEvaluate = "DefaultDirectory(\""+ directory +  File.separator + "\");";

            String result = "";
            try
            {
                result = piper.evaluate(toEvaluate);
            } catch (Piperexception pe)
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
        String defaultDirectory = null;
        String archive = "";
        String detect = "";
        String pathParent = "";
        boolean inZipFile = false;
        
        //Piper needs an output stream to send "side effect" output to.
        StdFileOutput stdoutput = new StdFileOutput(System.out);
        CPiper piper = new CPiper(stdoutput);
        piper.env.iCurrentInput = new CachedStdFileInput(piper.env.iInputStatus);
        
        
            /*My thought is that this initialization code should be moved to a single class instead of being
             * duplicated in multiple classes. tk.
             */
            java.net.URL detectURL = java.lang.ClassLoader.getSystemResource("piperinit.pi");
            pathParent = new File(detectURL.getPath()).getParent();
            addDirectory(piper, pathParent);
            //StdFileInput.setPath(pathParent + File.separator);


            if (detectURL != null)
            {
                detect = detectURL.getPath(); // file:/home/av/src/lib/piper.jar!/piperinit.pi

                if (detect.indexOf('!') != -1)
                {
                    archive = detect.substring(0, detect.lastIndexOf('!')); // file:/home/av/src/lib/piper.jar

                    try
                    {
                        String zipFileName = archive;//"file:/Users/ayalpinkus/projects/JavaPiper/piper.jar";

                        java.util.zip.ZipFile z = new java.util.zip.ZipFile(new File(new java.net.URI(zipFileName)));
                        Standard.zipFile = z;
                        inZipFile = true;
                    } catch (Exception e)
                    {
                        System.out.println("Failed to find piper.jar" + e.toString());
                    }
                }



            //System.out.println("Found archive ["+archive+"]");
            } else
            {
                
                System.out.println("Code is not in an archive.");
            }
        
        
        
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
        

        
            String result = "";
            try
            {
                result = piper.evaluate("Load(\"piperinit.pi\");");

            } catch (Piperexception pe)
            {
                pe.printStackTrace();
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
            } catch (Piperexception pe)
            {
                pe.printStackTrace();
            }

            return;
        }//end if.




        System.out.println("\nPiper version '" + CVersion.version + "'.");

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
            } catch (Piperexception pe)
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

