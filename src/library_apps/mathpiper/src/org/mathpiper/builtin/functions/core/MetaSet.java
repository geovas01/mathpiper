/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper.builtin.functions.core;


import java.util.HashMap;
import java.util.Map;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.ConsPointer;


public class MetaSet extends BuiltinFunction {

    public void evaluate(Environment aEnvironment, int aStackTop) throws Exception {

        ConsPointer objectPointer = new ConsPointer();
        objectPointer.setCons(getArgumentPointer(aEnvironment, aStackTop, 1).getCons());


        ConsPointer keyPointer = new ConsPointer();
        keyPointer.setCons(getArgumentPointer(aEnvironment, aStackTop, 2).getCons());
        LispError.checkIsString(aEnvironment, aStackTop, keyPointer, 2);


        ConsPointer value = new ConsPointer();
        value.setCons(getArgumentPointer(aEnvironment, aStackTop, 3).getCons());


        

        Map metadataMap = objectPointer.getCons().getMetadataMap();

        if(metadataMap == null)
        {
            metadataMap = new HashMap();

            objectPointer.getCons().setMetadataMap(metadataMap);
        }//end if.



        String keyString =(String) keyPointer.getCons().car();;

        metadataMap.put(keyString, value.getCons());

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(objectPointer.getCons());

        return;

/*
        //Local variable check.
        ConsPointer variablePointer = aEnvironment.getLocalVariable((String) object.car());


        if (variablePointer != null) {
            //Is an unbound local variable.


            //Check to see if the value already has metadata associated with it.
            ConsPointer metadataPointer = variablePointer.getCons().getMetadataMap();
            if (metadataPointer.getCons() == null) {
                //Create new meta data list.

                Cons listCons = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, "List"));

                ConsPointer listConsPointer = new ConsPointer(listCons);

                variablePointer.getCons().setMetadataMap(listConsPointer);

                getTopOfStackPointer(aEnvironment, aStackTop).setCons(variablePointer.getCons().getMetadataMap().getCons());

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
            ConsPointer metadataPointer = variablePointer.getCons().getMetadataMap();
            if (metadataPointer.getCons() == null) {
                //Create new meta data list.

                Cons listCons = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, "List"));

                ConsPointer listConsPointer = new ConsPointer(listCons);

                variablePointer.getCons().setMetadataMap(listConsPointer);

                getTopOfStackPointer(aEnvironment, aStackTop).setCons(variablePointer.getCons().getMetadataMap().getCons());

                return;

            } else {

                //Return existing meta
                getTopOfStackPointer(aEnvironment, aStackTop).setCons(metadataPointer.getCons());

                return;
            }//end if/else.

        }//end if.




        //If this point has been reached then we are dealing with an unbound variable.
        ConsPointer metaDataPointer = object.getCons().getMetadataMap();

        if (metaDataPointer.getCons() == null) {
            //Create new meta data list.

            Cons listCons = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, "List"));

            ConsPointer listConsPointer = new ConsPointer(listCons);

            object.getCons().setMetadataMap(listConsPointer);

            getTopOfStackPointer(aEnvironment, aStackTop).setCons(listCons);

        } else {

            //Return existing meta
            getTopOfStackPointer(aEnvironment, aStackTop).setCons(metaDataPointer.getCons());

            return;
        }//end if/else.
 *
 * */

    }//end method.


}//end class.


/*
%mathpiper_docs,name="MetaSet",categories="User Functions;Built In"
 *CMD MetaSet --- set the metadata for a value or an unbound variable
 *CORE
 *CALL
Meta(value_or_unbound_variable, key_string, value)

 *PARMS



{value_or_unbound_variable} -- a value or an unbound variable

{key_string} -- a string which will be the key for the given value

{value} -- a value such as a string, symbolic atom, or list

 *DESC

Adds metadata to values and unbound variables.  The metadata is
held in an associative list.  MetaSet returns the given value or unbound variable
as a result after it has had metadata added to it.



 *SEE MetaSet, Clear
%/mathpiper_docs
 */
