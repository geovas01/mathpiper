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
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.SubListCons;
import java.util.*;
import org.mathpiper.lisp.Evaluator;

/**
 * A function (usually mathematical) which is defined by one or more rules.
 * This is the basic class which implements functions.  Evaluation is done
 * by consulting a set of rewritng rules.  The body of the first rule that
 * matches is evaluated and its result is returned as the function's result.
 */
public class SingleArityBranchingUserFunction extends Evaluator {
    /// List of arguments, with corresponding \c iHold property.

    protected List<FunctionParameter> iParameters = new ArrayList(); //CArrayGrower<FunctionParameter>

    /// List of rules, sorted on precedence.
    protected List<Branch> iBranchRules = new ArrayList();//CDeletingArrayGrower<BranchRuleBase*>

    /// List of arguments
    ConsPointer iParameterList = new ConsPointer();
/// Abstract class providing the basic user function API.
/// Instances of this class are associated to the name of the function
/// via an associated hash table. When obtained, they can be used to
/// evaluate the function with some arguments.
    boolean iFenced = true;

    /**
     * Constructor.
     *
     * @param aParameters linked list constaining the names of the arguments
     * @throws java.lang.Exception
     */
    public SingleArityBranchingUserFunction(ConsPointer aParameters) throws Exception {
        // iParameterList and #iParameters are setCons from \a aParameters.
        iParameterList.setCons(aParameters.getCons());

        ConsTraverser parameterTraverser = new ConsTraverser(aParameters);

        while (parameterTraverser.getCons() != null) {
            LispError.check(parameterTraverser.getCons().string() != null, LispError.KLispErrCreatingUserFunction);
            FunctionParameter parameter = new FunctionParameter(parameterTraverser.getCons().string(), false);
            iParameters.add(parameter);
            parameterTraverser.goNext();
        }
    }

    /**
     * Evaluate the function with the given arguments.
     * First, all arguments are evaluated by the evaluator associated
     * with aEnvironment, unless the iHold flag of the
     * corresponding parameter is true. Then a new LocalFrame is
     * constructed, in which the actual arguments are assigned to the
     * names of the formal arguments, as stored in iParameter. Then
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
    public void evaluate(Environment aEnvironment, ConsPointer aResult, ConsPointer aArgumentsPointer) throws Exception {
        int arity = arity();
        int i;

        /*Enter trace code*/
        if (isTraced()) {
            ConsPointer argumentsPointer = new ConsPointer();
            argumentsPointer.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
            Evaluator.traceShowEnter(aEnvironment, argumentsPointer, "user");
            argumentsPointer.setCons(null);
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
            for (i = 0; i < arity; i++) {
                argumentsResultPointerArray[i] = new ConsPointer();
            }
        }

        // Walk over all arguments, evaluating them as necessary ********************************************************
        for (i = 0; i < arity; i++) {
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

        // declare a new local stack.
        aEnvironment.pushLocalFrame(fenced());
        try {
            // define the local variables.
            for (i = 0; i < arity; i++) {
                String variable = ((FunctionParameter) iParameters.get(i)).iParameter;
                // set the variable to the new value
                aEnvironment.newLocalVariable(variable, argumentsResultPointerArray[i].getCons());
            }

            // walk the rules database, returning the evaluated result if the
            // predicate is true.
            int numberOfRules = iBranchRules.size();
            UserStackInformation userStackInformation = aEnvironment.iLispExpressionEvaluator.stackInformation();
            for (i = 0; i < numberOfRules; i++) {
                Branch thisRule = ((Branch) iBranchRules.get(i));
                LispError.lispAssert(thisRule != null);



                userStackInformation.iRulePrecedence = thisRule.getPrecedence();
                boolean matches = thisRule.matches(aEnvironment, argumentsResultPointerArray);
                if (matches) {
                    
                    /* Rule dump trace code. */
                    if (isTraced()) {
                        ConsPointer argumentsPointer = new ConsPointer();
                        argumentsPointer.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
                        String ruleDump = org.mathpiper.lisp.UtilityFunctions.dumpRule(thisRule, aEnvironment, this);
                        Evaluator.traceShowRule(aEnvironment, argumentsPointer, ruleDump);
                    }

                    userStackInformation.iSide = 1;
                    aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aResult, thisRule.getBodyPointer());

                    /*Trace code */
                    if (isTraced()) {
                        ConsPointer argumentsPointer2 = new ConsPointer();
                        argumentsPointer2.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
                        Evaluator.traceShowLeave(aEnvironment, aResult, argumentsPointer2, "user");
                        argumentsPointer2.setCons(null);
                    }//end if.

                    return;
                }

                // If rules got inserted, walk back
                while (thisRule != ((Branch) iBranchRules.get(i)) && i > 0) {
                    i--;
                }
            }            // No predicate was true: return a new expression with the evaluated
            // arguments.

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


            /* Trace code */
            if (isTraced()) {
                ConsPointer argumentsPointer3 = new ConsPointer();
                argumentsPointer3.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
                Evaluator.traceShowLeave(aEnvironment, aResult, argumentsPointer3, "user");
                argumentsPointer3.setCons(null);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            aEnvironment.popLocalFrame();
        }
    }

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
            if (((FunctionParameter) iParameters.get(i)).iParameter == aVariable) {
                ((FunctionParameter) iParameters.get(i)).iHold = true;
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
     *  Add a RuleBranch to the list of rules.
     * See: insertRule()
     * 
     * @param aPrecedence
     * @param aPredicate
     * @param aBody
     * @throws java.lang.Exception
     */
    public void declareRule(int aPrecedence, ConsPointer aPredicate, ConsPointer aBody) throws Exception {
        // New branching rule.
        RuleBranch newRule = new RuleBranch(aPrecedence, aPredicate, aBody);
        LispError.check(newRule != null, LispError.KLispErrCreatingRule);

        insertRule(aPrecedence, newRule);
    }

    /**
     * Add a TruePredicateRuleBranch to the list of rules.
     * See: insertRule()
     * 
     * @param aPrecedence
     * @param aBody
     * @throws java.lang.Exception
     */
    public void declareRule(int aPrecedence, ConsPointer aBody) throws Exception {
        // New branching rule.
        RuleBranch newRule = new TruePredicateRuleBranch(aPrecedence, aBody);
        LispError.check(newRule != null, LispError.KLispErrCreatingRule);

        insertRule(aPrecedence, newRule);
    }

    /**
     *  Add a PatternBranch to the list of rules.
     *  See: insertRule()
     * 
     * @param aPrecedence
     * @param aPredicate
     * @param aBody
     * @throws java.lang.Exception
     */
    public void declarePattern(int aPrecedence, ConsPointer aPredicate, ConsPointer aBody) throws Exception {
        // New branching rule.
        PatternBranch newRule = new PatternBranch(aPrecedence, aPredicate, aBody);
        LispError.check(newRule != null, LispError.KLispErrCreatingRule);

        insertRule(aPrecedence, newRule);
    }

    /**
     * Insert any Branch object in the list of rules.
     * This function does the real work for declareRule() and
     * declarePattern(): it inserts the rule in <b>iRules</b>, while
     * keeping it sorted. The algorithm is O(log n), where
     * n denotes the number of rules.
     * 
     * @param aPrecedence
     * @param newRule
     */
    void insertRule(int aPrecedence, Branch newRule) {
        // Find place to insert
        int low, high, mid;
        low = 0;
        high = iBranchRules.size();

        // Constant time: find out if the precedence is before any of the
        // currently defined rules or past them.
        if (high > 0) {
            if (((Branch) iBranchRules.get(0)).getPrecedence() > aPrecedence) {
                mid = 0;
                // Insert it
                iBranchRules.add(mid, newRule);
                return;
            }
            if (((Branch) iBranchRules.get(high - 1)).getPrecedence() < aPrecedence) {
                mid = high;
                // Insert it
                iBranchRules.add(mid, newRule);
                return;
            }
        }

        // Otherwise, O(log n) search algorithm for place to insert
        for (;;) {
            if (low >= high) {
                mid = low;
                // Insert it
                iBranchRules.add(mid, newRule);
                return;
            }
            mid = (low + high) >> 1;

            if (((Branch) iBranchRules.get(mid)).getPrecedence() > aPrecedence) {
                high = mid;
            } else if (((Branch) iBranchRules.get(mid)).getPrecedence() < aPrecedence) {
                low = (++mid);
            } else {
                // Insert it
                iBranchRules.add(mid, newRule);
                return;
            }
        }
    }

    /**
     * Return the argument list, stored in #iParameterList.
     * 
     * @return a ConsPointer
     */
    public ConsPointer argList() {
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

