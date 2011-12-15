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

import org.mathpiper.lisp.stacks.UserStackInformation;

import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.SublistCons;
import java.util.*;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.exceptions.ReturnException;
import org.mathpiper.lisp.Evaluator;
import org.mathpiper.lisp.cons.Cons;

/**
 * A function (usually mathematical) which is defined by one or more rules.
 * This is the basic class which implements functions.  Evaluation is done
 * by consulting a set of rewritng rules.  The body of the first rule that
 * matches is evaluated and its result is returned as the function's result.
 */
public class SingleArityRulebase extends Evaluator {
    // List of arguments, with corresponding iHold property.
    protected List<ParameterName> iParameters = new ArrayList(); //CArrayGrower<ParameterName>

    // List of rules, sorted on precedence.
    protected List<Rule> iBranchRules = new ArrayList();//CDeletingArrayGrower<BranchRuleBase*>
    
    // List of arguments
    Cons iParameterList;
/// Abstract class providing the basic user function API.
/// Instances of this class are associated to the name of the function
/// via an associated hash table. When obtained, they can be used to
/// evaluate the function with some arguments.
    boolean iFenced = true;
    boolean showFlag = false;
    protected String functionType = "**** user rulebase";
    protected String functionName;
    protected Environment iEnvironment;


    /**
     * Constructor.
     *
     * @param aParameters linked list constaining the names of the arguments
     * @throws java.lang.Exception
     */
    public SingleArityRulebase(Environment aEnvironment, int aStackTop, Cons aParameters, String functionName) throws Exception {
        iEnvironment = aEnvironment;
        this.functionName = functionName;

        // iParameterList and #iParameters are set from \a aParameters.
        iParameterList = aParameters;

        Cons parameters = aParameters;

        while (parameters != null) {

            try {
                if(! (parameters.car() instanceof String)) LispError.throwError(aEnvironment, aStackTop, LispError.CREATING_USER_FUNCTION, functionName);
            } catch (EvaluationException ex) {
                if (ex.getFunctionName() == null) {
                    throw new EvaluationException(ex.getMessage() + " In function: " + this.functionName + ",  ", "none", -1,-1, -1, this.functionName);
                } else {
                    throw ex;
                }
            }//end catch.

            ParameterName parameter = new ParameterName((String) parameters.car(), false);
            iParameters.add(parameter);
            parameters = parameters.cdr();
        }
    }


    /**
     * Evaluate the function with the given arguments.
     * First, all arguments are evaluated by the evaluator associated
     * with aEnvironment, unless the iHold flag of the
     * corresponding parameter is true. Then a new LocalFrame is
     * constructed, in which the actual arguments are assigned to the
     * names of the formal arguments, as stored in iName. Then
     * all rules in <b>iRules</b> are tried one by one. The body of the
     * first rule that matches is evaluated, and the result is put in
     * aResult. If no rule matches, aResult will recieve a new
     * expression with evaluated arguments.
     * 
     * @param aResult (on output) the result of the evaluation
     * @param aEnvironment the underlying Lisp environment
     * @param aArguments the arguments to the function
     * @throws java.lang.Exception
     */
    public Cons evaluate(Environment aEnvironment, int aStackTop, Cons aArguments) throws Exception {

        Cons aResult;
        int arity = arity();
        Cons[] argumentsResultArray = evaluateArguments(aEnvironment, aStackTop, aArguments);

        // Create a new local variables frame that has the same fenced state as this function.
        aEnvironment.pushLocalFrame(fenced(), this.functionName);

        int beforeStackTop = -1;
        int beforeEvaluationDepth = -1;
        int originalStackTop = -1;

        try {

            // define the local variables.
            for (int parameterIndex = 0; parameterIndex < arity; parameterIndex++) {
                String variableName = ((ParameterName) iParameters.get(parameterIndex)).iName;
                // set the variable to the new value
                aEnvironment.newLocalVariable(variableName, argumentsResultArray[parameterIndex], aStackTop);
            }

            // walk the rules database, returning the evaluated result if the
            // predicate is true.
            int numberOfRules = iBranchRules.size();

            UserStackInformation userStackInformation = aEnvironment.iLispExpressionEvaluator.stackInformation();

            for (int ruleIndex = 0; ruleIndex < numberOfRules; ruleIndex++) {
                Rule thisRule = ((Rule) iBranchRules.get(ruleIndex));
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

                    try {
                        beforeStackTop = aEnvironment.iArgumentStack.getStackTopIndex();
                        beforeEvaluationDepth = aEnvironment.iEvalDepth;

                        aResult = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, thisRule.getBody()); //*** User function is called here.

                    } catch (ReturnException re) {
                        //todo:tk:note that user functions currently return their results in aResult, not on the stack.
                        int stackTopIndex = aEnvironment.iArgumentStack.getStackTopIndex();
                        aResult =  BuiltinFunction.getTopOfStack(aEnvironment, stackTopIndex - 1);

                        aEnvironment.iArgumentStack.popTo(beforeStackTop, aStackTop, aEnvironment);
                        aEnvironment.iEvalDepth = beforeEvaluationDepth;

                    }

                    /*Leave trace code */
                    if (isTraced(this.functionName) && showFlag) {
                        Cons arguments2 = SublistCons.getInstance(aEnvironment, aArguments);
                        String localVariables = aEnvironment.getLocalVariables(aStackTop);
                        Evaluator.traceShowLeave(aEnvironment, aResult, arguments2, functionType, localVariables);
                        arguments2 = null;
                    }//end if.

                    return aResult;
                }//end if matches.

                // If rules got inserted, walk back.
                while (thisRule != ((Rule) iBranchRules.get(ruleIndex)) && ruleIndex > 0) {
                    ruleIndex--;
                }
            }//end for.


            // No predicate was true: return a new expression with the evaluated
            // arguments.
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


            /* Trace code */
            if (isTraced(this.functionName) && showFlag) {
                Cons arguments3 = SublistCons.getInstance(aEnvironment, aArguments);
                String localVariables = aEnvironment.getLocalVariables(aStackTop);
                Evaluator.traceShowLeave(aEnvironment, aResult, arguments3, functionType, localVariables);
                arguments3 = null;
            }

            return aResult;

        } catch (EvaluationException ex) {

            //ex.printStackTrace(); System.exit(1);//todo:tk:uncomment for debugging.

            if (ex.getFunctionName() == null) {
                throw new EvaluationException(ex.getMessage() + " In function: " + this.functionName + ",  ", "none", -1,-1, -1, this.functionName);
            } else {
                throw ex;
            }
        } finally {
            aEnvironment.popLocalFrame(aStackTop);
        }
    }


    protected Cons[] evaluateArguments(Environment aEnvironment, int aStackTop, Cons aArguments) throws Exception {
        int arity = arity();
        int parameterIndex;

        /*Enter trace code*/
        if (isTraced(this.functionName)) {
            Cons arguments = SublistCons.getInstance(aEnvironment, aArguments);
            String traceFunctionName = "";
            if (arguments.car() instanceof Cons) {
                Cons sub = (Cons) arguments.car();
                if (sub.car() instanceof String) {
                    traceFunctionName = (String) sub.car();
                }
            }//end function.
            if (Evaluator.isTraceFunction(traceFunctionName)) {
                showFlag = true;
                Evaluator.traceShowEnter(aEnvironment, arguments, functionType);
            } else {
                showFlag = false;
            }//
            arguments = null;
        }

        Cons argumentsTraverser = aArguments;

        //Strip the function name from the head of the list.
        argumentsTraverser = argumentsTraverser.cdr();

        //Creat an array which holds pointers to each argument.
        Cons[] argumentsResultArray;
        if (arity == 0) {
            argumentsResultArray = null;
        } else {
            if(arity <= 0) LispError.lispAssert(aEnvironment, aStackTop);
            argumentsResultArray = new Cons[arity];
        }

        // Walk over all arguments, evaluating them as necessary ********************************************************
        for (parameterIndex = 0; parameterIndex < arity; parameterIndex++) {

            //argumentsResultPointerArray[parameterIndex] = new ConsPointer();

            if(argumentsTraverser == null) LispError.throwError(aEnvironment, aStackTop, LispError.WRONG_NUMBER_OF_ARGUMENTS, "Expected arity: " + arity + ".");

            if (((ParameterName) iParameters.get(parameterIndex)).iHold) {
                //If the parameter is on hold, don't evaluate it and place a copy of it in argumentsArray.
                argumentsResultArray[parameterIndex] = argumentsTraverser.copy(false);
            } else {
                //If the parameter is not on hold:

                //Verify that the pointer to the arguments is not null.
                //if(argumentsTraverser == null) LispError.throwError(aEnvironment, aStackTop, LispError.WRONG_NUMBER_OF_ARGUMENTS, "Expected arity: " + arity + ".", "INTERNAL");

                //Evaluate each argument and place the result into argumentsResultArray[i];
                argumentsResultArray[parameterIndex] = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, argumentsTraverser);
            }
            argumentsTraverser = argumentsTraverser.cdr();
        }//end for.

        /*Argument trace code */
        if (isTraced(this.functionName) && argumentsResultArray != null && showFlag) {

            Cons traceParameter = this.iParameterList;


            for (parameterIndex = 0; parameterIndex < argumentsResultArray.length; parameterIndex++) {
                Evaluator.traceShowArg(aEnvironment, traceParameter, argumentsResultArray[parameterIndex]);

                traceParameter = traceParameter.cdr();
            }//end for.
        }//end if.

        return argumentsResultArray;

    }//end method.


    /**
     * Put an argument on hold.
     * The \c iHold flag of the corresponding argument is setCons. This
     * implies that this argument is not evaluated by evaluate().
     * 
     * @param aVariable name of argument to put un hold
     */
    public void holdArgument(String aVariable) {
        int i;
        int nrc = iParameters.size();
        for (i = 0; i < nrc; i++) {
            if (((ParameterName) iParameters.get(i)).iName.equals(aVariable)) {
                ((ParameterName) iParameters.get(i)).iHold = true;
            }
        }
    }


    /**
     * Return true if the arity of the function equals \a aArity.
     * 
     * @param aArity
     * @return true of the arities match.
     */
    public boolean isArity(int aArity) {
        return (arity() == aArity);
    }


    /**
     * Return the arity (number of arguments) of the function.
     *
     * @return the arity of the function
     */
    public int arity() {
        return iParameters.size();
    }


    /**
     *  Add a PredicateRule to the list of rules.
     * See: insertRule()
     * 
     * @param aPrecedence
     * @param aPredicate
     * @param aBody
     * @throws java.lang.Exception
     */
    public void defineSometimesTrueRule(int aStackTop, int aPrecedence, Cons aPredicate, Cons aBody) throws Exception {
        // New branching rule.
        PredicateRule newRule = new PredicateRule(iEnvironment, aPrecedence, aPredicate, aBody);
        //LispError.check(iEnvironment, aStackTop, newRule != null, LispError.CREATING_RULE, "INTERNAL");

        insertRule(aPrecedence, newRule);
    }


    /**
     * Add a TrueRule to the list of rules.
     * See: insertRule()
     * 
     * @param aPrecedence
     * @param aBody
     * @throws java.lang.Exception
     */
    public void defineAlwaysTrueRule(int aStackTop, int aPrecedence, Cons aBody) throws Exception {
        // New branching rule.
        PredicateRule newRule = new TrueRule(iEnvironment, aPrecedence, aBody);
        //LispError.check(iEnvironment, aStackTop, newRule != null, LispError.CREATING_RULE, "INTERNAL");

        insertRule(aPrecedence, newRule);
    }


    /**
     *  Add a PatternRule to the list of rules.
     *  See: insertRule()
     * 
     * @param aPrecedence
     * @param aPredicate
     * @param aBody
     * @throws java.lang.Exception
     */
    public void definePattern(int aStackTop, int aPrecedence, Cons aPredicate, Cons aBody) throws Exception {
        // New branching rule.
        PatternRule newRule = new PatternRule(iEnvironment, aStackTop, aPrecedence, aPredicate, aBody);
        //LispError.check(iEnvironment, aStackTop, newRule != null, LispError.CREATING_RULE, "INTERNAL");

        insertRule(aPrecedence, newRule);
    }


    /**
     * Insert any Rule object in the list of rules.
     * This function does the real work for defineAlwaysTrueRule() and
     * definePattern(): it inserts the rule in <b>iRules</b>, while
     * keeping it sorted. The algorithm is O(log n), where
     * n denotes the number of rules.
     * 
     * @param aPrecedence
     * @param newRule
     */
    void insertRule(int aNewRulePrecedence, Rule aNewRule) {
        // Find place to insert
        int low, high, mid;
        low = 0;
        high = iBranchRules.size();

        // Constant time: find out if the precedence is before any of the
        // currently defined rules or past them.
        if (high > 0) {
            if (((Rule) iBranchRules.get(0)).getPrecedence() > aNewRulePrecedence) {
                mid = 0;
                // Insert it
                iBranchRules.add(mid, aNewRule);
                return;
            }
            if (((Rule) iBranchRules.get(high - 1)).getPrecedence() < aNewRulePrecedence) {
                mid = high;
                // Insert it
                iBranchRules.add(mid, aNewRule);
                return;
            }
        }

        // Otherwise, O(log n) search algorithm for place to insert
        while(true) {
            if (low >= high) {
                //Insert it.
                mid = low;
                iBranchRules.add(mid, aNewRule);
                return;
            }


            mid = (low + high) >> 1;

            Rule existingRule = (Rule) iBranchRules.get(mid);

            int existingRulePrecedence = existingRule.getPrecedence();

            if (existingRulePrecedence > aNewRulePrecedence) {
                high = mid;
            } else if (existingRulePrecedence < aNewRulePrecedence) {
                low = (++mid);
            } else {

                //existingRule.
                //Insert it.
                iBranchRules.add(mid, aNewRule);
                return;
            }
        }
    }


    /**
     * Return the argument list, stored in #iParameterList.
     * 
     * @return a Cons
     */
    public Cons argList() {
        return iParameterList;
    }


    public Iterator getRules() {
        return iBranchRules.iterator();
    }


    public Iterator getParameters() {
        return iParameters.iterator();
    }


    public void unFence() {
        iFenced = false;
    }


    public boolean fenced() {
        return iFenced;
    }

}//end class.

