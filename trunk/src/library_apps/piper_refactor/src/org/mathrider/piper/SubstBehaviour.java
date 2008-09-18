package org.mathrider.piper;

import org.mathrider.piper.lisp.LispStandard;
import org.mathrider.piper.lisp.LispPtr;
import org.mathrider.piper.lisp.LispEnvironment;


/** Substing one expression for another. The simplest form
 * of substitution
 */
public class SubstBehaviour
			implements SubstBehaviourBase
{

	LispEnvironment iEnvironment;
	LispPtr iToMatch;
	LispPtr iToReplaceWith;

	public SubstBehaviour(LispEnvironment aEnvironment, LispPtr aToMatch, LispPtr aToReplaceWith)
	{
		iEnvironment = aEnvironment;
		iToMatch = aToMatch;
		iToReplaceWith = aToReplaceWith;
	}

	public boolean matches(LispPtr aResult, LispPtr aElement)
	throws Exception
	{

		if (LispStandard.internalEquals(iEnvironment, aElement, iToMatch))
		{
			aResult.set(iToReplaceWith.get().copy(false));

			return true;
		}

		return false;
	}
};
