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
//	jProver
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;

/**
* This class implements a prolog proving engine.  It uses two
* goal stacks to represent progress in the search tree.  The <code>goals</code> goal 
* stack stores goals that have yet to be evaluated. The <code>proved</code> goal
* stack stores goals that are already evaluated (i.e., the frontier).
* <P>
* The proving process takes a goal from the <code>goals</code> stack and attempts 
* to prove it. If successful, any subgoals of the goal are pushed onto the 
* <code>goals</code> stack and the goal to be proved is pushed onto the 
* <code>proved</code> stack.the is the default goal stack implementation.
*
* @author       Glendon Holst
* @version      %I%, %G%
*/
public class jProver
{
 protected jKnowledgeBase 	database;
 protected iGoalStack 		goals;
 protected iGoalStack 		proved;
 
 /**
  * Construct a prover instance with a given <code>jKnowledgeBase</code>.
  *
  * @param kb 		the knowledge base which attempted proofs are based upon.
  */
 public 			jProver(jKnowledgeBase kb)
 {
  database = kb;
  goals = null;
  proved = null;
 };
 
 /**
  * Initiate a proof of the provided goal.
  *
  * @param goal 	the term to attempt to prove.
  * @return 		<code>true</code> if the proof of <code>goal</code> succeeded, 
  * 			<code>false</code> otherwise.
  */
 public boolean 	prove(jPredicateTerms goal)
 {jVariableVector 		vars; 
  jUserGoal 			ug;
  
  goals = createGoalsStack();
  proved = createProvedStack();
  proved.push(ug = new jUserGoal());
  
  goal.consult(database);
  
  vars = new jVariableVector();
  goal.registerVariables(vars);
  goal.addGoals(ug,vars.getVariables(),goals);
  
  return internal_prove();
 };

 /**
  * Attempts to prove the previous goal in a different way than before.  Only call after
  * the previous call to <code>prove</code> succeeded, and no call to <code>retry</code>
  * have failed.
  *
  * @return 		<code>true</code> if the proof of <code>goal</code> succeeded, 
  * 			<code>false</code> otherwise.
  */
 public boolean 	retry()
 {
  if (!goals.empty() || !internal_retry())
   return false;
  
  return internal_prove();
 };
 
 protected boolean 	internal_prove()
 {
  try
  {
   while (!goals.empty())
   {
    if (!goals.pop().prove(goals,proved))
    {
     while (!proved.pop().retry(goals,proved))
      ;
    }
   }
   return true;
  }
  catch (EmptyStackException e)
  {// this should only occur when the proved stack is empty
   return false;
  }
 };
 
 protected boolean 	internal_retry()
 {
  while (!proved.empty())
  {
   if (proved.pop().retry(goals,proved))
    return true;
  }
  return false;
 };
 
 /**
  * Construct a goal stack for <code>proved</code>. Subclasses can override this
  * factory method to return goal stacks with different capabilities (but same interface). 
  * 
  * @return 		instance of <code>iGoalStack</code>.
  */
 protected iGoalStack 		createProvedStack()
 {
  return new jGoalStack();
 };
 
 /**
  * Construct a goal stack for <code>goals</code>. Subclasses can override this
  * factory method to return goal stacks with different capabilities (but same interface). 
  * 
  * @return 		instance of <code>iGoalStack</code>.
  */
 protected iGoalStack 		createGoalsStack()
 {
  return new jGoalStack();
 };
};
