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
//	jGoalStack
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;

/**
 * <code>jGoalStack</code> is the default goal stack implementation. It is
 * efficient, with a super-efficient cut operation. The design purpose of this
 * class is purely efficiency.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jGoalStack implements iGoalStack {
	protected jGoal head;

	public jGoalStack() {
		head = null;
	};

	public boolean empty() {
		return head == null;
	};

	public jGoal pop() {
		jGoal top;

		if ((top = head) == null)
			throw new EmptyStackException();

		head = top.next;
		return top;
	};

	public jGoal peek() {
		if (head == null)
			throw new EmptyStackException();

		return head;
	};

	public jGoal peekn(int n) {
		jGoal g = head;

		for (g = head; g != null && n >= 0; n--) {
			if (n == 0)
				return g;

			g = g.next;
		}

		throw new EmptyStackException();
	};

	public jGoal push(jGoal item) {
		item.next = head;
		head = item;

		return item;
	};

	public jGoal cut(jGoal item) {
		head = item;

		return item;
	};
};
