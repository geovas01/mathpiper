
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

import java.util.List;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.interpreters.Interpreters;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.Cons;

/**
 *
 *
 */
public class Import extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        this.functionName = "Import";
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                this.functionName);
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {

        Cons pathPointer = getArgumentPointer(aEnvironment, aStackTop, 1);


        LispError.checkIsString(aEnvironment, aStackTop, pathPointer, 1, "Import");

        String path = Utility.stripEndQuotesIfPresent(aEnvironment, aStackTop, (String) pathPointer.car());

        /*org.mathpiper.builtin.javareflection.Import.addImport(path);
        Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));*/


        List failList = Interpreters.addOptionalFunctions(aEnvironment, path);

        if(failList.isEmpty())
        {
            setTopOfStackPointer(aEnvironment, aStackTop, Utility.putTrueInPointer(aEnvironment));
            return;
        }
        else
        {
            aEnvironment.write("Could not load " + path);
            setTopOfStackPointer(aEnvironment, aStackTop, Utility.putFalseInPointer(aEnvironment));
        }//end if/else

    }//end method.

}//end class.

