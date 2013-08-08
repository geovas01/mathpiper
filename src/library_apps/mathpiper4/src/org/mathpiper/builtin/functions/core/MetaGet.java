/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mathpiper.builtin.functions.core;


import java.util.Map;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.LispError;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;



public class MetaGet extends BuiltinFunction
{

    private MetaGet()
    {
    }

    public MetaGet(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {

        Cons object = getArgument(aEnvironment, aStackTop, 1);


        Cons key = getArgument(aEnvironment, aStackTop, 2);

        LispError.checkIsString(aEnvironment, aStackTop, key, 2);


        Map metadataMap = object.getMetadataMap();

        if (metadataMap == null) {
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "None"));

            return;
        }//end if.


        Cons valueCons = (Cons) metadataMap.get((String) key.car());


        if (valueCons == null) {
            setTopOfStack(aEnvironment, aStackTop, AtomCons.getInstance(aEnvironment, aStackTop, "None"));
        } else {
            setTopOfStack(aEnvironment, aStackTop, valueCons);
        }



    }//end method.


}//end class.



/*
%mathpiper_docs,name="MetaGet",categories="Mathematics Functions;Built In"
*CMD MetaGet --- returns the metadata for a value or an unbound variable
*CORE
*CALL
MetaGet(value_or_unbound_variable, key_string)

*PARMS

{value_or_unbound_variable} -- a value or an unbound variable

{key_string} -- a string which is the key for the given value


*DESC

Returns the metadata for a value or an unbound variables.  The metadata is
held in an associative list.



*SEE MetaSet, MetaKeys, MetaValues, Unassign
%/mathpiper_docs
 */
