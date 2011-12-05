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

import org.mathpiper.lisp.*;

public class AtomCons extends Cons
{

    private String iCar;

    //This variable is placed here instead of in Cons because it makes viewing it
    // in the debugger easier.
    private Cons iCdr;



    public AtomCons(String aString) throws Exception
    {
        //Make sure to use aEnvironment.getTokenHash().lookUp(aString) with aString before calling this constructor.
        
        super();
        iCar = aString;

    }

    public static Cons getInstance(Environment aEnvironment, int aStackTop, String aString) throws Exception
    {
        Cons self = null;
        if (Utility.isNumber(aString, true))  // check if aString is a number (int or float)
        {
            /// construct a number from a decimal string representation (also create a number object)
            self = new NumberCons(aString, aEnvironment.getPrecision());
        } else
        {
            self = new AtomCons((String)aEnvironment.getTokenHash().lookUp(aString));
        }
        
        //LispError.check(aEnvironment, aStackTop, self != null, LispError.NOT_ENOUGH_MEMORY, ""," INTERNAL");
        
        return self;
    }
    
    public Object car()
    {
        return iCar;
    }


    public void setCar(Object object) throws Exception
    {
        iCar = (String) object;
    }


    public Cons cdr() {
        return iCdr;
    }

    public void setCdr(Cons aCons)
    {
        iCdr = aCons;
    }



    public Cons copy(boolean aRecursed) throws Exception
    {
        Cons atomCons = new AtomCons(iCar);

        atomCons.setMetadataMap(this.getMetadataMap());
        
        return atomCons;
    }




    public int type()
    {
        return Utility.ATOM;
    }//end method.
   

}//end class.
