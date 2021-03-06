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
package org.mathpiper.interpreters;

import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.io.InputStatus;
import org.mathpiper.lisp.printers.MathPiperPrinter;
import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.io.StringOutputStream;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.lisp.parsers.Parser;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.lisp.printers.LispPrinter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.mathpiper.Scripts;
import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.io.StringOutput;
import org.mathpiper.lisp.Evaluator;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

/**
 *
 *
 */
class SynchronousInterpreter implements Interpreter {

    private ArrayList<ResponseListener> removeListeners;
    private ArrayList<ResponseListener> responseListeners;
    private Environment iEnvironment = null;
    MathPiperTokenizer tokenizer = null;
    LispPrinter printer = null;
    //private String iException = null;
    String defaultDirectory = null;
    String archive = "";
    String detect = "";
    String pathParent = "";
    boolean inZipFile = false;
    MathPiperOutputStream sideEffectsStream;
    private static SynchronousInterpreter singletonInstance;


    private SynchronousInterpreter(String docBase) {
        responseListeners = new ArrayList<ResponseListener>();
        removeListeners = new ArrayList<ResponseListener>();

        sideEffectsStream = new StringOutput();

        Utility.scriptsPath = "/org/mathpiper/assembledscripts/";

        try {
            System.out.print("Initializing CAS... ");

            iEnvironment = new Environment(sideEffectsStream);

            BuiltinFunction.addCoreFunctions(iEnvironment);


            iEnvironment.pushLocalFrame(true, "<START>");

            tokenizer = new MathPiperTokenizer();
            printer = new MathPiperPrinter(iEnvironment.iPrefixOperators, iEnvironment.iInfixOperators, iEnvironment.iPostfixOperators, iEnvironment.iBodiedOperators);


            //iEnvironment.iCurrentInput = new CachedStandardFileInputStream(iEnvironment.iInputStatus);



            //EvaluationResponse initializationEvaluationResponse = evaluate("LoadScript(\"initialization.rep/mathpiperinit.mpi\");");
            EvaluationResponse initializationEvaluationResponse = evaluate("MathPiperInitLoad();");
            if (initializationEvaluationResponse.isExceptionThrown()) {
                throw new Exception("Error during system script initialization.");
            }


            Scripts scripts = iEnvironment.scripts;

            Map scriptsMap = scripts.getMap();

            Set keysSet = scriptsMap.keySet();

            Iterator keyIterator = keysSet.iterator();

            while (keyIterator.hasNext()) {


                String functionName = (String) keyIterator.next();

                String[] scriptCode = scripts.getScript(functionName);

                if (scriptCode[0] == null) {

                    iEnvironment.iInputStatus.setTo(functionName);

                    String scriptString = scriptCode[1];

                    StringInputStream functionInputStream = new StringInputStream(scriptString, iEnvironment.iInputStatus);

                    scriptCode[0] = "+";

                    Utility.doInternalLoad(iEnvironment, -1, functionInputStream);

                } else {
                    //System.out.println("Already loaded.");
                }

            }//end while.

            iEnvironment.scripts = null;

            System.out.print("done. \n");

        } catch (Exception e) //Note:tk:need to handle exceptions better here.  should return exception to user in an EvaluationResponse.
        {
            if(e instanceof EvaluationException)
            {
                EvaluationException ee = (EvaluationException) e;
                System.out.println("Exception: " + ee.getMessage() + " Filename: " + ee.getFileName() + ", Line Number: " + ee.getLineNumber() + ", Line Index: " + ee.getLineIndex());
            }
            else
            {
                System.out.println(e.toString());
            }
            e.printStackTrace();
            
        }
    }//end constructor.


    private SynchronousInterpreter() {
        this(null);
    }


    static SynchronousInterpreter newInstance() {
        return new SynchronousInterpreter();
    }


    static SynchronousInterpreter newInstance(String docBase) {
        return new SynchronousInterpreter(docBase);
    }


    static SynchronousInterpreter getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new SynchronousInterpreter();
        }
        return singletonInstance;
    }


    static SynchronousInterpreter getInstance(String docBase) {
        if (singletonInstance == null) {
            singletonInstance = new SynchronousInterpreter(docBase);
        }
        return singletonInstance;
    }


    public EvaluationResponse evaluate(String inputExpression) {
        return this.evaluate(inputExpression, false);
    }//end method.


    /**
    Evaluate an input expression which is a string.

    @param inputExpression
    @param notifyEvaluationListeners
    @return
     */
    public EvaluationResponse evaluate(String inputExpression, boolean notifyEvaluationListeners) {


        EvaluationResponse evaluationResponse = EvaluationResponse.newInstance();
        if (inputExpression.length() == 0) {
            //return (String) "";
            evaluationResponse.setResult("Empty Input");
            return evaluationResponse;
        }

        InputStatus oldstatus = iEnvironment.iInputStatus;

        MathPiperInputStream previous = iEnvironment.iCurrentInput;

        try {
            iEnvironment.iEvalDepth = 0;

            //todo:tk:this was causing problems with GeoGebraPoint() on Windows.
            //environment.resetArgumentStack();


            //iException = null;

            ConsPointer inputExpressionPointer = new ConsPointer();

            iEnvironment.iInputStatus.setTo("String");

            StringInputStream newInput = new StringInputStream(inputExpression + ";", iEnvironment.iInputStatus);

            iEnvironment.iCurrentInput = newInput;

            if (iEnvironment.iPrettyReaderName != null) {
                ConsPointer args = new ConsPointer();
                Utility.applyString(iEnvironment, -1, inputExpressionPointer, iEnvironment.iPrettyReaderName, args);
            } else //Else not PrettyReader.
            {
                Parser infixParser = new MathPiperParser(tokenizer, newInput, iEnvironment, iEnvironment.iPrefixOperators, iEnvironment.iInfixOperators, iEnvironment.iPostfixOperators, iEnvironment.iBodiedOperators);
                infixParser.parse(-1, inputExpressionPointer);
            }

            return evaluate(inputExpressionPointer, notifyEvaluationListeners);

        } catch (Exception exception) {
            this.handleException(exception, evaluationResponse);
        } finally {
            iEnvironment.iCurrentInput = previous;
            iEnvironment.iInputStatus.restoreFrom(oldstatus);
        }

        if (notifyEvaluationListeners) {
            notifyListeners(evaluationResponse);
        }//end if.

        return evaluationResponse;

    }//end method.


    public EvaluationResponse evaluate(ConsPointer inputExpressionPointer) {
        return evaluate(inputExpressionPointer, false);
    }


    /**
    Evaluate an input expression which is a Lisp list.

    @param inputExpressionPointer
    @param notifyEvaluationListeners
    @return
     */
    public EvaluationResponse evaluate(ConsPointer inputExpressionPointer, boolean notifyEvaluationListeners) {


        //return this.evaluate(inputExpression, false);
        EvaluationResponse evaluationResponse = EvaluationResponse.newInstance();

        String resultString = "Exception";

        try {
            ConsPointer resultPointer = new ConsPointer();
            iEnvironment.iLispExpressionEvaluator.evaluate(iEnvironment, -1, resultPointer, inputExpressionPointer); //*** The main evaluation happens here.

            evaluationResponse.setResultList(resultPointer);

            if (resultPointer.type() == Utility.OBJECT) {

                Object object = resultPointer.car();

                if (object instanceof BuiltinContainer) {
                    BuiltinContainer builtinContainer = (BuiltinContainer) object;
                    evaluationResponse.setObject(builtinContainer.getObject());
                } else {
                    evaluationResponse.setObject(object);
                }
            }//end if.

            //Set the % symbol to the result of the current evaluation.
            String percent = (String) iEnvironment.getTokenHash().lookUp("%");
            iEnvironment.setGlobalVariable(-1, percent, resultPointer, true);

            StringBuffer outputBuffer = new StringBuffer();
            MathPiperOutputStream outputStream = new StringOutputStream(outputBuffer);

            if (iEnvironment.iPrettyPrinterName != null) {
                //Pretty printer.

                ConsPointer applyResultPointer = new ConsPointer();

                if (iEnvironment.iPrettyPrinterName.equals("\"RForm\"")) {
                    Cons holdAtom = AtomCons.getInstance(iEnvironment, -1, "Hold");

                    holdAtom.cdr().setCons(resultPointer.getCons());

                    Cons subListCons = SublistCons.getInstance(iEnvironment, holdAtom);

                    ConsPointer resultPointerWithHold = new ConsPointer(subListCons);

                    Utility.applyString(iEnvironment, -1, applyResultPointer, iEnvironment.iPrettyPrinterName, resultPointerWithHold);
                } else {
                    Utility.applyString(iEnvironment, -1, applyResultPointer, iEnvironment.iPrettyPrinterName, resultPointer);
                }

                printer.rememberLastChar(' ');
                printer.print(-1, applyResultPointer, outputStream, iEnvironment);
                resultString = outputBuffer.toString();

            } else {
                //Default printer.
                printer.rememberLastChar(' ');
                printer.print(-1, resultPointer, outputStream, iEnvironment);
                resultString = outputBuffer.toString();
            }



        } catch (Exception exception) {
            this.handleException(exception, evaluationResponse);
        }//end catch.


        evaluationResponse.setResult(resultString);

        String sideEffects = sideEffectsStream.toString();

        if (sideEffects != null && sideEffects.length() != 0) {
            evaluationResponse.setSideEffects(sideEffects);
        }

        /*try{
        org.mathpiper.builtin.functions.optional.ViewList.evaluate(iEnvironment, -1, inputExpressionPointer);
        }catch(Exception e)
        {
        e.printStackTrace();
        }*/

        try {
            if (inputExpressionPointer.getCons() instanceof SublistCons) {

                Object object = ((ConsPointer) inputExpressionPointer.getCons().car()).car();

                if (object instanceof String && ((String) object).startsWith("Load")) {
                    ConsPointer loadResult = new ConsPointer();
                    iEnvironment.getGlobalVariable(-1, "$LoadResult", loadResult);
                    StringBuffer string_out = new StringBuffer();
                    MathPiperOutputStream output = new StringOutputStream(string_out);
                    printer.rememberLastChar(' ');
                    printer.print(-1, loadResult, output, iEnvironment);
                    String loadResultString = string_out.toString();
                    evaluationResponse.setResult(loadResultString);
                    if (loadResult.type() == Utility.OBJECT) {
                        //JavaObject javaObject = (JavaObject) loadResult.car();
                        //evaluationResponse.setObject(javaObject.getObject());
                    }//end if.
                }//if.
            }//end if
        } catch (Exception e) {
            evaluationResponse.setExceptionMessage(e.getMessage());
            evaluationResponse.setException(e);
        }

        if (notifyEvaluationListeners) {
            notifyListeners(evaluationResponse);
        }//end if.

        return evaluationResponse;
    }


    private void handleException(Exception exception, EvaluationResponse evaluationResponse) {
        //exception.printStackTrace();  //todo:tk:uncomment for debugging.

        Evaluator.DEBUG = false;
        Evaluator.VERBOSE_DEBUG = false;
        Evaluator.TRACE_TO_STANDARD_OUT = false;
        Evaluator.iTraced = false;

        try {
            iEnvironment.iArgumentStack.reset(-1, iEnvironment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (exception instanceof EvaluationException) {
            EvaluationException mpe = (EvaluationException) exception;
            
            int errorLineNumber = mpe.getLineNumber();
            int errorLineIndex = mpe.getLineIndex();

            if (errorLineNumber == -1) {
                errorLineNumber = iEnvironment.iInputStatus.getLineNumber();
                errorLineIndex = iEnvironment.iInputStatus.getLineIndex();
                if (errorLineNumber == -1) {
                    errorLineNumber = 1; //Code was probably a single line submitted from the command line or from a single line evaluation request.
                }
                evaluationResponse.setLineNumber(errorLineNumber);
                evaluationResponse.setLineNumber(errorLineIndex);
                evaluationResponse.setSourceFileName(iEnvironment.iInputStatus.getFileName());
            } else {
                evaluationResponse.setLineNumber(mpe.getLineNumber());
                evaluationResponse.setLineNumber(errorLineIndex);
                evaluationResponse.setSourceFileName(mpe.getFileName());
            }


        } else {
            int errorLineNumber = iEnvironment.iInputStatus.getLineNumber();
            int errorLineIndex = iEnvironment.iInputStatus.getLineIndex();
            //if (errorLineNumber == -1) {
            //    errorLineNumber = 1; //Code was probably a single line submitted from the command line or from a single line evaluation request.
            //}
            evaluationResponse.setLineNumber(errorLineNumber);
            evaluationResponse.setLineNumber(errorLineIndex);
            evaluationResponse.setSourceFileName(iEnvironment.iInputStatus.getFileName());
        }

        evaluationResponse.setException(exception);
        evaluationResponse.setExceptionMessage(exception.getMessage());
    }


    public void haltEvaluation() {
        //synchronized (iEnvironment) {
        //iEnvironment.iEvalDepth = iEnvironment.iMaxEvalDepth + 100; //Deprecated.
        //evaluationThread.interrupt();
        //}
    }


    public Environment getEnvironment() {
        return iEnvironment;
    }

    /*public java.util.zip.ZipFile getScriptsZip()
    {
    return Utility.zipFile;
    }//end method.*/

    public void addResponseListener(ResponseListener listener) {
        responseListeners.add(listener);
    }


    public void removeResponseListener(ResponseListener listener) {
        responseListeners.remove(listener);
    }


    protected void notifyListeners(EvaluationResponse response) {
        //notify listeners.
        for (ResponseListener listener : responseListeners) {
            listener.response(response);

            if (listener.remove()) {
                removeListeners.add(listener);
            }//end if.
        }//end for.


        //Remove certain listeners.
        for (ResponseListener listener : removeListeners) {

            if (listener.remove()) {
                responseListeners.remove(listener);
            }//end if.
        }//end for.

        removeListeners.clear();

    }//end method.

}// end class.

