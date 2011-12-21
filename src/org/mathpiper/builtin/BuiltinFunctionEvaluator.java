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

    public void evaluate(Environment aEnvironment, int aStackTop, Cons aArguments) throws Exception {
        Cons[] argumentsResultArray = null;


        Cons arguments = null;

        String functionName = iCalledBuiltinFunction.functionName;

        /*Trace code*/
        if (isTraced(functionName)) {
            

            arguments = SublistCons.getInstance(aEnvironment, aArguments);
            if (arguments.car() instanceof Cons) {
                Cons sub = (Cons) arguments.car();
                if (sub.car() instanceof String) {
                    functionName = (String) sub.car();
                }
            }//end function.

            if (Evaluator.isTraceFunction(functionName)) {
                showFlag = true;
                Evaluator.traceShowEnter(aEnvironment, arguments, "builtin");
            } else {
                showFlag = false;
            }//end else.
            arguments = null;

            //Creat an array which holds pointers to each argument.  This will be used for printing the arguments.
            if (iNumberOfArguments == 0) {
                argumentsResultArray = null;
            } else {
                if(iNumberOfArguments <= 0) LispError.lispAssert(aEnvironment, aStackTop);
                argumentsResultArray = new Cons[iNumberOfArguments];
            }//end if.
        }//end if.



        if ((iFlags & Variable) == 0) { //This function has a fixed number of arguments.

            //1 is being added to the number of arguments to take into account
            // the function name that is at the beginning of the argument list.

            arguments = SublistCons.getInstance(aEnvironment, aArguments);


            LispError.checkNumberOfArguments(aStackTop, iNumberOfArguments + 1, aArguments, aEnvironment);
        }

        int stackTop = aEnvironment.iArgumentStack.getStackTopIndex();

        // Push a place holder for the result and initialize it to the function name for error reporting purposes.
        aEnvironment.iArgumentStack.pushArgumentOnStack(aArguments, aStackTop, aEnvironment);

        Cons argumentsConsTraverser = aArguments;

        //Strip the function name from the head of the list.
        argumentsConsTraverser = argumentsConsTraverser.cdr();

        int i;
        int numberOfArguments = iNumberOfArguments;

        if ((iFlags & Variable) != 0) {//This function has a variable number of arguments.
            numberOfArguments--;
        }//end if.

        Cons argumentResult;

        // Walk over all arguments, evaluating them only if this is a function. *****************************************************

        if ((iFlags & Macro) != 0) {//This is a macro, not a function. Don't evaluate arguments.

            for (i = 0; i < numberOfArguments; i++) {
                //Push all arguments on the stack.
                if(argumentsConsTraverser == null) LispError.throwError(aEnvironment, aStackTop, LispError.WRONG_NUMBER_OF_ARGUMENTS, "The number of arguments passed in was " + numberOfArguments);

                if (isTraced(functionName) && argumentsResultArray != null && showFlag) {

                    argumentsResultArray[i] = argumentsConsTraverser.copy(false);
                }

                aEnvironment.iArgumentStack.pushArgumentOnStack(argumentsConsTraverser.copy(false), aStackTop, aEnvironment);

                argumentsConsTraverser = argumentsConsTraverser.cdr();
            }

            if ((iFlags & Variable) != 0) {//This macro has a variable number of arguments.
                Cons head = aEnvironment.iListAtom.copy(false);
                head.setCdr(argumentsConsTraverser);
                aEnvironment.iArgumentStack.pushArgumentOnStack(SublistCons.getInstance(aEnvironment, head), aStackTop, aEnvironment);
            }//end if.

        } else {//This is a function, not a macro. Evaluate arguments.

            for (i = 0; i < numberOfArguments; i++) {
                
                if(argumentsConsTraverser == null) LispError.throwError(aEnvironment, aStackTop, LispError.WRONG_NUMBER_OF_ARGUMENTS, "The number of arguments passed in was " + numberOfArguments);

                int stackTopArgs = aEnvironment.iArgumentStack.getStackTopIndex();
                aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, argumentsConsTraverser);
                argumentResult = aEnvironment.iArgumentStack.getElement(stackTopArgs, aStackTop, aEnvironment);
                aEnvironment.iArgumentStack.popTo(stackTopArgs, aStackTop, aEnvironment);

                if (isTraced(functionName) && argumentsResultArray != null && showFlag) {

                    argumentsResultArray[i] = argumentResult.copy(false);
                }

                aEnvironment.iArgumentStack.pushArgumentOnStack(argumentResult, aStackTop, aEnvironment);
                
                argumentsConsTraverser = argumentsConsTraverser.cdr();
            }//end for.

            if ((iFlags & Variable) != 0) {//This function has a variable number of arguments.

                //LispString res;

                //printf("Enter\n");


                Cons head = aEnvironment.iListAtom.copy(false);
                head.setCdr(argumentsConsTraverser);
                Cons list = SublistCons.getInstance(aEnvironment, head);


                /*
                PrintExpression(res, list,aEnvironment,100);
                printf("before %s\n",res.String());
                 */
                int stackTopArgs = aEnvironment.iArgumentStack.getStackTopIndex();
                aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, list);
                argumentResult = aEnvironment.iArgumentStack.getElement(stackTopArgs, aStackTop, aEnvironment);
                aEnvironment.iArgumentStack.popTo(stackTopArgs, aStackTop, aEnvironment);

                /*
                PrintExpression(res, arg,aEnvironment,100);
                printf("after %s\n",res.String());
                 */

                aEnvironment.iArgumentStack.pushArgumentOnStack(argumentResult, aStackTop, aEnvironment);
                //printf("Leave\n");
                                    /*Trace code */

            }//end if.
        }//end else.


        /*Trace code */
        if (isTraced(functionName) && argumentsResultArray != null && showFlag == true) {

            Cons traceArgument = aArguments;

            traceArgument = traceArgument.cdr();

            int parameterIndex = 1;
            if ((iFlags & Variable) != 0) {//This function has a  variable number of arguments.

                while (traceArgument != null) {
                    Evaluator.traceShowArg(aEnvironment, AtomCons.getInstance(aEnvironment, aStackTop, "parameter" + parameterIndex++), traceArgument);
                    traceArgument = traceArgument.cdr();
                }//end while.

            } else {
                for (i = 0; i < argumentsResultArray.length; i++) {

                    Evaluator.traceShowArg(aEnvironment, AtomCons.getInstance(aEnvironment, aStackTop, "parameter" + parameterIndex++), argumentsResultArray[i]);

                    traceArgument = traceArgument.cdr();
                }//end for.

            }

        }//end if.


        iCalledBuiltinFunction.evaluate(aEnvironment, stackTop); //********************** built in function is called here.


        Cons aResult = aEnvironment.iArgumentStack.getElement(stackTop, aStackTop, aEnvironment);

        if (isTraced(functionName) && showFlag == true) {

            arguments = SublistCons.getInstance(aEnvironment, aArguments);
            String localVariables = aEnvironment.getLocalVariables(aStackTop);
            Evaluator.traceShowLeave(aEnvironment, aResult, arguments, "builtin", localVariables);
            arguments = null;
        }//end if.

        //aEnvironment.iArgumentStack.popTo(stackTop, aStackTop, aEnvironment);

    }
}


