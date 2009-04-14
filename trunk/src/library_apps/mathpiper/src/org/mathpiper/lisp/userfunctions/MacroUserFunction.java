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
package org.mathpiper.lisp.userfunctions;

import org.mathpiper.lisp.stacks.UserStackInformation;
import org.mathpiper.lisp.behaviours.BackQuoteSubstitute;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Evaluator;
import org.mathpiper.lisp.LispExpressionEvaluator;
import org.mathpiper.lisp.cons.SubListCons;

public class MacroUserFunction extends SingleArityBranchingUserFunction {

    public MacroUserFunction(ConsPointer aParameters) throws Exception {
        super(aParameters);
        ConsTraverser parameterTraverser = new ConsTraverser(aParameters);
        int i = 0;
        while (parameterTraverser.getCons() != null) {
            LispError.check(parameterTraverser.getCons().string() != null, LispError.KLispErrCreatingUserFunction);
            ((FunctionParameter) iParameters.get(i)).iHold = true;
            parameterTraverser.goNext();
            i++;
        }
	//Macros are all unfenced.
        unFence();
    }

    public void evaluate(Environment aEnvironment, ConsPointer aResult, ConsPointer aArgumentsPointer) throws Exception {
        int arity = arity();
        int i;

        /*Enter trace code*/
        if (isTraced()) {
            ConsPointer tr = new ConsPointer();
            tr.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
            LispExpressionEvaluator.traceShowEnter(aEnvironment, tr, "macro");
            tr.setCons(null);
        }

        ConsTraverser argumentsTraverser = new ConsTraverser(aArgumentsPointer);

        //Strip the word "Function" from the head of the list.
        argumentsTraverser.goNext();

        //Creat an array which holds pointers to each argument.
        ConsPointer[] argumentsResultPointerArray;
        if (arity == 0) {
            argumentsResultPointerArray = null;
        } else {
            LispError.lispAssert(arity > 0);
            argumentsResultPointerArray = new ConsPointer[arity];
        }

         // Walk over all arguments, evaluating them as necessary ********************************************************
        for (i = 0; i < arity; i++) {
            argumentsResultPointerArray[i] = new ConsPointer();

            LispError.check(argumentsTraverser.getCons() != null, LispError.KLispErrWrongNumberOfArgs);

            if (((FunctionParameter) iParameters.get(i)).iHold) {
                //If the parameter is on hold, don't evaluate it and place a copy of it in argumentsPointerArray.
                argumentsResultPointerArray[i].setCons(argumentsTraverser.getCons().copy(false));
            } else {
                //If the parameter is not on hold:

                //Verify that the pointer to the arguments is not null.
                LispError.check(argumentsTraverser.getPointer() != null, LispError.KLispErrWrongNumberOfArgs);

                //Evaluate each argument and place the result into argumentsResultPointerArray[i];
                aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, argumentsResultPointerArray[i], argumentsTraverser.getPointer());
            }
            argumentsTraverser.goNext();
        }

        /*Argument trace code */
        if (isTraced()) {
            //ConsTraverser consTraverser2 = new ConsTraverser(aArguments);
            ConsPointer traceArgumentPointer = new ConsPointer(aArgumentsPointer.getCons());

            traceArgumentPointer.goNext();
            for (i = 0; i < arity; i++) {
                Evaluator.traceShowArg(aEnvironment, traceArgumentPointer, argumentsResultPointerArray[i]);

                traceArgumentPointer.goNext();
            }//end if.
        }//end if.

        ConsPointer substedBody = new ConsPointer();
        {
            // declare a new local stack.
            aEnvironment.pushLocalFrame(false);
            try {
                // define the local variables.
                for (i = 0; i < arity; i++) {
                    String variable = ((FunctionParameter) iParameters.get(i)).iParameter;
                    // setCons the variable to the new value
                    aEnvironment.newLocalVariable(variable, argumentsResultPointerArray[i].getCons());
                }

                // walk the rules database, returning the evaluated result if the
                // predicate is true.
                int nrRules = iBranchRules.size();
                UserStackInformation st = aEnvironment.iLispExpressionEvaluator.stackInformation();
                for (i = 0; i < nrRules; i++) {
                    Branch thisRule = ((Branch) iBranchRules.get(i));
                    //TODO remove            CHECKPTR(thisRule);
                    LispError.lispAssert(thisRule != null);

                    st.iRulePrecedence = thisRule.getPrecedence();
                    boolean matches = thisRule.matches(aEnvironment, argumentsResultPointerArray);
                    if (matches) {
                        /* Rule dump trace code. */
                        if (isTraced()) {
                            ConsPointer argumentsPointer = new ConsPointer();
                            argumentsPointer.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
                            String ruleDump = org.mathpiper.lisp.UtilityFunctions.dumpRule(thisRule, aEnvironment, this);
                            Evaluator.traceShowRule(aEnvironment, argumentsPointer, ruleDump);
                        }
                        st.iSide = 1;

                        BackQuoteSubstitute behaviour = new BackQuoteSubstitute(aEnvironment);
                        UtilityFunctions.substitute(substedBody, thisRule.getBodyPointer(), behaviour);
                        //              aEnvironment.iLispExpressionEvaluator.Eval(aEnvironment, aResult, thisRule.body());
                        break;
                    }

                    // If rules got inserted, walk back
                    while (thisRule != ((Branch) iBranchRules.get(i)) && i > 0) {
                        i--;
                    }
                }
            } catch (Exception e) {
                throw e;
            } finally {
                aEnvironment.popLocalFrame();
            }
        }


        if (substedBody.getCons() != null) {
            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aResult, substedBody);
        } else // No predicate was true: return a new expression with the evaluated
        // arguments.
        {
            ConsPointer full = new ConsPointer();
            full.setCons(aArgumentsPointer.getCons().copy(false));
            if (arity == 0) {
                full.getCons().getRestPointer().setCons(null);
            } else {
                full.getCons().getRestPointer().setCons(argumentsResultPointerArray[0].getCons());
                for (i = 0; i < arity - 1; i++) {
                    argumentsResultPointerArray[i].getCons().getRestPointer().setCons(argumentsResultPointerArray[i + 1].getCons());
                }
            }
            aResult.setCons(SubListCons.getInstance(full.getCons()));
        }
        //FINISH:

        /*Trace code */
        if (isTraced()) {
            ConsPointer tr = new ConsPointer();
            tr.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
            LispExpressionEvaluator.traceShowLeave(aEnvironment, aResult, tr, "macro");
            tr.setCons(null);
        }

    }
}


