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
//	Arithmetic
//#########################################################################

package ubc.cs.JLog.Builtins;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Terms.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.Goals.*;

/**
 * The abstract class for binary arithmetic operators.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
abstract class jArithmetic extends jBinaryBuiltinPredicate implements
	iArithmetic {
    public jArithmetic(jTerm l, jTerm r) {
	super(l, r, TYPE_ARITHMETIC);
    };

    public jTerm getValue() {
	jTerm l, r;

	l = lhs.getValue();
	r = rhs.getValue();

	if (l.type == TYPE_INTEGER && r.type == TYPE_INTEGER)
	    return new jInteger(operatorInt(((jInteger) l).getIntegerValue(),
		    ((jInteger) r).getIntegerValue()));
	else {
	    float rl, rr;

	    if (l.type == TYPE_INTEGER)
		rl = (float) ((jInteger) l).getIntegerValue();
	    else if (l.type == TYPE_REAL)
		rl = ((jReal) l).getRealValue();
	    else
		throw new InvalidArithmeticOperationException();

	    if (r.type == TYPE_INTEGER)
		rr = (float) ((jInteger) r).getIntegerValue();
	    else if (r.type == TYPE_REAL)
		rr = ((jReal) r).getRealValue();
	    else
		throw new InvalidArithmeticOperationException();

	    return new jReal(operatorReal(rl, rr));
	}
    };

    /**
     * Perform the integer operations specified by this instance on the two
     * given integers, and return the integer result.
     * 
     * @param l
     *            The left operand.
     * @param r
     *            The right operand.
     * 
     * @return The result of <code>l</code> op <code>r</code> where <i>op</i> is
     *         specified by this instance.
     */
    abstract protected int operatorInt(int l, int r);

    /**
     * Perform the float operations specified by this instance on the two given
     * floats, and return the float result.
     * 
     * @param l
     *            The left operand.
     * @param r
     *            The right operand.
     * 
     * @return The result of <code>l</code> op <code>r</code> where <i>op</i> is
     *         specified by this instance.
     */
    abstract protected float operatorReal(float l, float r);

    /**
     * Arithmetics are intended as expressions for <code>is</code>, if used a
     * predicate they fail.
     */
    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	goals.push(new jFailGoal());
    };

    /**
     * Arithmetics are intended as expressions for <code>is</code>, if used a
     * predicate they fail.
     */
    public void addGoals(jGoal g, iGoalStack goals) {
	goals.push(new jFailGoal());
    };

    public String toString(boolean usename) {
	jTerm l, r;
	boolean bracket_l = false, bracket_r = false;

	l = lhs.getTerm();
	r = rhs.getTerm();

	if (l instanceof iArithmetic)
	    bracket_l = ((iArithmetic) l).getPriority() > getPriority();
	if (r instanceof iArithmetic)
	    bracket_r = ((iArithmetic) r).getPriority() > getPriority();

	return (bracket_l ? "(" : "") + lhs.toString(usename)
		+ (bracket_l ? ") " : " ") + getName()
		+ (bracket_r ? " (" : " ") + rhs.toString(usename)
		+ (bracket_r ? ")" : "");
    };
};
