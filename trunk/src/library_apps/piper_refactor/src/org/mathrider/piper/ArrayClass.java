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

import org.mathrider.piper.lisp.LispObject;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.LispPtrArray;
import org.mathrider.piper.lisp.LispArgList;


public class ArrayClass extends GenericClass
{
	LispPtrArray iArray;

	public ArrayClass(int aSize,LispObject aInitialItem)
	{
		iArray = new LispPtrArray(aSize,aInitialItem);
	}
	public String Send(LispArgList aArgList)
	{
		return null;
	}
	public String TypeName()
	{
		return "\"Array\"";
	}

	public int Size()
	{
		return iArray.Size();
	}
	public LispObject GetElement(int aItem) throws Exception
	{
		LispError.LISPASSERT(aItem>0 && aItem<=iArray.Size());
		return iArray.GetElement(aItem-1).Get();
	}
	public void SetElement(int aItem,LispObject aObject) throws Exception
	{
		LispError.LISPASSERT(aItem>0 && aItem<=iArray.Size());
		iArray.SetElement(aItem-1,aObject);
	}

}
