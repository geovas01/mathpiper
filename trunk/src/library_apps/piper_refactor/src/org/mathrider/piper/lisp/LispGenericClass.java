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

import org.mathrider.piper.*;


public class LispGenericClass extends LispObject
{
	GenericClass iClass;
	
	public static LispGenericClass newGenericClass(GenericClass aClass) throws Exception
	{
		LispError.LISPASSERT(aClass!=null);
		LispGenericClass self = new LispGenericClass(aClass);
		LispError.Check(self!=null,LispError.KLispErrNotEnoughMemory);
		return self;
	}
	
	public GenericClass generic()
	{
		return iClass;
	}
	
	public String string()
	{
		return null;
	}
	
	public LispObject copy(boolean aRecursed)
	{
		LispObject copied = new LispGenericClass(iClass);
		return copied;
	}
	
	public LispObject setExtraInfo(LispPtr aData)
	{
		//TODO FIXME
		return null;
	}

	LispGenericClass(GenericClass aClass)
	{
		iClass = aClass;
	}
};
