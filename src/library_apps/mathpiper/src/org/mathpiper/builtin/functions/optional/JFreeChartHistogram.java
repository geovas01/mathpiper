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
import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
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
public class JFreeChartHistogram extends BuiltinFunction {

    public void plugIn(Environment aEnvironment)
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "Histogram");
    }//end method.

    //private StandardFileOutputStream out = new StandardFileOutputStream(System.out);
    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        ConsPointer argumentPointer = getArgumentPointer(aEnvironment, aStackTop, 1);

        Cons argumentListCons = argumentPointer.getCons();

        Object argument1 = argumentListCons.car();
        
        if (argument1 instanceof ConsPointer) {

            ConsPointer dataSubList = (ConsPointer) argument1;

            //Skip past List type.
            dataSubList.goNext();

            //Obtain the Java object to call.
            //Cons argumentsCons = dataSubList.getCons();

            if(dataSubList.car() instanceof ConsPointer)
            {
                 ConsPointer dataListPointer = (ConsPointer) dataSubList.car();

                 if(dataListPointer.car() instanceof String)
                 {

                 }

                    dataListPointer.goNext();

                    ArrayList argumentArrayList = new ArrayList();

                    while (dataListPointer.getCons() != null) {
                        //argumentsCons = dataListPointer.getCons();

                       // Object argument = argumentsCons.car();

                        /*if (argument instanceof String) {
                            if (argument instanceof String) {
                                argument = ((String) argument).substring(1, ((String) argument).length());
                                argument = ((String) argument).substring(0, ((String) argument).length() - 1);
                            }
                        }*/

                        //argumentArrayList.add(argument);

                        dataListPointer.goNext();

                    }//end while.

                    }//end if.






                    Object response = null;
                    if (response == null) {
                        UtilityFunctions.internalFalse(aEnvironment, getResult(aEnvironment, aStackTop));
                        return;
                    } /*else if (response.equalsIgnoreCase("")) {
                    UtilityFunctions.internalTrue(aEnvironment, getResult(aEnvironment, aStackTop));
                    return;
                    }*/
                    getResult(aEnvironment, aStackTop).setCons(null);

                    return;

                }//end if.

  


        UtilityFunctions.internalFalse(aEnvironment, getResult(aEnvironment, aStackTop));

    }//end method.
}
