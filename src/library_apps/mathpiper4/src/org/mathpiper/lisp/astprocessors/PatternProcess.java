package org.mathpiper.lisp.astprocessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mathpiper.builtin.PatternContainer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher;

public class PatternProcess implements ASTProcessor {

    Environment iEnvironment;
    ParametersPatternMatcher matcher = null;
    Cons associationList;
    String operatorString;

    public PatternProcess(Environment aEnvironment, Cons patternArguments, Cons associationList) throws Throwable {
	iEnvironment = aEnvironment;

	// check that associationList is a compound object
	if (!(associationList.car() instanceof Cons))
	    LispError.checkArgument(aEnvironment, -1, 2);
	if (associationList.car() == null)
	    LispError.checkArgument(aEnvironment, -1, 2);

	this.associationList = associationList;

	Cons postPredicate = Utility.getTrueAtom(aEnvironment);

	if (patternArguments.car() instanceof Cons) {
	    Cons operator = (Cons) patternArguments.car();

	    operatorString = (String) operator.car();

	    if ((operatorString.equals("::"))) {

		patternArguments = operator.cdr();

		postPredicate = operator.cdr().cdr();

		patternArguments.setCdr(null);

		if (patternArguments instanceof AtomCons) {
		    operatorString = "_";
		} else {
		    operatorString = (String) Cons.caar(patternArguments);

		    patternArguments = (Cons) Cons.cdar(patternArguments);
		}

	    } else {
		patternArguments = operator.cdr();
	    }

	    matcher = new org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher(aEnvironment, -1, patternArguments, postPredicate);

	} else {
	    String patternArgument = (String) patternArguments.car();

	    if (patternArgument.contains("_")) {

		if (patternArgument.equals("_")) {
		    operatorString = patternArgument;
		} else if (patternArgument.startsWith("__")) {
		    // Literal matching of variable names that begin with an
		    // underscore.
		    operatorString = patternArgument.substring(1, patternArgument.length());
		} else if (patternArgument.endsWith("__")) {
		    // Literal matching of variable names that end with an
		    // underscore.
		    operatorString = patternArgument.substring(0, patternArgument.length()-1);
		} else if (patternArgument.startsWith("_")) {
		    // Pattern variable with no checking function.
		    operatorString = "_";
		} else {
		    // Pattern variable with a function.
		    operatorString = "_";
		    matcher = new org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher(aEnvironment, -1, patternArguments, Utility.getTrueAtom(aEnvironment));
		}
	    } else {
		// Literal operator.
		operatorString = patternArgument;
	    }
	}

    }

    public Cons matches(Environment aEnvironment, int aStackTop, Cons aElement, List<Integer> positionList)
	    throws Throwable {

	try {
	    Object nodeSymbol;

	    Cons elementCopy = aElement.copy(false);

	    Cons returnCons = null;

	    elementCopy.setMetadataMap(new HashMap());

	    if (elementCopy instanceof SublistCons) {
		// Attempt to match a subexpression.

		nodeSymbol = Cons.caar(elementCopy);

		if (operatorString.equals(nodeSymbol) && matcher != null) {

		    if (matcher.matches(aEnvironment, aStackTop, (Cons) Cons.cdar(elementCopy))) {
			returnCons = handleMatch(aEnvironment, aStackTop, elementCopy, positionList);

		    }
		}
	    } else {
		nodeSymbol = elementCopy.car();

		if (matcher != null && operatorString.equals("_")) {
		    if (matcher.matches(aEnvironment, aStackTop, elementCopy)) {
			returnCons = handleMatch(aEnvironment, aStackTop, elementCopy, positionList);
		    }
		} else if (matcher == null && (operatorString.equals(nodeSymbol) || operatorString.equals("_"))) {

		    // Attempt to match a single symbol. A variable in it that
		    // contains the _ character matches all symbols.

		    // Obtain the function from the association list;
		    Cons result = Utility.associationListGet(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "\"function\""), ((Cons) associationList.car()).cdr());
		    if (result != null) {
			result = ((Cons) result.car()).cdr().cdr();
		    }
		    Cons function = result;

		    Cons positionListAtom = AtomCons.getInstance(aEnvironment, aStackTop, positionListToString(positionList));

		    positionListAtom.setCdr(elementCopy);

		    associationList.setCdr(positionListAtom);

		    returnCons = Utility.applyPure(aStackTop, function, associationList, aEnvironment);

		}// end if.

	    }

	    return returnCons;

	} finally {
	    associationList.setCdr(null);
	}
    }

    private Cons handleMatch(Environment aEnvironment, int aStackTop, Cons elementCopy, List<Integer> positionList)
	    throws Throwable {
	// Obtain the function from the association list;
	Cons result = Utility.associationListGet(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "\"function\""), ((Cons) associationList.car()).cdr());
	if (result != null) {
	    result = ((Cons) result.car()).cdr().cdr();
	}
	Cons function = result;

	Cons positionListAtom = AtomCons.getInstance(aEnvironment, aStackTop, positionListToString(positionList));

	positionListAtom.setCdr(elementCopy);

	associationList.setCdr(positionListAtom);

	return Utility.applyPure(aStackTop, function, associationList, aEnvironment);
    }

    public Cons getAssociationList(Environment aEnvironment, int aStackTop)
	    throws Throwable {
	return associationList;
    }

    private String positionListToString(List<Integer> positionList) {
	if (positionList.size() > 0) {
	    StringBuilder sb = new StringBuilder();
	    for (int x : positionList) {
		sb.append(x);
	    }

	    return sb.toString();
	} else {
	    return " "; // todo:tk:An AtomCons needs at least one character.
	}
    }

};
