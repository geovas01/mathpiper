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


public class LispTokenizer
{
	static String symbolics = new String("~`!@#$^&*-=+:<>?/\\|");
	String iToken; //Can be used as a token container.

	/// NextToken returns a string representing the next token,
	/// or an empty list.
	public String NextToken(LispInput aInput, LispHashTable aHashTable) throws Exception
	{
		char c;
		int firstpos = aInput.Position();

		boolean redo = true;
		while (redo)
		{
			redo = false;
			//REDO: //TODO FIXME
			firstpos = aInput.Position();

			// End of stream: return empty string
			if (aInput.EndOfStream())
				break;

			c = aInput.Next();
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
			else if (c == '.' && !IsDigit(aInput.Peek()) )
			{
				while (aInput.Peek() == '.')
				{
					aInput.Next();
				}
			}
			// parse comments
			else if (c == '/' && aInput.Peek() == '*')
			{
				aInput.Next(); //consume *
				while (true)
				{
					while (aInput.Next() != '*' && !aInput.EndOfStream());
					LispError.Check(!aInput.EndOfStream(),LispError.KLispErrCommentToEndOfFile);
					if (aInput.Peek() == '/')
					{
						aInput.Next();  // consume /
						redo = true;
						break;
					}
				}
				if (redo)
				{
					continue;
				}
			}
			else if (c == '/' && aInput.Peek() == '/')
			{
				aInput.Next(); //consume /
				while (aInput.Next() != '\n' && !aInput.EndOfStream());
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
				while (aInput.Peek() != '\"')
				{
					if (aInput.Peek() == '\\')
					{
						aInput.Next();
						LispError.Check(!aInput.EndOfStream(),LispError.KLispErrParsingInput);
					}
					//TODO FIXME is following append char correct?
					aResult = aResult + ((char)aInput.Next());
					LispError.Check(!aInput.EndOfStream(),LispError.KLispErrParsingInput);
				}
				//TODO FIXME is following append char correct?
				aResult = aResult + ((char)aInput.Next()); // consume the close quote
				return aHashTable.LookUp(aResult);
			}
			//parse atoms
			else if (IsAlpha(c))
			{
				while (IsAlNum( aInput.Peek()))
				{
					aInput.Next();
				}
			}

			else if (IsSymbolic(c))
			{
				while (IsSymbolic( aInput.Peek()))
				{
					aInput.Next();
				}
			}
			else if (c == '_')
			{
				while (aInput.Peek() == '_')
				{
					aInput.Next();
				}
			}
			else if (IsDigit(c) || c == '.')
			{
				while (IsDigit( aInput.Peek())) aInput.Next();
				if (aInput.Peek() == '.')
				{
					aInput.Next();
					while (IsDigit( aInput.Peek())) aInput.Next();
				}
				if (BigNumber.NumericSupportForMantissa())
				{
					if (aInput.Peek() == 'e' || aInput.Peek() == 'E')
					{
						aInput.Next();
						if (aInput.Peek() == '-' || aInput.Peek() == '+')
							aInput.Next();
						while (IsDigit( aInput.Peek())) aInput.Next();
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
		return aHashTable.LookUp(aInput.StartPtr().substring(firstpos,aInput.Position()));
	}

	public static boolean IsDigit(char c)
	{
		return ((c>='0' && c<='9'));
	}

	public static boolean IsAlpha(char c)
	{
		return ( (c>='a' && c<='z') || (c>='A' && c<='Z') || (c == '\'') );
	}

	public static boolean IsAlNum(char c)
	{
		return (IsAlpha(c) || IsDigit(c));
	}
	
	
	
	public static boolean IsSymbolic(char c)
	{
		return (symbolics.indexOf(c) >= 0);
	}
	
};

