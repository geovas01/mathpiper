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
//	ConjunctTerm
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;

public abstract class jConjunctTerm extends jTerm
{
 protected jTerm 		lhs = null,rhs = null;
 
 protected jConjunctTerm(jTerm l,jTerm r)
 {
  lhs = l;
  rhs = r;
 };
 
 public jTerm 		getLHS()
 {
  return lhs;
 };
 
 public jTerm 		getRHS()
 {
  return rhs;
 };

 public void 		setLHS(jTerm l)
 {
  lhs = l;
 };
 
 public void 		setRHS(jTerm r)
 {
  rhs = r;
 };

 protected int 		compare(jTerm term,boolean first_call,boolean var_equal)
 {jTerm 	t = term.getTerm();
 
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

   return EQUAL;
  }
 
  if (t instanceof jConjunctTerm)
  {jTerm 	lt = ((jConjunctTerm) t).lhs;
   jTerm 	rt = ((jConjunctTerm) t).rhs;
   int 		compare_val;
   int  	result;
   
   compare_val = getName().compareTo(t.getName());
   
   if (compare_val < 0)
    return LESS_THAN;
   if (compare_val > 0)
    return GREATER_THAN;
  
   result = lhs.compare(lt,true,var_equal);
   if (result != EQUAL)
    return result;
   return rhs.compare(rt,true,var_equal);
  }
  
  return (first_call ? -t.compare(this,false,var_equal) : EQUAL);
 };

 public boolean 	requiresCompleteVariableState()
 {
  return (lhs.requiresCompleteVariableState() || rhs.requiresCompleteVariableState());
 };
 
 public void 		registerUnboundVariables(jUnifiedVector v)
 {
  lhs.registerUnboundVariables(v);
  rhs.registerUnboundVariables(v);
 };

 public boolean 	equivalence(jTerm term,jEquivalenceMapping v)
 {jTerm t = term.getTerm();
 
  // only unify with other cons terms of same type
  if (type != t.type)
   return false;
 
  // altough we cannot be certain that term is a jConjunctTerm, if it is not then type 
  // was wrong so this warrents a failing exception.
  {jConjunctTerm 		cterm;
   int 					sz;
   
   cterm = (jConjunctTerm) t;
   
  // equiv each element of cons term, exit on first failure.

   if (!lhs.equivalence(cterm.lhs,v))
    return false;
   if (!rhs.equivalence(cterm.rhs,v))
    return false;
  }
  return true;
 };

 public boolean 	unify(jTerm term,jUnifiedVector v)
 {
  // if term is variable we let it handle the unification
  if (term.type == TYPE_VARIABLE)
   return term.unify(this,v);

  // only unify with other cons terms of same type
  if (type != term.type)
   return false;
 
  // altough we cannot be certain that term is a jConjunctTerm, if it is not then type 
  // was wrong so this warrents a failing exception.
  {jConjunctTerm 		cterm;
   int 					sz;
   
   cterm = (jConjunctTerm) term;
   
  // unify each element of cons term, exit on first unification failure.

   if (!lhs.unify(cterm.lhs,v))
    return false;
   if (!rhs.unify(cterm.rhs,v))
    return false;
  }
  return true;
 };

 public void 		registerVariables(jVariableVector v)
 {
  lhs.registerVariables(v);
  rhs.registerVariables(v);
 };
 
 public void 		enumerateVariables(jVariableVector v,boolean all)
 {
  lhs.enumerateVariables(v,all);
  rhs.enumerateVariables(v,all);
 };

 public jTerm 		duplicate(jVariable[] vars)
 {  
  return duplicate(lhs.duplicate(vars),rhs.duplicate(vars));
 };

 public jTerm 		copy(jVariableRegistry vars)
 {
  return duplicate(lhs.copy(vars),rhs.copy(vars));
 };
 
 abstract protected jConjunctTerm 	duplicate(jTerm l,jTerm r);
 
 public void 		consult(jKnowledgeBase kb)
 {
  lhs.consult(kb);
  rhs.consult(kb);
 };
 
 public void 		consultReset()
 {
  lhs.consultReset();
  rhs.consultReset();
 };
};
