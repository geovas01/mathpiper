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
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;


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


    public void start() {

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


    public void initialize() {
        try {
            jlisp.initialize();

            //String result = evaluate("off int; on errcont;");

            //System.out.println(result);
        } catch (Throwable t) {
            t.printStackTrace();

        }

    }


    public String getStartMessage() {
        return startMessage;
    }//end method.


    public static Interpreter getInstance() {
        if (JlispCASInstance == null) {
            JlispCASInstance = new Interpreter();
        }
        return JlispCASInstance;
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


    //For use by JavaScript code.
    //public static String eval(String send) {
    //    return JlispCASInstance.evaluate(send);
    //}
    //public static native void exportStaticMethod() /*-{
    //   $wnd.mpreduceEval =
    //     $entry(@org.mathpiper.mpreduce.Interpreter::eval(Ljava/lang/String;)(send));
    //}-*/;


    
    @Override
    public void onModuleLoad() {


        final TextBox inputTextBox = new TextBox();
        inputTextBox.setVisibleLength(20);

        final TextArea outputTextArea = new TextArea();
        outputTextArea.setVisibleLines(40);
        outputTextArea.setCharacterWidth(100);


        inputTextBox.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == '\n') {
                    String result = evaluate(inputTextBox.getText());

                    outputTextArea.setText(result);
                }
            }

        });



        Button button = new Button("Evaluate");

        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String result = evaluate(inputTextBox.getText());

                outputTextArea.setText(result);
            }

        });



        Button testButton = new Button("Test mpreduce");
        testButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {

                Window.alert("Before start()");

                start();

                Window.alert("After start()");

                initialize();

                Window.alert("After initialize()");

                String result = evaluate("off int; on errcont;");

                Window.alert("After off_int.  " + result);

                Window.alert(test());

                Window.alert("After test()");
            }

        });


        RootPanel.get().add(inputTextBox);
        RootPanel.get().add(button);
        RootPanel.get().add(testButton);
        RootPanel.get().add(outputTextArea);
    }


    public static void main(String[] args) {

        Interpreter mpreduce = new Interpreter();

        try {
            mpreduce.start();

            mpreduce.initialize();

            String result = mpreduce.evaluate("off int; on errcont;");

            System.out.println(result);

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

