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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


import java.io.InputStream;
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

    }//end constructor.


    public void start()
    {


        jlisp = new Jlisp();

        try {

            in = new InterpreterInputStream(this);

            out = new LispOutputString();

            final String[] args = new String[0];

            jlisp.startup(args, in, out);


        } catch (Throwable t) {
            t.printStackTrace();

        }
    }


    public String getStartMessage() {
        return startMessage;
    }//end method.


    public static Interpreter getInstance() throws Throwable {
        if (JlispCASInstance == null) {
            JlispCASInstance = new Interpreter();
        }
        return JlispCASInstance;
    }//end method.


    public void evaluate(String send) throws Throwable {

        send = send.trim();

        if (((send.endsWith(";")) || (send.endsWith("$"))) != true) {
            send = send + ";\n";
        }

        while (send.endsWith(";;")) {
            send = send.substring(0, send.length() - 1);
        }

        while (send.endsWith("$")) {
            send = send.substring(0, send.length() - 1);
        }

        send = send + "\n";

        this.sendString = send;

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
        private int pos, len;
        private String result;


        InterpreterInputStream(Interpreter interpreter) {
            this.interpreter = interpreter;
            sendString = null;

            expressions.add("symbolic procedure update!_prompt; begin setpchar \"\" end;;");
            expressions.add(";");
            expressions.add("off int; on errcont; off nat;");
            expressions.add("off nat;");
            expressions.add("x^2;");
            expressions.add("(X-Y)^100;");
            expressions.add("2 + 2;");
            expressions.add("Factorize(100);");
            expressions.add("quit;");

            expressionsIterator = expressions.iterator();


        }


        public int available() {
            if (sendString != null) {
                return 1;
            } else {
                return 0;
            }
        }


        public void close() {
        }


        public boolean markSupported() {
            return false;
        }


        public int read() {
            if (sendString == null) {


                if (out.sb.length() != 0) {
                    result = out.toString();

                    out.flush(); //Clear the StringBuffer.

                    System.out.println(result + "\n");
                }

                try {

                    if (expressionsIterator.hasNext()) {
                        String expression = (String) expressionsIterator.next();
                        evaluate(expression);

                    } else {
                        throw new Exception("There was a problem during system shutdown.");
                    }
                } catch (Throwable t) {
                    System.out.println(t.getMessage());
                }


                pos = 0;
                len = sendString.length();
            }
            if (pos == len) {
                sendString = null;
                return (int) ' ';
            } else {
                return (int) sendString.charAt(pos++);
            }
        }


        public int read(char[] b) {
            if (b.length == 0) {
                return 0;
            }
            b[0] = (char) read();
            return 1;
        }


        public int read(char[] b, int off, int len) {
            if (b.length == 0 || len == 0) {
                return 0;
            }
            b[off] = (char) read();
            return 1;
        }

    }


    @Override
    public void onModuleLoad() {


        Label label = new Label("mpreduce test");
        Button button = new Button("Start mpreduce");
        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                start();
                Window.alert("mpreduce started");
            }

        });

        RootPanel.get().add(label);
        RootPanel.get().add(button);
    }


    public static void main(String[] args) {
        Interpreter mpreduce = new Interpreter();

        mpreduce.start();

        try{
            mpreduce.jlisp.readEvalPrintLoop(true);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
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

