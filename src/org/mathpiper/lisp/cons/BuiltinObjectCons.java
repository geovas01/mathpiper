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
import org.mathpiper.builtin.BuiltinContainer;


public class BuiltinObjectCons extends Cons {

    BuiltinContainer iCarBuiltin;

    //This variable is placed here instead of in Cons because it makes viewing it
    // in the debugger easier.
    private Cons iCdr;



    private BuiltinObjectCons(BuiltinContainer aClass) throws Exception  {
        super();
        iCarBuiltin = aClass;
    }

    public static BuiltinObjectCons getInstance(Environment aEnvironment, int aStackBase, BuiltinContainer aClass) throws Exception {
        if(aClass == null) LispError.lispAssert(aEnvironment, aStackBase);
        BuiltinObjectCons self = new BuiltinObjectCons(aClass);
        //LispError.check(aEnvironment, aStackBase, self != null, LispError.NOT_ENOUGH_MEMORY, "INTERNAL");
        return self;
    }


    public Object car() {
        return iCarBuiltin;
    }


    public void setCar(Object object) throws Exception
    {
        iCarBuiltin = (BuiltinContainer) object;
    }

    public Cons cdr() {
        return iCdr;
    }

    public void setCdr(Cons aCdr)
    {
        iCdr = aCdr;
    }

    
    public Cons copy(boolean aRecursed) throws Exception  {

        Cons copied = new BuiltinObjectCons(iCarBuiltin);

        copied.setMetadataMap(this.getMetadataMap());

        return copied;
        
    }



    public int type() {
        return Utility.OBJECT;
    }//end method.


    @Override
    public String toString()
    {
        return "JavaObject: " + this.iCarBuiltin.getObject().toString();
    }//end method.
    
};
