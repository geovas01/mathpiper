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
//	Rule
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Terms.Goals.*;

/**
* This class represents a rule of the form Head :- Base, where Head is a predicate and 
* Base is a term of predicates.
*
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class jRule implements iNameArity, iConsultable
{
 protected jPredicate 			head;
 protected jPredicateTerms 		base;
 protected jVariableVector 		variables;
 
 protected boolean 				all_variables = false;
 
 public 	jRule(jPredicate h,jPredicateTerms b)
 {
  if (((head = h) == null) || ((base = b) == null))
   throw new NullPointerException();
   
  variables = null;
 };

 public String 		getName()
 {
  return head.getName();
 }; 
 
 public int 		getArity()
 {
  return head.getArity();
 }; 
 
 public jPredicate 		getHead()
 {
  return head;
 };
 
 public jPredicateTerms 	getBase()
 {
  return base;
 };
 
 public boolean 	unify(jPredicateGoal pg,iGoalStack goals)
 {jVariable		vars[] = variables.duplicateVariables();
 	
  // we assume that the predicate name and arity already unify
  
  pg.rule_goal = head.duplicateArguments(vars);
  
  if (pg.rule_goal.unify(pg.input_goal,pg.unified))
  {
   if (all_variables)
    pg.input_goal.registerUnboundVariables(pg.unified);
    
   pg.setEndGoal(goals.empty() ? null : goals.peek());
   base.addGoals(pg,vars,goals);
   
   return true;
  }
  else
  {
   // this is required since the owning ruledefinitions may try more rules
   // before returning control to the predicategoal which normally restores on failure.
   pg.unified.restoreVariables();
   pg.rule_goal = null;
   
   return false;
  }
 }; 		
 
 /**
  * Test if rule head will unify. Assumes predicate name and arity already match.
  *
  * @return  		The duplicated base <code>jTerm</code> if it head unifies, null
  *			otherwise.
  */
 public jTerm 		getUnifiedBase(jCompoundTerm input_goal,jUnifiedVector unified)
 {jVariable		vars[] = variables.duplicateVariables();
  jCompoundTerm 	rule_goal;
  
  // we assume that the predicate name and arity already unify
  
  rule_goal = head.duplicateArguments(vars);
  
  if (rule_goal.unify(input_goal,unified))
  {
   if (all_variables)
    input_goal.registerUnboundVariables(unified);
    
   return base.duplicate(vars);
  }
  else
  {
   // this is required since the owning ruledefinitions may try more rules
   // before returning control to the predicategoal which normally restores on failure.
   unified.restoreVariables();
   
   return null;
  }
 }; 		
 
 public void 		consult(jKnowledgeBase kb)
 {
  head.consult(kb);
  base.consult(kb);
  variables = new jVariableVector();
  
  head.registerVariables(variables);
  base.registerVariables(variables); 
 
  all_variables = base.requiresCompleteVariableState();
};

 public void 		consultReset()
 {
  base.consultReset();
  variables = null;
  all_variables = false;
 };
 
 public boolean 	isConsultNeeded()
 {
  return true;
 };

 public Enumeration 	enumBase()
 {
  return base.enumTerms();
 };
 
 public String			toString()
 {
  return getName() + "/" + Integer.toString(getArity());
 };
};
