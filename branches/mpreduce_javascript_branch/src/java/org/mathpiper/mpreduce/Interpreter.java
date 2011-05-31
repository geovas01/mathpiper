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
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import java.io.IOException;


import org.mathpiper.mpreduce.io.streams.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.mathpiper.mpreduce.datatypes.Cons;
import org.mathpiper.mpreduce.datatypes.LispString;
import org.mathpiper.mpreduce.functions.functionwithenvironment.Bytecode;
import org.mathpiper.mpreduce.functions.lisp.LispFunction;
import org.mathpiper.mpreduce.io.streams.LispOutputString;
import org.mathpiper.mpreduce.io.streams.LispStream;
import org.mathpiper.mpreduce.numbers.LispSmallInteger;
import org.mathpiper.mpreduce.packagedatastore.PDS;
import org.mathpiper.mpreduce.packagedatastore.PDSInputStream;
import org.mathpiper.mpreduce.special.SpecialFunction;
import org.mathpiper.mpreduce.symbols.Symbol;
import org.mathpiper.mpreduce.zip.GZIPInputStream;

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

        } finally {
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


    public static String casVersion() {
        return "MPReduceJS version " + JlispCASInstance.version();
    }


    public static native void exportCasVersionMethod() /*-{
    $wnd.casVersion = function(){
    return @org.mathpiper.mpreduce.Interpreter::casVersion()();
    }
    }-*/;


    public static String casEvaluate(String send) {
        return JlispCASInstance.evaluate(send);
    }


    public static native void exportEvaluateMethod() /*-{
    $wnd.casEval = function(send){
    return @org.mathpiper.mpreduce.Interpreter::casEvaluate(Ljava/lang/String;)(send);
    }
    }-*/;


    public static String casInitialize() {
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


    public static String version() {
        return Jlisp.version;
    }


    private void loadImageSetup() throws Exception {

        LispSmallInteger.preAllocate();  // some small integers treated specially

        // For use while I am re-loading images and also to assist the
        // custom Lisp bytecoded stuff I build a table of all the functions
        // that I have built into this Lisp.
        //
        Jlisp.builtinFunctions = new HashMap();
        Jlisp.builtinSpecials = new HashMap();
        for (int i = 0; i < Jlisp.fns1.builtins.length; i++) {
            ((LispFunction) Jlisp.fns1.builtins[i][1]).name =
                    (String) Jlisp.fns1.builtins[i][0];
            Jlisp.builtinFunctions.put(Jlisp.fns1.builtins[i][0], Jlisp.fns1.builtins[i][1]);
        }
        for (int i = 0; i < Jlisp.fns2.builtins.length; i++) {
            ((LispFunction) Jlisp.fns2.builtins[i][1]).name =
                    (String) Jlisp.fns2.builtins[i][0];
            Jlisp.builtinFunctions.put(Jlisp.fns2.builtins[i][0], Jlisp.fns2.builtins[i][1]);
        }
        for (int i = 0; i < Jlisp.fns3.builtins.length; i++) {
            ((LispFunction) Jlisp.fns3.builtins[i][1]).name =
                    (String) Jlisp.fns3.builtins[i][0];
            Jlisp.builtinFunctions.put(Jlisp.fns3.builtins[i][0], Jlisp.fns3.builtins[i][1]);
        }
        for (int i = 0; i < Jlisp.mpreduceFunctions.builtins.length; i++) {
            ((LispFunction) Jlisp.mpreduceFunctions.builtins[i][1]).name =
                    (String) Jlisp.mpreduceFunctions.builtins[i][0];
            Jlisp.builtinFunctions.put(Jlisp.mpreduceFunctions.builtins[i][0], Jlisp.mpreduceFunctions.builtins[i][1]);
        }
        /*for (i=0; i<fns4.builtins.length; i++)
        {   ((LispFunction)fns4.builtins[i][1]).name =
        (String)fns4.builtins[i][0];
        builtinFunctions.put(fns4.builtins[i][0], fns4.builtins[i][1]);
        }*/
        for (int i = 0; i < Jlisp.specfn.specials.length; i++) {
            ((SpecialFunction) Jlisp.specfn.specials[i][1]).name =
                    (String) Jlisp.specfn.specials[i][0];
            Jlisp.builtinSpecials.put(Jlisp.specfn.specials[i][0], Jlisp.specfn.specials[i][1]);
        }
        Bytecode.setupBuiltins();

        // I may need to display diagnostics before I have finshed setting up
        // streams etc in their proper final form, so I arrange a provisional
        // setting that directs early messages to the terminal.
        //lispIO = lispErr = new LispOutputStream();

        Jlisp.lit[Lit.std_output] = Jlisp.lit[Lit.tr_output] =
                Jlisp.lit[Lit.err_output] = Jlisp.lit[Lit.std_input] =
                Jlisp.lit[Lit.terminal_io] = Jlisp.lit[Lit.debug_io] =
                Jlisp.lit[Lit.query_io] = Symbol.intern("temp-stream");

        Jlisp.standardStreams();

        Jlisp.images = null;
        try {

            //InputStream is = new FileInputStream("minireduce.img");

            InputStream is = new ReduceImageInputStream();

            if (is != null) {
                Jlisp.images = new PDS(is);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // The next stage is either to create an initial Lisp heap or to
        // re-load one that had been saved from a previous session. Things are
        // made MUCH more complicated here because a running Lisp can (under program
        // control) get itself restarted either in cold or warm-start mode.



        PDSInputStream ii = null;
        // I will re-load from the first checkpoint file in the list that has
        // a HeapImage stored in it.

        try {
            ii = new PDSInputStream(Jlisp.images, "HeapImage");
        } catch (IOException e) {
        }



        try {
            if (ii == null) {
                throw new IOException("No valid checkpoint file found");
            }


            gzip = new GZIPInputStream(ii);



            Symbol.symbolCount = Cons.consCount = LispString.stringCount = 0;

            LispReader.getInstance().incrementalRestore(gzip);


        } catch (Exception e) {
            throw e;
        }

    }

    GZIPInputStream gzip = null;


    public static void main(String[] args) {

        Interpreter mpreduce = new Interpreter();

        try {
            mpreduce.loadImageSetup();

            LispReader lispReader = LispReader.getInstance();


            while (lispReader.execute() == true) {
            }


            LispReader.getInstance().afterIncrementalRestore();

            String result = mpreduce.initialize();

            System.out.println(mpreduce.test());

            if (mpreduce.gzip != null) {

                mpreduce.gzip.close();

            }
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

