package org.mathrider.jyacasplugin;

import net.sf.yacas.*;
import net.sf.yacas.Yacasexception;
import console.Output;

import java.io.*;

/**
 *
 * @author tk
 */


 public class JYacasInterpreter { // extends LispOutput {

	private static JYacasInterpreter instance = null;
    private CYacas jyacas;
	private StringOutput stringOutput;
 
    /** Creates a new instance of YacasInterpreter */
    protected JYacasInterpreter() throws Yacasexception {
		
		stringOutput = new StringOutput();
	    
		jyacas = new CYacas(stringOutput);
        
        boolean scripts_found = loadScripts();
 
        if (!scripts_found) throw new Yacasexception ("Yacas error: Unable to load yacas.jar");
			
        jyacas.Evaluate("Load(\"yacasinit.ys\");");
 
    }//end constructor.
	
   public static JYacasInterpreter getInstance() throws Yacasexception{
      if(instance == null) {
         instance = new JYacasInterpreter();
      }
      return instance;
   }
 
    /** Searches for the file yacas.jar and passes its absolute path to the Yacas interpreter.
     * This method searches in the classpath (declared i.e. in MANIFEST.MF) for the file yacasinit.ys.
     * yacasinit.ys is inside yacas.jar.
     * Returns true if successful.*/
    private boolean loadScripts() {
        java.net.URL detectURL = java.lang.ClassLoader.getSystemResource("yacasinit.ys");
        // if yacasinit.ys not found:
        if (detectURL == null) return false;
 
        String detect = detectURL.getPath(); // file:/home/av/src/lib/yacas.yar!/yacasinit.ys
        String archive = detect.substring(0, detect.lastIndexOf('!')); // file:/home/av/src/lib/yacas.jar
 
        java.util.zip.ZipFile z;
        try {
            z = new java.util.zip.ZipFile(new java.io.File(new java.net.URI(archive)));
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
 
        // Pass the absolute path of yacas.jar to Yacas.
        LispStandard.zipFile = z;
 
        return true;
    }
 
    /** Use this method to pass an expression to the Yacas interpreter.
     *  Returns the output of the interpreter.
     */
    public String evaluate(String input) throws net.sf.yacas.Yacasexception {
		
			String result = jyacas.Evaluate(input);
			
			String outputMessage = stringOutput.toString();
			
			if(outputMessage != null && outputMessage.length() != 0){
				return result + ":\n" + outputMessage;
			}
			else{
				return result;
			}
    }//end method.
    

    
}//end class.

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
