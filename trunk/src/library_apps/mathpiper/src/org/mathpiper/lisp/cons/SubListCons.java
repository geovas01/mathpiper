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
import org.mathpiper.lisp.cons.ConsPointer;
import org.mathpiper.lisp.cons.Cons;


public class SubListCons extends Cons
{
	ConsPointer iCar = new ConsPointer();
	
	public static SubListCons getInstance(Cons aSubList)
	{
		return new SubListCons(aSubList);
	}
    ConsPointer iCdr = new ConsPointer();
        
        public Object first()
        {
            return iCar;
        }
        
        
	public ConsPointer getSublistPointer()
	{
		return iCar;
	}
        
        
	public String string()
	{
		return null;
	}
        
        /*
         public String toString()
        {
            return iCar.toString();
        }*/
        
        
	public Cons copy(boolean aRecursed) throws Exception
	{
		//TODO recursed copy needs to be implemented still
		LispError.lispAssert(aRecursed == false);
		Cons copied = new SubListCons(iCar.getCons());
		return copied;
	}
        
        
	public Cons setExtraInfo(ConsPointer aData)
	{
		//TODO FIXME
		/*
		    Cons* result = NEW LispAnnotatedObject<SubListCons>(this);
		    result->SetExtraInfo(aData);
		    return result;
		*/
		return null;
	}
        
        
	SubListCons(Cons aSubList)
	{
		iCar.setCons(aSubList);
	}

    public ConsPointer getRestPointer() {
        return iCdr;
    }
	
}
