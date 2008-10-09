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

package org.mathpiper.lisp;

import org.mathpiper.lisp.userfunctions.Evaluator;
import org.mathpiper.lisp.userfunctions.MultipleArityUserFunction;
import org.mathpiper.lisp.ExpressionEvaluator;
import org.mathpiper.lisp.Cons;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.userfunctions.UserFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.DefFile;


/// The basic evaluator for Lisp expressions.

public class LispExpressionEvaluator extends ExpressionEvaluator
{
	/// evaluate a Lisp expression
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
	/// element! Eg. its Next might be setCons...
	///
	public void evaluate(Environment aEnvironment, ConsPointer aResult, ConsPointer aExpression) throws Exception
	{
		LispError.lispAssert(aExpression.getCons() != null);
		aEnvironment.iEvalDepth++;
		if (aEnvironment.iEvalDepth>=aEnvironment.iMaxEvalDepth)
		{
			if (aEnvironment.iEvalDepth>aEnvironment.iMaxEvalDepth+20)
			{
				LispError.check(aEnvironment.iEvalDepth<aEnvironment.iMaxEvalDepth,
				                LispError.KLispErrUserInterrupt);
			}
			else
			{
				LispError.check(aEnvironment.iEvalDepth<aEnvironment.iMaxEvalDepth, LispError.KLispErrMaxRecurseDepthReached);
			}
		}

		String str = aExpression.getCons().string();

		// evaluate an atom: find the bound value (treat it as a variable)
		if (str != null)
		{
			if (str.charAt(0) == '\"')
			{
				aResult.setCons(aExpression.getCons().copy(false));
				aEnvironment.iEvalDepth--;
				return;
			}

			ConsPointer val = new ConsPointer();
			aEnvironment.getVariable(str,val);
			if (val.getCons() != null)
			{
				aResult.setCons(val.getCons().copy(false));
				aEnvironment.iEvalDepth--;
				return;
			}
			aResult.setCons(aExpression.getCons().copy(false));
			aEnvironment.iEvalDepth--;
			return;
		}

		{
			ConsPointer subList = aExpression.getCons().subList();

			if (subList != null)
			{
				Cons head = subList.getCons();
				if (head != null)
				{
					if (head.string() != null)
					{
						{
							Evaluator evaluator = (Evaluator)aEnvironment.getBuiltinFunctions().lookUp(head.string());
							// Try to find a built-in command
							if (evaluator != null)
							{
								evaluator.evaluate(aResult, aEnvironment, subList);
								aEnvironment.iEvalDepth--;
								return;
							}
						}

						{
							UserFunction userFunc;
							userFunc = GetUserFunction(aEnvironment, subList);
							if (userFunc != null)
							{
								userFunc.evaluate(aResult,aEnvironment,subList);
								aEnvironment.iEvalDepth--;
								return;
							}
						}
					}
					else
					{
						//printf("ApplyPure!\n");
						ConsPointer oper = new ConsPointer();
						ConsPointer args2 = new ConsPointer();
						oper.setCons(subList.getCons());
						args2.setCons(subList.getCons().cdr().getCons());
						UtilityFunctions.internalApplyPure(oper,args2,aResult,aEnvironment);
						aEnvironment.iEvalDepth--;
						return;
					}
					//printf("**** Undef: %s\n",head.String().String());
					UtilityFunctions.returnUnEvaluated(aResult,subList,aEnvironment);
					aEnvironment.iEvalDepth--;
					return;
				}
			}
			aResult.setCons(aExpression.getCons().copy(false));
		}
		aEnvironment.iEvalDepth--;
	}

	UserFunction GetUserFunction(Environment aEnvironment, ConsPointer subList) throws Exception
	{
		Cons head = subList.getCons();
		UserFunction userFunc = null;

		userFunc = (UserFunction)aEnvironment.userFunction(subList);
		if (userFunc != null)
		{
			return userFunc;
		}
		else if (head.string()!=null)
		{
			MultipleArityUserFunction multiUserFunc = aEnvironment.multiUserFunction(head.string());
			if (multiUserFunc.iFileToOpen!=null)
			{
				DefFile def = multiUserFunc.iFileToOpen;
				multiUserFunc.iFileToOpen=null;
				UtilityFunctions.internalUse(aEnvironment,def.iFileName);
			}
			userFunc = aEnvironment.userFunction(subList);
		}
		return userFunc;
	}

}
