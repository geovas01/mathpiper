package org.mathpiper.mpreduce;

// This file is part of the Jlisp implementation of Standard Lisp
// Copyright \u00a9 (C) Codemist Ltd, 1998-2011.
//
/**************************************************************************
 * Copyright (C) 1998-2011, Codemist Ltd.                A C Norman       *
 *                            also contributions from Vijay Chauhan, 2002 *
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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;
import org.mathpiper.mpreduce.symbols.Symbol;
import org.mathpiper.mpreduce.special.SpecialFunction;
import org.mathpiper.mpreduce.datatypes.Cons;
import org.mathpiper.mpreduce.functions.functionwithenvironment.Bytecode;
import org.mathpiper.mpreduce.exceptions.ProgEvent;
import org.mathpiper.mpreduce.datatypes.LispString;
import org.mathpiper.mpreduce.exceptions.EOFException;
import org.mathpiper.mpreduce.functions.lisp.LispFunction;
import org.mathpiper.mpreduce.numbers.LispSmallInteger;
import org.mathpiper.mpreduce.exceptions.LispException;
import org.mathpiper.mpreduce.io.streams.LispStream;
import org.mathpiper.mpreduce.io.streams.LispOutputStream;
import org.mathpiper.mpreduce.packagedatastore.PDS;
import org.mathpiper.mpreduce.packagedatastore.PDSInputStream;
import org.mathpiper.mpreduce.functions.builtin.Fns;
//import org.mathpiper.mpreduce.javacompiler.Fns4;


import org.mathpiper.mpreduce.exceptions.ResourceException;
import org.mathpiper.mpreduce.io.streams.WriterToLisp;
import org.mathpiper.mpreduce.special.Specfn;
import org.mathpiper.mpreduce.zip.GZIPInputStream;

public class Jlisp extends Environment {

    private static String version = ".013";
    // Within this file I will often reference lispIO and lispErr
    // directly. Elsewhere they should ONLY be accessed via the Lisp
    // variables that point towards them. The direct access here is in
    // cases where the Lisp world may not have been fully set up.
    public static LispStream lispIO, lispErr;
    public static boolean interactivep = false;

    public static boolean debugFlag = false;

    public static boolean headline = true;

    public static boolean backtrace = true;

    public static LispObject errorCode;
    public static int verbosFlag = 1;
    public static boolean trapExceptions = true;
    //private static LispPrintStream transcript = null;
    public static boolean interruptEvaluation = false;


    public static void print(String s) throws ResourceException {
        ((LispStream) (lit[Lit.std_output].car/*value*/)).print(s);
    }


    public static void println(String s) throws ResourceException {
        ((LispStream) (lit[Lit.std_output].car/*value*/)).println(s);
    }


    public static void print(LispObject s) throws ResourceException {
        if (s == null) {
            print("<null>");
        } else {
            s.print();
        }
    }


    public static void println(LispObject s) throws ResourceException {
        if (s == null) {
            print("<null>");
        } else {
            s.print();
        }
        ((LispStream) (lit[Lit.std_output].car/*value*/)).println();
    }


    public static void println() throws ResourceException {
        ((LispStream) (lit[Lit.std_output].car/*value*/)).println();
    }


    public static void errprint(String s) throws ResourceException {
        ((LispStream) (lit[Lit.err_output].car/*value*/)).print(s);
    }


    public static void errprintln(String s) throws ResourceException {
        ((LispStream) (lit[Lit.err_output].car/*value*/)).println(s);
    }


    public static void errprintln() throws ResourceException {
        ((LispStream) (lit[Lit.err_output].car/*value*/)).println();
    }


    public static void traceprint(String s) throws ResourceException {
        ((LispStream) (lit[Lit.tr_output].car/*value*/)).print(s);
    }


    public static void traceprintln(String s) throws ResourceException {
        ((LispStream) (lit[Lit.tr_output].car/*value*/)).println(s);
    }


    public static void traceprintln() throws ResourceException {
        ((LispStream) (lit[Lit.tr_output].car/*value*/)).println();
    }


    public static LispObject error(String s) throws LispException {
        if (headline) {
            errprintln();
            errprintln("++++ " + s);
        }
        ResourceException.errors_now++;
        if (ResourceException.errors_limit > 0
                && ResourceException.errors_now > ResourceException.errors_limit) {
            if (headline) {
                errprintln("++++ Error count resource exceeded");
            }
            throw new ResourceException("error count");
        }

        checkExit(s);

        throw new LispException(s);
    }


    public static LispObject error(String s, LispObject a) throws LispException {
        if (headline) {
            errprintln();
            errprint("++++ " + s + ": ");
            a.errPrint();
            errprintln();
        }

        ResourceException.errors_now++;
        if (ResourceException.errors_limit > 0
                && ResourceException.errors_now > ResourceException.errors_limit) {
            if (headline) {
                errprintln("++++ Error count resource exceeded");
            }
            throw new ResourceException("error count");
        }



        checkExit(s);



        throw new LispException(s);
    }

    // The main parts of this file relate to system startup options
    public static PDS images;
    static String imageFile;
    static InputStream in;
    public static LispStream out;
    public static boolean standAlone;
    public static Vector openOutputFiles = null;
    public static boolean restarting = false;
    static String restartModule = null;
    static String restartFn = null;
    static String restartArg = null;
    static boolean finishingUp = false;


    public static void startup(String[] args, InputStream Xin, LispStream Xout) {

        in = Xin;
        out = Xout;
        lispIO = null;

        // I am pretty keen that all output files should be closed (and in the
        // process they should be flushed) so that data is never lost. So I keep
        // a record (in this Vector) of ones that are liable to need closing, and
        // then in a "finally" clause I zoom through cleaning up.
        openOutputFiles = new Vector(10, 5);
        try {
            startup1(args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lispIO = null;
            finishingUp = true;

            int i;
            // In general I close in the opposite order from that in which I opened files.
            // The code here is such that if closing one file happened to have a side
            // effect of closing another along the way that would not be a calamity.
            while ((i = openOutputFiles.size()) != 0) {
                ((LispStream) openOutputFiles.get(i - 1)).close();
            }
        }
        // If I was run as an application not an applet (via any route!) I am
        // permitted to exit.
        // if (!CWin.isApplet) System.exit(0);
    }


    static void startup1(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        String[] inputFile = new String[10];
        int inputCount = 0;


        boolean coldStart = false;

        boolean verbose = false;
        boolean copyrightRequest = false;
        String[] errs = new String[10];
        int errCount = 0;
        String[] defineSymbol = new String[10];
        int defineCount = 0;
        String[] undefineSymbol = new String[10];
        int undefineCount = 0;
        boolean noRestart = false;
        boolean batchSwitch = false;
        // I may need to display diagnostics before I have finshed setting up
        // streams etc in their proper final form, so I arrange a provisional
        // setting that directs early messages to the terminal.
        lispIO = lispErr = new LispOutputStream();
        lit[Lit.std_output] = lit[Lit.tr_output] =
                lit[Lit.err_output] = lit[Lit.std_input] =
                lit[Lit.terminal_io] = lit[Lit.debug_io] =
                lit[Lit.query_io] = Symbol.intern("temp-stream");
        standardStreams();

        // The options that I accept here are intended to match (as far as I can
        // reasonably make them) the ones used with the "CSL" Lisp implementation.
        // I scan the command line to decode them. Note that until this has
        // been completed I can not do proper Lisp output because I will not have
        // seen redirection requests.
        int i;
        for (i = 0; i < args.length; i++) {
            String arg = args[i];
            String arg1;
            if (arg.length() >= 2 && arg.charAt(0) == '-') {
                char key = Character.toLowerCase(arg.charAt(1));
                switch (key) {
                    case '-': // redirect all output
                        break;
                    case 'b': // flips batchp() result
                        batchSwitch = true;
                        continue;
                    case 'g': // enhance debugging
                        debugFlag = true;
                        continue;
                    case 'i': // specify (input) image or library
                        break;
                    case 'n': // ignore restart function in image
                        noRestart = true;
                        continue;
                    case 'q': // quiet mode
                        verbose = false;
                        continue;
                    case 'v': // verbose mode
                        verbose = true;
                        continue;
                    case 'x': // less trapping of possibly internal errors
                        System.out.println("JVM exit on error set.");
                        trapExceptions = false;
                        continue;
                    case 'z': // cold start mode
                        coldStart = true;
                        continue;
                    default:
                        if (errCount < errs.length) {
                            errs[errCount++] =
                                    "Invalid option \"" + arg + "\"";
                        }
                        continue;
                }
                // In many cases an option takes an argument. I permit either -Ixx or -I xx
                // and separate off xxx here.
                if (arg.length() > 2) {
                    arg1 = arg.substring(2);
                } else if (i + 1 < args.length) {
                    arg1 = args[++i];
                } else {
                    if (errCount < errs.length) {
                        errs[errCount++] =
                                "Option \"" + arg
                                + "\" invalid as final option";
                    }
                    continue;
                }
                // Now arg is the initial key and arg1 is the follow-up.
                switch (key) {

                    case 'i': // specify (input) image or library
                        imageFile = arg1;

                        break;

                }
            } else {
                inputFile[inputCount++] = arg;
            }
        }
        // Now I have finished decoding the command line. The first parts I
        // process are those relating to the intended destination for
        // output.


        lispIO = new LispOutputStream();



        lispErr = lispIO; // lispErr sent to spool file if lispIO is...
        // now I have Java variables that refer to the output streams I need
        // to establish.

        // Now I am in a position to display any errors relating to
        // command line options.
        for (i = 0; i < errCount; i++) {
            lispErr.println(errs[i]);
        }

        LispSmallInteger.preAllocate();  // some small integers treated specially

        // For use while I am re-loading images and also to assist the
        // custom Lisp bytecoded stuff I build a table of all the functions
        // that I have built into this Lisp.
        //
        builtinFunctions = new HashMap();
        builtinSpecials = new HashMap();
        for (i = 0; i < fns1.builtins.length; i++) {
            ((LispFunction) fns1.builtins[i][1]).name =
                    (String) fns1.builtins[i][0];
            builtinFunctions.put(fns1.builtins[i][0], fns1.builtins[i][1]);
        }
        for (i = 0; i < fns2.builtins.length; i++) {
            ((LispFunction) fns2.builtins[i][1]).name =
                    (String) fns2.builtins[i][0];
            builtinFunctions.put(fns2.builtins[i][0], fns2.builtins[i][1]);
        }
        for (i = 0; i < fns3.builtins.length; i++) {
            ((LispFunction) fns3.builtins[i][1]).name =
                    (String) fns3.builtins[i][0];
            builtinFunctions.put(fns3.builtins[i][0], fns3.builtins[i][1]);
        }
        for (i = 0; i < mpreduceFunctions.builtins.length; i++) {
            ((LispFunction) mpreduceFunctions.builtins[i][1]).name =
                    (String) mpreduceFunctions.builtins[i][0];
            builtinFunctions.put(mpreduceFunctions.builtins[i][0], mpreduceFunctions.builtins[i][1]);
        }
        /*for (i=0; i<fns4.builtins.length; i++)
        {   ((LispFunction)fns4.builtins[i][1]).name =
        (String)fns4.builtins[i][0];
        builtinFunctions.put(fns4.builtins[i][0], fns4.builtins[i][1]);
        }*/
        for (i = 0; i < specfn.specials.length; i++) {
            ((SpecialFunction) specfn.specials[i][1]).name =
                    (String) specfn.specials[i][0];
            builtinSpecials.put(specfn.specials[i][0], specfn.specials[i][1]);
        }
        Bytecode.setupBuiltins();

        imageFile = "-";

        images = null;
        try {

            //InputStream is = new FileInputStream("minireduce.img");

            InputStream is = new ReduceImageInputStream();

            if (is != null) {
                images = new PDS(is);
            }

        } catch (IOException e) {
        }


        // The next stage is either to create an initial Lisp heap or to
        // re-load one that had been saved from a previous session. Things are
        // made MUCH more complicated here because a running Lisp can (under program
        // control) get itself restarted either in cold or warm-start mode.

        boolean loaded;

        loaded = false;


        if (!coldStart) {

            PDSInputStream ii = null;
            // I will re-load from the first checkpoint file in the list that has
            // a HeapImage stored in it.

            try {
                ii = new PDSInputStream(images, "HeapImage");
            } catch (IOException e) {
            }

            GZIPInputStream gzip = null;

            try {
                if (ii == null) {
                    throw new IOException("No valid checkpoint file found");
                }


                gzip = new GZIPInputStream(ii);



                Symbol.symbolCount = Cons.consCount = LispString.stringCount = 0;

                LispReader.restore(gzip);

                loaded = true;
            } catch (Exception e) {
                throw e;
            } finally {
                if (gzip != null) {
                    try {
                        gzip.close();
                    } catch (IOException e) {
                        lispErr.println("Failed to load image");
                        loaded = false;
                    }
                }
            }
            if (restarting && !loaded) {
                lispIO.println("+++ No image file when restarting");
                return;
            }
        }



        lispIO.tidyup(nil);
        lispErr.tidyup(nil);

        // Having set up an image I optionally display a banner.
        if (verbose) {
            lispIO.println("Jlisp 0.93a ... "
                    + ((LispString) lit[Lit.birthday]).string);
            if (loaded) {
                lispIO.println("Sym    = " + Symbol.symbolCount);
                lispIO.println("Cons   = " + Cons.consCount);
                lispIO.println("String = " + LispString.stringCount);
            }
            if (copyrightRequest) {
                lispIO.println("Copyright \u00a9 (C) Codemist Ltd, 1998-2000");
            }
        }

        // If the user specifed -Dxxx, -Dxxx=yyy or -Uxxx on the command
        // line I process that here. I will perform all the "undefine"
        // operations before any of the "define" ones, but otherwise
        // proceed left to right
        for (i = 0; i < undefineCount; i++) {
            Symbol s = Symbol.intern(undefineSymbol[i]);
            s.car/*value*/ = lit[Lit.undefined];
            s = null;
        }
        for (i = 0; i < defineCount; i++) {
            String name = defineSymbol[i];
            LispObject value;
            int eqPos = name.indexOf('=');
            // Just -Dname without an "=" sets the name to T
            if (eqPos == -1) {
                value = lispTrue;
            } else {
                String v = name.substring(eqPos + 1);
                name = name.substring(0, eqPos);
                int lv = v.length();
                // If the value specified was enclosed in double quotes I strip those
                // off. Thus -Dname=xxx and -Dname="xxx" both set name to a string "xxx".
                // Note that -Dname= will set name to the empty string "" which is non-nil
                // so is OK for "true".
                if (lv != 0
                        && v.charAt(0) == '\"'
                        && v.charAt(lv - 1) == '\"') {
                    v = v.substring(1, lv - 1);
                }
                value = new LispString(v);
            }
            Symbol s = Symbol.intern(name);
            s.car/*value*/ = value;
            s = null;
            value = null;
        }

        for (i = 0; i < 128; i++) // To speed up readch()
        {
            LispReader.chars[i] = Symbol.intern(String.valueOf((char) i));
        }

        // If no input files had been specified I will read from the standard
        // input - often the keyboard. Otherwise I will process each file that
        // is given. This seems a bulky bit of code because of Java's
        // insistence on exception processing. I do not work too hard on that!
        if (inputCount == 0) {
            interactivep = !batchSwitch;
            if (!restarting) {
                lispIO.setReader("<stdin>", in, standAlone, true);
            }
            standardStreams();


        }




        lispIO.close();


    }


    static void standardStreams() {
        lit[Lit.std_output].car/*value*/ = lispIO;
        lit[Lit.tr_output].car/*value*/ = lispIO;
        lit[Lit.err_output].car/*value*/ = lispErr;
        lit[Lit.std_input].car/*value*/ = lispIO;
        lit[Lit.terminal_io].car/*value*/ = lispIO;
        lit[Lit.debug_io].car/*value*/ = lispIO;
        lit[Lit.query_io].car/*value*/ = lispIO;
    }

    //public static OutputStream odump;
    public static InputStream idump;
    public static HashMap builtinFunctions, builtinSpecials;


    //-------------------------
    // set up fixed definitions
    static void initfns(Object[][] builtins) {
        for (int i = 0; i < builtins.length; i++) {
            Object[] s = builtins[i];
            String name = (String) s[0];
            LispFunction fn = (LispFunction) s[1];
            fn.name = name;
            Symbol.intern(name, fn, null);
        }

    }


    public static void readEvalPrintLoop(boolean noRestart) throws ProgEvent, ResourceException {

        LispObject r = lit[Lit.restart];

        println("MPReduceJS version " + Jlisp.version);

        try {

            if (r instanceof Symbol) {
                ((Symbol) r).fn.op0(); //Call Lisp "begin" function here.
            } else if (r instanceof LispFunction) {
                ((LispFunction) r).op0();
            } else {
                Fns.apply0(r);
            }

        } catch (Exception e) {
            if (trapExceptions == true) {
                if (e instanceof ProgEvent) {
                    throw ((ProgEvent) e);
                } else {

                    // ignore all other exceptions
                    System.err.println("Stopping because of error: "
                            + e.getMessage());
                }
            } else {
                checkExit(e.getMessage());
            }
        }//end try/catch.

        return;

    }//end method.


    public static void evaluate() throws ProgEvent, ResourceException {

        LispObject r = Symbol.intern("mpreduceeval");


        println("MPReduceJS version " + Jlisp.version);

        try {

            if (r instanceof Symbol) {
                ((Symbol) r).fn.op0(); //Call Lisp "begin" function here.
            } else if (r instanceof LispFunction) {
                ((LispFunction) r).op0();
            } else {
                Fns.apply0(r);
            }

        } catch (Exception e) {
            if (trapExceptions == true) {
                if (e instanceof ProgEvent) {
                    throw ((ProgEvent) e);
                } else {

                    // ignore all other exceptions
                    System.err.println("Stopping because of error: "
                            + e.getMessage());
                }
            } else {
                checkExit(e.getMessage());
            }
        }//end try/catch.

        return;

    }//end method.


    public static void simpleEvaluate() throws Exception {

        //LispObject resetParser = Symbol.intern("resetparser");
        LispObject xRead = Symbol.intern("expread");



        LispObject result = null;
        try {
            //result = ((Symbol) resetParser).fn.op0();
            result = ((Symbol) xRead).fn.op0();
        } catch (EOFException e) {
            //break;
        } catch (Exception e) {
            errprintln(
                    "Error while reading: " + e.getMessage());
            e.printStackTrace(new PrintWriter(new WriterToLisp(
                    ((LispStream) Jlisp.lit[Lit.err_output].car/*value*/))));
            //break;
        }
        try {

            LispObject reval = Symbol.intern("reval");
            LispObject v = ((Symbol) reval).fn.op1(result);

            //LispObject v = result.eval();
            if (Specfn.progEvent != Specfn.NONE) {
                Specfn.progEvent = Specfn.NONE;
                error("GO or RETURN out of context");
            }
            //println();
            //print("Value: ");
            v.print(LispObject.printEscape);

            if (!v.toString().equals("nil")) {
                LispObject rprint = Symbol.intern("mathprint");//mathprint prints 2d math.
                LispObject rp = ((Symbol) rprint).fn.op1(result);

                int xx = 1;
            }

            //println();
        } catch (Exception e) {
            if (e instanceof LispException) {
                if (e instanceof ProgEvent) {
                    ProgEvent ep = (ProgEvent) e;
                    switch (ep.type) {
                        case ProgEvent.STOP:
                        case ProgEvent.PRESERVE:
                        case ProgEvent.RESTART:
                            throw ep;
                        default:
                            break;
                    }
                }
                LispException e1 = (LispException) e;
                errprintln();
                errprint("+++++ Error: " + e1.getMessage());
                if (e1.details != null) {
                    errprint(": ");
                    e1.details.errPrint();
                }
                errprintln();
            } else {
                errprintln();
                errprintln("+++++ Error: "
                        + e.getMessage());
            }
            e.printStackTrace(new PrintWriter(new WriterToLisp(
                    ((LispStream) Jlisp.lit[Lit.err_output].car/*value*/))));
        }
    }//end method.


    public static void initialize() {
        try {
            LispObject reval = Symbol.intern("beginmpreduce");
            LispObject v = ((Symbol) reval).fn.op0();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*LispObject r = Symbol.intern("*mode");
        Symbol value = new Symbol();
        value.pname = "algebraic";
        r.car = value;

        r = Symbol.intern("cursym*");
        value = new Symbol();
        value.pname = "*semicol*";
        r.car = value;*/


    }


    private static void checkExit(String errorMessage) {
        if (trapExceptions == false) {
            System.out.println(errorMessage);

            try {

                if (lispIO != null) {
                    lispIO.flush();
                    lispIO.close();
                }

                if (lispErr != null) {
                    lispErr.flush();
                    lispErr.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            //System.exit(1);
            System.out.println("SYSTEM EXIT SHOULD BE HERE.");

        } else {
            try {

                if (lispIO != null) {
                    lispIO.flush();
                }

                if (lispErr != null) {
                    lispErr.flush();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }//end method.

} // End of Jlisp.java

