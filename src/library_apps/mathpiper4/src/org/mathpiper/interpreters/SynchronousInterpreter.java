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
import org.mathpiper.lisp.parsers.LispParser;
import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.io.StringOutputStream;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.parsers.Parser;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.lisp.unparsers.LispUnparser;
import org.mathpiper.lisp.unparsers.MathPiperUnparser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.mathpiper.Scripts;
import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.io.StringOutput;
import org.mathpiper.lisp.Evaluator;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

import org.mathpiper.builtin.JavaObject;

/**
 *
 *
 */
public class SynchronousInterpreter implements Interpreter {

    private ArrayList<ResponseListener> removeListeners;
    private ArrayList<ResponseListener> responseListeners;
    private Environment iEnvironment = null;
    LispUnparser printer = null;
    //private String iException = null;
    String defaultDirectory = null;
    String archive = "";
    String detect = "";
    String pathParent = "";
    boolean inZipFile = false;
    MathPiperOutputStream sideEffectsStream;
    private static SynchronousInterpreter singletonInstance;
    private int loopIndex = 1;
    boolean returnValue = true;
    Iterator keyIterator = null;
    Scripts scripts = null;

    private SynchronousInterpreter(String docBase) {
        responseListeners = new ArrayList<ResponseListener>();
        removeListeners = new ArrayList<ResponseListener>();

        sideEffectsStream = new StringOutput();


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

    public static SynchronousInterpreter getInstance() {
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

    public boolean initialize() {

    	if(returnValue == false)
    	{
    		return false;
    	}

        try {
            switch (loopIndex) {
                case 1:

                    System.out.print("Initializing CAS... ");

                    iEnvironment = new Environment(sideEffectsStream);

                    BuiltinFunction.addCoreFunctions(iEnvironment);
                    
                    //Initialize parsers.
                    new LispParser(iEnvironment.iCurrentTokenizer, iEnvironment.getCurrentInput(), iEnvironment);
                    new MathPiperParser(iEnvironment.iCurrentTokenizer, iEnvironment.getCurrentInput(), iEnvironment, iEnvironment.iPrefixOperators, iEnvironment.iInfixOperators, iEnvironment.iPostfixOperators, iEnvironment.iBodiedOperators);


                    iEnvironment.pushLocalFrame(true, "<START>");

                    
                    printer = new MathPiperUnparser(iEnvironment.iPrefixOperators, iEnvironment.iInfixOperators, iEnvironment.iPostfixOperators, iEnvironment.iBodiedOperators);


                    EvaluationResponse initializationEvaluationResponse = evaluate("MathPiperInitLoad();");
                    if (initializationEvaluationResponse.isExceptionThrown()) {
                        Throwable ex = initializationEvaluationResponse.getException();
                        throw ex;
                    }


                    scripts = iEnvironment.scripts;

                    //Map scriptsMap = scripts.getMap();

                    List functionList = new ArrayList();

                    functionList.add("+");
                    functionList.add("-");
                    functionList.add("*");
                    functionList.add("/");
                    functionList.add("^");
                    //functionList.add("UniVar?");
                    //functionList.add("Sign");
                    //functionList.add("MakeMultiNomial");
                    //functionList.add("Complex");
                    //functionList.add("Limit");
                    //functionList.add("II");
                    //functionList.add("UniVariate");
                    //functionList.add("SparseUniVar");
                    //functionList.add("AntiDeriv");


                    keyIterator = functionList.iterator();

                    //Set keysSet = scriptsMap.keySet();

                    //keyIterator = keysSet.iterator();

                    loopIndex++;
                    break;
                case 2:

                    int loadCounter = 0;

                    while (keyIterator.hasNext() && loadCounter++ <= 10) {

                        String functionName = (String) keyIterator.next();

                	if (Utility.loadLibraryFunction(functionName, iEnvironment, loopIndex) == false) {
                	    LispError.throwError(iEnvironment, -1, "No script returned for function: " + functionName
                		    + " from Scripts.java.");
                	}

                    }//end while.
                    if (!keyIterator.hasNext()) {
                        loopIndex++;
                    }
                    break;

                default:

                    //iEnvironment.scripts = null;
                    
                    initializationEvaluationResponse = evaluate("NM(1);");
                    if (initializationEvaluationResponse.isExceptionThrown()) {
                        Throwable ex = initializationEvaluationResponse.getException();
                        throw ex;
                    }

                    System.out.print("done. \n");

                    returnValue = false;

                    break;

            }//end switch.



        } catch (Throwable e) //Note:tk:need to handle exceptions better here.  should return exception to user in an EvaluationResponse.
        {
            if (e instanceof EvaluationException) {
                EvaluationException ee = (EvaluationException) e;
                System.out.println("Exception: " + ee.getMessage() + " Filename: " + ee.getFileName() + ", Line Number: " + ee.getLineNumber() + ", Line Index: " + ee.getEndIndex());
            } else {
                System.out.println(e.toString());
            }
            e.printStackTrace();

        }

        return returnValue;
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
        if (inputExpression == null || inputExpression.length() == 0) {
            //return (String) "";
            evaluationResponse.setResult("Empty Input");
            return evaluationResponse;
        }

        InputStatus oldstatus = iEnvironment.iInputStatus;

        MathPiperInputStream previous = iEnvironment.getCurrentInput();

        try {
            iEnvironment.iEvalDepth = 0;

            //todo:tk:this was causing problems with GeoGebraPoint() on Windows.
            //environment.resetArgumentStack();


            //iException = null;

            Cons parsedOrAppliedInputExpression;

            iEnvironment.iInputStatus.setTo("String");

            StringInputStream newInput = new StringInputStream(inputExpression + ";", iEnvironment.iInputStatus);

            iEnvironment.setCurrentInput(newInput);

            parsedOrAppliedInputExpression = Utility.applyString(iEnvironment, -1, iEnvironment.iParserName, null);

            return evaluate(parsedOrAppliedInputExpression, notifyEvaluationListeners);

        } catch (Throwable exception) {
            this.handleException(exception, evaluationResponse);
        } finally {
            iEnvironment.setCurrentInput(previous);
            iEnvironment.iInputStatus.restoreFrom(oldstatus);
        }

        if (notifyEvaluationListeners) {
            notifyListeners(evaluationResponse);
        }//end if.

        return evaluationResponse;

    }//end method.

    public EvaluationResponse evaluate(Cons inputExpression) {
        return evaluate(inputExpression, false);
    }

    /**
    Evaluate an input expression which is a Lisp list.

    @param inputExpression
    @param notifyEvaluationListeners
    @return
     */
    public EvaluationResponse evaluate(Cons inputExpression, boolean notifyEvaluationListeners) {


        //return this.evaluate(inputExpression, false);
        EvaluationResponse evaluationResponse = EvaluationResponse.newInstance();

        String resultString = "Exception";

        try {
            Cons result = iEnvironment.iLispExpressionEvaluator.evaluate(iEnvironment, -1, inputExpression); //*** The main evaluation happens here.

            evaluationResponse.setResultList(result);

            if (result.type() == Utility.OBJECT) {

                Object object = result.car();

                if (object instanceof BuiltinContainer) {
                    BuiltinContainer builtinContainer = (BuiltinContainer) object;
                    evaluationResponse.setObject(builtinContainer.getObject());
                } else {
                    evaluationResponse.setObject(object);
                }
            }//end if.

            //Set the % symbol to the result of the current evaluation.
            String percent = "%";
            iEnvironment.setLocalOrGlobalVariable(-1, percent, result, true, false);

            StringBuffer outputBuffer = new StringBuffer();
            MathPiperOutputStream outputStream = new StringOutputStream(outputBuffer);

            if (iEnvironment.iUnparserName != null) {
                //Unparser.

                Cons applyResult = null;

                if (iEnvironment.iUnparserName.equals("\"RForm\"")) {
                    Cons holdAtom = AtomCons.getInstance(iEnvironment, -1, "Hold");

                    holdAtom.cdr().setCdr(result);

                    Cons resultWithHold = SublistCons.getInstance(iEnvironment, holdAtom);

                    applyResult = Utility.applyString(iEnvironment, -1, iEnvironment.iUnparserName, resultWithHold);
                } else {
                    applyResult = Utility.applyString(iEnvironment, -1, iEnvironment.iUnparserName, result);
                }

                printer.rememberLastChar(' ');
                printer.print(-1, applyResult, outputStream, iEnvironment);
                resultString = outputBuffer.toString();

            } else {
                //Default printer.
                printer.rememberLastChar(' ');
                printer.print(-1, result, outputStream, iEnvironment);
                resultString = outputBuffer.toString();
            }



        } catch (Throwable exception) {
            this.handleException(exception, evaluationResponse);
        }//end catch.


        evaluationResponse.setResult(resultString);

        String sideEffects = sideEffectsStream.toString();

        if (sideEffects != null && sideEffects.length() != 0) {
            evaluationResponse.setSideEffects(sideEffects);
        }


        try {
            if (inputExpression instanceof SublistCons) {

                Object object = ((Cons) inputExpression.car()).car();

                if (object instanceof String && ((String) object).startsWith("Load")) {

                    Cons loadResult = iEnvironment.getLocalOrGlobalVariable(-1, "$LoadResult");
                    StringBuffer string_out = new StringBuffer();
                    MathPiperOutputStream output = new StringOutputStream(string_out);
                    printer.rememberLastChar(' ');
                    printer.print(-1, loadResult, output, iEnvironment);
                    String loadResultString = string_out.toString();
                    evaluationResponse.setResult(loadResultString);
                    if (loadResult.type() == Utility.OBJECT) {
                        JavaObject javaObject = (JavaObject) loadResult.car();
                        evaluationResponse.setObject(javaObject.getObject());
                    }//end if.
                }//if.
            }//end if
        } catch (Throwable e) {
            evaluationResponse.setException(e);
        }

        if (notifyEvaluationListeners) {
            notifyListeners(evaluationResponse);
        }//end if.

        return evaluationResponse;
    }

    private void handleException(Throwable exception, EvaluationResponse evaluationResponse) {
        //exception.printStackTrace();  //todo:tk:uncomment for debugging.

        Evaluator.DEBUG = false;
        Evaluator.VERBOSE_DEBUG = false;
        Evaluator.TRACE_TO_STANDARD_OUT = false;
        Evaluator.iTraced = false;
        Environment.saveDebugInformation = false;

        try {
            iEnvironment.iArgumentStack.reset(-1, iEnvironment);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (exception instanceof EvaluationException) {
            EvaluationException mpe = (EvaluationException) exception;

            int errorLineNumber = mpe.getLineNumber();
            int errorLineIndex = mpe.getEndIndex();

            if (errorLineNumber == -1) {
                errorLineNumber = iEnvironment.iInputStatus.getLineNumber();
                errorLineIndex = iEnvironment.iInputStatus.getLineIndex();
                if (errorLineNumber == -1) {
                    errorLineNumber = 1; //Code was probably a single line submitted from the command line or from a single line evaluation request.
                }
                evaluationResponse.setSourceFileName(iEnvironment.iInputStatus.getSourceName());
            } else {
                evaluationResponse.setSourceFileName(mpe.getFileName());
            }


        } else {
            int errorLineNumber = iEnvironment.iInputStatus.getLineNumber();
            int errorLineIndex = iEnvironment.iInputStatus.getLineIndex();
            //if (errorLineNumber == -1) {
            //    errorLineNumber = 1; //Code was probably a single line submitted from the command line or from a single line evaluation request.
            //}
            evaluationResponse.setSourceFileName(iEnvironment.iInputStatus.getSourceName());
        }

        evaluationResponse.setException(exception);
    }

    public void haltEvaluation() {
        //synchronized (iEnvironment) {
        //iEnvironment.iEvalDepth = iEnvironment.iMaxEvalDepth + 100; //Deprecated.
        //evaluationThread.interrupt();
        //}
        Environment.haltEvaluation = true;
        
        Environment.haltEvaluationMessage = "User halted evaluation.";
    }
    
    public void haltEvaluation(String message) {

        Environment.haltEvaluation = true;
        
        Environment.haltEvaluationMessage = message;
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

