
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
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.ConsPointer;

/**
 *
 *
 */
public class Import extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment)
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Import");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {

        ConsPointer pathPointer = getArgumentPointer(aEnvironment, aStackTop, 1);


        LispError.checkIsString(aEnvironment, aStackTop, pathPointer, 1);

        String path = Utility.stripEndQuotes((String) pathPointer.car());

        List failList = BuiltinFunction.addOptionalFunctions(aEnvironment, path);

        if(failList.isEmpty())
        {
            Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
            return;
        }
        else
        {
            aEnvironment.write("Could not load " + path);
            Utility.putFalseInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
        }//end if/else

    }//end method.

}//end class.

