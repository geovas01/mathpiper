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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.mathpiper.Scripts;
import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.interpreters.Interpreter;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.io.InputStatus;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.parsers.MathPiperParser;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

public class AnalyzeScripts {

    private Scripts scripts;
    private Interpreter cas;
    private List uniqueValues = new ArrayList();

    public AnalyzeScripts() {
        scripts = new Scripts();//Scripts();
        cas = Interpreters.getSynchronousInterpreter();
        cas.evaluate("StackTraceOn()");

        cas.getEnvironment().saveDebugInformation = true;

    }

    public void findOperator(String functionOrOperatorName) throws Exception {
        Map scriptsMap = scripts.getMap();

        Collection values = scriptsMap.values();

        for (Object value : values) {

            if (!this.uniqueValues.contains(value)) {

                uniqueValues.add(value);

                String[] scriptCodeArray = (String[]) value;

                String scriptCode = scriptCodeArray[1];

                InputStatus inputStatus = new InputStatus("ANALYZESCRIPTS");

                inputStatus.setTo(scriptCodeArray[2]);

                StringInputStream stringInputStream = new StringInputStream(scriptCode, inputStatus);

                analyzeScript(cas.getEnvironment(), -1, stringInputStream, functionOrOperatorName, scriptCodeArray, false);
            }
        }
    }

    public static String analyzeScript(Environment aEnvironment, int aStackTop, MathPiperInputStream aInput, String functionOrOperatorName, String[] scriptCodeArray, boolean evaluate) throws Exception {

        StringBuffer printedScriptStringBuffer = new StringBuffer();

        MathPiperInputStream previous = aEnvironment.getCurrentInput();
        try {
            aEnvironment.setCurrentInput(aInput);

            String eof = "EndOfFile";

            boolean endoffile = false;

            MathPiperParser parser = new MathPiperParser(new MathPiperTokenizer(), aEnvironment.getCurrentInput(), aEnvironment,
                    aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators,
                    aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
            Cons readIn = null;



            while (!endoffile) {



                /*
                if(scriptCodeArray[2].contains("!"))
                {
                int xx = 1;
                }errorMessage
                 */

                // Read expression
                Object[] result = parser.parseAndFind(aStackTop, functionOrOperatorName);

                readIn = (Cons) result[0];

                if (readIn == null) {
                    LispError.throwError(aEnvironment, aStackTop, LispError.READING_FILE, "");
                }





                // check for end of file
                if (readIn.car() instanceof String && ((String) readIn.car()).equals(eof)) {
                    endoffile = true;
                } // Else process the list.
                else {


                    if (functionOrOperatorName.equals("<--")) {
                        //ViewList.showFrame(readIn);

                        if (((Cons) readIn.car()).car() instanceof String && ((String) ((Cons) readIn.car()).car()).equals("<--")) {
                            //ViewList.showFrame(readIn);
                            Cons cons = (Cons) Cons.cadar(readIn);
                            dumpRule(cons, scriptCodeArray, result);
                        } else if (true && ((Cons) readIn.car()).car() instanceof String && ((String) ((Cons) readIn.car()).car()).equals("LocalSymbols")) {
//ViewList.showFrame(readIn);

                            Cons prog = (Cons) readIn.car();
                            processLocalSymbols(prog, scriptCodeArray, result);

                        }//end else if


                    }
                    else
                    {
                	
                	List locationsList = (List) result[1];
                	
                	if(locationsList.size() != 0)
                	{
                	
                	System.out.println("\n" + scriptCodeArray[2]);
                	
                        ArrayList<Map> functionOrOperatorLocationsList = (ArrayList) result[1];

                        for (Map location : functionOrOperatorLocationsList) {

                            String operatorOrFunctionName = (String) location.get("operatorOrFunctionName");

                            int lineNumber = (Integer) location.get("lineNumber");

                            int lineIndex = (Integer) location.get("lineIndex");

                            if (scriptCodeArray[0] != null) {
                                lineNumber = lineNumber + Integer.parseInt(scriptCodeArray[0]);
                            }

                            System.out.println("    " + operatorOrFunctionName + " " + (lineNumber + 1) + ":" + lineIndex);
                        }//end for.*/
                        
                	}//end if.
                    }
                    
                    
                    


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
            //System.out.println(e.getMessage());
            //e.printStackTrace(); //todo:tk:uncomment for debugging.

            //EvaluationException ee = new EvaluationException(e.getMessage(), aEnvironment.getCurrentInput().iStatus.getFileName(), aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1, aEnvironment.getCurrentInput().iStatus.getLineNumber());

            String errorMessage = e.getMessage();
            
            if(e instanceof EvaluationException)
            {
        	EvaluationException evaluationException = (EvaluationException)e;
        	
        	errorMessage = errorMessage + aEnvironment.getCurrentInput().iStatus.getFileName() + ", Line: " + evaluationException.getLineNumber() + ", Start Index: " + evaluationException.getStartIndex();
            }            
            
            
            System.out.println(errorMessage);

        } finally {
            aEnvironment.setCurrentInput(previous);
        }
        
        return "";
    }//end method.

    private static void processLocalSymbols(Cons prog, String[] scriptCodeArray, Object[] result) throws Exception {
        //Scan past variables to the Block.
        while (prog.cdr() != null) {
            prog = prog.cdr();
        }

        prog = (Cons) prog.car();
        prog = (Cons) prog.cdr();



        while (prog != null) {
            if (!(prog.car() instanceof String)) {
                if (((Cons) prog.car()).car() instanceof String && ((String) ((Cons) prog.car()).car()).equals("<--")) {
                    //ViewList.showFrame(prog);
                    Cons cons2 = (Cons) Cons.cadar(prog);
                    dumpRule(cons2, scriptCodeArray, result);
                } else if (((Cons) prog.car()).car() instanceof String && ((String) ((Cons) prog.car()).car()).equals("LocalSymbols")) {
//ViewList.showFrame(readIn);

                    Cons prog2 = (Cons) prog.car();
                    processLocalSymbols(prog2, scriptCodeArray, result);

                }//end else if
            }//end if.

            prog = prog.cdr();





        }
    }

    private static void dumpRule(Cons cons, String[] scriptCodeArray, Object[] result) throws Exception {


        String string = "";

        if (((String) cons.car()).equals("#")) {
            Cons cons2 = (Cons) Cons.caddr(cons);

            string = (String) cons2.car();

            if (string.equals("_")) {
                Cons cons3 = (Cons) Cons.cadr(cons2);
                string = (String) cons3.car();
            }

        } else if (((String) cons.car()).equals("_")) {
            Cons cons2 = (Cons) Cons.cadr(cons);

            string = (String) cons2.car();



        } else {
            string = (String) cons.car();
        }

        String mpwFileInformation = scriptCodeArray[2];

        String defInformation = mpwFileInformation.split(",")[1].trim();

        if (!defInformation.contains(string)) {
            System.out.println(scriptCodeArray[2]);

            System.out.println(string);

            ArrayList<Map> functionOrOperatorLocationsList = (ArrayList) result[1];

            //for (Map location : functionOrOperatorLocationsList) {

                Map location = functionOrOperatorLocationsList.remove(0);

                String operatorOrFunctionName = (String) location.get("operatorOrFunctionName");

                int lineNumber = (Integer) location.get("lineNumber");

                int lineIndex = (Integer) location.get("lineIndex");

                if (scriptCodeArray[0] != null) {
                    lineNumber = lineNumber + Integer.parseInt(scriptCodeArray[0]);
                }

                System.out.println("    " + operatorOrFunctionName + " " + (lineNumber + 1) + ":" + lineIndex + "\n");
            //}//end for.
        }//end if.
    }







    public static void main(String[] args) {

        AnalyzeScripts analyze = new AnalyzeScripts();

        try {
            analyze.findOperator(":");
        } catch (Exception e) {
            //e.printStackTrace();
              
            
        }


    }//end main.
}//end class.

