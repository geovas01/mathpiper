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

import org.mathrider.piper.lisp.MultiUserFunction;
import org.mathrider.piper.lisp.EvaluatorBase;
import org.mathrider.piper.lisp.Cons;
import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Error;
import org.mathrider.piper.lisp.UserFunction;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.DefFile;


/// The basic evaluator for Lisp expressions.

public class BasicEvaluator extends EvaluatorBase
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
	/// The cdr step is the actual evaluation. \a aExpression is a
	/// Cons, so we can distinguish three cases.
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
	public void eval(Environment aEnvironment, Pointer aResult, Pointer aExpression) throws Exception
	{
		Error.LISPASSERT(aExpression.get() != null);
		aEnvironment.iEvalDepth++;
		if (aEnvironment.iEvalDepth>=aEnvironment.iMaxEvalDepth)
		{
			if (aEnvironment.iEvalDepth>aEnvironment.iMaxEvalDepth+20)
			{
				Error.Check(aEnvironment.iEvalDepth<aEnvironment.iMaxEvalDepth,
				                Error.KLispErrUserInterrupt);
			}
			else
			{
				Error.Check(aEnvironment.iEvalDepth<aEnvironment.iMaxEvalDepth, Error.KLispErrMaxRecurseDepthReached);
			}
		}

		String str = aExpression.get().string();

		// Evaluate an atom: find the bound value (treat it as a variable)
		if (str != null)
		{
			if (str.charAt(0) == '\"')
			{
				aResult.set(aExpression.get().copy(false));
				aEnvironment.iEvalDepth--;
				return;
			}

			Pointer val = new Pointer();
			aEnvironment.getVariable(str,val);
			if (val.get() != null)
			{
				aResult.set(val.get().copy(false));
				aEnvironment.iEvalDepth--;
				return;
			}
			aResult.set(aExpression.get().copy(false));
			aEnvironment.iEvalDepth--;
			return;
		}

		{
			Pointer subList = aExpression.get().subList();

			if (subList != null)
			{
				Cons head = subList.get();
				if (head != null)
				{
					if (head.string() != null)
					{
						{
							PiperEvaluator evaluator = (PiperEvaluator)aEnvironment.builtinCommands().lookUp(head.string());
							// Try to find a built-in command
							if (evaluator != null)
							{
								evaluator.Evaluate(aResult, aEnvironment, subList);
								aEnvironment.iEvalDepth--;
								return;
							}
						}

						{
							UserFunction userFunc;
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
						Pointer oper = new Pointer();
						Pointer args2 = new Pointer();
						oper.set(subList.get());
						args2.set(subList.get().cdr().get());
						Standard.internalApplyPure(oper,args2,aResult,aEnvironment);
						aEnvironment.iEvalDepth--;
						return;
					}
					//printf("**** Undef: %s\n",head.String().String());
					Standard.returnUnEvaluated(aResult,subList,aEnvironment);
					aEnvironment.iEvalDepth--;
					return;
				}
			}
			aResult.set(aExpression.get().copy(false));
		}
		aEnvironment.iEvalDepth--;
	}

	UserFunction GetUserFunction(Environment aEnvironment, Pointer subList) throws Exception
	{
		Cons head = subList.get();
		UserFunction userFunc = null;

		userFunc = (UserFunction)aEnvironment.userFunction(subList);
		if (userFunc != null)
		{
			return userFunc;
		}
		else if (head.string()!=null)
		{
			MultiUserFunction multiUserFunc = aEnvironment.multiUserFunction(head.string());
			if (multiUserFunc.iFileToOpen!=null)
			{
				DefFile def = multiUserFunc.iFileToOpen;
				multiUserFunc.iFileToOpen=null;
				Standard.internalUse(aEnvironment,def.iFileName);
			}
			userFunc = aEnvironment.userFunction(subList);
		}
		return userFunc;
	}

}
