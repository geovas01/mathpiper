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

import org.mathrider.piper.lisp.Standard;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Error;
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.SubList;


public class MacroUserFunction extends BranchingUserFunction
{
	public MacroUserFunction(Pointer  aParameters) throws Exception
	{
		super(aParameters);
		Iterator iter = new Iterator(aParameters);
		int i=0;
		while (iter.GetObject() != null)
		{
			Error.Check(iter.GetObject().string() != null,Error.KLispErrCreatingUserFunction);
			((BranchParameter)iParameters.get(i)).iHold = true;
			iter.GoNext();
			i++;
		}
		UnFence();
	}
	public void Evaluate(Pointer  aResult,Environment  aEnvironment,
	                     Pointer  aArguments) throws Exception
	{
		int arity = Arity();
		int i;

		//hier
		/*TODO fixme
		    if (Traced())
		    {
		        Pointer tr;
		        tr.Set(SubList.New(aArguments.Get()));
		        TraceShowEnter(aEnvironment,tr);
		        tr.Set(null);
		    }
		*/
		Iterator iter = new Iterator(aArguments);
		iter.GoNext();

		// unrollable arguments
		Pointer[] arguments;
		if (arity==0)
			arguments = null;
		else
		{
			Error.LISPASSERT(arity>0);
			arguments = new Pointer[arity];
		}

		// Walk over all arguments, evaluating them as necessary
		for (i=0;i<arity;i++)
		{
			arguments[i] = new Pointer();
			Error.Check(iter.GetObject() != null, Error.KLispErrWrongNumberOfArgs);
			if (((BranchParameter)iParameters.get(i)).iHold)
			{
				arguments[i].set(iter.GetObject().copy(false));
			}
			else
			{
				Error.Check(iter.Ptr() != null, Error.KLispErrWrongNumberOfArgs);
				aEnvironment.iEvaluator.eval(aEnvironment, arguments[i], iter.Ptr());
			}
			iter.GoNext();
		}
		/*TODO fixme
		    if (Traced())
		    {
		        Iterator iter = new Iterator(aArguments);
		        iter.GoNext();
		        for (i=0;i<arity;i++)
		        {
		            TraceShowArg(aEnvironment,*iter.Ptr(),
		                  arguments[i]);

		            iter.GoNext();
		        }
		    }
		*/
		Pointer substedBody = new Pointer();
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
					Error.LISPASSERT(thisRule != null);

					st.iRulePrecedence = thisRule.Precedence();
					boolean matches = thisRule.Matches(aEnvironment, arguments);
					if (matches)
					{
						st.iSide = 1;

						BackQuoteBehaviour behaviour = new BackQuoteBehaviour(aEnvironment);
						Standard.internalSubstitute(substedBody, thisRule.Body(), behaviour);
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
			Pointer full = new Pointer();
			full.set(aArguments.get().copy(false));
			if (arity == 0)
			{
				full.get().cdr().set(null);
			}
			else
			{
				full.get().cdr().set(arguments[0].get());
				for (i=0;i<arity-1;i++)
				{
					arguments[i].get().cdr().set(arguments[i+1].get());
				}
			}
			aResult.set(SubList.getInstance(full.get()));
		}
		//FINISH:
		/*TODO fixme
		    if (Traced())
		    {
		        Pointer tr;
		        tr.Set(SubList.New(aArguments.Get()));
		        TraceShowLeave(aEnvironment, aResult,tr);
		        tr.Set(null);
		    }
		*/
	}
}


