/*
    This file is part of JLog.

    Created by Glendon Holst for Alan Mackworth and the 
    "Computational Intelligence: A Logical Approach" text.
    
    Copyright 1998, 2000, 2002, 2008 by University of British Columbia, 
    Alan Mackworth and Glendon Holst.
    
    This notice must remain in all files which belong to, or are derived 
    from JLog.
    
    Check <http://jlogic.sourceforge.net/> or 
    <http://sourceforge.net/projects/jlogic> for further information
    about JLog, or to contact the authors.

    JLog is free software, dual-licensed under both the GPL and MPL 
    as follows:

    You can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Or, you can redistribute it and/or modify
    it under the terms of the Mozilla Public License as published by
    the Mozilla Foundation; version 1.1 of the License.

    JLog is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License, or the Mozilla Public License for 
    more details.

    You should have received a copy of the GNU General Public License
    along with JLog, in the file GPL.txt; if not, write to the 
    Free Software Foundation, Inc., 
    59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    URLs: <http://www.fsf.org> or <http://www.gnu.org>

    You should have received a copy of the Mozilla Public License
    along with JLog, in the file MPL.txt; if not, contact:
    http://http://www.mozilla.org/MPL/MPL-1.1.html
    URLs: <http://www.mozilla.org/MPL/>
*/
//#########################################################################
//	pTokenizeStream
//#########################################################################

package ubc.cs.JLog.Parser;

import java.io.*;
import java.util.*;

/**
* Takes an input stream and produces a stream of tokens.  This is done using a
* pre tokenized stream (partial tokenizing parse), tokenizer tables for each type of
* pre token, and a pushback stack (for look aheads). 
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
class pTokenizeStream
{ 
 protected pPreTokenizeStream 		tokenizer;
 protected pTokenizerTable 		table,string_table,linecomment_table,comment_table;
 protected pTokenizerTable 		array_table,singles_table,basenumber_table;
 protected Stack 			pushback;
 
 protected int 			currentLine; //the current line of the reader
 protected int 			currentChar; // current offset of the current line
 
 protected final static int 		TOKEN_SINGLEQUOTE = pTokenizerTable.TOKEN_SINGLE + 1;
 protected final static int 		TOKEN_QUOTE = pTokenizerTable.TOKEN_SINGLE + 2;
 protected final static int 		TOKEN_STARTPAREN = pTokenizerTable.TOKEN_SINGLE + 3;
 protected final static int 		TOKEN_ENDPAREN = pTokenizerTable.TOKEN_SINGLE + 4;
 protected final static int 		TOKEN_STARTBRACE = pTokenizerTable.TOKEN_SINGLE + 5;
 protected final static int 		TOKEN_ENDBRACE = pTokenizerTable.TOKEN_SINGLE + 6;
 protected final static int 		TOKEN_STARTBRACKET = pTokenizerTable.TOKEN_SINGLE + 7;
 protected final static int 		TOKEN_ENDBRACKET = pTokenizerTable.TOKEN_SINGLE + 8;
 protected final static int 		TOKEN_COMMA = pTokenizerTable.TOKEN_SINGLE + 9;
 protected final static int 		TOKEN_PERIOD = pTokenizerTable.TOKEN_SINGLE + 10;
 protected final static int 		TOKEN_SEMICOLON = pTokenizerTable.TOKEN_SINGLE + 11;
 protected final static int 		TOKEN_LINECOMMENT = pTokenizerTable.TOKEN_SINGLE + 12;
 protected final static int 		TOKEN_CUT = pTokenizerTable.TOKEN_SINGLE + 13;
 protected final static int 		TOKEN_LISTCONS = pTokenizerTable.TOKEN_SINGLE + 14;
 protected final static int 		TOKEN_FORWARDSLASH = pTokenizerTable.TOKEN_SINGLE + 15;
 protected final static int 		TOKEN_STAR = pTokenizerTable.TOKEN_SINGLE + 16;
 
 protected final static int 		TOKEN_WHITESPACE = 2;
 protected final static int 		TOKEN_WORDS = 3;
 protected final static int 		TOKEN_SECONDARYWORDS = 4;
 protected final static int 		TOKEN_NUMBERS = 5;
 
 public pTokenizeStream(String s)
 { 
  tokenizer = new pPreTokenizeStream(new StringReader(s));

  initTokenizer();
 };
 
 public pTokenizeStream(Reader r)
 {
  tokenizer = new pPreTokenizeStream(r);

  initTokenizer();
 };
 
 protected void 	initTokenizer()
 {
  pushback = new Stack();
 
  table = new pTokenizerTable();
  string_table = new pTokenizerTable();
  array_table = new pTokenizerTable();
  linecomment_table = new pTokenizerTable();
  comment_table = new pTokenizerTable();
  singles_table = new pTokenizerTable();
  basenumber_table = new pTokenizerTable();
 
  currentLine = 0;
  currentChar = 0;
 
  initTable();
  initStringTable();
  initArrayTable();
  initLineCommentTable();
  initCommentTable();
  initSinglesTable();
  initBaseNumberTable();
  
  tokenizer.useTokenizerTable(table);
 };
  
 protected void 	initTable()
 {
  table.setTokenType('%',TOKEN_LINECOMMENT);

  table.setTokenType('A','Z',TOKEN_WORDS);
  table.setTokenType('a','z',TOKEN_WORDS);
  table.setTokenType('_',TOKEN_WORDS);

  table.setTokenType('0','9',TOKEN_NUMBERS);

  table.setTokenType('\"',TOKEN_QUOTE);
  table.setTokenType('\'',TOKEN_SINGLEQUOTE);
  
  table.setTokenType(0,32,TOKEN_WHITESPACE);
  table.setTokenType(10,pTokenizerTable.TOKEN_EOL);
  table.setTokenType(13,pTokenizerTable.TOKEN_EOL);
  
  table.setTokenType('(',TOKEN_STARTPAREN);
  table.setTokenType(')',TOKEN_ENDPAREN);
  
  table.setTokenType('{',TOKEN_STARTBRACE);
  table.setTokenType('}',TOKEN_ENDBRACE);
  
  table.setTokenType('[',TOKEN_STARTBRACKET);
  table.setTokenType(']',TOKEN_ENDBRACKET);
  table.setTokenType('|',TOKEN_LISTCONS);
  
  table.setTokenType(',',TOKEN_COMMA);
  table.setTokenType('.',TOKEN_PERIOD);
  table.setTokenType(';',TOKEN_SEMICOLON);
  table.setTokenType('!',TOKEN_CUT);
  
  table.setTokenType('/',TOKEN_FORWARDSLASH);
  table.setTokenType('*',TOKEN_STAR);
  table.setTokenType(':',TOKEN_SECONDARYWORDS);
  table.setTokenType('-',TOKEN_SECONDARYWORDS);
  table.setTokenType('+',TOKEN_SECONDARYWORDS);
  table.setTokenType('=',TOKEN_SECONDARYWORDS);
  table.setTokenType('<',TOKEN_SECONDARYWORDS);
  table.setTokenType('>',TOKEN_SECONDARYWORDS);
  table.setTokenType('\\',TOKEN_SECONDARYWORDS);
  table.setTokenType('~',TOKEN_SECONDARYWORDS);
  table.setTokenType('`',TOKEN_SECONDARYWORDS);
  table.setTokenType('#',TOKEN_SECONDARYWORDS);
  table.setTokenType('&',TOKEN_SECONDARYWORDS);
  table.setTokenType('?',TOKEN_SECONDARYWORDS);
  table.setTokenType('$',TOKEN_SECONDARYWORDS);
  table.setTokenType('@',TOKEN_SECONDARYWORDS);
  table.setTokenType('^',TOKEN_SECONDARYWORDS);
 }; 
  
 protected void 	initSinglesTable()
 {
  singles_table.setTokenType(0,31,TOKEN_WHITESPACE);
  singles_table.setTokenType(32,255,pTokenizerTable.TOKEN_SINGLE);
  singles_table.setTokenType(10,pTokenizerTable.TOKEN_EOL);
  singles_table.setTokenType(13,pTokenizerTable.TOKEN_EOL);
 }; 
  
 protected void 	initBaseNumberTable()
 {
  basenumber_table.setTokenType(0,31,TOKEN_WHITESPACE);
  basenumber_table.setTokenType('0','9',TOKEN_WORDS);
  basenumber_table.setTokenType('A','Z',TOKEN_WORDS);
  basenumber_table.setTokenType('a','z',TOKEN_WORDS);
  basenumber_table.setTokenType(10,pTokenizerTable.TOKEN_EOL);
  basenumber_table.setTokenType(13,pTokenizerTable.TOKEN_EOL);
 }; 
  
 protected void 	initStringTable()
 {
  string_table.setTokenType(0,31,TOKEN_WHITESPACE);
  string_table.setTokenType(32,255,TOKEN_WORDS);
  string_table.setTokenType(10,pTokenizerTable.TOKEN_EOL);
  string_table.setTokenType(13,pTokenizerTable.TOKEN_EOL);

  string_table.setTokenType('\'',TOKEN_SINGLEQUOTE);
 }; 
  
 protected void 	initArrayTable()
 {
  array_table.setTokenType(0,31,TOKEN_WHITESPACE);
  array_table.setTokenType(32,255,TOKEN_WORDS);
  array_table.setTokenType(10,pTokenizerTable.TOKEN_EOL);
  array_table.setTokenType(13,pTokenizerTable.TOKEN_EOL);

  array_table.setTokenType('\"',TOKEN_QUOTE);
 }; 
  
 protected void 	initLineCommentTable()
 {
  linecomment_table.setTokenType(0,255,TOKEN_WORDS);
  linecomment_table.setTokenType(10,pTokenizerTable.TOKEN_EOL);
  linecomment_table.setTokenType(13,pTokenizerTable.TOKEN_EOL);
 }; 
  
 protected void 	initCommentTable()
 {
  comment_table.setTokenType(0,255,TOKEN_WORDS);
  comment_table.setTokenType(10,pTokenizerTable.TOKEN_EOL);
  comment_table.setTokenType(13,pTokenizerTable.TOKEN_EOL);
  
  comment_table.setTokenType('/',TOKEN_FORWARDSLASH);
  comment_table.setTokenType('*',TOKEN_STAR);
 }; 
  
 public void 		pushBackToken(pToken t)
 {
  pushback.push(t);
 };
 
 public pToken 		getNextToken()
 {
  do
  {
   if (!pushback.empty())
    return (pToken) pushback.pop();
  
   {pTSPreToken 	 	pt;
  
    pt = read();
   
    switch (pt.getType())
    {
     case pPreTokenizeStream.TOKEN_EOF:
      return new pEOF(pt.getPosition(),pt.getLine(),pt.getCharPos());
     case TOKEN_WORDS:
      tokenizer.pushBackToken(pt);
      return parseWords();
     case TOKEN_STAR:
     case TOKEN_SECONDARYWORDS:
      tokenizer.pushBackToken(pt);
      return parseSecondaryWords();
     case TOKEN_QUOTE:
      tokenizer.pushBackToken(pt);
      return parseArray();
     case TOKEN_SINGLEQUOTE:
      tokenizer.pushBackToken(pt);
      return parseString();
     case TOKEN_NUMBERS:
      tokenizer.pushBackToken(pt);
      return parseNumbers();
     case TOKEN_STARTPAREN:
      return new pStartParen(pt.getPosition(),pt.getLine(),pt.getCharPos());
     case TOKEN_ENDPAREN:
      return new pEndParen(pt.getPosition(),pt.getLine(),pt.getCharPos());
     case TOKEN_STARTBRACE:
      tokenizer.pushBackToken(pt);
      return parseBrace();
     case TOKEN_ENDBRACE:
      return new pEndBrace(pt.getPosition(),pt.getLine(),pt.getCharPos());
     case TOKEN_STARTBRACKET:
      return new pStartBracket(pt.getPosition(),pt.getLine(),pt.getCharPos());
     case TOKEN_ENDBRACKET:
      return new pEndBracket(pt.getPosition(),pt.getLine(),pt.getCharPos());
     case TOKEN_LISTCONS:
      return new pListCons(pt.getPosition(),pt.getLine(),pt.getCharPos());
     case TOKEN_COMMA:
      return new pComma(pt.getPosition(),pt.getLine(),pt.getCharPos());
     case TOKEN_PERIOD:
      tokenizer.pushBackToken(pt);
      return parsePeriod();
     case TOKEN_SEMICOLON:
     {boolean 			predicate;
      pTSPreToken 		pt_la;
   
      pt_la = read();
      predicate = pt_la.getType() == TOKEN_STARTPAREN;
      tokenizer.pushBackToken(pt_la);

      return new pName(";",predicate,pt.getPosition(),pt.getLine(),pt.getCharPos());
     }
     case TOKEN_CUT:
     {boolean 			predicate;
      pTSPreToken 		pt_la;
   
      pt_la = read();
      predicate = pt_la.getType() == TOKEN_STARTPAREN;
      tokenizer.pushBackToken(pt_la);
	
      return new pName("!",predicate,pt.getPosition(),pt.getLine(),pt.getCharPos());
     }
     case TOKEN_LINECOMMENT:
      tokenizer.pushBackToken(pt);
      return parseLineComment();
     case TOKEN_FORWARDSLASH:
      tokenizer.pushBackToken(pt);
      return parseForwardSlash(); 
     // ignore token
     case TOKEN_WHITESPACE:
     case pTokenizerTable.TOKEN_EOL:
	 default:
    }
   }
  } while (true);
 };

 protected pToken 			parseBrace()
 {pTSPreToken 			pt1,pt2;
 
  pt1 = read();
  
  if (pt1.getType() != TOKEN_STARTBRACE)
   throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                     pt1.getPosition(),pt1.getLine(),pt1.getCharPos());

  pt2 = read();

  if (pt2.getType() == TOKEN_ENDBRACE)
  {boolean 				predicate;
   pTSPreToken 			pt3;
   
   pt3 = read();
   predicate = pt3.getType() == TOKEN_STARTPAREN;
   tokenizer.pushBackToken(pt3);
   
   return new pName("{}",predicate,pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  }
   
  tokenizer.pushBackToken(pt2);
  return new pStartBrace(pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
 };

 protected pToken 			parsePeriod()
 {pTSPreToken 			pt1,pt2;
  boolean 				predicate;
   
  pt1 = read();
  
  if (pt1.getType() != TOKEN_PERIOD)
   throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                     pt1.getPosition(),pt1.getLine(),pt1.getCharPos());

  pt2 = read();

  if (pt2.getType() == TOKEN_NUMBERS)
  {
   tokenizer.pushBackToken(pt2);
   tokenizer.pushBackToken(pt1);
   return parseNumbers();
  }
  else if (pt2.getType() == TOKEN_PERIOD || pt2.getType() == TOKEN_SECONDARYWORDS)
  {
   tokenizer.pushBackToken(pt2);
   tokenizer.pushBackToken(pt1);
   return parseSecondaryWords();
  }

  predicate = pt2.getType() == TOKEN_STARTPAREN;
  tokenizer.pushBackToken(pt2);

  return new pName(".",predicate,pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
 };

 protected pToken 			parseLineComment()
 {pTSPreToken 			pt1,pt2;
 
  pt1 = read();
  
  if (pt1.getType() != TOKEN_LINECOMMENT)
   throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                     pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  
  tokenizer.useTokenizerTable(linecomment_table);
  
  do
  {
   pt2 = read();
  } while (pt2.getType() == TOKEN_WORDS);
  
  tokenizer.pushBackToken(pt2);
  tokenizer.popTokenizerTable();
  return getNextToken();
 };

 protected pToken 			parseComment()
 {pTSPreToken 			pt1,pt2;
  boolean 				found,laststar;
  
  pt1 = read();
  pt2 = read();
  
  if (pt1.getType() != TOKEN_FORWARDSLASH || pt2.getType() != TOKEN_STAR)
   throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                     pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  
  tokenizer.useTokenizerTable(comment_table);
  
  found = false;
  laststar = false;
  
  do
  {
   pt2 = read();
   found = pt2.getType() == TOKEN_FORWARDSLASH && laststar;
   laststar = pt2.getType() == TOKEN_STAR;
  } while (pt2.getType() != pPreTokenizeStream.TOKEN_EOF && !found);
  
  if (pt2.getType() == pPreTokenizeStream.TOKEN_EOF)
   throw new SyntaxErrorException("Premature end of file.  Comment without end at ",
                                   pt1.getPosition(),pt1.getLine(),pt1.getCharPos()); 
 
  tokenizer.popTokenizerTable();
  return getNextToken();
 };

 protected pToken 			parseString()
 {pTSPreToken 			pt1,pt2;
  StringBuffer 			sb;
  
  pt1 = read();
  
  if (pt1.getType() != TOKEN_SINGLEQUOTE)
   throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                     pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  
  tokenizer.useTokenizerTable(string_table);

  sb = new StringBuffer();  
  do
  {
   pt2 = read();
   if (pt2.getType() == TOKEN_WORDS)
    sb.append(pt2.getToken());
  } while (pt2.getType() == TOKEN_WORDS || pt2.getType() == TOKEN_WHITESPACE);
  
  if (pt2.getType() == pTokenizerTable.TOKEN_EOL)
   throw new SyntaxErrorException("Premature end of line.  String without end at ",
                                   pt1.getPosition(),pt1.getLine(),pt1.getCharPos()); 
 
  if (pt2.getType() == pPreTokenizeStream.TOKEN_EOF)
   throw new SyntaxErrorException("Premature end of file.  String without end at ",
                                   pt1.getPosition(),pt1.getLine(),pt1.getCharPos()); 
 
  tokenizer.popTokenizerTable();

  {pTSPreToken 			pt3;
   boolean 				predicate;
   
   pt3 = read();
   predicate = pt3.getType() == TOKEN_STARTPAREN;
   tokenizer.pushBackToken(pt3);

   return new pName(sb.toString(),predicate,pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  }
 };

 protected pToken 			parseArray()
 {pTSPreToken 			pt1,pt2;
  StringBuffer 			sb;
  
  pt1 = read();
  
  if (pt1.getType() != TOKEN_QUOTE)
   throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                     pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  
  tokenizer.useTokenizerTable(array_table);

  sb = new StringBuffer();  
  do
  {
   pt2 = read();
   if (pt2.getType() == TOKEN_WORDS)
    sb.append(pt2.getToken());
  } while (pt2.getType() == TOKEN_WORDS || pt2.getType() == TOKEN_WHITESPACE);
  
  if (pt2.getType() == pTokenizerTable.TOKEN_EOL)
   throw new SyntaxErrorException("Premature end of line.  Character array without end at ",
                                   pt1.getPosition(),pt1.getLine(),pt1.getCharPos()); 
 
  if (pt2.getType() == pPreTokenizeStream.TOKEN_EOF)
   throw new SyntaxErrorException("Premature end of file.  Character array without end at ",
                                   pt1.getPosition(),pt1.getLine(),pt1.getCharPos()); 
 
  tokenizer.popTokenizerTable();
  return new pArray(sb.toString(),pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
 };

 protected pToken 			parseWords()
 {pTSPreToken 			pt1,pt2;
  StringBuffer 			sb;
  boolean 				predicate;
  
  pt1 = read();
  
  if (pt1.getType() != TOKEN_WORDS)
   throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                     pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  
  sb = new StringBuffer();  
  sb.append(pt1.getToken());
  do
  {
   pt2 = read();
   if (pt2.getType() == TOKEN_WORDS || pt2.getType() == TOKEN_NUMBERS)
    sb.append(pt2.getToken());
  } while (pt2.getType() == TOKEN_WORDS || pt2.getType() == TOKEN_NUMBERS);
  
  predicate = pt2.getType() == TOKEN_STARTPAREN;
  tokenizer.pushBackToken(pt2);
  
  if ((sb.charAt(0) >= 'A' && sb.charAt(0) <= 'Z') || sb.charAt(0) == '_')
   return new pVariable(sb.toString(),pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  else
   return new pName(sb.toString(),predicate,pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
 };

 protected pToken 			parseSecondaryWords()
 {pTSPreToken 			pt1,pt2;
  StringBuffer 			sb;
  int 					type;
  boolean 				found,predicate;
  
  pt1 = read();
  type = pt1.getType();
  
  if (type != TOKEN_SECONDARYWORDS && type != TOKEN_PERIOD && 
       type != TOKEN_FORWARDSLASH && type != TOKEN_STAR)
   throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                     pt1.getPosition(),pt1.getLine(),pt1.getCharPos());

  sb = new StringBuffer();  
  sb.append(pt1.getToken());
  
  // test for signed number
  if (pt1.getToken().equals("-") || pt1.getToken().equals("+"))
  {
   pt2 = read();
   type = pt2.getType();
   tokenizer.pushBackToken(pt2);
 
   if (type == TOKEN_NUMBERS)
   {pToken 	unum;
    pToken 	sgn;
    
    sgn = new pName(sb.toString(),false,pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
    unum = parseNumbers();
    return new pUnaryNumber(sgn,unum);
   }
  }
  
  do
  {
   found = false;
   pt2 = read();
   type = pt2.getType();
   if (type == TOKEN_SECONDARYWORDS || type == TOKEN_PERIOD || 
        type == TOKEN_FORWARDSLASH || type == TOKEN_STAR)
   {
    sb.append(pt2.getToken());
    found = true;
   }
  } while (found);
  
  predicate = pt2.getType() == TOKEN_STARTPAREN;
  tokenizer.pushBackToken(pt2);

  return new pName(sb.toString(),predicate,pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
 };

 protected pToken 			parseNumbers()
 {pTSPreToken 			pt1,pt2;
  StringBuffer 			sb;
  
  pt1 = read();
  
  if (pt1.getType() != TOKEN_NUMBERS && pt1.getType() != TOKEN_PERIOD)
   throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                     pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  
  sb = new StringBuffer();  
  sb.append(pt1.getToken());
  
  if (pt1.getType() == TOKEN_PERIOD) // case: .### or .###e[+,-]###
  {pTSPreToken 		pt3;
  
   pt2 = read();
   if (pt2.getType() != TOKEN_NUMBERS)
    throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                      pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  
   sb.append(pt2.getToken());
  
   pt3 = read();
   if (pt3.getType() == TOKEN_WORDS && pt3.getToken().equalsIgnoreCase("e"))
   {pTSPreToken		pt4;
   
    pt4 = read();
    if (pt4.getType() == TOKEN_SECONDARYWORDS && 
        (pt4.getToken().equals("-") || pt4.getToken().equals("+")))
    {pTSPreToken 	pt5;
    
     pt5 = read();
     if (pt5.getType() != TOKEN_NUMBERS)
      throw new SyntaxErrorException("Expected number after sign at ",
                                      pt4.getPosition(),pt4.getLine(),pt4.getCharPos());
     
     sb.append(pt3.getToken());
     sb.append(pt4.getToken());
     sb.append(pt5.getToken());
    }
	else if (pt4.getType() == TOKEN_NUMBERS)
    {
	 sb.append(pt3.getToken());
     sb.append(pt4.getToken());
    }
    else
     throw new SyntaxErrorException("Expected '+','-', or number after 'e' at ",
                                      pt3.getPosition(),pt3.getLine(),pt3.getCharPos());
   }
   else
    tokenizer.pushBackToken(pt3);
      
   return new pReal(sb.toString(),pt1.getPosition(),pt1.getLine(),pt1.getCharPos());  
  }
  else // case: ### or ###e[+,-]### or ###.### or ###.###e[+,-]### or ###'nnn
  {
   pt2 = read();
   if (pt2.getType() == TOKEN_PERIOD) // case: ###.### or ###.###e[+,-]### 
   {pTSPreToken 	pt3;
   
    pt3 = read();
    if (pt3.getType() == TOKEN_NUMBERS) // case: ###.### or ###.###e[+,-]### 
    {pTSPreToken 	pt4;
    
     sb.append(pt2.getToken());
     sb.append(pt3.getToken());
     
     pt4 = read();
     if (pt4.getType() == TOKEN_WORDS && pt4.getToken().equalsIgnoreCase("e"))
     {pTSPreToken		pt5;
   
      pt5 = read();
      if (pt5.getType() == TOKEN_SECONDARYWORDS && 
           (pt5.getToken().equals("-") || pt5.getToken().equals("+")))
      {pTSPreToken 	pt6;
    
       pt6 = read();
       if (pt6.getType() != TOKEN_NUMBERS)
        throw new SyntaxErrorException("Expected number after sign at ",
                                        pt5.getPosition(),pt5.getLine(),pt5.getCharPos());
     
       sb.append(pt4.getToken());
       sb.append(pt5.getToken());
       sb.append(pt6.getToken());
      }
	  else if (pt5.getType() == TOKEN_NUMBERS)
      {
	   sb.append(pt4.getToken());
       sb.append(pt5.getToken());
      }
      else
       throw new SyntaxErrorException("Expected '+', '-', or number after 'e' at ",
                                        pt4.getPosition(),pt4.getLine(),pt4.getCharPos());
     }
     else
      tokenizer.pushBackToken(pt4);
     
     return new pReal(sb.toString(),pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
    }
    else 
    {
     tokenizer.pushBackToken(pt3);
     tokenizer.pushBackToken(pt2);
     return new pInteger(sb.toString(),pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
    } 
   }
   else if (pt2.getType() == TOKEN_SINGLEQUOTE) // case: ###'nnn
   {String 			base = sb.toString();
    int 			basenum;
    
    try
    {
     basenum = Integer.valueOf(base).intValue();
    }
    catch (NumberFormatException e)
    {
     throw new TokenizeStreamException("Error in tokenizer.  Expected integer at ",
                                         pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
    }
    
    if (basenum == 0)
    {
     tokenizer.useTokenizerTable(singles_table);

     pt2 = read();
 
     tokenizer.popTokenizerTable();
     return new pInteger(base,pt2.getToken(),pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
    }
    else if (basenum >= 2 && basenum <= 36)
    {
     tokenizer.useTokenizerTable(basenumber_table);
        
     pt2 = read();
     
     tokenizer.popTokenizerTable();
     return new pInteger(base,pt2.getToken(),pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
    }
    else
     throw new SyntaxErrorException("Number base must be 0 or 2..36 at",
                                         pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
   }
   else if (pt2.getType() == TOKEN_WORDS && pt2.getToken().equalsIgnoreCase("e"))
   {pTSPreToken 	pt3;  // case: ###e[+,-]###

    pt3 = read();
    if (pt3.getType() == TOKEN_SECONDARYWORDS && 
           (pt3.getToken().equals("-") || pt3.getToken().equals("+")))
    {pTSPreToken 	pt4;
    
     pt4 = read();
     if (pt4.getType() != TOKEN_NUMBERS)
      throw new SyntaxErrorException("Expected number after sign at ",
                                        pt4.getPosition(),pt4.getLine(),pt4.getCharPos());
     
     sb.append(pt2.getToken());
     sb.append(pt3.getToken());
     sb.append(pt4.getToken());
    }
	else if (pt3.getType() == TOKEN_NUMBERS)
    {
	 sb.append(pt2.getToken());
     sb.append(pt3.getToken());
    }
    else
     throw new SyntaxErrorException("Expected '+', '-', or number after 'e' at ",
                                        pt2.getPosition(),pt2.getLine(),pt2.getCharPos());
     
    return new pReal(sb.toString(),pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
   }
   else
   {// case: ###
    tokenizer.pushBackToken(pt2);
    return new pInteger(sb.toString(),pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
   }
  }
 };

 protected pToken 			parseForwardSlash()
 {pTSPreToken 			pt1,pt2;
 
  pt1 = read();
  
  if (pt1.getType() != TOKEN_FORWARDSLASH)
   throw new TokenizeStreamException("Error in Tokenizer.  Unexpected token at ",
                                     pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  pt2 = read();
  
  tokenizer.pushBackToken(pt2);
  tokenizer.pushBackToken(pt1);
    
  if (pt2.getType() == TOKEN_STAR)
   return parseComment();     

  return parseSecondaryWords();
 };


 protected pTSPreToken 		peekNextToken()
 {pTSPreToken 		pt;
 
  pt = read();
  tokenizer.pushBackToken(pt);
  
  return pt;
 };

 protected boolean 		peekNextTokenType(int type)
 {pTSPreToken 		pt;
 
  pt = read();
  tokenizer.pushBackToken(pt);
  
  return pt.getType() == type;
 };

 protected pTSPreToken 		read()
 {pPreToken 	pt;
  pTSPreToken 	tspt;
  
  pt = tokenizer.getNextToken();
  
  if (!(pt instanceof pTSPreToken))
  {
   tspt = new pTSPreToken(pt.getToken(),pt.getType(),pt.getPosition(),currentLine,currentChar);

   if (pt.getType() == pTokenizerTable.TOKEN_EOL)
   {
    currentLine += countLines(pt.getToken());
    currentChar = 0; 
   }
   else
   {
    currentChar += pt.getToken().length();
   }
  }
  else
   tspt = (pTSPreToken) pt;
   
  return tspt;
 }; 
 
 protected int 		countLines(String s)
 {int 		count;
  int 		pos,len,c;
  boolean 	lastlf = false;
   
  for(pos = 0, count = 0, len = s.length(); pos < len; pos++)
  {
   c = s.charAt(pos);
    
   if (c == 13)
   {
    lastlf = true;
    count++;
   }
   else if (c == 10 && lastlf)
    lastlf = false;
   else
   {
    lastlf = false;
    count++;
   } 
  }
  return count;
 };
};

