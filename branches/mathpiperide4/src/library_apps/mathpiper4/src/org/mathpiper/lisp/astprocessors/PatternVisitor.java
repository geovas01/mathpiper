package org.mathpiper.lisp.astprocessors;

import java.util.Map;

import org.mathpiper.builtin.PatternContainer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher;

public class PatternVisitor implements ASTProcessor {

    Environment iEnvironment;
    ParametersPatternMatcher matcher = null;
    Cons associationList;
    String operatorString;



    public PatternVisitor(Environment aEnvironment, Cons pattern, Cons associationList) throws Throwable {
	iEnvironment = aEnvironment;

	//check that associationList is a compound object
	if (!(associationList.car() instanceof Cons))
	    LispError.checkArgument(aEnvironment, -1, 2);
	if (associationList.car() == null)
	    LispError.checkArgument(aEnvironment, -1, 2);

	this.associationList = associationList;

	Cons postPredicate = Utility.getTrueAtom(aEnvironment);

	if (pattern.car() instanceof Cons) {
	    Cons operator = (Cons) pattern.car();

	    operatorString = (String) operator.car();

	    if (!(operatorString.equals("_") && operator.cdr().cdr() == null)) {

		if (!operatorString.equals("_")) {
		    pattern = operator.cdr();
		}

		matcher = new org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher(aEnvironment, -1, pattern,
			postPredicate);
	    }

	} else {
	    operatorString = (String) pattern.car();
	}

    }



    public Cons matches(Environment aEnvironment, int aStackTop, Cons aElement) throws Throwable {

	Object nodeSymbol;

	if (aElement instanceof SublistCons) {
	    nodeSymbol = Cons.caar(aElement);

	    if (operatorString.equals(nodeSymbol) && matcher != null) {

		//aElement.setCdr(null);

		if (matcher.matches(aEnvironment, aStackTop, (Cons) Cons.cdar(aElement))) {
		    //Obtain the function from the association list;
		    Cons result = Utility.associativeListGet(aEnvironment, aStackTop,
			    AtomCons.getInstance(aEnvironment, aStackTop, "\"function\""), ((Cons) associationList.car()).cdr());
		    if (result != null) {
			result = ((Cons) result.car()).cdr().cdr();
		    }
		    Cons function = result;

		    associationList.setCdr(aElement);

		    result = Utility.applyPure(aStackTop, function, associationList, aEnvironment);

		    associationList.setCdr(null);

		    Map metaMap = result.getMetadataMap();

		    aElement.setMetadataMap(metaMap);

		    //System.out.println("Match: " + aElement.toString());
		    return null;
		}
	    }
	} else {
	    nodeSymbol = aElement.car();
	    if (operatorString.equals(nodeSymbol) || operatorString.equals("_")) {

		if (matcher == null) {
		    //Obtain the function from the association list;
		    Cons result = Utility.associativeListGet(aEnvironment, aStackTop,
			    AtomCons.getInstance(aEnvironment, aStackTop, "\"function\""), ((Cons) associationList.car()).cdr());
		    if (result != null) {
			result = ((Cons) result.car()).cdr().cdr();
		    }
		    Cons function = result;

		    associationList.setCdr(aElement);

		    result = Utility.applyPure(aStackTop, function, associationList, aEnvironment);

		    associationList.setCdr(null);

		    Map metaMap = result.getMetadataMap();

		    aElement.setMetadataMap(metaMap);

		    //System.out.println("Match: " + aElement.toString());
		    return null;
		} else {
		    if (matcher.matches(aEnvironment, aStackTop, aElement)) {
			//Obtain the function from the association list;
			Cons result = Utility
				.associativeListGet(aEnvironment, aStackTop,
					AtomCons.getInstance(aEnvironment, aStackTop, "\"function\""),
					((Cons) associationList.car()).cdr());
			if (result != null) {
			    result = ((Cons) result.car()).cdr().cdr();
			}
			Cons function = result;

			associationList.setCdr(aElement);

			result = Utility.applyPure(aStackTop, function, associationList, aEnvironment);

			associationList.setCdr(null);

			Map metaMap = result.getMetadataMap();

			aElement.setMetadataMap(metaMap);

			//System.out.println("Match: " + aElement.toString());
			return null;
		    }
		}

	    }//end if.
	}

	return null;
    }



    public Cons getAssociationList(Environment aEnvironment, int aStackTop) throws Throwable {
	return associationList;
    }

};
