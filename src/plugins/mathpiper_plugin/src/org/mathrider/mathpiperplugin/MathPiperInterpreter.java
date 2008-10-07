//Copyright (C) 2008 Ted Kosan (license information is at the end of this document.)

package org.mathrider.mathpiperplugin;

//import org.mathrider.piper.CPiper;
//import org.mathrider.piper.Piperexception;
import org.mathpiper.Interpreter;
import org.mathpiper.exceptions.PiperException;
import console.Output;

import java.io.*;


import errorlist.*;

/**
 *
 * @author tk
 */


 public class MathPiperInterpreter { 

	private static MathPiperInterpreter instance = null;
    private Interpreter mathpiper;
	private StringOutput stringOutput;
	//java.util.zip.ZipFile scriptsZip;
	
	private static DefaultErrorSource errorSource;
 
    /** Creates a new instance of MathPiperInterpreter */
    protected MathPiperInterpreter() throws PiperException {
		
		stringOutput = new StringOutput();
	    
		mathpiper = new Interpreter(stringOutput);
        
        //boolean scripts_found = loadScripts();
 
       // if (!scripts_found) throw new Piperexception ("MathPiper error: Unable to load mathpiper.jar");
			
        //mathpiper.evaluate("Load(\"mathpiperinit.pi\");");
 
    }//end constructor.
	
   public static MathPiperInterpreter getInstance() throws PiperException{
      if(instance == null) {
         instance = new MathPiperInterpreter();
      }
      return instance;
   }//end method.
   

   
   //public java.util.zip.ZipFile getScriptsZip()
   //{
	//   return scriptsZip;
   //}//end method.
   //
   // /** Searches for the file mathpiper.jar and passes its absolute path to the Piper interpreter.
   //  * This method searches in the classpath (declared i.e. in MANIFEST.MF) for the file mathpiperinit.pi.
   //  * mathpiperinit.pi is inside mathpiper.jar.
   //  * Returns true if successful.*/
   // private boolean loadScripts() {
   //     java.net.URL detectURL = java.lang.ClassLoader.getSystemResource("mathpiperinit.pi");
   //     // if mathpiperinit.pi not found:
   //     if (detectURL == null) return false;
   //
   //     String detect = detectURL.getPath(); // file:/home/av/src/lib/mathpiper.yar!/mathpiperinit.pi
   //     String archive = detect.substring(0, detect.lastIndexOf('!')); // file:/home/av/src/lib/mathpiper.jar
   //
   //     try {
   //         scriptsZip = new java.util.zip.ZipFile(new java.io.File(new java.net.URI(archive)));
   //     } catch (Exception e) {
   //         System.err.println(e.toString());
   //         return false;
   //     }
   //
   //     // Pass the absolute path of mathpiper.jar to Piper.
   //     org.mathrider.piper.LispStandard.zipFile = scriptsZip;
   //
   //     return true;
   // }
 
    /** Use this method to pass an expression to the Piper interpreter.
     *  Returns the output of the interpreter.
     */
    public String evaluate(String input) throws PiperException {
		
			String result = mathpiper.evaluate(input);
			
			String outputMessage = stringOutput.toString();
			
			if(outputMessage != null && outputMessage.length() != 0){
				return result + ":\n" + outputMessage;
			}
			else{
				return result;
			}
    }//end method.
	
	
	public static DefaultErrorSource getErrorSource()
	{
		if(errorSource == null)
		{
			errorSource = new DefaultErrorSource("MathRider");
			ErrorSource.registerErrorSource(errorSource);
		}//end if.

		return errorSource;
	}//end method.
    

    
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
