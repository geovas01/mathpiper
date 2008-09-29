package org.mathrider.piper.lisp.behaviours;

import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.Environment;


/** Substing one expression for another. The simplest form
 * of substitution
 */
public class Subst
			implements SubstBase
{

	Environment iEnvironment;
	ConsPointer iToMatch;
	ConsPointer iToReplaceWith;

	public Subst(Environment aEnvironment, ConsPointer aToMatch, ConsPointer aToReplaceWith)
	{
		iEnvironment = aEnvironment;
		iToMatch = aToMatch;
		iToReplaceWith = aToReplaceWith;
	}

	public boolean matches(ConsPointer aResult, ConsPointer aElement)
	throws Exception
	{

		if (Standard.internalEquals(iEnvironment, aElement, iToMatch))
		{
			aResult.set(iToReplaceWith.get().copy(false));

			return true;
		}

		return false;
	}
};
