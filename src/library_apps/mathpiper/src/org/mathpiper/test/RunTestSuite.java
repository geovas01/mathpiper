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

import org.mathpiper.Interfaces;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RunTestSuite {

    private Interpreter mathPiper;
    private java.io.File testDirectory;
    private EvaluationResponse evaluationResponse;
    private java.io.FileWriter logFile;

    public RunTestSuite() {
        super();

    }//end constructor.

    public void test() {
        try {

            logFile = new java.io.FileWriter("./mathpiper_tests.log");



            BufferedReader scriptNames = new BufferedReader(new InputStreamReader(java.lang.ClassLoader.getSystemResource("tests/scripts/test_index.txt").openStream()));
            if (scriptNames != null) //File is on the classpath.
            {
                String output;

                mathPiper = Interfaces.newSynchronousInterpreter();
                output = "\n***** Beginning of tests. *****\n";
                output = "\n***** " + new java.util.Date() + " *****\n";
                output += "***** Using a new interpreter instance for each test file. *****\n";
                 output += "***** MathPiper version: " + org.mathpiper.Version.version + " *****\n";
                System.out.print(output);
                logFile.write(output);

                while (scriptNames.ready()) {


                    String scriptName = scriptNames.readLine();


                    if (scriptName.endsWith(".mpt")) {

                        output = "\n===========================\n" + scriptName + ": \n\n";
                        System.out.print(output);
                        logFile.write(output);

                        evaluationResponse = mathPiper.evaluate("Load(\"tests/scripts/" + scriptName + "\");");
                        output = "Result: " + evaluationResponse.getResult() + "\n\nSide Effects:\n" + evaluationResponse.getSideEffects() + "\nException:" + evaluationResponse.getExceptionMessage();
                        System.out.println(output);
                        logFile.write(output);
                    } else {
                        output = "\n===========================\n" + scriptName + ": is not a MathPiper test file.\n";
                        System.out.print(output);
                        logFile.write(output);

                    }

                }//end while.

            } else {
                System.out.println("\nProblem finding test scripts.\n");
            }

            String output = "\n\n***** Tests complete *****\n\n";
            System.out.print(output);
            logFile.write(output);
            logFile.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();

        }

    }//end method.

    public static void main(String[] args) {

        RunTestSuite pt = new RunTestSuite();
        pt.test();

    }//end main
}//end class.


