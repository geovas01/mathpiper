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
//	ListPair
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;

public class jListPair extends jList
{
 // head or tail should never be null, except during the list construction phase.
 // complete lists should terminate with a nulllist, or other other term on tail.
 protected jTerm	head,tail;
 
 public jListPair(jTerm h,jTerm t)
 {
  head = h;
  tail = t;
  type = TYPE_LIST;
 };

 public final void 		setHead(jTerm h)
 {
  head = h;
 };
 
 public final void 		setTail(jTerm t)
 {
  tail = t;
 };
 
 public final jTerm 		getHead()
 {
  return head;
 };
 
 public final jTerm 		getTail()
 {
  return tail;
 };
 
 public final void 		setNode(jTerm h,jTerm t)
 {
  head = h;
  tail = t;
 };

 public String 		getName()
 {
  return LIST_PAIR;
 };
 
 public jTerm 		getValue()
 {
  if (tail.type == TYPE_NULLLIST)
   return head.getValue();
    
  return this;
 };

 protected int 		compare(jTerm term,boolean first_call,boolean var_equal)
 {jTerm 		t = term.getTerm();
 
  if ((t instanceof jVariable) || (t instanceof jReal) || (t instanceof jInteger))
   return GREATER_THAN;
  
  if (t instanceof iPredicate)
  {iPredicate 	ip = (iPredicate) t;
   int 			compare_val;
   int 			arity_b;
   
   arity_b = ip.getArity();
   
   if (2 < arity_b)
    return LESS_THAN;
   else if (2 > arity_b)
    return GREATER_THAN;
   
   compare_val = getName().compareTo(ip.getName());
   
   if (compare_val < 0)
    return LESS_THAN;
   if (compare_val > 0)
    return GREATER_THAN;

   // we can assume that predicate has arity/2 and same name
   if (t instanceof jPredicate)
   {jPredicate 		jp = (jPredicate) t;
    jCompoundTerm 	ct = jp.getArguments();
    int 			result;
    
    result = head.compare(ct.elementAt(0),true,var_equal);
    if (result != EQUAL)
     return result;
    
    return tail.compare(ct.elementAt(1),true,var_equal);
   }
   else if (t instanceof jBinaryBuiltinPredicate)
   {jBinaryBuiltinPredicate 	bip = (jBinaryBuiltinPredicate) t;
    int 						result;
    
    result = head.compare(bip.getLHS(),true,var_equal);
    if (result != EQUAL)
     return result;
    
    return tail.compare(bip.getRHS(),true,var_equal);
   }
  }
  
  // this is a special case, because a true list may be very long (the other binary predicates
  // have a length limit based on the Java stack size ~3000 calls/terms), so compare
  // without recursive calls;
  if (t instanceof jListPair)
  {jTerm		tt = this;
  
   while (tt.type == type && t.type == type)
   {jListPair 	l = (jListPair) t;  // assume type member value is correct
    jListPair 	ll = (jListPair) tt;  // assume type member value is correct
    int 		result;
   
    result = ll.head.compare(l.head,true,var_equal);
    if (result != EQUAL)
     return result;
	
	t = l.tail.getTerm();
	tt = ll.tail.getTerm();
   }
    
   return tt.compare(t,true,var_equal);
  }
  
  if (t instanceof jNullList || t instanceof jCommand)
   return GREATER_THAN;

  if (t instanceof jConjunctTerm)
  {jConjunctTerm 	ct = (jConjunctTerm) t;
   int 				compare_val,result;
  
   compare_val = getName().compareTo(ct.getName());
   
   if (compare_val < 0)
    return LESS_THAN;
   if (compare_val > 0)
    return GREATER_THAN;

   result = head.compare(ct.getLHS(),true,var_equal);
   if (result != EQUAL)
    return result;
    
   return tail.compare(ct.getRHS(),true,var_equal);
  }
   
  return (first_call ? -t.compare(this,false,var_equal) : EQUAL);
 };
 
 public void 		registerUnboundVariables(jUnifiedVector v)
 {jTerm			t = this;
 
  while (t.type == type)
  {jListPair		lp = (jListPair) t; // assume type member value is correct

   lp.head.registerUnboundVariables(v);
   t = lp.tail.getTerm();
  }
  
  t.registerUnboundVariables(v);
 };

 public boolean 	equivalence(jTerm term,jEquivalenceMapping v)
 {jTerm			t = term.getTerm();
  jListPair		lp = this;
  
  while (t != null)
  {
   // only unify with other list pairs (i.e., of this type).
   if (type != t.type)
    return false;

   // altough we cannot be certain that t is a jListPair, if it is not then type was wrong
   // so this warrents a failing exception.
   {jListPair 		lt = (jListPair) t;

    if (!lp.head.equivalence(lt.head,v))
     return false;
   
    {jTerm  t2 = lp.tail.getTerm();
	
	 if (t2.type != type)
	  return t2.equivalence(lt.tail,v);
	 else
	  lp = (jListPair) t2; // assume type member value is correct from above
    }
	
	t = lt.tail;
   }
  } 
  return false; // shouldn't get here, but if we do, it isn't equivalent
 };

 public boolean 	unify(jTerm term,jUnifiedVector v)
 {jTerm			t = term.getTerm();
  jListPair		lp = this;
  
  while (t != null)
  {
   // if term is variable we let it handle the unification
   if (t.type == TYPE_VARIABLE)
    return t.unify(lp,v);
  
   // only unify with other list pairs (i.e., of this type).
   if (type != t.type)
    return false;

   // altough we cannot be certain that t is a jListPair, if it is not then type was wrong
   // so this warrents a failing exception.
   {jListPair 		lt = (jListPair) t;

    if (!lp.head.unify(lt.head,v))
     return false;
   
    {jTerm  t2 = lp.tail.getTerm();
	
	 if (t2.type != type)
	  return t2.unify(lt.tail,v);
	 else
	  lp = (jListPair) t2; // assume type member value is correct from above
    }
	
	t = lt.tail;
   }
  } 
  return false; // shouldn't get here, but if we do, it didn't unify
 };
  
 public void 		registerVariables(jVariableVector v)
 {jTerm			t = this;
 
  while (t.type == type)
  {jListPair		lp = (jListPair) t; // assume type member value is correct

   lp.head.registerVariables(v);
   t = lp.tail; // no getTerm() since variables should be unbound, and need to add themselves
  }
  
  t.registerVariables(v);
 };
 
 public void 		enumerateVariables(jVariableVector v,boolean all)
 {jTerm			t = this;
 
  while (t.type == type)
  {jListPair		lp = (jListPair) t; // assume type member value is correct

   lp.head.enumerateVariables(v,all);
   t = lp.tail.getTerm();
  }
  
  t.enumerateVariables(v,all);
 };

 public jTerm 		duplicate(jVariable[] vars)
 {Stack			list = new Stack();
  jTerm			t = this;
  jTerm			t2 = null;
  
  // build stack of list elements to facilitate building the new list from the end.
  while (t.type == type)
  {jListPair	lp = (jListPair) t;  // assume type member value is correct

   list.push(lp);
   t = lp.tail.getTerm();
  }

  t2 = t.duplicate(vars);
  
  // for each element of the list, create its duplicate (if neither the head nor tail is 
  // different, then do not duplicate).
  while (!list.empty())
  {jListPair	lp = (jListPair) list.pop();
   jTerm		h = lp.head.getTerm();
   jTerm		h2 = h.duplicate(vars);

   if (h == h2 && t == t2)
    t2 = lp;
   else
    t2 = new jListPair(h2,t2);

   t = lp;
  }

  return t2;
 };

 public jTerm 		copy(jVariableRegistry vars)
 {Stack			list = new Stack();
  jTerm			t = this;
  jTerm			t2 = null;
  
  // build stack of list elements to facilitate building the new list from the end.
  while (t.type == type)
  {jListPair	lp = (jListPair) t;  // assume type member value is correct

   list.push(lp);
   t = lp.tail.getTerm();
  }

  t2 = t.copy(vars);
  
  // for each element of the list, create its duplicate.
  while (!list.empty())
  {jListPair	lp = (jListPair) list.pop();
   jTerm		h = lp.head.getTerm();
   jTerm		h2 = h.copy(vars);

   t2 = new jListPair(h2,t2);
  }

  return t2;
 };

 public void 		consult(jKnowledgeBase kb)
 {jTerm			t = this;
 
  while (t.type == type)
  {jListPair		lp = (jListPair) t; // assume type member value is correct

   lp.head.consult(kb);
   t = lp.tail.getTerm();
  }
  
  t.consult(kb);
 };
 
 public void 		consultReset()
 {jTerm			t = this;
 
  while (t.type == type)
  {jListPair		lp = (jListPair) t; // assume type member value is correct

   lp.head.consultReset();
   t = lp.tail.getTerm();
  }
  
  t.consultReset();
 };
 
 public boolean 	isConsultNeeded()
 {
  return true;
 };

 public String 		toString(boolean usename)
 {StringBuffer		sb = new StringBuffer();
  boolean			first = true;
  jTerm				t = this;
  
  sb.append("[");
  
  while (t.type == type)
  {jListPair	lp = (jListPair) t;  // assume type member value is correct
  
   if (!first)
    sb.append(",");
   else
    first = false;
		
   sb.append(lp.head.getTerm().toString(usename));

   t = lp.tail.getTerm();
  }
     
  if (t instanceof jNullList)
   sb.append("]");
  else if (t instanceof jVariable)
   sb.append("|"+t.toString(usename)+"]");
  else 
   sb.append(","+t.toString(usename)+"]");

  return sb.toString();
 }; 
 
 public Enumeration		elements(iTermToObject conv)
 {
  return new EnumerateListPair(this,conv);
 };
 
 /**
  * Returns a <code>jList</code> list representing the given enumeration of objects.
  *
  * @param e	The enumeration of elements to construct the list from.
  * @param conv		An object to term converter to construct terms in the list from
  *					the objects in the enumeration.
  *
  * @return		The new list where each element in the enumeration is converted into 
  *				an element in the list.
  */
 public static jList		createListFromEnumeration(Enumeration e,iObjectToTerm conv)
 {jList			head = null;
  jListPair		next = null;
  
  while (e.hasMoreElements())
  {jTerm		t = conv.createTermFromObject(e.nextElement());
   jListPair	lp = new jListPair(t,jNullList.NULL_LIST);
   
   if (head == null)
    head = lp;
   if (next != null)
    next.setTail(lp);
   
   next = lp;
  }

  if (next == null)
   return jNullList.NULL_LIST;
  else
   return head;
 };

 protected class EnumerateListPair implements Enumeration
 {
  protected jListPair		next;
  protected iTermToObject	convert;
   
  public EnumerateListPair(jListPair head,iTermToObject conv)
  {
   next = head;
   convert = conv;
  };
   
  public boolean		hasMoreElements()
  {
   return (next != null);
  };
   
  public Object			nextElement()
  {jTerm   head = next.getHead().getTerm();
   jTerm   tail = next.getTail().getTerm();
	
   if (tail instanceof jListPair)
    next = (jListPair) tail;
   else
	next = null;
	 
   return convert.createObjectFromTerm(head);
  };   
 };
};
