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
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.parsers.MathPiperParser;

import org.mathpiper.lisp.rulebases.SingleArityRulebase;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

/**
 *  The basic evaluator for Lisp expressions.
 *
 */
public class LispExpressionEvaluator extends Evaluator {

    private Environment aEnvironment;
    private Object[] stackJS = new Object[10000];
    private int stackJSIndex = 0;
    private Map bfa = new HashMap();
    private final int LISP_EXPRESSION_EVALUATOR = 10;
    private final int BUILTIN_FUNCTION_EVALUATOR_A = 20;
    private final int BUILTIN_FUNCTION_EVALUATOR_B = 30;
    private final int SINGLE_ARITY_RULEBASE_EVALUATOR = 40;
    private final int PROG_A = 50;
    private final int PROG_B = 60;
    private final int PROG_C = 70;

    public LispExpressionEvaluator() {
        bfa.put("Prog", PROG_A);
    }

    public void evaluate(Environment aEnvironment, int aStackBase, Cons aExpression) throws Exception {
        this.aEnvironment = aEnvironment;
        stackJS[stackJSIndex++] = aStackBase;
        stackJS[stackJSIndex++] = aExpression;

        evaluate();
    }

    private void evaluate() throws Exception {
        boolean evaluate = true;



        int address = LISP_EXPRESSION_EVALUATOR;

        mainLoop:
        while (evaluate) {

            //if(stackJSIndex == 0)
            //{
            //    return;
            //}



            switch (address) {
                //===============================================================================================

                case LISP_EXPRESSION_EVALUATOR: {

                    Cons aExpression = (Cons) stackJS[--stackJSIndex];
                    int aStackBase = (Integer) stackJS[--stackJSIndex];

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
                    // evaluate an atom: find the bound value (treat it as a variable)
                    if (aExpression.car() instanceof String) {

                        String atomName = (String) aExpression.car();

                        if (atomName.charAt(0) == '\"') {
                            //Handle string atoms.
                            aEnvironment.iEvalDepth--;
                            BuiltinFunction.pushOnStack(aEnvironment, aStackBase, aExpression.copy(false));

                            if (Evaluator.NEW_EVALUATOR_ON == false || Evaluator.OLD_EVAL_ARGS == true || Evaluator.USER_FUNCTION_EVAL == true) {
                                return;
                            } else {
                                address = (Integer) stackJS[--stackJSIndex];

                                continue mainLoop;
                            }
                        }


                        if (Character.isDigit(atomName.charAt(0))) {
                            //Handle number atoms.
                            aEnvironment.iEvalDepth--;
                            BuiltinFunction.pushOnStack(aEnvironment, aStackBase, aExpression.copy(false));

                            if (Evaluator.NEW_EVALUATOR_ON == false || Evaluator.OLD_EVAL_ARGS == true || Evaluator.USER_FUNCTION_EVAL == true) {
                                return;
                            } else {
                                address = (Integer) stackJS[--stackJSIndex];

                                continue mainLoop;
                            }


                        }


                        Cons val = aEnvironment.getLocalOrGlobalVariable(aStackBase, atomName);
                        if (val != null) {
                            aEnvironment.iEvalDepth--;
                            BuiltinFunction.pushOnStack(aEnvironment, aStackBase, val.copy(false));


                            if (Evaluator.NEW_EVALUATOR_ON == false || Evaluator.OLD_EVAL_ARGS == true || Evaluator.USER_FUNCTION_EVAL == true) {
                                return;
                            } else {
                                address = (Integer) stackJS[--stackJSIndex];

                                continue mainLoop;
                            }
                        }



                        //Handle unbound variables.
                        aEnvironment.iEvalDepth--;
                        BuiltinFunction.pushOnStack(aEnvironment, aStackBase, aExpression.copy(false));

                        if (Evaluator.NEW_EVALUATOR_ON == false || Evaluator.OLD_EVAL_ARGS == true || Evaluator.USER_FUNCTION_EVAL == true) {
                            return;
                        } else {
                            address = (Integer) stackJS[--stackJSIndex];

                            continue mainLoop;
                        }


                    }


                    Cons functionAndArgumentsList = (Cons) aExpression.car();
                    Cons head = functionAndArgumentsList;


                    String functionName;

                    if (head.car() instanceof String) {

                        functionName = (String) head.car();



                        //Built-in function handler.
                        BuiltinFunctionEvaluator builtinFunction = (BuiltinFunctionEvaluator) aEnvironment.iBuiltinFunctions.lookUp(functionName);
                        if (builtinFunction != null) {

                            aEnvironment.iEvalDepth--;

                            stackJS[stackJSIndex++] = functionAndArgumentsList;
                            stackJS[stackJSIndex++] = aStackBase;
                            stackJS[stackJSIndex++] = builtinFunction;

                            //builtinInFunctionEvaluator.evaluate(aEnvironment, aStackBase, functionAndArgumentsList);

                            //return;

                            address = BUILTIN_FUNCTION_EVALUATOR_A;

                            continue mainLoop;
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

                            Evaluator.USER_FUNCTION_EVAL = true;

                            userFunction.evaluate(aEnvironment, aStackBase, functionAndArgumentsList);

                            Evaluator.USER_FUNCTION_EVAL = false;

                            if (Evaluator.NEW_EVALUATOR_ON == false || Evaluator.OLD_EVAL_ARGS == true || Evaluator.USER_FUNCTION_EVAL == true) {
                                return;
                            } else {
                                address = (Integer) stackJS[--stackJSIndex];

                                continue mainLoop;
                            }
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


                        if (Evaluator.NEW_EVALUATOR_ON == false || Evaluator.OLD_EVAL_ARGS == true || Evaluator.USER_FUNCTION_EVAL == true) {
                            return;
                        } else {
                            address = (Integer) stackJS[--stackJSIndex];

                            continue mainLoop;
                        }
                    }






                    aEnvironment.iEvalDepth--;


                    if (Evaluator.NEW_EVALUATOR_ON == false || Evaluator.OLD_EVAL_ARGS == true || Evaluator.USER_FUNCTION_EVAL == true) {
                        return;
                    } else {
                        address = (Integer) stackJS[--stackJSIndex];

                        continue mainLoop;
                    }

                }
                //break;

                //===============================================================================================

                case BUILTIN_FUNCTION_EVALUATOR_A: {
                    BuiltinFunctionEvaluator builtInFunctionEvaluator = (BuiltinFunctionEvaluator) stackJS[--stackJSIndex];
                    int aStackBase = (Integer) stackJS[--stackJSIndex];
                    Cons aArguments = (Cons) stackJS[--stackJSIndex];


                    Cons[] argumentsResultArray = null;


                    Cons arguments = null;

                    String functionName = builtInFunctionEvaluator.iCalledBuiltinFunction.functionName;





                    if ((builtInFunctionEvaluator.iFlags & builtInFunctionEvaluator.Variable) == 0) { //This function has a fixed number of arguments.

                        //1 is being added to the number of arguments to take into account
                        // the function name that is at the beginning of the argument list.

                        arguments = SublistCons.getInstance(aEnvironment, aArguments);


                        LispError.checkNumberOfArguments(aStackBase, builtInFunctionEvaluator.iNumberOfArguments + 1, aArguments, aEnvironment);
                    }

                    int oldStackTop = aEnvironment.iArgumentStack.getStackTopIndex();

                    // Push a place holder for the result and initialize it to the function name for error reporting purposes.
                    aEnvironment.iArgumentStack.pushArgumentOnStack(aArguments, aStackBase, aEnvironment);

                    Cons argumentsConsTraverser = aArguments;

                    //Strip the function name from the head of the list.
                    argumentsConsTraverser = argumentsConsTraverser.cdr();

                    int i;
                    int numberOfArguments = builtInFunctionEvaluator.iNumberOfArguments;

                    if ((builtInFunctionEvaluator.iFlags & builtInFunctionEvaluator.Variable) != 0) {//This function has a variable number of arguments.
                        numberOfArguments--;
                    }//end if.

                    Cons argumentResult;

                    // Walk over all arguments, evaluating them only if this is a function. *****************************************************

                    if ((builtInFunctionEvaluator.iFlags & builtInFunctionEvaluator.Macro) != 0) {//This is a macro, not a function. Don't evaluate arguments.

                        for (i = 0; i < numberOfArguments; i++) {
                            //Push all arguments on the stack.
                            if (argumentsConsTraverser == null) {
                                LispError.throwError(aEnvironment, aStackBase, LispError.WRONG_NUMBER_OF_ARGUMENTS, "The number of arguments passed in was " + numberOfArguments);
                            }


                            aEnvironment.iArgumentStack.pushArgumentOnStack(argumentsConsTraverser.copy(false), aStackBase, aEnvironment);

                            argumentsConsTraverser = argumentsConsTraverser.cdr();
                        }

                        if ((builtInFunctionEvaluator.iFlags & builtInFunctionEvaluator.Variable) != 0) {//This macro has a variable number of arguments.
                            Cons head3 = aEnvironment.iListAtom.copy(false);
                            head3.setCdr(argumentsConsTraverser);
                            aEnvironment.iArgumentStack.pushArgumentOnStack(SublistCons.getInstance(aEnvironment, head3), aStackBase, aEnvironment);
                        }//end if.

                    } else {//This is a function, not a macro. Evaluate arguments.

                        for (i = 0; i < numberOfArguments; i++) {

                            if (argumentsConsTraverser == null) {
                                LispError.throwError(aEnvironment, aStackBase, LispError.WRONG_NUMBER_OF_ARGUMENTS, "The number of arguments passed in was " + numberOfArguments);
                            }

                            int oldStackTopArgs = aEnvironment.iArgumentStack.getStackTopIndex();
                            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackBase, argumentsConsTraverser);
                            argumentResult = aEnvironment.iArgumentStack.getElement(oldStackTopArgs, aStackBase, aEnvironment);
                            aEnvironment.iArgumentStack.popTo(oldStackTopArgs, aStackBase, aEnvironment);


                            aEnvironment.iArgumentStack.pushArgumentOnStack(argumentResult, aStackBase, aEnvironment);

                            argumentsConsTraverser = argumentsConsTraverser.cdr();
                        }//end for.

                        if ((builtInFunctionEvaluator.iFlags & builtInFunctionEvaluator.Variable) != 0) {//This function has a variable number of arguments.

                            Cons head4 = aEnvironment.iListAtom.copy(false);
                            head4.setCdr(argumentsConsTraverser);
                            Cons list = SublistCons.getInstance(aEnvironment, head4);


                            int oldStackTopArgs = aEnvironment.iArgumentStack.getStackTopIndex();
                            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackBase, list);
                            argumentResult = aEnvironment.iArgumentStack.getElement(oldStackTopArgs, aStackBase, aEnvironment);
                            aEnvironment.iArgumentStack.popTo(oldStackTopArgs, aStackBase, aEnvironment);


                            aEnvironment.iArgumentStack.pushArgumentOnStack(argumentResult, aStackBase, aEnvironment);


                        }//end if.
                    }//end else.






                    //============= switch-based built-in function handler.
                    if (Evaluator.NEW_EVALUATOR_ON == true && bfa.containsKey(functionName)) {
                        stackJS[stackJSIndex++] = BUILTIN_FUNCTION_EVALUATOR_B;
                        stackJS[stackJSIndex++] = oldStackTop;
                        address = (Integer) bfa.get(functionName);
                        continue mainLoop;
                    }
                    //============= end switch-based built-in function handler.


                    builtInFunctionEvaluator.iCalledBuiltinFunction.evaluate(aEnvironment, oldStackTop); //********************** built in function is called here.





                    if (true) {
                        return;
                    }

                }
                break;

                //===============================================================================================

                case BUILTIN_FUNCTION_EVALUATOR_B: {

                    if (true) {
                        return;
                    }

                }
                break;


                case SINGLE_ARITY_RULEBASE_EVALUATOR: {

                    if (true) {
                        return;
                    }

                }
                break;

                //===============================================================================================

                case PROG_A: {



                    int aStackBase = (Integer) stackJS[--stackJSIndex];

                    // Allow accessing previous locals.
                    aEnvironment.pushLocalFrame(false, "Prog");


                    // Evaluate args one by one.
                    Cons consTraverser = (Cons) BuiltinFunction.getArgument(aEnvironment, aStackBase, 1).car();
                    consTraverser = consTraverser.cdr();

                    if (consTraverser == null) {
                        BuiltinFunction.setTopOfStack(aEnvironment, aStackBase, Utility.getTrueAtom(aEnvironment));

                        address = (Integer) stackJS[--stackJSIndex];
                        continue mainLoop;
                    }

                    stackJS[stackJSIndex++] = aStackBase;
                    stackJS[stackJSIndex++] = consTraverser;

                    address = PROG_B;

                }
                break;

                //===============================================================================================

                case PROG_B: {

                    Cons consTraverser = (Cons) stackJS[--stackJSIndex];
                    int aStackBase = (Integer) stackJS[--stackJSIndex];


                    if (consTraverser != null) {
                        int oldStackTop = aEnvironment.iArgumentStack.getStackTopIndex();

                        stackJS[stackJSIndex++] = oldStackTop;
                        stackJS[stackJSIndex++] = aStackBase;
                        stackJS[stackJSIndex++] = consTraverser;
                        stackJS[stackJSIndex++] = PROG_C;

                        stackJS[stackJSIndex++] = aStackBase;
                        stackJS[stackJSIndex++] = consTraverser;



                        address = LISP_EXPRESSION_EVALUATOR;
                        continue mainLoop;
                    }



                    address = LISP_EXPRESSION_EVALUATOR;


                }
                break;

                //===============================================================================================

                case PROG_C: {
                    Cons consTraverser = (Cons) stackJS[--stackJSIndex];
                    int aStackBase = (Integer) stackJS[--stackJSIndex];
                    int oldStackTop = (Integer) stackJS[--stackJSIndex];

                    Cons result = aEnvironment.iArgumentStack.getElement(oldStackTop, aStackBase, aEnvironment);
                    aEnvironment.iArgumentStack.popTo(oldStackTop, aStackBase, aEnvironment);

                    consTraverser = consTraverser.cdr();
                    if (consTraverser != null) {
                        stackJS[stackJSIndex++] = aStackBase;
                        stackJS[stackJSIndex++] = consTraverser;

                        address = PROG_B;
                        continue mainLoop;

                    }

                    BuiltinFunction.setTopOfStack(aEnvironment, aStackBase, result);

                    aEnvironment.popLocalFrame(aStackBase);

                    address = (Integer) stackJS[--stackJSIndex];
                }
                break;

            }//end switch.

        }//end while

    }//end evaluate.
}//end class.

