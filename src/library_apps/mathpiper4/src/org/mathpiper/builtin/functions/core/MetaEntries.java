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

import java.util.Iterator;
import java.util.Map;
import org.mathpiper.builtin.BuiltinFunction;
import org.mathpiper.lisp.Environment;
import org.mathpiper.lisp.cons.AtomCons;
import org.mathpiper.lisp.cons.Cons;

import org.mathpiper.lisp.cons.SublistCons;

public class MetaEntries extends BuiltinFunction
{

    private MetaEntries()
    {
    }

    public MetaEntries(String functionName)
    {
        this.functionName = functionName;
    }


    public void evaluate(Environment aEnvironment, int aStackTop) throws Throwable {

        Cons object = getArgument(aEnvironment, aStackTop, 1);


        Map metadataMap = object.getMetadataMap();

        if (metadataMap == null || metadataMap.isEmpty()) {
            setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment, aEnvironment.iListAtom.copy(false)));

            return;
        }//end if.


        Cons head = aEnvironment.iListAtom.copy(false);

        Cons cons = head;

        java.util.Set keySet = (java.util.Set) metadataMap.keySet();

        Iterator keyIterator = keySet.iterator();

        java.util.Collection valueCollection = (java.util.Collection) metadataMap.values();

        Iterator valueIterator = valueCollection.iterator();

        while (keyIterator.hasNext()) {


            //Add -> operator cons.
            Cons operatorCons = AtomCons.getInstance(aEnvironment, aStackTop, "->");



            //Add key cons.
            String key = (String) keyIterator.next();

            Cons keyCons = AtomCons.getInstance(aEnvironment, aStackTop, key);

            operatorCons.setCdr(keyCons);



            //Add value cons.
            Cons valueCons = (Cons) metadataMap.get(key);
            keyCons.setCdr(valueCons);



            //Place entry in list.
            cons.setCdr(SublistCons.getInstance(aEnvironment, operatorCons));

            cons = cons.cdr();




        }//end while.



        setTopOfStack(aEnvironment, aStackTop, SublistCons.getInstance(aEnvironment, head));




    }//end method.
}//end class.
/*
%mathpiper_docs,name="MetaEntries",categories="User Functions;Built In"
 *CMD MetaValues --- returns the metadata values for a value or an unbound variable
 *CORE
 *CALL
MetaValues(value_or_unbound_variable)

 *PARMS


{value_or_unbound_variable} -- a value or an unbound variable


 *DESC

todo:tk: not functional yet.

Returns the metadata values for a value or an unbound variable.  The metadata is
held in an associative list.



 *SEE MetaGet, MetaSet, MetaKeys, Unassign
%/mathpiper_docs
 */