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

    public static int Function = 0;    // Function: evaluate arguments. todo:tk:not used.
    public static int Macro = 1;       // Function: don't evaluate arguments
    public static int Fixed = 0;     // fixed number of arguments. todo:tk:not used.
    public static int Variable = 2;  // variable number of arguments
    
    BuiltinFunction iCalledBuiltinFunction;
    
    int iNumberOfArguments;
    
    int iFlags;

    public BuiltinFunctionEvaluator(BuiltinFunction aCalledBuiltinFunction, int aNumberOfArguments, int aFlags) {
        iCalledBuiltinFunction = aCalledBuiltinFunction;
        iNumberOfArguments = aNumberOfArguments;
        iFlags = aFlags;
    }

    public void evaluate(Environment aEnvironment, ConsPointer aResultPointer, ConsPointer aArgumentsPointer) throws Exception {

        /*Trace code*/
        if (isTraced()) {
            ConsPointer argumentsPointer = new ConsPointer();
            argumentsPointer.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
            Evaluator.traceShowEnter(aEnvironment, argumentsPointer,"builtin");
            argumentsPointer.setCons(null);
        }

        if ((iFlags & Variable) == 0) { //This function has a fixed number of arguments.

            //1 is being added to the number of arguments to take into account
            // the function name that is at the beginning of the argument list.
            LispError.checkNumberOfArguments(iNumberOfArguments + 1, aArgumentsPointer, aEnvironment);
        }

        int stackTop = aEnvironment.iArgumentStack.getStackTopIndex();

        // Push a place holder for the result: push full expression so it is available for error reporting
        aEnvironment.iArgumentStack.pushArgumentOnStack(aArgumentsPointer.getCons());

        ConsTraverser argumentsConsTraverser = new ConsTraverser(aArgumentsPointer);

        //Strip the function name from the head of the list.
        argumentsConsTraverser.goNext();

        int i;
        int numberOfArguments = iNumberOfArguments;

        if ((iFlags & Variable) != 0) {//This function has a  variable number of arguments.
            numberOfArguments--;
        }

        // Walk over all arguments, evaluating them as necessary *****************************************************
        if ((iFlags & Macro) != 0) {//This is a macro, not a function.

            for (i = 0; i < numberOfArguments; i++) {
                //Push all arguments on the stack.
                LispError.check(argumentsConsTraverser.getCons() != null, LispError.KLispErrWrongNumberOfArgs);
                aEnvironment.iArgumentStack.pushArgumentOnStack(argumentsConsTraverser.getCons().copy(false));
                argumentsConsTraverser.goNext();
            }

            if ((iFlags & Variable) != 0) {//This function has a variable number of arguments.
                ConsPointer head = new ConsPointer();
                head.setCons(aEnvironment.iListAtom.copy(false));
                head.getCons().getRestPointer().setCons(argumentsConsTraverser.getCons());
                aEnvironment.iArgumentStack.pushArgumentOnStack(SubListCons.getInstance(head.getCons()));
            }//end if.

        } else {//This is a function, not a macro.
            ConsPointer argumentPointer = new ConsPointer();

            for (i = 0; i < numberOfArguments; i++) {
                LispError.check(argumentsConsTraverser.getCons() != null, LispError.KLispErrWrongNumberOfArgs);
                LispError.check(argumentsConsTraverser.getPointer() != null, LispError.KLispErrWrongNumberOfArgs);
                aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, argumentPointer, argumentsConsTraverser.getPointer());
                aEnvironment.iArgumentStack.pushArgumentOnStack(argumentPointer.getCons());
                argumentsConsTraverser.goNext();
            }

            if ((iFlags & Variable) != 0) {

                //LispString res;

                //printf("Enter\n");

                ConsPointer head = new ConsPointer();
                head.setCons(aEnvironment.iListAtom.copy(false));
                head.getCons().getRestPointer().setCons(argumentsConsTraverser.getCons());
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
        if (isTraced()) {

                ConsPointer[] argumentsFromStack = aEnvironment.iArgumentStack.getElements(this.iNumberOfArguments);

                //ConsTraverser consTraverser2 = new ConsTraverser(aArguments);
                ConsPointer iter2 = new ConsPointer(aArgumentsPointer.getCons());
                iter2.goNext();
                for (int index = 0; i < iNumberOfArguments; i++) {
                    Evaluator.traceShowArg(aEnvironment, iter2, argumentsFromStack[index]);

                    iter2.goNext();
                }//end if.
        }//end if.

        
        iCalledBuiltinFunction.evaluate(aEnvironment, stackTop);
        aResultPointer.setCons(aEnvironment.iArgumentStack.getElement(stackTop).getCons());

        if (isTraced()) {
            ConsPointer argumentsPointer = new ConsPointer();
            argumentsPointer.setCons(SubListCons.getInstance(aArgumentsPointer.getCons()));
            Evaluator.traceShowLeave(aEnvironment, aResultPointer, argumentsPointer,"builtin");
            argumentsPointer.setCons(null);
        }//end if.

        aEnvironment.iArgumentStack.popTo(stackTop);
    }
}


