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
//	DynamicRuleDefinitions
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Terms.Goals.*;

/**
 * This class represents an ordered collection of <code>jRule</code>s 
 * of the same name and arity. This is designed for dynamically defined
 * rules that may change during a proof.  There is a performance penalty
 * for using dynamic rules.
 *
 * @author       Glendon Holst
 * @version      %I%, %G%
 */
public class jDynamicRuleDefinitions extends jRuleDefinitions
{
 // builtin defaults to false.  A dynamic predicate is no longer a builtin.
 
/**
  * Construct dynamic rule based on name, arity, and vector of rules.  For 
  * internal duplications purposes.
  *
  * @param name  	The name for these rules.
  * @param arity  	The arity for these rules.
  * @param v  		The rules vector to duplicate.
  */
 protected jDynamicRuleDefinitions(String name,int arity,Vector v)
 {
  rule_name = name;
  rule_arity = arity;
  rules = (Vector) v.clone();
 };

 public jDynamicRuleDefinitions(String name,int arity)
 {
  super(name,arity);
 };

/**
  * Construct dynamic rule based on static rule definitions. Converts a 
  * <code>jRuleDefinition</code> to a dynamic rule definition.
  * The database should be consulted afterwards and before the next prove.  
  *
  * @param rd  		The <code>jRuleDefinitions</code> to duplicate. Argument 
  * 			should not be used again. to ensure against this, the 
  * 			dynamic rule takes total ownership of the vector 
  * 			(leaving <code>rd</code> empty).
  */
 public jDynamicRuleDefinitions(jRuleDefinitions rd)
 {
  super(rd.rule_name,rd.rule_arity);
  rules = rd.rules;
  rd.clearRules();
 };

 public jPredicateGoal 	createGoal(jCompoundTerm t)
 {
  return new jPredicateGoal(new jDynamicRuleDefinitions(rule_name,rule_arity,rules),t);
 };

 public jDynamicRuleDefinitions 	copy()
 {
  return new jDynamicRuleDefinitions(rule_name,rule_arity,rules);
 };

/**
  * Construct dynamic rule based on name, arity, and vector of rules.  For 
  * internal duplications purposes.
  *
  * @param cg  		The <code>jClauseGoal</code> we are trying to prove.  Contains 
  * 			an index to the next clause that needs to be proved.
  * @param h  		The rule head <code>jPredicate</code> of the rule body. Name
  * 			and arity must match.
  *
  * @return  		The next <code>jTerm</code> of the rule base to prove. 
  * 			null if there are no more terms.
  */
 public jTerm 		getClause(jClauseGoal cg,jPredicate h)
 {int 				next_rule;
  jCompoundTerm 	ct;
  jTerm 			t;
 
  if (!(match(h)))
   return null;
   
  ct = h.getArguments();
   
  while ((next_rule = cg.getNextRuleNumber()) < rules.size())
   if ((t = ((jRule) rules.elementAt(next_rule)).getUnifiedBase(ct,cg.unified)) != null) 
    return t;

  return null;
 };
  
/**
  * Attempts to retract a rule from the rule definition. Retracted rule must unify
  * with the provided rule.  All non-retracted rules must be non-unified upon 
  * completion.
  *
  * @param rule  	The <code>jRule</code> of the type we are trying to remove.
  * @param v  		The variable vector required for unification.
  *
  * @return  		true if the rule was retracted, false otherwise.
  */
 public boolean 		retractUnifyRule(jRule rule,jUnifiedVector v)
 {int 				i,sz = rules.size();
  jPredicate 		h = rule.getHead();
  jPredicateTerms 	b = rule.getBase();
  jRule 			src_rule;
  
  if (!match(rule))
   return false;
    
  for (i = 0; i < sz; i++)
  {
   src_rule = (jRule) rules.elementAt(i);
   if (h.unifyArguments(src_rule.getHead().getArguments(),v) && b.unify(src_rule.getBase(),v))
   {
    removeRule(src_rule);
    return true;
   }
   else
    v.restoreVariables();    
  }

  return false;
 };
  
 public String			toString()
 {
  return super.toString() + " (dynamic)";
 };
};

