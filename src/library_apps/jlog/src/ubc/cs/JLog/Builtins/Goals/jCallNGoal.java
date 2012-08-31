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
//	CallNGoal
//#########################################################################

package ubc.cs.JLog.Builtins.Goals;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Builtins.*;

public class jCallNGoal extends jCallGoal {
    public jCallNGoal(jCompoundTerm c) {
	super(c);
    };

    public boolean prove(iGoalStack goals, iGoalStack proved) {
	jCompoundTerm ct;
	jTerm t0;
	jPredicate pt;

	ct = (jCompoundTerm) callee.getTerm();
	t0 = ct.elementAt(0).getTerm();

	// must be a predicate that can have terms added
	if (t0.type != iType.TYPE_PREDICATE && t0.type != iType.TYPE_ATOM)
	    throw new InvalidCalleeTypeException();

	{
	    int i;
	    jCompoundTerm args = new jCompoundTerm(((iPredicate) t0).getArity()
		    + ct.size() - 1);

	    if (t0.type == iType.TYPE_PREDICATE) {
		jCompoundTerm t0ct = ((jPredicate) t0).getArguments();

		for (i = 0; i < t0ct.size(); i++)
		    args.addTerm(t0ct.elementAt(i));
	    }

	    for (i = 1; i < ct.size(); i++)
		args.addTerm(ct.elementAt(i));

	    pt = new jPredicate(t0.getName(), args);
	}

	setEndGoal(goals.empty() ? null : goals.peek());

	pt.addGoals(this, goals);

	proved.push(this);
	return true;
    };

    public int getArity() {
	return ((jCompoundTerm) callee).size();
    };

    public String toString() {
	StringBuffer sb = new StringBuffer();

	sb.append(getName() + "/" + String.valueOf(getArity()) + " -> ");
	sb.append(getName() + callee.toString());

	return sb.toString();
    };

};
