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
//	PredicateTerms
//#########################################################################

package ubc.cs.JLog.Terms;

import java.lang.*;
import java.util.*;
import ubc.cs.JLog.Foundation.*;
import ubc.cs.JLog.Builtins.*;

/**
 * This class represents a collection of predicates.
 * 
 * @author Glendon Holst
 * @version %I%, %G%
 */
public class jPredicateTerms extends jCompoundTerm {
    public jPredicateTerms() {
	super();
	type = TYPE_PREDICATETERMS;
    };

    protected jPredicateTerms(Vector t) {
	super(t);
	type = TYPE_PREDICATETERMS;
    };

    public void addTerm(jTerm t) {
	addPredicate((iPredicate) t);
    };

    public void removeTerm(jTerm t) {
	removePredicate((iPredicate) t);
    };

    public void addPredicate(iPredicate p) {
	super.addTerm(p);
    };

    public void removePredicate(iPredicate p) {
	super.removeTerm(p);
    };

    public jTerm duplicate(jVariable[] vars) {
	return new jPredicateTerms(internal_duplicate(vars));
    };

    public jTerm copy(jVariableRegistry vars) {
	return new jPredicateTerms(internal_copy(vars));
    };

    public void addGoals(jGoal g, jVariable[] vars, iGoalStack goals) {
	int i;

	for (i = terms.size() - 1; i >= 0; i--)
	    ((iPredicate) terms.elementAt(i)).addGoals(g, vars, goals);
    };

    public void addGoals(jGoal g, iGoalStack goals) {
	int i;

	for (i = terms.size() - 1; i >= 0; i--)
	    ((iPredicate) terms.elementAt(i)).addGoals(g, goals);
    };

    protected String getStartingSymbol() {
	return "";
    };

    protected String getEndingSymbol() {
	return "";
    };

    /**
     * Makes this a representation of the provided <code>jTerm</code>, which may
     * include conjunction and disjunction operators. Invokes
     * <code>makePredicateTerms</code>.
     * 
     * @param t
     *            <code>jTerm</code> using <code>jCons</code> to separate terms.
     */
    public void make(jTerm t) {
	removeAllTerms();
	makePredicateTerms(t);
    };

    /**
     * Add <code>jCons</code> and <code>jOr</code> separated <code>jTerm</code>s
     * to this <code>jPredicateTerms</code>.
     * 
     * @param t
     *            <code>jTerm</code> using <code>jCons</code> and/or
     *            <code>jOr</code> to separate terms.
     */
    public void makePredicateTerms(jTerm t) {
	if (t instanceof jPredicateTerms) {
	    jPredicateTerms pterm = (jPredicateTerms) t;
	    Enumeration e = pterm.enumTerms();

	    while (e.hasMoreElements())
		addPredicate((iPredicate) e.nextElement());
	} else if (t instanceof jOr) {
	    jOrPredicate jop = new jOrPredicate();

	    addPredicate(jop);
	    makeOrPredicateTerms(jop, t);
	} else
	    makeConsPredicateTerms(t);
    };

    /**
     * Add <code>jOr</code> separated <code>jTerm</code>s to the provided
     * <code>jOrPredicate</code>.
     * 
     * @param orpred
     *            The <code>jOrPredicate</code> to add the <code>jOr</code>
     *            separated <code>jTerm</code>s to.
     * @param term
     *            <code>jTerm</code> using <code>jOr</code> to separate terms.
     */
    protected void makeOrPredicateTerms(jOrPredicate orpred, jTerm term) {
	jTerm current = term;
	jPredicateTerms pterm;

	while (current instanceof jOr) {
	    jTerm t = ((jOr) current).getLHS();

	    if (t instanceof jOr)
		makeOrPredicateTerms(orpred, t);
	    else {
		pterm = new jPredicateTerms();
		pterm.makeConsPredicateTerms(t);
		orpred.addPredicateTerms(pterm);
	    }
	    current = ((jOr) current).getRHS();
	}

	if (current != null) {
	    pterm = new jPredicateTerms();
	    pterm.makeConsPredicateTerms(current);
	    orpred.addPredicateTerms(pterm);
	}
    };

    /**
     * Add <code>jCons</code> separated <code>jTerm</code>s to this
     * <code>jPredicateTerms</code> instance.
     * 
     * @param term
     *            <code>jTerm</code> using <code>jOr</code> to separate terms.
     */
    protected void makeConsPredicateTerms(jTerm term) {
	if (term instanceof jCons) {
	    makePredicateTerms(((jCons) term).getLHS());
	    makePredicateTerms(((jCons) term).getRHS());
	} else if (term instanceof iPredicate)
	    addPredicate((iPredicate) term);
	else if (term instanceof jVariable)
	    addPredicate(new jCall(term));
	else if (term != null)
	    throw new PredicateExpectedException(
		    "Expected term to be a predicate or variable.", term);
    };

    /**
     * Creates a <code>jCons</code> and/or <code>jOr</code> separated
     * <code>jTerm</code>s to represent this <code>jPredicateTerms</code>.
     * Invokes <code>unmakePredicateTerms</code> to perform work.
     * 
     * @return <code>jTerm</code> using <code>jCons</code> and <code>jOr</code>
     *         to separate terms. Duplicates this <code>jPredicateTerms</code>.
     */
    public jTerm unmake() {
	return unmakePredicateTerms();
    };

    /**
     * Creates a <code>jCons</code> and/or <code>jOr</code> separated
     * <code>jTerm</code>s to represent this <code>jPredicateTerms</code>.
     * 
     * @return <code>jTerm</code> using <code>jCons</code> and <code>jOr</code>
     *         to separate terms. Duplicates this <code>jPredicateTerms</code>.
     */
    public synchronized jTerm unmakePredicateTerms() {
	int i;
	jTerm prev = null;

	for (i = terms.size() - 1; i >= 0; i--) {
	    iPredicate curr = (iPredicate) terms.elementAt(i);
	    jTerm next;

	    if (curr instanceof iMakeUnmake)
		next = ((iMakeUnmake) curr).unmake();
	    else
		next = curr;

	    if (prev == null)
		prev = next;
	    else
		prev = new jCons(next, prev);
	}
	return prev;
    };
};
