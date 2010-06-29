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
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Utility;

/**
 *
 *
 */
public class PanAxiom extends BuiltinFunction {

    private static PanAxiom FriCASInstance = null;
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
    private boolean fricasInstalled = false;

    /** Creates a new instance of MaximaWrapper */
    public PanAxiom() {


        /*
        ArrayList command = new ArrayList();
        //command.add("C:\\Program Files\\Maxima-5.15.0\\bin\\maxima.bat");

        String fricasPath = "/home/tkosan/checkouts/usr/local/bin/fricas";
        File fricasCommandFile = new File(fricasPath);
        if(fricasCommandFile.exists())
        {
            command.add(fricasPath);
            command.add("-nox");
            command.add("-noclef");


        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process fricasProcess = processBuilder.start();
            inputStream = fricasProcess.getInputStream();
            outputStream = fricasProcess.getOutputStream();
            responseBuffer = new StringBuffer();
            inputPromptPattern = Pattern.compile("\\n\\([0-9]+\\) \\->");
            startMessage = getResponse();

            send("2+2\n");
            getResponse();

            fricasInstalled = true;
        } catch (Throwable t) {
            t.printStackTrace();

        }



        //System.out.println("M+");
        }
        else
        {
            //System.out.println("M-");
        }




    */

    }//end constructor.

    public String getStartMessage() {
        return startMessage;
    }//end method.

    public String getPrompt() {
        return prompt;
    }//end method.

    public static PanAxiom getInstance() throws Throwable {
        if (FriCASInstance == null) {
            FriCASInstance = new PanAxiom();
        }
        return FriCASInstance;
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
                    System.out.println("FriCAS session interrupted.");
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

                /*
                int promptIndex = response.lastIndexOf("(%");
                if (promptIndex == -1) {
                    promptIndex = response.lastIndexOf("MAX");
                }
                prompt = response.substring(promptIndex, response.length());
                response = response.substring(0, promptIndex);
                */

                keepChecking = false;

            }//end if.

        }//end while.

        return response;

    }//end method

    public void plugIn(Environment aEnvironment) throws Exception
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Maxima");

    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
        ConsPointer expressionPointerr = new ConsPointer();
        expressionPointerr.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());

        // Get operator
        LispError.checkArgument(aEnvironment, aStackTop, expressionPointerr.getCons() != null, 1, "Maxima");
        String orig = (String) expressionPointerr.car();
        LispError.checkArgument(aEnvironment, aStackTop, orig != null, 1, "Maxima");

        if(fricasInstalled)
        {
        orig = orig.substring(1,orig.length()-1); //Strip quotes.

        try {
            send(orig + ";\n");
            String response = getResponse();

            if(response.startsWith("\n"))
            {
                response = response.substring(1);
            }

            getTopOfStackPointer(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, aStackTop, response));
        } catch (Throwable t) {
            t.printStackTrace();
            Utility.putFalseInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
        }
        }
        else
        {
            aEnvironment.write("FriCAS is not installed.");
            Utility.putFalseInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
        }


    }
}//end class.


