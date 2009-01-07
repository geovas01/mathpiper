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
package org.mathpiper.ui.gui.worksheets;

import org.mathpiper.ui.gui.worksheets.SBoxBuilder;
import org.mathpiper.ui.gui.worksheets.SBox;


public class TexParser
{

	static String singleOps = "^_+=,";
	int currentPos;
	String iCurrentExpression;
	String nextToken;

	private void showToken()
	{
		System.out.println("[" + nextToken + "]");
	}

	void nextToken()
	{
		nextToken = "";

		if (currentPos == iCurrentExpression.length())
		{

			//showToken();
			return;
		}

		while (currentPos < iCurrentExpression.length() && isSpace(iCurrentExpression.charAt(currentPos)))
			currentPos++;

		if (currentPos == iCurrentExpression.length())
		{

			//showToken();
			return;
		}
		else if (isAlNum(iCurrentExpression.charAt(currentPos)))
		{

			int startPos = currentPos;

			while (currentPos < iCurrentExpression.length() && isAlNum(iCurrentExpression.charAt(currentPos)))
			{
				currentPos++;
			}

			nextToken = iCurrentExpression.substring(startPos, currentPos);

			//showToken();
			return;
		}

		int c = iCurrentExpression.charAt(currentPos);

		if (c == '{')
		{
			nextToken = "{";
			currentPos++;

			//showToken();
			return;
		}
		else if (c == '}')
		{
			nextToken = "}";
			currentPos++;

			//showToken();
			return;
		}
		else if (singleOps.indexOf(c) >= 0)
		{
			nextToken = "" + (char)c;
			currentPos++;

			//showToken();
			return;
		}
		else if (c == '\\')
		{

			int startPos = currentPos;

			while (currentPos < iCurrentExpression.length() && (isAlNum(iCurrentExpression.charAt(currentPos)) || iCurrentExpression.charAt(currentPos) == '\\'))
			{
				currentPos++;
			}

			nextToken = iCurrentExpression.substring(startPos, currentPos);

			//showToken();
			return;
		}

		//showToken();
	}

	boolean matchToken(String token)
	{

		if (nextToken.equals(token))

			return true;

		System.out.println("Found " + nextToken + ", expected " + token);

		return false;
	}

	public SBox parse(String aExpression)
	{
		iCurrentExpression = aExpression;
		currentPos = 0;
		nextToken();

		return parseTopExpression();
	}

	SBox parseTopExpression()
	{

		SBoxBuilder builder = new SBoxBuilder();
		parseOneExpression10(builder);

		SBox expression = builder.pop();

		return expression;
	}

	void parseOneExpression10(SBoxBuilder builder)
	{
		parseOneExpression20(builder);

		// = ,
		while (nextToken.equals("=") || nextToken.equals("\\neq") || nextToken.equals(","))
		{

			String token = nextToken;
			nextToken();
			parseOneExpression20(builder);
			builder.process(token);
		}
	}

	void parseOneExpression20(SBoxBuilder builder)
	{
		parseOneExpression25(builder);

		// +, -
		while (nextToken.equals("+") || nextToken.equals("-") || nextToken.equals("\\wedge") || nextToken.equals("\\vee") || nextToken.equals("<") || nextToken.equals(">") || nextToken.equals("\\leq") || nextToken.equals("\\geq"))
		{

			String token = nextToken;

			if (token.equals("-"))
				token = "-/2";
			else if (token.equals("\\leq"))
				token = "<=";
			else if (token.equals("\\geq"))
				token = ">=";

			nextToken();
			parseOneExpression25(builder);
			builder.process(token);
		}
	}

	void parseOneExpression25(SBoxBuilder builder)
	{
		parseOneExpression30(builder);

		// implicit *
		while (nextToken.length() > 0 && !nextToken.equals("+") && !nextToken.equals("-") && !nextToken.equals("=") && !nextToken.equals("\\neq") && !nextToken.equals("}") && !nextToken.equals("&") && !nextToken.equals("\\wedge") && !nextToken.equals("\\vee") && !nextToken.equals("<") && !nextToken.equals(">") && !nextToken.equals("\\leq") && !nextToken.equals("\\geq") && !nextToken.equals("\\end") && !nextToken.equals("\\\\") && !nextToken.equals("\\right)") && !nextToken.equals("\\right]") && !nextToken.equals(","))
		{

			//System.out.println("nextToken = "+nextToken);
			String token = "*";
			parseOneExpression30(builder);

			//System.out.println("After: nextToken = "+nextToken);
			builder.process(token);
		}
	}

	void parseOneExpression30(SBoxBuilder builder)
	{
		parseOneExpression40(builder);

		// _, ^
		while (nextToken.equals("_") || nextToken.equals("^") || nextToken.equals("!"))
		{

			if (nextToken.equals("!"))
			{
				builder.process(nextToken);
				nextToken();
			}
			else
			{

				String token = nextToken;
				nextToken();
				parseOneExpression40(builder);
				builder.process(token);
			}
		}
	}

	void parseOneExpression40(SBoxBuilder builder)
	{

		// atom
		if (nextToken.equals("{"))
		{
			nextToken();
			parseOneExpression10(builder);

			if (!nextToken.equals("}"))
			{
				System.out.println("Got " + nextToken + ", expected }");

				return;
			}
		}
		else if (nextToken.equals("\\left("))
		{
			nextToken();
			parseOneExpression10(builder);

			if (!nextToken.equals("\\right)"))
			{
				System.out.println("Got " + nextToken + ", expected \\right)");

				return;
			}

			builder.process("[roundBracket]");
		}
		else if (nextToken.equals("\\left["))
		{
			nextToken();
			parseOneExpression10(builder);

			if (!nextToken.equals("\\right]"))
			{
				System.out.println("Got " + nextToken + ", expected \\right]");

				return;
			}

			builder.process("[squareBracket]");
		}
		else if (nextToken.equals("\\sqrt"))
		{
			nextToken();
			parseOneExpression25(builder);
			builder.process("[sqrt]");

			return;
		}
		else if (nextToken.equals("\\exp"))
		{
			nextToken();
			builder.process("e");
			parseOneExpression40(builder);
			builder.process("^");

			return;
		}
		else if (nextToken.equals("\\imath"))
		{
			builder.process("i");
		}
		else if (nextToken.equals("\\mathrm"))
		{
			nextToken();

			if (!matchToken("{"))

				return;

			int startPos = currentPos;

			while (currentPos < iCurrentExpression.length() && iCurrentExpression.charAt(currentPos) != '}')
				currentPos++;

			String literal = iCurrentExpression.substring(startPos, currentPos);
			currentPos++;
			builder.processLiteral(literal);
			nextToken();

			return;
		}
		else if (nextToken.equals("-"))
		{
			nextToken();
			parseOneExpression30(builder);
			builder.process("-/1");

			return;
		}
		else if (nextToken.equals("\\neg"))
		{
			nextToken();
			parseOneExpression30(builder);
			builder.process("~");

			return;
		}
		else if (nextToken.equals("\\sum"))
		{
			builder.process("[sum]");
		}
		else if (nextToken.equals("\\int"))
		{
			builder.process("[int]");
		}
		else if (nextToken.equals("\\frac"))
		{
			nextToken();
			parseOneExpression40(builder);
			parseOneExpression40(builder);
			builder.process("/");

			return;
		}
		else if (nextToken.equals("\\begin"))
		{
			nextToken();

			if (!matchToken("{"))

				return;

			nextToken();

			String name = nextToken;
			nextToken();

			if (!matchToken("}"))

				return;

			if (name.equals("array"))
			{

				int nrColumns = 0;
				int nrRows = 0;
				nextToken();

				if (!matchToken("{"))

					return;

				nextToken();

				String coldef = nextToken;
				nextToken();

				if (!matchToken("}"))

					return;

				nrColumns = coldef.length();
				nrRows = 1;
				nextToken();

				while (!nextToken.equals("\\end"))
				{
					parseOneExpression10(builder);

					if (nextToken.equals("\\\\"))
					{
						nrRows++;
						nextToken();
					}
					else if (nextToken.equals("&"))
					{
						nextToken();
					}
					else
					{

						//  System.out.println("END? "+nextToken);
					}
				}

				nextToken();

				if (!matchToken("{"))

					return;

				nextToken();

				String name2 = nextToken;
				nextToken();

				if (!matchToken("}"))

					return;

				if (name2.equals("array"))
				{
					builder.process("" + nrRows);
					builder.process("" + nrColumns);
					builder.process("[grid]");
				}
			}
		}
		else
		{
			builder.process(nextToken);
		}

		nextToken();
	}

	boolean isSpace(int c)
	{

		if (c == ' ' || c == '\t' || c == '\r' || c == '\n')

			return true;

		return false;
	}

	boolean isAlNum(int c)
	{

		if (isSpace(c))

			return false;

		if (c == '{')

			return false;

		if (c == '}')

			return false;

		if (c == '\\')

			return false;

		if (singleOps.indexOf(c) >= 0)

			return false;

		return true;
	}
}
