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

package org.mathpiper.builtin;

// new-style evaluator, passing arguments onto the stack in Environment

import org.mathpiper.lisp.Evaluator;
import org.mathpiper.lisp.userfunctions.*;
import org.mathpiper.builtin.BuiltinFunction;

import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.SubListCons;


public class BuiltinFunctionEvaluator extends Evaluator
{
	// FunctionFlags can be ORed when passed to the constructor of this function

	public static int Function=0;    // Function: evaluate arguments
	public static int Macro=1;       // Function: don't evaluate arguments
	public static int Fixed = 0;     // fixed number of arguments
	public static int Variable = 2;  // variable number of arguments
	
	BuiltinFunction iCalledBuiltinFunction;
	int iNumberOfArguments;
	int iFlags;

	public BuiltinFunctionEvaluator(BuiltinFunction aCalledBuiltinFunction,int aNumberOfArguments, int aFlags)
	{
		iCalledBuiltinFunction = aCalledBuiltinFunction;
		iNumberOfArguments = aNumberOfArguments;
		iFlags = aFlags;
	}
	
	public void evaluate(Environment aEnvironment,ConsPointer aResult, ConsPointer aArguments) throws Exception
	{
		if ((iFlags & Variable) == 0)
		{
			LispError.checkNumberOfArguments(iNumberOfArguments+1,aArguments,aEnvironment);
		}

		int stacktop = aEnvironment.iArgumentStack.getStackTopIndex();

		// Push a place holder for the getResult: push full expression so it is available for error reporting
		aEnvironment.iArgumentStack.pushArgumentOnStack(aArguments.getCons());

		ConsTraverser consTraverser = new ConsTraverser(aArguments);
		consTraverser.goNext();

		int i;
		int numberOfArguments = iNumberOfArguments;

		if ((iFlags & Variable) != 0) numberOfArguments--;

		// Walk over all arguments, evaluating them as necessary
		if ((iFlags & Macro) != 0)
		{
			for (i=0;i<numberOfArguments;i++)
			{
				LispError.check(consTraverser.getCons() != null, LispError.KLispErrWrongNumberOfArgs);
				aEnvironment.iArgumentStack.pushArgumentOnStack(consTraverser.getCons().copy(false));
				consTraverser.goNext();
			}
			if ((iFlags & Variable) != 0)
			{
				ConsPointer head = new ConsPointer();
				head.setCons(aEnvironment.iListAtom.copy(false));
				head.getCons().getRestPointer().setCons(consTraverser.getCons());
				aEnvironment.iArgumentStack.pushArgumentOnStack(SubListCons.getInstance(head.getCons()));
			}
		}
		else
		{
			ConsPointer argument = new ConsPointer();
			for (i=0;i<numberOfArguments;i++)
			{
				LispError.check(consTraverser.getCons() != null, LispError.KLispErrWrongNumberOfArgs);
				LispError.check(consTraverser.ptr() != null, LispError.KLispErrWrongNumberOfArgs);
				aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, argument, consTraverser.ptr());
				aEnvironment.iArgumentStack.pushArgumentOnStack(argument.getCons());
				consTraverser.goNext();
			}
			if ((iFlags & Variable) != 0)
			{

				//LispString res;

				//printf("Enter\n");
				ConsPointer head = new ConsPointer();
				head.setCons(aEnvironment.iListAtom.copy(false));
				head.getCons().getRestPointer().setCons(consTraverser.getCons());
				ConsPointer list = new ConsPointer();
				list.setCons(SubListCons.getInstance(head.getCons()));


				/*
				PrintExpression(res, list,aEnvironment,100);
				printf("before %s\n",res.String());
				*/

				aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, argument, list);

				/*
				PrintExpression(res, arg,aEnvironment,100);
				printf("after %s\n",res.String());
				*/

				aEnvironment.iArgumentStack.pushArgumentOnStack(argument.getCons());
				//printf("Leave\n");
			}
		}

		iCalledBuiltinFunction.evaluate(aEnvironment,stacktop);
		aResult.setCons(aEnvironment.iArgumentStack.getElement(stacktop).getCons());
		aEnvironment.iArgumentStack.popTo(stacktop);
	}

}


