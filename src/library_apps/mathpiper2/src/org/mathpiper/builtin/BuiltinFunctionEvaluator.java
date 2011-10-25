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

import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.SublistCons;

public class BuiltinFunctionEvaluator extends Evaluator {
    // FunctionFlags can be ORed when passed to the constructor of this function

    public static int Function = 0;    // Function: evaluate arguments. todo:tk:not used.
    public static int Macro = 1;       // Function: don't evaluate arguments
    public static int Fixed = 0;     // fixed number of arguments. todo:tk:not used.
    public static int Variable = 2;  // variable number of arguments
    BuiltinFunction iCalledBuiltinFunction;
    int iNumberOfArguments;
    int iFlags;
    boolean showFlag = false;

    public BuiltinFunctionEvaluator(BuiltinFunction aCalledBuiltinFunction, int aNumberOfArguments, int aFlags) {
        iCalledBuiltinFunction = aCalledBuiltinFunction;
        iNumberOfArguments = aNumberOfArguments;
        iFlags = aFlags;
    }

    public Cons evaluate(Environment aEnvironment, int aStackTop, Cons aArgumentsPointer) throws Exception {
        Cons[] argumentsResultPointerArray = null;


        Cons argumentsPointer = null;

        String functionName = iCalledBuiltinFunction.functionName;

        /*Trace code*/
        if (isTraced(functionName)) {
            

            argumentsPointer = SublistCons.getInstance(aEnvironment, aArgumentsPointer);
            if (argumentsPointer.car() instanceof Cons) {
                Cons sub = (Cons) argumentsPointer.car();
                if (sub.car() instanceof String) {
                    functionName = (String) sub.car();
                }
            }//end function.

            if (Evaluator.isTraceFunction(functionName)) {
                showFlag = true;
                Evaluator.traceShowEnter(aEnvironment, argumentsPointer, "builtin");
            } else {
                showFlag = false;
            }//end else.
            argumentsPointer = null;

            //Creat an array which holds pointers to each argument.  This will be used for printing the arguments.
            if (iNumberOfArguments == 0) {
                argumentsResultPointerArray = null;
            } else {
                if(iNumberOfArguments <= 0) LispError.lispAssert(aEnvironment, aStackTop);
                argumentsResultPointerArray = new Cons[iNumberOfArguments];
            }//end if.
        }//end if.



        if ((iFlags & Variable) == 0) { //This function has a fixed number of arguments.

            //1 is being added to the number of arguments to take into account
            // the function name that is at the beginning of the argument list.

            argumentsPointer = SublistCons.getInstance(aEnvironment, aArgumentsPointer);


            LispError.checkNumberOfArguments(aStackTop, iNumberOfArguments + 1, aArgumentsPointer, aEnvironment, functionName);
        }

        int stackTop = aEnvironment.iArgumentStack.getStackTopIndex();

        // Push a place holder for the result and initialize it to the function name for error reporting purposes.
        aEnvironment.iArgumentStack.pushArgumentOnStack(aArgumentsPointer, aStackTop, aEnvironment);

        Cons argumentsConsTraverser = aArgumentsPointer;

        //Strip the function name from the head of the list.
        argumentsConsTraverser = argumentsConsTraverser.cdr();

        int i;
        int numberOfArguments = iNumberOfArguments;

        if ((iFlags & Variable) != 0) {//This function has a  variable number of arguments.
            numberOfArguments--;
        }//end if.

        Cons argumentResultPointer;

        // Walk over all arguments, evaluating them only if this is a function. *****************************************************

        if ((iFlags & Macro) != 0) {//This is a macro, not a function.

            for (i = 0; i < numberOfArguments; i++) {
                //Push all arguments on the stack.
                if(argumentsConsTraverser == null) LispError.throwError(aEnvironment, aStackTop, LispError.WRONG_NUMBER_OF_ARGUMENTS, "The number of arguments passed in was " + numberOfArguments, "INTERNAL");

                if (isTraced(functionName) && argumentsResultPointerArray != null && showFlag) {

                    argumentsResultPointerArray[i] = argumentsConsTraverser.copy(aEnvironment, false);
                }

                aEnvironment.iArgumentStack.pushArgumentOnStack(argumentsConsTraverser.copy(aEnvironment, false), aStackTop, aEnvironment);
                argumentsConsTraverser = argumentsConsTraverser.cdr();
            }

            if ((iFlags & Variable) != 0) {//This macro has a variable number of arguments.
                Cons head = aEnvironment.iListAtom.copy(aEnvironment, false);
                head.setCdr(argumentsConsTraverser);
                aEnvironment.iArgumentStack.pushArgumentOnStack(SublistCons.getInstance(aEnvironment, head), aStackTop, aEnvironment);
            }//end if.

        } else {//This is a function, not a macro.

            for (i = 0; i < numberOfArguments; i++) {
                if(argumentsConsTraverser == null) LispError.throwError(aEnvironment, aStackTop, LispError.WRONG_NUMBER_OF_ARGUMENTS, "The number of arguments passed in was " + numberOfArguments, "INTERNAL");
                if(argumentsConsTraverser == null) LispError.throwError(aEnvironment, aStackTop, LispError.WRONG_NUMBER_OF_ARGUMENTS, "The number of arguments passed in was " + numberOfArguments, "INTERNAL");
                argumentResultPointer = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, argumentsConsTraverser);

                if (isTraced(functionName) && argumentsResultPointerArray != null && showFlag) {

                    argumentsResultPointerArray[i] = argumentResultPointer.copy(aEnvironment, false);
                }

                aEnvironment.iArgumentStack.pushArgumentOnStack(argumentResultPointer, aStackTop, aEnvironment);
                argumentsConsTraverser = argumentsConsTraverser.cdr();
            }//end for.

            if ((iFlags & Variable) != 0) {//This function has a variable number of arguments.

                //LispString res;

                //printf("Enter\n");


                Cons head = aEnvironment.iListAtom.copy(aEnvironment, false);
                head.setCdr(argumentsConsTraverser);
                Cons listPointer = SublistCons.getInstance(aEnvironment, head);


                /*
                PrintExpression(res, list,aEnvironment,100);
                printf("before %s\n",res.String());
                 */

                argumentResultPointer = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, listPointer);

                /*
                PrintExpression(res, arg,aEnvironment,100);
                printf("after %s\n",res.String());
                 */

                aEnvironment.iArgumentStack.pushArgumentOnStack(argumentResultPointer, aStackTop, aEnvironment);
                //printf("Leave\n");
                                    /*Trace code */

            }//end if.
        }//end else.


        /*Trace code */
        if (isTraced(functionName) && argumentsResultPointerArray != null && showFlag == true) {

            ConsPointer traceArgumentPointer = new ConsPointer( aArgumentsPointer);

            traceArgumentPointer.goNext(aStackTop, aEnvironment);

            int parameterIndex = 1;
            if ((iFlags & Variable) != 0) {//This function has a  variable number of arguments.

                while (traceArgumentPointer.getCons() != null) {
                    Evaluator.traceShowArg(aEnvironment, AtomCons.getInstance(aEnvironment, aStackTop, "parameter" + parameterIndex++), traceArgumentPointer.getCons());
                    traceArgumentPointer.goNext(aStackTop, aEnvironment);
                }//end while.

            } else {
                for (i = 0; i < argumentsResultPointerArray.length; i++) {

                    /*      if (argumentsResultPointerArray[i] == null) {
                    argumentsResultPointerArray[i] = new ConsPointer(AtomCons.getInstance(aEnvironment, "NULL"));
                    }*/

                    Evaluator.traceShowArg(aEnvironment, AtomCons.getInstance(aEnvironment, aStackTop, "parameter" + parameterIndex++), argumentsResultPointerArray[i]);

                    traceArgumentPointer.goNext(aStackTop, aEnvironment);
                }//end for.

            }

        }//end if.


        iCalledBuiltinFunction.evaluate(aEnvironment, stackTop); //********************** built in function is called here.


        Cons aResultPointer = aEnvironment.iArgumentStack.getElement(stackTop, aStackTop, aEnvironment);

        if (isTraced(functionName) && showFlag == true) {

            argumentsPointer = SublistCons.getInstance(aEnvironment, aArgumentsPointer);
            String localVariables = aEnvironment.getLocalVariables(aStackTop);
            Evaluator.traceShowLeave(aEnvironment, aResultPointer, argumentsPointer, "builtin", localVariables);
            argumentsPointer = null;
        }//end if.

        aEnvironment.iArgumentStack.popTo(stackTop, aStackTop, aEnvironment);

        return aResultPointer;
    }
}


