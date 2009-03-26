package org.mathpiper.lisp.behaviours;

import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.Environment;


/** Substing one expression for another. The simplest form
 * of substitution
 */
public class ExpressionSubstitute
			implements Substitute
{

	Environment iEnvironment;
	ConsPointer iToMatch;
	ConsPointer iToReplaceWith;

	public ExpressionSubstitute(Environment aEnvironment, ConsPointer aToMatch, ConsPointer aToReplaceWith)
	{
		iEnvironment = aEnvironment;
		iToMatch = aToMatch;
		iToReplaceWith = aToReplaceWith;
	}

	public boolean matches(ConsPointer aResult, ConsPointer aElement)
	throws Exception
	{

		if (UtilityFunctions.internalEquals(iEnvironment, aElement, iToMatch))
		{
			aResult.setCons(iToReplaceWith.getCons().copy(false));

			return true;
		}

		return false;
	}
};
