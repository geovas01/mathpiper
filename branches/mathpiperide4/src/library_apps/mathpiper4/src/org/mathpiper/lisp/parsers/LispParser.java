package org.mathpiper.lisp.parsers;

import org.mathpiper.io.MathPiperInputStream;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;
import org.mathpiper.lisp.tokenizers.MathPiperTokenizer;

public class LispParser extends Parser {
    
    {
	String parserName = "ParseLisp";
	addSupportedParser(parserName, this);
    }

    public LispParser(MathPiperTokenizer aTokenizer, MathPiperInputStream aInput, Environment aEnvironment) {

	super(aTokenizer, aInput, aEnvironment);
    }



    public Cons parse(int aStackTop) throws Throwable {
	Cons aResult;

	String token;
	// Get token.
	token = iTokenizer.nextToken(iEnvironment, aStackTop, iInput);
	if (token.length() == 0) //TODO FIXME either token == null or token.length() == 0?
	{
	    aResult = AtomCons.getInstance(iEnvironment, aStackTop, "EndOfFile");
	    return aResult;
	}
	aResult = parseAtom(iEnvironment, aStackTop, token);
	return aResult;
    }
    
    
    public String processLineTermination(String code)
    {
	return code;
    }
    
    public String processCodeBlock(String code)
    {
	return code;
    }



    private Cons parseList(Environment aEnvironment, int aStackTop) throws Throwable {
	String token;

	Cons result = null;
	Cons iter = null;
	boolean firstLoop = true;
	if (iListed) {
	    result = AtomCons.getInstance(iEnvironment, aStackTop, "List");
	    iter = (result.cdr()); //TODO FIXME
	}

	for (;;) {
	    //Get token.
	    token = iTokenizer.nextToken(iEnvironment, aStackTop, iInput);
	    // if token is empty string, error!
	    if (token.length() <= 0)
		LispError.throwError(iEnvironment, aStackTop, LispError.INVALID_TOKEN, "Token empty."); //TODO FIXME
	    // if token is ")" return result.
	    if (token.equals(")")) {
		return result;
	    }
	    // else parse simple atom with parse, and append it to the
	    // results list.

	    Cons atomCons = parseAtom(aEnvironment, aStackTop, token);

	    if (!iListed && firstLoop) {
		iter = atomCons;
		result = iter;
		firstLoop = false;
	    } else {
		iter.setCdr(atomCons);

		iter = (iter.cdr()); //TODO FIXME
	    }

	}//end for.

    }



    private Cons parseAtom(Environment aEnvironment, int aStackTop, String aToken) throws Throwable {
	// if token is empty string, return null pointer (no expression)
	if (aToken.length() == 0) //TODO FIXME either token == null or token.length() == 0?
	{
	    return null;
	}
	// else if token is "(" read in a whole array of objects until ")",
	//   and make a sublist
	if (aToken.equals("(")) {
	    Cons subList = parseList(aEnvironment, aStackTop);
	    Cons aResult = SublistCons.getInstance(aEnvironment, subList);
	    return aResult;
	}
	// else make a simple atom, and return it.
	Cons aResult = AtomCons.getInstance(iEnvironment, aStackTop, aToken);

	return aResult;
    }
}
