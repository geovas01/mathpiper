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

package org.mathrider.piper.lisp.parsers;

import org.mathrider.piper.builtin.BigNumber;
import org.mathrider.piper.lisp.*;
import org.mathrider.piper.*;


public class Tokenizer
{
	static String symbolics = new String("~`!@#$^&*-=+:<>?/\\|");
	String iToken; //Can be used as a token container.

	/// NextToken returns a string representing the next token,
	/// or an empty list.
	public String nextToken(Input aInput, HashTable aHashTable) throws Exception
	{
		char c;
		int firstpos = aInput.position();

		boolean redo = true;
		while (redo)
		{
			redo = false;
			//REDO: //TODO FIXME
			firstpos = aInput.position();

			// End of stream: return empty string
			if (aInput.endOfStream())
				break;

			c = aInput.next();
			//printf("%c",c);

			//Parse brackets
		if (c == '(')      {}
			else if (c == ')') {}
			else if (c == '{') {}
			else if (c == '}') {}
			else if (c == '[') {}
			else if (c == ']') {}
			else if (c == ',') {}
			else if (c == ';') {}
			else if (c == '%') {}
			//    else if (c == '\'') {}
			else if (c == '.' && !isDigit(aInput.peek()) )
			{
				while (aInput.peek() == '.')
				{
					aInput.next();
				}
			}
			// parse comments
			else if (c == '/' && aInput.peek() == '*')
			{
				aInput.next(); //consume *
				while (true)
				{
					while (aInput.next() != '*' && !aInput.endOfStream());
					LispError.Check(!aInput.endOfStream(),LispError.KLispErrCommentToEndOfFile);
					if (aInput.peek() == '/')
					{
						aInput.next();  // consume /
						redo = true;
						break;
					}
				}
				if (redo)
				{
					continue;
				}
			}
			else if (c == '/' && aInput.peek() == '/')
			{
				aInput.next(); //consume /
				while (aInput.next() != '\n' && !aInput.endOfStream());
				redo = true;
				continue;
			}
			// parse literal strings
			else if (c == '\"')
			{
				String aResult;
				aResult = "";
				//TODO FIXME is following append char correct?
				aResult = aResult + ((char)c);
				while (aInput.peek() != '\"')
				{
					if (aInput.peek() == '\\')
					{
						aInput.next();
						LispError.Check(!aInput.endOfStream(),LispError.KLispErrParsingInput);
					}
					//TODO FIXME is following append char correct?
					aResult = aResult + ((char)aInput.next());
					LispError.Check(!aInput.endOfStream(),LispError.KLispErrParsingInput);
				}
				//TODO FIXME is following append char correct?
				aResult = aResult + ((char)aInput.next()); // consume the close quote
				return aHashTable.lookUp(aResult);
			}
			//parse atoms
			else if (isAlpha(c))
			{
				while (isAlNum( aInput.peek()))
				{
					aInput.next();
				}
			}

			else if (isSymbolic(c))
			{
				while (isSymbolic( aInput.peek()))
				{
					aInput.next();
				}
			}
			else if (c == '_')
			{
				while (aInput.peek() == '_')
				{
					aInput.next();
				}
			}
			else if (isDigit(c) || c == '.')
			{
				while (isDigit( aInput.peek())) aInput.next();
				if (aInput.peek() == '.')
				{
					aInput.next();
					while (isDigit( aInput.peek())) aInput.next();
				}
				if (BigNumber.NumericSupportForMantissa())
				{
					if (aInput.peek() == 'e' || aInput.peek() == 'E')
					{
						aInput.next();
						if (aInput.peek() == '-' || aInput.peek() == '+')
							aInput.next();
						while (isDigit( aInput.peek())) aInput.next();
					}
				}
			}
			// Treat the char as a space.
			else
			{
				redo = true;
				continue;
			}
		}
		return aHashTable.lookUp(aInput.startPtr().substring(firstpos,aInput.position()));
	}

	public static boolean isDigit(char c)
	{
		return ((c>='0' && c<='9'));
	}

	public static boolean isAlpha(char c)
	{
		return ( (c>='a' && c<='z') || (c>='A' && c<='Z') || (c == '\'') );
	}

	public static boolean isAlNum(char c)
	{
		return (isAlpha(c) || isDigit(c));
	}
	
	
	
	public static boolean isSymbolic(char c)
	{
		return (symbolics.indexOf(c) >= 0);
	}
	
};

