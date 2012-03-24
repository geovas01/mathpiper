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
//	OrGoal
//#########################################################################

package ubc.cs.JLog.Terms.Goals;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;

public class jOrGoal extends jGoal {
    protected final static int STARTING_PREDICATE = 0;

    protected jOrPredicate or_predicate;
    protected jGoal head_goal;
    protected jGoal end_goal;
    protected int pterm_number = STARTING_PREDICATE;

    public jOrGoal(jOrPredicate op, jGoal g, jGoal end) {
	or_predicate = op;
	head_goal = g;
	end_goal = end;
    };

    public final int getNextPredicateTermNumber() {
	return pterm_number++;
    };

    public final int getPredicateTermNumber() {
	return pterm_number;
    };

    public boolean prove(iGoalStack goals, iGoalStack proved) {
	if (or_predicate.prove(this, head_goal, goals)) {
	    proved.push(this);
	    return true;
	} else {
	    { // we need to initialize goal to potentially restart
		pterm_number = STARTING_PREDICATE;
	    }
	    goals.push(this); // a retry that follows may need a node to remove
			      // or retry
	    return false;
	}
    };

    public boolean retry(iGoalStack goals, iGoalStack proved) {
	goals.cut(end_goal);

	if (or_predicate.retry(this)) {
	    goals.push(this);
	    return true;
	} else {
	    { // we need to initialize goal to potentially restart
		pterm_number = STARTING_PREDICATE;
	    }
	    goals.push(this); // a retry that follows may need a node to remove
			      // or retry
	    return false;
	}
    };

    public final void internal_restore(iGoalStack goals) {
	goals.cut(end_goal);

	pterm_number = STARTING_PREDICATE;
    };

    public String getName() {
	return or_predicate.getName();
    };

    public int getArity() {
	return or_predicate.getArity();
    };

    public String toString() {
	StringBuffer sb = new StringBuffer();

	sb.append(getName() + "/" + String.valueOf(getArity()) + " GOAL: ");
	sb.append(or_predicate.toString());

	return sb.toString();
    };
};
