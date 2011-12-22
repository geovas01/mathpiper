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
package org.mathpiper.lisp;

import java.util.HashMap;
import java.util.Map;
import org.mathpiper.Scripts;
import org.mathpiper.builtin.BuiltinFunction;

import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.io.StringInputStream;
import org.mathpiper.lisp.parsers.MathPiperParser;

import org.mathpiper.lisp.rulebases.SingleArityRulebase;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

/**
 *  The basic evaluator for Lisp expressions.
 *
 */
public class LispExpressionEvaluator extends Evaluator{

    private Map bfa = new HashMap();
    private final int PROG = 10;

    public LispExpressionEvaluator() {
        bfa.put("Prog", PROG);

    }

    public void evaluate(Environment aEnvironment, int aStackBase, Cons aExpression) throws Exception {

        boolean evaluate = true;
        int address = 1;

        while (evaluate) {


            if (aExpression == null) {
                LispError.lispAssert(aEnvironment, aStackBase);
            }

            aEnvironment.iEvalDepth++;
            if (aEnvironment.iEvalDepth >= aEnvironment.iMaxEvalDepth) {

                if (aEnvironment.iEvalDepth >= aEnvironment.iMaxEvalDepth) {
                    LispError.throwError(aEnvironment, aStackBase, LispError.MAXIMUM_RECURSE_DEPTH_REACHED, "Maximum recursed depth set to " + aEnvironment.iMaxEvalDepth + ".");
                }

            }

            if (Environment.haltEvaluation == true) {
                Environment.haltEvaluation = false;

                LispError.raiseError("User halted calculation.", aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1, aEnvironment.getCurrentInput().iStatus.getLineIndex(), aStackBase, aEnvironment);
            }





            switch (address) {
                case 1:


                    // evaluate an atom: find the bound value (treat it as a variable)
                    if (aExpression.car() instanceof String) {

                        String atomName = (String) aExpression.car();

                        if (atomName.charAt(0) == '\"') {
                            //Handle string atoms.
                            aEnvironment.iEvalDepth--;
                            BuiltinFunction.pushOnStack(aEnvironment,aStackBase, aExpression.copy(false));
                            return;
                        }


                        if (Character.isDigit(atomName.charAt(0))) {
                            //Handle number atoms.
                            aEnvironment.iEvalDepth--;
                            BuiltinFunction.pushOnStack(aEnvironment, aStackBase, aExpression.copy(false));
                            return;
                        }


                        Cons val = aEnvironment.getLocalOrGlobalVariable(aStackBase, atomName);
                        if (val != null) {
                            aEnvironment.iEvalDepth--;
                            BuiltinFunction.pushOnStack(aEnvironment, aStackBase, val.copy(false));
                            return ;
                        }



                        //Handle unbound variables.

                        aEnvironment.iEvalDepth--;
                        BuiltinFunction.pushOnStack(aEnvironment, aStackBase, aExpression.copy(false));
                        return;


                    }


                    if (aExpression.car() instanceof Cons) {
                        Cons functionAndArgumentsList = (Cons) aExpression.car();
                        Cons head = functionAndArgumentsList;
                        if (head != null) {

                            String functionName;

                            if (head.car() instanceof String) {

                                functionName = (String) head.car();



                                //============= switch-based built-in function handler.
                               // if (bfa.containsKey(functionName)) {
                               //     address = (Integer) bfa.get(functionName);
                               //     break;
                               // }



                                //============= end switch-based built-in function handler.

                                //Built-in function handler.
                                BuiltinFunctionEvaluator builtinInFunctionEvaluator = (BuiltinFunctionEvaluator) aEnvironment.iBuiltinFunctions.lookUp(functionName);
                                if (builtinInFunctionEvaluator != null) {

                                    aEnvironment.iEvalDepth--;
                                    builtinInFunctionEvaluator.evaluate(aEnvironment, aStackBase, functionAndArgumentsList);
                                    return;
                                }


                                //User function handler.
                                Cons head2 = functionAndArgumentsList;

                                if (!(head2.car() instanceof String)) {
                                    LispError.throwError(aEnvironment, aStackBase, "No function name specified.");
                                }

                                SingleArityRulebase userFunction = (SingleArityRulebase) aEnvironment.getRulebase(aStackBase, functionAndArgumentsList);


                                if (userFunction == null) {

                                    //=============== Load library function.
                                    Scripts scripts = aEnvironment.scripts;

                                    String[] scriptCode = scripts.getScript(functionName);


                                    if (scriptCode == null) {
                                        LispError.throwError(aEnvironment, aStackBase, "No script returned for function: " + functionName + " from Scripts.java.");
                                    }

                                    if (scriptCode[0] == null) {

                                        if (scriptCode[1] == null) {
                                            LispError.throwError(aEnvironment, aStackBase, "No script returned for function: " + functionName + " from Scripts.java.");
                                        }



                                        String scriptString = scriptCode[1];



                                        StringInputStream functionInputStream = new StringInputStream(scriptString, aEnvironment.getCurrentInput().iStatus);

                                        scriptCode[0] = "+";

                                        // ============= Do internal load.
                                        MathPiperInputStream previous = aEnvironment.getCurrentInput();
                                        try {
                                            aEnvironment.setCurrentInput(functionInputStream);

                                            String eof = "EndOfFile";
                                            boolean endoffile = false;

                                            MathPiperParser parser = new MathPiperParser(new MathPiperTokenizer(), aEnvironment.getCurrentInput(), aEnvironment, aEnvironment.iPrefixOperators, aEnvironment.iInfixOperators, aEnvironment.iPostfixOperators, aEnvironment.iBodiedOperators);
                                            //Parser parser = new Parser(new MathPiperTokenizer(), aEnvironment.iCurrentInput, aEnvironment);

                                            while (!endoffile) {
                                                // Read expression
                                                Cons readIn = parser.parse(aStackBase);

                                                if (readIn == null) {
                                                    LispError.throwError(aEnvironment, aStackBase, LispError.READING_FILE, "");
                                                }
                                                // check for end of file
                                                if (readIn.car() instanceof String && ((String) readIn.car()).equals(eof)) {
                                                    endoffile = true;
                                                } // Else evaluate
                                                else {

                                                    int oldStackTop = aEnvironment.iArgumentStack.getStackTopIndex();
                                                    aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackBase, readIn);
                                                    Cons result = aEnvironment.iArgumentStack.getElement(oldStackTop, aStackBase, aEnvironment);
                                                    aEnvironment.iArgumentStack.popTo(oldStackTop, aStackBase, aEnvironment);
                                                    
                                                    if (aStackBase != -1) {
                                                        aEnvironment.setLocalOrGlobalVariable(aStackBase, "$LoadResult", result, false);//Note:tk:added to make the result of executing Loaded code available.
                                                    }
                                                }
                                            }//end while.



                                        } catch (Exception e) {
                                            //System.out.println(e.getMessage()); e.printStackTrace(); //todo:tk:uncomment for debugging.

                                            throw e;
                                        } finally {
                                            aEnvironment.setCurrentInput(previous);
                                        }

                                        // ============ end do internal load.


                                    }

                                    //=============== end Load library function

                                    userFunction = (SingleArityRulebase) aEnvironment.getRulebase(aStackBase, functionAndArgumentsList);

                                }


                                if (userFunction != null) {

                                    aEnvironment.iEvalDepth--;
                                    userFunction.evaluate(aEnvironment, aStackBase, functionAndArgumentsList);
                                    return;
                                }


                                if (functionName.equals("FreeOf?")) {

                                    aEnvironment.iEvalDepth--;

                                    Cons result = Utility.returnUnEvaluated(aStackBase, functionAndArgumentsList, aEnvironment);

                                    BuiltinFunction.pushOnStack(aEnvironment, aStackBase, result);

                                    return;
                                }
                                Map metaDataMap = functionAndArgumentsList.getMetadataMap();

                                int lineNumber = aEnvironment.getCurrentInput().iStatus.getLineNumber();
                                int startIndex = -1;
                                int endIndex = aEnvironment.getCurrentInput().iStatus.getLineIndex();

                                if (metaDataMap != null) {
                                    lineNumber = (Integer) metaDataMap.get("lineNumber");
                                    startIndex = (Integer) metaDataMap.get("startIndex");
                                    endIndex = (Integer) metaDataMap.get("endIndex");
                                }


                                LispError.raiseError("Problem with function ***(" + functionName + ")***, <wrong code: " + Utility.printLispExpression(-1, functionAndArgumentsList, aEnvironment, 50) + ">, <the " + (Utility.listLength(aEnvironment, aStackBase, functionAndArgumentsList) - 1) + " parameter version of this function is not defined (MAKE SURE THE FUNCTION IS SPELLED CORRECTLY).>", lineNumber, startIndex, endIndex, aStackBase, aEnvironment);


                            } else {
                                //Pure function handler.
                                Cons operator = functionAndArgumentsList;
                                Cons args2 = functionAndArgumentsList.cdr();

                                aEnvironment.iEvalDepth--;
                                Utility.applyPure(aStackBase, operator, args2, aEnvironment);
                                return;
                            }


                        }
                    }


                    aEnvironment.iEvalDepth--;


                    break;

                case PROG:
                    // Allow accessing previous locals.
                    aEnvironment.pushLocalFrame(false, "Prog");

                    try {

                        Cons result = Utility.getTrueAtom(aEnvironment);

                        // Evaluate args one by one.
                        Cons consTraverser = (Cons) BuiltinFunction.getArgument(aEnvironment, aStackBase, 1).car();
                        consTraverser = consTraverser.cdr();
                        while (consTraverser != null) {
                            int oldStackTop = aEnvironment.iArgumentStack.getStackTopIndex();
                            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackBase, consTraverser);
                            result = aEnvironment.iArgumentStack.getElement(oldStackTop, aStackBase, aEnvironment);
                            aEnvironment.iArgumentStack.popTo(oldStackTop, aStackBase, aEnvironment);

                            consTraverser = consTraverser.cdr();
                        }

                        BuiltinFunction.setTopOfStack(aEnvironment, aStackBase, result);

                    } catch (Exception e) {
                        throw e;
                    } finally {
                        aEnvironment.popLocalFrame(aStackBase);
                    }

                    address = 1;

                    break;
            }//end switch.

        }//end while

    }
}//end class.

