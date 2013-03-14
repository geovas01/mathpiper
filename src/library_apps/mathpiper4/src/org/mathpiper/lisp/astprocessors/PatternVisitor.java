package org.mathpiper.lisp.astprocessors;

import org.mathpiper.builtin.PatternContainer;
import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.parametermatchers.ParametersPatternMatcher;


public class PatternVisitor
        implements ASTProcessor {

    Environment iEnvironment;
    ParametersPatternMatcher matcher = null;
    Cons function;
    String operatorString;


    public PatternVisitor(Environment aEnvironment, Cons pattern, Cons function) throws Throwable {
        iEnvironment = aEnvironment;
        this.function = function;
        
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
