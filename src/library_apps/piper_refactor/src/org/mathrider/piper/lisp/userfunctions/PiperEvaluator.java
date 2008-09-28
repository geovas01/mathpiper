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

package org.mathrider.piper.lisp.userfunctions;

// new-style evaluator, passing arguments onto the stack in Environment

import org.mathrider.piper.builtin.BuiltinFunction;
import org.mathrider.piper.*;
import org.mathrider.piper.lisp.Pointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.Iterator;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.SubList;


public class PiperEvaluator extends EvalFuncBase
{
	// FunctionFlags can be orred when passed to the constructor of this function

	public static int Function=0;    // Function: evaluate arguments
	public static int Macro=1;       // Function: don't evaluate arguments
	public static int Fixed = 0;     // fixed number of arguments
	public static int Variable = 2;  // variable number of arguments
	
	BuiltinFunction iCaller;
	int iNrArgs;
	int iFlags;

	public PiperEvaluator(BuiltinFunction aCaller,int aNrArgs, int aFlags)
	{
		iCaller = aCaller;
		iNrArgs = aNrArgs;
		iFlags = aFlags;
	}
	
	public void Evaluate(Pointer aResult,Environment aEnvironment, Pointer aArguments) throws Exception
	{
		if ((iFlags & Variable) == 0)
		{
			LispError.CheckNrArgs(iNrArgs+1,aArguments,aEnvironment);
		}

		int stacktop = aEnvironment.iStack.getStackTop();

		// Push a place holder for the result: push full expression so it is available for error reporting
		aEnvironment.iStack.pushArgumentOnStack(aArguments.get());

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
				LispError.Check(iter.GetObject() != null, LispError.KLispErrWrongNumberOfArgs);
				aEnvironment.iStack.pushArgumentOnStack(iter.GetObject().copy(false));
				iter.GoNext();
			}
			if ((iFlags & Variable) != 0)
			{
				Pointer head = new Pointer();
				head.set(aEnvironment.iList.copy(false));
				head.get().cdr().set(iter.GetObject());
				aEnvironment.iStack.pushArgumentOnStack(SubList.getInstance(head.get()));
			}
		}
		else
		{
			Pointer arg = new Pointer();
			for (i=0;i<nr;i++)
			{
				LispError.Check(iter.GetObject() != null, LispError.KLispErrWrongNumberOfArgs);
				LispError.Check(iter.Ptr() != null, LispError.KLispErrWrongNumberOfArgs);
				aEnvironment.iEvaluator.eval(aEnvironment, arg, iter.Ptr());
				aEnvironment.iStack.pushArgumentOnStack(arg.get());
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

				aEnvironment.iStack.pushArgumentOnStack(arg.get());
				//printf("Leave\n");
			}
		}

		iCaller.eval(aEnvironment,stacktop);
		aResult.set(aEnvironment.iStack.getElement(stacktop).get());
		aEnvironment.iStack.popTo(stacktop);
	}

}


