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
//	iGoalStack
//#########################################################################

package ubc.cs.JLog.Foundation;

import java.lang.*;
import java.util.*;

/**
 * <code>iGoalStack</code> is the goal stack interface. A goal stack contains
 * items of type jGoal that typically represent either proved goals or goals
 * that need to be proved.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public interface iGoalStack {
    /**
     * Determines if the stack does not contain goals.
     * 
     * @return <code>true</code> if the stack is empty, <code>false</code>
     *         otherwise.
     */
    public boolean empty();

    /**
     * Returns and removes the top goal of the stack. Do not pop an empty stack.
     * 
     * @return the top <code>jGoal</code> of the stack.
     */
    public jGoal pop();

    /**
     * Returns the top goal of the stack, without removing that goal. Do not
     * peek an empty stack.
     * 
     * @return the top <code>jGoal</code> of the stack.
     */
    public jGoal peek();

    /**
     * Returns the nth goal on the stack, without removing that goal. Do not
     * peek an empty stack. 0 is the top element.
     * 
     * @return the nth <code>jGoal</code> of the stack.
     */
    public jGoal peekn(int n);

    /**
     * Places the provided goal <code>item</code> as the top element of the
     * stack.
     * 
     * @param item
     *            the goal to add to the stack top.
     * @return the top <code>jGoal</code> of the stack, which is the provided
     *         <code>item</code>.
     */
    public jGoal push(jGoal item);

    /**
     * Removes all goals above the specified <code>item</code>.
     * <code>item</code> becomes the top goal.
     * 
     * @param item
     *            the goal to truncate the stack at. <code>item</code> becomes
     *            the stack top. <code>item</code> must be in this stack
     *            instance.
     * @return the top <code>jGoal</code> of the stack, which is the provided
     *         <code>item</code>.
     */
    public jGoal cut(jGoal item);
};
