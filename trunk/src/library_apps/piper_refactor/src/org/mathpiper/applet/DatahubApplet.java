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

package org.mathpiper.applet;

import java.applet.*;
import java.net.*;
import java.io.*;


/* This little applet should facilitate communication between Java and Javascript.
 * The idea is to allow Javascript to set data in this applet at various times, and
 * for the main Piper console to then get that data at startup, when it is loaded.
 *
 * The Piper console thus does not need to be loaded always, but this applet should
 * always be there.
 */


public class DatahubApplet extends Applet
{
	static Article consoleProgram = new Article("You can start entering your own program between the code delimiters below.\n\n{{code:\n:code}}\n"+
	                                "\n\n{{example:Example();:example}}");
	static Article journalProgram = new Article("No article loaded yet.");
	static Article tutorialProgram = new Article("{{code: Echo(\"Welcome to the Piper tutorial!\"); :code}}");
	static int currentProgram = 0;

	public void init()
	{
		String programMode = getParameter("programMode");
		if (programMode != null)
		{
			setProgramMode(programMode);
			String articleFromFile = getParameter("articleFromFile");
			if (articleFromFile != null)
			{
				setArticleFromFile(articleFromFile);
			}

			String article = getParameter("article");
			if (article != null)
			{
				setArticle(article);
			}
		}
	}

	Article currentArticle()
	{
		switch (currentProgram)
		{
		case 2:
			return tutorialProgram;
		case 1:
			return journalProgram;
		case 0:
		default:
			return consoleProgram;
		}
	}
	public String getProgram()
	{
		synchronized(consoleProgram)
		{
			return currentArticle().codeBody;
		}
	}

	public String getExample()
	{
		synchronized(consoleProgram)
		{
			return currentArticle().getExample();
		}
	}

	public String getTestcode()
	{
		synchronized(consoleProgram)
		{
			return currentArticle().getTestcode();
		}
	}


	public String getNrExamples()
	{
		synchronized(consoleProgram)
		{
			return currentArticle().getNrExamples();
		}
	}


	public String getArticle()
	{
		synchronized(consoleProgram)
		{
			return currentArticle().iArticle;
		}
	}
	public String getArticleBody()
	{
		synchronized(consoleProgram)
		{
			return currentArticle().articleBody;
		}
	}

	public void setArticle(String p)
	{
		//System.out.println("article:\n"+p);
		synchronized(consoleProgram)
		{
			p = unescape(p);
			currentArticle().SetArticle(p);
		}
	}

	public void setProgramMode(String mode)
	{
		synchronized(consoleProgram)
		{
			if (mode.equals("console"))
			{
				currentProgram = 0;
			}
			else if (mode.equals("journal"))
			{
				currentProgram = 1;
			}
			else if (mode.equals("tutorial"))
			{
				currentProgram = 2;
			}
		}
	}

	String readArticleFromFile(String urlStr)
	{
		String docbase = getDocumentBase().toString();
		int pos = docbase.lastIndexOf('/');
		if (pos > -1)
		{
			docbase = docbase.substring(0,pos+1);
		}
		else
		{
			docbase = "";
		}
		docbase = docbase+urlStr;

		String prog = "";
		try
		{
			URL url = new URL(docbase);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			if (in != null)
			{
				while (true)
				{
					try
					{
						String mark = in.readLine();
						if (mark == null)
							break;
						prog = prog + mark + "\n";
					}
					catch (Exception e)
					{
					}
				}
				in.close();
			}
		}
		catch(Exception e)
		{
		}
		return prog;
	}


	private int unhex(int c)
	{
		if (c>='0' && c <= '9')
		{
			return (c-'0');
		}
		if (c>='a' && c <= 'f')
		{
			return 10 + (c-'a');
		}
		if (c>='A' && c <= 'F')
		{
			return 10 + (c-'A');
		}
		return 65;
	}
	private String unescape(String s)
	{
		StringBuffer buf = new StringBuffer();
		int i,nr=s.length();
		for(i=0;i<nr;i++)
		{
			if (s.charAt(i) == '%')
			{
				int high = s.charAt(i+1);
				int low = s.charAt(i+2);
				i+=2;
				int c = 16*unhex(high)+unhex(low);
				buf.append((char)c);
			}
			else
			{
				buf.append(s.charAt(i));
			}
		}
		return buf.toString();
	}


	// To make sure it is not reloaded each time
	static String prevLoadedArticle = "";

	public void setArticleFromFile(String urlStr)
	{
		if (!prevLoadedArticle.equals(urlStr))
		{
			prevLoadedArticle = urlStr;
			synchronized(consoleProgram)
			{
				setArticle(readArticleFromFile(urlStr));
			}
		}
	}


}

