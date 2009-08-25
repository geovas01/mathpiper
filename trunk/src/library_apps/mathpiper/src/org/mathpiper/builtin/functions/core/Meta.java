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



        ConsPointer result = variablePointer.getCons().getMetadataPointer();

        if (result.getCons() == null) {
            //Create new meta data list.

            Cons listCons = SublistCons.getInstance(aEnvironment, AtomCons.getInstance(aEnvironment, "List"));

            ConsPointer listConsPointer = new ConsPointer(listCons);

            variablePointer.getCons().setMetadataPointer(listConsPointer);

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

        object.getCons().setMetadataPointer(info);

        getTopOfStackPointer(aEnvironment, aStackTop).setCons(object.getCons());
    }
*/



/*
%mathpiper_docs,name="Meta",categories="User Functions;Built In"
*CMD Meta --- access the metadata for a value or an unbound variable
*CORE
*CALL
	Meta(value)
	Meta(unbound_variable)

*PARMS

{value} -- a value such as a string, symbolic atom, or list

{unbound_variable} -- an unbound variable

*DESC

Enables metadata to be added to values and unbound variables.  The metadata is
held in an associative list.

*E.G.
//One way to add tags to a list.
In> a := {1,2,3}
Result: {1,2,3}

//Add a list of tags to the list's metadata.
In> Meta(a)["Tags"] := {SET, TAG2, TAG3}
Result: True

In> Meta(a)["Tags"]
Result: {SET,TAG2,TAG3}

//Add a tag to the list.
In> DestructiveAppend(Meta(a)["Tags"], TAG4)
Result: {SET,TAG2,TAG3,TAG4}

In> Meta(a)["Tags"]
Result: {SET,TAG2,TAG3,TAG4}

//Check to see if the list has a SET tag.
In> If(Meta(a)["Tags"] = Empty, False, Contains(Meta(a)["Tags"],SET))
Result: True



//Another way to add tags to a list.
In> a := {1,2,3}
Result: {1,2,3}

In> Meta(a)["SET"] := TAG
Result: True

In> Meta(a)["TAG1"] := TAG
Result: True

//Check to see if a given tag is present.
In> Meta(a)["SET"] != Empty
Result: True

//Check to see if a given tag is present.
In> Contains(AssocIndices(Meta(a)), SET)
Result: True


//Adding metadata to an unbound variable.
In> Clear(b)
Result: True

In> Meta(b)["Assume"] := Hold( b > 0)
Result: True

In> Meta(b)["Assume"]
Result: b>0

In> IsBound(b)
Result: False

*SEE Clear()
%/mathpiper_docs
*/