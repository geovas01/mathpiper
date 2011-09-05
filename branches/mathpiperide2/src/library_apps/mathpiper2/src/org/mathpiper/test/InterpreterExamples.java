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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *///}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathpiper.test;


import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.interpreters.EvaluationResponse;
import org.mathpiper.interpreters.Interpreter;


/**
 *
 */
public class InterpreterExamples {

    public InterpreterExamples() {


        /**
         * The Interpreter interface is implemented by all MathPiper interpreters and it allows client code to evaluate
         * MathPiper scripts and to also control the evaluation process.
         */
        Interpreter interpreter;



        /*
         * The Interpreter returns the results of all evaluations in an EvaluationResponse object.
         * See org/mathpiper/interpreters/EvaluationResponse.java for more information.
         */
        EvaluationResponse response;



        /**
         * The Interpreters class consists exclusively of static factory methods which return MathPiper interpreter instances.
         * These static methods are the only way to obtain instances of MathPiper interpeters.
         */
        interpreter = Interpreters.getSynchronousInterpreter();

        

        //Perform a simple evaluation.
        response = interpreter.evaluate("3 + 3;");
        System.out.println("Result: " + response.getResult() + "    Side Effects: " + response.getSideEffects() + "    Errors: " + response.getExceptionMessage() + "\n");


        //This is a more involved evaluation.  The first time Factor() is called a significant amouint of initialization
        //takes place which takes some time.  Subsequent calls to Factor() are much quicker.
        response = interpreter.evaluate("Factor(100);");
        System.out.println("Result: " + response.getResult() + "    Side Effects: " + response.getSideEffects() + "    Errors: " + response.getExceptionMessage() + "\n");


        //MathPiper uses a functional programming design so all its functions always return a result.  However, a number of functions,
        //such as Echo(), also return side effects output and this example shows how this output is obtained.
        response = interpreter.evaluate("Echo(\"This is a test.\");");
        System.out.println("Result: " + response.getResult() + "    Side Effects: " + response.getSideEffects() + "    Errors: " + response.getExceptionMessage() + "\n");


        //This example generates an error.  The EvaluationResponse objects contain both the exception's message and the exception object itself.
        response = interpreter.evaluate("2 \\ 2;");
        System.out.println("Result: " + response.getResult() + "    Side Effects: " + response.getSideEffects() + "    Errors: " + response.getExceptionMessage() + "\n");


        //The result of this evaluation is returned in standard text format.
        response = interpreter.evaluate("Simplify(((2*a+b)/(7*a*b))*3*b^2/(4*a+2*b));");
        System.out.println("Result: " + response.getResult() + "    Side Effects: " + response.getSideEffects() + "    Errors: " + response.getExceptionMessage() + "\n");

        
        //This is the same as the previous example, except the result is returned in TeX form.
        response = interpreter.evaluate("TeXForm(Simplify(((2*a+b)/(7*a*b))*3*b^2/(4*a+2*b)));");
        System.out.println("Result: " + response.getResult() + "    Side Effects: " + response.getSideEffects() + "    Errors: " + response.getExceptionMessage() + "\n");


    }//end constructor.


    public static void main(String[] args) {
        new InterpreterExamples();


    }//end main.


}//end class.

