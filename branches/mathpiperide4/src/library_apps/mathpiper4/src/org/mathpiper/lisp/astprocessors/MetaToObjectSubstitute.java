package org.mathpiper.lisp.astprocessors;

import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;

/** Substing one expression for another. The simplest form
 * of substitution
 */
public class MetaToObjectSubstitute
        implements ASTProcessor {

    Environment iEnvironment;


    public MetaToObjectSubstitute(Environment aEnvironment) {
        iEnvironment = aEnvironment;

    }


    public Cons matches(Environment aEnvironment, int aStackTop, Cons aElement)
            throws Throwable {
	
	if(aElement.car() instanceof String)
	{
	    String name = ((String) aElement.car()).replace("_", "");
	    
	    return(AtomCons.getInstance(aEnvironment, aStackTop, name));
	}

        return null;
    }

};
