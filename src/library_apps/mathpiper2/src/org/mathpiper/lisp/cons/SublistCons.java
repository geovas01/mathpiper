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
package org.mathpiper.lisp.cons;


import org.mathpiper.exceptions.EvaluationException;
import org.mathpiper.io.StringOutput;
import org.mathpiper.lisp.*;
import org.mathpiper.lisp.printers.LispPrinter;


public class SublistCons extends Cons {

    Cons iCar;
    
    //This variable is placed here instead of in Cons because it makes viewing it 
    // in the debugger easier.
    private Cons iCdr;

    private SublistCons(Environment aEnvironment, Cons aSubList) throws Exception {
        super();

        iCar = aSubList;
    }

    public static SublistCons getInstance(Environment aEnvironment, Cons aSubList) throws Exception {
        return new SublistCons(aEnvironment, aSubList);
    }


    public Object car() {
        return iCar;
    }


    public void setCar(Object object) throws Exception
    {
        iCar = (Cons) object;
    }


    public Cons cdr() {
        return iCdr;
    }

    public void setCdr(Cons aCons)
    {
        iCdr = aCons;
    }


    /*
    public String toString()
    {
    return iCar.toString();
    }*/
    public Cons copy(Environment aEnvironment, boolean aRecursed) throws Exception {
        //TODO recursed copy needs to be implemented still
        //LispError.lispAssert(aRecursed == false, aEnvironment, aStackTop);

        if(aRecursed != false) throw new EvaluationException("Internal error in SublistCons.","",-1,-1,-1);

        Cons copied = new SublistCons(aEnvironment, iCar);

        copied.setMetadataMap(this.getMetadataMap());
        
        return copied;
    }







    public int type() {
        return Utility.SUBLIST;
    }//end method.


}//end class.

