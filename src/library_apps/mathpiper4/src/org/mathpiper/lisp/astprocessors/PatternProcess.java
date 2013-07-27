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
		
		operatorString = (String) Cons.caar(patternArguments);
		    
		patternArguments = (Cons) Cons.cdar(patternArguments);

	    } else {
		patternArguments = operator.cdr();
	    }

	    
	    matcher = new org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher(aEnvironment, -1, patternArguments, postPredicate);

	} else {
	    String patternArgument = (String) patternArguments.car();

	    if (patternArgument.contains("_")) {
		operatorString = "_";
	    } else {
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
		nodeSymbol = Cons.caar(elementCopy);

		if (operatorString.equals(nodeSymbol) && matcher != null) {

		    if (matcher.matches(aEnvironment, aStackTop, (Cons) Cons.cdar(elementCopy))) {
			// Obtain the function from the association list;
			Cons result = Utility.associationListGet(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "\"function\""), ((Cons) associationList.car()).cdr());
			if (result != null) {
			    result = ((Cons) result.car()).cdr().cdr();
			}
			Cons function = result;

			associationList.setCdr(elementCopy);

			returnCons = Utility.applyPure(aStackTop, function, associationList, aEnvironment);
			
			    if(returnCons != null)
			    {
				StringBuilder sb = new StringBuilder();
				for(int x:positionList)
				{
				    sb.append(x);
				}
				System.out.println(returnCons.car() + " " + sb);
			    }

		    }
		}
	    } else {
		nodeSymbol = elementCopy.car();
		if (operatorString.equals(nodeSymbol) || operatorString.contains("_")) { //|| operatorString.equals("_")) {

		    if (matcher == null) {
			// Obtain the function from the association list;
			Cons result = Utility.associationListGet(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "\"function\""), ((Cons) associationList.car()).cdr());
			if (result != null) {
			    result = ((Cons) result.car()).cdr().cdr();
			}
			Cons function = result;

			associationList.setCdr(elementCopy);

			returnCons = Utility.applyPure(aStackTop, function, associationList, aEnvironment);
			
			    if(returnCons != null)
			    {
				StringBuilder sb = new StringBuilder();
				for(int x:positionList)
				{
				    sb.append(x);
				}
				System.out.println(returnCons.car() + " " + sb);
			    }

		    } else {
			if (matcher.matches(aEnvironment, aStackTop, elementCopy)) {
			    // Obtain the function from the association list;
			    Cons result = Utility.associationListGet(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "\"function\""), ((Cons) associationList.car()).cdr());
			    if (result != null) {
				result = ((Cons) result.car()).cdr().cdr();
			    }
			    Cons function = result;

			    associationList.setCdr(elementCopy);

			    returnCons = Utility.applyPure(aStackTop, function, associationList, aEnvironment);
			    
			    if(returnCons != null)
			    {
				StringBuilder sb = new StringBuilder();
				for(int x:positionList)
				{
				    sb.append(x);
				}
				System.out.println(returnCons.car() + " " + sb);
			    }
			}
		    }

		}// end if.

	    }

	    return returnCons;

	} finally {
	    associationList.setCdr(null);
	}
    }

    public Cons getAssociationList(Environment aEnvironment, int aStackTop)
	    throws Throwable {
	return associationList;
    }

};
