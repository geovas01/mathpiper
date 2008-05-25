package org.mathrider.piperplugin;

import org.mathrider.piper.*;
import org.mathrider.piper.Piperexception;
import console.Output;

import java.io.*;


import errorlist.*;

/**
 *
 * @author tk
 */


 public class PiperInterpreter { // extends LispOutput {

//	private static org.gjt.sp.jedit.bsh.Interpreter bshInstance;
	private static PiperInterpreter instance = null;
    private CPiper piper;
	private StringOutput stringOutput;
	java.util.zip.ZipFile scriptsZip;
	
	private static DefaultErrorSource errorSource;
 
    /** Creates a new instance of PiperInterpreter */
    protected PiperInterpreter() throws Piperexception {
		
		stringOutput = new StringOutput();
	    
		piper = new CPiper(stringOutput);
        
        boolean scripts_found = loadScripts();
 
        if (!scripts_found) throw new Piperexception ("Piper error: Unable to load piper.jar");
			
        piper.Evaluate("Load(\"piperinit.pi\");");
 
    }//end constructor.
	
   public static PiperInterpreter getInstance() throws Piperexception{
      if(instance == null) {
         instance = new PiperInterpreter();
      }
      return instance;
   }//end method.
   
   
   //This provides a bsh instance that can be shared by the whole application.
   //Note:tk:look into finding a better place to put this.
//   public static org.gjt.sp.jedit.bsh.Interpreter getBshInstance()
//   {
//      if(bshInstance == null) {
//         	bshInstance = new org.gjt.sp.jedit.bsh.Interpreter();
//		 	//bshInstance.eval("import geogebra.GeoGebraApplet;");
//			//bshInstance.set("ggb",org.mathrider.geogebraplugin.Geogebra.getGeoGebraApplet());
//			//bshInstance.set("jEdit",org.gjt.sp.jedit.jEdit);
//			//bshInstance.set("returnError","none");
//      }
//      return bshInstance;
//   }//end method.
   
   

   
   
   public java.util.zip.ZipFile getScriptsZip()
   {
	   return scriptsZip;
   }//end method.
 
    /** Searches for the file piper.jar and passes its absolute path to the Piper interpreter.
     * This method searches in the classpath (declared i.e. in MANIFEST.MF) for the file piperinit.pi.
     * piperinit.pi is inside piper.jar.
     * Returns true if successful.*/
    private boolean loadScripts() {
        java.net.URL detectURL = java.lang.ClassLoader.getSystemResource("piperinit.pi");
        // if piperinit.pi not found:
        if (detectURL == null) return false;
 
        String detect = detectURL.getPath(); // file:/home/av/src/lib/piper.yar!/piperinit.pi
        String archive = detect.substring(0, detect.lastIndexOf('!')); // file:/home/av/src/lib/piper.jar
 
        try {
            scriptsZip = new java.util.zip.ZipFile(new java.io.File(new java.net.URI(archive)));
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
 
        // Pass the absolute path of piper.jar to Piper.
        LispStandard.zipFile = scriptsZip;
 
        return true;
    }
 
    /** Use this method to pass an expression to the Piper interpreter.
     *  Returns the output of the interpreter.
     */
    public String evaluate(String input) throws org.mathrider.piper.Piperexception {
		
			String result = piper.Evaluate(input);
			
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

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=1:
