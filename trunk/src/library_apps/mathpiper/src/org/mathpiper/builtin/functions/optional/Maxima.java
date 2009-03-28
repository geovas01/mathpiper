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
package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.ConsPointer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import org.mathpiper.lisp.UtilityFunctions;

/**
 *
 *
 */
public class Maxima extends BuiltinFunction {

    private static Maxima maximaInstance = null;
    private StringBuffer responseBuffer;
    private Pattern inputPromptPattern;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String response;
    private String startMessage;
    private String fileSearchMaximaAppendResponse;
    private String fileSearchLispAppendResponse;
    private boolean keepRunning;
    private String prompt;
    private boolean maximaInstalled = false;

    /** Creates a new instance of MaximaWrapper */
    public Maxima() {



        ArrayList command = new ArrayList();
        //command.add("C:\\Program Files\\Maxima-5.15.0\\bin\\maxima.bat");

        String maximaPath = "/usr/bin/maxima";
        File maximaCommandFile = new File(maximaPath);
        if(maximaCommandFile.exists())
        {
        command.add(maximaPath);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process maximaProcess = processBuilder.start();
            inputStream = maximaProcess.getInputStream();
            outputStream = maximaProcess.getOutputStream();
            responseBuffer = new StringBuffer();
            inputPromptPattern = Pattern.compile("\\n\\(%i[0-9]+\\)|MAXIMA>");
            startMessage = getResponse();

            send("display2d:false;\n");
            getResponse();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        maximaInstalled = true;
        System.out.println("Maxima is initialized");
        }
        else
        {
            System.out.println("Maxima is not installed");
        }




    /*//Add temporary files directory to maxima search path.
    File tempFile = File.createTempFile("mathrider", ".tmp");
    tempFile.deleteOnExit();
    String searchDirectory = tempFile.getParent() + File.separator + "###.{mac,mc}";
    searchDirectory = searchDirectory.replace("\\","/");
    send("file_search_maxima: append (file_search_maxima, [\"" + searchDirectory + "\"])$\n");
    fileSearchMaximaAppendResponse = getResponse();


    //Add temporary files directory to lisp search path.
    searchDirectory = tempFile.getParent() + File.separator + "###.{lisp,lsp}";
    searchDirectory = searchDirectory.replace("\\","/");
    send("file_search_lisp: append (file_search_lisp, [\"" + searchDirectory + "\"])$\n");
    fileSearchLispAppendResponse = getResponse();
    //System.out.println("FFF " + fileSearchMaximaAppendResponse);*/

    }//end constructor.

    public String getStartMessage() {
        return startMessage;
    }//end method.

    public String getPrompt() {
        return prompt;
    }//end method.

    public static Maxima getInstance() throws Throwable {
        if (maximaInstance == null) {
            maximaInstance = new Maxima();
        }
        return maximaInstance;
    }//end method.

    public synchronized void send(String send) throws Throwable {
        outputStream.write(send.getBytes());
        outputStream.flush();
    }//end send.

    protected String getResponse() throws Throwable {
        boolean keepChecking = true;

        mainLoop:
        while (keepChecking) {
            int serialAvailable = inputStream.available();
            if (serialAvailable == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    System.out.println("Maxima session interrupted.");
                }
                continue mainLoop;
            }//end while

            byte[] bytes = new byte[serialAvailable];

            inputStream.read(bytes, 0, serialAvailable);
            responseBuffer.append(new String(bytes));
            response = responseBuffer.toString();
            //System.out.println("SSSSS " + response);
            Matcher matcher = inputPromptPattern.matcher(response);
            if (matcher.find()) {
                //System.out.println("PPPPPP found end");
                responseBuffer.delete(0, responseBuffer.length());
                int promptIndex = response.lastIndexOf("(%");
                if (promptIndex == -1) {
                    promptIndex = response.lastIndexOf("MAX");
                }
                prompt = response.substring(promptIndex, response.length());
                response = response.substring(0, promptIndex);
                keepChecking = false;

            }//end if.

        }//end while.

        return response;

    }//end method

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
        ConsPointer expressionPointerr = new ConsPointer();
        expressionPointerr.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());

        // Get operator
        LispError.checkArgument(aEnvironment, aStackTop, expressionPointerr.getCons() != null, 1);
        String orig = expressionPointerr.getCons().string();
        LispError.checkArgument(aEnvironment, aStackTop, orig != null, 1);

        if(maximaInstalled)
        {
        orig = orig.substring(1,orig.length()-1); //Strip quotes.

        try {
            send(orig + ";\n");
            String response = getResponse();

            getResult(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, response));
        } catch (Throwable t) {
            t.printStackTrace();
            UtilityFunctions.internalFalse(aEnvironment, getResult(aEnvironment, aStackTop));
        }
        }
        else
        {
            aEnvironment.write("Maxima is not installed.");
            UtilityFunctions.internalFalse(aEnvironment, getResult(aEnvironment, aStackTop));
        }


    }
}//end class.


