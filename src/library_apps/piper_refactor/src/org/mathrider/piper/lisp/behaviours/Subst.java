package org.mathrider.piper.lisp.behaviours;

import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Environment;


/** Substing one expression for another. The simplest form
 * of substitution
 */
public class Subst
			implements SubstBase
{

	Environment iEnvironment;
	Pointer iToMatch;
	Pointer iToReplaceWith;

	public Subst(Environment aEnvironment, Pointer aToMatch, Pointer aToReplaceWith)
	{
		iEnvironment = aEnvironment;
		iToMatch = aToMatch;
		iToReplaceWith = aToReplaceWith;
	}

	public boolean matches(Pointer aResult, Pointer aElement)
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
