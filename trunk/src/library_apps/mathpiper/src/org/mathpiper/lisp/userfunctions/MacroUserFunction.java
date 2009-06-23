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

        this.functionType = "macro";
    }

    public void evaluate(Environment aEnvironment, ConsPointer aResult, ConsPointer aArgumentsPointer) throws Exception {
         int arity = arity();
        ConsPointer[] argumentsResultPointerArray = evaluateArguments(aEnvironment, aArgumentsPointer);
        int parameterIndex;

       

        ConsPointer substitutedBodyPointer = new ConsPointer();

        //Create a new local variable frame that is unfenced (false = unfenced).
        aEnvironment.pushLocalFrame(false);

        try {
            // define the local variables.
            for (parameterIndex = 0; parameterIndex < arity; parameterIndex++) {
                String variable = ((FunctionParameter) iParameters.get(parameterIndex)).iParameter;

                // setCons the variable to the new value
                aEnvironment.newLocalVariable(variable, argumentsResultPointerArray[parameterIndex].getCons());
            }

            // walk the rules database, returning the evaluated result if the
            // predicate is true.
            int numberOfRules = iBranchRules.size();
            UserStackInformation userStackInformation = aEnvironment.iLispExpressionEvaluator.stackInformation();
            for (parameterIndex = 0; parameterIndex < numberOfRules; parameterIndex++) {
                Branch thisRule = ((Branch) iBranchRules.get(parameterIndex));
                //TODO remove            CHECKPTR(thisRule);
                LispError.lispAssert(thisRule != null);

                userStackInformation.iRulePrecedence = thisRule.getPrecedence();

                boolean matches = thisRule.matches(aEnvironment, argumentsResultPointerArray);

                if (matches) {
                    /* Rule dump trace code. */
                    if (isTraced() && showFlag) {
                        ConsPointer argumentsPointer = new ConsPointer();
                        argumentsPointer.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
                        String ruleDump = org.mathpiper.lisp.UtilityFunctions.dumpRule(thisRule, aEnvironment, this);
                        Evaluator.traceShowRule(aEnvironment, argumentsPointer, ruleDump);
                    }
                    userStackInformation.iSide = 1;

                    BackQuoteSubstitute backQuoteSubstitute = new BackQuoteSubstitute(aEnvironment);

                    ConsPointer originalBodyPointer =  thisRule.getBodyPointer();
                    UtilityFunctions.substitute(substitutedBodyPointer,originalBodyPointer, backQuoteSubstitute);
                    //              aEnvironment.iLispExpressionEvaluator.Eval(aEnvironment, aResult, thisRule.body());
                    break;
                }

                // If rules got inserted, walk back
                while (thisRule != ((Branch) iBranchRules.get(parameterIndex)) && parameterIndex > 0) {
                    parameterIndex--;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            aEnvironment.popLocalFrame();
        }



        if (substitutedBodyPointer.getCons() != null) {
            //Note:tk:substituted body must be evaluated after the local frame has been popped.
            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aResult, substitutedBodyPointer);
        } else // No predicate was true: return a new expression with the evaluated
        // arguments.
        {
            ConsPointer full = new ConsPointer();
            full.setCons(aArgumentsPointer.getCons().copy(false));
            if (arity == 0) {
                full.getCons().getRestPointer().setCons(null);
            } else {
                full.getCons().getRestPointer().setCons(argumentsResultPointerArray[0].getCons());
                for (parameterIndex = 0; parameterIndex < arity - 1; parameterIndex++) {
                    argumentsResultPointerArray[parameterIndex].getCons().getRestPointer().setCons(argumentsResultPointerArray[parameterIndex + 1].getCons());
                }
            }
            aResult.setCons(SubListCons.getInstance(full.getCons()));
        }
        //FINISH:

        /*Leave trace code */
        if (isTraced() && showFlag) {
            ConsPointer tr = new ConsPointer();
            tr.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
            LispExpressionEvaluator.traceShowLeave(aEnvironment, aResult, tr, "macro");
            tr.setCons(null);
        }

    }
}


