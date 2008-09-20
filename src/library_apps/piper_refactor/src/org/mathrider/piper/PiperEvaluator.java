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

// new-style evaluator, passing arguments onto the stack in Environment

import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.Error;
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.SubList;


public class PiperEvaluator extends EvalFuncBase
{
	// FunctionFlags can be orred when passed to the constructor of this function

	static int Function=0;    // Function: evaluate arguments
	static int Macro=1;       // Function: don't evaluate arguments
	static int Fixed = 0;     // fixed number of arguments
	static int Variable = 2;  // variable number of arguments
	
	PiperEvalCaller iCaller;
	int iNrArgs;
	int iFlags;

	public PiperEvaluator(PiperEvalCaller aCaller,int aNrArgs, int aFlags)
	{
		iCaller = aCaller;
		iNrArgs = aNrArgs;
		iFlags = aFlags;
	}
	
	public void Evaluate(Pointer aResult,Environment aEnvironment, Pointer aArguments) throws Exception
	{
		if ((iFlags & Variable) == 0)
		{
			Error.CheckNrArgs(iNrArgs+1,aArguments,aEnvironment);
		}

		int stacktop = aEnvironment.iStack.GetStackTop();

		// Push a place holder for the result: push full expression so it is available for error reporting
		aEnvironment.iStack.PushArgOnStack(aArguments.get());

		Iterator iter = new Iterator(aArguments);
		iter.GoNext();

		int i;
		int nr = iNrArgs;

		if ((iFlags & Variable) != 0) nr--;

		// Walk over all arguments, evaluating them as necessary
		if ((iFlags & Macro) != 0)
		{
			for (i=0;i<nr;i++)
			{
				Error.Check(iter.GetObject() != null, Error.KLispErrWrongNumberOfArgs);
				aEnvironment.iStack.PushArgOnStack(iter.GetObject().copy(false));
				iter.GoNext();
			}
			if ((iFlags & Variable) != 0)
			{
				Pointer head = new Pointer();
				head.set(aEnvironment.iList.copy(false));
				head.get().cdr().set(iter.GetObject());
				aEnvironment.iStack.PushArgOnStack(SubList.getInstance(head.get()));
			}
		}
		else
		{
			Pointer arg = new Pointer();
			for (i=0;i<nr;i++)
			{
				Error.Check(iter.GetObject() != null, Error.KLispErrWrongNumberOfArgs);
				Error.Check(iter.Ptr() != null, Error.KLispErrWrongNumberOfArgs);
				aEnvironment.iEvaluator.eval(aEnvironment, arg, iter.Ptr());
				aEnvironment.iStack.PushArgOnStack(arg.get());
				iter.GoNext();
			}
			if ((iFlags & Variable) != 0)
			{

				//LispString res;

				//printf("Enter\n");
				Pointer head = new Pointer();
				head.set(aEnvironment.iList.copy(false));
				head.get().cdr().set(iter.GetObject());
				Pointer list = new Pointer();
				list.set(SubList.getInstance(head.get()));


				/*
				PrintExpression(res, list,aEnvironment,100);
				printf("before %s\n",res.String());
				*/

				aEnvironment.iEvaluator.eval(aEnvironment, arg, list);

				/*
				PrintExpression(res, arg,aEnvironment,100);
				printf("after %s\n",res.String());
				*/

				aEnvironment.iStack.PushArgOnStack(arg.get());
				//printf("Leave\n");
			}
		}

		iCaller.eval(aEnvironment,stacktop);
		aResult.set(aEnvironment.iStack.GetElement(stacktop).get());
		aEnvironment.iStack.PopTo(stacktop);
	}

}


