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
//	CutGoal
//#########################################################################

package ubc.cs.JLog.Builtins.Goals;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Terms.Goals.*;

public class jCutGoal extends jGoal {
    protected jGoal rule_goal;

    public jCutGoal(jGoal rule) {
	rule_goal = rule;
    };

    public void setRuleGoal(jGoal rule) {
	rule_goal = rule;
    };

    public boolean prove(iGoalStack goals, iGoalStack proved) {
	// we should cut out the proved goals that would never retry because of
	// cut.
	// this frees up memory early.
	if (rule_goal != null) {
	    proved.cut(rule_goal);
	    proved.push(this);

	    // tail-recursion optimization
	    try {
		jGoal g2;

		g2 = proved.peekn(2);

		if (rule_goal instanceof jPredicateGoal
			&& g2 instanceof jCutGoal) {
		    jPredicateGoal pg = (jPredicateGoal) rule_goal;
		    jCutGoal cg2 = (jCutGoal) g2;

		    if (cg2.rule_goal != null
			    && cg2.rule_goal instanceof jPredicateGoal) {
			jPredicateGoal pg2 = (jPredicateGoal) cg2.rule_goal;

			if (pg.getRules() == pg2.getRules()
				&& pg.getRuleNumber() == pg2.getRuleNumber())
			    proved.cut(g2);
		    }
		}
	    } catch (EmptyStackException e) {
	    } catch (IndexOutOfBoundsException e) {
	    } catch (NullPointerException e) {
	    }
	} else
	    proved.push(this);

	return true;
    };

    public boolean retry(iGoalStack goals, iGoalStack proved) {
	goals.push(this); // the remove that follows may need a node to remove
			  // or retry

	if (rule_goal != null) {
	    proved.cut(rule_goal);

	    { // we need to initialize goal to potentially restart
		rule_goal.internal_restore(goals);
	    }

	    // this puts the rule_goal on the goal stack
	    goals.push(proved.pop()); // a retry that follows may need a node to
				      // remove or retry
	}
	return false;
    };

    public String getName() {
	return "!";
    };

    public int getArity() {
	return 0;
    };

    public String toString() {
	StringBuffer sb = new StringBuffer();

	sb.append(getName() + "/" + String.valueOf(getArity()));

	return sb.toString();
    };
};
