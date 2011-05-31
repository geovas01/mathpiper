/**************************************************************************
 * Copyright (C) 2011 Ted Kosan                                           *
 *                                                                        *
 * Redistribution and use in source and binary forms, with or without     *
 * modification, are permitted provided that the following conditions are *
 * met:                                                                   *
 *                                                                        *
 *     * Redistributions of source code must retain the relevant          *
 *       copyright notice, this list of conditions and the following      *
 *       disclaimer.                                                      *
 *     * Redistributions in binary form must reproduce the above          *
 *       copyright notice, this list of conditions and the following      *
 *       disclaimer in the documentation and/or other materials provided  *
 *       with the distribution.                                           *
 *                                                                        *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS    *
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT      *
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS      *
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE         *
 * COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,   *
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,   *
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS  *
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND *
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR  *
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF     *
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH   *
 * DAMAGE.                                                                *
 *************************************************************************/
package org.mathpiper.mpreduce;

import com.google.gwt.core.client.EntryPoint;


import org.mathpiper.mpreduce.io.streams.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.mathpiper.mpreduce.io.streams.LispOutputString;
import org.mathpiper.mpreduce.io.streams.LispStream;

public class Interpreter implements EntryPoint {

    Jlisp jlisp;
    private static Interpreter JlispCASInstance = null;
    private String startMessage;
    private String prompt;
    private String sendString = null;
    InputStream in;
    //Lisp out, my in.
    LispStream out;


    public Interpreter() {

        JlispCASInstance = this;

    }//end constructor.


    public String initialize() {

        String result = "";

        jlisp = new Jlisp();

        try {

            in = new InterpreterInputStream(this);

            out = new LispOutputString();

            final String[] args = new String[0];

            jlisp.startup(args, in, out);

            jlisp.initialize();

            result = evaluate("off int; on errcont;");


        } catch (Throwable t) {
            t.printStackTrace();

            result = t.getMessage();

        }
        finally
        {
            return result;
        }
    }





    public String getStartMessage() {
        return startMessage;
    }//end method.



    public String evaluate(String send) {

        send = send.trim();

        if (send.equals("")) {
            return ("No Input Sumbitted.");
        }

        while (send.endsWith(";;")) {
            send = send.substring(0, send.length() - 1);
        }

        if (!send.endsWith(";") && !send.endsWith("$")) {
            send = send + ";";
        }

        send = send + "end;";


        try {

            sendString = send;

            in.close();

            jlisp.evaluate();

        } catch (Throwable t) {
            out.println();
            out.println(t.getMessage());
        } finally {
            String result;

            out.flush();

            result = out.toString();

            out.close();

            return result;
        }

    }//end evaluate.


    public void interruptEvaluation() {
        try {

            jlisp.interruptEvaluation = true;

        } catch (Throwable e) {
            //Each excpetion.
        }
    }

    //Lisp in, my out.
    class InterpreterInputStream extends InputStream {

        private ArrayList expressions = new ArrayList();
        private Iterator expressionsIterator;
        private Interpreter interpreter;
        public int pos, len;
        private String result;


        InterpreterInputStream(Interpreter interpreter) {
            this.interpreter = interpreter;

            sendString = null;
        }


        public int available() {
            if (sendString != null) {
                return 1;
            } else {
                return 0;
            }
        }


        public void close() {
            pos = 0;
            len = sendString.length();
        }


        public boolean markSupported() {
            return false;
        }


        public int read() {

            if (pos == len) {
                sendString = null;
                return (int) -1;
            } else {
                int i = (int) sendString.charAt(pos++);
                return i;
            }
        }

    }//end method.


    private String test() {
        String result = "";

        try {

            //result = evaluate("2+2");

            result = evaluate("(X-Y)^100");

        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }

        return result;



    }//end method.



    public static String casVersion()
    {
        return "MPReduceJS version " + JlispCASInstance.version();
    }

    public static native void exportCasVersionMethod() /*-{
       $wnd.casVersion = function(){
         return @org.mathpiper.mpreduce.Interpreter::casVersion()();
       }
    }-*/;



    public static String casEvaluate(String send)
    {
        return JlispCASInstance.evaluate(send);
    }

    public static native void exportEvaluateMethod() /*-{
       $wnd.casEval = function(send){
         return @org.mathpiper.mpreduce.Interpreter::casEvaluate(Ljava/lang/String;)(send);
       }
    }-*/;



    public static String casInitialize()
    {
        String result = JlispCASInstance.initialize();

        callCasLoaded();

        return result;
    }

    public static native void exportInitializeMethod() /*-{
       $wnd.casInitialize = function(){
         return @org.mathpiper.mpreduce.Interpreter::casInitialize()();
       }
    }-*/;


    public static native void callCasLoaded() /*-{
       $wnd.casLoaded();
    }-*/;


    @Override
    public void onModuleLoad() {

        exportCasVersionMethod();

        exportInitializeMethod();

        exportEvaluateMethod();
    }


    public static String version()
    {
        return Jlisp.version;
    }


    
    public static void main(String[] args) {

        Interpreter mpreduce = new Interpreter();
        String result = mpreduce.initialize();

        try {
            System.out.println(mpreduce.test());
        } catch (Throwable t) {
            t.printStackTrace();
        }





        /*String result = "";

        try {

        result = mpreduce.evaluate("off nat;");
        System.out.println(result + "\n");

        result = mpreduce.evaluate("x^2;");
        System.out.println(result + "\n");

        result = mpreduce.evaluate("(X-Y)^100;");
        System.out.println(result + "\n");


        result = mpreduce.evaluate("2 + 2;");
        System.out.println(result + "\n");


        result = mpreduce.evaluate("Factorize(100);");
        System.out.println(result + "\n");

        } catch (Throwable t) {
        t.printStackTrace();
        } finally {
        }*/




    }

}//end class.

