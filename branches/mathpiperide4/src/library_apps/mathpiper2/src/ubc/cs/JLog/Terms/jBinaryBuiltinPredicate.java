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
//	BinaryBuiltinPredicate
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.Goals.*;

abstract public class jBinaryBuiltinPredicate extends jBuiltinPredicate {
    protected jTerm lhs, rhs;

    public jBinaryBuiltinPredicate(jTerm l, jTerm r, int t) {
	lhs = l;
	rhs = r;

	type = t;
    };

    public final int getArity() {
	return 2;
    };

    public final jTerm getLHS() {
	return lhs;
    };

    public final jTerm getRHS() {
	return rhs;
    };

    public final jCompoundTerm getArguments() {
	jCompoundTerm ct = new jCompoundTerm();

	ct.addTerm(lhs);
	ct.addTerm(rhs);

	return ct;
    };

    protected int compareArguments(iPredicate ipred, boolean first_call,
	    boolean var_equal) {
	if (ipred instanceof jBinaryBuiltinPredicate) {
	    jBinaryBuiltinPredicate bip = (jBinaryBuiltinPredicate) ipred;
	    int compare_val, result;

	    compare_val = getName().compareTo(bip.getName());

	    if (compare_val < 0)
		return LESS_THAN;
	    if (compare_val > 0)
		return GREATER_THAN;

	    result = lhs.compare(bip.lhs, true, var_equal);
	    if (result != EQUAL)
		return result;

	    return rhs.compare(bip.rhs, true, var_equal);
	}

	return (first_call ? -ipred.compareArguments(this, false, var_equal)
		: EQUAL);
    };

    protected final boolean equivalenceArguments(jBuiltinPredicate pterm,
	    jEquivalenceMapping v) {
	if (pterm instanceof jBinaryBuiltinPredicate) {
	    jBinaryBuiltinPredicate bterm = (jBinaryBuiltinPredicate) pterm;

	    return lhs.equivalence(bterm.lhs, v)
		    && rhs.equivalence(bterm.rhs, v);
	} else
	    return false;
    };

    protected final boolean unifyArguments(jBuiltinPredicate pterm,
	    jUnifiedVector v) {
	if (pterm instanceof jBinaryBuiltinPredicate) {
	    jBinaryBuiltinPredicate bterm = (jBinaryBuiltinPredicate) pterm;

	    return lhs.unify(bterm.lhs, v) && rhs.unify(bterm.rhs, v);
	} else
	    return false;
    };

    public void registerVariables(jVariableVector v) {
	lhs.registerVariables(v);
	rhs.registerVariables(v);
    };

    public void enumerateVariables(jVariableVector v, boolean all) {
	lhs.enumerateVariables(v, all);
	rhs.enumerateVariables(v, all);
    };

    public void registerUnboundVariables(jUnifiedVector v) {
	lhs.registerUnboundVariables(v);
	rhs.registerUnboundVariables(v);
    };

    public boolean prove(jBinaryBuiltinPredicateGoal bg) {
	throw new UnimplementedPredicateProveMethodException();
    };

    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	goals.push(new jBinaryBuiltinPredicateGoal(this, lhs.duplicate(vars),
		rhs.duplicate(vars)));
    };

    public void addGoals(jGoal g, iGoalStack goals) {
	goals.push(new jBinaryBuiltinPredicateGoal(this, lhs, rhs));
    };

    // since binary predicates have variables, they should be able to duplicate
    // themselves
    // this version of duplicate only requires subclasses to create themselves.
    abstract protected jBinaryBuiltinPredicate duplicate(jTerm l, jTerm r);

    public jTerm duplicate(jVariable[] vars) {
	return duplicate(lhs.duplicate(vars), rhs.duplicate(vars));
    };

    public jTerm copy(jVariableRegistry vars) {
	return duplicate(lhs.copy(vars), rhs.copy(vars));
    };

    public void consult(jKnowledgeBase kb) {
	lhs.consult(kb);
	rhs.consult(kb);
    };

    public void consultReset() {
	lhs.consultReset();
	rhs.consultReset();
    };

    public boolean isConsultNeeded() {
	return true;
    };

    public String toString(boolean usename) {
	return getName() + "(" + lhs.toString(usename) + ","
		+ rhs.toString(usename) + ")";
    };
};
