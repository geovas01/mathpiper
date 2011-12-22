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
package org.mathpiper.builtin.functions.optional;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;


/**
 *
 *
 */
public class JavaAccess extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Exception {
        this.functionName = "JavaAccess";
        aEnvironment.iBuiltinFunctions.setAssociation(
                this.functionName, new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function));
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        Cons args = getArgument(aEnvironment, aStackTop, 1);

        args = (Cons) args.car();

        args = args.cdr();

        Cons result = Utility.applyString(aEnvironment, "\"JavaCall\"", args);

        result = Utility.applyString(aEnvironment, "\"JavaToValue\"", result);

        setTopOfStack(aEnvironment, aStackTop, result);

    }//end method.
}



/*
%mathpiper_docs,name="JavaAccess",categories="Programmer Functions;Built In;Native Objects",access="experimental"
*CMD JavaAccess --- calls a method on a Java object and converts the result into a MathPiper data structure
*CALL
    JavaAccess(javaObject, methodName, methodParameter1, methodParameter2, ...)

*PARMS
{javaObject} -- a Java object

{methodName} -- the name of a method to call on the Java object (it can be either a string or an atom)

{methodParameters} -- zero or more parameters which will be sent to the method

*DESC
This is a convenience function which can be used instead of using JavaCall and JavaToValue.

*E.G.
In> javaString := JavaNew("java.lang.String", "Hello")
Result: java.lang.String

In> JavaAccess(javaString, "charAt",1)
Result: e

*SEE JavaNew, JavaCall, JavaToValue
%/mathpiper_docs
*/
