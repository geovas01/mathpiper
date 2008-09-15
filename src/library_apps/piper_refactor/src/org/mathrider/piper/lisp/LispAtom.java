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


public class LispAtom extends LispObject
{
	String iString;

	public static LispObject New(LispEnvironment aEnvironment, String aString) throws Exception
	{
		LispObject self = null;
		if (LispStandard.IsNumber(aString,true))  // check if aString is a number (int or float)
		{
			/// construct a number from a decimal string representation (also create a number object)
			self = new LispNumber(aString, aEnvironment.Precision());
		}
		else
		{
			self = new LispAtom(aEnvironment.HashTable().LookUp(aString));
		}
		LispError.Check(self!=null,LispError.KLispErrNotEnoughMemory);
		return self;
	}
	public String String()
	{
		return iString;
	}
	public LispObject Copy(boolean aRecursed)
	{
		return new LispAtom(iString);
	}
	public LispObject SetExtraInfo(LispPtr aData)
	{
		//TODO FIXME
		System.out.println("NOT YET IMPLEMENTED!!!");
		/*
		    LispObject result = new LispAnnotatedObject<LispAtom>(this);
		    result->SetExtraInfo(aData);
		    return result;
		*/
		return null;
	}
	LispAtom(String aString)
	{
		iString = aString;
	}

};
