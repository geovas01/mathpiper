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


/** 
 * Similar to Pointer, but implements an array of pointers to objects.
 *  
 */
public class PointerArray
{
	int iSize;
	Pointer iArray[];
	
	public PointerArray(int aSize,Cons aInitialItem)
	{
		iArray = new Pointer[aSize];
		iSize = aSize;
		int i;
		for(i=0;i<aSize;i++)
		{
			iArray[i] = new Pointer();
			iArray[i].set(aInitialItem);
		}
	}
	
	public int size()
	{
		return iSize;
	}
	
	public Pointer getElement(int aItem)
	{
		return iArray[aItem];
	}
	
	public void setElement(int aItem,Cons aObject)
	{
		iArray[aItem].set(aObject);
	}

}
