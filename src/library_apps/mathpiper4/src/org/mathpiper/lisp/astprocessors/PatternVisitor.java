package org.mathpiper.lisp.astprocessors;

import org.mathpiper.builtin.PatternContainer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher;


public class PatternVisitor
        implements ASTProcessor {

    Environment iEnvironment;
    ParametersPatternMatcher matcher = null;
    Cons associationList;
    String operatorString;


    public PatternVisitor(Environment aEnvironment, Cons pattern, Cons associationList) throws Throwable {
        iEnvironment = aEnvironment;
        
        
        //check that associationList is a compound object
        if(! (associationList.car() instanceof Cons)) LispError.checkArgument(aEnvironment, -1, 2);
        Cons listCons = (Cons) associationList.car();
        if( listCons == null) LispError.checkArgument(aEnvironment, -1, 2);
        listCons = listCons.cdr();
        this.associationList = listCons;
        
        Cons postPredicate = Utility.getTrueAtom(aEnvironment);
        
        if(pattern.car() instanceof Cons)
        {
            Cons operator = (Cons) pattern.car();
            
            operatorString = (String) operator.car(); 
            
            pattern = operator.cdr();
            
            matcher = new org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher(aEnvironment, -1, pattern, postPredicate);
        //PatternContainer patternContainer = new PatternContainer(matcher);
        }
        else
        {
            operatorString = (String) pattern.car();
        }
        
    }


    public Cons matches(Environment aEnvironment, int aStackTop, Cons aElement)
            throws Throwable {

	
	Object nodeSymbol =  aElement.car();
		

	if (operatorString.equals(nodeSymbol)) {
	    
	    if(matcher == null)
	    {
		//Obtain the function from the association list;
		Cons result = Utility.associativeListGet(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "\"function\""), associationList);
		if(result != null)
		{
		    result = ((Cons) result.car()).cdr().cdr();
		}
		Cons function = result;
		
		//Cons listAtom1 = AtomCons.getInstance(aEnvironment, aStackTop, "List");
		
		//listAtom1.setCdr(associationList);
		
		//Cons associationSubList = SublistCons.getInstance(aEnvironment, listAtom1);
		
		Cons listAtom = AtomCons.getInstance(aEnvironment, aStackTop, "List");
		
		listAtom.setCdr(associationList);
		
		Cons sublist = SublistCons.getInstance(aEnvironment, listAtom);
		
		sublist.setCdr(aElement.copy(false));
		
		result = Utility.applyPure(aStackTop, function, sublist, aEnvironment);
		
		System.out.println("Match: " + aElement.toString());
		return null;
	    }
	    
	    if (matcher.matches(aEnvironment, aStackTop, aElement.cdr())) {
		System.out.println("Match: " + aElement.toString());
		return null;
	    }

	}

        return null;
    }

};
