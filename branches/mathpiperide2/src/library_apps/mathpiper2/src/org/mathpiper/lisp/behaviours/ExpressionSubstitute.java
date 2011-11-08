package org.mathpiper.lisp.behaviours;

import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.Cons;

/** Substing one expression for another. The simplest form
 * of substitution
 */
public class ExpressionSubstitute
        implements Substitute {

    Environment iEnvironment;
    ConsPointer iToMatch;
    ConsPointer iToReplaceWith;


    public ExpressionSubstitute(Environment aEnvironment, ConsPointer aToMatch, ConsPointer aToReplaceWith) {
        iEnvironment = aEnvironment;
        iToMatch = aToMatch;
        iToReplaceWith = aToReplaceWith;
    }


    public Cons matches(Environment aEnvironment, int aStackTop, Cons aElement)
            throws Exception {

        if (Utility.equals(iEnvironment, aStackTop, aElement, iToMatch.getCons())) {
            return iToReplaceWith.getCons().copy(aEnvironment, false);

        }

        return null;
    }

};
