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


/**
 * 
 * @author 
 */
public class LispNumber extends LispObject
{
        /* Note: Since LispNumber is a LispAtom, shouldn't it extend LispAtom instead of LispObject? tk
        */
    
	/// number object; NULL if not yet converted from string
	BigNumber iNumber;
	/// string representation in decimal; NULL if not yet converted from BigNumber
	String iString;

	/// constructors:
	/// construct from another LispNumber
	public LispNumber(BigNumber aNumber,String aString)
	{
		iString = aString;
		iNumber = aNumber;
	}

	/// construct from a BigNumber; the string representation will be absent
	public LispNumber(BigNumber aNumber)
	{
		iString = null;
		iNumber =aNumber;
	}

	/// construct from a decimal string representation (also create a number object) and use aBasePrecision decimal digits
	public LispNumber(String aString, int aBasePrecision)
	{
		iString = aString;
		iNumber = null;  // purge whatever it was
		// create a new BigNumber object out of iString, set its precision in digits
		//TODO FIXME enable this in the end    Number(aBasePrecision);
	}

	public LispObject Copy(boolean aRecursed)
	{
		return new LispNumber(iNumber, iString);
	}

	/// return a string representation in decimal with maximum decimal precision allowed by the inherent accuracy of the number
	public String String() throws Exception
	{
		if (iString == null)
		{
			LispError.LISPASSERT(iNumber != null);  // either the string is null or the number but not both
			iString = iNumber.ToString(0/*TODO FIXME*/,10);
			// export the current number to string and store it as LispNumber::iString
		}
		return iString;
	}

	/// give access to the BigNumber object; if necessary, will create a BigNumber object out of the stored string, at given precision (in decimal?)
	public BigNumber Number(int aPrecision) throws Exception
	{
		if (iNumber == null)
		{  // create and store a BigNumber out of string
			LispError.LISPASSERT(iString != null);
			String str;
			str = iString;
			// aBasePrecision is in digits, not in bits, ok
			iNumber = new BigNumber(str, aPrecision, 10/*TODO FIXME BASE10*/);
		}

		// check if the BigNumber object has enough precision, if not, extend it
		// (applies only to floats). Note that iNumber->GetPrecision() might be < 0

		else if (!iNumber.IsInt() && iNumber.GetPrecision() < aPrecision)
		{
			if (iString != null)
			{// have string representation, can extend precision
				iNumber.SetTo(iString,aPrecision, 10);
			}
			else
			{
				// do not have string representation, cannot extend precision!
			}
		}

		return iNumber;
	}

	/// annotate
	public LispObject SetExtraInfo(LispPtr aData)
	{
		/*TODO FIXME
		LispObject* result = NEW LispAnnotatedObject<LispNumber>(this);
		result->SetExtraInfo(aData);
		return result;
		*/
		return null;
	}

}
