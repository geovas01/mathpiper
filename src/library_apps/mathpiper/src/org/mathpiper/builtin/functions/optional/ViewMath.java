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
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.SublistCons;

/**
 *
 *
 */
public class ViewMath extends BuiltinFunction
{

    public void plugIn(Environment aEnvironment)
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Fixed | BuiltinFunctionEvaluator.Function),
                "ViewMath");
    }//end method.

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {

        //Utility.lispEvaluate(aEnvironment, "TeXForm(x^2);");

        Cons head = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, "TeXForm"));

        ((ConsPointer) head.car()).cdr().setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());


         ConsPointer resultPointer = new ConsPointer();

         aEnvironment.iLispExpressionEvaluator.evaluate(aEnvironment, resultPointer, new ConsPointer(head) );

         getTopOfStackPointer(aEnvironment, aStackTop).setCons(resultPointer.getCons());

    }//end method.

}//end class.

