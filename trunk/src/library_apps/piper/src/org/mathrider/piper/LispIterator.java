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

/**
 * class LispIterator works almost like LispPtr, but doesn't enforce
 * reference counting, so it should be slightly faster. This one
 * should be used in stead of LispPtr if you are going to traverse
 * a lisp expression in a non-destructive way.
 */
class LispIterator
{
	LispPtr iPtr;
	
	public LispIterator(LispPtr aPtr)
	{
		iPtr = aPtr;
	}
	
	public LispObject GetObject()
	{
		return iPtr.Get();
	}
	
	public LispPtr Ptr()
	{
		return iPtr;
	}
	
	public void GoNext() throws Exception
	{
		LispError.Check(iPtr.Get() != null,LispError.KLispErrListNotLongEnough);
		iPtr = (iPtr.Get().Next());
	}
	
	public void GoSub() throws Exception
	{
		LispError.Check(iPtr.Get() != null,LispError.KLispErrInvalidArg);
		LispError.Check(iPtr.Get().SubList() != null,LispError.KLispErrNotList);
		iPtr = iPtr.Get().SubList();
	}

};

