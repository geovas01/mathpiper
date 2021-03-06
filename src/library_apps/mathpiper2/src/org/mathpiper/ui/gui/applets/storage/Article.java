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

package org.mathpiper.ui.gui.applets.storage;

public class Article
{
	public String iArticle;
	public String testCode = "";
	public String codeBody = "";
	public String articleBody = "";
	public String[] examples = new String[100];
	public int nrExamples = 0;
	int currentExample = 0;

	public Article(String aArticle)
	{
		setArticle(aArticle);
	}
	public void setArticle(String aArticle)
	{
		iArticle = aArticle;
		testCode = "";
		codeBody = "";
		articleBody = "";
		nrExamples = 0;
		processBody(aArticle);
	}

	public String getExample()
	{
		String result = "\"No example defined\"";
		if (nrExamples>0)
		{
			result = examples[currentExample];
			currentExample++;
			if (currentExample == nrExamples)
			{
				currentExample = 0;
			}
		}
		return result;
	}

	public String getTestcode()
	{
		return testCode;
	}

	public String getNrExamples()
	{
		return ""+nrExamples;
	}

	void processText(String aString)
	{
		int pos = aString.indexOf("\n\n");
		if (pos == -1)
		{
			pos = aString.indexOf("\r\n\r\n");
		}
		if (pos == -1)
		{
			articleBody = articleBody + aString;
			return;
		}
		else
		{
			articleBody = articleBody + aString.substring(0, pos) + "\n<br /><br />\n";
			processText(aString.substring(pos + 2));
		}
	}
	void processBody(String aString)
	{
		int maxNrEntries = 10;
		String keys[] = new String[maxNrEntries];
		String values[] = new String[maxNrEntries];
		int nrEntries = 0;
		while (aString.length() > 0)
		{
			int pos = aString.indexOf("{{");
			if (pos == -1)
			{
				processText(aString);
				return;
			}
			processText(aString.substring(0, pos));
			aString = aString.substring(pos + 2);
			pos = aString.indexOf(":");
			String name = aString.substring(0, pos);
			aString = aString.substring(pos + 1);

			String toProcess = null;
			pos = name.indexOf(",");
			if (pos > -1)
			{
				toProcess = name.substring(pos+1);
				name = name.substring(0,pos);
			}

			String close = ":" + name + "}}";
			pos = aString.indexOf(close);
			String data = aString.substring(0, pos);
			aString = aString.substring(pos + close.length());

			nrEntries = 0;
			if (toProcess != null)
			{
				int pos3 = toProcess.indexOf(",");
				if (pos3 == -1)
					pos3 = toProcess.length();
				while (pos3>=0)
				{
					int pos2 = toProcess.indexOf(".");
					if (pos2 != -1)
					{
						keys[nrEntries] = toProcess.substring(0,pos2);
						values[nrEntries] = toProcess.substring(pos2+1,pos3);
						nrEntries++;
						if (nrEntries == maxNrEntries)
							break;
					}
					if (pos3 < toProcess.length())
					{
						toProcess = toProcess.substring(pos3+1);
						pos3 = toProcess.indexOf(",");
						if (pos3 == -1)
							pos3 = toProcess.length();
					}
					else
					{
						toProcess = "";
						pos3 = -1;
					}
				}
			}

			if (name.equals("title"))
			{
				articleBody = articleBody + "<h1>" + data + "</h1>";
			}
			else if (name.equals("code"))
			{
				boolean addToArticle = true;

				int i;
				for (i=0;i<nrEntries;i++)
				{
					if (keys[i].equals("article"))
					{
						addToArticle = values[i].equals("true");
					}
				}

				codeBody = codeBody + data;
				if (addToArticle)
				{
					articleBody = articleBody + "<table width=\"100%\"><tr><td width=\"100%\" bgcolor=\"#DDDDEE\"><pre>" + data + "</pre></tr></table>";
				}
			}
			else if (name.equals("test"))
			{
				testCode = testCode + data;
			}
			else if (name.equals("expr"))
			{
				articleBody = articleBody + "<tt><b>" + data + "</b></tt>";
			}
			else if (name.equals("math"))
			{
				// Example:
				// {{math,heightPixels.120,widthPixels.700: ... :math}}
				int height=70;
				int i;
				for (i=0;i<nrEntries;i++)
				{
					if (keys[i].equals("heightPixels"))
					{
						height = Integer.parseInt(values[i]);
					}
				}
				articleBody = articleBody + "<applet code=org.mathpiper.FormulaViewApplet archive=\"piper.jar\" width=600 height="+height+"><param name=\"expression\" value=\""+data+"\" /></applet><br />";
			}
			else if (name.equals("example"))
			{
				if (nrExamples < 100)
				{
					examples[nrExamples] = data;
					nrExamples++;
				}
			}
		}
	}
}

