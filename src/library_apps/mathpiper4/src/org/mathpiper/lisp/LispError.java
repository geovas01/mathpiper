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
package org.mathpiper.lisp;


import java.util.HashMap;
import java.util.Map;

import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.cons.Cons;

public class LispError {

    public static final int TODO = -1;
    public static final int NONE = 0;
    public static final int INVALID_ARGUMENT = 1;
    public static final int WRONG_NUMBER_OF_ARGUMENTS = 2;
    public static final int NOT_A_LIST = 3;
    public static final int NOT_LONG_ENOUGH = 4;
    public static final int INVALID_STACK = 5;
    public static final int QUITTING = 6;
    public static final int NOT_ENOUGH_MEMORY = 7;
    public static final int INVALID_TOKEN = 8;
    public static final int INVALID_EXPRESSION = 9;
    public static final int UNPRINTABLE_TOKEN = 10;
    public static final int FILE_NOT_FOUND = 11;
    public static final int READING_FILE = 12;
    public static final int CREATING_USER_FUNCTION = 13;
    public static final int CREATING_RULE = 14;
    public static final int ARITY_ALREADY_DEFINED = 15;
    public static final int COMMENT_TO_END_OF_FILE = 16;
    public static final int NOT_A_STRING = 17;
    public static final int NOT_AN_INTEGER = 18;
    public static final int PARSING_INPUT = 19;
    public static final int MAXIMUM_RECURSE_DEPTH_REACHED = 20;
    public static final int DEF_FILE_ALREADY_CHOSEN = 21;
    public static final int DIVIDE_BY_ZERO = 22;
    public static final int NOT_AN_INFIX_OPERATOR = 23;
    public static final int IS_NOT_INFIX = 24;
    public static final int SECURITY_BREACH = 25;
    public static final int LIBRARY_NOT_FOUND = 26;
    public static final int USER_INTERRUPT = 27;
    public static final int NON_BOOLEAN_PREDICATE_IN_PATTERN = 28;
    public static final int GENERIC_FORMAT = 29;
    public static final int LIST_LENGTHS_MUST_BE_EQUAL = 30;
    public static final int MAXIMUM_NUMBER_OF_ERRORS = 31;


    public static String errorString(int aError) throws Throwable {
        //lispAssert(aError >= 0 && aError < MAXIMUM_NUMBER_OF_ERRORS, aEnvironment, aStackTop);

        if (aError < 0 || aError >= MAXIMUM_NUMBER_OF_ERRORS) {
            throw new EvaluationException("Maximum number of errors exceeded.", "", -1, -1, -1);
        }


        //    switch (aError)
        {
            if (aError == NONE) {
                return "No error.";
            }
            if (aError == INVALID_ARGUMENT) {
                return "Invalid argument.";
            }
            if (aError == WRONG_NUMBER_OF_ARGUMENTS) {
                return "Wrong number of arguments.";
            }
            if (aError == NOT_A_LIST) {
                return "Argument is not a list.";
            }
            if (aError == NOT_LONG_ENOUGH) {
                return "List not long enough.";
            }
            if (aError == INVALID_STACK) {
                return "Invalid stack.";
            }
            if (aError == QUITTING) {
                return "Quitting...";
            }
            if (aError == NOT_ENOUGH_MEMORY) {
                return "Not enough memory.";
            }
            if (aError == INVALID_TOKEN) {
                return "Empty token during parsing.";
            }
            if (aError == INVALID_EXPRESSION) {
                return "Error parsing expression.";
            }
            if (aError == UNPRINTABLE_TOKEN) {
                return "Unprintable atom.";
            }
            if (aError == FILE_NOT_FOUND) {
                return "File not found.";
            }
            if (aError == READING_FILE) {
                return "Error reading file.";
            }
            if (aError == CREATING_USER_FUNCTION) {
                return "Could not create user function.";
            }
            if (aError == CREATING_RULE) {
                return "Could not create rule.";
            }
            if (aError == ARITY_ALREADY_DEFINED) {
                return "Rule base with this arity already defined.";
            }
            if (aError == COMMENT_TO_END_OF_FILE) {
                return "Reaching end of file within a comment block.";
            }
            if (aError == NOT_A_STRING) {
                return "Argument is not a string.";
            }
            if (aError == NOT_AN_INTEGER) {
                return "Argument is not an integer.";
            }
            if (aError == PARSING_INPUT) {
                return "Error while parsing input.";
            }
            if (aError == MAXIMUM_RECURSE_DEPTH_REACHED) {
                return "Max evaluation stack depth reached.\nPlease use MaxEvalDepth to increase the stack size as needed.";
            }
            if (aError == DEF_FILE_ALREADY_CHOSEN) {
                return "DefFile already chosen for function.";
            }
            if (aError == DIVIDE_BY_ZERO) {
                return "Divide by zero.";
            }
            if (aError == NOT_AN_INFIX_OPERATOR) {
                return "Trying to make a non-infix operator right-associative.";
            }
            if (aError == IS_NOT_INFIX) {
                return "Trying to get precedence of non-infix operator.";
            }
            if (aError == SECURITY_BREACH) {
                return "Trying to perform an insecure action.";
            }
            if (aError == LIBRARY_NOT_FOUND) {
                return "Could not find library.";
            }
            if (aError == USER_INTERRUPT) {
                return "User halted calculation.";
            }
            if (aError == NON_BOOLEAN_PREDICATE_IN_PATTERN) {
                return "Predicate doesn't evaluate to a boolean in pattern.";
            }
            if (aError == GENERIC_FORMAT) {
                return "Generic format.";
            }
            if (aError == LIST_LENGTHS_MUST_BE_EQUAL) {
                return "List lengths must be equal.";
            }
        }
        return "Unspecified Error.";
    }

    //========================================

    public static void throwError(Environment aEnvironment, int aStackTop, int aError, Object aErrorMessage) throws Throwable {

        throwError(aEnvironment, aStackTop, aError, aErrorMessage, aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1, aEnvironment.getCurrentInput().iStatus.getLineIndex());
    }

    public static void throwError(Environment aEnvironment, int aStackTop, int aError, Object aErrorMessage, int lineNumber, int tokenStartIndex, int tokenEndIndex) throws Throwable {

            if(aError == LispError.INVALID_ARGUMENT)
            {
                aErrorMessage = "The bad argument is <" + aErrorMessage + ">";
            }
            String errorMessage = errorString(aError) + " Extra information: <" + aErrorMessage + ">.";

            throwError(aEnvironment, aStackTop, errorMessage, lineNumber, tokenStartIndex, tokenEndIndex);

        
    }//end method.

    //========================================
    
    
    

    public static void throwError(Environment aEnvironment, int aStackTop, Object aErrorMessage) throws Throwable {
	Map<String,Integer> location = new HashMap<String,Integer>();
	location.put("lineNumber", -1);
	location.put("startIndex", -1);
	location.put("endIndex", -1);
	throwError(aEnvironment, aStackTop, aErrorMessage, location.get("lineNumber"), location.get("startIndex"), location.get("endIndex"));
    }

    public static void throwError(Environment aEnvironment, int aStackTop, Object aErrorMessage, Map<String,Object> location) throws Throwable {
        if(location != null)
        {
            throwError(aEnvironment, aStackTop, aErrorMessage, (Integer) location.get("lineNumber"), (Integer) location.get("startIndex"), (Integer) location.get("endIndex"));
        }
        else
        {
            throwError(aEnvironment, aStackTop, aErrorMessage, -1, -1, -1);
        }
    }
    
    public static void throwError(Environment aEnvironment, int aStackTop, Object aErrorMessage, int lineNumber, int tokenStartIndex, int tokenEndIndex) throws Throwable {
    
            String stackTrace = "";

            if (Evaluator.isStackTraced() && aStackTop >= 0) {
                stackTrace = aEnvironment.dumpStacks(aEnvironment, aStackTop);
            }


            if (aStackTop == -1) {
                // String errorMessage = "Error encountered during initialization: "; // TODO: Find better way to report errors that occur during system initialization.
                String errorMessage = "Error: ";
                throw new EvaluationException(errorMessage + aErrorMessage + stackTrace,  aEnvironment.getCurrentInput().iStatus.getSourceName(),  lineNumber, tokenStartIndex, tokenEndIndex);
            } else if (aStackTop == -2) {
                throw new EvaluationException("Error: " + aErrorMessage + stackTrace,  aEnvironment.getCurrentInput().iStatus.getSourceName(),  lineNumber, tokenStartIndex, tokenEndIndex);
            } else {
        	Cons arguments = null;
        	try{
        	    arguments = BuiltinFunction.getArgument(aEnvironment, aStackTop, 0);
        	}
        	catch(Throwable t)
        	{
        	    //Eat the exception.
        	}
        	
                if (arguments == null) {
                    throw new EvaluationException("Error in compiled code: " + aErrorMessage + "; " + stackTrace,  aEnvironment.getCurrentInput().iStatus.getSourceName(),  lineNumber, tokenStartIndex, tokenEndIndex);
                } else {
                    //TODO FIXME          ShowStack(aEnvironment);
                    aErrorMessage = aErrorMessage + " " + showFunctionError(arguments, aEnvironment);
                }


                throw new EvaluationException(aErrorMessage + stackTrace, aEnvironment.getCurrentInput().iStatus.getSourceName(),  lineNumber, tokenStartIndex, tokenEndIndex);

            }
        
    }//end method.


    //========================================
    public static void throwError(Environment aEnvironment, int aStackTop, int errNo) throws Throwable {
        throwError(aEnvironment, aStackTop, errNo, aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1, aEnvironment.getCurrentInput().iStatus.getLineIndex());
    }

    private static void throwError(Environment aEnvironment, int aStackTop, int errNo, int lineNumber, int tokenStartIndex, int tokenEndIndex) throws Throwable {

            String stackTrace = "";

            if (Evaluator.isStackTraced() && aStackTop >= 0) {
                stackTrace = aEnvironment.dumpStacks(aEnvironment, aStackTop);
            }

            if (aStackTop == -1) {
                // String errorMessage = "Error encountered during initialization: "; // TODO: Find better way to report errors that occur during system initialization.
                String errorMessage = "Error: ";
                throw new EvaluationException(errorMessage + errorString(errNo) + stackTrace,  aEnvironment.getCurrentInput().iStatus.getSourceName(), lineNumber, tokenStartIndex, tokenEndIndex);
            } else if (aStackTop == -2) {
                throw new EvaluationException(errorString(errNo) + stackTrace,  aEnvironment.getCurrentInput().iStatus.getSourceName(),  lineNumber, tokenStartIndex, tokenEndIndex);
            } else {
                Cons arguments = BuiltinFunction.getArgument(aEnvironment, aStackTop, 0);
                if (arguments == null) {
                    throw new EvaluationException(errorString(errNo) + " in compiled code." + stackTrace,  aEnvironment.getCurrentInput().iStatus.getSourceName(),  lineNumber, tokenStartIndex, tokenEndIndex);
                } else {
                    String error = errorString(errNo) + " ";
                    error = error + showFunctionError(arguments, aEnvironment);
                    throw new EvaluationException(error + stackTrace, aEnvironment.getCurrentInput().iStatus.getSourceName(),  lineNumber, tokenStartIndex, tokenEndIndex);
                }
            }
        
    }

    //========================================
    public static void raiseError(String errorMessage, int aStackTop, Environment aEnvironment) throws Throwable {
        raiseError(errorMessage,  aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1, aEnvironment.getCurrentInput().iStatus.getLineIndex(), aStackTop, aEnvironment);
    }

    public static void raiseError(String errorMessage, int lineNumber, int tokenStartIndex, int tokenEndIndex, int aStackTop, Environment aEnvironment) throws Throwable {
        throwError( aEnvironment, aStackTop, errorMessage, lineNumber, tokenStartIndex, tokenEndIndex);
    }
    
    
    //========================================

    public static void raiseParseError(String aErrorMessage, int lineNumber, int tokenStartIndex, int tokenEndIndex, int aStackTop, Environment aEnvironment) throws Throwable {
        String stackTrace = "";

        if (Evaluator.isStackTraced() && aStackTop >= 0) {
            stackTrace = aEnvironment.dumpStacks(aEnvironment, aStackTop);
        }


           //String functionName = aEnvironment.getCurrentInput().iStatus.
            EvaluationException exception =  new EvaluationException("ParseError", "Error encountered during parsing: " + aErrorMessage + stackTrace,  aEnvironment.getCurrentInput().iStatus.getSourceName(),  lineNumber, tokenStartIndex, tokenEndIndex,aEnvironment.getCurrentInput().iStatus.getSourceName(), aEnvironment.getCurrentInput().iStatus.getSourceName());

            throw exception;
            
    }    

    //========================================

    public static void checkNumberOfArguments(int aStackTop, int n, Cons aArguments, Environment aEnvironment) throws Throwable {
        int nrArguments = Utility.listLength(aEnvironment, aStackTop, aArguments);
        if (nrArguments != n) {
            errorNumberOfArguments(n - 1, nrArguments - 1, aArguments, aEnvironment, aStackTop);
        }
    }


    //========================================
    public static void errorNumberOfArguments(int needed, int passed, Cons aArguments, Environment aEnvironment, int aStackTop) throws Throwable {
        errorNumberOfArguments(needed, passed, aArguments, aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1, aEnvironment.getCurrentInput().iStatus.getLineIndex(), aEnvironment, aStackTop);
    }

    private static void errorNumberOfArguments(int needed, int passed, Cons aArguments,  int lineNumber, int tokenStartIndex, int tokenEndIndex, Environment aEnvironment, int aStackTop) throws Throwable {
        String stackTrace = "";

        if (Evaluator.isStackTraced() && aStackTop >= 0) {
            stackTrace = aEnvironment.dumpStacks(aEnvironment, aStackTop);
        }

        if (aArguments == null) {
            throw new EvaluationException("Error in compiled code." + stackTrace,  aEnvironment.getCurrentInput().iStatus.getSourceName(),  lineNumber, tokenStartIndex, tokenEndIndex);
        } else {
            //TODO FIXME      ShowStack(aEnvironment);
            String error = showFunctionError(aArguments, aEnvironment) + "expected " + needed + " arguments, got " + passed + ". ";
            throw new EvaluationException(error + stackTrace,  aEnvironment.getCurrentInput().iStatus.getSourceName(), lineNumber, tokenStartIndex, tokenEndIndex);

            /*TODO FIXME
            LispChar str[20];
            aEnvironment.iErrorOutput.Write("expected ");
            InternalIntToAscii(str,needed);
            aEnvironment.iErrorOutput.Write(str);
            aEnvironment.iErrorOutput.Write(" arguments, got ");
            InternalIntToAscii(str,passed);
            aEnvironment.iErrorOutput.Write(str);
            aEnvironment.iErrorOutput.Write("\n");
            LispError.check(passed == needed,LispError.WRONG_NUMBER_OF_ARGUMENTS);
             */
        }
    }
    //========================================

    public static String showFunctionError(Cons aArguments, Environment aEnvironment) throws Throwable {
    	/*
        if (aArguments == null) {
            return "Error in compiled code. ";
        } else {
            String string = (String) aArguments.car();
            if (string != null) {
                return "In function: " + string + ". ";// + "\" : " + aEnvironment.iCurrentInput.iStatus.getFileName() + ", " + " Line number: " + aEnvironment.iCurrentInput.iStatus.getLineNumber() + ", " + " Line index: " + aEnvironment.iCurrentInput.iStatus.getLineNumber() + ". ";
            }
        }
        return "[Atom]";
        */
    	return "";
    }



    public static void lispAssert(Environment aEnvironment, int aStackTop) throws Throwable {
  
            Map<String,Object> location = new HashMap<String,Object>();
            
            location.put("lineNumber", -1);
            location.put("startIndex", -1);
            location.put("endIndex", -1);
            
            throwError(aEnvironment, aStackTop, "Assertion error.", location);
     
    }


    public static void checkArgument(Environment aEnvironment, int aStackTop, int aArgNr) throws Throwable {
        checkArgumentTypeWithError(aEnvironment, aStackTop, aArgNr, "");
    }


    public static void checkIsList(Environment aEnvironment, int aStackTop, Cons evaluated, int aArgNr) throws Throwable {
        if(! Utility.isSublist(evaluated)) checkArgumentTypeWithError(aEnvironment, aStackTop, aArgNr, "argument is not a list.");
    }


    public static void checkIsString(Environment aEnvironment, int aStackTop, Cons evaluated, int aArgNr) throws Throwable {
        if(! Utility.isString(evaluated.car())) checkArgumentTypeWithError(aEnvironment, aStackTop, aArgNr, "argument is not a string.");
    }


    //========================================
    public static void checkArgumentTypeWithError(Environment aEnvironment, int aStackTop, int aArgNr, String aErrorDescription) throws Throwable {
        checkArgumentTypeWithError(aEnvironment, aStackTop, aArgNr, aErrorDescription, aEnvironment.getCurrentInput().iStatus.getLineNumber(), -1, aEnvironment.getCurrentInput().iStatus.getLineIndex());
    }

    private static void checkArgumentTypeWithError(Environment aEnvironment, int aStackTop, int aArgNr, String aErrorDescription, int lineNumber, int startIndex, int endIndex) throws Throwable {

            String stackTrace = "";

            if (Evaluator.isStackTraced() && aStackTop >= 0) {
                stackTrace = aEnvironment.dumpStacks(aEnvironment, aStackTop);
            }

            Cons arguments = BuiltinFunction.getArgument(aEnvironment, aStackTop, 0);
            if (arguments == null) {
                throw new EvaluationException("Error in compiled code." + stackTrace, aEnvironment.getCurrentInput().iStatus.getSourceName(), lineNumber, startIndex, endIndex);
            } else {
                String error = "";
                error = error + showFunctionError(arguments, aEnvironment) + "\nbad argument number " + aArgNr + "(counting from 1) : \n" + aErrorDescription + "\n";
                Cons arg = BuiltinFunction.getArgument(aEnvironment, aStackTop, arguments, aArgNr);
                String strout;

                error = error + "The offending argument ***( ";
                strout = Utility.printMathPiperExpression(aStackTop, arg, aEnvironment, 60);
                error = error + strout;

                
                Cons eval = aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, arg);
                error = error + " )*** evaluated to ***( ";
                strout = Utility.printMathPiperExpression(aStackTop, eval, aEnvironment, 60);
                error = error + strout;
                error = error + " )***\n";

                throw new EvaluationException(error + stackTrace,  aEnvironment.getCurrentInput().iStatus.getSourceName(), lineNumber, startIndex, endIndex);
            }//end else.

    }//end method.

    //========================================

}
