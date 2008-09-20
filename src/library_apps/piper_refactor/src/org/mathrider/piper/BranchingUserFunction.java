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
import org.mathrider.piper.lisp.ArityUserFunction;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.SubList;
import java.util.*;

/// A mathematical function defined by several rules.
/// This is the basic class which implements functions in Piper.
/// Evaluation is done by consulting a set of rewriting rules. The
/// body of the first rule that matches, is evaluated and this gives
/// the result of evaluating the function.

public class BranchingUserFunction extends ArityUserFunction
{
	/// List of arguments, with corresponding \c iHold property.
	protected Vector iParameters = new Vector(); //CArrayGrower<BranchParameter>

	/// List of rules, sorted on precedence.
	protected Vector iRules = new Vector();//CDeletingArrayGrower<BranchRuleBase*>

	/// List of arguments
	Pointer iParamList = new Pointer();

	/// Structure containing name of parameter and whether it is put on hold.
	public class BranchParameter
	{
		public BranchParameter(String aParameter, boolean aHold /*=false*/)
		{
			iParameter = aParameter;
			iHold = aHold;
		}
		String  iParameter;
		boolean iHold;
	}

	/// Abstract base class for rules.
	abstract class BranchRuleBase
	{
		public abstract boolean Matches(Environment aEnvironment, Pointer[] aArguments) throws Exception;
		public abstract int Precedence();
		public abstract Pointer Body();
	}

	/// A rule with a predicate.
	/// This rule matches if the predicate evaluates to #true.
	class BranchRule extends BranchRuleBase
	{
		public BranchRule(int aPrecedence,Pointer  aPredicate,Pointer  aBody)
		{
			iPrecedence = aPrecedence;
			iPredicate.set(aPredicate.get());
			iBody.set(aBody.get());
		}

		/// Return true if the rule matches.
		/// #iPredicate is evaluated in \a Environment. If the result
		/// IsTrue(), this function returns true.
		public boolean Matches(Environment  aEnvironment, Pointer[] aArguments) throws Exception
		{
			Pointer pred = new Pointer();
			aEnvironment.iEvaluator.eval(aEnvironment, pred, iPredicate);
			return Standard.isTrue(aEnvironment,pred);
		}

		/// Access #iPrecedence.
		public int Precedence()
		{
			return iPrecedence;
		}

		/// Access #iBody.
		public Pointer Body()
		{
			return iBody;
		}
		protected BranchRule()
		{
		}
		protected int iPrecedence;
		protected Pointer iBody = new Pointer();
		protected Pointer iPredicate = new Pointer();
	}

	/// A rule that always matches.
	class BranchRuleTruePredicate extends BranchRule
	{
		public BranchRuleTruePredicate(int aPrecedence,Pointer  aBody)
		{
			iPrecedence = aPrecedence;
			iBody.set(aBody.get());
		}
		/// Return #true, always.
		public boolean Matches(Environment  aEnvironment, Pointer[] aArguments) throws Exception
		{
			return true;
		}
	}

	/// A rule which matches if the corresponding PatternClass matches.
	class BranchPattern extends BranchRuleBase
	{
		/// Constructor.
		/// \param aPrecedence precedence of the rule
		/// \param aPredicate generic object of type \c Pattern
		/// \param aBody body of the rule
		public BranchPattern(int aPrecedence,Pointer  aPredicate,Pointer  aBody) throws Exception
		{
			iPatternClass = null;
			iPrecedence = aPrecedence;
			iPredicate.set(aPredicate.get());

			GenericClassContainer gen = aPredicate.get().generic();
			Error.Check(gen != null,Error.KLispErrInvalidArg);
			Error.Check(gen.TypeName().equals("\"Pattern\""),Error.KLispErrInvalidArg);

			iPatternClass = (PatternClass)gen;
			iBody.set(aBody.get());
		}

		/// Return true if the corresponding pattern matches.
		public boolean Matches(Environment  aEnvironment, Pointer[] aArguments) throws Exception
		{
			return iPatternClass.Matches(aEnvironment,aArguments);
		}

		/// Access #iPrecedence
		public int Precedence()
		{
			return iPrecedence;
		}

		/// Access #iBody
		public Pointer Body()
		{
			return iBody;
		}

		/// The precedence of this rule.
		protected int iPrecedence;

		/// The body of this rule.
		protected Pointer iBody = new Pointer();

		/// Generic object of type \c Pattern containing #iPatternClass
		protected Pointer iPredicate = new Pointer();

		/// The pattern that decides whether this rule matches.
		protected PatternClass iPatternClass;
	};

	/// Constructor.
	/// \param aParameters linked list constaining the names of the arguments
	///
	/// #iParamList and #iParameters are set from \a aParameters.
	public BranchingUserFunction(Pointer aParameters) throws Exception
	{
		iParamList.set(aParameters.get());
		Iterator iter = new Iterator(aParameters);
		while (iter.GetObject() != null)
		{
			Error.Check(iter.GetObject().string() != null,Error.KLispErrCreatingUserFunction);
			BranchParameter param = new BranchParameter(iter.GetObject().string(),false);
			iParameters.add(param);
			iter.GoNext();
		}
	}

	/// Evaluate the function on given arguments.
	/// \param aResult (on output) the result of the evaluation
	/// \param aEnvironment the underlying Lisp environment
	/// \param aArguments the arguments to the function
	///
	/// First, all arguments are evaluated by the evaluator associated
	/// to \a aEnvironment, unless the \c iHold flag of the
	/// corresponding parameter is true. Then a new LispLocalFrame is
	/// constructed, in which the actual arguments are assigned to the
	/// names of the formal arguments, as stored in \c iParameter. Then
	/// all rules in #iRules are tried one by one. The body of the
	/// first rule that matches is evaluated, and the result is put in
	/// \a aResult. If no rule matches, \a aResult will recieve a new
	/// expression with evaluated arguments.
	public void Evaluate(Pointer aResult,Environment aEnvironment, Pointer aArguments) throws Exception
	{
		int arity = Arity();
		int i;

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
			for (i=0;i<arity;i++)
				arguments[i] = new Pointer();
		}

		// Walk over all arguments, evaluating them as necessary
		for (i=0;i<arity;i++)
		{
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
		// declare a new local stack.
		aEnvironment.pushLocalFrame(Fenced());
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
				Error.LISPASSERT(thisRule != null);

				st.iRulePrecedence = thisRule.Precedence();
				boolean matches = thisRule.Matches(aEnvironment, arguments);
				if (matches)
				{
					st.iSide = 1;
					aEnvironment.iEvaluator.eval(aEnvironment, aResult, thisRule.Body());
					/*TODO fixme
					            if (Traced())
					            {
					                Pointer tr;
					                tr.Set(SubList.New(aArguments.Get()));
					                TraceShowLeave(aEnvironment, aResult,tr);
					                tr.Set(null);
					            }
					*/
					return;
				}

				// If rules got inserted, walk back
				while (thisRule != ((BranchRuleBase)iRules.get(i)) && i>0) i--;
			}

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
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			aEnvironment.popLocalFrame();
		}
	}

	/// Put an argument on hold.
	/// \param aVariable name of argument to put un hold
	///
	/// The \c iHold flag of the corresponding argument is set. This
	/// implies that this argument is not evaluated by Evaluate().
	public void HoldArgument(String aVariable)
	{
		int i;
		int nrc=iParameters.size();
		for (i=0;i<nrc;i++)
		{
			if (((BranchParameter)iParameters.get(i)).iParameter == aVariable)
				((BranchParameter)iParameters.get(i)).iHold = true;
		}
	}

	/// Return true if the arity of the function equals \a aArity.
	public boolean IsArity(int aArity)
	{
		return (Arity() == aArity);
	}

	/// Return the arity (number of arguments) of the function.
	public int Arity()
	{
		return iParameters.size();
	}

	/// Add a BranchRule to the list of rules.
	/// \sa InsertRule()
	public void DeclareRule(int aPrecedence, Pointer aPredicate, Pointer aBody) throws Exception
	{
		// New branching rule.
		BranchRule newRule = new BranchRule(aPrecedence,aPredicate,aBody);
		Error.Check(newRule != null,Error.KLispErrCreatingRule);

		InsertRule(aPrecedence,newRule);
	}

	/// Add a BranchRuleTruePredicate to the list of rules.
	/// \sa InsertRule()
	public void DeclareRule(int aPrecedence, Pointer aBody) throws Exception
	{
		// New branching rule.
		BranchRule newRule = new BranchRuleTruePredicate(aPrecedence,aBody);
		Error.Check(newRule != null,Error.KLispErrCreatingRule);

		InsertRule(aPrecedence,newRule);
	}

	/// Add a BranchPattern to the list of rules.
	/// \sa InsertRule()
	public void DeclarePattern(int aPrecedence, Pointer aPredicate, Pointer aBody) throws Exception
	{
		// New branching rule.
		BranchPattern newRule = new BranchPattern(aPrecedence,aPredicate,aBody);
		Error.Check(newRule != null,Error.KLispErrCreatingRule);

		InsertRule(aPrecedence,newRule);
	}

	/// Insert any BranchRuleBase object in the list of rules.
	/// This function does the real work for DeclareRule() and
	/// DeclarePattern(): it inserts the rule in #iRules, while
	/// keeping it sorted. The algorithm is \f$O(\log n)\f$, where
	/// \f$n\f$ denotes the number of rules.
	void InsertRule(int aPrecedence,BranchRuleBase newRule)
	{
		// Find place to insert
		int low,high,mid;
		low=0;
		high=iRules.size();

		// Constant time: find out if the precedence is before any of the
		// currently defined rules or past them.
		if (high>0)
		{
			if (((BranchRuleBase)iRules.get(0)).Precedence() > aPrecedence)
			{
				mid=0;
				// Insert it
				iRules.add(mid,newRule);return;
			}
			if (((BranchRuleBase)iRules.get(high-1)).Precedence() < aPrecedence)
			{
				mid=high;
				// Insert it
				iRules.add(mid,newRule);return;
			}
		}

		// Otherwise, O(log n) search algorithm for place to insert
		for(;;)
		{
			if (low>=high)
			{
				mid=low;
				// Insert it
				iRules.add(mid,newRule);return;
			}
			mid = (low+high)>>1;

			if (((BranchRuleBase)iRules.get(mid)).Precedence() > aPrecedence)
			{
				high = mid;
			}
			else if (((BranchRuleBase)iRules.get(mid)).Precedence() < aPrecedence)
			{
				low = (++mid);
			}
			else
			{
				// Insert it
				iRules.add(mid,newRule);return;
			}
		}
	}

	/// Return the argument list, stored in #iParamList
	public Pointer ArgList()
	{
		return iParamList;
	}


}

