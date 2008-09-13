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


class LispSubList extends LispObject
{
	LispPtr iSubList = new LispPtr();
	
	public static LispSubList New(LispObject aSubList)
	{
		return new LispSubList(aSubList);
	}
	public LispPtr SubList()
	{
		return iSubList;
	}
	public String String()
	{
		return null;
	}
	public LispObject Copy(boolean aRecursed) throws Exception
	{
		//TODO recursed copy needs to be implemented still
		LispError.LISPASSERT(aRecursed == false);
		LispObject copied = new LispSubList(iSubList.Get());
		return copied;
	}
	public LispObject SetExtraInfo(LispPtr aData)
	{
		//TODO FIXME
		/*
		    LispObject* result = NEW LispAnnotatedObject<LispSubList>(this);
		    result->SetExtraInfo(aData);
		    return result;
		*/
		return null;
	}
	LispSubList(LispObject aSubList)
	{
		iSubList.Set(aSubList);
	}
	
}
