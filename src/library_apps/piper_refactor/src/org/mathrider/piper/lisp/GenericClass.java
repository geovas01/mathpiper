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


public class GenericClass extends Cons
{
	GenericClassContainer iClass;
	
	public static GenericClass getInstance(GenericClassContainer aClass) throws Exception
	{
		Error.LISPASSERT(aClass!=null);
		GenericClass self = new GenericClass(aClass);
		Error.Check(self!=null,Error.KLispErrNotEnoughMemory);
		return self;
	}
	
	public GenericClassContainer generic()
	{
		return iClass;
	}
	
	public String string()
	{
		return null;
	}
	
	public Cons copy(boolean aRecursed)
	{
		Cons copied = new GenericClass(iClass);
		return copied;
	}
	
	public Cons setExtraInfo(Pointer aData)
	{
		//TODO FIXME
		return null;
	}

	GenericClass(GenericClassContainer aClass)
	{
		iClass = aClass;
	}
};
