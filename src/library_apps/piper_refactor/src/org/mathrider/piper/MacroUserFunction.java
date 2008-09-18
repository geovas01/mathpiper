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

import org.mathrider.piper.lisp.LispStandard;
import org.mathrider.piper.lisp.LispPtr;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.LispIterator;
import org.mathrider.piper.lisp.LispEnvironment;
import org.mathrider.piper.lisp.LispSubList;


public class MacroUserFunction extends BranchingUserFunction
{
	public MacroUserFunction(LispPtr  aParameters) throws Exception
	{
		super(aParameters);
		LispIterator iter = new LispIterator(aParameters);
		int i=0;
		while (iter.GetObject() != null)
		{
			LispError.Check(iter.GetObject().string() != null,LispError.KLispErrCreatingUserFunction);
			((BranchParameter)iParameters.get(i)).iHold = true;
			iter.GoNext();
			i++;
		}
		UnFence();
	}
	public void Evaluate(LispPtr  aResult,LispEnvironment  aEnvironment,
	                     LispPtr  aArguments) throws Exception
	{
		int arity = Arity();
		int i;

		//hier
		/*TODO fixme
		    if (Traced())
		    {
		        LispPtr tr;
		        tr.Set(LispSubList.New(aArguments.Get()));
		        TraceShowEnter(aEnvironment,tr);
		        tr.Set(null);
		    }
		*/
		LispIterator iter = new LispIterator(aArguments);
		iter.GoNext();

		// unrollable arguments
		LispPtr[] arguments;
		if (arity==0)
			arguments = null;
		else
		{
			LispError.LISPASSERT(arity>0);
			arguments = new LispPtr[arity];
		}

		// Walk over all arguments, evaluating them as necessary
		for (i=0;i<arity;i++)
		{
			arguments[i] = new LispPtr();
			LispError.Check(iter.GetObject() != null, LispError.KLispErrWrongNumberOfArgs);
			if (((BranchParameter)iParameters.get(i)).iHold)
			{
				arguments[i].set(iter.GetObject().copy(false));
			}
			else
			{
				LispError.Check(iter.Ptr() != null, LispError.KLispErrWrongNumberOfArgs);
				aEnvironment.iEvaluator.eval(aEnvironment, arguments[i], iter.Ptr());
			}
			iter.GoNext();
		}
		/*TODO fixme
		    if (Traced())
		    {
		        LispIterator iter = new LispIterator(aArguments);
		        iter.GoNext();
		        for (i=0;i<arity;i++)
		        {
		            TraceShowArg(aEnvironment,*iter.Ptr(),
		                  arguments[i]);

		            iter.GoNext();
		        }
		    }
		*/
		LispPtr substedBody = new LispPtr();
		{
			// declare a new local stack.
			aEnvironment.pushLocalFrame(false);
			try
			{
				// define the local variables.
				for (i=0;i<arity;i++)
				{
					String variable = ((BranchParameter)iParameters.get(i)).iParameter;
					// set the variable to the new value
					aEnvironment.newLocal(variable,arguments[i].get());
				}

				// walk the rules database, returning the evaluated result if the
				// predicate is true.
				int nrRules = iRules.size();
				UserStackInformation st = aEnvironment.iEvaluator.stackInformation();
				for (i=0;i<nrRules;i++)
				{
					BranchRuleBase thisRule = ((BranchRuleBase)iRules.get(i));
					//TODO remove            CHECKPTR(thisRule);
					LispError.LISPASSERT(thisRule != null);

					st.iRulePrecedence = thisRule.Precedence();
					boolean matches = thisRule.Matches(aEnvironment, arguments);
					if (matches)
					{
						st.iSide = 1;

						BackQuoteBehaviour behaviour = new BackQuoteBehaviour(aEnvironment);
						LispStandard.internalSubstitute(substedBody, thisRule.Body(), behaviour);
						//              aEnvironment.iEvaluator.Eval(aEnvironment, aResult, thisRule.Body());
						break;
					}

					// If rules got inserted, walk back
					while (thisRule != ((BranchRuleBase)iRules.get(i)) && i>0) i--;
				}
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


		if (substedBody.get() != null)
		{
			aEnvironment.iEvaluator.eval(aEnvironment, aResult, substedBody);
		}
		else
			// No predicate was true: return a new expression with the evaluated
			// arguments.
		{
			LispPtr full = new LispPtr();
			full.set(aArguments.get().copy(false));
			if (arity == 0)
			{
				full.get().next().set(null);
			}
			else
			{
				full.get().next().set(arguments[0].get());
				for (i=0;i<arity-1;i++)
				{
					arguments[i].get().next().set(arguments[i+1].get());
				}
			}
			aResult.set(LispSubList.newSubList(full.get()));
		}
		//FINISH:
		/*TODO fixme
		    if (Traced())
		    {
		        LispPtr tr;
		        tr.Set(LispSubList.New(aArguments.Get()));
		        TraceShowLeave(aEnvironment, aResult,tr);
		        tr.Set(null);
		    }
		*/
	}
}


