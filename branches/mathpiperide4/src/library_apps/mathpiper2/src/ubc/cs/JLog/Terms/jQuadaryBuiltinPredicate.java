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
//	QuadaryBuiltinPredicate
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.Goals.*;

abstract public class jQuadaryBuiltinPredicate extends jBuiltinPredicate {
    protected jTerm term1, term2, term3, term4;

    public jQuadaryBuiltinPredicate(jTerm t1, jTerm t2, jTerm t3, jTerm t4,
	    int t) {
	term1 = t1;
	term2 = t2;
	term3 = t3;
	term4 = t4;

	type = t;
    };

    public final int getArity() {
	return 4;
    };

    public final jTerm getTerm1() {
	return term1;
    };

    public final jTerm getTerm2() {
	return term2;
    };

    public final jTerm getTerm3() {
	return term3;
    };

    public final jTerm getTerm4() {
	return term4;
    };

    public final jCompoundTerm getArguments() {
	jCompoundTerm ct = new jCompoundTerm();

	ct.addTerm(term1);
	ct.addTerm(term2);
	ct.addTerm(term3);
	ct.addTerm(term4);

	return ct;
    };

    protected int compareArguments(iPredicate ipred, boolean first_call,
	    boolean var_equal) {
	if (ipred instanceof jQuadaryBuiltinPredicate) {
	    jQuadaryBuiltinPredicate tip = (jQuadaryBuiltinPredicate) ipred;
	    int compare_val, result;

	    compare_val = getName().compareTo(tip.getName());

	    if (compare_val < 0)
		return LESS_THAN;
	    if (compare_val > 0)
		return GREATER_THAN;

	    result = term1.compare(tip.term1, true, var_equal);
	    if (result != EQUAL)
		return result;
	    result = term2.compare(tip.term2, true, var_equal);
	    if (result != EQUAL)
		return result;
	    result = term3.compare(tip.term3, true, var_equal);
	    if (result != EQUAL)
		return result;
	    return term4.compare(tip.term4, true, var_equal);
	}

	return (first_call ? -ipred.compareArguments(this, false, var_equal)
		: EQUAL);
    };

    protected final boolean equivalenceArguments(jBuiltinPredicate pterm,
	    jEquivalenceMapping v) {
	if (pterm instanceof jQuadaryBuiltinPredicate) {
	    jQuadaryBuiltinPredicate tterm = (jQuadaryBuiltinPredicate) pterm;

	    return term1.equivalence(tterm.term1, v)
		    && term2.equivalence(tterm.term2, v)
		    && term3.equivalence(tterm.term3, v)
		    && term4.equivalence(tterm.term4, v);
	} else
	    return false;
    };

    protected final boolean unifyArguments(jBuiltinPredicate pterm,
	    jUnifiedVector v) {
	if (pterm instanceof jQuadaryBuiltinPredicate) {
	    jQuadaryBuiltinPredicate tterm = (jQuadaryBuiltinPredicate) pterm;

	    return term1.unify(tterm.term1, v) && term2.unify(tterm.term2, v)
		    && term3.unify(tterm.term3, v)
		    && term4.unify(tterm.term4, v);
	} else
	    return false;
    };

    public void registerVariables(jVariableVector v) {
	term1.registerVariables(v);
	term2.registerVariables(v);
	term3.registerVariables(v);
	term4.registerVariables(v);
    };

    public void enumerateVariables(jVariableVector v, boolean all) {
	term1.enumerateVariables(v, all);
	term2.enumerateVariables(v, all);
	term3.enumerateVariables(v, all);
	term4.enumerateVariables(v, all);
    };

    public void registerUnboundVariables(jUnifiedVector v) {
	term1.registerUnboundVariables(v);
	term2.registerUnboundVariables(v);
	term3.registerUnboundVariables(v);
	term4.registerUnboundVariables(v);
    };

    public boolean prove(jQuadaryBuiltinPredicateGoal bg) {
	throw new UnimplementedPredicateProveMethodException();
    };

    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	goals.push(new jQuadaryBuiltinPredicateGoal(this,
		term1.duplicate(vars), term2.duplicate(vars), term3
			.duplicate(vars), term4.duplicate(vars)));
    };

    public void addGoals(jGoal g, iGoalStack goals) {
	goals.push(new jQuadaryBuiltinPredicateGoal(this, term1, term2, term3,
		term4));
    };

    // since quadary predicates have variables, they should be able to duplicate
    // themselves
    // this version of duplicate only requires subclasses to create themselves.
    abstract protected jQuadaryBuiltinPredicate duplicate(jTerm t1, jTerm t2,
	    jTerm t3, jTerm t4);

    public jTerm duplicate(jVariable[] vars) {
	return duplicate(term1.duplicate(vars), term2.duplicate(vars),
		term3.duplicate(vars), term4.duplicate(vars));
    };

    public jTerm copy(jVariableRegistry vars) {
	return duplicate(term1.copy(vars), term2.copy(vars), term3.copy(vars),
		term4.copy(vars));
    };

    public void consult(jKnowledgeBase kb) {
	term1.consult(kb);
	term2.consult(kb);
	term3.consult(kb);
	term4.consult(kb);
    };

    public void consultReset() {
	term1.consultReset();
	term2.consultReset();
	term3.consultReset();
	term4.consultReset();
    };

    public boolean isConsultNeeded() {
	return true;
    };

    public String toString(boolean usename) {
	return getName() + "(" + term1.toString(usename) + ","
		+ term2.toString(usename) + "," + term3.toString(usename) + ","
		+ term4.toString(usename) + ")";
    };
};
