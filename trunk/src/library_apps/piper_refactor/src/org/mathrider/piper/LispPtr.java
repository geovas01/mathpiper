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

package org.mathrider.piper;


/** class LispPtr. This class is a smart pointer type class to Lisp
 *  objects that can be inserted into linked lists. They do the actual
 *  reference counting, and consequent destruction of the object if
 *  nothing points to it. LispPtr is used in LispObject as a pointer
 *  to the next object, and in diverse parts of the built-in internal
 *  functions to hold temporary values.
 */
class LispPtr
{
	LispObject iNext;
	
	public LispPtr()
	{
		iNext = null;
	}
	
	public LispPtr(LispPtr aOther)
	{
		iNext = aOther.iNext;
	}
	
	public LispPtr(LispObject aOther)
	{
		iNext = aOther;
	}
	
	public void Set(LispObject aNext)
	{
		iNext = aNext;
	}
	
	public LispObject Get()
	{
		return iNext;
	}
	
	public void GoNext()
	{
		iNext = iNext.iNext.iNext;
	}
	
	void DoSet(LispObject aNext)
	{
		iNext = aNext;
	}
	
}
