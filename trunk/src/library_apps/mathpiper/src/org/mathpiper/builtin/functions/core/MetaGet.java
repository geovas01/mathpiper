/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mathpiper.builtin.functions.core;

import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.SublistCons;



public class MetaGet extends BuiltinFunction {

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        ConsPointer object = new ConsPointer();

        object.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());

        //Local variable check.
        ConsPointer variablePointer = aEnvironment.getLocalVariable((String) object.car());


        if (variablePointer != null) {
            //Is an unbound local variable.


            //Check to see if the value already has metadata associated with it.
            ConsPointer metadataPointer = variablePointer.getCons().getMetadataPointer();
            if (metadataPointer.getCons() == null) {
                //Create new meta data list.

                Cons listCons = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, "List"));

                ConsPointer listConsPointer = new ConsPointer(listCons);

                variablePointer.getCons().setMetadataPointer(listConsPointer);

                getTopOfStackPointer(aEnvironment, aStackTop).setCons(variablePointer.getCons().getMetadataPointer().getCons());

                return;

            } else {

                //Return existing meta
                getTopOfStackPointer(aEnvironment, aStackTop).setCons(metadataPointer.getCons());

                return;
            }//end if/else.


        }//end if.





        //Check for global variable.
        variablePointer = new ConsPointer();
        aEnvironment.getGlobalVariable((String) object.car(), variablePointer);

        if (variablePointer.getCons() != null) {


            //Check to see if the value already has metadata associated with it.
            ConsPointer metadataPointer = variablePointer.getCons().getMetadataPointer();
            if (metadataPointer.getCons() == null) {
                //Create new meta data list.

                Cons listCons = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, "List"));

                ConsPointer listConsPointer = new ConsPointer(listCons);

                variablePointer.getCons().setMetadataPointer(listConsPointer);

                getTopOfStackPointer(aEnvironment, aStackTop).setCons(variablePointer.getCons().getMetadataPointer().getCons());

                return;

            } else {

                //Return existing meta
                getTopOfStackPointer(aEnvironment, aStackTop).setCons(metadataPointer.getCons());

                return;
            }//end if/else.

        }//end if.




        //If this point has been reached then we are dealing with an unbound variable.
        ConsPointer metaDataPointer = object.getCons().getMetadataPointer();

        if (metaDataPointer.getCons() == null) {
            //Create new meta data list.

            Cons listCons = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, "List"));

            ConsPointer listConsPointer = new ConsPointer(listCons);

            object.getCons().setMetadataPointer(listConsPointer);

            getTopOfStackPointer(aEnvironment, aStackTop).setCons(listCons);

        } else {

            //Return existing meta
            getTopOfStackPointer(aEnvironment, aStackTop).setCons(metaDataPointer.getCons());

            return;
        }//end if/else.

    }//end method.


}//end class.

