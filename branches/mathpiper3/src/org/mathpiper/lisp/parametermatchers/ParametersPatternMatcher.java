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
package org.mathpiper.lisp.parametermatchers;

import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.LispError;
//import org.mathpiper.lisp.AtomCons;
import org.mathpiper.lisp.Environment;
//import org.mathpiper.lisp.SublistCons;
import java.util.*;
import org.mathpiper.builtin.BigNumber;

/**
 *ParametersPatternMatcher matching code.
 *
 *General idea: have a class that can match function parameters
 *to a pattern, check for predicates on the arguments, and return
 *whether there was a match.
 *
 *First the pattern is mapped onto the arguments. Then local variables
 *are set. Then the predicates are called. If they all return true,
 *Then the pattern matches, and the locals can stay (the body is expected
 *to use these variables).
 *
 *Class that matches function arguments to a pattern.
 *This class (specifically, the matches() member function) can match
 *function parameters to a pattern, check for predicates on the
 *arguments, and return whether there was a match.
 */
public class ParametersPatternMatcher {
    //List of parameter matchers, one for every parameter.
    protected List iParamMatchers = new ArrayList();

    // List of variables appearing in the pattern.
    protected List iVariables = new ArrayList();

    // List of predicates which need to be true for a match.
    protected List<Cons> iPredicates = new ArrayList();


    /**
     *Constructor.
     *@param aEnvironment the underlying Lisp environment
     *@param aPattern Lisp expression containing the pattern
     *@param aPostPredicate Lisp expression containing the postpredicate
     *
     *The function makeParameterMatcher() is called for every argument
     *in aPattern, and the resulting pattern matchers are
     *collected in iParamMatchers. Additionally, aPostPredicate
     *is copied, and the copy is added to iPredicates.
     */
    public ParametersPatternMatcher(Environment aEnvironment, int aStackBase, Cons aPattern, Cons aPostPredicate) throws Exception {

        Cons  consTraverser =  aPattern;

        while (consTraverser != null) {

            PatternParameterMatcher matcher = makeParameterMatcher(aEnvironment, aStackBase, consTraverser);

            if(matcher == null) LispError.lispAssert(aEnvironment, aStackBase);

            iParamMatchers.add(matcher);

            consTraverser = consTraverser.cdr();
        }//end while.

        iPredicates.add(aPostPredicate);

        
    }//end method.


    /*
    Try to match the pattern against aArguments.
    First, every argument in aArguments is matched against the
    corresponding PatternParameterMatcher in iParamMatches. If any
    match fails, matches() returns false. Otherwise, a temporary
    LispLocalFrame is constructed, then setPatternVariables() and
    checkPredicates() are called, and then the LispLocalFrame is
    immediately deleted. If checkPredicates() returns false, this
    function also returns false. Otherwise, setPatternVariables()
    is called again, but now in the current LispLocalFrame, and
    this function returns true.
     */
    public boolean matches(Environment aEnvironment, int aStackBase, Cons aArguments) throws Exception {
        int i;

        Cons[] argumentsCons = null;
        if (iVariables.size() > 0) {
            argumentsCons = new Cons[iVariables.size()];

        }
        Cons argumentsTraverser = aArguments;

        for (i = 0; i < iParamMatchers.size(); i++) {
            if (argumentsTraverser == null) {
                return false;
            }

            if (!((PatternParameterMatcher) iParamMatchers.get(i)).argumentMatches(aEnvironment, aStackBase, argumentsTraverser, argumentsCons)) {
                return false;
            }
            argumentsTraverser = argumentsTraverser.cdr();
        }
        if (argumentsTraverser != null) {
            return false;
        }

        {
            //Set the local variables.
            aEnvironment.pushLocalFrame(false, "Pattern");
            try {
                setPatternVariables(aEnvironment, argumentsCons, aStackBase);

                //Do the predicates
                if (!checkPredicates(aEnvironment, aStackBase)) {
                    return false;
                }
            } catch (Exception e) {
                throw e;
            } finally {
                aEnvironment.popLocalFrame(aStackBase);
            }
        }

        // setCons the local variables for sure now
        setPatternVariables(aEnvironment, argumentsCons, aStackBase);

        return true;
    }


    /**
     *Try to match the pattern against aArguments.
     *This function does the same as matches(Environment, Cons),
     *but differs in the type of the arguments.
     */
    public boolean matches(Environment aEnvironment, int aStackBase, Cons[] aArguments) throws Exception {
        int i;

        Cons[] arguments = null;
        if (iVariables.size() > 0) {
            arguments = new Cons[iVariables.size()];
        }



        for (i = 0; i < iParamMatchers.size(); i++) {
            if(i >= aArguments.length) LispError.throwError(aEnvironment, aStackBase, "Listed function definitions need at least two parameters.");
            PatternParameterMatcher patternParameter = (PatternParameterMatcher) iParamMatchers.get(i);
            Cons argument = aArguments[i];
            if (!patternParameter.argumentMatches(aEnvironment, aStackBase, argument, arguments)) {
                return false;
            }
        }

        {
            //Set the local variables.
            aEnvironment.pushLocalFrame(false, "Pattern");
            try {
                setPatternVariables(aEnvironment, arguments, aStackBase);

                //Check the predicates.
                if (!checkPredicates(aEnvironment, aStackBase)) {
                    return false;
                }
            } catch (Exception e) {
                throw e;
            } finally {
                aEnvironment.popLocalFrame(aStackBase);
            }
        }

        // Set the local variables for sure now.
        setPatternVariables(aEnvironment, arguments, aStackBase);

        return true;
    }


    /*
    Construct a pattern matcher out of a Lisp expression.
    The result of this function depends on the value of aPattern:
    - If aPattern is a number, the corresponding NumberPatternParameterMatcher is
    constructed and returned.
    - If aPattern is an atom, the corresponding AtomCons is
    constructed and returned.
    - If aPattern is a list of the form ( _var ),
    where var is an atom, lookUp() is called on var. Then
    the correspoding VariablePatternParameterMatcher is constructed and returned.
    - If aPattern is a list of the form ( var_expr ),
    where var is an atom, lookUp() is called on var. Then,
    expr is appended to #iPredicates. Finally, the
    correspoding VariablePatternParameterMatcher is constructed and returned.
    - If aPattern is a list of another form, this function
    calls itself on any of the entries in this list. The
    resulting PatternParameterMatcher objects are collected in a
    SublistCons, which is returned.
    - Otherwise, this function returns #null.
     */
    protected PatternParameterMatcher makeParameterMatcher(Environment aEnvironment, int aStackBase, Cons aPattern) throws Exception {

        if (aPattern == null) {
            return null;
        }


        //Check for a number pattern.
        if (aPattern.getNumber(aEnvironment.iPrecision, aEnvironment) != null) {
            return new NumberPatternParameterMatcher((BigNumber) aPattern.getNumber(aEnvironment.iPrecision, aEnvironment));
        }


        //Check for an atom pattern.
        if (aPattern.car() instanceof String) {
            return new AtomPatternParameterMatcher((String) aPattern.car());
        }


        // Else, it must be a sublist pattern.
        if (aPattern.car() instanceof Cons) {

            // See if it is a variable template:
            Cons sublist = (Cons) aPattern.car();
            //LispError.lispAssert(sublist != null);

            int num = Utility.listLength(aEnvironment, aStackBase, sublist);

            // variable matcher here...
            if (num > 1) {
                Cons head = sublist;

                //Handle _ prefix or suffix on a pattern variables.
                if (((String) head.car()).equals("_")) {
                    Cons second = head.cdr();
                    if (second.car() instanceof String) {
                        int index = lookUp((String) second.car());


                        if (num > 2) {
                            //Handle a pattern variable which has a predicate (like var_PredicateFunction).

                            Cons third = null;
                            Cons predicate = second.cdr();
                            if ((predicate.car() instanceof Cons)) {
                                third = Utility.flatCopy(aEnvironment, aStackBase, (Cons) predicate.car());
                            } else {
                                third = second.cdr().copy(false);
                            }

                            String str = (String) second.car();
                            Cons last = third;
                            while (last.cdr() != null) {
                                last = last.cdr();
                            }

                            last.setCdr(org.mathpiper.lisp.cons.AtomCons.getInstance(aEnvironment, aStackBase, str));

                            Cons newPredicate = org.mathpiper.lisp.cons.SublistCons.getInstance(aEnvironment, third);

                            iPredicates.add(newPredicate);
                        }//end if.

                        return new VariablePatternParameterMatcher(index);
                    }
                }
            }

            PatternParameterMatcher[] matchers = new PatternParameterMatcher[num];

            int i;
            Cons consTraverser = sublist;
            for (i = 0; i < num; i++) {
                matchers[i] = makeParameterMatcher(aEnvironment, aStackBase, consTraverser);
                if(matchers[i] == null) LispError.lispAssert(aEnvironment, aStackBase);
                consTraverser = consTraverser.cdr();
            }
            return new SublistPatternParameterMatcher(matchers, num);
        }

        return null;

    }//end method.


    /*
     *Look up a variable name in iVariables.
     *Returns index in iVariables array where aVariable
     *appears.  If aVariable is not in iVariables, it is added.
     */
    protected int lookUp(String aVariable) {
        int i;
        for (i = 0; i < iVariables.size(); i++) {
            if (aVariable.equals(iVariables.get(i))) {
                return i;
            }
        }
        iVariables.add(aVariable);
        return iVariables.size() - 1;
    }


    /**
     *Set local variables corresponding to the pattern variables.
     *This function goes through the #iVariables array. A local
     *variable is made for every entry in the array, and the
     *corresponding argument is assigned to it.
     */
    protected void setPatternVariables(Environment aEnvironment, Cons[] arguments, int aStackBase) throws Exception {
        int i;
        for (i = 0; i < iVariables.size(); i++) {
            //Set the variable to the new value
            aEnvironment.newLocalVariable((String) iVariables.get(i), arguments[i], aStackBase);
        }
    }


    /**
     *Check whether all predicates are true.
     *This function goes through all predicates in iPredicates and
     *evaluates them. It returns false if at least one
     *of these results IsFalse(). An error is raised if any result
     *that is neither IsTrue() nor IsFalse().
     */
    protected boolean checkPredicates(Environment aEnvironment, int aStackBase) throws Exception {
        int i;
        for (i = 0; i < iPredicates.size(); i++) {

            int oldStackTop = aEnvironment.iArgumentStack.getStackTopIndex();
            aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackBase,  iPredicates.get(i));
            Cons resultPredicate = aEnvironment.iArgumentStack.getElement(oldStackTop, aStackBase, aEnvironment);
            aEnvironment.iArgumentStack.popTo(oldStackTop, aStackBase, aEnvironment);

            if (Utility.isFalse(aEnvironment, resultPredicate, aStackBase)) {
                return false;
            }


            // If the result is not False, it should be True, else probably something is wrong (the expression returned unevaluated)
            boolean isTrue = Utility.isTrue(aEnvironment, resultPredicate, aStackBase);
            if (!isTrue) {
                //TODO this is probably not the right way to generate an error, should we perhaps do a full throw new MathPiperException here?
                String errorMessage =  "The predicate " +
                Utility.printMathPiperExpression(aStackBase, iPredicates.get(i), aEnvironment, 60) +
                " evaluated to " +
                Utility.printMathPiperExpression(aStackBase, resultPredicate, aEnvironment, 60) +
                ".";


                if(! isTrue) LispError.throwError(aEnvironment, aStackBase, LispError.NON_BOOLEAN_PREDICATE_IN_PATTERN, errorMessage);
            }
        }
        return true;
    }


    public List getParameterMatchers() {
        return iParamMatchers;
    }


    public List getPredicates() {
        return iPredicates;
    }


    public List getVariables() {
        return iVariables;
    }

}

