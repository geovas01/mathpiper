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
 */

//}}}
// :indentSize=4:lineSeparator=\n:noTabs=false:tabSize=4:folding=explicit:collapseFolds=0:
package org.mathrider.piper;

import org.mathrider.piper.lisp.LispHashTable;
import org.mathrider.piper.lisp.LispError;
import org.mathrider.piper.lisp.LispTokenizer;
import org.mathrider.piper.lisp.LispInput;

public class XmlTokenizer
			extends LispTokenizer
{

	/// NextToken returns a string representing the next token,
	/// or an empty list.
	public String nextToken(LispInput aInput, LispHashTable aHashTable)
	throws Exception
	{

		char c;
		int firstpos = 0;

		if (aInput.EndOfStream())

			return aHashTable.lookUp(aInput.StartPtr().substring(firstpos, aInput.Position()));

		//skipping spaces
		while (IsSpace(aInput.Peek()))
			aInput.Next();

		firstpos = aInput.Position();
		c = aInput.Next();

		if (c == '<')
		{

			while (c != '>')
			{
				c = aInput.Next();
				LispError.Check(!aInput.EndOfStream(), LispError.KLispErrCommentToEndOfFile);
			}
		}
		else
		{

			while (aInput.Peek() != '<' && !aInput.EndOfStream())
			{
				c = aInput.Next();
			}
		}

		return aHashTable.lookUp(aInput.StartPtr().substring(firstpos, aInput.Position()));
	}

	private static boolean IsSpace(int c)
	{

		switch (c)
		{

		case 0x20:
		case 0x0D:
		case 0x0A:
		case 0x09:
			return true;

		default:
			return false;
		}
	}
}
