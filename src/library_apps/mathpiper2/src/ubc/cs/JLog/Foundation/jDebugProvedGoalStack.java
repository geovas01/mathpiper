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
//	jDebugProvedGoalStack
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;

public class jDebugProvedGoalStack implements iDebugGoalStack {
    protected Stack stack;

    public class jDebugGoalItem {
	protected jGoal goal, next = null;
	protected Vector sub_goals = null;

	public jDebugGoalItem(jGoal g) {
	    goal = g;
	};

	public void setSubGoals(Vector v) {
	    sub_goals = v;
	};

	// null if subgoals were not set.
	public Vector getSubGoals() {
	    return sub_goals;
	};

	public jGoal getGoal() {
	    return goal;
	};

	public void setNextGoal(jGoal n) {
	    next = n;
	};

	public jGoal getNextGoal() {
	    return next;
	};
    };

    public jDebugProvedGoalStack() {
	stack = new Stack();
    };

    public boolean empty() {
	return stack.empty();
    };

    public synchronized jGoal pop() {
	jDebugGoalItem g = (jDebugGoalItem) stack.pop();

	return g.getGoal();
    };

    public jGoal peek() {
	jDebugGoalItem g = (jDebugGoalItem) stack.peek();

	return g.getGoal();
    };

    public jGoal peekn(int n) {
	jDebugGoalItem g = (jDebugGoalItem) stack.elementAt(stack.size()
		- (n + 1));

	return g.getGoal();
    };

    public synchronized jGoal push(jGoal item) {
	item.next = null;
	stack.push(new jDebugGoalItem(item));
	return item;
    };

    public synchronized jGoal cut(jGoal item) {
	jDebugGoalItem g;

	while (!stack.empty()) {
	    g = (jDebugGoalItem) stack.pop();
	    if (g.getGoal() == item) {
		stack.push(g);
		return item;
	    }
	}
	return null;
    };

    public jDebugGoalItem peekDebugItem() {
	return (jDebugGoalItem) stack.peek();
    };

    public synchronized jDebugGoalItem popDebugItem() {
	return (jDebugGoalItem) stack.pop();
    };

    public synchronized jDebugGoalItem getDebugItem(jGoal item) {
	Enumeration e = stack.elements();

	while (e.hasMoreElements()) {
	    jDebugGoalItem g = (jDebugGoalItem) e.nextElement();

	    if (g.getGoal() == item)
		return g;
	}

	throw new DebugStackException();
    };

    public synchronized Vector getProvedDebugItems(jGoal next) {
	Enumeration e = stack.elements();
	Vector v = new Vector();

	while (e.hasMoreElements()) {
	    jDebugGoalItem g = (jDebugGoalItem) e.nextElement();

	    if (g.getNextGoal() == next)
		v.addElement(g);
	}

	return v;
    };

    public synchronized Vector getStackCopy() {
	Enumeration e = stack.elements();
	Vector v = new Vector(stack.size());

	while (e.hasMoreElements())
	    v.addElement(e.nextElement());

	return v;
    };
};

class DebugStackException extends RuntimeException {
    public DebugStackException() {
    };

    public DebugStackException(String s) {
	super(s);
    };
};
