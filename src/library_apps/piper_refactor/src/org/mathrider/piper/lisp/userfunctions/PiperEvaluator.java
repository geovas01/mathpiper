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
import org.mathrider.piper.lisp.ConsPointer;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.ConsTraverser;
import org.mathrider.piper.lisp.Environment;
import org.mathrider.piper.lisp.SubList;


public class PiperEvaluator extends EvalFuncBase
{
	// FunctionFlags can be orred when passed to the constructor of this function

	public static int Function=0;    // Function: evaluate arguments
	public static int Macro=1;       // Function: don't evaluate arguments
	public static int Fixed = 0;     // fixed number of arguments
	public static int Variable = 2;  // variable number of arguments
	
	BuiltinFunction iCalledFunction;
	int iNumberOfArguments;
	int iFlags;

	public PiperEvaluator(BuiltinFunction aCalledFunction,int aNumberOfArguments, int aFlags)
	{
		iCalledFunction = aCalledFunction;
		iNumberOfArguments = aNumberOfArguments;
		iFlags = aFlags;
	}
	
	public void evaluate(ConsPointer aResult,Environment aEnvironment, ConsPointer aArguments) throws Exception
	{
		if ((iFlags & Variable) == 0)
		{
			LispError.checkNumberOfArguments(iNumberOfArguments+1,aArguments,aEnvironment);
		}

		int stacktop = aEnvironment.iArgumentStack.getStackTop();

		// Push a place holder for the result: push full expression so it is available for error reporting
		aEnvironment.iArgumentStack.pushArgumentOnStack(aArguments.get());

		ConsTraverser iter = new ConsTraverser(aArguments);
		iter.goNext();

		int i;
		int numberOfArguments = iNumberOfArguments;

		if ((iFlags & Variable) != 0) numberOfArguments--;

		// Walk over all arguments, evaluating them as necessary
		if ((iFlags & Macro) != 0)
		{
			for (i=0;i<numberOfArguments;i++)
			{
				LispError.check(iter.getCons() != null, LispError.KLispErrWrongNumberOfArgs);
				aEnvironment.iArgumentStack.pushArgumentOnStack(iter.getCons().copy(false));
				iter.goNext();
			}
			if ((iFlags & Variable) != 0)
			{
				ConsPointer head = new ConsPointer();
				head.set(aEnvironment.iList.copy(false));
				head.get().cdr().set(iter.getCons());
				aEnvironment.iArgumentStack.pushArgumentOnStack(SubList.getInstance(head.get()));
			}
		}
		else
		{
			ConsPointer argument = new ConsPointer();
			for (i=0;i<numberOfArguments;i++)
			{
				LispError.check(iter.getCons() != null, LispError.KLispErrWrongNumberOfArgs);
				LispError.check(iter.ptr() != null, LispError.KLispErrWrongNumberOfArgs);
				aEnvironment.iEvaluator.evaluate(aEnvironment, argument, iter.ptr());
				aEnvironment.iArgumentStack.pushArgumentOnStack(argument.get());
				iter.goNext();
			}
			if ((iFlags & Variable) != 0)
			{

				//LispString res;

				//printf("Enter\n");
				ConsPointer head = new ConsPointer();
				head.set(aEnvironment.iList.copy(false));
				head.get().cdr().set(iter.getCons());
				ConsPointer list = new ConsPointer();
				list.set(SubList.getInstance(head.get()));


				/*
				PrintExpression(res, list,aEnvironment,100);
				printf("before %s\n",res.String());
				*/

				aEnvironment.iEvaluator.evaluate(aEnvironment, argument, list);

				/*
				PrintExpression(res, arg,aEnvironment,100);
				printf("after %s\n",res.String());
				*/

				aEnvironment.iArgumentStack.pushArgumentOnStack(argument.get());
				//printf("Leave\n");
			}
		}

		iCalledFunction.eval(aEnvironment,stacktop);
		aResult.set(aEnvironment.iArgumentStack.getElement(stacktop).get());
		aEnvironment.iArgumentStack.popTo(stacktop);
	}

}


