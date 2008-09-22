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

import org.mathrider.piper.lisp.Standard;
import java.io.File;

/**
 * Use this class in order to access the Piper interpreter from an external application.
 * Usage:
 * import org.mathrider.piper.PiperInterpreter;
 * PiperInterpreter interpreter = new PiperInterpreter();
 * String output1 = interpreter.Evaluate("a := 5");
 * String output2 = interpreter.Evaluate("Solve(x*x == a, x)");
 *
 *
 * @author av
 */
public class PiperInterpreter
{

    private CPiper piper;
    private StringOutput stringOutput;
    private StringBuffer outputCollector;

    /** Creates a new instance of PiperInterpreter */
    public PiperInterpreter() throws Piperexception
    {
        outputCollector = new StringBuffer();
        stringOutput = new StringOutput(outputCollector);

        piper = new CPiper(stringOutput);

        boolean scripts_found = loadScripts();

        if (!scripts_found)
        {
            System.err.println("Piper error: Unable to load piper.jar");
        }
        piper.evaluate("Load(\"piperinit.pi\");");

    }

    public void addDirectory( String directory)
    {
        String toEvaluate = "DefaultDirectory(\"" + directory + File.separator + "\");";

        String result = "";
        try
        {
            result = piper.evaluate(toEvaluate);
        } catch (Piperexception pe)
        {
            pe.printStackTrace();
        }

    }

    /** Searches for the file piper.jar and passes its absolute path to the Piper interpreter.
     * This method searches in the classpath (declared i.e. in MANIFEST.MF) for the file piperinit.pi.
     * piperinit.pi is inside piper.jar.
     * Returns true if successful.*/
    private boolean loadScripts()
    {

        String defaultDirectory = null;
        String archive = "";
        String detect = "";
        String pathParent = "";
        boolean inZipFile = false;
        {

            /*My thought is that this initialization code should be moved to a single class instead of being
             * duplicated in multiple classes. tk.
             */
            java.net.URL detectURL = java.lang.ClassLoader.getSystemResource("piperinit.pi");
            pathParent = new File(detectURL.getPath()).getParent();
            addDirectory(pathParent);
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
                        return false;
                    }
                }



            //System.out.println("Found archive ["+archive+"]");
            } else
            {
                System.out.println("Archive not found!!!!");
            }
        }





        return true;
    }

    /** Use this method to pass an expression to the Piper interpreter.
     *  Returns the output of the interpreter.
     */
    public String evaluate(String input) throws Piperexception
    {
        String output1 = piper.evaluate(input);
        String output2 = outputCollector.toString();
        outputCollector.delete(0, outputCollector.length());

        return output1 + "\n" + output2;
    }
}
