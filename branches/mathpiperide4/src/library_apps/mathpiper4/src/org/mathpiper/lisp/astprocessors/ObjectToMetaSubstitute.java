package org.mathpiper.lisp.astprocessors;

import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;

import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.NumberCons;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;
import org.mathpiper.lisp.variables.GlobalVariable;


public class ObjectToMetaSubstitute
        implements ASTProcessor {

    Environment iEnvironment;


    public ObjectToMetaSubstitute(Environment aEnvironment) {
        iEnvironment = aEnvironment;

    }


    public Cons matches(Environment aEnvironment, int aStackTop, Cons aElement)
            throws Throwable {
	
	if(aElement instanceof NumberCons)
	{
	    return null;
	}
	
	
	if(aElement.car() instanceof String)
	{
	    String atomName = ((String) aElement.car());
	    
	    boolean foundNonSymbol = false;
	    for(char c : atomName.toCharArray())
	    {
		if(!MathPiperTokenizer.isSymbolic(c))
		{
		    foundNonSymbol = true;
		    break;
		}
	    }
	    if(foundNonSymbol == false)
	    {
		return null;
	    }
	    
	    
	    
	    Object object = (GlobalVariable)aEnvironment.iGlobalState.get(atomName);

	    if(object != null && ((GlobalVariable)object).iEvalBeforeReturn == true)
	    {
		return null;
	    }
	    
	    
	    
	    BuiltinFunctionEvaluator builtinInFunctionEvaluator = (BuiltinFunctionEvaluator) aEnvironment.getBuiltinFunctions().get(atomName);
	    if (builtinInFunctionEvaluator != null) 
            {
		return null;
            }
	    

	    if(aEnvironment.getMultipleArityRulebase(aStackTop, atomName, false) != null)
	    {
		return null;
	    }
	    else
	    {
		Utility.loadLibraryFunction(atomName, aEnvironment, aStackTop);
		
		if(aEnvironment.getMultipleArityRulebase(aStackTop, atomName, false) != null)
		{
		    return null;
		}
	    }
	    
	    
	    if(! atomName.contains("_"))
	    {
		atomName = "_".concat(atomName);
	    }
	    
	    return(AtomCons.getInstance(aEnvironment, aStackTop, atomName));
	}

        return null;
    }

};
