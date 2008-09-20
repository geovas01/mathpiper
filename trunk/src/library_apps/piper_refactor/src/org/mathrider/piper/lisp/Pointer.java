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

package org.mathrider.piper.lisp;


/** class Pointer. This class is a smart pointer type class to Lisp
 *  objects that can be inserted into linked lists. They do the actual
 *  reference counting, and consequent destruction of the object if
 *  nothing points to it. Pointer is used in Cons as a pointer
 *  to the next object, and in diverse parts of the built-in internal
 *  functions to hold temporary values.
 */
public class Pointer
{
	Cons iNext;
	
	public Pointer()
	{
		iNext = null;
	}
	
	public Pointer(Pointer aOther)
	{
		iNext = aOther.iNext;
	}
	
	public Pointer(Cons aOther)
	{
		iNext = aOther;
	}
	
        
        
        
	public void set(Cons aNext)
	{
		iNext = aNext;
	}
	
	public Cons get()
	{
		return iNext;
	}
	
	public void goNext()
	{
		iNext = iNext.cdr.iNext;
	}
	
	void doSet(Cons aNext)
	{
		iNext = aNext;
	}
	
}
