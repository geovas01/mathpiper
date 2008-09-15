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


/** \class LispPtrArray is similar to LispPtr, but implements an array
 *  of pointers to objects.
 */
public class LispPtrArray
{
	int iSize;
	LispPtr iArray[];
	
	public LispPtrArray(int aSize,LispObject aInitialItem)
	{
		iArray = new LispPtr[aSize];
		iSize = aSize;
		int i;
		for(i=0;i<aSize;i++)
		{
			iArray[i] = new LispPtr();
			iArray[i].Set(aInitialItem);
		}
	}
	
	public int Size()
	{
		return iSize;
	}
	
	public LispPtr GetElement(int aItem)
	{
		return iArray[aItem];
	}
	
	public void SetElement(int aItem,LispObject aObject)
	{
		iArray[aItem].Set(aObject);
	}

}
