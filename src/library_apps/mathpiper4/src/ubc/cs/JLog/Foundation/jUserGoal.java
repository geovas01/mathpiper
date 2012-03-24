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
//	jUserGoal
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;

/**
 * Goal for getting user queries. <code>jUserGoal</code> is designed as a goal
 * stub so that user queries have the same behavior as if they were the based
 * off a rule. Notably, cuts will work, and or predicates containing cuts will
 * also work. <code>jUserGoal</code> assumes that it is the first goal (it is ok
 * to place it on the empty proved stack first without calling prove, the only
 * goal with this property). No attempt is made to keep track of unified
 * variables (for cuts), since a retry is guarenteed to fail, and standard
 * behavior is to not display variables if the query fails.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jUserGoal extends jGoal {
    public jUserGoal() {
    };

    public boolean prove(iGoalStack goals, iGoalStack proved) {
	proved.push(this);
	return true;
    };

    public boolean retry(iGoalStack goals, iGoalStack proved) {
	goals.push(this); // a retry that follows may need a node to remove or
			  // retry
	return false;
    };

    public String getName() {
	return "internal_user_goal_stub";
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