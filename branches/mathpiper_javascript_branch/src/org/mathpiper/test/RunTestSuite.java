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
import java.util.Map;
import java.util.Set;
import org.mathpiper.Tests;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;

public class RunTestSuite {

    private Interpreter mathPiper;
    private EvaluationResponse evaluationResponse;
    private java.io.FileWriter logFile;
    private int exceptionCount = 0;


    public RunTestSuite() {
        super();

    }//end constructor.


    public void test() {
        try {

            logFile = new java.io.FileWriter("./tests/mathpiper_tests.log");

            Tests tests = new Tests();


            String output;

            mathPiper = Interpreters.newSynchronousInterpreter();


            //Initialization code.
            evaluationResponse = mathPiper.evaluate("StackTraceOn();");
            output = evaluationResponse(evaluationResponse);
            System.out.println("Turning stack tracing on: " + output);
            logFile.write("Turning stack tracing on: " + output);


            output = "\n\n***** Beginning of tests. *****\n";
            output = "\n***** " + new java.util.Date() + " *****\n";
            //output += "***** Using a new interpreter instance for each test file. *****\n";
            output += "***** MathPiper version: " + org.mathpiper.Version.version + " *****\n";
            System.out.print(output);
            logFile.write(output);

            Map testsMap = tests.getMap();

            Set keySet = testsMap.keySet();
            
            ArrayList keyArray = new ArrayList(keySet);

            Collections.sort(keyArray, String.CASE_INSENSITIVE_ORDER);

            Iterator keyIterator = keyArray.iterator();



            while (keyIterator.hasNext()) {


                String testName = (String) keyIterator.next();

                String[] testScriptArray = (String[]) testsMap.get(testName);

                String testScript = (String) testScriptArray[1];


                output = "\n===========================\n" + testName + ": \n\n";
                System.out.print(output);
                logFile.write(output);

                evaluationResponse = mathPiper.evaluate(testScript);


                output = evaluationResponse(evaluationResponse);


                System.out.println(output);
                logFile.write(output);


            }//end while.


            output = "\n\n***** Tests complete *****\n\nException Count: " + exceptionCount + "\n\n";
            System.out.print(output);
            logFile.write(output);


            //Check the global variables.
            evaluationResponse = mathPiper.evaluate("Echo(GlobalVariablesGet());");
            output = evaluationResponse(evaluationResponse);
            System.out.println("Global variables: " + output);
            logFile.write("GlobalVariables: " + output);

            logFile.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();

        }

    }//end method.


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


    public static void main(String[] args) {

        RunTestSuite pt = new RunTestSuite();
        pt.test();

    }//end main
}//end class.

