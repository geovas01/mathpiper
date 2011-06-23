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
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.lisp.printers.MathPiperPrinter;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

public class RunTestSuite {

    private Interpreter mathPiper;
    private EvaluationResponse evaluationResponse;
    private java.io.FileWriter logFile;
    private int exceptionCount = 0;
    private Tests tests;
    private String output;


    public RunTestSuite() {
        super();

        tests = new Tests();


    }//end constructor.


    public void test() {
        test(null);
    }


    public void test(String nameOfSingleFunctionToTest) {
        try {

            logFile = new java.io.FileWriter("./tests/mathpiper_tests.log");

            mathPiper = Interpreters.newSynchronousInterpreter();


            //Initialization code.
            //evaluationResponse = mathPiper.evaluate("StackTraceOn();");
            //output = evaluationResponse(evaluationResponse);
            //System.out.println("Turning stack tracing on: " + output);
            //logFile.write("Turning stack tracing on: " + output);


            output = "\n\n***** Beginning of tests. *****\n";
            output = "\n***** " + new java.util.Date() + " *****\n";
            //output += "***** Using a new interpreter instance for each test file. *****\n";
            output += "***** MathPiper version: " + org.mathpiper.Version.version + " *****\n";
            System.out.print(output);
            logFile.write(output);



            Set keySet = tests.getMap().keySet();

            ArrayList keyArray = new ArrayList(keySet);

            Collections.sort(keyArray, String.CASE_INSENSITIVE_ORDER);

            Iterator keyIterator = keyArray.iterator();


            if (nameOfSingleFunctionToTest == null) {

                while (keyIterator.hasNext()) {

                    String testName = (String) keyIterator.next();

                    runSingleTest(testName);

                }//end while.
            } else {
                runSingleTest(nameOfSingleFunctionToTest);
            }


            output = "\n\n***** Tests complete *****\n\nException Count: " + exceptionCount + "\n\n";
            System.out.print(output);
            logFile.write(output);


            //Check the global variables.
            evaluationResponse = mathPiper.evaluate("Echo(GlobalVariablesGet());");
            output = evaluationResponse(evaluationResponse);
            System.out.println("Global variables: " + output);
            logFile.write("GlobalVariables: " + output);

            logFile.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }//end method.


    private void runSingleTest(String testName) throws Exception {
        String[] testScriptArray = (String[]) tests.getMap().get(testName);

        if(testScriptArray == null)
        {
            throw new Exception("The test named " + testName + " does not exist.");
        }

        String testScript = (String) testScriptArray[1];


        output = "\n===========================\n" + testName + ": \n\n";
        System.out.print(output);
        logFile.write(output);

        //evaluationResponse = mathPiper.evaluate(testScript);
        //output = evaluationResponse(evaluationResponse);

        try {

            evaluateTestScript(mathPiper.getEnvironment(), -1, new StringInputStream(testScript, mathPiper.getEnvironment().iInputStatus), true, true);

        } catch (Exception e) {
            exceptionCount++;

            System.out.println(e.getMessage());
            logFile.write(e.getMessage());
        }
    }


    private String evaluationResponse(EvaluationResponse evaluationResponse) {

        String result = "Result: " + evaluationResponse.getResult() + "\n";

        if (!evaluationResponse.getSideEffects().equals("")) {
            result = result + "\nSide Effects:\n" + evaluationResponse.getSideEffects();
        }


        if (evaluationResponse.isExceptionThrown()) {
            result = result + "\nException:" + evaluationResponse.getExceptionMessage() + " Source file: " + evaluationResponse.getSourceFileName() + " Line number: " + evaluationResponse.getLineNumber();
            exceptionCount++;
        }

        return result;
    }


    public String evaluateTestScript(Environment aEnvironment, int aStackTop, MathPiperInputStream aInput, boolean evaluate, boolean print) throws Exception {

        StringBuffer printedScriptStringBuffer = new StringBuffer();

        MathPiperInputStream previousInput = aEnvironment.iCurrentInput;

        StringBuffer outputStringBuffer = new StringBuffer();
        MathPiperOutputStream previousOutput = aEnvironment.iCurrentOutput;
        aEnvironment.iCurrentOutput = new StringOutputStream(outputStringBuffer);



        try {
            aEnvironment.iCurrentInput = aInput;
            // TODO make "EndOfFile" a global thing
            // read-parse-evaluate to the end of file
            String eof = (String) aEnvironment.getTokenHash().lookUp("EndOfFile");
            boolean endoffile = false;
            MathPiperParser parser = new MathPiperParser(new MathPiperTokenizer(),
                    aEnvironment.iCurrentInput, aEnvironment,
                    aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators,
                    aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);

            while (!endoffile) {

                ConsPointer readIn = new ConsPointer();
                // Read expression
                parser.parse(aStackTop, readIn);

                LispError.check(aEnvironment, aStackTop, readIn.getCons() != null, LispError.READING_FILE, "INTERNAL");
                // check for end of file
                if (readIn.car() instanceof String && ((String) readIn.car()).equals(eof)) {
                    endoffile = true;
                } // Else print and maybe evaluate
                else {

                    if (print == true) {
                        printExpression(printedScriptStringBuffer, aEnvironment, readIn);

                        String expression = printedScriptStringBuffer.toString();

                        System.out.println(expression);
                        logFile.append(expression);
                        printedScriptStringBuffer.delete(0, printedScriptStringBuffer.length());
                    }

                    if (evaluate == true) {
                        ConsPointer result = new ConsPointer();
                        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, result, readIn);

                        if (outputStringBuffer.length() > 0) {
                            String sideEffectOutputString = outputStringBuffer.toString();
                            System.out.println(sideEffectOutputString);
                            logFile.append(sideEffectOutputString);
                            outputStringBuffer.delete(0, outputStringBuffer.length());
                        }
                        ;
                    }

                }
            }//end while.

            return printedScriptStringBuffer.toString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(); //todo:tk:uncomment for debugging.

            EvaluationException ee = new EvaluationException(e.getMessage(), aEnvironment.iInputStatus.fileName(), aEnvironment.iCurrentInput.iStatus.lineNumber());
            throw ee;
        } finally {
            aEnvironment.iCurrentInput = previousInput;
            aEnvironment.iCurrentOutput = previousOutput;
        }
    }


    public static void printExpression(StringBuffer outString, Environment aEnvironment, ConsPointer aExpression) throws Exception {
        MathPiperPrinter infixprinter = new MathPiperPrinter(aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators, aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);

        MathPiperOutputStream stream = new StringOutputStream(outString);
        infixprinter.print(-1, aExpression, stream, aEnvironment);
        outString.append(";");

    }//end method.


    public static void main(String[] args) {

        RunTestSuite pt = new RunTestSuite();
        pt.test("IsList");

    }//end main
}//end class.

