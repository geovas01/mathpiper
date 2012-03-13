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
//	Compare
//#########################################################################

package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.Goals.*;

public class jCompare extends jTrinaryBuiltinPredicate {
    protected boolean var_equal = false;

    public jCompare(jTerm t1, jTerm t2, jTerm t3) {
	super(t1, t2, t3, TYPE_BUILTINPREDICATE);
    };

    public jCompare(jTerm t1, jTerm t2, jTerm t3, boolean ve) {
	super(t1, t2, t3, TYPE_BUILTINPREDICATE);
	var_equal = ve;
    };

    public String getName() {
	return "compare";
    };

    // comparegoal does not support unification tracking, only verify or use
    // existing bindings
    public boolean prove(jCompareGoal cg) {
	jTerm t1 = cg.term1.getTerm();
	jTerm t2 = cg.term2.getTerm();
	jTerm t3 = cg.term3.getTerm();
	int result;

	result = t2.compare(t3, cg.var_equal);

	if (t1 instanceof iPredicate && ((iPredicate) t1).getArity() == 0) {
	    if (t1.getName().equals("="))
		return result == EQUAL;
	    if (t1.getName().equals("<"))
		return result == LESS_THAN;
	    if (t1.getName().equals(">"))
		return result == GREATER_THAN;
	} else {
	    switch (result) {
	    case EQUAL:
		return t1.unify(new jAtom("="), cg.unified);
	    case LESS_THAN:
		return t1.unify(new jAtom("<"), cg.unified);
	    case GREATER_THAN:
		return t1.unify(new jAtom(">"), cg.unified);
	    }
	}
	return false;
    };

    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	goals.push(new jCompareGoal(this, term1.duplicate(vars), term2
		.duplicate(vars), term3.duplicate(vars), var_equal));
    };

    public void addGoals(jGoal g, iGoalStack goals) {
	goals.push(new jCompareGoal(this, term1, term2, term3, var_equal));
    };

    protected jTrinaryBuiltinPredicate duplicate(jTerm t1, jTerm t2, jTerm t3) {
	return new jCompare(t1, t2, t3, var_equal);
    };
};
