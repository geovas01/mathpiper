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
//	CallGoal
//#########################################################################

package ubc.cs.JLog.Builtins.Goals;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Builtins.*;

public class jCallGoal extends jGoal {
    protected jTerm callee;
    protected jUnifiedVector unified;
    protected jGoal end_goal = null;

    public jCallGoal(jTerm c) {
	callee = c;
	unified = new jUnifiedVector();
    };

    // endgoal is the previous goal on the goalstack. if endgoal is null, then
    // goalstack
    // was previously empty.
    public final void setEndGoal(jGoal goal) {
	end_goal = goal;
    };

    public boolean prove(iGoalStack goals, iGoalStack proved) {
	jTerm ct;

	ct = callee.getTerm();

	// variables are not permitted, since this would cause infinite regress
	if (ct.type == jType.TYPE_VARIABLE)
	    throw new InvalidCalleeTypeException();

	setEndGoal(goals.empty() ? null : goals.peek());

	try {
	    jPredicateTerms base;

	    base = new jPredicateTerms();
	    base.makePredicateTerms(ct);

	    if (base.requiresCompleteVariableState())
		base.registerUnboundVariables(unified);

	    base.addGoals(this, goals);
	} catch (PredicateExpectedException e) {
	    throw new InvalidCalleeTypeException();
	}

	proved.push(this);
	return true;
    };

    public boolean retry(iGoalStack goals, iGoalStack proved) {
	internal_restore(goals);

	goals.push(this); // a retry that follows may need a node to remove or
			  // retry
	return false;
    };

    protected final void internal_remove(iGoalStack goals) {
	goals.cut(end_goal);
    };

    public final void internal_restore(iGoalStack goals) {
	goals.cut(end_goal);

	unified.restoreVariables();
    };

    public String getName() {
	return "call";
    };

    public int getArity() {
	return 1;
    };

    public String toString() {
	StringBuffer sb = new StringBuffer();

	sb.append(getName() + "/" + String.valueOf(getArity()) + " goal: ");
	sb.append(getName() + "(" + callee.toString() + ")");

	return sb.toString();
    };
};
