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
//	Predicate
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;

/**
 * This is the abstract base class for Prolog predicates.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
abstract public class iPredicate extends jTerm implements iNameArity {
    protected int compare(jTerm term, boolean first_call, boolean var_equal) {
	jTerm t = term.getTerm();

	if ((t instanceof jVariable) || (t instanceof jReal)
		|| (t instanceof jInteger))
	    return GREATER_THAN;

	if (t instanceof iPredicate) {
	    iPredicate ip = (iPredicate) t;
	    int compare_val;
	    int arity_a, arity_b;

	    arity_a = getArity();
	    arity_b = ip.getArity();

	    if (arity_a < arity_b)
		return LESS_THAN;
	    else if (arity_a > arity_b)
		return GREATER_THAN;

	    compare_val = getName().compareTo(ip.getName());

	    if (compare_val < 0)
		return LESS_THAN;
	    if (compare_val > 0)
		return GREATER_THAN;

	    return compareArguments(ip, true, var_equal);
	}

	return (first_call ? -t.compare(this, false, var_equal) : EQUAL);
    };

    /**
     * Compares the arguments for predicates. Like <code>compare</code> except
     * that name and arity must be equal.
     * 
     * @param ipred
     *            the <code>iPredicate</code> to compare with this instance of
     *            <code>iPredicate</code>.
     * @param first_call
     *            <code>true</code> if just invoked by public
     *            <code>compare</code> member function. <code>false</code>
     *            otherwise.
     * @param var_equal
     *            if <code>var_equal</code> is <code>true</code>, then unbound
     *            variables are considered equal. <code>var_equal</code> =
     *            <code>false</code> is standard prolog behavior.
     * @return <code>LESS_THAN</code> if this instance is less than
     *         <code>term</code>, <code>EQUAL</code> if the two terms are equal,
     *         and <code>GREATER_THAN</code> if this instance is greater than
     *         <code>term</code>.
     */
    abstract protected int compareArguments(iPredicate ipred,
	    boolean first_call, boolean var_equal);

    /**
     * Accessor for the arguments for this predicate. Don't modify arguments
     * after consult. Arguments are not guaranteed to be original so modifying
     * has an unspecified effect.
     * 
     * @return The original or possibly duplicated arguments for this predicate.
     */
    public jCompoundTerm getArguments() {
	return new jCompoundTerm();
    };

    /**
     * Adds goal to prove this predicate onto goal stack. This version is called
     * when the original predicate must duplicate itself (or its arguments). Add
     * goals in the reverse order you want them evaluated.
     * 
     * @param g
     *            The super-goal <code>jGoal</code> for the sub-goal that this
     *            function will add.
     * @param vars
     *            The vector of duplicate variables produced from the previous
     *            call to <code>registerVariables</code>. Needed for call to
     *            <code>duplicate</code>.
     * @param goals
     *            The <code>iGoalStack</code> to add the goal to.
     */
    abstract public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals);

    /**
     * Adds goal to prove this predicate onto goal stack. This version is called
     * when the original predicate is already duplicated (usually as part of an
     * argument for another predicate). Add goals in the reverse order you want
     * them evaluated.
     * 
     * @param g
     *            The super-goal <code>jGoal</code> for the sub-goal that this
     *            function will add.
     * @param goals
     *            The <code>iGoalStack</code> to add the goal to.
     */
    abstract public void addGoals(jGoal g, iGoalStack goals);
};
