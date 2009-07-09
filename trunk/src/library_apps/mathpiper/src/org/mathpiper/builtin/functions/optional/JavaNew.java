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

import java.util.ArrayList;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.UtilityFunctions;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.ConsTraverser;

/**
 *
 *
 */
public class JavaNew extends BuiltinFunction {

    //private StandardFileOutputStream out = new StandardFileOutputStream(System.out);
    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        if (getArgumentPointer(aEnvironment, aStackTop, 1).getCons().first() instanceof ConsPointer) {

            ConsPointer subList = (ConsPointer) getArgumentPointer(aEnvironment, aStackTop, 1).getCons().first();
            ConsTraverser consTraverser = new ConsTraverser(subList);

            //Skip past List type.
            consTraverser.goNext();

            Cons argumentCons = consTraverser.getPointer().getCons();

            if (argumentCons != null) {

                String fullyQualifiedClassName = (String) argumentCons.first();
                //Strip leading and trailing quotes.
                fullyQualifiedClassName = fullyQualifiedClassName.substring(1, fullyQualifiedClassName.length());
                fullyQualifiedClassName = fullyQualifiedClassName.substring(0, fullyQualifiedClassName.length() - 1);

                consTraverser.goNext();

                ArrayList argumentArrayList = new ArrayList();

                while (consTraverser.getCons() != null) {
                    argumentCons = consTraverser.getPointer().getCons();
                    Object argument = argumentCons.first();

                    if (argument instanceof String) {
                        argument = ((String)argument).substring(1, ((String)argument).length());
                        argument = ((String)argument).substring(0, ((String)argument).length() - 1);
                    }

                    argumentArrayList.add(argument);

                    consTraverser.goNext();

                }//end while.


                JavaObject response = JavaObject.instantiate(fullyQualifiedClassName, (Object[]) argumentArrayList.toArray(new Object[0]));
                //System.out.println("XXXXXXXXXXX: " + response);

                if (response == null) {
                    UtilityFunctions.internalFalse(aEnvironment, getResult(aEnvironment, aStackTop));
                    return;
                } else {
                    getResult(aEnvironment, aStackTop).setCons(BuiltinObjectCons.getInstance(response));

                    return;
                }



            }//end if.

        }//end if.

        UtilityFunctions.internalFalse(aEnvironment, getResult(aEnvironment, aStackTop));

    }//end method.
}
