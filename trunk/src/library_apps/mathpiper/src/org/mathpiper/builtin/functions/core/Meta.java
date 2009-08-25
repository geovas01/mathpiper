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
package org.mathpiper.builtin.functions.core;


import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.SublistCons;


/**
 *
 *  
 */
public class Meta extends BuiltinFunction {

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {
        
        ConsPointer object = new ConsPointer();

        object.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());

        //Local variable.
        ConsPointer variablePointer = aEnvironment.findLocalVariable((String) object.car());

        //Global variable.
        if (variablePointer == null) {
            variablePointer = new ConsPointer();
            aEnvironment.getGlobalVariable((String) object.car(), variablePointer);

            //Unbound variable.
            if (variablePointer.getCons() == null) {
                
                variablePointer = object;

                Cons unboundMetadata = aEnvironment.getUnboundVariableMetadata((String) object.car());

                getTopOfStackPointer(aEnvironment, aStackTop).setCons(unboundMetadata);

                return;

            }//end if.

        }//end if.



        ConsPointer result = variablePointer.getCons().getExtraInfoPointer();

        if (result.getCons() == null) {
            //Create new meta data list.

            Cons listCons = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, "List"));

            ConsPointer listConsPointer = new ConsPointer(listCons);

            variablePointer.getCons().setExtraInfoPointer(listConsPointer);

            getTopOfStackPointer(aEnvironment, aStackTop).setCons(listCons);

        } else {

            //Return existing meta
            getTopOfStackPointer(aEnvironment, aStackTop).setCons(result.getCons());
        }//end if/else.

    }//end method.


}//end class.


/*
 * This is the code for MetaSet in case it is ever needed.
    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception
    {
        ConsPointer object = new ConsPointer();
        object.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());

        ConsPointer info = new ConsPointer();
        info.setCons(getArgumentPointer(aEnvironment, aStackTop, 2).getCons());

        object.getCons().setExtraInfoPointer(info);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(object.getCons());
    }
*/
