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
 */
//}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import org.mathpiper.Tests;
import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.io.MathPiperOutputStream;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.io.StringOutputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.Cons;

import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.lisp.printers.MathPiperPrinter;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

public class TestSuite {

    private boolean printExpression = false;
    private boolean stackTrace = false;
    private Interpreter interpreter;
    private EvaluationResponse evaluationResponse;
    private java.io.FileWriter logFile;
    private String logFileName = "mathpiper_tests.log";
    private Tests tests;
    private String output = "";
    //-------------------
    private static String[] argumentErrors = new String[10];
    private static int argumentErrorCount = 0;
    private static String testTypeArgs = "";
    private static String testTypeMessage = "";
    private static long elapsedTime;

    private static enum TestType {
        ALL, NONE, SOME, EXCEPT
    }
    private static TestType testType = TestType.ALL;

    public TestSuite() {
        super();

        tests = new Tests();


    }//end constructor.

    private ArrayList getKeyArray() {
        Set builtinFunctionsKeySet = tests.getBuiltInFunctionsMap().keySet();

        ArrayList builtInKeyArray = new ArrayList(builtinFunctionsKeySet);

        Collections.sort(builtInKeyArray, String.CASE_INSENSITIVE_ORDER);


        Set userFunctionsKeySet = tests.getUserFunctionsMap().keySet();

        ArrayList userKeyArray = new ArrayList(userFunctionsKeySet);

        Collections.sort(userKeyArray, String.CASE_INSENSITIVE_ORDER);


        builtInKeyArray.addAll(userKeyArray);

        return builtInKeyArray;
    }

    public void test() {
        test(getKeyArray());
    }

    public void testExcept(String except) {
        ArrayList keyArray = getKeyArray();

        String[] functionNamesArray = except.split(",");

        for (String name : functionNamesArray) {
            keyArray.remove(name);
        }

        test(keyArray);
    }

    public void testSome(String some) {

        ArrayList keyArray = new ArrayList();

        String[] functionNamesArray = some.split(",");

        for (String name : functionNamesArray) {
            keyArray.add(name);
        }

        test(keyArray);
    }

    public void test(ArrayList keyArray) {
        try {

            logFile = new java.io.FileWriter("./tests/" + logFileName); //"./tests/mathpiper_tests.log"
            
        	elapsedTime = System.currentTimeMillis();

            interpreter = Interpreters.newSynchronousInterpreter();

            Environment environment = interpreter.getEnvironment();

            Interpreters.addOptionalFunctions(environment,"org/mathpiper/builtin/functions/optional/");

            Interpreters.addOptionalFunctions(environment,"org/mathpiper/builtin/functions/plugins/jfreechart/");


            if (this.stackTrace == true) {
                evaluationResponse = interpreter.evaluate("StackTraceOn();");
                output = evaluationResponse(evaluationResponse);
                System.out.println("Stack tracing is on: " + output);
                logFile.write("Stack tracing is on: " + output);
            }


            output += new java.util.Date() + ".\n";
            //output += "***** Using a new interpreter instance for each test file. *****\n";
            output += "MathPiper version: " + org.mathpiper.Version.version + ".\n";
            output += testTypeMessage + ".\n";
            output += "Beginning of tests:\n";
            System.out.print(output);
            logFile.write(output);

            Iterator keyIterator = keyArray.iterator();
            
            Environment.saveDebugInformation = true;

            while (keyIterator.hasNext()) {

                String testName = (String) keyIterator.next();

                runSingleTest(testName);

            }//end while.



            output = "\n\n***** Tests complete *****\n\n";
            System.out.print(output);
            logFile.write(output);


            //Check the global variables.
            evaluationResponse = interpreter.evaluate("Echo(GlobalVariablesGet());");
            output = evaluationResponse(evaluationResponse);
            System.out.println("Global variables: " + output);
            logFile.write("GlobalVariables: " + output);
            
            elapsedTime = System.currentTimeMillis() - elapsedTime;
            
            int seconds = (int) (elapsedTime / 1000) % 60 ;
            
            int minutes = (int) ((elapsedTime / (1000*60)) % 60);
            
            System.out.println("Elapsed Time: " + minutes + ":" + seconds);
            logFile.write("\nElapsed Time: " + minutes + ":" + seconds);

            logFile.close();

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.flush();
            System.err.flush();

        }

    }//end method.

    private void runSingleTest(String testName) throws Exception {
        String[] testScriptArray = (String[]) tests.getBuiltInFunctionsMap().get(testName);

        if (testScriptArray == null) {
            testScriptArray = (String[]) tests.getUserFunctionsMap().get(testName);
        }

        if (testScriptArray == null) {
            throw new Exception("The test named " + testName + " does not exist.");
        }

        String testFilePath = testScriptArray[2];

        String testScript = testScriptArray[1];

        interpreter.getEnvironment().iInputStatus.setTo(testFilePath);


        output = "\n================================================================\nTesting " + testName + " in file <" + testFilePath + ">: \n\n";
        System.out.print(output);
        logFile.write(output);

        //evaluationResponse = mathPiper.evaluate(testScript);
        //output = evaluationResponse(evaluationResponse);

        try {

            evaluateTestScript(interpreter.getEnvironment(), -1, new StringInputStream(testScript, interpreter.getEnvironment().iInputStatus), true);

        } catch (Exception e) {

            System.out.println(e.getMessage());
            logFile.write(e.getMessage());

            logFile.flush();

            logFile.close();

            throw e;
        }
    }

    private String evaluationResponse(EvaluationResponse evaluationResponse) {

        String result = "Result: " + evaluationResponse.getResult() + "\n";

        if (!evaluationResponse.getSideEffects().equals("")) {
            result = result + "\nSide Effects:\n" + evaluationResponse.getSideEffects();
        }


        if (evaluationResponse.isExceptionThrown()) {
            result = result + "\nException:" + evaluationResponse.getException().getMessage() + " Source file: " + evaluationResponse.getSourceFileName();
            
            if(evaluationResponse.getException() instanceof EvaluationException)
            {
                EvaluationException ex = (EvaluationException) evaluationResponse.getException();
                result += " Line number: "  + ex.getLineNumber() + " Line start index: " + ex.getStartIndex();
            }
        }

        return result;
    }

    public String evaluateTestScript(Environment aEnvironment, int aStackTop, MathPiperInputStream aInput, boolean evaluate) throws Exception {

        StringBuffer printedScriptStringBuffer = new StringBuffer();

        MathPiperInputStream previousInput = aEnvironment.getCurrentInput();

        StringBuffer outputStringBuffer = new StringBuffer();
        MathPiperOutputStream previousOutput = aEnvironment.iCurrentOutput;
        aEnvironment.iCurrentOutput = new StringOutputStream(outputStringBuffer);





        try {
            aEnvironment.setCurrentInput(aInput);
            // TODO make "EndOfFile" a global thing
            // read-parse-evaluate to the end of file
            String eof = "EndOfFile";
            boolean endoffile = false;
            MathPiperParser parser = new MathPiperParser(new MathPiperTokenizer(), aEnvironment.getCurrentInput(), aEnvironment,
                    aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators,
                    aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);

            while (!endoffile) {

                // Read expression
                Cons readIn = parser.parse(aStackTop);

                if(readIn == null) LispError.throwError(aEnvironment, aStackTop, LispError.READING_FILE, "");
                // check for end of file
                if (readIn.car() instanceof String && ((String) readIn.car()).equals(eof)) {
                    endoffile = true;
                } // Else print and maybe evaluate
                else {

                    if (printExpression == true) {
                        printExpression(printedScriptStringBuffer, aEnvironment, readIn);

                        String expression = printedScriptStringBuffer.toString();

                        System.out.println(expression);
                        logFile.append(expression + "\n");
                        printedScriptStringBuffer.delete(0, printedScriptStringBuffer.length());
                    }

                    if (evaluate == true) {
                        
                        Cons result = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, readIn);

                        if (outputStringBuffer.length() > 0) {
                            String sideEffectOutputString = outputStringBuffer.toString();
                            System.out.println(sideEffectOutputString);
                            logFile.append(sideEffectOutputString + "\n");
                            outputStringBuffer.delete(0, outputStringBuffer.length());
                        }
                        ;
                    }

                }
            }//end while.

            return printedScriptStringBuffer.toString();

        } catch (Exception e) {
            //System.out.println(e.getMessage());
            //e.printStackTrace(); //todo:tk:uncomment for debugging.
            
            //EvaluationException ee = new EvaluationException("\n\n\n***EXCEPTION[ " + e.getMessage() + " ]EXCEPTION***\n", aEnvironment.getCurrentInput().iStatus.getFileName(), aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1, aEnvironment.getCurrentInput().iStatus.getLineNumber());
            
            String errorMessage = "\n\n\n***EXCEPTION[ " + e.getMessage() + " ]EXCEPTION*** ";
            
            if(e instanceof EvaluationException)
            {
        	EvaluationException evaluationException = (EvaluationException) e;
        	
        	errorMessage = errorMessage + aEnvironment.getCurrentInput().iStatus.getFileName() + ", Line: " + evaluationException.getLineNumber() + ", Start Index: " + evaluationException.getStartIndex();
            }
            
            
            Exception ee = new Exception(errorMessage);
            throw ee;
        } finally {
            aEnvironment.setCurrentInput(previousInput);
            aEnvironment.iCurrentOutput = previousOutput;
        }
    }

    public static void printExpression(StringBuffer outString, Environment aEnvironment, Cons aExpression) throws Exception {
        MathPiperPrinter infixprinter = new MathPiperPrinter(aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators, aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);

        MathPiperOutputStream stream = new StringOutputStream(outString);
        infixprinter.print(-1, aExpression, stream, aEnvironment);
        outString.append(";");

    }//end method.

    public boolean isPrintExpression() {
        return printExpression;
    }

    public void setPrintExpression(boolean printExpression) {
        this.printExpression = printExpression;
    }

    public boolean isStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(boolean stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public static void main(String[] args) {
    	
        TestSuite testSuite = new TestSuite();

        int argIndex;
        for (argIndex = 0; argIndex < args.length; argIndex++) {
            String arg = args[argIndex];

            String value;

            if (arg.length() >= 2 && arg.charAt(0) == '-') {
                char key = Character.toLowerCase(arg.charAt(1));
                switch (key) {
                    case 'h':
                        String usageMessage =
                                "-s test,test,... (Run only some of the tests, the ones that are listed).)\n"
                                + "-e test,test,... (Run all of the tests, except for the ones that are listed.)\n"
                                + "-f <file name> (Specifies the name and path of the log file.)\n"
                                + "-t (Include a stack trace when an exception is thrown.)\n"
                                + "-p (Print each test just before it is evaluated.)\n";

                        System.out.println(usageMessage);

                        testType = TestType.NONE;
                        continue;
                    case 's':
                        break;
                    case 'e':
                        break;
                    case 'f':
                        break;
                    case 't':
                        testSuite.setStackTrace(true);
                        continue;
                    case 'p':
                        testSuite.setPrintExpression(true);
                        continue;
                    default:
                        if (argumentErrorCount < argumentErrors.length) {
                            argumentErrors[argumentErrorCount++] =
                                    "Invalid option \"" + arg + "\"";
                        }
                        continue;
                }//end switch.




                //Process options that have values.
                if (arg.length() > 2) {
                    value = arg.substring(2);
                } else if (argIndex + 1 < args.length) {
                    value = args[++argIndex];
                } else {
                    if (argumentErrorCount < argumentErrors.length) {
                        argumentErrors[argumentErrorCount++] =
                                "Option \"" + arg
                                + "\" invalid.";
                    }
                    continue;
                }


                switch (key) {
                    case 's':
                        testType = TestType.SOME;
                        testTypeArgs = value;
                        break;
                    case 'e':
                        testType = TestType.EXCEPT;
                        testTypeArgs = value;
                        break;
                    case 'f':
                        testSuite.setLogFileName(value);
                        break;

                }//end switch.

            }//end if.

            //Place error message here.
        }//end for.

        if (argumentErrorCount > 0) {
            //Print all of the argument errors (if any).
            for (int i = 0; i < argumentErrorCount; i++) {
                System.out.println(argumentErrors[i]);
            }
        } else {
            //Run test.
        	
            switch (testType) {
                case ALL:
                    testTypeMessage = "Running all tests.";
                    testSuite.test();
                    break;

                case SOME:
                    testTypeMessage = "Running only the following tests: " + testTypeArgs;
                    testSuite.testSome(testTypeArgs);
                    break;

                case EXCEPT:
                    testTypeMessage = "Running all tests except the following: " + testTypeArgs;
                    testSuite.testExcept(testTypeArgs);
                    break;

                case NONE:
                    break;

            }//end switch.
            


            
        }//end else.


    }//end main
}//end class.

