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
//	Retract
//#########################################################################
 
package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.Goals.*;

public class jRetract extends jUnaryBuiltinPredicate
{
 public jRetract(jTerm t)
 {
  super(t,TYPE_BUILTINPREDICATE);
 };
  
 public String 		getName()
 {
  return "retract";
 };

 public boolean 	prove(jRetractGoal rg,jKnowledgeBase kb)
 {jTerm 	t;
 
  t = rg.term.getTerm();
  
  if (t instanceof jIf)
  {jIf 					jif = (jIf) t;
   jTerm 				lhs = jif.getLHS();
   jPredicate 			head = null;
   jPredicateTerms 		base = null;
         
   if (lhs instanceof jPredicate)
    head = (jPredicate) lhs;
   else
    throw new InvalidRetractException("Expected single predicate before if operator in retract.");

   try
   {
    base = new jPredicateTerms();
    base.makePredicateTerms(jif.getRHS());
   }
   catch (PredicateExpectedException e)
   {
    throw new InvalidRetractException("Expected predicates after if operator.");
   }
     
   return retractPredicate(head,base,kb,rg.unified);
  }
  else if (t instanceof jPredicate)
   return retractPredicate((jPredicate) t,null,kb,rg.unified);
  else if (t instanceof jAtom)
   return retractPredicate(new jPredicate((jAtom) t),null,kb,rg.unified);

  return false;
 };

 // b may be null. return true if rule retracted, false if cannot retract.
 protected boolean 		retractPredicate(jPredicate h,jPredicateTerms b,jKnowledgeBase kb,
 											jUnifiedVector unified)
 {jRuleDefinitions 		rds;
  jRule 				rule;
  
  rule = new jRule(h,(b == null ? new jPredicateTerms() : b));
  if ((rds = h.getCachedRuleDefinitions()) == null)
   rds = kb.getRuleDefinitionsMatch(rule);
  
  if (rds == null)
   return false;
  else if (rds instanceof jDynamicRuleDefinitions)
   return ((jDynamicRuleDefinitions) rds).retractUnifyRule(rule,unified);
  else
   throw new InvalidRetractException("Retracted term '"+h.getName()+"/"+
                                      String.valueOf(h.getArity())+"' must be dynamic.");
 };
 
 public void 		addGoals(jGoal g,jVariable[] vars,iGoalStack goals)
 {
  goals.push(new jRetractGoal(this,rhs.duplicate(vars)));
 };

 public void 		addGoals(jGoal g,iGoalStack goals)
 {
  goals.push(new jRetractGoal(this,rhs));
 };

 protected jUnaryBuiltinPredicate 		duplicate(jTerm r)
 {
  return new jRetract(r); 
 };
};

