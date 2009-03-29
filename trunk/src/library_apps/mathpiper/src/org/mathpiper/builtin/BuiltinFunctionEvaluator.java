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
import org.mathpiper.builtin.BuiltinFunction;

import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.SubListCons;

public class BuiltinFunctionEvaluator extends Evaluator {
    // FunctionFlags can be ORed when passed to the constructor of this function

    public static int Function = 0;    // Function: evaluate arguments
    public static int Macro = 1;       // Function: don't evaluate arguments
    public static int Fixed = 0;     // fixed number of arguments
    public static int Variable = 2;  // variable number of arguments
    BuiltinFunction iCalledBuiltinFunction;
    int iNumberOfArguments;
    int iFlags;

    public BuiltinFunctionEvaluator(BuiltinFunction aCalledBuiltinFunction, int aNumberOfArguments, int aFlags) {
        iCalledBuiltinFunction = aCalledBuiltinFunction;
        iNumberOfArguments = aNumberOfArguments;
        iFlags = aFlags;
    }

    public void evaluate(Environment aEnvironment, ConsPointer aResult, ConsPointer aArgumentsPointer) throws Exception {

        /*Trace code*/
        if (isTraced()) {
            ConsPointer argumentsPointer = new ConsPointer();
            argumentsPointer.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
            Evaluator.traceShowEnter(aEnvironment, argumentsPointer);
            argumentsPointer.setCons(null);
        }

        if ((iFlags & Variable) == 0) {
            LispError.checkNumberOfArguments(iNumberOfArguments + 1, aArgumentsPointer, aEnvironment);
        }

        int stackTop = aEnvironment.iArgumentStack.getStackTopIndex();

        // Push a place holder for the getResult: push full expression so it is available for error reporting
        aEnvironment.iArgumentStack.pushArgumentOnStack(aArgumentsPointer.getCons());

        ConsTraverser consTraverser = new ConsTraverser(aArgumentsPointer);
        consTraverser.goNext();

        int i;
        int numberOfArguments = iNumberOfArguments;

        if ((iFlags & Variable) != 0) {
            numberOfArguments--;
        }

        // Walk over all arguments, evaluating them as necessary
        if ((iFlags & Macro) != 0) {

            for (i = 0; i < numberOfArguments; i++) {
                LispError.check(consTraverser.getCons() != null, LispError.KLispErrWrongNumberOfArgs);
                aEnvironment.iArgumentStack.pushArgumentOnStack(consTraverser.getCons().copy(false));
                consTraverser.goNext();
            }

            if ((iFlags & Variable) != 0) {
                ConsPointer head = new ConsPointer();
                head.setCons(aEnvironment.iListAtom.copy(false));
                head.getCons().getRestPointer().setCons(consTraverser.getCons());
                aEnvironment.iArgumentStack.pushArgumentOnStack(SubListCons.getInstance(head.getCons()));
            }//end if.

        } else {
            ConsPointer argumentPointer = new ConsPointer();
            for (i = 0; i < numberOfArguments; i++) {
                LispError.check(consTraverser.getCons() != null, LispError.KLispErrWrongNumberOfArgs);
                LispError.check(consTraverser.ptr() != null, LispError.KLispErrWrongNumberOfArgs);
                aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, argumentPointer, consTraverser.ptr());
                aEnvironment.iArgumentStack.pushArgumentOnStack(argumentPointer.getCons());
                consTraverser.goNext();
            }

            if ((iFlags & Variable) != 0) {

                //LispString res;

                //printf("Enter\n");

                ConsPointer head = new ConsPointer();
                head.setCons(aEnvironment.iListAtom.copy(false));
                head.getCons().getRestPointer().setCons(consTraverser.getCons());
                ConsPointer listPointer = new ConsPointer();
                listPointer.setCons(SubListCons.getInstance(head.getCons()));


                /*
                PrintExpression(res, list,aEnvironment,100);
                printf("before %s\n",res.String());
                 */

                aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, argumentPointer, listPointer);

                /*
                PrintExpression(res, arg,aEnvironment,100);
                printf("after %s\n",res.String());
                 */

                aEnvironment.iArgumentStack.pushArgumentOnStack(argumentPointer.getCons());
            //printf("Leave\n");
                                    /*Trace code */

            }//end if.
        }//end else.


        /*Trace code */  // todo:tk:This section of code generates illegal argument errors.
        /*if (isTraced()) {
        ConsPointer subList = BuiltinFunction.getArgumentPointer(aEnvironment, stackTop, 1).getCons().getSublistPointer();
        if (subList != null) {
            int argumentsCounter = 0;
            ConsTraverser argumentsConsTraverser = new ConsTraverser(subList);
            argumentsConsTraverser.goNext();
            while (argumentsConsTraverser.getCons() != null)
            {
                aEnvironment.iCurrentPrinter.print(argumentsConsTraverser.ptr(), aEnvironment.iCurrentOutput, aEnvironment);
                argumentsConsTraverser.goNext();
                argumentsCounter++;
            }
                ConsPointer[] argumentsFromStack = aEnvironment.iArgumentStack.getElements(argumentsCounter);

                //ConsTraverser consTraverser2 = new ConsTraverser(aArguments);
                ConsPointer iter2 = new ConsPointer(aArgumentsPointer.getCons());
                iter2.goNext();
                for (int index = 0; i < iNumberOfArguments; i++) {
                    Evaluator.traceShowArg(aEnvironment, iter2, argumentsFromStack[index]);

                    iter2.goNext();
                }//end if.
            }//end if.
        }//end if.*/

        iCalledBuiltinFunction.evaluate(aEnvironment, stackTop);
        aResult.setCons(aEnvironment.iArgumentStack.getElement(stackTop).getCons());

        if (isTraced()) {
            ConsPointer argumentsPointer = new ConsPointer();
            argumentsPointer.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
            Evaluator.traceShowLeave(aEnvironment, aResult, argumentsPointer);
            argumentsPointer.setCons(null);
        }//end if.

        aEnvironment.iArgumentStack.popTo(stackTop);
    }
}


