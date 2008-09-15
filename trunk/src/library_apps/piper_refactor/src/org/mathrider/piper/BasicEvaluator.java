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

import org.mathrider.piper.lisp.LispMultiUserFunction;
import org.mathrider.piper.lisp.LispEvaluatorBase;
import org.mathrider.piper.lisp.LispObject;
import org.mathrider.piper.lisp.LispStandard;
import org.mathrider.piper.lisp.LispPtr;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.LispUserFunction;
import org.mathrider.piper.lisp.LispEnvironment;
import org.mathrider.piper.lisp.LispDefFile;


/// The basic evaluator for Lisp expressions.

public class BasicEvaluator extends LispEvaluatorBase
{
	/// Evaluate a Lisp expression
	/// \param aEnvironment the Lisp environment, in which the
	/// evaluation should take place.
	/// \param aResult the result of the evaluation.
	/// \param aExpression the expression to evaluate.
	///
	/// First, the evaluation depth is checked. An error is raised if
	/// the maximum evaluation depth is exceeded.
	///
	/// The next step is the actual evaluation. \a aExpression is a
	/// LispObject, so we can distinguish three cases.
	///   - If \a aExpression is a string starting with \c " , it is
	///     simply copied in \a aResult. If it starts with another
	///     character (this includes the case where it represents a
	///     number), the environment is checked to see whether a
	///     variable with this name exists. If it does, its value is
	///     copied in \a aResult, otherwise \a aExpression is copied.
	///   - If \a aExpression is a list, the head of the list is
	///     examined. If the head is not a string. InternalApplyPure()
	///     is called. If the head is a string, it is checked against
	///     the core commands; if there is a check, the corresponding
	///     evaluator is called. Then it is checked agaist the list of
	///     user function with GetUserFunction() . Again, the
	///     corresponding evaluator is called if there is a check. If
	///     all fails, ReturnUnEvaluated() is called.
	///   - Otherwise (ie. if \a aExpression is a generic object), it is
	///     copied in \a aResult.
	///
	/// \note The result of this operation must be a unique (copied)
	/// element! Eg. its Next might be set...
	///
	public void Eval(LispEnvironment aEnvironment, LispPtr aResult, LispPtr aExpression) throws Exception
	{
		LispError.LISPASSERT(aExpression.Get() != null);
		aEnvironment.iEvalDepth++;
		if (aEnvironment.iEvalDepth>=aEnvironment.iMaxEvalDepth)
		{
			if (aEnvironment.iEvalDepth>aEnvironment.iMaxEvalDepth+20)
			{
				LispError.Check(aEnvironment.iEvalDepth<aEnvironment.iMaxEvalDepth,
				                LispError.KLispErrUserInterrupt);
			}
			else
			{
				LispError.Check(aEnvironment.iEvalDepth<aEnvironment.iMaxEvalDepth, LispError.KLispErrMaxRecurseDepthReached);
			}
		}

		String str = aExpression.Get().String();

		// Evaluate an atom: find the bound value (treat it as a variable)
		if (str != null)
		{
			if (str.charAt(0) == '\"')
			{
				aResult.Set(aExpression.Get().Copy(false));
				aEnvironment.iEvalDepth--;
				return;
			}

			LispPtr val = new LispPtr();
			aEnvironment.GetVariable(str,val);
			if (val.Get() != null)
			{
				aResult.Set(val.Get().Copy(false));
				aEnvironment.iEvalDepth--;
				return;
			}
			aResult.Set(aExpression.Get().Copy(false));
			aEnvironment.iEvalDepth--;
			return;
		}

		{
			LispPtr subList = aExpression.Get().SubList();

			if (subList != null)
			{
				LispObject head = subList.Get();
				if (head != null)
				{
					if (head.String() != null)
					{
						{
							PiperEvaluator evaluator = (PiperEvaluator)aEnvironment.CoreCommands().LookUp(head.String());
							// Try to find a built-in command
							if (evaluator != null)
							{
								evaluator.Evaluate(aResult, aEnvironment, subList);
								aEnvironment.iEvalDepth--;
								return;
							}
						}

						{
							LispUserFunction userFunc;
							userFunc = GetUserFunction(aEnvironment, subList);
							if (userFunc != null)
							{
								userFunc.Evaluate(aResult,aEnvironment,subList);
								aEnvironment.iEvalDepth--;
								return;
							}
						}
					}
					else
					{
						//printf("ApplyPure!\n");
						LispPtr oper = new LispPtr();
						LispPtr args2 = new LispPtr();
						oper.Set(subList.Get());
						args2.Set(subList.Get().Next().Get());
						LispStandard.InternalApplyPure(oper,args2,aResult,aEnvironment);
						aEnvironment.iEvalDepth--;
						return;
					}
					//printf("**** Undef: %s\n",head.String().String());
					LispStandard.ReturnUnEvaluated(aResult,subList,aEnvironment);
					aEnvironment.iEvalDepth--;
					return;
				}
			}
			aResult.Set(aExpression.Get().Copy(false));
		}
		aEnvironment.iEvalDepth--;
	}

	LispUserFunction GetUserFunction(LispEnvironment aEnvironment, LispPtr subList) throws Exception
	{
		LispObject head = subList.Get();
		LispUserFunction userFunc = null;

		userFunc = (LispUserFunction)aEnvironment.UserFunction(subList);
		if (userFunc != null)
		{
			return userFunc;
		}
		else if (head.String()!=null)
		{
			LispMultiUserFunction multiUserFunc = aEnvironment.MultiUserFunction(head.String());
			if (multiUserFunc.iFileToOpen!=null)
			{
				LispDefFile def = multiUserFunc.iFileToOpen;
				multiUserFunc.iFileToOpen=null;
				LispStandard.InternalUse(aEnvironment,def.iFileName);
			}
			userFunc = aEnvironment.UserFunction(subList);
		}
		return userFunc;
	}

}
