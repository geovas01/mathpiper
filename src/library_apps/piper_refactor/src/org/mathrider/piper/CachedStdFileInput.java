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


import org.mathrider.piper.lisp.LispInput;
import java.io.*;

/** CachedStdFileInput : input from stdin */
public class CachedStdFileInput extends LispInput
{
	StringBuffer iBuffer;
	int iCurrentPos;

	public CachedStdFileInput(InputStatus aStatus)
	{
		super(aStatus);
		Rewind();
	}
	public char Next() throws Exception
	{
		int c = Peek();
		iCurrentPos++;
		if (c == '\n')
			iStatus.NextLine();
		return (char)c;
	}
	public char Peek() throws Exception
	{
		if (iCurrentPos == iBuffer.length())
		{
			int newc;
			newc = System.in.read();
			iBuffer.append((char)newc);
			while (newc != '\n')
			{
				newc = System.in.read();
				iBuffer.append((char)newc);
			}
		}
		return iBuffer.charAt(iCurrentPos);
	}
	public boolean EndOfStream()
	{
		return false;
	}
	public void Rewind()
	{
		iBuffer = new StringBuffer();
		iCurrentPos = 0;
	}
	public StringBuffer StartPtr()
	{
		return iBuffer;
	}
	public int Position()
	{
		return iCurrentPos;
	}
	public void SetPosition(int aPosition)
	{
		iCurrentPos = aPosition;
	}


}
