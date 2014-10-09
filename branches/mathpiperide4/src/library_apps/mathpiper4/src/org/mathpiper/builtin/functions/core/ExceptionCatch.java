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
package org.mathpiper.builtin.functions.core;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.lisp.Environment;


/**
 *
 *  
 */
public class ExceptionCatch extends BuiltinFunction
{

    private ExceptionCatch()
    {
    }

    public ExceptionCatch(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable
    {
        try
        {
            //Return the first argument.
            setTopOfStack(aEnvironment, aStackTop, aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 1)));
        } catch (Throwable exception)
        {   //Return the second argument.
            //e.printStackTrace();
            //Boolean interrupted = Thread.currentThread().interrupted(); //Clear interrupted condition.
        	
        	if(exception instanceof EvaluationException)
        	{
        		EvaluationException evaluationException = (EvaluationException) exception;
        		
        		if(evaluationException.getType().equals("ParseError"))
            	{
            		throw evaluationException;
            	}
        	}
        	
            aEnvironment.iException = exception;
            setTopOfStack(aEnvironment, aStackTop, aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, aStackTop, getArgument(aEnvironment, aStackTop, 2)));
            aEnvironment.iException = null;
        }
    }
}



/*
%mathpiper_docs,name="ExceptionCatch",categories="Programming Functions;Error Reporting;Built In"
*CMD ExceptionCatch --- catches exceptions
*CORE
*CALL
	ExceptionCatch(expression, exceptionHandler)

*PARMS

{expression} -- expression to evaluate (causing potential error)

{exceptionHandler} -- expression which is evaluated to handle the exception

*DESC
ExceptionCatch evaluates its argument {expression} and returns the
result of evaluating {expression}. If an exception is thrown,
{errorHandler} is evaluated, returning its return value instead.

{ExceptionGet} can be used to obtain information about the caught exception.


 
*E.G.

In> ExceptionCatch(Check(1 =? 2, "Test", "Throwing a test exception."), "This string is returned if an exception is thrown.");
Result: "This string is returned if an exception is thrown."




/%mathpiper,title="Example of how to use ExceptionCatch and ExceptionGet in test code (long version)."
{
	Local(exception);
	
	exception := False;
	
	ExceptionCatch(Check(1 =? 2, "Test", "Throwing a test exception."), exception := True);
	
	Verify(exception, True);

};
/%/mathpiper

    /%output,sequence="8",timestamp="2014-10-09 17:15:18.472",preserve="false"
      Result: True
.   /%/output





/%mathpiper,title="Example of how to use ExceptionCatch and ExceptionGet in test code (short version)."

//ExceptionGet returns False if there is no exception or an association list if there is.
Verify( ExceptionCatch(Check(1 =? 2, "Test", "Throwing a test exception."), ExceptionGet()) =? False, False);

/%/mathpiper

    /%output,preserve="false"
      Result: True
.   /%/output





/%mathpiper,title="Example of how to handle a caught exception."

TestFunction(x) :=
{

    Check(Integer?(x), "Argument", "The argument must be an integer.");

};



caughtException := ExceptionCatch(TestFunction(1.2), ExceptionGet());

Echo(caughtException);

NewLine();

Echo("Type: ", caughtException["type"]);

NewLine();

Echo("Message: ", caughtException["message"]);


/%/mathpiper

    /%output,sequence="11",timestamp="2014-10-09 17:19:05.509",preserve="false"
      Result: True
      
      Side Effects:
      [["type","Unspecified"],["message","Argument Error: The argument must be an integer. "],["exceptionObject",class org.mathpiper.exceptions.EvaluationException]] 
      
      Type: Unspecified
      
      Message: Argument Error: The argument must be an integer. 
      
.   /%/output

*SEE Check, ExceptionGet

%/mathpiper_docs





%mathpiper,name="ExceptionCatch",subtype="automatic_test"

  //Test ExceptionCatch and ExceptionGet.
  Local(exception);
  exception := False;
  ExceptionCatch(Check(False, "Unspecified", "some error"), exception := ExceptionGet());
  Verify(exception =? False, False);


%/mathpiper
*/