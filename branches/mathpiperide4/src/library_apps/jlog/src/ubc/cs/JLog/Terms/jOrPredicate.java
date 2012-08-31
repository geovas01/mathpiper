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
//	OrPredicate
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Terms.Goals.*;

public class jOrPredicate extends iPredicate implements iMakeUnmake {
    protected Vector predicateterms;

    public jOrPredicate() {
	predicateterms = new Vector();
	type = TYPE_ORPREDICATE;
    };

    protected jOrPredicate(Vector t) {
	predicateterms = t;
	type = TYPE_ORPREDICATE;
    };

    public String getName() {
	return ";";
    };

    public int getArity() {
	return predicateterms.size();
    };

    public jCompoundTerm getArguments() {
	jCompoundTerm ct = new jCompoundTerm();
	Enumeration e = predicateterms.elements();

	while (e.hasMoreElements())
	    ct.addTerm((jPredicateTerms) e.nextElement());

	return ct;
    };

    protected int compareArguments(iPredicate ipred, boolean first_call,
	    boolean var_equal) {
	if (ipred instanceof jOrPredicate) {
	    Vector pt = ((jOrPredicate) ipred).predicateterms;
	    int i, max = predicateterms.size();
	    int result;

	    // both vectors being the same size is a pre-condition
	    for (i = 0; i < max; i++) {
		result = ((jPredicateTerms) predicateterms.elementAt(i))
			.compare((jPredicateTerms) pt.elementAt(i), true,
				var_equal);
		if (result != EQUAL)
		    return result;
	    }
	    return EQUAL;
	}

	return (first_call ? -ipred.compareArguments(this, false, var_equal)
		: EQUAL);
    };

    public void addPredicateTerms(jPredicateTerms t) {
	predicateterms.addElement(t);
    };

    public void removePredicateTerms(jPredicateTerms t) {
	predicateterms.removeElement(t);
    };

    public Enumeration enumPredicateTerms() {
	return predicateterms.elements();
    };

    public int size() {
	return predicateterms.size();
    };

    public jPredicateTerms elementAt(int index) {
	return (jPredicateTerms) predicateterms.elementAt(index);
    };

    public boolean isEmpty() {
	return predicateterms.isEmpty();
    };

    public boolean equivalence(jTerm term, jEquivalenceMapping v) {
	jTerm t = term.getTerm();

	// only equiv with other terms of same type
	if (type != t.type)
	    return false;

	// altough we cannot be certain that term is a jOrPredicate, if it is
	// not then type
	// was wrong so this warrents a failing exception.
	{
	    jOrPredicate orpred;
	    int sz;

	    orpred = (jOrPredicate) t;

	    // only unify if terms have same size
	    if ((sz = predicateterms.size()) != orpred.predicateterms.size())
		return false;

	    {
		int i;

		// equiv each element of orpredicate, exit on first failure.
		for (i = 0; i < sz; i++)
		    if (!((jPredicateTerms) predicateterms.elementAt(i))
			    .equivalence(
				    (jPredicateTerms) orpred.predicateterms
					    .elementAt(i), v))
			return false;
	    }
	}
	return true;
    };

    public boolean unify(jTerm term, jUnifiedVector v) {
	// if term is variable we let it handle the unification
	if (term.type == TYPE_VARIABLE)
	    return term.unify(this, v);

	// only unify with other terms of same type
	if (type != term.type)
	    return false;

	// altough we cannot be certain that term is a jOrPredicate, if it is
	// not then type
	// was wrong so this warrents a failing exception.
	{
	    jOrPredicate orpred;
	    int sz;

	    orpred = (jOrPredicate) term;

	    // only unify if terms have same size
	    if ((sz = predicateterms.size()) != orpred.predicateterms.size())
		return false;

	    {
		int i;

		// unify each element of orpredicate, exit on first unification
		// failure.
		for (i = 0; i < sz; i++)
		    if (!((jPredicateTerms) predicateterms.elementAt(i)).unify(
			    (jPredicateTerms) orpred.predicateterms
				    .elementAt(i), v))
			return false;
	    }
	}
	return true;
    };

    public boolean requiresCompleteVariableState() {
	Enumeration e;

	e = predicateterms.elements();

	while (e.hasMoreElements())
	    if (((jPredicateTerms) e.nextElement())
		    .requiresCompleteVariableState())
		return true;

	return false;
    };

    public void registerUnboundVariables(jUnifiedVector v) {
	int i, sz = predicateterms.size();

	for (i = 0; i < sz; i++)
	    ((jPredicateTerms) predicateterms.elementAt(i))
		    .registerUnboundVariables(v);
    };

    public void registerVariables(jVariableVector v) {
	Enumeration e;

	e = predicateterms.elements();

	while (e.hasMoreElements())
	    ((jPredicateTerms) e.nextElement()).registerVariables(v);
    };

    public void enumerateVariables(jVariableVector v, boolean all) {
	Enumeration e;

	e = predicateterms.elements();

	while (e.hasMoreElements())
	    ((jPredicateTerms) e.nextElement()).enumerateVariables(v, all);
    };

    public jTerm duplicate(jVariable[] vars) {
	int i, sz = predicateterms.size();
	Vector t = new Vector(sz);

	for (i = 0; i < sz; i++)
	    t.addElement(((jPredicateTerms) predicateterms.elementAt(i))
		    .duplicate(vars));

	return new jOrPredicate(t);
    };

    public jTerm copy(jVariableRegistry vars) {
	int i, sz = predicateterms.size();
	Vector t = new Vector(sz);

	for (i = 0; i < sz; i++)
	    t.addElement(((jPredicateTerms) predicateterms.elementAt(i))
		    .copy(vars));

	return new jOrPredicate(t);
    };

    public void consult(jKnowledgeBase kb) {
	Enumeration e;

	e = enumPredicateTerms();

	while (e.hasMoreElements())
	    ((iConsultable) e.nextElement()).consult(kb);
    };

    public void consultReset() {
	Enumeration e;

	e = enumPredicateTerms();

	while (e.hasMoreElements())
	    ((iConsultable) e.nextElement()).consultReset();
    };

    public String toString(boolean usename) {
	StringBuffer sb = new StringBuffer();
	Enumeration e;
	boolean first = true;

	e = predicateterms.elements();

	while (e.hasMoreElements()) {
	    if (!first)
		sb.append(";");
	    sb.append(((jPredicateTerms) e.nextElement()).toString(usename));

	    first = false;
	}

	return sb.toString();
    };

    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	goals.push(new jOrGoalWVars(this, g, vars, (goals.empty() ? null
		: goals.peek())));
    };

    public void addGoals(jGoal g, iGoalStack goals) {
	goals.push(new jOrGoal(this, g, (goals.empty() ? null : goals.peek())));
    };

    public final boolean prove(jOrGoal og, jGoal g, jVariable[] vars,
	    iGoalStack goals) {
	int next_pterm;

	if ((next_pterm = og.getNextPredicateTermNumber()) < predicateterms
		.size()) {
	    ((jPredicateTerms) predicateterms.elementAt(next_pterm)).addGoals(
		    g, vars, goals);
	    return true;
	}

	return false;
    };

    public final boolean prove(jOrGoal og, jGoal g, iGoalStack goals) {
	int next_pterm;

	if ((next_pterm = og.getNextPredicateTermNumber()) < predicateterms
		.size()) {
	    ((jPredicateTerms) predicateterms.elementAt(next_pterm)).addGoals(
		    g, goals);
	    return true;
	}

	return false;
    };

    public boolean retry(jOrGoal og) {
	return (og.getPredicateTermNumber() < predicateterms.size());
    };

    public void make(jTerm t) {
	predicateterms.removeAllElements();
	makeOrPredicate(t);
    };

    public void makeOrPredicate(jTerm t) {
    };

    public jTerm unmake() {
	return unmakeOrPredicate();
    };

    public synchronized jTerm unmakeOrPredicate() {
	int i;
	jTerm prev = null;

	for (i = predicateterms.size() - 1; i >= 0; i--) {
	    jPredicateTerms curr = (jPredicateTerms) predicateterms
		    .elementAt(i);

	    if (prev == null)
		prev = curr.unmakePredicateTerms();
	    else
		prev = new jOr(curr.unmakePredicateTerms(), prev);
	}
	return prev;
    };
};
