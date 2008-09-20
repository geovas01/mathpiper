/* {{{ License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */ //}}}

// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:

package org.mathrider.piper;


/// \file
/// Pattern matching code.
///
/// General idea: have a class that can match function parameters
/// to a pattern, check for predicates on the arguments, and return
/// whether there was a match.
///
/// First the pattern is mapped onto the arguments. Then local variables
/// are set. Then the predicates are called. If they all return true,
/// Then the pattern matches, and the locals can stay (the body is expected
/// to use these variables).


import org.mathrider.piper.lisp.LispObject;
import org.mathrider.piper.lisp.LispStandard;
import org.mathrider.piper.lisp.LispPtr;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.LispIterator;
import org.mathrider.piper.lisp.LispAtom;
import org.mathrider.piper.lisp.LispEnvironment;
import org.mathrider.piper.lisp.LispSubList;
import java.util.*;

/// Class that matches function arguments to a pattern.
/// This class (specifically, the Matches() member function) can match
/// function parameters to a pattern, check for predicates on the
/// arguments, and return whether there was a match.

public class PiperPatternPredicateBase
{
	/// List of parameter matches, one for every parameter.
	protected ArrayList iParamMatchers = new ArrayList(); //CDeletingArrayGrower<PiperParamMatcherBase*> iParamMatchers;

	/// List of variables appearing in the pattern.
	protected ArrayList iVariables = new ArrayList(); //CArrayGrower<String>

	/// List of predicates which need to be true for a match.
	protected ArrayList iPredicates = new ArrayList(); //CDeletingArrayGrower<LispPtr[] >

	/// Constructor.
	/// \param aEnvironment the underlying Lisp environment
	/// \param aPattern Lisp expression containing the pattern
	/// \param aPostPredicate Lisp expression containing the
	/// postpredicate
	///
	/// The function MakePatternMatcher() is called for every argument
	/// in \a aPattern, and the resulting pattern matchers are
	/// collected in #iParamMatchers. Additionally, \a aPostPredicate
	/// is copied, and the copy is added to #iPredicates.
	public PiperPatternPredicateBase(LispEnvironment  aEnvironment,
	                                 LispPtr  aPattern,
	                                 LispPtr  aPostPredicate) throws Exception
	{
		LispIterator iter = new LispIterator(aPattern);

		while (iter.GetObject() != null)
		{
			PiperParamMatcherBase matcher = MakeParamMatcher(aEnvironment,iter.GetObject());
			LispError.LISPASSERT(matcher!=null);
			iParamMatchers.add(matcher);
			iter.GoNext();
		}
		LispPtr  post = new LispPtr();
		post.set(aPostPredicate.get());
		iPredicates.add(post);
	}


	/// Try to match the pattern against \a aArguments.
	/// First, every argument in \a aArguments is matched against the
	/// corresponding PiperParamMatcherBase in #iParamMatches. If any
	/// match fails, Matches() returns false. Otherwise, a temporary
	/// LispLocalFrame is constructed, then SetPatternVariables() and
	/// CheckPredicates() are called, and then the LispLocalFrame is
	/// immediately deleted. If CheckPredicates() returns false, this
	/// function also returns false. Otherwise, SetPatternVariables()
	/// is called again, but now in the current LispLocalFrame, and
	/// this function returns true.
	public boolean Matches(LispEnvironment  aEnvironment, LispPtr  aArguments) throws Exception
	{
		int i;

		LispPtr[]  arguments = null;
		if (iVariables.size() > 0)
		{
			arguments = new LispPtr[iVariables.size()];
			for (i=0;i<iVariables.size();i++)
			{
				arguments[i] = new LispPtr();
			}

		}
		LispIterator iter = new LispIterator(aArguments);

		for (i=0;i<iParamMatchers.size();i++)
		{
			if (iter.GetObject() == null)
				return false;
			LispPtr  ptr = iter.Ptr();
			if (ptr==null)
				return false;
			if (!((PiperParamMatcherBase)iParamMatchers.get(i)).argumentMatches(aEnvironment,ptr,arguments))
			{
				return false;
			}
			iter.GoNext();
		}
		if (iter.GetObject() != null)
			return false;

		{
			// set the local variables.
			aEnvironment.pushLocalFrame(false);
			try
			{
				SetPatternVariables(aEnvironment,arguments);

				// do the predicates
				if (!CheckPredicates(aEnvironment))
					return false;
			}
			catch (Exception e)
			{
				throw e;
			}
			finally
			{
				aEnvironment.popLocalFrame();
			}
		}

		// set the local variables for sure now
		SetPatternVariables(aEnvironment,arguments);

		return true;
	}

	/// Try to match the pattern against \a aArguments.
	/// This function does the same as Matches(LispEnvironment ,LispPtr ),
	/// but differs in the type of the arguments.
	boolean Matches(LispEnvironment  aEnvironment, LispPtr[]  aArguments) throws Exception
	{
		int i;

		LispPtr[]  arguments = null;
		if (iVariables.size() > 0)
			arguments = new LispPtr[iVariables.size()];
		for (i=0;i<iVariables.size();i++)
		{
			arguments[i] = new LispPtr();
		}

		for (i=0;i<iParamMatchers.size();i++)
		{
			if (!((PiperParamMatcherBase)iParamMatchers.get(i)).argumentMatches(aEnvironment,aArguments[i],arguments))
			{
				return false;
			}
		}

		{
			// set the local variables.
			aEnvironment.pushLocalFrame(false);
			try
			{
				SetPatternVariables(aEnvironment,arguments);

				// do the predicates
				if (!CheckPredicates(aEnvironment))
					return false;
			}
			catch (Exception e)
			{
				throw e;
			}
			finally
			{
				aEnvironment.popLocalFrame();
			}
		}

		// set the local variables for sure now
		SetPatternVariables(aEnvironment,arguments);
		return true;
	}

	/// Construct a pattern matcher out of a Lisp expression.
	/// The result of this function depends on the value of \a aPattern:
	/// - If \a aPattern is a number, the corresponding MatchNumber is
	///   constructed and returned.
	/// - If \a aPattern is an atom, the corresponding MatchAtom is
	///   constructed and returned.
	/// - If \a aPattern is a list of the form <tt>( _ var )<tt>,
	///   where \c var is an atom, LookUp() is called on \c var. Then
	///   the correspoding MatchVariable is constructed and returned.
	/// - If \a aPattern is a list of the form <tt>( _ var expr )<tt>,
	///   where \c var is an atom, LookUp() is called on \c var. Then,
	///   \a expr is appended to #iPredicates. Finally, the
	///   correspoding MatchVariable is constructed and returned.
	/// - If \a aPattern is a list of another form, this function
	///   calls itself on any of the entries in this list. The
	///   resulting PiperParamMatcherBase objects are collected in a
	///   MatchSubList, which is returned.
	/// - Otherwise, this function returns #null.
	protected PiperParamMatcherBase MakeParamMatcher(LispEnvironment  aEnvironment, LispObject aPattern) throws Exception
	{
		if (aPattern == null)
			return null;
		if (aPattern.number(aEnvironment.precision()) != null)
		{
			return new MatchNumber(aPattern.number(aEnvironment.precision()));
		}
		// Deal with atoms
		if (aPattern.string() != null)
		{
			return new MatchAtom(aPattern.string());
		}

		// Else it must be a sublist
		if (aPattern.subList() != null)
		{
			// See if it is a variable template:
			LispPtr  sublist = aPattern.subList();
			LispError.LISPASSERT(sublist != null);

			int num = LispStandard.internalListLength(sublist);

			// variable matcher here...
			if (num>1)
			{
				LispObject head = sublist.get();
				if (head.string() == aEnvironment.hashTable().lookUp("_"))
				{
					LispObject second = head.cdr().get();
					if (second.string() != null)
					{
						int index = LookUp(second.string());

						// Make a predicate for the type, if needed
						if (num>2)
						{
							LispPtr third = new LispPtr();

							LispObject predicate = second.cdr().get();
							if (predicate.subList() != null)
							{
								LispStandard.internalFlatCopy(third, predicate.subList());
							}
							else
							{
								third.set(second.cdr().get().copy(false));
							}

							String str = second.string();
							LispObject last = third.get();
							while (last.cdr().get() != null)
								last = last.cdr().get();

							last.cdr().set(LispAtom.getInstance(aEnvironment,str));

							LispPtr pred = new LispPtr();
							pred.set(LispSubList.getInstance(third.get()));

							iPredicates.add(pred);
						}
						return new MatchVariable(index);
					}
				}
			}

			PiperParamMatcherBase[] matchers = new PiperParamMatcherBase[num];

			int i;
			LispIterator iter = new LispIterator(sublist);
			for (i=0;i<num;i++)
			{
				matchers[i] = MakeParamMatcher(aEnvironment,iter.GetObject());
				LispError.LISPASSERT(matchers[i] != null);
				iter.GoNext();
			}
			return new MatchSubList(matchers, num);
		}

		return null;
	}

	/// Look up a variable name in #iVariables
	/// \returns index in #iVariables array where \a aVariable
	/// appears.
	///
	/// If \a aVariable is not in #iVariables, it is added.
	protected int LookUp(String aVariable)
	{
		int i;
		for (i=0;i<iVariables.size();i++)
		{
			if (iVariables.get(i) == aVariable)
			{
				return i;
			}
		}
		iVariables.add(aVariable);
		return iVariables.size()-1;
	}

	/// Set local variables corresponding to the pattern variables.
	/// This function goes through the #iVariables array. A local
	/// variable is made for every entry in the array, and the
	/// corresponding argument is assigned to it.
	protected void SetPatternVariables(LispEnvironment  aEnvironment, LispPtr[]  arguments) throws Exception
	{
		int i;
		for (i=0;i<iVariables.size();i++)
		{
			// set the variable to the new value
			aEnvironment.newLocal((String)iVariables.get(i),arguments[i].get());
		}
	}

	/// Check whether all predicates are true.
	/// This function goes through all predicates in #iPredicates, and
	/// evaluates them. It returns #false if at least one
	/// of these results IsFalse(). An error is raised if any result
	/// neither IsTrue() nor IsFalse().
	protected boolean CheckPredicates(LispEnvironment  aEnvironment) throws Exception
	{
		int i;
		for (i=0;i<iPredicates.size();i++)
		{
			LispPtr pred = new LispPtr();
			aEnvironment.iEvaluator.eval(aEnvironment, pred, ((LispPtr)iPredicates.get(i)));
			if (LispStandard.isFalse(aEnvironment, pred))
			{
				return false;
			}


			// If the result is not False, it should be True, else probably something is wrong (the expression returned unevaluated)
			boolean isTrue = LispStandard.isTrue(aEnvironment, pred);
			if (!isTrue)
			{
				//TODO this is probably not the right way to generate an error, should we perhaps do a full throw new PiperException here?
				String strout;
				aEnvironment.iCurrentOutput.Write("The predicate\n\t");
				strout = LispStandard.printExpression(((LispPtr)iPredicates.get(i)), aEnvironment, 60);
				aEnvironment.iCurrentOutput.Write(strout);
				aEnvironment.iCurrentOutput.Write("\nevaluated to\n\t");
				strout = LispStandard.printExpression(pred, aEnvironment, 60);
				aEnvironment.iCurrentOutput.Write(strout);
				aEnvironment.iCurrentOutput.Write("\n");

				LispError.Check(isTrue,LispError.KLispErrNonBooleanPredicateInPattern);
			}
		}
		return true;
	}

}

