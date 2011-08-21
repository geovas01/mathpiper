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
package org.mathpiper.test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import org.mathpiper.Scripts;
import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.io.InputStatus;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

public class AnalyzeScripts {

    private Scripts scripts;
    private Interpreter cas;

    public AnalyzeScripts() {
        scripts = new Scripts();
        cas = Interpreters.getSynchronousInterpreter();

    }

    public void findOperator(String functionOrOperatorName) throws Exception {
        Map scriptsMap = scripts.getMap();

        Set<String> functionNames = scriptsMap.keySet();

        for (String name : functionNames) {

      //name = "PermutationsList";
            String[] scriptCodeArray = (String[]) scriptsMap.get(name);

            String scriptCode = scriptCodeArray[1];

            InputStatus inputStatus = new InputStatus();
            inputStatus.setTo(scriptCodeArray[2]);

            StringInputStream stringInputStream = new StringInputStream(scriptCode, inputStatus);

            System.out.println(inputStatus.getFileName());

            analyzeScript(cas.getEnvironment(), -1, stringInputStream, functionOrOperatorName, false);
        }
    }

    public static String analyzeScript(Environment aEnvironment, int aStackTop, MathPiperInputStream aInput, String functionOrOperatorName, boolean evaluate) throws Exception {

        StringBuffer printedScriptStringBuffer = new StringBuffer();

        MathPiperInputStream previous = aEnvironment.iCurrentInput;
        try {
            aEnvironment.iCurrentInput = aInput;

            String eof = (String) aEnvironment.getTokenHash().lookUp("EndOfFile");

            boolean endoffile = false;

            MathPiperParser parser = new MathPiperParser(new MathPiperTokenizer(),
                    aEnvironment.iCurrentInput, aEnvironment,
                    aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators,
                    aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
            ConsPointer readIn = new ConsPointer();

            while (!endoffile) {
                // Read expression
                ArrayList<String> functionOrOperatorLocationsList = parser.parseAndFind(aStackTop, readIn, "=");

                for(String location: functionOrOperatorLocationsList)
                {
                    System.out.println("    " + location);
                }

                LispError.check(aEnvironment, aStackTop, readIn.getCons() != null, LispError.READING_FILE, "", "INTERNAL");
                // check for end of file
                if (readIn.car() instanceof String && ((String) readIn.car()).equals(eof)) {
                    endoffile = true;
                } // Else print and maybe evaluate
                else {
                    //printExpression(printedScriptStringBuffer, aEnvironment, readIn);

                    /*
                     if (evaluate == true) {
                        ConsPointer result = new ConsPointer();
                        aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, result, readIn);
                    }  
                     */


                }
            }//end while.

            return printedScriptStringBuffer.toString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(); //todo:tk:uncomment for debugging.

            EvaluationException ee = new EvaluationException(e.getMessage(), aEnvironment.iCurrentInput.iStatus.getFileName(), aEnvironment.iCurrentInput.iStatus.getLineNumber(), aEnvironment.iCurrentInput.iStatus.getLineNumber());
            throw ee;
        } finally {
            aEnvironment.iCurrentInput = previous;
        }
    }

    public static void main(String[] args) {

        AnalyzeScripts analyze = new AnalyzeScripts();

        try {
            analyze.findOperator("=");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//end main.
}//end class.

