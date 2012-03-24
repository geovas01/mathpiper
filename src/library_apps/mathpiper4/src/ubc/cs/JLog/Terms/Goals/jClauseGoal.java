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
//	ClauseGoal
//#########################################################################

package ubc.cs.JLog.Terms.Goals;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;

public class jClauseGoal extends jGoal {
    protected final static int STARTING_RULE = 0;

    protected jClause clause;

    protected jDynamicRuleDefinitions rules = null;
    protected int rule_number = STARTING_RULE;

    // for use by retract
    public jTerm lhs, rhs;
    public jUnifiedVector unified;

    public jClauseGoal(jClause c, jTerm l, jTerm r) {
	clause = c;
	lhs = l;
	rhs = r;
	unified = new jUnifiedVector();
    };

    public jDynamicRuleDefinitions getDynamicRules() {
	return rules;
    };

    public void setDynamicRules(jDynamicRuleDefinitions r) {
	rules = r;
	rule_number = STARTING_RULE;
    };

    public int getNextRuleNumber() {
	return rule_number++;
    };

    public int getRuleNumber() {
	return rule_number;
    };

    public boolean prove(iGoalStack goals, iGoalStack proved) {
	Thread t;

	t = Thread.currentThread();

	if (t instanceof jPrologServiceThread) {
	    jPrologServiceThread pst = (jPrologServiceThread) t;
	    jPrologServices prolog = pst.getPrologServices();
	    jKnowledgeBase database = prolog.getKnowledgeBase();

	    if (clause.prove(this, database)) {
		proved.push(this);
		return true;
	    }
	}

	{ // we need to initialize goal to potentially restart
	    rule_number = STARTING_RULE;
	    unified.restoreVariables();
	}
	goals.push(this); // a retry that follows may need a node to remove or
			  // retry
	return false;
    };

    public boolean retry(iGoalStack goals, iGoalStack proved) {
	unified.restoreVariables();

	goals.push(this); // a retry that follows may need a node to remove or
			  // retry
	return true; // retry until prove fails
    };

    public void internal_restore(iGoalStack goals) {
	unified.restoreVariables();
	rule_number = STARTING_RULE;
    };

    public String getName() {
	return clause.getName();
    };

    public int getArity() {
	return clause.getArity();
    };

    public String toString() {
	StringBuffer sb = new StringBuffer();

	sb.append(getName() + "/" + String.valueOf(getArity()) + " GOAL: ");
	sb.append(getName() + "(" + lhs.toString() + "," + rhs.toString() + ")");

	return sb.toString();
    };
};
