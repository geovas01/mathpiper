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
//	jGoal
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;

/**
 * This abstract class specifies the behaviour for a goal.  
 * The <code>jGoal</code> class is designed to work in conjunction with 
 * <code>iGoalStack</code>s and with <code>jProver</code>.  
 * <code>jGoal</code>s provide services for proving and retrying goals, and
 * in the process they must correctly update the <code>goal</code> and
 * <code>proved</code> stacks for <code>jProver</code>.  <P>
 * Each <code>jGoal</code> is associated to some type of provable predicate,
 * which provides some type of <code>prove</code> member function to perform
 * the actual proof (or evaluation).  The <code>jGoal</code> is responsible 
 * for updating the goal stacks as needed.
 *
 * @author       Glendon Holst
 * @version      %I%, %G%
 */
abstract public class jGoal implements iNameArity
{
 public 	jGoal()
 {
 };
 
 /**
  * Attempt to prove the goal represented by this <code>jGoal</code>.
  * Goal must not be on either stack to prove (pop off goals stack before prove call).
  * Goal must place itself on the appropriate stack before returning. 
  * <code>proved</code> stack if 'proved', <code>goals</code> stack otherwise.
  * Since proved doesn't pop the stack we don't need a try/catch block.
  * Must be called at least once before calling <code>retry</code>.
  *
  * @param goals 	<code>iGoalStack</code> containing goals yet to be proved.
  * @param proved 	<code>iGoalStack</code> containing evaluated goals (goals 
  * 			that may already be proved, or awaiting their child goals 
  * 			to be proved).
  *
  * @return 		<code>boolean</code> is true if this goal ended up on 
  * 			<code>proved</code>, false otherwise.
  */
 abstract public boolean 	prove(iGoalStack goals,iGoalStack proved);
 
 /**
  * Attempt to prove the goal represented by this <code>jGoal</code>.
  * Goal must not be on either stack to retry (pop off proved stack before retry call).
  * Goal must place itself on the goal stack, and normally need not pop either stack.
  * Since retry doesn't pop the stack we don't need a try/catch block.
  *
  * @param goals 	<code>iGoalStack</code> containing goals yet to be proved.
  * @param proved 	<code>iGoalStack</code> containing evaluated goals (goals 
  * 			that may already be proved, or awaiting their child goals 
  * 			to be proved).
  *
  * @return 		<code>boolean</code> is true if the prover should attempt
  * 			a prove on this goal, false otherwise.
  */
 abstract public boolean 	retry(iGoalStack goals,iGoalStack proved);
 
 /**
  * Make a rule on the proved stack clean up children on the goal stack.
  * Should be called from within <code>retry</code>, and should rely on 
  * <code>retry</code> to adjust the proved stack.  this remains on proved stack.
  *
  * @param goals 	<code>iGoalStack</code> containing goals yet to be proved.
  */
 protected void 	internal_remove(iGoalStack goals) {};

 /**
  * Combines <code>internal_remove</code> with a full restart and resoration of 
  * any previous variable state. May be called by other goals, such as a 
  * <code>jCutGoal</code>. this is still located on proved stack.
  *
  * @param goals 	<code>iGoalStack</code> containing goals yet to be proved.
  */
  public void 	internal_restore(iGoalStack goals) {};
 
 public jGoal 	next = null; // only iGoalStack should access this member
};

