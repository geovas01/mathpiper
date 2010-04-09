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
import org.mathpiper.builtin.BigNumber;
import org.mathpiper.builtin.BuiltinContainer;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.builtin.BuiltinFunctionEvaluator;
import org.mathpiper.builtin.JavaObject;
import org.mathpiper.builtin.javareflection.Invoke;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.Utility;
import org.mathpiper.lisp.cons.BuiltinObjectCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.ConsTraverser;
import org.mathpiper.lisp.cons.NumberCons;

/**
 *
 *
 */
public class JavaCall extends BuiltinFunction {

    public void plugIn(Environment aEnvironment) throws Exception
    {
        aEnvironment.getBuiltinFunctions().setAssociation(
                new BuiltinFunctionEvaluator(this, 1, BuiltinFunctionEvaluator.Variable | BuiltinFunctionEvaluator.Function),
                "JavaCall");
    }//end method.

    //private StandardFileOutputStream out = new StandardFileOutputStream(System.out);
    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        if (getArgumentPointer(aEnvironment, aStackTop, 1).car() instanceof ConsPointer) {

            ConsPointer subList = (ConsPointer) getArgumentPointer(aEnvironment, aStackTop, 1).car();
            ConsTraverser consTraverser = new ConsTraverser(aEnvironment, subList);

            //Skip past List type.
            consTraverser.goNext(aStackTop);

            //Obtain the Java object to call.
            Cons argumentCons = consTraverser.getPointer().getCons();

            BuiltinContainer builtinContainer = null;

            if (argumentCons != null) {




                if (argumentCons.car() instanceof String) {
                    String firstArgumentString = (String) argumentCons.car();
                    //Strip leading and trailing quotes.
                    firstArgumentString = Utility.stripEndQuotes(firstArgumentString);
                    Object clas = Class.forName(firstArgumentString);
                    builtinContainer = new JavaObject(clas);
                } else if (argumentCons.car() instanceof BuiltinContainer) {
                    builtinContainer = (BuiltinContainer) argumentCons.car();
                }//end else.


                if (builtinContainer != null) {


                    consTraverser.goNext(aStackTop);
                    argumentCons = consTraverser.getPointer().getCons();
                    String methodName = (String) argumentCons.car();
                    //Strip leading and trailing quotes.
                    methodName = Utility.stripEndQuotes(methodName);

                    consTraverser.goNext(aStackTop);

                    ArrayList argumentArrayList = new ArrayList();

                    while (consTraverser.getCons() != null) {
                        argumentCons = consTraverser.getPointer().getCons();
                        
                        Object argument = null;

                        if(argumentCons instanceof NumberCons)
                        {
                            NumberCons numberCons = (NumberCons) argumentCons;
                            BigNumber bigNumber = (BigNumber) numberCons.getNumber(aEnvironment.getPrecision(), aEnvironment);

                            if(bigNumber.isInteger())
                            {
                                argument = bigNumber.toInt();
                            }
                            else
                            {
                                argument = bigNumber.toDouble();
                            }
                        }
                        else
                        {
                            argument = argumentCons.car();


                            if (argument instanceof String) {
                                argument = Utility.stripEndQuotes((String)argument);
                            }

                            if(argument instanceof JavaObject)
                            {
                                argument = ((JavaObject)argument).getObject();
                            }
                        }//end if/else.
                        

                        argumentArrayList.add(argument);

                        consTraverser.goNext(aStackTop);

                    }//end while.
                    

                    Object[] argumentsArray = (Object[]) argumentArrayList.toArray(new Object[0]);


                    Object o  = Invoke.invokeInstance(builtinContainer.getObject(), methodName, argumentsArray, true);

                    JavaObject response = new JavaObject(o);

                    //JavaObject response = builtinContainer.execute(methodName, (Object[]) argumentArrayList.toArray(new Object[0]));
                    //System.out.println("XXXXXXXXXXX: " + response);

                    if (response == null || response.getObject() == null) {
                        Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
                        return;
                    } /*else if (response.equalsIgnoreCase("")) {
                    Utility.putTrueInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));
                    return;
                    }*/
                    getTopOfStackPointer(aEnvironment, aStackTop).setCons(BuiltinObjectCons.getInstance(aEnvironment, aStackTop, response));

                    return;

                }//end if.

            }//end if.

        }//end if.

        Utility.putFalseInPointer(aEnvironment, getTopOfStackPointer(aEnvironment, aStackTop));

    }//end method.
}
