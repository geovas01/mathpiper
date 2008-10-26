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
package org.mathpiper.lisp.tokenizers;

import org.mathpiper.lisp.TokenHash;
import org.mathpiper.io.InputStream;

/**
 *
 *  
 */
/*
Running MathPiper from Eclipse IDE
From: Axel <axelclk@gm...> - 2005-08-14 09:56




Hi

I"ve a little project which is similar to the piper applet:
http://www.matheclipse.org
which uses a JavaScript interface and a Java servlet backend.

So I would like to test the piper applet from sources inside the
http://www.eclipse.org IDE and checked out JavaMathPiper from CVS and get the
following compile errors:

Severity	Description	Resource	In Folder	Location	Creation Time
2	Syntax error on token "goto", throw
expected	CommonLispTokenizer.java	JavaMathPiper	line 14	14. August 2005
18:43:21
2	Syntax error on token "goto", break
expected	CommonLispTokenizer.java	JavaMathPiper	line 47	14. August 2005
18:43:21
2	Syntax error on token "goto", throw
expected	CommonLispTokenizer.java	JavaMathPiper	line 49	14. August 2005
18:43:21
2	Syntax error on token "goto", break
expected	CommonLispTokenizer.java	JavaMathPiper	line 54	14. August 2005
18:43:21
2	Syntax error on token "goto", throw
expected	CommonLispTokenizer.java	JavaMathPiper	line 113	14. August 2005
18:43:21
2	Syntax error on token "&", delete this
token	CommonLispTokenizer.java	JavaMathPiper	line 117	14. August 2005
18:43:21
2	CVersion cannot be resolved	ConsoleApplet.java	JavaMathPiper	line
123	14. August 2005 18:43:21
2	CVersion cannot be resolved	MathCommands.java	JavaMathPiper	line
4194	14. August 2005 18:43:20
2	CVersion cannot be resolved	MathPiperConsole.java	JavaMathPiper	line 134	14.
August 2005 18:43:18
2	The method AddLine(String) in the type HintWindow is not applicable
for the arguments (String,
MathPiperGraphicsContext)	MathPiperNotebook.java	JavaMathPiper	line 36	14. August
2005 18:43:18
2	The method AddLine(String) in the type HintWindow is not applicable
for the arguments (String,
MathPiperGraphicsContext)	MathPiperNotebook.java	JavaMathPiper	line 37	14. August
2005 18:43:18
2	The method AddDescription(String) in the type HintWindow is not
applicable for the arguments (String,
MathPiperGraphicsContext)	MathPiperNotebook.java	JavaMathPiper	line 38	14. August
2005 18:43:18
2	The method AddDescription(String) in the type HintWindow is not
applicable for the arguments (String,
MathPiperGraphicsContext)	MathPiperNotebook.java	JavaMathPiper	line 39	14. August
2005 18:43:18

BTW:
 * all *.java files don"t contain a package declaration, which is
really unusual in Java, by convention you can use something like:
"package net.sourceforge.piper;"
 * also by convention in most projects java source files a stored below
a subfolder "/src".
 * I attached the eclipse .project and .classpath I"ve used for the test.

-- 
Axel Kramer





Re: Running MathPiper from Eclipse IDE
From: Ayal Pinkus <apinkus@xs...> - 2005-08-14 11:32

Hi Axel,
matheclipse looks interesting!

For now I think you can leave out compiling CommonLispTokenizer.java.

CVersion.java is generated by the make file makefile.piper. The  
contents are
currently:

class CVersion { static String VERSION = "1.0.58"; }

For now you can skip compiling MathPiperNotebook.java too. Regarding  
package,
that is something some one else mentioned too. I am not a very  
experienced
Java programmer, as you might be able to tell.

I will fix these things as soon as possible. In the mean time, I"ll  
forward an email I
got from some one who is trying to use it in a way that might be  
similar to how you
want to use it. He made changes that made it work for him. That might  
be of interest
to you.

Ayal



*/

import org.mathpiper.lisp.*;

class CommonLispTokenizer extends MathPiperTokenizer
{
/*
class CommonLispTokenizer extends LispTokenizer
{
    String NextToken(LispInput aInput, LispHashTable aHashTable)
  {
    char c;
    int firstpos;

REDO://TODO FIXME
    
    while(true)
    {
    firstpos = aInput.Position();

    // End of stream: return empty string
    if (aInput.EndOfStream())
    {
        return aHashTable.LookUpCounted(&aInput.StartPtr()[firstpos],aInput.Position()-firstpos);
    }
      //goto FINISH;

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
    else if (c == '\'') {}
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
FALSEALARM://TODO FIXME
      while (aInput.Next() != '*' && !aInput.EndOfStream());
      Check(!aInput.EndOfStream(),KLispErrCommentToEndOfFile);
      if (aInput.Peek() == '/')
      {
        aInput.Next();  // consume /
        goto REDO;
      }
      goto FALSEALARM;
    }
    else if (c == ';')
    {
      while (aInput.Next() != '\n' && !aInput.EndOfStream());
      goto REDO;
    }
    // parse literal strings
    else if (c == '\"')
    {
      LispString aResult;
      aResult.Resize(0);
      aResult.Append(c);
      while (aInput.Peek() != '\"')
      {
        if (aInput.Peek() == '\\')
        {
          aInput.Next();
          Check(!aInput.EndOfStream(),KLispErrParsingInput);
        }
        aResult.Append(aInput.Next());
        Check(!aInput.EndOfStream(),KLispErrParsingInput);
      }
      aResult.Append(aInput.Next()); // consume the close quote
      aResult.Append('\0');
      return aHashTable.LookUp(aResult.String());
    }
    //parse atoms
    else if (IsAlpha(c) || IsSymbolic(c))
    {
      while (IsAlNum( aInput.Peek()) || IsSymbolic( aInput.Peek()))
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
      if (NumericSupportForMantissa())
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
      goto REDO;
    }

//FINISH://TODO FIXME
    return aHashTable.LookUpCounted(&aInput.StartPtr()[firstpos],aInput.Position()-firstpos);
  }
  
   */
}

