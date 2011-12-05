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
//	pPreParseStream
//#########################################################################

package ubc.cs.JLog.Parser;

import java.io.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Terms.Entries.*;

/**
* The pre parsing phase that converts a stream of tokens into a tree of packets. 
*  
* @author       Glendon Holst
* @version      %I%, %G%
*/
class pPreParseStream
{
 protected pTokenizeStream 		tokenizer;
 protected pPredicateRegistry 	predicates;
 protected pOperatorRegistry 	operators;
 
 public pPreParseStream(String s,pPredicateRegistry pr,pOperatorRegistry or)
 { 
  tokenizer = new pTokenizeStream(s);
  predicates = pr;
  operators = or;
 };
 
 public pPreParseStream(Reader r,pPredicateRegistry pr,pOperatorRegistry or)
 {
  tokenizer = new pTokenizeStream(r);
  predicates = pr;
  operators = or;
 };
 
 /**
  * Performs the next parsing operation, returning one packet at a time. Returns the next packet in the stream.
  *
  * @return 		The next <code>pPacket</code> from the stream, or null if there is nothing left to parse.
  */
 public pPacket 			parse()
 {pToken 		pt;
  pPacket 		pak;
  
  pak = parseTerm(false);
  
  pt = tokenizer.getNextToken();
  if ((pt instanceof pEOF && pak == null) || 
       (pt instanceof pName && ((pName) pt).getToken().equals(".")))
   return pak;
  else
   throw new SyntaxErrorException("Expected '.' at ",
 	                                 pt.getPosition(),pt.getLine(),pt.getCharPos());
 };
 
 /**
  * Internal method that parses the stream begining with a parenthesis, and returns the packet representing it.
  *
  * @return 		The next <code>pParens</code> parenthesis packet from the stream.
  */
 protected pParens 				parseParens()
 {pToken 		pt1,pt2;
  pPacket 		pak;
  
  pt1 = tokenizer.getNextToken();
  if (!(pt1 instanceof pStartParen))
   throw new SyntaxErrorException("Expected '(' at ",
 	                                 pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  
  pak = parseTerm(false);
  
  pt2 = tokenizer.getNextToken();
  if (pt2 instanceof pEndParen)
   return new pParens((pStartParen) pt1,pak);
  else
   throw new SyntaxErrorException("Expected ')' at ",
 	                                 pt2.getPosition(),pt2.getLine(),pt2.getCharPos());
 };
 
 /**
  * Internal method that parses the stream begining with a curley brace, and returns the packet representing it.
  *
  * @return 		The next <code>pPredicate</code> packet from the stream.
  */
 protected pPredicate 			parseBrace()
 {pToken 		pt1,pt2;
  pPacket 		pak;
  
  pt1 = tokenizer.getNextToken();
  if (!(pt1 instanceof pStartBrace))
   throw new SyntaxErrorException("Expected '{' at ",
 	                                 pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  
  pak = parseTerm(false);

  pt2 = tokenizer.getNextToken();
  if (pt2 instanceof pEndBrace)
   return new pPredicate(predicates,"{}",pt1,new pParens((pStartBrace) pt1,pak));
  else
   throw new SyntaxErrorException("Expected '}' at ",
 	                                 pt2.getPosition(),pt2.getLine(),pt2.getCharPos());
 };
 
 /**
  * Internal method that parses the stream begining with a bracket, and returns the packet representing it.
  *
  * @return 		The next <code>pList</code> list packet from the stream.
  */
 protected pList 				parseList()
 {pToken 		pt1,pt2;
  pPacket 		pak1,pak2 = null;
  
  pt1 = tokenizer.getNextToken();
  if (!(pt1 instanceof pStartBracket))
   throw new SyntaxErrorException("Expected '[' at ",
 	                                 pt1.getPosition(),pt1.getLine(),pt1.getCharPos());
  
  pak1 = parseTerm(false);
  
  pt2 = tokenizer.getNextToken();
  
  if (pt2 instanceof pListCons)
  {
   if (pak1 == null)
    throw new SyntaxErrorException("Expected term after '[' at ",
 	                                 pt1.getPosition(),pt1.getLine(),pt1.getCharPos());

   pak2 = parseTerm(true);
   
   if (pak2 == null)
    throw new SyntaxErrorException("Expected term after '|' at ",
 	                                 pt2.getPosition(),pt2.getLine(),pt2.getCharPos());
   
   pt2 = tokenizer.getNextToken();
  }
  
  if (pt2 instanceof pEndBracket)
   return new pList(pt1,pak1,pak2);

  throw new SyntaxErrorException("Expected ']' at ",
 	                                 pt2.getPosition(),pt2.getLine(),pt2.getCharPos());
 };
 
 /**
  * Internal method that parses the stream and returns the packet representing the next term.
  *
  * @param single 	Affects the treatment of commas.  If true, then a comma indicates the end 
  * 			of an expression.
  *
  * @return 		The next <code>pPacket</code> from the stream.
  */
 protected pPacket 				parseTerm(boolean single)
 {Stack 		paks = new Stack();
  pToken 		pt;
  pPacket 		pp;
  boolean 		need_lhsop = false;
  
  do
  {
   pt = tokenizer.getNextToken();
  
   if (pt instanceof pComma && single)
   {
    tokenizer.pushBackToken(pt);
    return finalizePacketStack(paks);
   }
  
   if (pt instanceof pName && !need_lhsop && ((pName) pt).isPotentialPredicate())
    pp = new pPredicate(predicates,pt,parseParens()); 
   else if (pt instanceof pStartBrace && !need_lhsop)
   {
    tokenizer.pushBackToken(pt);
    pp = parseBrace();
   }
   else if (pt instanceof pStartBracket && !need_lhsop)
   {
    tokenizer.pushBackToken(pt);
    pp = parseList();
   }
   else if (pt instanceof pStartParen && !need_lhsop)
   {
    tokenizer.pushBackToken(pt);
    pp = parseParens();
   }
   else if (pt instanceof pArray && !need_lhsop)
    pp = new pArrayList((pArray) pt);
   else if (pt instanceof pVariable && !need_lhsop)
    pp = new pVar((pVariable) pt);
   else if ((pt instanceof pReal || pt instanceof pInteger || 
                                    pt instanceof pUnaryNumber) && !need_lhsop)
    pp = new pNumber(pt);
   else if (pt instanceof pUnaryNumber)
   {
    tokenizer.pushBackToken(((pUnaryNumber) pt).getValue());
    tokenizer.pushBackToken(((pUnaryNumber) pt).getSign());
    continue;
   } 
   else
   {
    pp = packetizeToken(pt,need_lhsop);
    if (pp == null || (need_lhsop && !(pp instanceof pOperator)))
    {
     tokenizer.pushBackToken(pt);
     return finalizePacketStack(paks);
    }
   } 

   need_lhsop = !(pp instanceof pOperator && ((pOperator) pp).hasRHS());
   updatePacketStack(paks,pp);
  } while (true);      
 };
 
 /**
  * Internal method which updates the given stack of packets used to store operands for operators.
  * Because of the previous tests, we can make a number of assumptions when reordering
  * the stack.  Assumptions include: only the first and only element on the stack can ever
  * be a non-operator, no incompletely bound operators are on the stack if the current 
  * pp operator requires a lhs, if all operators on the stack are complete then pp
  * is a lhs operator, only the top operator on the stack will be incomplete, 
  * if the top operator is incomplete, then the next package will not be an operator 
  * requiring a lhs. the stack has the property that each operator on the stack is
  * the rhs for the previous operator, the priority goes in increasing order from top to
  * bottom of stack, and operators that are non associative to the right do not have any 
  * operators higher on the stack with the same priority.
  *
  * @param paks 	The stack of packets.
  * @param pp 		The packet to add to the stack (may be an operator that takes other 
  * 			elements of the stack as operands).
  */
 protected void 		updatePacketStack(Stack paks,pPacket pp)	
 {
  if (paks.empty())
   paks.push(pp);
  else if (pp instanceof pOperator && ((pOperator) pp).hasLHS())
  {pOperator 	ppo = (pOperator) pp;
   pPacket 		spp;

   spp = (pPacket) paks.pop();
   
   if (spp instanceof pOperator)
   {pOperator 	sppo = (pOperator) spp;
    
    do
    {
     if (ppo.getPriority() < sppo.getPriority() ||
         (ppo.getPriority() == sppo.getPriority() && !sppo.isNonAssociativeRight()))
     {
      ppo.setLHS(sppo.getRHS());
      sppo.setRHS(ppo);
      paks.push(sppo);
      paks.push(ppo);
      break;
     }
     else if ((ppo.getPriority() > sppo.getPriority() && paks.empty()) ||
               (ppo.getPriority() == sppo.getPriority() && !ppo.isNonAssociativeLeft()))
     {
      ppo.setLHS(sppo);
      if (!paks.empty())
       ((pOperator) paks.peek()).setRHS(ppo);
      paks.push(ppo);
      break;
     }
     else 
     {
      if (!paks.empty())
       sppo = (pOperator) paks.pop();
      else
      {pToken 		opt = pp.getToken();
     
       throw new SyntaxErrorException("Parentheses required. Associativity rules do not permit use of operator at ",
                                         opt.getPosition(),opt.getLine(),opt.getCharPos());
      }
     }
    } while(true);    
   }
   else
   {// this happens when the first and only element on the stack is not an operator
    ppo.setLHS(spp);
    paks.push(ppo);
   }
  }
  else // is not operator or not lhs operator
  {pOperator 	sppo = (pOperator) paks.peek(); 

   if (pp instanceof pOperator)
   {pOperator 	ppo = (pOperator) pp;
    pToken 		opt = pp.getToken();
         
    if (ppo.getPriority() > sppo.getPriority() ||
        (ppo.getPriority() == sppo.getPriority() && sppo.isNonAssociativeRight()))
     throw new SyntaxErrorException("Operator with lower priority number or parenthesis expected for operator at ",
 	                                         opt.getPosition(),opt.getLine(),opt.getCharPos());
 	                                         
    paks.push(ppo);
   }
   sppo.setRHS(pp);
  }  
 };

 /**
  * Internal method which produces a final packet representing the given stack of packets.
  *
  * @param paks 	The stack of packets.
  *
  * @return 		The packet representing the given stack.
  */
 protected pPacket 		finalizePacketStack(Stack paks)
 {pPacket 		last;
 
  if (paks.empty())
   return null;
  
  last = (pPacket) paks.peek();
  if (last instanceof pOperator)
   ((pOperator) last).verifyCompletion();
  
  return (pPacket) paks.firstElement();
 };

 /**
  * Internal method which produces a final packet representing the given stack of packets.
  * Should only return operators or predicates (or atoms).    
  * Should not touch the tokenizer stream, and should not call any other functions that might. 
  *
  * @param paks 	The stack of packets.
  * @param need_lhsop 	If true then return an operator which requires a lhs or return null if a lhs operator 
  * 			was required. If false, return a packet which doesn't required a lhs or return null.
  *
  * @return 		The packet representing the given token.
  */
 protected pPacket 		packetizeToken(pToken pt,boolean need_lhsop)
 {pOperatorEntry 		oe;
 
  if (!(pt instanceof pName || pt instanceof pComma))
   return null;
  
  if ((oe = operators.getOperator(pt.getToken(),need_lhsop)) != null)
  {
   if (pt instanceof pComma && oe instanceof pConsOperatorEntry)
    return new pCons((pConsOperatorEntry) oe,pt);
    
   if (oe instanceof pIfOperatorEntry)
    return new pIf((pIfOperatorEntry) oe,pt);
    
   if (oe instanceof pCommandOperatorEntry)
    return new pCommand((pCommandOperatorEntry) oe,pt);
    
   if (oe instanceof pDCGOperatorEntry)
    return new pDCG((pDCGOperatorEntry) oe,pt);
    
   return new pOperator(oe,pt);
  }
  
  if (need_lhsop)
   return null;
    
  return new pPredicate(predicates,pt);
 };
};

