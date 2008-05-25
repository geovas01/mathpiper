package org.mathrider.piper;


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
public class PiperInterpreter {
 
    private CPiper piper;
 
    /** Creates a new instance of PiperInterpreter */
    public PiperInterpreter() throws Piperexception {
        piper = new CPiper(new StringOutput(new StringBuffer()));
        boolean scripts_found = loadScripts();
 
        if (!scripts_found) System.err.println("Piper error: Unable to load piper.jar");
        piper.Evaluate("Load(\"piperinit.pi\");");
 
    }
 
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
 
        java.util.zip.ZipFile z;
        try {
            z = new java.util.zip.ZipFile(new java.io.File(new java.net.URI(archive)));
        } catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
 
        // Pass the absolute path of piper.jar to Piper.
        LispStandard.zipFile = z;
 
        return true;
    }
 
    /** Use this method to pass an expression to the Piper interpreter.
     *  Returns the output of the interpreter.
     */
    public String Evaluate(String input) throws Piperexception {
        return piper.Evaluate(input);
    }
}
