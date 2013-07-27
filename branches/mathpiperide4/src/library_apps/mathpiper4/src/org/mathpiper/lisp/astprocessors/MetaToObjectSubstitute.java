package org.mathpiper.lisp.astprocessors;

import java.util.List;

import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;


public class MetaToObjectSubstitute
        implements ASTProcessor {

    Environment iEnvironment;


    public MetaToObjectSubstitute(Environment aEnvironment) {
        iEnvironment = aEnvironment;

    }


    public Cons matches(Environment aEnvironment, int aStackTop, Cons aElement, List<Integer> positionList)
            throws Throwable {
	
	if(aElement.car() instanceof String)
	{
	    String name = ((String) aElement.car()).replace("_", "");
	    
	    return(AtomCons.getInstance(aEnvironment, aStackTop, name));
	}

        return null;
    }

};
