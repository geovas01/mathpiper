
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
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.ConsPointer;

/**
 *
 *
 */
public class Ring extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment) throws Exception
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "Ring");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {

        ConsPointer pathPointer = getArgumentPointer(aEnvironment, aStackTop, 1);


        LispError.checkIsString(aEnvironment, aStackTop, pathPointer, 1, "Ring");

        String configurationString = Utility.stripEndQuotes((String) pathPointer.car());

        org.mathpiper.builtin.library.jas.Ring ring = new org.mathpiper.builtin.library.jas.Ring(configurationString);

        JavaObject response = new JavaObject(ring);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));
        return;


    }//end method.

}//end class.

