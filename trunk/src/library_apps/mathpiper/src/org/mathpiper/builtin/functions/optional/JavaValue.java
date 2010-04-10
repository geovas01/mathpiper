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
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.AtomCons;

/**
 *
 *
 */
public class JavaValue extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Exception
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "JavaValue");
    }//end method.

    //private StandardFileOutputStream out = new StandardFileOutputStream(System.out);
    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        Object argument = getArgumentPointer(aEnvironment, aStackTop, 1).car();

        if(argument instanceof JavaObject)
        {
            JavaObject javaObject = (JavaObject) argument;

            getTopOfStackPointer(aEnvironment, aStackTop).setCons(AtomCons.getInstance(aEnvironment, aStackTop, javaObject.getObject().toString().trim()));
        }
        else
        {
            LispError.raiseError("The argument must be a JavaObject.", "JavaValue", aStackTop, aEnvironment);
        }

    }//end method.
    
}//end class.
