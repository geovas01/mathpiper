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
package org.mathpiper.lisp.rulebases;

import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.lisp.stacks.UserStackInformation;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Evaluator;
import org.mathpiper.lisp.LispExpressionEvaluator;
import org.mathpiper.lisp.astprocessors.BackQuoteSubstitute;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

public class MacroRulebase extends SingleArityRulebase {

    public MacroRulebase(Environment aEnvironment, int aStackTop, Cons aParameters, String functionName) throws Throwable {
        super(aEnvironment, aStackTop, aParameters, functionName);
        Cons parameterTraverser =  aParameters;
        int i = 0;
        while (parameterTraverser != null) {

            //LispError.check(parameterTraverser.car() != null, LispError.CREATING_USER_FUNCTION);
            try {
                if(! (parameterTraverser.car() instanceof String)) LispError.throwError(aEnvironment, aStackTop, LispError.CREATING_USER_FUNCTION, "");
            } catch (EvaluationException ex) {
                if (ex.getFunctionName() == null) {
                    throw new EvaluationException(ex.getMessage() + " In function: " + this.functionName + ",  ", "none", -1,-1,-1, this.functionName);
                } else {
                    throw ex;
                }
            }//end catch.


            ((ParameterName) iParameters.get(i)).iHold = true;
            parameterTraverser = parameterTraverser.cdr();
            i++;
        }
        //Macros are all unfenced.
        unFence();

        this.functionType = "macro";
    }


    @Override
    public Cons evaluate(Environment aEnvironment, int aStackTop, Cons aArguments) throws Throwable {
        Cons aResult;
        int arity = arity();
        Cons[] argumentsResultArray = evaluateArguments(aEnvironment, aStackTop, aArguments);



        Cons substitutedBody = null;
        //Create a new local variable frame that is unfenced (false = unfenced).
        aEnvironment.pushLocalFrame(false, this.functionName);

        try {
            // define the local variables.
            for (int parameterIndex = 0; parameterIndex < arity; parameterIndex++) {
                String variable = ((ParameterName) iParameters.get(parameterIndex)).iName;

                // set the variable to the new value
                aEnvironment.newLocalVariable(variable, argumentsResultArray[parameterIndex], aStackTop);
            }

            // walk the rules database, returning the evaluated result if the
            // predicate is true.
            int numberOfRules = iBranchRules.size();
            UserStackInformation userStackInformation = aEnvironment.iLispExpressionEvaluator.stackInformation();
            for (int ruleIndex = 0; ruleIndex < numberOfRules; ruleIndex++) {
                Rule thisRule = ((Rule) iBranchRules.get(ruleIndex));
                //TODO remove            CHECKPTR(thisRule);
                if(thisRule == null) LispError.lispAssert(aEnvironment, aStackTop);

                userStackInformation.iRulePrecedence = thisRule.getPrecedence();

                boolean matches = thisRule.matches(aEnvironment, aStackTop, argumentsResultArray);

                if (matches) {
                    /* Rule dump trace code. */
                    if (isTraced(this.functionName) && showFlag) {
                        Cons arguments = SublistCons.getInstance(aEnvironment, aArguments);
                        String ruleDump = org.mathpiper.lisp.Utility.dumpRule(aStackTop, thisRule, aEnvironment, this);
                        Evaluator.traceShowRule(aEnvironment, arguments, ruleDump);
                    }
                    userStackInformation.iSide = 1;

                    BackQuoteSubstitute backQuoteSubstitute = new BackQuoteSubstitute(aEnvironment);

                    Cons originalBody = thisRule.getBody();
                    substitutedBody = Utility.substitute(aEnvironment, aStackTop, originalBody, backQuoteSubstitute);
                    //              aEnvironment.iLispExpressionEvaluator.Eval(aEnvironment, aResult, thisRule.body());
                    break;
                }

                // If rules got inserted, walk back
                while (thisRule != ((Rule) iBranchRules.get(ruleIndex)) && ruleIndex > 0) {
                    ruleIndex--;
                }
            }
        } catch (EvaluationException ex) {
            if (ex.getFunctionName() == null) {
                throw new EvaluationException(ex.getMessage() + " In function: " + this.functionName + ",  ", "none", -1,-1, -1, this.functionName);
            } else {
                throw ex;
            }
        } finally {
            aEnvironment.popLocalFrame(aStackTop);
        }



        if (substitutedBody != null) {
            //Note:tk:substituted body must be evaluated after the local frame has been popped.
            aResult = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, substitutedBody);
        } else // No predicate was true: return a new expression with the evaluated arguments.
        {
            Cons full = aArguments.copy(false);
            if (arity == 0) {
                full.setCdr(null);
            } else {
                full.setCdr(argumentsResultArray[0]);
                for (int parameterIndex = 0; parameterIndex < arity - 1; parameterIndex++) {
                    argumentsResultArray[parameterIndex].setCdr(argumentsResultArray[parameterIndex + 1]);
                }
            }
            aResult = SublistCons.getInstance(aEnvironment, full);
        }
        //FINISH:

        /*Leave trace code */
        if (isTraced(this.functionName) && showFlag) {
            Cons tr = SublistCons.getInstance(aEnvironment, aArguments);
            String localVariables = aEnvironment.getLocalVariables(aStackTop);
            LispExpressionEvaluator.traceShowLeave(aEnvironment, aResult, tr, "macro", localVariables);
            tr = null;
        }

        return aResult;
    }

}
